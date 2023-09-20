<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.dialog">
	<%--标签页标题--%>
	<template:replace name="title"><c:out value="${lfn:message('km-signature:module.km.signature') }" /></template:replace>
	<%--左侧主内容--%>
	<template:replace name="content">
		<div class="lui_form_content_frame" style="padding-top: 10px;">
			<div class="sig_normal" width="95%">
				<div id="basicInfo" class="basicInfo">
					<!-- 印章名称 -->
					<div class="title" width="15%">
						<c:out value="${lfn:message('km-signature:signature.markname') }" />
					</div>
					<div width="25%">
						<xform:select property="fdSignatureMainId" showStatus="edit" style="width:100%" onValueChange="cleanPassword();">
							<xform:beanDataSource serviceBean="kmSignatureMainService" selectBlock="fdId,fdMarkName" whereBlock="" orderBy="" />
						</xform:select>
					</div>
					<div>
						<kmss:authShow roles="ROLE_SIGNATURE_ADD;ROLE_SIGNATURE_COMPANY;ROLE_SIGNATURE_ADMIN">
							<ui:button text="${lfn:message('button.add') }" order="2" onclick="addSig();">
							</ui:button>
						</kmss:authShow>
					</div>
				</div>
				<!-- 密码 -->
				<div class="sigPassword">
					<div class="title" width="15%">
						<c:out value="${lfn:message('km-signature:signature.sigPassword') }" />
					</div>
					<div width="25%">
						<input type="password" name="checkPassword" width="95%">
					</div>
					<div width="20%">
						<ui:button text="${lfn:message('km-signature:signature.sign') }" order="2" onclick="confirmSignature();">
						</ui:button>
					</div>
				</div>
				<!-- 印章展示 -->
				<div class="sigMarkbody">
					<div class="title" width="15%">
						<c:out value="${lfn:message('km-signature:signature.markbody') }" />
					</div>
					<div colspan="4" width="85%">
						<div id="imgHtml"></div>
					</div>
				</tr>
			</div>
			<div class="ding_dialog_buttons_container" style="text-align:right;padding-top:17px;padding-right: 15px;">
				<ui:button styleClass="lui_toolbar_btn_gray"  text="${lfn:message('button.close') }" order="2" onclick="if(!confirmClose())return;Com_CloseWindow();" style="padding-left:5px">
				</ui:button>
				<ui:button styleClass="lui_toolbar_btn_def"  text="${lfn:message('button.ok') }" order="1" onclick="save();" >
				</ui:button>
			</div>
		</div>
	</template:replace>
</template:include>

<script type="text/javascript">
Com_IncludeFile("dingSignature.css", Com_Parameter.ContextPath + "km/signature/resource/css/","css",true);
function cleanPassword(){
	var password = document.getElementsByName("checkPassword")[0];
	if(password){
		if(password.value != "" || password.value != null){
			password.value = "";
		}
	}
}

function addSig(){
	seajs.use(['sys/ui/js/dialog'], function(dialog) {
		var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+"km/signature/km_signature_main/kmSignatureMain.do?method=add";
		 dialog.iframe(url,"${lfn:message('km-signature:signature.createSig') }",function(rtn){
    	    location.reload();
		  },{width:900,height:550}); 
		
	});
}

function save(){
	var fdSignatureMainId = document.getElementsByName("fdSignatureMainId")[0].value;
	var checkPassword = document.getElementsByName("checkPassword")[0].value;
	var flag = false;
	if(flag){
		return;
	}
	var saveUrl = "${KMSS_Parameter_ContextPath}km/signature/km_signature_main/kmSignatureMain.do?method=confirmSignature";
	$.ajax({
	     type:"post",
	     url:saveUrl,
	     data:{"fdMainId":fdSignatureMainId,"confirmPassword":checkPassword},
	     async:false,
	     success:function(data){
			if (data.flag == '1') {
				$dialog.hide(data);
			}else{
				alert("${lfn:message('km-signature:signature.warn15') }");
			}
		}
    });
}
//该值用来判断是否重复上传同一份签章图片，重复则不提示"是否确认替换签章图片？"
var fileId = "";

function confirmSignature(){
	var fdSignatureMainId = document.getElementsByName("fdSignatureMainId")[0].value;
	var checkPassword = document.getElementsByName("checkPassword")[0].value;
	var checkUrl = "${KMSS_Parameter_ContextPath}km/signature/km_signature_main/kmSignatureMain.do?method=confirmSignature";
	$.ajax({
	     type:"post",
	     url:checkUrl,
	     data:{"fdMainId":fdSignatureMainId,"confirmPassword":checkPassword},
	     async:true,
	     success:function(data){
		     //debugger;
			//图片上传完拼装显示的html
			if(data.flag == "1"){
				if(fileId == ""){
					fileId = data.fdMainId;
				}else{
					if(fileId == data.fdMainId){
						return;
					}else{
						fileId = data.fdMainId;
					}
				}
				var imageDiv = $("#imgHtml");
				var html = '<img width="100" height="75" id="preview" src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId='+data.attId+'"/>';
				if(imageDiv.length > 0){
					if(imageDiv[0].innerHTML==""){
						imageDiv.html(html);
					}else{
						if(confirm("${lfn:message('km-signature:signature.warn6') }")){
							imageDiv.html(html);
						}
					}
			    }
			}else{
				alert("${lfn:message('km-signature:signature.warn15') }");
			}
		}
    });
}

function confirmClose(){
	var cls = confirm('<bean:message key="message.closeWindow"/>');
	return cls;
}
</script>
