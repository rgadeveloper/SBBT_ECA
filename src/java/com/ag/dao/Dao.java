/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author 85154220
 */
public interface Dao {
    public void persist(Object entity);

    public void persist(Object[] entities);
    
    public void delete(Object entity);

    public <T> T load(Class<T> entityClass, Serializable id);

    public <T> List<T> find(String hql);

    public <T> T findObject(String hql);
    
    public <T> List<T> find(Class<T> entityClass);

    public <T> List<T> findByNamedQuery(String namedQuery,Object ... values);

    public <T> List<T> findByNamedQuery(String namedQuery);

    public <T> T findByNamedQueryObject(String namedQuery,Object ... values);

    public <T> T findByNamedQueryObject(String namedQuery);
    
    public Connection getConnection();
    
    public List executeQuerie(String sql);
    
    public Session getSessionHibernate();
    
    //Temporalmente
    public Connection getConnectionJDBC();
}
