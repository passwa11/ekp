<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%
     pageContext.setAttribute("_isWpsWebOffice", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
%>

<script>Com_IncludeFile("jquery.js");</script>
<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
<script>Com_IncludeFile("wps_cloud_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/cloud/js/","js",true);</script>
<script>Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);</script>
<script type="text/javascript">
			
		    Com_AddEventListener(window, 'load', function() {
		    	
		    	var resultJson = checkValue(${resultJson});
		    	var _isWpsWebOffice = "${_isWpsWebOffice}";
		    	
		    	if(_isWpsWebOffice == 'true')
		    	{
		    		$(".qrcodeArea").remove();
		    		var fdTemplateId = "${fdTemplateId}";
		             var wpsPrintEdit = "${sysPrintTemplateModel.fdPrintEdit}";
		        	  var url ="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=findAttMains"; 
		        	    $.ajax({
		        	        type:"post",
		        	        url:url,
					 	     data:{fdModelName:"${HtmlParam.modelName}",fdKey:"sysprint_editonline",fdModelId:"${fdTemplateId}"},    
		        	        dataType:"json",
		        	        async:false,
		        	        success:function(data){
		        	        	var wpsParam = {};
						    	wpsParam['wpsSysPrintApp'] = 'SysPrintApp';
						    	wpsParam['wpsPrintEdit'] = wpsPrintEdit;
						    	wpsParam['printMarkContent'] = resultJson;
						    	openWpsOAAssit(data.attMainId,wpsParam);
						    //	setTimeout(function(){ window.close();},10000); //10秒后关闭
		        			}
		        	     });

		    	}
		    	
			});
		    
		    function checkValue(value)
		    {
		    	if(value == '')
		    	{
		    		return '';
		    	}else 
		    	{
		    		return value;
		    	}
		    }
		  
    	</script>