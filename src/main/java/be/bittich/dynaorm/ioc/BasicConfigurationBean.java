package be.bittich.dynaorm.ioc;

import be.bittich.dyanorm.connection.ConnectionDB;
import be.bittich.dynaorm.connection.impl.BasicConnectionDBImpl;
import static be.bittich.dynaorm.core.SystemConstant.DIALECT;
import static be.bittich.dynaorm.core.SystemConstant.DRIVER_NAME;
import be.bittich.dynaorm.dialect.Dialect;
import be.bittich.dynaorm.exception.BeanAlreadyExistException;
import be.bittich.dynaorm.exception.BeanNotFoundException;
import be.bittich.dynaorm.exception.IOCContainerException;
import static be.bittich.dynaorm.ioc.BasicContainer.getContainer;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.QueryRunner;

/**
 *
 * @author Nordine
 */
public class BasicConfigurationBean implements Serializable {

    private static final long serialVersionUID = -4279720585191618511L;

    /**
     * BeanBuilder. if you want to add new Bean to the container, it would be
     * better to extends BasicConfigurationBean and configure your beans inside
     * If you do that, do not forget to override the builder method and call
     * super()
     */
    public static void builder(Properties dbProperties) {
        configureConn(dbProperties);
        configureQueryRunner();
    }

    /**
     * Configure the bean for the connection to the database
     *
     * @param dbProperties
     */
    private static void configureConn(Properties dbProperties) {
        String driver = dbProperties.getProperty("driver");
        ConnectionDB conn = BasicConnectionDBImpl.getInstance()
                .setDriver(DRIVER_NAME.get(driver))
                .setLogin(dbProperties.getProperty("user"))
                .setPassword(dbProperties.getProperty("password"))
                .setUrl(dbProperties.getProperty("url"))
                .setInitialSize(Integer.parseInt(dbProperties.getProperty("initialSize")));
        registerBean("connectionDB", conn);

        //configure dialect
        configureDialect(driver);
    }

    /**
     * configure a QueryRunner bean
     */
    private static void configureQueryRunner() {

        ConnectionDB bean;
        try {
            bean = getContainer().inject("connectionDB");
            QueryRunner run = new QueryRunner(bean.getDataSource());
            registerBean("queryRunner", run);
        } catch (BeanNotFoundException ex) {
            Logger.getLogger(BasicConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void configureDialect(String dialect) {
        Dialect dialectB;
        try {
            dialectB = (Dialect) Class.forName(DIALECT.get(dialect)).newInstance();
            registerBean("dialect", dialectB);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(BasicConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Register a new bean
     *
     * @param <T>
     * @param t
     * @param label
     */
    public static <T> void registerBean(String label, T t) {
        Bean<T> bean = new Bean(label, t);
        try {
            getContainer().addBean(bean);
        } catch (BeanAlreadyExistException | IOCContainerException ex) {
            Logger.getLogger(BasicConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
