<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<kmss:authShow roles="ROLE_SYSPRAISE_DEFAULT">
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.praise.service.ISysPraiseInfoConfigService"%>
<%
	ISysPraiseInfoConfigService sysPraiseInfoConfigService = (ISysPraiseInfoConfigService) SpringBeanUtil
			.getBean("sysPraiseInfoConfigService");
	String fdUsefulLink = sysPraiseInfoConfigService.getUsefulLink();
	String fdShowPraiseBtn = "false";
	String fdURI = request.getRequestURI();
	fdURI = fdURI.replaceFirst(request.getContextPath(), "");
	if (StringUtil.isNotNull(fdURI) && StringUtil.isNotNull(fdUsefulLink)) {
		String[] items = fdURI.split("/");
		if (items.length > 2) {
			String checkItem = items[1] + "/" + items[2];
			if (fdUsefulLink.indexOf(checkItem) > -1) {
				fdShowPraiseBtn = "true";
			}
		}
	}
	request.setAttribute("fdShowPraiseBtn", fdShowPraiseBtn);
%>
<c:if test="${fdShowPraiseBtn eq 'true' }">
<link href="${LUI_ContextPath}/sys/praise/style/zonePage-css.css"
	rel="stylesheet">
<ui:button parentId="top" styleClass="lui-praise-btn" order="9"
	onclick="addPraiseInfo()" id="luiPraiseBtn">
	<img src="${LUI_ContextPath}/sys/praise/style/images/praiseItem.png">
</ui:button>
<script>
	seajs.use([ 'lui/topic' ], function(topic) {

			topic.subscribe('lui.page.open', function(evt) {

				if (evt.target !== '_content') {
					LUI('luiPraiseBtn').setVisible(false);
				}
			});

		})
	
	var __top = Com_Parameter.top || window.top;
	__top.window.showPraiseIframe = true;
	
    function addPraiseInfo() {
       var top = Com_Parameter.top || window.top;
    	if(top.window.showPraiseIframe){
            seajs.use( [ 'lui/dialog' ],
                    function (dialog) {
                        var fdUrl = "/sys/praise/sys_praise_info/sysPraiseInfo.do?method=add&editPerson=true";
                        if (top.window.fdPraiseTargetPerson) {
                            fdUrl += "&fdPersonId="
                                    + top.window.fdPraiseTargetPerson;
                        }
                        dialog
                                .iframe(
                                        fdUrl,
                                        "${ lfn:message('sys-praise:module.sys.praiseInfo') }",
                                        changetInfo,
                                        {
                                            width : 600,
                                            height : 380 
                                        });
                    });
        }
        top.window.showPraiseIframe = false;
    }
    
    function changetInfo(){
    	var top = Com_Parameter.top || window.top;
        top.window.showPraiseIframe = true;
    }
</script>
</c:if>
</kmss:authShow>