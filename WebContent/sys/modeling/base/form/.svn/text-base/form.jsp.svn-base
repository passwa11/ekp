<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appDesign.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appList.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css" />
		<script type="text/javascript">seajs.use(['theme!profile'])</script>
		<script type="text/javascript">seajs.use(['theme!iconfont'])</script>
		<script>
		Com_IncludeFile("view.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
		Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
		</script>
		<style>
			.app_add_noflow .lui_iconfont_nav_km_summary,.app_add_flow .lui_iconfont_nav_km_review:before{
				font-size: 34px;
   				color: #999999;
   				display: inline-block;
			    width: 28px;
			    height: 28px;
			    position: absolute;
			    top: 70px;
			    left: 97px;
			    cursor: pointer;
			}
			.app_add_noflow .lui_iconfont_nav_km_summary:before{
				content: "\e6f0";
			}
			.app_add_flow .lui_iconfont_nav_km_review:before{
				content: "\e6ee";
			}
			/* 一下样式若使用formRender渲染时要带入 */
			.nofloat{
				float:none
			}
			.create_icon{
				font-size: 34px;
			    color: #999999;
			    display: inline-block;
			    width: 28px;
			    height: 28px;
			    position: absolute;
			    top: 70px;
			    left: 97px;
			    cursor: pointer;
			}
			.flow_icon{
				display: inline-block;
			    width: 28px;
			    height: 28px;
			    position: absolute;
			    top: auto;
			    right:10px;
			    color:#CCCCCC
			}
			.has_flow_icon{
				background: url(${LUI_ContextPath}/sys/modeling/base/resources/images/form/flow@2x.png) no-repeat center;
				background: url(${LUI_ContextPath}/sys/modeling/base/resources/images/form/flow.png) no-repeat center \9;
				background-size: 28px;
			}
			.no_flow_icon{
				background: url(${LUI_ContextPath}/sys/modeling/base/resources/images/form/no-flow@2x.png) no-repeat center;
				background: url(${LUI_ContextPath}/sys/modeling/base/resources/images/form/no-flow.png) no-repeat center \9;
				background-size: 28px;
			}
			.hover{
				z-index:10;
				opacity:1;
			}
			.form_item_head{
				position: absolute;
    			right: 0;
			}
			.appMenu_main{
				height:100%
			}
			.appMenu_main_cover{
				height: 200px;
    			width: 224px;
    			right:auto
			}
			.appMenu_main_cover .modeling_app_edit{
				top:85px;
			}
			.modeling_app_btn{
				width:33%!important;
				margin-right:0!important;
			}
			.modeling_app_btn_bar{
				top: auto!important;
    			bottom: 20px!important;
			}
			.appMenu_main_title{
			    text-align: center;
			    position: absolute;
			    top: 135px;
			    width: 100%;
			}
			/*空页面*/
			.appMenu_main_icon{
			  	width: 52px;
			    height: 52px;
			    background: #FFFFFF;
			    box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.10);
			    border-radius: 8px;
			   display: inline-block;
			    text-align: center;
			    line-height: 52px;
			    /*border: 1px solid #DFE3E9;*/
			  }
			  .appMenu_main_icon i{
			  	font-size:30px;
			  	padding-left:0px!important;
			  }
			  .select_icon_btn{
			  	width: 90px;
			    display: inline-block;
			    height: 30px;
			    border: 1px solid #DDDDDD;
				border-radius: 2px;
				border-radius: 2px;
				bottom: 4px;
   				position: relative;
   				margin-left: 15px;
   				font-size: 14px;
   				text-align: center;
    			line-height: 30px;
			  }
			  .lui_custom_list_box_content_col_btn a{
			  	margin:0 10px;
			  }
			.appFormBoxText{
				display: inline-flex;
				display:-webkit-inline-flex;
				align-items: center;
				justify-content: space-between;
				height:30px;
				border: 1px solid #DDDDDD;
				background-color: #fff;
				border-radius: 2px;
				position: relative;
				padding:0 8px;
				float: left;
				margin-top: 10px;
			}
			.appFormBoxText>span{
				font-size: 12px;
			}
			.appFormBoxText input{
				font-size: 12px;
				color: #999999;
			}
			.appFormBoxText>.appFormBoxIconText{
				float: right;
				width: 20px;
				height: 20px;
				background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_sortInvertedOrder_desc.png) no-repeat center;
			}
			.appFormBoxListText{
				display: none;
				width: 100%;
				position: absolute;
				top: 100%;
				right:-1px;
				background-color: #fff;
				border: 1px solid #DDDDDD;
				box-shadow: 0 1px 3px 0 rgba(0,0,0,0.15);
				border-radius: 2px;
				z-index: 11;
				box-sizing: content-box;
				transform: translateY(1px);
			}
			.appFormBoxListText>ul>li{
				display: -webkit-flex;
				align-items: center;
				height: 30px;
				height:30px;
				font-size: 12px;
				padding:0 6px;
				background-color: #fff;
				cursor: pointer;
			}
			.appFormBoxListText>ul>li:hover{
				background-color: rgba(66,133,244,0.10);
			}


			.headViewTypeBoxText_form{
				display: inline-flex;
				display:-webkit-inline-flex;
				align-items: center;
				justify-content: space-between;
				height:29px;
				border: 1px solid #DDDDDD;
				background-color: #fff;
				border-radius: 2px;
				position: relative;
				padding:0 8px;
				float: left;
				margin-top: 10px;
			}
			.headViewTypeBoxText_form>span{
				font-size: 14px;
			}
			.headViewTypeBoxText_form input{
				font-size: 14px;
				color: #999999;
			}
			.headViewTypeBoxText_form>.headViewTypeIcon_form{
				float: left;
				width: 20px;
				height: 20px;
				background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_comm_gongGeView.svg) no-repeat center;
			}
			.headViewTypeBoxText_form>.headViewTypeBoxIconText_form{
				float: right;
				width: 20px;
				height: 20px;
				background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_commo_arrowDown.svg) no-repeat center;
			}
			.headViewTypeBoxListText_form{
				display: none;
				width: 100%;
				position: absolute;
				top: 100%;
				right:-1px;
				background-color: #fff;
				border: 1px solid #DDDDDD;
				box-shadow: 0 1px 3px 0 rgba(0,0,0,0.15);
				border-radius: 2px;
				z-index: 11;
				box-sizing: content-box;
				transform: translateY(1px);
			}
			.headViewTypeBoxListText_form>ul>li{
				display: -webkit-flex;
				align-items: center;
				height: 30px;
				height:30px;
				font-size: 14px;
				padding:0 6px;
				background-color: #fff;
				cursor: pointer;
			}
			.headViewTypeBoxListText_form>ul>li:hover{
				background-color: rgba(66,133,244,0.10);
			}
			.form_empty .form_empty_img div {
				width: 164px;
				height: 127px;
				margin: 0 auto;
				background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/empty.png) no-repeat center;
			}

			.form_empty .form_empty_img p {
				font-size: 28px;
				color: #333333;
				text-align: center;
				margin: 28px 0px;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<c:if test="${isEmpty == true }">
			<!-- 无表单 -->
			<div class="app_form lui_modeling_main"  style="margin:0;padding-top:0">
				<div class="app_form_empty">
					<div class="app_form_empty_img">
						<div></div>
						<p>${lfn:message('sys-modeling-base:modeling.form.Createforms')}</p>
					</div>
					<div>
						<center>
							<form>
								<table class="tb_simple model-view-panel-table" width=100%>
									<tr>
										<td class="td_normal_title" width=15% style="line-height: 30px;">
											${lfn:message('sys-modeling-base:modeling.model.form.name')}
										</td>
										<td width=85% class="model-view-panel-table-td">
											<input class="inputsgl" placeholder="${lfn:message('sys-modeling-base:modeling.form.FormName')}" style="width: 94%;padding-left: 8px;font-size: 12px" name="fdName" subject="${lfn:message('sys-modeling-base:modeling.model.form.name')}" type="text" validate="required maxLength(100)" /><span class="txtstrong">*</span>
										</td>
									</tr>
									<tr>
										<td class="td_normal_title" width=15% style="line-height: 54px;">
											${lfn:message('sys-modeling-base:modeling.form.FormIcon')}
										</td>
										<td width=85% class="model-view-panel-table-td">
											<div class="appMenu_main_icon" style="border: 1px solid #DFE3E9;"><i class="iconfont_nav"></i></div>
											<span class="txtstrong">*</span>
											<a href="javascript:void(0);" class="select_icon_btn" onclick="selectIcon();">
												<!-- <i class="iconfont_nav" style="color:#999;font-size:40px;"></i> -->
												${lfn:message('sys-modeling-base:modeling.form.ChangeIcon')}
											</a>
											<input name="fdIcon" type="hidden" value="iconfont_nav"/>
										</td>
									</tr> 
								</table>
								<div class="" >
									<center>
										  <div class="lui_custom_list_box_content_col_btn"  style="text-align: center;width: 100%">
											<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="submitForm('1');">${lfn:message('sys-modeling-base:modeling.form.FlowlessForm')}</a>
											<a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="submitForm('2');">${lfn:message('sys-modeling-base:modeling.form.FlowForm')}</a>
											<a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="importForm()">${lfn:message('sys-modeling-base:modeling.form.ImportForm')}</a>
										  </div>
									</center>
								</div>
								<input type="hidden" name="fdAppId" value="${JsParam.fdAppId}">
								<input type="hidden" name="type" value="">
							</form>
						</center>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${isEmpty == false }">
			<!-- 有表单 -->
			<div class="app_form lui_modeling_main"  style="margin:0;padding-top:0">
				<div class="app_form_head lui_modeling_main_head" style="z-index:200">
					<div class="app_form_head_searchWrap" style="float: left ;margin:0 0 0 16px;">
						<i class="lui_profile_listview_icon lui_icon_s_icon_search"  style="cursor: pointer" onclick="searchForm_icon(this)"></i>
						<input type="text" class="lui_profile_search_input" placeholder="${lfn:message('sys-modeling-base:modeling.model.search')}" onkeyup='searchForm(event,this);'>
					</div>
					<div style="float:left;margin:0 0 0 5px;">
						<div class="appFormBoxText">
							<i class="appFormBoxIconText"></i>
							<span>${lfn:message('sys-modeling-base:modelingApp.lication.form.order.desc')}</span>
							<input type="hidden" value="desc">
							<div class="appFormBoxListText" style="display: none;">
								<ul>
									<li data-select="desc">${lfn:message('sys-modeling-base:modelingApp.lication.form.order.desc')}</li>
									<li data-select="asc">${lfn:message('sys-modeling-base:modelingApp.lication.form.order.asc')}</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="app_form_head_import">
						<ui:button onclick="importForm()" text="${lfn:message('sys-modeling-base:modeling.form.ImportForm')}"/>
					</div>
					<div style="display: none" class="listTableButton">
						<div class="app_form_head_import">
							<ui:button onclick="createForm('1')" text="${lfn:message('sys-modeling-base:modeling.form.AddFlowlessForm')}"/>
						</div>
						<div class="app_form_head_import">
							<ui:button onclick="createForm('2')" text="${lfn:message('sys-modeling-base:modeling.form.AddFlowForm')}"/>
						</div>
					</div>
					<div id="head_viewType_form" class="head_import">
						<div class="headViewTypeBoxText_form">
							<i class="headViewTypeIcon_form"></i>
							<span>${lfn:message('sys-modeling-base:listview.card')}</span>
							<input type="hidden" value="1">
							<i class="headViewTypeBoxIconText_form"></i>
							<div class="headViewTypeBoxListText_form" style="display: none;">
								<ul>
									<li data-select="1"><i class="headViewTypeBoxIconTextggView_form"></i>${lfn:message('sys-modeling-base:listview.card')}</li>
									<li data-select="2"><i class="headViewTypeBoxIconTextListView_form"></i>${lfn:message('sys-modeling-base:modelingAppSpace.dataList')}</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="app_form_body lui_modeling_main_content">
					<!-- 功能区 start -->
					<div class="form_operation lui_profile_listview_content ">
						<div id="formList" data-lui-type="sys/modeling/base/form/js/formListDataView!FormListDataView" style="display:none;">
							<ui:source type="AjaxJson">
								{
								url : "/sys/modeling/base/modelingAppModel.do?method=findFormByAppId&fdAppId=${JsParam.fdAppId}&c.like.fdName=!{fdFormName}&c.fdOrder=!{fdOrder}&c.viewTypeForm=!{viewTypeForm}"
								}
							</ui:source>
							<ui:render type="Javascript">
								<c:import url="/sys/modeling/base/form/formRender.jsp" charEncoding="UTF-8"></c:import>
							</ui:render>
						</div>
					</div>
					<!-- 功能区 end -->
					<!-- <HR /> -->
<%--					<div class="form_custom_list lui_profile_listview_content">--%>
<%--						<div id="formList" data-lui-type="sys/modeling/base/form/js/formListDataView!FormListDataView" style="display:none;">--%>
<%--							<ui:source type="AjaxJson">--%>
<%--								{--%>
<%--								url : "/sys/modeling/base/modelingAppModel.do?method=findFormByAppId&fdAppId=${JsParam.fdAppId}&c.like.fdName=!{fdFormName}"--%>
<%--								}--%>
<%--							</ui:source>--%>
<%--							<ui:render type="Javascript">--%>
<%--								<c:import url="/sys/modeling/base/form/js/formRender.js" charEncoding="UTF-8"></c:import>--%>
<%--							</ui:render>--%>
<%--						</div>--%>
<%--					</div>--%>
				</div>
			</div>
		</c:if>
		<script>
			var appInfos = {};
			getAllForms();
			var __validation = $KMSSValidation();

			function searchForm_icon() {
				var val = encodeURIComponent($(".lui_profile_search_input").val());
				var orderType = $(".appFormBoxText input").val();
				var viewTypeForm = $(".headViewTypeBoxText_form input").val();
				LUI("formList").source.resolveUrl({"fdFormName" : val , "fdOrder" : orderType , "viewTypeForm" : viewTypeForm});
				LUI("formList").doRefresh();
			}
			function searchForm(event , dom){
				var val = encodeURIComponent($(dom).val());
				var orderType = $(".appFormBoxText input").val();
				var viewTypeForm = $(".headViewTypeBoxText_form input").val();
				if ((event && event.keyCode == '13') || val === "") {
					LUI("formList").source.resolveUrl({"fdFormName" : val , "fdOrder" : orderType , "viewTypeForm" : viewTypeForm});
					LUI("formList").doRefresh();
				}
			}
			function searchForm_order(orderType){
				var val = encodeURIComponent($(".lui_profile_search_input").val());
				var viewTypeForm = $(".headViewTypeBoxText_form input").val();
				LUI("formList").source.resolveUrl({"fdFormName" : val , "fdOrder" : orderType , "viewTypeForm" : viewTypeForm});
				LUI("formList").doRefresh();
			}
			function switchViewType_form(viewTypeForm){
				var val = encodeURIComponent($(".lui_profile_search_input").val());
				var orderType = $(".appFormBoxText input").val();
				LUI("formList").source.resolveUrl({"fdFormName" : val , "fdOrder" : orderType , "viewTypeForm" : viewTypeForm});
				LUI("formList").doRefresh();
			}
			function getAllForms() {
				var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=findFormNumAndAppIsSelfBuild&fdAppId=${JsParam.fdAppId}";
				$.ajax({
					url:url,
					async:false,
					type:'GET',
					dataType:'json',
					success:function(result){
						if (result){
							appInfos =result ;
							if (appInfos.selfBuild == false){
								$(".app_form_head_import").empty();
							}
						}else{
							dialog.failure(result.errmsg);
						}
					}
				});
				return appInfos;
			}
			var submitFlag = false;
			seajs.use(['lui/dialog'],function(dialog){
				// 新建流程表单
				window.createForm = function(type){
					getAllForms();
					console.log("appInfos",appInfos);
				    if (appInfos.FormNums >= appInfos.maxNum){
				    	dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.up.to.30.forms')}");
					}else {
					// type : 1(无流程)|2(有流程)
					var title = type === "1"?"${lfn:message('sys-modeling-base:modeling.form.AddFlowlessForm')}":"${lfn:message('sys-modeling-base:modeling.form.AddFlowForm')}";
					var url = "/sys/modeling/base/form/createForm_dialog.jsp?fdAppId=${JsParam.fdAppId}&type=" + type;
					dialog.iframe(url,title,null,{width:550,height:300,params:{formWindow:this}});
					}

				}
				//导入表单
				window.importForm = function(){
					getAllForms();
						if (appInfos.FormNums >= appInfos.maxNum){
						dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.up.to.30.forms')}");
					}else {
					var url= '/sys/modeling/datainit/import/upload.jsp?fdAppId=${JsParam.fdAppId}&type=form';
					dialog.iframe(url, '${lfn:message("button.import")}', function(data){
						if("${isEmpty}" == "true"){
							window.location.reload();
						}else{
							LUI("formList").doRefresh();
						}
					},{
						width : 540+5,
						height : 232
					});
					}
				}


				//选择图标
				window.selectIcon = function(){
	 				var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
	 				dialog.iframe(url,"${lfn:message('sys-modeling-base:modeling.app.selectIcon')}",changeIcon,{width : 750,height : 500})
	 			}
				
				window.changeIcon = function(className){
	 				if(className){
	 					$("i.iconfont_nav").removeClass().addClass(className);
		 				$("input[name='fdIcon']").val(className.split(" ")[1]);
	 				}
	 			}
				
				window.submitForm = function(type){
					
					if(submitFlag){
                		return;
                	}
					window.submit_load = dialog.loading();
					if(!__validation.validate()){
						window.submit_load.hide();
						return;
					}
					$("[name='type']").val(type);
		 			// 通过ajax的方式提交表单数据
		 			var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=saveBaseInfoByAjax";
		 			submitFlag=true;
		 			$.ajax({
		 				url : url,
		 				type : "post",
		 				data : $('form').serialize(),
		 				success : function(rtn){
		 					submitFlag=false;
		 					if(rtn.status === '00'){
		 						//刷新当前窗口
			 					var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=frame&fdId=" + rtn.data.id;
			 					 top.open(url,"_self");
		 					}else{
		 						dialog.failure(rtn.errmsg);
		 					}
		 				}
		 			});
		 		}
			});
			//159896 输入名称后点击回车报错。
			document.onkeydown = function (ev) {
				ev = ev || event;
				if(ev.keyCode === 13){
					return false;
				}
			}
			$(document).ready(function() {
				//排序下拉选项
				$('.appFormBoxText').click(function(e){
					e?e.stopPropagation():event.cancelBubble = true;
					if($(this).find('.appFormBoxListText').css('display') == 'none'){
						$('.appFormBoxListText').hide();
						$(this).find('.appFormBoxListText').show();
					}else{
						$(this).find('.appFormBoxListText').hide();
					}
				});
				$('.appFormBoxListText>ul>li').click(function(e){
					e?e.stopPropagation():event.cancelBubble = true;
					$(this).parents('.appFormBoxText').children('span').text($(this).text());
					var dataSelect = $(this).attr("data-select");
					$(this).parents('.appFormBoxText').children('input').val(dataSelect);
					$(this).parents('.appFormBoxText').find('.appFormBoxListText').hide();
					var url = "";
					if (dataSelect == "desc"){
						url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_sortInvertedOrder_desc.png";
					}else{
						url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_sortInvertedOrder_asc.png";
					}
					var backgroundValue="url('"+url+"') no-repeat center"
					$('.appFormBoxText .appFormBoxIconText').css("background",backgroundValue);
					searchForm_order(dataSelect);
				});
				// 下拉列表点击外部或者按下ESC后列表隐藏
				$(document).click(function(){
					$('.appFormBoxListText').hide();
					$(this).find('.watermark_configuration_list_pop').hide();
					$(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
				}).keyup(function(e){
					var key =  e.which || e.keyCode;;
					if(key == 27){
						$('.appFormBoxListText').hide();
						$(this).find('.watermark_configuration_list_pop').hide();
					}
				});

				//视图类型下拉
				$('.headViewTypeBoxText_form').click(function(e){
					e?e.stopPropagation():event.cancelBubble = true;
					if($(this).find('.headViewTypeBoxListText_form').css('display') == 'none'){
						$('.headViewTypeBoxListText_form').hide();
						$(this).find('.headViewTypeBoxListText_form').show();
					}else{
						$(this).find('.headViewTypeBoxListText_form').hide();
					}
				});
				$('.headViewTypeBoxListText_form>ul>li').click(function(e){
					e?e.stopPropagation():event.cancelBubble = true;
					$(this).parents('.headViewTypeBoxText_form').children('span').text($(this).text());
					var dataSelect = $(this).attr("data-select");
					$(this).parents('.headViewTypeBoxText_form').children('input').val(dataSelect);
					//改变不同视图的样式
					var url = "";
					if (dataSelect == "1"){
						url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_comm_gongGeView.svg";
					}else{
						url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_comm_menuList.svg";
					}
					var backgroundValue="url('"+url+"') no-repeat center"
					$('.headViewTypeBoxText_form .headViewTypeIcon_form').css("background",backgroundValue);
					$(this).parents('.headViewTypeBoxText_form').find('.headViewTypeBoxListText_form').hide();
					switchViewType_form(dataSelect);
				});
				// 下拉列表点击外部或者按下ESC后列表隐藏
				$(document).click(function(){
					$('.headViewTypeBoxListText_form').hide();
					$(this).find('.watermark_configuration_list_pop').hide();
					$(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
				}).keyup(function(e){
					var key =  e.which || e.keyCode;;
					if(key == 27){
						$('.headViewTypeBoxListText_form').hide();
						$(this).find('.watermark_configuration_list_pop').hide();
					}
				});
			});
			
		</script>
	</template:replace>
</template:include>