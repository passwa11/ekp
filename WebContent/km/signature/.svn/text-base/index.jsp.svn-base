<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list" spa="true" rwd="true">
	<%-- 标签页标题 --%>
	<template:replace name="title">
		<c:out value="${ lfn:message('km-signature:module.km.signature') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<script>
			(function() {
				var  toggleView = "${JsParam.toggleView}";
				if(toggleView) {
					var toggleList = ["gridtable","columntable"];
					for(var i = 0; i < toggleList.length; i++) {
						if(toggleView === toggleList[i]);
							localStorage.setItem("toggle.change", toggleView);	
					}
				}
			})();
		</script>
		<style>
		.lui_listview_gridtable_table .lui_listview_gridtable_content .lui_listview_gridtable_content_docSubject{
			text-align: left;
			display: block;
			word-wrap : break-word;
			min-height: 24px;
			width:100%
		}
		.lui_listview_gridtable_table .lui_listview_gridtable_content .lui_listview_gridtable_content_container .txtBox .txtBox_label {
			width: 65px;
			text-align: left;
			float: left;
		}
		</style>
	</template:replace>
	
	<%-- 左侧导航栏 --%>
	<template:replace name="nav">
		<%-- 所有分类 --%>
		
		<%-- 新建按钮 --%>
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('km-signature:module.km.signature') }" />
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href": "javascript:void(0);",
						"icon": "km_signature"
					}
				]
			</ui:varParam>
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<%-- 常用查询 --%>
				<ui:content title="${ lfn:message('list.search') }">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('km-signature:signature.allSig') }",
		  						"href" :  "/listAll",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_all"
		  					},{
		  						"text" : "${ lfn:message('km-signature:signature.createdByMe') }",
		  						"href" :  "/listCreate",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_my_drafted"
		  					},{
		  						"text" : "${ lfn:message('km-signature:signature.authorizeMe') }",
		  						"href" :  "/listAuthorize",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_signature_author"
		  					}]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
				</ui:content>
				<%-- 后台配置 --%>
				<kmss:authShow roles="ROLE_SIGNATURE_BACKSTAGE_MANAGER">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="false">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
			  					{
									"text" : "${ lfn:message('list.manager') }",
									"icon" : "lui_iconfont_navleft_com_background",
									"router" : true,
									"href" : "/management"
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
	
	<%-- 右侧页面 --%>
	<template:replace name="content">  
		<ui:tabpanel id="kmSignaturePanel" layout="sys.ui.tabpanel.list" cfg-router="true">
			<ui:content id="kmSignatureContent" title="${ lfn:message('km-signature:signature.allSig') }">
				<%-- 查询栏 --%>
				<list:criteria id="criteria1">
					<%-- 搜索条件:签章名称--%>
					<list:cri-ref key="fdMarkName" ref="criterion.sys.docSubject" title="${ lfn:message('km-signature:signature.markname') }">
					</list:cri-ref>
					<%-- 搜索条件:是否有效 --%>
					<list:cri-criterion title="${ lfn:message('km-signature:signature.fdIsAvailable')}" key="fdIsAvailable" multi="false" >
						<list:box-select>
							<list:item-select cfg-defaultValue="1">
								<ui:source type="Static">
									[{text:'${ lfn:message('message.yes')}', value:'1'},
									{text:'${ lfn:message('message.no')}',value:'0'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-ref ref="criterion.sys.postperson.availableAll"
								  key="docCreatorId" multi="false"
								  title="${lfn:message('km-signature:signature.docCreator') }" />
					<%-- 其他搜索条件:签章类型（个人签名、单位印章）、签章保存时间 --%>
					<list:cri-auto modelName="com.landray.kmss.km.signature.model.KmSignatureMain" 
						property="fdDocType;docCreateTime" />
				</list:criteria>
				
				<%-- 操作栏 --%>
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
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
								<list:sort property="docCreateTime" text="${lfn:message('km-signature:signature.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div class="lui_list_operation_toolbar">
						<ui:toolbar count="4">
							<%-- 视图选择 --%>
							<ui:togglegroup order="0">
								<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
									value="columntable"	group="tg_1" text="${ lfn:message('list.columnTable') }" 
									onclick="LUI('listview').switchType(this.value);">
								</ui:toggle>
								<ui:toggle icon="lui_icon_s_tuwen" title="${lfn:message('list.gridTable') }" group="tg_1"
									value="gridtable" text="${lfn:message('list.gridTable') }"
									onclick="LUI('listview').switchType(this.value);">
								</ui:toggle>
							</ui:togglegroup>
							<%-- 新建 --%>
							<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=add">
								<ui:button text="${lfn:message('button.add')}" onclick="window.moduleAPI.kmSignature.addDoc()" order="1"></ui:button>	
							</kmss:auth>
							<%-- 批量置为无效 --%>
							<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=invalidatedAll">
								<ui:button id="fdAvailable" text="${lfn:message('km-signature:signature.isNotAvailable') }" onclick="window.moduleAPI.kmSignature.invalidatedAll()" order="2"></ui:button>
							</kmss:auth>	
							<%-- 删除 --%>		
							<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=deleteall">
								<ui:button text="${lfn:message('button.deleteall')}" onclick="window.moduleAPI.kmSignature.delDoc()" order="3"></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>	
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 <%--list页面--%>
				<list:listview id="listview" >
					<ui:source type="AjaxJson" >
						{url:'/km/signature/km_signature_main/kmSignatureMainIndex.do?method=listChildren&categoryId=${JsParam.categoryId}'}
					</ui:source>
					<%--列表视图--%>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable"
						rowHref="/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=!{fdId}" >
						
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-html title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left">
						{$ <span class="com_subject" >{%row['fdMarkName']%}</span> $}
						</list:col-html>
						<list:col-auto props="fdUsers;fdIsDefault;fdIsFreeSign;fdDocType;docCreator.fdName;docCreateTime" ></list:col-auto>
					</list:colTable>
					<%-- 图文  --%>
					<list:gridTable name="gridtable" columnNum="4" gridHref="/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=!{fdId}">
						<ui:source type="AjaxJson">
							{url:'/km/signature/km_signature_main/kmSignatureMainIndex.do?method=listChildren&categoryId=${JsParam.categoryId}&orderby=fdMarkDate&ordertype=down&dataType=pic'}
						</ui:source>
						<list:row-template ref="sys.ui.listview.gridtable" >
						</list:row-template>
					</list:gridTable>
				</list:listview> 
			 	<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>	 
	 		 
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			seajs.use('km/signature/km_signature_main_ui/style/index.css');
			seajs.use(['lui/framework/module'],function(Module){
      			Module.install('kmSignature',{
					//模块变量
					$var : {
						docType : '${JsParam.docType}',
						categoryId : '${JsParam.categoryId}'
					},
 					//模块多语言
 					$lang : {
 						pageNoSelect : '${lfn:message("page.noSelect")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '{lfn:message("button.delete")}',
 						comfirmDelete : '${lfn:message("page.comfirmDelete")}',
 						changeRightBatch : '${lfn:message("sys-right:right.button.changeRightBatch")}',
 						fdIsAvailableConfirm : '${lfn:message("km-signature:signature.fdIsAvailableConfirm")}',
 						allSig : '${lfn:message("km-signature:signature.allSig")}',
 						createdByMe :'${lfn:message("km-signature:signature.createdByMe")}',
 						authorizeMe : '${lfn:message("km-signature:signature.authorizeMe")}'
 					},
 					//搜索标识符
 					$search : 'com.landray.kmss.km.signature.model.KmSignatureMain'
  				});
      		});
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/km/signature/resource/js/index.js"></script>
	</template:replace>
</template:include>
