<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.component.locker.interfaces.*"%>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%
KmssMessageWriter msgWriter = null;
if(request.getAttribute("KMSS_RETURNPAGE")!=null){
	msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
	boolean isComponentLockerVersionException = false;
	KmssReturnPage returnPage = (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE");
	if(returnPage != null) {
		KmssMessages kmssMessages = returnPage.getMessages();
		if(kmssMessages!=null && kmssMessages.hasError()){
			List<KmssMessage> msgs = kmssMessages.getMessages();
			if(msgs!=null && msgs.size()>0){
				KmssMessage msg = msgs.get(0);
				Throwable t = msg.getThrowable();
				if(t!=null &&  t.toString().indexOf( "ComponentLockerVersionException")>0 ){
	                String modifyPerson = (String)request.getAttribute("modifyPerson");
	                Object modifyTime = request.getAttribute("modifyTime");
					isComponentLockerVersionException = true;
					if(request.getHeader("accept")!=null){
						if(request.getHeader("accept").indexOf("application/json") >=0){
							JSONObject json = msgWriter.DrawJsonMessage(false);
							json.put("exceptionType", "ComponentLockerVersionException");
							json.put("modifyTime", modifyTime);
							json.put("modifyPerson", modifyPerson);
							out.write(json.toString());
							response.setHeader("lui-status","true");
							return;
						}
						}
				}else {
					//msgWriter = new KmssMessageWriter(request, null);
					JSONObject json = msgWriter.DrawJsonMessage(false);
					out.write(json.toString());
					response.setHeader("lui-status","true");
					return;
				}
			}
		}
	}
}
%>