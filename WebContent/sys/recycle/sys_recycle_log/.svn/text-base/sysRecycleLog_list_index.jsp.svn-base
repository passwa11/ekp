<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="content">
<list:criteria id="criteria1">
	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
	</list:cri-ref>
	<list:cri-auto modelName="com.landray.kmss.sys.recycle.model.SysRecycleLog" 
		property="fdModelId;fdCreator;fdOperator;fdOperatorIp;fdOptDate;fdOptType" />
	<c:if test="${HtmlParam.all=='true'}">
		<list:cri-criterion title="所属模块" key="fdModelName" multi="false">
			<list:box-select>
				<list:item-select type="lui/criteria!CriterionSelectDatas"  id="moduleNames">
					<ui:source type="AjaxJson" >
						{url: "/sys/recycle/sys_recycle_log/sysRecycleLog.do?method=getModules"}
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
	</c:if>
</list:criteria>
<%@ include file="/sys/recycle/sys_recycle_log/sysRecycleLog_listview.jsp" %>
	</template:replace>
</template:include>