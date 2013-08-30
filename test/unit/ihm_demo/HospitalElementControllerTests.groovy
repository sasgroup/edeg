package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(HospitalElementController)
@Mock(HospitalElement)
class HospitalElementControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/hospitalElement/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.hospitalElementInstanceList.size() == 0
        assert model.hospitalElementInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.hospitalElementInstance != null
    }

    void testSave() {
        controller.save()

        assert model.hospitalElementInstance != null
        assert view == '/hospitalElement/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/hospitalElement/show/1'
        assert controller.flash.message != null
        assert HospitalElement.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalElement/list'

        populateValidParams(params)
        def hospitalElement = new HospitalElement(params)

        assert hospitalElement.save() != null

        params.id = hospitalElement.id

        def model = controller.show()

        assert model.hospitalElementInstance == hospitalElement
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalElement/list'

        populateValidParams(params)
        def hospitalElement = new HospitalElement(params)

        assert hospitalElement.save() != null

        params.id = hospitalElement.id

        def model = controller.edit()

        assert model.hospitalElementInstance == hospitalElement
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/hospitalElement/list'

        response.reset()

        populateValidParams(params)
        def hospitalElement = new HospitalElement(params)

        assert hospitalElement.save() != null

        // test invalid parameters in update
        params.id = hospitalElement.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/hospitalElement/edit"
        assert model.hospitalElementInstance != null

        hospitalElement.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/hospitalElement/show/$hospitalElement.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        hospitalElement.clearErrors()

        populateValidParams(params)
        params.id = hospitalElement.id
        params.version = -1
        controller.update()

        assert view == "/hospitalElement/edit"
        assert model.hospitalElementInstance != null
        assert model.hospitalElementInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/hospitalElement/list'

        response.reset()

        populateValidParams(params)
        def hospitalElement = new HospitalElement(params)

        assert hospitalElement.save() != null
        assert HospitalElement.count() == 1

        params.id = hospitalElement.id

        controller.delete()

        assert HospitalElement.count() == 0
        assert HospitalElement.get(hospitalElement.id) == null
        assert response.redirectedUrl == '/hospitalElement/list'
    }
}
