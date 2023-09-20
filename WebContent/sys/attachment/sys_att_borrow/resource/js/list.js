seajs.use(['lui/topic', 'lui/util/env', 'lui/jquery', 'lui/dialog'], function(
  topic,
  env,
  $,
  dialog
) {
  // 监听新建更新等成功后刷新
  topic.subscribe('successReloadPage', function() {
    topic.publish('list.refresh')
  })

  // 新建借阅
  window.add = function() {
    Com_OpenWindow(
      env.fn.formatUrl(
        '/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=add'
      )
    )
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
        '/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=close'
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
})
