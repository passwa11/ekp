<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<script>
Com_IncludeFile("dialog.js");
function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
//点评信息
//window.onload = function(){
//	parent.document.getElementById('commentContent').style.height=document.body.scrollHeight+100;
//};
</script>
<c:if test="${sysTagTagsForm.fdStatus == '1'}">
<c:import
		url="/sys/tag/sys_tag_tags/sysTagTags_move_button.jsp?fdCategoryId=${param.fdCategoryId}&fdId=${param.fdId}"
		charEncoding="UTF-8">
</c:import>
<c:import
	url="/sys/tag/sys_tag_tags/sysTagTags_merger_button.jsp?fdCategoryId=${param.fdCategoryId}&fdId=${param.fdId}"
	charEncoding="UTF-8">
	<c:param
		name="type"
		value="alias" />
</c:import>
<c:import
	url="/sys/tag/sys_tag_tags/sysTagTags_remove_button.jsp?fdCategoryId=${param.fdCategoryId}"
	charEncoding="UTF-8">
</c:import>
<c:import
	url="/sys/tag/sys_tag_tags/sysTagTags_reset_button.jsp?fdCategoryId=${param.fdCategoryId}&fdId=${param.fdId}"
	charEncoding="UTF-8">
</c:import>
</c:if>
<div id="optBarDiv">
	<!-- 点评 -->
	<!-- 
	<kmss:auth requestURL="/sys/tag/sys_tag_comment/sysTagComment.do?method=add" requestMethod="GET">
		<input class="btnopt" type="button" value="<bean:message key="sysTagComment.button" bundle="sys-tag"/>"
			   onclick="if(Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+'sys/tag/sys_tag_comment/sysTagComment.do?method=add&fdTagId=${JsParam.fdId}'),'600','300'))location.reload();">
	</kmss:auth>
	 -->
	<c:if test="${sysTagTagsForm.fdStatus == '0'}">
		<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveValidateTag&fdCategoryId=${param.fdCategoryId}" requestMethod="GET">
			<input type="button" value="<bean:message key="sysTagTags.button.saveValidateTags" bundle="sys-tag"/>"
				onclick="Com_OpenWindow('sysTagTags.do?method=saveValidateTag&fdId=${JsParam.fdId}&fdCategoryId=${param.fdCategoryId}','_self');">
		</kmss:auth>
	</c:if>
	<c:if test="${sysTagTagsForm.fdStatus == '1'}">
		<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTag&fdCategoryId=${param.fdCategoryId}" requestMethod="GET">
			<input type="button" value="<bean:message key="sysTagTags.button.saveInvalidateTags" bundle="sys-tag"/>"
				onclick="Com_OpenWindow('sysTagTags.do?method=saveInvalidateTag&fdId=${JsParam.fdId}&fdCategoryId=${param.fdCategoryId}','_self');">
		</kmss:auth>
	</c:if>
	<%-- <c:if test="${sysTagTagsForm.fdIsPrivate=='0' }"> 
		<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=updateFromPriToPub" requestMethod="GET">
			<input type="button" value="<bean:message  bundle="sys-tag" key="sysTagTags.button.updateFromPriToPub"/>"
				onclick="Com_OpenWindow('sysTagTags.do?method=updateFromPriToPub&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	</c:if>--%>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-tag" key="table.sysTagTags"/></p>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysTagTagsForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" colspan="4">
			<FONT size="5"><b><c:out value="${sysTagTagsForm.fdName}" /></b></FONT>
			&nbsp;&nbsp;
			<bean:message bundle="sys-tag" key="sysTagTags.fdAlias"/>:
			<c:out value="${sysTagTagsForm.fdAliasNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-tag" key="sysTagTags.fdCategoryId"/>
		</td><td width=35%>
			<c:out value="${sysTagTagsForm.fdCategoryName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdManagerId"/>
		</td><td width=35%>
			<c:out value="${sysTagTagsForm.fdCategoryManagerName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdIsSpecial"/>
		</td><td width=35%>
			<xform:radio property="fdIsSpecial" showStatus="">
				<xform:enumsDataSource enumsType="sysTagIsSpecial_YesOrNo" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagTags.fdStatus"/>
		</td><td width=35%>
			<sunbor:enumsShow value="${sysTagTagsForm.fdStatus}" enumsType="sysTagTags_fdStatus" bundle="sys-tag"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagTags.fdQuoteTimes"/>
		</td><td colspan=3>
			<c:out value="${sysTagTagsForm.fdCountQuoteTimes}" />
			<a  onclick="gotoQuote()" target="_blank" style="cursor:pointer;color:blue; text-align:center" >（查看标签引用详情）</a> 
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-tag" key="sysTagTags.docCreatorId"/>
		</td><td width=35%>
			<c:out value="${sysTagTagsForm.docCreatorName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagTags.docCreateTime"/>
		</td><td width=35%>
			<c:out value="${sysTagTagsForm.docCreateTime}" />
		</td>
	</tr>
</table>
<script>
	function gotoQuote(){
		//ie中url带中文跳转报错
		//每跳转一次会自动解码一次，所以一般传中文查询乱码问题 则需要对要传的参数进行二次编码   但是这里list_index.jsp中间还有import一层，
		//所以要再加一次编码，目的是最终进入url中不携带中文。。。。
		var name = "<c:out value='${sysTagTagsForm.fdName}'/>";
		name = htmlEncodeByRegExp(name);
		var fdName =encodeURIComponent(encodeURIComponent(encodeURIComponent(name))); 
		var url = "${LUI_ContextPath}"+"/sys/tag/sys_tag_main/sysTagMain_list_index.jsp?fdModelName=com.landray.kmss.sys.tag.model.SysTagMain&fdTagName="+fdName;
		window.open(url);                 
	}

	function htmlEncodeByRegExp(str){ 
       var s = "";
       if(str.length == 0) return "";
       s = str.replace(/&amp;/g,"&");
       s = s.replace(/&lt;/g,"<");
       s = s.replace(/&gt;/g,">");
       s = s.replace(/&nbsp;/g," ");
       s = s.replace(/&#39;/g,"\'");
       s = s.replace(/&quot;/g,"\"");
       s = s.replace(/&#034;/g,"\"");
       s = s.replace(/&#039;/g,"\'");
       return s; 
    } 
	
</script>
<!-- 点评信息 -->
<!--  
<c:import url="/sys/tag/sys_tag_comment/sysTagComment_view_list.jsp" charEncoding="UTF-8"/>
-->
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>