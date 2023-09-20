<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	Com_IncludeFile("calendar.js");
</script>
<!-- 动态列表表格，组件，ID根据DocList_Info -->
<table id="ruleSetting" class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="70px;" align="center">
			<span style="white-space:nowrap;"><kmss:message key="sys-rule:sysRuleSetRule.executionOrder" /></span>
		</td>
		<td width="240px;"><kmss:message key="sys-rule:sysRuleSetRule.fdName" /></td>
		<td width="240px;"><kmss:message key="sys-rule:sysRuleSetRule.fdCondition"/></td>
		<td width="110px"><kmss:message key="sys-rule:sysRuleSetRule.returnType" /></td>
		<td width="240px"><kmss:message key="sys-rule:sysRuleSetRule.fdResult"/></td>
		<td width="100px">
			<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="DocList_AddRow('ruleSetting');" style="cursor:pointer">
		</td>
	</tr>
	<%-- 基准行，KMSS_IsReferRow = 1 --%>
	<tr style="display:none;" KMSS_IsReferRow="1">
		<!-- 序号列，KMSS_IsRowIndex = 1 -->
		<td KMSS_IsRowIndex="1" align="center">
			!{index}
		</td>
		<td>
			<input type="hidden" name="sysRuleSetRules[!{index}].fdId">
			<input type="hidden" name="sysRuleSetRules[!{index}].fdOrder">
			<input type="hidden" name="sysRuleSetRules[!{index}].sysRuleSetParamIds" />
			<input type="hidden" name="sysRuleSetRules[!{index}].sysRuleSetParamNames" />
			<xform:text property="sysRuleSetRules[!{index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetRule.fdName') }" required="true" style="width:85%"></xform:text>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[!{index}].fdDisCondition" showStatus="readOnly" style="width: 80%;"></xform:text>
			<a href="#" onclick="editConditionContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[!{index}].fdCondition" type="hidden"/>
			<input name="sysRuleSetRules[!{index}].fdConditionMode" type="hidden"/>
		</td>
		<td>
			<xform:select property="sysRuleSetRules[!{index}].returnType" required="true" style="width:85%;" subject="${lfn:message('sys-rule:sysRuleSetRule.returnType') }" onValueChange="switchRtnType">
				<xform:enumsDataSource enumsType="sys_rule_return_type" />
			</xform:select>
			<div class="isMulti" style="display:none">
				<xform:checkbox property="sysRuleSetRules[!{index}].isMulti" style="display:none" onValueChange="switchMulti">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetRule.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[!{index}].fdDisResult" showStatus="readOnly" style="width: 70%;"></xform:text>
			<a href="#" onclick="editResultContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[!{index}].fdResult" type="hidden"/>
			<input name="sysRuleSetRules[!{index}].fdResultMode" type="hidden"/>
		</td>
		<td>
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="DocList_AddRow('ruleSetting');" style="cursor:pointer">
				<img src="<c:url value="/resource/style/default/icons/up.gif"/>" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img src="<c:url value="/resource/style/default/icons/down.gif"/>" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">
			</div>
		</td>
	</tr>
	<%-- 内容行 --%>
	<c:forEach items="${sysRuleSetDocForm.sysRuleSetRules}" var="sysRuleSetRuleForm" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		<td align="center">
			${vstatus.index + 1}
		</td>
		<td>
			<input type="hidden" name="sysRuleSetRules[${vstatus.index}].fdId" dataId="${vstatus.index}" value="${sysRuleSetRuleForm.fdId}"/>
			<input type="hidden" name="sysRuleSetRules[${vstatus.index}].fdOrder" value="${sysRuleSetRuleForm.fdOrder}">
			<input type="hidden" name="sysRuleSetRules[${vstatus.index}].sysRuleSetParamIds" value="${sysRuleSetRuleForm.sysRuleSetParamIds}"/>
			<input type="hidden" name="sysRuleSetRules[${vstatus.index}].sysRuleSetParamNames" value="${sysRuleSetRuleForm.sysRuleSetParamNames}"/>
			<xform:text property="sysRuleSetRules[${vstatus.index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetRule.fdName') }" value="${sysRuleSetRuleForm.fdName}" required="true" style="width:90%"></xform:text>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[${vstatus.index}].fdDisCondition" value="${sysRuleSetRuleForm.fdDisCondition}" showStatus="readOnly" style="width: 80%;"></xform:text>
			<a href="#" onclick="editConditionContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[${vstatus.index}].fdCondition" value="<c:out value='${sysRuleSetRuleForm.fdCondition }'></c:out>" type="hidden"/>
			<input name="sysRuleSetRules[${vstatus.index}].fdConditionMode" value="${sysRuleSetRuleForm.fdConditionMode}" type="hidden"/>
		</td>
		<td>
			<xform:select property="sysRuleSetRules[${vstatus.index}].returnType" subject="${lfn:message('sys-rule:sysRuleSetRule.fdType') }" value="${sysRuleSetRuleForm.returnType}" required="true" style="width:90%;" onValueChange="switchRtnType">
				<xform:enumsDataSource enumsType="sys_rule_return_type" />
			</xform:select>
			<div class="isMulti" style="display:none">
				<xform:checkbox property="sysRuleSetRules[${vstatus.index}].isMulti" value="${sysRuleSetRuleForm.isMulti}"  style="display:none" onValueChange="switchMulti">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetRule.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[${vstatus.index}].fdDisResult" showStatus="readOnly" value='${sysRuleSetRuleForm.fdDisResult }'></xform:text>
			<a href="#" onclick="editResultContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[${vstatus.index}].fdResult" type="hidden" value="<c:out value='${sysRuleSetRuleForm.fdResult }'></c:out>"/>
			<input name="sysRuleSetRules[${vstatus.index}].fdResultMode" type="hidden" value="${sysRuleSetRuleForm.fdResultMode }"/>
		</td>
		<td>
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="DocList_AddRow('ruleSetting');" style="cursor:pointer">
				<img src="<c:url value="/resource/style/default/icons/up.gif"/>" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img src="<c:url value="/resource/style/default/icons/down.gif"/>" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img class="ruleDelBtn" src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">
			</div>
		</td>
	</tr>
	</c:forEach>
</table>
<script type="text/javascript">
DocList_Info.push('ruleSetting');
//切换返回值类型
function switchRtnType(value, obj){
	ruleSetRule.switchRtnType(value, obj);
	addTxtStrong(obj);
}

//结果类型改变则结果内容必填
function addTxtStrong(obj){
	if ($(obj).closest('td').next()){
		let $tdnext =$( $(obj).closest('td').next()[0]);
		if ($tdnext.find(".inputsgl")){
			let $findinputsgl = $($tdnext.find(".inputsgl")[0]);
			if ($findinputsgl.css("display") == "inline-block"){
				if ($tdnext.find('.txtstrong').length == 0){
					$tdnext.append('<span class="txtstrong">*</span>');
				}
			}else if ($findinputsgl.css("display") == "none"){
				if ($tdnext.find('.txtstrong').length > 0){
					$tdnext.find('.txtstrong').remove();
				}
			}
		}
	}
}

//设置条件内容
function editConditionContent(){
	ruleSetRule.editConditionContent();
}
//设置结果内容
function editResultContent(){
	ruleSetRule.editResultContent();
}
//切换多值
function switchMulti(value, obj){
	ruleSetRule.switchMulti(value, obj[0]);
}
</script>