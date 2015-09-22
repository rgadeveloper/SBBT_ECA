/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.view.Document;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
//import org.primefaces.showcase.domain.Document;
 
@ManagedBean(name = "documentService1")
@ApplicationScoped
public class DocumentService1 {
     
    public TreeNode createDocuments() {
       TreeNode root = new DefaultTreeNode(new Document("Prodecimientos", "-", "Folder"), null);
        
       TreeNode transformador = new DefaultTreeNode(new Document("Transformador", "-", "Folder"), root);
        TreeNode circuito = new DefaultTreeNode(new Document("Circuito", "-", "Folder"), transformador);
        TreeNode barrio = new DefaultTreeNode(new Document("Barrio", "-", "Folder"), circuito);
        TreeNode subestacion = new DefaultTreeNode(new Document("Subestación", "-", "Folder"), barrio);
         
        TreeNode poblacion = new DefaultTreeNode(new Document("Población", "-", "Folder"), subestacion);
        TreeNode delegacion = new DefaultTreeNode(new Document("Delegación", "-", "Folder"), poblacion);
        TreeNode municipio = new DefaultTreeNode(new Document("Municipio", "-", "Folder"), delegacion);
         
        TreeNode zona = new DefaultTreeNode(new Document("Zona", "-", "Folder"), municipio);
        TreeNode delegacion2 = new DefaultTreeNode(new Document("Delegación 2", "-", "Folder"), zona);
        TreeNode empresa = new DefaultTreeNode(new Document("Empresa", "-", "Folder"), delegacion2);
         
        TreeNode departamento = new DefaultTreeNode(new Document("Departamento", "-", "Folder"), empresa);
        TreeNode empresa2 = new DefaultTreeNode(new Document("Empresa 2", "-", "Folder"), departamento);  
        return root;
    }
}