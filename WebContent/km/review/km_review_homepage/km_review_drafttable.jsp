<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="process_datas" class="lui_review_echarts_table"></div>
<script>
    Com_IncludeFile('echarts4.2.1.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
</script>
<script type="text/javascript">
    Com_AddEventListener(window,"load",function() {
        var date = new Date().getFullYear();
        window.processQueue = echarts.init(document.getElementById("process_datas"));
        var option = {

            tooltip : {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            xAxis: {
                type: 'category',
                data: [date+'-01', date+'-02', date+'-03', date+'-04', date+'-05', date+'-06', date+'-07',date+'-08',date+'-09',date+'-10',date+'-11',date+'-12'],
                axisLabel: {
                    interval:0,
                    rotate:50
                }
            },
            yAxis: {
                type: 'value',
            },
            grid: {
                left: "3%",
                right: "4%",
                bottom: "12%",
                width: "88%",
                height: "250px",
                containLabel: true
            },
            series: [{
                name: '发起文档数',
                data: ${draftProcessTable},
                type: 'line'
            }]
        };
        window.processQueue.setOption(option);
    });
</script>
