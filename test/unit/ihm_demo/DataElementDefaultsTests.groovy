package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DataElementDefaults)
class DataElementDefaultsTests {

    void testSomething() {
       def dataElementDefaults =new DataElementDefaults(isIMO:true,
							   							location:"location",
														queryMnemonic:"queryMnemonic",
														valueSet:"valueSet",
														valueSetRequired:true,
														locationtype:"Internal")
		
	  assertEquals 'true, location, queryMnemonic, valueSet, true, Internal' , dataElementDefaults.toString()
    }
}
