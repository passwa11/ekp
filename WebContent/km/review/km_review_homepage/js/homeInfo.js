Com_IncludeFile("data.js");
//流程统计初始化加载
Com_AddEventListener(window,"load",function(){
	$.ajax({
		url : Com_Parameter.ContextPath+"km/review/km_review_index/kmReviewIndex.do?method=homeInfo&isHome=true",
		type : "POST",
		dataType: "html",
		success : function(data) {
			document.getElementById("reviewCount").innerHTML = data;
		}
	});
})

// 监听新建更新等成功后刷新
seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
	topic.subscribe('successReloadPage', function() {
		if(LUI("tabInfo").selectedIndex == 0){
			$.ajax({
				url : Com_Parameter.ContextPath+"km/review/km_review_index/kmReviewIndex.do?method=reviewProcessList",
				type : "POST",
				dataType: "html",
				success : function(data) {
					LUI("tabInfo").contents[0].element.html(data);
				}
			});
		}
	});

});
//初始化含摘要列表及切换时重置iframe高度
function resizeHeight(){
	var iframe = window.parent.document.getElementsByTagName('iframe')[0];
	if(iframe){
		iframe.setAttribute('height', $(iframe.contentWindow.document.body).height()+"px");
	}
}
//流程含摘要列表初始化及页签切换加载
LUI.ready( function (){
	LUI("tabInfo").on("indexChanged",function (args){
		if(args.index.after != null || args.index.after != undefined){
			if(args.index.after == 0){
				sendAjax(args,args.index.after,"%2FlistApproval&mydoc=approval","reviewProcessList");
			}else if(args.index.after == 1){
				sendAjax(args,args.index.after,"%2FlistApproved&mydoc=approved","reviewedProcessList");
			}else if(args.index.after == 2){
				sendAjax(args,args.index.after,"%2FlistCreate&mydoc=create","draftProcessList");
			}else if(args.index.after == 3){
				sendAjax(args,args.index.after,"%2FlistFollow&doctype=follow","followProcessList");
			}
		}
	})
});


//最近使用及常用流程初始化及页签切换加载
LUI.ready( function (){
	LUI("tabUsual").on("indexChanged",function (args){
		if(args.index.after != null || args.index.after != undefined){
			var url = "";
			if(args.index.after == 0){
				url = Com_Parameter.ContextPath+"km/review/km_review_index/kmReviewIndex.do?method=offenUseList";
				sendAjaxs(args,args.index.after,url);
			}else if(args.index.after == 1){
				url = Com_Parameter.ContextPath+"sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsually&mainModelName=com.landray.kmss.km.review.model.KmReviewMain";
				sendAjaxs(args,args.index.after,url);
			}
		}
	})
});

function sendAjaxs (args,contentIndex,url){
	$.ajax({
		url : url,
		type : "POST",
		dataType: "html",
		success : function(data) {
			args.panel.contents[contentIndex].element.html(data);
		}
	});
}

function sendAjax (args,contentIndex,moreMethodName,linkMethodName){
	document.getElementById("moreDetails").href = Com_Parameter.ContextPath+"km/review/#j_path="+moreMethodName;
	$.ajax({
		url : Com_Parameter.ContextPath+"km/review/km_review_index/kmReviewIndex.do?method="+linkMethodName+"&modelName=com.landray.kmss.km.review.model.KmReviewMain",
		type : "POST",
		dataType: "html",
		success : function(data) {
			args.panel.contents[contentIndex].element.html(data);
			resizeHeight();
		}
	});
}

/**
 * 获取指定日期的周的第一天、月的第一天、季的第一天、年的第一天
 * @param date new Date()形式，或是自定义参数的new Date()
 * @returns 返回值为格式化的日期，yy-mm-dd
 */
//日期格式化，返回值形式为yy-mm-dd
function timeFormat(date) {
	if (!date || typeof(date) === "string") {
		this.error("参数异常，请检查...");
	}
	var y = date.getFullYear(); //年
	var m = date.getMonth() + 1; //月
	var d = date.getDate(); //日

	return y + "-" + m + "-" + d;
}

//获取这周的周一
function getFirstDayOfWeek () {
	var date = new Date();
	var weekday = date.getDay()||7; //获取星期几,getDay()返回值是 0（周日） 到 6（周六） 之间的一个整数。0||7为7，即weekday的值为1-7

	date.setDate(date.getDate()-weekday);//往前算（weekday-1）天，年份、月份会自动变化
	return timeFormat(date);
}

//获取当月第一天
function getFirstDayOfMonth () {
	var date = new Date();
	date.setDate(1);
	return timeFormat(date);
}

//获取当年第一天
function getFirstDayOfYear () {
	var date = new Date();
	date.setDate(1);
	date.setMonth(0);
	return timeFormat(date);
}
//流程统计数量 跳转url
function bindTimeTogether(prefix,methodName,doctype,type,timeFlag){
	if("week"==timeFlag){
		var url = prefix+"/km/review/#j_path=%2F"+methodName+"&mydoc="+doctype+"&cri.q="+type+"%3A"+getFirstDayOfWeek()+"%3B"+type+"%3A"+timeFormat(new Date());
		window.open(url);
	}
	if("month"==timeFlag){
		var url = prefix+"/km/review/#j_path=%2F"+methodName+"&mydoc="+doctype+"&cri.q="+type+"%3A"+getFirstDayOfMonth()+"%3B"+type+"%3A"+timeFormat(new Date());
		window.open(url);
	}
	if("year"==timeFlag){
		var url = prefix+"/km/review/#j_path=%2F"+methodName+"&mydoc="+doctype+"&cri.q="+type+"%3A"+getFirstDayOfYear()+"%3B"+type+"%3A"+timeFormat(new Date());
		window.open(url);
	}
}

//流程echarts报表统计初始化及切换方法
LUI.ready( function (){
	LUI("echartsPanel").on("indexChanged",function (args){
		if(args.index.after != null || args.index.after != undefined){
			if(args.index.after == 0){
				sendAjaxss(args,args.index.after,"draftProcessTab");
			}else if(args.index.after == 1){
				sendAjaxss(args,args.index.after,"reviewedProcessTab");
			}

		}
	})
});
//异步调用后台接口
function sendAjaxss (args,contentIndex,linkMethodName){
	$.ajax({
		url : Com_Parameter.ContextPath+"km/review/km_review_index/kmReviewIndex.do?method="+linkMethodName,
		type : "POST",
		dataType: "html",
		success : function(data) {
			var result = JSON.parse(data);
			if(linkMethodName == "draftProcessTab"){
				echartsTabinfo(result,"process_datas",Data_GetResourceString('km-review:kmReview.nav.draft.count'));
				document.getElementById("process_datas").style.display = 'block';
				document.getElementById("img1").style.display = 'none';

			}else {
				echartsTabinfo(result,"process_data",Data_GetResourceString('km-review:kmReview.nav.resolve.count'));
				document.getElementById("process_data").style.display = 'block';
				document.getElementById("img2").style.display = 'none';
			}
		},
	});
}
//echarts 报表渲染方法
function echartsTabinfo(res,id,name){
	var date = new Date().getFullYear();
	window.processQueue = echarts.init(document.getElementById(id));
	var option = {

		tooltip : {
			trigger: 'axis',
			axisPointer: {            // 坐标轴指示器，坐标轴触发有效
				type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		xAxis: {
			type: 'category',
			data: [date+'-01', date+'-02', date+'-03', date+'-04', date+'-05', date+'-06', date+'-07',date+'-08',date+'-09',date+'-10',date+'-11',date+'-12'],
			axisLabel: {
				interval:0,
				rotate:50
			}
		},
		yAxis: {
			type: 'value',
		},
		grid: {
			left: "3%",
			right: "4%",
			bottom: "12%",
			width: "88%",
			height: "250px",
			containLabel: true
		},
		series: [{
			name: name,
			data: res,
			type: 'line'
		}]
	};
	window.processQueue.setOption(option);
}