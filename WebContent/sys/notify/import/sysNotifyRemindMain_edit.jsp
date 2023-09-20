<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />
<script type="text/javascript">Com_IncludeFile("doclist.js");</script>
<script type="text/javascript">DocList_Info.push("${JsParam.fdPrefix}_${JsParam.fdKey}");</script>
<style type="text/css">
	.sys_notify_add{color: #1354ca;}
	.sys_notify_add:HOVER {text-decoration: underline;}
</style>
 <table  class="tb_simple" width="100%" id="${HtmlParam.fdPrefix}_${HtmlParam.fdKey}" >
 	<%--基准行 --%>
	<tr KMSS_IsReferRow="1" style="display:none">
		 <td>
		 	<input  type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdModelName" value="${HtmlParam.fdModelName}"  />
		 	<kmss:editNotifyType value="todo" property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdNotifyType" multi="false" ></kmss:editNotifyType>
	       	<input style="width: 50px;" type="text" subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
	       		name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdBeforeTime" value="30"  validate="required"/>
			<xform:select style="height:inherit" property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[!{index}].fdTimeUnit" value="minute" showStatus="edit" showPleaseSelect="false">
				<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
			</xform:select>
			<span>
		    	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
			</span>
	    </td>
	</tr>
	<%--内容行--%>
    <c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		 <td>
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
		 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${HtmlParam.fdModelName}" />
		 	<kmss:editNotifyType property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType" multi="false" value="${sysNotifyRemindMainFormListItem.fdNotifyType }"></kmss:editNotifyType>
			<input style="width:50px" type="text"  subject="${lfn:message('sys-notify:sysNotifyRemindCategory.fdBeforeTime')}"
				 name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime" value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" validate="required"/>
			<xform:select property="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" showStatus="edit" showPleaseSelect="false">
				<xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" />
			</xform:select>
			<span>
			 	<a href="javascript:void(0);" onclick="sysNotifyRemind_Delete(this);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_ContextPath}sys/notify/images/delete_btn.png" border="0"  title="<bean:message key="button.delete"/>"/></a>
			</span>
	    </td>
	</tr>
   </c:forEach> 
	<tr>
	  	<td align="left">
	    	<div style="width:100px;" >
			    <a href="javascript:void(0);"  onclick="DocList_AddRow();" class="sys_notify_add"><bean:message bundle="sys-notify" key="sysNotify.remind.add"/></a>
	     	</div>
	  	</td>
	</tr>
</table>
<script type="text/javascript">
	Com_IncludeFile('calendar.js');
</script>
<script type="text/javascript">

	function sysNotifyRemind_alert(msg){
		if (typeof(seajs) != 'undefined'){
			seajs.use('lui/dialog', function(dialog) {
				dialog.alert(msg);
			});
		}else{
			alert(msg);
		}
	}
	
	function sysNotifyRemind_validate(notifyDate){
		var fdTimeUnitMap = {
			'minute' : 60 * 1000,
			'hour' : 3600 * 1000,
			'day' : 24 * 3600 * 1000,
			'week' : 7 * 24 * 3600 * 1000
		};
		if(typeof(notifyDate) === 'string'){
			notifyDate = formatDate(notifyDate);
		}
		var dynRowObj = $('#${JsParam.fdPrefix}_${JsParam.fdKey}').find("tr");
		for(var i = 0;i < dynRowObj.length;i++){
			var row = dynRowObj.eq(i);
			var fdModelName = $(row).find(':input[name$="fdModelName"]');
			if(!fdModelName.val()){
				continue;
			}
			var fdBeforeTime = $(row).find(':input[name$="fdBeforeTime"]');
			var fdTimeUnit = $(row).find(':input[name$="fdTimeUnit"]');
			if(fdBeforeTime.val() && fdTimeUnit.val()){
				var tmpDate = notifyDate.getTime() - fdTimeUnitMap[fdTimeUnit.val()] * parseInt(fdBeforeTime.val());
				if(tmpDate <= new Date().getTime()){
					var fdTimeUnitText = fdTimeUnit.find('option:selected').text(),
						msg = '${lfn:message("sys-notify:sysNotifyRemindCommon.check.msg")}'
							.replace('%beforeTime%',fdBeforeTime.val())
							.replace('%timeUnitText%',fdTimeUnitText);
					sysNotifyRemind_alert(msg);
					return false;
				}
			}
		}
		return true;
	}
	
	//删除消息列
	function sysNotifyRemind_Delete(self){
		var tr=$(self).parents("tr").eq(0);
		tr.find("[name$='fdModelName']").val("");
		tr.find("[name$='fdBeforeTime']").attr("validate",'');
		tr.hide();
	}
</script>