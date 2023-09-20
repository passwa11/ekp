<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.property.model.SysPropertyDefine"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="sysPropertyDefine" list="${queryPage.list}"
		varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdName"
			title="${ lfn:message('sys-property:table.sysPropertyDefine') }">
		</list:data-column>
		<list:data-column property="hbmParent.fdId"
			title="${ lfn:message('sys-property:sysPropertyReference.fdParentId') }">
		</list:data-column>
		<list:data-column col="hbmParentId"
			title="${ lfn:message('sys-property:sysPropertyReference.fdParentId') }">
			<%
				Object basedocObj = pageContext.getAttribute("sysPropertyDefine");
				if (basedocObj != null) {
					SysPropertyDefine sysPropertyDefine = (SysPropertyDefine) basedocObj;
					SysPropertyDefine hbmParent = (SysPropertyDefine)sysPropertyDefine.getHbmParent();
					if(hbmParent!=null){
						out.print(sysPropertyDefine.getHbmParent().getFdId());
					}else
						out.print("");
				}
			%>
		</list:data-column>
		<list:data-column property="hbmParent.fdName"
			title="${ lfn:message('sys-property:sysPropertyReference.fdParentName') }">
		</list:data-column>
		
		<list:data-column col="hbmParentName"
			title="${ lfn:message('sys-property:sysPropertyReference.fdParentId') }">
			<%
				Object basedocObj = pageContext.getAttribute("sysPropertyDefine");
				if (basedocObj != null) {
					SysPropertyDefine sysPropertyDefine = (SysPropertyDefine) basedocObj;
					
					SysPropertyDefine hbmParent = (SysPropertyDefine)sysPropertyDefine.getHbmParent();
					if(hbmParent!=null){
						out.print(hbmParent.getFdName());
					}else
						out.print("");
					
				}
			%>
		</list:data-column>
		
		<list:data-column property="fdType"
			title="${ lfn:message('sys-property:sysPropertyDefine.fdType') }">
		</list:data-column>
		<list:data-column col="fdDisplayType"
			title="${ lfn:message('sys-property:sysPropertyDefine.fdDisplayType') }">
			<%
				Object basedocObj = pageContext.getAttribute("sysPropertyDefine");
				if (basedocObj != null) {
					SysPropertyDefine sysPropertyDefine = (SysPropertyDefine) basedocObj;
					
					String fdDisplayType = sysPropertyDefine.getFdDisplayType();
					if(fdDisplayType!=null){
						out.print(fdDisplayType);
					}else
						out.print("");
				}
			%>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>