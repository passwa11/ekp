/**
 * 
 */
var element = render.parent.element;
$(element).html("");
if(data && data.land){
	var clean = data.land.clean;
	var search = data.land.search;
	var expandFilter = data.land.expandFilter;
	var collapseFilter = data.land.collapseFilter;
	var choose = data.land.choose;
	var enterWord = data.land.enterWord;
	var pleaserWord= data.land.pleaserWord;
}
var searchNumber = data && data.searchNumber ? parseInt(data.searchNumber) : 2;
var data = data.data;
if(data == null || data.length == 0){
	done();
}else{
	if(data && data.length && data.length > 0){
		if(data.length == 1 && data[0].businessType == "textarea"){
			//#171719 过滤多行文本
		}else{
			var $wrap = $("<div style='display:block;'/>").appendTo(element);
			var $ul = $("<ul/>").appendTo($wrap);
			// 伸展收缩
			createExpandElement($wrap);
			for(var i = 0;i < data.length;i++){
				var searchInfo = data[i];
				$ul.append(buildSearchNode(searchInfo));
			}
			//#157380 渲染searchNumber个li,兼容li的bottom-border没有100%
			var mod = data.length%searchNumber;
			if(mod > 0){
				for(var i=searchNumber - mod;i>0;i--){
					var $li = $("<li/>");
					$li.css("display","none");
					$ul.append($li);
				}
			}
			var $opt =$("<div class='hearder-search-opt' />").appendTo($wrap);
			var $cleanBtn = $("<div class='search-opt-btn-clean'>"+clean+"</div>").appendTo($opt);
			var $searchBtn = $("<div class='search-opt-btn'>"+search+"</div>").appendTo($opt);
			$searchBtn.on("click", function(){
				doSearch();
			});
			$cleanBtn.on("click", function(){
				$(".search-string .search-string-input").val("");
				$(".search-address").find("input[type='text']").val("");
				doClean();
			});
		}
	}
}

function createExpandElement(targetElement){
	var $expandWrap = $("<div class='hearder-search-selected' />").prependTo(element);
	var $expand = $("<div class='hearder-search-expand' >"+expandFilter+"</div>").appendTo($expandWrap);
	$expand.on("click",function(){
		if($expand.prop("className").indexOf("showmany") > -1){
			targetElement.find("ul li").each(function(){
				$(this).css("display","inline-block");
			});
			$expand.text(collapseFilter);
			$expand.removeClass("showmany");
		}else{
			if(targetElement.is(":visible")){
				targetElement.slideToggle();
				$expand.html(expandFilter);
			}else{
				targetElement.slideToggle();
				$expand.html(collapseFilter);
			}
		}
	});
}

function buildSearchNode(searchInfo){
	if(searchInfo && searchInfo.businessType == "textarea"){
		//#171719 过滤多行
		return ;
	}
	var textLable = searchInfo.label;
	if(searchInfo.fullLabel){
		textLable = searchInfo.fullLabel;
	}
	var $li = $("<li/>");
	$li.css("display","none");
	var $title = $("<div class='lui-search-item-title' title='"+textLable+"'></div>").appendTo($li);
	$title.append("<span>"+ searchInfo.label +"</span>")
	
	var $content = $("<div class='lui-search-item-content'></div>").appendTo($li);
	if(searchInfo.type === "String"){
		if(searchInfo.enumValues){
			//枚举类字段类型
			if(searchInfo.businessType==="inputRadio"){
				//单选框
				$content.append(radioDraw(searchInfo));
			}else if(searchInfo.businessType==="inputCheckbox"){
				//复选框
				$content.append(checkBoxDraw(searchInfo));
			}else if(searchInfo.businessType==="select"){
				//下拉框
				$content.append(selectDraw(searchInfo));
			}else if(searchInfo.businessType==="fSelect"){
				//多选下拉框(用多选框代替)
				$content.append(checkBoxDraw(searchInfo));
			}else{
				$content.append(strDraw(searchInfo));
			}
		}else{
			$content.append(strDraw(searchInfo));
		}
	}else  if(searchInfo.type.indexOf("com.landray.kmss.sys.organization") > -1){
		// 地址本
		$content.append(addressDraw(searchInfo));
	}else if(searchInfo.type === "Date" || searchInfo.type === "DateTime" || searchInfo.type === "Time"){
		// 日期
		$content.append(dateDraw(searchInfo));
	}else if(searchInfo.type === "Double"){
		if(searchInfo.enumValues){
			//枚举类字段类型
			if(searchInfo.businessType==="inputRadio"){
				//单选框
				$content.append(radioDraw(searchInfo));
			}else if(searchInfo.businessType==="select"){
				//下拉框
				$content.append(selectDraw(searchInfo));
			}else{
				$content.append(strDraw(searchInfo));
			}
		}else{
			$content.append(strDraw(searchInfo));
		}
	}
	else{
		//默认string
		$content.append(strDraw(searchInfo));
	}
	
	return $li;
}

function strDraw(searchInfo){
	var $div = $("<div class='search-string'></div>");
	var $input = $('<input type="text" class="search-string-input" data-search="true" name="'+ searchInfo.name +'" placeholder="'+enterWord+'"/>').appendTo($div);
	$input.on("keyup",function(event , dom){
		if (event && event.keyCode == '13') {
			doSearch();
		}
	});
	return $div;
}

//单选框
function radioDraw(searchInfo){
	var $div = $("<div class='where_enum_check' style='color: #999999;margin:5px 0 0 0; '></div>");
	var enumValues =searchInfo.enumValues;
	var enumArray = enumValues.split(';');
	for(var i in enumArray){
		var enumArrayinfo=enumArray[i];
		var ev = enumArrayinfo.split('|');
		var checkId = "where_check_" + searchInfo.name + ev[1] + new Date().getTime();
		var $check = $("<input type='radio' value='" + ev[1] + "'  name='" + searchInfo.name + "'  />");
		//直接使用value会被替换，具体原因未知，暂用这个代替
		$check.attr("enumValue", ev[1]);
		$check.attr("enumTxt", ev[0]);
		$check.attr("id", checkId);
		$check.appendTo($div);
		$("<labe for='" + checkId + "'>" + ev[0] + " &nbsp;</labe>").appendTo($div);
	}
	return $div;
}

//复选框
function checkBoxDraw(searchInfo){
	var $div = $("<div class='where_enum_check' style='color: #999999;margin:5px 0 0 0; '></div>");
	var enumValues =searchInfo.enumValues;
	var enumArray = enumValues.split(';');
	for(var i in enumArray){
		var enumArrayinfo=enumArray[i];
		var ev = enumArrayinfo.split('|');
		var checkId = "where_check_" + searchInfo.name + ev[1] + new Date().getTime();
		var $check = $("<input type='checkbox' value='" + ev[1] + "'  name='" + searchInfo.name + "'  />");
		//直接使用value会被替换，具体原因未知，暂用这个代替
		$check.attr("enumValue", ev[1]);
		$check.attr("enumTxt", ev[0]);
		$check.attr("id", checkId);
		$check.appendTo($div);
		$("<labe for='" + checkId + "'>" + ev[0] + " &nbsp;</labe>").appendTo($div);
	}
	return $div;
}

//下拉框
function selectDraw(searchInfo){
	var $div = $("<div class='search-string'></div>");
	var enumValues =searchInfo.enumValues;
	var enumArray = enumValues.split(';');
	var $select=$("<select style='width: 180px;' data-search='true' name='" + searchInfo.name + "' ><option value=''>"+pleaserWord+"</option></select>");
	for(var i in enumArray){
		var enumArrayinfo=enumArray[i];
		var ev = enumArrayinfo.split('|');
		var $option = $("<option  value='" + ev[1] + "'>"+ev[0]+"</option>");
		$select.append($option);
	}
	$div.append($select);
	return $div;
}

//地址本
function addressDraw(searchInfo){
	var addressType = addressTypeTransFilter(searchInfo) || "";
	var orgSelectType = ORG_TYPE_ALLORG;
	if(addressType === "ORG_TYPE_ORG"){
		orgSelectType = ORG_TYPE_ORG
	}else if(addressType === "ORG_TYPE_PERSON"){
		orgSelectType = ORG_TYPE_PERSON;
	} else if(addressType === "ORG_TYPE_POST"){
		orgSelectType = ORG_TYPE_POST;
	}else if(addressType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
		orgSelectType = ORG_TYPE_ORGORDEPT;
	}else if(addressType === "ORG_TYPE_POST|ORG_TYPE_PERSON"){
		orgSelectType = ORG_TYPE_POSTORPERSON;
	}
	var $div = $("<div class='search-address'></div>");
	// 看表单地址本的设计，只要不是“person”，都是“element”
	var type = searchInfo === "com.landray.kmss.sys.organization.model.SysOrgPerson" ? ORG_TYPE_PERSON : ORG_TYPE_ALLORG;
	
	$div.append("<input type='hidden' data-search='true' name='"+ searchInfo.name +".id' />");
	$div.append("<input type='text' name='"+ searchInfo.name +".name' class='inputsgl' style='width:150px' readonly/>");
	var $address = $("<span class='highLight'><a href='javascrip:void(0);'>"+choose+"</a></span>").appendTo($div);
	$address.on("click",function(e){
		Dialog_Address(false, searchInfo.name + ".id", searchInfo.name + ".name", ";", orgSelectType,function(){
			doSearch();
		});
	});
	return $div;
}

function addressTypeTransFilter(searchInfo){
	var code = ORG_TYPE_ALL + "";
	if(searchInfo.customProperties && searchInfo.customProperties.orgType){
		code = searchInfo.customProperties.orgType;
	}
	return code;
}
// 日期控件
function dateDraw(searchInfo){
	var $div = $("<div class='search-date'></div>");
	var  dateHtml=getDate(false,searchInfo.name,searchInfo.type);
	$div.append(dateHtml);
	return $div;
}
// 返回日期html
function getDate(isMulti, name, type) {

	var html = $("<div class='modeling_data_select inputselectsgl'/>");
	var $input = $(" <div class=\"input\"   style='width:85%;min-height:20px;float: left;' />")
	$input.append("<input type='text' data-search='true' name='" + name + "' style='width:150px' readonly/>");

	html.append($input);

	var icon = $("<div class='inputdatetime'/>");
	//区分日期，时间、时间日期
	if (type === "Date") {
		html.on("click", function (e) {
			selectDate(name, null, function (c) {
				__CallXFormDateTimeOnValueChange(c);
			});
		});
	} else if (type === "DateTime") {
		html.on("click", function (e) {
			selectDateTime(name, null, function (c) {
				__CallXFormDateTimeOnValueChange(c);
			});
		});
	} else if (type === "Time") {
		icon = $("<div class='inputtime'/>");
		html.on("click", function (e) {
			selectTime(name, null, function (c) {
				__CallXFormDateTimeOnValueChange(c);
			});
		});
	}
	html.append(icon);
	return html;
}

function doSearch(){
	render.parent.doSearch();
}
function doClean(){
	render.parent.doClean();
}