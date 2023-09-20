<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ include file="/resource/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<link rel=stylesheet href="../css/panel.css">
<script>
Com_IncludeFile("validation.jsp|validation.js|plugin.js|jquery.js|doclist.js|docutil.js|dialog.js|formula.js");
</script>
</head>
<body style="margin:0px">
<center>
<form>
<c:if test="${JsParam.notTitle ne 'true' }">
	<div class="lbpm_freeflow_winTitle">
		<c:choose>
			<c:when test="${JsParam.nodeType eq 'reviewNode' }">
				新增审批节点
			</c:when>
			<c:when test="${JsParam.nodeType eq 'signNode' }">
				新增签字节点
			</c:when>
			<c:when test="${JsParam.nodeType eq 'sendNode' }">
				新增抄送节点
			</c:when>
		</c:choose>
	</div>
</c:if>
<br>
<br>
<table style="height:99.9%; width:90%;padding: 8px" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td>
			<div class="lbpm_freeflow_row">
				<div class="lbpm_freeflow_title">
					节点审批人：
				</div>
				<div class="lbpm_freeflow_detail">
					<c:choose>
						<c:when test="${JsParam.nodeType eq 'sendNode' }">
							<c:choose>
								<c:when test="${JsParam.isTemplate eq 'true' }">
									<xform:address propertyName="handlerNames" propertyId="handlerIds" subject="节点审批人" orgType="ORG_TYPE_ALL | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address propertyName="handlerNames" propertyId="handlerIds" required="true" subject="节点审批人" orgType="ORG_TYPE_ALL | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${JsParam.isTemplate eq 'true' }">
									<xform:address propertyName="handlerNames" propertyId="handlerIds" subject="节点审批人" orgType="ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" onValueChange="_addressRule"></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address propertyName="handlerNames" propertyId="handlerIds" required="true" subject="节点审批人" orgType="ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" onValueChange="_addressRule"></xform:address>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="lbpm_freeflow_row">
				<div class="lbpm_freeflow_title">
					分支类型：
				</div>
				<div class="lbpm_freeflow_detail lbpm_freeflow_type">
					<label class="checked">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="3" checked><span>默认</span><!-- 将所选审批人合并一个节点  --></div>
						<div class="lbpm_freeflow_type_img"><img id="default_selected" src="../images/default_selected.png"></div>
					</label>
					<label id="drawerGw_1" style="padding-left: 10px;">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="0"><span>串行</span><!-- 将所选审批人逐个串发  --></div>
						<div class="lbpm_freeflow_type_img"><img id="serial_select" src="../images/serial_noselected.png"></div>
					</label>
					<label id="drawerGw_2" style="padding-left: 10px;">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="1"><span>并行</span><!-- 将所选审批人逐个并发  --></div>
						<div class="lbpm_freeflow_type_img"><img id="spit_select" src="../images/spit_noselected.png"></div>
					</label>
				</div>
			</div>
		</td>
	</tr>
</table>
<input name="ruleVal" type="hidden">
<input name="opinionType" type="hidden">
<table class="gridtable" id="addreRuleTab" style="height:99.9%; width:90%;padding: 8px" border=0 cellpadding=0 cellspacing=0>
</table>
<div class="lbpm_freeflow_operation_row">
	<div id="gwsave" class="lbpm_freeflow_btn" onclick="save();">确定</div>
	<div  class="lbpm_freeflow_btn" onclick="closeDialog();">取消</div>
</div>
</form>
</center>
<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.js|xform.js");
</script>
<script>
var lbpmAllSettingInfo = null;
var _validation = $KMSSValidation();
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		dialogObject = parent.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		dialogObject = parent.Com_Parameter.Dialog;
	}
}

$(document).ready(function () {
	$(".lbpm_freeflow_type>label").click(function(){
		$(".lbpm_freeflow_type>label").removeClass("checked");
		$(this).addClass("checked");
		$(this).find('input:radio[name="handlerType"]').prop("checked","true").trigger($.Event("change"));
		var handlerTypeValue=$('input:radio[name="handlerType"]:checked').val();
		if(handlerTypeValue=="3"){
			$("#default_selected").attr("src","../images/default_selected.png");
			$("#serial_select").attr("src","../images/serial_noselected.png");
			$("#spit_select").attr("src","../images/spit_noselected.png");
		}
		if(handlerTypeValue=="0"){
			$("#default_selected").attr("src","../images/default_noselected.png");
			$("#serial_select").attr("src","../images/serial_selected.png");
			$("#spit_select").attr("src","../images/spit_noselected.png");
		}
		if(handlerTypeValue=="1"){
			$("#default_selected").attr("src","../images/default_noselected.png");
			$("#serial_select").attr("src","../images/serial_noselected.png");
			$("#spit_select").attr("src","../images/spit_selected.png");
		}
    });
	
	lbpmAllSettingInfo = new parent.KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
	window.lbpmAllSettingInfo = lbpmAllSettingInfo;
	// 意见类型  com.landray.kmss.sys.lbpmservice.support.actions.LbpmAuditNoteTypeAction
	if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
		var lbpmUrl = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=getAllAuditNodeType";
		$.ajax({
			url: lbpmUrl,
			async: false,
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.length > 0){
					data = JSON.stringify(data);
					$("input[name='opinionType']").val(data);
				}
			},
			error: function (er) {

			}
		});
	}

	//初始化数据
	var handlerData = '${JsParam.handlerData}';
	var handlerShow = '${JsParam.handlerShow}';
	if(handlerData!=null && handlerData!="" &&
			handlerShow!=null && handlerShow!=""){
		handlerData=JSON.parse(handlerData);
		handlerShow=JSON.parse(handlerShow);
		$("[name='handlerIds']").val(handlerData['id']);
		$("[name='handlerNames']").val(handlerData['name']);
		Address_QuickSelection("handlerIds","handlerNames",";",ORG_TYPE_POSTORPERSON|ORG_TYPE_ROLE,true,handlerShow,_addressRule,null,"");
	}

	_addressRule();

	var gwFlag = '${JsParam.htmlGw}';
	if(gwFlag == 'true'){
		$(".lbpm_freeflow_row .orgelement").click();
	}
});

</script>
<script>
function Com_GetUrlParameter(param){
	var url = location.href;
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null)
		return null;
	else
		return decodeURIComponent(arr[1]);
}

// 单选按钮值发生改变
$('input[type=radio][name=handlerType]').change(function() {
	selectRule();
	var ruleVal =  $("input[name='ruleVal']").val();
	if (!ruleVal) return;
	switchOptionType();
});

//内容发生改变设置值
// $("#addressRule").change(function(){
// 	selectRule();
// 	switchOptionType();
// });
function _addressRule() {
	selectRule();
	var ruleVal =  $("input[name='ruleVal']").val();
	if (!ruleVal) return;
	switchOptionType();
}

function getReturnVal(){
	var returnValue;
	if (typeof(window.$dialog) != 'undefined') {
		if(!_validation.validate()){
			return;
		}
		returnValue = buildReturnValue();
	} else {
		returnValue = buildReturnValue();
	}
	return returnValue;
}

function selectRule(){
	var returnValue = getReturnVal();
	var handlerIds = $("[name='handlerIds']").val();
	var checkedClass = $('.lbpm_freeflow_row').find(".checked");
	var handlerType = $(checkedClass).find("[name='handlerType']").val();
	if(returnValue){
		var handlerIds = returnValue['handlerIds'];
		var modelName = '${JsParam.modelName}';
		var urlPath = Com_Parameter.ContextPath + "km/imissive/km_imissive_rule/kmImissiveRule.do?method=findRuleListMobile";
		$.ajax({
			url: urlPath,
			async: false,
			data:{'fdImissiveType':modelName,"handlerIds":handlerIds,"handlerType":handlerType},
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.length > 0){
					data = JSON.stringify(data);
					$("input[name='ruleVal']").val(data);
				}
			},
			error: function (er) {

			}
		});
	}
}
function switchOptionType(){
	$("#addreRuleTab").html("");
	var returnValue = getReturnVal();
	if(returnValue){
		var handlerIds = returnValue['handlerIds'];
		if(handlerIds){
			//type 3:默认、0:串行、1：并行
			var handlerNames = returnValue['handlerNames'];
			var handlerType = returnValue['handlerType'];
			handlerIds = handlerIds.split(";");
			handlerNames = handlerNames.split(";");

			if(handlerIds.length > 1){
				$('#drawerGw_1').show();
				$('#drawerGw_2').show();
			}else{
				$('#drawerGw_1').hide();
				$('#drawerGw_2').hide();
			}
			var html='';

			var ruleVal =  $("input[name='ruleVal']").val();
			var opinionType =  $("input[name='opinionType']").val();
			if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
				html +="<tr><td>已选择</td><td>权限</td><td>意见类型</td></tr>";
			} else {
				html +="<tr><td>已选择</td><td>权限</td></tr>";
			}

			if(handlerType == "3"){
				var handlerName ='';
				for ( var key in handlerIds) {
					if (key != 0) {
						handlerName +=  ";" +handlerNames[key];
					} else {
						handlerName += handlerNames[key];
					}
				}
				handlerName = handlerName.replace(/;/g,"、");
				if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
					html +="<tr><td>"+handlerName+"</td><td><select id='ruleSelectId' name='ruleSelect' class='inputsgl'><option value='-1'>请选择</option></select></td><td><select name='opinionType' class='inputsgl'><option value='-1'>请选择</option></select></td></tr>";
				} else {
					html +="<tr><td>"+handlerName+"</td><td><select id='ruleSelectId' name='ruleSelect' class='inputsgl'><option value='-1'>请选择</option></select></td></tr>";
				}

			}else if(handlerType =="0" || handlerType =="1"){
				if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
					for ( var key in handlerIds) {
						html +="<tr><td>"+handlerNames[key]+"</td><td><select id='ruleSelectId' name='ruleSelect"+key+"' class='inputsgl'><option value='-1'>请选择</option></select></td><td><select name='opinionType"+key+"' class='inputsgl'><option value='-1'>请选择</option></select></td></tr>";
					}
				} else {
					for ( var key in handlerIds) {
						html +="<tr><td>"+handlerNames[key]+"</td><td><select id='ruleSelectId' name='ruleSelect"+key+"' class='inputsgl'><option value='-1'>请选择</option></select></td></tr>";
					}
				}
			}
			$("#addreRuleTab").append(html);
			ruleVal=JSON.parse(ruleVal);
			if (opinionType) {
				opinionType = JSON.parse(opinionType);
			}
			if(handlerType == "3"){
				var option = "";
				for ( var i in ruleVal) {
					if(ruleVal[i].value){
						option += "<option value='" + ruleVal[i].value + "'  >" + ruleVal[i].text + "</option>";
					}

				}
				$("select[name='ruleSelect']").append(option);

				var ruleSelParent = $("select[name='ruleSelect']");
				var optCount = $("select[name='ruleSelect']")[0].options.length;
				if(optCount>1) {
					$(ruleSelParent).prop("selectedIndex", 1);
				}
				//意见类型
				if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
					var optionHtml = '';
					$(opinionType).each(function(i){
						optionHtml += "<option value='" +  this.val + "'  >" +  this.key + "</option>";
					});
					$("select[name='opinionType']").append(optionHtml);

					// 初始化意见类型
					if(optCount>1){
						var parentStr = $(ruleSelParent).parent().parent();

						var opinionType = $(parentStr).find("select[name='opinionType']");
						var opinionTypeName = opinionType[0].name;

						var ruleId = $(ruleSelParent).val();
						var ruleSource = getRuleSource(ruleId);

						var itemVal = $(opinionType).find("option").removeAttr("selected");
						if(ruleSource){
							var fdNodeRuleVal = ruleSource[0][0].ruleVal[0].fdNodeRule;
							if(fdNodeRuleVal){
								fdNodeRuleVal=JSON.parse(fdNodeRuleVal);
								var optionVal =  fdNodeRuleVal['opinionType'];
								if(optionVal){
									$(opinionType).find('option[value="'+optionVal+'"]').prop('selected', true);
								}else{
									$("select[name='"+opinionTypeName+"']").find('option[value="-1"]').attr('selected','selected');
								}
							}
						}
					}
				}

				window.resizeTo(700, 440);
				var nodeGwSize = '${JsParam.nodeGwSize}';
				if(optCount == 2 && nodeGwSize =='true'){
					setTimeout(function(){
						$("#gwsave").click();
					}, 100);
				}
			}else if(handlerType =="0" || handlerType =="1"){
				for ( var i in handlerIds) {
					var option = "";
					var id = handlerIds[i];
					var showRule = ruleVal[i];
					for ( var key in showRule) {
						option += "<option value='" + showRule[key].value + "'  >" + showRule[key].text + "</option>";
					}
					$("select[name='ruleSelect"+i+"']").append(option);
					//意见类型
					if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
						var optionHtml = '';
						$(opinionType).each(function(){
							optionHtml += "<option col="+i+" value='" +  this.val + "'  >" +  this.key + "</option>";
						});
						$("select[name='opinionType"+i+"']").append(optionHtml);
					}
				}

				for ( var i in handlerIds) {
					// 初始化权限
					var ruleSelParent = $("select[name='ruleSelect"+i+"']");
					var optCount = $("select[name='ruleSelect"+i+"']")[0].options.length;
					if(optCount>1){
						$(ruleSelParent).prop("selectedIndex", 1);
						if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
							var parentStr = $(ruleSelParent).parent().parent();
							var opinionType = $(parentStr).find("select[name='opinionType"+i+"']");
							var opinionTypeName = opinionType[0].name;

							var ruleVal = $(ruleSelParent).val();
							var ruleSource = getRuleSource(ruleVal);

							var itemVal = $(opinionType).find("option").removeAttr("selected");
							if(ruleSource){
								var fdNodeRuleVal = ruleSource[0][0].ruleVal[0].fdNodeRule;
								if(fdNodeRuleVal){
									fdNodeRuleVal=JSON.parse(fdNodeRuleVal);
									var optionVal =  fdNodeRuleVal['opinionType'];
									if(optionVal){
										$(opinionType).find('option[value="'+optionVal+'"]').prop('selected', true);
									}else{
										$("select[name='"+opinionTypeName+"']").find('option[value="-1"]').attr('selected','selected');
									}
								}
							}
						}

					}
				}
				var imHeight =  document.body.scrollHeight||document.documentElement.scrollHeight;
				var nodeTypeHei = $('.lbpm_freeflow_winTitle').height();
				window.resizeTo(700, imHeight+nodeTypeHei);
			}
		}
	}
}

function getRuleSource(ruleId){
	var ruleSource = '';
	var urlPath = Com_Parameter.ContextPath + "km/imissive/km_imissive_rule/kmImissiveRule.do?method=getRuleSource";
	$.ajax({
		url: urlPath,
		data:{'ruleId':ruleId},
		async: false,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			if(data){
				ruleSource = data;
			}
		},
		error: function (er) {

		}
	});
	return ruleSource;
}


$("body").delegate('#ruleSelectId','change',function (){
	var parentStr = $(this).parent().parent();
	if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
		var opinionType = $(parentStr).find("select[name^='opinionType']");
		var opinionTypeName = opinionType[0].name;
		var ruleVal = $(this).val();
		var ruleSource = getRuleSource(ruleVal);
		
		var itemVal = $(opinionType).find("option").removeAttr("selected");
		 	
		if(ruleSource){
			var fdNodeRuleVal = ruleSource[0][0].ruleVal[0].fdNodeRule
			if(fdNodeRuleVal){
				fdNodeRuleVal=JSON.parse(fdNodeRuleVal);
				var optionVal =  fdNodeRuleVal['opinionType'];
				if(optionVal){
					$(opinionType).find('option[value="'+optionVal+'"]').prop('selected', true);
				}else{
					$("select[name='"+opinionTypeName+"']").find('option[value="-1"]').attr('selected','selected');
				}
			}
		}
	}
});

//{handlerIds: "1746c40691a4fd369400dbf4e1a85072", handlerNames: "汕头一号", handlerType: "3"}
function returnDialogValue(){
	if(!_validation.validate())
		return;
	returnValue = buildReturnValue();
	window.close();
}

function save(){
	if (typeof(window.$dialog) != 'undefined') {
		if(!_validation.validate())
			return;
		returnValue = buildReturnValue();
		$dialog.hide(returnValue);
	} else {
		returnDialogValue();
	}
}

function buildReturnValue(){
	var rtn = {};
	var handlerIds = $("[name='handlerIds']").val();
	var handlerType = $("[name='handlerType']:checked").val();
	rtn["handlerIds"] = handlerIds;
	rtn["handlerNames"] = $("[name='handlerNames']").val();
	rtn["handlerType"] = handlerType;
	
	var selectRule = {};
	if(handlerType == "3"){
		var val = $("select[name='ruleSelect']").find("option:selected").val();
		var text = $("select[name='ruleSelect']").find("option:selected").text();
		if(val != "-1"){
			var ruleSource = getRuleSource(val);
			ruleSource = JSON.stringify(ruleSource);
			rtn["handlerRule"] = ruleSource;
			rtn["handlerRuleTitle"] = text;
		}
		
		//意见类型
		if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
			var opinionTypeVal = $("select[name='opinionType']").find("option:selected").val();
			if(opinionTypeVal != "-1"){
				rtn["opinionType"] = opinionTypeVal;
			}
		}
	}else if(handlerType =="0" || handlerType =="1"){
		var count = handlerIds.split(";");
		for ( var key in count) {
			var val = $("select[name='ruleSelect"+key+"']").find("option:selected").val();
			var text = $("select[name='ruleSelect"+key+"']").find("option:selected").text();
			
			if(val != "-1"){
				var ruleSource = getRuleSource(val);
				ruleSource = JSON.stringify(ruleSource);
				selectRule["ruleSelect"+key+""] = ruleSource;
				selectRule["ruleSelectTitle"+key+""] = text;
			}
			
			//意见类型
			if (lbpmAllSettingInfo['isOpinionTypeEnabled'] == "true") {
				var opinionTypeVal = $("select[name='opinionType"+key+"']").find("option:selected").val();
				if(opinionTypeVal != "-1"){
					selectRule["opinionType"+key+""] = opinionTypeVal;
				}
			}
		}
		rtn["handlerRule"] = selectRule;
	}
	return rtn;
}

function closeDialog(){
	if(!confirm('<bean:message key="message.closeWindow"/>')){
		return;
	}
	if (typeof(window.$dialog) != 'undefined') {
		$dialog.hide();
	}else{
		Com_CloseWindow();
	}
}
</script>
</body>
</html>