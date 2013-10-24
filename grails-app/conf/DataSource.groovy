/*
dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
//ORACLE
environments {
    development {
<<<<<<< HEAD
=======
        
		
		/*dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		}*/
		
		
		
>>>>>>> 6d375940d956b629924e7e4dc473980218d592ff
		dataSource {
		  pooled = true
		   dialect = org.hibernate.dialect.Oracle11gDialect
		   driverClassName = 'oracle.jdbc.OracleDriver'
		   username = 'sys as sysdba'
		   password = 'sys'
		   url = 'jdbc:oracle:thin:@localhost:1521:IHM'
		   dbCreate = 'validate' // 'create-drop' // 
		   //logSql = true
       }
       
		
    }   
    test {
      dataSource {
           pooled = true
		   dialect = org.hibernate.dialect.Oracle11gDialect
		   driverClassName = 'oracle.jdbc.OracleDriver'
		   username = 'sys as sysdba'
		   password = 'sys'
		   url = 'jdbc:oracle:thin:@localhost:1521:IHM'
		   dbCreate = 'validate'
		   //logSql = true
        }
    }
    production { 
		dataSource {
           pooled = true
		   dialect = org.hibernate.dialect.Oracle11gDialect
		   driverClassName = 'oracle.jdbc.OracleDriver'
		   username = 'sys as sysdba'
		   password = 'sys'
		   url = 'jdbc:oracle:thin:@localhost:1521:IHM'
		   dbCreate = 'validate'
		   //logSql = true
        }
    }  
}
*/
 
dataSource {
    pooled = true
    driverClassName = "oracle.jdbc.driver.OracleDriver"       
    jndiName = "java:comp/env/jdbc/EDEG"
    dialect = org.hibernate.dialect.OracleDialect   
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
        }
    }
    test {
        dataSource {
            dbCreate = "update"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
        }
    }
}
