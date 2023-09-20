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
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	var statusFlag=document.getElementsByName("statusFlag")[0];
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
	var base=document.getElementsByName("base")[0];
	var info=document.getElementsByName("info")[0];
	var note=document.getElementsByName("note")[0];
	var qrcode=document.getElementsByName("qrcode")[0];
	var subjectvalue=document.getElementsByName("value(subject)")[0];
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	var statusFlagvalue=document.getElementsByName("value(statusFlag)")[0];
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	if(subjectvalue.value=="0"){
		subject.checked=false;
	}else subject.checked=true;
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	if(statusFlagvalue.value=="0"){
		statusFlag.checked=false;
	}else statusFlag.checked=true;
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
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
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	var statusFlagvalue=document.getElementsByName("value(statusFlag)")[0];
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	if(subjectvalue.value=='0'&&basevalue.value=='0'&&infovalue.value=='0'&&notevalue.value=='0'&&qrcodevalue.value=='0'&&statusFlagvalue.value=='0'){
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
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	var statusFlag=document.getElementsByName("statusFlag")[0];
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
	var subjectvalue=document.getElementsByName("value(subject)")[0];
	var basevalue=document.getElementsByName("value(base)")[0];
	var infovalue=document.getElementsByName("value(info)")[0];
	var notevalue=document.getElementsByName("value(note)")[0];
	var qrcodevalue=document.getElementsByName("value(qrcode)")[0];
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	var statusFlagvalue=document.getElementsByName("value(statusFlag)")[0];
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
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
	/*#139365-打印时能够打印出来文档的状态标记-开始*/
	if(statusFlag.checked){
		statusFlagvalue.value="statusFlag";
	}else{
		statusFlagvalue.value="0";
	}
	/*#139365-打印时能够打印出来文档的状态标记-结束*/
}

Com_Parameter.event["submit"].push(function(){
	var form = document.sysAppConfigForm;
	for(var i=0;i<form.length;i++){
		if(form[i].value == ""){
			form[i].value = "false";
		}
	}
	return true;
})

</script>
<table class="tb_normal" width=90%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="km-review" key="kmReview.enable.setting"/>
		</td>
		<td colspan="6">
			<!-- 关联信息 -->
			<c:if test="${sysAppConfigForm.map.enableSysRelation != null}">
				<xform:checkbox property="value(enableSysRelation)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysRelation"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysRelation == null}">
				<xform:checkbox property="value(enableSysRelation)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysRelation"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 日程同步 -->
			<c:if test="${sysAppConfigForm.map.enableSysAgenda != null}">
				<xform:checkbox property="value(enableSysAgenda)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysAgenda"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysAgenda == null}">
				<xform:checkbox property="value(enableSysAgenda)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysAgenda"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 打印模板 -->
			<c:if test="${sysAppConfigForm.map.enableSysPrint != null}">
				<xform:checkbox property="value(enableSysPrint)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysPrint"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysPrint == null}">
				<xform:checkbox property="value(enableSysPrint)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysPrint"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 流程归档 -->
			<kmss:ifModuleExist path="/sys/archives/">
				<c:if test="${sysAppConfigForm.map.enableKmArchives != null}">
					<xform:checkbox property="value(enableKmArchives)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmArchives"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableKmArchives == null}">
					<xform:checkbox property="value(enableKmArchives)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmArchives"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 流程沉淀 -->
			<kmss:ifModuleExist path="/kms/multidoc/">
				<c:if test="${sysAppConfigForm.map.enableKmsMultidoc != null}">
					<xform:checkbox property="value(enableKmsMultidoc)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmsMultidoc"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableKmsMultidoc == null}">
					<xform:checkbox property="value(enableKmsMultidoc)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmsMultidoc"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 规则设置 -->
			<c:if test="${sysAppConfigForm.map.enableSysRule != null}">
				<xform:checkbox property="value(enableSysRule)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysRule"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysRule == null}">
				<xform:checkbox property="value(enableSysRule)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysRule"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 提醒中心 -->
			<kmss:ifModuleExist path="/sys/remind/">
				<c:if test="${sysAppConfigForm.map.enableSysRemind != null}">
					<xform:checkbox property="value(enableSysRemind)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableSysRemind"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableSysRemind == null}">
					<xform:checkbox property="value(enableSysRemind)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableSysRemind"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 智能检查 -->
			<kmss:ifModuleExist path="/sys/iassister/">
				<c:if test="${sysAppConfigForm.map.enableSysIassister != null}">
					<xform:checkbox property="value(enableSysIassister)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableSysIassister"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableSysIassister == null}">
					<xform:checkbox property="value(enableSysIassister)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableSysIassister"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 相关沟通 -->
			<kmss:ifModuleExist path = "/km/collaborate/">
				<c:if test="${sysAppConfigForm.map.enableKmCollaborate != null}">
					<xform:checkbox property="value(enableKmCollaborate)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmCollaborate"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableKmCollaborate == null}">
					<xform:checkbox property="value(enableKmCollaborate)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmCollaborate"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 相关督办 -->
			<kmss:ifModuleExist path="/km/supervise/">
				<c:if test="${sysAppConfigForm.map.enableKmSupervise != null}">
					<xform:checkbox property="value(enableKmSupervise)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmSupervise"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableKmSupervise == null}">
					<xform:checkbox property="value(enableKmSupervise)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableKmSupervise"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<!-- 流程传阅 -->
			<c:if test="${sysAppConfigForm.map.enableSysCirculation != null}">
				<xform:checkbox property="value(enableSysCirculation)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysCirculation"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysCirculation == null}">
				<xform:checkbox property="value(enableSysCirculation)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysCirculation"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 流程收藏 -->
			<c:if test="${sysAppConfigForm.map.enableSysBookmark != null}">
				<xform:checkbox property="value(enableSysBookmark)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysBookmark"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysBookmark == null}">
				<xform:checkbox property="value(enableSysBookmark)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysBookmark"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 访问统计 -->
			<c:if test="${sysAppConfigForm.map.enableSysReadlog != null}">
				<xform:checkbox property="value(enableSysReadlog)" showStatus="edit">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysReadlog"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<c:if test="${sysAppConfigForm.map.enableSysReadlog == null}">
				<xform:checkbox property="value(enableSysReadlog)" showStatus="edit" value="true">
					<xform:simpleDataSource value="true">
						<bean:message bundle="km-review" key="kmReview.enable.enableSysReadlog"/>
					</xform:simpleDataSource>
				</xform:checkbox>
			</c:if>
			<!-- 数据图表 -->
			<kmss:ifModuleExist path="/dbcenter/echarts/">
				<c:if test="${sysAppConfigForm.map.enableDbcenterEcharts != null}">
					<xform:checkbox property="value(enableDbcenterEcharts)" showStatus="edit">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableDbcenterEcharts"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
				<c:if test="${sysAppConfigForm.map.enableDbcenterEcharts == null}">
					<xform:checkbox property="value(enableDbcenterEcharts)" showStatus="edit" value="true">
						<xform:simpleDataSource value="true">
							<bean:message bundle="km-review" key="kmReview.enable.enableDbcenterEcharts"/>
						</xform:simpleDataSource>
					</xform:checkbox>
				</c:if>
			</kmss:ifModuleExist>
			<br>
			<span class="txtstrong">（<bean:message bundle="km-review" key="kmReview.enable.settingTip"/>）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="km-review" key="kmReview.config.notify.default"/>
		</td>
		<td colspan="6">
			<kmss:editNotifyType property="value(fdNotifyType)"/>
			<span class="txtstrong"><bean:message bundle="km-review" key="kmReview.config.notify.hint"/></span>			
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=20% rowspan="2">
			<bean:message bundle="km-review" key="kmReview.config.print"/>
		</td>
		<td>
			<html:hidden property="value(subject)"/>
			<label>
			<input name="subject" type="checkbox" onclick="changeValue(this);"/>
			<bean:message bundle="km-review" key="kmReview.config.subject"/>
			</label>
		</td>
		<%--#139365-打印时能够打印出来文档的状态标记-开始--%>
		<td>
			<html:hidden property="value(statusFlag)"/>
			<label>
				<input name="statusFlag" type="checkbox" onclick="changeValue(this);"/>
				<bean:message bundle="km-review" key="kmReview.config.statusFlag" />
			</label>
		</td>
		<%--#139365-打印时能够打印出来文档的状态标记-结束--%>
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
	<tr>
		<td colspan="6">
			<span class="txtstrong"><bean:message bundle="km-review" key="kmReview.config.print.hint"/></span>	
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