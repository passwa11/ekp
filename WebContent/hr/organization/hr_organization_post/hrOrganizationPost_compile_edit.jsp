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
			<div class="ld-setting-compile">
	        <div class="ld-person-account-content">
	        	<c:import url="/hr/organization/hr_organization_compile/compile_statiscs.jsp" charEncoding="UTF-8">
	        		<c:param name="map" value="${map}"></c:param>
	        		<c:param name="fdType" value="post"></c:param>
	        	</c:import>
	            <div class="ld-setting-compile-form">
                    <div class="enableSwitch">
                        <h3>${lfn:message('hr-organization:hr.organization.info.setup.enable')}</h3>
                        <%-- 是否启用--%>
                        <html:hidden property="fdIsCompileOpen" /> 
						<label class="weui_switch">
							<span class="weui_switch_bd" id="weui_switch_compile_open">
								<input type="checkbox" ${'true' eq hrOrganizationPostForm.fdIsCompileOpen ? 'checked' : '' } />
								<span><dfn>${lfn:message('hr-organization:hr.organization.info.open')}</dfn></span>
								<small></small>
								<i>${lfn:message('hr-organization:hr.organization.info.close')}</i>
							</span>
							<span id="fdIsCompileOpenText"></span>
						</label>
                    </div>
                    <div class="prepareNumber">
                        <span>
                        	${lfn:message('hr-organization:hr.organization.info.setup.number')} 
                        </span>
                        <div>
                        	<html:hidden property="fdIsLimitNum" />
                            <label for=""><input type="radio" name="prepareNumber" value="1" onchange="setCompileNum();">
                            ${lfn:message('hr-organization:hr.organization.info.setup.unlimited.num')}
                            </label><p>
                            <label><input type="radio" name="prepareNumber" value="2" onchange="setCompileNum();">
                            ${lfn:message('hr-organization:hr.organization.info.setup.limited.num')}
                            </label>
                            <xform:text property="fdCompileNum" htmlElementProperties="id='fdCompileNum'" showStatus="edit"/></p>
                        </div>
                    </div>
                     <%-- <div class="enableSwitch" id="fdIsLimitNumId">
                        <h3>岗位满编设置</h3>
                        是否启用
                        <html:hidden property="fdIsFullCompile" /> 
						<label class="weui_switch">
							<span class="weui_switch_bd" id="weui_switch_full_compile">
								<input type="checkbox" ${'true' eq hrOrganizationPostForm.fdIsFullCompile ? 'checked' : '' } />
								<span><dfn>开</dfn></span>
								<small></small>
								<i>关</i>
							</span>
							<span id="fdIsFullCompileText"></span>
						</label>
						
						<script type="text/javascript">

						</script>
						<span class="ld-setting-compile-form-tips">启用后，当岗位人数满编时不允许发起入职审批与调人审批</span>
                    </div> --%>
	            </div>
	        </div>
	    </div>
	    <html:hidden property="fdId" />
	    <html:hidden property="method_GET" />
    </html:form>
	</template:replace>
</template:include>
<script type="text/javascript">
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
			
			$("#weui_switch_full_compile :checkbox").on("click", function() {
				var status = $(this).is(':checked');
				$("input[name=fdIsFullCompile]").val(status);
			});
		    
		    window.setCompileNum = function(){
		    	var prepareNumber = $("input[name='prepareNumber']:checked").val();
		    	if(prepareNumber == '1'){
		    		$("input[name='fdIsLimitNum']").val(false);
		    		$("#fdCompileNum").hide();
		    	}else{
		    		$("input[name='fdIsLimitNum']").val(true);
		    		$("#fdCompileNum").show();
		    	}
		    }
		    
		    window._submit = function(){
		    	var isSubmit = true;
		    	if($("input[name=prepareNumber]:checked").val()=="2"){
		    		if($("#fdCompileNum").val()<1){
		    			isSubmit=false;
		    		}
		    		if(Number($("#fdCompileNum").val())%1!=0){
		    			isSubmit=false;
		    		}
		    	}
		    	//不限人数或者关闭编制的时候关闭校验
		    	if($("#weui_switch_compile_open :checkbox").is(':checked')){
		    		if($("input[name=prepareNumber]:checked").val()=="1"){
		    			isSubmit=true;
		    		}
		    	}else{
		    		isSubmit=true;
		    	}
		    	if(isSubmit){
					var url = '${LUI_ContextPath}/hr/organization/hr_organization_post/hrOrganizationPost.do?method=update';
					$.post(url, $(document.hrOrganizationPostForm).serialize(),function(data){
					   if(data!=""){
						  	dialog.success('<bean:message key="return.optSuccess" />');
							setTimeout(function (){
								window.$dialog.hide("success");
							}, 1500);
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					})
		    	}else{
		    		dialog.failure("${lfn:message('hr-organization:hr.organization.info.tip.15')}")
		    	}
		    }
		    $(function(){
		    	var fdIsLimitNum = '${hrOrganizationPostForm.fdIsLimitNum}';
		    	if(fdIsLimitNum == 'true'){
		    		$("input[name='prepareNumber'][value='2']").attr("checked",true);  
		    		$("#fdCompileNum").show();
		    	}else{
		    		$("input[name='prepareNumber'][value='1']").attr("checked",true);  
		    		$("#fdCompileNum").hide();
		    	}
		    	changeCompileOpen('${hrOrganizationPostForm.fdIsCompileOpen}');
		    });

			function setFdIsCompileOpenText(status) {
				changeCompileOpen(status);
			}
			function changeCompileOpen(fdIsCompileOpen){
		    	if(fdIsCompileOpen == 'true' || fdIsCompileOpen == true){
		    		$(".prepareNumber").show();
					$("#fdIsLimitNumId").show();
		    	}else{
		    		$(".prepareNumber").hide();
					$("#fdIsLimitNumId").hide();
		    	}
		    }
			$("#weui_switch_compile_open :checkbox").on("click", function() {
				var status = $(this).is(':checked');
				$("input[name=fdIsCompileOpen]").val(status);
				setFdIsCompileOpenText(status);
			});
			    
	});
</script>
