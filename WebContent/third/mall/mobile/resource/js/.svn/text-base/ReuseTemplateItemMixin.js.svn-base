define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/dialog/Tip",
   	"mui/device/adapter",
   	"dojo/_base/lang",
   	"mui/i18n/i18n!third-mall:mui"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,openProxyMixin,Tip,adapter,lang,msg) {
	var item = declare("third.mall.mobile.resource.js.ReuseTemplateItemMixin", [ItemBase,openProxyMixin], {
		
		tag:"div",
		
		baseClass:"muiKmReuseTemplateItem",
		
		fdName:"",
		
		iconClass:"muiReuseIcon",
		
		href: "",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(this.tag, { className : 'muiKmReuseTemplateListItem' });
			if (this.baseClass) {
				domClass.add(this.domNode,this.baseClass);
			}
			this.buildInternalRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildInternalRender : function() {
			
			this.buildImg();
			
			var contentRightNode = domConstruct.create("div",{className:"muiReuseRight"},this.contentNode);
			
			var rightContext = domConstruct.create("div",{className:"rightContent"},contentRightNode);
			
			//标题
			var titleNode = domConstruct.create("p",{className:"muiFontSizeLA muiFontColorInfo", innerHTML:this.label},rightContext);
			
			//描述
			var desc = this.fdDesc || "";
			var desc = domConstruct.create("span",{innerHTML:desc},rightContext);
			
			//操作
			var optNode = domConstruct.create("div",{className:"btn muiFontSizeL", innerHTML:msg["mui.thirdMall.use"]},contentRightNode);
			
			// 绑定点击事件
			var createUrlObj =document.getElementsByName("createUrl")[0];
			var isAuth = document.getElementsByName("isAuth")[0].value;
			if (isAuth == "true") {
				if(createUrlObj){
					var createUrl = createUrlObj.value;
					createUrl += "&fdThirdTemplateId=" + this.fdId;
					var url = this.escape(this.mobilePic);
					createUrl += "&previewUrl=" + encodeURIComponent(url);
					createUrl += "&_referer=" + encodeURIComponent(util.formatUrl("/km/review/km_review_template/kmReviewTemplate.do?method=index"));
					this.proxyClick(optNode, createUrl, '_blank');
				}
			} else {
				this.connect(optNode, 'click', function() {
					Tip.tip({text: msg["mui.thirdMall.noAuth"]})
				});
			}
		},
		
		buildImg : function() {
			var iconWrapNode = domConstruct.create('div', {
				className : "image"
			}, this.contentNode);
			if (this.mobilePic) {
				var url = this.escape(this.mobilePic);
				url += "&s_time=" + new Date().getTime();
				this.iconNode = domConstruct.create('img', {
					src : url,
					className : "preview"
				}, iconWrapNode);
				this.connect(iconWrapNode, 'click',lang.hitch(this._onItemClick));
			} else {
				this.iconNode = domConstruct.create('img', {
					className : this.iconClass,
					src : ""
				}, iconWrapNode);
			}
		},
		
		_onItemClick:function(){
			if (this.mobilePic) {
				var url = this.escape(this.mobilePic);
				adapter.imagePreview({
					curSrc : url, 
					srcList : [url],
					previewImgBgColor : 'rgba(0, 0, 0, 0.6)'
				});
			}
		},
		
		escape : function(url) {
			if (url == null || url == "") {
				return "";
			}
			url = url.replace(/&amp;/g,"\&");
			url = url.replace(/&quot;/g,"\"");
			url = url.replace(/&lt;/g,"\<");
			url = url.replace(/&#39;/g,"\'");
			url = url.replace(/&gt;/g,"\>");
			return url;
		} ,
		
		startup:function(){
			this.inherited(arguments);
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});