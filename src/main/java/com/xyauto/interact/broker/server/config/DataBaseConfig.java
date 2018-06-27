package com.xyauto.interact.broker.server.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@MapperScan(basePackages = "com.xyauto.interact.broker.server.dao")
public class DataBaseConfig {

    @Autowired
    private Environment env;

    // 数据源寄存
    public static class DsTempStorage {

        public enum Type {
            Master, SlaveFirst
        }

        private static ThreadLocal<Type> CTX = new ThreadLocal<>();

        public static void setType(Type type) {
            if (type == null) {
                throw new NullPointerException();
            }
            CTX.set(type);
        }

        public static Type getType() {
            return CTX.get() == null ? Type.SlaveFirst : CTX.get();
        }

        public static void clearDbType() {
            CTX.remove();
        }
    }

    public static class DynamicDataSource extends AbstractRoutingDataSource {
        @Override
        protected Object determineCurrentLookupKey() {
            return DsTempStorage.getType();
        }
    }

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource getMasterDataSource() throws Exception {
        return DruidDataSourceFactory.createDataSource(new Properties());
    }

    @Bean(name = "slave.first")
    @ConfigurationProperties(prefix = "druid.slave.first")
    public DataSource getFirstSlaveDataSource() throws Exception {
        return DruidDataSourceFactory.createDataSource(new Properties());
    }

    @Bean
    @Primary
    @DependsOn({"master", "slave.first"})
    public DynamicDataSource dyDataSource(
            @Qualifier("master") DataSource master,
            @Qualifier("slave.first") DataSource slaveFirst
    ) throws Exception {
        DynamicDataSource ds = new DynamicDataSource();
        Map<Object, Object> dataSources = Maps.newHashMap();
        dataSources.put(DataBaseConfig.DsTempStorage.Type.Master, master);
        dataSources.put(DataBaseConfig.DsTempStorage.Type.SlaveFirst, slaveFirst);
        ds.setTargetDataSources(dataSources);
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
        return fb.getObject();
    }

}
