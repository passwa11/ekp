/**
 * 仿真计划js
 */
//初始化
$(function(){
	//给exampleId隐藏域绑定值改变事件
	var $exampleId = $("input[name='exampleIds']");
	//隐藏域值改变事件
	$exampleId.on("drawExampleTable",drawExampleTable);
	$exampleId.on("change", getLbpmSimulationExampleById);
	//根据计划id获取相关实例
	getLbpmSimulationExampleByPlanId();
	
	initFdUseTypeRaido();
});

//初始化单选按钮
function initFdUseTypeRaido(){
	var status = $("input[name='method_GET']").val();
	var fdUseType = $("input[name='fdUseType']").val();
	if (fdUseType === "true"){
		$("input[name='userType'][value='true']").prop("checked",true);
		if (status==="view"){
			$("input[name='userType'][value='false']").prop("disabled","disabled");
		}
	}
	if(fdUseType === "false"){
		$("input[name='userType'][value='false']").prop("checked",true);
		if (status==="view"){
			$("input[name='userType'][value='true']").prop("disabled","disabled");
		}
		
	}
}

//查看执行计划明细
//status: total,success,fail
function viewLbpmSimulationPlanRecord(self,status){
	var recordId = $(self).closest("tr").attr("kmss_fdid");
	var planId = $(document.forms["lbpmSimulationPlanForm"]).find("input[name='fdId']").val();
	var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_simulation_log/lbpmSimulationLog.do?method=list&recordId=" + recordId
			+ "&status=" + status + "&planId=" + planId;
	window.open(url);
}
	
//绘制实例列表事件
function drawExampleTable(event,rtnVal){
	var $exampleTable = $("#lbpmSimulationExampleTable");
	$exampleTable.find("tr:gt(0)").remove();
	var data = rtnVal.data;
	if (data.length > 0){
		$exampleTable.show();
		for(var i = 0; i < data.length; i++){
			var tr = getTrHtml(data[i]);
			$exampleTable.append(tr);
		}
	}
}
	
function getTrHtml(obj){
	var status = $("input[name='method_GET']").val();
	var html = new Array();
	html.push("<tr>");
	html.push("<td>");
	if (status === "edit" ||status === "add" ){
		html.push("<input type='checkbox' name='exampleId' value='" + obj.fdId + "' onclick='_lbpmSimulationChangeSelectAll(this);'/>&nbsp;&nbsp;&nbsp;&nbsp;");
	}
	html.push(obj.fdTitle);
	html.push("</td>");
	html.push("<td>");
	html.push(obj.fdCreatorName);
	html.push("</td>");
	html.push("<td>");
	html.push(obj.fdCreateTime);
	html.push("</td>");

	if (status != "view"){
		html.push("<td>");
		html.push('<a href="javascript:;" class="com_btn_link" onclick="Com_EventPreventDefault();_removeLbpmSimulationExample(this);">');
		html.push('<img alt="" src="/ekp/resource/style/default/icons/delete.gif" title="删除">');		 
		html.push('</a>');			
	}
	html.push("</tr>");
	return html.join("");
}
	
//根据实例id获取相关的实例信息
function getLbpmSimulationExampleById(){
	var $example = $("input[name='exampleIds']")
	//实例id
	var ids = $example.val();
	
	//如果取消了值清空 表格
	if (ids == ""){
		 $example.trigger("drawExampleTable",[{data:[]}]);
	}
	//如果ids存在则发送请求获取相关联实例id
	if (ids){
		var obj = {};
		obj.url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSimulationExample.do?method=queryExampleByIds";
		obj.data = {ids:ids};
		obj.success = function(rtnVal){
			//发布绘制实例表格事件
			if (rtnVal.type && rtnVal.data && rtnVal.data.length > 0){
				var data = rtnVal.data
				 $example.trigger("drawExampleTable",[rtnVal]);
			}
		}
		getData(obj);
	}
}
	
//发送请求获取数据
function getData(obj){
	$.ajax({
		url: obj.url,
		type: 'POST',
		async: false,
		dataType: 'json',
		data: obj.data,
		success:obj.success
	});
}
	
//发送请求根据计划id获取相关的实例
function getLbpmSimulationExampleByPlanId(){
	//获取计划id
	var ids = $("input[name='fdId']").val();
	var $exampleId = $("input[name='exampleIds']");
	var $exampleName = $("input[name='exampleName']");
	//如果fdId存在则发送请求获取相关联实例id
	
	if (ids){
		var obj = {};
		obj.url = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmSimulationExample.do?method=queryExampleByPlanId";
		obj.data = {planId:ids};
		obj.success = function(rtnVal){
			if(rtnVal.type && rtnVal.data &&  rtnVal.data.length > 0){
				var idArr = new Array();
				var nameArr = new Array();
				$.each(rtnVal.data,function(index,val){
					idArr.push(val.fdId);
					nameArr.push(val.fdTitle);
				});
				$exampleId.val(idArr.join(";"));
				$exampleName.val(nameArr.join(";"));
				//发布绘制实例表格事件
				$exampleId.trigger("drawExampleTable",[rtnVal]);
			}
		}
		getData(obj);
	}
}
	
//删除选中的仿真实例
function _deleteLbpmSimulationExamples(){
	var inputArr = new Array();
	var $exampleTable = $("#lbpmSimulationExampleTable");
	inputArr = $("input:checked",$exampleTable);
	var $exampleId = $("input[name='exampleIds']");
	//校验是否有选中表达式
	if (inputArr.length <= 0){
		alert("请先选择要删除的实例!");
	}else{
		//获取所有的仿真实例
		inputArr = $exampleTable.find("input[type='checkbox'][name != 'deleteAllExample']");
		//总数
		var size = inputArr.length;
		//删除全部的时候隐藏实例表
		var isHide = false,length=0;
		var idArr =  new Array();
		inputArr.each(function(index,ele){
			if (ele.checked){
				length++; //删除的个数
				$(ele).closest("tr").remove();
			}else{
				idArr.push($(ele).val());
			}
		});
		$exampleId.val(idArr.join(";"));
	}
}

//删除单个仿真实例
function _removeLbpmSimulationExample(self){
	var $exampleId = $("input[name='exampleIds']");
	var tr = $(self).closest('tr');
	tr.remove();
	//获取所有的仿真实例
	var $exampleTable = $("#lbpmSimulationExampleTable");
	var inputArr = $exampleTable.find("input[type='checkbox'][name != 'deleteAllExample']");
	var idArr =  new Array();
	$.each(inputArr,function(index,dom){
		idArr.push(dom.value);
	});
	$exampleId.val(idArr.join(";"));
}

//仿真实例复选框点击事件
function _lbpmSimulationChangeSelectAll(self){
	//取消选中,则设置全选按钮为不选中
	var $lbpmSimulationExampleTable = $("#lbpmSimulationExampleTable");
	var $exampleCheckBoxs = $lbpmSimulationExampleTable.find("input[type='checkbox'][name!= 'deleteAllExample']");
	var $deleteAllCheckBox = $lbpmSimulationExampleTable.find("input[type='checkbox'][name = 'deleteAllExample']")
	if (!self.checked){
		$deleteAllCheckBox.prop("checked","");
	}
	//选中，则判断其它选项是否选中，若都选中则设置全选按钮选中
	if (self.checked){
		var isChecked = true;
		 $exampleCheckBoxs.each(function(index,ele){
			if (!ele.checked){
				isChecked = false;
			}
		});
		if (isChecked){
			$deleteAllCheckBox.prop("checked","true");
		}
	}
}

//实例全选按钮事件
function _selectAllLbpmSimulationExample(self){
	var input = $("#lbpmSimulationExampleTable").find("input[type='checkbox'][name != 'deleteAllExample']");
	if (self.checked){
		input.each(function(index,ele){
			if (!ele.checked){
				$(ele).prop("checked","true");
			}
		});
	}else{
		input.each(function(index,ele){
			$(ele).prop("checked","");
		});
	}
}

//改变是否启用隐藏域的值
function changeFdUserTypeVal(self){
	$("input[name='fdUseType']").val($(self).val());
}

