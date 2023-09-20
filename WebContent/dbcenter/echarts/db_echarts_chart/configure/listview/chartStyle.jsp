<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var chartStyle = layout.parent;
var config = chartStyle.curItem.chart.viewConfig;
if(!config){
	return "";
}
var value = chartStyle.value;
if(config.hasOwnProperty("textPosition")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.titlePosition') }</td>
			<td>
				<div class="chartOption_titlePosition">
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.leftDistance') }:</span>
					<select name="chartOption.title.x" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="left">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.left') }</option>
						<option value="center">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="right">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.right') }</option>
					</select>
					<br/>
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.topDistance') }:</span>
					<select name="chartOption.title.y" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="top">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.top') }</option>
						<option value="middle">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="bottom">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.bottom') }</option>
					</select>
				</div>
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("legendPosition")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.legendPosition') }</td>
			<td>
				<div class="chartOption_legendPosition">
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.leftDistance') }:</span>
					<select name="chartOption.legend.x" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="left">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.left') }</option>
						<option value="center">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="right">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.right') }</option>
					</select>
					<br/>
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.topDistance') }:</span>
					<select name="chartOption.legend.y" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="top">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.top') }</option>
						<option value="middle">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="bottom">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.bottom') }</option>
					</select>
				</div>
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("legendOrient")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.legendType') }</td>
			<td>
				<select name="chartOption.legend.orient" data-dbecharts-config="fdCode" data-valuechange="true">
					<option value="horizontal">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.hori') }</option>
					<option value="vertical">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.verti') }</option>
				</select>
			</td>
		</tr>
	$}
}

{$
	<tr class="dynamicTr">
		<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.showLayout') }</td>
		<td>
			<select name="chartOption.chartType" data-dbecharts-config="fdCode" data-valuechange="true">
$}
		var key = chartStyle.value.chartType;
		key = key.split("-")[0];
		for(var i = 0;i < chartStyle.curItem.chart.series.length;i++){
			var s = chartStyle.curItem.chart.series[i];
			{$
				<option value="{%key%}-{%s.type%}">{%s.text%}</option>
			$}
		}
{$
			</select>
			<input name="chartOption.personality" type="hidden" data-dbecharts-config="fdCode" data-valuechange="true"/>
		</td>
	</tr>
$}


if(config.hasOwnProperty("gridMargin")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.spacing') }</td>
			<td>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.leftSpacing') }:</span>
				<input name="chartOption.grid.left" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="px" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.leftSpacing') }" />px
				</br>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.topSpacing') }:</span>
				<input name="chartOption.grid.top" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="px" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.topSpacing') }"/>px
				</br>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.bottomSpacing') }:</span>
				<input name="chartOption.grid.bottom" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="px" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.bottomSpacing') }"/>px
				</br>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.rightSpacing') }:</span>
				<input name="chartOption.grid.right" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="px" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.rightSpacing') }"/>px
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("seriesPieRadius")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartSize') }</td>
			<td>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.outsideDiameter') }:</span>
				<input name="chartOption.series[].radius[1]" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="%" validate="digits range(0,100)" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.outsideDiameter') }"/>%
				</br>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.internalDiameter') }:</span>
				<input name="chartOption.series[].radius[0]" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="px" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.internalDiameter') }"/>px
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("seriesGaugeRadius")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartSize') }</td>
			<td>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.radius') }:</span>
				<input name="chartOption.series[].radius" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="%" validate="digits range(0,100)" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.radius') }"/>%
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("seriesPieCenter")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartPosition') }</td>
			<td>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.xabs') }:</span>
				<input name="chartOption.series[].center[0]" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="%" validate="digits range(0,100)" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.xabs') }"/>%
				</br>
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.yabs') }:</span>
				<input name="chartOption.series[].center[1]" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdCode" data-valuechange="true" data-digit-unit="%" validate="digits range(0,100)" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.yabs') }"/>%
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("rotate")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.rotate') }</td>
			<td>
				<input name="chartOption.xAxis[].axisLabel.rotate" class='inputsgl' style="width:100px;" type="text" data-valuechange="true" data-dbecharts-config="fdCode" placeholder="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.angle') }"/>
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("zoom")){
	{$
		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.xaxisSlider') }</td>
			<td>
				<label class="checkboxlabel">
					<input name="chartOption.dataZoomOption.show" type="checkbox" data-valuechange="false" data-dbecharts-config="fdCode" onclick="dbEchartsChart_CheckBoxChange(this,'chartOption_dataZoom','block','none');"/>
					&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.open') }
				</label>
				<div class="chartOption_dataZoom" style="display:none;">
					<div>
						<select name="chartOption.dataZoomOption.type" data-valuechange="false" onchange="dbEchartsChart_DataZoomTypeChange(this);" data-dbecharts-config="fdCode">
							<option value="range">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.interval') }</option>
							<option value="number">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataBarNum') }</option>
						</select>
					</div>
					<div class="chartOption_dataZoom_range">
						<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.initInterval') }:</span>
						<input name="chartOption.dataZoomOption.rangeData" class='inputsgl' style="width:150px;" type="text" data-valuechange="false" data-dbecharts-config="fdCode" placeholder="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.range') }" />		
					</div>
					<div class="chartOption_dataZoom_count" style="display:none;">
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.exceeding') }<input name="chartOption.dataZoomOption.count" class='inputsgl' style="width:50px;" type="text" data-valuechange="false" data-dbecharts-config="fdCode" validate="digits" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.slider2') }"/>
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.slider1') }
						<select name="chartOption.dataZoomOption.align" data-valuechange="false" data-dbecharts-config="fdCode">
							<option value="left">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.left1') }</option>
							<option value="center">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center1') }</option>
							<option value="right">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.right1') }</option>	
						</select>
						${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.slider3') }
					</div>
				</div>
			</td>
		</tr>
	$}
}

if(config.hasOwnProperty("visualMap")) {
{$

		<tr class="dynamicTr">
			<td class="td_normal_title" width="40%">${lfn:message('dbcenter-echarts:dbEcharts.echart.configure.visualMap')}</td>
			<td>
				<div class="chartOption_legendPosition">
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.leftDistance') }:</span>
					<select name="chartOption.visualMap.x" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="left">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.left') }</option>
						<option value="center">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="right">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.right') }</option>
					</select>
					<br/>
					<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.topDistance') }:</span>
					<select name="chartOption.visualMap.y" data-dbecharts-config="fdCode" data-valuechange="true">
						<option value="top">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.top') }</option>
						<option value="middle">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.center') }</option>
						<option value="bottom">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.bottom') }</option>
					</select>
				</div>
				<div>
					<span>${lfn:message('dbcenter-echarts:dbEcharts.echart.configure.visualMap.param')}</span>
					<textarea name="chartOption.visualMap.splitList"  data-dbecharts-config="fdCode" style="width:90%" data-valuechange="true" >
					</textarea>
				</div>
			</td>
		</tr>
$}
}
