define([
  "dojo/_base/declare",
  "dijit/_Contained",
  "dijit/_Container",
  "dijit/_WidgetBase",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/topic",
  "dojo/query",
  "mui/util",
  "mui/i18n/i18n!sys-mobile",
  "mui/history/listener",
  "dojo/_base/lang"
], function(
  declare,
  Contained,
  Container,
  WidgetBase,
  domClass,
  domConstruct,
  domStyle,
  topic,
  query,
  util,
  Msg,
  listener,
  lang
) {
  return declare("mui.search.SearchBar", [WidgetBase, Container, Contained], {
    searchUrl:
      "/sys/ftsearch/mobile/index.jsp?keyword=!{keyword}&modelName=!{modelName}&searchFields=!${searchFields}&docStatus=!{docStatus}",
    //模块标识
    modelName: "",

    //搜索关键字
    keyword: "",
    //搜索字段范围
    searchFields: "",
    //文档状态
    docStatus: '',
    jumpToSearchUrl: true,

    //显示高度
    height: "inherit",

    needPrompt: true,

    // 独立页面
    forPage: false,

    //提示文字
    placeHolder: Msg['mui.search.hint'],

    buildRendering: function() {
      this.inherited(arguments)

      domClass.replace(this.containerNode, "muiSearchBar")
      var searchContainer = null
      var nodelist = query(this.containerNode).parent("form")

      // 如果当前页面存在form，则使用，否则则创建，用于触发键盘搜索按钮
      var formContainer
      if (nodelist.length == 0) {
        this._searchForm = domConstruct.create(
          "form",
          {
            action: "",
            onsubmit: "return false;"
          },
          this.containerNode
        )

        formContainer = this._searchForm
      } else {
        this._searchForm = nodelist[0]
        formContainer = this.containerNode
      }

      searchContainer = domConstruct.create(
        "div",
        {
          className: "muiSearchBarContainer"
        },
        formContainer
      )

      var searchArea = domConstruct.create(
        "div",
        {
          className: "muiSearchDiv"
        },
        searchContainer
      )
      this._searchContainer = searchContainer

      this.searchIconNode = domConstruct.create(
        "div",
        {
          className: "muiSearchIcon fontmuis muis-search",
          style: {
            "z-index": "2"
          }
        },
        searchArea
      )

      this.clearNode = domConstruct.create(
        "div",
        {
          className: "muiSearchClear fontmuis muis-delete"
        },
        searchArea
      )

      this.searchNode = domConstruct.create(
        "input",
        {
          className: "muiSearchInput",
          type: "search",
          value: this.keyword,
          placeHolder: this.placeHolder || Msg["mui.search.search"]
        },
        searchArea
      )

      this.buttonArea = domConstruct.create(
        "div",
        {
          className: "muiSearchBtnDiv"
        },
        searchContainer
      )

      this.cancelNode = domConstruct.create(
        "div",
        {
          className: "muiSearchCancelBtn muiFontSizeM muiFontColorMuted",
          innerHTML: Msg["mui.search.cancel"]
        },
        this.buttonArea
      )
      domStyle.set(this.buttonArea, {
        display: "none"
      })
    },

    postCreate: function() {
      this.inherited(arguments)
      // 搜索事件
      this.connect(this._searchForm, "onsubmit", "_onSearch")
      this.connect(this.searchNode, "onfocus", "_onfocus")
      this.connect(this.searchNode, "onclick", "_onfocus")
      this.connect(this.searchNode, "oninput", "_onChange")
      this.connect(this.clearNode, "onclick", "_onClear")
      this.connect(this.cancelNode, "onclick", "_onCancel")
      this.subscribe("/mui/searchbar/cancel", "_onCancel")
      this.subscribe("/mui/searchbar/clickListItem", "clickListItem")
      this.subscribe("/mui/searchbar/show", "_onfocus")
      this.subscribe("/mui/search/keyword", "_fillKeyword")
      this.subscribe("/mui/search/submit", "_onSubmit")
    },

    startup: function() {
      if (this._started) return
      this.inherited(arguments)

      if ( this.height && this.height != "inherit") {
        domStyle.set(this.domNode, {height: this.height, "line-height": this.height})
      }

      if (this.forPage) {
        this._onfocus$(!!this.keyword)
      }
    },

    _onSubmit: function(s, r) {
      if (this == s && r && r.url && this.jumpToSearchUrl) {
        this.searchNode.value = this.keyword
        location = r.url
      }
    },

    _fillKeyword: function(srcObj, ctx) {
      if (ctx) {
        var keyword = ctx.keyword
        if (!keyword) {
          return
        }

        keyword = util.decodeHTML(keyword)
        keyword = keyword.replace(/&amp;/g, "&")
        this.set("keyword", keyword)

        this._onSearch()
      }
    },

    _onfocusCommon: function() {
      topic.publish("/mui/search/onfocus", this)

      // 显示取消按钮
      if (!this.forPage) {
        domStyle.set(this.buttonArea, {
          display: "table-cell"
        })
      }

      // 如果搜索框有值，显示清除图标
      if (this.keyword) {
        this.showClear()
      }
    },

    _onfocus$: function(submit) {
      this._onfocusCommon()
      this._showPrompt$(submit)
    },

    _onfocus: function(srcObj) {
      if (this.show) {
        return
      }
      // 显示弹出层
      if (!this.needPrompt) {
        this._onfocusCommon()
      } else {
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
        this._eventStop(srcObj)
      }
    },

    _showPrompt$: function(submit) {
      if (!this.promptNode) {
        this.promptNode = domConstruct.create(
          "div",
          {
            className: "muiSearchPrompt fadeIn animated"
          },
          document.body
        )

        var self = this
        require(["mui/search/_SearchPrompt"], function(SearchPrompt) {
          self._prompt = new SearchPrompt({
            srcNodeRef: self.promptNode,
            prefix: self.modelName,
            modelName: self.modelName,
            parent: self,
            forPage: self.forPage
          })
          self._prompt.startup()
          if (submit) {
            self._fillKeyword(null, self)
          }
        })
      } else {
        if (this._prompt) {
          this._prompt.show()
        }
      }

      var _self = this
      var tmpEvt = this.connect(document.body, "touchend", function(evt) {
        if (evt.target != _self.searchNode) {
          setTimeout(function() {
            _self.searchNode.blur()
            _self.disconnect(tmpEvt)
          }, 410)
        }
      })
    },

    show: false,

    // 显示清除图标
    showClear: function() {
      domStyle.set(this.clearNode, {
        display: "inline-block"
      })
    },

    // 隐藏清除图标
    hideClear: function() {
      domStyle.set(this.clearNode, {
        display: "none"
      })
    },

    _onChange: function() {
      if (this.timeout) {
        clearTimeout(this.timeout)
      }

      var self = this
      this.timeout = setTimeout(function() {
        self.keyword = self.searchNode.value
        // 有值情况
        if (self.keyword) {
          // 显示清空输入框按钮
          self.showClear()
          if (self.needPrompt) {
            // 初始化联想组件
            if (!self._complete) {
              require(["mui/search/_SearchComplete"], function(SearchComplete) {
                self._complete = new SearchComplete({parent: self})
                self._complete.startup()
              })
            }
          }
        } else {
          self.hideClear()
        }

        // 值改变事件，用于搜索联想
        topic.publish("/mui/search/onChange", self)
      }, 300)
    },

    _onClear: function() {
      this.set("keyword", "")

      this.hideClear()

      topic.publish("/mui/search/onClear", this)
      topic.publish("/mui/search/onChange", this)
    },

    _onCancel$: function() {
      this.show = false
      this._onClear()
      domStyle.set(this.buttonArea, {
        display: "none"
      })
      topic.publish("/mui/search/cancel", this)
    },
    
    clickListItem: function(data) {
    	listener.go({
    		historyId: this.beforeSelectCateHistoryId,
    		callback: lang.hitch(this, function() {
    			if(data && data.cb)
    				data.cb()
    		})
    	})
    },

    _onCancel: function() {
      if (this.beforeSelectCateHistoryId) {
        listener.go({
          historyId: this.beforeSelectCateHistoryId,
          callback: lang.hitch(this, function() {
            this._onCancel$()
          })
        })
      } else {
        this._onCancel$()
      }
    },

    // 禁止默认事件
    _eventStop: function(evt) {
      if (evt) {
        if (evt.stopPropagation) evt.stopPropagation()
        if (evt.cancelBubble) evt.cancelBubble = true
        if (evt.preventDefault) evt.preventDefault()
        if (evt.returnValue) evt.returnValue = false
      }
    },

    _setKeywordAttr: function(keyword) {
      this.keyword = keyword
      this.searchNode.value = keyword
    },

    // 搜索
    _onSearch: function(evt) {
      this._eventStop(evt)
      this.set("keyword", this.searchNode.value)
      if (this.keyword) {
        var t = {}
        if (!this.needPrompt) {
          if (this.searchUrl) {
            var url = util.formatUrl(util.urlResolver(this.searchUrl, this))
            t.url = url
          }
          t.keyword = decodeURIComponent(this.keyword)
          t.modelName = this.modelName
          t.searchFields = this.searchFields
          t.docStatus = this.docStatus
        }
        topic.publish("/mui/search/submit", this, t)
      }

      return false
    }
  })
})
