<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.mportal.util.SysMportalImgUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar"  var-navwidth="95%" style="float:right;margin:30px 20px">
			<ui:button text="上传" onclick="upload();">
			</ui:button>
			<ui:button text="确定" onclick="submit()">
			</ui:button>
		</ui:toolbar>
		<link
			href="${LUI_ContextPath }/sys/mportal/sys_mportal_bgInfo/style/mbg.css"
			rel="stylesheet" type="text/css" />
		
		<script>
			seajs.use([ 'lui/topic' ], function(topic) {
				topic.subscribe('successReloadPage', function() {
					location.reload()
				});
			});
			function upload() {
				seajs
						.use(
								'lui/dialog',
								function(dialog) {
									dialog
											.iframe(
													'/sys/mportal/sys_mportal_bgInfo/sysMportalBgInfo_upload.jsp',
													'上传文件', function() {
													}, {
													});
								});
			}

			function submit() {
				var src = $('.selected .lui_mportal_bg').attr('data-src');
				$('input[name="value(bgUrl)"]').val(src);
				Com_Submit(document.sysAppConfigForm, 'update');
			}

			seajs.use([ 'lui/jquery' ], function($) {
				$(function() {
					$('.lui_mportal_bgcontainer a').on(
							'click',
							function(evt) {
								var $a = $(this);
								if ($a.length > 0) {
									$('.lui_mportal_bgcontainer a').removeClass(
											'selected');
									$a.addClass('selected');
								}
							});
				});
			});
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
	</template:replace>

	<template:replace name="content">
		
		<div class="lui_mportal_bgcontainer">
			<div>
				<div style="margin:6px;">
					<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
						<input name="value(bgUrl)" type="hidden" />
						<div >
							<span style="display:inline-block;position: relative;top:8px;">
								字体颜色：<input name="value(fontColor)" type="text" class="inputsgl" value="${color}" />
							</span>
						<input id="_fontColor"></input>
						</div>
						
					</html:form>
				</div>
				<a href="javascript:;"
					<c:if test="${bg =='/sys/mportal/mobile/img/bg.jpg' }">class="selected"</c:if>>
					<div class="lui_mportal_bg"
						style="background-image:url('${LUI_ContextPath }/sys/mportal/mobile/img/bg.jpg')" 
						data-src="/sys/mportal/mobile/img/bg.jpg">
						<div class="lui_mportal_bgLogo">
							<img src="${LUI_ContextPath}<%=SysMportalImgUtil.logo()%>" 
								alt="logo">
						</div>
						<div class="lui_mportal_bgInput">
						</div>
						<ul class="lui_mportal_bgMenu" style="color:${color}">
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-approval"></div>
								<div class="lui_mportal_bgMenuText">
									知识
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-address">
								</div>
								<div class="lui_mportal_bgMenuText">
									黄页
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-quiz">
								</div>
								<div class="lui_mportal_bgMenuText">
									提问
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-more"></div>
								<div class="lui_mportal_bgMenuText">
									更多
								</div>
							</li>
						</ul>
					</div> 
					<span class="lui_mportalbg_tag"></span> 
				</a>

				<c:forEach items="${paths }" var="path">
					<a href="javascript:;"
						<c:if test="${bg == path }">class="selected"</c:if>>
						<div class="lui_mportal_bg" 
						style="background-image:url('${LUI_ContextPath }${path }')"
						data-src="${path}"> 
							<div class="lui_mportal_bgLogo">
							<img src="${LUI_ContextPath}<%=SysMportalImgUtil.logo()%>" 
								alt="logo">
						</div>
						<div class="lui_mportal_bgInput">
						</div>
						<ul class="lui_mportal_bgMenu" style="color:${color}">
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-approval"></div>
								<div class="lui_mportal_bgMenuText">
									知识
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-address">
								</div>
								<div class="lui_mportal_bgMenuText">
									黄页
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-quiz">
								</div>
								<div class="lui_mportal_bgMenuText">
									提问
								</div>
							</li>
							<li>
								<div class="lui_mportal_bgMenuIcon mui mui-more"></div>
								<div class="lui_mportal_bgMenuText">
									更多
								</div>
							</li>
						</ul>
						</div><span class="lui_mportalbg_tag"></span>
					</a>
				</c:forEach>
			</div>
		</div>
		<script type="text/javascript">
			Com_IncludeFile("jquery.js");
			
		</script>
		
		<script src='${LUI_ContextPath}/sys/mportal/sys_mportal_bgInfo/js/spectrum.js'>
		</script>
		<link rel='stylesheet' href='${LUI_ContextPath}/sys/mportal/sys_mportal_bgInfo/js/spectrum.css' />
		<script>
			$("#_fontColor").spectrum({
			    showPaletteOnly: true,
			    showPalette:true,
			    color:  '${color}'?'${color}':'white',
			    palette: [
			        [ 'white','black', 'blanchedalmond',
			        'rgb(255, 128, 0);', 'hsv 100 70 50'],
			        ['red', 'yellow', 'green', 'blue', 'violet']
			    ],
			    change : function(color) {
			    	var _color = color.toHexString();
			    	$("[name='value(fontColor)']").val(_color);
					$(".lui_mportal_bgMenu").css("color", _color);
			    }
			});
		</script>
	</template:replace>
</template:include>