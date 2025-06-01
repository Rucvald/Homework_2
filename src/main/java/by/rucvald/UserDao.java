package by.rucvald;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void create(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            if (isNull(session.find(User.class, user.getId()))) {
                session.persist(user);
                logger.info("Пользователь создан: {}", user);
            } else {
                logger.warn("Пользователь не создан, так как он уже существует: {}", user);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Ошибка при создании пользователя: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
        }
    }

    public User findById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Пользователь найден: {}", session.find(User.class, id));
            return session.find(User.class, id);
        } catch (HibernateException e) {
            logger.error("Ошибка при поиске пользователя: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
            return null;
        }
    }

    public void update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            if (nonNull(session.find(User.class, user.getId()))) {
                session.persist(user);
                logger.info("Пользователь обновлен: {}", user);
            } else {
                logger.warn("Пользователь не найден для обновления");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Ошибка при обновлении пользователя: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
        }
    }

    public void delete(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (nonNull(user)) {
                logger.info("Пользователь удален: {}", user);
                session.remove(user);
            } else {
                logger.info("Пользователь не найден для удаления");
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Ошибка при удалении пользователя: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
        }
    }

    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            logger.info("Пользователи удалены");
            return session.createQuery("from User, User.class").list();
        } catch (HibernateException e) {
            logger.error("Ошибка при поиске пользователей: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
            return null;
        }
    }

    public void deleteAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Ошибка при удалении всех пользователей: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Неизвестная ошибка: {}", e.getMessage());
        }
    }
}
