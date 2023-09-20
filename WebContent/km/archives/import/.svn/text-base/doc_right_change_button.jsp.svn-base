<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic'],function($,dialog,topic) {
		window.fileChangeRightBatch = function() {
			var selected = [];
			$("input[name='List_Selected']:checked").each(function() {
				selected.push($(this).val());
			});
			if (selected.length == 0) {
				dialog.alert('${lfn:message("page.noSelect")}');
				return;
			}
			var url = "/km/archives/km_archives_main/kmArchivesMain_changeRight.jsp?selectedIds="+selected.join(";");
			dialog.iframe(url,"${lfn:message('km-archives:button.modifyFileRight.batch')}",function(value) {
				topic.publish('list.refresh');
			}, {"width" : 700,"height" : 400});
		};
	});
</script>

<c:choose>
	<c:when test="${not empty param.spa && param.spa eq 'true' }">
		<ui:button
			text="${ lfn:message('km-archives:button.modifyFile.batch')}"
			id="btnChangeRightBatch" order="4" onclick="fileChangeRightBatch()"
			cfg-map="{\"docTemplate\":\"criteria('docTemplate')\"}"
			cfg-auth="/sys/right/rightDocChange.do?method=docRightEdit&modelName=${param.modelName}&categoryId=!{docTemplate}">
		</ui:button>
	</c:when>
	<c:otherwise>
		<kmss:auth
			requestURL="/sys/right/rightDocChange.do?method=docRightEdit&modelName=${param.modelName}&categoryId=${param.categoryId}"
			requestMethod="GET">
			<ui:button
				text="${ lfn:message('km-archives:button.modifyFile.batch')}"
				id="btnChangeRightBatch" order="4" onclick="fileChangeRightBatch()">
			</ui:button>
		</kmss:auth>
	</c:otherwise>
</c:choose>


