seajs.use([
    'lui/topic',
	'lui/util/env', 
	'lui/jquery', 
	'lui/dialog',
	'kms/knowledge/resource/js/export',
	'theme!list',
    'lang!kms-knowledge'], function(
	topic,
	env,
	$,
	dialog,
	exportss,
    list,
    knowledge_lang) {
  
  // 监听新建更新等成功后刷新
  topic.subscribe('successReloadPage', function() {
    topic.publish('list.refresh')
  })

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
              dialog.alert(knowledge_lang['kms.knowledge.borrow.single.tip']);
              return;
          }
          var fdDocId = values[0];
          // 判断是否选择了无需订阅的文档
          $.ajax({
              url : env.fn.formatUrl('/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=checkDocPromission'),
              type : 'POST',
              dataType : 'json',
              async : false,
              data : {
                  fdId: fdDocId
              },
              success : function(data, textStatus, xhr) {
                  if (data && data['flag'] == true) {
                      // 提示无需借阅
                      if(data['docStatus'] == "30"){
                          dialog.alert(knowledge_lang['kms.knowledge.borrow.noneed.tip']);
                      }else{
                          dialog.alert(knowledge_lang['kms.knowledge.borrow.noneed.otherStatus.tip']);
                      }
                      return;
                  }

                  // 打开借阅页面
                  Com_OpenWindow(
                      env.fn.formatUrl(
                          '/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=add&fdDocId='+fdDocId
                      )
                  );
              },
              error : function(xhr, textStatus, errorThrown) {
                  dialog.failure(lang['return.optFailure'],'#listview');
              }
          });
      }else{
          Com_OpenWindow(
            env.fn.formatUrl(
              '/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=add'
            )
          )
      }
  }

  // 关闭借阅
  window.close = function() {
    var checked = $('input[name="List_Selected"]:checked')
    if (checked.length == 0) {
      dialog.alert('请选择需要关闭的借阅！')
      return
    }

    var loading = dialog.loading()

    var values = []
    $.each(checked, function(index, item) {
      values.push($(item).val())
    })

    $.ajax({
      url: env.fn.formatUrl(
        '/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=close'
      ),
      method: 'POST',
      dataType: 'json',
      data: $.param({ fdIds: values }, true)
    })
      .done(function() {
        loading.hide()
        dialog.success('关闭借阅成功！')
      })
      .fail(function() {
        loading.hide()
        dialog.failure('关闭借阅失败！')
      })
  }
  
  window.exportExcel = function(id) {
	 exportss.exportExcel(id);
  }


    window.exportExcel = function(id) {
        var url = env.fn.formatUrl("/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport",true);
        exportss.listExport(url,id);
    };
});