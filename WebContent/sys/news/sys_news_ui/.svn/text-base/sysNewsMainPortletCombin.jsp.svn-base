<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
<%String rate=request.getParameter("rate");  
  pageContext.setAttribute("rate",rate+"%");
%>
	<table width="100%" height="100%" data-lui-mark='panel.dataview.height'>
		   <tr>
			    <td width="<c:out value="${rate}"/>" valign="top">
				  <ui:dataview>
					    <ui:source type="AjaxXml">
						     {"url":"/sys/common/dataxml.jsp?s_bean=sysNewsMainPortletService&cateid=${JsParam.cateid}&rowsize=${JsParam.rowsize_image}&type=pic&scope=${JsParam.scope}"}
					    </ui:source>
					    <ui:render ref="sys.ui.slide.default"  var-showTitle="${param.showTitle}"  var-stretch="${param.stretch}" >
					    </ui:render>
				 </ui:dataview>
			  </td>
			   <td valign="top">
			     <ui:dataview format="sys.ui.classic">
					  	 <ui:source type="AjaxXml">
						   	 {"url":"/sys/common/dataxml.jsp?s_bean=sysNewsMainPortletService&cateid=${JsParam.cateid}&rowsize=${JsParam.rowsize_text }&type=!{type}&scope=${JsParam.scope}"}
						 </ui:source>
						  <ui:render ref="sys.ui.classic.default" var-showCreator="${param.showCreator}" var-showCreated="${param.showCreated}"
						                                          var-highlight="${param.highlight}"   var-target="${param.target}"
						                                          var-showCate="${param.showCate}" var-cateSize="${param.cateSize}" var-newDay="${param.newDay}">
					     </ui:render>
			    </ui:dataview>
		      </td>
		 </tr>
	  </table>
</ui:ajaxtext>