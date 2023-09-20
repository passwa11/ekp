function MapObj() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();
    
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
    };
    
    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
    
    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };
};

var excel = {
	sheetULWidth : 0,
	sheetPos : null,
	initialExcelViewer : function(){
		excel.sheetPos=new MapObj();
		var showPageNum=1;
		if(commonFuncs.contains(viewerStyle,"aspose",true)){
			showPageNum=document.getElementById("exceljs").param;
			excel.initialSheets(showPageNum);
		}
		excel.loadSheet(showPageNum);
	},
	initialSheets : function(showNum){
		var sheetNamesLis="";
		var sheetNames=viewerParam.sheetName.split(";");
		var sheetTotalNum=0;
		for(var i in sheetNames){
			sheetTotalNum++;
			var sheetNum=parseInt(i);
			var sheetName=sheetNames[i].substring(sheetNames[i].indexOf("-")+1);
			sheetNamesLis+="<li id='sheetLi-"+(sheetNum+1)+"'><span onclick='excel.changeSheet("+(sheetNum+1)+");'>"+sheetName+"</span></li>";
		}
		totalPageNum=sheetTotalNum;
		seajs.use('lui/jquery',function($){$("#sheetUL").html(sheetNamesLis);});
		seajs.use('lui/jquery',function($){
			var sheetDocList_ul_w = 0;
			$("#sheetUL").find("li").each(function() {
				sheetDocList_ul_w += $(this).outerWidth(true);
			});
			excel.sheetULWidth=Math.ceil(sheetDocList_ul_w+86);
			$("#sheetUL").width(excel.sheetULWidth);
			if($("#sheetUL").width()<=$(".lui_sheet_doc_list").width()){
				$(".lui_sheet_switch").each(function(){
					$(this).find("li").each(function(){
						$(this).css('cursor','auto');
						$(this).hover(function(){
							$(this).css('background-color','#F7F6F6');
						});
					});
				});
				$(".icon_toHome").css('background-position','0 0');
				$(".icon_prev").css('background-position','-7px 0');
				$(".icon_next").css('background-position','-12px 0');
				$(".icon_toEnd").css('background-position','-17px 0');
			}else{
				$(".zoom").find("ul").prepend("<li id='zoom_li' style='cursor:pointer'>...</li>");
				var i=1;
				var num = parseInt(sheetDocList_ul_w/500);
				$("#zoom_li").click(function(){
					excel.goTo('next');
					i++;
					if(i==num){
						$("#zoom_li").css('display','none');
					}
				});
			}
		});
		seajs.use('lui/jquery',function($){
			var i=1;
			$("#sheetUL").find("li").each(function() {
				excel.sheetPos.put(i,$(this).offset().left-86);
				i++;
			});
		});
		excel.setSheetChooseStatus(showNum);
	},
	changeSheet : function(sheetNum){
		currentPage=sheetNum;
		excel.loadSheet(sheetNum);
	},
	goTo : function(type){
		seajs.use('lui/jquery',function($){
			var scrollLeft=$(".lui_sheet_doc_list").scrollLeft();
			if(type=="end"){
				$(".lui_sheet_doc_list").scrollLeft(excel.sheetPos.get(totalPageNum));
			}
			if(type=="start"){
				$(".lui_sheet_doc_list").scrollLeft(0);
			}
			if(type=="prev"){
				$(".lui_sheet_doc_list").scrollLeft(scrollLeft-500);
			}
			if(type=="next"){
				$(".lui_sheet_doc_list").scrollLeft(scrollLeft+500);
			}
		});
	},
	loadSheet : function(sheetNum){
		var sheetFrame=document.getElementById("sheetContent");
		if(!sheetFrame){
			var iframeHtml="<iframe id='sheetContent' width='100%' height='100%' style='border: 0px; background: white;'></iframe>";
			seajs.use('lui/jquery',function($){$(mainMiddle).html(iframeHtml);});
		}
		seajs.use('lui/jquery',function($){
			var sheetContentFrame=document.getElementById("sheetContent");
			$(sheetContentFrame).load(function(){excel.setFrame(sheetContentFrame);});
			$(sheetContentFrame).removeAttr("src").attr("src",dataSrc+"?method=view&viewer=htmlviewer&fdId="+fdId+"&filekey="+commonFuncs.getFileName(sheetNum));
		});
		if(commonFuncs.contains(viewerStyle,"aspose",true)){
			excel.setSheetChooseStatus(sheetNum);
			currentPage=sheetNum;
		}
	},
	setSheetChooseStatus : function(sheetNum){
		seajs.use('lui/jquery',function($){
			$("li.status_current").removeClass("status_current");
			$("#sheetLi-"+sheetNum).addClass("status_current");
		});
	},
	setFrame : function(frameObj){
		var frameDoc=frameObj.contentDocument||frameObj.Document;
		if(commonFuncs.isIE()){
			var topWindow=frameObj.contentWindow.top;
			if(topWindow.fullScreen=="yes"){
				seajs.use('lui/jquery',function($){
					$(frameDoc).bind("keydown",commonFuncs.keyDownHandler);
				});
			}
		}
		excel.setFrameSize(frameObj);
		excel.setFrameBodyBackGround(frameObj);
		try{
			var bodyEle = frameObj.contentDocument.body || frameObj.Document.body;
			var children = bodyEle.children;
			for ( var i = 0; i < children.length; i++) {
				if (i == 0) {
					excel.setAuthentication(children[i], true);
				}
				excel.setAuthentication(children[i]);
			}
		}catch(e){
			
		}
		excel.setAuthentication(frameObj, true);
		if(waterMarkConfig.showWaterMark=="true"){
			commonFuncs.setWaterMarkBody(frameDoc);
		}
		
	},
	setWaterMark : function(frameObj){
		var waterMarkBgType=waterMarkConfig.markBgType;
		var picUrl="../sys_att_watermark/sysAttWaterMark.do?method=getWaterMarkPNG";
		var bgOpacity=parseFloat(waterMarkConfig.markOpacity).toFixed(2);
		var markDiv = document.getElementById("waterMark");
		if(markDiv==null || typeof(markDiv) == undefined){
			markDiv=document.createElement("div");
			markDiv.id="waterMark";
			markDiv.className="waterMark";
		}
		try{
			var frameDoc=frameObj.contentDocument||frameObj.Document;
			var bodyDoc=frameDoc.body;
			bodyDoc.appendChild(markDiv);
			seajs.use('lui/jquery',function($){
				$(markDiv).css({"background-color":"transparent","overflow":"hidden","position":"absolute","height":$(bodyDoc)[0].scrollHeight+"px","width":$(bodyDoc)[0].scrollWidth+"px","top":"0px","left":"0px"});
			});
			commonFuncs.setWaterMarkInner(markDiv,waterMarkBgType,"url("+picUrl+")",bgOpacity);
		}catch(e){
			//
		}
	},
	setAuthentication : function(frameObj,fire){
		if (fire) {
			excel.load(frameObj);
			return;
		}
		if (frameObj.tagName != 'FRAME')
			return;
		seajs.use('lui/jquery',function($){$(frameObj).on('load', function(evt) {excel.load(evt.target);});});
	},
	load : function(frameObj){
		var bodyEle;
		if (frameObj.contentDocument) {
			bodyEle = frameObj.contentDocument.body;
		}
		if (!bodyEle) {
			if (frameObj.Document)
				bodyEle = frameObj.Document.body;
		}
		if (!bodyEle)
			return;
		bodyEle.oncopy = function() {
			return commonFuncs.onAuthentication();
		};
		bodyEle.oncontextmenu = function() {
			return commonFuncs.onAuthentication();
		};
		bodyEle.onselectstart = function() {
			return commonFuncs.onAuthentication();
		};
		$(bodyEle).click(function(eventObj) {  // 点击excel里面的链接时候发出事件，只检查到父级节点
			var e = eventObj || window.event;  // 兼容 IE8
			var target = e.target||e.srcElement, currentTarget = e.currentTarget, _href=null, i = 1;
			// 判断当前点击的目标对象或其父对象中是否包含A标签（超链接），如果链接的目标为Office办公文档（如：Excel、Word、PowerPoint）,则阻止浏览器默认行为，使用Aspose去处理
			while(target && i<=2 && target !== currentTarget )  {
				if(target.tagName.toLowerCase() === "a") {
					var href = decodeURI(target.getAttribute("href")) ;
					if(href) {
						var ii = href.indexOf("?");
						if(ii > -1) {
							href = href.slice(0 , ii);
						}
						if(/\.(doc|xls|ppt|docx|xlsx|pptx|et|ett|wps|wpt)$/i.test(href)) {
							_href = href;
						}
					}
					break;
				}
				target = target.parentNode;
				i++;
			}
			if(_href) {
				e.preventDefault(); // 阻止浏览器默认行为
				LUI.fire(
					{
						type:"topic", 
						name:"asposeClick",
						data:{
							hrefText : _href,
						}
					}, 
					parent||window
				);
			}
		});
		if (!commonFuncs.onAuthentication()){
			debugger;
			if(bodyEle.currentStyle){//IE
				bodyEle.style.cssText = "-moz-user-select:none;-webkit-user-select: none;-ms-user-select: none;-khtml-user-select: none;user-select: none;";
			}else{
				bodyEle.style = "-moz-user-select:none;-webkit-user-select: none;-ms-user-select: none;-khtml-user-select: none;user-select: none;";
			}
		}
	},
	setFrameSize : function(frameObj){
		if (frameObj && !window.opera) {
			//
		}
	},
	setFrameBodyBackGround : function(frameObj){
		try{
			var frameDoc=frameObj.contentDocument||frameObj.Document;
			var bodyDoc=frameDoc.body;
			seajs.use('lui/jquery',function($){$(bodyDoc).css("margin", "0px");});	
		}catch(e){
			//
		}
	}
};
function goTo(sheetNum){
	excel.changeSheet(sheetNum);
}
excel.initialExcelViewer();