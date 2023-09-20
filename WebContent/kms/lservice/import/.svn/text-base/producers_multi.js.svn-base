define(function(require, exports, module) {
  var Class = require('lui/Class')
  var lang = require('lang!sys-ui')
  var lservice_lang = require('lang!kms-lservice')
  var dialog = require('lui/dialog')
  var $ = require('lui/jquery')
  var env = require('lui/util/env')

  var producers = new Class.create(
    {},
    {
      producers: {},

      initProducer: function(modelName, url, title, customerName, customerId) {
        var self = this

        this.producers = {};
        
        this.producers[modelName] = {
          modelName: modelName,
          customerId: customerId,
          customerName: customerName,
          url: url,
          title: title,
          iframeAndSubmit: self.iframeAndSubmit,
          del: self.del
        }
      },

      get: function(modelName) {
        if (modelName) return this.producers[modelName]

        return this.producers
      },

      // args{
      // callback:回调函数；fdId:主键，一般用于编辑使用；isDoing:是否进行中；others:其他传输数据
      // }
      // hw为高宽对象
      iframeAndSubmit: function(title, models, args, hw) {
        var url

        if (!args) return

        var self = this
        var fdId = args.fdId
        var fdModelId = args.fdModelId

        if (!fdModelId) fdModelId = models.modelId

        if (fdId) url = Com_SetUrlParameter(this.url, 'method', 'edit')
        else url = Com_SetUrlParameter(this.url, 'method', 'add')

        if (args.others)
          url = Com_SetUrlParameter(
            url,
            'others',
            encodeURIComponent(JSON.stringify(args.others))
          )

        // 已经有人参加，处于进行中
        if (args.isDoing)
          url = Com_SetUrlParameter(url, 'isDoing', args.isDoing)

        url = Com_SetUrlParameter(url, 'fdId', fdId)

        url = Com_SetUrlParameter(url, 'fdModelName', models.modelName)

        url = Com_SetUrlParameter(url, 'fdModelId', fdModelId)

        dialog.iframe(url, title, null, {
          width: (hw && hw.width) || 550,
          height: (hw && hw.height) || 300,
          buttons: [
            {
              name: fdId
                ? lservice_lang['lserviceProducer.edit.btn']
                : lang['ui.dialog.button.ok'],
              value: true,
              focus: true,
              fn: function(value, _dialog) {
                var _frame = _dialog.frame[0]
                var iframe = $(_frame).find('iframe')[0]
                var win = iframe.contentWindow
                var doc = iframe.contentDocument

                var index = 0
                win.Com_Submit.ajaxSubmit = function(form) {
                  var datas = $(form).serializeArray()

                  datas.push({
                    name: 'fdCustomerName',
                    value: models.modelName
                  })

                  datas.push({
                    name: 'fdCustomerId',
                    value: models.modelId
                  })

                  $.ajax({
                    url: form.action,
                    type: 'POST',
                    dataType: 'json',
                    data: $.param(datas),
                    success: function(data, textStatus, xhr) {
                      var o = {}
                      $.each(datas, function() {
                        if (o[this.name]) {
                          if (!o[this.name].push) {
                            o[this.name] = [o[this.name]]
                          }
                          o[this.name].push(this.value || '')
                        } else {
                          o[this.name] = this.value || ''
                        }
                      })

                      if (args.callback)
                        args.callback(
                          {
                            modelName: self.modelName,
                            modelId: data.id ? data.id : args.fdId,
                            title: self.title
                          },
                          o
                        )

                      if (index < length - 1) {
                        index++
                      } else {
                        _dialog.hide()
                      }
                    },
                    error: function(xhr, textStatus, errorThrown) {
                      dialog.failure(lang['ui.dialog.operation.failure'])
                    }
                  })
                }
                var length = $(doc).find('form').length
                for (var i = 0; i < length; i++) {
                  win.Com_Parameter.isSubmit = false
                  win.Com_Submit(
                    $(doc).find('form')[i],
                    fdId ? 'update' : 'save'
                  )
                }
              }
            },
            {
              name: lang['ui.dialog.button.cancel'],
              styleClass: 'lui_toolbar_btn_gray',
              value: false,
              fn: function(value, _dialog) {
                _dialog.hide()
              }
            }
          ]
        })
      },

      /**
       * 删除操作
       */
      del: function(id) {
        if (!id) return

        var customerName = this.customerName
        var customerId = this.customerId

        var url = Com_SetUrlParameter(this.url, 'method', 'delete')
        url = Com_SetUrlParameter(url, 'fdModelName', customerName)
        url = Com_SetUrlParameter(url, 'fdModelId', customerId)

        url = Com_SetUrlParameter(url, 'fdId', id)

        $.ajax({
          url: env.fn.formatUrl(url),
          type: 'get',
          dataType: 'json',
          success: function(data, textStatus, xhr) {}
        })
      },
      deleteRefs: function(config) {
        // 新增引用模块检测
        $.ajax({
          url: env.fn.formatUrl('/kms/lservice/Lservice.do?method=refs'),
          data: {
            fdModelName: config.producerName,
            fdId: config.modelId
          },
          type: 'post',
          cache: false,
          success: function(data) {
            // 没有引用
            if (!data || !data.datas || data.datas.length == 0) {
              Com_Delete_Get(env.fn.formatUrl(config.url), config.modelName)
              return
            }

            dialog.iframe(
              '/kms/lservice/refs_list.jsp?fdId=' +
                config.modelId +
                '&fdModelName=' +
                config.producerName,
              config.title,
              null,
              {
                width: 720,
                height: 300
              }
            )
          }
        })
      }
    }
  )

  module.exports = producers
})
