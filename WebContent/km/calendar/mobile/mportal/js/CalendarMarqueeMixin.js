define([ "dojo/_base/declare",
         "dojo/_base/array",
         "dojo/dom-style",
         "dojo/dom-class",
         "dojo/dom-construct",
         "dojo/query",
         "mui/util",
         'mui/calendar/CalendarUtil',
         "sys/mportal/mobile/OpenProxyMixin"
       ], function( declare, array, domStyle, domClass, domConstruct, query, util, calendarUtil, openProxyMixin ) {

	return declare("sys.mportal.CalendarMarqueeMixin", [openProxyMixin], {
		

		/**
		* 构建日程跑马灯内容
		* @param dataList 日程数据列表
		* @return
		*/
		generateMarquee : function(dataList) {
		  
		  // 日程跑马灯最外层容器DIV
	      var containerNode = domConstruct.create('div', { className : 'muiPortalCalendarMarqueeContainer' }, this.domNode);
	      
          // 绑定跑马灯部件点击事件
          this.proxyClick(containerNode, "/km/calendar/mobile/index.jsp", '_blank');
	      
	      // 左侧图标DIV
	      var iconNode = domConstruct.create('div', { className : 'muiPortalCalendarMarqueeIcon' }, containerNode);
	       
          if (dataList.length>0) {
        	  
        	  // 构建日程标题信息DOM元素
        	  var contentNode = domConstruct.create('div', { className : 'muiPortalCalendarMarqueeContent' }, containerNode);
	          var ulDom = domConstruct.create('ul', { className : 'muiPortalCalendarMarqueeItemContainer muiFontSizeM muiFontColorInfo' }, contentNode);
	          
	          // 循环构建跑马灯的子元素DOM 
	  		  array.forEach(dataList, function(itemData) {
                 this.createMarqueeChildItem( ulDom, itemData, calendarUtil.formatDate(new Date(),'yyyy-MM-dd'));
			  }, this);
  			
  			  // 超过一条数据时，绑定向上滑动跑马灯效果
               if(dataList.length>1){
            	  this.bindMarqueeUpEffect(ulDom);
              }
  			
          } else {
			  
			  // 创建无数据时的提醒  
			  var noDataTipDom = domConstruct.create('div', { className : 'muiPortalCalendarMarqueeNoDataTip muiFontSizeM muiFontColorInfo' }, containerNode);
			  noDataTipDom.innerText = "有日程安排？快去添加吧！";
			  
          }
          
          // 右侧快速跳转导航(箭头)图标
          var navIconNode = domConstruct.create('div', { className : 'muiPortalCalendarMarqueeArrowIcon' }, containerNode);
	      
		},
		
		
		
		/**
		* 构建跑马灯的子元素DOM
		* @param ulDom 跑马灯对应的DOM (HTML DOM Element对象) 
		* @param itemData 子元素JSON数据
		* @param todayDate 今天的日期（yyyy-MM-dd）
		* @return
		*/      
		createMarqueeChildItem: function( ulDom, itemData, todayDate ){
			
 			 var liDom = domConstruct.create('li', { className : 'muiPortalCalendarMarqueeItem' }, ulDom);
 			 
  			 // 构建开始时间DOM元素
  			 var timeSpanDom = domConstruct.create('span', { className : 'muiPortalCalendarMarqueeItemTime' }, liDom);
  			 var startTime = ""; // 日程开始时间（如果日程开始时间为当天，则仅显示小时和分钟）
  			 if(itemData.start){
  				var itemDate = calendarUtil.parseDate(itemData.start,'yyyy-MM-dd HH:mm');
  				var date = calendarUtil.formatDate(itemDate,'yyyy-MM-dd');
  				if(date==todayDate){
  					var time = calendarUtil.formatDate(itemDate,'HH:mm');
  					startTime = time;
  				}else{
  					startTime = itemData.start;
  				}
  			 }
  			 timeSpanDom.innerText = startTime;
  			 
  		     // 构建标题DOM元素
  			 var titleSpanDom = domConstruct.create('span', { className : 'muiPortalCalendarMarqueeItemTitle' }, liDom);
  			 titleSpanDom.innerText = dojo.string.trim(itemData.title);			
  			 
		},
		
		/**
		* 绑定向上滑动跑马灯效果（通过JS定时修改滚动位置scrollTop实现）
		* @param ulDom 跑马灯对应的DOM (HTML DOM Element对象) 
		* @return
		*/       
		bindMarqueeUpEffect: function(ulDom){
			
			(function(ulDom) {
				
				var liHeight = 26; // 单个日程项的高度（单位:px）
				
				var speed = 1000;  // 动画完成向上移动所需的时间（单位:ms）
				
				var delay = 3000;  // 每个日程数据停顿展示的时间间隔
				
				var transformUpStyle = {  // li元素向上滑动动画样式
						'transform' : 'translateY(-'+liHeight+'px)',
						'-webkit-transform': 'translateY(-'+liHeight+'px)', /* Safari 和 Chrome */
						'transition-duration': speed+'ms',
						'-webkit-transition-duration': speed+'ms'       /* Safari 和 Chrome */
				};
				
				var transformOriginalStyle = { // li元素原始动画样式
						'transform' : 'inherit',
						'-webkit-transform': 'inherit',           /* Safari 和 Chrome */
						'transition-duration': 'inherit',
						'-webkit-transition-duration': 'inherit' /* Safari 和 Chrome */
				}
				
				 function startMove()
				 {
						// 获取li标签元素DOM列表
						var liDomList = query(".muiPortalCalendarMarqueeItem",ulDom);
						
						// 第一个li标签元素对象
						var firstLiDom = liDomList[0];
						
						// 第二个li标签元素对象
						var secondLiDom = liDomList[1];
						
						// 设置第一个li标签元素向上移动动画样式
						domStyle.set(firstLiDom, transformUpStyle);
											
						// 设置第二个li标签元素向上移动动画样式
						domStyle.set(secondLiDom, transformUpStyle);			
						
						// 向上移动完成之后重置动画样式到原始状态,并将第一个li元素移动至ul元素末尾以便之后能循环滚动
						setTimeout(function(){ 
							domStyle.set(firstLiDom, transformOriginalStyle);
							domStyle.set(secondLiDom, transformOriginalStyle);
							ulDom.appendChild(firstLiDom); 
							setTimeout(function(){startMove();},delay);
						},speed+100);
				 }
				
				 setTimeout(function(){startMove();},delay);//初始化
				 
			})(ulDom);
			
	    }
		

		
	});
});