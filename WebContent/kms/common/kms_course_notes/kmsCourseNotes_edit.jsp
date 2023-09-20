<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
<template:replace name="content">		
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_course_notes/resource/style/notes_edit.css?s_cache=${LUI_Cache}" />
		
		<html:form action="/kms/common/kms_notes/kmsCourseNotes.do">
			<div class="lui_form_content_frame_clearfloat" >
				<div class="edit_notes"  > 
						<div class='notes_edit_content'  >
							<xform:textarea className='notes_edit_content' property="fdNotesContent" showStatus="edit" style="width:500px;height: 170px;" validators="maxLength(900)" />
						</div>
						<div class='notes_Tips' >
							<input type="checkbox" <c:if test="${kmsCourseNotesForm.isShare=='true'}"> checked="checked" </c:if> id="notes_share">
							<label for='notes_share' id="notes_label" onclick="label_chg(this)"></label><span><bean:message key="kmsCommon.notesTips" bundle="kms-common" /></span>
							<div class="com_bgcolor_d" style="display:none"></div>
						</div>
					</div>
				</div>
		</html:form>
		<script>
		seajs.use(['lui/jquery'],function($) {
			$(function() {
				if($('#notes_share').is(':checked')){
					var color = $(".com_bgcolor_d").css('background-color');
					var border = "1px solid "+color;
					$('#notes_label').css("background-color",color);

					$('#notes_label').css("border",border);
					
				}
				$('.lui_form_body').css("background","");
				$("#lui_validate_message").css("display","none");
			});

		});
		function label_chg(obj){
			
			
			var color = $(".com_bgcolor_d").css('background-color');
			var border = "1px solid "+color;
			//因为label for checkbox的属性更改在该函数之后，所以checked属性对应刚好相反
			if(!($("#notes_share").is(':checked'))){
				 $(obj).css("background-color",color);

				 $(obj).css("border",border);
			}else{
				 $(obj).css("background-color","white");

				 $(obj).css("border","1px solid #d5d5d5");
			}

			
			
		}
		</script>
</template:replace>
</template:include>
	