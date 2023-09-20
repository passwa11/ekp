<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
	<script type="text/javascript">
	
	$(function(){ 
		$.ajax({
	        type: "post",
	        url: "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=checkKKConfig",
	        async : false,
	        dataType: "json",
	        success: function (data ,textStatus, jqXHR)
	        {
	            if(true == data.kkConfigFlag ){
	               	window.location.href = "${LUI_ContextPath}/third/im/kk/connKKConfig.do?method=configView";
	            }else{
	            	window.location.href = "${LUI_ContextPath}/third/im/kk/kk_config/kkConfig_index.jsp";
	            }
	        }
	     });
	})
	
	</script>
