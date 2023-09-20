define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "mui/list/JsonStoreList",
  "dojox/mobile/viewRegistry",
  "dijit/registry",
], function (declare, lang, JsonStoreList, viewRegistry, registry) {
  return declare("km.forum.ForumJsonStoreList", [JsonStoreList], {
    sortby: "", //降序升序用
    processing: null,
    isMore: false,
    scrollView: null,

    onPageBreakTo: function (widget, handle) {
      var scroll = viewRegistry.getEnclosingView(this.domNode);
      if (widget === scroll) {
        if (!this.url && scroll.rel && scroll.rel.url) {
          this.set("url", scroll.rel.url);
        }
        this._onPageReload(handle, widget); //breakPageTo
      }
    },

    onPageSortBy: function (widget, handle) {
      var scroll = viewRegistry.getEnclosingView(this.domNode);
      if (widget === scroll) {
        if (!this.url && scroll.rel && scroll.rel.url) {
          this.set("url", scroll.rel.url);
        }
        this.scrollView = widget;
        this._loadOver = false;
        return this.doLoad(handle, false);
      }
    },

    startup: function () {
      this.inherited(arguments);
      this.subscribe("/km/forum/onPageBreakTo", "onPageBreakTo");
      this.subscribe("/km/forum/onPageSortBy", "onPageSortBy");
    },
    _onPageReload: function (handle, widget) {
      if (this._isNumber(widget.breakPageTo)) {
        this.scrollView = widget;
        this.pageno = widget.breakPageTo;
        this._loadOver = false;
        return this.doLoad(handle, false);
      }
    },
    doLoad: function (handle, append) {
      this.inherited(arguments);
    },

    onComplete: function (items) {
      if (this.scrollView) {
        if (this.scrollView.isAppendBar == true) {
          var arrOptArr = [];
          var barOpt = { col: "backFloorBar", value: true };
          arrOptArr.push(barOpt);
          var page = { col: "currentPage", value: this.pageno };
          arrOptArr.push(page);
          this._insertBarToFloors(items, 1, arrOptArr);
          var contentHide = { col: "isContentHide", value: true }; //隐藏主贴内容部分
          items.datas[0].push(contentHide);
          items.datas[0].push(page);
          this.scrollView.isAppendBar = false;
        }
        if (this.scrollView.isAppendSortBar == true) {
          var sortBarArr = [];
          var barObj = { col: "isRefreshBar", value: true };
          var sortTypeObj = { col: "sortby", value: this.sortby };
          sortBarArr.push(barObj);
          sortBarArr.push(sortTypeObj);
          this._insertBarToFloors(items, 1, sortBarArr);
          var contentHide = { col: "isContentHide", value: true };
          var isShowFloorsAsc = { col: "isShowFloorsAsc", value: true };
          items.datas[0].push(contentHide);
          items.datas[0].push(isShowFloorsAsc);
          this.scrollView.isAppendSortBar = false;
        }
      }
      this.inherited(arguments);
      if (this.scrollView) {
        if (this.scrollView.scrollTo) {
          if (this.scrollView.scrollToBottom == true) {
            if (
              this._isNumber(this.scrollView.headHeight) &&
              this._isNumber(this.scrollView.screenHeight)
            ) {
              var scrollHeight =
                this.domNode.clientHeight + this.scrollView.headHeight;
              scrollHeight =
                scrollHeight -
                this.scrollView.screenHeight +
                this.scrollView.headHeight;
              this.scrollView.scrollTo({ y: -scrollHeight });
              this.loadMore(null);
            }
            this.scrollView.scrollToBottom = false;
          }
        }
      }
      this.scrollView = null;
      var pageCount = Math.ceil(this.totalSize / this.rowsize);
      var breakPageBtn = registry.byId("breakPageButton");
      if (breakPageBtn) {
        pageCount = pageCount < 1 ? 1 : pageCount;
        breakPageBtn.set("pageCount", pageCount);
      }
    },
    loadMore: function (handle) {
      //重写
      this.isMore = true;
      this.inherited(arguments);
      this.isMore = false;
    },
    // 重写构建
    buildQuery: function () {
      return lang.mixin(
        {},
        {
          pageno: this.pageno,
          rowsize: this.rowsize,
          sortby: this.sortby,
          isMore: this.isMore,
        }
      );
    },
    _isNumber: function (v) {
      if (v == null || v == "") {
        return false;
      }
      var re = /^[0-9]+.?[0-9]*$/;
      if (!re.test(v)) {
        return false;
      }
      return true;
    },
    _insertBarToFloors: function (items, index, params) {
      //插入一个返回顶部/查看前面的楼层的 操作栏识别参数， index 是插入的位置
      var newDatasArr = [];
      var isAppendYet = false;
      for (var i = 0; i < items.datas.length; i++) {
        if (i == index && !isAppendYet) {
          newDatasArr.push(params);
          isAppendYet = true;
          i = i - 1;
        } else {
          newDatasArr.push(items.datas[i]);
        }
      }
      items.datas = newDatasArr;
    },
  });
});
