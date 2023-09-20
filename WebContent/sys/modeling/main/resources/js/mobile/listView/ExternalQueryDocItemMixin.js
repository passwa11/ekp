define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"dojo/on",
   	"mui/dialog/Tip", 
	"mui/openProxyMixin",
	"dojo/dom-geometry",
	"dojo/_base/window",
	"mui/i18n/i18n!sys-modeling-main",
	"dojo/query"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,on,Tip, openProxyMixin,domGeometry,win,modelingLang,query) {
	var item = declare("sys.modeling.main.resources.js.mobile.listView.ExternalQueryDocItemMixin", [ItemBase, openProxyMixin], {
		tag:"li",
		baseClass:"muiDocItem",


		buildRendering:function(){
			var className = "";
			this.domNode = domConstruct.create('li', {className : className}, this.containerNode);
			this.inherited(arguments);
			this.buildExternalQueryRender();
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},


		buildExternalQueryRender:function(){
			var itemClass = this.href ? {}:{className:'lock'};
			this.contentNode = domConstruct.create('a', itemClass);

			var contentClassName = "viewMobileCardContent";
			if(this.showFieldData.length <= 0){
				contentClassName = "viewMobileCardContent abstract";
			}
			var $infoNode = domConstruct.create("div",{className:contentClassName},this.contentNode);

			var $textBoxNode = domConstruct.create("div",{className:"viewMobileCardTextBox"},$infoNode);

			// 标题
			var $cardTextTitle = domConstruct.create("div",{
				className : "viewMobileConfigCardTextTitle",
			},$textBoxNode);
			var $cardTextTitleLeft = domConstruct.create("div",{
				className : "viewMobileCardTextTitleLeft",
				innerHTML : "<i class='title-icon'></i>"+this.mobileTitleText
			},$cardTextTitle);
			var $cardTextTitleRight = domConstruct.create("div",{
				className : "viewMobileCardTextTitleRight",
				innerHTML : this.mobileTitleValue
			},$cardTextTitle);

			//内容
			var $cardTextContent = domConstruct.create("div",{
				className : "viewMobileCardTextContent",
			},$textBoxNode);
			if(this.showFieldData.length > 0){
				for(var i = 0;i < this.showFieldData.length;i++){
					var $cardTextContentTr = domConstruct.create("div",{
						className : "viewMobileCardTextContentTr",
					},$cardTextContent);
					var $cardTextContentTdLeft = domConstruct.create("div",{
						className : "viewMobileCardTextContentTdLeft",
						innerHTML : this.showFieldData[i].text +":"
					},$cardTextContentTr);
					var $cardTextContentTdRight = domConstruct.create("div",{
						className : "viewMobileCardTextContentTdRight",
						innerHTML : this.showFieldData[i].value
					},$cardTextContentTr);

					if(i>=4){
						domStyle.set($cardTextContentTr, "display", "none");
					}
				}
			}

			//展开
			if(this.showFieldData.length > 4){
				var $show = domConstruct.create("div",{
					className : "viewMobileCardTextContentTr show",
				},$cardTextContent);

				var $showDiv = domConstruct.create("div",{
					className : "viewMobileCardTextContentShow",
					innerHTML : modelingLang['mui.sysModeling.btn.expand']+"<i class='down-icon'></i>"
				},$show);

				var $hide = domConstruct.create("div",{
					className : "viewMobileCardTextContentTr hide",
				},$cardTextContent);

				var $hideDiv = domConstruct.create("div",{
					className : "viewMobileCardTextContentHide",
					innerHTML : modelingLang['mui.sysModeling.btn.collapse']+"<i class='up-icon'></i>"
				},$hide);

				domStyle.set($hide,"display","none");
				
				on($show,'click',function(evt){
					evt.stopPropagation();
					var $CardTextTitles =query(".viewMobileCardTextContentTr",$textBoxNode);
					for (var i = 4; i < $CardTextTitles.length; i++) {
							domStyle.set($CardTextTitles[i], "display", "block");
					}
					domStyle.set($show,"display","none");
					domStyle.set($hide,"display","block");
				});

				on($hide,'click',function(evt){
					evt.stopPropagation();
					var $CardTextTitles =query(".viewMobileCardTextContentTr",$textBoxNode);
					for (var i = 4; i < $CardTextTitles.length; i++) {
							domStyle.set($CardTextTitles[i],"display","none");
					}
					domStyle.set($show,"display","block");
					domStyle.set($hide,"display","none");
				});
			}

			domStyle.set(this.domNode, "padding-right", "1.5rem");
			domStyle.set(this.domNode, "padding-top", "0");
			domConstruct.place(this.contentNode,this.domNode);
		},


		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},

		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},



	});
	return item;
});