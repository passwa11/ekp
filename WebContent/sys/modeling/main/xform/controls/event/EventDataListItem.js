define([
  "dojo/_base/array",
  "dojo/_base/declare",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dojo/topic"
], function(
  array,
  declare,
  domClass,
  domConstruct,
  domStyle,
  WidgetBase,
  topic
) {
  return declare(
    "sys.xform.mobile.controls.event.EventDataListItem",
    [WidgetBase],
    {
      key: null,

      isMul: false,

      data: [],

      headers: [],

      selected: false,

      baseClass: "muiEventItem",

      buildRendering: function() {
        this.inherited(arguments);
        this.contentNode = domConstruct.create(
          "div",
          { className: "muiEventContent" },
          this.domNode
        );
      },

      postCreate: function() {
        this.inherited(arguments);
          var _self = this;
        this.subscribe("/sys/xform/event/checked", "_setSelected");
        this.subscribe("/sys/xform/event/selectAll", "_setSelectAll"); //全选按钮事件
        this.connect(this.contentNode, "click", function(evt) {
            //解决kk双击问题
            var curDate = new Date();
            if(_self.clickTime && (curDate.getTime() - _self.clickTime < 500)){
                return;
            }
            _self.clickTime = curDate.getTime();
            this._selectData(evt);
        });
      },

      startup: function() {
        this.inherited(arguments);
        this.doRender();
      },

      doRender: function() {
        var areaDiv = domConstruct.create(
          "div",
          {
            className:
              "muiEventSelArea " +
              (this.isMul ? "muiEventSelMul" : "muiEventSelSgl")
          },
          this.contentNode
        );

        this.selNode = domConstruct.create(
          "div",
          { className: "muiEventSel" },
          areaDiv
        );

        array.forEach(
          this.data.itemVals,
          function(tmpVal, idx) {
            var eItem = domConstruct.create(
              "div",
              { className: "muiEventItem" },
              this.contentNode
            );
            var info = domConstruct.create(
              "div",
              { className: "muiEventInfo" },
              eItem
            );

            var conf = this.headers[idx];
            domConstruct.create(
              "div",
              {
                className: "muiEventTitle",
                innerHTML: conf.fieldNameForm
                  ? conf.fieldNameForm
                  : conf.fieldName
              },
              info
            );
            var valueNode = domConstruct.create(
              "div",
              { className: "muiEventValue", innerHTML: tmpVal },
              info
            );

            if (conf.hiddenFlag == "1") {
              domStyle.set(eItem, "display", "none");
            }

            if (idx == 0) {
              domClass.add(valueNode, "muiEventValueMain");
            }
          },
          this
        );
      },

      _setSelected: function(srcObj) {
        if (this.key == srcObj.key) {
          if (!this.isMul && this.id != srcObj.id) {
            if (this.selected) {
              this.selected = false;
              domConstruct.empty(this.selNode);
              domClass.remove(this.selNode, "muiEventSeled");
              topic.publish("/sys/xform/event/unchecked", this);
            }
          }
        }
      },
      _selectData: function() {
        this.selected = !this.selected;
        if (this.selected) {
          domConstruct.create(
            "i",
            {
              className: "fontmuis muis-form-selected-cor"
            },
            this.selNode
          );
          domClass.add(this.selNode, "muiEventSeled");
          topic.publish("/sys/xform/event/checked", this);
        } else {
          domConstruct.empty(this.selNode);
          domClass.remove(this.selNode, "muiEventSeled");
          topic.publish("/sys/xform/event/unchecked", this);
        }
      },
      _setSelectAll: function(srcObj) {
        //监听全选按钮选中事件
        if (this.key == srcObj.key) {
          if (srcObj.selected) {
            if (!this.selected) {
              this._selectData();
            }
          } else {
            if (this.selected) {
              this._selectData();
            }
          }
        }
      }
    }
  );
});
