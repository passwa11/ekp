<!doctype html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/ctrip/xform/resource/css/thirdCtripXform.css">
</head>
<body>
	<script>
		seajs.use(['lui/jquery'], function($) {
			
			LUI.ready( function() {
				approvalCtrip();
			});
			
			//预审批
			function approvalCtrip(){
				var url = Com_Parameter.ContextPath + "third/ctrip/ctripCommon.do?method=appovalCtripOrder&modelName=${param.modelName}&fdId=${param.fdId}"
							+ "&fdControlId=${param.fdBookId}&bookType=${param.type}";
				
				$.ajax({
					type:"post",
					url:url,
					asyncx:true,
					success:function(result){
						var resultJson = $.parseJSON(result);
						if(resultJson && resultJson != null){
							//error为0即表明数据没问题
							if(resultJson.errcode == '0'){					
								var ssoUrl = Com_Parameter.ContextPath + "third/ctrip/ctripCommon.do?method=ctripSsoAuth&modelName=${param.modelName}&fdId=${param.fdId}&fdControlId=${param.fdBookId}&type=pc&orderId=${param.fdId}&bookType=${param.type}";
								window.location.href = ssoUrl;
							}else{
								$("#third_ctrip_loading").hide();
								alert(resultJson.errmsg);
							}
						}
					}
				});
			}
			
		});
		
	</script>
	<div title="Loading..." class="third_ctrip_loading"></div>
	
</body>
</html>