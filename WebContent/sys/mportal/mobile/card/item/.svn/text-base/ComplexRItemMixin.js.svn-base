define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojox/mobile/_ItemBase",
  "mui/util",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/i18n/i18n!sys-mportal:sysMportalPicDisplay.tip.num"
], function(declare, domConstruct, domClass, domStyle, ItemBase, util, OpenProxyMixin, msg) {
  var item = declare(
    "sys.mportal.item.ComplexRItemMixin",
    [ItemBase, OpenProxyMixin],
    {
      tag: "li",
      // 简要信息
      summary: "",
      // 创建时间
      created: "",
      // 创建者
      creator: "",
      // 状态
      status: "",

      // 创建时间
      docCreateTimeInteval: "",
      // 扩展字段，章节数
      catalogNum: "",
      // 扩展字段，学习人数
      fdLearnCount: "",

      buildRendering: function() {
        this._templated = !!this.templateString;
        if (!this._templated) {
          this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag, { className: "muiPortalComplexRItem" });
        }
        this.inherited(arguments);
        if (!this._templated) this.buildInternalRender();
      },
      
      buildInternalRender: function() {
    	  
    	// 构建a标签容器
        var aContainer = domConstruct.create("a", { className: "muiPortalComplexRItemLink", href: "javascript:;" }, this.domNode);
        
        // 绑定a标签点击跳转链接
        this.proxyClick(aContainer, this.href, "_blank");

        // 右侧图标
        if (this.icon) {
          this.icon = util.formatUrl(this.icon);
          this.imgNode = domConstruct.create("span", {className:"muiComplexRImg"}, aContainer);
          domStyle.set(this.imgNode, { 'background-image' : 'url(' + this.icon + ')' });
        }

		//  左侧容器
		var leftContainer = domConstruct.create('div', {}, aContainer);
        
        // 左侧上方标题
        if (this.label) {
          var titleLabelClass = this.icon ? "muiComplexrTitle muiComplexrIconTitle muiFontSizeM muiFontColorInfo" : "muiComplexrTitle muiFontSizeM muiFontColorInfo";
          var titleInnerHTML = (this.status ? this.status : "") + this.label;
          this.labelNode = domConstruct.create("h2", { className: titleLabelClass, innerHTML: titleInnerHTML }, leftContainer);
        }

        // 组件底部左侧基本信息容器
        var leftBottomContainer = domConstruct.create("div", { className: "muiPortalComplexRItemFooter muiFontSizeS muiFontColorMuted" }, leftContainer);

        
		// 显示 共“N”章节 ( 学习管理《课程列表》组件显示该信息 )
		if (this.catalogNum) {
			var catalogNumNode = domConstruct.create('span', { className:"muiComplexrCatalog" }, leftBottomContainer);
			catalogNumNode.innerText = msg["sysMportalPicDisplay.tip.num.chapter"].replace("{0}",this.catalogNum);
		}
         
		// 显示创建人姓名
        if (!this.catalogNum && this.creator) {
            this.createdNode = domConstruct.create("span", { className:"muiComplexrCreated", innerHTML:this.creator }, leftBottomContainer)
        }

		// 显示创建时间( 注：部分组件如《课程列表》显示的创建时间 “N”分钟前、“N”小时前...格式, 另外一些部件如《图文新闻》显示 YYYY-MM-DD格式 )
		if (!this.created)
			this.created = this.docCreateTimeInteval;
		if (this.created)
			domConstruct.create('span', {innerHTML:this.created }, leftBottomContainer);
        
		// 显示阅读数量( “N” 观看 )
        if (this.count) {
            this.countNode = domConstruct.create("span", { className:"muiComplexrRead", innerHTML:"<span class='muiComplexrReadNum'>"+this.count+"</span><span class='muiComplexrReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasRead"]+"</span>" }, leftBottomContainer);
        }
        
		// 显示 “N”人学习 ( 学习管理《课程列表》组件显示该信息 )
		if (this.fdLearnCount) {
			this.summary = domConstruct.create("span", { className:"muiComplexrRead", innerHTML:"<span class='muiComplexrReadNum'>"+this.fdLearnCount+"</span><span class='muiComplexrReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasLearned"]+"</span>" }, leftBottomContainer);
		}
        
      },

      startup: function() {
        if (this._started) {
          return;
        }
        this.inherited(arguments);
      },

      _setLabelAttr: function(text) {
        if (text) this._set("label", text);
      }
    }
  )
  return item;
})
