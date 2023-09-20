seajs.use(['lui/dialog', 'lang!sys-ui', 'lang!sys-attachment-borrow'], function(
  dialog,
  ui_lang,
  lang
) {
  var _klValidation = $KMSSValidation(document.forms['sysAttBorrowForm'])

  var validators = {
    compareNow: {
      error: '{name} ' + lang['sysAttBorrow.fdBorrowEffectiveTime.now.tip'],
      test: function(v) {
        if (!v) return true
        var now = new Date()
        var selected = Com_GetDate(v.replace(/-/g, '/'))
        return selected.getTime() >= now.getTime()
      }
    },
    checkDuration: {
      error: lang['sysAttBorrow.fdDuration.int.tip'],
      test: function(v, e, o) {
        var fdNum = $.trim(v)
        if (fdNum != '') {
          if (isNaN(fdNum)) {
            return false
          } else {
            if (Number(fdNum) < 0) {
              return false
            } else {
              var fdNum = parseInt(fdNum)
              document.getElementsByName('fdDuration')[0].value = fdNum
              return true
            }
          }
        } else {
          return false
        }
      }
    }
  }

  _klValidation.addValidators(validators)
  // 选择附件
  window.selectAtt = function() {
    dialog.iframe(
      '/sys/attachment/sys_att_borrow/sysAttMain_list.jsp',
      lang['sysAttBorrow.selectBorrowAtt'],
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
              var _frame = _dialog.frame[0]
              var contentWindow = $(_frame).find('iframe')[0].contentWindow
              if (contentWindow.onSubmit()) {
                var datas = contentWindow.onSubmit()
                if (datas.length > 0) {
                  $("input[name='fdAttId']").val(datas[0].id)
                  $("input[name='docSubject']").val(datas[0].name)

                  $KMSSValidation().validateElement(
                    $("input[name='docSubject']")[0]
                  )

                  setTimeout(function() {
                    _dialog.hide(value)
                  }, 200)
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

  window.submitForm = function(method, status) {
    var formObj = document.sysAttBorrowForm
    $('input[name="docStatus"]').val(status)
    Com_Submit(formObj, method)
  }
})
