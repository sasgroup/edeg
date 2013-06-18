package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(MeasureCategoryController)
@Mock(MeasureCategory)
class MeasureCategoryControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/measureCategory/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.measureCategoryInstanceList.size() == 0
        assert model.measureCategoryInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.measureCategoryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.measureCategoryInstance != null
        assert view == '/measureCategory/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/measureCategory/show/1'
        assert controller.flash.message != null
        assert MeasureCategory.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/measureCategory/list'

        populateValidParams(params)
        def measureCategory = new MeasureCategory(params)

        assert measureCategory.save() != null

        params.id = measureCategory.id

        def model = controller.show()

        assert model.measureCategoryInstance == measureCategory
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/measureCategory/list'

        populateValidParams(params)
        def measureCategory = new MeasureCategory(params)

        assert measureCategory.save() != null

        params.id = measureCategory.id

        def model = controller.edit()

        assert model.measureCategoryInstance == measureCategory
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/measureCategory/list'

        response.reset()

        populateValidParams(params)
        def measureCategory = new MeasureCategory(params)

        assert measureCategory.save() != null

        // test invalid parameters in update
        params.id = measureCategory.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/measureCategory/edit"
        assert model.measureCategoryInstance != null

        measureCategory.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/measureCategory/show/$measureCategory.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        measureCategory.clearErrors()

        populateValidParams(params)
        params.id = measureCategory.id
        params.version = -1
        controller.update()

        assert view == "/measureCategory/edit"
        assert model.measureCategoryInstance != null
        assert model.measureCategoryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/measureCategory/list'

        response.reset()

        populateValidParams(params)
        def measureCategory = new MeasureCategory(params)

        assert measureCategory.save() != null
        assert MeasureCategory.count() == 1

        params.id = measureCategory.id

        controller.delete()

        assert MeasureCategory.count() == 0
        assert MeasureCategory.get(measureCategory.id) == null
        assert response.redirectedUrl == '/measureCategory/list'
    }
}
