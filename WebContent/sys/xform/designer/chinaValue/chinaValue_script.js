/***********************************************
JS文件说明：
	此文件为大小写转换控件，生成中文数字

***********************************************/
//转换大写操作
function XForm_ChinaValueDoExecutor(value, dom){
	if (dom instanceof Array) {
		dom = dom[0];
	}
	var executors = XForm_ChinaValueGetControl(dom);
	if(executors && executors.length > 0){
		var val = XForm_GetChinaValue(value);
		for(var i = 0;i < executors.length;i++){
			var dateFormatType=executors[i].getAttribute("dateFormatType");
			if(!dateFormatType){
				executors[i].value = val;
				$(executors[i]).attr("value",val);
				addSpanVal($(executors[i]),val);
			}else {
				//#170133
				$(executors[i]).css("display","none");
				$(executors[i]).after("<span>"+$(executor).val()+"</span>");
			}
		}
	}
}

//#170133，添加span标签,金额大写值在span中展示，方便换行。
function addSpanVal(inp,val){
	let nextSibling = inp.next();
	if (nextSibling.prop("tagName")=="SPAN"){
		nextSibling.text(val);
	}else {
		inp.css("display","none");
		inp.after("<span>"+val+"</span>");
		inp.closest("div.xform_chinaValue").css("float","left");
	}
}

function XForm_ChinaValueOnLoadFunc(){
	var executors = $('[chinavalue="true"]');
	for(var i = 0;i < executors.length;i++){
		var executor = executors.eq(i),
			relateId = executor.attr('relatedid');
		if(executor.attr('isrow')=='true'){
			var ___relateId = relateId.substring(relateId.lastIndexOf('.') + 1,relateId.length),
				relatedoms =$(GetXFormFieldById(___relateId, true));// $('[name$="'+___relateId+')"]');
			var isExit = false;
			for(var j = 0;j < relatedoms.length;j++){
				if (XForm_GetDetailsTableTr(executor[0]) === XForm_GetDetailsTableTr(relatedoms[j])) {
					var relatedom =  relatedoms.eq(j);
					isExit = true;
					break;
				}
			}
			if(!isExit){
				var relatedom =$(GetXFormFieldById(relateId, true));//$('[name$="value('+relateId+')"]');
			}
		}else{
			var relatedom =$(GetXFormFieldById(relateId, true));//$('[name$="value('+relateId+')"]');
		}
		if(relatedom && relatedom.length > 0){
			var value = XForm_GetChinaValue(relatedom.val());
			executor.val(value);
			$(executor).attr("value",value);
			addSpanVal($(executor),value);
		}else {
			//#170133
			$(executor).css("display","none");
			$(executor).after("<span>"+$(executor).val()+"</span>");
		}
	}
}

//获取大写控件
function XForm_ChinaValueGetControl(dom){
	var forms = document.forms;
	var executor=null;
	var varName = XForm_ChinaValueParseVar(dom);
	if (varName == null) {
		return executor;
	}
	var executors = [];
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			var elem = null;
			elem = elems[j];
			if (elem && elem.name != null && elem.getAttribute && elem.getAttribute("relatedid")!=null && elem.getAttribute("relatedid").indexOf(varName) > -1) {
				if ( elem.getAttribute("isrow") == 'true' && /\.(\d+)\./g.test(dom.name)) {
					if (XForm_GetDetailsTableTr(elem) === XForm_GetDetailsTableTr(dom)) {
						executors.push(elem) ;
					}
				}else{
					executors.push(elem);
				}
			}
		}
	}
	return executors;
}

//返回关联控件的ID
function XForm_ChinaValueParseVar(dom) {
	if (dom.name == '' || dom.name == null) {
		return null;
	}
	var name = dom.name.toString();
	var sIndex = name.indexOf('value(');
	if (sIndex < 0) {
		sIndex = 0;
	}
	var eIndex = name.lastIndexOf(')');
	name = name.substring(sIndex + 6, eIndex);
	var dIndex = name.lastIndexOf('.');
	if (dIndex > -1) {
		name = name.substring(dIndex + 1, name.length);
	}
	return name;
}

//将数字转成中文大写
function XForm_GetChinaValue(value){
	var chineseValue = ""; //转换后的汉字金额
	if(/^(\d{1,3})((,\d{3})+(\.\d+)?)$/.test(value)){
		value=value.replace(new RegExp(",","g"),"");
	}
	//数字才做转化
	if(!isNaN(value)){
		//如果大于9999999999999,提示超出可计算范围
		if(value>9999999999999 || value <-9999999999999){
			chineseValue="超出大写可计算范围";
			return chineseValue;
		}
		//如果是负数,前面加"负"字
		if(value<0){
			chineseValue="负";
			value=Math.abs(value);
		}
		var numberValue = new String(Math.round(value * 100)); //数字金额   
		var String1 ='零壹贰叁肆伍陆柒捌玖'; //汉字数字   
		var String2 =' 万仟佰拾亿仟佰拾万仟佰拾元角分'; //对应单位   
		var len = numberValue.length; //   numberValue的字符串长度   
		var Ch1; //数字的汉语读法   
		var Ch2; //数字位的汉字读法   
		var nZero = 0; //用来计算连续的零值的个数   
		var String3; //指定位置的数值   
		if (numberValue == "0") {
			chineseValue = '零元整';
			return chineseValue;
		}
		String2 = String2.substr(String2.length - len, len); //   取出对应位数的STRING2的值   
		for ( var i = 0; i < len; i++) {
			String3 = parseInt(numberValue.substr(i, 1), 10); //   取出需转换的某一位的值   
			if (i != (len - 3) && i != (len - 7) && i != (len - 11)
					&& i != (len - 15)) {
				if (String3 == 0) {
					Ch1 = "";
					Ch2 = "";
					nZero = nZero + 1;
				} else if (String3 != 0 && nZero != 0) {
					Ch2 = String2.substr(i, 1);
					//#23231 当个位、小数第1位为0时，大写的零不需要加上
					//#29645 大小写转换控件，阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字
					if(Ch2 == '角'){
						Ch1 = String1.substr(String3, 1);
					}else{
						Ch1 = '零' + String1.substr(String3, 1);
					}
					nZero = 0;
				} else {
					Ch1 = String1.substr(String3, 1);
					Ch2 = String2.substr(i, 1);
					nZero = 0;
				}
				//该位是万亿，亿，万，元位等关键位   
			} else { 
				if (String3 != 0 && nZero != 0) {
					Ch1 = '零'
							+ String1.substr(String3, 1);
					Ch2 = String2.substr(i, 1);
					nZero = 0;
				} else if (String3 != 0 && nZero == 0) {
					Ch1 = String1.substr(String3, 1);
					Ch2 = String2.substr(i, 1);
					nZero = 0;
				} else if (String3 == 0 && nZero >= 3) {
					Ch1 = "";
					Ch2 = "";
					nZero = nZero + 1;
				} else {
					Ch1 = "";
					Ch2 = String2.substr(i, 1);
					nZero = nZero + 1;
				}
				//如果该位是亿位或元位，则必须写上   
				if (i == (len - 11) || i == (len - 3)) {
					Ch2 = String2.substr(i, 1);
				}
			}
			chineseValue = chineseValue + Ch1 + Ch2;
		}
		var String4 =0;
		if(len>2){
			String4=parseInt(numberValue.substr(len - 2, 1), 10);
		}
		//最后一位（分）为0时，加上“整”  
		if (String3 == 0 && String4 == 0) {  
			chineseValue = chineseValue;
		}
		var tempVal = new String(value);
		if (tempVal.indexOf(".") < 0) {
			chineseValue += "整";
		} else {
			var flag = true;
			tempVal = tempVal.substr(tempVal.indexOf(".") + 1);
			for ( var i = 0; i < tempVal.length; i++) {
				if (parseInt(tempVal[i]) !== 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				chineseValue += "整";
			}
		}
	}
	return chineseValue;
}
/**
 * 支持业务模块的的字段
 * @returns
 */
function XForm_ChinaValueBindModelFieldChange(row) {
	var context = row || document;
	var executors = $(document).find("[chinavalue='true']");
	for(var i = 0;i < executors.length;i++){
		var executor = executors.eq(i);
		if(!row && executor.attr('isrow')=='true'){
			continue;
		}
		var relateId = executor.attr('relatedid');
		var isNonXForm = $("xformflag[flagid='" + relateId + "']").length == 0;
		if (isNonXForm) {
			$("[name*='" + relateId + "']").bind("input propertychange change",function(event){
				var val = $(event.target).val();
				XForm_ChinaValueDoExecutor(val,event.target);
			});
		}
	}
}

//附加Change的监听事件
function XForm_ChinaValueOnLoad() {
	XFormOnValueChangeFuns.push(XForm_ChinaValueDoExecutor);
	XForm_ChinaValueOnLoadFunc();
	XForm_ChinaValueBindModelFieldChange();
	//新增事件
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		//蛋疼的事件，回调参数里居然没有所在行
		XForm_ChinaValueOnLoadFunc();
		var row = argus.row;
		XForm_ChinaValueBindModelFieldChange(row);
	});
	//复制事件
	$(document).on('table-copy-new','table[showStatisticRow]',function(e,argus){
		//蛋疼的事件，回调参数里居然没有所在行
		XForm_ChinaValueOnLoadFunc();
		//目前动态列表复制的时候会执行新增操作，避免二次事件绑定
		//var row = argus.row;
		//XForm_ChinaValueBindModelFieldChange(row)
	});
}

Com_AddEventListener(window, 'load', XForm_ChinaValueOnLoad);