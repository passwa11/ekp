<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {outputs:[{key,split,format,argument}]} --%>
<script>
DocList_Info.push("outputs_DocList");
function changePageFormat(value,i,argument,formatVal){
	
	if((typeof i) == "string"){
		if(i.startsWith("outputs")){
			//i 拿到的是name名称，把name里的数字提取出来  ： outputs[0].format -> 0
			i = i.substring(8,i.indexOf("]"));
		}
	}
	argument=argument||'';
	formatVal=formatVal||'';
    var argumentParent="outputs["+i+"].argumentParent";
    var htmlStr="";
    $("[id='" +argumentParent+ "']").empty();
	if(value=='Number'){//显示数字下拉框
		var naArg="outputs["+i+"].argument";
		//$.('textarea[name=naArg]').hide();
		
		htmlStr="<select name=\"outputs["+i+"].argument\" align=\"left\" data-dbecharts-config=\"fdCode\" value=\""+argument+"\"  onchange=\"changePageFormatVal(this,"+i+")\">"+ 
		   "<option value=\"thousandth\">千分符</option>"+
		   "<option value=\"percentage\" >百分比</option>"+
		   "<option value=\"point\">小数点</option>"+
		   "</select>"+"&nbsp;&nbsp;";
		   if(argument=='point'){//小数点
			   htmlStr+="<input type=\"text\" class=\"inputsgl\"  name=\"outputs["+i+"].formatVal\" data-dbecharts-config=\"fdCode\"  value=\""+formatVal+"\" />";  
		   }
	}else if(value != ""){//非数字且非‘不转换’
		htmlStr="<center><textarea name=\"outputs["+i+"].argument\" style=\"width:99%; height:40px;\" data-dbecharts-config=\"fdCode\">"+argument+"</textarea></center>";
	}
	$("[id='" +argumentParent+ "']").html(htmlStr);
	if(value=='Number'){
		 var argumentName="outputs["+i+"].argument";
		 $("select[name='" +argumentName+ "']").val(argument);
	}
}

function changePageFormatVal(obj,i){
	//var argumentName="outputs["+i+"].argument";
	//var argumentValue=$("input[name='" +argumentName+ "']").val();
	var formatValName="outputs["+i+"].formatVal";
	if($(obj).val()=='point'){//小数
	    $("input[name='" +formatValName+ "']").remove();  //有隐藏的数据，要先移除，不然首次选择数字不会出现输入框
		if($("input[name='" +formatValName+ "']").size()==0){
			$(obj).after("&nbsp;&nbsp;"+"<input type=\"text\" class=\"inputsgl\"  name=\"outputs["+i+"].formatVal\" data-dbecharts-config=\"fdCode\"/>");
		}
	}else{
		$("input[name='" +formatValName+ "']").remove();
	} 
}
</script>
<table id="outputs_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
	<tr class="tr_normal_title">
		<td style="width:40px;">${ lfn:message('dbcenter-echarts:output_hint_001')}</td>
		<td style="width:150px;">${ lfn:message('dbcenter-echarts:output_hint_002')}
		<a href="javascript:_showHelpLogInfo('outputsKeyHelp');"><span id="outputsKeyHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:150px;">${ lfn:message('dbcenter-echarts:output_hint_003')}
		<a href="javascript:_showHelpLogInfo('outputsSplitHelp');"><span id="outputsSplitHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:150px;">${ lfn:message('dbcenter-echarts:output_hint_004')}
		</td>
		<td>${ lfn:message('dbcenter-echarts:output_hint_005')}
		<a href="javascript:_showHelpLogInfo('outputsFormatHelp');"><span id="outputsFormatHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>								
		</td>
		<td style="width:110px;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:output_hint_006')}</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<select name="outputs[!{index}].key"  class="inputsgl key" style="width:90%;"  data-dbecharts-config="fdCode"  validate="required" subject="${ lfn:message('dbcenter-echarts:output_hint_007')}">
			</select>
			<!--
			<input name="outputs[!{index}].key" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:output_hint_007')}" placeholder="${ lfn:message('dbcenter-echarts:output_hint_008')}">
			-->
		</center></td>
		<td><center>
			<input name="outputs[!{index}].split" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode">
		</center></td>
		<td><center>
			<select name="outputs[!{index}].format" data-dbecharts-config="fdCode" onchange="changePageFormat(this.value,this.name)">
				<option value="">${ lfn:message('dbcenter-echarts:output_hint_009')}</option>
				<option value="Date">${ lfn:message('dbcenter-echarts:output_hint_010')}</option>
				<option value="Enum">${ lfn:message('dbcenter-echarts:output_hint_011')}</option>
				<option value="Number">${ lfn:message('dbcenter-echarts:output_hint_015')}</option>
			</select>
		</center></td>
		<td>
		   <input type="hidden" name="outputs[!{index}].argument" data-dbecharts-config="fdCode"/>
           <input type="hidden" name="outputs[!{index}].formatVal" data-dbecharts-config="fdCode"/>
		  <div id="outputs[!{index}].argumentParent">
           
          </div>
		</td>
		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:output_hint_012')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:output_hint_013')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:output_hint_014')}</a>
		</center>
		</td>
	</tr>
</table>