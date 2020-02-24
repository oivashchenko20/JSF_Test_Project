package com.example.validator;

import com.example.entity.Field;
import com.example.entity.Type;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class HomeValidator {
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    private static void setMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public static boolean validateAnswers(Field field, String answer) {
        if (answer.trim().isEmpty()) {
            setMessage(BUNDLE.getString("answer.empty"), BUNDLE.getString("answer.empty"));
            return false;
        }
        if (field.getType().equals(Type.TEXT) && answer.length() > 255) {
            setMessage(BUNDLE.getString("answer.text.length"), BUNDLE.getString("answer.text.length"));
            return false;
        }
        if (field.getType().equals(Type.TEXTAREA) && answer.length()>255){
            setMessage(BUNDLE.getString("answer.textarea.length"), BUNDLE.getString("answer.textarea.length"));
            return false;
        }
            return true;
    }
}
