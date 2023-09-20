<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">	
<script language="JavaScript">
	Com_IncludeFile("dialog.js");
</script>
<script language="JavaScript">
seajs.use(['lui/dialog'],function(dialog) {
	//提交验证新闻类别不可为空
	window.SNP_validateForm=function (){
	var fdCategoryId=document.getElementsByName("fdTemplateId")[0].value;
		if(fdCategoryId==null||fdCategoryId==""){
			dialog.alert("${lfn:message('sys-news:sysNewsPublishMain.checkCategory')}");
		return false;
		}
		return true;
	 }
	});
</script>
<form name="sysNewsMainForm" action="${LUI_ContextPath}/sys/news/sys_news_main/sysNewsMain.do" method="post" onsubmit="return SNP_validateForm();">
<hr>
<center>
<p class="txttitle">
<bean:message  bundle="sys-news" key="sysNewsPublishMain.pushTitle"/>
</p>
<table class="tb_normal" width=95%>
    <!-- 要推送的附件key-->
    <input type="hidden" name="attKey" value="${HtmlParam.attKey}"/>
    <!-- 要推送的正文附件key-->
    <input type="hidden" name="contentAttKey" value="${HtmlParam.contentAttKey}"/>
 	<input type="hidden" name="fdKey" value="${HtmlParam.fdKey}"/>
    <input type="hidden" name="fdModelId" value="${HtmlParam.fdModelId}"/>
	<input type="hidden" name="fdModelName" value="${HtmlParam.fdModelName}"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-news" key="sysNewsPublishCategory.fdCategoryId"/>
		</td>
		<td width=35%>			
			<input type="hidden" name="fdTemplateId"/>			
			<div class="inputselectsgl"  style="width:90%">
			   <div class="input">
			       <input type="text" name="fdTemplateName"  onclick="Dialog_SimpleCategoryForNewFile('com.landray.kmss.sys.news.model.SysNewsTemplate',
											'fdTemplateId::fdTemplateName',false,true,null);"/> 		
				</div>
				<div class="selectitem" id="tag_selectItem" onclick="Dialog_SimpleCategoryForNewFile('com.landray.kmss.sys.news.model.SysNewsTemplate',
								'fdTemplateId::fdTemplateName',false,true,null);">
				</div>
		    </div>		
			<span class="txtstrong">*</span>	
		 </td>
		<td class="td_normal_title" width=17%>
			<bean:message  bundle="sys-news" key="sysNewsPublishCategory.fdImportance"/>
		</td>
		<td width=33%> 
			<sunbor:enums  property="fdImportance" elementType="radio"  enumsType="sysNewsMain_fdImportance" bundle="sys-news"/>
	  </td> 
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-news" key="sysNewsPublishMain.push.rang"/>
		</td>
		<td width=85% colspan="3">
		  <xform:address textarea="true" showStatus="edit" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" idValue="${param.authReaderIds}" nameValue="${param.authReaderNames}" style="width:97%;height:90px;" ></xform:address>
		  <div class="description_txt">
			<bean:message  bundle="sys-news" key="sysNewsPublishMain.push.info"/>
		  </div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-news" key="sysNewsPublishMain.push.fdNotifyType"/>
		</td>
		<td colspan="3">
			<kmss:editNotifyType property="fdNotifyType" />
		</td>
	</tr>
</table>
<div style="padding-top:17px">
	    <ui:button  text="${lfn:message('button.update') }" onclick="Com_Submit(document.sysNewsMainForm, 'publishAttAdd');"></ui:button>
        <ui:button style="padding-left:5px" text="${lfn:message('button.close') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"></ui:button>
</div>
</center>
</form> 
</template:replace>
</template:include>