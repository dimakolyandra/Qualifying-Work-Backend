package com.brokersystem.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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
    
    public Session currentSession(){
        return sessionFactory.getCurrentSession();
    }
    
    private void refreshObject(TypeObj obj){
        currentSession().refresh(obj);
    }
    
    public void add(TypeObj newObj){
        currentSession().save(newObj);
    }
    
    public void addList(List<TypeObj> listObjs){
        for(TypeObj obj: listObjs){
            currentSession().save(obj);
        }
    }
    
    public TypeObj getObj(TypeKey key){
        return (TypeObj) currentSession().get(typeObjClass, (Serializable) key);
    }
        
    public void updateObj(TypeObj obj){
        currentSession().update(obj);
    }
    
    public List<TypeObj> getObjectsByFieldsValue(Map<String, String> fieldsValue){
        Criteria criteria = currentSession().createCriteria(typeObjClass);
        for(Map.Entry<String, String> field: fieldsValue.entrySet()){
            criteria.add(Restrictions.eq(field.getKey(), field.getValue()));
        }
        return criteria.list();
    }
    
    public List<TypeObj> getAllObjects(){
        return currentSession().createCriteria(typeObjClass).list();
    }

}
