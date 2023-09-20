$(function(){
    relation_checkbox_load_ready();
	//明细表内控件 有明细表的 table-add 事件触发初始化
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		var row = argus.row;
		$(row).find('div[mytype="relation_checkbox"]').each(function(i,obj){
			var vals = argus.vals;
			var val = $(obj).attr('hiddenvalue');
			// 如果存在vals，即是需要用vals里面的数据填充到动态单选的，而不是默认值
			if(vals){
				val = relation_getControlValByVals(obj,vals);
			}
			relation_checkbox_setListener(obj,val);
		});
		//查看状态,加载默认值
		$(row).find('input[mytype="relation_checkbox"]').each(function(i,obj){
			relation_checkbox_load_defaultVal(obj);
		});
	})
});

/** 监听高级明细表 */
$(document).on("detailsTable-init", function(e, tbObj){
    relation_checkbox_load_ready(tbObj);
})

function relation_checkbox_load_ready(tbObj) {
    var context = tbObj || document;
    //明细表里面的模板行不需要监控
    $("div[mytype='relation_checkbox']:not([myid*='!{index}'])").each(function(i,obj){
        relation_checkbox_setListener(obj);
    });
    //查看状态,加载默认值
    $("input[mytype='relation_checkbox']:not([myid*='!{index}'])").each(function(i,obj){
        relation_checkbox_load_defaultVal(obj);
    });
}

//套了权限控件,view状态加载默认值
function relation_checkbox_load_defaultVal(obj,curVal) {
	var myid = relation_getParentXformFlagIdHasPre(obj);
	//正在加载时,不需要再次触发
	if(document.getElementById("spinner_img_"+myid)){
		return;
	}
	var defValue = $(obj).attr("defvalue");
	var textValue = $(obj).attr("textValue");
	var val = $(obj).val();
	if (defValue && val === defValue && !textValue) {
		relation_common_source_base_run(obj,$(obj).attr("inputParams"),$(obj).attr("outputParams"),$(obj).attr("params"),defValue,true,relation_checkbox_add_default_text);
	}
}

function relation_checkbox_add_default_text(control, values, texts, isInit, curVal){
	// 显示div
	if(control){
		$(control).css('display','');
	}
	// 设置实际值
	var hiddenValue = $(control).attr('hiddenvalue');
	if(curVal){
		hiddenValue = curVal;
	}
	var hasValue = false;
	// 只有在初始加载的时候才需要
	if(isInit && hiddenValue && hiddenValue!= ''){
		hasValue = true;
	}
	//显示值绑定的name
	var textName = relation_getTextNameByControl(control);
	for ( var i = 0; i < values.length; i++) {
		if(values[i] != ''){
			// 如果实际值相等则选择
			if(hasValue && hiddenValue == values[i]){
				// 同时设置textname的value
				$(control).after(texts[i]);
			}
		}
	}
}

/*设置监控*/
function relation_checkbox_setListener(obj,curVal){
	// 用于设置加载过程图标
	var myid = relation_getParentXformFlagIdHasPre(obj);
	var inDetailTable = false;
	var rowIndex;
	if(/\.(\d+)\./g.test(myid)){
		inDetailTable = true;
	}else{
		inDetailTable = false;
	}
	// 监控的控件
	var bindDom= $(obj).attr('bindDom');
	if(bindDom && bindDom != ''){
		// 如果是多个输入，则每个输入都需要监听
		var bindDomArray = bindDom.split(';');
		for(var i = 0;i < bindDomArray.length;i++){
			var bindDomId = bindDomArray[i];
			if(/-fd(\w+)/g.test(bindDomId)){
				bindDomId = bindDomId.match(/(\S+)-/g)[0].replace("-","");
			}
			// 如果是在明细表里面，处理id
			if(/(\w+)\.(\w+)/g.test(bindDomId)){
				if(inDetailTable){
					rowIndex = myid.match(/\.(\d+)\./g);
					rowIndex = rowIndex ? rowIndex:[];
					bindDomId = bindDomId.replace(".",rowIndex[0]);	
				}else{
					alert(XformObject_Lang.relationCheckbox_msg);
					return;
				}
			}
			// 绑定动态事件的初始化完成事件 用于当输入控件是动态控件（动态单选、动态多选）时，设置初始值 by zhugr 2017-08-01
			$("xformflag[flagid*='" + bindDomId + "']").on('relation_dynamicLoaded',function(){
				relation_checkbox_triggerSelect(myid,obj,true);
			});
			//获取绑定的事件控件对象
			var bindStr = document.getElementById(bindDomId)?"#" + bindDomId:'[name*="' + bindDomId + '"]';
			$(bindStr).on('change',function(){
				// 之所以用延迟，是因为当传入控件有onclick事件时，控件在IE上（Chrome不会）会先触发change再触发onclick，这种情况这动态多选作为传入控件的时候，就需要阻塞 by zhugr 2017-08-03
				setTimeout(function(){
					relation_checkbox_triggerSelect(myid,obj);
				},0);
			});
		}	
	}
	// 有可能有传入值，而且绑定的控件有初始值
	relation_checkbox_triggerSelect(myid,obj,true,curVal);	
	
}

/*
 * 点击复选框触发事件
 * */
function relation_checkbox_click(dom, onValueChange) {
	var control = $(dom).closest('div');
	relation_checkbox_setFieldValue(control,onValueChange);
}

// 设置复选框的实际值
function relation_checkbox_setFieldValue(control,onValueChange){
	var hiddenElements = '', cbElement, hiddenTextElements = '';
	var name = relation_getParentXformFlagIdHasPre(control); //含extendDataFormInfo.value
	var textName = relation_getTextNameByControl(control); // 不含extendDataFormInfo.value
	var cbElements = $(control).find("input");// 由于control里面的CheckBox在明细表里面的索引是变动的，不固定的，故直接查找所有的input
	// 获得已勾选的值列表
	for (var i = 0;i < cbElements.length; i++) {
		cbElement = cbElements[i];
		if (cbElement.type.toLowerCase() == 'checkbox') {
			if (cbElement.checked) {
				hiddenElements += ';' + cbElement.value;
				hiddenTextElements += ';' + $(cbElement).attr('textvalue'); 
			}
		}
	}
	// 设置值，包括显示值和实际值
	$(control).parent().find("input[name='"+ name +"']").val(hiddenElements.substring(1));//精确匹配
	$(control).parent().find("input[name='"+ name +"']").trigger($.Event("change"));	
	relation_setFieldValueByDom(control,textName,hiddenTextElements.substring(1),null,true);
	// 回调
	if (onValueChange != null) {
		onValueChange(name , cbElements);
	}
}

/*
 * 触发查询
 * */
function relation_checkbox_triggerSelect(myid,obj,isInit,curVal){
	//正在加载时,不需要再次触发
	if(document.getElementById("spinner_img_"+myid)){
		return;
	}
	relation_common_source_base_run(obj,$(obj).attr("inputParams"),$(obj).attr("outputParams"),$(obj).attr("params"),curVal,isInit,relation_checkbox_addItems);
}

/*
 * 设置多选样式
 * */
function relation_checkbox_addItems(control, values, texts, isInit, curVal) {
	// 显示div
	if(control){
		$(control).css('display','');
		$(control).closest("xformflag").removeProp("_xForm_cache");
	}
	// 设置实际值
	var hiddenValue = $(control).attr('hiddenvalue');
	if(curVal){
		hiddenValue = curVal;
	}
	var hasValue = false;
	var hiddenValueArray = [];
	// 只有在初始加载的时候才需要
	if(isInit && hiddenValue && hiddenValue!= ''){
		hasValue = true;
		hiddenValueArray = hiddenValue.split(';');
	}
	// 设置标题
	var title = $(control).attr('title');
	var subject = $(control).attr('subject');
	var html = '';
	// 设置必填标志
	var validate = $(control).attr('validate');
	var isRequired = false;
	if(validate && validate.indexOf('required') > -1){
		isRequired = true;
	}
	// 点击触发事件 ，扩展用
	var onclickHtml = "relation_checkbox_click(this,__xformDispatch);";
	// 单选排列方式
	var alignment = $(control).attr('alignment');
	// 默认隔开空格个数
	var appendHtml = '&nbsp;&nbsp;';
	if(alignment){
		if(alignment == 'V'){
			appendHtml = '</br>';
		}
	}
	// 显示文本的name
	var textName = relation_getTextNameByControl(control);//不含extendDataFormInfo.value
	var $textName = $("input[name*='"+ textName +"']");
	//如果由于各种原因导致返回的结果集为空（例如：原有记录被删除），设置为原有数据，只有在初始加载的时候才需要
	if(isInit && values.length == 0 && texts.length == 0 && $textName.val() != '' ){
		values = hiddenValueArray;
		texts = $textName.val().split(';');
	}
	var valueChange = false;
	var myId = relation_getParentXformFlagIdHasPre(control);
	for ( var i = 0; i < values.length; i++) {
		html += "<label><input name='_" + myId + "'";
		// 如果实际值相等则选择
		if(hasValue && hiddenValue){
			for(var j = 0;j < hiddenValueArray.length;j++){
				if(hiddenValueArray[j] == values[i]){
					html += " checked='true' ";
					valueChange = true;
				}
			}
		}
		// 设置标题
		html += " title='" + title + "' subject='" + subject + "'";
		// 设置必填
		if(isRequired){
			html += " validate='required'";
		}
		html += "' type='checkbox' textValue='" + texts[i].replace(/'/g,"&#039;") + "' value='" + values[i] + "' onclick='" + onclickHtml + "' />" + texts[i];
		// 结束
		html += "</label>";
		if(i<values.length-1){
			html += appendHtml;
		}	
	}
	// 设置必填星号
	if(isRequired){
		var name = "_" + myId;
		var validateFlag = name + "_checkBoxFlag";
		// 保证每次触发时，都把校验信息去除 by zhugr 2017-08-18
		relation_clearValidateTip(name);
		relation_clearValidateTip(validateFlag);
		if(values && values.length == 0){
			var element = $("input[name='" + validateFlag + "']");
			// 如果没有数据的时候，虚构一个隐藏的input（不能是hidden），使其能够应用系统的校验框架
			if(element && element.length == 0){
				html += "<input name='" + validateFlag + "' type='text' style='display:none;'";
				html += " title='" + title + "' subject='" + subject + "' validate='required' value='' />";
			}
		}
		html += "&nbsp;&nbsp;<span class='txtstrong'>*</span>";
	}
	$(control).html(html);
	// 初始化的时候需要初始化值
	relation_checkbox_setFieldValue(control);	
	//初始化发布事件
	var myId = myId.substring(myId.indexOf('(') + 1,myId.indexOf(')'));
	$("xformflag[flagid='" + myId +"']").trigger($.Event("relation_dynamicLoaded"));	
}