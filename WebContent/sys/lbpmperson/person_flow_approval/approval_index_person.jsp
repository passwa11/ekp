<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
  <script type="text/javascript">
			seajs.use(['theme!form']);	
		</script>
			<jsp:include page="/sys/lbpmperson/person_flow_approval/approval_index_common.jsp"></jsp:include>
</template:replace>
</template:include>