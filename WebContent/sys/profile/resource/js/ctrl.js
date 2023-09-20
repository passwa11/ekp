seajs.use(['lui/jquery'],function($){
	
	//帧结构集级别与帧结构名称的映射表
	var frameMap = [];
	frameMap["treeFrame"] = 1;
	frameMap["orgFrame"] = 2;
	frameMap["viewFrame"] = 3;
	frameMap["docFrame"] = 4;
	frameMap[1] = "treeFrame";
	frameMap[2] = "orgFrame";
	frameMap[3] = "viewFrame";
	frameMap[4] = "docFrame";
	
	/**
	 * 获取Frame的索引
	 */
	function Frame_GetIndex(frameWin){
		var name = Frame_GetName(frameWin);
		return frameMap[name];
	}
	
	/**
	 * 获取Frame的名字
	 */
	function Frame_GetName(frameWin){
		return frameWin.window == frameWin ? frameWin.name : frameWin;
	}
	
	/**
	 * 打开一个窗口，提供给Com_OpenWindow调用
	 */
	function Frame_OpenWindow(frameWin, url, target, winStyle){
		var srcWin = Frame_GetIndex(frameWin);
		if(target==null || target==""){
			target = srcWin == 1 ? 3 : srcWin + 1;
		}else{
			target = parseInt(target);
		}
		if(target > 3)
			return window.open(url, "_blank");
		var i;
		if(winStyle!="remain"){
			if(target == 2){
				Frame_SetLV2FrameSize(180);
			}else{
				if(srcWin < 2 || target < 2){
					setTimeout(function(){
						Frame_SetLV2FrameSize(0);
					},1000);
				}
			}
			if(target < 4){
				i = 2;
			}else{
				if(srcWin < 3)
					i = -2;
				else{
					switch(winStyle){
						case "max": i = -1;	break;
						case "mid": i = 0; break;
						case "min": i = 1; break;
						default:
							if(srcWin == 4){
								i = null;
							}else{
								i = 0;
							}
					}
				}
			}
			i = Math.min(srcWin, target);
			for(i=i+1; i<5; i++)
				window.open("about:blank", frameMap[i]);
		}
		return window.open(url, frameMap[target]);
	}
	
	/**
	 * 执行Ctrl的移动动作
	 */
	function Frame_MoveBy(ctrlName, direct){
		function setLeftCSS(_ctrlName){
			var downChildFrame = $('[data-frame="downFrame"]').children(),
				ctrlFrame = $('[data-frame="'+ _ctrlName +'"]'),
				index = ctrlFrame.index();
			downChildFrame.each(function(_index,elem){
				if(_index < index - 1)
					return;
				var that= $(this);
				if(_index == index -1){
					var width = that.width();
					width = width + direct * 180 ;
					if(width < 0 || width > 360){
						return false;
					}
					that.width(width);
					var toLeft = ctrlFrame.find(".toLeft");
					var toRight = ctrlFrame.find(".toRight");
					if(width < 180){
						// 隐藏左则开关
						toLeft.hide();
					}else if(width > 180){
						// 隐藏右则开关
						toRight.hide();
					}else{
						// 显示2个开关
						toLeft.show();
						toRight.show();
					}
				}else{
					left = that.position().left || 0;
					left = parseInt(left) + direct * 180 ;
					that.css('left', left + 'px');
				}
			});
		}
		switch(ctrlName){
			case 'ctrl1Frame' : 
				setLeftCSS(ctrlName);
				break;
			case 'ctrl2Frame' : 	
				setLeftCSS(ctrlName);
				break;	
			case 'ctrl3Frame' : 
				break;	
		}
	}
	
	/**
	 * 执行Ctrl的拖拽动作
	 */
	function Frame_DragBy(ctrlName,size){
		switch(ctrlName){
			
		
		
		}
	}
	
	/**
	 * 	功能：设置二级窗口的大小
	 *	参数：
	 *		size：窗口大小
	 *		remainCtrl：是否保留控制帧
	 */
	function Frame_SetLV2FrameSize(size, remainCtrl){
		var downFrame = $('[data-frame="downFrame"]'),
			orgFrame = downFrame.find('[data-frame="orgFrame"]'),
			ctrl2Frame = downFrame.find('[data-frame="ctrl2Frame"]'),
			rightFrame = downFrame.find('[data-frame="rightFrame"]'),
			rightFrameLeft = 0;
		orgFrame.width(size);
		rightFrameLeft = orgFrame.position().left + size + 10;
		rightFrame.css('left',rightFrameLeft + 'px' );
		if(!remainCtrl){
			var _width = size == 0 ? 0 : 10;
			ctrl2Frame.width(_width);
			rightFrame.css('left',rightFrameLeft + _width + 'px' );
		}
		return size;
	}
	
	window.Frame_OpenWindow = Frame_OpenWindow;
	window.Frame_MoveBy = Frame_MoveBy;
	window.Frame_DragBy = Frame_DragBy;
});