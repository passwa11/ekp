<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}" rel="stylesheet">
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/modeling/base/mobile/design/css/template.css?s_cache=${LUI_Cache}"/>
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
		</script>

		<div class="modelAppSpaceContainerSettingPop">
			<div class="modelAppSpaceContainerSettingPopContent tmpl">
				<div class="modelAppSpacePopContentTemplateBox">
					<div class="modelAppSpacePopContentTemplateBoxLeft">
						<ul>
							<li class="modelAppSpacePopContentTempl_c active" data-type="block"></li>
							<li class="modelAppSpacePopContentTempl_01" data-type="default"></li>
<%--							<li class="modelAppSpacePopContentTempl_02" data-type="list"></li>--%>
							<li class="modelAppSpacePopContentTempl_03" data-type="mportal"></li>
							<li class="modelAppSpacePopContentTempl_04" data-type="mportalList"></li>
						</ul>
					</div>
					<div class="modelAppSpacePopCOntentTemplateRightBox">
						<div class="modelAppSpacePopCOntentTemplateBoxRight">
							<div class="modelAppSpacePopCOntentTemplateBoxRightTitle">
								<strong>${lfn:message('sys-modeling-base:module.mobile.indexTmp.empty.name')}</strong>
								<span>${lfn:message('sys-modeling-base:module.mobile.indexTmp.empty.desc')}</span>
							</div>
							<div class="modelAppSpacePopCOntentTemplateBoxRightC">
								<img src="design/images/tmpl_c.png">
							</div>
						</div>
						<div class="modelAppSpacePopCOntentTemplateBoxRight">
							<div class="modelAppSpacePopCOntentTemplateBoxRightTitle">
								<strong>${lfn:message('sys-modeling-base:module.mobile.indexTmp.default.name')}</strong>
								<span>${lfn:message('sys-modeling-base:module.mobile.indexTmp.default.desc')}</span>
							</div>
							<div class="modelAppSpacePopCOntentTemplateBoxRightC">
								<img src="design/images/tmpl_01.png">
							</div>
						</div>
<%--						<div class="modelAppSpacePopCOntentTemplateBoxRight">--%>
<%--							<div class="modelAppSpacePopCOntentTemplateBoxRightTitle">--%>
<%--								<strong>${lfn:message('sys-modeling-base:module.mobile.indexTmp.list.name')}</strong>--%>
<%--								<span>${lfn:message('sys-modeling-base:module.mobile.indexTmp.list.desc')}</span>--%>
<%--							</div>--%>
<%--							<div class="modelAppSpacePopCOntentTemplateBoxRightC">--%>
<%--								<img src="design/images/tmpl_02.png">--%>
<%--							</div>--%>
<%--						</div>--%>
						<div class="modelAppSpacePopCOntentTemplateBoxRight">
							<div class="modelAppSpacePopCOntentTemplateBoxRightTitle">
								<strong>${lfn:message('sys-modeling-base:module.mobile.indexTmp.mportal.name')}</strong>
								<span>${lfn:message('sys-modeling-base:module.mobile.indexTmp.mportal.desc')}</span>
							</div>
							<div class="modelAppSpacePopCOntentTemplateBoxRightC">
								<img src="design/images/tmpl_03.png">
							</div>
						</div>
						<div class="modelAppSpacePopCOntentTemplateBoxRight">
							<div class="modelAppSpacePopCOntentTemplateBoxRightTitle">
								<strong>${lfn:message('sys-modeling-base:module.mobile.indexTmp.mportalList.name')}</strong>
								<span>${lfn:message('sys-modeling-base:module.mobile.indexTmp.mportalList.desc')}</span>
							</div>
							<div class="modelAppSpacePopCOntentTemplateBoxRightC">
								<img src="design/images/tmpl_04.png">
							</div>
						</div>
					</div>


				</div>
			</div>
<%--			<div class="modelAppSpaceContainerSettingPopFooter">--%>
<%--				<div class="modelAppSpaceContainerSettingPopBtnCancel" onclick="cancle()">--%>
<%--					取消--%>
<%--				</div>--%>
<%--				<div class="modelAppSpaceContainerSettingPopBtnConfirm" onclick="ok();">--%>
<%--					确定--%>
<%--				</div>--%>
<%--			</div>--%>
			<div class="toolbar-bottom">
				<ui:button text="${ lfn:message('button.ok')}" onclick="ok();" order="1"/>
				<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="cancle();" order="2"/>
			</div>
		</div>
		<script type="text/javascript">

			var interval = setInterval(beginInit, 50);

			function beginInit(){
				if(!window['$dialog'])
					return;
				clearInterval(interval);
				window.init();
			}
			// // 计算布局宽度
			// function calcWidth(){
			// 	var docWidth = document.body.scrollWidth;
			// 	var docHeight = document.body.scrollHeight;
			// 	//设置最外层容器高度
			// 	$(".modelAppSpaceBox").css("height", docHeight);
			// 	$(".modelAppSpaceMask").css("width",docWidth).css("height",docHeight);
			// }
			// calcWidth();

			// 关闭弹窗
			function closeModelAppSpacePop(){
				$(".modelAppSpaceMask").hide();
				$(".modelAppSpaceContainerSettingPop").hide();
			}


			seajs.use(['lui/jquery'],function ($){
				$(".modelAppSpacePopContentTemplateBoxLeft>ul>li").click(function(){
					if(!$(this).hasClass("active")){
						$(this).addClass("active").siblings().removeClass("active")
					}
					var idx = $(this).index();
					curTmpValue = $(this).data("type");
					$(".modelAppSpacePopCOntentTemplateBoxRight").eq(idx).show().siblings().hide()
				})
			})


			function cancle(){
				$dialog.hide('cancle');
			}

			function init(){

			}
			var curTmpValue ='block';
			function ok(){
				$dialog.hide(curTmpValue);
			}

		</script>
	</template:replace>
</template:include>
