package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Ehr)
class EhrTests {

    void testSomething() {
      def ehr =new Ehr(code:"ehr",
					   name:"name",
					   notes:"")
		
	  assertEquals 'ehr, name' , ehr.toString()
    }
}
