dataSource {
    //pooled = true
    //driverClassName = "oracle.jdbc.driver.OracleDriver"       
    jndiName = "java:comp/env/jdbc/EDEG"
    //dialect = org.hibernate.dialect.Oracle11gDialect   
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
			//loggingSql=true
        }
    }
    test {
        dataSource {
            dbCreate = "update"
			//loggingSql=true
        }
    }
    production {
        dataSource {
            dbCreate = "update"
			//loggingSql=true
        }
    }
}
