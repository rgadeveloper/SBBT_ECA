/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.service.impl;

import com.ag.dao.Dao;
import com.ag.service.OperativoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Larry
 */

@Service("OperativoManager")


public class OperativoManagerImpl implements OperativoManager{


    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;

    @Override
    public int validarMacro(String codigoTrafo,String codigoMacro ){
       String hql="";
       int retorno =0;
       int contTrafos =0;
       int contTrafoAct =0;
       int contMacros =0;

       // 1. El trafo debe existir.
        hql = " select count(*)"
                + " from  Componente t "
                + " where t.tipoComponente.idTipoComponente = 8"
                + " and   t.idComponente = " + codigoTrafo;

        contTrafos = Integer.parseInt(String.valueOf(dao.findObject(hql)));

        if (contTrafos > 0){
           //2. Si el trafo esta activo
           hql = " select count(*)"
                + " from  Componente t "
                + " where t.tipoComponente.idTipoComponente = 8"
                + " and   t.idComponente = " + codigoTrafo
                + "and t.idestado = 'AC001'";
            
           contTrafoAct = Integer.parseInt(String.valueOf(dao.findObject(hql)));

           if(contTrafoAct >0){

            //3. Revisar si existe el macromedidor.
            hql="select count(*)"
                    + "from ComponenteMedida c"
                    + "where c.idComponenteMedida = 1"
                    + "and   c.estado  = 'AC001'";

            contMacros = Integer.parseInt(String.valueOf(dao.findObject(hql)));

            if(contMacros < 0){
                retorno = 3;

            }


           }else{
                retorno =2;
           }

        }else{
            retorno=1;
        }

        return retorno;


    }

}
