<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
	</template:replace>
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<div style="margin-top:25px">
<center>

<script type="text/javascript">
window.onload = function(){
	var printFirstWidth = 100;
	var printSecondWidth = 100;
	var $printTypeTd = $("#printTypeTr").children('td');
	$printTypeTd.each(function(j,item){
		if(j==0){
			printFirstWidth = item.offsetWidth;
		}
		if(j==1){
			printSecondWidth = item.offsetWidth;
		}
	});
	var $typeLable = $("#fdNotifyTypetd").children('label');
	$typeLable.each(function(j,item){
		    if(j==0){
			    item.style.width=(printFirstWidth-5)+"px";
			    item.style.display="inline-block";
		    }else{
		    	item.style.width=(printSecondWidth-5)+"px";
			    item.style.display="inline-block";
		    }
	 });
	
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
		alert("请至少选择一个默认打印选项!");
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
	var fdNotifyType=document.getElementsByName("fdNotifyType")[0];
	var email=document.getElementsByName("email")[0];
	var msg=document.getElementsByName("msg")[0];
	
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

<table style="border: 0px;" width=90%>
	<tr style="height: 45px;line-height: 45px;">
		<td colspan="5" style="font-family: PingFangSC-Medium;font-size: 16px;color: #111F2C;font-weight:bolder;">参数设置<td>
	</tr>
	<tr style="height: 45px;line-height: 45px;">
		<td colspan="5">
			<bean:message bundle="km-review" key="kmReview.config.notify.default"/>
			<%-- <span style="font-family: PingFangSC-Regular;font-size: 12px;color: rgba(17,31,44,0.56);letter-spacing: 0;line-height: 12px;"><bean:message bundle="km-review" key="kmReview.config.notify.hint.ding"/></span> --%>
		</td>
	</tr>
	<tr class="lui_config_tr_h">	
		<td colspan="5" id="fdNotifyTypetd">
			<kmss:editNotifyType property="value(fdNotifyType)" />
		</td>
	</tr>
	
	<tr style="height: 45px;line-height: 45px;border-top:#F5F5F5 solid 1px;">
		<td colspan="5">
			<bean:message bundle="km-review" key="kmReview.config.print"/>
			<span style="font-family: PingFangSC-Regular;font-size: 12px;color: rgba(17,31,44,0.56);letter-spacing: 0;line-height: 12px;"><bean:message bundle="km-review" key="kmReview.config.print.hint.ding"/></span>
		</td>
	</tr>	
	<tr class="lui_config_tr_h" id="printTypeTr">
		<td>
			<html:hidden property="value(subject)"/>
			<label>
			<input name="subject" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.subject"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(base)"/>
			<label>
			<input name="base" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.base"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(info)"/>
			<label>
			<input  name="info" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.info"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(note)"/>
			<label>
			<input name="note" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.note"/>
			</label>
		</td>
		<td>
			<html:hidden property="value(qrcode)"/>
			<label>
			<input name="qrcode" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.qrcode"/>
			</label>
		</td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}"  height="35" width="120" onclick="commitMethod();" order="1" styleClass="lui_toolbar_btn_gray" style="background: #0089FF!important;border-radius: 13px;"></ui:button>
</div>
</center>
</div>
</html:form>
</template:replace>
</template:include>