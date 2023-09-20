<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="person" list="${list}" varIndex="status">
	    <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="index">
		     ${status+1}
		</list:data-column >
		<!--创建时间-->
		<list:data-column col="templateName"  title="${ lfn:message('sys-lbpmperson:sys.lbpmperson.myFlow.recent') }" escape="false">
					<a  title="${person.templateName}" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/${person.addUrl}" target="_blank">
							 <!-- 修改 -->
			       			 <span class="${person.className}" style="margin-right:10px;font-size:16px;"></span>
			       			 ${person.templateDesc}
			       	    </a>
		</list:data-column>
	</list:data-columns>
	
</list:data>