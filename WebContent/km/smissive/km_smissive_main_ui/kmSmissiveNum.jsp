<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<%@ include file="/km/smissive/cookieUtil_script.jsp"%>
	<script language="JavaScript">
	seajs.use(['theme!form']);
	seajs.use(['lui/jquery'],function($){
		$(document).ready(function(){
			var docId = "${JsParam.fdId}";
			var fdNumberId = "${JsParam.fdNumberId}";
			  generateNum(docId,fdNumberId);
		});
	});
	function generateNum(docId,fdNumberId){
		 var docNum = document.getElementsByName("fdNum")[0];
		if(getTempNumberFromDb(fdNumberId)){
			 docNum.value = decodeURI(getTempNumberFromDb(fdNumberId));
	    }else if("${JsParam.isAdd}" == 'true' && getTempNumberFromDb(fdNumberId)){
	    	 docNum.value = decodeURI(getTempNumberFromDb(fdNumberId));
		}else{
			 var url = "${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=generateNumByNumberId"; 
			 $.ajax({     
	    	     type:"post",     
	    	     url:url,     
	    	     data:{fdNumberId:fdNumberId,fdId:docId},
	    	     async:false,    //用同步方式 
				 dataType:"json",
				 success:function(results){
	    		    if(results['docNum']!=null){
	    		   	   docNum.value = results['docNum'];
	    			}
	    		}    
	        });
		}      
	}
	function optSubmit(){
		var fdNum = document.getElementsByName("fdNum")[0].value;
		$dialog.hide(fdNum);
		return true;	
	}
	</script>
	<center>
		<div>
			<table class="tb_normal" width=95% style="margin-top:25px">
				<tr>
				    <td><bean:message  bundle="km-smissive" key="kmSmissiveMain.fdFileNo"/></td>
					<td>
					 <xform:text property="fdNum" style="width:97%" showStatus="edit" className="inputsgl"/>
					</td>
				</tr>
			</table>
			<br/>
			<span>
			    <ui:button id="ok_id" text="${lfn:message('button.ok') }" order="2"  onclick="optSubmit();">
				</ui:button>&nbsp;&nbsp;
				<ui:button text="${lfn:message('button.cancel') }" order="2"  onclick="$dialog.hide();">
				</ui:button>
			</span>
		</div>
	</center>
	</template:replace>
</template:include>
