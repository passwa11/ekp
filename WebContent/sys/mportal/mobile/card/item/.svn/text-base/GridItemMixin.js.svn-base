define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojox/mobile/_ItemBase",
  "mui/util",
  "sys/mportal/mobile/OpenProxyMixin",
  "dojo/window",
  "mui/i18n/i18n!sys-mportal:sysMportalPicDisplay.tip.num"
], function(
  declare,
  domConstruct,
  domStyle,
  ItemBase,
  util,
  OpenProxyMixin,
  window,
  msg
) {
  var item = declare(
    "sys.mportal.item.GridItemMixin",
    [ItemBase, OpenProxyMixin],
    {
      label: "",

      icon: "",

      href: "",

      fdLearnCount: "",

      baseClass: "muiPortalGridItem",

      docCreateTimeInteval: "",

      buildRendering: function() {
        this.inherited(arguments)

        var aContainer = domConstruct.create("a", {href: "javascript:;"}, this.domNode);

        if (this.href){
        this.proxyClick(aContainer, this.href, "_blank");
        }

        if (this.icon) {
          var icon = domConstruct.create("p", null, aContainer);


          domStyle.set(icon, {"background-image": "url(" + util.formatUrl(this.icon) + ")"});

          // 图片比例改为1:0.68
          domStyle.set(icon, { height: ((window.getBox().w - 3.6 * 12) / 2) * 0.68 + "px"});
          
        }

        // 标题
        if (this.label) {
          domConstruct.create("span",{className: "muiPortalGridItemTitle muiFontSizeM muiFontColorInfo", innerHTML: this.label}, aContainer);
        }
        
        // 底部DOM  
        var footerNode = domConstruct.create("div", { className: "muiPortalGridItemFooter muiFontSizeS muiFontColorMuted" }, aContainer);
        
        // 创建人
        if (this.creator) {
          // 底部第一行DOM
          var footerFirstRowNode = domConstruct.create("div", {}, footerNode);
          domConstruct.create("div", {className: "muiPortalGridItemCreator",innerHTML: this.creator}, footerFirstRowNode);
        }
        
        // 底部第二行DOM
        var footerSecondRowNode = domConstruct.create("div", {}, footerNode);
        
        // 创建时间
        if (this.created) {
          domConstruct.create("div", {className: "muiPortalGridItemCreated",innerHTML: this.created}, footerSecondRowNode);
        }

        // 创建时间（带icon，“xxx分钟前”、“xxx小时前” ...）
        if (this.docCreateTimeInteval) {
          domConstruct.create("div", {className: "muiPortalGridItemCreated",innerHTML: this.docCreateTimeInteval}, footerSecondRowNode);
        }
        
        // 学习人数
        if (this.fdLearnCount) {
          domConstruct.create("div", {className: "muiPortalGridItemCount",innerHTML:"<span class='muiPortalGridItemCountReadNum'>"+this.fdLearnCount+"</span><span class='muiPortalGridItemCountReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasLearned"]+"</span>"}, footerSecondRowNode);
        }

        // 浏览人数
        if (this.count) {
          domConstruct.create("div", {className: "muiPortalGridItemCount",innerHTML:"<span class='muiPortalGridItemCountReadNum'>"+this.count+"</span><span class='muiPortalGridItemCountReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasRead"]+"</span>"}, footerSecondRowNode);
        }
        
      },

      _setLabelAttr: function(text) {
        if (text) this._set("label", text)
      }
    }
  )
  return item
})
