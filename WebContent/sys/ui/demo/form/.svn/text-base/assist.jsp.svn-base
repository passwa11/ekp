<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String status = request.getParameter("status");
boolean required = false;
if(status==null){
	status = "edit";
}else if("required".equals(status)){
	required = true;
	status = "edit";
}
request.setAttribute("status", status);
request.setAttribute("required", required);
%>
<div id="assistDiv" style="line-height:30px;">
&nbsp;&nbsp;初始
<select id="t_init" onchange="onInitChange(this.value);">
	<option value="edit">编辑</option>
	<option value="required">必填</option>
	<option value="readOnly">只读有控件</option>
	<option value="view">只读无控件</option>
	<option value="noShow">隐藏有控件</option>
	<option value="hidden">隐藏无控件</option>
</select>

<c:if test="${mobile}"><br></c:if>
&nbsp;&nbsp;目标
<select id="t_field">
	<option value="fdText">单行文本</option>
	<option value="fdSelect">下拉菜单</option>
	<option value="fdRadio">单选按钮</option>
	<option value="fdCheckboxIds">多选按钮</option>
	<option value="fdDate">日期选择</option>
	<option value="fdOrgId">人员ID</option>
	<option value="fdOrgName">人员Name</option>
	<option value="fdDialogId">对话框ID</option>
	<option value="fdDialogName">对话框Name</option>
	<option value="fdTextarea">多行文本</option>
</select>

&nbsp;&nbsp;明细表
<select id="t_detail">
	<option value="">无</option>
	<option value="*">*</option>
	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
</select>

<c:if test="${mobile}"><br></c:if>
&nbsp;&nbsp;操作
<select id="t_opt" onchange="onOptChange();">
	<option value="get-val">值-读</option>
	<option value="set-val">值-写</option>
	<option value="get-display">显示-读</option>
	<option value="set-display">显示-写</option>
	<option value="get-readOnly">只读-读</option>
	<option value="set-readOnly">只读-写</option>
	<option value="get-required">必填-读</option>
	<option value="set-required">必填-写</option>
	<option value="get-editLevel">权限-读</option>
	<option value="set-editLevel">权限-写</option>
</select>
<input id="t_value" style="display:none;width:150px;">
<select id="t_boolean" onchange="execTest();" style="display:none;">
	<option value="true">是</option>
	<option value="false">否</option>
</select>

<c:if test="${mobile}"><br></c:if>
&nbsp;&nbsp;<input type="button" value="执行" onclick="execTest();" style="padding:0px 10px; font-size:14px; ">
<input id="t_result" style="width:150px;">
<div id="t_event"></div>
</div>
<script>
	function onInitChange(value){
		if(value=='${param.status}'){
			return;
		}
		location.href = Com_SetUrlParameter(location.href, 'status', value);
	}
	function onOptChange(){
		var opt = byId('t_opt').value.split('-');
		var showBoolean = (opt[1]=='display' || opt[1]=='readOnly' || opt[1]=='required');
		var set = opt[0]=='set';
		byId('t_value').style.display = set && !showBoolean ? '' : 'none';
		byId('t_boolean').style.display = set && showBoolean ? '' : 'none';
	}
	
	function execTest(){
		var field = byId('t_field').value;
		var detail = byId('t_detail').value;
		if(detail!=''){
			field = 'fdDetail_Form['+detail+'].'+field;
		}
		field = $form(field);
		var opt = byId('t_opt').value.split('-');
		var value;
		if(opt[0]=='set'){
			if(opt[1]=='display' || opt[1]=='readOnly' || opt[1]=='required'){
				value = byId('t_boolean').value=='true';
			}else{
				value = byId('t_value').value;
			}
			field[opt[1]](value);
		}
		byId('t_result').value = field[opt[1]]();
	}
	function byId(id){
		return document.getElementById(id);
	}
	function doOnLoad(){
		var index = 0;
		var time = 0;
		byId('t_init').value = '${param.status}' || '${status}';
		$form.bind({
			onValueChange : onChange,
			onDisplayChange : onChange,
			onReadOnlyChange : onChange
		});
		function onChange(e){
			index++;
			var now = new Date().getTime();
			var div = byId('t_event');
			var html = '';
			if(now-time<2000){
				html = div.innerHTML;
			}
			time = now;
			html += index + '.'+e.type+':'+e.field +', by:'+e.by+ '<br>';
			div.innerHTML = html;
		}
	}
</script>
