seajs.use(['lui/dialog', 'lang!sys-ui', 'lang!kms-knowledge-borrow'], function(
  dialog,
  ui_lang,
  lang
) {
var _validation = $KMSSValidation(document.forms['kmsKnowledgeBorrowForm']);
	
var validators = {
compareNow: {
  error: '{name} ' + lang['kmsKnowledgeBorrow.fdBorrowEffectiveTime.now.tip'],
  test: function(v) {
    if (!v) return true
    var now = new Date()
    var selected = Com_GetDate(v.replace(/-/g, '/'))
    return selected.getTime() >= now.getTime()
  }
},
checkDuration : {
	error : lang['kmsKnowledgeBorrow.fdDuration.int.tip'],
	test : function(v, e, o) {
		var fdDurationLimit = $('input[name="fdDurationLimit"]').val();	
		// 不限时长不校验
		if("true"==fdDurationLimit){
			return true;
		}
		
		var fdNum = $.trim(v)
		if (fdNum != '') {
			if (isNaN(fdNum)) {
				return false
			} else {
				if (Number(fdNum) <= 0) {
					return false
				} else {
					var fdNum = parseInt(fdNum)
					document
							.getElementsByName('fdDuration')[0].value = fdNum
						return true
					}
				}
			} else {
				return false
			}
		}
	}
};

_validation.addValidators(validators);

LUI.ready(function (){
	fdDurationLimitChange()
});

// 选择文档
window.selectDoc = function(curId,docSubject) {
  dialog.iframe(
	'/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow_select.jsp?curId='+curId+'&docSubject='+docSubject,
    lang['kmsKnowledgeBorrow.select.tip'],
    function() {},
    {
      width: 900,
      height: 620,
      buttons: [
        {
          name: ui_lang['ui.dialog.button.ok'],
          value: true,
          focus: true,
          fn: function(value, _dialog) {
            var _frame = _dialog.frame[0];
            var contentWindow = $(_frame).find('iframe')[0].contentWindow;
            if (contentWindow.onSubmit()) {
              var datas = contentWindow.onSubmit();
              if (datas.length > 0) {
				  _dialog.hide(value);

				  var method = $("input[name='method_GET']").val();
				  if(method == "edit"){
					  // 修改只许修改文档，不用另起流程
					  $("input[name='fdDocId']").val(datas[0].id);
					  $("input[name='docSubject']").val(datas[0].name);
				  }else{
					  var reloadUrl = Com_Parameter.ContextPath + "kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=add&fdDocId="+datas[0].id;
					  window.location.href = reloadUrl;
				  }
              }
            }
          }
        },
        {
          name: ui_lang['ui.dialog.button.cancel'],
          value: false,
          styleClass: 'lui_toolbar_btn_gray',
          fn: function(value, dialog) {
            dialog.hide(value)
          }
        }
      ]
    }
  )
}

window.fdDurationLimitChange = function(){
	var fdDurationLimit = $('input[name="fdDurationLimit"]').val();
	// 不限时长不校验
	if("true"==fdDurationLimit){
		$("#fdDurationArea").hide();
		$("input[name='_fdDurationLimit']").attr("checked", true);
        if ($('#fdDuration_td .validation-advice').length > 0){
            $('#fdDuration_td .validation-advice').hide();
        }
	}else{
		$("#fdDurationArea").show();
	}	
};

window.configDownloadChange = function(obj){
	var fdDownloadEnableOriginal = $('input[name="fdDownloadEnableOriginal"]').val();	
	var fdDownloadEnable = $('input[name="fdDownloadEnable"]').val();	
	if("true"==fdDownloadEnableOriginal && "false"==fdDownloadEnable){
		dialog.alert(lang['kmsKnowledgeBorrow.fdHasAuth.tip']);
		$('input[name="fdDownloadEnable"]').val("true");
		$('input[name="fdDownloadEnable"]').next().find("span[name='switchText']").html(" 开启");
		obj.checked=true;
	}
}

window.configCopyChange = function(obj){
	var fdCopyEnableOriginal = $('input[name="fdCopyEnableOriginal"]').val();	
	var fdCopyEnable = $('input[name="fdCopyEnable"]').val();	
	if("true"==fdCopyEnableOriginal && "false"==fdCopyEnable){
		dialog.alert(lang['kmsKnowledgeBorrow.fdHasAuth.tip']);
        $('input[name="fdCopyEnable"]').val("true");
        $('input[name="fdCopyEnable"]').next().find("span[name='switchText']").html(" 开启");
        obj.checked=true;
        return;
	}
}

window.configPrintChange = function(obj){
	var fdPrintEnableOriginal = $('input[name="fdPrintEnableOriginal"]').val();	
	var fdPrintEnable = $('input[name="fdPrintEnable"]').val();	
	if("true"==fdPrintEnableOriginal && "false"==fdPrintEnable){
		dialog.alert(lang['kmsKnowledgeBorrow.fdHasAuth.tip']);
        $('input[name="fdPrintEnable"]').val("true");
        $('input[name="fdPrintEnable"]').next().find("span[name='switchText']").html(" 开启");
        obj.checked=true;
        return;
	}
}

// 表单提交	
window.submitForm = function(method, status) {
    var formObj = document.kmsKnowledgeBorrowForm
	if(status){
		$('input[name="docStatus"]').val(status)
	}
    Com_Submit(formObj, method)
}
});