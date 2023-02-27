package com.softeem.dao.impl;

import com.softeem.bean.Tbook;
import com.softeem.dao.TbookDao;
import com.softeem.utils.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TbookDaoImpl extends BaseDao implements TbookDao {


    @Override
    public List<Tbook> findAll() throws SQLException {
        BeanListHandler<Tbook> handler = new BeanListHandler<>(Tbook.class,hump());
        List<Tbook> query = queryRunner.query("select * from t_book order by id desc;", handler);
        return query;
    }

    @Override
    public Long save(Tbook tbook) throws SQLException {
        Long update = Long.valueOf(queryRunner.update("insert into t_book values(null,?,?,?,?,?,?)",
                tbook.getName(), tbook.getPrice(), tbook.getAuthor(), tbook.getSales(), tbook.getStock(), tbook.getImgPath()));
        return update;
    }

    @Override
    public int updateById(Tbook tbook) throws SQLException {
        int update = queryRunner.update("update t_book set name=?,price=?,author=?,sales=?,stock=?,img_path=? where id=?",
                tbook.getName(), tbook.getPrice(), tbook.getAuthor(), tbook.getSales(), tbook.getStock(), tbook.getImgPath(),
                tbook.getId());
        return update;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        int update = queryRunner.update("delete from t_book where id=?", id);
        return update;
    }

    @Override
    public Tbook findById(Integer id) throws SQLException {
        BeanHandler<Tbook> handler = new BeanHandler<>(Tbook.class,hump());
        Tbook query = queryRunner.query("select * from t_book where id=?", handler, id);
        return query;
    }

    @Override
    public List<Tbook> page(Integer pageNumber) throws SQLException {
        BeanListHandler<Tbook> handler = new BeanListHandler<>(Tbook.class,hump());
        List<Tbook> query = queryRunner.query("select * from t_book limit ?,?", handler, (pageNumber - 1) * pageSize, pageSize);
        return query;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query("select count(*) from t_book;", handler);
        return query.intValue();
    }

    public List<Tbook> search(String searchvalue) throws SQLException {
        /*BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);*/
        BeanListHandler<Tbook> handler = new BeanListHandler<>(Tbook.class,hump());

        /*方式一:字符串拼接*/
        /*List<Tbook> query = queryRunner.query("select * from t_book where name like '%"+searchvalue+"%'",
                handler);*/

        /*方式二: 占位符*/
        String sql="select * from t_book where name like ?";
        List<Tbook> query = queryRunner.query(sql,"'%"+searchvalue+"%'",
                handler);
        return query;
    }

    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query("select count(*) from t_book;", handler);
        return query.intValue();

    }

    @Override
    public List<Tbook> queryForPageItems(int begin, int pageSize) throws SQLException {
        BeanListHandler<Tbook> handler = new BeanListHandler<>(Tbook.class,hump());
        List<Tbook> query = queryRunner.query("select * from t_book order by id desc limit ?,?",
                handler, begin,pageSize);
        return query;
    }

    @Override
    public Integer queryForPageTotalCount(String name,String author,BigDecimal min, BigDecimal max) throws SQLException{
        StringBuilder sql = new StringBuilder("select count(*) from t_book where 1=1 ");
        ArrayList list = new ArrayList();
        if(name != null && !"".equals(name)){
            sql.append(" and name like ? ");
            list.add("%"+name+"%");
        }
        if(author != null && !"".equals(author)){
            sql.append(" and author like ? ");
            list.add("%"+author+"%");
        }
        if((min != null && min.signum()==1) && (max != null && max.signum()==1)){ //min 和 max都是正整数 >0
            //min值 大于 max值
            if(min.compareTo(max)==1){
                //进行两值交换
                if(min.compareTo(max)==1){
                    BigDecimal temp=min;
                    min=max;
                    max=temp;
                }
            }
            sql.append(" and price between ? and ? ");
            list.add(min);
            list.add(max);
        }else if(min != null && min.signum()==1){ // 仅仅是 min是正整数 >0
            sql.append(" and price > ? ");
            list.add(min);
        }else if(max != null && max.signum()==1){ // 仅仅是 max是正整数 >0
            sql.append(" and price < ? ");
            list.add(max);
        }

        ScalarHandler<Long> handler = new ScalarHandler<>();
        //Long query = queryRunner.query("select count(*) from t_book where price between ? and ?;", handler,min, max);
        //进阶版:
        Long query = queryRunner.query(sql.toString(),handler,list.toArray());
        return query.intValue();
    }

    @Override
    public List<Tbook> queryForPageItems(String name,String author,BigDecimal min, BigDecimal max,int begin, int pageSize) throws SQLException {
        StringBuilder sql = new StringBuilder("select * from t_book where 1=1 ");
        ArrayList list = new ArrayList();
        if(name != null && !"".equals(name)){
            sql.append("and name like ? ");
            list.add("%"+name+"%");
        }
        if(author != null && !"".equals(author)){
            sql.append("and author like ? ");
            list.add("%"+author+"%");
        }
        if((min != null && min.signum()==1) && (max != null && max.signum()==1)){ //min 和 max都是正整数 >0
            //min值 大于 max值
            if(min.compareTo(max)==1){
                //进行两值交换
                if(min.compareTo(max)==1){
                    BigDecimal temp=min;
                    min=max;
                    max=temp;
                }
            }
            sql.append("and price between ? and ? ");
            list.add(min);
            list.add(max);
        }else if(min != null && min.signum()==1){ // 仅仅是 min是正整数 >0
            sql.append("and price > ? ");
            list.add(min);
        }else if(max != null && max.signum()==1){ // 仅仅是 max是正整数 >0
            sql.append("and price < ? ");
            list.add(max);
        }

        String end="order by id desc limit ?,?";
        sql.append(end);
        list.add(begin);
        list.add(pageSize);

        BeanListHandler<Tbook> handler = new BeanListHandler<>(Tbook.class, hump());
        //List<Tbook> query = queryRunner.query("select * from t_book where price between ? and ? order by id desc limit ?,?",
        //        handler, min, max,begin,pageSize);
        //进阶版:
        List<Tbook> query = queryRunner.query(sql.toString(), handler, list.toArray());
        return query;
    }
}
