<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
var numFlag = false; //字数标识符
//提交笔记
function notes_submitData(fdModelId,fdModelName){
	var S_SaveUrl = Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=save";
	var isShare;
	if($("#notes_share").is(':checked')){
		 isShare = true ;
		}else{
			isShare = false;
		}
	var data = getUploadData();
	if(numFlag){
		//data['docCreateTime'] = (new Date()).format(Calendar_Lang.format.dataTime);
		//data['fdModelId'] = fdModelId;
		//data['fdModelName'] = fdModelName;
		data['isShare'] = isShare;
		data['docStatus'] = '30';
		data['docPraiseCount'] = '0';
		data['docEvalCount'] = '0';
		
		$.post(S_SaveUrl,data,notes_afterAddSubmit,'json');
		numFlag = false; //重置字数标识符
		return true;
			
	}
	
};

//笔记提交后
function notes_afterAddSubmit(data){
	if(data!=null && data.status==true){
		notesRecordChange("add","notes_chl_1","notes_chl_2"); //刷新列表
		notes_resetEditStatus(); //重置文本框
	}else{
		//TODO 提交报错
	}
}

//笔记文本框清空操作
function notes_resetEditStatus(){
	var contentObj = $('iframe[id="mainNotesIframe"]').contents().find("body");
	var contentVal = $.trim(contentObj[0].innerHTML); 
	//IE,包括IE11
	if(!!window.ActiveXObject || "ActiveXObject" in window){
		contentVal = contentVal.replace("<br>","");   
		contentVal = contentVal.replace(/<p>|<\/p>/ig,"");
		contentVal = contentVal.replace(/&nbsp;/ig,"");//过滤特殊字符
	}
	var chgVal = false;
	contentObj[0].innerHTML = "";
	contentObj.css({'color':'#8a8a8a'});
	
}

//提交成功发事件
function notesRecordChange(flag,cnl_1,cnl_2){
	seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
		dialog.success(notes_lang['notes_prompt_success_' + flag ],$("#notes_EditMain"));
		topic.channel(cnl_1).publish("list.refresh");
		topic.channel(cnl_2).publish("list.refresh");

	});
}

//刷新列表
function notesListChange(cnl_1,cnl_2){
	seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
		topic.channel(cnl_1).publish("list.refresh");
		topic.channel(cnl_2).publish("list.refresh");

	});
}
//刷新一个列表
function notesListChangeOne(cnl_1){alert(cnl_1);
	seajs.use(['lui/dialog','lui/topic'], function(dialog,topic) {
		topic.channel(cnl_1).publish("list.refresh");
	});
}

//得到提交的笔记数据
function getUploadData(){
	var dataObj = {};
	$("#notes_EditMain").find(":input").each(function(){
		var domObj = $(this);
		var eName = domObj.attr("name");
		if(eName!=null && eName!="" ){
			dataObj[eName] = domObj.val();
		}
	});
	//过滤表情
	var evalIframeBody = $('iframe[id="mainNotesIframe"]').contents().find("body");
	var fdNotesContent = filterExpress(evalIframeBody);
	dataObj['fdNotesContent'] = fdNotesContent;
	var contentVal = $.trim(fdNotesContent);//去除文本的前后空格
	if(contentVal!=null&&contentVal!=''){//非空校验
			numFlag = true;    
	}else{
			alert("${lfn:message('kms-common:kmsCommon.notesTips3')}");
		}
	
	return dataObj;
}

function filterExpress(contentBody){
	contentBody.find("img[type='face']").each(function(){
		var imgHtml = $(this)[0].outerHTML;
		var gifIndex = imgHtml.indexOf('.gif');
		var imgToGif = imgHtml.substring(0,gifIndex);
		var imgId = imgToGif.substring(imgToGif.lastIndexOf("/")+1);
		$(this)[0].outerHTML = "[face"+imgId+"]";
	});
	var evalContent = contentBody[0].innerText;
	//contentBody[0].innerHTML = "";
	return evalContent;
}

function notes_change(type){
	var listTyle = type;
	var color = $("#notes_button").css('background-color');
	var no_color = "#999";
	var border = "1px solid "+color;
	var no_border = "1px solid "+no_color;
	if(type == 'myNotes'){
		//document.getElementById('myNotes').style.color= '#2f9adb';
		//document.getElementById('shareNotes').style.color= 'black';
		 $("#myNotes").css("color",color);
		 $("#shareNotes").css("color",no_color);
		 $("#myNotes").addClass("actived");

		 $("#shareNotes").removeClass("actived");

		 $("#myNotes").css("border",border);
		 $("#shareNotes").css("border",no_border);

		document.getElementById('share_notes_list').style.display= 'none';
		document.getElementById('my_notes_list').style.display= 'block';

	}else{
		 $("#shareNotes").addClass("actived");

		 $("#myNotes").removeClass("actived");
		 $("#myNotes").css("color",no_color);
		 $("#shareNotes").css("color",color);
		 $("#myNotes").css("border",no_border);
		 $("#shareNotes").css("border",border);

		document.getElementById('my_notes_list').style.display= 'none';
		document.getElementById('share_notes_list').style.display= 'block';

	}
}


//取消共享
function cancel_notes(notesId){
	if(notesId !=null && notesId !=''){
		seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
			dialog.confirm(notes_lang['notes_prompt_cal_confirm'],function(value){
				if(value==true){
					$.ajax({
						url : Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=cancelShare",
						type : 'post',
						cache : false,
						data: "fdId=" +  notesId,
						success : function(data) { 
								notesRecordChange("cal","notes_chl_1","notes_chl_2");
							},
						error: function() {
							dialog.failure(notes_lang['notes_prompt_fail_cal']);
						}
					});
				}
			});
		});
	}
}

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
													notes_save(notesId,_dialog);
													
													
												}
											},
											
									     	{
												name : "${lfn:message('button.close')}",
												value : false,
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
function notes_save(notesId,notes_dialog){
	var domObj =LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('fdNotesContent')[0].value;
	var contentVal = $.trim(domObj);//去除文本的前后空格
	if(contentVal!=null&&contentVal!=''){//非空校验
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
									notesListChange("notes_chl_1","notes_chl_2");
									notes_dialog.hide();
							},
						error: function() {
							dialog.failure(notes_lang['notes_prompt_fail_upt']);
						}
					});
				}
			});
		});
	}else{
		alert("${lfn:message('kms-common:kmsCommon.notesTips3')}");
	}
}

//删除笔记
function notes_delete(notesId){
	seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
		dialog.confirm(notes_lang['notes_prompt_del_confirm'],function(value){
			if(value==true){
				$.ajax({
					url : Com_Parameter.ContextPath + "kms/common/kms_notes/kmsCourseNotes.do?method=delete",
					type : 'post',
					cache : false,
					data: {"fdId" :notesId},
					success : function(data) { 
								notesRecordChange("del","notes_chl_1","notes_chl_2");
							
						},
					error: function() {
						dialog.failure(notes_lang['notes_prompt_fail_del']);
					}
				});
			}
		});
	});
}

//查看笔记
function notes_view(notesId){
	if(notesId !=null && notesId !=''){
		seajs.use(['lui/dialog'],function(dialog){
			dialog.iframe("/kms/common/kms_notes/kmsCourseNotes.do?method=view&showType=2&fdId="+notesId,
							'${lfn:message("kms-common:kmsCommon.shareNotes")}',
							 null, 
							 {	
								width:590,
								height:600
								
							}
			); 
		});
	}
}

//查看中提交笔记点评
function eval_save(notesId){
	var domObj =LUI.$('#dialog_iframe').find('iframe')[0].contentDocument.getElementsByName('fdEvaluationContent')[0].value;
	var data = {};
	data['fdEvaluationContent'] = domObj;
	data['fdEvaluationTime'] = (new Date()).format(Calendar_Lang.format.dataTime);
	data['fdModelId'] = notesId;
	data['fdModelName'] = "com.landray.kmss.kms.common.model.KmsCourseNotes";
	data['fdEvaluationScore'] = "0";
	seajs.use(['sys/ui/js/dialog'], function(dialog,LUI) {
		dialog.confirm(notes_lang['notes_prompt_eval_confirm'],function(value){
			if(value==true){
				$.ajax({
					url : Com_Parameter.ContextPath + "sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=save",
					type : 'post',
					cache : false,
					data: data,
					success : function(data) { 
								dialog.success(notes_lang['notes_prompt_success_eval']);
								notesListChangeOne("notes_eval_chl");
								_dialog.hide();
						},
					error: function() {
						dialog.failure(notes_lang['notes_prompt_fail_eval']);
					}
				});
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
		var font_family = $(".department").css("font-family");
		$(".fdNotesContent .new_content").css("font-family",font_family);
		
		var color = $("#notes_button").css('background-color');
		var no_color = "#999";
		var border = "1px solid "+color;
		var no_border = "1px solid "+no_color;
		 $("#myNotes").css("color",color);
		 $("#shareNotes").css("color",no_color);
		 
		 $("#myNotes").css("border",border);
		 $("#shareNotes").css("border",no_border);
	});

});

function btnColor_chg(obj){
	var color = $("#notes_button").css('background-color');
	var no_color = "#999";
	var border = "1px solid "+color;
	
	if(!($(obj).hasClass("actived"))){
		 $(obj).css("color",color);

		 $(obj).css("border",border);
	}


	
}

function nobtnColor_chg(obj){
	var no_color = "#999";
	var no_border = "1px solid "+no_color;

	if(!($(obj).hasClass("actived"))){
		 $(obj).css("color",no_color);

		 $(obj).css("border",no_border);
	}

}

function label_chg(obj){
	
	
	var color = $("#notes_button").css('background-color');
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