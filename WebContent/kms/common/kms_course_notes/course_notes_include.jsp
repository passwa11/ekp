<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

	<div class="notes_EditMain" id="notes_EditMain">
		<input type="hidden" name="fdModelId" value="${param.fdModelId}"/>
		<input type="hidden" name="fdModelName" value="${param.fdModelName}"/>
	
		<div class="km-note-share-textarea-wrap">
				  <iframe name="fdNotesContent" id="mainNotesIframe" class="notes_content_iframe" 
				  		 src="${ LUI_ContextPath}/kms/common/kms_course_notes/import/course_notes_content_area.jsp?iframeType=notes">
				  </iframe>		      <div class="km-note-share-textarea-footer">
				<input type="checkbox"  id="notes_share">
				<label for='notes_share' onclick="label_chg(this)"></label><span><bean:message key="kmsCommon.notesTips" bundle="kms-common" /></span>
		
					<input id="notes_button" class="km-note-share-submit com_bgcolor_d" type='button' value="${lfn:message('kms-common:kmsCommon.notesSubmit') }" onclick="notes_submitData('${param.fdModelId}','${param.fdModelName}')" />
		      </div>
   		</div>
		
	</div>
