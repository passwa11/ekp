<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<link href="${LUI_ContextPath}/sys/xform/maindata/dialog/mydata/css/mydata.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" >
			var fdTemplateId, fdTemplateModelName, fdTemplateSubject, fdMainDataModelName, displayName;
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				var dialog_iframe = $("#dialog_iframe", parent.document);
				var _parents = dialog_iframe.parents("div");
				
				//选择模板
				window._select_main_data = function() {
					// 隐藏父窗口
					$(_parents[_parents.length - 1]).hide();
					
					dialog.dialogForNewFile({
						id: 'MyDataTypeDialog',
						url: '/sys/xform/maindata/dialog/mydata/category.jsp',
						modelName: 'com.landray.kmss.sys.xform.maindata.model.SysFormJdbcDataSetCategory',
						winTitle: '<bean:message bundle="sys-xform-maindata" key="sysFormMainData.select.category"/>',
						sourceType: 2,
						okBtnMsg: '<bean:message bundle="sys-xform-maindata" key="sysFormMainData.select.next"/>',
						callback: _select_callback
					});
				}
				
				// 选择模板后的回调
				//选择好模板后，这里取到模板数据，进一步去查主数据
				window._select_callback = function(data) {
					if(data && typeof data === 'object') {
						fdTemplateId = data.id;
						_set_path(fdTemplateId);
						_load_data();
						
						// 显示父窗口
						$(_parents[_parents.length - 1]).show();
					} else {
						// 关闭父窗口
						$dialog.hide();
					}
				}
				
				window.enterTrigleSelect = function(event) {
					if (event && event.keyCode == '13') {
						searchData();
					}
				}
				
				window.searchData = function() {
					if(!fdTemplateId) {
						dialog.alert('<bean:message bundle="sys-xform-maindata" key="sysFormMainData.select.category.null"/>');
						return false;
					}
					var keyword = $.trim($("input[name='keyword']").val());
					if(keyword.length > 0) {
						_load_data(keyword);
					}
				}
				
				// 获取数据
				window._load_data = function(keyword) {
					var listview = LUI('Mydata_listview');
					var url = "/sys/xform/maindata/sysFormMyData.do?method=getMydatas&pageno=1&rowsize=10&id=" + fdTemplateId;
					if(keyword) {
						url += "&keyword=" + encodeURI(keyword);
					}
					listview.source.setUrl(url);
					listview.source.get();
				}
				
				// 设置选中的模板路径
				window._set_path = function(id) {
					$.ajax({
						url: Com_Parameter.ContextPath + "sys/xform/maindata/sysFormMyData.do?method=getTemplateData",
						data: {'id': id},
						dataType: 'json',
						success: function(data) {
							$("#modelPath").text(data.path);
							fdTemplateModelName = data.templateModelName;
							fdTemplateSubject = data.templateSubject;
							fdMainDataModelName = data.mainDataModelName;
							displayName = data.displayName;
						}
					});
				}
				
				// 选中行
				window._rowClick = function(fdId) {
					var selectData;
					var datas = LUI("Mydata_listview").table.kvData;
					$.each(datas, function(i, n) {
						if(n.fdId == fdId) {
							selectData = n;
							return false;
						}
					});
	                
					// 设置选中行的背景颜色
					var _tr = $("tr[kmss_fdid='" + fdId + "']");
					_tr.siblings().removeClass("lui_listview_columntable_tr_current");
					_tr.addClass("lui_listview_columntable_tr_current");
					
					// 获取父窗口底部显示选中记录的DIV
					var _desc = $(window._parent_dialog_dom).find('[data-lui-mark="dialog.content.desc"]').css({"padding-top":"10px", "width":"450px"});
					// 底部显示已选中的记录
					_desc.html('<b><bean:message key="dialog.currentValue"/></b>' + selectData[displayName]);
					
					// 保存当前选中的记录
					selectData.fdTemplateId = fdTemplateId;
					selectData.fdTemplateModelName = fdTemplateModelName;
					selectData.fdTemplateSubject = fdTemplateSubject;
					selectData.fdMainDataModelName = fdMainDataModelName;
					selectData.displayName = displayName;
					$dialog.mydata = selectData;
				}
				
				$(function() {
					// 页面加载完后，自动弹出分类选择
					_select_main_data();
				});
			});
		</script>
	</template:replace>
	<template:replace name="content"> 
		<div id="MydataTypesDiv" class="lui_mydata_listview_container">
			<!-- 分类导航 -->
			<div class="lui_mydata_header">
				<span name="__select_main_data" onclick="_select_main_data();" style="display: none;"></span>
				<div class="lui_mydata_qsearch">
					<div class="lui_mydata_search_box">
						<input type="text" name="keyword" placeholder="${lfn:message('sys-xform-maindata:sysFormMainData.search.keyword') }" onkeyup="enterTrigleSelect(event);">
						<a href="javascript:;" onclick="searchData();">${lfn:message('button.search') }</a>
					</div>
				</div>
			</div>
			
			<!-- 当前路径 -->
			<div class="lui_mydata_location"><bean:message key="page.curPath"/><span id="modelPath"></span></div>
			
			<!-- 数据列表 -->
			<list:listview id="Mydata_listview">
				<ui:source type="AjaxJson">
					{url:''}
				</ui:source>
				<list:colTable onRowClick="_rowClick('!{fdId}');" >
					<list:col-serial headerStyle="width:50px"></list:col-serial>
					<list:col-auto></list:col-auto> 
				</list:colTable>
				<ui:event topic="list.loaded">  
				   seajs.use(['lui/jquery'], function($) {
						$("#Mydata_listview").find("table > tbody > tr").dblclick(function(event) {
							$(".button_ok", parent.document).click();
						});
					});
				</ui:event>
			</list:listview>
			<br>
			<list:paging></list:paging>
		</div>
	</template:replace>
</template:include>