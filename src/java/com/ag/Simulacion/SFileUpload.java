/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.Simulacion;

import com.ag.service.SArchivoManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
//import java.io.*;
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped; 
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author arodriguezr
 */
@ManagedBean(name="sFileUpload")  
@SessionScoped
public class SFileUpload implements Serializable {

    //private String destination="C:\\Windows\\Temp\\";
    private String mensaje,estado;
    private UploadedFile file;    
    private SArchivoManager archivoManager;
   
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }   
        
    public SFileUpload() {
        SpringContext context = SpringContext.getInstance();
        archivoManager =  (SArchivoManager) context.getBean("SArchivoManager");
        mensaje = "";
        estado = "Carga";
        //estadoSuminTrafo = "Carga";
    }
    
    public void upload(String procceso, String usuario, String programa){
        String extValidate;
        if(getFile() != null){
            String ext = getFile().getFileName();
            if(ext != null) extValidate = ext.substring(ext.indexOf(".")+1);
            else extValidate = "null";
            
            if(extValidate.equals("txt")){
                try {
                    String[] respuesta;
                    if (procceso.equals("Amarres")) {
                        estado = "Cargado";
                        respuesta = archivoManager.cargaAmarreMasivo(usuario, programa, file.getInputstream());  
                    }else{//consumos
                        estado= "Cargado";
                        respuesta = archivoManager.cargaConsumoMasivo(usuario, programa, file.getInputstream());
                    }
                    mensaje =  respuesta[0]; 
                    String resultado =  respuesta[1];
                    String procesados = respuesta[2];
                    if (resultado.equals("exitoso")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", procesados));

                    } else if(resultado.equals("warning")){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Operación Con errores", procesados));

                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Errada", procesados));
                    }            
                    
                } catch (Exception e) {                   
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al cargar archivo.", "Error en el Sistema."));
                }
            }else{
               FacesContext context = FacesContext.getCurrentInstance();
               context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"solo archivo con extension .txt","Intente de nuevo.")); 
            }            
        }else{
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"No ha seleccionado el archivo.","Seleccione un archivo con extension .txt")); 
        }
    }
    
    public void volver(){
        setEstado("Carga");
        mensaje = "";
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
        
    /*public void proccesarAmarres(InputStream inputStream, String usuario, String programa){        
        try {          
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
         
         String linea, tipoAmarre, idPadre, idHijo, periodo;
         String[] columna;
         while((linea=br.readLine())!=null){
             columna=linea.split(";");
             tipoAmarre=columna[0];
             idPadre=balanceManager.getIdComponenteByIdCliente(columna[1]);
             idHijo=balanceManager.getIdComponenteByIdCliente(columna[2]);
             periodo=columna[3];
             
             if (tipoAmarre.equals("1") || tipoAmarre.equals("3")){
                 //mover trafo a otro circuito o barrio
                 ZonaGeografica z = tipoAmarre.equals("1")?
                                    balanceManager.getZonaByTrafo(idHijo,"3"):
                                    balanceManager.getZonaByTrafo(idHijo,"7");                 
                 if (z!=null) {
                    String idZonaAnterior = z.getIdZona().toString();
                    if(!idZonaAnterior.equals(idPadre)){                
                        ZonaGeografica zona = tipoAmarre.equals("1")? 
                                                balanceManager.getZonaGeografica(idPadre,"3"):
                                                balanceManager.getZonaGeografica(idPadre,"7");
                        SComponente componente = balanceManager.getComponente(idHijo);
                        if(zona!=null && componente!=null)
                            balanceManager.movTrafo(usuario, programa, componente, zona, idZonaAnterior);                        
                    }
                 }                
             }   
             
             if (tipoAmarre.equals("2") || tipoAmarre.equals("4")){
                 //mover suministro a otro trafo
                 SComponente t=balanceManager.getTrafoBySuministro(idHijo);                 
                 if (t!=null) {
                    String idTrafoAnterior=t.getIdComponente().toString();
                    if(!idTrafoAnterior.equals(idPadre)){
                    SComponente trafo=balanceManager.getComponente(idPadre);
                    SComponente suministro=balanceManager.getComponente(idHijo);
                    if(trafo!=null && suministro!=null)
                       balanceManager.movSuministro(usuario, programa, suministro, trafo, idTrafoAnterior);            
                   }
                 }
                                   
             }
         }         
              br.close();
              inputStream.close();
         
        }catch(Exception e){
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al proccesar amarres.", e.getMessage()));                
        }
    }
    
        public void proccesarConsumos(InputStream inputStream, String usuario, String programa){        
        try {          
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
         
         String linea, tipoComponente, idCliente, consumoFacturado, diasFacturado/*, cargaExtra*/;
         /*String[] columna;
         while((linea=br.readLine())!=null){
             columna=linea.split(";");
             tipoComponente=columna[0];
             idCliente=columna[1];
             consumoFacturado=columna[2];
             diasFacturado=columna[3];
             if (tipoComponente.equalsIgnoreCase("m")) {
                String cargaExtra = columna[4];
                balanceManager.actConsumoTrafo(usuario, programa, idCliente, consumoFacturado, diasFacturado, cargaExtra);
             }
             
             if (tipoComponente.equalsIgnoreCase("c")) {
                 balanceManager.actConsumoSumin(usuario, programa, idCliente, consumoFacturado, diasFacturado);
             }          
         }         
              br.close();
              inputStream.close();
         
        }catch(Exception e){
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al proccesar amarres.", e.getMessage()));                
        }
    }*/

}
