package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Product)
class ProductTests {

    void testSomething() {
      def product =new Product(code:"prod",
							   name:"name",
							   notes:"")
		
	  assertEquals 'prod, name' , product.toString()
    }
}
