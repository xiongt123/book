package test.com.softeem.service.impl; 

import com.softeem.bean.Tbook;
import com.softeem.dao.TbookDao;
import com.softeem.dao.impl.TbookDaoImpl;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.awt.print.Book;
import java.math.BigDecimal;

/** 
* BookServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>7�� 30, 2022</pre> 
* @version 1.0 
*/ 
public class BookServiceImplTest {
    private BookService bookService;
    @Before
public void before() throws Exception {
    bookService = new BookServiceImpl();
}

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: addBook(Tbook tbook) 
* 
*/ 
@Test
public void testAddBook() throws Exception { 
//TODO: Test goes here...
    bookService.addBook(new Tbook(null,"С�����֣��������У�",new BigDecimal(1000000),"1125",1000,0,"static/img/default.jpg"));
} 

/** 
* 
* Method: deleteBookById(Integer id) 
* 
*/ 
@Test
public void testDeleteBookById() throws Exception { 
//TODO: Test goes here...
    bookService.deleteBookById(22);
} 

/** 
* 
* Method: updateBook(Tbook tbook) 
* 
*/ 
@Test
public void testUpdateBook() throws Exception { 
//TODO: Test goes here...
    Tbook tbook = bookService.queryBookById(21);
    System.out.println("tbook = " + tbook);
    tbook.setName("abc");
    tbook.setStock(200);
    System.out.println("tbook.getName() = " + tbook.getName());
    System.out.println("tbook = " + tbook);
    bookService.updateBook(tbook);
} 

/** 
* 
* Method: queryBookById(Integer id) 
* 
*/ 
@Test
public void testQueryBookById() throws Exception { 
//TODO: Test goes here...
    Tbook tbook = bookService.queryBookById(38);
    System.out.println(tbook);
} 

/** 
* 
* Method: queryBooks() 
* 
*/ 
@Test
public void testQueryBooks() throws Exception { 
//TODO: Test goes here...
    for (Tbook queryBook : bookService.queryBooks()) {
        System.out.println(queryBook);
    }

} 


} 
