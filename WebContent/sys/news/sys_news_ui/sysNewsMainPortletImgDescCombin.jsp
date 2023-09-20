<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:ajaxtext>
	<table width="100%" height="100%" data-lui-mark='panel.dataview.height'>
		   <tr>
			    <td width="${HtmlParam.rate}%" valign="top">
				  <ui:dataview>
					    <ui:source type="AjaxXml">
						     {"url":"/sys/common/dataxml.jsp?s_bean=sysNewsMainPortletService&cateid=${JsParam.cateid}&rowsize=${JsParam.rowsize_image}&type=pic&scope=${JsParam.scope}"}
					    </ui:source>
					    <ui:render ref="sys.ui.slide.default" var-stretch="${HtmlParam.stretch}">
					    </ui:render>
				 </ui:dataview>
			  </td>
			   <td valign="top">
			     <ui:dataview format="sys.ui.listdesc">
					  	 <ui:source type="AjaxXml">
						   	 {"url":"/sys/common/dataxml.jsp?s_bean=sysNewsMainPortletService&cateid=${JsParam.cateid}&rowsize=${JsParam.rowsize_text }&type=!{type}&scope=${JsParam.scope}"}
						 </ui:source>
						  <ui:render ref="sys.ui.listdesc.default" var-target="${HtmlParam.target}" var-newDay="${HtmlParam.newDay}">
					     </ui:render>
			    </ui:dataview>
		      </td>
		 </tr>
	  </table>
</ui:ajaxtext>