Com_IncludeFile('json2.js');
Com_IncludeFile("select2.min.js|select2_locale_zh-CN.js|select2.css",Com_Parameter.ContextPath + "sys/xform/designer/relation_select/select2/",null,true);
$( function() {
	// 动态下拉框的xformflag的type为select
	$form.regist({
		support : function(target){
			var isRelationSelect = false;
			if(target.type === "select"){
				if(target.root && target.root.length && target.root.length > 0){
					var flagType = $(target.root).attr("flagtype");
					if(flagType === "xform_relation_select"){
						isRelationSelect = true;
					}
				}
			}
			return isRelationSelect;
		},
		
		// 必填标签兄弟节点
		// 让显示值的input也是指向select
		getRequiredFlagPreElement : function(target){
			var rsDom = target.display || target.element;
			if(target.element && target.element.length > 0 ){
				var curKeyDom = target.element[0];
				if(curKeyDom.tagName === "INPUT"){
					rsDom = target.root.find("select");
				}
			}
			return rsDom;
		},
		
		readOnly : function(target, value){
			var element = target.display || target.element;
			if(value==null){
				if(element){
					return element.prop('disabled');
				}
				return;
			}
			if(element){
				element.prop('disabled', value);
				if(this.inArray(element,$form.disabledSelect) == -1){
					$form.disabledSelect.push(element);
				}
			}else{
				return false;
			}
		},
		inArray : function(element,arr){
			var index = -1;
			for(var i = 0;i < arr.length;i++){
				if(element[0] === arr[i][0]){
					index = i;
					break;
				}
			}
			return index;
		}
	});

    relation_select_load_ready();

	//明细表内控件 有明细表的 table-add 事件触发初始化
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		var row = argus.row;
		$(row).find('select[mytype="relation_select"]').each(function(index,dom){
			var vals = argus.vals;
			var control = dom;
			var w = $(control).attr('width') ? $(control).attr('width'):120;
			if(w.indexOf("%")>-1){
				w = "95%";
			}
			$(control).select2({dropdownAutoWidth:false,width:w});//.on('select2-focus',function(){alert('abc');});
			//删除明细表复制行时出现的的重复控件现象#19147 ＃19098　作者 曹映辉 #日期 2015年9月1日
			if($(control).prev().is("div[class='select2-container']") && $(control).prev().prev().is("div[class='select2-container']")){
				$(control).prev().prev().remove();
			}
			var val = $(control).val();
			// 如果存在vals，即是需要用vals里面的数据填充到动态下拉框的，而不是默认值，不支持联动的情况（联动的更加复杂）
			if(vals){
				val = relation_getControlValByVals(control,vals);
			}
			if(val != ''){
				var inputParams = $(control).attr('inputParams');
				var outputParams = $(control).attr('outputParams');
				var params = $(control).attr('params');
				relation_common_source_base_run(control, inputParams, outputParams, params,val,true,relation_select_addItems);
			}
			//增加传入参数的事件监听
			relation_select_listenBindDomResetValue(control);
		});

		
	});
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		var row = argus.row;
		$(row).find('input[mytype="relation_select"]').each(function(index,dom){
			var vals = argus.vals;
			var control = dom;
			var val = $(control).val();
			// 如果存在vals，即是需要用vals里面的数据填充到动态下拉框的，而不是默认值，不支持联动的情况（联动的更加复杂）
			if(vals){
				val = relation_getControlValByVals(control,vals);
			}
			if(val != ''){
				var inputParams = $(control).attr('inputParams');
				var outputParams = $(control).attr('outputParams');
				var params = $(control).attr('params');
				relation_common_source_base_run(control, inputParams, outputParams, params,val,true,relation_select_addItems_asInput);
			}			
		});		
	});
	//控件点击时操作
	$(document).on('select2-focusnodrop', '[mytype="relation_select"]', function() {
		var control = this;
		var inputParams = $(control).attr('inputParams');
		var outputParams = $(control).attr('outputParams');
		var params = $(control).attr('params');
		relation_common_source_base_run(control, inputParams, outputParams, params,false,false,relation_select_addItems);
	});
	
	//控件change时操作
	$(document).on('change', '[mytype="relation_select"]', function() {
		relation_select_changeValue(this);
	});
});

/** 监听高级明细表 */
$(document).on("detailsTable-init", function(e, tbObj){
    relation_select_load_ready(tbObj);
})

function relation_select_load_ready(tbObj) {
    var context = tbObj || document;
    //非明细表内控件初始化
    $('select[mytype="relation_select"]', context).each(function(){
        if(this.name.indexOf("!{index}")>=0){
            return;
        }
        var w=$(this).attr('width')?$(this).attr('width'):120;
        if(w.indexOf("%")>-1){
			w = "95%";
		}
        $(this).select2({dropdownAutoWidth:false,width:w});

        var control = this;
        //只要实际值不为空，即查询
        if($(control).val() && $(control).val() != '' ){
            var inputParams = $(control).attr('inputParams');
            var outputParams = $(control).attr('outputParams');
            var params = $(control).attr('params');
            //去掉relation_common_source_base_run默认加载动态数据，只显示已保存的数据，以免没加载完成就提交表单，导致显示值为空
            var values = new Array($(control).val());
            var texts = new Array($(control).text());
            // 如果有显示值，则表明是保存过的；没有则是起草的时候，只填了默认实际值
            if($(control).text() != ''){
                relation_select_addItems(control,values,texts,true,$(control).val());
            }else{
                relation_common_source_base_run(control, inputParams, outputParams, params,$(control).val(),true,relation_select_addItems);
            }
        }
        //增加传入参数的事件监听
        relation_select_listenBindDomResetValue(control);
    });
    $('input[mytype="relation_select"]', context).each(function(){
        if(this.name.indexOf("!{index}")>=0){
            return;
        }
        var control = this;
        //只要实际值不为空，即查询
        if($(control).val() && $(control).val() != '' ){
            var inputParams = $(control).attr('inputParams');
            var outputParams = $(control).attr('outputParams');
            var params = $(control).attr('params');
            //去掉relation_common_source_base_run默认加载动态数据，只显示已保存的数据，以免没加载完成就提交表单，导致显示值为空
            var values =$(control).val();
            var texts = $(control).text();
            var defValue = $(control).attr("defvalue");
            if (defValue && defValue !="null" && values === defValue && !texts) {
                // 如果有显示值，则表明是保存过的；没有则是起草的时候，只填了默认实际值
                relation_common_source_base_run(control, inputParams, outputParams, params,$(control).val(),true,relation_select_addItems_asInput);
            }
        }
        //增加传入参数的事件监听
        relation_select_listenBindDomResetValue(control);
    });
}

function relation_select_changeValue(control){
	var text = $(control).find('option:selected').text();
	//有值的的时候才设置，防止编辑页面加载时，用户点击了下拉框不做动作后导致显示文本为空 作者 曹映辉 #日期 2016年12月14日
	if(text){
		//默认 选着 请选着的时候不需要 吧 请选着 几个字带到文本中去 作者 曹映辉 #日期 2017年3月16日
		if($(control).val()==''){
			text="";
		}
		//判断是否有校验规则
		if($(control).attr("validate")){
			if (document.forms != null && document.forms.length >0 && window.$GetFormValidation) {
				$GetFormValidation(document.forms[0]).validateElement(control);
			}
		}
	}
	var textName = relation_getTextNameByControl(control);
	if(textName){
		relation_setFieldValueByDom(control,textName,text);
		//SetXFormFieldValueById(textName, text);	
	}
}

// 动态下拉框 监听输入控件 清空值
function relation_select_listenBindDomResetValue(control){
	var bindDom = $(control).attr('bindDom');
	if(bindDom && bindDom != ''){
		var bindDomArray = bindDom.split(';');
		var controlid = relation_getParentXformFlagId(control);
		if(!controlid){
			controlid = $(control).attr('name');
		}
		var inDetailTable = false;
		var rowIndex;
		if(/\.(\d+)\./g.test(controlid)){
			inDetailTable = true;
		}else{
			inDetailTable = false;
		}
		for(var i = 0;i < bindDomArray.length;i++){
			var bindDomId = bindDomArray[i];
			if(bindDomId != ''){
				if(/-fd(\w+)/g.test(bindDomId)){
					bindDomId = bindDomId.match(/(\S+)-/g)[0].replace("-","");
				}
				// 如果是在明细表里面，处理id
				if(/(\w+)\.(\w+)/g.test(bindDomId)){
					if(inDetailTable){
						rowIndex = controlid.match(/\.(\d+)\./g);
						rowIndex = rowIndex ? rowIndex:[];
						bindDomId = bindDomId.replace(".",rowIndex[0]);	
					}else{
						alert(XformObject_Lang.relationSelect_msg);
						return;
					}
				}
				//获取绑定的事件控件对象
				var bindStr = document.getElementById(bindDomId)?"#" + bindDomId:'[name*="' + bindDomId + '"]';
				$(bindStr).on('change',function(){
					//把实际值和显示值清空
					$(control).val('');
					var textName = relation_getTextNameByControl(control);
					if(textName){
						relation_setFieldValueByDom(control,textName,'');
						//SetXFormFieldValueById(textName, '');	
					}
					//触发change事件
					$(control).change();
				});	
			}
		}	
	}
}
/**
 * 仅用于权限只读下的该控件（此时没有select只有input）
 */
function relation_select_addItems_asInput(control, values, texts, isInit, curVal){
	if(values && texts && values.length>0 && texts.length>0 && curVal){
		for(var i=0;i<values.length;i++){
			if(values[i]==curVal){
				$(control).val(values[i]);				
				var text =$(control).parent().text();
				var type=$(control).attr("type");
				var classNamee=$(control).parent().attr("class");
				
				if(text.indexOf(texts[i])==-1 && classNamee && classNamee=="xform_relation_select"){
					$(control).parent().text(texts[i]);
				}
				break;
			}
		}
	}
}
/*
 * 设置下拉选项样式
 * */
function relation_select_addItems(control, values, texts, isInit, curVal) {	
	var html = "<option value=''>"+XformObject_Lang.ControlPleaseSelect+"</option>";
	var value = $(control).val();
	if(curVal){
		value = curVal;
	}
	if(values.length > 0 && value){
		//当默认值不正确的时候，设置一个空选项 by zhugr 2017-07-31
		if($.inArray(value,values) < 0){
			value = '';
		}
	}
	var hasVal = false;
	for ( var i = 0; i < values.length; i++) {
			html += "<option value='" + values[i] + "'";
			if(values[i] == value && values[i] != ""){
				hasVal = true;
				var textName = relation_getTextNameByControl(control);
				// 设置显示值
				if(textName){
					relation_setFieldValueByDom(control,textName,texts[i],null,isInit);
				}
			}
			html += ">" + texts[i] + "</option>";
	}
	$(control).html(html);
	if(hasVal){
		$(control).select2("val",value); 	
	}
	if(!isInit){
		$(control).select2("open");
	}
}

