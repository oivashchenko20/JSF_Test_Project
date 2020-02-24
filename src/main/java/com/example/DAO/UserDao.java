package com.example.DAO;

import com.example.dto.PasswordDto;
import com.example.encodingMD5.EncoderPassword;
import com.example.entity.User;
import com.example.util.JPAUtil;
import com.example.util.MailUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Random;
import java.util.ResourceBundle;

public class UserDao {
    @PersistenceContext
    private EntityManager entityManager = JPAUtil.getEntityManager();
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    public User getUserFromDb(String email, String password) {
        User user = new User();
        Query query = entityManager.createQuery("from User where email=:email and password=:password");
        query.setParameter("email", email);
        query.setParameter("password", EncoderPassword.getEncodePassword(password));
        if (query.getResultList().size() != 0) {
            user = (User) query.getSingleResult();
            return user;
        }
        return null;
    }

    private User findByEmail(String email) {
        User user = new User();
        Query query = entityManager.createQuery("from User where email=:email");
        query.setParameter("email", email);
        if (query.getResultList().size() != 0) {
            user = (User) query.getSingleResult();
            return user;
        }
        return null;
    }

    public void saveUser(User user) {
        try {
            entityManager.getTransaction().begin();
            user.setPassword(EncoderPassword.getEncodePassword(user.getPassword()));
            user.setActive(true);
            user.setRole("USER");
            entityManager.persist(user);
            MailUtil.sendMail(user.getEmail(), BUNDLE.getString("subject"), BUNDLE.getString("message"));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void updateUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void updatePassword(PasswordDto passwordDto, User user) {
        try {
            entityManager.getTransaction().begin();
            user.setPassword(EncoderPassword.getEncodePassword(passwordDto.getNewPassword()));
            entityManager.persist(user);
            MailUtil.sendMail(user.getEmail(), BUNDLE.getString("password.subject"), BUNDLE.getString("password.message"));
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    private String getRandPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();
        while (salt.length() < 18) {
            int index = (int) (rand.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public void forgotPassword(String email) {
        User user = findByEmail(email);
        String rand = getRandPassword();
        String message = "Hello, user \n" +
                "Welcome to Copybook. Your new password " + rand + " ";
        try {
            entityManager.getTransaction().begin();
            user.setPassword(EncoderPassword.getEncodePassword(rand));
            MailUtil.sendMail(email, BUNDLE.getString("forgotPassword.subject"), message);
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}