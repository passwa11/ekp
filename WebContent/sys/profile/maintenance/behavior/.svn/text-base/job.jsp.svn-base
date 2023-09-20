<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.job" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			function submitForm(dom, action){
				if(confirm('<bean:message key="sys.profile.behavior.confirm" bundle="sys-profile-behavior" />'+dom.value)){
					$('input[name="action"]').val(action);
					document.forms[0].submit();
				}
			}
	
			$(function(){
				menu_focus("1__job");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.job" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=job" method="post">
					<div style="margin:10px auto; width:900px;">
						<div style="text-align: right;">
							<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.job.refresh') }" style="padding:4px 15px; margin:5px 30px;" onclick="location.href = location.href;">
							<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.job.delete') }" style="padding:4px 15px; margin:5px 30px;" onclick="submitForm(this,'delete');">
							<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.job.reset') }" style="padding:4px 15px; margin:5px 30px;" onclick="submitForm(this,'reset');">
							<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.job.active') }" style="padding:4px 15px; margin:5px 30px;" onclick="submitForm(this,'active');">
						</div>
						<hr>
						<c:forEach items="${jobs}" var="job">
							<div style="width:290px; line-height: 40px; display:inline-block; font-size:14px; vertical-align:baseline;">
								<label><input name="jobs" value="${job}" type="checkbox"> ${job}</label>
							</div>
						</c:forEach>
						<input type="hidden" name="action">
					</div>
				</form>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>