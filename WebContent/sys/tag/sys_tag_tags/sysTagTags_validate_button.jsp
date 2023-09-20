<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
function saveValidateTags(){
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveValidateTags';
	if(!url || typeof url != "string")
		return;
	var values = [],
	     selected,
	     select = document.getElementsByName("List_Selected");
	for (var i = 0; i < select.length; i++) {
		if (select[i].checked) {
			values.push(select[i].value);
			selected = true;
		}
	}
	if (selected) {
		    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
				function(dialog, topic, $) {
		    	
		    		var data;
					var dataObj = $.extend({},data,{"List_Selected":values});
					
						
							var loading = dialog.loading();
							$.ajax({
									url : url,
									cache : false,
									data : $.param(dataObj,true),
									type : 'post',
									dataType :'json',
									success : function(data) {
										
										if (data.flag) {
											loading.hide();
											if(data.mesg) {
												dialog.success("${lfn:message('return.optSuccess')}" );
												window.location.reload();　												
											}
											
										} else {
								
											loading.hide();	
											dialog.success("${lfn:message('return.optFailure')}")
											window.location.reload();
											
										}
									},
									error : function(error) {
										loading.hide();	
										dialog.failure(
												"${lfn:message('return.optFailure')}");
									}
							}
						);
				
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}	
	
}
function validateTagCheckSelect() {
	//list页面选择标签生效
	if(${param.method == 'list'}){
		var values="";
		var selected;
		var select = document.getElementsByName("List_Selected");
		for(var i=0;i<select.length;i++) {
			if(select[i].checked){
				values+=select[i].value;
				values+=",";
				selected=true;
			}
		}
		if(selected) {
			values = values.substring(0,values.length-1);
			if(selected) {
				var url="<c:url value="sys/tag/sys_tag_tags/sysTagTags.do"/>";
				url+="?method=editValidateTag&fdMoveTagIds="+values+"&fdCategoryId=${JsParam.fdCategoryId}";
				var left = document.body.clientWidth/2;
				var top = document.body.clientHeight/2;
				Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+url),'500','250');
				return;
			}
		}
		seajs.use(['lui/dialog'],
				function(dialog) {
	   dialog.alert("<bean:message key="page.noSelect"/>");
	    return ;
	   })
		return ;
	}
	//view页面选择别名标签合并到当前标签
	if(${param.method == 'view'}){
		var fdMoveTagIds = "${JsParam.fdId}";
		if(fdMoveTagIds!=""){
			var url="<c:url value="sys/tag/sys_tag_tags/sysTagTags.do"/>";
			url+="?method=editValidateTag&fdCategoryId=${JsParam.fdCategoryId}&fdMoveTagIds="+fdMoveTagIds+"";
			var left = document.body.clientWidth/2;
			var top = document.body.clientHeight/2;
			Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+url),'500','250');
			return;
		}
	}	
}
</script>
<c:set var="isOptBar" value="true"></c:set>

<c:if test="${param.isOptBar!=null && param.isOptBar==false}">
	<c:set var="isOptBar" value="false"></c:set>
</c:if>
<c:if test="${isOptBar==true }">
<kmss:auth 
	requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveValidateTags&fdCategoryId=${param.fdCategoryId}" 
	requestMethod="GET">
		<div
			id="validateTag"
			style="display:none;">
			<input type="button"
				   value="<bean:message key="sysTagTags.button.saveValidateTags" bundle="sys-tag"/>"
				   onclick="saveValidateTags();">
		</div>
	<script>OptBar_AddOptBar("validateTag");</script>
</kmss:auth>
</c:if>
