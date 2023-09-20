define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/dom",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/_base/lang",
  "dojo/html",
  "dojo/_base/array",
  "mui/util",
  "dojo/touch",
  "dojox/mobile/_css3",
  "dijit/registry",
  "dojo/dom-class",
  "dijit/_WidgetBase",
  "mui/history/listener"
], function(
  declare,
  topic,
  dom,
  domConstruct,
  domStyle,
  lang,
  html,
  array,
  util,
  touch,
  css3,
  registry,
  domClass,
  WidgetBase,
  listener
) {
  var eDialog = declare(
    "sys.xform.mobile.controls.EventDataDialog",
    [WidgetBase],
    {
      //是否多选
      isMul: false,

      //是否分页
      pageAble: false,

      //模板地址
      templURL: "sys/xform/mobile/controls/event/event_select.tmpl",

      modelingType: "",
      
      key: null,

      //数据源
      dataSource: null,

      //其他参数
      argu: null,
      
      selectedDatas:[],

      _dialogDivPrefix: "__cate_dialog_",
       //#156263 修复 动态选择框在移动端选择之后重新选择并确认时，页面会返回到最上一级页面
        postCreate:function(){
          this.inherited(arguments);
            topic.subscribe(
                "/sys/xform/event/selected",
                lang.hitch(this, "returnDialog")
            );
            topic.subscribe(
                "/sys/xform/event/cancel",
                lang.hitch(this, "closeDialog")
            );
        },
      _init: function(config) {
        this.isMul = config.isMul;
        this.key = config.key;
        this.pageAble = config.pageAble;
        this.argu = config.argu;
        this.dataSource = config.dataSource;
        this.selectedDatas = config.argu.selectedDatas;
      },

      returnDialog: function(srcObj, evt) {
        if (evt) {
          if (srcObj.key == this.key) {
            topic.publish("/sys/xform/event/setvalue", evt);
            this.closeDialog(srcObj);
          }
        }
      },

      showAnimate: function() {
        domClass.add(this.dialogDiv, "fadeIn animated");
        domStyle.set(this.dialogDiv, "display", "block");
        domStyle.set(this.dialogDiv, "background-color", "rgba(0, 0, 0, 0.6)");
        domClass.add(this.dialogContainerDiv, "fadeInRight animated");
        domStyle.set(this.dialogContainerDiv, "display", "block");
        var self = this;
        setTimeout(function() {
          //移除动画类名
          domClass.remove(self.dialogDiv, "fadeIn");
          domClass.remove(self.dialogContainerDiv, "fadeInRight");
        }, 500);
      },

      closeDialog: function(srcObj) {
        if (this.dialogDiv && srcObj.key == this.key) {
          domStyle.set(
            this.dialogDiv,
            css3.name("transform"),
            "translate3d(100%, 0, 0)"
          );
          var _self = this;
          setTimeout(function() {
            if (_self.parseResults && _self.parseResults.length) {
              array.forEach(_self.parseResults, function(w) {
                if (w.destroy) {
                  w.destroy();
                }
              });
              delete _self.parseResults;
            }
            domConstruct.destroy(_self.dialogDiv);
            _self.dialogDiv = null;
            //_self._working = false;
          }, 410);
        }
        listener.go({step:-1});
      },

      initSelData: function() {
        var outerSearchParams = this.argu.outerSearchParams;
        var showItems = [];
        array.forEach(
          outerSearchParams,
          function(outerSearch) {
            // 如果设置了隐藏，则不显示
            if (outerSearch.isHidden && outerSearch.isHidden == "true") {
              return;
            }
            showItems.push(outerSearch);
          },
          this
        );

        // 初始化搜索框
        var searchListWgt = registry.byId(
          "_eventdata_sgl_search_list_" + this.key
        );
        var dataWgt = registry.byId("_eventdata_sgl_list_" + this.key);
        
        if(showItems && showItems.length > 0){
        	if (searchListWgt) {
                searchListWgt.initSearchBars(this.argu, this.dataSource, showItems);
              }

              var filteWgt = registry.byId("_eventdata_sgl_filter_" + this.key);
              if (filteWgt) {
                filteWgt.initFilter(this.argu, this.dataSource, showItems);
              }        	
        } else {
        	domStyle.set(searchListWgt.getParent().domNode,{display:'none'});
        	dataWgt.getParent().resize();
        }
        
        // 初始化选项数据
        dataWgt.setArgu(this.argu);
        dataWgt.setSelectedDatas(this.selectedDatas);
        dataWgt.setData(this.dataSource);

        var selectionWgt = registry.byId(
          "_eventdata_sgl_selection_" + this.key
        );
        selectionWgt.setArgu(this.argu);
      },

      _closeDialog: function(evt) {
        var target = evt.target;
        if (this.dialogDiv && target === this.dialogDiv) {
          this.__doCloseDialog();
        }
      },

      __doCloseDialog: function() {
        domClass.add(this.dialogContainerDiv, "fadeOutRight animated");
        domClass.add(this.dialogDiv, "fadeOut animated");

        this.defer(function() {
          if (this.dialogContainerDiv) {
            domStyle.set(this.dialogContainerDiv, "display", "none");
          }
          if (this.dialogDiv) {
            domStyle.set(this.dialogDiv, "display", "none");
          }
        }, 500);

        setTimeout(
          lang.hitch(this, function() {
            if (this.parseResults && this.parseResults.length) {
              array.forEach(this.parseResults, function(w) {
                if (w.destroy) {
                  w.destroy();
                }
              });
              delete this.parseResults;
            }
            domConstruct.destroy(this.dialogDiv);
            this.dialogDiv = null;
            this._working = false;
          }),
          410
        );
      },

      _select$1: function(config) {
        this._init(config);
        if (this.templURL && !this._working) {
          var dialogId = this._dialogDivPrefix + this.key;
          this._working = true;
          this.dialogDiv = dom.byId(dialogId);

          if (this.dialogDiv == null) {
            var _self = this;
            require([
              "dojo/text!" + util.urlResolver(this.templURL, this)
            ], function(tmplStr) {
              _self.dialogDiv = domConstruct.create(
                "div",
                { id: dialogId, className: "muiCateDiaglog", tabindex: "0" },
                document.body,
                "last"
              );
              _self.dialogContainerDiv = domConstruct.create(
                "div",
                {
                  className: "muiCateDiaglogContainer muiEventContainer",
                  style: {
                    width: "100%"
                  }
                },
                _self.dialogDiv
              );
              _self.dialogDiv.focus();
              _self.defer(function() {
                _self.connect(_self.dialogDiv, "click", "_closeDialog");
              }, 500);
              util.disableTouch(_self.dialogDiv, touch.move);
              var dhs = new html._ContentSetter({
                node: _self.dialogContainerDiv,
                parseContent: true,
                cleanContent: true,
                onBegin: function() {
                  this.content = lang.replace(this.content, { argu: _self });
                  this.inherited("onBegin", arguments);
                }
              });
              dhs.set(tmplStr);
              dhs.parseDeferred.then(function(results) {
                _self.parseResults = results;
                domStyle.set(
                  _self.dialogDiv,
                  css3.name("transform"),
                  "translate3d(0, 0, 0)"
                );
                _self.initSelData();
              });
              dhs.tearDown();
              _self.showAnimate();
            });
          }
        }
      },

      select: function(config) {
        var forwardCallback = lang.hitch(this, function() {
          this._select$1(config);
        });

        var backCallback = lang.hitch(this, function() {
          window.beforeSelectCateHistoryId = null;
          this.__doCloseDialog();
        });

        var listenerResult = listener.push({
          forwardCallback: forwardCallback,
          backCallback: backCallback
        });
        window.beforeSelectCateHistoryId = listenerResult.previousId;
      }
    }
  );

  return eDialog;
});
