<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
	<div class="eval_reply_infos" id="{%row['fdId']%}">
		<dl class="eval_record_dl">
			<dd class="eval_record_msg">
				<div class="img">
					<img src="${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId={%row['fdEvaluator.fdId']%}">
				</div>
				<div class="txt" id="eval_txt_{%row['fdId']%}">
					<div class="eval_info">
						<ui:person personId="{%row['fdEvaluator.fdId']%}" personName="{%env.fn.formatText(row['fdEvaluator.fdName'])%}"></ui:person>
						<span>{%row['fdEvaluationTime']%}</span>
						<c:if test="${param.eval_validateAuth}">
							<span class="eval_delopt" eval_id_flag="{%row['fdId']%}">
								<bean:message key="button.delete"/>
							</span>
						</c:if>
					</div>
					<c:if test="${param.isViewMain != 'true'}">
						<div class="eval_quote_cont">
							<blockquote>{%row['docSubject']%}</blockquote>
						</div>
					</c:if>
					<div class="eval_record_content">{%row['fdEvaluationContent']%}</div>
					<div class="eval_praise_reply" eval-view-modelname="${param.fdModelName}">
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
								title="<bean:message key="sysEvaluation.reply.commment" bundle="sys-evaluation"/>">
							<span id="reCount_{%row['fdId']%}">{%row['fdReplyCount']%}</span>
						</a>
						$}
							if(!template.parent.ids){
								template.parent.ids = [];
							}
							template.parent.ids.push(row['fdId']);
						{$
					</div>
				</div>
			</dd>
			<div id="reply_cont_box" class="reply_cont_box"></div>
			
		</dl>
	</div>
$}