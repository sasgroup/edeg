package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DataElement)
class DataElementTests {

    void testSomething() {
       def dataElement =new DataElement(code:"de",
							   name:"name",
							   notes:"")
		
	  assertEquals 'de, name' , dataElement.toString()
    }
}
