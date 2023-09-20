define([ 'dojo/_base/declare', 'dijit/_WidgetBase', 'dijit/_TemplatedMixin', 
         'dojo/text!../tmpl/zipViewer.jsp', './list/ZipViewerItemList', './list/ZipViewerEmptyList',
         'dojo/_base/lang', 'dojo/_base/array', 'dojo/request', 'dojo/dom-construct', 'dojo/dom-attr', 
         'dojo/dom-style', 'dojo/dom-class', 'dojo/html', 'dojo/topic', 'dojo/on' ], 
         function(declare, _WidgetBase, _TemplatedMixin, tmpl, ZipViewerItemList, ZipViewerEmptyList, lang, array, 
        		 request, domCtr, domAttr, domStyle, domClass, html, topic, on) {
	return declare('sys.attachment.mobile.zip.ZipViewer', [ _WidgetBase, _TemplatedMixin ], {
		
		templateString: tmpl,
		
		_counter: 0,
		_links: [],
		_files: {},
		
		ZIPVIEWER_PUSHLINK: 'sys/attachment/mobile/zipViewer/pushLink',
		ZIPVIEWER_POPLINK: 'sys/attachment/mobile/zipViewer/popLink',
		
		postCreate: function() {
			this.initialize();
			this.inherited(arguments);
		},
		
		initialize: function() {
			
			var self = this;
			
			//加载并初始化数据
			request(this.url, {
				method: 'get',
				handleAs: 'json'
			}).then(function(res) {
				res = res || [];
				
				//数据转换
				res = self.resolveData(res);
				
				//添加根节点
				res = {
						fdId: 'root',
						type: 'dir',
						name: self.title,
						list: res,
						canRead : true
				};
				
				//单节点数据缓存
				self.initData(res);
				
				//渲染根节点数据
				self.pushLink('root');
			}, function(err) {
				
			});
			
			//监听事件
			topic.subscribe(this.ZIPVIEWER_PUSHLINK, function(fdId) {
				self.pushLink(fdId);
			});
			topic.subscribe(this.ZIPVIEWER_POPLINK, function() {
				self.popLink();
			});
			
			/*
			on(this.btnBackNode, 'click', function() {
				self.popLink();
			});
			*/
		},
		
		_renderLinks: function() {
			var self = this;
			
			domCtr.empty(this.headNode);
			
			array.forEach(this._links || [], function(link, i) {
				
				var linkNode = domCtr.create('div', {
					className: 'muiAttViewerLink'
				}, self.headNode);
				
				var file = self._files[link];
				
				domCtr.create('span', {
					innerHTML: file.name
				}, linkNode);
				
				if(i != self._links.length - 1) {
					domCtr.create('i', {
						className: 'mui mui-forward'
					}, linkNode);
					
					on(linkNode, 'click', function() {
						
						var links = [];
						for(var i = 0; i < self._links.length; i++) {
							links.push(self._links[i]);
							if(self._links[i] == link) {
								break;
							}
						}
						self.setLinks(links);
					});
					
				} else {
					domClass.add(linkNode, 'active');
				}
			});
			
		},
		
		renderLinks: function() {
			
			this._renderLinks();
			
			/*
			if(this._links.length > 1) {
				domStyle.set(this.btnBackNode, 'display', 'block');
			} else {
				domStyle.set(this.btnBackNode, 'display', 'none');
			}
			*/
			
			var fdId = this._links[this._links.length - 1];
			var link = this._files[fdId];
			var title = link.name;
			var list = link.list || [];
			
			//html.set(this.titleNode, title);
			
			domCtr.empty(this.contentNode);
			if(list.length <= 0) {
				new ZipViewerEmptyList().placeAt(this.contentNode);
			} else {
				new ZipViewerItemList({
					list: list
				}).placeAt(this.contentNode);
			}
		},
		
		setLinks: function(links) {
			this._links = links;
			this.renderLinks();
		},
		
		pushLink: function(fdId) {
			this._links.push(fdId);
			this.renderLinks();
		},
		popLink: function() {
			if(this._links.length > 1) {
				this._links.pop();
				this.renderLinks();
			}
		},
		
		resolveData: function(data) {
			var self = this;
			var res = [];
			for(var i = 0; i < data.length; i++) {
				var t = data[i];
				
				res.push({
					fdId: (t.type == 'dir' ? ('dir' + self._counter++) : (t.attId || '')),
					name: t.name,
					type: t.type,
					list: self.resolveData(t.child || []),
					size: t.size,
					canView: t.view == '1',
					hasViewer: t.hasViewer
				});
			}
			return res;
		},
		
		initData: function(data) {
			var self = this;
			self._files[data.fdId] = data;
			array.forEach(data.list || [], function(d) {
				self.initData(d);
			});
		}
		
	});
});