<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
			window.matrixId = "${sysOrgMatrixForm.fdId}";
			window.fdRelationConditionals = [];
			window.curVersion = "${JsParam.curVersion}" || "V1";
			// 多语言资源信息
			window.Msg_Info = {
					button_ok: '<bean:message key="button.ok" />',
					button_cancel: '<bean:message key="button.cancel" />',
					button_clear: '<bean:message key="button.clear" />'
			};
			/* 唯一校验提示 */
			window.uniqueError = function(val) {
				seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
					dialog.failure("<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.fdIsUnique.err.left'/>" + val + "<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.fdIsUnique.err.right'/>");
				});
			}
			window.checkError = function(field, val) {
				seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
					dialog.failure(field + " <bean:message bundle='sys-organization' key='sysOrgMatrixRelation.err.left'/>" + val + "<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.err.right'/>");
				});
			}
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/organization/resource/js/MatrixEdit.js" ></script>
		<link href="${LUI_ContextPath}/resource/style/default/doc/document.css" rel="stylesheet" type="text/css" />
	</template:replace>
	<template:replace name="body">
		<center>
			<div style="height: 50px;">
				<p style="text-align: center;font-size: 22;margin-top: 10px"><bean:message key="sysOrgMatrix.edit.bulk" bundle="sys-organization"/></p>
			</div>
			<table class="tb_normal" width=90% style="top:50px">
				<!-- 存在常量、系统内数据、自定义数据、组织架构元素数据四种类型，输入值时有区别 -->
				<c:forEach items="${sysOrgMatrixForm.fdRelationConditionals }" var="con" varStatus="conIndex">
					<script language="JavaScript">
						window.fdRelationConditionals.push({"fdId":"${con.fdId}", "fdFieldName": "${con.fdFieldName}", "fdIsUnique": "${con.fdIsUnique}"});
					</script>
					<c:choose>
						<c:when test="${con.fdType == 'constant'}">
							<tr style="height: 50px" class="matrix_add_tr matrix_con_tr">
								<td  style="background: #f1f1f1" class="fdName"><c:out value="${con.fdName }"></c:out></td>
								<td style="width: 75%">
									<div class="inputselectsgl">
										<div class="input">
											<input class="isConstant" type="text" data-type="fieldId" onblur="checkconstant(this);" name="${con.fdFieldName }" value="${con.fdConditionalId }">
										</div>
									</div>
								</td>
								<td>
									<label>
										<bean:message key="sysOrgMatrix.edit.split.value.constant" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:when>
						<c:when test="${con.fdType == 'numRange'}">
							<tr style="height: 50px" class="matrix_add_tr matrix_con_tr">
								<td  style="background: #f1f1f1" class="fdName"><c:out value="${con.fdName }"></c:out></td>
								<td style="width: 75%">
									<div class="inputselectsgl">
										<div class="input">
											<input class="isConstant" type="text" data-type="fieldId" onblur="checknumRange(this);" name="${con.fdFieldName }" value="${con.fdConditionalId }">
										</div>
									</div>
								</td>
								<td>
									<label>
										<bean:message key="sysOrgMatrix.edit.split.value.constant" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:when>
						<c:when test="${con.fdType == 'sys'}">
							<tr style="height: 50px" class="matrix_add_tr matrix_con_tr">
								<td  style="background: #f1f1f1" class="fdName"><c:out value="${con.fdName }"></c:out></td>
								<td>
									<div class="inputselectsgl" onclick="Dialog_MainData('${con.fdId}', '${con.fdFieldName}', '${con.fdMainDataText}','checkbox_${conIndex.index}');">
										<input type="hidden" data-type="fieldId" name="${con.fdId }" value="${con.fdConditionalId }" >
										<div class="input">
											<input type="text" data-type="fieldText" name="${con.fdFieldName }" value="${con.fdConditionalValue }" readonly="readonly">
										</div>
										<div class="selectitem"></div>
									</div>
										
								</td>
								<td>
									<label>
										<input type="checkbox" id="checkbox_${conIndex.index}" onclick="clearInput(this);">
										<bean:message key="sysOrgMatrix.edit.split.value" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:when>
						
						<c:when test="${con.fdType == 'cust'}">
							<tr style="height: 50px" class="matrix_add_tr matrix_con_tr">
								<td  style="background: #f1f1f1" class="fdName"><c:out value="${con.fdName }"></c:out></td>
								<td>
									<div class="inputselectsgl" onclick="Dialog_CustData('checkbox_${conIndex.index}','${con.fdId}', '${con.fdFieldName}', null,'sysOrgMatrixMainDataService&id=${con.fdMainDataType }','${con.fdMainDataText}');">
										<input type="hidden" data-type="fieldId" name="${con.fdId }" value="${con.fdConditionalId }" >
										<div class="input">
											<input type="text" data-type="fieldText" name="${con.fdFieldName }" value="${con.fdConditionalValue }" readonly="readonly" >
										</div>
										<div class="selectitem"></div>
									</div>
								</td>
								<td>
									<label>
										<input type="checkbox" id="checkbox_${conIndex.index}" onclick="clearInput(this);">
										<bean:message key="sysOrgMatrix.edit.split.value" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:when>
						
						<c:otherwise>
							<tr style="height: 50px" class="matrix_add_tr matrix_con_tr">
								<td style="background: #f1f1f1;" class="fdName"><c:out value="${con.fdName }"></c:out></td>
								<td style="width: 75%">
									<div class="inputselectsgl" onclick="Dialog_Address_Cust('checkbox_${conIndex.index}', '${con.fdId}', '${con.fdFieldName}', null, '${con.fdType}',resultCheck);">
										<input type="hidden" data-type="fieldId" name="${con.fdId }" value="${con.fdConditionalId }" >
										<div class="input">
											<input type="text" data-type="fieldText" name="${con.fdFieldName }" value="${con.fdResultValueNames }" readonly="readonly" >
										</div>
										<div class="orgelement"></div>
									</div>
								</td>
								<td>
									<label>
										<input type="checkbox" id="checkbox_${conIndex.index}" onclick="clearInput(this);">
										<bean:message key="sysOrgMatrix.edit.split.value" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:forEach items="${sysOrgMatrixForm.fdRelationResults }" var="res" varStatus="resIndex">
					<c:choose>
						<c:when test="${res.fdType == 'person_post' }">
							<tr style="height: 50px" class="matrix_add_tr matrix_res_tr matrix_res_person_post">
								<td style="background: #ecf3ff" class="fdName"><c:out value="${res.fdName}"></c:out></td>
								<td style="width: 75%">
									<input type="hidden" data-type="fieldId" name="${res.fdId }" value="${res.fdResultValueIds }" class="person_post" >
									<div style="width: 45%" class="inputselectsgl" onclick="Dialog_Address_Cust('res_checkbox_${resIndex.index}', '${res.fdId}_person', '${res.fdFieldName}_person', null, 'person',resultCheck2);">
										<input type="hidden" data-type="fieldId" name="${res.fdId }_person">
										<div class="input">
											<input type="text" data-type="fieldText" name="${res.fdFieldName }_person" readonly="readonly">
										</div>
										<div class="orgelement"></div>
									</div>
									<div style="width: 45%" class="inputselectsgl" onclick="Dialog_Address_Cust('res_checkbox_${resIndex.index}', '${res.fdId}_post', '${res.fdFieldName}_post', null, 'post',resultCheck2);" name="${res.fdId}_person">
										<input type="hidden" data-type="fieldId" name="${res.fdId }_post">
										<div class="input">
											<input type="text" data-type="fieldText" name="${res.fdFieldName }_post" readonly="readonly">
										</div>
										<div class="orgelement"></div>
									</div>
									
								</td>
								<td>
									<label>
										<input type="checkbox" id="res_checkbox_${resIndex.index}" onclick="clearInput(this)">
										<bean:message key="sysOrgMatrix.edit.split.value" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr style="height: 50px" class="matrix_add_tr matrix_res_tr">
								<td style="background: #ecf3ff" class="fdName"><c:out value="${res.fdName}"></c:out></td>
								<td style="width: 75%">
									<div class="inputselectsgl" onclick="Dialog_Address_Cust('res_checkbox_${resIndex.index}', '${res.fdId}', '${res.fdFieldName}', null, '${res.fdType}',resultCheck);">
										<input type="hidden" data-type="fieldId" name="${res.fdId }" value="${res.fdResultValueIds }" >
										<div class="input">
											<input type="text" data-type="fieldText" name="${res.fdFieldName }" value="${res.fdResultValueNames }"  readonly="readonly">
										</div>
										<div class="orgelement"></div>
									</div>
								</td>
								<td>
									<label>
										<input type="checkbox" id="res_checkbox_${resIndex.index}" onclick="clearInput(this)">
										<bean:message key="sysOrgMatrix.edit.split.value" bundle="sys-organization" />
									</label>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</center>
		<div style="position: relative;left: 20px;bottom: 0px;top: 30px;width: 90%">
			<p>
				<span style="color: red"><bean:message key="sysOrgMatrix.edit.tip" bundle="sys-organization" /></span>
				</br>
				<label style="color: red"><bean:message key="sysOrgMatrix.edit.tip1" bundle="sys-organization" /></label>
				</br>
				<label style="color: red"><bean:message key="sysOrgMatrix.edit.tip2" bundle="sys-organization" /></label>
				</br>
				<label style="color: red"><bean:message key="sysOrgMatrix.edit.tip3" bundle="sys-organization" /></label>
				</br>
				<label style="color: red"><bean:message key="sysOrgMatrix.edit.tip4" bundle="sys-organization" /></label>
				</br>
			</p>
		</div>
	</template:replace>
</template:include>
