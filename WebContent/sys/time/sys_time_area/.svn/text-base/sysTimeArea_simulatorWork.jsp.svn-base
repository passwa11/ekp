<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<link href="resource/css/maincss.css" rel="stylesheet">
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
</script>
<style>
.lui_workforce_management-table-remark{
  font-size: 12px;
  color: red;
  padding: 10px 0;
  text-align: left;
}
</style>
<div id="optBarDiv">
	<input type=button value="${lfn:message('sys-organization:sysOrgRoleConf.simulator.calculate')}"
			onclick="startCalculate();">
	<input type="button" value="<bean:message key="button.close"/>" onclick="top.close();">
</div>

<p class="txttitle"><bean:message  bundle="sys-time" key="calendar.simulator.worktime"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-organization" key="sysOrgRoleConf.simulator.user"/>
		</td><td width=80%>
			<input name="fdUserId" type="hidden" value="">
			<input name="fdUserName" class="inputsgl" readonly value="">
			<a href="#" onclick="Dialog_Address(false, 'fdUserId', 'fdUserName', ';', ORG_TYPE_PERSON, null, null, null, true);">
				<bean:message key="dialog.selectOrg"/> <span class="txtstrong">*</span>
			</a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-time" key="calendar.simulator.worktime.start.time"/>
		</td><td width=80%>
			<xform:datetime property="startTime" dateTimeType="datetime" showStatus="edit" htmlElementProperties="onchange='checkChangeTime()'"/><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message  bundle="sys-time" key="calendar.simulator.worktime.end.time"/>
		</td><td width=80%>
			<xform:datetime  property="endTime" dateTimeType="datetime" showStatus="edit" validators="checkEndTime" htmlElementProperties="onchange='checkChangeTime()'"/><span class="txtstrong">*</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.result"/>
		</td><td width=80% id="fd" style="vertical-align: top;height: 182px">
          	 <span id="TD_Result"></span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" style="display: none;color:#0D93D6;" id="ckmx" class="ckmx">查看排班明细</a>
             <iframe id="workTimeContent" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.help"/>
		</td><td width=80%>
			 <div class="lui_workforce_management-table-remark">
                ${lfn:message('sys-time:sysTimeArea.descriptption')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.area')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.time')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.voa')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.patch')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.patch5')}<br>
			    ${lfn:message('sys-time:sysTimeArea.descriptption.patch6')}<br>
			    
			    ${lfn:message('sys-time:sysTimeArea.notice')} <br>
			    ${lfn:message('sys-time:sysTimeArea.notice1')}<br>
			    ${lfn:message('sys-time:sysTimeArea.notice2')}<br>
			    
			    ${lfn:message('sys-time:sysTimeArea.notice.example')}<br>
			    ${lfn:message('sys-time:sysTimeArea.notice.example1')}<br>
			    ${lfn:message('sys-time:sysTimeArea.notice.example2')}<br>
             </div>
		</td>
	</tr>
</table>
<br>
</center>
<script>
function startCalculate(){
	var h = $("#fd").height();
	$("#ckmx").hide();
	$("#workTimeContent").hide();
	if($("#workTimeContent").attr("display")=="block"){
		$("#fd").height(h);
	}else{
		$("#fd").height(198);
	}
	var fdUserId = document.getElementsByName("fdUserId")[0].value;
	if(fdUserId==""){
		alert('<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgUserNotNull"/>');
		return;
	}
	var startTime = document.getElementsByName("startTime")[0].value;
	if(startTime==""){
		alert('<bean:message bundle="sys-time" key="calendar.simulator.worktime.start.time.notnull"/>');
		return;
	}
	var endTime = document.getElementsByName("endTime")[0].value;
	if(endTime==""){
		alert('<bean:message bundle="sys-time" key="calendar.simulator.worktime.end.time.notnull"/>');
		return;
	}
	$(TD_Result).text("");
	var kmssdata = new KMSSData();
	kmssdata.AddHashMap({userId:fdUserId,startTime:startTime,endTime:endTime,type:"work"});
	kmssdata.SendToBean("sysTimeAreaSimulator", function(rtnData){
		if(rtnData==null)
			return;
		var rtnVal = rtnData.GetHashMapArray();
		if(rtnVal.length==0)
			return;
		if(rtnVal[0].noperson=="0"){
			$("#ckmx").show();			
		}else{
			$("#ckmx").hide();
			$("#workTimeContent").hide();
		}
		$(TD_Result).html(rtnVal[0].message);
	});
}
	
	/*内选项卡*/
	$('#ckmx').click(function() {
		 var fdUserId = document.getElementsByName("fdUserId")[0].value;
		if(fdUserId==""){
			alert('<bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.orgUserNotNull"/>');
			return;
		}
		var startTime = document.getElementsByName("startTime")[0].value;
		if(startTime==""){
			alert('<bean:message bundle="sys-time" key="calendar.simulator.worktime.start.time.notnull"/>');
			return;
		}
		var endTime = document.getElementsByName("endTime")[0].value;
		if(endTime==""){
			alert('<bean:message bundle="sys-time" key="calendar.simulator.worktime.end.time.notnull"/>');
			return;
		}
		var param = "&fdUserId="+fdUserId+"&startTime="+startTime+"&endTime="+endTime;
	  $("#workTimeContent").attr("src",'<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=listTime"/>'+param);
	  iFrameHeight("workTimeContent");
	  $("#workTimeContent").show();
	})
	$(function(){
		window.setInterval("iFrameHeight('workTimeContent')",100);
	});
	/* ===============iframe高度自适应=============*/
	function iFrameHeight(id) {
		try {
		 var browserVersion = window.navigator.userAgent.toUpperCase();
		 var isOpera = false, isFireFox = false, isChrome = false, isSafari = false, isIE = false;
	    isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
        isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
        isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
        isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
        if (!!window.ActiveXObject || "ActiveXObject" in window)
            isIE = true;
        var iframe = document.getElementById(id);
        var bHeight = 0;
        if (isChrome == false && isSafari == false){
        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
        		bHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].scrollHeight+100;
        	}else{
	            bHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].scrollHeight;
        	}
        }
        var dHeight = 0;
        if (isFireFox){
        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].offsetHeight + 102;
        	}else{
	            dHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].offsetHeight + 2;
        	}
        }else if (!isIE && !isOpera){
        	if(iframe.contentWindow.document.getElementsByTagName("table").length>1){
        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[1].scrollHeight+100;
        	}else{
        		dHeight = iframe.contentWindow.document.getElementsByTagName("table")[0].scrollHeight;
        	}
            
        }else if (isIE && -[1,] ) {
        } else{//ie9+
            bHeight += 3;
        }
	
        var height = Math.max(bHeight, dHeight);
        iframe.style.height = height + "px";
		} catch (ex) {
        }
	}
</script>
<script type="text/javascript">
Com_IncludeFile("validation.js|plugin.js|validation.jsp ");
</script>
<script type="text/javascript">
			var validation = $KMSSValidation();
			validation.addValidator('checkEndTime','<bean:message bundle="sys-time" key="calendar.simulator.worktime.end.time.small.start"/>',function(){
				var st = document.getElementsByName("startTime")[0].value;
	            var et = document.getElementsByName("endTime")[0].value;
	            if(st==""||et==""){
	            	return true;
	            }else if (st > et) {
	                return false;
	            }else{
	            	return true;
	            }
            }); 
			//修改时间，触发开始和结束时间校验
			function checkChangeTime(){
				validation.validateElement($('[name="endTime"]')[0]);
			}
</script>


<%@ include file="/resource/jsp/edit_down.jsp"%>
