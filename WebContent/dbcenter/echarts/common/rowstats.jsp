<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {rowstats:[{key,split,format,argument}]} --%>
<script>
DocList_Info.push("rowstats_DocList");

function changeRowStatsFormatVal(obj,i){
	debugger;
	var argumentParent = "rowstats["+i+"].argumentParent" ;
	if($(obj).val()=='point'){//小数
		$("[id='" +argumentParent+ "']").show();
	}else{
		$("[id='" +argumentParent+ "']").hide();
	} 
}

function _getRowStatsColumn(){
	debugger;
	var ret = [];
	if(!_pageLoaded){
		var rowstats = _data["rowstats"]||[];
		for(var i=0;i<rowstats.length;i++){
			ret.push({key:rowstats[i]["key"],name:rowstats[i]["label"]});
		}
		return ret;
	}
	$.each($("#rowstats_DocList tr:gt(0)"),function(i){
		debugger;
		 var el="rowstats["+i+"].key";
		 var key = document.getElementsByName(el)[0].value;
		 el="rowstats["+i+"].label";
		 var label = document.getElementsByName(el)[0].value;
		if(label==""){
			label = key;
		}
		ret.push({key:key,name:label});
	});
	return ret;
}

function _triggerRowStatsChange(){
	_pageLoaded = true;
	 var array =  _getRowStatsColumn();
	 var fields = _data["fields"] ;
	 array = array.concat(fields);
	 var options = _pushNotExsitElement(fields,array);
	_restSelect("colstats","key",options);
	_restSelect("columns","key",options);
}

</script>
<table id="rowstats_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
	<tr class="tr_normal_title">
		<td style="width:50px;">${ lfn:message('dbcenter-echarts:output_hint_001')}</td>
		<td width="15%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.label') }
		<a href="javascript:_showHelpLogInfo('rowstatsLabelHelp');"><span id="rowstatsLabelHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td width="15%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.name') }
		<a href="javascript:_showHelpLogInfo('rowstatsNameHelp');"><span id="rowstatsNameHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		
		
		
		<td width="28%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.formal') }
		<a href="javascript:_showHelpLogInfo('rowstatsFormalHelp');"><span id="rowstatsFormalHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		
		
		<td width="18%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }
		<a href="javascript:_showHelpLogInfo('rowstatsFormatHelp');"><span id="rowstatsFormatHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td width="10%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.sort') }
		<a href="javascript:_showHelpLogInfo('rowstatsSortHelp');"><span id="rowstatsSortHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:150px;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<input name="rowstats[!{index}].label" type="text" onchange="_triggerRowStatsChange();" class="inputsgl" size='15' data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:table.model.rowstats.col.label') }">
		</center></td>
		<td><center>
			<input name="rowstats[!{index}].key" type="text" onchange="_triggerRowStatsChange();" class="inputsgl" size='15' data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:table.model.rowstats.col.name') }">
		</center></td>
		<td><center>
			<input name="rowstats[!{index}].expressionText" type="text" readonly class="inputsgl" validate="required" size='25' data-dbecharts-config="fdCode" subject="${ lfn:message('dbcenter-echarts:table.model.rowstats.col.formal') }">
			<input name="rowstats[!{index}].expressionValue" type="hidden" data-dbecharts-config="fdCode"><span class='highLight'><a href='javascript:selectFormula(!{index});'>选择</a></span>
		</center></td>
		<td><center>
			<select name="rowstats[!{index}].format"  data-dbecharts-config="fdCode"  onchange="changeRowStatsFormatVal(this,!{index})"> 
				<option value="">${ lfn:message('dbcenter-echarts:output_hint_009')}</option>
			   <option value="point">${ lfn:message('dbcenter-echarts:relation.point')}</option>
			   <option value="thousandth">${ lfn:message('dbcenter-echarts:relation.thousandth')}</option>
			   <option value="percentage">${ lfn:message('dbcenter-echarts:relation.percentage')}</option>
		   </select>
		  <span id="rowstats[!{index}].argumentParent" style="display:none;">
			 <input type="text" class="inputsgl"  size='5' name="rowstats[!{index}].argument" data-dbecharts-config="fdCode"/>
          </span>
		</center>
		</td>

		<td><center>
			<select name="rowstats[!{index}].sort" data-dbecharts-config="fdCode">
				<option value="">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.sort.no')}</option>
				<option value="desc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.des')}</option>
				<option value="asc">${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.asc')}</option>
			</select>
		</center></td>

		<td><center>
			<a href="#" onclick="DocList_DeleteRow();_triggerRowStatsChange();return false;">${ lfn:message('dbcenter-echarts:output_hint_012')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:output_hint_013')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:output_hint_014')}</a>
		</center>
		</td>
	</tr>
</table>