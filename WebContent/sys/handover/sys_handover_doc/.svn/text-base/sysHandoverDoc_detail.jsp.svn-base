<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content"> 
	   <%--筛选器--%>
		<div style="margin-top:10px">
			<list:criteria id="criteria">
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-handover:sysHandoverConfigMain.docSubject') }">
				</list:cri-ref>
			</list:criteria>
		</div>	   
	   <%--按钮栏--%>
		<div style="position:relative;padding:7px 25px 5px 0px;text-align: right">
			<kmss:authShow roles="ROLE_SYSHANDOVER_CREATE">
		      <ui:button style="width:60px" text="${ lfn:message('sys-handover:sysHandoverConfigLog.operation.confirm') }" onclick="select()" order="2" ></ui:button>
		   </kmss:authShow>
		</div>
		<%--数据显示--%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=detail&type=${JsParam.type}&fdFromId=${JsParam.fdFromId}&fdToId=${JsParam.fdToId}&moduleName=${JsParam.moduleName}&item=${JsParam.item}'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="!{url}"
				name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>
		<script type="text/javascript">
			var currentIds = new Array();
			// 判断数组中包含element元素 
			Array.prototype.contains = function(element) {
			  for (var i = 0; i < this.length; i++) {
			      if (this[i] == element) {
			          return true; 
			      } 
			  } 
			  return false; 
			};
			// 数组删除某一元素
			Array.prototype.remove = function(element) {
			    for (var i = 0; i < this.length; i++) {
				      if (this[i] == element) {
				    	  this.splice(i,1);
				    	  break;
				      } 
				  } 
			};
			
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				// 加载已选择的数据
				var selectedIds = parent.selectedIds;
				if(selectedIds && "undefined" != selectedIds && "null" != selectedIds) {
					var temp = selectedIds.split(",");
					for (var i = 0; i < temp.length; i++) {
						if(temp[i].length > 0)
							currentIds.push(temp[i]);
					}
				}
				
				//选取明细
				window.select = function() {
					if(currentIds.length > 200) {
						dialog.alert('<bean:message bundle="sys-handover" key="sysHandoverConfigMain.detail.maxNumInfo"/>');
						return false;
					}
					window.$dialog.hide(currentIds);
				};
				
				// 监听请求数据AJAX，当数据加载后，自动勾选已经选择好的数据
				$(document).ajaxComplete(function(event, request, settings) {
					if(settings.url.indexOf("sysHandoverConfigMain.do?method=detail") > -1) {
						$.each($("input[name='List_Selected']"), function(i, n) {
							if ($(n) && $(n).length > 0) {
								var _n = $(n)[0];
								// 绑定点事件
								$(_n).on('click', function(){
									var val = this.value;
									if(this.checked) {
										currentIds.push(val);
									} else {
										currentIds.remove(val);
									}
								});
								if(currentIds.contains($(_n).val())) {
									$(_n).attr("checked", "checked");
								}
							}
						});

						$("input[name=List_Tongle]").on("click", function() {
							if (this.checked) {
								$.each($("input[name='List_Selected']"), function(i, n) {
									if(!currentIds.contains(this.value)) {
										currentIds.push(this.value);
									}
								});
							} else {
								$.each($("input[name='List_Selected']"), function(i, n) {
									currentIds.remove(this.value);
								});
							}
						});

						$("input[name=List_Tongle]").attr("checked", "checked");
						$.each($("input[name='List_Selected']"), function(i, n) {
							if(!currentIds.contains(this.value)) {
								$("input[name=List_Tongle]").attr("checked", false);
								return;
							}
						});
					}
			});
		});
</script>
	</template:replace>
</template:include>