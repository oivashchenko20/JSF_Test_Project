package com.example.beans;

import com.example.DAO.FieldDao;
import com.example.dto.FieldDto;
import com.example.entity.Field;
import com.example.entity.Option;
import com.example.entity.Type;
import com.example.entity.User;
import com.example.validator.FieldValidator;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "fieldBean")
@SessionScoped
@Getter
@Setter
public class FieldsBean implements Serializable {
    private User user;
    private FieldDao fieldDao = new FieldDao();
    private FieldDto fieldDto = new FieldDto();
    private Field selectedField;
    private String selectedOption;
    private boolean showOption;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.user = (User) facesContext.getExternalContext().getSessionMap().get("user");
    }

    public void setSelectedField(Field selectedField) {
        this.selectedField = new Field(selectedField);
    }

    public List<Field> getAllFields() {
        return fieldDao.findAll();
    }

    public void reset() {
        fieldDto.setText("");
        fieldDto.setActive(false);
        fieldDto.setRequired(false);
        fieldDto.setType(Type.TEXT);
        fieldDto.setName("");
    }

    public String getSelectedOption() {
        String result = "";
        if (selectedField.getOptions() != null) {
            for (Option option1 : selectedField.getOptions()) {
                result += option1.getText() + "\r\n";
            }
        }
        return result;
    }

    public String addField() {
        if (!FieldValidator.validField(fieldDto.getName(), fieldDto.getType(), fieldDto.getText())) {
            return "field";
        }
        fieldDao.addField(fieldDto, user);
        return "field?faces-redirect=true";
    }

    public String deleteField() {
        fieldDao.delete(selectedField);
        return "field?faces-redirect=true";
    }

    public String updateField() {
        if (!FieldValidator.validField(selectedField.getName(), selectedField.getType(), selectedOption)) {
            return "field";
        }
        fieldDao.updateField(selectedField, user, selectedOption);
        return "field?faces-redirect=true";
    }
}
