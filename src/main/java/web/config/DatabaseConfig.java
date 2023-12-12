package web.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("web")
public class DatabaseConfig {

  private final Environment env;


  public DatabaseConfig(Environment env) {
    this.env = env;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("web.model");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    em.setJpaProperties(hibernateProperties());

    return em;
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
    dataSource.setUrl(env.getRequiredProperty("db.url"));
    dataSource.setUsername(env.getProperty("db.username"));
    dataSource.setPassword(env.getRequiredProperty("db.password"));

    return dataSource;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("db.show_sql", env.getRequiredProperty("db.show_sql"));
    properties.put("db.hbm2ddl.auto", env.getRequiredProperty("db.hbm2ddl.auto"));
    properties.put("db.dialect", env.getRequiredProperty("db.dialect"));

    return properties;
  }

//    @Bean
//    public LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("web.model");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }

//    @Bean
//    public HibernateTransactionManager getTransactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(getSessionFactory().getObject());
//        return transactionManager;
//    }

  @Bean
  public PlatformTransactionManager platformTransactionManager() {
    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

    return manager;
  }
}