define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/topic",
  "sys/xform/mobile/controls/event/EventDataSelectAllCheckBox",
  "mui/i18n/i18n!sys-xform-base",
  "mui/history/listener"
], function(
  declare,
  WidgetBase,
  domConstruct,
  topic,
  EventDataSelectAllCheckBox,
  Msg,
  listener
) {
  var selection = declare(
    "sys.xform.mobile.controls.event.EventDataSelection",
    [WidgetBase],
    {
      baseClass: "muiEventSec",

      //对外事件唯一标示
      key: null,

      isMul: false,

      count: 0,

      argu: null,

      buildRendering: function() {
        this.inherited(arguments);
        this.containerNode = domConstruct.create(
          "div",
          { className: "muiEventContainer" },
          this.domNode
        );
        this.leftArea = domConstruct.create(
          "div",
          { className: "muiEventSecLeft" },
          this.containerNode
        );
        var selectAllWiget = this._createSelectAllCheckBox();
        this.selectAllCheckboxArea = selectAllWiget.selectAllCheckboxArea;
        domConstruct.place(this.selectAllCheckboxArea, this.leftArea);
        this.rightArea = domConstruct.create(
          "div",
          { className: "muiEventSecRight" },
          this.containerNode
        );
        this.buttonNode = domConstruct.create(
          "span",
          {
            className: "muiCateSecBtn muiCateSecBtnDis",
            innerHTML: Msg["mui.event.selection.ok"]
          },
          this.rightArea
        );
      },

      postCreate: function() {
        this.inherited(arguments);
        this.subscribe("/sys/xform/event/checked", "_addSelItme");
        this.subscribe("/sys/xform/event/unchecked", "_delSelItem");
        // 监控搜索，搜索的时候把数量清零
        this.subscribe("/sys/xform/event/searchData", "_delCount");
      },

      _createSelectAllCheckBox: function() {
        var selectAllCheckBox = new EventDataSelectAllCheckBox({
          key: this.key,
          isMul: this.isMul
        });
        selectAllCheckBox.startup();
        return selectAllCheckBox;
      },

      _subSelItem: function() {
        topic.publish("/sys/xform/event/submit", this);
      },

      setArgu: function(argu) {
        this.argu = argu;
      },

      _addSelItme: function() {
	      var srcOb = arguments[0];
	      var srcObj_key = srcOb.key;
          var this_key = this.key;
          if(
	         srcObj_key && this_key 
             && srcObj_key.indexOf("(")>-1
             && srcObj_key.indexOf(")")>-1
             && this_key.indexOf("(")>-1
             && this_key.indexOf(")")>-1
             && 
                (
	            srcObj_key.substring(srcObj_key.indexOf("("),srcObj_key.indexOf(")")+1)
                ==
                this_key.substring(srcObj_key.indexOf("("),this_key.indexOf(")")+1)
                )
          ){
                
                topic.publish("/sys/xform/event/addSeleteItem", srcOb);
                if (!srcOb.isSelectAll) {
                    this.count = this.count + 1;
                    this._resizeSelection();
                }
          }else if(// 如果没有获取到对应id，则走原逻辑,括号中的是控件id
	          srcObj_key.indexOf("(")==-1
             || srcObj_key.indexOf(")")==-1
             || this_key.indexOf("(")==-1
             || this_key.indexOf(")")==-1
          ){
	          topic.publish("/sys/xform/event/addSeleteItem", srcOb);
                if (!srcOb.isSelectAll) {
                    this.count = this.count + 1;
                    this._resizeSelection();
                }
          }
      },

      _delSelItem: function() {
        var srcOb = arguments[0];
        topic.publish("/sys/xform/event/delSeleteItem", srcOb);
        if (this.count > 0) {
          this.count = this.count - 1;
        }
        this._resizeSelection();
      },

      _delCount: function() {
        if (this.argu && this.argu.appendSearchResult == "true") {
          return;
        }
        if (this.count > 0) {
          this.count = 0;
          this._resizeSelection();
        }
      },

      _resizeSelection: function() {
          if(this.argu != null && (this.argu.paramsJSON.listRule == '01' || this.argu.paramsJSON.listRule =='00')){
              this.count = 1;
          }
        if (this.count > 0) {
          this.buttonNode.innerHTML =
            Msg["mui.event.selection.ok"] + "(" + this.count + ")";
          this.buttonNode.className = "muiCateSecBtn";
          if (this.subHandle == null)
            this.subHandle = this.connect(
              this.buttonNode,
              "click",
              "_subSelItem"
            );
        } else {
          this.buttonNode.innerHTML = Msg["mui.event.selection.ok"];
          this.buttonNode.className = "muiCateSecBtn muiCateSecBtnDis";
          if (this.subHandle) {
            this.disconnect(this.subHandle);
            this.subHandle = null;
          }
        }
      }
    }
  );
  return selection;
});
