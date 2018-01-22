package com.xaut.web.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Author : wangzhe
 * Date : on 2017/12/16
 * Description :
 * Version :
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfiguration {

    @Value("${sql.jdbc.driver}")
    private String driver;
    @Value("${sql.jdbc.url}")
    private String url;
    @Value("${sql.jdbc.username}")
    private String username;
    @Value("${sql.jdbc.password}")
    private String password;
    @Value("${sql.jdbc.maxActive}")
    private int maxActive;
    @Value("${sql.jdbc.maxIdel}")
    private int maxIdel;
    @Value("${sql.jdbc.minIdel}")
    private int minIdel;
    @Value("${sql.jdbc.maxWait}")
    private int maxWait;
    @Value("${sql.jdbc.validateQuery}")
    private String validateQuery;
    @Value("${sql.jdbc.validateInterval}")
    private long validateInterval;
    @Value("${sql.jdbc.initialSize}")
    private int initialSize;

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);//连接池的最大数据库连接数。设为0表示无限制。
        dataSource.setMaxIdle(maxIdel); //最大空闲数，数据库连接的最大空闲时间。默认100
        dataSource.setMinIdle(minIdel); //超过空闲时间，数据库连接将被标记为不可用，然后被释放。设为0表示无限制。
        dataSource.setMaxWait(maxWait);//最大建立连接等待时间, 如果超过此时间将接到异常。设为-1表示无限制。
        dataSource.setValidationQuery(validateQuery);//心跳连接验证
        dataSource.setValidationInterval(validateInterval);//心跳间隔
        dataSource.setInitialSize(initialSize);//初始化连接个数
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }
}
