<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.portal.util.SysPortalInfo"%>
<%@ page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@ page import="org.springframework.security.web.savedrequest.DefaultSavedRequest"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%  
    // 1、访问的URL地址
    String urlAdress = null;
    Object redirect = (Object)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
    // 2、从admin.do拿到的服务器DNS(外网)
    String urlPrefix = ResourceUtil.getKmssConfig("kmss.urlPrefix").get("kmss.urlPrefix");
    // 3、从admin.do拿到的服务器DNS(内网)
    String innerUrlPrefix = ResourceUtil.getKmssConfig("kmss.innerUrlPrefix").get("kmss.innerUrlPrefix");
    
    if (redirect != null && redirect instanceof DefaultSavedRequest) {
        DefaultSavedRequest dsr = (DefaultSavedRequest) redirect;
        urlAdress = dsr.getRedirectUrl();
        session.setAttribute(Globals.SPRING_SECURITY_TARGET_URL_KEY,urlAdress);
    }
    
    // 这里admin.do中，URL结尾时多一个/号，少一个/的处理
    if (urlPrefix != null) {
        if (!urlPrefix.matches("^.+/$")) {
            urlPrefix = urlPrefix.concat("/");
        }
    }
    if (innerUrlPrefix != null) {
        if (!innerUrlPrefix.matches("^.+/$")) {
            innerUrlPrefix = innerUrlPrefix.concat("/");
        }
    }
    
    if(urlAdress != null){
        if (!urlAdress.matches("^.+/$")) {
            urlAdress = urlAdress.concat("/");
        }
    }
    
    if (urlAdress == null || urlAdress.equals(urlPrefix) || urlAdress.equals(innerUrlPrefix)) {
        // 跳到匿名门户
        session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
        SysPortalInfo xpage = PortalUtil.viewAnonymousPortalInfo(request);
        if (xpage.getPageType().equals("2")) { // 内部链接页面，直接跳转
            response.sendRedirect(PortalUtil.formatUrl(request, xpage.getPageUrl()));
        } else {
            String path = PortalUtil.getPortalPageJspPathAnonym(xpage);
            String mainPageId = request.getParameter("mainPageId");
            String portalId = request.getParameter("portalId");
            if(StringUtil.isNotNull(mainPageId)){
                SysPortalInfo info = new SysPortalInfo();   
                PortalUtil.getSysPortalPageInfo(info,mainPageId);
                path = PortalUtil.getPortalPageJspPath(info);
            }
            if(xpage.getPortalIsQuick() != null && xpage.getPortalIsQuick()){
                if("true".equals(request.getParameter("j_content"))){
                    // 当j_content=true时返回HTML片段而不进入极速门户
                    path += (path.indexOf("?") > -1 ? "&" : "?") +  "j_content=true";
                }else{
                    //进入极速门户
                    path = "/sys/portal/template/quick/anonym.jsp?j_start=" + URLEncoder.encode(path);
                }
            }
            if(StringUtil.isNotNull(portalId)){
                PortalUtil.setSysPortalPageLang(request,portalId);
            }
            request.setAttribute("headerPortalPageName", xpage.getPortalPageName());
            request.getRequestDispatcher(path).forward(request, response);
        }
    } else if ((null!=urlPrefix&&urlAdress.contains(urlPrefix.concat("/resource"))) || (null!=innerUrlPrefix&&urlAdress.contains(innerUrlPrefix.concat("/resource")))) {
        // 跳到匿名路径
        response.sendRedirect(PortalUtil.formatUrl(request, urlAdress));
    } else {      
        
        // 跳到登录页
        String corpid = request.getParameter("corpid");
        if(StringUtil.isNotNull(corpid)){
        	 //蓝桥和钉钉的单点特殊处理
        	 response.sendRedirect(PortalUtil.formatUrl(request, (request.getAttribute("LUI_ContextPath").toString() + "/login.jsp?corpid="+corpid)));
        }else{
        	  response.sendRedirect(PortalUtil.formatUrl(request, (request.getAttribute("LUI_ContextPath").toString() + "/login.jsp")));
        }
      
    }
%>