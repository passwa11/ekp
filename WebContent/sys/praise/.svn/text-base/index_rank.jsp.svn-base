<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" >
	<template:replace name="title">
		${lfn:message('sys-praise:sysPraiseInfo.portlet.rank') }
	</template:replace>
	<template:replace name="head">
		<template:super/>
		<script>
			seajs.use(['theme!list','theme!portal','sys/praise/style/index.css']);
		</script>
	</template:replace>
	<template:replace name="body">
		<portal:header var-width="100%" />
		<%--模块筛选 --%>
		<div style="width:90%; min-width:980px; margin:4px auto 15px auto;">
			<list:criteria id="criteria1" expand="true" multi="false">
				<list:cri-criterion title="${ lfn:message('sys-praise:sysPraiseInfo.calculate.time')}" key="fdTimeType"> 
					<list:box-select>
						<list:item-select cfg-defaultValue="week" cfg-required = "true">
							<ui:source type="Static">
								[{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.week')}', value:'week'},
								{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.month')}',value:'month'},
								{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.year')}',value:'year'},
								{text:'${ lfn:message('sys-praise:sysPraiseInfo.calculate.total')}',value:'total'}]
							</ui:source>
							<ui:event event="selectedChanged" args="evt">
								var vals = evt.values;
								var val = vals[0].value;
								document.getElementById("timeValue").setAttribute("value",val);
							</ui:event>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfoPersonal" 
			property="fdPerson"/>
		</list:criteria>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<%--list视图--%>
			<list:listview id = "listview">
			<ui:source type="AjaxJson">
				{url:'/sys/praise/sys_praise_info_personal/sysPraiseInfoPersonal.do?method=data&orderInfo=fdSumCount'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdPerson,fdSumCount,fdPraisedNum,fdReceiveNum,fdRichGet"></list:col-auto>
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>
	 	</div>
	 	<portal:footer var-width="100%" />
	</template:replace>
</template:include>
