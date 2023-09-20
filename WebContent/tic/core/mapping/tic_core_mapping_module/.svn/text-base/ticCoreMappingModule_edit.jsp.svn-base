<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do">
<div id="optBarDiv">
	<c:if test="${ticCoreMappingModuleForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			<%-- 
			onclick="if(!confirm('<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.confirm.restart"/>'))return;Com_Submit(document.ticCoreMappingModuleForm, 'update');">
			--%>
			onclick="doSubmit('update')">
			
	</c:if>
	<c:if test="${ticCoreMappingModuleForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="doSubmit('save')">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="doSubmit('saveadd')">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-mapping" key="table.ticCoreMappingModule"/></p>

<center>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
var TicCore_lang = {
	    examineFlow : "<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.lang.examineFlow"/>",
	    newModule : "<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.lang.newModule"/>"
	};
</script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/mapping/tic_core_mapping_module/modelSettingCfg.js">
</script>

<table class="tb_normal" width=95%>
	<%-- 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdServerName"/>
		</td>
		<td colspan="3" width="85%">
			<xform:text property="fdServerName" style="width:50%"  required="true"/>
		</td>
	</tr>
	--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdModuleName"/>
		</td><td width="35%">
		<xform:text property="fdModuleName" required="true" > </xform:text>
		<select name="fdModuleName_select"  onchange="changeOther(this)"></select>
		</td>
		
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdUse"/>
		</td><td width="35%">
			<xform:radio property="fdUse">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemplateName"/>
		</td><td width="35%">
			<xform:text property="fdTemplateName" value="" style="width:85%"  required="true"/><br>
			<xform:radio property="fdCate"  onValueChange="changeAllConfig(this);">
				<xform:enumsDataSource enumsType="ticCoreMappingModule_cate" />
			</xform:radio>
		</td>
			<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdMainModelName"/>
		</td><td width="35%">
			<xform:text property="fdMainModelName" value="" style="width:85%"  required="true" />
		</td>
	</tr>

	<tr id="allConfig" style="display: ${ticCoreMappingModuleForm.fdCate==0?'none':''}">
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemCateFieldName"/>
		</td><td width="35%">
		<xform:text property="fdTemCateFieldName" value="" style="width:70%"  required="${ticCoreMappingModuleForm.fdCate==0?'false':'true'}" />
		<%--  
		<font color="red">一般为docCategory</font>
		--%>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemNameFieldName"/>
		</td><td width="35%">
		<xform:text property="fdTemNameFieldName" value="" style="width:70%" required="${ticCoreMappingModuleForm.fdCate==0?'false':'true'}" />
		<%-- 
		<font color="red">一般为fdName</font>
		--%>
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdModelTemFieldName"/>
		</td><td width="35%">
		<xform:text property="fdModelTemFieldName" value="" style="width:50%"  required="true" />
		<%-- 
		<font color="red">一般为fdTemplate</font>
		--%>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdFormTemFieldName"/>
		</td><td width="35%">
		<xform:text property="fdFormTemFieldName" value="" style="width:50%"  required="true" />
		<%-- 
		<font color="red">一般为fdTemplateId</font>
		--%>
		
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdType"/>
		</td><td width="35%" id="td_type">
			 <xform:checkbox property="fdType" value="${ticCoreMappingModuleForm.fdType}" >
			     <xform:customizeDataSource className="com.landray.kmss.tic.core.mapping.plugins.taglib.TicCorePluginsDataSource"/>
		     </xform:checkbox>
		</td>
		<td class="td_normal_title" width=15%>

		</td><td width="35%">

		
		</td>
	</tr>	
	
	
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script type="text/javascript">
	$KMSSValidation();
	function changeAllConfig(_this){
		var dom=document.getElementById("allConfig");
		if(_this.value=='0') {
			dom.style.display="none";
			document.getElementsByName("fdTemCateFieldName")[0].validate="";
			document.getElementsByName("fdTemNameFieldName")[0].validate="";
			document.getElementsByName("fdTemCateFieldName")[0].removeAttribute("validate");
			document.getElementsByName("fdTemNameFieldName")[0].removeAttribute("validate");
		}
		else {
			dom.style.display="";
			document.getElementsByName("fdTemCateFieldName")[0].validate="required";
			document.getElementsByName("fdTemNameFieldName")[0].validate="required";
			document.getElementsByName("fdTemCateFieldName")[0].setAttribute('validate', 'required');
			document.getElementsByName("fdTemNameFieldName")[0].setAttribute('validate', 'required');
			}
	}

	<%-- init fdModuleName --%>
   function initElementByCfg(elem){
	   emptyItem(elem);
	   var defOption=new Option("==请选择==","");
	   elem.options.add(defOption);
	   for(var i=0 ,len=cacheModuelInfo.length;i<len;i++){
		   if(cacheModuelInfo[i]["modelName"]){
			   elem.options.add(new Option(cacheModuelInfo[i]["modelName"],cacheModuelInfo[i]["modelName"]));
			   }
		   } 
	   defOption.selected=true;
	   }

   function emptyItem(elem){
	   while (elem.firstChild) {
			elem.removeChild(elem.firstChild);
		}
	   }
	
	;(
   function initSelect(){
     var elem= document.getElementsByName("fdModuleName_select")[0];
     initElementByCfg(elem);
     var initVal='${ticCoreMappingModuleForm.fdModuleName}';
      for(var i=0 ,len =elem.options.length;i<len ;i++){
   	   if(elem.options[i].value==initVal){
    		   elem.options[i].selected=true;
    		   return ;
        	   }
	   }

	   

   })();

   window.onload = function(){
	   //alert(document.getElementById("td_type").children.length);
	   var children = document.getElementById("td_type").children;
	   //alert(children.length);
	   //debugger;
	   var method = '${param.method}';
	   for(i=0;i<children.length;i++){
		   if(children[i].children[0].value == '4'){
			   children[i].style.display = "none";
		   }else{
			   if("add"==method){
				   children[i].children[0].checked = 'true';
			   }
		   }
	   }
	   
	   //document.getElementById("td_type").children[2].style.display = "none";
   }
   
   function doSubmit(method){
	   var fdMainModelName = document.getElementsByName("fdMainModelName")[0].value;
	   var fdUse = $("input[name='fdUse']:checked").val();
	   if(fdUse=='false'){
		   Com_Submit(document.ticCoreMappingModuleForm, method);
		   return;
	   }
	   $.ajax({
			url : '<c:url value="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=checkUnique" />',
			type : 'post',
			async : true,//是否异步
			data : {'mainModelName' : fdMainModelName,'fdId':'${param.fdId}'},
			success : function(data){
				if("false"==data){
					alert(fdMainModelName+"已经配置过，每个model只能配置一次");
					return;
				}else if("true"==data){
					Com_Submit(document.ticCoreMappingModuleForm, method);
				}else{
					alert(data);
					return;
				}
			}
		})
   }

	

	
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
