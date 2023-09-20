<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 全屏预览图表区域（默认隐藏，点击“全屏”图标触发显示） -->
<div id="echart_preview_zoom_in_area" >
   <div style="width:100%;height:100%;position:absolute;" >
	 <div id="preview_zoom_in_echart" ></div>
   </div>
   <div id="preview_zoom_out_icon" onclick="$('#echart_preview_zoom_in_area').hide();" ></div>   
</div>	

<!-- 图表预览区域 -->
<center id="echart_config_area_view" >
    <!-- 左侧 图表属性和列表数据配置区域 -->
	<div style="display:inline-block;*display:inline;*zoom:1;width:65%;">
		<c:import url="/dbcenter/echarts/db_echarts_chart/configure/data_view.jsp" charEncoding="UTF-8">
		</c:import>
	    <div style="margin-top:26px;height:20px;">
	       <div style="float:left;">
		       <div class="echartOperateButton previewButton"> 
		          <div class="echartOperateButtonText">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.preview') }</div>
		       </div>	       
		       <div class="echartOperateButton fullScreenPreviewButton">
		          <div class="echartOperateButtonText">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.screenPreview') }</div>
		       </div>	       
	       </div>
	    </div>
		<div style="margin-top: 4px;">
			<div id="main_chart" style="min-width:330px;min-height:300px;width:100%;height:100%;max-height:500px;z-index:1;"></div>
		</div>
	</div>
	
	<!-- 右侧 echarts预览图表和图表样式配置区域 -->
	<div style="display:inline-block;*display:inline;*zoom:1;vertical-align:top;margin-left:10px;width:330px;">

		<div>
			<p class="txttitle2"><span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartStyleConfiguration') }</span></p>
			<table class="tb_normal chartViewTable" width="100%">
				<tbody>
					<tr>
						<td class="td_normal_title" width=40%>
							<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.fdTheme"/>
						</td><td width="85%" colspan="3">
							<xform:select property="fdTheme" showPleaseSelect="false" onValueChange="dbEcharts_changeTheme(this);">
								<xform:simpleDataSource value="default"><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></xform:simpleDataSource>
								<c:forEach items="${themes }" var="theme">
									<xform:simpleDataSource value="${theme }">${theme }</xform:simpleDataSource>
								</c:forEach>
							</xform:select>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="40%">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartSize') }</td>
						<td>
							<label class="checkboxlabel">
							<input name="isAdapterSize" type="checkbox" data-dbecharts-config="fdCode" onclick="dbEchartsChart_CheckBoxChange(this,'chartOption_size','none','block',dbEchartsChart_AdapterSizeChange);"/>
							&nbsp;${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.hwAdapte') }
							</label>
							<div class="chartOption_size" style="display:none;">
								<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.width') }</span>
								<input name="width" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdConfig" />
								</br>
								<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.height') }</span>
								<input name="height" class='inputsgl' style="width:50px;" type="text" data-dbecharts-config="fdConfig" />
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</center>
