package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MeasureCategory)
class MeasureCategoryTests {

    void testSomething() {
        def measureCategory =new MeasureCategory(name:"name",
								 				 description:"description",
												 categoryType:[core:'core', menu:"menu", cqm:"cqm" ] as CategoryType)
		assertEquals 'name, description' , measureCategory.toString()
    }
}
