package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(HospitalElement)
class HospitalElementTests {

    void testSomething() {
       def hospitalElement =new HospitalElement(answer:"answer",
											    question:"question",
											    isIMO : true,
							   					location:"location",
												queryMnemonic:"queryMnemonic",
												valueSet:"valueSet",
												valueSetRequired:true,
												locationtype:"Internal")
		
	  assertEquals 'true, location, queryMnemonic, valueSet, true, Internal' , hospitalElement.toString()
    }
}
