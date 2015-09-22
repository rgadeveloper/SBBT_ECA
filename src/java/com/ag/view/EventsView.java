/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.service.InterfazManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "treeEventsView")
@ViewScoped
public class EventsView implements Serializable {

    private TreeNode root;
    private List<String> nombresEsta;
    private boolean expandir;
    private boolean bloquearAnterior;
    private boolean deshabilitarPeriodo;
    private boolean deshabilitarRadio;
    private String todoORSecuencial;
    private TreeNode selectedNode;
    private String periodo;
    private SpringContext context;
    private int countView;
    private int c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12;
    private InterfazManager interfazManager;
    @ManagedProperty("#{documentService1}")
    private DocumentService1 service;

    @PostConstruct
    public void init() {
        root = service.createDocuments();
        context = SpringContext.getInstance();
        interfazManager = (InterfazManager) context.getBean("InterfazManager");
        expandir = false;
        context = SpringContext.getInstance();
        nombresEsta = new ArrayList();
        ini();
        initr();
    }

    public void ini() {
        nombresEsta.add("Transformador");
        nombresEsta.add("Circuito");
        nombresEsta.add("Barrio");
        nombresEsta.add("Subestación");
        nombresEsta.add("Población");
        nombresEsta.add("Delegación");
        nombresEsta.add("Municipio");
        nombresEsta.add("Zona");
        nombresEsta.add("Delegación 2");
        nombresEsta.add("Empresa");
        nombresEsta.add("Departamento");
        nombresEsta.add("Empresa 2");
    }
    
    public void initr()
    {
    countView = -1;
    c1=0;
    c2=0;
    c3=0;
    c4=0;
    c5=0;
    c6=0;
    c7=0;
    c8=0;
    c9=0;
    c10=0;
    c11=0;
    c12=0;
    
    }

    public TreeNode getRoot() {
        return root;
    }

    public void asigarPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void setService(DocumentService1 service) {
        this.service = service;
    }
    
    public boolean isExpandir() {
        return expandir;
    }

    public void setExpandir(boolean expandir) {
        this.expandir = expandir;
    }

    public boolean isBloquearAnterior() {
        return bloquearAnterior;
    }

    public void setBloquearAnterior(boolean bloquearAnterior) {
        this.bloquearAnterior = bloquearAnterior;
    }

    public String getTodoORSecuencial() {
        return todoORSecuencial;
    }

    public void setTodoORSecuencial(String todoORSecuencial) {
        this.todoORSecuencial = todoORSecuencial;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public SpringContext getContext() {
        return context;
    }

    public void setContext(SpringContext context) {
        this.context = context;
    }

    public InterfazManager getInterfazManager() {
        return interfazManager;
    }

    public void setInterfazManager(InterfazManager interfazManager) {
        this.interfazManager = interfazManager;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        //Expandimos lo procedimientos para ejecutar el siguiente       
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Abriendo el procedimiento...", event.getTreeNode().toString()));       
        expandir = false;
        bloquearAnterior = true;
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cerrando el procedimiento...", event.getTreeNode().toString()));
    }

    public void onNodeSelect(NodeSelectEvent event) {
        //ejecutamos los eventos
        //Orden de los procedimeintos a ejecutar
       /*
         Transformador,Circuito,Barrio,Subestación,Población,Delegación,Municipio,Zona,Delegación 2,
         Empresa,Departamento,Empresa 2
         */
        if (todoORSecuencial.equals("SECUENCIAL")) {

            if (periodo != null) {
                int parametro = !periodo.equals("") && periodo != null ? Integer.valueOf(periodo) : 0;

                String var = event.getTreeNode().toString();
                bloquearAnterior = true;

                if (countView == -1) {
                    //procedimeinto 1
                    if (c1 == 0) {
                        if (nombresEsta.get(0).equals(var))//si es el primer procedimiento
                        {
                            Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                            if (error instanceof Boolean) {
                                expandir = true;
                                countView = 0;
                                c1++;
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(1)));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                expandir = false;
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(0)));
                        }
                    } else {
                        if (countView == 11) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                        }
                    }
                } else {
                    if (countView == 0) {
                        //procedimeinto 2
                        if (c2 == 0) {
                            if (nombresEsta.get(1).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 1;//se ejecuto el segundo procedimiento
                                    c2++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(2)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(1)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }
                    //procedimeinto 3
                    if (countView == 1) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c3 == 0) {
                            if (nombresEsta.get(2).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 2;//se ejecuto el segundo procedimiento
                                    c3++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(3)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(2)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedimeinto 4
                    if (countView == 2) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c4 == 0) {
                            if (nombresEsta.get(3).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 3;//se ejecuto el segundo procedimiento
                                    c4++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(4)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(3)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedimeinto 5
                    if (countView == 3) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c5 == 0) {
                            if (nombresEsta.get(4).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 4;//se ejecuto el segundo procedimiento
                                    c5++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(5)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(4)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedimeinto 6
                    if (countView == 4) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c6 == 0) {
                            if (nombresEsta.get(5).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 5;//se ejecuto el segundo procedimiento
                                    c6++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(6)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(5)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedimeinto 7
                    if (countView == 5) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c7 == 0) {
                            if (nombresEsta.get(6).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 6;//se ejecuto el segundo procedimiento
                                    c7++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(7)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(6)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }
                    //procedimiento 8
                    if (countView == 6) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c8 == 0) {
                            if (nombresEsta.get(7).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 7;//se ejecuto el segundo procedimiento
                                    c8++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(8)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(7)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }
                    //procedieminto 9
                    if (countView == 7) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c9 == 0) {
                            if (nombresEsta.get(8).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 8;//se ejecuto el segundo procedimiento
                                    c9++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(9)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(8)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedieminto 10
                    if (countView == 8) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c10 == 0) {
                            if (nombresEsta.get(9).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 9;//se ejecuto el segundo procedimiento
                                    c10++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(10)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(9)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }


                    //procedieminto 11
                    if (countView == 9) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c11 == 0) {
                            if (nombresEsta.get(10).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 10;//se ejecuto el segundo procedimiento
                                    c11++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Procedimiento se ejecutó con exito. " + var, "Ahora puede ejecutar el procedimiento: " + nombresEsta.get(11)));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(10)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }

                    //procedieminto 12
                    if (countView == 10) {//se ejecutó el primer procedimiento, ahora vamos a ejecutar el segundo
                        if (c12 == 12) {
                            if (nombresEsta.get(11).equals(var))//si es el sgundo procedimiento
                            {
                                Object error = interfazManager.executeProcedimientoAlmacenado(parametro, var);
                                if (error instanceof Boolean) {
                                    expandir = true;
                                    countView = 11;//se ejecuto el segundo procedimiento
                                    c12++;
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ejecutaron con exito todos los procedimientos. ", "Finalizado!!!!"));
                                } else {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El procedimiento no se pudo ejecutar. Error de base de datos.", var));
                                    expandir = false;
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ejecute el procedimiento con nombre: ", nombresEsta.get(11)));
                            }
                        } else {
                            if (countView == 11) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usted ya ejecutó todos los procedimientos. ", ""));
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ejecute el procedimiento: " + nombresEsta.get(countView + 1), ""));
                            }
                        }
                    }



                }

                if (var.equals(
                        "Empresa 2")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ejecutaron todos los procedimientos.", var));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Seleccione un periodo.", ""));
            }
        }

    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}