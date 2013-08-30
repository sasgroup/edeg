package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(MeasureController)
@Mock(Measure)
class MeasureControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/measure/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.measureInstanceList.size() == 0
        assert model.measureInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.measureInstance != null
    }

    void testSave() {
        controller.save()

        assert model.measureInstance != null
        assert view == '/measure/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/measure/show/1'
        assert controller.flash.message != null
        assert Measure.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/measure/list'

        populateValidParams(params)
        def measure = new Measure(params)

        assert measure.save() != null

        params.id = measure.id

        def model = controller.show()

        assert model.measureInstance == measure
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/measure/list'

        populateValidParams(params)
        def measure = new Measure(params)

        assert measure.save() != null

        params.id = measure.id

        def model = controller.edit()

        assert model.measureInstance == measure
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/measure/list'

        response.reset()

        populateValidParams(params)
        def measure = new Measure(params)

        assert measure.save() != null

        // test invalid parameters in update
        params.id = measure.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/measure/edit"
        assert model.measureInstance != null

        measure.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/measure/show/$measure.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        measure.clearErrors()

        populateValidParams(params)
        params.id = measure.id
        params.version = -1
        controller.update()

        assert view == "/measure/edit"
        assert model.measureInstance != null
        assert model.measureInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/measure/list'

        response.reset()

        populateValidParams(params)
        def measure = new Measure(params)

        assert measure.save() != null
        assert Measure.count() == 1

        params.id = measure.id

        controller.delete()

        assert Measure.count() == 0
        assert Measure.get(measure.id) == null
        assert response.redirectedUrl == '/measure/list'
    }
}
