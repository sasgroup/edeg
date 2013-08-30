package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(DataElementDefaultsController)
@Mock(DataElementDefaults)
class DataElementDefaultsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/dataElementDefaults/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.dataElementDefaultsInstanceList.size() == 0
        assert model.dataElementDefaultsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.dataElementDefaultsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.dataElementDefaultsInstance != null
        assert view == '/dataElementDefaults/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/dataElementDefaults/show/1'
        assert controller.flash.message != null
        assert DataElementDefaults.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementDefaults/list'

        populateValidParams(params)
        def dataElementDefaults = new DataElementDefaults(params)

        assert dataElementDefaults.save() != null

        params.id = dataElementDefaults.id

        def model = controller.show()

        assert model.dataElementDefaultsInstance == dataElementDefaults
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementDefaults/list'

        populateValidParams(params)
        def dataElementDefaults = new DataElementDefaults(params)

        assert dataElementDefaults.save() != null

        params.id = dataElementDefaults.id

        def model = controller.edit()

        assert model.dataElementDefaultsInstance == dataElementDefaults
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/dataElementDefaults/list'

        response.reset()

        populateValidParams(params)
        def dataElementDefaults = new DataElementDefaults(params)

        assert dataElementDefaults.save() != null

        // test invalid parameters in update
        params.id = dataElementDefaults.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/dataElementDefaults/edit"
        assert model.dataElementDefaultsInstance != null

        dataElementDefaults.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/dataElementDefaults/show/$dataElementDefaults.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        dataElementDefaults.clearErrors()

        populateValidParams(params)
        params.id = dataElementDefaults.id
        params.version = -1
        controller.update()

        assert view == "/dataElementDefaults/edit"
        assert model.dataElementDefaultsInstance != null
        assert model.dataElementDefaultsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/dataElementDefaults/list'

        response.reset()

        populateValidParams(params)
        def dataElementDefaults = new DataElementDefaults(params)

        assert dataElementDefaults.save() != null
        assert DataElementDefaults.count() == 1

        params.id = dataElementDefaults.id

        controller.delete()

        assert DataElementDefaults.count() == 0
        assert DataElementDefaults.get(dataElementDefaults.id) == null
        assert response.redirectedUrl == '/dataElementDefaults/list'
    }
}
