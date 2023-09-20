<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/sunbor.tld" prefix="sunbor"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.Map"%>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppConfig" %>
<%
	if(request.getAttribute("LUI_ContextPath")==null){
		String LUI_ContextPath = request.getContextPath();
		request.setAttribute("LUI_ContextPath", LUI_ContextPath);
		request.setAttribute("LUI_CurrentTheme",SysUiPluginUtil.getThemeById("default"));
		request.setAttribute("MUI_Cache",ResourceCacheFilter.mobileCache);
		request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
		request.setAttribute("KMSS_Parameter_Style", "default");
		request.setAttribute("KMSS_Parameter_ContextPath", LUI_ContextPath+"/");
		request.setAttribute("KMSS_Parameter_ResPath", LUI_ContextPath+"/resource/");
		request.setAttribute("KMSS_Parameter_StylePath", LUI_ContextPath+"/resource/style/default/");
		request.setAttribute("KMSS_Parameter_CurrentUserId", UserUtil.getKMSSUser(request).getUserId());
		request.setAttribute("KMSS_Parameter_Lang", UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-'));
	}

	BaseAppConfig esaConfig =BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.esa.model.EsaConfig");
	Map dataMap = esaConfig==null?null:esaConfig.getDataMap();
	boolean esaEnable = false;
	String ESASerSystemSq = "";

	if (dataMap!=null&&"true".equals(dataMap.get("kmss.integrate.esa.enabled"))) {
		esaEnable = true;
		request.setAttribute("lbpmNote_Id", request.getParameter("auditNoteFdId"));
		request.setAttribute("lbpmHandler_Id", request.getParameter("curHanderId"));

		ESASerSystemSq = (String)dataMap.get("kmss.esa.ESASerSystemSq");
		request.setAttribute("ESASerSystemSq", ESASerSystemSq);
	}
	request.setAttribute("esaEnable", esaEnable);

	String signWidth = "70";
	String width = dataMap==null?"":(String)dataMap.get("kmss.esa.sign.width");
	if(width!=null && !"".equals(width)){
		signWidth = width;
	}
	request.setAttribute("signWidth", signWidth);

	String username = (String) session.getAttribute("PKI_VALIDATA_USERNAME");
	username = username==null ? "":username;

%>
<kmss:ifModuleExist path="/km/signature/">
<kmss:authShow roles="ROLE_SIGNATURE_DEFAULT">
	<!-- 流程页签下展示已盖的签章 -->
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<div id="showSignature" style="display:none">
				<div class="lui-lbpm-titleNode">
					<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.show" />
				</div>
				<div class="lui-lbpm-detailNode" id="signaturePic">
					<ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">
					</ul>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<tr id="showSignature" style="display:none;">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.show" />
				</td>
				<td id="signaturePic" colspan="3" width="85%">
					<ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">
					</ul>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
<script src="<c:url value="/sys/lbpmservice/signature/sysLbpmProcess_signature.js?s_cache=${LUI_Cache}"/>"></script>
<script>
(function(constant){
	constant.signatureName = '<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.name" />';
	lbpm.constant.signatureSigPic = '<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.sigPic" />';
	lbpm.constant.signatureReupload = '<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.reupload" />';
	lbpm.constant.signatureConfirm = '<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.confirm" />';
	lbpm.constant.signatureAuditNoteFdId = "${JsParam.auditNoteFdId}" + "_qz";
})(lbpm.constant);
</script>
</kmss:authShow>
</kmss:ifModuleExist>

<c:if test="${esaEnable == true}">
	<object classid="clsid:5A41B8F3-2BE5-4000-8ABF-E6E269C20218" id="aztManageKey" width="0" height="0"></object>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<div id="showSignature2" style="display:none">
				<div class="lui-lbpm-titleNode">
					<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.show" />
				</div>
				<div class="lui-lbpm-detailNode" id="signaturePic">
					<div id="signSealkeyPrivate_${JsParam.auditNoteFdId}" style="position:relative;min-width:120px;max-width:150px; height:80px;float:left;">&nbsp;
						<object style="z-index:1000;position:absolute;width:100%;height:100%;" id="aztWebSignSealkeyPrivate" classid="clsid:07121F49-A0DC-4EBD-A2A2-A0A71DC6FDB9"></object>
					</div>
					<div id="signSealkeyPublic_${JsParam.auditNoteFdId}" style="position:relative;min-width:120px;max-width:150px; height:100px;float:left;">&nbsp;
						<object style="z-index:1000;position:absolute;width:100%;height:100%;" id="aztWebSignSealkeyPublic" classid="clsid:07121F49-A0DC-4EBD-A2A2-A0A71DC6FDB9"></object>
					</div>
					<ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">
					</ul>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<!-- 流程页签下展示已盖的签章 -->
			<tr id="showSignature2" style="display:none;">
				<td class="td_normal_title" width="15%">
					<bean:message bundle="sys-lbpmservice" key="lbpm.process.signature.show" />
				</td>
				<td id="signaturePic" colspan="3" width="85%">
					<div id="signSealkeyPrivate_${JsParam.auditNoteFdId}" style="position:relative;min-width:120px;max-width:150px; height:80px;float:left;">&nbsp;
						<object style="z-index:1000;position:absolute;width:100%;height:100%;" id="aztWebSignSealkeyPrivate" classid="clsid:07121F49-A0DC-4EBD-A2A2-A0A71DC6FDB9"></object>
					</div>
					<div id="signSealkeyPublic_${JsParam.auditNoteFdId}" style="position:relative;min-width:120px;max-width:150px; height:100px;float:left;">&nbsp;
						<object style="z-index:1000;position:absolute;width:100%;height:100%;" id="aztWebSignSealkeyPublic" classid="clsid:07121F49-A0DC-4EBD-A2A2-A0A71DC6FDB9"></object>
					</div>
					<ul id="signaturePicUL" class="clearfloat lui_sns_signatureList">
					</ul>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
<style>
ul, li { list-style: none; }
.btn_canncel_img{
	display:inline-block;
	width:20px;
	height:20px;
    background:url(${KMSS_Parameter_ContextPath}sys/lbpmservice/signature/images/closeDiv.png) no-repeat 50%;
    background-size: 20px; 
    display:block;
    cursor:pointer;
}

.clearfloat { zoom: 1; }
.clearfloat:after { display: block; clear: both; visibility: hidden; line-height: 0px; content: 'clear'; }

.lui_sns_signatureList{ }
.lui_sns_signatureList li{ float:left; padding-top:3px; margin-right:15px; margin-bottom:8px; position:relative;}
.lui_sns_signatureList li img{ width:100px; height:75px;}
.lui_sns_signatureList .btn_canncel_img{ position:absolute; right:-8px; top:-5px;}
</style>
<script>
//流程页签初始化时将签章按钮插入其他按钮后
$(document).ready(function () {
	var nodePublicSign = "${requestScope[param.formName].sysWfBusinessForm.fdNodeAdditionalInfo.publicSign}";
	var nodePrivateSign = "${requestScope[param.formName].sysWfBusinessForm.fdNodeAdditionalInfo.privateSign}";
	if (typeof(seajs) != 'undefined') {
		//var func = "/km/signature/km_signature_main/kmSignatureMain_showSig.jsp";
		var html = '&nbsp;&nbsp;';
		if(nodePrivateSign!="" && nodePrivateSign=="true"){
			html += '<a href="javascript:;" class="com_btn_link" id="signaturePrivate" onclick="keySignPrivateSeal();">';
			html += '个人签名';
			html += '</a>';
		}
		if(nodePublicSign!="" && nodePublicSign=="true"){
			html += '&nbsp;&nbsp;<a href="javascript:;" class="com_btn_link" id="signaturePublic" onclick="keySignpublicSeal();">';
			html += '加盖公章';
			html += '</a>';
		}
		$("#optionButtons").append(html);
	}else{//非新UED模块屏蔽流程签章功能
		document.getElementById("showSignature2").style.display = "none";
	}
});
if (typeof(seajs) != 'undefined') {//非新UED模块屏蔽流程签章功能
	var fileIds = [];
	var signature_flag = true;
	var ESASerSystemSq_value="<%=ESASerSystemSq%>";
	var signWidth_value = "<%=signWidth%>";
	
	var login_name = "<%=UserUtil.getUser().getFdLoginName()%>";
	var username = "<%=username%>";
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
		//设置服务授权
		function ESASerSystemSq(){
			if(username==null || username==""){
				alert("获取电子签章信息失败，请使用电子证书登录！");
				return false;
			}
			//if(signature_flag){
				var nRit=document.getElementById("aztManageKey").ESASerSystemSq(ESASerSystemSq_value); 
				if(nRit>0){
					alert("电子签字服务授权失败");
					return false;
				}else{
					signature_flag = false;
				}
			//}
			return true;
		}
		
		//加盖公章
		window.keySignpublicSeal = function() {
			if(!ESASerSystemSq()){
				alert("获取电子签章信息失败");
				return false;
			}
			var aztWebSignSealkeyPublic = document.getElementById("aztWebSignSealkeyPublic");
			aztWebSignSealkeyPublic.ESASetDataisEncrypt(1);//设置返回数据是否加密(1加密，否则不加密)
			aztWebSignSealkeyPublic.ESASetPositionTay("signSealkeyPublic_${JsParam.auditNoteFdId}",1);//设置印章签章ID为ONE的标签上，2标识印章中心点在标签0,0位置
			var base64s = new Base64_ESA();
			//签章传入保护数据，移动端必填对应PC也填入。base64的数据
			aztWebSignSealkeyPublic.ESASetSignData(1,base64s.encode("${JsParam.modelId}"));
			aztWebSignSealkeyPublic.ESASignSeal(1,2,"10001","");
			aztWebSignSealkeyPublic.style.top="0px";
			aztWebSignSealkeyPublic.style.left="300px";
			
			$("#showSignature2").show();
			document.getElementById("showSignature2").style.display = "";
		};
		//个人签名
		window.keySignPrivateSeal = function() {
			if(!ESASerSystemSq()){
				alert("获取电子签章信息失败");
				return false;
			}
			var aztWebSignSealkeyPrivate = document.getElementById("aztWebSignSealkeyPrivate");
			aztWebSignSealkeyPrivate.ESASetDataisEncrypt(1);//设置返回数据是否加密(1加密，否则不加密)
			aztWebSignSealkeyPrivate.ESASetPositionTay("signSealkeyPrivate_${JsParam.auditNoteFdId}",1);//设置印章签章ID为ONE的标签上，2标识印章中心点在标签0,0位置
			//alert("aztWebSignSealkeyPrivate.ESASetPositionTay");
			var base64s = new Base64_ESA();
			aztWebSignSealkeyPrivate.ESASetSignData(1,base64s.encode("${JsParam.modelId}"));
			aztWebSignSealkeyPrivate.ESASignSeal(2,2,"10001","");
			//alert("aztWebSignSealkeyPrivate.ESASignSeal");
			aztWebSignSealkeyPrivate.ESASetIMGSize(signWidth_value,0);
			aztWebSignSealkeyPrivate.style.top="0px";
			aztWebSignSealkeyPrivate.style.left="0px";
	      	//------------判断当前电子证书和登录人是否一直-------------------------
			$("#showSignature2").show();
			document.getElementById("showSignature2").style.display = "";
		};
	});

	//监听流程提交事件，绑定签章信息
	Com_Parameter.event["submit"].push(function(){     //流程提交生成附件信息
		var nodePublicSign = "${requestScope[param.formName].sysWfBusinessForm.fdNodeAdditionalInfo.publicSign}";
		var nodePrivateSign = "${requestScope[param.formName].sysWfBusinessForm.fdNodeAdditionalInfo.privateSign}";
		
		if((nodePublicSign!="" && nodePublicSign=="true")|| (nodePrivateSign!="" && nodePrivateSign=="true")){
			
		}else{
			//alert("无需签章");
			return true;
		}
		var aztWebSignSealkeyPrivate= document.getElementById("aztWebSignSealkeyPrivate");
		var aztWebSignSealkeyPublic= document.getElementById("aztWebSignSealkeyPublic");
		var fdModelId = "${JsParam.modelId}";
		var fdModelName = "${JsParam.modelName}";
		var lbpmNote_Id = "${JsParam.auditNoteFdId}";
		var checkUrl = "${KMSS_Parameter_ContextPath}third/esa/third_esa_signature_lbpm_info/esaSignatureLbpmInfo.do?method=ajaxSave";
		
		var personStaffType= 1;
		var _StaffType = true;
		//获取员工类型民警必须签章。
		if(personStaffType!=null && personStaffType != 1){
			_StaffType = false;
		}
		try{
			if(aztWebSignSealkeyPrivate || aztWebSignSealkeyPublic){
				privategndata=aztWebSignSealkeyPrivate.ESASaveSignData();  //保存个人签章数据;
				publicgndata=aztWebSignSealkeyPublic.ESASaveSignData();  //保存公章信息数据;
				if(privategndata!=null && privategndata!=""){
					//alert(privategndata);
					$.ajax({
					     type:"post",
					     url:checkUrl,
					     data:{"privategndata":privategndata,"publicgndata":publicgndata,"fdModelId":fdModelId,"lbpmNote_Id":lbpmNote_Id,"fdModelName":fdModelName},
					     async:false,
					     success:function(data){
					    	 //alert(data);
						}
				    });
				}
			}
		}catch(e){
			console.log(e);
			alert("获取签章数据失败,"+e);
			return false;
		}
		return true;
	});
	
	function Base64_ESA() {
		_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		this.encode = function (input) {
			var output = "";
			var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
			var i = 0;
			input = _utf8_encode(input);
			while (i < input.length) {
				chr1 = input.charCodeAt(i++);
				chr2 = input.charCodeAt(i++);
				chr3 = input.charCodeAt(i++);
				enc1 = chr1 >> 2;
				enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
				enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
				enc4 = chr3 & 63;
				if (isNaN(chr2)) {
					enc3 = enc4 = 64;
				} else if (isNaN(chr3)) {
					enc4 = 64;
				}
				output = output +
				_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
				_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
			}
			return output;
		}
	 
		// public method for decoding
		this.decode = function (input) {
			var output = "";
			var chr1, chr2, chr3;
			var enc1, enc2, enc3, enc4;
			var i = 0;
			input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
			while (i < input.length) {
				enc1 = _keyStr.indexOf(input.charAt(i++));
				enc2 = _keyStr.indexOf(input.charAt(i++));
				enc3 = _keyStr.indexOf(input.charAt(i++));
				enc4 = _keyStr.indexOf(input.charAt(i++));
				chr1 = (enc1 << 2) | (enc2 >> 4);
				chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
				chr3 = ((enc3 & 3) << 6) | enc4;
				output = output + String.fromCharCode(chr1);
				if (enc3 != 64) {
					output = output + String.fromCharCode(chr2);
				}
				if (enc4 != 64) {
					output = output + String.fromCharCode(chr3);
				}
			}
			output = _utf8_decode(output);
			return output;
		}
	 
		// private method for UTF-8 encoding
		_utf8_encode = function (string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";
			for (var n = 0; n < string.length; n++) {
				var c = string.charCodeAt(n);
				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
	 
			}
			return utftext;
		}
	 
		// private method for UTF-8 decoding
		_utf8_decode = function (utftext) {
			var string = "";
			var i = 0;
			var c = c1 = c2 = 0;
			while ( i < utftext.length ) {
				c = utftext.charCodeAt(i);
				if (c < 128) {
					string += String.fromCharCode(c);
					i++;
				} else if((c > 191) && (c < 224)) {
					c2 = utftext.charCodeAt(i+1);
					string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
					i += 2;
				} else {
					c2 = utftext.charCodeAt(i+1);
					c3 = utftext.charCodeAt(i+2);
					string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
					i += 3;
				}
			}
			return string;
		}
	}
	
}
</script>

</c:if>