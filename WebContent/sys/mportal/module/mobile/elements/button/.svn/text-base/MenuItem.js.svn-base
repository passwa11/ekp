define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils"
], function(
  declare,
  _WidgetBase,
  domConstruct,
  domStyle,
  OpenProxyMixin,
  iconUtils
) {
  return declare(
    "sys.mportal.module.button.MenuItem",
    [_WidgetBase, OpenProxyMixin],
    {
      // 点击链接
      href: "",
      // 标题
      text: "",
      // 图标
      icon: "",
      // 宽度百分比
      width: "",
      // 自定义点击事件
      click: null,

      baseClass: "muiModuleButtonMenuItem",

      buildRendering: function() {
        this.inherited(arguments);
        domStyle.set(this.domNode, "width", this.width);
        this.renderIcon();
        this.renderText();
        this.bindClick();
      },

      // 绑定点击事件
      bindClick: function() {
        if (this.href) {
          this.proxyClick(this.domNode, this.href, "_blank");
          return;
        }

        if (this.click) {
          this.customClick();
        }
      },

      // 自定义点击事件
      customClick: function() {
        this.connect(this.domNode, "click", "_click");
      },

      _click: function() {
        this.click();
        // 默认关闭弹出框
        this.parent.onClose();
      },

      // 渲染图标
      renderIcon: function() {
        if (this.icon) {
          iconUtils.createIcon(this.icon, null, null, null, this.domNode);
        }
      },

      // 渲染文本
      renderText: function() {
        this.textNode = domConstruct.create(
          "div",
          { innerHTML: this.text, className: "muiModuleButtonMenuItemText" },
          this.domNode
        );
      }
    }
  );
});
