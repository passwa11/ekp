<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%> 
<pre>
//数据格式说明:
//数据格式说明:
[
	{
		fromDate: “12/01/2017”,	// 开始日期
		toDate: “12/31/2017”,	// 结束日期，可以为空（表示只有一天）
		fromWeek: 1,				// 开始周几，可以为空
		toWeek: 4,		// 结束周几，可以为空（若fromWeek不为空，表示只有一天）
		name: “”					// 标记字符串，可以为空
		type: 0					// 标记日期类型
		color: “#000000”			// 标记日期颜色
	}
]

</pre>