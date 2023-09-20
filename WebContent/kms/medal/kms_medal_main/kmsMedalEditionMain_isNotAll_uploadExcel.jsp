<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<center>
	<table class="tb_normal" width="100%" id="centerId">
		<tr>
			<td valign="center" width="113px;" class="td_normal_title">
				<bean:message key="kmsMedal.send.honoursPersonnel" bundle="kms-medal" />
			    
			</td>
			<td valign="center" width="227px;">
				<table width="100%" class="tb_noborder" id="checkbox">
					<tr>
						<td>
							<kmss:editNotifyType property="fdNotifyType" required="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
	</table>
	
</center>
<script type="text/javascript">

	seajs.use(["lui/dialog", "lui/jquery", "lui/topic"], function(dialog, $, topic) {
	         $("input[type=checkbox]").click(function(){

	        	var checked=$(this).is(":checked");
	        	if(checked==true){
	        		$(this).attr("checked","checked");
	        	}else{
	        		$(this).removeAttr("checked");
	        	}
	         });
	}); 
</script>
<script type="text/javascript">
 
	 seajs.use(["lui/dialog", "lui/jquery", "lui/topic"], function(dialog, $, topic) {
		   //方法二：
        (function ($) {
            $.getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            }
        })(jQuery);
		   
       var htmlTmp = $.getUrlParam('htmlTmp');
       
       $("#centerId tr:first").before(decodeURI(htmlTmp));
     
    }); 
 </script>


	</template:replace>
</template:include>
