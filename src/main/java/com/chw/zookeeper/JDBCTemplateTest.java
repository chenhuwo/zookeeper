package com.chw.zookeeper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.concurrent.CountDownLatch;

/**
 * @author chw
 * 2018/5/10
 */
public class JDBCTemplateTest{


    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/wsc?useSSL=true");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        JdbcTemplate template = new JdbcTemplate(dataSource);
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    test(template);
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {

            threads[i].start();
        }

    }

    public static void test(JdbcTemplate template) {
        Long id = template.queryForObject("select goods_id from g_goods where status = '1' limit 1 for update", Long.class);
        template.update("update g_goods set status='0' where goods_id=" + id);
        System.out.println(id);
    }


}
