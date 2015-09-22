/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Componente;
import com.ag.model.Estado;
import com.ag.model.RelComponente;
import com.ag.model.RelComponentePK;
import com.ag.model.RelComponenteUbicacion;
import com.ag.service.ArchivoManager;
import com.ag.service.BalanceManager;
import com.ag.service.SpringContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ultimate
 */
@ManagedBean
@SessionScoped
public class AmarresView implements Serializable {

    private String mensaje, estado, mensajeEliminar;
    @ManagedProperty("#{login}")
    private Login login;
    private UploadedFile file;
    private String tipoMacroSel;
    private String transformador;
    private String macromedidor;
    private List<RelComponente> relCompoList;
    private List<RelComponenteUbicacion> relCompoListUbi;
    private List<RelComponente> relCompoListEliminar;
    private List<RelComponenteUbicacion> relCompoListUbiEli;
    private boolean recargar;
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public AmarresView() {
        mensaje = "";
        mensajeEliminar = "";
        estado = "Carga";
        relCompoList = new ArrayList();
        relCompoListUbi = new ArrayList();
        relCompoListEliminar = new ArrayList();
        relCompoListUbiEli = new ArrayList();
        recargar=false;
    }

    public void upload() throws IOException {
        boolean ban = true;
        FacesMessage msg;
        if (file != null) {
            estado = "Cargado";
            mensaje="";
            mensajeEliminar="";
            //SpringContext context = SpringContext.getInstance();
            //ArchivoManager archivoManager = (ArchivoManager) context.getBean("ArchivoManager");

            try {
                ban = copyFile(file.getInputstream());//eviamos al copyfile            
            } catch (IOException ex) {
                Logger.getLogger(AmarresView.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se pudo cargar el archivo"));
            }
        }
        if (ban) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje!", "Archivo cargado con éxito");
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Corrija el archivo e intente nuevamente");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    /* 
     * amarreSuministro
     * Relcomponente
     * Id_padre
     Ammratrafo
     * relcomponente ubicacion
     * Id_zona
     */

    public void handleFileUpload(FileUploadEvent event) {
        boolean ban = true;
        FacesMessage msg;
        estado = "Cargado";
        mensaje="";
        mensajeEliminar="";
        try {
            ban = copyFile(event.getFile().getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(AmarresView.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "No se pudo cargar el archivo"));
        }

        if (ban) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje!", "Archivo cargado con éxito");
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Corrija el archivo e intente nuevamente");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private boolean copyFile(InputStream in) {
        HashMap<String, String> map = new HashMap<String,String>();
        BufferedReader br = null;
        String linea = "";
        String columnas[] = null;
        List<String[]> registros = new ArrayList<String[]>();
        int i = 0;
        boolean ban = true;

        try {
            br = new BufferedReader(new InputStreamReader(in));
            columnas = br.readLine().split(";");
            for (int j = 0; j < columnas.length; j++) {
                map.put("" + j, columnas[j]);
            }

            while ((linea = br.readLine()) != null) {
                registros.add(linea.split(";"));
                for (int j = 0; j < 4; j++) {
                    if (registros.get(i)[j].equals("")) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Por favor verifique el registro " + (i + 1) + " en la columna " + map.get("" + j));
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        ban = false;
                        break;
                    }
                }
                if (!ban) {
                    break;
                }
                i++;
            }

        } catch (Exception ex) {
            Logger.getLogger(AmarresView.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Tipo_Carga-id_Componente-id_hijo-Periodo
        if (ban) {
            int contRegist = 0;
            SpringContext context = SpringContext.getInstance();
            ArchivoManager archManager = (ArchivoManager) context.getBean("ArchivoManager");
            BalanceManager balance = (BalanceManager) context.getBean("BalanceManager");
            Componente componente;
            Componente suministro;
            relCompoList = new ArrayList();
            relCompoListUbi = new ArrayList();
            relCompoListEliminar = new ArrayList();
            relCompoListUbiEli = new ArrayList();
            RelComponente relComObj;
            for (Iterator<String[]> it = registros.iterator(); it.hasNext();) {
                try {
                    String[] regist = it.next();
                    contRegist++;
                    relComObj = new RelComponente();
                    if (regist[0].equals("1")) {
                        //Transformaddor
                        if (!regist[1].equals("")) {
                            //Verificamos que el componente exista y este activo
                            componente = new Componente();
                            componente = balance.getComponente(regist[1]);
                            //Verificamos que el compoente existe
                            if (componente instanceof Componente && componente != null) {
                                //Verifica si el componete esta activo
                                if (componente.getEstado().getIdEstado().equals("AC001")) {
                                    //El componente paso todas las validaciones
                                    //Ahora pasamo al suministri
                                    relComObj.setComponente(componente);

                                    if (!regist[2].equals("")) {
                                        suministro = new Componente();
                                        suministro = balance.getComponente(regist[2]);
                                        if (suministro instanceof Componente && suministro != null) {
                                            //Verificamos que el suministro este activo
                                            if (suministro.getEstado().getIdEstado().equals("AC001")) {
                                                //Verificamos que el suministro no este asiciado a ningun tranfo
                                                List<RelComponente> rel = new ArrayList();
                                                rel = balance.RelComponente(regist[2]);
                                                if (rel instanceof List && rel.size() > 0) {
                                                    //Me trae la relacion desactiva--Podemos hacer la asociacion
                                                    //Verificamos que no tenga ualguna relacion activa
                                                    int estadocont = 0;
                                                    for (int j = 0; j < rel.size(); j++) {
                                                        if (rel.get(j).getEstado().getIdEstado().equals("AC001")) {//estado activo
                                                            estadocont = j + 1;
                                                            break;
                                                        }
                                                    }
                                                    if (estadocont == 0) {
                                                        for (int j = 0; j < rel.size(); j++) {
                                                            if (rel.get(j).getPeriodoFin() == 999912) {//RElacion activa
                                                                estadocont = j + 1;
                                                                break;
                                                            }
                                                        }

                                                        if (estadocont == 0) {
                                                            //Ahora por ultimo validamos el periodo

                                                            if (!regist[3].equals("")) {
                                                                try {
                                                                    int periodo = Integer.parseInt(regist[3]);
                                                                    RelComponentePK rpk = new RelComponentePK();
                                                                    rpk.setPeriodoIni(periodo);
                                                                    rpk.setIdComponente(componente.getIdComponente().toBigInteger());
                                                                    rpk.setIdComponenteHijo(suministro.getIdComponente().toBigInteger());
                                                                    relComObj.setRelComponentePK(rpk);
                                                                    relComObj.setComponente1(suministro);
                                                                    Estado e = balance.getEstado("AC001");
                                                                    relComObj.setEstado(e);
                                                                    relComObj.setEstado1(e);
                                                                    relComObj.setEstado2(e);
                                                                    relComObj.setFechaModif(new Date());
                                                                    relComObj.setPeriodoFin(999912);
                                                                    relComObj.setPrograma("Amarres.xhtml");
                                                                    relComObj.setUsuario(login.getUsuario());
                                                                    relCompoList.add(relComObj);//Agregaamos el nuevo objeto                                                            
                                                                } catch (NumberFormatException e) {
                                                                    mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                                } catch (Exception e) {
                                                                    mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                                }
                                                            } else {
                                                                mensaje += "El periodo del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " tiene que ser distinto de null <br>";
                                                            }
                                                        } else {
                                                            //Tiene una validacion activa
                                                            mensaje += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponentePK().getPeriodoIni() + "<br>";
                                                            mensajeEliminar += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponentePK().getPeriodoIni() + "<br>";
                                                            //  relCompoListEliminar - Buscamos el relcomponente que tenga los datos del registro a eliminar
                                                            relCompoListEliminar.add(rel.get(estadocont - 1));
                                                        }

                                                    } else {
                                                        //Tiene una validacion activa
                                                        mensaje += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponentePK().getPeriodoIni() + "<br>";
                                                        mensajeEliminar += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponentePK().getPeriodoIni() + "<br>";
                                                        //  relCompoListEliminar - Buscamos el relcomponente que tenga los datos del registro a eliminar
                                                        relCompoListEliminar.add(rel.get(estadocont - 1));
                                                    }

                                                } else {
                                                    // sino existe podemos crear una relacion
                                                    if (!regist[3].equals("")) {
                                                        try {
                                                            int periodo = Integer.parseInt(regist[3]);
                                                            RelComponentePK rpk = new RelComponentePK();
                                                            rpk.setPeriodoIni(periodo);
                                                            rpk.setIdComponente(componente.getIdComponente().toBigInteger());
                                                            rpk.setIdComponenteHijo(suministro.getIdComponente().toBigInteger());
                                                            relComObj.setRelComponentePK(rpk);
                                                            relComObj.setComponente1(suministro);
                                                            Estado e = balance.getEstado("AC001");
                                                            relComObj.setEstado(e);
                                                            relComObj.setEstado1(e);
                                                            relComObj.setEstado2(e);
                                                            relComObj.setFechaModif(new Date());
                                                            relComObj.setPeriodoFin(999912);
                                                            relComObj.setPrograma("Amarres.xhtml");
                                                            relComObj.setUsuario(login.getUsuario());
                                                            relCompoList.add(relComObj);//Agregaamos el nuevo objeto                                                            
                                                        } catch (NumberFormatException e) {
                                                            mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico\n";
                                                        } catch (Exception e) {
                                                            mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico\n";
                                                        }
                                                    } else {
                                                        mensaje += "El periodo del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " tiene que ser distinto de null \n";
                                                    }
                                                }
                                            } else {
                                                mensaje += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  esta inactivo\n";
                                            }
                                        } else {
                                            mensaje += "El suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  no existe\n";
                                        }
                                    } else {
                                        mensaje += "El suministro para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  no existe\n";
                                    }
                                } else {
                                    mensaje += "El estado del componente " + regist[1] + " para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  esta desactiva\n";
                                }
                            } else {
                                mensaje += "El componente " + regist[1] + " para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  no existe\n";
                            }

                        } else {
                            mensaje += "El componente para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  no existe\n";
                        }
                    } else if (regist[0].equals("2")) {
                        //Suministro
                        //Es Un 
                        //Es suministro - Asosciar validar
                        if (!regist[1].equals("")) {
                            //Verificamos que el componente exista y este activo
                            componente = new Componente();
                            componente = balance.getComponente(regist[1]);
                            //Verificamos que el compoente existe
                            if (componente instanceof Componente && componente != null) {
                                //Verifica si el componete esta activo
                                if (componente.getEstado().getIdEstado().equals("AC001")) {
                                    //El componente paso todas las validaciones
                                    //Ahora pasamo al suministri
                                    relComObj.setComponente(componente);

                                    if (!regist[2].equals("")) {
                                        suministro = new Componente();
                                        suministro = balance.getComponente(regist[2]);
                                        if (suministro instanceof Componente && suministro != null) {
                                            //Verificamos que el suministro este activo
                                            if (suministro.getEstado().getIdEstado().equals("AC001")) {
                                                //Verificamos que el suministro no este asiciado a ningun tranfo
                                                List<RelComponenteUbicacion> rel = new ArrayList();
                                                rel = balance.RelComponenteU(regist[2]);
                                                if (rel instanceof List && rel.size() > 0) {
                                                    //Me trae la relacion desactiva--Podemos hacer la asociacion
                                                    //Verificamos que no tenga ualguna relacion activa
                                                    int estadocont = 0;
                                                    for (int j = 0; j < rel.size(); j++) {
                                                        if (rel.get(j).getPeriodoFin() == 999912) {//RElacion activa
                                                            estadocont = j + 1;
                                                            break;
                                                        }
                                                    }
                                                    if (estadocont == 0) {
                                                        //Ahora por ultimo validamos el periodo
                                                        if (!regist[3].equals("")) {
                                                            try {
                                                                int periodo = Integer.parseInt(regist[3]);
                                                                RelComponentePK rpk = new RelComponentePK();
                                                                rpk.setPeriodoIni(periodo);
                                                                rpk.setIdComponente(componente.getIdComponente().toBigInteger());
                                                                rpk.setIdComponenteHijo(suministro.getIdComponente().toBigInteger());
                                                                relComObj.setRelComponentePK(rpk);
                                                                relComObj.setComponente1(suministro);
                                                                Estado e = balance.getEstado("AC001");
                                                                relComObj.setEstado(e);
                                                                relComObj.setEstado1(e);
                                                                relComObj.setEstado2(e);
                                                                relComObj.setFechaModif(new Date());
                                                                relComObj.setPeriodoFin(999912);
                                                                relComObj.setPrograma("Amarres.xhtml");
                                                                relComObj.setUsuario(login.getUsuario());
                                                                relCompoList.add(relComObj);//Agregaamos el nuevo objeto                                                            
                                                            } catch (NumberFormatException e) {
                                                                mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                            } catch (Exception e) {
                                                                mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                            }
                                                        } else {
                                                            mensaje += "El periodo del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " tiene que ser distinto de null<br>";
                                                        }
                                                    } else {
                                                        //Tiene una validacion activa
                                                        mensaje += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponenteUbicacionPK().getPeriodoIni() + "<br>";
                                                        mensajeEliminar += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  tiene una relacion activa con el componente: " + rel.get(estadocont - 1).getComponente().getIdComponente() + " en el periodo " + rel.get(estadocont - 1).getRelComponenteUbicacionPK().getPeriodoIni() + "<br>";
                                                        //  relCompoListEliminar - Buscamos el relcomponente que tenga los datos del registro a eliminar
                                                        relCompoListUbiEli.add(rel.get(estadocont - 1));
                                                    }
                                                } else {
                                                    // sino existe podemos crear una relacion
                                                    if (!regist[3].equals("")) {
                                                        try {
                                                            int periodo = Integer.parseInt(regist[3]);
                                                            RelComponentePK rpk = new RelComponentePK();
                                                            rpk.setPeriodoIni(periodo);
                                                            rpk.setIdComponente(componente.getIdComponente().toBigInteger());
                                                            rpk.setIdComponenteHijo(suministro.getIdComponente().toBigInteger());
                                                            relComObj.setRelComponentePK(rpk);
                                                            relComObj.setComponente1(suministro);
                                                            Estado e = balance.getEstado("AC001");
                                                            relComObj.setEstado(e);
                                                            relComObj.setEstado1(e);
                                                            relComObj.setEstado2(e);
                                                            relComObj.setFechaModif(new Date());
                                                            relComObj.setPeriodoFin(999912);
                                                            relComObj.setPrograma("Amarres.xhtml");
                                                            relComObj.setUsuario(login.getUsuario());
                                                            relCompoList.add(relComObj);//Agregaamos el nuevo objeto                                                            
                                                        } catch (NumberFormatException e) {
                                                            mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                        } catch (Exception e) {
                                                            mensaje += "El periodo para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y suministro es: " + regist[2] + "  debe ser numerico<br>";
                                                        }
                                                    } else {
                                                        mensaje += "El periodo del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " tiene que ser distinto de null<br>";
                                                    }
                                                }
                                            } else {
                                                mensaje += "El estado del suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  esta inactivo<br>";
                                            }
                                        } else {
                                            mensaje += "El suministro " + regist[2] + " para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  no existe<br>";
                                        }
                                    } else {
                                        mensaje += "El suministro para el registro: " + contRegist + " cuyo componente es:" + regist[1] + " y periodo es: " + regist[3] + "  no existe<br>";
                                    }
                                } else {
                                    mensaje += "El estado del componente " + regist[1] + " para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  esta desactiva<br>";
                                }
                            } else {
                                mensaje += "El componente " + regist[1] + " para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  no existe<br>";
                            }

                        } else {
                            mensaje += "El componente para el registro: " + contRegist + " cuyo suministro es:" + regist[2] + " y periodo es: " + regist[3] + "  no existe<br>";
                        }
                    }else
                    {
                    mensaje += "El registro: " + contRegist + " no  tipo:" + regist[0] + " no existe. Recuerde que los tipos son 1= suministro y 2=zonas<br>";
                    }


                } catch (NullPointerException e) {
                    mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
                    Logger.getLogger(AmarresView.class.getName()).log(Level.SEVERE, null, e);
                } catch (Exception e) {
                    mensaje += "Se ha producido un error desconocido en el registro: " + contRegist + "<br>";
                    Logger.getLogger(AmarresView.class.getName()).log(Level.SEVERE, null, e);
                }
            }

            if (relCompoList instanceof List && relCompoList.size() > 0) {
                //suministro 
                RelComponente[] rc = new RelComponente[relCompoList.size()];
                relCompoList.toArray(rc);
                boolean guardado = balance.saveRelCompoente(rc);
                if (guardado) {
                    ban = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", relCompoList.size() + " archivos guardados correctamente."));
                } else {
                    ban = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", relCompoList.size() + " archivos no se pudieron guardar."));
                }
            }

            if(relCompoListUbi instanceof List && relCompoListUbi.size()>0)
            {
             RelComponente[] rc = new RelComponente[relCompoListUbi.size()];
             relCompoListUbi.toArray(rc);
                boolean guardado = balance.saveRelCompoente(rc);
                if (guardado) {
                    ban = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", relCompoList.size() + " archivos guardados correctamente."));
                } else {
                    ban = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", relCompoList.size() + " archivos no se pudieron guardar."));
                }
            }

        }
        

        return ban;
    }
    
    public void eliminarAmarres() {
        SpringContext context = SpringContext.getInstance();
        BalanceManager balance = (BalanceManager) context.getBean("BalanceManager");


        if (relCompoListEliminar instanceof List && relCompoListEliminar.size() > 0) {
            String fechaMostrar = new SimpleDateFormat("yyyyMM").format(new Date());
            Integer date = Integer.parseInt(fechaMostrar);
            Estado e = balance.getEstado("AC002");
            for (int i = 0; i < relCompoListEliminar.size(); i++) {
                relCompoListEliminar.get(i).setPeriodoFin(date);
                relCompoListEliminar.get(i).setEstado1(e);
                relCompoListEliminar.get(i).setEstado2(e);
                relCompoListEliminar.get(i).setEstado(e);
            }
            RelComponente[] rc = new RelComponente[relCompoListEliminar.size()];
            relCompoListEliminar.toArray(rc);
            boolean object = balance.saveRelCompoente(rc);
            if (object) {
                recargar = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", relCompoListEliminar.size() + " Amarres de suministros realizados correctamente."));
            } else {
                recargar = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", relCompoListEliminar.size() + " Amarres de suministros no fueron realizados correctamente."));
            }
        }

        if (relCompoListUbiEli instanceof List && relCompoListUbiEli.size() > 0) {
            String fechaMostrar = new SimpleDateFormat("yyyyMM").format(new Date());
            Integer date = Integer.parseInt(fechaMostrar);
            Estado e = balance.getEstado("AC002");
            for (int i = 0; i < relCompoListUbiEli.size(); i++) {
                relCompoListUbiEli.get(i).setPeriodoFin(date);
            }
            RelComponenteUbicacion[] rc = new RelComponenteUbicacion[relCompoListUbiEli.size()];
            relCompoListUbiEli.toArray(rc);
            boolean object = balance.saveRelCompoenteUbi(rc);
            if (object) {
                recargar = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Fallida", relCompoListUbiEli.size() + " Amarres de trafos realizados correctamente."));
            } else {
                recargar = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operación Fallida", relCompoListUbiEli.size() + " Amarres de trafos no fueron realizados correctamente."));
            }
        }

        if (relCompoListEliminar.size() == 0 && relCompoListUbiEli.size() == 0) {
            recargar = true;
        }

    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoMacroSel
     */
    public String getTipoMacroSel() {
        return tipoMacroSel;
    }

    /**
     * @param tipoMacroSel the tipoMacroSel to set
     */
    public void setTipoMacroSel(String tipoMacroSel) {
        this.tipoMacroSel = tipoMacroSel;
    }

    /**
     * @return the transformador
     */
    public String getTransformador() {
        return transformador;
    }

    /**
     * @param transformador the transformador to set
     */
    public void setTransformador(String transformador) {
        this.transformador = transformador;
    }

    /**
     * @return the macromedidor
     */
    public String getMacromedidor() {
        return macromedidor;
    }

    /**
     * @param macromedidor the macromedidor to set
     */
    public void setMacromedidor(String macromedidor) {
        this.macromedidor = macromedidor;
    }

    public class csv {

        private String idComponente;
        private String idSuministro;
        private String periodo;

        public csv() {
        }

        public csv(String idComponente, String idSuministro, String periodo) {
            this.idComponente = idComponente;
            this.idSuministro = idSuministro;
            this.periodo = periodo;
        }

        public String getIdComponente() {
            return idComponente;
        }

        public void setIdComponente(String idComponente) {
            this.idComponente = idComponente;
        }

        public String getIdSuministro() {
            return idSuministro;
        }

        public void setIdSuministro(String idSuministro) {
            this.idSuministro = idSuministro;
        }

        public String getPeriodo() {
            return periodo;
        }

        public void setPeriodo(String periodo) {
            this.periodo = periodo;
        }
    }

    public String getMensajeEliminar() {
        return mensajeEliminar;
    }

    public void setMensajeEliminar(String mensajeEliminar) {
        this.mensajeEliminar = mensajeEliminar;
    }

    public List<RelComponente> getRelCompoList() {
        return relCompoList;
    }

    public void setRelCompoList(List<RelComponente> relCompoList) {
        this.relCompoList = relCompoList;
    }

    public List<RelComponenteUbicacion> getRelCompoListUbi() {
        return relCompoListUbi;
    }

    public void setRelCompoListUbi(List<RelComponenteUbicacion> relCompoListUbi) {
        this.relCompoListUbi = relCompoListUbi;
    }

    public List<RelComponente> getRelCompoListEliminar() {
        return relCompoListEliminar;
    }

    public void setRelCompoListEliminar(List<RelComponente> relCompoListEliminar) {
        this.relCompoListEliminar = relCompoListEliminar;
    }

    public List<RelComponenteUbicacion> getRelCompoListUbiEli() {
        return relCompoListUbiEli;
    }

    public void setRelCompoListUbiEli(List<RelComponenteUbicacion> relCompoListUbiEli) {
        this.relCompoListUbiEli = relCompoListUbiEli;
    }
    
    
}
