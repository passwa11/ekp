define([
  'dojo/_base/declare',
  'mui/util',
  'dojo/request',
  'dojo/_base/lang'
], function(declare, util, request, lang) {
  return declare('sys.attachment.log.api', null, {
    // 新增日志接口
    ADD_URL: '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=save',
    // 更新日志接口
    UPDATE_URL:
      '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=update',
    // 获取日志接口
    FIND_URL:
      '/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=viewByAttId',

    // 启用
    enable: false,
    // 支持
    support: true,
    // 日志主键
    fdLogId: null,

    isEnable: function() {
      return this.enable && this.support
    },

    // 根据附件主键获取当前用户日志信息
    find: function(fdAttId, fdType) {
      request
        .get(
          util.formatUrl(
            this.FIND_URL + '&fdAttId=' + fdAttId + '&fdType=' + this.type
          ),
          {
            handleAs: 'json'
          }
        )
        .response.then(
          lang.hitch(this, function(data) {
            if (data.status == 200) {
              var data = data.data
              this.enable = data.fdEnable
              this.findDone(data)
            }
          })
        )
    },

    // 新增附件日志
    add: function(fdAttId, fdParam) {
      if (!this.isEnable()) {
        return
      }
      request
        .post(util.formatUrl(this.ADD_URL), {
          data: { fdAttId: fdAttId, fdParam: fdParam, fdType: this.type },
          timeout: 30000,
          handleAs: 'json'
        })
        .response.then(
          lang.hitch(this, function(data) {
            if (data.status == 200) {
              this.addDone(data.data)
            }
          })
        )
    },

    // 更新指定附件日志相关参数
    update: function(fdId, fdParam) {
      if (!this.isEnable()) {
        return
      }
      request
        .post(util.formatUrl(this.UPDATE_URL), {
          data: { fdId: fdId, fdParam: fdParam },
          timeout: 30000,
          handleAs: 'json'
        })
        .response.then(
          lang.hitch(this, function(data) {
            if (data.status == 200) {
              this.updateDone(data.data)
            }
          })
        )
    },

    // 记录日志
    log: function(fdAttId, lastTime) {
      if (this.fdLogId) {
        this.update(this.fdLogId, lastTime)
      } else {
        this.add(fdAttId, lastTime)
      }
    },

    // 获取日志完成回调，供重写
    findDone: function(data) {
      if (data && data.fdId) {
        this.fdLogId = data.fdId
      }
    },

    // 更新日志完成回调，供重写
    updateDone: function() {},

    // 新增日志完成回调，供重写
    addDone: function(data) {
      if (data && data.fdId) {
        // 暂存主键，通过主键更新性能更好
        this.fdLogId = data.fdId
      }
    }
  })
})
