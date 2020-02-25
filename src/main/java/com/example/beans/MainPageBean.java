package com.example.beans;

import com.example.dao.MainDao;
import com.example.entity.*;
import com.example.validator.HomeValidator;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ManagedBean(name = "mainBean")
@SessionScoped
@Data
public class MainPageBean implements Serializable {
    private User user;
    private String answer;
    private Date dateAnswer;
    private boolean checkAnswer = false;
    private MainDao mainDao = new MainDao();
    private HashMap<Field, String> answers = new HashMap<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.user = (User) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public List<Option> getOptionsByField(Field field) {
        return mainDao.findOptions(field);
    }

    public void closeAnswer() {
        this.answer = "";
    }

    public void addToList(Field field) {
        if (field.getType().equals(Type.DATE)) {
            answer = dateFormat.format(dateAnswer);
        }
        if (HomeValidator.validateAnswers(field, this.answer)) {
            answers.put(field, this.answer);
            setCheckAnswer(true);
        }
    }

    public String addResponse() {
        if (checkAnswer) {
            List<Answer> selectAnswers = mainDao.getAllAnswers(answers);
            mainDao.addResponse(user, selectAnswers);
            return "respAnswer";
        }
        return "home";
    }

    public List<Response> getAllResponses() {
        return mainDao.findAllResponse();
    }

    public List<Answer> getAllAnswers() {
        return mainDao.findAllAnswers();
    }

    public List<Field> getAllFields() {
        return mainDao.findAllFields("from Field where is_delete=:delete and active=:active");
    }

    public List<Field> getFindFields() {
        List<Field> fields = mainDao.findAll("From Field");
        fields.sort(Comparator.comparingLong(Field::getId));
        return fields;
    }
}


