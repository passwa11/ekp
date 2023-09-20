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
	"dojo/topic",
	"sys/modeling/main/resources/js/mobile/listView/ModelingCalendarViewUtil",
	"dojo/query",
	"mui/calendar/CalendarView"
	], function(declare, lang, array, domClass, domAttr, domStyle, html, View, TransitionEvent, WidgetBase, Container, Contained, util, topic,ModelingCalendarViewUtil,query,CalendarView){
	
	return declare('sys.modeling.main.resources.js.mobile.calendar.ModelingCalendarView', [ WidgetBase, Container, Contained ], {

		baseClass : '',
		
		width : "100%",
		
		height : "4rem",
		
		templateChildNodes: null, // 子元素节点集合

		fdType : "",

		postCreate: function(){
			this.inherited(arguments);
			// 监听导航栏渲染完成事件
			this.subscribe('/mui/nav/onComplete', 'handleNavOnComplete');
			// 监听导航项切换事件
			this.subscribe('/mui/navitem/_selected', 'handleNavChanged');
		},
		
		handleNavOnComplete: function(widget, items){
			this.loadIndex = this.ensureloadIndex(widget);
			this.renderHeader(items, this.loadIndex);
		},

		handleNavChanged: function(item, data){
			if((item.channelName && item.channelName != this.channelName) ||
				item.key && item.key != this.key){
				return;
			}
			var currentNode = null;
			// 同步切换header视图
			if(this.getChildren().length > data.index){
				currentNode=this.getChildren()[data.index];
				new TransitionEvent(document.body, {
					moveTo: this.getChildren()[data.index].id
				}).dispatch();
			}
			var currentType ="";
			array.forEach(this.getChildren(), function(element){
				if(element.nodeType === "calendar" && element == currentNode){
					currentType = element.nodeType;
					return;
				}
			});
			var modelListView = query(".modelListView");
			if (currentType === "calendar"){
				domStyle.set(modelListView[0],"display","none");
			}else{
				domStyle.set(modelListView[0],"display","block");
			}
		},
		
		channelName : "",
		key:"",
		
		renderHeader: function(items, loadIndex){
			array.forEach(items, function(item, index){
				var view = new View({ 'class': 'muiCalendarContainer',"nodeType":item.listViewType });
				this.addChild(view);
				if(item.listViewType === "calendar"){
					var options = {
						key: this.channelName+index,
						container: view.domNode,
						index: index
					}
					var tmplStr = ModelingCalendarViewUtil.getCalendarViewHtml(item).trim();
					this.renderHeaderWithTemplate(tmplStr, options);
				}
			}, this);
			if(this.getChildren().length > loadIndex){
				this.defer(function(){
					new TransitionEvent(document.body, {
						moveTo: this.getChildren()[loadIndex].id
					}).dispatch();
				},1);
			}
		},

		renderHeaderWithTemplate: function(tmplContent, options){
			if(typeof(tmplContent)=='string'){
				tmplContent = tmplContent.trim();
			}
			if(!tmplContent || tmplContent.length==0){
				domStyle.set(options.container, 'display', 'none');
				return;
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
						}
					});
				}
			});
			node = dhs.set(tmplContent);
			dhs.parseDeferred.then(lang.hitch(this, function(widgetList){
				array.forEach(widgetList, function(child){
					if(child.isInstanceOf(CalendarView) && child.resize){
						child.resize();
					}
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
			if(hashLoadIndex){
				loadIndex = hashLoadIndex;
			}
			
			return loadIndex;
		}
		
	});

});