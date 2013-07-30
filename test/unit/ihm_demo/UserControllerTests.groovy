package ihm_demo



import org.junit.*
import grails.test.mixin.*

@TestFor(UserController)
@Mock(User)
class UserControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/users/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.usersInstanceList.size() == 0
        assert model.usersInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.usersInstance != null
    }

    void testSave() {
        controller.save()

        assert model.usersInstance != null
        assert view == '/users/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/users/show/1'
        assert controller.flash.message != null
        assert User.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/users/list'

        populateValidParams(params)
        def users = new User(params)

        assert users.save() != null

        params.id = users.id

        def model = controller.show()

        assert model.usersInstance == users
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/users/list'

        populateValidParams(params)
        def users = new User(params)

        assert users.save() != null

        params.id = users.id

        def model = controller.edit()

        assert model.usersInstance == users
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/users/list'

        response.reset()

        populateValidParams(params)
        def users = new User(params)

        assert users.save() != null

        // test invalid parameters in update
        params.id = users.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/users/edit"
        assert model.usersInstance != null

        users.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/users/show/$users.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        users.clearErrors()

        populateValidParams(params)
        params.id = users.id
        params.version = -1
        controller.update()

        assert view == "/users/edit"
        assert model.usersInstance != null
        assert model.usersInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/users/list'

        response.reset()

        populateValidParams(params)
        def users = new User(params)

        assert users.save() != null
        assert User.count() == 1

        params.id = users.id

        controller.delete()

        assert User.count() == 0
        assert User.get(users.id) == null
        assert response.redirectedUrl == '/users/list'
    }
}
