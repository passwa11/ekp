<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="
java.util.Map,java.util.Set,
java.util.ArrayList,
java.util.List,
java.util.Map,
javax.servlet.http.HttpServletRequest,
javax.servlet.http.HttpServletResponse,
org.apache.commons.lang.math.NumberUtils,
com.landray.kmss.web.action.ActionForm,
com.landray.kmss.web.action.ActionForward,
com.landray.kmss.web.action.ActionMapping,
com.landray.kmss.common.actions.BaseAction,
com.landray.kmss.sys.ftsearch.interfaces.ISimDoc,
com.landray.kmss.util.SpringBeanUtil,
com.landray.kmss.util.StringUtil
" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
try {
	if(request.getAttribute("simDoc") == null)
	{
		ISimDoc simDocService = (ISimDoc) SpringBeanUtil.getBean("simDoc");
		String id = request.getParameter("fdId");
		String modelName = request.getParameter("modelName");
		String attachmentId = request.getParameter("attachmentId");
		String topSizeStr = request.getParameter("size");
		String needAttachment = request.getParameter("needAttachment");
		if(StringUtil.isNull(needAttachment)){
		    needAttachment = "false";
		}
		int topSize = NumberUtils.toInt(topSizeStr, 10);// 默认值：10
		List<Map<String, Object>> result;
		if (StringUtil.isNull(id) || StringUtil.isNull(modelName)) {
			result = new ArrayList<Map<String, Object>>();
		} else {
			result = simDocService.getSimilarDocuments(id, modelName, attachmentId, needAttachment, topSize);
		}
		request.setAttribute("simDoc", result);
	}
} catch (Throwable e) {

}
%>

<!-- 文档智能推荐 -->
<div class="simdoc_div">
	<ul class="simdoc_ul">
<%
try {
	List<Map<String, Object>> simDocList = (List<Map<String, Object>>) request.getAttribute("simDoc");
	int count = 0;
	for (Map<String, Object> simDoc : simDocList) {
		count++;
		request.setAttribute("simDocCount", count);
		// "subject","fileName","createTime","author","linkStr","creator","docKey"
		String subject = (String) simDoc.get("subject");
		String fileName = (String) simDoc.get("fileName");
		String createTime = (String) simDoc.get("createTime");
		String author = (String) simDoc.get("creator");
		String docKey = (String) simDoc.get("docKey");
		if (author == null) {
			author = (String) simDoc.get("author");
		}
		String linkStr = (String) simDoc.get("linkStr");
		if (fileName != null) {
			// 是附件
			if (docKey != null && docKey.indexOf("_") != -1) {
				String linkArgu = docKey.substring(docKey.lastIndexOf("_") + 1);
				linkStr = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=" + linkArgu;
			} else {
				break;
			}
		}
		String title = (fileName == null ? subject : fileName);
		if (title == null || "".equals(title.trim())) {
			break;
		}
%>
		<li class="simdoc_li">
			<a class="simdoc_a" href="<c:url value='<%=linkStr %>'/>" id="list_<%=count%>" target="_blank"><%=title%></a>
		</li>
<%
	}
} catch (Throwable e) {

}
%>
	</ul>
</div>

<%
if(request.getAttribute("simDocCount") == null) {
%>
<div class="simdoc_not_found">
${lfn:message('sys-ftsearch-db:sysFtsearch.simdoc.notfound')}
</div>
<%
}
%>