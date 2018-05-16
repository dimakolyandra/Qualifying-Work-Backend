package com.brokersystem.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.brokersystem.models.Currency;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class BaseDAO <TypeObj, TypeKey> {
    
    private Class<TypeObj> typeObjClass;
    private SessionFactory sessionFactory;
    
    public Class<TypeObj> getTypeObjClass() {
        return typeObjClass;
    }

    public void setTypeObjClass(Class<TypeObj> typeObjClass) {
        this.typeObjClass = typeObjClass;
    }

    @Autowired
    public BaseDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    private Session currentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public void add(TypeObj newObj){
        currentSession().save(newObj);
    }
    
    public TypeObj getObj(TypeKey key){
        return (TypeObj) currentSession().get(typeObjClass, (Serializable) key);
    }
        
    public void updateObj(TypeKey obj){
        currentSession().update(obj);
    }
}
