package com.example.beans;

import com.example.DAO.UserDao;
import com.example.dto.PasswordDto;
import com.example.entity.User;
import com.example.validator.LoginValidator;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@SessionScoped
@Getter
@Setter
public class LoginBean implements Serializable {

    private User user = new User();
    private UserDao userDao = new UserDao();
    private PasswordDto passwordDto = new PasswordDto();
    private String userMail;
    private User selectedUser;
    private boolean rememberMe;

    public String loginProject() {
        FacesContext context = FacesContext.getCurrentInstance();
        User userFromDb = userDao.getUserFromDb(user.getEmail(), user.getPassword());
        if (userFromDb != null) {
            selectedUser = userFromDb;
            userMail = userFromDb.getEmail();
            context.getExternalContext().getSessionMap().put("user", selectedUser);
            context.getExternalContext().getSessionMap().put("role", selectedUser.getRole());
            if (rememberMe) {
                Cookie email = new Cookie("userEmail", userMail);
                context.getExternalContext().getSessionMap().put("userEmail", selectedUser.getEmail());
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.addCookie(email);
            }
            return "home?faces-redirect=true";
        } else {
            LoginValidator.validateLogin();
            return "login";
        }
    }

    public String registration() {
        if (LoginValidator.validateRegister(user)) {
            userDao.saveUser(user);
            return "login?faces-redirect=true";
        }
        return "registration";
    }

    public String updateUser() {
        if (LoginValidator.validateProfile(selectedUser, userMail)) {
            userDao.updateUser(selectedUser);
            return "home?faces-redirect=true";
        }
        return "editProfile";
    }

    public String changePassword() {
        if (LoginValidator.validChangePassword(selectedUser, passwordDto)) {
            userDao.updatePassword(passwordDto, selectedUser);
            return "home?faces-redirect=true";
        }
        return "changePassword";
    }

    public String forgotPassword() {
        if (LoginValidator.validateForgotPassword(user.getEmail())) {
          userDao.forgotPassword(user.getEmail());
            return "forgotAnswer";
        }
        return "forgotPassword";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        return "login?faces-redirect=true";
    }
}
