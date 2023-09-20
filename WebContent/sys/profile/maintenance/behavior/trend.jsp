<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.trend" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			$(function(){
				menu_focus("0__trend");
			});
		</script>
	</template:replace>
	<template:replace name="filter">
		<div class="beh-container-heading">
			<div class="criteria_frame">
				<span class="criteria_title"><bean:message key="sys.profile.behavior.time.slot" bundle="sys-profile-behavior" /></span>
				<span id="month_select"></span>
			</div>
		</div>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.trend" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
				<div style="width:100%;margin: 5px auto;text-align: center;">
					<h2>暂无数据！！！</h2>
				</div>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>