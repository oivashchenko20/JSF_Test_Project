package com.example.validator;

import com.example.entity.Type;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

public class FieldValidator {
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    private static void setMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public static boolean validField(String name, Type type, String text) {
        boolean isTrueType = type.equals(Type.COMBOBOX) | type.equals(Type.RADIOBUTTON);
        if (name.trim().isEmpty()) {
            setMessage(BUNDLE.getString("field.name.empty"), BUNDLE.getString("field.name.empty"));
            return false;
        }
        if (name.length() > 255) {
            setMessage(BUNDLE.getString("field.name.length"), BUNDLE.getString("field.name.length"));
            return false;
        }
        if (isTrueType && text.trim().isEmpty()) {
            setMessage(BUNDLE.getString("field.option.empty"), BUNDLE.getString("field.option.empty"));
            return false;
        }
        if (isTrueType && text.length() > 255) {
            setMessage(BUNDLE.getString("field.option.length"), BUNDLE.getString("field.option.length"));
            return false;
        }
        return true;
    }
}
