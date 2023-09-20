<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script>
    $(document).ready(function(){
        var docStatus = "${param.docStatus}";
        if(docStatus == '10' || docStatus == '11'){
            setTimeout(function(){
                if($(".com_ap_bar2_centre").length > 0 && $("#descriptionRow").length > 0){
                    $(".com_ap_bar2_centre").hide();
                    $("#descriptionRow").hide();
                }
            },1);
        }
    });
</script>

<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="${param.formName}" />
	<c:param name="fdKey" value="${param.fdKey}" />
	<c:param name="isExpand" value="${param.isExpand}" />
</c:import>
