// create 191217 编号机制相关

// 目前的索引值的累计
var nowGenId = 0;
// 当前的索引值
var curIndex = 0;
// 当前类型
var nowType = '';

var ischange = true;

//自定义元素计算范围
var globalAreaIds = new Array();
var areaEles = new Array();

//添加数组IndexOf方法
if (!Array.prototype.indexOf){
  Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++){
      if (from in this && this[from] === elt)
        return from;
    }
    return -1;
  };
}

//兼容ie8
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, ""); //正则匹配空格  
}


//阻止事件冒泡
function _stopPropagation(event){
	if (event.stopPropagation) {
	    // 针对 Mozilla 和 Opera
	    event.stopPropagation();
	} else if (window.event) {
	    // 针对 IE
	    window.event.cancelBubble = true;
	}
}

//根据id查找编号元素
function findItemById(id){
	for(index in fdContentJson){
		var item = fdContentJson[index];
		if(id==item.id){
			return item;
		}
	}
}

//解析含有$的字符串，返回数组  
function resolv$string(e){
	var arrs=new Array();
	var eles="";
	var isstart=false;
	var index=0;
	for ( var i = 0; i < e.length; i++) {
		var c = e.charAt(i);
		if (isstart == false) {
			if (c == '$') {
				isstart = true;
				continue;
			}
		} else {
			if (c == '$') {
				if(eles.indexOf(".")==-1)
				{
					arrs[index] = eles;
					index++;
				}
				eles = "";
				isstart = false;
				continue;
			}
			eles = eles.concat(c);
		}
	}
	return arrs;
}


//调整嵌入到iframe时的高度
function dyniFrameSize() {
	try {
		// 调整高度
		var arguObj = document.getElementsByTagName("form")[0];
		if(arguObj!=null && window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var iframeH = arguObj.offsetHeight > 0 ? arguObj.offsetHeight : 315;
			window.frameElement.style.height = (iframeH + 40) + "px";
		}
	} catch(e) {
		
	}
}

//初始化
function init(){	
	var evalue=document.getElementsByName("DefaultFlag")[0];
	var fvalue=document.getElementsByName("fdDefaultFlag")[0];
	if(typeof(evalue)!='undefined'){
		if (fvalue.value == "0") {
			evalue.checked = true ;
		}else {
			evalue.checked = false;
		}
	}else{
		fvalue.value="1";
	}

	if("1"== isCustom){
		setTimeout("dyniFrameSize();", 1400);
	} 
	if("view"==method){
	  $("#number_btn_clear").css({"display":"none"});
	  $("#selectDateArea").attr("disabled","disabled");
	  $("#selectDate").attr("disabled","disabled");
	  $("#selectTimeArea").attr("disabled","disabled");
	  $("#selectTime").attr("disabled","disabled");
	  $("#len").attr("disabled","disabled");
	  $("#isZeroFill").attr("disabled","disabled");
	  $("#isAutoElevation").attr("disabled","disabled");
	  $("#start").attr("disabled","disabled");
	  $("#firstStart").attr("disabled","disabled");
	  $("#textConst").attr("disabled","disabled");
	  $("#textCustomName").attr("disabled","disabled");
	  $("#textCustomNameFromUser").attr("disabled","disabled");
	  $("#isHidden").attr("disabled","disabled");
	  $(".number_raido input[type='radio']").attr("disabled","disabled");
	  $("#formula").css({"display":"none"});
	  $(".number_btn_group").css({"display":"none"});
	}
	//日期加载
	$("#selectDateArea").empty();
	$("#selectDate").empty();
	var area_date_json=eval(area_date);
	var isfirstDate=true;
	$.each(area_date_json,function(idx,item){
		$("#selectDateArea").append("<option value='"+item.area+"'>"+item.area+"</option>");
		var data=item.data;
		$.each(data,function(idx,item){
				if(isfirstDate)
					$("#selectDate").append("<option value='"+item.value+"'>"+item.name+"</option>");
			}		
		);
		isfirstDate=false;
	});
	$("#selectDateArea").change(function(){
		var area=$("#selectDateArea").val();
		$("#selectDate").empty();
		var area_date_json=eval(area_date);
		$.each(area_date_json,function(idx,item){
			if(area==item.area){
					var data=item.data;
					$.each(data,function(idx,item){
						$("#selectDate").append("<option value='"+item.value+"'>"+item.name+"</option>");
						}		
					);
				}
		});	
	});
	
	//时间加载
	$("#selectTimeArea").empty();
	$("#selectTime").empty();
	var area_time_json=eval(area_time);
	var isfirstTime=true;
	$.each(area_time_json,function(idx,item){
		$("#selectTimeArea").append("<option value='"+item.area+"'>"+item.area+"</option>");
		var data=item.data;
		$.each(data,function(idx,item){
				if(isfirstTime)
					$("#selectTime").append("<option value='"+item.value+"'>"+item.name+"</option>");
		});
		isfirstTime=false;
	});	
	$("#selectTimeArea").change(function(){
		var area=$("#selectTimeArea").val();
		$("#selectTime").empty();
		var area_time_json=eval(area_time);
		$.each(area_time_json,function(idx,item){
				if(area==item.area){
						var data=item.data;
						$.each(data,function(idx,item)
							{
							$("#selectTime").append("<option value='"+item.value+"'>"+item.name+"</option>");
							}		
						);
					}
		});	
	});
	//初始化edit页面选择的编号计算元素
	updateTabItemList();
	
}


//初始化edit页面选择的编号计算元素
function updateTabItemList(){
	areaEles = new Array();
	globalAreaIds = new Array();
	for(index in fdContentJson){
		var item = fdContentJson[index];
		var id = item.id;
		if(id>nowGenId){
			nowGenId = id;
		}
		if(item.type=='custom'){
			var ids= resolv$string(fdContentJson[index].ruleData.formulaID);
			var names= resolv$string(fdContentJson[index].ruleData.formulaName);
			for(var i = 0; i < ids.length; i++){
				if(globalAreaIds.indexOf(ids[i])==-1){
					var areaEle = {'itemIndex':index,'id':ids[i],'name':names[i],'selected':'true'};
					areaEles.push(areaEle);
					globalAreaIds.push(ids[i]);
				}
			}
		}
		item.index = index;
		addItem(item.id,item.type,item.name,false);
	}
}

//更新edit页面编号计算范围
function updateAreaEles(){
	//更新计算范围
	areaEles = new Array();
	globalAreaIds = new Array();
	for(index in fdContentJson){
		var item = fdContentJson[index];
		if(item.type=='custom'){
			var ids= resolv$string(fdContentJson[index].ruleData.formulaID);
			var names= resolv$string(fdContentJson[index].ruleData.formulaName);
			for(var i = 0; i < ids.length; i++){
				if(globalAreaIds.indexOf(ids[i])==-1){
					var areaEle = {'itemIndex':index,'id':ids[i],'name':names[i],'selected':'false'};
					areaEles.push(areaEle);
					globalAreaIds.push(ids[i]);
				}
			}
		}
	}
	//去除不在自定义数据内的范围选项
	$.each(fdContentJson,function(idx,item){
		if(item.type=='flow'){
			if(item.ruleData.areas){
				var areas = new Array();
				$.each(item.ruleData.areas,function(idx_a,item_a){
					if(globalAreaIds.indexOf(item_a.id)!=-1){
						areas.push(item_a);
					}
				});
				fdContentJson[idx].ruleData.areas = areas;
			}
		}
	});
	
}


//点击第一排选中元素
$('#number_select_list').on('click', 'li', function (event) {
  if("view"==method){
	  return false;
  }
  var $this = $(this);
  var type = $this.attr('data-type').toString();
  var name = $this.text().trim();
  addItem(nowGenId, type, name, true);
  _stopPropagation(event);
})

function encodeHTML(str) {
	return str.replace(/&/g,"&amp;")
		.replace(/</g,"&lt;")
		.replace(/>/g,"&gt;")
		.replace(/\"/g,"&quot;")
		.replace(/¹/g, "&sup1;")
		.replace(/²/g, "&sup2;")
		.replace(/³/g, "&sup3;");
};

// 添加元素 从第一排添加元素
function addItem(id, type, name, showTabContent) {
  if(!id&&!type){
	  return false;
  }
  var html =  '<li data-id="'+id+'" data-type="'+type+'">'
  			+ '<i class="number_icon icon_'+type+'"></i>'
  			+ '<i class="icon_arrow"></i><i class="icon_del"></i>'
  			+ '</li>';
  var htmlView =  '<li data-id="'+id+'" data-type="'+type+'"><i class="number_icon icon_'+type+'"></i></li>';
  // 添加图标
  $('#number_select_list_tab').append(method!='view'?html:htmlView);
  // 添加提示
  var tipHtml = '<span data-id="'+id+'">'+encodeHTML(name)+'</span>';
  $('.number_input_list').append(tipHtml);
  // id 自定递增
  nowGenId++
  if(showTabContent){
	  //新加显示TabContent
	  //#119938 改为定位最后一个item
	  $('#number_select_list_tab li').last().addClass('tab_cur').siblings().removeClass('tab_cur');
	  $('.number_tab_item[data-type=' + type + ']').css("display", '').siblings().css('display', 'none'); 
	  var item = {'id':id+'','name':'','type':type,'ruleData':{},'index':fdContentJson.length};
	  fdContentJson.push(item);
	  //设置确定按钮的数据索引
	  $('.number_btn_group span').attr("data-index",item.index);
	  //新加根据是否有计算范围确定显示
	  if(areaEles.length>0){
			$("#limits").empty();
			$.each(areaEles,function(idx,item){
				var checked = item.selected=='true'?'checked':'';
				var checkbox = '<input '+checked+' type="checkbox" onclick="onFlowLimitsChange(this)" name="'+item.id+'" id="'+item.id+'"/>';
				var checklable = '<label style="padding-right:5px;" for="'+item.id+'">'+item.name+'</label>';
				var eledom = checkbox + checklable;
				$("#limits").append(eledom);
			});
			$("#flowscope").show();
	  }
  }
  if('const' == type)
	  $("#textConst").val("");
  dyniFrameSize();
}

// tab提示切换
$('#number_select_list_tab').on('click', 'li', function (event) {
  var $this = $(this);
  var type = $this.attr('data-type').toString();
  var id = $this.attr('data-id').toString();
  var item = findItemById(id);
  var json_id=item.id,
	  json_type=item.type,
	  json_ruleData=item.ruleData,
	  json_name=item.name;
  if(type!=json_type){
	  return false;
  }

  //设置确定按钮的数据索引
  $('.number_btn_group span').attr("data-index",item.index);
  switch (type) {
	case "date":
		$("#selectDate").val("");
		$.each(json_ruleData,function(idx,item){
			if(idx=='zone'){
				$("#selectDateArea").val(item);
				$("#selectDateArea").change();
			}
		});
		$.each(json_ruleData,function(idx,item){
			if(idx=='format')
				$("#selectDate").val(item);
		});
		break;
	case "time":
		$("#selectTime").val("");
		$.each(json_ruleData,function(idx,item){
			 	if(idx=='zone'){
			 		$("#selectTimeArea").val(item);
			 		$("#selectTimeArea").change();
			 	}
		});
		$.each(json_ruleData,function(idx,item){
			 	if(idx=='format')
			 		$("#selectTime").val(item);
		});
		break;
	case "const":
		$("#textConst").val("");
		$.each(json_ruleData,function(idx,item){
			if(idx=='value'){
				var v_data=item;
				if((v_data != undefined || v_data!=null) && v_data!=''){
					$("#textConst").val(v_data);
				}	
			}
		});
		break;
	case "flow":
		 $("input[name='period'][value='0']").prop("checked",true);
		 $("#len").prop("value","3"); 
		 $("#len").addClass("grayColor");
		 $("#start").prop("value","1"); 
		 $("#start").addClass("grayColor");
		 $("#firstStart").prop("value","");
		 $("#firstStart").addClass("grayColor");
		 $("#step").prop("value","1");
		 $("#step").addClass("grayColor");
		 $("#isZeroFill").prop("checked",true);
          var hasJson_ruleData = false;
		 $.each(json_ruleData,function(idx,item){
			 hasJson_ruleData = true;
			 if(idx=='period'){
				 if(item=='0') {
					 $("#li_period_start").hide();
				 } else {
					 $("#li_period_start").show();
				 }
			 }
		 });

		 if(!hasJson_ruleData) {
			 $("#li_period_start").hide();
		 }
		 $.each(json_ruleData,function(idx,item){
			 	if(idx=='period'){
			 		 if(item=='0')
			 			$("input[name='period'][value='0']").prop("checked",true);
			 		 else if(item=='1')
			 			$("input[name='period'][value='1']").prop("checked",true);
			 		 else if(item=='2')
			 			$("input[name='period'][value='2']").prop("checked",true);
			 		 else if(item=='3')
			 			$("input[name='period'][value='3']").prop("checked",true);
			 	}
			 	if(idx=='len'){
			 		$("#len").prop("value",item); 
			 		$("#len").removeClass("grayColor");
			 		var _fdFlowContent = $('#fdFlowContent').val();
			 		if(_fdFlowContent){
						var _fdFlowContentJson = JSON.parse(_fdFlowContent);
						$.each(_fdFlowContentJson,function(index,item){
							if(item.id==json_id){
								var isZeroFill = item.isZeroFill=='true'? true:false;
								var isAutoElevation = item.isAutoElevation=='true'? true:false;
								$("#isZeroFill").prop("checked",isZeroFill);
								$("#isAutoElevation").prop("checked",isAutoElevation);
							}
						});
				 	}
			 	}else if(idx=='period')
			 		$("#period").prop("value",item);
			 	else if(idx=='start'){
			 		$("#start").prop("value",item); 
			 		$("#start").removeClass("grayColor");
			 	}else if(idx=='firstStart' && item != ""){
					$("#firstStart").prop("value",item);
					$("#firstStart").removeClass("grayColor");
				}else if(idx=='step'){
			 		$("#step").prop("value",item);
			 		$("#step").removeClass("grayColor");
			 	}else if(idx=='areas'){
			 		if(areaEles.length>0){
						$("#limits").empty();
						$.each(areaEles,function(idx,elem){
							var selected = 'false';
							//判断是否选中
							if(item.length>0){
								$.each(item,function(idx,itemc){
									if(elem.id==itemc.id){
										selected = itemc.selected;
									}
								});
							}
							var checked = selected=='true'?' checked ':'';
							var disabled = method == 'view' ? ' disabled ':'';
							var checkbox = '<input '+checked + disabled +' type="checkbox" onclick="onFlowLimitsChange(this)" name="'+elem.id+'" id="'+elem.id+'"/>';
							var checklable = '<label style="padding-right:5px;" for="'+elem.id+'">'+elem.name+'</label>';
							var eledom = checkbox + checklable;
							$("#limits").append(eledom);
						});
						$("#flowscope").show();
			 	   }
			  }
		});
		break;
	case "custom":
		$("#textCustomID").val("");	
		$("#textCustomName").val("");
		$("#isHidden").prop("checked",false);
		$.each(json_ruleData,function(idx,item){
			 if(idx=='formulaID')
				 $("#textCustomID").val(item.replace(/'/g,"\""));
			 else if(idx=='formulaName')
				 $("#textCustomName").val(item.replace(/'/g,"\""));
			var _fdFlowContent = $('#fdFlowContent').val();
			if(_fdFlowContent){
				var _fdFlowContentJson = JSON.parse(_fdFlowContent);
				$.each(_fdFlowContentJson,function(index,item){
					if(item.id==json_id){
						var isHidden = item.isHidden=='true'? true:false;
						$("#isHidden").prop("checked",isHidden);
					}
				});
			}
		});
		$("#textCustomNameFromUser").val(json_name==""?fdCustomTxt:json_name);
		break;
	default:
		break;
  }
  $this.addClass('tab_cur').siblings().removeClass('tab_cur');
  $('.number_tab_item[data-type=' + type + ']').css("display", '').siblings().css('display', 'none');
  dyniFrameSize();
  _stopPropagation(event);
})


// 清空按钮
$('#number_btn_clear').click(function (event) {
  $('#number_select_list_tab').html('');
  $('.number_input_wrap .number_input_list').html('');
  $('.number_tab_item').css("display", 'none');
  $("input[name='fdContent']").val('[]');
  $('#fdFlowContent').attr('value', '');
  fdContentJson = [];
  $("#flowscope").hide();
  $("#limits").empty();
  areaEles = new Array();
  _stopPropagation(event);
})

//悬浮出现提示
$(".number_prompt_tooltip_drop").hover(function(){
	$(".number_dropdown_tooltip_menu").css("display","block");
})
$(".number_prompt_tooltip_drop").mouseout(function(){
	$(".number_dropdown_tooltip_menu").css("display","none");
})

// 确定按钮点击
$('.number_btn_group').on('click','span',function (event) {
  var $this = $(this);
  var type = $this.attr('data-type').toString();
  var index = $this.attr('data-index');
  switch (type) {
	case "date":
		if(isNull($("#selectDateArea").val())||isNull($("#selectDate").val())){
			addError(dateIsNotEmpty);
			return false;
		}
		fdContentJson[index].name = $("#selectDate option:selected").text();
		fdContentJson[index].ruleData.zone =  $("#selectDateArea").val();
		fdContentJson[index].ruleData.format =  $("#selectDate").val();
		updateItem(fdContentJson[index]);
		break;
	case "time":
		if(isNull($("#selectTimeArea").val())||isNull($("#selectTime").val())){
			addError(timeIsNotEmpty);
			return false;
		}
		fdContentJson[index].name = $("#selectTime option:selected").text();
		fdContentJson[index].ruleData.zone =  $("#selectTimeArea").val();
		fdContentJson[index].ruleData.format =  $("#selectTime").val();
		updateItem(fdContentJson[index]);
		break;
	case "const":
		if(isNull($("#textConst").val())){
			addError(constIsNotNumber);
			return false;
		}
		fdContentJson[index].name = $("#textConst").val();
		fdContentJson[index].ruleData.value = $("#textConst").val();
		updateItem(fdContentJson[index]);
		break;
	case "flow":
		//清空提示信息
		$('#flowLenTip').empty();
		$("#showError").empty();
		$('#flowStartTip').empty();
		//计算周期 
		var period=$("input:radio[name='period']:checked").val();
		if(period==undefined)
			period=0;
		var start=$.trim($("#start").prop("value"));
		var firstStart=$.trim($("#firstStart").prop("value"));
		var len=$.trim($("#len").prop("value"));
		var step=$.trim("1");
		var areas= new Array();
		var isZeroFill = $("#isZeroFill").is(":checked");
		var isAutoElevation = $("#isAutoElevation").is(":checked");
		if(isNull(len)){
			addFlowLenError(flowLenNotEmpty);
			return false;
		}
		if(isNotNum(len)){
			addFlowLenError(flowLenNotNumber);
			return false;
		}
		if(isNull(start)){
			addFlowStartError(flowIsNotEmpty);
			return false;
		}
		if(isNotNum(start)){
			addFlowStartError(flowIsNotNumber);
			return false;
		}
		//流水号初始值
		var dateFormatText = start;
		//流水号长度
		var num=parseInt(len)-start.length;
		//流水号长度不能为负
		if(num<0){
			addFlowLenError(numberStartBiggerThanLength);
			return false;
		}
		//流水号长度不能大于10
		if(parseInt(len)>10){
			addFlowLenError(flowLenBiggerBit);
			return false;
		}


		//流水号长度
		if(firstStart && firstStart != ""){
			var num2=parseInt(len)-firstStart.length;
			//流水号长度不能为负
			if(num2<0){
				addFlowLenError(numberFirstStartBiggerThanLength);
				return false;
			}
		}

		//是否自动补0
		if(isZeroFill){
			var str="";
			for(var i=0;i<num;i++){
				str=str.concat("0");
			}
			dateFormatText=str.concat(start);
		}
		fdContentJson[index].name = dateFormatText;
		fdContentJson[index].ruleData.period = period;
		fdContentJson[index].ruleData.start = start;
		fdContentJson[index].ruleData.firstStart = firstStart;
		fdContentJson[index].ruleData.len = len;
		fdContentJson[index].ruleData.step = step;
		fdContentJson[index].ruleData.areas = areas;
		updateItem(fdContentJson[index]);
		//保存自动补0信息到数据库字段
		setFlowContent(fdContentJson[index].id,isZeroFill,isAutoElevation);
		//同步全局计算范围
		for(var i=0;i<areaEles.length;i++)
		{
			var check_value=$("#"+areaEles[i].id).is(":checked").toString();
			var area = {'id':areaEles[i].id,'name':areaEles[i].name,'selected':check_value};
			areas.push(area);
		}
		break;
	case "custom":
		if(isNull($("#textCustomName").val())){
			addError(formulaIsNotNumber);
			return false;
		}
		if(isNull($("#textCustomNameFromUser").val())){
			addError(formulaShowNameIsNotNumber);
			return false;
		}
		var isHidden = $("#isHidden").is(":checked");
		fdContentJson[index].name = $("#textCustomNameFromUser").val();
		fdContentJson[index].ruleData.formulaID = $("#textCustomID").val();
		fdContentJson[index].ruleData.formulaName = $("#textCustomName").val();
		fdContentJson[index].ruleData.isHidden = isHidden;
		updateItem(fdContentJson[index]);
		updateAreaEles();	
		break;
	default:
		break;
  }
  $("input[name='fdContent']").val(filterfdContent(fdContentJson));
  _stopPropagation(event);
});

//更新第二排元素
function updateItem(itemNew){
	//更新提示栏渲染
	$('.number_input_list').find('span[data-id=' + itemNew.id + ']').text(itemNew.name);
}

// 删除元素 从第二排移除元素
function delItem(id) {
//  if($('#number_select_list_tab').find('li[data-id=' + id + ']').hasClass('tab_cur')){
//	  $('.number_tab_item').css("display", 'none');
//  } 
  $('.number_tab_item').css("display", 'none');
  $('#number_select_list_tab').find('li[data-id=' + id + ']').remove();
  $('.number_input_list').find('span[data-id=' + id + ']').remove();
  saveOrder();
  $('#fdFlowContent').attr('value', JSON.stringify(filterFlow()));
}

// 删除编号
$('#number_select_list_tab').on('mousedown', 'li .icon_del', function (event) {
  var $this = $(this);
  var $li = $this.parent();
  var id = $li.attr('data-id');
  delItem(id);
  _stopPropagation(event);
})

// 拖动
$('.lui_number_setting_content .number_setting_tab_list').dragsort({
  dragSelector: "li",
  dragBetween: true,
  dragEnd: saveOrder, //拖拽完成后回调函数
})

// 提示排序
function saveOrder() {
  try {
	//调整顺序时隐藏元素修改界面
	$('.number_tab_item').css("display", 'none');
	$('#number_select_list_tab li').removeClass('tab_cur');
    var $ordered_list = $('.lui_number_setting_content .number_setting_tab_list li');
    //调整后的fdContentJson
    var fdContentJsonNew = new Array();
    // 暂存排序后的顺序
    var tmpids = [];
    for (var index = 0; index < $ordered_list.length; index++) {
      var element = $ordered_list[index];
      tmpids.push($(element).attr('data-id'));
    }
    // 提示行重新渲染
    for (var index = 0; index < tmpids.length; index++) {
      $('.number_input_list').append(
        $('.number_input_list [data-id="' + tmpids[index] + '"]')[0]
      );
      var item = findItemById(tmpids[index]);
	  item.index = index;
	  fdContentJsonNew.push(item); 
    }
    fdContentJson = fdContentJsonNew;
    $("input[name='fdContent']").val(filterfdContent(fdContentJson));
  } catch (error) {
    alert('排序异常，请刷新页面后重试！')
  }
}

// 是否包含函数
var contains = document.documentElement.contains ?
  function(parent, node) {
    return parent !== node && parent.contains(node)
  } :
  function(parent, node) {
    while (node && (node = node.parentNode))
      if (node === parent) return true
    return false
  }

// 点击空白处去掉所有的提示
$('body').click(function (event) {
	if(!contains(document.getElementById("lui_number_setting_content"),event.target)){
		$('#number_select_list_tab li').removeClass('tab_cur');
		$('.number_tab_item').css("display", 'none');
	}
	_stopPropagation(event);
})

