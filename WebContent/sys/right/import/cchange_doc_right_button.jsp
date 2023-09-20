<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<c:set var="hasAuth" value="false" scope="request"/>
<kmss:auth requestURL="/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=${JsParam.modelName}&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}&authReaderNoteFlag=${JsParam.authReaderNoteFlag}" requestMethod="GET">
	<c:set var="hasAuth" value="true" scope="request"/>
</kmss:auth>
<script type="text/javascript">
	function changeRightCheckSelect(cateId,nodeType) {
		var values = "";
		var selected;
		var select = document.getElementsByName("List_Selected");
		for ( var i = 0; i < select.length; i++) {
			if (select[i].checked) {
				selected = true;
				break;
			}
		}
		if (selected) {
			var url = '/sys/right/cchange_doc_right/cchange_doc_right.jsp?modelName=${JsParam.modelName}&categoryId='+cateId+'&nodeType='+nodeType+'&authReaderNoteFlag=${JsParam.authReaderNoteFlag}';
			seajs.use( [ 'lui/dialog','lui/topic' ], function(dialog,topic) {
				dialog.iframe(url,"${lfn:message('sys-right:right.button.changeRightBatch')}", function(value) {
				}, {
					"width" : 800,
					"height" : 500
				});
			});
			return;
		} else {
			seajs
					.use(
							[ 'lui/dialog' ],
							function(dialog) {
								dialog
										.alert("${lfn:message('page.noSelect')}");
							});
		}
	}
	
	LUI.ready(function(){
		//普通用户默认不显示按钮
		if("${hasAuth}"=="false"){
			if(LUI('docChangeRightBatch')){
	      	    LUI('Btntoolbar').removeButton(LUI('docChangeRightBatch'));
	      	    LUI('docChangeRightBatch').destroy();
	        }
		}
    });
	
</script>
	<ui:button id="docChangeRightBatch"
		text="${ lfn:message('sys-right:right.button.changeRightBatch')}"
		order="4" onclick="changeRightCheckSelect('${JsParam.categoryId}','${JsParam.nodeType}')">
	</ui:button>
