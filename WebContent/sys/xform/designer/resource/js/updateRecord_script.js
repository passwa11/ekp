/*表单留痕配置*/
Com_IncludeFile("data.js");
record_ImportCss(Com_Parameter.ContextPath+'sys/xform/designer/resource/css/updateRecord.css');
Com_AddEventListener(window,'load',function() {
	Control_InitUpdateRecordDomAll();
});
window.controlRecordData = {};
function record_ImportCss(url){
	var link = document.createElement('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = url;
    var head = document.getElementsByTagName('head')[0];
    head.appendChild(link);
}
//初始化控件留痕的（旧的初始化方法，存在性能问题）
function Control_InitUpdateRecordDom(refId){
	return;
}
//初始化控件留痕（所有的留痕控件，优化后的）
function Control_InitUpdateRecordDomAll(){
	var fdId = Xform_ObjectInfo.mainFormId;
	var modelName = Xform_ObjectInfo.mainModelName;
	var properties = Xform_ObjectInfo.properties;
	if(!fdId || !modelName || !properties){
		return;//获取不到必须的数据，则不进行处理
	}

	var mainDocStatus;
	if(Xform_ObjectInfo){
		mainDocStatus = Xform_ObjectInfo.mainDocStatus;
	}else{
		var href = window.location.href;
		if(href.indexOf('method=print') > -1){
			mainDocStatus = "print";
		}
	}
	if(mainDocStatus && mainDocStatus == "print"){
		return;//打印时不执行
	}

	//构造请求参数
	var controls = [];
	//获取所有的留痕属性
	for(var index in properties){
		var property = properties[index];
		if(!property.isMark || property.isMark == false){
			continue;
		}
		var name = property.name;
		if(name.indexOf(".") > -1){//按明细表处理
			var subTableId = name.split(".")[0];
			var controlId = name.split(".")[1];
			var doms = $("[flagid^='"+subTableId+"'][flagid$='"+controlId+"']");
			$(doms).each(function(index,dom){
				var refId = $(dom).attr("flagid");
				if(refId && refId.indexOf("!{index}") == -1){
					var tableDom = $(dom).parents("table[fd_type]").eq(0);
					var rowId=0,rowIndex=0;
					if(tableDom && tableDom.length>0 && tableDom.attr("fd_type") == 'detailsTable'){
						//明细表里的控件
						var ids = refId.split(".");
						subTableId = ids[0];
						rowIndex = ids[1];
						rowId = document.getElementsByName("extendDataFormInfo.value("+ids[0]+"."+ids[1]+".fdId)")[0].value;
						controlId = ids[2];

						controls.push({
							controlId:controlId,
							subTableId:subTableId,
							rowId:rowId,
							rowIndex:rowIndex
						});
					}
				}
			})
		}else{//按非明细表处理
			controls.push({
				controlId:name
			});
		}
	}
	if(controls.length > 0) {
		$.ajax({
			url: Com_Parameter.ContextPath + 'sys/xform/base/sysFormUpdateRecordAction.do?method=datas',
			data: {
				fdId: fdId,
				modelName: modelName,
				controls: JSON.stringify(controls)
			},
			type: 'post',//使用post，因为带的数据可能很多，使用get有一定的限制
			dataType: "json",
			success: function (result) {
				console.log(result)
				var status = result.status;
				if (status == 1) {
					var datas = result.datas;
					window.controlRecordData.datas = datas;
					for (var key in datas) {
						var data = datas[key];
						var recordStatus = data.recordStatus;
						if (recordStatus == 1) {//处理状态正常的记录
							var records = data.records;
							if (records && records.length >= 2) {
								var dom = document.getElementById("_xform_extendDataFormInfo.value(" + key + ")");
								if (!dom) {
									dom = $("[flagid='" + key + "']")[0];
								}
								Control_CreateUpdateRecordDom(dom,key);
							}
						}
					}
				} else {
					console.error("读取留痕数据异常，后台发生异常问题")
				}
			},
			error: function (errorMsg) {
				console.error("读取留痕数据异常，请求失败")
			}
		})
	}
}

//创建控件留痕的图标内容
function Control_CreateUpdateRecordDom(dom,refId){
	var $parent = $(dom).parents("td").eq(0);
	$parent.css("position", "relative");
	var $div = $("<div></div>");
	$div.addClass("record_icon");
	/*$div.css({
		"position": "absolute",
	    "top": "0",
	    "right": "0",
	    "width": "10px",
	    "height": "10px",
	    "background-color": "yellow"
	});*/
	$div.attr("refId",refId);
	$div.bind("click",function(){
		Control_CreateUpdateRecordDataDom(dom,refId);
		return false;
	})
	$(dom).append($div);
}
//创建控件留痕的数据内容
var lastRecordDom;
function Control_CreateUpdateRecordDataDom(dom,refId){
	var dom = document.getElementById("_xform_extendDataFormInfo.value("+refId+")");
	if(!dom){
		dom = $("[flagid='"+refId+"']")[0];
	}
	var $parent = $(dom).parents("td").eq(0);
	var recordDom = $parent.find(".record_data");
	//创建数据内容dom，若已经初始化过则不再请求初始化
	if(!(recordDom && recordDom.length > 0)){
		$("div.record_data").addClass("hide");
		var tableDom = $(dom).parents("table[fd_type]").eq(0);
		var fdId;
		var modelName;
		var controlId = refId;
		var subTableId="",rowId="";
		if(Xform_ObjectInfo){
			fdId = Xform_ObjectInfo.mainFormId;
			modelName = Xform_ObjectInfo.mainModelName;
		}else{
			modelName = _xformMainModelClass;
			fdId = _xformMainModelId;
		}
		if(!fdId || !modelName){
			alert("发生错误，获取model和id失败！");
			return;
		}
		if(tableDom && tableDom.length>0 && tableDom.attr("fd_type") == 'detailsTable'){
			//明细表里的控件
			var ids = refId.split(".");
			subTableId = ids[0];
			rowId = document.getElementsByName("extendDataFormInfo.value("+ids[0]+"."+ids[1]+".fdId)")[0].value;
			controlId = ids[2];
		}
		var records = null;
		if($("input[name='recordControl_refId']").length > 0 || (window.controlRecordData && window.controlRecordData.datas)){
			var data = window.controlRecordData && window.controlRecordData.datas ? window.controlRecordData.datas[refId] : null;
			if(data){
				records = data.records;
			}
			if(!records){
				alert("该控件数据并未进行修改！");
				return;
			}
		}else{
			records = new KMSSData().AddBeanData("sysFormUpdateRecordService&fdId="+fdId+"&modelName="+modelName+"&controlId="+controlId+"&subTableId="+subTableId+"&rowId="+rowId).GetHashMapArray();
			if(records.length == 1 && records[0].status && records[0].status == -1){
				alert("该控件数据并未进行修改！");
				return;
			}
		}
		//创建包含数据的dom
		var $div = $("<div></div>");
		$div.addClass("record_data");
		var $ul = $("<ul></ul>");
		for(var i=records.length-1; i>=0; i--){
			var record = records[i];
			var $li = $("<li></li>");
			$li.append("<div class='head'><i></i><p>"+record.time+"</p></div>");
			$li.append("<div class='content'><span class='item' title='"+record.name+"'>"+record.name+"</span><span style='padding:0 5px'>修改</span><span class='item data-item' title='"+(record.data || '')+"'></span><i class='more' style='display:none' onclick='toggleMoreData(this)'></i></div>");
			if(i == 0){
				$li.addClass("lastChild");
			}else if(i == records.length-1){
				$li.addClass("firstChild");
			}
			$ul.append($li);
		}
		$div.append($ul);
		$(dom).append($div);
		lastRecordDom = $div;
		//填充数据内容
		var $liDoms = $ul.find("li");
		for(var i=0; i<$liDoms.length; i++){
			var $liDom = $($liDoms[i]);
			var $dataItemDom = $liDom.find("div.content").find(".data-item").eq(0);
			var data = $dataItemDom.attr("title") || "";
			if(data && data.length > 24){
				data = data.substring(0,24) + "...";
				$liDom.find("div.content").find("i.more").eq(0).css("display","inline-block");
			}
			$dataItemDom.text(data);
		}
		
	}else{
		if(lastRecordDom[0] != recordDom[0]){
			lastRecordDom.addClass("hide");
		}
		lastRecordDom = recordDom;
		recordDom.toggleClass("hide");
	}
}

function toggleMoreData(obj){
	var $div = $(obj).parents("div.content").eq(0);
	var $dataItemDom = $div.find(".data-item").eq(0);
	var data = $dataItemDom.attr("title") || "";
	if($(obj).attr("class") && $(obj).attr("class").indexOf("active") != -1){
		$dataItemDom.text(data.substring(0,24) + "...");
		$(obj).removeClass("active");
	}else{
		$dataItemDom.text(data);
		$(obj).addClass("active");
	}
}