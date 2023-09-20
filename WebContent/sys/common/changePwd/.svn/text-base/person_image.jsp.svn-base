<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<style type="text/css">
			body{ background-color: #f4fcfe; }
			#attachmentObject_personPic_content_div{ text-align: center; }
			.personInfoBox{}
			.crop_image_container{ padding: 15px 0!important; }
			.crop_image_container .person_crop_image_src{
				width: auto !important;
				height: auto !important;
				min-width: 300px;
				min-height: 300px;
				padding: 0 40px;
			}
			.personInfoBox .btnW{
				margin-top: 0;
				padding: 0;
				text-align: center;
			}
			.personInfoBox .btnW a {
				display: inline-block;
				cursor: pointer;
				margin: 0px 10px;
				padding: 10px 15px;
				min-width: 100px;
				text-align: center;
				line-height: 20px;
				font-size: 14px;
				color: #20b3ff;
				border-radius: 5px;
				border: 1px solid #20b3ff;
				background-color: transparent;
				transition: all .3s ease-in-out;
			}
			.personInfoBox .btnW a:hover{
				color: #fff;
				background-color: #20b3ff;
			}
			.personInfoBox .btnW a.btn_disable {
				color: #333;
				border-color: #ccd1d9;
				background-color: #ccd1d9;
				opacity: .65;
				cursor: not-allowed;
			}
			.personInfoBox .btnW .btn_cancel {
				color: #333;
				border-color: #b0b0b0;
				background-color: #b0b0b0;
				opacity: .65;
			}
			.personInfoBox .swfuploadbutton,.personInfoBox .uploadinfotext{float:left;}
			.personInfoBox .upload_btn_title{float:left;padding: 5px 10px 0px 0px;font-size:14px;}
			.crop_image_area{margin-left:30px;}
			.crop_image_m, .crop_image_box_m, .crop_image_s, .crop_image_box_s{margin-left: 30px !important;}
			.crop_cue_area{display:inline-block;margin-left: 30px;}
			.person_crop_image_src{
				width:300px !important;
				height:200px !important;
				max-width:500px;
				background:url(${KMSS_Parameter_ContextPath }sys/common/changePwd/images/bg.png);
				overflow:hidden;
			}
		</style>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/common/changePwd/css/person.css?v=1.0"/>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath }resource/js/jquery.js"></script>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step3"/>
	</template:replace>
	<template:replace name="body">
	  <div class="lui-changePwd-header">
	    <h1 class="lui-changePwd-header-title"><img src="${KMSS_Parameter_ContextPath }sys/common/changePwd/images/changePwd-header-heading.png" alt=""></h1>
	  </div>
		 <div class="lui-changePwd-content">
		 	<!-- 进度栏 Starts -->
		    <ul class="lui-changePwd-step-nav">
		      <li class="finish"><span class="num">1</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step1"/></li>
		      <li class="finish" class="active"><span class="num">2</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step2"/></li>
		      <li class="active"><span class="num">3</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step3"/></li>
		    </ul>
		    <!-- 进度栏 Ends -->
		    <div class="lui-changePwd-form-box personInfoBox">
		    	<%
			    	String path = request.getContextPath();
					String basePath = "";
					if(80 == request.getServerPort() || 443 == request.getServerPort())
						basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
					else
						basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		    		String SELECTED_EXT = ResourceUtil.getKmssConfigString("sys.person.image.plugin");
		    		pageContext.setAttribute("_IS_EKP_IMAGE","sysZonePersonImageBean".equals(SELECTED_EXT) ? true:false);
		    	%>
		    	<c:if test="${_IS_EKP_IMAGE eq true }">
		    		<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do">
					 <!-- 参数fdEditMode: add(新建文档时) edit(编辑编辑文档时) view(非新建编辑文档时进行上传裁剪)-->
				 		<ui:nonepanel layout="sys.ui.nonepanel.default" scroll="false">

				 		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_crop.jsp" charEncoding="UTF-8">
				          <c:param name="fdKey" value="personPic"/>
				          <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
				          <c:param name="fdModelId" value="<%=UserUtil.getKMSSUser().getPerson().getFdId() %>"/>
				          <c:param name="fdEditMode" value="view"/>
				        </c:import>
				       </ui:nonepanel>
					</html:form>
					<div class="btnW"><a class="btn_submit" id="btn_submit"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.btn2"/></a></div>
					<script type="text/javascript">
						function doReady(){
							$('#crop_personPic').parent().addClass('person_crop_image_src');
							var uploadBtnTxt = $('.personInfoBox .swfuploadbutton .swfuploadtext');
							var uploadDesc = $('.personInfoBox .uploadinfotext');
							var imgType = $('.personInfoBox .crop_image_types_title');
							uploadBtnTxt.html('<bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.uploadbtn"/>');
							if($.trim(imgType.html())){
								uploadDesc.html(imgType.html());
							}
							imgType.html("");
							if($('.personInfoBox .upload_btn_title').length==0){
								uploadDesc.parent().prepend('<div class="upload_btn_title"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.uploadtitle"/></div>');
							}

						}
						function onImageSubmit(){
							location.href = "<%=basePath%>";
						}

						seajs.use(['lui/topic','sys/attachment/Jcrop/js/imageCrop'], function(topic,imageCrop) {
							attachmentObject_personPic.on("cropSuccess",function(evt){
								$('#crop_personPic').parent().css({width:'300px',height:'200px'});

							});
							topic.subscribe("crop.load.ready",function(evt) {
								doReady(evt);
							});
							topic.subscribe("crop.button.show",function(evt) {
								if(evt.isShow){
									$('#btn_submit').hide();
								}else{
									$('#btn_submit').show();
								}
							});

						});
						$("#btn_submit").click(onImageSubmit);

					</script>
		    	</c:if>
		    	<c:if test="${_IS_EKP_IMAGE eq false }">
		    		<div class="personInfo-illustration">
		    			<p class="personInfo-illustration-heading"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.kk.txt"/>
		    			<a href="/third/im/kk/webparts/chgKk5HeadImg.jsp" target="_blank"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.kk.modify"/></a></p>
		    			<p id="_timeArea" class="personInfo-illustration-countdown"></p>
		    		</div>
		    		<script type="text/javascript">
		    		var time = 0;
					var intvalId =null;
					function timeInteval(){
						var _timeArea = document.getElementById("_timeArea");
						if(time==10){
							window.location.href="<%=basePath%>";
							return;
						}
						_timeArea.innerHTML = '<bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.kk.image"/>'.replace('{0}', "<span>"+(10 - time)+"</span>");
						time =time+1;
						intvalId=setTimeout("timeInteval()",1000);
					}
					Com_AddEventListener(window,"load",timeInteval);
		    		</script>
		    	</c:if>


		    </div>
		 </div>



	</template:replace>
</template:include>
