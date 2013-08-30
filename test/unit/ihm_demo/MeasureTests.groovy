package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Measure)
class MeasureTests {

    void testSomething() {
       def measure = new Measure(code:"meas",
							     name:"name",
							     notes:"",
								 measureCategory:[name:"name",description:'description', categoryType:[core:'core', menu:"menu", cqm:"cqm" ] as CategoryType ] as MeasureCategory,
								 cqmDomain:[name:"name", notes:"notes" ] as CqmDomain)
	  
	   measure.save()
	   
	   assertEquals 'meas, name' , measure.toString()
    }
}
