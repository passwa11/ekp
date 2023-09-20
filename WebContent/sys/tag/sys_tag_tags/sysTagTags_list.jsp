<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<script type="text/javascript">
function List_ConfirmSaveValidateTags(checkName){
	return List_CheckSelect(checkName) && confirm("<bean:message key="sysTagTags.confirmSaveValidateTags" bundle="sys-tag"/>");
}
function updateFromPriToPubInList(){
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=updateFromPriToPubInList';
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
		    	dialog.confirm("<bean:message key="sysTagTags.updateFromPriToPubInList" bundle="sys-tag"/>", function(flag, d) {
		    		if (flag) {
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
					}
				});
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}	
	
	
}
function saveInvalidateTags(){
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags';
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
function saveSpecialTags(){
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveSpecialTags';
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
		    	dialog.confirm("<bean:message key="sysTagTags.confirmSaveSpecialTags" bundle="sys-tag"/>", function(flag, d) {
		    		if (flag) {
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
					}
				});
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}	
}
function saveCommonTags() {
	
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags';
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
		    	dialog.confirm("<bean:message key="sysTagTags.confirmSaveCommonTags" bundle="sys-tag"/>", function(flag, d) {
		    		if (flag) {
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
					}
				});
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}
	
}
function delTag() {
	
	var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=deleteall';
	
	kms_TagDoc(url);
}
function kms_TagDoc(url) {
	
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
		    	dialog.confirm("<bean:message key="sysTagTags.deleteAll" bundle="sys-tag"/>", function(flag, d) {
		    		if (flag) {
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
											if(data.errorMessage) {
												dialog.alert("${lfn:message('sys-tag:sysTag.tags.info')}" );
												　												
											}
											
										} else {
								
											
											loading.hide();	
											dialog.success("${lfn:message('sys-tag:sysTag.tags.info.success')}")
											window.location.reload();
											
										}
									},
									error : function(error) {//删除失败
										
										loading.hide();	
										dialog.failure(
												"${lfn:message('sys-tag:sysTag.tags.info.fail')}");
									}
							}
						);
					}
				});
			});
} else {
		seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert("${lfn:message('page.noSelect')}");
				});
	}
}
</script>
<c:if test="${param.fdStatus == '1' || param.fdCategoryIds != null}">
	<c:import
		url="/sys/tag/sys_tag_tags/sysTagTags_move_button.jsp?fdCategoryId=${param.fdCategoryId}"
		charEncoding="UTF-8">
	</c:import>
	<c:import
		url="/sys/tag/sys_tag_tags/sysTagTags_merger_button.jsp?fdCategoryId=${param.fdCategoryId}"
		charEncoding="UTF-8">
		<c:param
			name="type"
			value="main" />
	</c:import>
</c:if>
<c:if test="${param.fdStatus == '0' || (param.fdStatus == null && param.fdCategoryIds == null)}">
	<c:import
		url="/sys/tag/sys_tag_tags/sysTagTags_validate_button.jsp?fdCategoryId=${param.fdCategoryId}"
		charEncoding="UTF-8">
	</c:import>
</c:if>
<html:form action="/sys/tag/sys_tag_tags/sysTagTags.do">
	<div id="optBarDiv">
		<c:if test="${param.fdStatus == '1' || param.fdCategoryIds != null}">
			<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
				<input type="button" value="<bean:message key="sysTagTags.button.saveInvalidateTags" bundle="sys-tag"/>"
					onclick="saveInvalidateTags();">
			</kmss:auth>
		</c:if>
		<!--	特殊标签显示置为普通标签	-->
		<c:if test="${param.fdIsSpecial == '1' || param.fdCategoryIds != null}">
			<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
				<input type="button" value="<bean:message key="sysTagTags.button.saveCommonTags" bundle="sys-tag"/>"
					onclick="saveCommonTags();">
			</kmss:auth>
		</c:if>
		<!--	所有标签显示置为普通标签	-->
		<c:if test="${param.fdIsSpecial == null && param.fdCategoryIds == null}">
			<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
				<input type="button" value="<bean:message key="sysTagTags.button.saveCommonTags" bundle="sys-tag"/>"
					onclick="saveCommonTags();">
			</kmss:auth>
		</c:if>
		<c:if test="${param.fdIsSpecial == '0' || (param.fdIsSpecial == null && param.fdCategoryIds == null)}">
			<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveSpecialTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
				<input type="button" value="<bean:message key="sysTagTags.button.saveSpecialTags" bundle="sys-tag"/>"
					onclick="saveSpecialTags();">
			</kmss:auth>
		</c:if>
		<c:if test="${param.fdStatus == null && param.fdCategoryIds == null}">
			<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
				<input type="button" value="<bean:message key="sysTagTags.button.saveInvalidateTags" bundle="sys-tag"/>"
					onclick="saveInvalidateTags();">
			</kmss:auth>
		</c:if>
		<c:if test="${param.fdIsPrivate == '1' }">
		<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=add&categoryId=${param.fdCategoryId}" requestMethod="GET">
			<input type="button" value="<bean:message key="button.add"/>"
					onclick="Com_OpenWindow('<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do" />?method=add&categoryId=${JsParam.fdCategoryId}&fdIsPrivate=${JsParam.fdIsPrivate}');">
		</kmss:auth>
		</c:if>
		<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=deleteall&fdCategoryId=${param.fdCategoryId}" requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="delTag();">
		</kmss:auth>
	<%-- 	<c:if test="${param.fdIsPrivate == '0'}"> --%>
		<%-- 	<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=updateFromPriToPubInList" requestMethod="GET"> --%>
		<%-- 		<input type="button" value="<bean:message  bundle="sys-tag" key="sysTagTags.button.updateFromPriToPub"/>" --%>
			<%-- 		onclick="updateFromPriToPubInList();"> --%>
		<%-- 	</kmss:auth> --%>
	<%-- 	</c:if> --%>
		<input  type="button" value="<bean:message key="button.search"/>" onclick="Com_OpenWindow('<c:url value="/sys/search/search.do?method=condition&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags"/>')">		
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
				<td width="40pt"><bean:message key="page.serial"/></td>
				<sunbor:column property="sysTagTags.fdName">
					<bean:message  bundle="sys-tag" key="sysTagTags.fdName"/>
				</sunbor:column>
				<td>
					<bean:message  bundle="sys-tag" key="sysTagTags.fdAlias"/>
				</td>
				<sunbor:column property="sysTagTags.fdCategorys.fdName">
					<bean:message  bundle="sys-tag" key="sysTagTags.fdCategoryId"/>
				</sunbor:column>
				<sunbor:column property="sysTagTags.fdStatus">
					<bean:message  bundle="sys-tag" key="sysTagTags.fdStatus"/>
				</sunbor:column>
				<sunbor:column property="sysTagTags.docCreator.fdName">
					<bean:message  bundle="sys-tag" key="sysTagTags.docCreatorId"/>
				</sunbor:column>
				<sunbor:column property="sysTagTags.docCreateTime">
					<bean:message  bundle="sys-tag" key="sysTagTags.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="sysTagTags.fdCountQuoteTimes">
					<bean:message  bundle="sys-tag" key="sysTagTags.fdQuoteTimes"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysTagTags" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do" />?method=view&fdId=${sysTagTags.fdId}&fdCategoryId=${HtmlParam.fdCategoryId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysTagTags.fdId}">
				</td>
				<td>${vstatus.index+1}</td>
				<td width="20%">
					<c:out value="${sysTagTags.fdName}" />
				</td>
				<td kmss_wordlength="25" >
					<kmss:joinListProperty value="${sysTagTags.hbmAlias}" properties="fdName" split=";" />
				</td>
				<td>
					<kmss:joinListProperty value="${sysTagTags.fdCategorys}" properties="fdName" split=";" />
				</td>
				<td>
					<sunbor:enumsShow value="${sysTagTags.fdStatus}" enumsType="sysTagTags_fdStatus" bundle="sys-tag"/>
				</td>
				<td>
					<c:out value="${sysTagTags.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysTagTags.docCreateTime}" type="datetime"/>
				</td>
				<td>
					<c:out value="${sysTagTags.fdCountQuoteTimes}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>