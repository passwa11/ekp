<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
Com_IncludeFile("swf_attachment.js?mode=edit","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);

require(["dijit/registry","dojo/topic","dojo/dom-construct","dojo/dom-style",
         "mui/dialog/Dialog","mui/util","dojo/_base/array","mui/i18n/i18n!sys-mobile"],
		 function(registry,topic,domConstruct,domStyle,Dialog,util,array,msg){
			
		}
);
</script>
<html:form action="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=save" style="margin-top:10px;">
<center>
<table class="muiSimple lbpmScriptTable fy" width=100%>
	<tr>
		<td width="100%">
			<div id='fdPostscript' data-dojo-type='mui/form/Textarea' data-dojo-mixins="mui/form/_ValidateMixin"
				 data-dojo-props='"subject":"<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdPostscript"/>",
								 "placeholder":"<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdPostscript"/>",
							     "name":"fdPostscript","validate":"required maxLength(2000)","required":true,opt:false' alertText="" key="fdPostscript">
		     </div>
		</td>
	</tr>
	 <tr>
		 <td width="100%">
			<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
		          <c:param name="formBeanName" value="${requestScope['formBeanName']}"/>
		          <c:param name="fdKey" value="${lbpmPostscriptForm.fdId}"/>
		          <c:param name="fdModelId" value="${lbpmPostscriptForm.fdModelId}"/>
		          <c:param name="fdModelName" value="${lbpmPostscriptForm.fdModelName}"/>
		          <c:param name="fdViewType" value="byte" />
			 </c:import>
		</td>
	</tr>
	<tr>
		 <td width="100%">
		 	<div data-dojo-type="mui/form/CheckBoxGroup" 
		 		 data-dojo-props="name:'fdIsNotify', mul:false,concentrate:false,alignment: 'H', showStatus:'edit',subject:'<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotify"/>',
		 		 				store:[{'text':'<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotify"/>','value':'1','checked':false}],orient:'none'">
		 	</div>
		</td> 
	</tr>
	
	
	<tr class="fy_notify" style="display:none;" name="notifyTargetTypeWrap">
		 <td width="100%">
		 	<div>
			 	<div data-dojo-type="mui/form/CheckBoxGroup" 
			 		 data-dojo-props="name:'fdNotifyTargetType', mul:false,concentrate:false, alignment: 'V', showStatus:'edit',subject:'',
			 		 store:[{'text':'<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyDrafter"/>','value':'0','checked':false},{'text':'<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyHandled"/>','value':'1','checked':false},{'text':'<bean:message bundle="sys-lbpmservice-support" key="lbpmPostscript.fdNotifyCurrentHandler"/>','value':'2','checked':false}],orient:'none'">
			 	</div>
		 	</div>
		</td> 
	</tr>
	<tr class="fy_notify" style="display: none" name="notifyTargetTypeWrap">
		<td width="100%">
		 	<div id="notifyTypeWrap"></div>
		</td> 
	</tr>
	
</table>
</center>
<html:hidden property="fdId" value="${lbpmPostscriptForm.fdId}"/>
<html:hidden property="fdPostscriptFrom" />
<html:hidden property="fdAuditNoteId" value="${lbpmPostscriptForm.fdAuditNoteId}"/>
<html:hidden property="fdModelName" value="${lbpmPostscriptForm.fdModelName}"/>
<html:hidden property="fdModelId" value="${lbpmPostscriptForm.fdModelId}"/>
<html:hidden property="fdCreatorId" value="${lbpmPostscriptForm.fdCreatorId}"/>
<html:hidden property="fdProcessId" value="${lbpmPostscriptForm.fdModelId}"/>
</html:form>