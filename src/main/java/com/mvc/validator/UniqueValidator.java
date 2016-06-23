package com.mvc.validator;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
 

public class UniqueValidator implements ConstraintValidator<Unique, Serializable> {
 
	@Autowired 
	DataSource dataSource;
 
    private String entityName;
    private String uniqueField;
 
    
    public void initialize(Unique unique) {
        entityName = unique.entityName();
        uniqueField = unique.uniqueField();
    }
 
    
    public boolean isValid(Serializable property, ConstraintValidatorContext cvContext) {
    	
        String query = String.format("select %s from %s where %s = '%s' ", 
        		uniqueField, entityName, uniqueField, property);
 
        try {
			return !dataSource.getConnection().createStatement().executeQuery(query).next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
    }
    
}