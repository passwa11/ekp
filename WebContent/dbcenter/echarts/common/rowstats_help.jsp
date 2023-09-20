<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="help_title">行统计定义</div>
<div class="help_content">

	<ul>
		<li id="rowstatsLabelHelp"><b>列标题</b>:
			对结果集中行数据中某些列统计的列命显示名
		</li>

		<li id="rowstatsNameHelp"><b>列名</b>:
			对结果集中行数据中某些列统计的列命变量名
		</li>
		<li id="rowstatsFormalHelp"><b>计算公式定义</b>:
			对SQL查询出的数据某些列定义计算公式
		</li>
		<li id="rowstatsFormatHelp"><b>转换参数</b>：
			 对计算出的数据进行格式化，小数位格式化，整数千分位格式化等
		</li>
		<li id="rowstatsSortHelp"><b>排序方式</b>:
			对计算出的数据进行列上的排序，此处的排序会覆盖列表视图配置中的默认排序和列表视图中列排序
		</li>
	</ul>
</div>