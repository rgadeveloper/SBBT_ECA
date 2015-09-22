/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.view;

import com.ag.model.Funcion;
import com.ag.model.Menu;
import com.ag.model.Perfil;
import com.ag.service.FuncionManager;
import com.ag.service.MenuManager;
import com.ag.service.PerfilManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;   
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.DefaultTreeNode;  
import org.primefaces.model.TreeNode; 

/**
 *
 * @author 85154220
 */

@ManagedBean
@SessionScoped
public class PerfilView implements Serializable{
    private Menu converter;
    private SpringContext context ;
    private PerfilManager perfilManager;
    private FuncionManager funcionManager;
    private MenuManager menuManager;
    private List<Perfil> perfiles, perfilesActivos;
    private List<Funcion> funciones;
    private List<Menu> menus;
    private Funcion funSelected;
    private List<Funcion> funcionesAsig;
    private Funcion funAsigSelected;
    private Perfil perSelected, perSelectedUser; 
    private Funcion funcion;
    private Funcion funcionAsig;
    private String newperfil, newcodperfil, newestado;
    
    
    private TreeNode root;    
    private TreeNode[] selectedNodes;    
    
    public PerfilView(){        
        context = SpringContext.getInstance();
        perfilManager = (PerfilManager) context.getBean("PerfilManager");
        funcionManager = (FuncionManager) context.getBean("FuncionManager");
        menuManager =  (MenuManager) context.getBean("MenuManager");
        setPerfiles(perfilManager.getPerfilesAll());
        setPerfilesActivos(perfilManager.getPerfilesActivos());
        
        this.perSelectedUser=this.perSelected; //copy of perSelected      
        
        
        
    }  
    
    public void llenarTreeMenus(){
        root = new DefaultTreeNode("Root", null); 
        Map<String,TreeNode> nodoPadre = new HashMap<String, TreeNode>();
        
        List<Menu> listPadres;
        listPadres = menuManager.getMenusSoloPadres();
         
        Iterator iter = listPadres.iterator();
        while (iter.hasNext()){
             Menu m = (Menu) iter.next();
             TreeNode nodo;             
             String codPadre=m.getCodPadre();
             if (codPadre==null){                 
                nodo=new DefaultTreeNode(m.getCodigo() + " - " + m.getNombre() , root); 
                nodo.setSelected(menuManager.menuAsociado(m.getCodigo(), perSelected.getCodigo()));
                nodoPadre.put(m.getCodigo(), nodo);
             } 
        }
        listPadres.clear();
        
        List<Menu> listhijos;
        listhijos = menuManager.getMenusHijos();
        Iterator iter2 = listhijos.iterator();
        while (iter2.hasNext()){
             Menu m = (Menu) iter2.next();
             TreeNode nodo;             
             String codPadre=m.getCodPadre();
             if (codPadre!=null){                 
                TreeNode padre= nodoPadre.get(m.getCodPadre());
                nodo=new DefaultTreeNode(m.getCodigo() + " - " + m.getNombre() , padre);  
                nodo.setSelected(menuManager.menuAsociado(m.getCodigo(), perSelected.getCodigo()));
                nodoPadre.put(m.getCodigo(), nodo);                
             }     
        }       
        
        listhijos.clear();
       
    }
    
    public String getNombreMenu(String id){
        return menuManager.getMenu(id).getNombre();
    }
        
    public void editPerfil(String usuario, String programa){
        try {
            newcodperfil = perSelected.getCodigo();
            newperfil = perSelected.getNombre();
            newestado = perSelected.getEstado().getIdEstado();
            perfilManager.save(usuario, programa, newperfil, newcodperfil, newestado); 
            setPerfiles(perfilManager.getPerfilesAll());
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        } finally {
            ClearCampos();
        }
    }
    
    public void ClearCampos(){
        newcodperfil="";
        newperfil="";
        newestado="";
    }
    
    public void savePerfil(String usuario, String programa){
       try {
          if (!perfilManager.existePerfil(newcodperfil)) {             
            perfilManager.save(usuario, programa, newperfil, newcodperfil, newestado);       
            setPerfiles(perfilManager.getPerfilesAll());
            setPerfilesActivos(perfilManager.getPerfilesActivos());
            FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Perfil creado exitosamente.", ""));
          }else
              FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "El perfil "+newcodperfil+" ya existe.", ""));
       } catch (Exception e) {
           FacesContext.getCurrentInstance().addMessage
                    (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
       }finally{
          ClearCampos();  
       }   
        
    }

    public String getNewcodperfil() {
        return newcodperfil;
    }

    public void setNewcodperfil(String newcodperfil) {
        this.newcodperfil = newcodperfil;
    }

    public String getNewperfil() {
        return newperfil;
    }

    public void setNewperfil(String newperfil) {
        this.newperfil = newperfil;
    }

    public String getNewestado() {
        return newestado;
    }

    public void setNewestado(String newestado) {
        this.newestado = newestado;
    }

    public List<Perfil> getPerfilesActivos() {
        return perfilesActivos;
    }

    public void setPerfilesActivos(List<Perfil> perfilesActivos) {
        this.perfilesActivos = perfilesActivos;
    }
    
        
    
    
   
    
    /**
     * @return the menus
     */    
    public List<Menu> getMenus() {
        return menus;
    }
    
     /**
     * @param menus the menus to set
     */
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
        
    /**
     * @return the perfiles
     */    
    public List<Perfil> getPerfiles() {
        return perfiles;
    }
    
    /**
     * @return the perfiles in order by nombre
     */    
    public List<Perfil> getPerfilesOrderByNombre() {
        //Collections.sort(perfilesActivos); 
        return perfilesActivos;
    } 

    /**
     * @param perfiles the perfiles to set
     */
    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    /**
     * @return the perSelected
     */
    public Perfil getPerSelected() {
        return perSelected;
    }
    
    /**
     * @return the copy of perSelected
     */
    public Perfil getPerSelectedUser() {
        return perSelectedUser;
    }
    /**
     * @param perSelected the perSelected to set
     */
    public void setPerSelected(Perfil perSelected) {
        this.perSelected = perSelected;
    }
   
    public void msgEditado(){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Perfil Editado exitosamente", ""));   
    }
    
   public void msgGuardado(){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Perfil Guardado exitosamente", ""));   
   }
   
   public void nuevo(){
       perfiles.add(new Perfil());
       
   }
   
   public void eliminar(){
       if(perSelected!=null)
            perfiles.remove(perSelected);
   }

    /**
     * @return the funciones
     */
    public List<Funcion> getFunciones() {
        return funciones;
    }

    /**
     * @param funciones the funciones to set
     */
    public void setFunciones(List<Funcion> funciones) {
        this.funciones = funciones;
    }

    /**
     * @return the funcionesAsig
     */
    public List<Funcion> getFuncionesAsig() {
        return funcionesAsig;
    }

    /**
     * @param funcionesAsig the funcionesAsig to set
     */
    public void setFuncionesAsig(List<Funcion> funcionesAsig) {
        this.funcionesAsig = funcionesAsig;
    }

    

    /**
     * @return the funcion
     */
    public Funcion getFuncion() {
        return funcion;
    }

    /**
     * @param funcion the funcion to set
     */
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    /**
     * @return the funcionAsig
     */
    public Funcion getFuncionAsig() {
        return funcionAsig;
    }

    /**
     * @param funcionAsig the funcionAsig to set
     */
    public void setFuncionAsig(Funcion funcionAsig) {
        this.funcionAsig = funcionAsig;
    }

    /**
     * @return the funSelected
     */
    public Funcion getFunSelected() {
        return funSelected;
    }

    /**
     * @param funSelected the funSelected to set
     */
    public void setFunSelected(Funcion funSelected) {
        this.funSelected = funSelected;
    }

    /**
     * @return the funAsigSelected
     */
    public Funcion getFunAsigSelected() {
        return funAsigSelected;
    }

    /**
     * @param funAsigSelected the funAsigSelected to set
     */
    public void setFunAsigSelected(Funcion funAsigSelected) {
        this.funAsigSelected = funAsigSelected;
    }
    
    public void asignarFuncion(DragDropEvent ddEvent) {  
        System.out.println("Inicio DradDROp asignarFuncion");
        Funcion fun = ((Funcion) ddEvent.getData());
        funcionesAsig.add(fun);  
        funciones.remove(fun);  System.out.println(funcionesAsig.toString()+funciones.toString());
    } 
    
    public void desAsignarFuncion(DragDropEvent ddEvent) {  
        System.out.println("Inicio DradDROp asignarFuncion");
        Funcion fun = ((Funcion) ddEvent.getData());
        funciones.add(fun);  
        funcionesAsig.remove(fun);  System.out.println(funcionesAsig.toString()+funciones.toString());
    }

    public TreeNode getRoot() {  
        return root;  
    }  
  
    public TreeNode[] getSelectedNodes() {  
        return selectedNodes;  
    }  
  
    public void setSelectedNodes(TreeNode[] selectedNodes) {  
        this.selectedNodes = selectedNodes;  
    }
    
    public void asociarMenus(String usuario, String programa){
      try{           
       if(selectedNodes != null && selectedNodes.length > 0) {  
             
            List<Menu> listMenus = new ArrayList<Menu>();
  
            for(TreeNode node : selectedNodes) {  
                String [] datoNodo=node.getData().toString().split("-");
                String id=datoNodo[0].trim(); 
                Menu m;
                do {                    
                  m=menuManager.getMenu(id);                  
                  if (!listMenus.contains(m)) listMenus.add(m);
                  id=m.getCodPadre();                  
                } while (id!=null);
            } 
            
            if (!listMenus.isEmpty() && perSelected!=null) {
                
                menuManager.desactivarMenuPerfil(perSelected.getCodigo());
                
                Iterator iter = listMenus.iterator();
                while (iter.hasNext()){
                    Menu m = (Menu) iter.next();
                    menuManager.saveMenuPerfil(usuario, programa, m, perSelected);
                }  
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proceso exitoso. Menus asociados", "");    
                FacesContext.getCurrentInstance().addMessage(null, message); 
            }  
        } 
      }catch(Exception e){
          FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Procceso interrumpido. Error en el sistema","");    
          FacesContext.getCurrentInstance().addMessage(null, message);
      }finally{
          selectedNodes=null;
      }  
    }
    
    public void verMenusAsociados(){
        if(selectedNodes != null && selectedNodes.length > 0) {
            for(TreeNode node : selectedNodes) { 
                
            }
        }            
    }
  
    public void displaySelectedMultiple() {  
        if(selectedNodes != null && selectedNodes.length > 0) {  
            StringBuilder builder = new StringBuilder();  
  
            for(TreeNode node : selectedNodes) {                 
                builder.append(node.getData().toString());  
                builder.append("<br />");  
            }  
  
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());  
  
            FacesContext.getCurrentInstance().addMessage(null, message);  
        }  
    }  
   
}

