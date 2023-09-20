<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set value="<%=IDGenerator.generateID()%>" var="uuid"></c:set>
<c:choose>
	<c:when test="${not empty param.spa && param.spa eq 'true' }">

		<script type="text/javascript">
	
			function changeCateCheckSelect_${uuid}() {
				var values = "";
				var selected;
				var listSelectedName = "${param.listSelectedName}" ? "${param.listSelectedName}" : "List_Selected";
				var select = document.getElementsByName(listSelectedName);
				for ( var i = 0; i < select.length; i++) {
					if (select[i].checked) {
						selected = true;
						values+=select[i].value;
						values+=",";
					}
				}
				if (selected) {
					values = values.substring(0, values.length - 1);
					seajs.use(
							[ 'lui/dialog','lui/spa/Spa' ],
								function(dialog,Spa) {
									var url = '/sys/sc/cateChg.do?method=cateChgEdit&listSelectedName=' + listSelectedName + '&cateModelName=${JsParam.cateModelName}&modelName=${JsParam.modelName}&categoryId='+Spa.spa.getCriteriaValue('docTemplate')+'&docFkName=${JsParam.docFkName}&extProps=${JsParam.extProps}&authType=${JsParam.authType}&fdIds='+values;
					
									dialog 
											.iframe(
													url,
													"${ lfn:message('sys-simplecategory:sysSimpleCategory.chg.button') }",
													function() {
													}, {
														"width" : 600,
														"height" : 300
													});
								});
		
				} else {
					seajs.use( [ 'lui/dialog' ], function(dialog) {
						dialog.alert("${lfn:message('page.noSelect')}");
					});
				}
			}
		</script>

		<ui:button
			cfg-map="{\"docTemplate\":\"criteria('docTemplate')\"}"
			cfg-auth="/sys/sc/cateChg.do?method=cateChgEdit&cateModelName=${param.cateModelName}&categoryId=!{docTemplate}&modelName=${param.modelName}"
			text="${ lfn:message('sys-simplecategory:sysSimpleCategory.chg.button') }"
			onclick="changeCateCheckSelect_${uuid}();" order="4"></ui:button>

	</c:when>
	<c:otherwise>
		<kmss:auth
			requestURL="/sys/sc/cateChg.do?method=cateChgEdit&cateModelName=${param.cateModelName}&categoryId=${param.categoryId}&modelName=${param.modelName}"
			requestMethod="GET">

			<script type="text/javascript">
	
				function changeCateCheckSelect_${uuid}() {
					var values = "";
					var selected;
					var listSelectedName = "${param.listSelectedName}" ? "${param.listSelectedName}" : "List_Selected";
					var select = document.getElementsByName(listSelectedName);
					for ( var i = 0; i < select.length; i++) {
						if (select[i].checked) {
							selected = true;
							values+=select[i].value;
							values+=",";
						}
					}
					if (selected) {
						values = values.substring(0, values.length - 1);
						
						var url = '/sys/sc/cateChg.do?method=cateChgEdit&listSelectedName=' + listSelectedName + '&cateModelName=${JsParam.cateModelName}&modelName=${JsParam.modelName}&categoryId=${JsParam.categoryId}&docFkName=${JsParam.docFkName}&extProps=${JsParam.extProps}&authType=${JsParam.authType}&fdIds='+values;
						seajs
								.use(
										[ 'lui/dialog' ],
										function(dialog) {
											dialog 
													.iframe(
															url,
															"${ lfn:message('sys-simplecategory:sysSimpleCategory.chg.button') }",
															function() {
															}, {
																"width" : 600,
																"height" : 300
															});
										});
			
					} else {
						seajs.use( [ 'lui/dialog' ], function(dialog) {
							dialog.alert("${lfn:message('page.noSelect')}");
						});
					}
				}
			</script>

			<ui:button
				text="${ lfn:message('sys-simplecategory:sysSimpleCategory.chg.button') }"
				onclick="changeCateCheckSelect_${uuid}();" order="4"></ui:button>
		</kmss:auth>
	</c:otherwise>
</c:choose>

