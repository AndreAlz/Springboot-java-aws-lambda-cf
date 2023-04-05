package com.example.lambdajavatemplate.snapstart;

import org.crac.Context;
import org.crac.Core;
import org.crac.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class SnapStartConfig implements Resource{
    LocalContainerEntityManagerFactoryBean _dataSourceBean;
    public SnapStartConfig(LocalContainerEntityManagerFactoryBean dataSourceBean)
    {


        Core.getGlobalContext().register(SnapStartConfig.this);

        _dataSourceBean = dataSourceBean;
    }

    @Override
    public void beforeCheckpoint(Context<? extends Resource> context) throws Exception {
        DataSource dataSource = _dataSourceBean.getDataSource();
        Connection databaseConnection = dataSource.getConnection();

        if (!databaseConnection.isClosed())
        {

            databaseConnection.close();
        }
    }

    @Override
    public void afterRestore(Context<? extends Resource> context) throws Exception {
    }
}
