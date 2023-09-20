<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
if(data && data.length > 0) {
{$
<ul class="lui_eval_addition_ul">
$}
	for(var i = 0 ; i < data.length; i ++) {
		{$
			<li>
				
				<div class="lui_eval_addition_info">
					<span>${lfn:message('sys-evaluation:sysEvaluationMain.addition') }</span>&nbsp<span>
					{% data[i].fdEvaluationTime %}</span>
					<c:if test="${param.eval_validateAuth == 'true'}">
						<span class="eval_delopt" 
							eval-view-modelname="com.landray.kmss.sys.evaluation.model.SysEvaluationMain"
							eval_id_flag="{%data[i]['fdId']%}">
							<bean:message key="button.delete"/>
						</span>
					</c:if>
				</div>
				<div class="lui_eval_addition_content eval_record_content linefeed_desc">
					{% data[i].fdEvaluationContent %}
				</div>
				<div data-eval-main-att="{%data[i]['fdId']%}"></div>
				
			</li>
		$}
	}
{$
</ul>
$}
}