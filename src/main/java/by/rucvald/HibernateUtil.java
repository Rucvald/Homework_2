package by.rucvald;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import static java.util.Objects.nonNull;

public class HibernateUtil {

    @Getter
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    // Метод для настройки SessionFactory с параметрами подключения
    public static void setSessionFactory(String jdbcUrl, String username, String password) {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        try {
            Configuration configuration = new Configuration();

            // Установка параметров подключения
            configuration.setProperty("hibernate.connection.url", jdbcUrl);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop"); // или "create-drop" для тестирования

            // Создание ServiceRegistry и SessionFactory
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

