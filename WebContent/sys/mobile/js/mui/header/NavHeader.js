/**
 * 配置型头部，根据nav.json配置渲染头部
 */
define([
	'dojo/_base/declare',
	'dojo/_base/lang',
	'dojo/_base/array',
	'dojo/dom-class',
	'dojo/dom-attr',
	'dojo/dom-style',
	'dojo/html',
	'dojox/mobile/View',
	'dojox/mobile/TransitionEvent',
	'dijit/_WidgetBase',
	"dijit/_Container",
	"dijit/_Contained", 
	'mui/util',
	'./NavHeaderHashMixin',
	"dojo/topic"
	], function(declare, lang, array, domClass, domAttr, domStyle, html, View, TransitionEvent, WidgetBase, Container, Contained, util, NavHeaderHashMixin, topic){
	
	return declare('mui.header.NavHeader', [ WidgetBase, Container, Contained, NavHeaderHashMixin ], {
		
		baseClass : 'muiNavHeader',
		
		baseItemClass: 'muiHeaderItem',
		
		width : "100%",
		
		height : "4rem",
		
		templateChildNodes: null, // 子元素节点集合
		
		buildRendering : function() {
			this.inherited(arguments);
			if(!this.templateChildNodes){
				// 获取默认的模板子元素节点集合，并排除掉空格、注释等非元素节点
				this.templateChildNodes = [];
				var allChildNodes = this.domNode.childNodes;
				for(var i=0;i<allChildNodes.length;i++){
					var node = allChildNodes[i];
					if(node.nodeType==1){
						// 克隆模板子元素节点添加至子模板元素节点集合中
						this.templateChildNodes.push(node.cloneNode(true));
					}
				}
				this.domNode.innerHTML = '';
			}
		},
		
		postCreate: function(){
			this.inherited(arguments);
			// 监听导航栏渲染完成事件
			this.subscribe('/mui/nav/onComplete', 'handleNavOnComplete');
			// 监听导航项切换事件
			this.subscribe('/mui/navitem/_selected', 'handleNavChanged');
		},
		
		handleNavOnComplete: function(widget, items){
			var loadIndex = this.ensureloadIndex(widget);
			this.renderHeader(items, loadIndex);
		},
		
		handleNavChanged: function(item, data){
			if(item.channelName && item.channelName != this.channelName){
				return;
			}
			// 同步切换header视图
            if (this.getChildren()[data.index]) {
                new TransitionEvent(document.body, {
                    moveTo: this.getChildren()[data.index].id
                }).dispatch();
            }
            //#165145
            //window.cateFilterSetSelc标记分类所在页签切换
			//navId页签id
			//openCount分类窗口打开次数
			if (!window.cateFilterSetSelc) {
				window.cateFilterSetSelc = {navId:'',openCount:0};
			}
			if (window.cateFilterSetSelc.navId != this.getChildren()[data.index].id) {
				window.cateFilterSetSelc = {navId: this.getChildren()[data.index].id, openCount: 0};
				//#168157
				window._curIds = '';
			}
		},
		
		channelName : "",
		
		renderHeader: function(items, loadIndex){
			array.forEach(items, function(item, index){
				var view = new View({ 'class': 'muiHeaderView' });
				this.addChild(view);
				var options = {
					key: this.channelName+index,
					container: view.domNode,
					index: index
				}
				if(item.headerTemplate){
					// 先从配置的headerTemplate中获取
					var templateUrl = util.formatUrl(item.headerTemplate);
					require([templateUrl], lang.hitch(this, function(tmplStr){
						this.renderHeaderWithTemplate(tmplStr, options);
					}));
				}else if(item.headerTempStr){
					var tmplStr = item.headerTempStr.trim();
					this.renderHeaderWithTemplate(tmplStr, options);
				}else if(this.templateChildNodes){
					var cloneTemplateChildNodes = [];
					for(var i=0;i<this.templateChildNodes.length;i++){
						cloneTemplateChildNodes.push(this.templateChildNodes[i].cloneNode(true));
					}
					// 再从默认中获取
					this.renderHeaderWithTemplate(cloneTemplateChildNodes, options);
				}
			}, this);
			
			this.defer(function(){
				new TransitionEvent(document.body, {
					moveTo: this.getChildren()[loadIndex].id
				}).dispatch();
			},1);
			
		},
		
		renderHeaderWithTemplate: function(tmplContent, options){
			if(typeof(tmplContent)=='string'){
				tmplContent = tmplContent.trim();
			}
			if(!tmplContent || tmplContent.length==0){
				domStyle.set(options.container, 'display', 'none');
				return;
			}
			
			if (this.height != '') {
				domStyle.set(options.container, {
					"height" : this.height,
					"line-height" : this.height
				});
			}
			
			topic.publish('/mui/navView/resize',this,{key:options.index})
			
			var self = this;
			var dhs = new html._ContentSetter({
				node: options.container,
		        parseContent: true,
		        cleanContent: true,
		        setContent: function(){
		        	this.inherited("setContent",arguments);
		        	// 所有的子组件绑定key，避免不同视图下组件通信混乱
		        	array.forEach(this.node.childNodes, function(element){
						if(element.nodeType === 1){
							domAttr.set(element, 'key', options.key);
							domClass.add(element, self.baseItemClass);
						}
					});
		        }
			});
			node = dhs.set(tmplContent);
	        dhs.parseDeferred.then(lang.hitch(this, function(widgetList){
	        	array.forEach(widgetList, function(child){
//	        		domClass.add(child, this.baseItemClass);
	        	},this);
	        }));
	        dhs.tearDown();
		},
		
		/**
		 * 计算选中的Tab导航
		 */
		ensureloadIndex : function(widget){
			var loadIndex = 0;
			
			// 从selected中取 （为修复数据源selected参数无效的问题，使用selectedItem获取选中值）
			if(widget && widget.selectedItem){
				var items = widget.getChildren();
				loadIndex = array.indexOf(items, widget.selectedItem);
			}
			
			// 从localStoreage中取
			if (window.localStorage && this.canStore) {
				loadIndex = localStorage.getItem("swapIndex:" + location.pathname);
				if (loadIndex == null) {
					loadIndex = 0;
				} else {
					loadIndex = parseInt(loadIndex);
				}
			}
			
			var hashLoadIndex = this.inherited(arguments);
			if(hashLoadIndex !== null){
				loadIndex = hashLoadIndex;
			}
			
			return loadIndex;
		}
		
	});

});