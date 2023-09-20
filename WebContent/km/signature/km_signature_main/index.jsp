<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
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
		}
		.lui_listview_gridtable_table .lui_listview_gridtable_content .lui_listview_gridtable_content_container .txtBox .txtBox_label {
			width: 65px;
			text-align: right;
			float: left;
		}
		</style>
	</template:replace>
	<%-- 右侧页面 --%>
	<template:replace name="content">  
		<%-- 查询栏 --%>
		<list:criteria id="criteria1">
			<%-- 搜索条件:签章名称--%>
			<list:cri-ref key="fdMarkName" ref="criterion.sys.docSubject" title="${ lfn:message('km-signature:signature.markname') }">
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('km-signature:tree.myDoc') }" key="mydoc" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('km-signature:signature.createdByMe') }', value:'create'},{text:'${ lfn:message('km-signature:signature.authorizeMe') }',value:'authorize'}]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<%-- 搜索条件:是否有效 --%>
			<list:cri-criterion title="${ lfn:message('km-signature:signature.fdIsAvailable')}" key="fdIsAvailable" >
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
			<%-- 其他搜索条件:签章分类、签章类型（个人签名、单位印章）、用户名称、签章保存时间 --%>
			<list:cri-auto modelName="com.landray.kmss.km.signature.model.KmSignatureMain" 
				property="docCreateTime" />
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
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5">
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
							<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>	
						</kmss:auth>
						<%-- 批量置为无效 --%>
						<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=invalidatedAll">
							<ui:button id="fdAvailable" text="${lfn:message('km-signature:signature.isNotAvailable') }" onclick="invalidatedAll()" order="2"></ui:button>
						</kmss:auth>	
						<%-- 删除 --%>		
						<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=deleteall">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="3"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		 <%--list页面--%>
		<list:listview id="listview" >
			<ui:source type="AjaxJson" >
				{url:'/km/signature/km_signature_main/kmSignatureMainIndex.do?method=listChildren&fdDocType=${JsParam.docType}&backstage=true'}
			</ui:source>
			<%--列表视图--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable"
				rowHref="/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=!{fdId}" >
				
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-html title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left">
				{$ <span class="com_subject" >{%row['fdMarkName']%}</span> $}
				</list:col-html>
				<c:choose>
					<c:when test="${JsParam.docType == '2'}">
						<list:col-auto props="fdUsers;fdDocType;docCreator.fdName;docCreateTime,operations" ></list:col-auto>
					</c:when>
					<c:otherwise>
						<list:col-auto props="fdUsers;fdIsDefault;fdIsFreeSign;fdDocType;docCreator.fdName;docCreateTime,operations" ></list:col-auto>
					</c:otherwise>
				</c:choose>
				<ui:event topic="list.loaded">
					Dropdown.init();
				</ui:event>
			</list:colTable>
			<%-- 图文  --%>
			<list:gridTable name="gridtable" columnNum="4" gridHref="/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=!{fdId}">
				<ui:source type="AjaxJson">
					{url:'/km/signature/km_signature_main/kmSignatureMainIndex.do?method=listChildren&fdDocType=${JsParam.docType}&orderby=fdMarkDate&ordertype=down&dataType=pic'}
				</ui:source>
				<list:row-template ref="sys.ui.listview.gridtable" >
				</list:row-template>
			</list:gridTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
		 	seajs.use('km/signature/km_signature_main_ui/style/index.css');
	 		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.signature.model.KmSignatureMain";
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				//控制批量置为无效按钮的显示
				topic.subscribe('criteria.changed',function(evt){
					//按钮对象存在则先隐藏
					if(LUI('fdAvailable')){
						LUI('fdAvailable').setVisible(false);
					}
				    if(evt['criterions'].length>0){
						for(var i=0;i<evt['criterions'].length;i++){
							//控制批量领取和批量归还按钮的显示
							if(evt['criterions'][i].key == "fdIsAvailable"){
								if(evt['criterions'][i].value.length == 1){
									if(evt['criterions'][i].value[0] == "1"){
										 LUI('fdAvailable').setVisible(true);
									}
								}
							}
						}
					} else {
					  document.getElementById("fdAvailable").style.display = "none";
					}
				});

				//新建
				window.addDoc = function() {
					Com_OpenWindow('<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=add&docType=${JsParam.docType}" />');
				};
				
				//编辑
				window.edit = function(id) {
					if(id)
					 Com_OpenWindow('<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=edit&fdId=" />'+id);
				};
				
				window.settingDefaultSignature = function(fdId){
					var url = '<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=settingDefaultSignature"/>';
					dialog.confirm("${lfn:message('km-signature:signature.fdIsDefaultConfirm')}",function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"fdId":fdId},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};

				//批量置为无效
				window.invalidatedAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=invalidatedAll"/>';
					dialog.confirm('<bean:message key="page.invalid"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				
				//批量置为无效
				window.validatedAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=validatedAll"/>';
					dialog.confirm('您真的要启用所选记录吗？',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				//删除
				window.delDoc = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
					   });
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=deleteall"/>&categoryId=${JsParam.categoryId}',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
				//删除回调函数
				window.delCallback = function(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};

			});
		</script>	 
	</template:replace>
</template:include>
