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
        
		
		/*dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		}*/
		
		
		
		dataSource {
		  pooled = true
		   dialect = org.hibernate.dialect.Oracle11gDialect
		   driverClassName = 'oracle.jdbc.OracleDriver'
		   username = 'EDEG'
		   password = 'ihmedeg'
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
		   username = 'TEST'
		   password = 'ihmedeg'
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
		   username = 'EDEG'
		   password = 'ihmedeg'
		   url = 'jdbc:oracle:thin:@localhost:1521:IHM'
		   dbCreate = 'validate'
		   //logSql = true
        }
    }  
}
 
