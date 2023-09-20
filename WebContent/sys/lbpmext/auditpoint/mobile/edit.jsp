<%@page import="com.landray.kmss.sys.lbpmext.auditpoint.util.AuditPointUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" /> 

<%
	String _auditNoteFdId = request.getParameter("auditNoteFdId");
	request.setAttribute("_lbpmExtAuditPointCfg",
		AuditPointUtil.loadLbpmExtAuditPointCfg(_auditNoteFdId,pageContext.getAttribute("sysWfBusinessForm")));
%>

<c:if test="${not empty _lbpmExtAuditPointCfg }">

<script>
var _extAuditPointCfg=${_lbpmExtAuditPointCfg};
var _pointsLangs={};

if($("#lbpmext_auditpoint_select_content").length <= 0){//兼容
	$('.lbpmAuditNoteTable').parent().before('<div class="actionView"><div id="lbpmext_auditpoint_select_content" style="display:none"></div></div><div class="optionsSplitLine"></div>');
}

lbpm.globals.extAuditPointDestroyId=function(pointCfgs){
	require(["dijit/registry"],
		function(registry){
			for(var i=0;i<pointCfgs.length;i++){
				var id=pointCfgs[i]["fdId"];
				if(registry.byId(id)){
						registry.byId(id).destroy();    
				}
			}
		}
	);
}

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
	lbpm.globals.extAuditPointDestroyId(pointCfgs);

	var html=[];
	html.push('<div class="titleNode operation_title">');
	html.push('<bean:message key="table.lbpmExtAuditPoint" bundle="sys-lbpmext-auditpoint" />');
	html.push('</div>');
	html.push('<div class="detailNode" style="border-bottom:0;padding-bottom:0;word-break:break-all;">');
	for(var i=0;i<pointCfgs.length;i++){
		html.push('<div>');
		html.push('<label>');

		var titleLangs = pointCfgs[i]["titleLangs"];
		var title = pointCfgs[i]["fdTitle"];
		if(_isLangSuportEnabled){
			if(typeof titleLangs!="undefined"){
				title = _getLangLabel(title,titleLangs,_userLang);
				_pointsLangs[pointCfgs[i]["fdId"]]=titleLangs;
			}
		}

		if(dojoConfig.dingXForm == "true"){
			html.push('<div style="float:left" data-dojo-type="mui/form/CheckBoxGroup" ');	
		}else {
		    html.push('<div data-dojo-type="mui/form/CheckBoxGroup" ');	
		}
		if (i != pointCfgs.length-1) {
			//html.push('style="border-bottom:1px solid #e2e2e2;" ');
		}
		var dataDojoProps={};
		dataDojoProps["name"]='lbpmext_auditpoint_chk_'+pointCfgs[i]["fdId"];
		dataDojoProps["mul"]=false;
		dataDojoProps["concentrate"]=false;
		dataDojoProps["showStatus"]="edit";
		dataDojoProps["subject"]=title;
		dataDojoProps["store"]=[
				{
				"text":title.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;"),
				"value":"true",
				"checked":false
				}
		];
		var str = Com_HtmlEscape(JSON.stringify(dataDojoProps));
		str=str.substring(1,str.length-1);
		html.push('data-dojo-props="'+str+'"');
		html.push(' id="'+pointCfgs[i]["fdId"]+'"');
		html.push(' lbpmext-auditpoint-data-order="'+pointCfgs[i]["fdOrder"]+'"');
		html.push(' lbpmext-auditpoint-data-id="'+pointCfgs[i]["fdId"]+'"');
		html.push(' lbpmext-auditpoint-data-title="'+Com_HtmlEscape(title)+'"');
		html.push(' lbpmext-auditpoint-data-important="'+pointCfgs[i]["fdIsImportant"]+'">');
		html.push('</div>');

		html.push('</label>');
		html.push('</div>');

		html.push('<style>');
		html.push("[id='"+pointCfgs[i]["fdId"]+"'] .muiCheckBoxWrap{");
		html.push('	margin-top: 0 !important;');
		html.push('	padding-top: 1rem;');
		html.push('	padding-bottom: 1rem;');
		html.push('}');		
		html.push("[id='"+pointCfgs[i]["fdId"]+"'] .muiCheckItem{");
		html.push('	margin-top: 0 !important;');
		html.push('}');	
		if(pointCfgs[i]["fdIsImportant"]=="true"){
			html.push("[id='"+pointCfgs[i]["fdId"]+"']:AFTER{");
		    html.push("	position: absolute;");
			html.push("	content:'*';");
			html.push('	color: red;');
			html.push('	right: 0;');
			html.push(' top:0;');
			html.push(' padding-top:1rem;');
			html.push('}');
			html.push("[id='"+pointCfgs[i]["fdId"]+"'] .muiCheckItem{");
			html.push(" width:100%;");
			html.push("}");
			lbpm.operationButtonType = null;
			window.operationButtonType = null;
		}
		html.push('</style>');
	}
	html.push('</div>');
	//添加一个隐藏的校验域
	var nodeHtml = "<div id='auditPointEmptyDiv' style='position:relative'><div style='position:relative;height:0px;width:0px;' class='auditPointValidator' data-dojo-type='mui/form/Input' data-dojo-props='\"name\":\"auditPointEmpty\",\"showStatus\":\"edit\",\"validate\":\"auditPointRequired\"'></div></div>";
	if(dojoConfig.dingXForm == "true"){
	   nodeHtml = "<div id='auditPointEmptyDiv' style='z-index: -1;width:100%;position:relative'><div style='position:relative;height:0px;width:0px;' class='auditPointValidator' data-dojo-type='mui/form/Input' data-dojo-props='\"name\":\"auditPointEmpty\",\"showStatus\":\"view\",\"validate\":\"auditPointRequired\"'></div></div>";
	}
	html.push(nodeHtml);		
	return html.join('');
}

lbpm.globals.extAuditPointSwitch=function(){
	require(["dojo/query"],
		function(query){
			var html=lbpm.globals.extAuditPointHTML();
			if(html!=""){
				query('#lbpmext_auditpoint_select_content').html(html, {parseContent: true, onEnd: function() {
					this.inherited("onEnd", arguments);
					if (this.parseDeferred) {
						this.parseDeferred.then(function() {
						});
					}
				}});
			} else {
				query('#lbpmext_auditpoint_select_content').html("");
			}
			$('#lbpmext_auditpoint_select_content .mui-checkbox-base').css({"text-align":"left","display":"block"}); 
	});
}

Com_Parameter.event["submit"].push(function(formObj, method, clearParameter, moreOptions){
	//暂存无需必填
	if(moreOptions && moreOptions.saveDraft){
		return true;
	}
	var opr=$("#operationMethodsGroup").val();
	if(opr.indexOf("handler_pass")>-1
			||opr.indexOf("handler_sign")>-1
			||opr.indexOf("drafter_submit")>-1){
		var records=$("input[type='hidden'][name*='lbpmext_auditpoint_chk_']");
		for(var i=0;i<records.length;i++){
			var e=records[i];
			var fdId=e.name.substring('lbpmext_auditpoint_chk_'.length,e.name.length);
			var source=$("#"+fdId);
			var fdIdImportant=source.attr("lbpmext-auditpoint-data-important");
			var fdIsPass=e.value;
			if("true"==fdIdImportant&&!"true"==fdIsPass){
				require(["mui/dialog/Alert"], function(Alert){
					Alert('<bean:message key="lbpmExtAuditPoint.important.cannot.benull" bundle="sys-lbpmext-auditpoint" />');
				});
				return false;
			}
		}
	}
	
	lbpm.globals.extAuditPointParameter();

	return true;
});


lbpm.globals.extAuditPointParameter=function(){
	var params=[];
	$("input[type='hidden'][name*='lbpmext_auditpoint_chk_']").each(function(i,e){
		var param={};
		param["fdId"]=e.name.substring('lbpmext_auditpoint_chk_'.length,e.name.length);
		var source=$("#"+param["fdId"]);

		if(_isLangSuportEnabled){
			var id = source.attr("lbpmext-auditpoint-data-id");
			if(typeof _pointsLangs[id] !="undefined"){
				param["fdLangs"]=JSON.stringify(_pointsLangs[id]);
			}
		}

		param["fdIsImportant"]=source.attr("lbpmext-auditpoint-data-important");
		param["fdTitle"]=source.attr("lbpmext-auditpoint-data-title");
		param["fdOrder"]=source.attr("lbpmext-auditpoint-data-order");
		param["fdIsPass"]=""+e.value;
		param["auditNoteFdId"]="${param.auditNoteFdId}";

		params.push(param);
	});
	
	if(params.length>0){
		lbpm.globals.setOperationParameterJson(JSON.stringify(params),"auditPointJson", "param");
	}
}

require(["dojo/query" ,"dojo/dom","dojo/fx","dojo/topic","dijit/registry"],
	function(query,dom,fx,topic,registry){
		function showAndHide(operationType){
			if (dom.byId("lbpmext_auditpoint_select_content") == null) {
				return;
			}
			if(!(operationType
					&&lbpm.operations[operationType]
					) || (lbpm.nowProcessorInfoObj && lbpm.nowProcessorInfoObj.type == 'communicateWorkitem')){ //&&lbpm.operations[lbpm.currentOperationType].isPassType//沟通工作项不显示审批要点
				fx.wipeOut({  
					node: dom.byId("lbpmext_auditpoint_select_content")  
				}).play();
			}else{
				lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.extAuditPointSwitch);
				lbpm.globals.extAuditPointSwitch();

				fx.wipeIn({  
					node: dom.byId("lbpmext_auditpoint_select_content")  
				}).play();
			}
		}
		topic.subscribe("/lbpm/operation/switch",function(wgt,ctx){
			if(ctx && ctx.methodSwitch){
				showAndHide(ctx.value);
			}
		});
		topic.subscribe("/mui/form/valueChanged",function(wgt,ctx){
			if(!lbpm || !lbpm.nowNodeId){
				return;
			}

			if(!wgt){
				return;
			}

			var currNodeId = lbpm.nowNodeId;
			var pointCfgs = _extAuditPointCfg[currNodeId];
			if(pointCfgs){
				for(var i=0; i<pointCfgs.length; i++){
					if(pointCfgs[i]["fdIsImportant"]=="true"){
						if(wgt.id == pointCfgs[i]["fdId"]){//符合id并且是必填的
							//执行校验
							var wgtId = query(".auditPointValidator")[0].id;
							var auditPointValidator = registry.byId(wgtId);
							if(auditPointValidator.validation){
								//修改值
								query("input[type='hidden'][name='lbpmext_auditpoint_chk_"+wgt.id+"']")[0].value = ctx.value;
								auditPointValidator.validation.validateElement(auditPointValidator);
							}
						}
					}
				}
			}
		})
});

// 起草时也要加载出审批要点
require(["dojo/query" ,"dojo/dom","dojo/fx","dojo/topic","dojo/ready"],
		function(query,dom,fx,topic,ready){
			topic.subscribe('parser/done',function(){
				lbpm.events.addListener(lbpm.constant.EVENT_CHANGEWORKITEM, lbpm.globals.extAuditPointSwitch);
				lbpm.globals.extAuditPointSwitch();
				if (dom.byId("lbpmext_auditpoint_select_content") == null) {
					return;
				}
				fx.wipeIn({  
					node: dom.byId("lbpmext_auditpoint_select_content")  
				}).play();
			})
		});
</script>
</c:if>