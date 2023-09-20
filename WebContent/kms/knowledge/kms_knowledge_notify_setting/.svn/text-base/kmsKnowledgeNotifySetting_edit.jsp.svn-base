<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils"%>
<%@ page import="com.landray.kmss.kms.common.util.KmsBorrowCategoryUtil" %>
<%@ page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
	.inputselectmul textarea {
		border: 0px !important;
	}
	.fdNotifyPersonTypeTr {
		display: none;
	}
	.fdNotifyPersonTypeTr .fdNotifyPerson_customize {
		display: none;
	}
</style>

<c:set var="param2" value="0"></c:set>
<c:set var="param3" value="0"></c:set>
<c:set var="param4" value="0"></c:set>

<c:choose>
	<c:when test="${kmsKnowledgeNotifySettingForm.param2 == '1'  }">
		<c:set var="param2" value="1" />
	</c:when>
	<c:when test="${kmsKnowledgeNotifySettingForm.param3 == '1'  }">
		<c:set var="param3" value="1" />
	</c:when>
	<c:when test="${kmsKnowledgeNotifySettingForm.param4 == '1'  }">
		<c:set var="param4" value="1" />
	</c:when>

</c:choose>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">
					${lfn:message('kms-knowledge-notifySetting:kms.knowledge.fdNotify.setting.update.2')}
			</span>
		</h2>
		<html:form action="/kms/knowledge/kms_knowledge_notify_setting/kmsKnowledgeNotifySetting.do">
			<center>

				<table class="tb_normal" width=95%>
					<c:choose>
						<c:when test="${showOnlyContent == true}">
							<html:hidden property="param1" />
							<html:hidden property="fdId" />

							<tr>
								<td class="tb_normal_title" width="15%">
									<div class="knowledge_notify">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify")}</div>
								</td>
								<td width="85%"  colspan="3">
									<input type="radio" name="param2" value="1"  onclick="onFdIsNotifyClick('1')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify.yes")}
									<input type="radio" name="param2" value="0" checked onclick="onFdIsNotifyClick('0')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify.no")}
									<span class="com_help">${lfn:message('kms-knowledge:kmsKnowledgeBase.fdNotify.tip')}</span>
								</td>
							</tr>
							<tr class="fdNotifyPersonTypeTr">
								<td class="tb_normal_title" width="15%">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType")}</td>
								<td width="85%"  colspan="3">
									<span>
										<input type="radio" name="param3" value="0" checked onclick="onFdNotifyPersonType('0')" checked>${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.0")}
										<input type="radio" name="param3" value="1" onclick="onFdNotifyPersonType('1')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.1")}
										<input type="radio" name="param3" value="2" onclick="onFdNotifyPersonType('2')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.2")}
									</span>

									<br/>
									<span class="com_help com_help_0" style="font-size: 12px">文档可阅读者为空时，不发送通知</span>
									<div class="fdNotifyPerson_customize">
										<xform:address
												subject="${lfn:message('kms-knowledge:kmsKnowledgeBase.fdNotifyPersons')}"
												showStatus="edit"
												orgType="ORG_TYPE_PERSON"
												textarea="true"
												mulSelect="true"
												propertyId="fdNotifyPersonIds"
												propertyName="fdNotifyPersonNames"
												style="width:97%;height:90px;" >
										</xform:address>
									</div>
								</td>
							</tr>
							<tr class="fdNotifyPersonTypeTr">
								<td class="tb_normal_title" width="15%">
									<div class="knowledge_notify">${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way")}</div>
								</td>
<%--								<td width="85%"  colspan="3">--%>
<%--									<input type="radio" name="param4" checked value="0" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.0")}--%>
<%--									<input type="radio" name="param4" value="1" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.1")}--%>
<%--									<input type="radio" name="param4" value="2" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.2")}--%>
<%--								</td>--%>
								<td>
									<kmss:editNotifyType property="param4" required="true"/>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<ui:content title="通知" id="kmsKnowledgeNotify">
								<table class="tb_normal" width=100%>
									<html:hidden property="param1" />
									<html:hidden property="fdId" />
									<tr>
										<td class="tb_normal_title" width="15%">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify")}</td>
										<td width="85%">
											<input type="radio" name="param2" value="1"  onclick="onFdIsNotifyClick('1')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify.yes")}
											<input type="radio" name="param2" value="0" checked onclick="onFdIsNotifyClick('0')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdIsNotify.no")}
											<span class="com_help">${lfn:message('kms-knowledge:kmsKnowledgeBase.fdNotify.tip')}</span>
										</td>
									</tr>
									<tr class="fdNotifyPersonTypeTr">
										<td class="tb_normal_title" width="15%">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType")}</td>
										<td width="85%">
											<span>
												<input type="radio" name="param3" value="0" checked onclick="onFdNotifyPersonType('0')" checked>${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.0")}
												<input type="radio" name="param3" value="1" onclick="onFdNotifyPersonType('1')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.1")}
												<input type="radio" name="param3" value="2" onclick="onFdNotifyPersonType('2')">${lfn:message("kms-knowledge:kmsKnowledgeBase.fdNotifyType.2")}
											</span>
											<br/>
											<span class="com_help com_help_0" style="font-size: 12px">文档可阅读者为空时，不发送通知</span>
											<div class="fdNotifyPerson_customize">
												<xform:address
														subject="${lfn:message('kms-knowledge:kmsKnowledgeBase.fdNotifyPersons')}"
														showStatus="edit"
														orgType="ORG_TYPE_PERSON"
														textarea="true"
														mulSelect="true"
														propertyId="fdNotifyPersonIds"
														propertyName="fdNotifyPersonNames"
														style="width:97%;height:90px;" >
												</xform:address>
											</div>
										</td>
									</tr>
									<tr class="fdNotifyPersonTypeTr">
										<td class="tb_normal_title" width="15%">
											<div class="knowledge_notify">${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way")}</div>
										</td>
<%--										<td width="85%"  colspan="3">--%>
<%--											<input type="radio" name="param4" checked value="0" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.0")}--%>
<%--											<input type="radio" name="param4" value="1" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.1")}--%>
<%--											<input type="radio" name="param4" value="2" >${lfn:message("kms-knowledge-notifySetting:kms.knowledge.fdNotify.way.2")}--%>
<%--										</td>--%>
										<td>
											<kmss:editNotifyType property="param4" required="true"/>
										</td>
									</tr>
								</table>
							</ui:content>
						</c:otherwise>
					</c:choose>
				</table>
			</center>

			<html:hidden property="method_GET" />

			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.save')}" height="35"
						   width="120"
						   onclick="submitForm();"></ui:button>
			</center>
		</html:form>

		<script>
			window.submitForm = function(){
				// 触发页面刷新
				try {
					if (window.opener != null) {
						try {
							if (window.opener.LUI) {
								window.opener.LUI
										.fire({
											type : "topic",
											name : "successReloadPage"
										});
							}
						} catch (e) {
							console.log(e)
						}
					}
				} catch (e) {
					console.log(e)
				}
				Com_Submit(document.kmsKnowledgeNotifySettingForm, 'update');
			}

			function onFdIsNotifyClick(type){
			if("1" == type ){
			$(".fdNotifyPersonTypeTr").show();
		}else{
			$(".fdNotifyPersonTypeTr").hide();
		}
		}

			function onFdNotifyPersonType(type){
			var value =  $("[name='fdNotifyType']:checked").val();
			if(!type && value) {
			type = value;
		}

			if("2" == type){
			$(".fdNotifyPersonTypeTr .fdNotifyPerson_customize").show();
		}else {
			$(".fdNotifyPersonTypeTr .fdNotifyPerson_customize").hide();
		}

			if("0" == type){
			$(".com_help_0").show();
		}else {
			$(".com_help_0").hide();
		}
		}
		// function filterPersonInfo(personIds){
		// 	var ids = personIds;
		// 	var data = new KMSSData();
		// 	data.AddBeanData('kmsKnowledgeNotifySettingService&ids='+ids+'&checkIds=true');
		// 	var values = data.GetHashMapArray();
		// 	var _ids=[],_names=[];
		// 	for(var i=0;i<values.length;i++){
		// 		_ids.push(values[i].value);
		// 		_names.push(values[i].text);
		// 	}
		// 	params['fdNotifyPersonIds'] = _ids.join(";");
		// 	params['fdNotifyPersonNames'] = _names.join(";");
		// }
		seajs.use(['lui/jquery'], function($){
			var param2 = "${kmsKnowledgeNotifySettingForm.param2}";
			var param3 = "${kmsKnowledgeNotifySettingForm.param3}";
			var param4 = "${kmsKnowledgeNotifySettingForm.param4}";
			var personIds = "${kmsKnowledgeNotifySettingForm.fdNotifyPersonIds}";
			if('1' == param2){
				$(".fdNotifyPersonTypeTr").show();
			}
			if('2' == param3){
				$(".fdNotifyPersonTypeTr .fdNotifyPerson_customize").show();
			}
			var param2_ = $("input[name='param2']");
			for(var i=0;i<param2_.length;i++ ){
				var result = param2_[i].value;
				if(result == param2){
					param2_[i].checked = true;
				}
			}
			var param3_ = $("input[name='param3']");
			for(var i=0;i<param3_.length;i++ ){
				var result = param3_[i].value;
				if(result == param3){
					param3_[i].checked = true;
				}
			}
			var param4_ = $("input[name='param4']");
			for(var i=0;i<param4_.length;i++ ){
				var result = param4_[i].value;
				if(result == param4){
					param4_[i].checked = true;
				}
			}
		});

		</script>
	</template:replace>
</template:include>