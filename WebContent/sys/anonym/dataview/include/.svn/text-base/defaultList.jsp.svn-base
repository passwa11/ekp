<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld"
	prefix="portal"%>
<template:include ref="template.anonymous.default" pagewidth="980px">
	<template:replace name="body1">
		<div style="margin: 5px 10px;">
			<!-- 筛选 -->
			<list:criteria id="criteria1">
				<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-anonym:sysAnonymMain.fdName')}" />
				<list:cri-auto modelName="com.landray.kmss.sys.anonym.model.SysAnonymCommon" property="docCreateTime"/>
				<list:cri-auto modelName="com.landray.kmss.sys.anonym.model.SysAnonymCommon" property="fdSummary"/>
			</list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysAnonymCommon.docCreateTime" text="${lfn:message('sys-anonym:sysAnonymMain.docCreateTime')}" group="sort.list" value="down"/>
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
            </div>
            
            <ui:fixed elem=".lui_list_operation" />
			<!-- 列表 -->
			<list:listview id="listview">
				<ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/anonym/sysAnonymData.do?method=dataList&formatKey=listTable${queryString }')}
                </ui:source>
				<!-- 列表视图 -->
				<list:colTable isDefault="false"
					rowHref="/sys/anonym/sysAnonymData.do?method=dataView&fdId=!{fdAnonymId}"
					name="columntable">
					<list:col-checkbox />
					<list:col-serial />
					<list:col-auto props="fdName;fdSummary;docCreateTime" url="" />
				</list:colTable>
			</list:listview>
			<!-- 翻页 -->
			<list:paging />
		</div>
		<script>
			var listOption = {
				contextPath : '${LUI_ContextPath}',
				jPath : 'main',
				modelName : 'com.landray.kmss.sys.anonym.model.SysAnonymCommon',
				templateName : '',
				basePath : '/sys/anonym/sysAnonymData.do',
				canDelete : '${canDelete}',
				mode : '',
				templateService : '',
				templateAlert : '${lfn:message("sys-anonym:treeModel.alert.templateAlert")}',
				customOpts : {

					____fork__ : 0
				},
				lang : {
					noSelect : '${lfn:message("page.noSelect")}',
					comfirmDelete : '${lfn:message("page.comfirmDelete")}'
				}

			};
			Com_IncludeFile("list.js",
					"${LUI_ContextPath}/sys/anonym/dataview/resource/js/", 'js', true);
		</script>
	</template:replace>
</template:include>