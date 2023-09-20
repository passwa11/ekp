<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${sysUnitSecretaryForm.method_GET=='edit'}">
			 <ui:button text="${ lfn:message('button.update') }" order="2" onclick="submitMethod('update');">
			 </ui:button>	
		</c:if>
		<c:if test="${sysUnitSecretaryForm.method_GET=='add'}">
			<ui:button text="${ lfn:message('button.submit') }" order="1" onclick="submitMethod('save');">
		    </ui:button>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script>
    Com_IncludeFile("dialog.js|jquery.js");
    Com_IncludeFile("sysUnitDialog.js", Com_Parameter.ContextPath+ "sys/unit/resource/js/", "js", true);
</script>
<html:form action="/sys/unit/sys_unit_secretary/sysUnitSecretary.do">
<p class="txttitle"><bean:message bundle="sys-unit" key="table.sysUnitSecretary"/></p>
<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdPerson"/>
		</td>
		<td width=35%>
		     <xform:address  required="true" subject="${ lfn:message('sys-unit:sysUnitSecretary.fdPerson')}"  onValueChange="changeDept()" propertyName="fdPersonName" propertyId="fdPersonId" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdDept"/>
		</td>
		<td width=35%>
		    <div id="deptDiv">
		    </div>
		</td>
	</tr>

	<%--<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdSupervisePerson"/>
		</td>
		<td width=35%>
			<xform:address required="true" subject="${ lfn:message('sys-unit:sysUnitSecretary.fdSupervisePerson')}"  onValueChange="supervisePersonchangeDept()" propertyName="fdSupervisePersonName" propertyId="fdSupervisePersonId" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdDept"/>
		</td>
		<td width=35%>
			<div id="supervisePersondeptDiv">
			</div>
		</td>
	</tr>--%>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdBelongUnit"/>
		</td>
		<td width=85% colspan="3">
			<xform:dialog propertyId="fdBelongUnitId"
						  propertyName="fdBelongUnitName"
						  style="width:95%"
						  showStatus="edit"
						  required="true"
						  subject="${ lfn:message('sys-unit:sysUnitSecretary.fdBelongUnit') }">
				Dialog_Tree(false,'fdBelongUnitId','fdBelongUnitName',null,'sysUnitDecTreeService&parentId=!{value}&showDecUnit=true','文书单位选择',false,changeBelongUnit);
			</xform:dialog>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdUnits"/>
		</td>
		<td width=85% colspan="3">
			<xform:dialog htmlElementProperties="id='mainUnit'" dialogJs="selectUnit();" required="true" subject="${ lfn:message('sys-unit:sysUnitSecretary.fdUnits')}"  propertyId="fdUnitIds" propertyName="fdUnitNames" style="width:98%" textarea="true"  useNewStyle="false"> 
			</xform:dialog>
			<font color="red">(注:设置前请确保(后台设置->对接单位设置)中已添加设置,同时已进行了单位同步操作)</font>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-unit" key="sysUnitSecretary.fdContent"/>
		</td><td width=85% colspan='3'>
			<xform:textarea property="fdContent" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdOrder"/>
		</td>
		<td width=35%>
			<xform:text property="fdOrder" style="width:85%" validators="digits"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="sysUnitSecretary.fdIsAvailable"/>
		</td>
		<td width=35%>
			<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<script language="JavaScript">
      var validation = $KMSSValidation(document.forms['sysUnitSecretaryForm']);
      
        seajs.use(['sys/ui/js/dialog'], function(dialog) {
    		window.dialog = dialog;
    	});
      
      $(document).ready(function(){
    	  changeDept();
		  supervisePersonchangeDept();
    	 /*  resetNewDialog("fdUnitIds","fdUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit&fdNature=1",true,"","",null);
    	  resetNewDialog("fdBelongUnitId","fdBelongUnitName",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit&fdNature=1",false,"","",null);
    	  if("${sysUnitSecretaryForm.fdUnitIds}" != ""){
    		  resetNewDialog("fdUnitIds","fdUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit&fdNature=1",true,"${sysUnitSecretaryForm.fdUnitIds}","${sysUnitSecretaryForm.fdUnitNames}",null);
    	  }
    	  $("#mainUnit").parent().parent().find(".selectitem").unbind("click"); //移除click
    	  $("#mainUnit").parent().parent().find(".selectitem").click(function(){
    		  Dialog_UnitTreeList(true, 'fdUnitIds', 'fdUnitNames', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}&fdNature=1', '负责单位', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit&fdNature=1',null,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit&fdNature=1');
       	  }); 
    	  
    	  if("${sysUnitSecretaryForm.fdBelongUnitId}" != ""){
     		 resetNewDialog("fdBelongUnitId","fdBelongUnitName",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit&fdNature=1",false,"${sysUnitSecretaryForm.fdBelongUnitId}","${sysUnitSecretaryForm.fdBelongUnitName}",null);
     	  }
     	  $("#belongUnit").parent().parent().find(".selectitem").unbind("click"); //移除click
     	  $("#belongUnit").parent().parent().find(".selectitem").click(function(){
     		  Dialog_UnitTreeList(false, 'fdBelongUnitId', 'fdBelongUnitName', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}&fdNature=1', '所在单位', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit&fdNature=1',null,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit&fdNature=1');
          }); */
      });



	  function changeBelongUnit(){
		  var fdUnitIds = document.getElementsByName("fdUnitIds")[0];
		  var fdUnitNames = document.getElementsByName("fdUnitNames")[0];
		  fdUnitIds.value = "";
		  fdUnitNames.value = "";
	  }
      
      function checkUnitCateType(){
    	  var fieldObj = document.getElementsByName("fdUnitIds")[0];
    	  var url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_unit/kmImissiveUnit.do?method=checkUintCateType";
    	  var fdUnitNames="";
  		 $.ajax({   
  		     url:url,  
  		     data:{fdUnitIds:fieldObj.value},  
  		     async:false,    //用同步方式 
  		     success:function(data){
  		 	    var results =  eval("("+data+")");
  		 	    if(results.flag == "true"){
		 	    	fdUnitNames = results.fdUnitNames;
		 	    }
  			}    
  	    });
  		return fdUnitNames;
      }

	  function selectUnit(){
		  var fieldObj = document.getElementsByName("fdBelongUnitId")[0];
		  Dialog_List(true, "fdUnitIds", "fdUnitNames", ";", "kmImissiveOuterUnitDataBeanService&type=all&fdUnitId="+fieldObj.value, null, null, false, true, "外部单位选择");
	  }

      function changeDept(){
    	  var fdPersonId = document.getElementsByName("fdPersonId");
    	  var url="${KMSS_Parameter_ContextPath}sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=getLeaderDept";
			 $.ajax({   
			     url:url,  
			     data:{fdPersonId:fdPersonId[0].value},   
			     async:false,    //用同步方式 
			     success:function(data){
			 	    var results =  eval("("+data+")");
				    if(results['deptName']){
				    	$('#deptDiv').html(results['deptName']);
					}else{
						$('#deptDiv').html("");
					}
				}    
		    });
      }

	  function supervisePersonchangeDept(){
		  var fdPersonId = document.getElementsByName("fdSupervisePersonId");
		  var url="${KMSS_Parameter_ContextPath}sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=getLeaderDept";
		  $.ajax({
			  url:url,
			  data:{fdPersonId:fdPersonId[0].value},
			  async:false,    //用同步方式
			  success:function(data){
				  var results =  eval("("+data+")");
				  if(results['deptName']){
					  $('#supervisePersondeptDiv').html(results['deptName']);
				  }else{
					  $('#supervisePersondeptDiv').html("");
				  }
			  }
		  });
	  }
      
      function checkUnique(){
    	  var unique = true;
    	  var fdPersonId = document.getElementsByName("fdPersonId");
			 var url="${KMSS_Parameter_ContextPath}sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=checkUnique"; 
			 $.ajax({   
			     url:url,  
			     data:{fdPersonId:fdPersonId[0].value},   
			     async:false,    //用同步方式 
			     success:function(data){
			 	    var results =  eval("("+data+")");
				    if(results['repeat'] =='true'){
				    	unique = false;
				    	alert("已存在该文书的设置!");
					}
				}    
		    });
		return unique;
      }

	  function supervisePersonCheckUnique(){
		  var unique = true;
		  var fdSupervisePersonId = document.getElementsByName("fdSupervisePersonId");
		  var url="${KMSS_Parameter_ContextPath}sys/unit/sys_unit_secretary/sysUnitSecretary.do?method=checkUnique";
		  $.ajax({
			  url:url,
			  data:{fdSupervisePersonId:fdSupervisePersonId[0].value},
			  async:false,    //用同步方式
			  success:function(data){
				  var results =  eval("("+data+")");
				  if(results['repeat'] =='true'){
					  unique = false;
					  alert("已存在该联络员的设置!");
				  }
			  }
		  });
		  return unique;
	  }
      
      
      function submitMethod(method){ 
    		var formObj = document.sysUnitSecretaryForm;
    		if(validation.validate()){
    			//var fdBelongUnitName=checkBelongUnitCateType();
    			var fdUnitNames = checkUnitCateType();
    			if(fdUnitNames!="" ){
    				var msg = "<div style='text-align:left'>";
    				msg += "负责单位:"+fdUnitNames;
    				msg += "不能为其他单位类型的单位</div>";
    				dialog.alert(msg);
    			}else{ 
    				if(method != "update"){
            			if(checkUnique()){
            				if(supervisePersonCheckUnique()){
								Com_Submit(formObj, method);
							}
            			}
            		}else{
            			Com_Submit(formObj, method);
            		}
    			}
    		}
    	}
</script>
</html:form>
</template:replace>
</template:include>