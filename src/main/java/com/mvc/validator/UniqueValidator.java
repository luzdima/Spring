package com.mvc.validator;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
 

public class UniqueValidator implements ConstraintValidator<Unique, Serializable> {
 
	@Autowired 
	EntityManagerFactory emf;
	EntityManager entityManager;
 
    private String entityName;
    private String uniqueField;
 
    
    public void initialize(Unique unique) {
        entityName = unique.entityName();
        uniqueField = unique.uniqueField();
        entityManager = emf.createEntityManager();
    }
 
    
    public boolean isValid(Serializable property, ConstraintValidatorContext cvContext) {
    	
        String query = String.format("select %s from %s where %s = '%s' ", 
        		uniqueField, entityName, uniqueField, property);
        System.out.println(query);
        //List<?> list = hibernateTemplate.find(query, property);
        List<Object> list = entityManager.createNativeQuery(query).getResultList();
 
        return list != null && list.size() > 0;
    }
    
}