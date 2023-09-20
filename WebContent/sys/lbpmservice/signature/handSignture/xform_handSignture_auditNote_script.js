/**
 * 表单手写签章
 */

function xform_handSignture_change(type,controlId,isNewAuditNote){
	var aSignture = $("[name='xform_auditNote_a_signture_"+ controlId +"']")[0];
	var aWord = $("[name='xform_auditNote_a_word_"+ controlId +"']")[0];
	var divSignture = $("[name='xform_auditNote_div_signture_"+ controlId +"']")[0];
	var divWord = $("[name='xform_auditNote_div_word_"+ controlId +"']").find("textarea[name^='textareaNote_']")[0];
	var commonUsagesAuditNote = "commonUsages_" + controlId + "_wrap";
	// type为0，转换为手写签批， 为1转换为文字签批
	if(type == 1){
		aSignture.style.display = "inline-block";
		aWord.style.display = "none";
		divSignture.style.display = "none";
		divWord.style.display = "block";
		// 隐藏常用审批意见
		$("div[name='" + commonUsagesAuditNote + "']").css("display","inline-block");
		//文字签批的时候需要清空手写的内容
		try{
			var iWebRevisionObject = $(divSignture).find("[name='iWebRevisionObject']")[0];
			iWebRevisionObject.Clear();	
		}catch(e){
			
		}
	}else if(type == 0){
		aSignture.style.display = "none";
		aWord.style.display = "inline-block";
		// 清空多行文本的内容
		divWord.value = "";
		document.getElementsByName("fdUsageContent")[0].value = "";
		if (typeof isNewAuditNote != 'undefined'){
			divSignture.style.display = "inline-block";
		}else{
			divSignture.style.display = "block";
		}
		divWord.style.display = "none";
		$("div[name='"+ commonUsagesAuditNote + "']").hide();
	}
}