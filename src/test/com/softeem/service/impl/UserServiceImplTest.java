package test.com.softeem.service.impl; 

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>7�� 22, 2022</pre> 
* @version 1.0 
*/ 
public class UserServiceImplTest {
    UserService userService = new UserServiceImpl();

    @Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: registUser(User user) 
* 
*/ 
@Test
public void testRegistUser() throws Exception { 
//TODO: Test goes here...
    userService.registUser(new User(null, "����", "666666", "bbj168@qq.com"));
    userService.registUser(new User(null, "abc168", "666666", "abc168@qq.com"));
} 

/** 
* 
* Method: login(User user) 
* 
*/ 
@Test
public void testLogin() throws Exception { 
//TODO: Test goes here...
    System.out.println( userService.login(new User(null, "����", "123", null)) );
} 

/** 
* 
* Method: existsUsername(String username) 
* 
*/ 
@Test
public void testExistsUsername() throws Exception { 
//TODO: Test goes here...
    if (userService.existsUsername("wzg16888")) {
        System.out.println("�û����Ѵ��ڣ�");
    } else {
        System.out.println("�û������ã�");
    }

} 


} 
