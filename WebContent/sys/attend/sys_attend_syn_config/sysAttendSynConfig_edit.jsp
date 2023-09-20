<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style type="text/css">
	.sysAttendSynConfig-item { 
		display: flex;
	    display: -webkit-flex;
	    align-items: center;
	    margin: 5px 0;
	    padding-left: 10px;
    }
    .sysAttendSynConfig-item.fdClientLimit,.sysAttendSynConfig-item.fdDeviceLimit{display:inline;}
    .fdClientLimit div[data-lui-type="lui/switch!Switch"]{display:inline;}
    .fdDeviceLimit div[data-lui-type="lui/switch!Switch"]{display:inline;}
</style>
<html:form action="/sys/attend/sys_attend_syn_config/sysAttendSynConfig.do">
<html:hidden property="fdId"/>
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysAttend.tree.config.sync" bundle="sys-attend" /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
	   <td class="td_normal_title" width=15%>
	       <bean:message key="sysAttendSynConfig.fdSynType"  bundle="sys-attend"/>
	   </td>
	   <td colspan=3>
	       <xform:select property="fdSynType" showStatus="edit" showPleaseSelect="false">
	           <c:if test="${isEnableDingConfig eq true}">
	         <xform:simpleDataSource value="dingding">${ lfn:message('sys-attend:sysAttendSynConfig.config.source.dingding') }</xform:simpleDataSource>
	        </c:if>
	        <c:if test="${isEnableWxConfig eq true}">
	         <xform:simpleDataSource value="qywx">${ lfn:message('sys-attend:sysAttendSynConfig.config.source.qywx') }</xform:simpleDataSource>
	        </c:if>
	       </xform:select>
	   </td>
    </tr>
	<tr>
	   <td class="td_normal_title" width=15%>
	       <bean:message key="sysAttendSynConfig.fdEnableRecord"  bundle="sys-attend"/>
	   </td>
	   <td colspan=3>
	       <table>
	           <tr>
	               <td>
	                   <div class='sysAttendSynConfig-item'>
	                       <ui:switch property="fdEnableRecord" 
	                           enabledText="${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.enable') }"
	                           disabledText="${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.disable') }"
	                           onValueChange="changeRecordConfig()">
                            </ui:switch>
                        </div>
                    </td>
                </tr>
                <tr id="fdRecordTime" style="display: none">
                    <td>
                        <div class='sysAttendSynConfig-item'>
                            ${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.time') }${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.from') }
                            <xform:datetime onValueChange="onTimeChange" property="fdStartTime" dateTimeType="date"
                                validators="beforeToday" subject="${ lfn:message('sys-attend:sysAttendSynConfig.fdStartTime') }"></xform:datetime>
                            ${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.to') }
                            <xform:datetime property="fdEndTime" dateTimeType="date"
                                validators="afterStartTime" subject="${ lfn:message('sys-attend:sysAttendSynConfig.fdEndTime') }"></xform:datetime>
	                        <ui:button text="${lfn:message('sys-attend:sysAttendSynConfig.config.syn.button') }" onclick="cleanTime();" style="vertical-align: top;padding-left: 10px;"></ui:button>
	                        <span style="color: #999999;padding-left: 10px;">${ lfn:message('sys-attend:sysAttendSynConfig.config.lastSynTime') }</span>
	                        <span style="color: #999999;padding-left: 10px;"><xform:datetime property="fdSyncTime" dateTimeType="date" showStatus="view"/></span>
                        </div>
                    </td>
                </tr>
                <tr id="configRecordTip" style="display: none">
                    <td>
                        <div style="color: #999999;padding-left: 10px;">
                            <bean:message key="sysAttendSynConfig.config.record.tip"  bundle="sys-attend"/><br>
                        </div>
                    </td>
                </tr>
            </table>
       </td>
    </tr>
    <tr style="display:none;">
        <td class="td_normal_title" width=15%>
            <bean:message key="sysAttendSynConfig.fdEnableCategory"  bundle="sys-attend"/>
        </td>
        <td colspan=3>
            <table>
	            <tr>
	               <td>
	                   <div class='sysAttendSynConfig-item'>
	                       <ui:switch property="fdEnableCategory" 
				              enabledText="${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.enable') }"
				              disabledText="${ lfn:message('sys-attend:sysAttendSynConfig.config.syn.disable') }">
				           </ui:switch>
				           <span style="color: #999999;padding-left: 10px;">
				               <bean:message key="sysAttendSynConfig.config.category.tip"  bundle="sys-attend"/>
				           </span>
			           </div>
	               </td>
	            </tr>
            </table>
        </td>
    </tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
    <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="submint();" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
<script type="text/javascript">

function submint() {
	Com_Submit(document.sysAttendSynConfigForm, 'update');
}

window.changeRecordConfig = function() {
	var fdEnableRecord = $(':hidden[name="fdEnableRecord"]').val(),
	fdStartTime = $(':text[name="fdStartTime"]'),
	fdEndTime = $(':text[name="fdEndTime"]'),
	fdRecordTime = $('#fdRecordTime'),
	configRecordTip = $('#configRecordTip');
	if (fdEnableRecord == 'true') {
		fdStartTime.removeAttr('disabled');
		fdEndTime.removeAttr('disabled');
		fdRecordTime.show();
		configRecordTip.show();
	} else {
		fdStartTime.attr('disabled','disabled');
		fdEndTime.attr('disabled','disabled');
		fdRecordTime.hide();
		configRecordTip.hide();
	}
}

var configValidation = $KMSSValidation();

configValidation.addValidator('afterStartTime', "${ lfn:message('sys-attend:sysAttendSynConfig.config.validate.time.afterStartTime') }", function(v,e,o){
	var fdStartTime = $('input[name="fdStartTime"]:enabled').val();
	if(fdStartTime && v) {
		return fdStartTime <= v;
	} else {
		return true;
	}
});

configValidation.addValidator('beforeToday','<bean:message bundle="sys-attend" key="sysAttendSynConfig.config.stat.time.tip1" />',function(v, e, o){
    if(!v){
        return true;
    }
    var valueObj=Com_GetDate(v, 'date', Com_Parameter.Date_format);
    var now = new Date();
    now.setHours(0,0,0,0)
    if(valueObj.getTime()<now.getTime()){
        return true;    
    }
    return false;
});

configValidation.addValidator('beforeEndToday','<bean:message bundle="sys-attend" key="sysAttendSynConfig.config.stat.time.tip2" />',function(v, e, o){
    if(!v){
        return true;
    }
    var valueObj=Com_GetDate(v, 'date', Com_Parameter.Date_format);
    var now = new Date();
    now.setHours(0,0,0,0)
    if(valueObj.getTime()<now.getTime()){
        return true;    
    }
    return false;
});

window.onTimeChange = function(value,element){
	configValidation.validateElement(element);
	if(element.name=='fdStartTime'){
		configValidation.validateElement($('[name="fdEndTime"]')[0]);
	}
};

function cleanTime(){
    if(confirm("<bean:message key='sysAttendSynConfig.config.clearMsg' bundle='sys-attend' />")){
        var url = '<c:url value="/sys/attend/sys_attend_syn_config/sysAttendSynConfig.do?method=cleanTime" />';
        $.ajax({
           type: "POST",
           url: url,
           async:false,
           dataType: "json",
           success: function(data){
                if(data.status=="1"){
                    alert("<bean:message key='sysAttendSynConfig.config.clearSuccess' bundle='sys-attend' />");
                }else{
                    alert(data.msg);
                }
           }
        });
    }
}

LUI.ready(function(){
	changeRecordConfig();
});
</script>
</template:replace>
</template:include>