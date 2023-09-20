<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{
    tooltip: {
        trigger: 'axis'
    },
    grid: {
        right: '10px',
        left: '40px'
    },
    toolbox: {
        right:'25px',
        itemSize:20,
        feature: {
            restore: {
                show: true,
                icon: 'image://${ LUI_ContextPath }/kms/knowledge/kms_knowledge_index_ui/images/chart_restore.png'
            },
            saveAsImage: {
                show: true,
                icon: 'image://${ LUI_ContextPath }/kms/knowledge/kms_knowledge_index_ui/images/chart_save.png'
            },
            myDataTableView:{
                show:true,
                title: "${lfn:message('sys-ui:echart.dataView') }",
                icon: 'image://' + Com_GetCurDnsHost() + Com_Parameter.ContextPath+"kms/knowledge/kms_knowledge_index_ui/images/chart_data.png",
                onclick:function(e){
                    // console.log("e=>",e)

                    var chartId = 'kn_cat_chart';
                    $("#"+chartId+" .div_chart").hide();

                    var listDiv = $("#"+chartId+" .div_listSection");
                    listDiv.html('');

                    var xdata=[];
                    var field = [];
                    var datas=[];
                    var series = e.option.series;

                    // console.log("series=>",series);
                    if(series.length==1&&(series[0].type == 'pie'||series[0].type == 'gauge')){
                        var pData = series[0];
                        var tempdata=[];
                        field.push(pData.name);
                        for(var key in pData.data){
                            var obj = pData.data[key];
                            xdata.push(obj.name);
                            tempdata.push(obj.value);
                        }
                        datas.push(tempdata);
                    }else{
                        xdata = e.option.xAxis[0].data;

                        for(var i=0;i<series.length;i++){
                            var sData = series[i];
                            field.push(sData.name);
                            datas.push(sData.data);
                        }
                    }

                    var reButton = '<div style="text-align:left;width:96%;" class="div_close com_btn_link">${lfn:message("sys-ui:echart.comeBack") }</div>';
                    listDiv.append($(reButton).click(function(){
                        $("#"+chartId+" .div_listSection").hide();
                        $("#"+chartId+" .div_chart").show();
                    }));

                    if(datas!=null && datas.length>0){
                        var content = $('<table class="tab_listData"></table>');
                        var titleTr = $('<tr class="tab_title"></tr>');
                        var xaxis = "/";
                        // 添加x轴标题
                        if(e.option.xAxis){
                            if(e.option.xAxis.constructor === Array && e.option.xAxis.length > 0){
                                xaxis = e.option.xAxis[0].name;
                            }else{
                                if(e.option.xAxis.name){
                                    xaxis = e.option.xAxis.name;
                                }
                            }
                        }else if(e.option.xAxisName){
                            xaxis = e.option.xAxisName;
                        }

                        if(xaxis == null) {
                            $('<th></th>').appendTo(titleTr);
                        } else {
                            $('<th>'+xaxis+'</th>').appendTo(titleTr);
                        }
                        for(var j=0;j<field.length;j++){
                            $('<th>'+field[j]+'</th>').appendTo(titleTr);
                        }

                        titleTr.appendTo(content);

                        for ( var k = 0; k < xdata.length; k++) {
                            var dataTr = $('<tr class="tab_data"></tr>');
                            $('<td>'+xdata[k]+'</td>').appendTo(dataTr);
                            for ( var m = 0; m < datas.length; m++) {
                                var mk = datas[m][k];
                                if(typeof mk === "object" && mk != null && mk.value){
                                    mk = mk.value;
                                }
                                $('<td>'+ mk +'</td>').appendTo(dataTr);
                            }
                            dataTr.appendTo(content);
                        }

                        listDiv.append(content);
                    }

                    listDiv.show();
                }
            }
        }
    },
	xAxis: [
				{
                    name: '分类',
					type: 'category',
					splitLine: {
						show:false
					},
					axisTick: {
						show: false
					},
                    axisLine: {
                        lineStyle: {
                            color: '#999999',
                            width: 1, //这里是为了突出显示加上的
                        }
                    },
                    "axisLabel":{
                        interval: 0,
                        formatter: function(value){
                            var valueText = "";
                            if(value.length > 5){
                                valueText = value.substring(0,5) + "...";
                            }else{
                                valueText = value;
                            }
                            return valueText;
                        }
                    },
					data:${empty data.names ? 'null' : data.names},
				}
	],
    yAxis : [
        {
            type: 'value',
            name: '${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.num')}',
            position: 'left',
			splitLine :{    //网格线
				lineStyle:{
					color: '#eeeeee',
					type:'dashed'    //设置网格线类型 dotted：虚线   solid:实线
				},
				show:true //隐藏或显示
			},
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#999999'
                }
            },
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: '#999999'
                }
            }
        }
    ],
    series: [
    {
        name:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.num')}",
        type:'bar',
        yAxisIndex: 0,
        itemStyle: {
            normal: {
                color: function(params) {
                    //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                    var colorList = ['#64DAFF','#60D1FE', '#5CC6FC', '#59BEFC', '#55B5FA','#52ADF9', '#4EA3F8', '#4A9BF6', '#4792F6', '#4285F4'];
                    if (params.dataIndex >= colorList.length) {  //给大于颜色数量的柱体添加循环颜色的判断
                        index = params.dataIndex - colorList.length;
                    }
                    return colorList[params.dataIndex]
                }
            }
        },
        data:${empty data.counts ? 'null' : data.counts},
    }
]
}
