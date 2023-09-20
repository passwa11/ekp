<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImissiveUnitForm.method_GET=='edit'}">
		    <ui:button text="${ lfn:message('button.update') }" order="2" onclick="submitForm('update');">
		    </ui:button>
		</c:if>
		<c:if test="${kmImissiveUnitForm.method_GET=='add'}">
	  	    <ui:button text="${ lfn:message('button.save') }" order="1" onclick="submitForm('save');">
		    </ui:button>
		    <ui:button text="${ lfn:message('button.saveadd') }" order="2" onclick="submitForm('saveadd');">
		    </ui:button>
		</c:if>
		 <ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<html:form action="/sys/unit/km_imissive_unit/kmImissiveUnit.do">
<% 
request.setAttribute("admin", (UserUtil.checkRole("ROLE_SYSUNIT_ADDOUTERUNIT")||UserUtil.checkRole("ROLE_SYSUNIT_CONFIG_SETTING")||UserUtil.checkRole("ROLE_KMIMISSIVE_CONFIG_SETTING")||UserUtil.getKMSSUser().isAdmin()));
%>
<script type="text/javascript">
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.dialog = dialog;
});
function submitForm(method){
	var flag = true;
	if(!checkUniqueCode()){
		dialog.alert("${ lfn:message('sys-unit:kmImissive.alert.CompanyNumber') }");
		flag = false;
	}
	if (flag) {
		let pronounsCode =  $('[name="fdPronounsCode"]').val();
		if (pronounsCode && !checkPronounsCodeUnique()) {
			dialog.alert("代字编码不能重复!");
			return;
		}
		if (checkUnique()) {
			Com_Submit(document.kmImissiveUnitForm, method);
		}else{
			dialog.confirm('<bean:message  bundle="sys-unit" key="kmImissiveUnit.tips.exist"/>',function(rtn){
				if(rtn == true){
					Com_Submit(document.kmImissiveUnitForm, method);
				}
			});
		}
	}
}

function checkUniqueCode(){ 
	var url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_unit/kmImissiveUnit.do?method=checkUniqueCode"; 
	var  fdCode = document.getElementsByName("fdCode")[0];
	var fdUnitId = document.getElementsByName('fdId')[0];
	var flag = true;
	 $.ajax({     
	     type:"post",   
	     url:url,     
	     data:{fdCode:fdCode.value,fdUnitId:fdUnitId.value},    
	     async:false,    //用同步方式 
	     success:function(data){
	 	    var results =  eval("("+data+")");
	 	    if(results['repeat']=="true"){
	 	       flag=false;
	   	 	}
		 }    
  });
   return flag;
}

function checkUnique(){ 
	var url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_unit/kmImissiveUnit.do?method=checkUnique"; 
	var fdName = document.getElementsByName("fdName")[0];
	var fdCategoryId = document.getElementsByName('fdCategoryId')[0];
	var fdUnitId = document.getElementsByName('fdId')[0];
	var flag = true;
	 $.ajax({     
	     type:"post",   
	     url:url,     
	     data:{fdName:fdName.value,fdCategoryId:fdCategoryId.value,fdUnitId:fdUnitId.value},    
	     async:false,    //用同步方式 
	     success:function(data){
	 	    var results =  eval("("+data+")");
	 	    if(results['repeat']=="true"){
	 	       flag=false;
	   	 	}
		 }    
  });
   return flag;
}

/**
 * @returns {flag 唯一 : true}
 */
function checkPronounsCodeUnique() {
	let flag;
	let url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_unit/kmImissiveUnit.do?method=checkPronounsCodeUnique";
	let pronounsCode = $('[name="fdPronounsCode"]').val();
	let unitId = $('[name="fdId"]').val();
	if (pronounsCode) {
		$.ajax({
			type: "GET",
			url: url,
			data: {pronounsCode: pronounsCode, unitId: unitId},
			async: false,    //用同步方式
			success: function (res) {
				flag = res === 'true';
			}
		});
	}
	return flag;
}

function refreshUnitDisplay(value){
	if(value=="1"){
		$(".secretary").show();
		document.getElementById("sec").setAttribute("validate", "required");
		document.getElementById("span_inner").style.display ="block";
		document.getElementsByName("fdCanReturn")[0].value="1";
		if("${kmImissiveUnitForm.fdCanReturn}" != 'false'){
			document.getElementsByName("CanReturn")[0].checked=true;
		}
	}
	if(value=="0"){
		document.getElementById("sec").setAttribute("validate", "");
		//为外部单位则清除已选单位
		document.getElementsByName("fdSecretaryIds")[0].value="";
		document.getElementsByName("fdSecretaryNames")[0].value="";
		document.getElementsByName("fdCanReturn")[0].value="0";
		document.getElementsByName("CanReturn")[0].checked=false;
		$(".secretary").hide();
		document.getElementById("span_inner").style.display ="none";
	}
}
function changeVal(obj){
    if(obj.checked){
    	document.getElementsByName("fdCanReturn")[0].value="1";
    }else{
    	document.getElementsByName("fdCanReturn")[0].value="0";
    }
}
$(document).ready(function(){
	refreshUnitDisplay('${kmImissiveUnitForm.fdNature}');
	//没有参数设置角色时，不允许创建内部单位
	if("${admin}"!="true"){
		$('input:radio[name="fdNature"][value="1"]').prop("disabled",true);
	}
});
</script>
<p class="txttitle"><bean:message  bundle="sys-unit" key="table.kmImissiveUnit"/></p>

<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCategoryId"/>
		</td><td width=85% colspan="3">
		    <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" className="inputsgl" style="width:94%" subject="${ lfn:message('sys-unit:kmImissiveUnit.fdCategoryId')}">
		       Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 'kmImissiveUnitCategoryInnerTreeService&parentId=!{value}', '<bean:message key="kmImissiveUnit.fdCategoryId" bundle="sys-unit"/>', null, null, '${JsParam.fdId}', null, null, '<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCategoryId"/>');
		    </xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdNature"/>
		</td><td width=85% colspan="3">
			<sunbor:enums property="fdNature" enumsType="kmImissiveUnit.fdNature" elementType="radio" htmlElementProperties="onclick='refreshUnitDisplay(value);'"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdName"/>
		</td>
		<td width=85%  colspan="3">
		  <div>
		    <div style="width:90%;float:left">
		     <input type="hidden" name="fdDeptId">
			 <xform:text property="fdName" className="inputsgl" style="width:96%" required="true" subject="${ lfn:message('sys-unit:kmImissiveUnit.fdName')}" validators="maxLength(200)"/>
		    </div>
			<span id="span_inner" style="float:left;margin-left:8px">
			  <a href="#" onclick="Dialog_Address(false, 'fdDeptId', 'fdName', null, ORG_TYPE_DEPT|ORG_TYPE_ORG);"><bean:message key="dialog.selectOrg"/></a>		
		    </span>
		  </div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
				<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdShortName"/>
		</td>
		<td width=35%>
			<xform:text property="fdShortName" style="width:85%" required="true" validators="maxLength(200)"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdSocialCreditCode"/>
		</td>
		<td width=35%>
			<xform:text property="fdSocialCreditCode" style="width:85%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCode"/><br>
		</td>
		<td width=35%>
			<xform:text property="fdCode" style="width:85%" />
			</br>
			<font color="red"><bean:message  bundle="sys-unit" key="tips.unique.code.fdCode"/></font>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="sysUnit.fdPronounsCode"/><br>
		</td>
		<td width=35%>
			<xform:text property="fdPronounsCode" style="width:85%" />
			</br>
			<font color="red"><bean:message  bundle="sys-unit" key="tips.unique.code.fdPronounsCode"/></font>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdUnitLeader"/>
		</td>
		<td width=35%>
			<xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdLeader')}" propertyName="fdUnitLeaderName" propertyId="fdUnitLeaderId" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" ></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
		  <div class="secretary">
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdSecretaryId"/>
		  </div>
		</td>
		<td width=35%>
		  <div class="secretary">
		    <xform:address htmlElementProperties="id='sec'" required="true" subject="${ lfn:message('sys-unit:kmImissiveUnit.fdSecretaryId')}" propertyName="fdSecretaryNames" propertyId="fdSecretaryIds" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" mulSelect="true"></xform:address>
		    <input name="fdCanReturn" type="hidden" value="1"><input type="checkbox" name="CanReturn" onchange="changeVal(this);" <c:if test="${kmImissiveUnitForm.fdCanReturn!='false'}">checked</c:if>><bean:message  bundle="sys-unit" key="kmImissiveUnit.fdCanReturn"/>
		  </div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdMeetingLiaison"/>
		</td>
		<td width=35%>
			<xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdMeetingLiaison')}" propertyName="fdMeetingLiaisonNames" propertyId="fdMeetingLiaisonIds" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" mulSelect="true"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
		  <bean:message  bundle="sys-unit" key="kmImissiveUnit.fdSuperViseLiaison"/>
		</td>
		<td width=35%>
		    <xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdSuperViseLiaison')}" propertyName="fdSuperViseLiaisonNames" propertyId="fdSuperViseLiaisonIds" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" mulSelect="true"></xform:address>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdProposalLiaison"/>
		</td>
		<td width=35%>
			<xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdProposalLiaison')}" propertyName="fdProposalLiaisonNames" propertyId="fdProposalLiaisonIds" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" mulSelect="true"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
		  <bean:message  bundle="sys-unit" key="kmImissiveUnit.fdAdviseLiaison"/>
		</td>
		<td width=35%>
		    <xform:address  subject="${ lfn:message('sys-unit:kmImissiveUnit.fdAdviseLiaison')}" propertyName="fdAdviseLiaisonNames" propertyId="fdAdviseLiaisonIds" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%" mulSelect="true"></xform:address>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdOrder"/>
		</td><td width=35%>
			<xform:text property="fdOrder" style="width:85%" validators="digits"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdIsAvailable"/>
		</td><td width=35%>
			<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="kmImissiveUnit.fdContent"/>
		</td><td width=85% colspan='3'>
			<xform:textarea property="fdContent" style="width:94%" />
		</td>
	</tr>
	<tr>
			<td class="td_normal_title" width="15%">
			   <bean:message bundle="sys-unit" key="kmImissiveUnit.areader.distribute"/>
			</td>
			<td width="85%" colspan="3">
			 <xform:address propertyName="authReaderNamesDistribute" propertyId="authReaderIdsDistribute" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE"  style="width:94%" textarea="true" mulSelect="true"></xform:address>
			<br><bean:message bundle="sys-unit" key="kmImissiveUnit.unitUser"/>
			</td>
	</tr>
	<tr>
			<td class="td_normal_title" width="15%">
			  <bean:message bundle="sys-unit" key="kmImissiveUnit.areader.report"/>
			</td>
			<td width="85%" colspan="3">
			<xform:address propertyName="authReaderNamesReport" propertyId="authReaderIdsReport" orgType="ORG_TYPE_ALL|ORG_TYPE_ROLE"  style="width:94%" textarea="true" mulSelect="true"></xform:address>
			<br><bean:message bundle="sys-unit" key="kmImissiveUnit.unitUser"/>
			</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<script language="JavaScript">
	$KMSSValidation(document.forms['kmImissiveUnitForm']);
</script>
</html:form>
</template:replace>
</template:include>