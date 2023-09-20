<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>Com_IncludeFile("dialog.js|doclist.js|calendar.js|jquery.js");</script>
<html:form action="/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do">
<div id="optBarDiv">
	<c:if test="${kmCalendarPersonGroupForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="update(document.kmCalendarPersonGroupForm, 'update');">
	</c:if>
	<c:if test="${kmCalendarPersonGroupForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.kmCalendarPersonGroupForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.kmCalendarPersonGroupForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="km-calendar" key="table.kmCalendarPersonGroup"/></p>

<center>
	<table class="tb_normal" width=95%>
		<tr>
			<%--模板名称--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.docSubject"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text subject="${lfn:message('km-calendar:kmCalendarPersonGroup.docSubject')}" property="docSubject" required="true" style="width:85%" />
			</td>
		</tr>
		<tr>
			<%--群组描述 --%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdDescription"/>
			</td>
			<td>
				<xform:text property="fdDescription" style="width:85%" />
			</td>
		</tr>
		<tr>
			<%--排序号--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdOrder"/>
			</td>
			<td width="85%" colspan="3">
				<xform:text property="fdOrder" style="width:100px" />
			</td>
		</tr>
		<tr>
			<%--群组成员--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.fdPersonGroup"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true" subject="${lfn:message('km-calendar:kmCalendarPersonGroup.fdPersonGroup')}" required="true" propertyId="fdPersonGroupIds" propertyName="fdPersonGroupNames" style="width:85%" mulSelect="true"/>
			</td>
		</tr>
		<tr>
			<%--可阅读者--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.authReaders"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true" subject="${lfn:message('km-calendar:kmCalendarPersonGroup.authReaders')}" required="true" propertyId="authReaderIds" propertyName="authReaderNames" style="width:85%" mulSelect="true"/>
			</td>
		</tr>
		<tr>
			<%--可维护者--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.authEditors"/>
			</td>
			<td width="85%" colspan="3">
				<xform:address textarea="true" subject="${lfn:message('km-calendar:kmCalendarPersonGroup.authEditors')}" required="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:85%" mulSelect="true"/>
			</td>
		</tr>
		<tr>
			<%-- 说明 --%>
			<td class="td_normal_title" width="15%">
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.desc"/>
			</td>
			<td width="85%">
				<bean:message bundle="km-calendar" key="kmCalendarPersonGroup.desc.info"/>
			</td>
		</tr>
	</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<html:hidden property="beforeChangePerson" />
<script>
	Com_IncludeFile("dialog.js|jquery.js");
	var validation=$KMSSValidation();
	seajs.use([
	 	      'km/calendar/resource/js/dateUtil',
	 	      'km/calendar/resource/js/arrayUtil',
	 	      'lui/jquery',
	 	      'lui/dialog',
	 	      'lui/topic',
	 	      'lui/util/env'
	 	        ],function(dateUtil,arrayUtil,$,dialog,topic,env){
		window.update = function(formObj, commitType){
			var beforePerson=LUI.toJSON($('[name="beforeChangePerson"]').val());
			var beforePersons=[],afterPersons=[],
				totalPersons={};//totalPersons存储id:name
			//变更前的人员
			var beforeIds=convertToArray(beforePerson['fdPersonGroupIds']),
				beforeNames=convertToArray(beforePerson['fdPersonGroupNames']);
			for(var i=0;i<beforeIds.length;i++){
				beforePersons.push(beforeIds[i]);
				totalPersons[beforeIds[i]]=beforeNames[i];
			}
			//变更后的人员
			var afterIds=convertToArray($('[name="fdPersonGroupIds"]').val()),
				afterNames=convertToArray($('[name="fdPersonGroupNames"]').val());
			for(var i=0;i<afterIds.length;i++){
				afterPersons.push(afterIds[i]);
				totalPersons[afterIds[i]]=afterNames[i];
			}
			var newPersons=arrayUtil.minus(afterPersons,beforePersons,true);//新增人员
			var deletePersons=arrayUtil.minus(beforePersons,afterPersons,true);//剔除人员
			
			var str="",names="",index=1;
			
			//新增人员提示
			if( newPersons.length>0 ){
				for(var i=0 ;i< newPersons.length;i++){
					names+=totalPersons[newPersons[i]]+";";
				}
				names=names.substring(0,names.length-1);
				names = '<span class="com_author">'+names+'</span>';
				str+=index+"、"+names+"的日程由归属人在前端界面进行同步<br/><br/>";
				index++;
				names="";
			}
			//剔除人员提示
			if(deletePersons.length>0){
				for(var i=0 ;i< deletePersons.length;i++){
					names+=totalPersons[deletePersons[i]]+";";
				}
				names=names.substring(0,names.length-1);
				names = '<span class="com_author">'+names+'</span>';
				str+=index+"、"+names+"在该群组下未来的日程会被删除<br/><br/>";
				index++;
				names="";
			}
			
			if(str){
				var text = str;
				var text = '<div style="text-align: left; max-height: 128px; overflow: hidden;">'+text+'</div>';
				
				dialog.confirm(text,function(value){
					if(value==true){
						Com_Submit(formObj, commitType);
					}
				});
			}else{
				Com_Submit(formObj, commitType);
			}
		};
		//转换成数组
		function convertToArray(){
			var slice=Array.prototype.slice,
				args=slice.call(arguments,0),
				arr=[];
			for(var i=0;i<args.length;i++){
				if(args[i]){
					var ids=args[i].split(';');
					for(var j=0;j<ids.length;j++){
						if(ids[j])
							arr.push(ids[j]);
					}
				}
			}
			return arr;
		}
	});
	
</script>
	
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>