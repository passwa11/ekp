<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{
			grid : {
				x : 45
			},
            tooltip : {
                show : true
            },
            legend : {
                data : [ '数量' ]
            },
            xAxis : [ {
                type : 'category',
                data : ${name}
            } ],
            yAxis : [ {
                type : 'value'
            } ],
            series : [ {
                "name" : "数量",
                "type" : "bar",
                "data" : ${value}
            } ]
}