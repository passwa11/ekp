<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil"%>
<c:set var="wpsoaassist" value="<%=SysAttWpsoaassistUtil.isEnable()%>"/>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>

<c:choose>
	<c:when test="${ param.fdAttType == 'office'}">
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_ocx.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${ param.fdKey }" />
			<c:param name="fdModelName" value="${ param.fdModelName }" />
			<c:param name="fdModelId" value="${ param.fdModelId }" />
			<c:param name="fdMulti" value="${ param.fdMulti }" />
			<c:param name="fdAttType" value="${ param.fdAttType }" />
			<c:param name="fdViewType" value="${ param.fdViewType }" />
			<c:param name="formBeanName" value="${param.formBeanName }" />	
			
			<c:param name="fdImgHtmlProperty" value="${param.fdImgHtmlProperty }" />
			<c:param name="fdShowMsg" value="${ param.fdShowMsg }" />
			<c:param name="hidePicName" value="${ param.hidePicName }" />
			<c:param name="showDefault" value="${ param.showDefault }" />
			<c:param name="buttonDiv" value="${ param.buttonDiv }" />
			<c:param name="fdOfficeType" value="${param.fdOfficeType}" />
			<c:param name="bookMarks" value="${param.bookMarks}" />
			<c:param name="width" value="${param.picWidth}" />
			<c:param name="height" value="${param.picHeight}" />
			<c:param name="proportion" value="${param.proportion}" />
			
			<c:param name="isTemplate" value="${ param.isTemplate }" />
			<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
			<c:param name="fdTemplateModelName" value="${param.fdTemplateModelName}" />
			<c:param name="fdTemplateKey" value="${param.fdTemplateKey}" />
			<c:param name="templateBeanName" value="${param.templateBeanName}"/>
			<c:param name="fdSupportLarge" value="${param.fdSupportLarge}"/>
		</c:import>
	</c:when>
	<c:when test="${ param.dtable == 'true'}">
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_dt_swf.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${ param.fdKey }" />
			<c:param name="fdModelName" value="${ param.fdModelName }" />
			<c:param name="fdModelId" value="${ param.fdModelId }" />
			<c:param name="fdMulti" value="${ param.fdMulti }" />
			<c:param name="fdAttType" value="${ param.fdAttType }" />
			<c:param name="fdViewType" value="${ param.fdViewType }" />
			<c:param name="fdRequired" value="${ param.fdRequired }" />
			<c:param name="formBeanName" value="${ param.formBeanName }" />	
			<c:param name="enabledFileType" value="${ param.enabledFileType }" />
			<c:param name="uploadUrl" value="${ param.uploadUrl }" />	
			
			<c:param name="fdImgHtmlProperty" value="${ param.fdImgHtmlProperty }" />
			<c:param name="fdShowMsg" value="${ param.fdShowMsg }" />
			<c:param name="hidePicName" value="${ param.hidePicName }" />
			<c:param name="showDefault" value="${ param.showDefault }" />
			<c:param name="buttonDiv" value="${ param.buttonDiv }" />
			<c:param name="fdOfficeType" value="${param.fdOfficeType}" />
			<c:param name="bookMarks" value="${param.bookMarks}" />
			<c:param name="width" value="${param.picWidth}" />
			<c:param name="height" value="${param.picHeight}" />
			<c:param name="proportion" value="${param.proportion}" />
			
			<c:param name="isTemplate" value="${ param.isTemplate }" />
			<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
			<c:param name="fdTemplateModelName" value="${param.fdTemplateModelName}" />
			<c:param name="fdTemplateKey" value="${param.fdTemplateKey}" />
			<c:param name="templateBeanName" value="${param.templateBeanName}" />
			<c:param name="fdSupportLarge" value="${param.fdSupportLarge}"/>
			
			<c:param name="extParam" value="${param.extParam}"/>
			<c:param name="fdLayoutType" value="${param.fdLayoutType}" />	
			<c:param name="fdPicContentWidth" value="${param.fdPicContentWidth}" />	
			<c:param name="fdPicContentHeight" value="${param.fdPicContentHeight}" />
			<c:param name="idx" value="${param.idx}" />	
			<%-- 不允许别人删除自己上传的附件 --%>
			<c:param name="otherCanNotDelete" value="${ param.otherCanNotDelete }" />
			<c:param name="allCanNotDelete" value="${ param.allCanNotDelete }" />
			<c:param  name="hideReplace"  value="${param.hideReplace}"/>
		</c:import>
	</c:when>
	<c:otherwise>
		<c:if test="${param.wpsExtAppModel eq 'kmImissive'}">
			<c:set var="wpsoaassist" value="true"/>
		</c:if>
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_swf.jsp" charEncoding="UTF-8">
			<c:param name="fdKey" value="${ param.fdKey }" />
			<c:param name="fdModelName" value="${ param.fdModelName }" />
			<c:param name="fdModelId" value="${ param.fdModelId }" />
			<c:param name="fdMulti" value="${ param.fdMulti }" />
			<c:param name="fdAttType" value="${ param.fdAttType }" />
			<c:param name="fdViewType" value="${ param.fdViewType }" />
			<c:param name="fdRequired" value="${ param.fdRequired }" />
			<c:param name="formBeanName" value="${ param.formBeanName }" />	
			<c:param name="enabledFileType" value="${ param.enabledFileType }" />	
			<c:param name="uploadUrl" value="${ param.uploadUrl }" />
			<c:param name="fileNumLimit" value="${ param.fileNumLimit }" />	
			<c:param name="disabledImageView" value="${JsParam.disabledImageView }" />	
			
			<c:param name="fdImgHtmlProperty" value="${ param.fdImgHtmlProperty }" />
			<c:param name="fdImgSizeLimit" value="${ param.fdImgSizeLimit }" />
			<c:param name="fdShowMsg" value="${ param.fdShowMsg }" />
			<c:param name="hidePicName" value="${ param.hidePicName }" />
			<c:param name="showDefault" value="${ param.showDefault }" />
			<c:param name="buttonDiv" value="${ param.buttonDiv }" />
			<c:param name="fdOfficeType" value="${param.fdOfficeType}" />
			<c:param name="bookMarks" value="${param.bookMarks}" />
			<c:param name="width" value="${param.picWidth}" />
			<c:param name="height" value="${param.picHeight}" />
			<c:param name="proportion" value="${param.proportion}" />
			<c:param name="uploadText" value="${param.uploadText}" />
			
			<c:param name="isTemplate" value="${ param.isTemplate }" />
			<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
			<c:param name="fdTemplateModelName" value="${param.fdTemplateModelName}" />
			<c:param name="fdTemplateKey" value="${param.fdTemplateKey}" />
			<c:param name="templateBeanName" value="${param.templateBeanName}" />
			<c:param name="fdSupportLarge" value="${param.fdSupportLarge}"/>
			
			<c:param name="extParam" value="${param.extParam}"/>
			<c:param name="fdLayoutType" value="${param.fdLayoutType}" />	
			<c:param name="fdPicContentWidth" value="${param.fdPicContentWidth}" />	
			<c:param name="fdPicContentHeight" value="${param.fdPicContentHeight}" />	
			<c:param name="fdLabel" value="${ param.fdLabel }" />
			<c:param name="fdGroup" value="${ param.fdGroup }" />
			<c:param name="addToPreview" value="${ param.addToPreview }" />
			<%-- 是否开启文件拖拽上传（默认开启） --%>
			<c:param name="supportDnd" value="${ param.supportDnd }" />
			<%-- 是否开启拖拽排序（默认开启） --%>
			<c:param name="supportDndSort" value="${ param.supportDndSort }" />
			<%-- 固定宽度 --%>
			<c:param name="fixedWidth" value="${ param.fixedWidth }" />
			<%-- 总宽度（可传入：90% 或 980px），默认：100% --%>
			<c:param name="totalWidth" value="${ param.totalWidth }" />
			<%-- 是否打印模式（或者通过全局设置：window.isPrintModel = true） --%>
			<c:param name="isPrintModel" value="${ param.isPrintModel }" />
			<%-- 是否允许删除（只针对单附件模式），默认允许删除 --%>
			<c:param name="showDelete" value="${ param.showDelete }" />
			<%-- 不允许别人删除自己上传的附件 --%>
			<c:param name="otherCanNotDelete" value="${ param.otherCanNotDelete }" />
			<c:param name="allCanNotDelete" value="${ param.allCanNotDelete }" />
			<c:param name="wpsoaassist" value="${wpsoaassist}" />
			<c:param name="wpsoaassistEmbed" value="${wpsoaassistEmbed}" />
			<c:param name="wpsExtAppModel" value="${param.wpsExtAppModel}" />
			<c:param name="cleardraft" value="${param.cleardraft}" />
			<c:param name="signtrue" value="${param.signtrue}" />
			<c:param name="newFlag" value="${param.newFlag}" />
			<c:param  name="saveRevisions"  value="${param.saveRevisions}"/>
			<c:param  name="forceRevisions"  value="${param.forceRevisions}"/>
			<%-- 是否允许阅读 --%>
			<c:param  name="canRead"  value="${param.canRead}"/>
			<%-- 是否允许编辑 --%>
			<c:param  name="canEdit"  value="${param.canEdit}"/>
			<%-- 是否允许打印--%>
			<c:param  name="canPrint"  value="${param.canPrint}"/>
			<%-- 单文件时，是否隐藏提示 --%>
			<c:param  name="hideTips"  value="${param.hideTips}"/>
			<%-- 单文件时，是否隐藏【替换】 --%>
			<c:param  name="hideReplace"  value="${param.hideReplace}"/>
			 <%-- 是否可以重新命名 --%>
			 <c:param  name="canChangeName"  value="${param.canChangeName}"/>
			  <%-- 自己定义文件宽度 --%>
			  <c:param  name="filenameWidth"  value="${param.filenameWidth}"/>
			<c:param  name="fileNameMax"  value="${param.fileNameMax}"/>
		</c:import>
	</c:otherwise>
</c:choose>
