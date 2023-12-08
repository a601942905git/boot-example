package com.boot.example;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.driver.executor.DriverJDBCExecutor;
import org.apache.shardingsphere.driver.jdbc.core.connection.ShardingSphereConnection;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.driver.jdbc.core.driver.DriverDataSourceCache;
import org.apache.shardingsphere.driver.jdbc.core.statement.ShardingSpherePreparedStatement;
import org.apache.shardingsphere.infra.binder.context.statement.SQLStatementContext;
import org.apache.shardingsphere.infra.config.props.ConfigurationProperties;
import org.apache.shardingsphere.infra.executor.kernel.model.ExecutionGroupContext;
import org.apache.shardingsphere.infra.executor.kernel.model.ExecutionGroupReportContext;
import org.apache.shardingsphere.infra.executor.sql.execute.engine.driver.jdbc.JDBCExecutorCallback;
import org.apache.shardingsphere.infra.executor.sql.prepare.AbstractExecutionPrepareEngine;
import org.apache.shardingsphere.infra.hint.HintValueContext;
import org.apache.shardingsphere.infra.instance.InstanceContext;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.infra.route.context.RouteContext;
import org.apache.shardingsphere.infra.session.connection.ConnectionContext;
import org.apache.shardingsphere.infra.session.query.QueryContext;
import org.apache.shardingsphere.infra.yaml.config.swapper.resource.YamlDataSourceConfigurationSwapper;
import org.apache.shardingsphere.metadata.persist.NewMetaDataPersistService;
import org.apache.shardingsphere.mode.manager.ContextManagerBuilderParameter;
import org.apache.shardingsphere.mode.metadata.NewMetaDataContextsFactory;
import org.apache.shardingsphere.readwritesplitting.route.ReadwriteSplittingDataSourceRouter;
import org.apache.shardingsphere.readwritesplitting.route.ReadwriteSplittingSQLRouter;
import org.apache.shardingsphere.readwritesplitting.rule.ReadwriteSplittingRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * @see com.zaxxer.hikari.HikariDataSource#getConnection()
 * @see com.zaxxer.hikari.util.DriverDataSource#getConnection()
 * @see org.apache.shardingsphere.driver.ShardingSphereDriver#connect(String, Properties)
 * @see DriverDataSourceCache#get(String, String)
 * @see YamlShardingSphereDataSourceFactory#createDataSource(byte[])
 *
 * 根据shardingSphere.yml配置文件创建数据源
 * @see YamlDataSourceConfigurationSwapper#swapToDataSources(Map)
 * - key：write_ds value：HikariDataSource
 * - key：read_ds_0 value：HikariDataSource
 * - key：read_ds_1 value：HikariDataSource
 * </p>
 *
 * <p>
 * 该方法中的checkDataSourceStates()方法会通过获取数据库连接的方式检查数据源是否可用
 * @see NewMetaDataContextsFactory#create(NewMetaDataPersistService, ContextManagerBuilderParameter, InstanceContext, Map)
 *
 * 1.获取ShardingSphereConnection
 * @see ShardingSphereDataSource#getConnection()
 * @see ShardingSphereConnection
 *
 * 2.获取ShardingSpherePreparedStatement
 * @see ShardingSphereConnection#prepareStatement(String)
 *
 * 3.执行ShardingSpherePreparedStatement
 * @see ShardingSpherePreparedStatement#execute()
 *
 * 4.获取目标数据源
 * @see ReadwriteSplittingSQLRouter#decorateRouteContext(RouteContext, QueryContext, ShardingSphereDatabase, ReadwriteSplittingRule, ConfigurationProperties, ConnectionContext)
 * @see ReadwriteSplittingDataSourceRouter#route(SQLStatementContext, HintValueContext)
 *
 * 5.使用正常的connection创建PreparedStatement
 * @see AbstractExecutionPrepareEngine#prepare(RouteContext, Map, Collection, ExecutionGroupReportContext)
 * @see DriverJDBCExecutor#execute(ExecutionGroupContext, QueryContext, Collection, JDBCExecutorCallback)
 *
 * 6.真正执行sql的地方
 * @see ShardingSpherePreparedStatement#createExecuteCallback()
 *
 * 获取结果集
 * @see ShardingSpherePreparedStatement#getResultSet()
 * </p>
 *
 */
@SpringBootApplication
public class ShardingSphereApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }
}
