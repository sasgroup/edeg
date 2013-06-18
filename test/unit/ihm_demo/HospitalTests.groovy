package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Hospital)
class HospitalTests {

    void testSomething() {
      def hospital =new Hospital(name:"Sun Fra Hospital",
								 notes:"NOTES",
								 ehr:[code:'code', name:"name", notes:"notes" ] as Ehr,
								 product:[code:'code', name:"name", notes:"notes" ] as Product)
	  assertEquals 'Sun Fra Hospital, NOTES' , hospital.toString()
    }
}
