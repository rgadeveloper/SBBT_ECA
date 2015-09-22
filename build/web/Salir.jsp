<%@ page import = "com.ag.view.Login,javax.faces.context.*,javax.servlet.*" %>

<jsp:useBean id="bean" class="com.ag.view.Login" scope="page">
    <jsp:setProperty name="bean" property="*" />
</jsp:useBean>

<% ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();%>
<% String ctxPath =  ((ServletContext) ctx.getContext()).getContextPath(); %>
<%((HttpSession) ctx.getSession(false)).invalidate();%>
<% bean.setValidarSession(true);%>
<%ctx.redirect(ctxPath + "/login.xhtml");%>
