package com.lavalliere.daniel.spring.noboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

// Refer to
//      https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
//      https://blog.jetbrains.com/idea/2021/02/creating-a-simple-jpa-application/

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

    // To use JPA in a Spring project, we need to set up the EntityManager.
    // This is the main part of the configuration and we can do it via a Spring factory bean.
    // This can be either the simpler LocalEntityManagerFactoryBean or the
    // more flexible LocalContainerEntityManagerFactoryBean.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.lavalliere.daniel.spring.noboot" });

        /*
           The following is standard bean configuration that would be required so that the bean is recognized

           <bean id="myEmf"
              class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
                <property name="dataSource" ref="dataSource" />
                <property name="packagesToScan" value="com.baeldung.persistence.model" />
                <property name="jpaVendorAdapter">
                    <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
                </property>
                <property name="jpaProperties">
                    <props>
                        <prop key="hibernate.hbm2ddl.auto">create-drop</prop>
                        <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</prop>
                    </props>
                </property>
            </bean>

            <bean id="dataSource"
              class="org.springframework.jdbc.datasource.DriverManagerDataSource">
                <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://localhost:3306/spring_jpa" />
                <property name="username" value="tutorialuser" />
                <property name="password" value="tutorialmy5ql" />
            </bean>

            <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
                <property name="entityManagerFactory" ref="myEmf" />
            </bean>
            <tx:annotation-driven />

            <bean id="persistenceExceptionTranslationPostProcessor" class=
              "org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor" />
         */

        // Usually, JPA defines a persistence unit through the META-INF/persistence.xml file.
        // Starting with Spring 3.1, the persistence.xml is no longer necessary.
        // The LocalContainerEntityManagerFactoryBean now supports a packagesToScan property
        // where the packages to scan for @Entity classes can be specified.
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // This file was the last piece of XML we need to remove. We can now set up JPA fully with no XML.
        // We would usually specify JPA properties in the persistence.xml file. Alternatively,
        // we can add the properties directly to the entity manager factory bean:
        em.setJpaProperties(additionalProperties());

        return em;
    }

    // We also need to explicitly define the DataSource bean we've used above:
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring_jpa");
        dataSource.setUsername( "tutorialuser" );
        dataSource.setPassword( "tutorialmy5ql" );
        return dataSource;
    }

    // PlatformTransactionManager is the central interface in Spring's imperative transaction infrastructure
    @Bean
    public PlatformTransactionManager transactionManager() {

        // There's a relatively small difference between the XML and the new Java-based configuration.
        // Namely, in XML, a reference to another bean can point to either the bean or
        // a bean factory for that bean

        //In Java, however, since the types are different, the compiler doesn't allow it, and so
        // the EntityManagerFactory is first retrieved from its bean factory and
        // then passed to the transaction manager:

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // Additional configuration information
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }
}
