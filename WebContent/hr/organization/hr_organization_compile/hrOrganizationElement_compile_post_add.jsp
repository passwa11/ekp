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
		                <span>
		                ${lfn:message('hr-organization:hr.organization.info.setup.deptpost')}
		                
		                	：<span id='add_coompile'>${param.currCompileNum}</span>
		                ${lfn:message('hr-organization:hr.organization.info.emp.p')}，
		                ${lfn:message('hr-organization:hr.organization.info.setup.surplus')}
		               	 ：<span id='add_surcompile'>${param.surCompileNum gt 0?param.surCompileNum:0}</span>${lfn:message('hr-organization:hr.organization.info.emp.p')}
		               	 <span>，
		               	  ${lfn:message('hr-organization:hr.organization.info.tip.18')}
		               	</span></span></span>
		            </div>
		            <div class="ld-newDeparmentPosition-form">
		            	<table class="tb_simple lui_hr_tb_simple">
							<tr>
								<td class="tr_normal_title">
									${lfn:message('hr-organization:hr.organization.info.emp.post')}
								</td>
								<td>
									<xform:address propertyId="fdOrgPostsIds" propertyName="fdOrgPostsNames" orgType="ORG_TYPE_POST"
									 required="true" isHrAddress="true" showStatus="edit" onValueChange="getCompileNum" subject="岗位" style="width:80%;" />
								</td>
							</tr>
							<tr>
								<td class="tr_normal_title">
									${lfn:message('hr-organization:hrOrganizationPost.compile')} 
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
	//选择的岗位编制数
	var fdCompileNum= 0;
	//岗位是否限制人数
	var postIsLimitNum = false;
	//岗位在职人数
	var postsNum = 0;
	//目前组织下岗位编制数目
	var currDeptCompileNum = "${param.currCompileNum}"
	//剩余编制
	var surCompileNum = "${param.surCompileNum}";
	//组织是否限制岗位编制人数
	var fdIsLimitNum = "${param.fdIsLimitNum}";
	//岗位人数
	var parentId = '${param.fdId}';
	//  
	//var currIsLimitNum ="${param.currIsLimitNum}";
	if(fdIsLimitNum=='true'){
		$(".ld-concat-org-main-title").css("display", "block");
	}
	seajs.use(['lui/dialog','lui/jquery', 'lang!hr-organization'], function(dialog, jquery, lang){
		    window._submit = function(){
		    	var fdOrgPostsIds = document.getElementsByName("fdOrgPostsIds")[0];
		    	if(null == parentId || parentId == ""){
		    		dialog.alert(lang['hr.organization.info.tip.19']);
		    		return;
		    	}
		    	if ($KMSSValidation().validate()) {
		    		var postId = $("input[name=fdOrgPostsIds]").val();
		    		var postName = $("input[name=fdOrgPostsNames]").val();
		    		if(fdIsLimitNum=="true"){
		    			if(postIsLimitNum==false){
			    			dialog.alert(lang['hr.organization.info.tip.20']);
			    			return;
			    		}
		    		}
		    		return {
    					fdCompileNum:fdCompileNum,
    					fdIsLimitNum:fdIsLimitNum,
    					postId:postId,
    					postName:postName,
    					postsNum:postsNum,
    					parentId:'${param.fdId}'
    				};
		    	}
		    };
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
	        	 		if(postParentId){
							if(parentId!=postParentId){
								dialog.confirm({
									html:lang['hr.organization.info.tip.21'],
									callback:function(v){

									},
									width:400
								});
							}
						}
		    			if(fdIsLimitNum == 'true' && Number(fdCompileNum)>Number(surCompileNum)){
		    				$(".ld-concat-org-main-title").css("display", "block");
				    		$("#add_coompile").text(currDeptCompileNum);
				    		$("#add_surcompile").text(surCompileNum);
		    			}
	        		 }    
	          	});
		    }
	});
</script>