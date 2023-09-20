define(
		[ "dojo/_base/declare", "mui/nav/MobileCfgNavBar", "dojo/request",
				"mui/util", "dojo/string","dojo/dom-geometry",
				"dojo/dom-construct", "dojo/Deferred", "dojo/when",
				"dojo/_base/lang", "dojo/_base/json", "dojo/_base/array",
				"dojo/dom-style", "dojox/mobile/_css3", "dojo/dom-attr","dojo/dom-class","dojo/dom-style",
				"dojo/topic" ,"dojo/_base/connect","dojo/request", "dojo/query"],
		function(declare, Nav, request, util, string, domGeometry ,domConstruct,
				Deferred, when, lang, json, array, domStyle, css3, domAttr, domClass, domStyle,
				topic,connect, request,query) {

			return declare("sys.xform.maindata.MainDataHeaderMixin",null,{
					
					dataRequest : '',//头部数据请求
				
					dataTitle : '',//头部名称
					
					dataIcon : '',//头部图标
					
					datas : [], //头部字段列表
					
					dataViewMax : 5, //头部字段默认显示条数(其余折叠)
					
					mobileView : '',
					
					detailUrl : '',
					
					showFieldsUrl : '',
					
					moreUrl : '',
					
					subject : '',
					
					color : '',
					
					buildRendering : function() {
						this.inherited(arguments);
						this.buildHeader();
						this.bindEvent();
					},
					
					buildHeader : function(){
						var self = this;
						if(this.dataRequest){
							var promise = request.get(util.formatUrl(this.dataRequest),{ handleAs : 'json' });
							promise.then(lang.hitch(this,function(data){
								this.dataTitle = data.title;
								this.dataIcon = data.icon;
								this.datas = data.fields;
								this.mobileView = data.mobileView;
								this.detailUrl = data.detailUrl;
								this.showFieldsUrl = data.showFieldsUrl;
								this.color = data.color;
								this.subject = data.subject;
								this._buildHeader();
							}));
						}else{
							this._buildHeader();
						}
					},
					
					_buildHeader : function(){
						domClass.add(this.domNode,'muiMainDataHeader');
						var headerBanner = this.headerBanner = domConstruct.create('div',{
							 className : 'mui_keydata_secpage_header'
						},this.domNode,'first');
//						var bannerLogo = domConstruct.create('div',{ className : 'muiBannerLogo' },headerBanner);
//						if(this.dataIcon){
//							domConstruct.create('img',{ src : util.formatUrl(this.dataIcon) },bannerLogo);
//						}
						var card = domConstruct.create('div',{ className : 'mui_keydata_card'},headerBanner);
						var card_content = domConstruct.create('div',{ className : 'mui_keydata_card_content'},card);
						
						if(this.dataTitle){
							var title_html = "<div class=\"inner\">     <span style=\"background-color:"+ this.color + "\" class=\"mui_keydata_card_tag tag-warning\">"+this.subject+"</span>   <h4 class=\"mui_keydata_card_title\">"+this.dataTitle+"</h4> </div>";
							domConstruct.create('div',{ id:'secpageHeaderTitle', className : 'mui_keydata_card_heading',innerHTML : title_html},card_content);
						}
						var card_list_ul = domConstruct.create('ul',{ className : 'mui_keydata_card_list'},card_content);
						
						if(this.datas && this.datas.length > 0){
							var showCount = 4;
							if(this.datas.length<4){
								showCount = this.datas.length;
							}else if(this.datas.length==5){
								showCount = 5;
							}
							for(var i = 0; i < showCount; i++){
								this._buildItem(this.datas[i], card_list_ul);
							}
							var mobileViewSupport = false;
							if(this.mobileView=='true'){
								this._buildMoreHeader(card_content,this.detailUrl);
								return;
							}
							//alert(this.detailUrl);
							//alert(this.dataRequest);
							var promise = request.get(util.formatUrl(this.detailUrl+"&checkMobilePage=true"),{ handleAs : 'txt' });
							promise.then(lang.hitch(this,function(data){
								if(data){
									data = data.replace(/[\r\n]/g, "");
								}
								if('true'==data){
									this._buildMoreHeader(card_content,util.formatUrl(this.detailUrl,true));
									return;
								}else{
									if(this.datas.length > showCount){
										this._buildMoreHeader(card_content,this.showFieldsUrl);
									}
								}
							}));
							
						}
					},
					
					_buildItem : function(data, container){
						var li = domConstruct.create('li', null , container);
						domConstruct.create('span',{className : 'title', innerHTML : data.text }, li);
						domConstruct.create('span',{className : 'txt', innerHTML : data.value }, li);
						return li;
					},
					
					_buildMoreHeader : function(card_content, url){
						this.moreUrl = url;
						var card_footer = domConstruct.create('div',{ className : 'mui_keydata_card_footer' }, card_content);
						
						var link = domConstruct.create('a',{
							 className : 'card_btn_more', 
							 innerHTML : '查看详情<i class="mui mui-forward"></i>' ,
							 href: url,
							 target: '_top'
						}, card_footer);
						connect.connect(link, 'click', this , '_showMore');
					},
					
					_showMore : function(){
//						this.defer(function(){
//							var showStatus = domStyle.get(this.headerMoreDatasContent, 'display');
//							if(showStatus == 'none'){
//								domStyle.set(this.headerMoreDatasContent, 'display', 'block');
//								this.headerDatasMoreLink.innerHTML = '收起';
//							}else{
//								domStyle.set(this.headerMoreDatasContent, 'display', 'none');
//								this.headerDatasMoreLink.innerHTML = '展开更多';
//							}
//							this._fixHeader();
//						},350);
						window.open(this.moreUrl,'_top');
					},
					
					bindEvent : function(){
						document.addEventListener('scroll', lang.hitch(this,this._fixHeader), false);
						this.subscribe('/dojox/mobile/viewChanged', 'handleViewChanged');
					},
					
					_fixHeaderOld : function(evt){
						var scrollTop = document.documentElement.scrollTop || window.scrollY,
							headerBannerH = domGeometry.getMarginBox(this.headerBanner).h,
							headerH = domGeometry.getMarginBox(this.domNode).h;
						//头部固定在顶部
						if(scrollTop >= headerBannerH - 10){
							if(!this.headerBannerFix){
								this.headerBannerFix = domConstruct.create('div',{
									className : 'mui_keydata_secpage_header',
									innerHTML : this.dataTitle
								},document.body);
							}
							domStyle.set(this.headerBannerFix,'display','block');
						}else{
							this.headerBannerFix && domStyle.set(this.headerBannerFix,'display','none');
						}
						//导航栏固定在顶部
						if(scrollTop >= headerH - 10){
							domStyle.set(this.refNavBarNode,{
								position : 'fixed',
								top : domGeometry.getMarginBox(this.headerBannerFix).h + 'px'
							});
						}else{
							domStyle.set(this.refNavBarNode,{
								position : 'relative',
								top : '0'
							});
						}
					},
					
					_fixHeader : function(evt){
						//var allEle = query('[name ="'+placeId+'"]',this.domNode);
						var scrollTop = document.documentElement.scrollTop || window.scrollY;
						var objHeader = query("#secpageHeaderTitle")[0];
				          var objTabs = query("#secpageTabs")[0];
				          var headerH = domGeometry.getMarginBox(objHeader).h;  //头部标题 高度
				          var tabsH = domGeometry.getMarginBox(objTabs).h;  //二级页选项卡 高度
				          var headerOffsetT = domGeometry.getMarginBox(objHeader).t;  //头部标题偏移 高度
				          var tabsOffsetT = domGeometry.getMarginBox(objTabs).t;   //二级页选项卡偏移 高度

				          //alert(scrollTop+"---"+headerOffsetT+"---"+tabsOffsetT);
				          if (scrollTop > headerOffsetT) {
				            domStyle.set(objHeader,'height',headerH);
				            query('#secpageHeaderTitle .inner').addClass('fixed-top');
				          } else {
				        	  query('#secpageHeaderTitle .inner').removeClass('fixed-top');
				        	  domAttr.remove(objHeader, 'style');
				          };

				          if (scrollTop > tabsOffsetT-36) {
				            domStyle.set(objTabs,'height',tabsH);
				            domClass.add(objTabs,'fixed-top');
				          } else {
				        	  domClass.remove(objTabs,'fixed-top');
					        domAttr.remove(objTabs, 'style');
				          };
				          
					},
					
					handleViewChanged : function(){
						this.defer(function(){
							this._fixHeader();
						},350);
					},

					startup : function() {
						var self = this;
						this.inherited(arguments);
						var children = this.getChildren();
						children.forEach(function(child){
							if(child.isInstanceOf(Nav)){
								self.refNavBarNode = child.domNode;
							}
						});
					}

			});
});