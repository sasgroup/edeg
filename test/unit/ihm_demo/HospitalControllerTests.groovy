package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(HospitalController)
@Mock(Hospital)
class HospitalControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/hospital/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.hospitalInstanceList.size() == 0
        assert model.hospitalInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.hospitalInstance != null
    }

    void testSave() {
        controller.save()

        assert model.hospitalInstance != null
        assert view == '/hospital/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/hospital/show/1'
        assert controller.flash.message != null
        assert Hospital.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/hospital/list'

        populateValidParams(params)
        def hospital = new Hospital(params)

        assert hospital.save() != null

        params.id = hospital.id

        def model = controller.show()

        assert model.hospitalInstance == hospital
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/hospital/list'

        populateValidParams(params)
        def hospital = new Hospital(params)

        assert hospital.save() != null

        params.id = hospital.id

        def model = controller.edit()

        assert model.hospitalInstance == hospital
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/hospital/list'

        response.reset()

        populateValidParams(params)
        def hospital = new Hospital(params)

        assert hospital.save() != null

        // test invalid parameters in update
        params.id = hospital.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/hospital/edit"
        assert model.hospitalInstance != null

        hospital.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/hospital/show/$hospital.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        hospital.clearErrors()

        populateValidParams(params)
        params.id = hospital.id
        params.version = -1
        controller.update()

        assert view == "/hospital/edit"
        assert model.hospitalInstance != null
        assert model.hospitalInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/hospital/list'

        response.reset()

        populateValidParams(params)
        def hospital = new Hospital(params)

        assert hospital.save() != null
        assert Hospital.count() == 1

        params.id = hospital.id

        controller.delete()

        assert Hospital.count() == 0
        assert Hospital.get(hospital.id) == null
        assert response.redirectedUrl == '/hospital/list'
    }
}
