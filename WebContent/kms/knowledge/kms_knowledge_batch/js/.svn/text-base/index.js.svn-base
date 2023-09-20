seajs.use(
  ['lui/jquery', 'lui/util/env', 'lui/dialog', 'lang!', 'lang!sys-ui'],
  function($, env, dialog, msg, ui_msg) {
    // 存储方式初始化处理
    $('input[name="configForm.fdType"]')[0].checked = true

    var currentCateName

    // 模块切换
    window.changeModule = function(val) {
      if (val) {
        $.getJSON(
          env.fn.formatUrl(
            '/kms/knowledge/kms_knowledge_batch/kmsKnowledgeModuleBatch.do?method=getCategoryName'
          ),
          {
            modelName: val
          }
        ).done(function(json) {
          if (json.cateName) {
            currentCateName = json.cateName
          }
        })
      }
    }

    // 启动后选中第一个模块
    $(function() {
      var inputs = $('input[name="configForm.fdModelName"]')
      if (inputs.length == 0) {
        return
      }

      inputs.eq(0).trigger('click')
    })

    // 存储类型设置
    window.onConfig = function(jsp) {
      dialog.iframe(jsp, '存储配置', null, {
        width: 550,
        height: 300,
        buttons: [
          {
            name: '保存配置',
            value: true,
            focus: true,
            fn: function(value, _dialog) {
              var _frame = _dialog.frame[0]
              var iframe = $(_frame).find('iframe')[0]
              var win = iframe.contentWindow
              var doc = iframe.contentDocument
              win.Com_Submit($(doc).find('form')[0], 'update')
            }
          },
          {
            name: ui_msg['ui.dialog.button.cancel'],
            styleClass: 'lui_toolbar_btn_gray',
            value: false,
            fn: function(value, _dialog) {
              _dialog.hide()
            }
          }
        ]
      })
    }

    // 分类选择
    window.selectCategory = function() {
      dialog.simpleCategory({
        modelName: currentCateName,
        authType: 2,
        idField: 'configForm.fdCategoryId',
        nameField: 'configForm.fdCategoryName',
        canClose: true,
        notNull: false,
        ___urlParam:{'fdTemplateType':'1,3'},
      })
    }

    // 加入队列操作
    var configForm = $('[name="kmsKnowledgeBatchLogForm"]')[0]
    $KMSSValidation(configForm)

    Com_Submit.ajaxSubmit = function(form) {
      var loading = dialog.loading()
      $.post(form.action, $(form).serialize())
        .done(function() {
          loading.hide()
          dialog.success(msg['return.optSuccess'])
        })
        .fail(function() {
          loading.hide()
          dialog.failure(msg['return.optFailure'])
        })
    }

    window.submit = function() {
      Com_Submit(configForm, 'addBatch');
    }
  }
)
