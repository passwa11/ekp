window.onscroll = null;

var pptFullScreen = {
	showImgSize : {"width":1600,"height":900},
	initialFullScreenViewer : function(pageNum) {
		var divMiddle=document.getElementById("mainMiddle");
		seajs.use('lui/jquery',function($){$(divMiddle).html("");});
		var tableContentHTML="<table id='viewerContent' align='center' width='100%' height='100%' cellpadding='0px' cellspacing='0px'></table>";
		seajs.use('lui/jquery',function($){$(divMiddle).html(tableContentHTML);});
		seajs.use('lui/jquery',function($){$("#totalPageCount").html(totalPageNum);});
		seajs.use('lui/jquery',function($){$("#mainMiddle").css("overflow","hidden");});
		pptFullScreen.preLoadPage(pageNum);
	},
	preLoadPage : function(pageNum){
		var divMiddle=document.getElementById("mainMiddle");
		var divImgHide=document.createElement("div");
		divMiddle.appendChild(divImgHide);
		divImgHide.setAttribute("style", "display:none;");
		divImgHide.id="divHide";
		var imgHideHtml="<img scrolling='no' src='"+dataSrc+"?method=view&fdId="+fdId+"&filekey="+commonFuncs.getFileName(pageNum)+"' id='imgHide' onload='pptFullScreen.setShowImgSize("+pageNum+");' />";
		seajs.use('lui/jquery',function($){$(divImgHide).html(imgHideHtml);});
	},
	loadPage : function(pageNum){
		var tableContent=document.getElementById("viewerContent");
		seajs.use('lui/jquery',function($){$(tableContent).html("");});
		var tableInnerHTML ="<tr id='pageTr_"+pageNum+"' height='0px'><td id='pageTd_"+pageNum+"' height='0px' style='text-align:center'></td></tr>";
		seajs.use('lui/jquery',function($){$(tableContent).html(tableInnerHTML);});
		var pageTd=document.getElementById("pageTd_"+pageNum);
		var pageTdInnerHTML="<div style='margin:0 auto;width:"+pptFullScreen.showImgSize.width+"px;height:"+pptFullScreen.showImgSize.height+"px;overflow:hidden;pointer-events:none;'><img width=" + pptFullScreen.showImgSize.width + " height=" +pptFullScreen.showImgSize.height +" scrolling='no' src='"+dataSrc+"?method=view&fdId="+fdId+"&filekey="+commonFuncs.getFileName(pageNum)+"' id='dataLoad_"+pageNum+"' onload='pptFullScreen.setImg("+pageNum+");' /></div>";
		seajs.use('lui/jquery',function($){$(pageTd).html(pageTdInnerHTML);});
		commonFuncs.setPageToolBar(pageNum);
	},
	getNaturalImgSize : function(imgObj){
		var nW,nH;
		if (typeof imgObj.naturalWidth == "undefined") {
			var i = new Image();
			i.src = imgObj.src;
			nW = i.width;
			nH = i.height;
		}
		else {
			nW = imgObj.naturalWidth;
			nH = imgObj.naturalHeight;
		}
		return {"width":nW,"height":nH};
	},
	setShowImgSize : function(pageNum){
		var browser=commonFuncs.getBrowser();
		var screenWidth;
		var screenHeight;
		if(browser=="IE"){
			screenWidth=window.screen.width-60*(window.screen.width/window.screen.height);
			screenHeight=window.screen.height-60;
		}else{
			screenWidth=window.screen.width;
			screenHeight=window.screen.height;
		}
		var objImg=document.getElementById("imgHide");
		var fHeight;
		var fWidth;
		seajs.use('lui/jquery',function($){
			var naturalImgSize=pptFullScreen.getNaturalImgSize(objImg);
			fWidth=naturalImgSize.width;
			fHeight=naturalImgSize.height;
		});
		var okSize=pptFullScreen.getOkSize(fWidth, fHeight, screenWidth, screenHeight);
		pptFullScreen.showImgSize.width=okSize.okWidth;
		pptFullScreen.showImgSize.height=okSize.okHeight;
		var divMiddle=document.getElementById("mainMiddle");
		var divHide=document.getElementById("divHide");
		divMiddle.removeChild(divHide);
		pptFullScreen.loadPage(pageNum);
	},
	getOkSize : function(imgWidth,imgHeight,screenWidth,screenHeight){
		var widthHeight=imgWidth/imgHeight;//设置宽高比
		var heightWidth=imgHeight/imgWidth;//设置高宽比
		var okHeight=screenHeight;
		var okWidth=okHeight*widthHeight;
		if(okWidth>screenWidth){
			okWidth=screenWidth;
			okHeight=okWidth*heightWidth;
		}
		if(okHeight>screenHeight){
			return pptFullScreen.getOkSize(imgWidth,imgHeight,screenWidth,screenHeight*0.9999);
		}
		return {"okWidth":okWidth,"okHeight":okHeight};
	},
	setImg : function(pageNum){
		var imgObj=document.getElementById("dataLoad_"+pageNum);
		pptFullScreen.setImgSize(imgObj);
		pptFullScreen.setImgAuthentication(imgObj);
	},
	setImgSize : function(objImg){
		seajs.use('lui/jquery',function($){$(objImg).parent().css("width",pptFullScreen.showImgSize.width);$(objImg).attr("width",pptFullScreen.showImgSize.width);});
		seajs.use('lui/jquery',function($){$(objImg).parent().css("height",pptFullScreen.showImgSize.height);$(objImg).attr("height",pptFullScreen.showImgSize.height);});
		if(waterMarkConfig.showWaterMark=="true"){
			commonFuncs.setWaterMarkBody(document);
		}
	},
	setImgAuthentication : function(objImg){
		objImg.oncontextmenu=function(){ return commonFuncs.onAuthentication();};
		objImg.ondragstart=function(){ return commonFuncs.onAuthentication();};
	},
	goTo : function(pageNum){
		currentPage=pageNum;
		pptFullScreen.loadPage(pageNum);
	}
};

pptFullScreen.initialFullScreenViewer(currentPage);

function goTo(pageNum) {
	pptFullScreen.goTo(pageNum);
}