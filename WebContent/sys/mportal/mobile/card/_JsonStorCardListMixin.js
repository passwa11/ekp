define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojox/mobile/_StoreListMixin",
  "mui/store/JsonRest",
  "mui/store/JsonpRest",
  "dojo/topic",
  "mui/util",
  "dojo/dom-construct",
  "mui/loadingMixin"
], function(
  declare,
  lang,
  _StoreListMixin,
  JsonStore,
  JsonpStore,
  topic,
  util,
  domConstruct,
  loadingMixin
) {
  return declare(
    "sys.mportal.card._JsonStorCardListMixin",
    [_StoreListMixin, loadingMixin],
    {
      // 支持URL
      url: "",

      pageno: 1,

      rowsize: null,

      busy: false,

      again: true, //是否从头开始

      dataType: "json",

      //是否马上请求数据
      lazy: true,

      _setUrlAttr: function(url) {
        this.url = util.formatUrl(url)
      },

      handleOnReload: function(widget, evt) {
        this.reload(evt)
      },

      startup: function() {
        if (this._started) {
          return
        }
        this.inherited(arguments)
        if (!this.lazy) {
          this.doLoad()
        }
        this.subscribe("/mui/mportal/card/reload", "handleOnReload")
      },

      onComplete: function(items) {
        this.busy = false
        this.destroyLoading(this.domNode)
        var list = this.resolveItems(items)
        this.generateList(list)
        topic.publish("/mui/mportal/card/loaded", this, items)
      },

      onError: function(error) {
        this.busy = false
      },

      doLoad: function(evt, append) {
        if (this.busy) {
          return
        }

        this.busy = true
        this.append = !!append

        if (this._loadOver) {
          this.busy = false
          return
        }

        this.buildLoading(this.domNode)

        var promise = null
        if (this.store) {
          this.store.target = this.url
          promise = this.setQuery(this.buildQuery(), {})
        } else {
          if (this.dataType == "jsonp") {
            promise = this.setStore(
              new JsonpStore({
                idProperty: "fdId",
                target: util.urlResolver(this.url, this)
              }),
              this.buildQuery(),
              {}
            )
          } else {
            promise = this.setStore(
              new JsonStore({
                idProperty: "fdId",
                target: util.urlResolver(this.url, this)
              }),
              this.buildQuery(),
              {}
            )
          }
        }
        var self = this
        return promise
      },

      reload: function(evt) {
        this.pageno = 1
        this._loadOver = false
        return this.doLoad(evt, false)
      },

      loadMore: function(evt) {
        if (this.again && this._loadOver) {
          this.pageno = 1
          this._loadOver = false
        }
        return this.doLoad(evt, false)
      },

      formatDatas: function(datas) {
        var dataed = []
        for (var i = 0; i < datas.length; i++) {
          var datasi = datas[i]
          dataed[i] = {}
          for (var j = 0; j < datasi.length; j++) {
            dataed[i][datasi[j].col] = datasi[j].value
          }

          dataed[i]["context"] = this.context || false
        }
        return dataed
      },

      resolveItems: function(items) {
        this._loadOver = false
        var page = {}
        if (items) {
          if (items["datas"]) {
            //分页数据
            this.listDatas = this.formatDatas(items["datas"])
            page = items["page"]
            if (page) {
              this.pageno = parseInt(page.currentPage, 10) + 1
              this.rowsize = parseInt(page.pageSize, 10)
              this.totalSize = parseInt(page.totalSize, 10)
              if (
                parseInt(page.totalSize || 0, 10) <=
                (this.pageno - 1) * this.rowsize
              ) {
                this._loadOver = true
              }
            }
          } else {
            //直接数据,不分页
            this.listDatas = items
            this.totalSize = items.length
            this.pageno = 1
            this._loadOver = true
          }
        }

        return this.listDatas
      },

      // 构建
      buildQuery: function() {
        return lang.mixin([], {
          pageno: this.pageno,
          rowsize: this.rowsize
        })
      }
    }
  )
})
