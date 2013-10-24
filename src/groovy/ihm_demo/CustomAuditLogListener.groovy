package ihm_demo;

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogListener
import org.hibernate.collection.PersistentCollection
import org.hibernate.engine.CollectionEntry
import org.hibernate.engine.PersistenceContext
import org.hibernate.event.PostUpdateEvent

class CustomAuditLogListener extends AuditLogListener {

    @Override
    void onPostUpdate(final PostUpdateEvent event) {
        if (isAuditableEntity(event)) {
            log.trace "${event.getClass()} onChange handler has been called"
            onChange(event)
        }
    }

    private void onChange(final PostUpdateEvent event) {
        def entity = event.getEntity()
        String entityName = entity.getClass().getName()
        def entityId = event.getId()

        // object arrays representing the old and new state
        def oldState = event.getOldState()
        def newState = event.getState()

        List<String> propertyNames = event.getPersister().getPropertyNames()
        Map oldMap = [:]
        Map newMap = [:]

        if (propertyNames) {
            for (int index = 0; index < newState.length; index++) {
                if (propertyNames[index]) {
                    if (oldState) {
                        populateOldStateMap(oldState, oldMap, propertyNames[index], index)
                    }
                    if (newState) {
                        newMap[propertyNames[index]] = newState[index]
                    }
                }
            }
        }

        if (!significantChangeCustom(entity, oldMap, newMap)) {
            return
        }

        // allow user's to over-ride whether you do auditing for them.
        if (!callHandlersOnly(event.getEntity())) {
            logChanges(newMap, oldMap, event, entityId, 'UPDATE', entityName)
        }
        executeHandler(event, 'onChange', oldMap, newMap)
        return
    }

    private populateOldStateMap(def oldState, Map oldMap, String keyName, index) {
        def oldPropertyState = oldState[index]
        if (oldPropertyState instanceof PersistentCollection) {
            PersistentCollection pc = (PersistentCollection) oldPropertyState;
            PersistenceContext context = sessionFactory.getCurrentSession().getPersistenceContext();
            CollectionEntry entry = context.getCollectionEntry(pc);
            Object snapshot = entry.getSnapshot();
            /*System.out.println(entry);
            System.out.println(snapshot);*/
            if (pc instanceof List) {
                oldMap[keyName] = Collections.unmodifiableList((List) snapshot);
            }
            else if (pc instanceof Map) {
                oldMap[keyName] = Collections.unmodifiableMap((Map) snapshot);
            }
            else if (pc instanceof Set) {
                //Set snapshot is actually stored as a Map
                if(!snapshot)
                {
                    //System.out.println("ERRRRRORRRRRR");
                    //System.out.println(keyName);
                    oldMap[keyName]=null
                }
                else
                {
                    Map snapshotMap = (Map) snapshot;
                    oldMap[keyName] = Collections.unmodifiableSet(new HashSet(snapshotMap?.values()));
                    //System.out.println(oldMap[keyName].class);
                }
            }
            else {
                oldMap[keyName] = pc;
            }
        }
        else {
            oldMap[keyName] = oldPropertyState
        }
    }
	
	
	private boolean significantChangeCustom(entity, oldMap, newMap) {
		def ignore = ignoreList(entity)
		ignore?.each({key ->
		  oldMap.remove(key)
		  newMap.remove(key)
		})
		boolean changed = false
		oldMap.each({k, v ->
			if (null!=v){
				if (null!=newMap[k] && v!=newMap[k])	
					changed = true
			}
			else{
				//if ("" != newMap[k])
					changed = true
			}
		})
		return changed
	  }
	
@Override
def logChanges(newMap, oldMap, parentObject, persistedObjectId, eventName, className) {
	log.trace "logging changes... "
	AuditLogEvent audit = null
	def persistedObjectVersion = (newMap?.version) ?: oldMap?.version
	if (newMap) newMap.remove('version')
	if (oldMap) oldMap.remove('version')
	if (newMap && oldMap) {
	  log.trace "there are new and old values to log"
	  newMap.each({key, val ->
		if ( (null!=oldMap[key]  && null!=val && val!=oldMap[key]) || (null==oldMap[key] && "" != val) ) {
		  audit = new AuditLogEvent(
				  actor: this.getActor(),
				  uri: this.getUri(),
				  className: className,
				  eventName: eventName,
				  persistedObjectId: persistedObjectId?.toString(),
				  persistedObjectVersion: persistedObjectVersion?.toLong(),
				  propertyName: key,
				  oldValue: truncate(oldMap[key]),
				  newValue: truncate(newMap[key]),
		  )
		  saveAuditLog(audit)
		}
	  })
	}
	else if (newMap && verbose) {
	  log.trace "there are new values and logging is verbose ... "
	  newMap.each({key, val ->
		audit = new AuditLogEvent(
				actor: this.getActor(),
				uri: this.getUri(),
				className: className,
				eventName: eventName,
				persistedObjectId: persistedObjectId?.toString(),
				persistedObjectVersion: persistedObjectVersion?.toLong(),
				propertyName: key,
				oldValue: null,
				newValue: truncate(val),
		)
		saveAuditLog(audit)
	  })
	}
	else if (oldMap && verbose) {
		log.trace "there is only an old map of values available and logging is set to verbose... "
		oldMap.each({key, val ->
		  audit = new AuditLogEvent(
				  actor: this.getActor(),
				  uri: this.getUri(),
				  className: className,
				  eventName: eventName,
				  persistedObjectId: persistedObjectId?.toString(),
				  persistedObjectVersion: persistedObjectVersion?.toLong(),
				  propertyName: key,
				  oldValue: truncate(val),
				  newValue: null
		  )
		  saveAuditLog(audit)
		})
	  }
	  else {
		log.trace "creating a basic audit logging event object."
		audit = new AuditLogEvent(
				actor: this.getActor(),
				uri: this.getUri(),
				className: className,
				eventName: eventName,
				persistedObjectId: persistedObjectId?.toString(),
				persistedObjectVersion: persistedObjectVersion?.toLong()
		)
		saveAuditLog(audit)
	  }
	  return
  }
	
}
