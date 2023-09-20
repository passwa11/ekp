<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<style>
	html { overflow-x:hidden; overflow-y:auto;}
</style>
<center>
<table id="relationEntry" width=95% style="border: 0px;">
	<tr>
		<td width="16%" style="border: 0px;" nowrap="nowrap">
			<bean:message bundle="sys-relation" key="sysRelationEntry.select.type" />
		</td>
		<td style="border: 0px;">
			<nobr>
			<input type="hidden" name="fdOtherUrl" />
			<!-- 文档关联移到单独的页面
			<label>
			<input type="radio" name="fdType" value="5" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType5" />
			</label>
			 -->
			<label>
			<input type="radio" name="fdType" value="4" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType4" />
			</label>
			<label>
			<input type="radio" name="fdType" value="1" onclick="changeRelationType(this.value);"/>
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType1" />
			</label>
			<label>
			<input type="radio" name="fdType" value="2" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType2" />
			</label>
			<label>
			<input type="radio" name="fdType" value="6" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType6" />
			</label>
			<!-- czk2019 -->
			<label>
			<input type="radio" name="fdType" value="8" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType8" />
			</label>
			<!-- 图表中心 -->
			<kmss:ifModuleExist path="/dbcenter/echarts/">
			<label>
			<input type="radio" name="fdType" value="9" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType9" />
			</label>
			</kmss:ifModuleExist>
			<%-- 
			<label>
			<input type="radio" name="fdType" value="3" onclick="changeRelationType(this.value);" />
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType3" />
			</label>
			 --%>
			</nobr>
		</td>
	</tr>
	<tr>
		<td valign="top" colspan="2">
			<iframe id="sysRelationEntry" 
				frameborder="0" scrolling="no" width="100%"></iframe>
		</td>
	</tr>
</table>
</center>

<script>
    var relation_entry_model_name = "${JsParam.currModelName}";
    var relation_entry_model_id = "${JsParam.currModelId}";
    var flowkey = "${param.flowkey}";
    Com_IncludeFile("sysRelationEntry.js", Com_Parameter.ContextPath+"sys/relation/sys_relation_main/js/", null, true);
</script>

<%@ include file="/resource/jsp/view_down.jsp"%>