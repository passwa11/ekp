Com_RegisterFile("draw.js");
$(function() {
	var wsocket;  // WebSocket对象
	var ispendown = false;
	var lastX = -1;  // 笔最后写字的X坐标
	var lastY = -1;  // 笔最后写字的Y坐标
	var lastCusorX = -1;  //笔悬浮位置(就是圆圈的位置)的X坐标
	var lastCusorY = -1;  //笔悬浮位置(就是圆圈的位置)的Y坐标
	/**
	 * 对于一个标准的A4PDF文件（宽=210mm，高=297mm)，可得出对应的点阵页面尺寸为：
	 * 宽=210/0.3*8=5600AU，高=297/0.3*8=7920AU
	 */
	
	var pageLeft = 285;  //打印页面左/右边距
	var pageTop = 280;  //打印页面上/下边距
	var pageWidth = 5600 - 2*pageLeft;  //打印页面宽度
	var titleHeight = 395;  // 标题高度
	var textWidth = pageWidth * 0.15;  //左边文字宽度，15%
	var marginLeft; //手写坐标原点X偏移量
	var marginTop;  //手写坐标原点Y偏移量
	var dotWidth;   //手写覆盖内容宽度
	var dotHeight;  // 手写覆盖内容高度，以点阵单位(AU)计算，1AU=0.3/8mm（毫米）
	
	var tryCount=0;
	
	var drawStrokeId = "drawStrokeCanvas";  // 绘制字迹的canvas的ID
	var drawCircleId = "drawCircleCanvas";  // 绘制笔位置的canvas的ID
	
	var drawDiv;  //当前手写的div
	
	var drawCanvas;
	
	var Xrate;  // 水平方向比率
	var Yrate = 5.19;  // 垂直方向比率
	
	var initParam = function() {
		var s_table = drawDiv.parents("table[fd_type='standardTable']");
		var lattice_x = getOffsetTopByBody(drawDiv[0]).left-getOffsetTopByBody(s_table[0]).left;
		var lattice_y = getOffsetTopByBody(drawDiv[0]).top-getOffsetTopByBody(s_table[0]).top;
		var left = lattice_x - 8;
		var top = lattice_y - 8;
		dotWidth = (drawDiv.width()+16)*Xrate;
		dotHeight = (drawDiv.height()+16)*Yrate;
		titleHeight = 78 * Yrate;
		marginLeft = left * Xrate + pageLeft;
		marginTop = top * Yrate + pageTop;
		kmss_console_log("divWidth : "+drawDiv.width());
		kmss_console_log("divHeight : "+drawDiv.height());
		kmss_console_log("left : "+left);
		kmss_console_log("top : "+top);
		kmss_console_log("dotWidth : "+dotWidth);
		kmss_console_log("dotHeight : "+dotHeight);
		kmss_console_log("marginLeft : "+marginLeft);
		kmss_console_log("marginTop : "+marginTop);
	};
	var hasInit = false;
	window.handInit = function() {
		if($("div[lattice='true']").length > 0 && !hasInit) {
			$("<div style=\"display:none;\"><a id=\"hand_link\" href=\"latticeWebSocket://\">dfa</a></div>").appendTo($(document.body));
			initWebSocket();
			handDraw();
			Com_Parameter.event["confirm"].unshift(handSaveAsImage);
			hasInit = true;
		}
	};
	
	function getOffsetTopByBody(el) {
		  var offsetTop = 0;
		  var offsetLeft = 0;
		  while (el && el.tagName !== 'BODY') {
		    offsetTop += el.offsetTop;
		    offsetLeft += el.offsetLeft;
		    el = el.offsetParent;
		  }
		  return {left:offsetLeft,top:offsetTop};
	}
	
	// 当前的写字的canvas的宽度
	var nowCanvasWidth = 0;
	var nowCanvasHeight = 0;
	
	function handDraw() {
		drawDiv = $("div[lattice='true']").parent().css("position","relative");
		var tableWidth = drawDiv.parents("table[fd_type='standardTable']").outerWidth();
		kmss_console_log("tableWidth : "+tableWidth);
		Xrate = (5600 - 2 * pageLeft) / tableWidth;
		kmss_console_log("rate : "+Xrate);
		initParam();
		nowCanvasWidth = drawDiv.width();
		nowCanvasHeight = drawDiv.height();
		drawCanvas = $("<canvas id='"+drawStrokeId+"'></canvas><canvas id='"+drawCircleId+"'></canvas>").css({
			'position':'absolute',
			'left': 0,
			'top': 0,
			'z-index': 100,
		}).attr("width",drawDiv.width()).attr("height",drawDiv.height()).appendTo(drawDiv);
		$("#"+drawStrokeId).css("box-shadow","rgba(60,64,67,.3) 0 1px 2px 0,rgba(60,64,67,.15) 0 1px 3px 1px");
		alwaysCheckWidthAndHeight();
	}
	
	// 由于页面时异步加载，无法监听页面加载完毕事件，这里采用死循环处理canvas的宽度问题
	function alwaysCheckWidthAndHeight() {
		var realWidth = drawDiv.width();
		var realHeight = drawDiv.height();
		if(nowCanvasWidth !== realWidth) {
			nowCanvasWidth = realWidth;
			$("#"+drawStrokeId+",#"+drawCircleId).attr("width",realWidth);
		}
		if(nowCanvasHeight !== realHeight) {
			nowCanvasHeight = realHeight;
			$("#"+drawStrokeId+",#"+drawCircleId).attr("height",realHeight);
		}
		setTimeout(alwaysCheckWidthAndHeight,500);
	}
	
	window.handSaveAsImage = function() {
		var oCanvas = document.getElementById(drawStrokeId);
		if(oCanvas != null) {
			var strDataURI = oCanvas.toDataURL();
			drawDiv.find("img.latticeImg")[0].src = strDataURI;
			drawDiv.find("[name='latticeValue']").val(strDataURI);
			// 释放资源
			drawCanvas.remove();
			wsocket.close();
		}
		return true;
	}
	
	/**
	 * 初始化WebSocket连接
	 * @returns
	 */
	function initWebSocket(){
		var linkstr = "ws://"+latticeWebSocketIp+":9001";
		tryCount++;
		if("WebSocket" in window) {
			wsocket = new window.WebSocket(linkstr);
		} else if("MozWebSocket" in window) {
			wsocket = new window.MozWebSocket(linkstr);
		} else {
			wsocket = new window.WebSocket(linkstr);
		}
		
		ConnectWebsocket();
	}
	/**
	 * 用URL Protocol的方式打开服务
	 * @returns
	 */
	function OpenWebSocketServer(){
		document.getElementById('hand_link').click();
		kmss_console_log("URL Protocol打开服务");
		setTimeout(initWebSocket,4000);
	}
	/**
	 * 绑定WebSocket事件（监听），绘制字迹
	 * @returns
	 */
	function ConnectWebsocket(){
		/**
		 * 建立连接
		 */
		wsocket.onopen = function(){
			kmss_console_log("建立连接了！！！")
		};
		/**
		 * 连接失败
		 */
		wsocket.onerror = function(msg) {
			kmss_console_log("连接失败："+msg);
			if(tryCount==1){
				kmss_console_log("点击打开")
				OpenWebSocketServer();
			}else{
				kmss_console_log("初始化websocket")
				initWebSocket();
			}
		};
		wsocket.onclose=function(msg){
			
		};
		/**
		 * 连接成功，开始通信
		 */
		wsocket.onmessage = function(msg){
			var jobj = JSON.parse(msg.data);
			switch(jobj.Event){
				// 点阵笔连接事件
				case "Conn":{
					kmss_console_log("点阵笔连接");
					ispendown=false;
					break;
				}
				// 点阵笔断开连接事件
				case "DisConn":{
					kmss_console_log("点阵笔断开连接");
					ispendown=false;
					break;
				}
				// 点阵笔落笔事件
				case "PenDown":{
					kmss_console_log("点阵笔落下");
					ispendown = true;
					break;
				}
				// 点阵笔抬笔事件
				case "PenUp":{
					kmss_console_log("点阵笔抬起");
					ispendown = false;
					lastX=-1;
					lastY=-1;
					break;
				}
			    // 正常书写（笔落下）状态下坐标事件
				case "Coor":
					kmss_console_log("点阵笔正常书写");
					if (ispendown == true) {
			        	kmss_console_log(jobj.X + " : "+jobj.Y);
						var can = $('#'+drawStrokeId);
						kmss_console_log("can width:"+can.width());
						kmss_console_log("can height:"+can.height());
						kmss_console_log("dotWidth : "+dotWidth);
						var x = (can.width() / dotWidth) * (jobj.X-marginLeft); //等比
						var y = (can.height()/ dotHeight) * (jobj.Y-marginTop);
						//var y = (jobj.Y-marginTop) / rate;  //将AU转换为px
						if(lastX==-1&&lastY==-1){
							lastX=x;
							lastY=y;
							
						}
						DrawStroke(lastX,lastY,x,y);
						if(lastCusorX!=-1&&lastCusorX!=-1){
							DrawCircle(lastCusorX,lastCusorY,x,y);
						}
						lastX=x;
						lastY=y;
						lastCusorX=lastX;
						lastCusorY=lastY;
						//document.getElementById('pencoor').innerHTML=jobj.X+','+jobj.Y+';'+jobj.Page;
				     }
					break;
				// 悬浮书写（笔抬起）状态下坐标事件
				case "Hover":{
					kmss_console_log("点阵笔悬浮书写");
					var can = $('#'+drawStrokeId);
					var x = (can.width() / dotWidth) * (jobj.X-marginLeft);  //等比
					var y = (can.height()/ dotHeight) * (jobj.Y-marginTop);
					//var y = (jobj.Y-marginTop) / rate;
					if(lastCusorX!=-1&&lastCusorX!=-1){
						DrawCircle(lastCusorX,lastCusorY,x,y);
					}
					lastCusorX=x;
					lastCusorY=y;
					//document.getElementById('pencoor').innerHTML=jobj.X+','+jobj.Y+';'+jobj.Page;
					break;
				}
			}
		};
	}
	/**
	 * 画线，笔迹也是由无数条直线构成的
	 * @param lastX
	 * @param lastY
	 * @param x
	 * @param y
	 * @returns
	 */
	function DrawStroke(lastX,lastY,x,y){
		var can = document.getElementById(drawStrokeId);
	    var ctx = can.getContext('2d');
		ctx.moveTo(lastX,lastY);
		ctx.lineTo(x,y);
		ctx.lineWidth=0.3; 
		ctx.strokeStyle = 'black';
	    ctx.stroke();
	}
	/**
	 * 画一个圆，就是笔的位置
	 * @param lastX
	 * @param lastY
	 * @param x
	 * @param y
	 * @returns
	 */
	function DrawCircle(lastX,lastY,x,y){
		var can = document.getElementById(drawCircleId);
	    var ctx = can.getContext('2d');
		ctx.clearRect(lastX-6,lastY-6,12,12);
	    ctx.beginPath();
	    ctx.arc(x,y,2,0,2*Math.PI,true);
	    ctx.closePath();
	    ctx.fill();
		
		
		ctx.strokeStyle = 'red';
	    ctx.stroke();
	}
	
	function kmss_console_log(msg) {
		if(window.console) {
			//window.console.log(msg);
		}
	}
});
