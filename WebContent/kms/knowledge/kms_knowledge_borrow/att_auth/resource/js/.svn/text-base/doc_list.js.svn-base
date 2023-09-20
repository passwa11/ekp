seajs.use(['lui/jquery', 'lui/dialog','lui/util/env','lang!sys-ui','lang!kms-knowledge'], function($, dialog,env,lang,knowledge_lang) {
  window.selectItem = function(id, name, flag) {
	    if(flag && flag!='30'){
		  // 提示无需进行附件权限申请
          dialog.alert(knowledge_lang['kmsKnowledge.borrow.nopublish.tip']);
		  return false;
	    }
	  
		$.ajax({
			url : env.fn.formatUrl('/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrowAttAuth.do?method=checkIfHadDocAllAttAuth'),
			type : 'POST',
			dataType : 'json',
			async : true,
			data : {
				fdId: id
			},
			success : function(data, textStatus, xhr) {
				if (data && data['flag'] === false) {
					// 提示无需进行附件权限申请
					dialog.alert(data['msg']);
					return false;
				}

				var newdata = {
				  id: id,
				  name: name
				};
				if (LUI('selectedBean').hasVal(newdata)) {
				  LUI('selectedBean').removeVal(newdata);
				  return;
				}

				LUI('selectedBean').removeValAll();
				LUI('selectedBean').addVal(newdata);
			},
			error : function(xhr, textStatus, errorThrown) {
				dialog.failure(lang['return.optFailure'],'#listview');
			}
	   });
  };

  window.onSubmit = function() {
    var values = LUI('selectedBean').getValues();
    if (values.length < 1) {
      dialog.alert(knowledge_lang['kmsKnowledge.borrow.select.notEmpty']);
      return false;
    }

    var rtn = [];

    for (var i = 0; i < values.length; i++) {
      rtn.push(values[i]);
    }

    // console.log(rtn[0]);
    if (rtn.length > 0) {
	   return rtn;
    }
  };
});
