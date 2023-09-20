<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<!-- czk2019 -->
<style type="text/css"> 
ul, li{list-style-type: none;} 
ul{padding:1px;margin:0px 0px 0px 0px;height: 100px;width: 100%;}
li { float:left;width: 50%;}
.clear {clear:both; height: 0px;}
</style>
<center>

<table class="tb_normal" width="100%" id="tab_person_setting">
	<tr>
		<%--人员选择--%>
		<td width="15%" style="height: 40; padding-left: 15px;">
			<bean:message bundle="sys-relation" key="sysRelationEntry.person.text"/>
		</td>
		<td style="height: 40">
			<input type='hidden' name='fdPersonIds' />
			<input type='text' name='fdPersonNames' readonly="readonly" class="inputsgl" style="width:80%"  />
			<span class="txtstrong">*</span>
			 <a href="#" onclick="Dialog_Address(true, 'fdPersonIds','fdPersonNames', ';',ORG_TYPE_PERSON);"> 
				<bean:message key="dialog.selectOrg" />
			</a>
			<a href=# onclick="clearField('fdPersonIds;fdPersonNames');"><bean:message bundle="sys-relation" key="sysRelationEntry.person.clearTime"/></a>
		</td>
	</tr>
	
</table>
<br /><br />
<input type="button" class="btnopt" value="<bean:message key="button.ok"/>" onclick="doOK();" />
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="reset" class="btnopt" value="<bean:message key="button.close" />" onclick="Com_CloseWindow();" />
</center>
<%@ include file="sysRelationPersonPage_edit_script.jsp"%>
<%@ include file="/resource/jsp/edit_down.jsp" %>