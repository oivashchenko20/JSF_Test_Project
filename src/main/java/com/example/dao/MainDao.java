package com.example.dao;

import com.example.entity.*;
import com.example.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDao {
    @PersistenceContext
    private EntityManager entityManager = JPAUtil.getEntityManager();

    public List<Option> findOptions(Field field) {
        Query query = entityManager.createQuery("from Option where field_id=:field_id");
        query.setParameter("field_id", field.getId());
        return query.getResultList();
    }

    public void addResponse(User user, List<Answer> resultAnswer) {
        try {
            entityManager.getTransaction().begin();
            Response response = new Response();
            response.setAuthor(user);
            response.setAnswers(resultAnswer);
            entityManager.persist(response);
            addAnswer(resultAnswer, response);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    private void addAnswer(List<Answer> answers, Response response) {
        try {
            for (Answer answer : answers) {
                Answer newAnswer = new Answer();
                newAnswer.setField(answer.getField());
                if (answer.getText().trim().isEmpty()) {
                    newAnswer.setText("N/A");
                } else {
                    newAnswer.setText(answer.getText());
                }
                newAnswer.setResponse(response);
                entityManager.persist(newAnswer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public List<Answer> getAllAnswers(HashMap<Field, String> answers) {
        List<Answer> allAnswers = new ArrayList<>();
        for (Map.Entry<Field, String> entry : answers.entrySet()) {
            allAnswers.add(new Answer(entry.getKey(), entry.getValue()));
        }
        return allAnswers;
    }

    public List<Response> findAllResponse() {
        Query query = entityManager.createQuery("from Response");
        return query.getResultList();
    }

    public List<Answer> findAllAnswers() {
        Query query = entityManager.createQuery("from Answer");
        return query.getResultList();
    }

    public List<Field> findAllFields(String sql) {
        Query query = entityManager.createQuery(sql);
        query.setParameter("delete", false);
        query.setParameter("active", true);
        return query.getResultList();
    }

    public List<Field> findAll(String sql) {
        Query query = entityManager.createQuery(sql);
        return query.getResultList();
    }
}
