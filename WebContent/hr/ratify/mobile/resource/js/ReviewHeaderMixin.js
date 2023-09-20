define(
		[ "dojo/_base/declare", "mui/nav/MobileCfgNavBar", "dojo/request",
				"mui/util", "dojo/text!../tmpl/header.html", "dojo/string",
				"dojo/dom-construct", "dojo/Deferred", "dojo/when",
				"dojo/_base/lang", "dojo/_base/json", "dojo/_base/array",
				"dojo/dom-style", "dojox/mobile/_css3", "dojo/dom-attr",
				"dojo/topic", "mui/i18n/i18n!hr-ratify:mobile.hr.ratify" ],
		function(declare, Nav, request, util, tmpl, string, domConstruct,
				Deferred, when, lang, json, array, domStyle, css3, domAttr,
				topic, msg) {

			return declare(
					"hr.ratify.ReviewHeaderMixin",
					null,
					{

						navCount : 3,

						SCROLL_UP : '/km/review/scrollup',
						SCROLL_DOWN : '/km/review/scrolldown',
						SCROLL_TOP : '/mui/list/toTop',
						modelName:"",
						li : '<li data-index="${index}"><div><h3>${count}</h3><h4>${text}</h4></div></li>',

						buildRendering : function() {
							this.inherited(arguments);
							this.subscribe('/mui/nav/onComplete', 'onComplete');

							this.subscribe(this.SCROLL_UP, 'scrollup');
							this.subscribe(this.SCROLL_DOWN, 'scrolldown')
						},

						scrollup : function(obj, evt) {
							domStyle.set(this.headerNode, {
								'height' : 0
							});

							domStyle.set(this.navNode, {
								'height' : '4rem'
							});
						},

						scrolldown : function(obj, evt) {
							var headerHeight = window.headerOffsetHeight;
							if(!headerHeight){
								headerHeight = 168;
							}
							domStyle.set(this.headerNode, {
								'height' : headerHeight + 'px'
							});
							domStyle.set(this.navNode, {
								'height' : 0
							});
							if (!evt || !evt.evt)
								return;
							var navItem = this.getNav().getChildren()[0];
							navItem.setSelected();
							navItem.defaultClickAction(evt.evt);
							topic.publish(this.SCROLL_TOP, this, {
								time : 0
							});
						},
						
						onComplete : function(obj, items) {
							if (!items)
								return;
							when(this.requestNav(items), lang.hitch(this,
									this.buildNav));
						},

						buildNav : function() {
							
							this.buildHeaderNode();
							
							this.navNode = domConstruct.create('div', {
								className : 'muiAskHeaderNav'
							}, this.domNode);

							var children = this.getChildren();
							array.forEach(children, function(item) {
								domConstruct.place(item.domNode, this.navNode,
										'last');
							}, this);
							window._header_height = this.domNode.offsetHeight;
							
						},
						
						buildHeaderNode : function(){
							var self = this;
							var ls = [];
							array.forEach(this.navSource,function(item, index) {
								ls.push(string.substitute(this.li, {
									count : item['count'],
									text : item['text'],
									index : index
								}))
							}, this);
							
							var deferred = new Deferred();
							if(this.name && this.imgUrl){
								deferred.resolve();
							}else{
								var url = util.formatUrl('/sys/person/zone.do?method=info');
								request(url,{handleAs : 'json'}).then(function(data){
									self.name = data.fdName;
									self.imgUrl  = util.formatUrl(data.imgUrl);
									deferred.resolve();
								});
							}
							deferred.then(lang.hitch(this,function(){
								var modelName = this.showModuleName()
								this.headerNode = domConstruct.toDom(string
										.substitute(tmpl, {
											name : this.name,
											moduleName:modelName,
											imgUrl : this.imgUrl,
											nav : ls.join('')
										}));
								domConstruct.place(this.headerNode, this.domNode,'last');
								// 切换nav标签事件
								this.connect(this.headerNode, 'click','_onNavClick');
							}));
						},
						showModuleName:function(){						
							if(!this.mobileKey){
								
								switch (this.modelKey){
								case 'leave':
									return '<span><i></i>'+msg['mobile.hr.ratify.index.8']+'</span>';
									break;
								case 'transfer':
									return '<span><i></i>'+msg['mobile.hr.ratify.index.7']+'</span>';
									break;
								case 'entry':
									return '<span><i></i>'+msg['mobile.hr.ratify.index.5']+'</span>';
									break;
								case 'salary':
									return '<span><i></i>'+msg['mobile.hr.ratify.index.10']+'</span>';
									break;
								case 'positive':
									return '<span><i></i>'+msg['mobile.hr.ratify.index.6']+'</span>';
									break;
								case 'main':
									return "";
									break;
								case '':
									return "";
									break;
								}

							}else{
								if(this.mobileKey=='other')
									return '<span><i></i>'+msg['mobile.hr.ratify.index.11']+'</span>';
								else
									return '<span><i></i>'+msg['mobile.hr.ratify.index.9']+'</span>'
							}

							
						},
						_onNavClick : function(evt) {
							var target = evt.target;
							while (target) {
								var index = domAttr.get(target, 'data-index');
								if (index) {
									topic.publish(this.SCROLL_UP, this, {});
									var navItem = this.getNav().getChildren()[index];
									navItem.setSelected();
									navItem.defaultClickAction(evt);
									break;
								}
								target = target.parentNode;
							}
						},

						getNav : function() {
							if (this.navClaz)
								return this.navClaz;
							var children = this.getChildren();
							for (var i = 0; i < children.length; i++) {
								if (children[i] instanceof Nav)
									return this.navClaz = children[i];
							}
						},

						navIndex : 0,

						requestNav : function(items) {
							this.nav_leng = Math.min(items.length,
									this.navCount);
							this.deferred = new Deferred();
							this.navSource = [];
							for (var i = 0; i < this.nav_leng; i++) {
								var item = items[i];
								var text = item['text'];
								var url = item['url'];
								this.navSource.push({
									text : text
								});
								
								var newUrl = "/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=getCount";
								var count = this.ajaxRequest(newUrl, i);
								
							}
							return this.deferred.promise;
						},

						ajaxRequest : function(url, i) {
							var self = this;
							var promise = request.post(util.formatUrl(url), {
								handleAs : 'json',
								data:{
									modelKey:this.modelKey,
									mobileKey:this.mobileKey
								}
							}).response.then(function(data) {
								if (data.status == 200) {
									self.navIndex++;
									if(data){
										if(Object.keys(data['data']).length>0){
											var dataJson = data['data']
											var dataArr = []
											for(var key in dataJson){
												dataArr.push(dataJson[key])
											}
											self.navSource[i].count = dataArr[i];
										}else{
											self.navSource[i].count = 0;
										}
									}
									
									if (self.nav_leng == self.navIndex)
										self.deferred.resolve();
								}
							});
						},

						startup : function() {
							this.inherited(arguments);
							window.___header_height = this.domNode.offsetHeight;
						},

						makeTranslateStr : function(to) {
							var y = to.y;
							return "translate3d(0," + y + ",0px)";
						},
						
						// 让惯性变得平滑
						smooth : function(dom) {
							var cssKey = css3['transition'];
							domStyle.set(dom, cssKey,
									' -webkit-transform 100ms linear');
							this.defer(function() {
								domStyle.set(dom, cssKey, '')
							}, 100);
						},

						scrollTo : function(dom, to, smooth) {
							if (smooth)
								this.smooth(dom);
							var s = dom.style;
							s[css3.name("transform")] = this
									.makeTranslateStr(to);
						}

					});
		});