<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{

    legend: {
        orient: "horizontal",
        left: "center",
        bottom: 20,
        data: [
        "${lfn:message('km-archives:kmArchives.chart.type.main')}", 
        "${lfn:message('km-archives:kmArchives.chart.type.appraise')}", 
        "${lfn:message('km-archives:kmArchives.chart.type.borrow')}",
        "${lfn:message('km-archives:kmArchives.chart.type.destroy')}"
        ]
    },
　　label : {
　　　　normal : {
　　　　　　formatter: "{b}\n{c}%",
　　　　　　textStyle : {
　　　　　　　　fontWeight : "normal",
　　　　　　　　fontSize : 15
　　　　　　}
　　　　}
　　},    
    series: [
        {
            type: "pie",
            radius: "55%",
            center: ["50%", "45%"],
            data: ${datas}
        }
    ]
}