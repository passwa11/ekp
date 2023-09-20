<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- {colstats:[{key,split,format,argument}]} --%>
<script>
DocList_Info.push("colstats_DocList");

function changeColStatsFormatVal(obj,i){
	var argumentParent = "colstats["+i+"].argumentParent" ;
	if($(obj).val()=='point'){//小数
		$("[id='" +argumentParent+ "']").show();
	}else{
		$("[id='" +argumentParent+ "']").hide();
	} 
}

function _doCallback4Other(_data,_fields){
	 var array =  _getRowStatsColumn();
	 array = array.concat(_fields);

	 //构建所有SQL语句的列统计定义下拉控件
	var colstats = _data["colstats"]||[];
	var options = _pushNotExsitElement(colstats,array);
	_restSelect("colstats","key",options);

	 //构建所有SQL语句的列表视图配置下拉控件
	var columns = _data["columns"]||[];
	options = _pushNotExsitElement(columns,array);
	_restSelect("columns","key",options);
}

$(document).on("table-add-new",function(event,argu){
	var id = argu.table.id;
	if(!(id=="outputs_DocList" || id=="colstats_DocList" || id=="columns_DocList")){
		return;
	}	
	if(!_data["fields"]){
		var code = LUI.$('[name="fdCode"]').val();
		 _data = code==''?{}:LUI.toJSON(code);
		 if(_fields==null){
			_fields = _data["fields"];
		 }
	}
	var $select = $(argu.row).find(".key");
	var optArray = _data["fields"];
	if(!optArray){
		return ;
	}
	if(id=="colstats_DocList" || id=="columns_DocList"){
		 var array =  _getRowStatsColumn();
		 optArray = optArray.concat(array);
	}
	_createOptions4Select($select,optArray);
});

</script>
<table id="colstats_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
	<tr class="tr_normal_title">
		<td style="width:50px;">${ lfn:message('dbcenter-echarts:output_hint_001')}</td>
		<td width="30%">${ lfn:message('dbcenter-echarts:table.model.rowstats.col.name') }
		<a href="javascript:_showHelpLogInfo('colstatsKeyHelp');"><span id="colstatsKeyHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td width="25%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.summaryType') }
		<a href="javascript:_showHelpLogInfo('colstatsTypeHelp');"><span id="colstatsTypeHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td width="30%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.dataFormat') }
		<a href="javascript:_showHelpLogInfo('colstatsFormatHelp');"><span id="colstatsFormatHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
		</td>
		<td style="width:150px;">
			<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.add') }</a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td><center>
			<select name="colstats[!{index}].key"  class="inputsgl key" style="width:90%;"  data-dbecharts-config="fdCode"  validate="required" subject="${ lfn:message('dbcenter-echarts:table.model.colstats.col.name') }">
			</select>
			<!--
			<input name="colstats[!{index}].key" type="text" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode"  validate="required" subject="${ lfn:message('dbcenter-echarts:table.model.colstats.col.name') }">
			-->
		</center></td>
		<td><center>
			<select name="colstats[!{index}].type" data-dbecharts-config="fdCode">
				<option value="sum">${ lfn:message('dbcenter-echarts:relation.sum')}</option>
				<option value="avg">${ lfn:message('dbcenter-echarts:relation.avg')}</option>
				<option value="max">${ lfn:message('dbcenter-echarts:relation.max')}</option>
				<option value="min">${ lfn:message('dbcenter-echarts:relation.min')}</option>
				<option value="count">${ lfn:message('dbcenter-echarts:relation.count')}</option>
			</select>
		</center></td>
		<td><center>
			<select name="colstats[!{index}].format"  data-dbecharts-config="fdCode" onchange="changeColStatsFormatVal(this,!{index})"> 
				<option value="">${ lfn:message('dbcenter-echarts:output_hint_009')}</option>
			   <option value="point">${ lfn:message('dbcenter-echarts:relation.point')}</option>
			   <option value="thousandth">${ lfn:message('dbcenter-echarts:relation.thousandth')}</option>
			   <option value="percentage">${ lfn:message('dbcenter-echarts:relation.percentage')}</option>
		   </select>
		  <span id="colstats[!{index}].argumentParent" style="display:none;">
			 <input type="text" class="inputsgl"  size='5' name="colstats[!{index}].argument" data-dbecharts-config="fdCode"/>
          </span>
		</center>
		</td>

		<td><center>
			<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:output_hint_012')}</a>
			<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:output_hint_013')}</a>
			<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:output_hint_014')}</a>
		</center>
		</td>
	</tr>
</table>