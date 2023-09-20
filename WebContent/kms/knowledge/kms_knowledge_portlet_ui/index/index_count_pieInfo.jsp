<%@ page import="net.sf.json.JSONObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    JSONObject dataObj = (JSONObject) request.getAttribute("data");
    String ratestr = String.format("%.0f", (dataObj.getDouble("docCount") / dataObj.getDouble("totalCount")) * 1000);
    Integer rate = Integer.parseInt(ratestr) ;

    if (rate >= 0 && rate <= 100) {
        request.setAttribute("startAngle", 0);
    } else if (rate >= 101 && rate <= 200) {
        request.setAttribute("startAngle", 0);
    }else if (rate >= 201 && rate <= 300) {
        request.setAttribute("startAngle", 30);
    } else if (rate >= 301 && rate <= 400) {
        request.setAttribute("startAngle", 40);
    } else if (rate >= 401 && rate <= 500) {
        request.setAttribute("startAngle", 50);
    } else if (rate >= 501 && rate <= 600) {
        request.setAttribute("startAngle", 60);
    } else if (rate >= 601 && rate <= 700) {
        request.setAttribute("startAngle", 70);
    } else if (rate >= 701 && rate <= 800) {
        request.setAttribute("startAngle", 110);
    } else if (rate >= 801 && rate <= 900) {
        request.setAttribute("startAngle", 120);
    } else if (rate >= 901 && rate <= 1000) {
        request.setAttribute("startAngle", 150);
    } else {
        request.setAttribute("startAngle", 0);
    }
%>
{
    title: {
        text: '${data.totalCount}',
        left: 'center',
        top: '10%',
        fontSize: 28,
        color: '#333333'
    },
    "tooltip": {
        "trigger": "item"
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
                    console.log("e=>",e)

                    var chartId = 'kn_count';
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
    graphic: { //图形中间文字
        type: 'text',
        left: 'center',
        top: '16%',
        style: {
            text: '${lfn:message('kms-knowledge:kmsKnowledge.portlet.allCount.total')}',
            textAlign: 'center',
            fill: '#333333',
            fontSize: 14
        }
    },
    xAxisName:"类型",
    series: [{
        name: '百分比',
        type: 'pie',
        radius: ['18%', '25%'],
        center: ['50%', '15%'],
        // minAngle: 15,//最小角度
        startAngle:${startAngle}, //起始角
        data: [
        <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '1')}">
            {
                value: (${data.docCount}/${data.totalCount}).toFixed(3)*100,
                count: ${data.docCount},
                name: '${lfn:message('kms-knowledge:kmsKnowledge.index.count.doc')}',
                itemStyle: {
                    color: '#F3AD34'
                }
            },
        </c:if>
        <kmss:ifModuleExist path="/kms/wiki/">
            <c:if test="${(JsParam.type eq '1,2,3') or (JsParam.type eq '2')}">
            {
                value: (${data.wikiCount}/${data.totalCount}).toFixed(3)*100,
                count: ${data.wikiCount},
                name: '${lfn:message('kms-knowledge:kmsKnowledge.index.count.wiki')}',
                itemStyle: {
                    color: '#3079E0'
                }
            }
            </c:if>
        </kmss:ifModuleExist>
        ],
        itemStyle: {
            borderWidth: 5,
            borderColor: '#fff'
        },
        tooltip: {
            trigger: "item",
            "formatter" : function(data) {
                var dname = data.name;
                var dcount = "，" + data.data.count;
                return dname + dcount;
            },
            backgroundColor: "#f2f2f2", //设置背景图片 rgba格式
            textStyle: {
                color: "#4285F4" //设置文字颜色
            }
        },
        hoverAnimation: false,
        "label" : {
            "normal" : {
                "formatter" : function(data) {
                    var dname = data.name;
                    var dpercentage = data.percent.toFixed(1) + "%，" + data.data.count;
                    return '{percentage|'+dpercentage+'} \r\n {name|'+ dname +'}';
                },
                rich: {
                    percentage: {
                        fontSize: 18,
                        fontWeight: 'bolder',
                        color: 'rgba(0,0,0,0.85)' // 主标题文字颜色
                    },
                        name: {
                        fontSize: 12,
                        color: '#666666' // 主标题文字颜色
                    }
                },
                textStyle: {
                    fontSize: 18,
                    fontWeight: 'bolder',
                    color: '#333' // 主标题文字颜色
                }
            }
        },
        labelLine: {
            show: true,
            length: 20,
            lineStyle: {
            color:'#D4D6DB',
            width: 1,
            type: 'solid'
        }
        }
    }]
}
