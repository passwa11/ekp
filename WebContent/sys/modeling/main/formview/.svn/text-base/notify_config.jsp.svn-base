<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<div style="margin-top:25px">
<p class="configtitle">
<bean:message bundle="sys-organization" key="sysOrgConfig"/>
</p>
<center>
<script type="text/javascript">
window.onload = function(){
	var subject=document.getElementsByName("subject")[0];
	var base=document.getElementsByName("base")[0];
	var info=document.getElementsByName("info")[0];
	var note=document.getElementsByName("note")[0];
	var qrcode=document.getElementsByName("qrcode")[0];
	var subjectvalue=document.getElementsByName("value(subject)")[0];
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	if(subjectvalue.value=="0"){
		subject.checked=false;
	}else subject.checked=true;
	if(basevalue.value=="0"){
		base.checked=false;
	}else base.checked=true;
	if(infovalue.value=="0"){
		info.checked=false;
	} else 	info.checked=true;
	if(notevalue.value=="0"){
		note.checked=false;
	} else 	note.checked=true;
	if(qrcodevalue.value=="0"){
		qrcode.checked=false;
	} else 	qrcode.checked=true;
}

function commitMethod(){
	var subjectvalue=document.getElementsByName("value(subject)")[0];
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	if(subjectvalue.value=='0'&&basevalue.value=='0'&&infovalue.value=='0'&&notevalue.value=='0'&&qrcodevalue.value=='0'){
		alert("${lfn:message('sys-modeling-main:modeling.select.default.printing.option') }");
		return false;
	}
	   Com_Submit(document.sysAppConfigForm, 'update');
}
function changeValue(thisObj){
	var subject=document.getElementsByName("subject")[0];
	var base=document.getElementsByName("base")[0];
	var info=document.getElementsByName("info")[0];
	var note=document.getElementsByName("note")[0];
	var qrcode=document.getElementsByName("qrcode")[0];
	var subjectvalue=document.getElementsByName("value(subject)")[0];
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	if(subject.checked){
		subjectvalue.value="subject";
		}else{
		subjectvalue.value="0";
	}
	if(base.checked){
		basevalue.value="base";
		}else{
		basevalue.value="0";
	}
	if(info.checked){
		infovalue.value="info";
	    }else{
	    infovalue.value="0";
	}
	if(note.checked){
		notevalue.value="note";
	    }else{
	    notevalue.value="0";
	}
	if(qrcode.checked){
		qrcodevalue.value="qrcode";
	    }else{
	    qrcodevalue.value="0";
	}
}

</script>
<table class="tb_normal" width=90%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-modeling-main" key="main.config.notify.default"/>
		</td>
		<td colspan="5">
			<kmss:editNotifyType property="value(fdNotifyType)"/>
			<span class="txtstrong"><bean:message bundle="sys-modeling-main" key="main.config.notify.hint"/></span>			
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20% rowspan="2">
			<bean:message bundle="sys-modeling-main" key="main.config.print"/>
		</td>
		<td>
			<html:hidden property="value(subject)"/>
			<label>
			<input name="subject" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="sys-modeling-main" key="main.config.subject"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(base)"/>
			<label>
			<input name="base" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="sys-modeling-main" key="main.config.base"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(info)"/>
			<label>
			<input  name="info" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="sys-modeling-main" key="main.config.info"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(note)"/>
			<label>
			<input name="note" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="sys-modeling-main" key="main.config.note"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(qrcode)"/>
			<label>
			<input name="qrcode" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="sys-modeling-main" key="main.config.qrcode"/>
			</label>
		</td>
	</tr>
	<tr>
		<td colspan="5">
			<span class="txtstrong"><bean:message bundle="sys-modeling-main" key="main.config.print.hint"/></span>	
		</td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();" order="1" ></ui:button>
</div>
</center>
</div>
</html:form>
</template:replace>
</template:include>