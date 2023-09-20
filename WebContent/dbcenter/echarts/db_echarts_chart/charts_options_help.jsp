<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="help_title">图表选项配置</div>
<div class="help_content">
	<ul>
		<li id="optionsPropertiesHelp"><b>更多选项更多选项</b>：
			例如需要在图表中点击某系例数据打开某个页面，且需要带图表数据做为参数，可能需要做以下样例配置<br>
<pre>
{
    on: {
        click: function(data) {
            window.open('/ekp/dbcenter/echarts/db_echarts_table/dbEchartsT able.do?method=view&fdId=15cd87c645380477d7c02d14d71a06a4#cri.q=v_date:' + data.name, '_blank');
        }
    }
}
</pre>
		</li>

		<li id="optionsPropertiesAdvHelp"><b>图表高级配置</b>：

			<ul>
				<li><b>SQL查询数据集样例</b>：
				<pre>
select to_char(t.fd_create_time,'yyyy-mm-dd') d,count(*) c from sys_org_element t
group by to_char(t.fd_create_time,'yyyy-mm-dd')
order by d
					</pre>
				</li>
				<li><b>SQL查询数据集对应图表配置</b>：
				<pre>
var option = {
    xAxis: {
        type: 'category',
        data: data.d
    },
    yAxis: {
        type: 'value'
    },
    series: [{
        data: data.c,
        type: 'line'
    }]
};

return option;
					</pre>
				</li>

				<li><b>定义数据集的对应图表配置样例</b>：
				<pre>
var option = {
    title: {
        text: '堆叠区域图'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    legend: {
        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'邮件营销',
            type:'line',
            stack: '总量',
            areaStyle: {},
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'line',
            stack: '总量',
            areaStyle: {},
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'line',
            stack: '总量',
            areaStyle: {},
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'直接访问',
            type:'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'搜索引擎',
            type:'line',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            areaStyle: {normal: {}},
            data:[820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};
return option;
					</pre>
				</li>
                <li><b>定义数据集的地图图表配置样例，请勾选上面加载中国地图js选项</b>：
                <pre>
var option = {
    tooltip: {
        trigger: 'item'
    },
    on: {
        click: function(param){
            if(!param || !param.data || !param.data.provinceMapId) return;
            window.open("dbEchartsChart.do?method=view&fdId=" + param.data.provinceMapId);
        }
    },
    visualMap: {
        dataRangex: 'left',
        y: 'bottom',
        itemWidth:10,
        itemHeight:10,
        textStyle:{
            fontSize: 8
        },
        splitList: [{
            start: 10000,label:"≥10000"
        }, {
            start: 1000,
            end: 9999,label:"1000-9999"
        }, {
            start: 100,
            end: 999,label:"100-999"
        }, {
            start: 10,
            end: 99,label:"10-99"
        }, {
            end: 9,label:"1-9"
        }],
        color: ['#660208', '#8C0D0D', '#CC2929', '#FF7B69', '#FFAA85']
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        x: 'right',
        y: 'center',
        feature: {
            mark: {
                show: true
            },
            dataView: {
                show: true,
                readOnly: false
            },
            restore: {
                show: true
            },
            saveAsImage: {
                show: true
            }
        }
    },
    roamController: {
        show: true,
        x: 'right',
        mapTypeControl: {
            'china': true
        }
    },
    series: [{
        name: '确诊人数',
        type: 'map',
        mapType: 'china',
        roam: false,
        zoom: 1.18,
        top:200,
        layoutCenter: ['50%', '50%'],
        layoutSize:650,
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    textStyle: {
                        color: "rgba(0,0,0,0.7)",
                        fontSize: "8",
                        fontWeight: "bold"
                    }
                }
            },
            emphasis: {
                label: {
                    show: true
                },
                areaColor: "#C7FFFD"
            }
        },
        top: "3%",
        data: [        
            {name:'北京',value:342,provinceMapId:""/*填写配置好的省级地图的id*/},
            {name: '福建',value:267},
            {name: '贵州',value:118},
            {name: '广东',value:1177,provinceMapId:""},
            {name: '广西',value:215},
            {name: '云南',value:149},
            {name: '四川',value:417},
            {name: '甘肃',value:86},
            {name: '新疆',value:55},
            {name: '宁夏',value:53},
            {name: '陕西',value:219},
            {name: '重庆',value:486},
            {name: '江西',value:804},
            {name: '浙江',value:1117},
            {name: '安徽',value:860},
            {name: '江苏',value:515},
            {name: '上海',value:303},
            {name: '山东',value:486},
            {name: '河南',value:1105},
            {name: '山西',value:122},
            {name: '河北',value:239},
            {name: '天津',value:104},
            {name: '辽宁',value:108},
            {name: '内蒙古',value:58},
            {name: '吉林',value:81},
            {name: '黑龙江',value:360},
            {name: '台湾',value:18},
            {name: '澳门',value:2},
            {name: '香港',value:42},
            {name: '海南',value:142},
            {name: '青海',value:18},
            {name: '湖北',value:31728},
            {name: '湖南',value:912},
            {name: '西藏',value:1}           
        ]
    }]
};
return option;
                    </pre>
                </li>

			</ul>

		</li>

	</ul>
</div>