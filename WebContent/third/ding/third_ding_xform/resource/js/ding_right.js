
Com_IncludeFile("right.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);
Com_IncludeFile("common.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/resource/css/","css",true);

/**
 * 规则框右上角关闭按钮
 */
function closedTab(){
	$("#ruleFlag").val("0");
	//$('.d_lui_mix_wids').addClass('mask_switch');
	document.getElementById("ruleId").style.display="none";
}
/**
 * 明细框右上角关闭按钮 
 */
function _hide(){
	$("#rightFlag").val("0");
	//$('.d_lui_mix_wid_mask').addClass('mask_switch');
	document.getElementById("rightId").style.display="none";	
}

/**
 * 展示和关闭右侧规则框
 */
function rightShowRule(title,content) {
	var ruleFlag = $("#ruleFlag").val();
	if("1" == ruleFlag){//关闭
		$("#ruleFlag").val("0");
		//$('.d_lui_mix_wids').addClass('mask_switch');
		document.getElementById("ruleId").style.display="none";
	}else{//展示
		var	rightHtml = rightCreateRule(title,content);
		var rightPanel = document.getElementById("rightJS");
		if(!rightPanel){
			rightPanel = $("<div id='rightJS' />").appendTo(document.body)[0];
		}
		rightPanel.innerHTML = rightHtml;
		$("#ruleFlag").val("1");
		//$('.d_lui_mix_wids').removeClass('mask_switch');
		document.getElementById("ruleId").style.display="block";
	}
}

/**
 * 展示和关闭右侧明细框
 */
function rightShow(title,bluep,blueSpan,content) {
	var rightFlag = $("#rightFlag").val();
	if("1" == ruleFlag){//关闭
		$("#rightFlag").val("0");
		//$('.d_lui_mix_wid_mask').addClass('mask_switch');
		document.getElementById("rightId").style.display="none";	
	}else{//展示
		var	rightHtml = rightCreate(title,bluep,blueSpan,content);
		var rightPanel = document.getElementById("rightJS");
		if(!rightPanel){
			rightPanel = $("<div id='rightJS' />").appendTo(document.body)[0];
		}
		rightPanel.innerHTML = rightHtml;
		$("#rightFlag").val("1");
		//$('.d_lui_mix_wid_mask').removeClass('mask_switch');
		document.getElementById("rightId").style.display="block";	
	}
	
}

/**
 * JS生成右侧规则框
 */
function rightCreateRule(title,content) {
	var rightHtml = "";
	rightHtml += "<div class='d_lui_mix_wids' id = 'ruleId'>";
	rightHtml += "<input type='hidden' id='ruleFlag' name='ruleFlag' value='0' />";
	rightHtml += "<div class='d_lui_mix_head'><span class='d_lui_mix_strong'>"+title+"</span><span class='d_lui_mix_deletes' onclick='closedTab();'>✕</span> </div>";
	rightHtml += "<table class='d_lui_mix_table'>";
	rightHtml += content;
	rightHtml += "</table>";
	rightHtml += "</div>";
	return rightHtml;
}

/**
 * JS生成右侧明细框
 */
function rightCreate(title,bluep,blueSpan,content) {
	var rightHtml = "";
	rightHtml += "<div class='d_lui_mix_wid_mask' id = 'rightId'>";
	rightHtml += "<div class='d_lui_mix_prowidth'>";
	rightHtml += "<div class='d_lui_mix_header clearfloat'>";
	rightHtml += "<span class='d_lui_mix_title' id ='title'>"+title+"</span><span class='d_lui_mix_delete' onclick = '_hide();'>✕</span>";
	rightHtml += "</div>";
	rightHtml += "<div class='d_lui_mix_body'>";
	rightHtml += "<div class='d_lui_mix_vital' id = 'blueDiv'>";	
	rightHtml += "<input type='hidden' id='rightFlag' name='rightFlag' value='0' />";
	rightHtml += "<p id = 'bluep'>"+bluep+"</p>";
	rightHtml += "<span id = 'blueSpan'>"+blueSpan+"</span>";	
	rightHtml += "</div>";
	rightHtml += "<div id = 'contentDiv'>";
	rightHtml += content;
	rightHtml += "</div>";
	rightHtml += "</div>";
	rightHtml += "</div>";	
	return rightHtml;
}

/**
 * 构建从上到下进度式的HTML，返回HTML，一般用于设置rightShow的content参数
 * 
 * @param details
 * 		{"duration":"总时长","duration_unit":"时长单位(day|halfDay|hour)","duration_details":[{
 * 		"date":"日期", "week":"星期几","duration":"时长","schedule_start":"上班开始时间","schedule_end":"下班结束时间",
 * 		"leave_start":"请假开始时间","leave_end":"请假结束时间"}]}
 * @returns
 */
function RightPanel_BuildProgressStyleContent(details){
	var html = "";
	html += "<ul>";
	for(var i = 0;i < details["duration_details"].length; i++){
		html += RightPanel_BuildProgressStyleItem(details["duration_details"][i], details["duration_unit"]);
	}
	html += "</ul>";
	return html;
}

/**
 * @param detail 详细信息
 * 		{"date":"日期", "week":"星期几", "duration":"时长","schedule_start":"上班开始时间","schedule_end":"下班结束时间",
 * 		"leave_start":"请假开始时间","leave_end":"请假结束时间"}
 * @param unit 单位
 * @returns
 */
function RightPanel_BuildProgressStyleItem(detail, unit){
	var html = "";
	html += "<li class='d_lui_mix_detail_item detail_first_item'>";
	
	html += "<div class='d_lui_mix_detail_progress'>";
	html += "<span></span><p></p>";
	html += "</div>";
	
	html += "<div class='d_lui_mix_detail_content'>";
	
	html += "<span class='long'>"+ detail["duration"] + (unit === "hour" ? "小时" : "天") +"</span>";
	html += "<div class='time'>";
	html += "<p class='date'>"+ detail["date"] +"</p>";
	html += "<span class='week'>"+ detail["week"] +"</span>";
	html += "</div>";
	
	var tmpTxt = "请假 "+ detail["leave_start"] +"～" + detail["leave_end"];
	html += "<div class='section'><div class='rest_section'>"+ tmpTxt +"</div>" +
			"<div class='full_section'><p></p><div><span class='starts'>"+ detail["schedule_start"] +"</span>" +
			"<span class='ends'>"+ detail["schedule_end"] +"</span></div></div></div>";
	
	html += "</div>";
	
	html += "</li>";
	return html;
}