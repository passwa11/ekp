<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" >
	<template:replace name="title">
		${lfn:message('sys-praise:sysPraiseInfo.detail') }
	</template:replace>
	<template:replace name="head">
		<template:super/>
	</template:replace>
	<template:replace name="body">
		<portal:header var-width="100%" />
		<%--模块筛选 --%>
	<div style="width: 90%;margin: auto;margin-top: 30px;margin-bottom: 30px;">
			<list:criteria id="module_sort" multi="false">		
				<list:cri-criterion title="${lfn:message('sys-praise:sysPraiseInfo.fdType') }" key="fdType" multi="false">
					<list:box-select>
						<list:item-select >
							<ui:source type="Static">
								[{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }", value: '1'},
								{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }", value:'2'},
							{text:"${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }", value:'3'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
			</list:criteria>
			<list:listview id="listview">
				<ui:source type="AjaxJson">
					{url:'/sys/praise/sys_praise_info/sysPraiseInfo.do?method=data&fdModelId=${param.fdModelId}&q.fdTargetPerson=${param.fdTargetPerson}&fdSourceId=${param.fdSourceId}'}
				</ui:source>
				<list:colTable name="columntable">
				<list:col-serial />
				<list:col-html title="${lfn:message('sys-praise:sysPraiseInfo.docSubject')}" style="width:55%;text-align:left;padding:0 8px">
					{$
						<span class="com_subject">{%row['docSubject']%}</span> 
					$}
				</list:col-html>
				 <list:col-auto props="fdPraisePersonName;fdType;docCreateTime"></list:col-auto>
				 </list:colTable>
			</list:listview> 
		 	<list:paging></list:paging>
	 	</div>
			
	 	<portal:footer var-width="100%" />
	</template:replace>
</template:include>
