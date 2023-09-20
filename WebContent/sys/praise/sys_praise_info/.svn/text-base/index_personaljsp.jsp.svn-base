<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list">
	<template:replace name="title">${lfn:message('sys-praise:module.sys.praiseInfo.manager') }</template:replace>
	<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav" id="simplecategoryId">
		<ui:menu-item text="${ lfn:message('home.home') }"
			icon="lui_icon_s_home" href="/" target="_self">
		</ui:menu-item>
		<ui:menu-item text="${lfn:message('sys-praise:module.sys.praiseInfo.manager') }" href="javascript:;" target="_self">
		</ui:menu-item>
		<c:if test="${empty JsParam.type || JsParam.type == 'create'}">
			<ui:menu-item text="${lfn:message('sys-praise:sysPraiseInfo.myCreate') }">
			</ui:menu-item>
		</c:if>
		<c:if test="${not empty JsParam.type && JsParam.type=='receive'}">
			<ui:menu-item text="${lfn:message('sys-praise:sysPraiseInfo.myReceive') }">
			</ui:menu-item>
		</c:if>
	</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.title">
		</ui:combin>
		
		<div class="lui_list_nav_frame">
		<ui:accordionpanel>
		<ui:content title="${ lfn:message('list.search') }">
	    	<ui:combin ref="menu.nav.simple">
	            <ui:varParam name="source">
	                <ui:source type="Static">
	                    [
	                    <kmss:authShow roles="ROLE_SYSATTACHMENT_BORROW_DEFAULT">
	                        {
	                        "text" : "${ lfn:message('sys-praise:sysPraiseInfo.all') }",
	                        "href" :  "javascript:sysPraise.navOpenPage('${LUI_ContextPath }/sys/praise/sys_praise_info/index_admin.jsp', '_self')",
	                        "icon" : "iconfont lui_iconfont_navleft_lmap_all"
	                        },
	                    </kmss:authShow>
	                        {
	                            "text" : "${ lfn:message('sys-praise:sysPraiseInfo.myCreate') }",
	                            "href" :  "javascript:sysPraise.navOpenPage('${LUI_ContextPath}/sys/praise/index.jsp?type=create', '_self')",
	                            "icon" : "iconfont lui_iconfont_navleft_lmap_assigned"
	                        }
	                        ,{
	                            "text" : "${ lfn:message('sys-praise:sysPraiseInfo.myReceive') }",
	                            "href" :  "javascript:sysPraise.navOpenPage('${LUI_ContextPath}/sys/praise/index.jsp?type=receive', '_self')",
	                            "icon" : "iconfont lui_iconfont_navleft_learn_need"
	                        }
	                    ]
	                </ui:source>
	            </ui:varParam>
	        </ui:combin>
		</ui:content>
		<kmss:ifModuleExist path="/kms/imall/">
			<ui:content title="${ lfn:message('kms-imall:kmsImallExpenseSource.imallStore')}">  
				<ul class='lui_list_nav_list'>
					<li><a onclick="gotoImall()"><i class="iconfont iconfont_nav lui_iconfont_nav_kms_imall"></i>${ lfn:message('kms-imall:kmsImallExpenseSource.imallStore')}</a></li>
				</ul>
			</ui:content>
		</kmss:ifModuleExist>
		<kmss:authShow roles="ROLE_SYSPRAISEINFO_MAINTAINER">
			<ui:content title="${ lfn:message('list.otherOpt') }">
				<ui:combin ref="menu.nav.simple">
					<ui:varParam name="source">
						<ui:source type="Static">
							[{
								"text" : "${ lfn:message('list.manager') }",
								"href":"/management",
								"router":true,
								"icon" : "lui_iconfont_navleft_com_background"
								}
							]
						</ui:source>
					</ui:varParam>
				</ui:combin>
			</ui:content>
		</kmss:authShow>	
		</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<list:criteria id="criteria1" multi="false" >
			<list:cri-criterion title="${lfn:message('sys-praise:sysPraiseInfo.personType') }" key="mydoc" expand="false" multi="false">
				<list:box-select >
					<list:item-select cfg-required="true" cfg-defaultValue="${empty JsParam.type?'create':JsParam.type}" >
						<ui:source type="Static" >
							[{text:'${lfn:message('sys-praise:sysPraiseInfo.myCreate') }', value:'create'},{text:'${lfn:message('sys-praise:sysPraiseInfo.myReceive') }',value:'receive'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
			<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdPraisePerson" />
			<list:cri-auto modelName="com.landray.kmss.sys.praise.model.SysPraiseInfo" property="fdTargetPerson" />
		</list:criteria>
		<div class="lui_list_operation">
		</div>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/praise/sys_praise_info/sysPraiseInfo.do?method=myData&fdModelId=${param.fdModelId}'}
			</ui:source>
			<list:colTable name="columntable" rowHref="/sys/praise/sys_praise_info/sysPraiseInfo.do?method=view&fdId=!{fdId}" >
			<list:col-serial />
			 <list:col-auto props="fdTargetPersonName;fdRich"></list:col-auto>
			<list:col-html title="${lfn:message('sys-praise:sysPraiseInfo.fdReason')}" style="width:45%;padding:0 8px">
				{$
					<span class="com_subject">{%row['fdReason']%}</span> 
				$}
			</list:col-html>
			 <list:col-auto props="fdPraisePersonName;fdType;docCreateTime"></list:col-auto>
			 </list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/praise/resource/js/index.js"></script>
		<script>
			function gotoImall(){
	              Com_OpenWindow("${LUI_ContextPath}/kms/imall/","_blank");
	        }
		</script>
		<style>
			.lui_dataview_picmenu_content{
				cursor: auto !important;
			}
		</style>
	</template:replace>
</template:include>