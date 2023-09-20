/**
 *  带有Tab页签的Panel组件
 * (构建的Tab页签支持点击自动定位到Content)
 */
define("mui/panel/NavPanel", [
	    "dojo/_base/declare",
	    "dojo/_base/lang",
	    "dojo/store/Memory",
	    "dojo/dom-construct",
	    "dojo/dom-class",
	    "dojo/dom-style",
	    "dojo/topic",
	    "dojo/query",
        "dojo/window",
	    "dojo/dom-geometry",
	    "dijit/registry",
	    "mui/panel/AccordionPanelMixin",
	    "mui/fixed/Fixed",
	    "mui/fixed/FixedItem",
	    "mui/panel/NavPanelBar",
        "mui/panel/DelayContent"
	], function(declare, lang, Memory, domConstruct, domClass, domStyle, topic, query, win, domGeometry, registry, AccordionPanelMixin, Fixed, FixedItem, NavPanelBar, DelayContent) {
		
	return declare("mui.panel.NavPanel", [ AccordionPanelMixin ], {
		
		/* 页签栏默认高度    */
		navBarHeight: '4.4rem',
		
		/* 当前选中的页签索引值    */
		selectedNavIndex: 0,
		
		/* 页签栏Fixed定位排序号    */
		fixedOrder: 0,
		
		
		startup : function() {
			this.inherited(arguments);
			
			// 读取“mui/panel/Content”对象数组封装页签数据
			var navStoreData = [];
			if(this.contentList){
				for(var i=0;i<this.contentList.length;i++){
					var contentClaz = this.contentList[i].claz;
					var data = {
						text: contentClaz.title,
						key: i
					};
					navStoreData.push(data);
				}
			}
			
			// 页签数量大于1个时才构建页签栏
			if(navStoreData.length>1){
				
				// 获取第一个“mui/panel/Content”组件下的标题DOM Node元素，并将其设置为隐藏状态，同时在其上方添加一个不占高度的空div占位作为滑动滚动条过程钟标题计算时的元素（需求：第一个页签下不显示组件标题，但需要可以点击页签自动定位）
				var firstTitleNode = this.titleList[0];
				domStyle.set(firstTitleNode, 'display', 'none');
				var virtualNodeTitle = domConstruct.create('div',{ className: 'muiAccordionVirtualTitleLine' });
				domConstruct.place(virtualNodeTitle,firstTitleNode,'before');
				this.titleList[0] = virtualNodeTitle;
				
				// 页签栏的页签数据对象
				var barStore = new Memory({
				      data: navStoreData
				});
				
				// 实例化一个fixed对象（用于定位页签吸顶效果）
				this.fixed = new Fixed({ fixedOrder:this.fixedOrder });
				domConstruct.place(this.fixed.domNode,this.domNode,'first');
				
				// 实例化一个fixedItem对象（用于定位页签吸顶效果）
				this.fixedItem = new FixedItem({ baseClass : 'muiFlowFixedItem'});
				domConstruct.place(this.fixedItem.domNode,this.fixed.domNode,'first');

				// 实例化一个页签栏对象
				this.navPanelBar = new NavPanelBar({ store:barStore, height:this.navBarHeight });
				domConstruct.place(this.navPanelBar.domNode,this.fixedItem.domNode,'first');
				
				// 启动页签栏
				this.fixed.startup();
				this.fixedItem.startup();
				this.navPanelBar.startup();
				
				//实力化其他内容
				if(this.buildOtherItems)
					this.buildOtherItems();

				// 绑定NavPanel组件相关的事件订阅及监听
	            this.bindNavPanelListener();
			}
			
		},
		
		
		/**
		* 绑定NavPanel组件相关的事件订阅及监听
		*/	
		bindNavPanelListener: function(){
			
			// 滚动条滚动事件响应函数对象
			this.handleScrollEventFunc = lang.hitch(this, 'handleScrollEvent');
			
			this.defer(function() {
				// 监听导航页签项切换事件
				this.subscribe('/mui/navitem/_selected', 'handleNavChanged');
				// 监听浏览器滚动条滚动事件
				window.addEventListener('scroll', this.handleScrollEventFunc);
			}, 500);

			// 监听NavPanel组件滑动滚动条事件，提供给外部滑动至指定目标DOM位置的能力
			this.subscribe('/mui/panel/navPanel/scroll', 'handleNavPanelScroll');
			
			// 发布事件选中第一个页签（此处适当延时的原因是页签栏构建页签项时有进行延迟，此处必须等待页签栏构建完毕，详见：_StoreNavBarMixin.js(startup)）
			this.defer(function() {
				var navItem = this.navPanelBar.getChildren()[this.selectedNavIndex];
				topic.publish("/mui/navitem/changedSelected", navItem, {key:this.selectedNavIndex});
			}, 50);
			
			// 如果延迟加载的子组件DelayContent出现在首屏（页面初始化加载完成后的第一屏），则直接解析加载
			this.defer(function() {
				var selectedNavIndex = this.getSelectedNavIndex();
				for(var i=0; i<this.contentList.length; i++){
					if(i<=selectedNavIndex){
						// 通知延迟加载的DelayContent（mui/panel/DelayContent）组件进行解析
						var contentClaz = this.contentList[i].claz;
						if(contentClaz instanceof DelayContent){
							contentClaz.isInFirstScreen = true;
							topic.publish("/mui/panel/delayContent/init", contentClaz);
						}	
					}
				}
			}, 100);
			
		},
		
		
		
		/**
		* 处理导航页签项切换时滚动条模拟动画滑动至页签对应的Content标题处
		* @param item 页签对象(mui/nav/NavItem)
		* @param data 页签数据{url、text、index....}
		* @return
		*/	
		handleNavChanged: function(item, data){
			// 当点击的页签是NavPanel下navPanelBar页签栏的子元素时，才处理滑动逻辑
			var navItems = this.navPanelBar.getChildren();
			if(navItems && navItems.length>0){
				var isChildren = false;
				for(var index in navItems){
					if(navItems[index]==item)
						isChildren = true;
				}
				if(!isChildren)return;
			}else{
				return;
			}
			
			// 通知延迟加载的DelayContent（mui/panel/DelayContent）组件进行解析
			var contentClaz = this.contentList[data.index].claz;
			if(contentClaz instanceof DelayContent){
				topic.publish("/mui/panel/delayContent/init", contentClaz);
			}

			// 获取选中的页签对应的Content标题DOM
			var selectedTitleNode = this.titleList[data.index];
			
			// 滑动至标题DOM元素位置
			this._scrollToNode(selectedTitleNode);
            
            this.selectedNavIndex = data.index;
		},
		
		
		/**
		* NavPanel组件滑动滚动条事件响应函数
		* @param navPanel NavPanel对象(mui/panel/NavPanel)
		* @param data 页签数据{target}
		* @return
		*/			
		handleNavPanelScroll: function(navPanel,data){
			if( navPanel==this && data && data.target ){
				this._scrollToNode(data.target);
			}
		},
		
		
		/**
		* 滑动至指定的元素位置
		* @param node 定位元素DOM
		* @return
		*/		
		_scrollToNode: function(node){
			// 获取标题DOM的纵向(Y轴)坐标滚动位置
			var toWinTop = domGeometry.getContentBox(node).t;
			
			// 获取当前滚动条所处的滚动位置
            var currWinTop = win.getBox().t;
            
            // 滑动至页签对应的标题处（通过setInterval模拟滑动效果）
            var _self = this;
            var stepTimeScroll = 10;  // 每次Interval滑动的步长 

            if(toWinTop>currWinTop){  
            	// 【   向下滑动  】
            	window.removeEventListener('scroll', _self.handleScrollEventFunc); // 移除浏览器滚动条滚动监听事件
    			var time_id = setInterval(function(){
    				toWinTop = _self._getToWinTop(node);
    				if( toWinTop-currWinTop <= stepTimeScroll || currWinTop>=document.documentElement.scrollHeight ){
                    	_self._setScrollTop(toWinTop);
                    	clearInterval(time_id);
                    	setTimeout(function(){ window.addEventListener('scroll', _self.handleScrollEventFunc); },100);	// 重新绑定浏览器滚动条滚动监听事件
    				}else{
    					currWinTop += stepTimeScroll;
    					_self._setScrollTop(currWinTop);
    					if(stepTimeScroll<50)stepTimeScroll++;
    				}
    		    },1);
            }else if(toWinTop<currWinTop){
            	// 【   向上滑动  】
            	window.removeEventListener('scroll', _self.handleScrollEventFunc); // 移除浏览器滚动条滚动监听事件
    			var time_id = setInterval(function(){
    				toWinTop = _self._getToWinTop(node);
    				if( currWinTop-toWinTop <= stepTimeScroll || currWinTop<=0 ){
	                   	 _self._setScrollTop(toWinTop);
	                	 clearInterval(time_id);
	                     setTimeout(function(){ window.addEventListener('scroll', _self.handleScrollEventFunc); },100);	// 重新绑定浏览器滚动条滚动监听事件    					
    				}else{
        				currWinTop -= stepTimeScroll;
        				_self._setScrollTop(currWinTop);
        				if(stepTimeScroll<50)stepTimeScroll++;
    				}
    		    },1);            	
            }			
		},
		
		
		/**
		* 设置滚动条位置
		* @param value  滚动位置scollTop值(Y轴滚动条)
		* @return
		*/
		_setScrollTop: function(value){
            document.body.scrollTo(0,value);
            document.documentElement.scrollTo(0,value);
		},
		
		
		/**
		* 根据标题DOM获取offset top坐标位置
		* @param selectedTitleNode 标题DOM
		* @return 返回页面需要滑动至的坐标位置（offsetTop）数值
		*/		
		_getToWinTop: function(selectedTitleNode){
            if(domClass.contains(this.fixed.domNode,"muiFixed")){
            	return domGeometry.getContentBox(selectedTitleNode).t - domGeometry.getContentBox(this.fixed.domNode).h;
            }else{
            	return domGeometry.getContentBox(selectedTitleNode).t
            }
		},
		
		
		/**
		* 判断DOM元素是否可见
		* @param node DOM元素
		* @return boolean
		*/			
		_isVisible: function(node){
			var visible = function(dom){
				return domStyle.get(dom, "display") !== "none";
			};
			for(var n = node; n.tagName !== "BODY"; n = n.parentNode){
				if(!visible(n)){ return false; }
			}
			return true;
		},
		
		
		/**
		* 处理浏览器滚动时，根据当前滚动条停留的位置设置页签的选中状态
		* @param evt  window.event对象
		* @return
		*/
		handleScrollEvent: function(evt){
			
			// 获取滚动条所处的位置对应应该选中的页签索引值
			var selectedNavIndex = 0;
            if(win.getBox().t>1){ // 屏幕滚动位置小于1时，页签选中第一个
            	selectedNavIndex = this.getSelectedNavIndex();
            }
		    
			if(this.selectedNavIndex!=selectedNavIndex){
				
				// 设置页签选中状态
				var navItem = this.navPanelBar.getChildren()[selectedNavIndex];
				topic.publish("/mui/navitem/changedSelected", navItem, {key:selectedNavIndex});
				
				// 通知延迟加载的DelayContent（mui/panel/DelayContent）组件进行解析
				var contentClaz = this.contentList[selectedNavIndex].claz;
				if(contentClaz instanceof DelayContent){
					topic.publish("/mui/panel/delayContent/init", contentClaz);
				}
				
				this.selectedNavIndex = selectedNavIndex;
			}

		},
	
		
		/**
		* 获取页面底部fixed元素的高度（指浮动在页面底部的元素（TabBar）高度）
		* @return 返回页面底部fixed元素的高度
		*/		
		getFixedBottomHeight: function(){
			var bottomHeight=0;
			var fixedBottomDomArray = query("[fixed='bottom'].muiViewBottom");
			if(fixedBottomDomArray.length>0){
				var fixedBottomDom = fixedBottomDomArray[0];
				if(this._isVisible(fixedBottomDom)){
					bottomHeight = domGeometry.getContentBox(fixedBottomDom).h;
				}
			}
			return bottomHeight;
		},
		
		
		/**
		* 获取滚动条所处的位置对应应该选中的页签索引值
		* @return 返回页签索引
		*/	
		getSelectedNavIndex: function(){
			var selectedNavIndex = 0;
			
			// 获取底部fixed元素的高度（指浮动在页面底部的元素（TabBar）高度）
			var bottomHeight = this.getFixedBottomHeight();
			
			var winBox = win.getBox();
			
			// 当前滚动条所处的滚动位置
            var currWinTop = winBox.t;
           	
            // 当前window窗口的高度
            var currWinHeight = winBox.h;
			
			var titleDomList = this.titleList;
			
			if(titleDomList.length>0){
				for(var i=0;i<titleDomList.length;i++){
					// 分类标题DOM
					var titleDom = titleDomList[i];
					// 分类标题坐标、宽高信息
					var titleCBox= domGeometry.getContentBox(titleDom);
					// 分类标题OffsetTop坐标位置
					var titleOffsetTop = titleCBox.t;
					// 分类标题DOM元素高度
					var titleHeight = titleCBox.h;
					var fixedOffsetTop = 0;
					if(domClass.contains(this.fixed.domNode, "muiFixed"))
						fixedOffsetTop = this.fixed.domNode.offsetTop;
					
				    // 分类标题DOM元素的最底部的OffsetTop坐标位置
					var titleBottomOffsetTop = titleOffsetTop + bottomHeight + titleHeight + fixedOffsetTop  - currWinHeight;
					
					// 分类标题DOM元素的最底部与当前滚动条所处的滚动位置的距离(计算此值是为了在一定精度范围内（大于0且小于1）容错)
					var distanceNum = titleBottomOffsetTop - currWinTop; 
					
					// 判断是否标题是否进入底部可视范围
					if( currWinTop >= titleBottomOffsetTop || (distanceNum>0 && distanceNum<1) ){
						selectedNavIndex = i;
					} else {
						break;
					}
				}	
			}            	
          
			return selectedNavIndex;
		}

		
	});
});