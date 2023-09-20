define("mui/panel/AccordionPanelMixin", [
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "mui/panel/Content",
], function (declare, WidgetBase, Contained, Container, domConstruct, Content) {
  return declare(
    "mui.panel.AccordionPanelMixin",
    [WidgetBase, Contained, Container],
    {
      startup: function () {
        if (this._started) return;
        this.buildContent();
        this.inherited(arguments);
      },

      // 所有标题位置
      titleList: [],

      contentList: [],

      containDom: function (dom) {
        var children = this.getChildren();
        for (var i = 0; i < children.length; i++) {
          if (children[i].domNode == dom) return children[i];
        }
        return null;
      },

      // 构建内容
      buildContent: function () {
        var childrenNodes = [];
        for (var j = 0; j < this.domNode.childNodes.length; j++) {
          childrenNodes.push(this.domNode.childNodes[j]);
        }

        for (var i = 0; i < childrenNodes.length; i++) {
          var c = this.containDom(childrenNodes[i]);
          if (c) {
            if (c instanceof Content) {
              var container = domConstruct.create(
                "div",
                { className: "muiAccordionPanelContainer" },
                this.domNode
              );
              var title = domConstruct.create(
                "div",
                { className: "muiAccordionPanelTitle" },
                container
              );
              this.titleList.push(title);
              var __content = c;
              this.contentList.push({
                claz: __content,
              });
              // 非延迟则显示内容
              var icon = "";
              if (__content.icon) {
                if (__content.icon.indexOf("muis-") == 0) {
                  // 新的图标库
                  icon =
                    '<span class="fontmuis ' + __content.icon + '"></span>';
                } else {
                  // 兼容历史图标库
                  icon = '<span class="mui ' + __content.icon + '"></span>';
                }
              }

              var titleMsg = __content.title;
              if (titleMsg)
                title.innerHTML = icon + "<div>" + titleMsg + "</div>";
              domConstruct.place(__content.domNode, container);
            }
          } else domConstruct.place(childrenNodes[i], this.domNode);
        }
      },
    }
  );
});
