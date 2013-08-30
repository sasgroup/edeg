package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(CqmDomainController)
@Mock(CqmDomain)
class CqmDomainControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/cqmDomain/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.cqmDomainInstanceList.size() == 0
        assert model.cqmDomainInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.cqmDomainInstance != null
    }

    void testSave() {
        controller.save()

        assert model.cqmDomainInstance != null
        assert view == '/cqmDomain/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/cqmDomain/show/1'
        assert controller.flash.message != null
        assert CqmDomain.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/cqmDomain/list'

        populateValidParams(params)
        def cqmDomain = new CqmDomain(params)

        assert cqmDomain.save() != null

        params.id = cqmDomain.id

        def model = controller.show()

        assert model.cqmDomainInstance == cqmDomain
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/cqmDomain/list'

        populateValidParams(params)
        def cqmDomain = new CqmDomain(params)

        assert cqmDomain.save() != null

        params.id = cqmDomain.id

        def model = controller.edit()

        assert model.cqmDomainInstance == cqmDomain
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/cqmDomain/list'

        response.reset()

        populateValidParams(params)
        def cqmDomain = new CqmDomain(params)

        assert cqmDomain.save() != null

        // test invalid parameters in update
        params.id = cqmDomain.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/cqmDomain/edit"
        assert model.cqmDomainInstance != null

        cqmDomain.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/cqmDomain/show/$cqmDomain.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        cqmDomain.clearErrors()

        populateValidParams(params)
        params.id = cqmDomain.id
        params.version = -1
        controller.update()

        assert view == "/cqmDomain/edit"
        assert model.cqmDomainInstance != null
        assert model.cqmDomainInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/cqmDomain/list'

        response.reset()

        populateValidParams(params)
        def cqmDomain = new CqmDomain(params)

        assert cqmDomain.save() != null
        assert CqmDomain.count() == 1

        params.id = cqmDomain.id

        controller.delete()

        assert CqmDomain.count() == 0
        assert CqmDomain.get(cqmDomain.id) == null
        assert response.redirectedUrl == '/cqmDomain/list'
    }
}
