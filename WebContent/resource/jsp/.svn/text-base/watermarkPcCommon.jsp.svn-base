<%@ page import="com.landray.kmss.sys.profile.util.WaterMarkUtil" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<% 
	pageContext.setAttribute("waterMarkContext", UserUtil.getKMSSUser().getUserName()); 
	//System.out.println(request.getRequestURI());
	pageContext.setAttribute("waterMarkPCEnable", WaterMarkUtil.getWaterMarkPCEnable());

	pageContext.setAttribute("waterMarkParams", WaterMarkUtil.getWaterMarkParams());

	pageContext.setAttribute("isLowerThanIE8", (WaterMarkUtil.isLowerThanIE8(request) ? true : false));

	if(request.getAttribute("LUI_ContextPath")==null) {
		String LUI_ContextPath = request.getContextPath();
		request.setAttribute("LUI_ContextPath", LUI_ContextPath);
	}

%>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/watermark.js?s_cache=${ LUI_Cache }"></script>
<script>

var isLowerThanIE8 = "${isLowerThanIE8}";
try{
	if(LUI){
		LUI.ready(function () {
			var waterMarkPcEnable = "${waterMarkPCEnable}";
			if(waterMarkPcEnable == "true")
				doMark();
		});
	}
}catch (e){
	Com_AddEventListener(window,'load',function(){
		var waterMarkPcEnable = "${waterMarkPCEnable}";
		if(waterMarkPcEnable == "true")
			doMark();
	})
}

function Com_AddEventListener(obj, eventType, func){
	if(typeof window.ActiveXObject!="undefined")
		obj.attachEvent("on"+eventType, func);
	else
		obj.addEventListener(eventType, func, false);
}

function doMark(){//执行水印
	try{
		var hasWm = false;
		var top = window.top;
		var pnt = window;
		if(pnt!=top)
			return;

		if(top.vm)
			hasWm = true;
		// while(pnt){
		// 	try{
		// 		if(pnt.wm){
		// 			hasWm = true;
		// 			break;
		// 		}else{
		// 			if(pnt == top)//如果是顶层了就不再继续
		// 				break;
		// 			pnt = pnt.parent;
		// 		}
		// 	}catch(e){
		//
		// 	}
		// }

		if (hasWm) {
			return;
		}else{

			var waterMarkParams = '${waterMarkParams}';

			if(waterMarkParams!=null || waterMarkParams!='') {
				var jsonArr = JSON.parse(waterMarkParams);
				var arr = [];
				for (var key in jsonArr.content) {
					if(jsonArr.content[key].deleted==false ||jsonArr.content[key].deleted=="false"){
						arr.push(jsonArr.content[key].value);
					}
				}

			}

			var watermarkParam = {
				context: arr,//水印内容
				x_space: parseInt(jsonArr.compose.horizontalCoord),//水印起始位置x轴坐标
				y_space: parseInt(jsonArr.compose.verticalCoord),//水印起始位置Y轴坐标
				color: jsonArr.compose.fontColor,//水印字体颜色
				alpha: (jsonArr.compose.alpha)/100,//水印透明度
				fontsize: (jsonArr.compose.fontSize).toString()+"px",//水印字体大小
				font: jsonArr.compose.fontFamily,//水印字体
				angle: parseInt(jsonArr.compose.scale),//水印倾斜度数
				fontbold: jsonArr.compose.fontBold,//水印字体加粗
				width: 200,//水印宽度
				height: 80 //水印长度
			};
		    window.wm = new WaterMark(watermarkParam);

		    wm.render();
		    
		    var timer;
		    var w = Math.max(document.body.scrollWidth, document.body.clientWidth);

			var innerHeight = window.innerHeight || document.documentElement.clientHeight;

		    var h = Math.max(document.body.scrollHeight, document.body.clientHeight, innerHeight);
		    function refresh(){
		    	var curW = Math.max(document.body.scrollWidth, document.body.clientWidth);
		        var curH = Math.max(document.body.scrollHeight, document.body.clientHeight, innerHeight);
		        if (curW != w || curH != h) {
		            w = curW;
		            h = curH;
		            wm.refresh();
		        }
		        timer = setTimeout(refresh, 500);
		    }
			 refresh();
			
		}
	}catch(e){}
}



/* !function preventUserRemove() {
    // MutationObserver 是一个可以监听DOM结构变化的接口。
    const MutationObserver = window.MutationObserver || window.WebKitMutationObserver;
    if (MutationObserver) {
      let mo = new MutationObserver(() => {
        const wmInstance = document.querySelector('.water-mark');
        if (!wmInstance) {
          //如果标签在，只修改了属性，重新赋值属性
          if (wmInstance) {
            // 避免一直触发
            // mo.disconnect();
            // console.log('水印属性修改了');
            //alert(1);
          } else {
            //标签被移除，重新添加标签
            // console.log('水印标签被移除了');
            //alert(2);
            //document.body.appendChild(watermark)
          }
        }
      })

      mo.observe(document, {
        attributes: true,
        subtree: true,
        childList: true,
      });

    }
  }() */
</script>