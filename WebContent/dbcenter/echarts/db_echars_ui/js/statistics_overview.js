seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
	
	var scrollWindow = null;

	/** 获取出现滚动条的父窗口window对象
	 * 注：因本页面同时供给前台和后台使用，而点击Tab自动切换至锚点的功能需要获取滚动条所属的元素来实现重置滚动条位
	 * 以及为了监听滚动条事件的时候确保监听的window对象正确
	 * 所以此方法实现递归向上查找出现滚动条的window对象（因目前系统中存在多重IFrame嵌套的情况）
	 * **/
	function getScrollWindow(s_window){
		var innerHeight = s_window.innerHeight || s_window.document.documentElement.clientHeight; // 页面可视高度
		var scrollHeight = s_window.document.body.scrollHeight; // 页面滚动高度
		
		if(scrollHeight>innerHeight){
			return s_window;
		}else{
			var parentWindow = s_window.parent;
			if(parentWindow){
				return getScrollWindow(parentWindow);
			}else{
				return s_window;
			}	
		}
		
	}
	
	/**------ 等待500毫秒之后再获取出现滚动条的父窗口（避免父窗口绑定的load事件没处理完导致获取出现滚动条的父窗口不准确，父窗口有可能会在onLoad事件中重置嵌入IFrame的宽、高） ------ **/
	setTimeout(function(){
		scrollWindow = getScrollWindow(window);
		bindScrollWindowScrollEvent(scrollWindow);
	},500);

	/**------ 因 滚动条在父窗口的情况下，“position:fixed;” 样式在iframe窗口中不起作用，下面这段逻辑是为了兼容滚动条滚动时悬浮Tab能正常显示 ------ **/
    function bindScrollWindowScrollEvent(scrollWindow){
    	$(scrollWindow).scroll(function(){
    		var scrollTop = $(this).scrollTop();
    		var suspendTabPanel = $(".suspend_overview_tab_panel"); // 悬浮Tab面板
    		var overviewTabPanel = $(".overview_tab_panel");        // 正常的Tab面板
    		// 当正常Tab被滚动条拖出可视区域时，显示悬浮Tab面板
            if(scrollTop>overviewTabPanel.height()+10){
            	suspendTabPanel.show();
            	if(scrollWindow != window){
                	suspendTabPanel.css({
    	  			       top : $(this).scrollTop()-10
    	  			});
            	}
            	
    			// 定义包含页面中所有的锚点ID的数组，迭代判断当前滚动条所处的锚点范围，根据位置动态切换悬浮Tab选中状态
    			var anchorPointIdArray = ["chart_type_title","config_method_title","app_scenario_title"];
    			for(var i=0; i<anchorPointIdArray.length; i++){
    				var anchorPointId = anchorPointIdArray[i];
    				var thisOffsetTop = $("#"+anchorPointId).offset().top;
    				var nextOffsetTop = i<anchorPointIdArray.length-1 ? $("#"+anchorPointIdArray[i+1]).offset().top : scrollWindow.document.body.scrollHeight;
        		    if(scrollTop>=thisOffsetTop-200 && scrollTop<nextOffsetTop){
        		    	// 获取悬浮Tab中需要置为选中样式的项
        		    	var selectedTarget = $(".suspend_overview_tab[anchor_point_id='"+anchorPointId+"']").get(0);
        			    // 切换悬浮Tab选中样式
        			 	switchSuspendTabSelected(selectedTarget);
        		    }	
    			}

            }else{
            	suspendTabPanel.hide();
            }

    	});    	
    };

	
	
	/**------ 绑定悬浮 Tab 点击事件 ------ **/
    $(".suspend_overview_tab").on("click",function(){
       var currentTarget = this;   
 	   // 获取Tab对应的锚点ID，并调用滑动至锚点
 	   var anchorPointId = $(this).attr("anchor_point_id");
 	   swipeToAnchorPoint(anchorPointId);
	    
 	}); 
	
	
    /**------ 根据选中的悬浮Tab切换选中样式  ------ **/
	window.switchSuspendTabSelected = function(selectedTarget){
		var selectedClass = "suspend_overview_tab_selected";
 	    $(".suspend_overview_tab").each(function(){
 		    if(selectedTarget==this){
 		    	if($(this).hasClass(selectedClass)==false){
 		    		$(this).addClass(selectedClass);
 		    	}
 		    }else{
 		    	$(this).removeClass(selectedClass);
 		    }
 	    });	
    };

	
	/**------ 绑定顶部 引导 Tab 点击事件 ------ **/
    $(".tab_category").on("click",function(){
       var currentTarget = this;
       var selectedClass = "tab_category_selected";
       
       // 切换选中样式
 	   $(".tab_category").each(function(){
 		    if(currentTarget==this){
 		    	if($(this).hasClass(selectedClass)==false){
 		    		$(this).addClass(selectedClass);
 		    	}
 		    }else{
 		    	$(this).removeClass(selectedClass);
 		    }
 	   });
 	   
 	   // 获取Tab对应的锚点ID，并调用滑动至锚点
 	   var anchorPointId = $(this).attr("anchor_point_id");
 	   swipeToAnchorPoint(anchorPointId);
	    
 	}); 

	
    /**------ 根据锚点元素ID滑动滚动条至锚点处  ------ **/
	window.swipeToAnchorPoint = function(anchorPointId){
		   // 获取锚点距离内窗口顶部的高度
		   var offsetTop = $("#"+anchorPointId).offset().top; 
		   // 滑动滚动条至锚点
		   if(scrollWindow == window.top){
			   offsetTop = offsetTop-30;
		   }else{
			   offsetTop = offsetTop-40;
		   }
		   scrollWindow.$('html,body').animate({  scrollTop: offsetTop  },  500);  // 减去的30像素是为了给浮动Tab留出展示空间
    };
    
    
    /**------ 绑定图表应用场景左侧菜单点击事件 ------ **/
    $(".scenario_menu_item").on("click",function(){
	       var currentTarget = this;
	       var selectedClass = "scenario_menu_item_selected";
	       
	       // 切换选中样式
	 	   $(".scenario_menu_item").each(function(){
	 		    if(currentTarget==this){
	 		    	if($(this).hasClass(selectedClass)==false){
	 		    		$(this).addClass(selectedClass);
	 		    	}
	 		    }else{
	 		    	$(this).removeClass(selectedClass);
	 		    }
	 	   });
	       
	       // 切换右侧截屏展示区域的图片
	       var imageName = null;
	       var scenarioIndex = $(this).attr("scenario_index"); 
	       switch(scenarioIndex)
	       {
	         case '1':
	           imageName = "scenario_1";
	           break;
	         case '2':
	           imageName = "scenario_2";
	           break;
	         case '3':
	           imageName = "scenario_3";
		       break;
	         case '4':
	        	 imageName = "scenario_4";
	        	 break;
	         default:
	    	   imageName = "scenario_1";
	       }
	       $(".scenario_screenshot").css("background-image","url(./images/"+imageName+".png)");
	       
	       // 切换右侧截屏展示区域的描述文字
	 	   $(".scenario_text").each(function(){
	 		    if($(this).attr("text_index")==scenarioIndex){
	 		    	$(this).show();
	 		    }else{
	 		    	$(this).hide();
	 		    }
	 	   });
    });
    
    
    /**------ 新建  自定义数据  ------ **/
	window.addCustomData = function(){
			var addUrl = Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=add";
			Com_OpenWindow(addUrl);	
		};
		
		
		/**------ 新建  统计图表  ------ **/
	window.addStatisticChart = function(){
			var url = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart_mode.jsp?fdTemplateId="+categoryId;
			var height = "455";
		    var width = "600";
			dialog.iframe(url,"选择模式",null,{width:width,height : height});	
		};

		
		/**------ 新建  统计列表  ------ **/
	window.addStatisticList = function(){
			var url = "/dbcenter/echarts/db_echarts_table/dbEchartsTable_mode.jsp?fdTemplateId="+categoryId;
			var height = "455";
		    var width = "600";
			dialog.iframe(url,"选择模式",null,{width:width,height : height});
		}
		
		
		/**------ 新建  统计图表集  ------ **/
		window.addChartSet = function(){
			var addUrl = Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=add";
			Com_OpenWindow(addUrl);	
		};
    
		
});