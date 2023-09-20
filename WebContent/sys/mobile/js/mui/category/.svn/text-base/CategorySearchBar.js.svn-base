/**
 *  都不知道在写什么，后悔没有重构父级搜索部件
 */
define([
  "dojo/_base/declare",
  "mui/search/SearchBar",
  "dojox/mobile/TransitionEvent",
  "mui/history/listener",
  "dojo/_base/lang"
], function(declare, SearchBar, TransitionEvent, listener, lang) {
  return declare("sys.category.CategorySearchBar", [SearchBar], {
    jumpToSearchUrl: false,
    needPrompt: false,
    showLayer: false,

    getViewId: function(view) {
      return view + "_" + this.key
    },

    _onfocus$: function() {
      this._onfocusCommon()
      new TransitionEvent(document.body, {
        moveTo: this.getViewId("searchView")
      }).dispatch()
    },

    // 取消
    _onCancel$: function() {
      this.inherited(arguments)
      new TransitionEvent(document.body, {
        moveTo: this.getViewId("defaultView")
      }).dispatch()
    },

    // 焦点
    _onfocus: function() {
      if (this.show) {
        return
      }
      this.show = true
      var forwardCallback = lang.hitch(this, function() {
        this._onfocus$()
      })

      var backCallback = lang.hitch(this, function() {
        this._onCancel$()
      })

      var listenerResult = listener.push({
        forwardCallback: forwardCallback,
        backCallback: backCallback
      })

      // 记录进入分类前的页面ID
      this.beforeSelectCateHistoryId = listenerResult.previousId
    }
  })
})
