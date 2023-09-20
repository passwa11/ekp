<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>		
<%@page import="com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer" %>
<%@page import="javax.servlet.jsp.JspException" %>			
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>		

<%
	String isJGHandSignatureEnabled = ResourceUtil.getKmssConfigString("sys.att.isJGHandSignatureEnabled");
	LbpmSetting lbpmSetting = new LbpmSetting();
	if(lbpmSetting.getIsHandSignatureEnabled().equalsIgnoreCase("true") 
			&& "true".equalsIgnoreCase(isJGHandSignatureEnabled)
			&& lbpmSetting.getHandSignatureType().equalsIgnoreCase("jg")){
		String handSignture = "";
		String targetUrl = "/sys/lbpmservice/signature/handSignture/iWebRevision_edit.jsp?";
		targetUrl += "iWebRevisionObjectId=iWebRevisionObject_process"; // 唯一id
		targetUrl += "&recordID=" + request.getParameter("auditNoteFdId");
		targetUrl += "&fieldName=LBPMProcess_" + request.getParameter("auditNoteFdId");
		targetUrl += "&userName=" + request.getParameter("curHanderId");
		targetUrl = TargetUrlContentAcquirer.coverUrl(targetUrl,pageContext);
		try {
			handSignture = new TargetUrlContentAcquirer(targetUrl,pageContext,
					"UTF-8").acquireString().trim().replaceAll("\\s{1,}", " ");
		} catch (JspException e) {
			e.printStackTrace();
		}
%>

<script>
	Com_IncludeFile("iWebRevisionObject_script.js",Com_Parameter.ContextPath+'sys/lbpmservice/signature/handSignture/','js',true);
</script>
<script>

function lbpmWordSign(dom){
	var wordsObj = document.getElementById("fdUsageContentSpan");
	var handObj = document.getElementById("process_hand_Sign");
	wordsObj.style.display="block";
	handObj.style.display="none";
	var $tb = $(dom).closest('table');
	if($tb){
		var webRevision = $tb.find("[name='iWebRevisionObject']");
		if(webRevision.length > 0){
			try{
				webRevision[0].Clear();		
			}catch(e){
				
			}		
		}
	}
	
	document.getElementById("hand_Sign_Href").style.display="inline-block";
	document.getElementById("words_Sign_Href").style.display="none";
}

function lbpmHandSign(dom){
	var wordsObj = document.getElementById("fdUsageContentSpan");
	var handObj = document.getElementById("process_hand_Sign");
	wordsObj.style.display="none";
	if(handObj){
		handObj.style.display="block";	
	}
	var $tb = $(dom).closest('table');
	
	// 清空文字意见值
	document.getElementsByName("fdUsageContent")[0].value = "";
	document.getElementById("words_Sign_Href").style.display="inline-block";
	document.getElementById("hand_Sign_Href").style.display="none";
	
	if($tb){
		var webRevision = $tb.find("[name='iWebRevisionObject']");
		if(webRevision.length > 0){
			try{
				// 加个延时，不然首次没反应
				setTimeout(function(){
					webRevision[0].OpenSignature();
				},0);
			}catch(e){
				
			}		
		}
	}
}

// 保存手写签章
function iWebRevisionObj() {
	
	try{
		var iWebRevisionObjectProcess = $("#iWebRevisionObject_process");
		// 流程页签的手写签批，以手写签批的为准 
		if(!iWebRevisionObjectProcess[0].DocEmpty){
			iWebRevisionObjectProcess[0].SaveSignature();
		}
		var iWebRevisionObject = $("[name='iWebRevisionObject']");
		var recordId = iWebRevisionObjectProcess[0].RecordID;
		var userName = iWebRevisionObjectProcess[0].UserName;
		// 从后面开始
		for(var i = iWebRevisionObject.length - 1;i >= 0;i-- ){
			var revision = iWebRevisionObject[i];
			if(revision == iWebRevisionObjectProcess[0]){
				continue;
			}
			if(!revision.DocEmpty && revision.Enabled && revision.RecordID == recordId && revision.UserName == userName){
				revision.SaveSignature();
				break;
			}
		}
		return true;	
	}catch(e){
		return true;
	}
	
}

// 初始化审批意见栏
function initOperation(){
	var isRight = "${param.approveModel eq 'right'}";
	if(isRight == "true"){
		var html = '<div class="lui-lbpm-opinion-words" id="words_Sign_Href" style="display: none" title="文字签批" onclick="lbpmWordSign(this);">';
		html += ' <i></i>';
		html += '</div>';
		html += '<div class="lui-lbpm-opinion-hand lui-lbpm-opinion-btn" id="hand_Sign_Href" style="display: inline-block;" title="手写签批" onclick="lbpmHandSign(this);">';
		html += ' <i></i>';
		html += '</div>';
		var btnLen= $('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').length;
		//如果超过3个按钮，则按三个按钮计算，并把当前按钮放置到更多中
		if(btnLen-1 >= 3){
			//转移最后一个到更多中
			$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").append("<li>"+$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).prop("outerHTML")+ "</li>");
			$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn').eq(btnLen-2).remove();
			
			html = "<li>" + html + "</li>";
			$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").find(".lui-lbpm-opinion-moreList").eq(0).find("ul").append(html);
			$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn,.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-words').css({
			       'width':100/3+'%'
			});
			$('.lui-lbpm-opinion-moreList .lui-lbpm-opinion-btn,.lui-lbpm-opinion-moreList .lui-lbpm-opinion-words').css({
			       'width':'100%'
			});
			$('.lui-lbpm-opinion-more').css({
				'display':'inline-block'
			})
		}else{
			$(".lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-more").before(html)
			$('.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-btn,.lui-lbpm-opinion-otherFnc .lui-lbpm-opinion-words').css({
			       'width':100/btnLen+'%'
			});
		}
	}else{
		var optionButtons = $('#optionButtons');
		// 手写和文字签批的转换
		if(optionButtons){
			var html = '<span id="words_Sign_Href" style="display: none">';
			html += '<a href="javascript:;" class="com_btn_link" style="margin: 0 10px;" onclick="lbpmWordSign(this);">文字签批</a>';
			html += '</span>';
			html += '<span id="hand_Sign_Href" style="display: inline-block;">';
			html += '<a href="javascript:;" class="com_btn_link" style="margin: 0 10px;" onclick="lbpmHandSign(this);">手写签批</a>';
			html += '</span>';
			optionButtons.append(html);
		}
	}
	
	if(lbpm && lbpm.constant && lbpm.constant.ROLETYPE!='' && lbpm.constant.ROLETYPE!=lbpm.constant.PROCESSORROLETYPE){
		$("#hand_Sign_Href .com_btn_link").hide();
	}
	
	var fdUsageContentTd = $("#fdUsageContentTd");
	if(fdUsageContentTd){
		var html = '<span id="process_hand_Sign" style="display: none;width:98%;">';
		html += '<%=handSignture%>';
		html += '</span>';
		fdUsageContentTd.append(html);
	}
	
	var revisionObjList = document.getElementsByName("iWebRevisionObject");
	
		for(var i=0;i<revisionObjList.length;i++){
			try{
				revisionObjList[i].InputText = "";
				revisionObjList[i].InvisibleMenus('-4,');
				revisionObjList[i].ShowPage = '1';
				revisionObjList[i].InvisiblePages('0,');
			}catch(e){
				
			}	
		}				
	
	//添加必填校验，该校验和fdUsageContent的必填校验，只要有一个通过即校验通过 by zhugr 2017-09-04
	lbpm.events.addListener(lbpm.constant.EVENT_validateMustSignYourSuggestion,function() {
		try{
			var iWebRevisionObject = $("[name='iWebRevisionObject']");
			// 流程页签的手写签批，以手写签批的为准 
			var iWebRevisionObjectProcess = $("#iWebRevisionObject_process");
			var recordId = iWebRevisionObjectProcess[0].RecordID;
			var userName = iWebRevisionObjectProcess[0].UserName;
			for(var i = iWebRevisionObject.length - 1;i >= 0;i-- ){
				var revision = iWebRevisionObject[i];
				if(!revision.DocEmpty && revision.Enabled && revision.RecordID == recordId && revision.UserName == userName){
					return true;
				}
			}
			return false;	
		}catch(e){
			return true;
		}
		
	});
}

lbpm.onLoadEvents.delay.push(initOperation);

Com_Parameter.event["confirm"].push(iWebRevisionObj);

</script>
<%}%>