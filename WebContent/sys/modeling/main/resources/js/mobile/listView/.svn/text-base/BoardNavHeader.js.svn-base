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
	'./BoardNavHeaderHashMixin',
	"dojo/topic",
	"./ModelingBoardNavBar"
	], function(declare, lang, array, domClass, domAttr, domStyle, html, View, TransitionEvent, WidgetBase, Container, Contained, util, BoardNavHeaderHashMixin, topic,ModelingBoardNavBar){
	
	return declare('sys.modeling.main.resources.js.mobile.listView.BoardNavHeader', [ WidgetBase, Container, Contained, BoardNavHeaderHashMixin ], {

		itemRenderer:ModelingBoardNavBar,

		baseClass : 'muiNavHeader boardNavHeader',
		
		baseItemClass: 'muiHeaderItem',
		
		width : "100%",
		
		height : "4rem",
		
		templateChildNodes: null, // 子元素节点集合

		fdType : "",

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
			// 监听看板视图导航渲染完成事件
			this.subscribe('/modeling/board/nav/onComplete', 'handleBoardNavOnComplete');

			this.subscribe('/modeling/board/group/select', 'handleBoardGroupSelect');
		},
		
		handleNavOnComplete: function(widget, items){
			this.loadIndex = this.ensureloadIndex(widget);
			this.renderHeader(items, this.loadIndex);
		},

		handleBoardNavOnComplete: function(widget, items){
			if(this.fdType !== "2"){
				return;
			}
			// 所有的子组件绑定key，避免不同视图下组件通信混乱
			array.forEach(widget.getChildren(), function(element){
				element.key = widget.key;
			});
			if(!document.body.classList.contains("boardBody")){
				document.body.classList.add("boardBody");
			}
		},

		handleNavChanged: function(item, data){
			if((item.channelName && item.channelName != this.channelName) ||
				item.key && item.key != this.key){
				return;
			}
			//不是看板视图直接返回
			if(this.fdType !== "2"){
				return;
			}
			// 同步切换header视图
			if(this.getChildren().length > data.index){
				this.loadIndex = data.index;
				new TransitionEvent(document.body, {
					moveTo: this.getChildren()[data.index].id
				}).dispatch();
			}
		},
		
		channelName : "",
		
		renderHeader: function(items, loadIndex){
			this.items = items;
			array.forEach(items, function(item, index){
				var view = new View({ 'class': 'muiHeaderView' });
				this.addChild(view);
				if(item.group){
					this.fdType = item.listViewType;
					var options = {
						key: this.channelName+index,
						container: view.domNode,
						index: index
					}
					var board = new this.itemRenderer(item);
					board.key = options.key;
					view.addChild(board);
					this.renderHeaderWithTemplate(board.group.groupField, options);
				}
			}, this);
			if(this.fdType ==="2" && this.getChildren().length > loadIndex){
				this.defer(function(){
					new TransitionEvent(document.body, {
						moveTo: this.getChildren()[loadIndex].id
					}).dispatch();
				},1);
			}
		},

		handleBoardGroupSelect:function(item){
			if(!item || !item.value){
				return;
			}
			this.getChildren().forEach(function(element){
				element.destroy();
			})
			array.forEach(this.items, function(ele, index){
				var view = new View({ 'class': 'muiHeaderView' });
				this.addChild(view);
				if(ele.group){
					this.fdType = ele.listViewType;
					var options = {
						key: this.channelName+index,
						container: view.domNode,
						index: index
					}
					ele.group.groupField = item.value;
					var board = new this.itemRenderer(ele);
					board.key = options.key;
					view.addChild(board);
					this.renderHeaderWithTemplate(board.group.groupField, options);
				}
			}, this);
			if(this.fdType ==="2" && this.getChildren().length > this.loadIndex){
				this.defer(function(){
					new TransitionEvent(document.body, {
						moveTo: this.getChildren()[this.loadIndex].id
					}).dispatch();
				},1);
			}
		},
		
		renderHeaderWithTemplate: function(groupField, options){
			if(typeof(groupField)=='string'){
				groupField = groupField.trim();
			}

			if(!groupField || groupField.length==0){
				domStyle.set(options.container, 'display', 'none');
				domStyle.set(options.container, {
					"height" : "0rem",
					"line-height" : "0rem"
				});
				return;
			}
			
			if (this.height != '') {
				domStyle.set(options.container, {
					"height" : this.height,
					"line-height" : this.height
				});
			}
			
			topic.publish('/mui/navView/resize',this,{key:options.index})
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