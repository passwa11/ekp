<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
		
<kmss:auth 
	requestURL="/sys/right/rightDocChange.do?method=docRightEdit&modelName=${param.fdModelName}&categoryId=${param.fdCategoryId}"
	requestMethod="GET">
		<script type="text/javascript">
		function changeRightCheckSelect() {
				var url = '/sys/right/rightDocChange.do?method=docRightEdit&modelName=${JsParam.fdModelName}&categoryId=${JsParam.fdCategoryId}&fdIds=${JsParam.fdModelId}';
				seajs
						.use(
								[ 'lui/dialog', 'lui/topic' ],
								function(dialog, topic) {
									dialog
											.iframe(
													url,
													"${lfn:message('sys-right:right.button.changeRight.view')}",
													function(value) {
													}, {
														"width" : 800,
														"height" : 500
													});
								});
		}
	</script>
	<c:set var="toolbarOrder" value="4"></c:set>
	<c:if test="${not empty param && not empty param.toolbarOrder}">
		<c:set var="toolbarOrder" value="${param.toolbarOrder}"></c:set>
	</c:if>
	<ui:button
		text="${ lfn:message('sys-right:right.button.changeRight.view')}"
		order="${toolbarOrder}" onclick="changeRightCheckSelect()">
	</ui:button>
</kmss:auth>	
		

