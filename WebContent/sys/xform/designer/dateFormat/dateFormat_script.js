/***********************************************
JS文件说明：
	此文件为日期转换控件，生成格式化的时间

***********************************************/
//格式化日期操作
function XForm_DateFormatDoExecutor(value, dom){
	if (dom instanceof Array) {
		dom = dom[0];
	}
	
	var executors = XForm_DateFormatGetControl(dom);
	if(executors && executors.length > 0){
		for(var i = 0;i < executors.length;i++){
			var val = XForm_GetDateFormat(value,executors[i].getAttribute("dateFormatType"));
			executors[i].value = val;
			$(executors[i]).attr("value",val);
		}
	}
}

function XForm_DateFormatOnLoadFunc(){
	
	var executors = $('[dateformat="true"]');
	for(var i = 0;i < executors.length;i++){
		var executor = executors.eq(i),
			relateId = executor.attr('relatedid'),
			dateFormatType= executor.attr('dateFormatType');
		if(executor.attr('isrow')=='true'){
			var ___relateId = relateId.substring(relateId.lastIndexOf('.') + 1,relateId.length);
            if(/\.(\d+)\./g.test($(executor).attr("name"))){
                var executorName = /\.(\d+)\./g.exec($(executor).attr("name"));
                if (executorName && executorName.length == 2 && relateId.indexOf(".") > -1) {
                    relateId = relateId.replace(".", "." + executorName[1] + ".");
                }
            }
			var	relatedoms =$(GetXFormFieldById(relateId, true));// $('[name$="'+___relateId+')"]');
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
			var value = XForm_GetDateFormat(relatedom.val(),dateFormatType);
			executor.val(value);
			$(executor).attr("value",value);
		}
	}
}

//获取日期格式化控件
function XForm_DateFormatGetControl(dom){
	
	var forms = document.forms;
	var executor=null;
	var varName = XForm_DateFormatParseVar(dom);
	if (varName == null) {
		return executor;
	}
	var executors = [];
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			var elem = null;
			elem = elems[j];
			if (elem && elem.name != null && elem.getAttribute && elem.getAttribute("relatedid")!=null&&elem.getAttribute("relatedid").indexOf(varName) > -1&& elem.getAttribute("dateFormatType")!=null) {
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
function XForm_DateFormatParseVar(dom) {
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

//判断是否是ie
function isIe(){
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    return isIE||isEdge||isIE11;
}
// 由于ie转化异常，需要拼接日期才能转化格式
function splicValue(value,dateFormatType){
	var resultValue = "";
	var _number = (value.split('\/')).length-1;
	if( _number==1 && value.length==7  ){
		var yearMonths=value.split('\/');
		var yearTemp=null;
		var monthTemp=null;
		for(var z=0;z<yearMonths.length;z++){
			if(yearMonths[z].length===4){
				yearTemp=yearMonths[z];
			}
			if(yearMonths[z].length===2){
				monthTemp=parseInt(yearMonths[z])-1;
			}
		}


		var dateTemp=new Date();
		dateTemp.setFullYear(yearTemp,monthTemp,1);

		resultValue=dateTemp.Format(Com_Parameter.Date_format);
	}else {
		resultValue = value;
	}
	return resultValue;
}
// 获取格式化的值
function getDateFormatValue(oldValue,newValue,dateFormatType){
	var resultValue = "";
	var _number = (oldValue.split('\/')).length-1
	if(oldValue.indexOf(":")>-1 && oldValue.indexOf("\/") == -1){
	   // 如果日期格式是   12：30  这种格式 则直接返回
       return oldValue;
    }else{// 其他情况则走格式化转化的代码
	   resultValue = new Date(newValue).Format(dateFormatType);
       if(_number ==1 && oldValue.indexOf(":") == -1){
	       // 如果日期格式是   2029/05  这种格式 则直接返回
           if(dateFormatType.indexOf("月")>-1){
	            resultValue = resultValue.substring(0,8);
           }else{
	            resultValue = resultValue.substring(0,7);
           }

       }
       return resultValue;
    }
    
}
//将时间格式化输出
function XForm_GetDateFormat(value,dateFormatType){
	
	var dateFormatValue = ""; //转换后的时间格式化
	
	if(value){
		try {			
			value=value.replace(new RegExp("-","g"),"\/");
            var newValue = splicValue(value,dateFormatType);
			dateFormatValue = getDateFormatValue(value,newValue,dateFormatType);  
		} catch (error) {
		    //在错误发生时怎么处理
			console.log(error.message);
		}
	}
	
	return dateFormatValue;
}

Date.prototype.Format = function (fmt) {                    
    var o = {
            "M+": this.getMonth() + 1,                      //月份 
            "d+": this.getDate(),                           //日 
            "H+": this.getHours(),                          //小时 
            "m+": this.getMinutes(),                        //分 
            "s+": this.getSeconds(),                        //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3),    //季度 
            "S": this.getMilliseconds()                     //毫秒 
    };
    if (/(y+)/.test(fmt)) 
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) 
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


/** 监听高级明细表 */
$(document).on("detailsTable-init-begin", function(e, tbObj){
    XForm_DateFormat_Ready();
})

var XForm_DateFormat_Loaded = false;

//附加Change的监听事件
function XForm_DateFormat_Ready() {
    if (!XForm_DateFormat_Loaded) {
        XForm_DateFormat_Loaded = true;
        XFormOnValueChangeFuns.push(XForm_DateFormatDoExecutor);
        XForm_DateFormatOnLoadFunc();
        //新增事件
        $(document).on('table-add','table[showStatisticRow]',function(){
            //蛋疼的事件，回调参数里居然没有所在行
            XForm_DateFormatOnLoadFunc();
        });
        //复制事件
        $(document).on('table-copy','table[showStatisticRow]',function(){
            //蛋疼的事件，回调参数里居然没有所在行
            XForm_DateFormatOnLoadFunc();
        });
    }
}
Com_AddEventListener(window, 'load', XForm_DateFormat_Ready);