<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImissiveUnitBrunchLeaderForm.method_GET=='edit'}">
			 <ui:button text="${ lfn:message('button.update') }" order="2" onclick="submitMethod('update');">
			 </ui:button>	
		</c:if>
		<c:if test="${kmImissiveUnitBrunchLeaderForm.method_GET=='add'}">
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
<html:form action="/sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do">
<p class="txttitle">分管领导设置</p>
<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdLeader"/>
		</td>
		<td width=35%>
		     <xform:address  required="true" subject="${ lfn:message('sys-unit:kmImissiveUnitBrunchLeader.fdLeader')}" onValueChange="changeDept()" propertyName="fdLeaderName" propertyId="fdLeaderId" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" className="inputsgl" style="width:65%"></xform:address>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdDept"/>
		</td>
		<td width=35%>
		    <div id="deptDiv">
		    </div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdUnits"/>
		</td>
		<td width=85% colspan="3">
			<xform:dialog htmlElementProperties="id='mainUnit'" propertyId="fdUnitIds" propertyName="fdUnitNames" style="width:100%" textarea="true"  useNewStyle="true"> </xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdOrder"/>
		</td>
		<td width=35%>
			<xform:text property="fdOrder" style="width:85%" validators="digits"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitBrunchLeader.fdIsAvailable"/>
		</td>
		<td width=35%>
			<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<script language="JavaScript">
      var validation = $KMSSValidation(document.forms['kmImissiveUnitBrunchLeaderForm']);
      $(document).ready(function(){
    	  changeDept();
    	  if("${kmImissiveUnitBrunchLeaderForm.fdUnitIds}" != ""){
    		 initNewDialog("fdUnitIds","fdUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"","",null);
    		 resetNewDialog("fdUnitIds","fdUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"${kmImissiveUnitBrunchLeaderForm.fdUnitIds}","${kmImissiveUnitBrunchLeaderForm.fdUnitNames}",null);
    	  }else{
    		 initNewDialog("fdUnitIds","fdUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"","",null);
    	  }
    	  $("#mainUnit").parent().parent().find(".selectitem").unbind("click"); //移除click
    	  $("#mainUnit").parent().parent().find(".selectitem").click(function(){
    		  Dialog_UnitTreeList(true, 'fdUnitIds', 'fdUnitNames', ';', 'kmImissiveUnitCategoryInnerTreeService&parentId=!{value}', '分管单位', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit',null,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit');
       	});
      });
      
      function changeDept(){
    	  var fdLeaderId = document.getElementsByName("fdLeaderId");
    	  var url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=getLeaderDept"; 
			 $.ajax({   
			     url:url,  
			     data:{fdLeaderId:fdLeaderId[0].value},   
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
      
      function submitMethod(method){
    		var formObj = document.kmImissiveUnitBrunchLeaderForm;
    		if(validation.validate()){
    			if(method != "update"){
        			var fdLeaderId = document.getElementsByName("fdLeaderId");
        			 var url="${KMSS_Parameter_ContextPath}sys/unit/km_imissive_brunchleader/kmImissiveUnitBrunchLeader.do?method=checkUnique"; 
        			 $.ajax({   
        			     url:url,  
        			     data:{fdLeaderId:fdLeaderId[0].value},   
        			     async:false,    //用同步方式 
        			     success:function(data){
        			 	    var results =  eval("("+data+")");
        				    if(results['repeat'] =='true'){
        				    	alert("已存在该领导的设置!");
        				    	return;
        					}else{
        				    	Com_Submit(formObj, method);
        					}
        				}    
        		    });
        		}else{
        			Com_Submit(formObj, method);
        		}
    		}
    	}
</script>
</html:form>
</template:replace>
</template:include>