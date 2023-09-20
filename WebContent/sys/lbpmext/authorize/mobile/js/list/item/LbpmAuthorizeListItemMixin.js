define(	["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"dojox/mobile/iconUtils", "mui/util", 
				"mui/list/item/_ListLinkItemMixin",
				"dojo/string","mui/i18n/i18n!sys-lbpmext-authorize:lbpmAuthorize.mobile"],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				iconUtils, util, _ListLinkItemMixin, string,Msg) {
			var item = declare("lbpm.authorize.item.LbpmAuthorizeListItemMixin", [ItemBase, _ListLinkItemMixin], {

						tag : "li",
						
						baseClass : "muiLbpmAuthorizeListItem",
						
						buildRendering : function() {
							this._templated = !!this.templateString;
							if (!this._templated) {
								this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag);
							}
							this.inherited(arguments);
							if (!this._templated)
								this.buildInternalRender();
						},

						buildInternalRender : function() {
							if(this.href){
								this.makeLinkNode(this.domNode);
							}
							// 内容载体DOM
                            var itemContentContainer = domConstruct.create("div", {className: "muiLbpmAuthorizeItemContent"}, this.domNode);
                            
                			// 构建头部信息（授权人、被授权人、授权类型、生效状态）
                			this._buildHeaderContent(itemContentContainer);
                            
                			// 构建底部信息（起止时间、有效时间）
                			this._buildFooterContent(itemContentContainer);
						},
						
						
						_buildHeaderContent:function(itemContentContainer){
                			// 头部信息DOM
                			var headerDom = domConstruct.create("div", { className: "headerContainer" }, itemContentContainer);
                			
                			// 授权人与被授权人左侧纵向连接线
                			var leftConnectLine = domConstruct.create("div", { className: "authorizeLeftConnectLine" }, headerDom);
                			domConstruct.create("div", { className: "authorizeLeftConnectLineStart" }, leftConnectLine);
                			domConstruct.create("div", { className: "authorizeLeftConnectLineEnd" }, leftConnectLine);
                			
                			// 授权人信息载体DOM
                			var authorizerInfoDom = domConstruct.create("div", { className: "authorizerInfo" }, headerDom);
                			
                			// 授权人头像
                			var authorizerHeadIconNode = domConstruct.create("div",{className:"muiAuthorizerHeadIcon"},authorizerInfoDom);
                			domConstruct.create('img',{src:util.formatUrl(this.fdAuthorizerIcon), className:'muiAuthorizerHeadImg'},authorizerHeadIconNode);
                			
                			// 授权人姓名标题		
                			var authorizerTitleNode = domConstruct.create("div",{className:"authorizerTitle"},authorizerInfoDom);
                			authorizerTitleNode.innerText = Msg["lbpmAuthorize.mobile.fdAuthorizer"]+":";
                			
                			// 授权人姓名		
                			var authorizerNode = domConstruct.create("div",{className:"authorizer"},authorizerInfoDom);
                			authorizerNode.innerText = this.authorizer;
                			
                			// 被授权人信息载体DOM
                			var authorizedPersonInfoDom = domConstruct.create("div", { className: "authorizedPersonInfo" }, headerDom);
                			
                			// 被授权人头像
                			var authorizedPersonHeadIconNode = domConstruct.create("div",{className:"muiAuthorizedPersonHeadIcon"},authorizedPersonInfoDom);
                			domConstruct.create('img',{src:util.formatUrl(this.fdAuthorizedPersonIcon), className:'muiAuthorizedPersonHeadImg'},authorizedPersonHeadIconNode);
                			
                			// 被授权人姓名标题		
                			var authorizedPersonTitleNode = domConstruct.create("div",{className:"authorizedPersonTitle"},authorizedPersonInfoDom);
                			authorizedPersonTitleNode.innerText = Msg["lbpmAuthorize.mobile.fdAuthorizedPerson"]+":";
                			
                			// 被授权人姓名	
                			var authorizedPersonNode = domConstruct.create("div",{className:"authorizedPerson"},authorizedPersonInfoDom);
                			authorizedPersonNode.innerText = this.authorizedPerson;					
                			
                			// 标签载体DOM
                			var tagContainerDom = domConstruct.create("div", { className: "authorizeTagContainer" }, headerDom);
                			
                			// 授权类型标签 
                			var authorizeTypeTag = domConstruct.create("div", { className: "authorizeTag authorizeTypeTag" }, tagContainerDom);
                			authorizeTypeTag.innerText = this.fdAuthorizeType;
                			
                			if(this.status){
                    			// “生效”标签
                    			var authorizeEffectTag = domConstruct.create("div", { className: "authorizeTag authorizeEffectTag" }, tagContainerDom);
                    			authorizeEffectTag.innerText = Msg["lbpmAuthorize.mobile.effect"];                				
                			}

						},
						
						
						_buildFooterContent:function(itemContentContainer){
							// 底部信息DOM
							var footerDom = domConstruct.create("div", { className: "footerContainer" }, itemContentContainer);
							
							// 起止时间信息DOM
							var durationTimeInfoNode = domConstruct.create("div", { className: "durationTimeInfo" }, footerDom);
							
							// 起止时间图标
							domConstruct.create("i", { className: "fontmuis muis-clock muiDurationTimeIcon"}, durationTimeInfoNode);
							
							// 起止时间标题
							var durationTimeTitleNode = domConstruct.create("div", { className: "durationTimeTitle" }, durationTimeInfoNode);
							durationTimeTitleNode.innerText = Msg["lbpmAuthorize.mobile.durationTime"]+":";
							
							// 起止时间
							var durationTimeNode = domConstruct.create("div", { className: "durationTime" }, durationTimeInfoNode);
							
							if(this.fdAuthorizeTypeV != 1){
								durationTimeNode.innerHTML = "<span class='durationStartTime' >"+this.fdStartTime+"</span><span class='durationTo' >"+Msg["lbpmAuthorize.mobile.duration.to"]+"</span><span class='durationEndTime' >"+this.fdEndTime+"</span>";
							}else{
								// 授权阅读的起止时间是：创建时间 至 永久
								durationTimeNode.innerHTML = "<span class='durationStartTime' >"+this.fdCreateTime+"</span><span class='durationTo' >"+Msg["lbpmAuthorize.mobile.duration.to"]+"</span><span class='durationEndTime' >"+Msg["lbpmAuthorize.mobile.duration.forever"]+"</span>";
							}
							
							// 有效时间信息DOM
							var effectiveTimeInfoNode = domConstruct.create("div", { className: "effectiveTimeInfo" }, footerDom);
							
							// 有效时间图标
							domConstruct.create("i", { className: "fontmuis muis-calid-time muiEffectiveTimeIcon"}, effectiveTimeInfoNode);
							
							// 有效时间标题
							var effectiveTimeTitleNode = domConstruct.create("div", { className: "effectiveTimeTitle" }, effectiveTimeInfoNode);
							effectiveTimeTitleNode.innerText = Msg["lbpmAuthorize.mobile.effectiveTime"]+":";
							
							// 有效时间
							var effectiveTimeNode = domConstruct.create("div", { className: "effectiveTime" }, effectiveTimeInfoNode);
							
							
							if(this.fdAuthorizeTypeV != 1){
								effectiveTimeNode.innerText = Msg['lbpmAuthorize.mobile.about']+this.overDay;
							}else{
								// 授权阅读类型显示长期有效
								effectiveTimeNode.innerText = Msg["lbpmAuthorize.mobile.effectiveTime.longTermEffective"];
							}
						}, 

						startup : function() {
							if (this._started) {
								return;
							}
							this.inherited(arguments);
						},

						_setLabelAttr : function(text) {
							if (text)
								this._set("label", text);
						}
					});
			return item;
		});
