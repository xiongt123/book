package test.com.softeem.dao.impl; 

import com.softeem.bean.User;
import com.softeem.dao.UserDao;
import com.softeem.dao.impl.UserDaoImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* UserDaoImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>7月 22, 2022</pre> 
* @version 1.0 
*/ 
public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: findAll() 
* 
*/ 
@Test
public void testFindAll() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: save(User user) 
* 
*/ 
@Test
public void testSave() throws Exception { 
//TODO: Test goes here...
    User user = new User(null, "熊婷", "123", "123@qq.com");
    Long save = userDao.save(user);
    if(save>=1){
        System.out.println("user = " + user+"保存成功");

    }
} 

/** 
* 
* Method: updateById(User user) 
* 
*/ 
@Test
public void testUpdateById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteById(Integer id) 
* 
*/ 
@Test
public void testDeleteById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: findById(Integer id) 
* 
*/ 
@Test
public void testFindById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: page(Integer pageNumber) 
* 
*/ 
@Test
public void testPage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: pageRecord() 
* 
*/ 
@Test
public void testPageRecord() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: queryUserByUsername(String username) 
* 
*/ 
@Test
public void testQueryUserByUsername() throws Exception { 
//TODO: Test goes here...
    User user = userDao.queryUserByUsername("admin");
    System.out.println("user = " + user);
} 

/** 
* 
* Method: queryUserByUsernameAndPassword(String username, String password) 
* 
*/ 
@Test
public void testQueryUserByUsernameAndPassword() throws Exception { 
//TODO: Test goes here...
    User user = userDao.queryUserByUsernameAndPassword("admin", "admin");
    System.out.println("user = " + user);
} 


} 
