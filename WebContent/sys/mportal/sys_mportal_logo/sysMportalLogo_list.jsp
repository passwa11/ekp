<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" style="float:right;margin:30px 20px"
			var-navwidth="95%">
			<ui:button text="${lfn:message('common.fileUpLoad.upload')}" onclick="upload();"/>
			<ui:button text="${lfn:message('button.delete') }" onclick="deleteall()"/>
			<ui:button text="${lfn:message('button.refresh') }" onclick="refresh()"/>
		</ui:toolbar>
		<link
			href="${LUI_ContextPath }/sys/mportal/sys_mportal_logo/style/mlogo.css"
			rel="stylesheet" type="text/css" />
		<script>
			function upload() {
				seajs
						.use(
								'lui/dialog',
								function(dialog) {
									dialog
											.iframe(
													'/sys/mportal/sys_mportal_logo/sysMportalLogo_upload.jsp',
													'${lfn:message('common.fileUpLoad.title')}', function() {
														// 做手动关闭和上传完毕自动关闭的区分
														if(typeof arguments[0] == 'undefined')
															location.reload();
													}, {
													});
								});
			}
			function refresh() {
				location.reload();
			}
			function deleteall() {
				seajs.use(["lui/dialog",'lui/util/env','lang!'], function(dialog,env,lang) {
					var srcs = ""
					var values = [];
					var src = $('.selected img').each(function() {
							var node = $(this);
							srcs += node.data("src") + ";";
							values.push(node.data("src"));
					});
					if(!srcs) {
						dialog.alert("${lfn:message('page.noSelect')}");
					} else {
						$.ajax({
							url : env.fn.formatUrl("/sys/mportal/sys_mportal_logo/sysMportalLogo.do?method=validateLogo"),
							data : $.param({"selectSrc":values},true),
							type : 'post',
							dataType : "json",
							success : function(data) {
                                  if(data.flag){
                                	  dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag) {
              								if(flag) {
              									$('input[name="srcs"]').val(srcs);
              									Com_Submit(document.sysUiLogoForm, 'delete');
              								}
              							});
                                  }else{
                                	  //被引用弹框
                                	  dialog.iframe("/sys/mportal/sys_mportal_logo/sysMportalLogo_quote_dialog.jsp", 
                                			  "${lfn:message('sys-ui:ui.dialog.operation.title')}", null, {
												width : 400,
												height : 250
											});
                                  }
							}
						});
					}
				});
			}
		
			seajs.use([ 'lui/jquery' ], function($) {
				$(function() {
					$('.lui_mportal_container').on(
							'click',
							function(evt) {
								var $target = $(evt.target);
								var $a = $target.is('a') ? $target : $target
										.parent('a');
								if ($a.length > 0) {
									if($a.find("img").data("src") == "/resource/images/logo.png") {
										return;
									}
									if($a.hasClass("selected")) {
										$a.removeClass("selected")
									} else {
										$a.addClass('selected');
									}
								}
							});
				});
			});
		</script>

	</template:replace>

	<template:replace name="content">
		<html:form action="/sys/mportal/sys_mportal_logo/sysMportalLogo.do">
			<input name="srcs" type="hidden" />
		</html:form>
		<div class="lui_mportal_container">
			<div>
				<a ><img 
					class="lui_mportal_logo"
					src="${LUI_ContextPath }/resource/images/logo.png" 
					data-src="/resource/images/logo.png"> <span
					class="lui_mportal_tag"></span> </a>

				<c:forEach items="${paths }" var="path">
					<a href="javascript:;"
						<c:if test="${logo == path }">class="selected"</c:if>><img
						class="lui_mportal_logo" src="${LUI_ContextPath }${path }"
						data-src="${path}"> <span class="lui_mportal_tag"></span> </a>
				</c:forEach>
			</div>
		</div>
	</template:replace>
</template:include>