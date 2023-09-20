<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="head">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/address/extend/simple/dialog.js"></script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ wxworkOmsRelationModelForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="addOrUpdateCheck('update','${param.type}');"></ui:button>
				</c:when>
				<c:when test="${ wxworkOmsRelationModelForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="addOrUpdateCheck('add','${param.type}');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do">
 
<br><p class="txttitle"><bean:message bundle="third-weixin-work" key="table.wxOmsRelation"/></p><br>

<center>
	<c:if test="${ wxworkOmsRelationModelForm.method_GET == 'edit' }">
		<table class="tb_normal" width=95%>
			<c:if test="${param.type=='dept' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId.org"/>
					</td><td width="35%">
						<input type="hidden" name="fdEkpId" id="fdEkpId" value="${wxworkOmsRelationModelForm.fdEkpId }">
						<xform:text property="fdEkpName" style="width:85%" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId.Id.org"/>
					</td><td width="35%">
						<xform:text property="fdEkpId" style="width:85%" showStatus="view"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId.org"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird"/><span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
					</td><td width="35%"></td>
				</tr>
			</c:if>
			<c:if test="${param.type=='person' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId"/>
					</td><td width="35%">
						<input type="hidden" name="fdEkpId" id="fdEkpId" value="${wxworkOmsRelationModelForm.fdEkpId }">
						<xform:text property="fdEkpName" style="width:85%" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpLoginName"/>
					</td><td width="35%">
						<xform:text property="fdEkpLoginName" style="width:85%" showStatus="view"/>
					</td>
				 </tr>
				 <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird"/><span class="txtstrong">*</span>
					</td>
					 <td class="td_normal_title" width=15%>
						 <bean:message bundle="third-weixin-work" key="wxOmsRelation.fdOpenId"/>
					 </td><td width="35%">
					 <xform:text property="fdOpenId" style="width:85%"/>
				 </td>
				</tr>


			</c:if>
		</table>
	</c:if>
	<c:if test="${ wxworkOmsRelationModelForm.method_GET == 'add' }">
		<table class="tb_normal" width=95%>
			<c:if test="${param.type=='dept' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId.org"/>
					</td><td width="35%">
						<%-- <input type="hidden" name="fdEkpId" id="fdEkpId" value="">					
						<input type="text" style="width:85%;height: 20px;border: 0px;border-bottom: 1px solid #b4b4b4;" name="fdEkpName" id="fdEkpName" value=""><span class="txtstrong">*</span>
						<a id="ekp" style="cursor: pointer;text-decoration: underline;color: blue;" onclick="checkEKP('1');"><bean:message key="button.select"/></a> 
						--%>
						<xform:dialog icon="orgelement" propertyId="fdEkpId" style="width:85%" propertyName="fdEkpName" showStatus="edit" htmlElementProperties="id='ekp'" required="true">
							Dialog_AddressSimple(false, 'fdEkpId','fdEkpName', ';',null,null, {nodeBeanURL: 'thirdWxworkOmsInitListService&orgType=3'});
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
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId.org"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird" /><span class="txtstrong">*</span>
					</td>
				</tr>
			</c:if>
			<c:if test="${param.type=='person' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId"/>
					</td><td width="35%">
						<%-- <input type="hidden" name="fdEkpId" id="fdEkpId" value="">			
						<input type="text" style="width:85%;height: 20px;border: 0px;border-bottom: 1px solid #b4b4b4;" name="fdEkpName" id="fdEkpName" value="" readonly="readonly"><span class="txtstrong">*</span>
						<a id="ekp" style="cursor: pointer;text-decoration: underline;color: blue;" onclick="checkEKP('0');"><bean:message key="button.select"/></a>
						 --%>
						 <xform:dialog icon="orgelement" propertyId="fdEkpId" style="width:85%" propertyName="fdEkpName" showStatus="edit" htmlElementProperties="id='ekp'" required="true">
							Dialog_AddressSimple(false, 'fdEkpId','fdEkpName', ';',null,null, {nodeBeanURL: 'thirdWxworkOmsInitListService&orgType=8'});
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
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpLoginName"/>
					</td><td width="35%">
						<xform:text property="fdEkpLoginName" style="width:85%" htmlElementProperties="readonly='readonly'"/>
					</td>
				 </tr>
				 <tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId"/>
					</td><td width="35%">
						<xform:text property="fdAppPkId" style="width:85%" onValueChange="checkThird" /><span class="txtstrong">*</span>
					</td>
					 <td class="td_normal_title" width=15%>
						 <bean:message bundle="third-weixin-work" key="wxOmsRelation.fdOpenId"/>
					 </td><td width="35%">
					 <xform:text property="fdOpenId" style="width:85%"/>
				 </td>
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
	var ekpInfo = "EKP人员";
	if(type == '1'){
		ekpInfo = "EKP部门";
	}
	//type 0表示人员 1是部门
	var fdEkpId = $("input[name='fdEkpId']").val();
	var fdId = '${wxworkOmsRelationModelForm.fdId}';
	var url = '<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=checkEKP&fdEkpId=" />'+fdEkpId+"&fdId="+fdId+"&type="+type;
		$.post(url, function(data){
	    	if(data.status=="1"){
	    		//校验通过
				$("#msg").hide();
	    		if("0"==type){
		    		$("input[name='fdEkpLoginName']").val(data.msg);
	    		}
	    	}else{
	    		//校验不通过
				$("input[name='fdEkpId']").val("");
				$("input[name='fdEkpName']").val("");
	    		if("0"==type){
		    		$("input[name='fdEkpLoginName']").val("");
	    		}
				$("#msg").empty();
				$("#msg").show();
				$("#msg").html(ekpInfo+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+data.msg);
	    	}
	   },"json");
}
function checkEKP(type){
	Dialog_List(false, "fdEkpId", "fdEkpName", ';',
    'thirdWxworkOmsInitListService&type='+type, function(rtnVal){
		if("0"==type){
			var fdEkpId = $("#fdEkpId").val();
			var url = '<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=checkEKP&fdEkpId=" />'+fdEkpId;
				$.post(url, function(data){
			    	if(data.status=="1"){
			    		$("input[name='fdEkpLoginName']").val(data.msg);
			    	}
			   },"json");
		}
	 },
    'thirdWxworkOmsInitListService&search=!{keyword}&type='+type, false, false,'<bean:message bundle="third-weixin-work" key="thirdWxOmsInit.omsinit.ekp.org"/>');
}
function checkThird(){
	var fdAppPkId = $("input[name='fdAppPkId']").val();
	var fdId = '${wxworkOmsRelationModelForm.fdId}';
	var url = '<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=checkThird&fdId=" />'+fdId+"&fdAppPkId="+fdAppPkId+"&type=${param.type}";
	$.post(url,function(data){
	    	if(data.status=="0"){
	    		var info = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.error.info"/>';
	    		$("#msg").empty();
	    		$("#msg").show();
	    		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+data.msg);
	    		var method = "${ wxworkOmsRelationModelForm.method_GET}";
	    		if("add"==method){
		    		$("input[name='fdAppPkId']").val("");
	    		}else{
	    			$("input[name='fdAppPkId']").val("${ wxworkOmsRelationModelForm.fdAppPkId}");
	    		}
	    	}else{
	    		$("#msg").hide();
	    	}
	   },"json");
}
function addOrUpdateCheck(method,type){
	var fdAppPkId = $("input[name='fdAppPkId']").val();
	var fdEkpId = $("input[name='fdEkpId']").val();
	var info = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.error.info"/>';
	var fdAppPkIdInfo = "";
	if(fdEkpId==null||fdEkpId==""){
		if('${param.type}'=='dept'){
			fdAppPkIdInfo = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId.org"/>';
		}else{
			fdAppPkIdInfo = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdEkpId"/>';
		}
		fdAppPkIdInfo += '<bean:message bundle="third-weixin-work" key="wxOmsRelation.no.empty"/>';
		$("#msg").empty();
		$("#msg").show();
		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+fdAppPkIdInfo);
		return false;
	}
	var fdEkpIdInfo = "";
	if(fdAppPkId==null||fdAppPkId==""){
		if('${param.type}'=='dept'){
			fdEkpIdInfo = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId.org"/>';
		}else{
			fdEkpIdInfo = '<bean:message bundle="third-weixin-work" key="wxOmsRelation.fdAppPkId"/>';
		}
		fdEkpIdInfo += '<bean:message bundle="third-weixin-work" key="wxOmsRelation.no.empty"/>';
		$("#msg").empty();
		$("#msg").show();
		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+fdEkpIdInfo);
		return false;
	}
	var fdId = '${wxworkOmsRelationModelForm.fdId}';
	var url = '<c:url value="/third/weixin/work/spi/wxwork_oms_relation/wxworkOmsRelation.do?method=addOrUpdateCheck" />';
	$.post(url, {fdAppPkId:fdAppPkId,type:type,fdId:fdId,fdEkpId:fdEkpId},
	   	function(data){
	    	if(data.status=="0"){
	    		if(data.msg!=""){
	    			$("#msg").empty();
		    		$("#msg").show();
		    		$("#msg").html(info+"<br>&nbsp;&nbsp;&nbsp;&nbsp;"+data.msg);
	    		}
	    	}else{
	    		$("#msg").hide();
	    		if("add"==method){
		    		Com_Submit(document.wxworkOmsRelationModelForm, 'saveadd');
	    		}else{
	    			Com_Submit(document.wxworkOmsRelationModelForm, 'update');
	    		}
	    	}
	   },"json");
}
</script>
</html:form>

	</template:replace>
</template:include>