<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.ui.util.ComponentUtil"%>
<%@page import="com.landray.kmss.sys.config.xml.XmlReaderContext"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 部件管理 -->
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/login_upload.css">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/ui/resource/css/component.css">
		<style>
			ul.profile_loginSetting_list>li{
				width: 100%;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel>
			<%--呈现--%>
			<ui:content title="${ lfn:message('sys-ui:mall.component.render') }">
				<!--筛选器 start-->
				<list:criteria id="criteria1" expand="true"  channel="channel_render">
					<%-- 搜索--%>
					<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-ui:sys.ui.search.fdName') }">
					</list:cri-ref>
					<!--来源：不限、内置、扩展-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.source')}" key="fdSource" multi="false" channel="channel_render">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.inner.component')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.outter.component')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!--数据格式-->
					<list:cri-criterion title="${lfn:message('sys-portal:sysPortalPortlet.fdFormat') }" key="formate" multi="true" channel="channel_render">
						<list:box-select>
							<list:item-select type="lui/criteria!CriterionSelectDatas">
								<ui:source type="AjaxJson" >
									{url: "/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=getFormates"}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!--筛选器 end-->
				<div id="profile_loginsetting_box" class="profile_loginsetting_box_curstom" >
					<div class="profile_loginSetting_list">
						<list:listview channel="channel_render">
							<ui:source type="AjaxJson" channel="channel_render">
								{url:'/sys/ui/sys_ui_component/sysUiComponent.do?method=listChildren&rowsize=19&type=render'}
							</ui:source>
							<list:gridTable name="gridtable_pic" columnNum="4"  channel="channel_render" style="table-layout: fixed;">
								<list:row-template>
									<c:import url="/sys/ui/help/component/component_tmpl.jsp" charEncoding="UTF-8">
										<c:param name="type" value="render"></c:param>
									</c:import>
								</list:row-template>
							</list:gridTable>
						</list:listview>
					</div>
				</div>
				<!-- 翻页 -->
				<list:paging  channel="channel_render"/>
				<script>
					seajs.use(['lui/topic'], function(topic) {
						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							window.setTimeout(function() {
								topic.channel("channel_render").publish('list.refresh');
							}, 500);
						});
					});
				</script>
			</ui:content>
			<!-- 外观 -->
			<ui:content title="${ lfn:message('sys-ui:mall.component.panel') }">
				<!--筛选器 start-->
				<list:criteria id="criteria2" expand="true"  channel="channel_layout">
					<%-- 搜索--%>
					<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-ui:sys.ui.search.fdName') }">
					</list:cri-ref>
					<!--来源：不限、内置、扩展-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.source')}" key="fdSource" multi="false" channel="channel_layout">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.inner.component')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.outter.component')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>

				<!--筛选器 end-->
				<div id="profile_loginsetting_box"  class="profile_loginsetting_box_curstom">
					<div class="profile_loginSetting_list">
						<list:listview  channel="channel_layout">
							<ui:source type="AjaxJson">
								{url:'/sys/ui/sys_ui_component/sysUiComponent.do?method=listChildren&rowsize=19&type=layout'}
							</ui:source>
							<list:gridTable name="gridtable_pic" columnNum="4"  channel="channel_layout">
								<list:row-template>
									<c:import url="/sys/ui/help/component/component_tmpl.jsp" charEncoding="UTF-8">
										<c:param name="type" value="panel"></c:param>
									</c:import>
								</list:row-template>
							</list:gridTable>
						</list:listview>
					</div>
				</div>
				<!-- 翻页 -->
				<list:paging  channel="channel_layout"/>
				<script>
					seajs.use(['lui/topic'], function(topic) {
						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							window.setTimeout(function() {
								topic.channel("channel_layout").publish('list.refresh');
							}, 500);
						});
					});
				</script>
			</ui:content>
			<!-- 页眉 -->
			<ui:content title="${ lfn:message('sys-ui:mall.component.header') }">
				<!--筛选器 start-->
				<list:criteria id="criteria3" expand="true"  channel="channel_header">
					<%-- 搜索--%>
					<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-ui:sys.ui.search.fdName') }">
					</list:cri-ref>
					<!--来源：不限、内置、扩展-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.source')}" key="fdSource" multi="false" channel="channel_header">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.inner.component')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.outter.component')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!--是否匿名-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.anonymous')}" key="fdAnonymous" multi="false" channel="channel_template">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.anonymous.yes')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.anonymous.no')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!--筛选器 end-->
				<div id="profile_loginsetting_box" class="profile_loginsetting_box_curstom  profile_componentset_box_curstom_portlet" >
					<div class="profile_loginSetting_list">
						<list:listview  channel="channel_header">
							<ui:source type="AjaxJson">
								{url:'/sys/ui/sys_ui_component/sysUiComponent.do?method=listChildren&rowsize=19&type=header'}
							</ui:source>
							<list:gridTable name="gridtable_pic" columnNum="4"  channel="channel_header">
								<list:row-template>
									<c:import url="/sys/ui/help/component/component_tmpl.jsp" charEncoding="UTF-8">
										<c:param name="type" value="header"></c:param>
									</c:import>
								</list:row-template>
							</list:gridTable>
						</list:listview>
					</div>
				</div>
				<!-- 翻页 -->
				<list:paging  channel="channel_header"/>
				<script>
					seajs.use(['lui/topic'], function(topic) {
						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							window.setTimeout(function() {
								topic.channel("channel_header").publish('list.refresh');
							}, 500);
						});
					});
				</script>
			</ui:content>
			<!-- 页脚 -->
			<ui:content title="${ lfn:message('sys-ui:mall.component.footer') }">
				<!--筛选器 start-->
				<list:criteria id="criteria4" expand="true"  channel="channel_footer">
					<%-- 搜索--%>
					<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-ui:sys.ui.search.fdName') }">
					</list:cri-ref>
					<!--来源：不限、内置、扩展-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.source')}" key="fdSource" multi="false" channel="channel_footer">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.inner.component')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.outter.component')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!--是否匿名-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.anonymous')}" key="fdAnonymous" multi="false" channel="channel_template">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.anonymous.yes')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.anonymous.no')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!--筛选器 end-->
				<div id="profile_loginsetting_box" class="profile_loginsetting_box_curstom">
					<div class="profile_loginSetting_list">
						<list:listview  channel="channel_footer">
							<ui:source type="AjaxJson">
								{url:'/sys/ui/sys_ui_component/sysUiComponent.do?method=listChildren&rowsize=19&type=footer'}
							</ui:source>
							<list:gridTable name="gridtable_pic" columnNum="4"  channel="channel_footer">
								<list:row-template>
									<c:import url="/sys/ui/help/component/component_tmpl.jsp" charEncoding="UTF-8">
										<c:param name="type" value="footer"></c:param>
									</c:import>
								</list:row-template>
							</list:gridTable>
						</list:listview>
					</div>
				</div>
				<!-- 翻页 -->
				<list:paging  channel="channel_footer"/>
				<script>
					seajs.use(['lui/topic'], function(topic) {
						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							window.setTimeout(function() {
								topic.channel("channel_footer").publish('list.refresh');
							}, 500);
						});
					});
				</script>
			</ui:content>
			<!-- 页面模板 -->
			<ui:content title="${ lfn:message('sys-ui:mall.component.template') }">
				<!--筛选器 start-->
				<list:criteria id="criteria5" expand="true"  channel="channel_template">
					<%-- 搜索--%>
					<list:cri-ref key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-ui:sys.ui.search.fdName') }">
					</list:cri-ref>
					<!--来源：不限、内置、扩展-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.source')}" key="fdSource" multi="false" channel="channel_template">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.inner.component')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.outter.component')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!--是否匿名-->
					<list:cri-criterion title="${ lfn:message('sys-ui:sys.ui.anonymous')}" key="fdAnonymous" multi="false" channel="channel_template">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-ui:sys.ui.anonymous.yes')}', value:'true'},
									{text:'${ lfn:message('sys-ui:sys.ui.anonymous.no')}',value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
				</list:criteria>
				<!--筛选器 end-->
				<div id="profile_loginsetting_box" class="profile_loginsetting_box_curstom">
					<div class="profile_loginSetting_list">
						<list:listview  channel="channel_template">
							<ui:source type="AjaxJson">
								{url:'/sys/ui/sys_ui_component/sysUiComponent.do?method=listChildren&rowsize=19&type=template'}
							</ui:source>
							<list:gridTable name="gridtable_pic" columnNum="4"  channel="channel_template">
								<list:row-template>
									<c:import url="/sys/ui/help/component/component_tmpl.jsp" charEncoding="UTF-8">
										<c:param name="type" value="template"></c:param>
									</c:import>
								</list:row-template>
							</list:gridTable>
						</list:listview>
					</div>
				</div>
				<!-- 翻页 -->
				<list:paging  channel="channel_template"/>
				<script>
					seajs.use(['lui/topic'], function(topic) {
						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							window.setTimeout(function() {
								topic.channel("channel_template").publish('list.refresh');
							}, 500);
						});
					});
				</script>
			</ui:content>
		</ui:tabpanel>
		<script type="text/javascript">
			//下载部件包
			function _download(fdId) {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
					$("#downloadId").val(fdId);
					document.componentForm.submit();
				});
			}
			// 预览图片
			function _review(url) {
				var previewUrl = "/sys/ui/help/component/component_preview.jsp";
				seajs.use(['lui/dialog'],function(dialog){
					dialog.iframe(previewUrl, "", function() {
					}, {
						"width": 920,
						"height": 680,
						params: {
							"pcPreviewUrl": url,
							"createUrl": url,
						}
					});
				});
			}
			//删除部件
			function _delete(fdId,uiType){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.confirm("删除该部件将删除上传的整个部件包，确认删除？",function(val){
						if(val){
							$.get("${LUI_ContextPath}/sys/ui/sys_ui_component/sysUiComponent.do?method=deleteComponent&fdId="+fdId+"&uiType="+uiType,function(txt){
								if(txt=="1"){
									dialog.success("删除成功",null,function(){
										window.location.reload();
									});
								}else{
									dialog.failure("删除失败",null,function(){
										window.location.reload();
									});
								}
							});
						}
					});
				});
			}

			function uploadComponent(type){
				seajs.use(['lui/dialog'],function(dialog){
					var url = "/sys/ui/help/component/upload.jsp";
					var title;
					var config = {"width" : 600,"height" : 320};
					//有商城模块
					<kmss:ifModuleExist path="/third/mall/">
					url = "/sys/ui/help/component/uploadComponentTempl.jsp?type="+type;
					config = {"width" : 920,"height" : 650};
					</kmss:ifModuleExist>
					if(type=='render'){
						title="${lfn:message('sys-ui:mall.component.add.render') }";
					}else if(type=='panel'){
						title="${lfn:message('sys-ui:mall.component.add.panel') }";
					}else if(type=='header'){
						title="${lfn:message('sys-ui:mall.component.add.header') }";
					}else if(type=='footer'){
						title="${lfn:message('sys-ui:mall.component.add.footer') }";
					}else{
						title="${lfn:message('sys-ui:mall.component.add.template') }";
					}
					dialog.iframe(url,title,function(data){
						if (window.LUI) {
							LUI.fire({ type: "topic", name: "successReloadPage" });
						}else{
							location.href = Com_SetUrlParameter(location.href, "reLoad", "yes");
						}
					},config);
				});
			}
		</script>
		<!-- 打包下载 -->
		<form target="_blank" name="componentForm" action="<c:url value="/sys/ui/sys_ui_component/sysUiComponent.do?method=download"/>" method="post">
		<input type="hidden" name="downloadId" id="downloadId" />
		</form>
	</template:replace>
</template:include>