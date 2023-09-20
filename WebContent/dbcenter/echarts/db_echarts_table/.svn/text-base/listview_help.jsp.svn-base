<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="help_title">列表视图配置</div>
<div class="help_content">

	<ul>
		<li id="columnsNameHelp"><b>列名</b>:
			为列表视图中的显示列命名
		</li>
		<li id="columnsKeyHelp"><b>参数</b>:
			通过执行“获取参数属性”操作自动从SQL语句的返回结果的字段属性，以及在行统计中定义的列进行下拉选择
		</li>
		<li id="columnsWidthHelp"><b>列宽</b>:
			可设置单列的宽度，例如 150px
		</li>
		<li id="columnsCompHelp"><b>数据合并</b>:
			暂无
		</li>
		<li id="columnsTemplateHelp"><b>自定义模板</b>：
			<br/> 参考内容：   注意：自定义模板中所引用的参数(如例子的fd_id和fd_name)所在的列需独立于自定义模板列，否则自定义模板列取不到对应的参数<br/>
			    <textarea rows="1" cols="150" style="text-align: left;" readonly="readonly">{$<a href="/sys/organization/sys_org_person/sysOrgPerson.do?method=view&fdId={%row['fd_id']%}" target="_blank">{%row['fd_name']%}</a>$}</textarea>
			 <br/>
			 <font color="red">注意：自定义模板中所引用的参数(如例子的fd_id和fd_name)所在的列需独立于自定义模板列，否则自定义模板列取不到对应的参数 </font><br/>
		</li>
		<li id="columnsSortHelp"><b>排序</b>：
			 勾选此项，则该列可以在列表视图中让用户点击排序，此处的排序会被行统计中定义覆盖
		</li>
		<li id="columnsHiddenHelp"><b>隐藏</b>：
			勾选此项，则该列数据不会在列表中显示，数据会在页面中隐藏，此处的作用可能需将此处的数据在打开页面时作为参数传递
		</li>
		<li id="columnsTotalWidthHelp"><b>列表宽度</b>：
			默认为设备的自适应宽度；当统计列表的列数较多时，可以适当调整列表总宽度，以便有足够宽度显示每列数据
		</li>
		<li id="columnsUrlHelp"><b>URL</b>：
			定义在列表视图的行上打开一个详情页面，参数的引用方式用<font color="red">!{参数名}</font>，如果需要引用列表中的数据，可以使用定义列名引用列数据
		</li>
		<li id="columnsPageHelp"><b>允许翻页</b>：
			勾选此项，则列表视图可以翻页，要有翻页能力，<br>
			1、必须定义获取数据总记录的SQL语句<font color="red"> select count(*) as "rowCount" from 表名或数据集查询，且返回数组选择不能勾选</font><br>
			2、必须在获取数据的SQL语句中进行startIndex, endIndex参数的设置，具体说明请看SQL的语句帮助，且勾选返回数组选择
		</li>
		<li id="listviewSortHelp"><b>默认排序</b>：
			列表视图中数据按此处定义的列进行默认排序，在SQL语句必须定义含有<font color="red"> order by :_orderby</font>这样的语句
		</li>
		<li id="listviewSorttypeHelp"><b>默认排序方式</b>：
			与默认排序配合使用
		</li>
	</ul>
</div>