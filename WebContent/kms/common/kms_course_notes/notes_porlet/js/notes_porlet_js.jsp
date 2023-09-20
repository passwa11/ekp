<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">

//编辑笔记
function edit_notes(notesId){
	if(notesId !=null && notesId !=''){
		seajs.use(['lui/dialog'],function(dialog){
			dialog.iframe("/kms/common/kms_notes/kmsCourseNotes.do?method=edit&fdId="+notesId,
							'${lfn:message("kms-common:kmsCommon.myNotes")}',
							 null, 
							 {	
								width:555,
								height:350,
								buttons:[
											{
												name : "${lfn:message('button.save')}",
												value : true,
												focus : true,
	
												fn : function(value,_dialog) {
													notes_save(notesId);
												}
											},
											
									     	{
												name : "${lfn:message('button.close')}",
												styleClass : 'lui_toolbar_btn_gray', 
												value : true,
												focus : true,
												fn : function(value, _dialog) {
													_dialog.hide();
												}
											}
											
											
										]
							}
			); 
		});
	}
}

//编辑中保存笔记
function notes_save(notesId){
	var domObj =LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('fdNotesContent')[0].value;
	var isShare = LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementById('notes_share').checked;
	var dataObj = {};
	dataObj['fdNotesContent'] = domObj;
	seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
		dialog.confirm(notes_lang['notes_prompt_upt_confirm'],function(value){
			if(value==true){
				$.ajax({
					url : Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=update",
					type : 'post',
					cache : false,
					data: {"fdId" :notesId,
							"fdNotesContent" :domObj,
							"isShare" : isShare},
					success : function(data) { 
								dialog.success(notes_lang['notes_prompt_success_upt']);
								location.reload(true);
						},
					error: function() {
						dialog.failure(notes_lang['notes_prompt_fail_upt']);
					}
				});
				
			}
		});
	});
	
}

//删除笔记
function notes_delete(notesId){
	seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
		dialog.confirm(notes_lang['notes_prompt_del_confirm'],function(value){
			if(value==true){
				Com_OpenWindow(
						'kmsCourseNotes.do?method=delete&fdId='+notesId,'_self');
			}
		});
	});
}

seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
	LUI.ready(function(){
		var _div = $(".fdNotesContent [name='rtf_fdNotesContent']").html();
		$(".fdNotesContent").prepend("<xmp class='new_content'></xmp>");
		$(".fdNotesContent .new_content").html(_div);
		$(".fdNotesContent [name='rtf_fdNotesContent']").remove();	
		var font_family = $(".notes_praise").css("font-family");
		$(".fdNotesContent .new_content").css("font-family",font_family);
	});

});


	
</script>