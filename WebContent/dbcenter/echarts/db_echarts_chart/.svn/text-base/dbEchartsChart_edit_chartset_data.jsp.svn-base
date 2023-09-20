<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
DocList_Info.push("series_DocList");

$(document).on("table-add-new",function(event,argu){
	var id = argu.table.id;
	if(!(id=="outputs_DocList" || id=="series_DocList")){
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
	_createOptions4Select($select,optArray);
});

function _doCallback4Other(_data,_fields){
	 //构建数据维度（X轴）下拉控件
	var $select = $("select[name='xAxis.key']");
	var bv =$select.val();
	_createOptions4Select($select,_fields);
	$select.val(bv);

	 //构建数据度量（Y轴）下拉控件
	var series = _data["series"]||[];
	options = _pushNotExsitElement(series,_fields);
	_restSelect("series","key",options);
}

Com_AddEventListener(window, "load", function(){
	var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
	var data = value==''?{}:LUI.toJSON(value);
	if(!data["fields"]){
		if(!data["xAxis"]){
			return;
		}
		var key = data["xAxis"]["key"];
		data["fields"]=[{key:key,name:key}];
	}
	 //构建数据维度（X轴）下拉控件
	var $select = $("select[name='xAxis.key']");
	var bv =$select.val();
	_createOptions4Select($select,data["fields"]||[]);
	$select.val(bv);
});

</script>
<center id="echart_area_chartset_data" >
	<div>
		<table class="tb_normal"  width=100%>
		
			<!-- 数据维度（X轴） -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.data.x') }
				</td>
			</tr>
			<tr>
				<td>
					<table class="config_table" width="100%">
					<tr>
						<td>
							<div class="config_item">
								${ lfn:message('dbcenter-echarts:canshu') }:
								<select name="xAxis.key"  class="inputsgl key"  data-dbecharts-config="fdCode"  validate="required" subject="${ lfn:message('dbcenter-echarts:xShujucanshu') }">
								</select>
								<!--
								<input name="xAxis.key" class="inputsgl" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:xShujucanshu') }">
								-->
							</div>
						</td>
						<td>
							<div class="config_item">
								<label><input name="xAxis.unique" type="checkbox" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:quchuchongfuzhi') }</label>
							</div>
						</td>
						<td>
							<div class="config_item">
								${ lfn:message('dbcenter-echarts:chongxinpaixu') }:
								<select name="xAxis.order" data-dbecharts-config="fdCode">
									<option value="">${ lfn:message('dbcenter-echarts:buchuli') }</option>
									<option value="normal">${ lfn:message('dbcenter-echarts:shunxu') }</option>
									<option value="desc">${ lfn:message('dbcenter-echarts:nixu') }</option>
								</select>
								<a href="javascript:_showHelpLogInfo('xAxisOrderHelp');"><span id="xAxisOrderHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
							</div>
						</td>
						<td>
							<div class="config_item2">
								URL：<input name="chart.url" class="inputsgl" data-dbecharts-config="fdCode" subject="URL" placeholder="${ lfn:message('dbcenter-echarts:url_placeholder') }" style="width:400px;">
							</div>
						</td>
						<td>
							<div class="config_item">
								<label><input name="chart.inner" type="checkbox" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:neibutubiao') }</label>
								<a href="javascript:_showHelpLogInfo('xAxisInnerHelp');"><span id="xAxisInnerHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
							</div>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<!-- 数据度量（Y轴） -->
			<tr class="tr_normal_title">
				<td class="config_title" align="left">
					${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.data.y') }
				</td>
			</tr>
			<tr>
				<td>

					<table id="series_DocList" class="tb_normal" width="100%" data-dbecharts-table="fdCode">
						<tr class="tr_normal_title">
							<td style="width:3%;">${ lfn:message('dbcenter-echarts:xuhao') }</td>
							<td style="width:8%;">${ lfn:message('dbcenter-echarts:mingcheng') }</td>
							<td style="width:12%;">${ lfn:message('dbcenter-echarts:canshu') }</td>
							<td style="width:10%;">${ lfn:message('dbcenter-echarts:zhanxian') }</td>
							<td style="width:15%;">${ lfn:message('dbcenter-echarts:shujubuquan') }
								<a href="javascript:_showHelpLogInfo('xAxisshujubuquanHelp');"><span id="xAxisshujubuquanHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
							</td>
							<td style="width:10%;">${ lfn:message('dbcenter-echarts:fenzucanshu') }
								<a href="javascript:_showHelpLogInfo('xAxisfenzucanshuHelp');"><span id="xAxisfenzucanshuHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
							</td>
							<td style="width:12%;">${ lfn:message('dbcenter-echarts:duiji') }
								<a href="javascript:_showHelpLogInfo('xAxisduijiHelp');"><span id="xAxisduijiHelp" class="glyphicon glyphicon-question-sign" style="color:#4898d5"></span></a>						
							</td>
							<td style="width:8%;">${ lfn:message('dbcenter-echarts:youY') }</td>
							<td style="width:12%;">${ lfn:message('dbcenter-echarts:tuxingxuanxiang') }</td>
							<td style="width:10%;">
								<a href="#" onclick="DocList_AddRow();return false;">${ lfn:message('dbcenter-echarts:tianjia') }</a>
							</td>
						</tr>
						<!--基准行-->
						<tr KMSS_IsReferRow="1" style="display:none">
							<td KMSS_IsRowIndex="1"></td>
							<td><center>
								<input name="series[!{index}].name" class="inputsgl" style="width:100px;" data-dbecharts-config="fdCode">
							</center></td>
							<td><center>
							<!--
								<input name="series[!{index}].key" class="inputsgl" style="width:160px;" data-dbecharts-config="fdCode" validate="required" subject="${ lfn:message('dbcenter-echarts:yCanshu') }">
							-->
							<select name="series[!{index}].key"  class="inputsgl key" style="width:90%;"  data-dbecharts-config="fdCode"  validate="required" subject="${ lfn:message('dbcenter-echarts:yCanshu') }">
							</select>

							</center></td>
							<td><center>
								<select name="series[!{index}].type" data-dbecharts-config="fdCode">
									<option value="line">${ lfn:message('dbcenter-echarts:zhexiantu') }</option>
									<option value="linearea">${ lfn:message('dbcenter-echarts:mianjitu') }</option>
									<option value="bar">${ lfn:message('dbcenter-echarts:zhuzhuangtu') }</option>
									<option value="pie">${ lfn:message('dbcenter-echarts:bingtu') }</option>
									<option value="gauge">${ lfn:message('dbcenter-echarts:yibiaopan') }</option>
									<option value="none">${ lfn:message('dbcenter-echarts:shujuxulie') }</option>
								</select>
							</center></td>
							<td><div style="text-align: right; margin-right:4px;">
								${ lfn:message('dbcenter-echarts:xCanshu') }:<input name="series[!{index}].xKey" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode">
								<br>
								${ lfn:message('dbcenter-echarts:morenzhi') }:<input name="series[!{index}].defaultValue" class="inputsgl" style="width:80px;" data-dbecharts-config="fdCode">
							</div></td>
							<td><center>
								<input name="series[!{index}].group" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode">
							</center></td>
							<td><center>
								<input name="series[!{index}].stack" class="inputsgl" style="width:99%;" data-dbecharts-config="fdCode" placeholder="${ lfn:message('dbcenter-echarts:zhixiangtongshiduiji') }">
							</center></td>
							<td><center>
								<label><input type="checkbox" name="series[!{index}].forY2" data-dbecharts-config="fdCode">&nbsp;${ lfn:message('dbcenter-echarts:shi') }</label>
							</center></td>
							<td><center>
								<textarea name="series[!{index}].properties" style="width:99%; height:40px;" data-dbecharts-config="fdCode"></textarea>
							</center></td>
							<td><center>
								<a href="#" onclick="DocList_DeleteRow();return false;">${ lfn:message('dbcenter-echarts:shanchu') }</a>
								<a href="#" onclick="DocList_MoveRow(-1);return false;">${ lfn:message('dbcenter-echarts:shangyi') }</a>
								<a href="#" onclick="DocList_MoveRow(1);return false;">${ lfn:message('dbcenter-echarts:xiayi') }</a>
							</center></td>
						</tr>
					</table>

				</td>
			</tr>

		</table>		
	</div>
</center>