<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.common.service.IXMLDataBean,com.landray.kmss.common.actions.RequestContext"%>
<%@page import="java.util.*,com.landray.kmss.util.*,net.sf.json.*,com.landray.kmss.dbcenter.echarts.util.ConfigureUtil"%>
<script>
function _showModelFieldText(select){
	var $tr = $(select).parent().parent();
	if(select.value==""){
		$tr.find("td:eq(1)").html("");
		$tr.find("td:eq(2)").html("");
		$tr.find("td:eq(3)").html("");
	}
	var arr = select.value.split(":");
	var type = arr[2];
	if(type.indexOf("com.landray.kmss")!=-1){
		type = type.substring(type.lastIndexOf(".")+1);
	}
	$tr.find("td:eq(1)").html(arr[0]);
	$tr.find("td:eq(2)").html(arr[1]);
	$tr.find("td:eq(3)").html(type);
}

function _switchModelingInfo(flag){
	var show = (flag=="show");
	$.each($("#modeling_info").find(".m_table"),function(i){
		if(show){
			$(this).show();
		}else{
			$(this).hide();
		}
	});

	$.each($("#modeling_info").find(".m_field"),function(i){
		if(show){
			$(this).show();
		}else{
			$(this).hide();
		}
	});
	if(show){
		$("#_opr_show").html("<a href=\"javascript:_switchModelingInfo('hide')\">隐藏</a>");
	}else{
		$("#_opr_show").html("<a href=\"javascript:_switchModelingInfo('show')\">显示</a>");
	}
}

Com_AddEventListener(window, "load", function(){
	_switchModelingInfo("hide");
});

</script>
<table class="tb_normal" width="100%" id="modeling_info">
	<tr>
		<td width=40%  class="td_normal_title" align="center">表单字段选择	</td>
		<td width=22%  class="td_normal_title" align="center">字段文本</td>
		<td width=22%  class="td_normal_title" align="center">字段名称</td>
		<td width=10%  class="td_normal_title" align="center">字段类型</td>
		<td width=6%  class="td_normal_title" align="center" id="_opr_show"><a href="javascript:_switchModelingInfo('show')">显示</a></td>
	</tr>

<%
	IXMLDataBean xdb = (IXMLDataBean)SpringBeanUtil.getBean("modelingAppModelService");
	List<Map<String, String>> modes = xdb.getDataList(new RequestContext(request));
	List<String> ids = new ArrayList<String>();
	for(Map<String, String> mode:modes) {
		ids.add(mode.get("value"));
	}

	RequestContext rcModel = new RequestContext();
	rcModel.setParameter("key",ConfigureUtil.XFORM_MODEL_KEY);
	rcModel.setParameter("fdMainModelName","com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
	JSONArray modelArray = ConfigureUtil.findModelByKey(rcModel);

	for(int i=0;i<modelArray.size();i++){
		JSONObject json = modelArray.getJSONObject(i);
		String mid = json.getString("fdModelId");
		if(ids.contains(mid)) {
			%>
			<tr class="m_table">
				<td colspan="5"><%=json.getString("modelText")%>(<%=json.getString("tableName")%>)</td>
			</tr>
			<tr class="m_field">
				<td>
					<select onchange="_showModelFieldText(this);">
						<option value="">==请选择表单字段==</option>
			<%
			RequestContext rcField = new RequestContext();
			rcField.setParameter("modelName", json.getString("modelName"));
			rcField.setParameter("isxform","true");
			JSONObject dict = ConfigureUtil.findFieldDictByModelName(rcField);
			JSONArray fieldArray = dict.getJSONArray("data");
			for(int j=0;j<fieldArray.size();j++) {
				JSONObject field = fieldArray.getJSONObject(j);
				String value = field.getString("fieldText")+":"+field.getString("field")+":"+field.getString("fieldType");
				String text = field.getString("fieldText")+"("+field.getString("field")+")";
			%>
				<option value="<%=value%>"><%=text%></option>
			<%
			}
			%>
					</select>
				</td>
				<td></td>
				<td></td>
				<td colspan="2"></td>
			</tr>

		  <%
		}
	}
%>

<%
	rcModel = new RequestContext();
	rcModel.setParameter("key",ConfigureUtil.XFORM_MODEL_KEY);
	rcModel.setParameter("fdMainModelName","com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
	modelArray = ConfigureUtil.findModelByKey(rcModel);

	for(int i=0;i<modelArray.size();i++){
		JSONObject json = modelArray.getJSONObject(i);
		String mid = json.getString("fdModelId");
		if(ids.contains(mid)) {
			%>
			<tr class="m_table">
				<td colspan="5"><%=json.getString("modelText")%>(<%=json.getString("tableName")%>)</td>
			</tr>
			<tr class="m_field">
				<td>
					<select onchange="_showModelFieldText(this);">
						<option value="">==请选择表单字段==</option>
			<%
			RequestContext rcField = new RequestContext();
			rcField.setParameter("modelName", json.getString("modelName"));
			rcField.setParameter("isxform","true");
			JSONObject dict = ConfigureUtil.findFieldDictByModelName(rcField);
			JSONArray fieldArray = dict.getJSONArray("data");
			for(int j=0;j<fieldArray.size();j++) {
				JSONObject field = fieldArray.getJSONObject(j);
				String value = field.getString("fieldText")+":"+field.getString("field")+":"+field.getString("fieldType");
				String text = field.getString("fieldText")+"("+field.getString("field")+")";
			%>
				<option value="<%=value%>"><%=text%></option>
			<%
			}
			%>
					</select>
				</td>
				<td></td>
				<td></td>
				<td colspan="2"></td>
			</tr>

		  <%
		}
	}
%>
</table>