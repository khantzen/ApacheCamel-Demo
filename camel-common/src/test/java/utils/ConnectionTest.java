package utils;

import com.kevin.demo.camel.common.utils.ConfigManager;
import com.kevin.demo.camel.common.utils.Connection;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ConnectionTest {

    @Before
    public void setUp() throws IOException {
        ConfigManager config = ConfigManager.getInstance();
        config.loadConfig(new String[] {"camel-common/src/test/resources/config-dev.properties"});
    }


    @Test
    public void testConnection() {
        SessionFactory sf = Connection.GetHibernateSessionFactory();
    }
}
