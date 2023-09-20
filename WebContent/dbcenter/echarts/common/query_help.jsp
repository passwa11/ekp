<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="help_title">${ lfn:message('dbcenter-echarts:query_help_hint_001')}</div>
<div class="help_content">
	<ul>
		<li id="dsNameHelp"><b>数据源</b>：
			数据源默认包含本地连接，此数据源不需要配置。<br>
			其他数据源需要先在图表中心中的数据源进行配置，然后再在此处使用。
		</li>
		<li id="arrayHelp"><b>返回数组</b>：
			如果返回的结果集需要返回一条数据记录，可以不用勾选该选项，如要获取翻页时总数据条记，就不能勾选。<br>
			如果返回的结果集需要多条数据记录，可以勾选该选项，如需要显示多条数据。
		</li>
		<li id="sqlHelp"><ul>
			<li><b>${ lfn:message('dbcenter-echarts:query_help_hint_002')}</b>：
				${ lfn:message('dbcenter-echarts:query_help_hint_003')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_004')}
			</li>
			<li><b>${ lfn:message('dbcenter-echarts:query_help_hint_005')}</b>：
				${ lfn:message('dbcenter-echarts:query_help_hint_006')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_007')}
			</li>
			<li id="docSubjectHelp"><b>${ lfn:message('dbcenter-echarts:query_help_hint_008')}</b>：
				${ lfn:message('dbcenter-echarts:query_help_hint_009')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_010')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_011')}
			</li>
			<li><b>${ lfn:message('dbcenter-echarts:query_help_hint_012')}</b>：
				${ lfn:message('dbcenter-echarts:query_help_hint_013')}<b>${ lfn:message('dbcenter-echarts:query_help_hint_014')}</b>${ lfn:message('dbcenter-echarts:query_help_hint_015')}<b>${ lfn:message('dbcenter-echarts:query_help_hint_016')}</b>${ lfn:message('dbcenter-echarts:query_help_hint_017')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_018')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_019')}<b>${ lfn:message('dbcenter-echarts:query_help_hint_020')}</b>${ lfn:message('dbcenter-echarts:query_help_hint_021')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_022')}
			</li>
			<li><b>${ lfn:message('dbcenter-echarts:query_help_hint_023')}</b>：
				${ lfn:message('dbcenter-echarts:query_help_hint_024')}<br>
				${ lfn:message('dbcenter-echarts:query_help_hint_025')}
			</li>
			<li><b>${ lfn:message('dbcenter-echarts:query_help_hint_026')}</b>${ lfn:message('dbcenter-echarts:query_help_hint_027')}</li>

			<li><b>获取列表翻页时的总记录数的SQL样例</b>：
				<font color="red">select count(*) as "rowCount" from 表名或数据集查询</font>
			</li>
			<li><b>MySQL分页SQL样例</b>：
				<font color="red">select * from 表名或查询集合 limit :startIndex,:rowSize</font>
			</li>
			<li><b>Oracle分页SQL样例</b>：
				<font color="red">
					<pre>
select table.*
  from (select t.*, row_number() over(order by t.字段 desc) pageNumber
          from 表名 t
        ) table
where pageNumber  between :startIndex+1 and :endIndex
				</pre>
			</font>
		</li>
			<li><b>SQL Server 2005以上版本分页SQL样例</b>：
				<font color="red">
					<pre>
select _t.*
  from (select t.*, row_number() over(order by t.字段 desc) pageNumber
          from 表名 t
        ) _t
where pageNumber  between :startIndex+1 and :endIndex
				</pre>
			</font>
		</li>
       <li id="likeHelp"><b>模糊查询</b>： <br>	
          <span><b>方式 一 （仅支持V15版本）</b>：
			如果需要模糊查询传参，则需要在条件参数选择‘模糊查询’，默认会在值的两边加上 % 符号，如果只需要单边加%符号，则需要在转换参数自定义（比如参数为v_id,转换参数可以为 v_id%,这样会以入参值匹配关系的开头部分）<br>
						<font color="red">
					<pre>
//第一：sql填写
select * from sys_news_main where 1=1 :vname{ and doc_subject like :vname}
//第二 ：“条件参数”配置对应的入参，选择模糊查询
					</pre>
			</font>		
	      </span>
	      <span><b>方式二</b>：
			Mysql和 Sql Server数据库可以通过concat函数将“ %和入参”连起来，Oracle数据库可通过 || 将 “ %和入参”连起来以实现模糊查询；然后在“条件参数”配置对应的入参即可<br/>
			sql 例子:
			<font color="red">
					<pre>
//Mysql和 Sql Server数据库:
select * from sys_news_main where 1=1 :vname{and doc_subject like concat('%',:vname,'%')}
//Oracle数据库:
select * from sys_news_main where 1=1 :vname{ and doc_subject like '%'||:vname||'%'}
					</pre>
			</font>
		</span>
		</li>
		</ul>
		</li>
	</ul>
</div>