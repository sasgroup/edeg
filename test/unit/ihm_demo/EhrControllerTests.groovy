package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(EhrController)
@Mock(Ehr)
class EhrControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/ehr/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ehrInstanceList.size() == 0
        assert model.ehrInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.ehrInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ehrInstance != null
        assert view == '/ehr/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/ehr/show/1'
        assert controller.flash.message != null
        assert Ehr.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/ehr/list'

        populateValidParams(params)
        def ehr = new Ehr(params)

        assert ehr.save() != null

        params.id = ehr.id

        def model = controller.show()

        assert model.ehrInstance == ehr
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/ehr/list'

        populateValidParams(params)
        def ehr = new Ehr(params)

        assert ehr.save() != null

        params.id = ehr.id

        def model = controller.edit()

        assert model.ehrInstance == ehr
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/ehr/list'

        response.reset()

        populateValidParams(params)
        def ehr = new Ehr(params)

        assert ehr.save() != null

        // test invalid parameters in update
        params.id = ehr.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/ehr/edit"
        assert model.ehrInstance != null

        ehr.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/ehr/show/$ehr.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        ehr.clearErrors()

        populateValidParams(params)
        params.id = ehr.id
        params.version = -1
        controller.update()

        assert view == "/ehr/edit"
        assert model.ehrInstance != null
        assert model.ehrInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/ehr/list'

        response.reset()

        populateValidParams(params)
        def ehr = new Ehr(params)

        assert ehr.save() != null
        assert Ehr.count() == 1

        params.id = ehr.id

        controller.delete()

        assert Ehr.count() == 0
        assert Ehr.get(ehr.id) == null
        assert response.redirectedUrl == '/ehr/list'
    }
}
