<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.util.HQLUtil" %>
<%@ page import="com.landray.kmss.util.HibernateUtil" %>
<%pageContext.setAttribute("sql",HQLUtil.buildLogicIN("kmSignatureMain.fdUsers.fdId ", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds())); %>
<%pageContext.setAttribute("available", "(kmSignatureMain.fdIsAvailable = " + HibernateUtil.toBooleanValueString(true) + " or kmSignatureMain.fdIsAvailable is null)");%>
<template:include ref="default.dialog">
	<%--标签页标题--%>
	<template:replace name="title"><c:out value="${lfn:message('km-signature:module.km.signature') }" /></template:replace>
	<%--左侧主内容--%>
	<template:replace name="content">
		<div class="lui_form_content_frame" style="padding-top: 10px;">
			<table class="tb_normal" width="95%">
				<tr id="basicInfo">
					<!-- 印章名称 -->
					<td class="td_normal_title" width="15%">
						<c:out value="${lfn:message('km-signature:signature.markname') }" />
					</td><td width="25%">
						<xform:select property="fdSignatureMainId" showStatus="edit" style="width:72%" onValueChange="cleanPassword();">
							<xform:beanDataSource serviceBean="kmSignatureMainService" selectBlock="fdId,fdMarkName" whereBlock="${available} and (${sql })" orderBy="" />
						</xform:select>
						<kmss:authShow roles="ROLE_SIGNATURE_ADD;ROLE_SIGNATURE_COMPANY;ROLE_SIGNATURE_ADMIN">
							&nbsp;
							<a class="com_btn_link" href='javascript:void(0);' onclick='addSig();'>
								<bean:message key="button.add"/>
							</a>
						</kmss:authShow>
					</td>
					<!-- 密码 -->
					<td class="td_normal_title" width="15%" >
					    <span name="spanPassword">
						<c:out value="${lfn:message('km-signature:signature.sigPassword') }" />
						</span>
					</td><td width="25%">
					    <span name="spanPassword">
						<input type="password" name="checkPassword" width="95%">
						</span>
					</td>
					<td width="20%">
					     <span name="spanPassword">
						<ui:button text="${lfn:message('km-signature:signature.sign') }" order="2" onclick="confirmSignature(1);">
						</ui:button>
						</span>
					</td>
				</tr>
				<!-- 印章展示 -->
				<tr>
					<td class="td_normal_title" width="15%">
						<c:out value="${lfn:message('km-signature:signature.markbody') }" />
					</td>
					<td colspan="4" width="85%">
					<div id="imgHtml"></div>
					</td>
				</tr>
			</table>
			<center style="padding-top:17px">
				<ui:button text="${lfn:message('button.ok') }" order="1" onclick="save();" >
				</ui:button>
				<ui:button text="${lfn:message('button.close') }" order="2" onclick="if(!confirmClose())return;Com_CloseWindow();" style="padding-left:5px">
				</ui:button>
			</center>
		</div>
	</template:replace>
</template:include>

<script type="text/javascript">
function cleanPassword(){
	var password = document.getElementsByName("checkPassword")[0];
	if(password){
		if(password.value != "" || password.value != null){
			password.value = "";
		}
	}
	confirmSignature();
}
function addSig(){
	seajs.use(['sys/ui/js/dialog'], function(dialog) {
		var url = Com_GetCurDnsHost()+Com_Parameter.ContextPath+"km/signature/km_signature_main/kmSignatureMain.do?method=add";
		 dialog.iframe(url,"${lfn:message('km-signature:signature.createSig') }",function(rtn){
    	    location.reload();
		  },{width:990,height:550});
		
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

function confirmSignature(flag){
	var fdSignatureMainId = document.getElementsByName("fdSignatureMainId")[0].value;
	var checkPassword = document.getElementsByName("checkPassword")[0].value;
	var checkUrl = "${KMSS_Parameter_ContextPath}km/signature/km_signature_main/kmSignatureMain.do?method=confirmSignature";
	$.ajax({
	     type:"post",
	     url:checkUrl,
	     data:{"fdMainId":fdSignatureMainId,"confirmPassword":checkPassword},
	     async:true,
	     success:function(data){
	    	 var $spanPassword = $("span[name='spanPassword']");
	    	 $spanPassword.show();
			 if ((data.isFreeSign ==true) && (flag!=1) ){
				$spanPassword.hide();
			 }
		     //debugger;
			//图片上传完拼装显示的html
			var imageDiv = $("#imgHtml");
				imageDiv.html("");
			if(data.flag == "1"){
				//if(data.isFreeSign ==true ||confirm("${lfn:message('km-signature:signature.warn6') }")){
					var html = '<img width="100" height="75" id="preview" src="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId='+data.attId+'"/>';
					if(imageDiv){
						imageDiv.html(html);
				    }
					if(fileId == ""){
						fileId = data.fdMainId;
					}else{
						if(fileId == data.fdMainId){
							return;
						}else{
							fileId = data.fdMainId;
						}
					}
			 //	}
			}else{
				if (flag){
					alert("${lfn:message('km-signature:signature.warn15') }");
				}
			}
		}
    });
}

function confirmClose(){
	var cls = confirm('<bean:message key="message.closeWindow"/>');
	return cls;
}
</script>
