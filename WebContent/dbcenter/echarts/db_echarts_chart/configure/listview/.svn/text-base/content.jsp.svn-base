<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var chartsListView = layout.parent;
var charts = chartsListView.dataSource.source.charts;


/**************画导航 start ******************/ 
{$ <div class="lui-chartConfig-col-3-stickyL"><ul class="lui-chartConfig-nav"> $}
	for(var key in charts){
		{$ 
			<li data-charttype="{%key%}">
				<a href="javascript:void(0);" title="{% charts[key].text %}"><i class="icon {%charts[key].icon%}"></i>{% charts[key].text %}</a>
			</li>
		$}
	}
{$ </ul></div> $}
/**************画导航 end ******************/

/**************画展示 start ******************/ 
{$ <div class="lui-chartConfig-col-3-flexibleM">
	<div class="lui-chartConfig-col-3-flexibleM-inner lui-chartConfig-nav-cnt">
		<div class="chart-content"> $}
			for(var key in charts){
				{$ <dl class="lui-chartConfig-list">
					<dt data-charttype-title="{%key%}">{% charts[key].text %}</dt>			
				$}
				if(charts[key].series && charts[key].series.length > 0){
					var series = charts[key].series;
					for(var i in series){
						{$
							<dd>
								<a href="javascript:void(0);" title="{%series[i].text%}" data-charttype="{% key + '-' + series[i].type %}">
									<div class="imgbox">
									  <div class="{%series[i].image%}" >
									    <img src="${KMSS_Parameter_ContextPath}{%series[i].imageUrl%}" />
									  </div>
									</div>
                 		 			<p class="imgtxt">{%series[i].text%}</p>
								</a>
							</dd>
						$}
					}
				}
				{$ </dl> $}
			}
{$ </div></div></div> $}
/**************画展示 end ******************/

/**************描述 start ******************/ 
{$ 
   <div class="lui-chartConfig-col-3-stickyR">
     <div class="how-to-choose-chart">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.helpTip') }</div>
     <div class="lui-chartConfig-info chartDesc"></div>
   </div> 
$}
/**************描述 end ******************/
