package test.com.softeem.dao;

import com.softeem.bean.Order;
import com.softeem.dao.OrderDao;
import com.softeem.dao.impl.OrderDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDaoTest {
    @Test
    public void saveOrder() throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.save(new Order(""+System.currentTimeMillis(),new Timestamp(System.currentTimeMillis()),new BigDecimal(100),0, 1));
    }
}
