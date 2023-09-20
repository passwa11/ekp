<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/evaluation/resource/evaluation/share/share_view.css">
<script>
    window.iframeHeight = parent.$("div[class='lui_dialog_content']").css("height");

    function shareSelect(localId, thisObj) {
        if(!$){
            return;
        }
        $("#shareGroup>div").hide();
        $("#" + localId).show();

        $("input[name='shareBtn']").removeClass("share_btn_on");
        $(thisObj).addClass("share_btn_on");

        var dialogContent = parent.$("div[class='lui_dialog_content']");
        if ("shareModule1" == localId) {
            dialogContent.css("height", "330px");
        } else {
            parent.$("div[class='lui_dialog_content']").css("height", window.iframeHeight);
        }
    }

    window.onload=function () {
        bindPersonIframeEvent();
        if(typeof bindGroupIframeEvent != "undefined"){
            bindGroupIframeEvent();
        }
    }
</script>

<body>
    <div class="share_toolbar">
        <!-- 分享项 -->
        <input id="shareBtnPerson" name="shareBtn" type="button" onclick="shareSelect('shareModule0',this)" value="${lfn:message('sys-evaluation:enums.stype.1')}" class="share_btn_gray share_btn_on">
        <input id="shareBtnFreiends" name="shareBtn" type="button" onclick="shareSelect('shareModule1',this)" value="${lfn:message('sys-evaluation:enums.stype.2')}" class="share_btn_gray">
        <kmss:ifModuleExist path="/sns/group">
        	<input id="shareBtnCircle" name="shareBtn" type="button" onclick="shareSelect('shareModule2',this)" value="${lfn:message('sys-evaluation:enums.stype.3')}" class="share_btn_gray">
   		</kmss:ifModuleExist>
    </div>

    <div id="shareGroup">
	    <div id="shareModule0" style="display: block;">
	        <c:import url="/sys/evaluation/sys_evaluation_share/sysEvaluationShareToPerson.jsp" charEncoding="UTF-8" >
                <c:param name="fdModelId" value="${param.fdModelId}" />
                <c:param name="fdModelName" value="${param.fdModelName}" />
	        </c:import>
	    </div>
	
	    <div id="shareModule1" style="display: none;">
	        <c:import url="/sys/evaluation/sys_evaluation_share/sysEvaluationShareToFriends.jsp" charEncoding="UTF-8" >
                <c:param name="fdModelId" value="${param.fdModelId}" />
                <c:param name="fdModelName" value="${param.fdModelName}" />
                <c:param name="fdUrl" value="${param.fdUrl}" />
	        </c:import>
	    </div>

        <kmss:ifModuleExist path="/sns/group">
            <div id="shareModule2" style="display: none;">
                <c:import url="/sys/evaluation/sys_evaluation_share/sysEvaluationShareToGroup.jsp" charEncoding="UTF-8" >
                    <c:param name="fdModelId" value="${param.fdModelId}" />
                    <c:param name="fdModelName" value="${param.fdModelName}" />
                </c:import>
            </div>
        </kmss:ifModuleExist>
    </div>
</body>