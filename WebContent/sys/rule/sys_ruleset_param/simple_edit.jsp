<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 动态列表表格，组件，ID根据DocList_Info -->
<table id="paramSetting" class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="100px;" align="center">
			<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
		</td>
		<td width="200px;"><bean:message bundle="sys-rule" key="sysRuleSetParam.title.1"/></td>
		<td width="300px;"><kmss:message key="sys-rule:sysRuleSetParam.fdName" /></td>
		<td width="200px"><kmss:message key="sys-rule:sysRuleSetParam.fdType" /></td>
		<td width="200px">
			<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow('paramSetting');" style="cursor:pointer">
		</td>
	</tr>
	<%-- 基准行，KMSS_IsReferRow = 1 --%>
	<tr style="display:none;" KMSS_IsReferRow="1">
		<!-- 序号列，KMSS_IsRowIndex = 1 -->
		<td KMSS_IsRowIndex="1" align="center">
			!{index}
		</td>
		<td>
			<xform:select showStatus="edit" property="fields[!{index}]" required="true" style="width:90%;" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.5') }" onValueChange="switchField">
			</xform:select>
		</td>
		<td>
			<xform:text showStatus="edit" property="sysRuleSetParams[!{index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetParam.fdName') }" required="true" style="width:90%"></xform:text>
		</td>
		<td>
			<xform:text showStatus="readOnly" property="fdTypeName[!{index}]" style="width:90%;" className="inputsgl"></xform:text>
			<input type="hidden" name="sysRuleSetParams[!{index}].fdType">
			<div class="isMulti" style="display:none">
				<xform:checkbox showStatus="edit" property="sysRuleSetParams[!{index}].isMulti" style="display:none">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetParam.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
			<input type="hidden" name="sysRuleSetParams[!{index}].isEdit">
		</td>
		<td>
			<input type="hidden" name="sysRuleSetParams[!{index}].fdId">
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="addParamRow('paramSetting');" style="cursor:pointer">
				<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left: 2px;">
			</div>
		</td>
	</tr>
</table>
<script  language="JavaScript">
	DocList_Info.push('paramSetting');
	if(window.showModalDialog){
		dialogObject = window.dialogArguments;
	}else{
		dialogObject = opener.Com_Parameter.Dialog;
	}
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
			
			//初始化字段
			ruleSetParam.initFieldsSelect(dialogObject.parameters.fields);
		}
		//切换类型
		window.switchField = function(value, obj){
			ruleSetParam.switchField(value, obj);
		}
	});
</script>