<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="help_title">基本信息配置</div>
<div class="help_content">
	<ul>
		<li id="editModeHelp"><b>模式切换</b>：
		分配置模式和代码模式两种模式，配置模式将分别对获取列表数据进行简单的选择配置，而代码模式仅仅提供编写JSON的方式进行配置，两种配置模式可相互切换。代码模式配置起来比较难，比较且容易出错，但可以对代码模式中的配置内容进行备份，方便修改。
		</li>
		<li id="fdCodeHelp"><b>执行代码</b>：
		在代码模式下可以进行编辑，JSON内容包含如下：querys为SQL语句数组，inputs为条件参数数组，outputs为返回结果格式转换数组，rowstats为行统计定义数组，colstats为列统计定义数组， columns为列表视图定义数组，fields为在获取参数属性时执行SQL时返回的所有列定义数组，listview为列表视图配置。
		结构如下：
		<div style="height:240px;overflow-y:scroll;">		
		<pre>
{
    "querys": [
        {
            "sql": "",
            "type": "query",
            "array": "false"
        }
    ],
    "outputs": [
        {
            "key": ""
        }
    ],
    "rowstats": [
        {
            "label": "",
            "key": "",
            "expressionText": "",
            "expressionValue": ""
        }
    ],
    "colstats": [
        {
            "key": "",
            "type": "",
            "format": "",
            "argument": ""
        }
    ],
    "columns": [
        {
            "name": "",
            "key": "",
            "align": "",
            "sort": "",
            "hidden": ""
        }
    ],
    "listview": {
        "page": "true",
        "sort": "",
        "sorttype": ""
    },
    "fields": [
        {
            "name": "",
            "key": "",
            "type": ""
        }
    ]
}
</pre>
</div>
		</li>

	</ul>
</div>