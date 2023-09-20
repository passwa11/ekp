define(function(require, exports, module) {
  var $ = require('lui/jquery')
  var topic = require('lui/topic')
  var BasePlugin = require('./base')

  //内部是否存在ui:iframe组件
  function hasIframeComponent(parentComponent) {
    var children = parentComponent.children
    if (!children || children.length == 0) {
      return false
    }
    for (var i = 0; i < children.length; i++) {
      var childComponent = children[i]
      if (getType(childComponent) === 'lui/iframe!Iframe') {
        return true
      }
      if (hasIframeComponent(childComponent)) {
        return true
      }
    }
    return false
  }

  //获取组件类型
  function getType(component) {
    var element = component.element
    if (element) {
      return element.attr('data-lui-type')
    }
    return null
  }

  var TabPanelPlugin = BasePlugin.extend({
    action: function(options) {
      var self = this
      var panelId = options.panelId || 'tabpanel',
        panel = LUI(panelId)
      if (!panel) {
        return
      }
      if (!panel.panelLoaded) {
        setTimeout(function() {
          self.action(options)
        }, 250)
      }
      var contentOptions = options.contents
      if (!$.isPlainObject(contentOptions)) {
        return
      }
      LUI.pageHide('_rIframe')
      var allContents = panel.contents,
        selectedIndex = null,
        selectedOption = null
      for (var index = 0; index < allContents.length; index++) {
        var content = allContents[index],
          __contentOption = contentOptions[content.id]
        if (__contentOption) {
          panel.props(index, { visible: true })
          if (__contentOption.selected) {
            selectedIndex = index
            selectedOption = __contentOption
          }
          if (__contentOption.title) {
            panel.props(index, { title: __contentOption.title })
          }
          if (__contentOption.route) {
            content.route = __contentOption.route
          }
        } else {
          panel.props(index, { visible: false })
        }

        content.isLazy = true
      }
      if (selectedOption) {
        var cri = $.extend({}, selectedOption.cri, options.$paramsCri)
        if (options.$isInit) {
          cri = $.extend({}, cri, options.$initCri)
        }

        // 是否进行初始化

        if (
          (!options.$isInit && allContents[selectedIndex].isLazy) ||
          hasIframeComponent(allContents[selectedIndex])
        ) {
          allContents[selectedIndex].isLazy = false
          //初始化且content内部没有ui:iframe则不发送事件（防止listview重复触发）
          topic.publish('spa.change.reset', {
            value: cri,
            target: self,
            $parent: allContents[selectedIndex]
          })
        }

        allContents[selectedIndex].isLazy = false

        setTimeout(function() {
          panel.setSelectedIndex(selectedIndex)
        }, 1)
      }
    },
    getCriValue: function(options) {
      var contentOptions = options.contents
      if (!$.isPlainObject(contentOptions)) {
        return {}
      }
      for (var key in contentOptions) {
        var contentOption = contentOptions[key]
        if (contentOption.selected) {
          return contentOption.cri
        }
      }
      return {}
    }
  })

  module.exports = TabPanelPlugin
})
