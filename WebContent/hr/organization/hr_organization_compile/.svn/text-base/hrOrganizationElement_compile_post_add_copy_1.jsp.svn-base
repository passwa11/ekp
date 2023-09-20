<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/common.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/hrorg.css">
	</template:replace>
	<template:replace name="content">
		<script src="../resource/weui_switch.js"></script>
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<html:form action="/hr/organization/hr_organization_post/hrOrganizationPost.do">
			<div class="ld-newDeparmentPosition">
		        <div class="ld-newDeparmentPosition-main">
		            <div class="ld-concat-org-main-title" style="display: none;">
		                <i></i>
		                <span>部门内部岗位编制：<span id='add_coompile'></span>人，剩余可编制：<span id='add_surcompile'>0</span>人<span>，您可前往 <span style="color:#4285F4;cursor:pointer" onclick="$dialog.hide(null);">增加部门编制</span></span></span>
		            </div>
		            <div class="ld-newDeparmentPosition-form">
		            	<table class="tb_simple lui_hr_tb_simple">
							<tr>
								<td class="tr_normal_title">
									岗位
								</td>
								<td>
									<xform:address propertyId="fdOrgPostsIds" propertyName="fdOrgPostsNames" orgType="ORG_TYPE_POST"
									 required="true" isHrAddress="true" showStatus="edit" onValueChange="getCompileNum" subject="岗位" style="width:80%;" />
								</td>
							</tr>
							<tr>
								<td class="tr_normal_title">
									人员编制
								</td>
								<td class="compile_add_noboder">
									<xform:text property="fdCompileNum" showStatus="readOnly" style="width:80%"></xform:text>
									<html:hidden property="fdIsLimitNum"></html:hidden>
								</td>
							</tr>
						</table>
		            </div>
		        </div>
		    </div>
	    <html:hidden property="fdId" />
	    <html:hidden property="method_GET" />
    </html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	var _validation = $KMSSValidation();
	var fdCompileNum= 0;
	var postIsLimitNum = false;
	var postsNum = 0;
	var currDeptCompileNum = "${param.currCompileNum}"
	var surCompileNum = "${param.surCompileNum}"; //剩余人数
	var fdIsLimitNum = "${param.fdIsLimitNum}";
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			var parentId = '${param.fdId}';
		    window._submit = function(){
		    	 //当前部门编制人数
		    	var currIsLimitNum ="${param.currIsLimitNum}";
		    	if(currIsLimitNum == "false" && fdIsLimitNum == "true"){
		    		$(".ld-concat-org-main-title").css("display", "block");
		    		return;
		    	}
		    	var fdOrgPostsIds = document.getElementsByName("fdOrgPostsIds")[0];
		    	if(null == parentId || parentId == ""){
		    		dialog.alert("上级部门不能为空！");
		    		return;
		    	}
		    	if ($KMSSValidation().validate()) {
		    		var postId = $("input[name=fdOrgPostsIds]").val();
		    		var postName = $("input[name=fdOrgPostsNames]").val();
		    		if(postIsLimitNum){
		    			
		    			return {
		    					fdCompileNum:fdCompileNum,
		    					fdIsLimitNum:fdIsLimitNum,
		    					postId:postId,
		    					postName:postName,
		    					postsNum:postsNum,
		    					parentId:'${param.fdId}'
		    				};
		    		}
		    		else{
		    			dialog.alert("请选择限制人数编制的岗位");
		    		}
		    		
			    	/* $.ajax({     
		        	     type:"post",   
		        	     url:"${KMSS_Parameter_ContextPath}hr/organization/hr_organization_post/hrOrganizationPost.do?method=setPostParent",    
		        	     data:{fdOrgPostsIds:fdOrgPostsIds.value, parentId:parentId},    
		        	     async:false,    //用同步方式 
		        	     success:function(data){
		        	    	 console.log(data);
		        		 }    
		          	}); */
		    	}
		    }
		    
		    window.getCompileNum = function(){
		    	var fdOrgPostsIds = document.getElementsByName("fdOrgPostsIds")[0];
		    	$.ajax({     
	        	     type:"post",   
	        	     url:"${KMSS_Parameter_ContextPath}hr/organization/hr_organization_post/hrOrganizationPost.do?method=getCompileNum",    
	        	     data:{fdOrgPostsIds:fdOrgPostsIds.value},    
	        	     async:false,    //用同步方式 
	        	     success:function(data){
	        	    	 fdCompileNum= data.fdCompileNum;
	        	    	 postIsLimitNum = data.fdIsLimitNum=="true"?true:false;
	        	    	 postsNum = data['postsNum'];
	        	    	 var postParentId=data['parentId'];
	        	 	   	$('input[name="fdCompileNum"]').val(data.fdCompileNum);
	        	 		$('input[name="fdIsLimitNum"]').val(data.fdIsLimitNum);
	        	 		console.log(parentId,postParentId);
	        	 		if(parentId!=postParentId){
	        	 			dialog.confirm({
	        	 				html:"该岗位编制已经在别的组织下，是否覆盖",
	        	 				callback:function(v){
	        	 					
	        	 				},
	        	 				width:400
	        	 			});
	        	 		}
		    			if(fdIsLimitNum == 'true' && fdCompileNum>Number(surCompileNum)){
		    				$(".ld-concat-org-main-title").css("display", "block");
				    		$("#add_coompile").text(currDeptCompileNum);
				    		$("#add_surcompile").text(surCompileNum);
		    			}
	        		 }    
	          	});
		    }
	});
</script>