seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
  window.selectItem = function(id, name) {
    var data = {
      id: id,
      name: name
    }
    if (LUI('selectedBean').hasVal(data)) {
      LUI('selectedBean').removeVal(data)
      return
    }

    LUI('selectedBean').removeValAll()
    LUI('selectedBean').addVal(data)
  }

  window.onSubmit = function() {
    var values = LUI('selectedBean').getValues()
    if (values.length < 1) {
      dialog.alert('必须选择一个附件')
      return false
    }

    var rtn = []

    for (var i = 0; i < values.length; i++) {
      rtn.push(values[i])
    }

    if (rtn.length > 0) {
      return rtn
    }
  }
})
