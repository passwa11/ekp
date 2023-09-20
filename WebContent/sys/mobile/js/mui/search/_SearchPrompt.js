define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dijit/_WidgetBase",
  "dojo/json",
  "dojo/topic",
  "dojo/dom-construct",
  "dojo/dom-style",
  "mui/util",
  "dojo/touch",
  "mui/i18n/i18n!sys-mobile",
  "dojo/request",
  "./_SearchPromptList",
  "dojo/dom-attr",
  "mui/i18n/i18n!sys-mobile:mui.mobile.sysFtSearch"
], function(
  declare,
  array,
  WidgetBase,
  JsonFun,
  topic,
  domConstruct,
  domStyle,
  util,
  touch,
  Msg,
  request,
  _SearchPromptList,
  domAttr,
  msg
) {
  return declare("mui.search._SearchPrompt", [WidgetBase, _SearchPromptList], {
    _storageKey: "_muiSearchKeywords",

    _storageItem: [],

    isNeeAnimation: true,

    isShow: false,

    forPage: false,

    hotUrl:
      "/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=hot",

    prefix: "",

    langSetting: {
      clearRecord: Msg["mui.search.clearrecord"],
      hot: msg["mui.mobile.sysFtSearch.hot"],
      history: msg["mui.mobile.sysFtSearch.history"]
    },

    buildRendering: function() {
      this.domNode = this.containerNode =
        this.srcNodeRef || domConstruct.create("div", {})
      this.inherited(arguments)

      this.contentNode = domConstruct.create("div", {}, this.domNode)
      // 热门推荐
      this.hotRecord = domConstruct.create(
        "div",
        {
          className: "muiSearchHotRecord"
        },
        this.contentNode
      )

      this.drawHot()

      // 历史搜索
      this.histroyRecord = domConstruct.create(
        "div",
        {
          className: "muiSearchRecord"
        },
        this.contentNode
      )

      this.drawRecord()

      this.show()
    },

    drawRecord: function() {
      var titleNode = domConstruct.create("div", null, this.histroyRecord)
      domConstruct.create(
        "h3",
        {
          innerHTML: this.langSetting.history
        },
        titleNode
      )
      var clearNode = domConstruct.create(
        "i",
        {
          className: "fontmuis muis-trash-can",
          innerHTML: " "+msg["mui.mobile.sysFtSearch.clean"]
        },
        titleNode
      )
      this.connect(clearNode, "onclick", "_clearRecords")
      this.ulNode = domConstruct.create("ul", null, this.histroyRecord)
      this._drawRecord()
    },

    startup: function() {
      this.inherited(arguments)
      this.subscribe("/mui/search/submit", "_onSubmit")
    },

    postCreate: function() {
      this.inherited(arguments)
      this.subscribe("/mui/search/submit", "_addRecord")
      this.subscribe("/mui/search/cancel", "_onCancel")
      this.subscribe("/mui/search/onClear", "_onClear")

      util.disableTouch(this.domNode, touch.move)
    },

    _onCancel: function(srcObj) {
      if (srcObj != this.parent) {
        return
      }

      if (!this.forPage) {
        this.hide()
      }
      this.showPanel()
      this.inherited(arguments)
    },

    _onSubmit: function(srcObj) {
      if (srcObj != this.parent) {
        return
      }
      this.show()
      this.hidePanel()
      this.inherited(arguments)
    },

    _onClear: function(srcObj) {
      if (srcObj != this.parent) {
        return
      }
      this.showPanel()
      this.inherited(arguments)
    },

    // 隐藏最热历史
    hidePanel: function() {
      domStyle.set(this.contentNode, {
        display: "none"
      })
    },

    // 显示最热历史
    showPanel: function() {
      domStyle.set(this.contentNode, {
        display: "block"
      })
    },

    // 显示最近搜索
    showRecord: function() {
      domStyle.set(this.histroyRecord, {
        display: "block"
      })
    },

    // 隐藏最近搜索
    hideRecord: function() {
      domStyle.set(this.histroyRecord, {
        display: "none"
      })
    },

    // 显示面板
    show: function() {
      domStyle.set(this.containerNode, {
        display: "block"
      })
    },

    // 隐藏面板
    hide: function(flag) {
      domStyle.set(this.containerNode, {
        display: "none"
      })

      if (flag) {
        return
      }
    },

    getStoreKey: function() {
      return this.prefix + this._storageKey
    },

    // 最近搜索
    _drawRecord: function() {
      if (window.localStorage) {
        var storeKey = this.getStoreKey()
        var str = window.localStorage.getItem(storeKey)

        if (!str) {
          str = "[]"
        }

        this._storageItem = JsonFun.parse(str)
        // 存在历史搜索
        if (this._storageItem.length > 0) {
          domConstruct.empty(this.ulNode)

          for (var i = 0; i < this._storageItem.length; i++) {
            var record = this._storageItem[i]
            if (!record) {
              continue
            }
            record = decodeURIComponent(record)
            record = util.formatText(record)

            // 搜索历史项
            var rItem = domConstruct.create("li", null, this.ulNode)
            var textItem = domConstruct.create(
              "span",
              {className: "muiSearchRText", innerHTML: record},
              rItem
            )

            // 单项清除图标
            var iconItem = domConstruct.create(
              "icon",
              {className: "fontmuis muis-delete", "data-index": i},
              rItem
            )

            this.connect(textItem, "onclick", "_selectRecord")
            this.connect(iconItem, "onclick", "_clearRecord")
          }
          this.showRecord()
        } else {
          this.hideRecord()
        }
      }
    },

    _addRecord: function(srcObj) {
      if (srcObj != this.parent) {
        return
      }
      if (window.localStorage) {
        var keyword = decodeURIComponent(srcObj.keyword)
        var storeKey = this.getStoreKey()
        if (this._storageItem.length == 0) {
          this._storageItem.push(keyword)
        } else if (this._storageItem.length <= 10) {
          var idx = array.indexOf(this._storageItem, keyword)
          if (idx > -1) {
            this._storageItem.splice(idx, 1)
          }
          this._storageItem.unshift(keyword)
        }
        if (this._storageItem.length > 10) {
          this._storageItem = this._storageItem.slice(0, 10)
        }
        window.localStorage.setItem(
          storeKey,
          JsonFun.stringify(this._storageItem)
        )
      }
      this._drawRecord()
    },

    _clearRecord: function(evt) {
      if (window.localStorage) {
        var target = evt.target
        var idx = domAttr.get(target, "data-index")
        var storeKey = this.getStoreKey()
        this._storageItem.splice(idx, 1)
        window.localStorage.setItem(
          storeKey,
          JsonFun.stringify(this._storageItem)
        )
        this._drawRecord()
      }
    },

    _clearRecords: function() {
      if (window.localStorage) {
        this._storageItem = []
        var storeKey = this.getStoreKey()
        window.localStorage.removeItem(storeKey)
        this._drawRecord()
      }
    },

    _selectRecord: function(evt) {
      var target = evt.target || evt.srcElement
      var record = target.innerHTML

      topic.publish("/mui/search/keyword", this, {
        keyword: record
      })
    },

    // 热门推荐
    drawHot: function() {
      var self = this
      request
        .get(util.formatUrl(this.hotUrl), {
          handleAs: "json"
        })
        .response.then(function(result) {
          var data = result.data
          if (data && data.length > 0) {
            self.hotWords = data
            domConstruct.create(
              "h3",
              {
                innerHTML: self.langSetting.hot
              },
              self.hotRecord
            )
            var ul = domConstruct.create("ul", null, self.hotRecord)
            for (var i = 0; i < self.hotWords.length; i++) {
              var item = domConstruct.create(
                "li",
                {
                  innerHTML: self.hotWords[i]
                },
                ul
              )
              self.connect(item, "onclick", "_selectRecord")
            }
          } else {
            domStyle.set(self.hotRecord, "display", "none")
          }
        })
    }
  })
})
