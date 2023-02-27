package test.com.softeem.dao.impl;

import com.softeem.bean.Tbook;
import com.softeem.dao.TbookDao;
import com.softeem.dao.impl.TbookDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class TbookDaoImplTest {
    TbookDao tbookDao;

    @Before
    public void before() throws Exception {
        tbookDao=new TbookDaoImpl();
    }

    @After
    public void after() throws Exception {

    }


    /**
     * Method: findAll()
     */
    @Test
    public void testFindAll() throws Exception {
        List<Tbook> all = tbookDao.findAll();
        for (Tbook tbook : all) {
            System.out.println("tbook = " + tbook);
        }
    }

    /**
     * Method: save(tbook tbook)
     */
    @Test
    public void testSave() throws Exception {
//TODO: Test goes here...
        Tbook tbook = new Tbook(null,"java入门到入土",new BigDecimal(88.8),"张三",
                333,100,"static/img/default.jpg");
        Long save = tbookDao.save(tbook);
        System.out.println(save);
    }

    /**
     * Method: updateById(tbook tbook)
     */
    @Test
    public void testUpdateById() throws Exception {
//TODO: Test goes here...
        /*Tbook tbook = new Tbook(21,"java入门到入土",new BigDecimal(88.8),
                "张三",433,0,"static/img/default.jpg");
        tbookDao.updateById(tbook);*/

        Tbook tbook = tbookDao.findById(39);
        tbook.setName("小花在手，天下我有!");
        tbookDao.updateById(tbook);
    }

    /**
     * Method: deleteById(Integer id)
     */
    @Test
    public void testDeleteById() throws Exception {
//TODO: Test goes here...
        tbookDao.deleteById(20);
    }

    /**
     * Method: findById(Integer id)
     */
    @Test
    public void testFindById() throws Exception {
//TODO: Test goes here...
        Tbook byId = tbookDao.findById(39);
        System.out.println(byId);
    }

    /**
     * Method: page(Integer pageNumber)
     */
    @Test
    public void testPage() throws Exception {
//TODO: Test goes here...
        List<Tbook> page = tbookDao.page(2);
        for (Tbook tbook : page) {
            System.out.println(tbook);
        }
    }

    /**
     * Method: pageRecord()
     */
    @Test
    public void testPageRecord() throws Exception {
//TODO: Test goes here...
        Integer record = tbookDao.pageRecord();
        System.out.println("record = " + record);
    }

}