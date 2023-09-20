<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<c:set value="<%=IDGenerator.generateID()%>" var="uuid"></c:set>
<script type="text/javascript">

	var categoryId = "${JsParam.categoryId}";
	$(function(){
		if(!categoryId){
			var spa = "${param.spa}";

			if(spa!== null && spa !== undefined && spa == 'true'){
			 	seajs.use(['lui/topic'], function(topic) {
					topic.subscribe("spa.change.values",function(evt) {  //监听分类变化
						if (!evt)
							return;
			
						if (!evt.value) 
							return;
						if(evt.value.docCategory){
							categoryId = evt.value.docCategory;
						}
						
						
					});
					
			 	});
			
			}
		}

	})

	function changeRightCheckSelect_${uuid}() {
		var values = "";
		var selected;
		var listSelectedName = "${param.listSelectedName}" ? "${param.listSelectedName}" : "List_Selected";
		var select = document.getElementsByName(listSelectedName);
		var fdIds = "";
		for (var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				selected = true;
				fdIds = fdIds+select[i].value +";";
				//break;
			}
		}
		if (selected) {
			fdIds = fdIds.substring(0,fdIds.length-1);
			var url = '/sys/right/rightDocChange.do?method=docRightEdit&modelName=${JsParam.modelName}&categoryId='+categoryId+'&nodeType=${JsParam.nodeType}&authReaderNoteFlag=${JsParam.authReaderNoteFlag}';
			seajs
					.use(
							[ 'lui/dialog', 'lui/topic' ],
							function(dialog, topic) {

								dialog
										.iframe(
												url,
												"${lfn:message('sys-right:right.button.changeRightBatch')}",
												function(value) {

												}, {
													"width" : 800,
													"height" : 500,
													"topWin" : window.parent || window
												});
							});
			return;
		} else {
			seajs.use([ 'lui/dialog' ], function(dialog) {

				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}
// -->
</script>

<c:choose>
	<c:when test="${not empty param.spa && param.spa eq 'true' }">
	  
		<ui:button
			text="${ lfn:message('sys-right:right.button.changeRightBatch')}"
			id="changeRightBatch" order="4" onclick="changeRightCheckSelect_${uuid}()"
			cfg-auth="/sys/right/rightDocChange.do?method=docRightEdit&modelName=${param.modelName}&categoryId=!{docCategory}">
		</ui:button>

	</c:when>
	<c:otherwise>

		<kmss:auth
			requestURL="/sys/right/rightDocChange.do?method=docRightEdit&modelName=${param.modelName}&categoryId=${param.categoryId}"
			requestMethod="GET">
			<ui:button
				text="${ lfn:message('sys-right:right.button.changeRightBatch')}"
				id="changeRightBatch" order="4" onclick="changeRightCheckSelect_${uuid}()">
			</ui:button>
		</kmss:auth>


	</c:otherwise>
</c:choose>


