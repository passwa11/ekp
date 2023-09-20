<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!-- 饼图 -->
<textarea name="data_pie" hidden="true">
{"legend":{"data":["直接访问","邮件营销","联盟广告","视频广告"]},
 "series":[
	 {"name":"浮层标题",
	  "data":
		 [{"name":"直接访问","value":"335.0"},
		  {"name":"邮件营销","value":"310.0"},
		  {"name":"联盟广告","value":"234.0"},
		  {"name":"视频广告","value":"135.0"}]
	 }]
}
</textarea>

<!-- 折线图、面积图、柱形图 -->
<textarea name="data_line_area_bar" hidden="true">
{"legend":{"data":["直接访问","邮件营销","联盟广告","视频广告","搜索引擎"]},
 "xAxis":[{"data":["周一","周二","周三","周四","周五","周六","周日"]}],
 "yAxis":[{"type":"value"}],
 "series":[
          {"name":"直接访问",
            "data":["120.0","132.0","101.0","134.0","90.0","230.0","210.0"]},
          {"name":"邮件营销",
           "data":["220.0","182.0","191.0","234.0","290.0","330.0","310.0"]},
		  {"name":"联盟广告",
		   "data":["150.0","232.0","201.0","154.0","190.0","330.0","410.0"]},
		  {"name":"视频广告",
		   "data":["320.0","332.0","301.0","334.0","390.0","330.0","320.0"]},
		  {"name":"搜索引擎",
		   "data":["820.0","932.0","901.0","934.0","1290.0","1330.0","1320.0"]}]
}
</textarea>

<!-- 仪表盘 -->
<textarea name="data_gauge" hidden="true">
{"series":[
         {"name":"浮层标题",
		  "data": [{"name":"完成率","value":"50.0"}]}]
}
</textarea>

<textarea name="data_map" hidden="true">
{
    series: [{
        name: "确诊人数",
        data: [       
        	{"name":"湖北","value":29631}, 
            {"name":"广东","value":1151},  
            {"name":"浙江","value":1092},    
            {"name":"河南","value":1073},
            {"name":"湖南","value":879}, 
            {"name":"安徽","value":830}, 
            {"name":"江西","value":771},
            {"name":"江苏","value":492},     
            {"name":"重庆","value":468},
            {"name":"山东","value":459},
            {"name":"四川","value":405},
            {"name":"北京","value":337},
            {"name":"黑龙江","value":331},
            {"name":"上海","value":299},
            {"name":"福建","value":261},
            {"name":"河北","value":218},
            {"name":"陕西","value":213},
            {"name":"广西","value":210},
            {"name":"云南","value":141},
            {"name":"广西","value":210},
            {"name":"海南","value":136},
            {"name":"山西","value":119},
            {"name":"贵州","value":109},
            {"name":"辽宁","value":107},
            {"name":"天津","value":94},
            {"name":"甘肃","value":83},
            {"name":"吉林","value":80},
            {"name":"内蒙古","value":58},
            {"name":"宁夏","value":49},
            {"name":"新疆","value":49},
            {"name":"香港","value":36},
            {"name":"台湾","value":18},
            {"name":"青海","value":18},
            {"name":"澳门","value":10},
            {"name":"西藏","value":1}
        ]
    }] ,
    "visualMap": {
        splitList: [{
            start: 10000
        }, {
            start: 1000,
            end: 9999
        }, {
            start: 100,
            end: 999
        }, {
            start: 10,
            end: 99
        }, {
            end: 9
        }],
        color: ['#660207', '#8c0d0d', '#cc2929','#ff7b69','#ffaa85']
    }
}

</textarea>