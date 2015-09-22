/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view.fileupload;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.FileUploadEvent;


@ManagedBean
@SessionScoped
public class UploaderBB {

    public void handleFileUpload(FileUploadEvent event) {
        try {
             FacesContext ctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            Map<String, Object> parametros = new HashMap<String, Object>();
            HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
            String aa = request.getRequestURL().toString().replace(request.getRequestURI(), "") + request.getContextPath();
            
            File targetFolder = new File("/images/");
            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}