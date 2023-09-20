<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="head">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/address/extend/simple/dialog.js"></script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ omsRelationModelForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="addOrUpdateCheck('update','${param.type}');"></ui:button>
				</c:when>
				<c:when test="${ omsRelationModelForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="addOrUpdateCheck('add','${param.type}');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/third/ding/oms_relation_model/omsRelationModel.do">
 
<br><p class="txttitle"><bean:message bundle="third-ding" key="table.omsRelationModel"/></p><br>

<center>
	<c:if test="${ omsRelationModelForm.method_GET == 'edit' }">
		<table class="tb_normal" width=95%>
			<c:if test="${param.type=='dept' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.org"/>
					</td><td width="35%">
						<input type="hidden" name="fdEkpId" id="fdEkpId" value="${omsRelationModelForm.fdEkpId }">
						<xform:text property="fdEkpName" style="width:85%" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.Id.org"/>
					</td><td width="35%">
						<xform:text property="fdEkpId" style="width:85%" showStatus="view"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId.org"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird"/><span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
					</td><td width="35%"></td>
				</tr>
				<tr style="display:none">
					<input type="hidden" name="fdType" value="2" /> 
				</tr>
			</c:if>
			<c:if test="${param.type=='person' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId"/>
					</td><td width="35%">
						<input type="hidden" name="fdEkpId" id="fdEkpId" value="${omsRelationModelForm.fdEkpId }">
						<xform:text property="fdEkpName" style="width:85%" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpLoginName"/>
					</td><td width="35%">
						<xform:text property="fdEkpLoginName" style="width:85%" showStatus="view"/>
					</td>
				 </tr>
				 <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird"/><span class="txtstrong">*</span>
					</td>
					 <td class="td_normal_title" width=15%>
						 UnionId
					 </td>
					 <td width="35%">
						 <xform:text property="fdUnionId" style="width:85%" htmlElementProperties="readonly='readonly'"/>
					 </td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdAccountType"/>
					</td>
					<td width="35%">
						<c:if test="${omsRelationModelForm.fdAccountType=='common'}">
							普通账号
						</c:if>
						<c:if test="${omsRelationModelForm.fdAccountType=='dingtalk' }">
							专属账号(dingtalk)
						</c:if>
						<c:if test="${omsRelationModelForm.fdAccountType=='sso' }">
							专属账号(sso)
						</c:if>
					</td>
				</tr>
				<tr style="display:none">
					<input type="hidden" name="fdType" value="8" />
					<input type="hidden" name="fdAccountType" value="common" />
				</tr>
			</c:if>
		</table>
	</c:if>
	<c:if test="${ omsRelationModelForm.method_GET == 'add' }">
		<table class="tb_normal" width=95%>
			<c:if test="${param.type=='dept' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.org"/>
					</td><td width="35%">
						<%-- <input type="hidden" name="fdEkpId" id="fdEkpId" value="">					
						<input type="text" style="width:85%;height: 20px;border: 0px;border-bottom: 1px solid #b4b4b4;" name="fdEkpName" id="fdEkpName" value=""><span class="txtstrong">*</span>
						<a id="ekp" style="cursor: pointer;text-decoration: underline;color: blue;" onclick="checkEKP('1');"><bean:message key="button.select"/></a>
						 --%>
						<xform:dialog icon="orgelement" propertyId="fdEkpId" style="width:85%" propertyName="fdEkpName" showStatus="edit" htmlElementProperties="id='ekp'" required="true">
							Dialog_AddressSimple(false, 'fdEkpId','fdEkpName', ';',null,null, {nodeBeanURL: 'thirdDingOmsInitListService&orgType=3'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								$("input[name='fdEkpId']").change(function(){
									ekpHandle('1');
								});
							});
						</script>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId.org"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird" /><span class="txtstrong">*</span>
					</td>
				</tr>
				<tr style="display:none">
					<input type="hidden" name="fdType" value="2" /> 
				</tr>
			</c:if>
			<c:if test="${param.type=='person' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId"/>
					</td><td width="35%">
						<%-- <input type="hidden" name="fdEkpId" id="fdEkpId" value="">			
						<input type="text" style="width:85%;height: 20px;border: 0px;border-bottom: 1px solid #b4b4b4;" name="fdEkpName" id="fdEkpName" value="" readonly="readonly"><span class="txtstrong">*</span>
						<a id="ekp" style="cursor: pointer;text-decoration: underline;color: blue;" onclick="checkEKP('0');"><bean:message key="button.select"/></a>
						 --%>
						<xform:dialog icon="orgelement" propertyId="fdEkpId" style="width:85%" propertyName="fdEkpName" showStatus="edit" htmlElementProperties="id='ekp'" required="true">
							Dialog_AddressSimple(false, 'fdEkpId','fdEkpName', ';',null,null, {nodeBeanURL: 'thirdDingOmsInitListService&orgType=8'});
						</xform:dialog>
						<script type="text/javascript">
							$(function(){
								$("input[name='fdEkpId']").change(function(){
									ekpHandle('0');
								});
							});
						</script>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdEkpLoginName"/>
					</td><td width="35%">
						<xform:text property="fdEkpLoginName" style="width:85%" htmlElementProperties="readonly='readonly'"/>
					</td>
				 </tr>
				 <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird" /><span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
						UnionId
					</td>
					 <td width="35%">
					    <xform:text property="fdUnionId" style="width:85%" htmlElementProperties="readonly='readonly'"/>
				     </td>
				</tr>
				<tr style="display:none">
					<input type="hidden" name="fdType" value="8" />
					<input type="hidden" name="fdAccountType" value="common" />
				</tr>
			</c:if>
		</table>
	</c:if>
	<br>
	<table class="tb_noborder" width=95%>
		<tr>
			<td>
				<div id="msg" style="color: red;display: none;width: 100%"></div>
			</td>
		</tr>
	</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("dialog.js");
	$KMSSValidation();
</script>
<script>
function ekpHandle(type) {
	var fdEkpId = $("input[name='fdEkpId']").val();
	var url = '<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=checkEKP&fdEkpId=" />'+fdEkpId;
		$.post(url, function(data){
	    	if(data.status=="1"){
	    		if("0"==type){
		    		$("input[name='fdEkpLoginName']").val(data.msg);
	    		}else if("1"==type){
	    			$("input[name='fdEkpId']").val("");
		    		$("input[name='fdEkpName']").val("");
		    		$("input[name='fdEkpLoginName']").val("");
	    		}
	    	}else{
	    		if("0"==type){
		    		$("input[name='fdEkpId']").val("");
		    		$("input[name='fdEkpName']").val("");
		    		$("input[name='fdEkpLoginName']").val("");
	    		}
	    	}
	   },"json");
}
function checkEKP(type){
	Dialog_List(false, "fdEkpId", "fdEkpName", ';',
    'thirdDingOmsInitListService&type='+type, function(rtnVal){
		if("0"==type){
			var fdEkpId = $("#fdEkpId").val();
			var url = '<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=checkEKP&fdEkpId=" />'+fdEkpId;
				$.post(url, function(data){
			    	if(data.status=="1"){
			    		$("input[name='fdEkpLoginName']").val(data.msg);
			    	}
			   },"json");
		}
	 },
    'thirdDingOmsInitListService&search=!{keyword}&type='+type, false, false,'<bean:message bundle="third-ding" key="thirdDingOmsInit.omsinit.ekp.org"/>');
}
function checkThird(){
	var fdAppPkId = $("input[name='fdAppPkId']").val();
	var fdId = '${omsRelationModelForm.fdId}';
	var url = '<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=checkThird&fdId=" />'+fdId+"&fdAppPkId="+fdAppPkId;
	$.post(url,function(data){
	    	if(data.status=="0"){
	    		var info = '<bean:message bundle="third-ding" key="omsRelationModel.error.info"/>';
	    		$("#msg").empty();
	    		$("#msg").show();
	    		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+data.msg);
	    		var method = "${ omsRelationModelForm.method_GET}";
	    		if("add"==method){
		    		$("input[name='fdAppPkId']").val("");
	    		}else{
	    			$("input[name='fdAppPkId']").val("${ omsRelationModelForm.fdAppPkId}");
	    		}
	    	}else{
	    		$("#msg").hide();
	    	}
	   },"json");
}
function addOrUpdateCheck(method,type){
	var fdAppPkId = $("input[name='fdAppPkId']").val();
	var fdEkpId = $("input[name='fdEkpId']").val();
	var info = '<bean:message bundle="third-ding" key="omsRelationModel.error.info"/>';
	var fdAppPkIdInfo = "";
	if(fdEkpId==null||fdEkpId==""){
		if('${param.type}'=='dept'){
			fdAppPkIdInfo = '<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId.org"/>';
		}else{
			fdAppPkIdInfo = '<bean:message bundle="third-ding" key="omsRelationModel.fdEkpId"/>';
		}
		fdAppPkIdInfo += '<bean:message bundle="third-ding" key="omsRelationModel.no.empty"/>';
		$("#msg").empty();
		$("#msg").show();
		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+fdAppPkIdInfo);
		return false;
	}
	var fdEkpIdInfo = "";
	if(fdAppPkId==null||fdAppPkId==""){
		if('${param.type}'=='dept'){
			fdEkpIdInfo = '<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId.org"/>';
		}else{
			fdEkpIdInfo = '<bean:message bundle="third-ding" key="omsRelationModel.fdAppPkId"/>';
		}
		fdEkpIdInfo += '<bean:message bundle="third-ding" key="omsRelationModel.no.empty"/>';
		$("#msg").empty();
		$("#msg").show();
		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+fdEkpIdInfo);
		return false;
	}
	var fdId = '${omsRelationModelForm.fdId}';
	var url = '<c:url value="/third/ding/oms_relation_model/omsRelationModel.do?method=addOrUpdateCheck" />';
	$.post(url, {fdAppPkId:fdAppPkId,type:type,fdId:fdId},
	   	function(data){
	    	if(data.status=="0"){
	    		if(data.msg!=""){
	    			$("#msg").empty();
		    		$("#msg").show();
		    		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+data.msg);
	    		}
	    	}else{
	    		//成功
	    		$("#msg").hide();
				if('${param.type}'=='person'){
					$("[name='fdUnionId']").val(data.unionId);
					$("[name='fdAccountType']").val(data.fdAccountType);
				}
	    		if("add"==method){
		    		Com_Submit(document.omsRelationModelForm, 'saveadd');
	    		}else{
	    			Com_Submit(document.omsRelationModelForm, 'update');
	    		}
	    	}
	   },"json");
}
</script>
</html:form>

	</template:replace>
</template:include>