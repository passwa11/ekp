<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<tr id="subFormTr">
	<td
	<c:if test="${JsParam.isFreeFlow ne 'true'}">width="100px"</c:if>
	 ><kmss:message key="lbpmNode.subform.Corresponding_form" bundle="sys-lbpmservice" /></td>
	<td>
		<input type="hidden" name="wf_subFormId" />
		<input type="hidden" name="wf_subFormName" />PC:
		<select name="subForm" onchange="switchSubFormInfo(this);" style="width: 110px">
		</select>
		&nbsp;&nbsp;<c:if test="${JsParam.isFreeFlow eq 'true'}"><br/></c:if>
		<input type="hidden" name="wf_subFormMobileId" />
		<input type="hidden" name="wf_subFormMobileName" /><kmss:message key="lbpmNode.subform.web_form" bundle="sys-lbpmservice" />:
		<select name="subFormMobile" onchange="switchSubFormMobileInfo(this);" style="width: 110px">
		</select>
		&nbsp;&nbsp;<c:if test="${JsParam.isFreeFlow eq 'true'}"><br/></c:if>
		<div id="subPrintDiv" style="display:inline-block;">
		<input type="hidden" name="wf_subFormPrintId" />
		<input type="hidden" name="wf_subFormPrintName" /><kmss:message key="lbpmNode.subform.print_form" bundle="sys-lbpmservice" />:
		<select name="subFormPrint" onchange="switchSubFormPrintInfo(this);" style="width: 110px">
		</select>
		</div>
	</td>
</tr>

<script>
var subFormId = AttributeObject.NodeData["subFormId"];
var subFormName = AttributeObject.NodeData["subFormName"];
var subFormMobileId = AttributeObject.NodeData["subFormMobileId"];
var subFormMobileName = AttributeObject.NodeData["subFormMobileName"];
var subFormPrintId = AttributeObject.NodeData["subFormPrintId"];
var subFormPrintName = AttributeObject.NodeData["subFormPrintName"];
AttributeObject.Init.ViewModeFuns.push(function(){
	if(FlowChartObject.ProcessData.subFormMode && FlowChartObject.ProcessData.subFormMode=="true"){
		var info = FlowChartObject.SubFormInfoList;
		if(info==null){
			$("#subFormTr").show();
			var select = $("select[name='subForm']");
			var subFormIdObj = $("input[name='wf_subFormId']");
			var subFormNameObj = $("input[name='wf_subFormName']");
			if(subFormId && subFormName){
				select.append('<option value="'+subFormId+'" selected="selected">'+subFormName+'</option>');
				subFormIdObj.val(subFormId);
				subFormNameObj.val(subFormName);
			}else{
				select.append('<option value="default" selected="selected"><kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" /></option>');
				subFormIdObj.val("default");
				subFormNameObj.val("<kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" />");
			}
			var selectMobile = $("select[name='subFormMobile']");
			var subFormMobileIdObj = $("input[name='wf_subFormMobileId']");
			var subFormMobileNameObj = $("input[name='wf_subFormMobileName']");
			if(subFormMobileId && subFormMobileName){
				selectMobile.append('<option value="'+subFormMobileId+'" selected="selected">'+subFormMobileName+'</option>');
				subFormMobileIdObj.val(subFormMobileId);
				subFormMobileNameObj.val(subFormMobileName);
			}else{
				selectMobile.append('<option value="default" selected="selected"><kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" /></option>');
				subFormMobileIdObj.val("default");
				subFormMobileNameObj.val("<kmss:message key="lbpmNode.subform.defaut_form" bundle="sys-lbpmservice" />");
			}
			var selectPrint = $("select[name='subFormPrint']");
			var subPrintIdObj = $("input[name='wf_subFormPrintId']");
			var subPrintNameObj = $("input[name='wf_subFormPrintName']");
			if(subFormPrintId && subFormPrintName){
				selectPrint.append('<option value="'+subFormPrintId+'" selected="selected">'+subFormPrintName+'</option>');
				subPrintIdObj.val(subFormPrintId);
				subPrintNameObj.val(subFormPrintName);
			}else{
				selectPrint.append('<option value="default" selected="selected"><kmss:message key="lbpmNode.subform.default_print_form" bundle="sys-lbpmservice" /></option>');
				subPrintIdObj.val("default");
				subPrintNameObj.val("<kmss:message key="lbpmNode.subform.default_print_form" bundle="sys-lbpmservice" />");
			}
		}
	}
})

AttributeObject.Init.AllModeFuns.push(function() {
	var info = FlowChartObject.SubFormInfoList;
	var info_print = FlowChartObject.SubPrintInfoList;
	var select = $("select[name='subForm']");
	var selectMobile = $("select[name='subFormMobile']");
	var selectPrint = $("select[name='subFormPrint']");
	if(info!=null && info.length>0 && FlowChartObject.xform_mode=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
		var subFormIdObj = $("input[name='wf_subFormId']");
		var subFormNameObj = $("input[name='wf_subFormName']");
		var subFormMobileIdObj = $("input[name='wf_subFormMobileId']");
		var subFormMobileNameObj = $("input[name='wf_subFormMobileName']");
		var pcFormIds = {};
		var mobileFormIds = {};
		var hasPcDefaultWebForm = false;
		// 标识是否有移动表单
		var hasMobileForm = false;
		for(var i = 0;i<info.length;i++){
			if (!info[i].mobileForm || info[i].mobileForm == "false") {
				select.append('<option value="'+info[i].id+'">'+info[i].name+'</option>');
				pcFormIds[info[i].id] = info[i] ;
			}
			if (info[i].mobileForm === "true" || info[i].defaultWebForm) {
				hasMobileForm = true;
				selectMobile.append('<option value="'+info[i].id+'">'+info[i].name+'(mobile)'+'</option>');
				if (info[i].pcFormId) {
					mobileFormIds[info[i].pcFormId] = info[i];
				}
				if (info[i].pcDefaultWebForm) {
					hasPcDefaultWebForm = true;
				}
			}
			if (info[i]["default"] === "true") {
				subFormIdObj.val(info[i].id);
				subFormNameObj.val(info[i].name);
			}
		}
		//没有移动表单,则建pc表单添加到移动表单中
		if (!hasMobileForm) {
			//将pc表单加到移动中
			for (var key in  pcFormIds) {
				if (key == "default" && hasPcDefaultWebForm) {
					continue;
				}
				if (!mobileFormIds[key]) {
					selectMobile.append('<option value="'+key+'">'+pcFormIds[key].name+'</option>');
				}
			}
		}
		
		if(info_print!=null && info_print.length>0){
			var subPrintIdObj = $("input[name='wf_subFormPrintId']");
			var subPrintNameObj = $("input[name='wf_subFormPrintName']");
			subPrintIdObj.val(info_print[0].id);
			subPrintNameObj.val(info_print[0].name);
			selectPrint.append('<option value="'+info_print[0].id+'" selected="selected">'+info_print[0].name+'</option>');
			for(var i =1;i<info_print.length;i++){
				selectPrint.append('<option value="'+info_print[i].id+'">'+info_print[i].name+'</option>')
			}
		}else{
			$("#subPrintDiv").hide();
		}
	}else{
		$("#subFormTr").hide();
	}
	if(FlowChartObject.xform_mode=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
		if(subFormId && subFormName){
			var select = $("select[name='subForm']");
			var values = select.find("option").map(function(){
				return $(this).val();
			}).get().join(",");
			if(values.indexOf(subFormId)>-1){
				select.val(subFormId).trigger($.Event("change"));
			}else{
				var subFormIdObj = $("input[name='wf_subFormId']");
				var subFormNameObj = $("input[name='wf_subFormName']");
				var newFormId = findNewFormIdByOldFormId(FlowChartObject.SubFormInfoList,subFormId);
				if (newFormId) {
					select.val(newFormId);
					subFormIdObj.val(newFormId);
					AttributeObject.NodeData["subFormId"] = newFormId;
				} else {
					select.val("default");
					subFormIdObj.val("default");
					AttributeObject.NodeData["subFormId"] = "default";
				}
				var option=select.find("option:selected");//获取当前选择项.
				var name = option.text();//获取当前选择项的文本
				subFormNameObj.val(name);
				AttributeObject.NodeData["subFormName"] = name;
			}
		}
		if(subFormMobileId && subFormMobileName){
			var selectMobile = $("select[name='subFormMobile']");
			var subFormMobileIdObj = $("input[name='wf_subFormMobileId']");
			var subFormMobileNameObj = $("input[name='wf_subFormMobileName']");
			var Mobilevalues = selectMobile.find("option").map(function(){
				return $(this).val();
			}).get().join(",");
			if(Mobilevalues.indexOf(subFormMobileId)>-1){
				selectMobile.val(subFormMobileId).trigger($.Event("change"));
				var option=selectMobile.find("option:selected");//获取当前选择项.
				var name = option.text();//获取当前选择项的文本
				subFormMobileNameObj.val(name);
				AttributeObject.NodeData["subFormMobileName"] = name;
			}else{
				var newMobileFormId = findNewFormIdByOldFormId(FlowChartObject.SubFormInfoList,subFormMobileId);
				if (newMobileFormId) {
					selectMobile.val(newMobileFormId);
					subFormMobileIdObj.val(newMobileFormId);
					AttributeObject.NodeData["subFormMobileId"] = newMobileFormId;
				}
				var option=selectMobile.find("option:selected");//获取当前选择项.
				var name = option.text();//获取当前选择项的文本
				subFormMobileNameObj.val(name);
				AttributeObject.NodeData["subFormMobileName"] = name;
			}
		}
		if(subFormPrintId && subFormPrintName){
			var selectPrint = $("select[name='subFormPrint']");
			var Printvalues = selectPrint.find("option").map(function(){
				return $(this).val();
			}).get().join(",");
			if(Printvalues.indexOf(subFormPrintId)>-1){
				selectPrint.val(subFormPrintId).trigger($.Event("change"));
			}else{
				var subPrintIdObj = $("input[name='wf_subFormPrintId']");
				var subPrintNameObj = $("input[name='wf_subFormPrintName']");
				var newPrintId = findNewFormIdByOldFormId(FlowChartObject.SubPrintInfoList,subFormPrintId);
				if (newPrintId) {
					selectPrint.val(newPrintId);
					subPrintIdObj.val(newPrintId);
					AttributeObject.NodeData["subFormPrintId"] = newPrintId;
				} else {
					selectPrint.val("default");
					subPrintIdObj.val("default");
					AttributeObject.NodeData["subFormPrintId"] = "default";
				}
				var option=selectPrint.find("option:selected");//获取当前选择项.
				var name = option.text();//获取当前选择项的文本
				subPrintNameObj.val(name);
				AttributeObject.NodeData["subFormPrintName"] = name;
			}
		}
		var isFreeFlow = "${JsParam.isFreeFlow eq 'true'}";
		var isOpenNewWin = "${JsParam.isOpenNewWin eq 'true'}";
		if(isFreeFlow=="true"&&isOpenNewWin!="true"){
			$("select[name='subForm']").change(function(){
				saveNodeData();
			});
			$("select[name='subFormMobile']").change(function(){
				saveNodeData();
			});
			$("select[name='subFormPrint']").change(function(){
				saveNodeData();
			});
		}
	}
});

function findNewFormIdByOldFormId(formList,oldFormId) {
	if (formList && oldFormId) {
		for (var i = 0; i < formList.length; i++) {
			if (oldFormId === formList[i].fdOldFormId) {
				return formList[i].id;
			}
		}
		//#123653 补充兼容建模
		if(oldFormId=="default"){
			for (var i = 0; i < formList.length; i++) {
				if (formList[i]["default"]) {
					return formList[i].id;
				}
			}
		}
	}
}

function getDefaultWebForm(data){
	for(var i = 0;i<data.length;i++){
		if(data[i].defaultWebForm){
			return data[i];
		}
	}
	return null;
}

function switchSubFormInfo(self){
	var subFormId = $("input[name='wf_subFormId']");
	var subFormName = $("input[name='wf_subFormName']");
	var id = self.value;//获取当前选择项的值
	var option=$(self).find("option:selected");//获取当前选择项.
	var name = option.text();//获取当前选择项的文本
	subFormId.val(id);
	subFormName.val(name);
}

function switchSubFormMobileInfo(self){
	var subFormMobileIdObj = $("input[name='wf_subFormMobileId']");
	var subFormMobileNameObj = $("input[name='wf_subFormMobileName']");
	var id = self.value;//获取当前选择项的值
	var option=$(self).find("option:selected");//获取当前选择项.
	var name = option.text();//获取当前选择项的文本
	subFormMobileIdObj.val(id);
	subFormMobileNameObj.val(name);
}

function switchSubFormPrintInfo(self){
	var subPrintIdObj = $("input[name='wf_subFormPrintId']");
	var subPrintNameObj = $("input[name='wf_subFormPrintName']");
	var id = self.value;//获取当前选择项的值
	var option=$(self).find("option:selected");//获取当前选择项.
	var name = option.text();//获取当前选择项的文本
	subPrintIdObj.val(id);
	subPrintNameObj.val(name);
}
</script>