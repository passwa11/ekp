<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head" >
	<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
	</template:replace>
	<template:replace name="content">  
		<list:criteria id="criteria1">
	     	<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"> 
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('km-smissive:kmSmissiveMain.docStatus')}" key="docStatus"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('status.draft')}', value:'10'},{text:'${ lfn:message('status.examine')}',value:'20'},{text:'${ lfn:message('status.refuse')}',value:'11'},{text:'${ lfn:message('status.discard')}',value:'00'},{text:'${ lfn:message('status.publish')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.km.smissive.model.KmSmissiveMain" property="docAuthor;fdMainDept;docCreateTime;fdFileNo;docProperties" />
		</list:criteria>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('km-smissive:kmSmissiveMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="docPublishTime" text="${lfn:message('km-smissive:kmSmissiveMain.docPublishTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="4">
						<%-- 收藏 --%>
						<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp"
							charEncoding="UTF-8">
							<c:param name="fdTitleProName" value="docSubject" />
							<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
						</c:import>
						<kmss:authShow roles="ROLE_KMSMISSIVE_CREATE">
						<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=add" requestMethod="GET">
						<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2"></ui:button>
						</kmss:auth>
						</kmss:authShow>
						<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=deleteall&status=${param.status}&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="3"></ui:button>
						</kmss:auth>
						
						<%-- 修改权限 --%>
						<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
							<c:param name="authReaderNoteFlag" value="2" />
						</c:import>							
						<%-- 分类转移 --%>
						<%-- 如果用户有“分类权限扩充”角色，则允许转移到所有的分类 --%>
						<% 
							if(com.landray.kmss.util.UserUtil.checkRole("ROLE_KMSMISSIVE_OPTALL")) {
								request.setAttribute("authType", "00");
							} 
						%>
						<c:import url="/sys/simplecategory/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
							<c:param name="modelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
							<c:param name="docFkName" value="fdTemplate" />
							<c:param name="cateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
							<c:param name="authType" value="${authType}" />
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/smissive/km_smissive_main/kmSmissiveMain.do?method=manageList&categoryId=${JsParam.categoryId}'}
			</ui:source>
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain" isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto ></list:col-auto>
			</list:colTable>
			<list:rowTable isDefault="false"
				rowHref="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=!{fdId}" name="rowtable" >
				<list:row-template ref="sys.ui.listview.rowtable">
				</list:row-template>
			</list:rowTable>
		</list:listview>
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">

	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.smissive.model.KmSmissiveMain";
	 	
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

				LUI.ready(function(){
			 		  // 初始化左则菜单样式
		        	  setTimeout("initMenuNav()", 300);
				});
				// 左则样式
				window.initMenuNav = function() {
			 		var mydoc = getValueByHash("mydoc");
			 	 	if(mydoc != "") {
			 			resetMenuNavStyle($("#smissive_" + mydoc));
			 		}else {
			 			resetMenuNavStyle($("#smissive_allFlow")); 
			 		}
				 }
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.addDoc = function() {
						dialog.simpleCategoryForNewFile(
								'com.landray.kmss.km.smissive.model.KmSmissiveTemplate',
								'/km/smissive/km_smissive_main/kmSmissiveMain.do?method=add&categoryId=!{id}',false,null,null,'${JsParam.categoryId}');
				};
				//删除
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					
					var url= '<c:url value="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=deleteall"/>&categoryId=${JsParam.categoryId}';
					
					var config = {
							url : url, // 删除数据的URL
							data : $.param({"List_Selected":values},true), // 要删除的数据
							modelName : "com.landray.kmss.km.smissive.model.KmSmissiveMain"
					};
					function delCallback(data){
						topic.publish("list.refresh");
						dialog.result(data);
					};
					// 通用删除方法
					Com_Delete(config, delCallback);
				};
			});
		</script>	 	
	</template:replace>
</template:include>