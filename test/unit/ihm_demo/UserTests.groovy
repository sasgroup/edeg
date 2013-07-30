package ihm_demo



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserTests {

    void testSomething() {
       def user =new User(username:"username",
						   password:"password",
						   role:"SystemUser",
						   hospital:"hospital")
	   
	  assertEquals 'username, password' , user.toString()
    }
}
