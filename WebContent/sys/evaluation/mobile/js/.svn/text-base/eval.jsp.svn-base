<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ul>
	<li>
		<textarea name="fdEvaluationContent" placeholder="<bean:message bundle="sys-evaluation" key="mui.sysEvaluation.mobile.reply"/>" class="muiEvalMaskText"></textarea>
	</li>
	<li class="muiEvalGradeBar" >
		<div class="muiEvalGradeLeft" data-mark="muiEvalGrade" style="display:!{startShow}">
			<span class="title"><bean:message bundle="sys-evaluation" key="mui.sysEvaluation.mobile.eval.score"/>:</span>
			<span id="eval_star_opt" 
				data-dojo-type="!{starklass}" 
				data-dojo-props="value:4,editable:true">
			</span>
			<span id="contentLength" style="color: red;"></span>
		</div>
		<div class="muiEvalGradeLeft" data-mark="muiEvalAdd" style="display:!{additionTxtShow}">
			${lfn:message('sys-evaluation:sysEvaluationMain.addition') }
		</div>
	</li>
	<li class="muiEvalNotifyTargetBar">
		<span class="title">
			${lfn:message('sys-evaluation:sysEvaluation.evalNotifyTarget.mobile') }:
		</span>
		<input type="checkbox" data-dojo-type="mui/form/CheckBox" 
			  data-dojo-props="showStatus:'edit',name:'isNotify',text:'${lfn:message('sys-evaluation:sysEvaluation.evalDocCreatorName') }',value:'yes'">
		<span id="_notifyOtherName"></span>
	</li>
	<li class="muiEvalNotifyTypeBar">
		<span class="title">${lfn:message('sys-evaluation:sysEvaluation.evalNotify.type') }:</span>
		<div style="display: inline-block">
			<kmss:editNotifyType property="fdNotifyType"  mobile="true" />
		</div>
	</li>
	
	<li id="_eval_att_node">
	</li>
	
	<li class="muiEvalSubmitBar">
		<div class="muiEvalGradeRight" >
			<div class="muiEvalSubmit"><bean:message bundle="sys-evaluation" key="mui.sysEvaluation.mobile.eval.submit"/></div>
		</div>
	</li>
	<li class="muiEvalHideMask"><div class="mui-down-n mui"></div></li>
</ul>