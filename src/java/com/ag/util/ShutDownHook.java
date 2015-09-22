/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.util;

import com.ag.dao.DaoHibernateImp;
import java.sql.Connection;

/**
 *
 * @author CIVEL
 */
public class ShutDownHook extends Thread {

    /* Antes de finalizar el programa la JVM invocara a este método
    para cerrar la conexión
    */
    @Override
    public void run() {
        try {
            Connection con = new DaoHibernateImp(null).getConnection();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
}
