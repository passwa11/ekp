<%@page import="com.landray.kmss.sys.lbpmext.auditpoint.util.AuditPointUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<%
	String _auditNoteFdId = request.getParameter("auditNoteFdId");
	request.setAttribute("_lbpmExtAuditPointCfg",
		AuditPointUtil.loadLbpmExtAuditPointCfg(_auditNoteFdId,pageContext.getAttribute("sysWfBusinessForm")));
%>
<c:if test="${not empty _lbpmExtAuditPointCfg }">
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<div id="_lbpmExtAuditPointCfgTr" style="display:none">
		</div>
	</c:when>
	<c:otherwise>
		<tr id="_lbpmExtAuditPointCfgTr" style="display:none">
		</tr>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
Com_IncludeFile("json2.js");

var _extAuditPointCfg=${_lbpmExtAuditPointCfg};
var _pointsLangs={};
var isRight = "${param.approveModel eq 'right'}";

lbpm.onLoadEvents.delay.push(function () {
	if(lbpm && lbpm.events && lbpm.events.addListener){
		lbpm.events.addListener(lbpm.constant.EVENT_HANDLERTYPECHANGE, function(param){
			if((param!='' && param!=lbpm.constant.PROCESSORROLETYPE) 
					|| (lbpm.nowProcessorInfoObj && lbpm.nowProcessorInfoObj.type == 'communicateWorkitem')){
				$("#_lbpmExtAuditPointCfgTr").hide();
			}else{
				if(lbpm.globals.extAuditPointHTML()!=""){
					$("#_lbpmExtAuditPointCfgTr").show();
				}else{
					$("#_lbpmExtAuditPointCfgTr").hide();
				}
			}
		});
		
		lbpm.events.addListener(lbpm.constant.EVENT_BEFORELBPMSUBMIT, function(param){
			if(param){
				var $AuditPointCfgTr = $("#_lbpmExtAuditPointCfgTr");
				if($AuditPointCfgTr.length>0 && $AuditPointCfgTr.html()){
					if(param.isPassType){
						//重置审批要点必填校验
						param.validation.resetElementsValidate($AuditPointCfgTr[0]);
					}else{
						//移除审批要点必填校验
						param.validation.removeElements($AuditPointCfgTr[0],'required');
						param.validation.removeElements($AuditPointCfgTr[0],'lbpmext_auditpoint_required');
					}
				}
			}
		});
	}
});

lbpm.globals.extAuditPointSwitch=function(){
	var html=lbpm.globals.extAuditPointHTML();
	$("#_lbpmExtAuditPointCfgTr").html(html);
	if(html!=""){
		$("#_lbpmExtAuditPointCfgTr").html(html);
		lbpm.globals.extAuditPointAddValidator();
		if(lbpm.nowProcessorInfoObj && lbpm.nowProcessorInfoObj.type == 'communicateWorkitem'){
			$("#_lbpmExtAuditPointCfgTr").hide();
		}else{
			$("#_lbpmExtAuditPointCfgTr").show();
		}
		if(lbpm && lbpm.approveType == "right"){
			var $auditpoint = $("#_lbpmExtAuditPointCfgTr").find("input[type='checkbox'][lbpmext-auditpoint-data-important='true']");
			if($auditpoint.length>0){
				$("#_lbpmExtAuditPointCfgTr .lui-lbpm-titleNode").prepend("<span class='txtstrong dingtxtstrong'>*</span>");
			}
		}
	}
}

lbpm.onLoadEvents.delay.push(function () {
	lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.extAuditPointSwitch);
	lbpm.globals.extAuditPointSwitch();
});

Com_Parameter.event["submit"].push(function(){

	var target = Com_GetEventObject();
	if(Com_Parameter.preOldSubmit!=null){
		target = Com_Parameter.preOldSubmit
	}

	var isDraft = ((target && target.currentTarget && target.currentTarget.title == lbpm.constant.BTNSAVEDRAFT) 
			|| (target && target.srcElement && target.srcElement.innerText == lbpm.constant.BTNSAVEDRAFT) 
			|| (target && target.srcElement && target.srcElement.title == lbpm.constant.BTNSAVEDRAFT));
	if(isDraft){
		return true;
	}

	var validateObject=$GetFormValidation(document.forms[0]);
	if(validateObject){
		var inputs=$("input[validate='lbpmext_auditpoint_required']");
		if(inputs.length>0){
			var result=validateObject.validateElement(inputs[0]);
			if(!result){
				if(lbpm.approveType == "right"){
					var $spreadBtn = $('#spreadBtn');
	    			if($spreadBtn.hasClass('spread')){
	    				$spreadBtn.click();
	    			}
				}
				return false;
			}
		}
	}
	lbpm.globals.extAuditPointParameter();
	return true;
});

lbpm.globals.extAuditPointHTML=function(){
	function _getLangLabel(defLabel,langsArr,lang){
		if(langsArr==null){
			return defLabel;
		}
		for(var i=0;i<langsArr.length;i++){
			if(lang==langsArr[i]["lang"]){
				return langsArr[i]["value"]||defLabel;
			}
		}
		return defLabel;
	}

	var currNodeId = lbpm.nowNodeId;
	if(!currNodeId){
		return "";
	}
	var pointCfgs = _extAuditPointCfg[currNodeId];
	if(!pointCfgs || pointCfgs.length==0){
		return "";
	}
	
	var html=[];
	if(isRight == "true"){
		html.push('<div class="lui-lbpm-titleNode ding-lui-lbpm-titleNode">');
	}else{
		html.push('<td class="td_normal_title lbpmext-auditpoint-warn">');
	}
	html.push('<bean:message key="table.lbpmExtAuditPoint" bundle="sys-lbpmext-auditpoint" />');
	if(isRight == "true"){
		html.push('</div>');
		html.push('<div class="lui-lbpm-detailNode"><table><tr><td>');
	}else{
		html.push('</td>');
		html.push('<td>');
	}
	
	for(var i=0;i<pointCfgs.length;i++){

		var titleLangs = pointCfgs[i]["titleLangs"];
		var title = pointCfgs[i]["fdTitle"];
		if(_isLangSuportEnabled){
			if(typeof titleLangs!="undefined"){
				title = _getLangLabel(title,titleLangs,_userLang);
				_pointsLangs[pointCfgs[i]["fdId"]]=titleLangs;
			}
		}

		html.push('<div style="word-break:break-all;">');
		html.push('<label class="lui-lbpm-checkbox">');
		html.push('<input type="checkbox" value="true" ');
		html.push('lbpmext-auditpoint-data-order="'+pointCfgs[i]["fdOrder"]+'"');
		html.push('lbpmext-auditpoint-data-id="'+pointCfgs[i]["fdId"]+'"');
		html.push('lbpmext-auditpoint-data-title="'+Com_HtmlEscape(title)+'"');
		html.push('lbpmext-auditpoint-data-important="'+pointCfgs[i]["fdIsImportant"]+'"');
		if(pointCfgs[i]["fdIsImportant"]=="true"){
			html.push('validate="lbpmext_auditpoint_required"');
		}else{
			html.push('validate=""');
		}
		html.push('subject="${ lfn:message('sys-lbpmext-auditpoint:lbpmExtAuditPoint.important') }：'+Com_HtmlEscape(title)+'"');
		html.push('name="lbpmext_auditpoint_chk"/><span class="checkbox-label">');
		html.push(Com_HtmlEscape(title));
		if(pointCfgs[i]["fdIsImportant"]=="true"){
			html.push('<span class="txtstrong">*</span>');
		}
		html.push('</span></label>');
		html.push('</div>');
	}
	if(isRight == "true"){
		html.push('</td></tr></table></div>');
	}else{
		html.push('</td>');
	}
	return html.join('');
}

lbpm.globals.extAuditPointAddValidator=function(){
	if(isRight == "true" && lbpm && lbpm.constant && lbpm.constant.ISINIT){
		$(".lui-lbpm-fold-add").before($("#_lbpmExtAuditPointCfgTr"))
	}else{
		$("#_lbpmExtAuditPointCfgTr").after($("#descriptionRow"));
	}
	var validateObject=$GetFormValidation(document.forms[0]);
	if(validateObject){
		validateObject.addValidator("lbpmext_auditpoint_required",
				'<bean:message key="lbpmExtAuditPoint.important.cannot.benull" bundle="sys-lbpmext-auditpoint" />',
				function(v, e, o){
					var target = Com_GetEventObject();
					if(Com_Parameter.preOldSubmit!=null){
						target = Com_Parameter.preOldSubmit
					}
					var isDraft = ((target && target.currentTarget && target.currentTarget.title == lbpm.constant.BTNSAVEDRAFT) 
							|| (target && target.srcElement && target.srcElement.innerText == lbpm.constant.BTNSAVEDRAFT) 
							|| (target && target.srcElement && target.srcElement.title == lbpm.constant.BTNSAVEDRAFT));
					if(isDraft){
						return true;
					}
					if(lbpm.operations[lbpm.currentOperationType] && lbpm.operations[lbpm.currentOperationType].isPassType
							&&!lbpm.globals.extAuditPointValidate()){
						if(lbpm && lbpm.approveType == "right"){
							var $spreadBtn = $('#spreadBtn');
			    			if($spreadBtn.hasClass('spread')){
			    				$spreadBtn.click();
			    			}
						}
						setTimeout(function(){
							//校验不通过,定位到指定元素
							try {
								e.focus();
							} catch (err) {}
							
							var posHeight = $(e).offset().top;
							var screenHeight = window.innerHeight?window.innerHeight:$(window).height();
							var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
							if(lbpm && lbpm.approveType == "right"){
								scrollTop = $(".lui-fm-stickyR-inner").scrollTop();
							}
							if( (posHeight + $(e).height() + scrollTop) >= screenHeight){
								var y = 20;
								var _adviceMsg = $KMSSValidation_GetAdvice(e);
								if (_adviceMsg) {
									y = _adviceMsg.offsetHeight > y ? _adviceMsg.offsetHeight : y;
								}
								var scrollTop =  posHeight - screenHeight/2 + y;
								$("html,body").scrollTop(scrollTop);
								$(".lui-fm-stickyR-inner").scrollTop(scrollTop);
							}else{
								$("html,body").scrollTop(0);
								$(".lui-fm-stickyR-inner").scrollTop(0);
							}
						},300)
						return false;
					}
					return true;
			});
	}else{
		$("input[validate='lbpmext_auditpoint_required']").attr("validate","required");
	}
}

lbpm.globals.extAuditPointValidate = function(){
	var inputs=$("input[validate='lbpmext_auditpoint_required']");
	for(var i=0;i<inputs.length;i++){
		if(!inputs[i].checked){
			return false;
		}
	}
	return true;
}

lbpm.globals.extAuditPointParameter=function(){
	var params=[];
	$("[name='lbpmext_auditpoint_chk']").each(function(i,e){
		var param={};
		param["fdOrder"]=$(this).attr("lbpmext-auditpoint-data-order");
		param["fdTitle"]=$(this).attr("lbpmext-auditpoint-data-title");
		if(_isLangSuportEnabled){
			var id = $(this).attr("lbpmext-auditpoint-data-id");
			if(typeof _pointsLangs[id] !="undefined"){
				param["fdLangs"]=JSON.stringify(_pointsLangs[id]);
			}
		}
		param["fdIsImportant"]=$(this).attr("lbpmext-auditpoint-data-important");
		param["fdId"]=$(this).attr("lbpmext-auditpoint-data-id");
		param["auditNoteFdId"]="${param.auditNoteFdId}";
		param["fdIsPass"]=""+e.checked;
		params.push(param);
	});
	if(params.length>0){
		lbpm.globals.setOperationParameterJson(JSON.stringify(params),"auditPointJson", "param");
	}
}

</script>
</c:if>
