package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(HospitalMeasureController)
@Mock(HospitalMeasure)
class HospitalMeasureControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/hospitalMeasure/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.hospitalMeasureInstanceList.size() == 0
        assert model.hospitalMeasureInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.hospitalMeasureInstance != null
    }

    void testSave() {
        controller.save()

        assert model.hospitalMeasureInstance != null
        assert view == '/hospitalMeasure/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/hospitalMeasure/show/1'
        assert controller.flash.message != null
        assert HospitalMeasure.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalMeasure/list'

        populateValidParams(params)
        def hospitalMeasure = new HospitalMeasure(params)

        assert hospitalMeasure.save() != null

        params.id = hospitalMeasure.id

        def model = controller.show()

        assert model.hospitalMeasureInstance == hospitalMeasure
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalMeasure/list'

        populateValidParams(params)
        def hospitalMeasure = new HospitalMeasure(params)

        assert hospitalMeasure.save() != null

        params.id = hospitalMeasure.id

        def model = controller.edit()

        assert model.hospitalMeasureInstance == hospitalMeasure
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalMeasure/list'

        response.reset()

        populateValidParams(params)
        def hospitalMeasure = new HospitalMeasure(params)

        assert hospitalMeasure.save() != null

        // test invalid parameters in update
        params.id = hospitalMeasure.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/hospitalMeasure/edit"
        assert model.hospitalMeasureInstance != null

        hospitalMeasure.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/hospitalMeasure/show/$hospitalMeasure.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        hospitalMeasure.clearErrors()

        populateValidParams(params)
        params.id = hospitalMeasure.id
        params.version = -1
        controller.update()

        assert view == "/hospitalMeasure/edit"
        assert model.hospitalMeasureInstance != null
        assert model.hospitalMeasureInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/hospitalMeasure/list'

        response.reset()

        populateValidParams(params)
        def hospitalMeasure = new HospitalMeasure(params)

        assert hospitalMeasure.save() != null
        assert HospitalMeasure.count() == 1

        params.id = hospitalMeasure.id

        controller.delete()

        assert HospitalMeasure.count() == 0
        assert HospitalMeasure.get(hospitalMeasure.id) == null
        assert response.redirectedUrl == '/hospitalMeasure/list'
    }
}
