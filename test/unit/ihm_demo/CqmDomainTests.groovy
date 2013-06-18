package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CqmDomain)
class CqmDomainTests {

   void testSomething() {
      def cqmDomain =new CqmDomain(name:"name",
					   			   notes:"!")
		
	  assertEquals 'name, !' , cqmDomain.toString()
    }
}
