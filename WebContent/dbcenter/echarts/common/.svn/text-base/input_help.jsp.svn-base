<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="help_title">${ lfn:message('dbcenter-echarts:input_help_hint_001') }</div>
<div class="help_content">
	<ul>
		
		<li id="inputNameHelp"><b>名称</b>：
			给查询参数进行中文命名，在数据列表查询时显示为参数的标签Label
		</li>
		<li id="inputKeyHelp"><b>参数</b>：
			通过执行“获取参数属性”操作自动从SQL语句的参数定义格式中获取
		</li>
		<li id="inputValueHelp"><b>初始值</b>：
			用于列表数据第一次载入时的条件默认值设置
		</li>
		<li id="inputFormatHelp"><b>类型</b>：
			参数的类型一般与数据表中的字段类型一一对应，SQL语句的查询条件是区分数据类型的
		</li>
		<li id="inputForumals"><b>名称</b>：
			当类型为"时间","日期","日期时间",时，支持使用公式定义器，主要针对当天、当月、一个月前等这种场景
		</li>
		
		<li id="inputArgumentHelp"><b>转换参数</b>：
			<ul>
				<li><b>${ lfn:message('dbcenter-echarts:input_help_hint_002')}</b> ${ lfn:message('dbcenter-echarts:input_help_hint_003')}
					${ lfn:message('dbcenter-echarts:input_help_hint_004')}
				</li>
				<li><b>${ lfn:message('dbcenter-echarts:input_help_hint_005')}</b> ${ lfn:message('dbcenter-echarts:input_help_hint_006')}
					${ lfn:message('dbcenter-echarts:input_help_hint_007')}
				</li>
			</ul>
		</li>
		
		<li id="inputSplitHelp"><b>${ lfn:message('dbcenter-echarts:input_help_hint_008')}</b>：
			${ lfn:message('dbcenter-echarts:input_help_hint_009')}
		</li>
		<li id="inputCriteriaHelp"><b>筛选项</b>：
			一般用于查询条件可以从多个枚举值中进行筛选，如文档状态。20=待审 30=发布 等这样的值进行设置
		</li>
		<li id="inputOuterHelp"><b>${ lfn:message('dbcenter-echarts:input_help_hint_010')}</b>：
			${ lfn:message('dbcenter-echarts:input_help_hint_011')}；
			若勾选了该选项，在数据列表显示时，是可以作为筛选条件进行用户进行条件输入或选择进行查询；
			若勾选了该选项同时也勾选了“是否隐藏”,则业务上可通过配置参数db_dynamic(值为json格式且URL编码)进行入参，如&db_dynamic=%7Btype%3A2%7D
		</li>

		<li id="inputHideHelp"><b>是否隐藏</b>：
			若勾选了该选项，则该条件参数不需要供用户进行输入值进筛选。将会作为隐含的参数在页面上传入；
			若不勾选了该选项，配置允许传值选项供用户筛选条件
		</li>

	</ul>
</div>