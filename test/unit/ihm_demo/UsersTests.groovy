package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Users)
class UsersTests {

    void testSomething() {
       def user =new Users(username:"username",
						   password:"password",
						   role:"SystemUser",
						   hospital:"hospital")
	   
	  assertEquals 'username, password' , user.toString()
    }
}
