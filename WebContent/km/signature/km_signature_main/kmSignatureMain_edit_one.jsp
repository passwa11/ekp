<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="gb2312"%>
<%@ page
	import="java.io.*,java.text.*,java.util.*,java.sql.*,com.landray.kmss.km.signature.util.iDBManager2000"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="edit_top.jsp"%>
<script language=javascript>
	function setImagePreview() {
		var docObj = document.getElementById("MarkFile");
		var imgObjPreview = document.getElementById("preview");
		if (docObj.files && docObj.files[0]) {
			//����£�ֱ����img����   
			alert(imgObjPreview);
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '168px';
			imgObjPreview.style.height = '168px';
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		} else {
			 //IE�£�ʹ���˾�                          
	         docObj.select();    
	         docObj.blur();
	         var imgSrc = document.selection.createRange().text;                          
	         var localImagId = document.getElementById("localImag");  	 
	         //�������ó�ʼ��С                          	 
	         localImagId.style.width = "168px";                          	 
	         localImagId.style.height = "168px";
	        //ͼƬ�쳣�Ĳ�׽����ֹ�û��޸ĺ�׺��α��ͼƬ  	 
	        try{                                  	 
	            localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";                                   
	            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;                          
	        }catch(e){                                  
	            alert('<bean:message bundle="km-signature" key="signature.warn1"/>');                                  
	            return false;                          
	        }  
	            imgObjPreview.style.display = 'none';                          
	            document.selection.empty();                  
	        }                  
	            return true;          
	        }  
	Com_IncludeFile("dialog.js");
	function Check() {
		var sigName=document.getElementById("MarkName").value;
		$("#webform").attr("action","kmSignatureMain_info.jsp?meth=save&sigName="+encodeURIComponent(sigName));
		var p1=document.getElementById("password").value;
		var p2=document.getElementById("password1").value;
		var MarkFile=document.getElementById("MarkFile").value;
		if(MarkFile==null||MarkFile==""){
			alert('<bean:message bundle="km-signature" key="signature.selectPic"/>');
			return (false);
		}
		if(p1==p2){
			return (true);
		}else{
			alert('<bean:message bundle="km-signature" key="signature.warn2"/>');
			return (false);
		}
	}
</Script>
<%
	String userName = UserUtil.getUser().getFdName();
%>
<form name="webform" id="webform" method="post" enctype="multipart/form-data"
	action="kmSignatureMain_info.jsp?meth=save" onsubmit="return Check()">
	<div id="optBarDiv">
		<input type="submit" value="<bean:message key="button.save"/>"  />
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
	</div>
	<p class="txttitle"><bean:message bundle="km-signature" key="table.signature"/></p>
	<center>
		<table class="tb_normal" width="95%">
			<!-- �û����� -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.username"/>
				</td><td width="85%">
					<input type="hidden" name="UserName" value="<%=userName%>" disabled="true ">
					<%=userName%>
				</td>
			</tr>
			<!-- ӡ������ -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.markname"/>
				</td><td width="85%">
					<input name="MarkName" class="inputsgl" style="width: 80%;" type="text" validate="required" />
					<span class="txtstrong">*</span> 
				</td>
			</tr>
			<!-- �û����� -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.password"/>
				</td><td width="85%">
					<input id="fdPassword" type="password" name="fdPassword" size="50" maxlength="32" class="inputsgl">
					<!-- xform:text type="password" property="fdPassword" required="true" size="50" validators="maxLength(32)" className="inputsgl" / -->
					<span class="txtstrong">*</span>
				</td>
			</tr>
			<!-- ȷ������ -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.password"/>
				</td><td width="85%">
					<input id="fdPassword1" type="password" name="fdPassword1" size="50" maxlength="32" class="inputsgl">
					<span class="txtstrong">*</span>
				</td>
			</tr>
			<!-- �Ƿ���Ч -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.docInForce"/>
				</td><td width="85%">
					<xform:radio property="docInForce" showStatus="edit">
		       			<xform:enumsDataSource enumsType="km_signature_main_docInForce" />
					</xform:radio>
				</td>		
			</tr>
			<!-- ǩ������ -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.docType"/>
				</td><td width="85%">
					<xform:radio property="fdDocType" showStatus="edit">
		       			<xform:enumsDataSource enumsType="km_signature_main_fdDocType" />
					</xform:radio>
				</td>
			</tr>
			<!-- ��Ȩ�û� -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.users"/>
				</td><td width="85%">
					<input name=fdUserIds type=hidden>
					<input name="fdUserNames" class="inputsgl" style="width: 80%;" type="text" readOnly="" /> 
					<a onclick="Dialog_Address(true, 'fdUserIds','fdUserNames', ';', 'ORG_TYPE_PERSON|ORG_TYPE_POST');" href="#">
						<bean:message key="button.select"/>
					</a>
					<span class="txtstrong">*</span>
				</td>
			</tr>
			<!-- ӡ���ļ� -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.markFile"/>
				</td><td width="85%">
				<input id="MarkFile" type="file" id="MarkFile" name="MarkFile" size="50" class="inputsgl" onchange="setImagePreview();"><span
					class="txtstrong">*<bean:message bundle="km-signature" key="signature.warn3"/></span>
					
				</td>
			</tr>
			<!-- ͼƬ��ʾ -->
			<tr>
				<td class="td_normal_title" width="15%">
					<bean:message bundle="km-signature" key="signature.picShow"/>
				</td><td width="85%">
					<div id="localImag">
						<img id="preview" width=-1 height=-1 style="diplay: none" />
					</div>
				</td>
			</tr>

		</table>
	</center>
</form>
<script type="text/javascript">
	Com_IncludeFile("calendar.js");
	Com_IncludeFile("common.js");
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("data.js");
	var _validation = $KMSSValidation();
</scritp>
<%@ include file="/resource/jsp/edit_down.jsp"%>