package com.example.lambdajavatemplate.config;


import com.example.lambdajavatemplate.scrtmanager.AWSSecretManagerInformatioDAO;
import com.example.lambdajavatemplate.scrtmanager.AWSSecretManagerRetriver;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@NoArgsConstructor
public class JPAConfig {
    @Bean
    public DataSource dataSource() {
        final AWSSecretManagerInformatioDAO dbCredentials = AWSSecretManagerRetriver.getSecret();

        var dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl("jdbc:postgresql://" +
                dbCredentials.getHost()
                + ":" + dbCredentials.getPort() + "/iprovider_sme_dev?currentSchema=aws-sample");
        dataSource.setUsername(dbCredentials.getUsername());
        dataSource.setPassword(dbCredentials.getPassword());

        return dataSource;
    }


    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        jpaVendorAdapter.setGenerateDdl(true);

        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource());
        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lemfb.setPackagesToScan("com.example.lambdajavatemplate.**.*");
        return lemfb;
    }
}
