<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{
    title: {
        text: '0',
        left: 'center',
        top: '10%',
        fontSize: 28,
        color: '#333333'
    },
    toolbox: {
        "show":false
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
    series: [{
        name: '',
        type: 'pie',
        radius: ['18%', '25%'],
        center: ['50%', '15%'],
        data: [{
            value: 0,
            name: '${lfn:message('kms-knowledge:kmsKnowledge.index.count.wiki')}',
			itemStyle: {
				normal: {color: '#eee'},
				emphasis: {color: '#eee'}
			}
        }],
        itemStyle: {
            borderWidth: 5,
            borderColor: '#fff'
        },
        hoverAnimation: false,
        label: {
            normal: {
                show: false
            }
        }
    }]
}
