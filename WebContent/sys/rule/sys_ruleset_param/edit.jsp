<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 动态列表表格，组件，ID根据DocList_Info -->
<table id="paramSetting" class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="70px;" align="center">
			<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
		</td>
		<td width="480px;"><kmss:message key="sys-rule:sysRuleSetParam.fdName" /></td>
		<td width="350px"><kmss:message key="sys-rule:sysRuleSetParam.fdType" /></td>
		<td width="100px">
			<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow('paramSetting')" style="cursor:pointer">
		</td>
	</tr>
	<%-- 基准行，KMSS_IsReferRow = 1 --%>
	<tr style="display:none;" KMSS_IsReferRow="1">
		<!-- 序号列，KMSS_IsRowIndex = 1 -->
		<td KMSS_IsRowIndex="1" align="center">
			!{index}
		</td>
		<td>
			<xform:text property="sysRuleSetParams[!{index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetParam.fdName') }" required="true" style="width:90%"></xform:text>
		</td>
		<td>
			<xform:select property="sysRuleSetParams[!{index}].fdType" required="true" style="width:90%;" subject="${lfn:message('sys-rule:sysRuleSetParam.fdType') }" onValueChange="switchType">
				<xform:enumsDataSource enumsType="sys_rule_param_type" />
			</xform:select>
			<div class="isMulti" style="display:none">
				<xform:checkbox property="sysRuleSetParams[!{index}].isMulti" style="display:none">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetParam.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
			<input type="hidden" name="sysRuleSetParams[!{index}].isEdit">
		</td>
		<td>
			<input type="hidden" name="sysRuleSetParams[!{index}].fdId">
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow('paramSetting')" style="cursor:pointer">
				<img class="paramDelBtn"  src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
			</div>
		</td>
	</tr>
	<%-- 内容行 --%>
	<c:forEach items="${sysRuleSetDocForm.sysRuleSetParams}" var="sysRuleSetParamForm" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		<td align="center">
			${vstatus.index + 1}
		</td>
		<td>
			<xform:text property="sysRuleSetParams[${vstatus.index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetParam.fdName') }" value="${sysRuleSetParamForm.fdName}" required="true" style="width:90%"></xform:text>
		</td>
		<td>
			<xform:select property="sysRuleSetParams[${vstatus.index}].fdType" subject="${lfn:message('sys-rule:sysRuleSetParam.fdType') }" value="${sysRuleSetParamForm.fdType}" required="true"  style="width:90%;" onValueChange="switchType">
				<xform:enumsDataSource enumsType="sys_rule_param_type" />
			</xform:select>
			<div class="isMulti" style="display:none">
				<xform:checkbox property="sysRuleSetParams[${vstatus.index}].isMulti" value="${sysRuleSetParamForm.isMulti}" style="display:none">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetParam.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
			<input type="hidden" name="sysRuleSetParams[${vstatus.index}].isEdit" value="${sysRuleSetParamForm.isEdit}">
		</td>
		<td>
			<input type="hidden" name="sysRuleSetParams[${vstatus.index}].fdId" value="${sysRuleSetParamForm.fdId}">
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow('paramSetting');" style="cursor:pointer">
				<img class="paramDelBtn" src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
			</div>
		</td>
	</tr>
	</c:forEach>
</table>
<c:if test="${sysRuleSetDocForm.method_GET == 'edit' }">
	<div style="color: #FF0000;"><bean:message key="sysRuleSetParam.edit.desc" bundle="sys-rule"/></div>	
</c:if>
<script  language="JavaScript">
	DocList_Info.push('paramSetting');
	//预加载100个参数随机ID
	var ids = Data_GetRadomId(100);
	
	seajs.use(['lui/jquery'], function($) {
		/*新增一行*/
		window.addParamRow = function(table){
			DocList_AddRow(table);
			if(ids.length <= 0){
				//重新加载100个
				ids = Data_GetRadomId(100);
			}
			
			var id = ids[0];
			ids.splice(0,1);
			
			//参数id
			var rowCount = $("#paramSetting").find("tr").length - 1;
			$("[name='sysRuleSetParams["+(rowCount-1)+"].fdId']").val(id);
			//记录参数id
			var method = '${sysRuleSetDocForm.method_GET}';
			if("edit" == method){
				ruleSetParam.newParamIds.push(id);
			}
		}
		//切换类型
		window.switchType = function(value, obj){
			ruleSetParam.switchType(value, obj);
		}
	});
</script>