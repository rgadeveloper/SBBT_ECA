package com.ag.util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

public class HibernateUtil {

  private static final SessionFactory sessionFactory;
  
  static {
    try {
      sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Creación inicial del SessionFactory ha fallado." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
  
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
