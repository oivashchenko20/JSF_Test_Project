package com.example.validator;

import com.example.dto.PasswordDto;
import com.example.encodingMD5.EncoderPassword;
import com.example.entity.User;
import com.example.util.JPAUtil;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ResourceBundle;

public class LoginValidator {
    @PersistenceContext
    private static EntityManager entityManager = JPAUtil.getEntityManager();
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    private static void setMessage(String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                summary, detail));
    }

    public static void validateLogin() {
        setMessage(BUNDLE.getString("login.validation"), BUNDLE.getString("detail.login.validation"));
    }

    private static boolean compareEmail(String email) {
        Query query = entityManager.createQuery("from User where email=:email");
        query.setParameter("email", email);
        return query.getResultList().size() != 0;
    }

    private static boolean mainValid(User user) {
        if (user.getEmail().isEmpty()) {
            setMessage(BUNDLE.getString("email.required.valid"), BUNDLE.getString("email.required.valid"));
            return true;
        }
        if (!user.getEmail().matches("^\\S+@\\S+$")) {
            setMessage(BUNDLE.getString("email.pattern.valid"), BUNDLE.getString("email.pattern.valid"));
            return true;
        }
        if (!user.getFirstName().isEmpty() && !user.getFirstName().matches("^[a-zA-Z]{2,}$")) {
            setMessage(BUNDLE.getString("firstName.valid"), BUNDLE.getString("firstName.valid"));
            return true;
        }
        if (!user.getLastName().isEmpty() && !user.getLastName().matches("^[a-zA-Z]{2,}$")) {
            setMessage(BUNDLE.getString("lastName.valid"), BUNDLE.getString("lastName.valid"));
            return true;
        }
        if (!user.getMobile().isEmpty() && !user.getMobile().matches("(?:\\d{3}-){2}\\d{4}")) {
            setMessage(BUNDLE.getString("mobile.valid"), BUNDLE.getString("mobile.valid"));
            return true;
        }
        return false;
    }

    public static boolean validateProfile(User user, String firstUser) {
        if (mainValid(user)) {
            return false;
        }
        if (!firstUser.equals(user.getEmail())) {
            if (compareEmail(user.getEmail())) {
                setMessage(BUNDLE.getString("email.equals.valid"), BUNDLE.getString("email.equals.valid"));
                return false;
            }
        }
        return true;
    }

    public static boolean validateRegister(User user) {
        if (mainValid(user)) {
            return false;
        }
        if (compareEmail(user.getEmail())) {
            setMessage(BUNDLE.getString("email.equals.valid"), BUNDLE.getString("email.equals.valid"));
            return false;
        }
        if (user.getPassword().isEmpty()) {
            setMessage(BUNDLE.getString("password.required.valid"), BUNDLE.getString("password.required.valid"));
            return false;
        }
        if (user.getPassword().length() > 15) {
            setMessage(BUNDLE.getString("password.length.valid"), BUNDLE.getString("password.length.valid"));
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            setMessage(BUNDLE.getString("passwords.equals.valid"), BUNDLE.getString("passwords.equals.valid"));
            return false;
        }
        return true;
    }

    public static boolean validChangePassword(User user, PasswordDto passwordDto) {
        if (passwordDto.getOldPassword().isEmpty() | passwordDto.getNewPassword().isEmpty()) {
            setMessage(BUNDLE.getString("password.required.valid"), BUNDLE.getString("password.required.valid"));
            return false;
        }
        if (!user.getPassword().equals(EncoderPassword.getEncodePassword(passwordDto.getOldPassword()))) {
            setMessage(BUNDLE.getString("passwords.equals.valid"), BUNDLE.getString("passwords.equals.valid"));
            return false;
        }

        if (!passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword())) {
            setMessage(BUNDLE.getString("passwords.equals.valid"), BUNDLE.getString("passwords.equals.valid"));
            return false;
        }
        if (passwordDto.getNewPassword().length() > 15) {
            setMessage(BUNDLE.getString("password.length.valid"), BUNDLE.getString("password.length.valid"));
            return false;
        }
        return true;
    }

    public static boolean validateForgotPassword(String email) {
        if (!compareEmail(email)) {
            setMessage(BUNDLE.getString("forgotPassword.valid"), BUNDLE.getString("forgotPassword.valid"));
            return false;
        }
        return true;
    }
}
