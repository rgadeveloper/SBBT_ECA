/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.service.SCalculaBalanceManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
//import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("SCalcularBalance")
public class SCalcularBalanceManagerImpl implements SCalculaBalanceManager{
    
    @Autowired
    
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
    @Override
    public String calcularBalance(String periodo, String idSimulacion) {
        try {
            Connection con = dao.getConnection();
            
            String sql = "{call P_S_CALCULO_BALANCES(?,?,?)}";
            CallableStatement  statement = con.prepareCall(sql);
            
            statement.setInt(1, Integer.parseInt(periodo));
            int idSim=Integer.parseInt(idSimulacion)-1;
            statement.setInt(2, idSim);
            
            statement.registerOutParameter(3,Types.VARCHAR);            
                        
            statement.executeQuery();
            
            String error = statement.getString(3);
            con.close();
            return error;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SCalcularBalanceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String copiarDatosSimulacion(String periodo) {
         try {
            Connection con = dao.getConnection();
            
            String sql = "{call P_INT_COPIA_SIMULACION(?,?)}";
            CallableStatement  statement = con.prepareCall(sql);
            
            statement.setInt(1, Integer.parseInt(periodo));            
            
            statement.registerOutParameter(2,Types.VARCHAR);            
                        
            statement.executeQuery();
            
            String error = statement.getString(2);
            con.close();
            return error;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(SCalcularBalanceManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
