package com.example.dao;

import com.example.dto.FieldDto;
import com.example.entity.Field;
import com.example.entity.Option;
import com.example.entity.Type;
import com.example.entity.User;
import com.example.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class FieldDao {
    @PersistenceContext
    private EntityManager entityManager = JPAUtil.getEntityManager();

    public List<Field> findAll() {
        Query query = entityManager.createQuery(" from Field where is_delete=:delete");
        query.setParameter("delete", false);
        return query.getResultList();
    }

    public void addField(FieldDto fieldDto, User user) {
        try {
            entityManager.getTransaction().begin();
            Field field = new Field();
            field.setName(fieldDto.getName());
            field.setType(fieldDto.getType());
            field.setActive(fieldDto.isActive());
            field.setRequired(fieldDto.isRequired());
            field.setDelete(false);
            field.setAuthor(user);
            entityManager.persist(field);
            if (!fieldDto.getText().isEmpty()) {
                saveOptions(fieldDto.getText(), field);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    private void saveOptions(String selectOption, Field field) {
        if (selectOption != null) {
            String[] options = selectOption.trim().split("\r\n");
            for (String s : options) {
                Option option = new Option();
                option.setField(field);
                option.setText(s);
                entityManager.persist(option);
            }
        }
    }

    public void delete(Field field) {
        try {
            entityManager.getTransaction().begin();
            field.setDelete(true);
            entityManager.merge(field);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void updateField(Field field, User user, String text) {
        try {
            entityManager.getTransaction().begin();
            field.setAuthor(user);
            if (field.getType().equals(Type.COMBOBOX)|field.getType().equals(Type.RADIOBUTTON)) {
                deleteOption(field.getOptions());
                saveOptions(text, field);
            }
            entityManager.merge(field);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    private void deleteOption(List<Option> options) {
        try {
            for (Option option : options) {
                entityManager.remove(option);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
