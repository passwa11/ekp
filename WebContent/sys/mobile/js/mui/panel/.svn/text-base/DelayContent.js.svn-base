define("mui/panel/DelayContent", [ 
       "dojo/_base/declare", 
       "dojo/parser",
       "dojo/topic",
       "dojo/query",
       "dojo/dom-style",
       "mui/panel/Content"
], function(declare, parser, topic, query, domStyle, Content) {
	return declare('mui.panel.DelayContent', [ Content ], {
		
	    /* stopParser属性为true时，决定着DelayContent组件下的DOJO元素不会被立即解析，需等待后续手动调用去解析 */
		stopParser: true,
		
		/* DelayContent组件是否已经解析，默认为false */
		isParser: false,
		
		/* DelayContent组件是否已显示在首屏 (首屏的组件不做自动定位滑动) */
		isInFirstScreen: false,
		
		startup : function() {
			// 默认情况下隐藏DelayContent组件，等待触发初始化解析时设置为显示
			domStyle.set(this.domNode, "display", "none");

			this.inherited(arguments);
			
			// 监听初始化解析DelayContent组件事件
			this.subscribe('/mui/panel/delayContent/init', 'initDelayContent');
			
			// 监听完成解析DelayContent组件事件
			this.subscribe('/mui/panel/delayContent/loaded', 'loadedDelayContent');
		},
		
		
		/**
		* 初始化解析DelayContent容器的响应函数（触发解析DelayContent组件下的DOJO组件）
		* @param delayContent DelayContent对象(mui/panel/DelayContent)
		* @return
		*/
		initDelayContent: function(delayContent){
			if( delayContent==this && !this.isParser ){
				domStyle.set(this.domNode, "display", "inherit");
				this._initView();
				this.isParser = true;
			}
		},
		
		
		/**
		* 完成解析DelayContent容器的响应函数（触发通知mui/panel/NavPanel组件滑动滚动调至DelayContent容器的标题处）
		* (注：适用于组件包裹的内容需要异步请求后再渲染构建DOM元素，由于不知道异步请求什么时候处理完，因此需要由DelayContent包裹的内部组件来发布事件通知DelayContent已处理完成)
		* @param delayContent DelayContent对象(mui/panel/DelayContent)
		* @return
		*/
		loadedDelayContent: function(delayContent){
			if( delayContent==this && !isInFirstScreen ){
				var navPanel = this.getParent();
                var titleNode = query(".muiAccordionPanelTitle",navPanel.domNode);
				topic.publish("/mui/panel/navPanel/scroll",this.getParent(),{target:titleNode});
			}
		},
		
	    _initView: function() {
	        //异步请求资源，进行界面初始化
	    	var _self = this;
	        parser.parse(_self.domNode).then(function() {
	           topic.publish("/mui/list/resize");
	        });
	    }
		
	});
});