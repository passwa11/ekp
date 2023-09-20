seajs.use([
    'lui/topic',
	'lui/util/env', 
	'lui/jquery', 
	'lui/dialog',
	'lui/export/export',
	'sys/ui/extend/template/module/export',
	'theme!list',
    'lang!',
    'lang!kms-iso','lang!kms-iso-borrow'], function(
	topic,
	env,
	$,
	dialog,
	exportss,
    $export,
    list,
    lang,
    iso_lang, isoBorrowLang) {
  
  // 监听新建更新等成功后刷新
  topic.subscribe('successReloadPage', function() {
    topic.publish('list.refresh');
  });

  // 新建借阅
  window.add = function() {
      var values = [], selected, select = document
          .getElementsByName("List_Selected");
      for (var i = 0; i < select.length; i++) {
          if (select[i].checked) {
              values.push(select[i].value);
              selected = true;
          }
      }
      if (selected && values.length>0) {
          // 单选校验
          if(values.length>1){
              dialog.alert(iso_lang['kms.iso.borrow.single.tip']);
              return;
          }
          var fdDocId = values[0];
          // 判断是否选择了无需订阅的文档
          $.ajax({
              url : env.fn.formatUrl('/kms/iso/kms_iso_doc/kmsIsoDoc.do?method=checkDocCanBorrow'),
              type : 'POST',
              dataType : 'json',
              async : false,
              data : {
                  fdId: fdDocId
              },
              success : function(data, textStatus, xhr) {
                  if (data && data['flag'] == false) {
                      dialog.alert(data.msg);
                      return;
                  }

                  // 打开借阅页面
                  var url = env.fn.formatUrl('/kms/iso/kms_iso_borrow/kmsIsoBorrow.do?method=add&fdDocId='+fdDocId);
                  Com_OpenWindow(url);
              },
              error : function(xhr, textStatus, errorThrown) {
                  dialog.failure(lang['return.optFailure'],'#listview');
              }
          });
      }else{
          Com_OpenWindow(env.fn.formatUrl('/kms/iso/kms_iso_borrow/kmsIsoBorrow.do?method=add'));
      }
  };

  // 关闭借阅
  window.close = function() {
        var checked = $('input[name="List_Selected"]:checked');

        if (checked.length === 0) {
            dialog.alert(isoBorrowLang['kmsIsoBorrow.info.pleaseSelectNeedCloseBorrow']);
            return;
        }

        var loading = dialog.loading();

        var values = [];
        $.each(checked, function(index, item) {
          values.push($(item).val());
        });

        $.ajax({
              url: env.fn.formatUrl('/kms/iso/kms_iso_borrow/kmsIsoBorrow.do?method=close'),
              method: 'POST',
              dataType: 'json',
              data: $.param({ fdIds: values }, true)
        }).done(function() {
            loading.hide();
            dialog.success(isoBorrowLang['kmsIsoBorrow.info.closeBorrowSuccess']);
        }).fail(function() {
            loading.hide();
            dialog.failure(isoBorrowLang['kmsIsoBorrow.info.closeBorrowFailure']);
        });
  };
  
  window.exportExcel = function(id) {
	 //exportss.exportExcel(id);
      var url = env.fn.formatUrl("/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport",true);
      $export.listExport(url);
  };

});