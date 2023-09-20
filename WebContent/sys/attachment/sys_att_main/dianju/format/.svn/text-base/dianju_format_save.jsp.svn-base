<%@ page contentType="text/html;charset=GB2312"%><jsp:useBean
	id="dianjuSmartUpload" scope="page" class="com.landray.kmss.sys.attachment.integrate.dianju.DianjuSmartUpload" />
<%@ page import="com.landray.kmss.sys.attachment.integrate.dianju.DianjuFile" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.dianju.DianjuSaveRequestVO" %>
<%
	try {
		// 初始化上传组件
		dianjuSmartUpload.initialize(pageContext);
		dianjuSmartUpload.upload();
		String attMainId = request.getParameter("attMainId");
		String type = request.getParameter("type");
		String fileName = request.getParameter("fileName");
		String modelId = request.getParameter("modelId");
		String modelName = request.getParameter("modelName");
		DianjuSaveRequestVO dianjuSaveRequestVO = new DianjuSaveRequestVO(type,
				attMainId, fileName, modelId, modelName);
		
		DianjuFile dianjuFile = dianjuSmartUpload.getFiles().getFile(0);

		if (dianjuFile != null && !dianjuFile.isMissing()) {
			try {
				dianjuFile.saveToAttmain(dianjuSaveRequestVO);
			} catch (Exception dsue) {
				out.print("failed");
				dsue.getStackTrace();
			}
			out.print("success");
		} else {
			out.print("failed");
		}
	} catch (Exception e) {
		out.clear();
		out.print("failed");//返回控件HttpPost()方法值。
		out.flush();
	}
%>