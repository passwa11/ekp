<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
		     <list:cri-ref key="__keyword" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sysPortalPortlet.fdName') }">
			</list:cri-ref>
			<list:cri-criterion title="${lfn:message('sys-portal:sysPortalPortlet.module') }" key="__module" multi="true" expand="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson" >
							{url: "/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=getModules"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${lfn:message('sys-portal:sysPortalPortlet.fdFormat') }" key="__formate" multi="true" expand="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson" >
							{url: "/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=getFormates"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${ lfn:message('sys-portal:sysportal.anonymous')}" key="fdAnonymous" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
						  [{text:'${ lfn:message('sys-portal:sys_portal_anonymous')}', value:'1'},
						  {text:'${ lfn:message('sys-portal:sys_portal_general')}',value:'0'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdName" text="${lfn:message('sys-portal:sysPortalPortlet.fdName') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdModule" text="${lfn:message('sys-portal:sysPortalPortlet.fdModule') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			rowHref="/sys/portal/sys_portal_portlet/sysPortalPortlet.do?method=view&fdId=!{fdId}">
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdAnonymous,fdModule,fdDescription,fdFormat,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
	 		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	 			window.preview = function(id) {
	 				dialog.iframe('/resource/jsp/widget.jsp?portletId=' + id + '&example=1',
	 						"${lfn:message('sys-portal:sys.portal.preview')}", null, {
	 					width : 600,
	 					height : 480,
	 					/* 新增数据预览与样例预览按钮 @author 吴进 by 20191024 */
	 					buttons : [{
	 						name : "${lfn:message('sys-portal:sys.portal.preview.example')}",
	 						value : 1,
	 						focus : true,
	 						fn : function(value, _dialog) {	
	 							if (value == 1) {
	 								var buttonText = "${lfn:message('sys-portal:sys.portal.preview.data')}";
	 								var button = _dialog.content.buttons[0];
	 								var buttonContainer = $(button).find(".lui_dialog_buttons_container")[0];
	 								$(buttonContainer).find(".lui_toolbar_btn_def").eq(0).attr("title", buttonText);
	 								$(buttonContainer).find(".lui_widget_btn_txt").eq(0).text(buttonText);
	 								
	 								this.value = 2;
	 								
	 								var imagesrc = $(_dialog.frame[0]).find("iframe")[0].contentWindow.document.getElementById("imagesrc").value;
	 								if (imagesrc!=null) {
	 									var path = formatsrc(imagesrc.trim());
	 									if (endsWith(path)) {
	 									   var $iframeObj = $(_dialog.frame[0]).find("iframe").eq(0);
	 									   	//如果是图片，添加居中样式
	 									  /*   $iframeObj.load(function(){
	 									    	this.contentWindow.document.body.style["text-align"]="center";
	 									    }); */
	 									    $iframeObj.attr("src", "${LUI_ContextPath}"+"/sys/portal/sys_portal_portlet/no_data_example.jsp?imgPath=" + path);
	 									} else {
	 										$(_dialog.frame[0]).find("iframe").eq(0).attr("src", "${LUI_ContextPath}" + '/resource/jsp/widget.jsp?portletId=' + id + '&example=2');
	 									}
	 								}
	 							} else {
	 								var buttonText = "${lfn:message('sys-portal:sys.portal.preview.example')}";
	 								var button = _dialog.content.buttons[0];
	 								var buttonContainer = $(button).find(".lui_dialog_buttons_container")[0];
	 								$(buttonContainer).find(".lui_toolbar_btn_def").eq(0).attr("title", buttonText);
	 								$(buttonContainer).find(".lui_widget_btn_txt").eq(0).text(buttonText);
	 								
	 								this.value = 1;
	 								
	 								$(_dialog.frame[0]).find("iframe").eq(0).attr("src","${LUI_ContextPath}" + '/resource/jsp/widget.jsp?portletId=' + id + '&example=1');
	 							}
	 						}
	 					}]
	 				});
	 			}
	 		});
	 		function formatsrc(input) {
	 			var prefix = "{^", suffix = "$}";
	 			return input.replace(prefix, "").replace(suffix, "");
	 		}
	 		function endsWith(input) {
	 			var extension = input.substring(input.lastIndexOf('.') + 1);
	 			if ('png'==extension.trim() || 'jpg'==extension.trim() || 'PNG'==extension.trim() || 'JPG'==extension.trim()) {
	 				return true;
	 			} else {
	 				return false;
	 			}
	 		}
	 	</script>
	</template:replace>
</template:include>
