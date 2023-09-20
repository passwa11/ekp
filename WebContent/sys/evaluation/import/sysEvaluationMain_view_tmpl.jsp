<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
	<div id="{%row['fdId']%}" class="eval_reply_infos">
		<dl class="eval_record_dl">
			<dd class="eval_record_msg">
				<div class="img">
					<img src="{%env.fn.formatUrl(row['imgUrl'])%}">
				</div>
				<div class="txt" id="eval_txt_{%row['fdId']%}">
					<div class="eval_info clearfloat">
						<ui:person personId="{%row['fdEvaluator.fdId']%}" personName="{%env.fn.formatText(row['fdEvaluator.fdName'])%}"></ui:person>
						<span>{%row['fdEvaluationTime']%}</span>
						<c:if test="${param.isViewMain == 'true'}">
							<span class="eval_star_display"><ul class="eval_summary_star">$}
								for(var m=0;m<5;m++){
									var flag = 4- parseInt(row['fdEvaluationScore']);
									var className = 'lui_icon_s_starbad'
									if(m <= flag){
										className = 'lui_icon_s_stargood';
									}
									{$<li class="{%className%}"></li>$}
								}
							{$</ul></span>
						</c:if>
						<c:if test="${param.eval_validateAuth}">
							<span class="eval_delopt" 
								  eval-view-modelname="${param.fdModelName}"
								  eval_evaluator_id = "{%row['fdEvaluator.fdId']%}"
								  eval_parent_id="{%row['fdParentId']%}"
								  eval_id_flag="{%row['fdId']%}">
								<bean:message key="button.delete"/>
							</span>
						</c:if>
					</div>
					<c:if test="${param.isViewMain != 'true'}">
						<div class="eval_quote_cont">
							<blockquote>{%row['docSubject']%}</blockquote>
						</div>
					</c:if>
					<div class="eval_record_content linefeed_desc">{%row['fdEvaluationContent']%}</div>
					
					<div data-eval-main-att="{%row['fdId']%}"></div>
					
					<div data-lui-mark="superaddition_{%row['fdId']%}"></div>
					<div class="eval_praise_reply" eval-view-modelname="${HtmlParam.fdModelName}">
						$}
							var _docPraiseCount = row['docPraiseCount'];
							var _fdId = row['fdId'];
						{$
						<!-- 点赞 -->
						<c:import
							url="/sys/praise/tmpl/sysPraiseMain_tmpl_view.jsp"
							charEncoding="UTF-8">
							<c:param name="docPraiseCount" value="{%_docPraiseCount%}" />
							<c:param name="fdModelId" value="{%_fdId%}" />
							<c:param name="fdModelName" value="${param.fdModelName}" />
						</c:import>
						$}
							var replyBtn = "";
							if(row['fdReplyCount'] == 0){
								replyBtn = "reply_off";
							}
						{$
						
						<a class="eval_reply {%replyBtn%}" eval_reply_id="{%row['fdId']%}" 
								title="<bean:message key="sysEvaluation.reply.ct" bundle="sys-evaluation"/>">
							<span id="reCount_{%row['fdId']%}">{%row['fdReplyCount']%}</span>
						</a>
						$}
							if(!template.parent.ids){
								template.parent.ids = [];
							}
							template.parent.ids.push(row['fdId']);
						{$
					</div>
					<div id="reply_cont_box"></div>
				</div>
			</dd>
			
		</dl>
	</div>
$}