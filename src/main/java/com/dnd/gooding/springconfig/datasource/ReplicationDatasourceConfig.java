package com.dnd.gooding.springconfig.datasource;

import com.zaxxer.hikari.HikariDataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ReplicationDatasourceConfig {

    private final JpaProperties jpaProperties;
    private final ReplicationDataSourceProperties dataSourceProperties;

    /** 직접 생성한 dataSource 정보를 등록한다. */
    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new LinkedHashMap<>();

        DataSource masterDataSource =
                createDataSource(
                        dataSourceProperties.getDriverClassName(),
                        dataSourceProperties.getUsername(),
                        dataSourceProperties.getPassword(),
                        dataSourceProperties.getUrl());
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);

        for (ReplicationDataSourceProperties.Slave slave : dataSourceProperties.getSlaves().values()) {
            DataSource slaveDataSource =
                    createDataSource(
                            slave.getDriverClassName(), slave.getUsername(), slave.getPassword(), slave.getUrl());
            targetDataSources.put(slave.getName(), slaveDataSource);
        }

        ReplicationRoutingSource routingDataSource = new ReplicationRoutingSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    private DataSource createDataSource(
            String driverClassName, String userName, String password, String uri) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(driverClassName)
                .username(userName)
                .password(password)
                .url(uri)
                .build();
    }

    /**
     * Spring은 transaction 시작 시점에(query를 실행하기 전에) data source connection을 가져온다. transaction이 시작하면 같은
     * data source만 사용하게 된다. TransactionManager 식별 -> DataSource Connection 획득 -> Transaction
     * 동기화(Connection 저장) 따라서 query를 실행할 시점에 data source connection을 가져올 수 있도록 늦은 연결로 구현해야 한다.
     */
    @Bean
    public DataSource lazyRoutingDataSource(
            @Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    /** JPA에서 사용하는 EntityManagerFactory 설정 hibernate 설정을 직접 주입 */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("lazyRoutingDataSource") DataSource dataSource) {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder =
                createEntityManagerFactoryBuilder(jpaProperties);
        return entityManagerFactoryBuilder.dataSource(dataSource).packages("com.dnd").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
            JpaProperties jpaProperties) {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null);
    }

    /** JPA에서 사용할 TransactionManager 설정 */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
