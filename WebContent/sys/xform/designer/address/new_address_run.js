/***********************************************
JS文件说明：
	此文件专为pc端高级地址本解析时使用的JS。

作者：王逍
版本：1.0 2017-8-11
***********************************************/

$(function(){
	//新建时有初始值和传参时自动加载
	//#157884 权限区段控制的明细表，明细表内的高级地址本，在有编辑权限的流程节点，设置了初始值，无法传出参数
	var flag = false;
	if (!flag){
		$(document).on("table-add",function(event,source){
			new_address_tableAdd(event,source);
			flag = true;
		});
	}
	var loadDoms = $("xformflag[flagtype='xform_new_address']:not([flagid*='!{index}'])").find("input[new_address_autoload='true']");
	new_address_load(loadDoms);
});

function new_address_load(loadDoms){
	for (var i = 0; i < loadDoms.length; i++){
		var $loadDom = $(loadDoms[i]);
		if($loadDom.closest("xformflag[flagtype='xform_new_address']:not([flagid*='!{index}'])").data('right') != "edit"){
			continue;
		}
		if($loadDom.attr('params') && $loadDom.attr('orgtype')){
			var $flag = $loadDom.parents("xformflag[flagtype='xform_new_address']");
			if($flag.length>0){
				var filedsInfo = GetXFormFieldById($flag.attr('flagid'), true);
				for(var j = 0;j < filedsInfo.length; j++){
					if(/\w+\.id/g.test($(filedsInfo[j]).attr('name'))){
						var value = [];
						value.push($(filedsInfo[j]).val())
						setAddressOutputParams(value,$loadDom.attr('params'),$loadDom.attr('orgtype'),$flag.attr('flagid'));
						break;
					}
				}
			}
		}
	}
}

function new_address_tableAdd(event,source){
	var loadDoms = $(source).find("xformflag[flagtype='xform_new_address']").find("input[new_address_autoload='true']");
	new_address_load(loadDoms);
}

function setNewAddressOutputParams(value,outputParams,orgType,domElmentArr){
	var $flag = $(domElmentArr[0]).parents("xformflag[flagtype='xform_new_address']");
	setAddressOutputParams(value,outputParams,orgType,$flag.attr('flagid'));
}

//根据输出参数来赋值
function setAddressOutputParams(value,outputParams,orgType,id){
	if(value){
		//xform_ajax_count++;
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/xform/controls/address.do?method=getInfos&orgType="+encodeURIComponent(orgType)+"&id="+value[0]+"&outputParams="+encodeURIComponent(outputParams)+"&fdControlId="+id,
			type:'GET',
			async:false,//异步请求
			success: function(json){
				if(json && json.results){
					var fdControlId = json.fdControlId;
					var results = json.results;
					for(var fieldIdForm in results){
						var msg = '';
						var info = results[fieldIdForm];
						if(info.length>0){
							for(var i=0;i<info.length-1;i++){
								msg+=info[i].fd+';';
							}
							msg+=info[info.length-1].fd;
						}
						if(fieldIdForm.indexOf(".")>-1&&fdControlId.indexOf(".")>-1){
							//地址本和所需赋值的控件都在明细表
							var index = fdControlId.match(/\.(\d+)\./g);
							fieldIdForm = fieldIdForm.replace('.',index);
						}else if(fieldIdForm.indexOf(".")<0&&fdControlId.indexOf(".")<0){
							//地址本和所需赋值的控件都不在明细表
						}else if(fieldIdForm.indexOf(".")>-1&&fdControlId.indexOf(".")<0){
							//地址本不在，所需赋值的控件在
						}else if(fieldIdForm.indexOf(".")<0&&fdControlId.indexOf(".")>-1){
							//地址本在，所需赋值的控件不在
						}
						var filedsInfo = GetXFormFieldById(fieldIdForm, true);
						for(var i = 0;i<filedsInfo.length;i++){
							if(filedsInfo[i].type == 'checkbox'){
								filedsInfo[i].checked = false;
							}
						}
						SetXFormFieldValueById(fieldIdForm,msg);
						for(var i = 0;i<filedsInfo.length;i++){
							// 手动触发change
							// blur 用于jsp片段监控的；change 用于普通input的
							$(filedsInfo[i]).trigger($.Event("blur"));
							$(filedsInfo[i]).trigger($.Event("change"));
						}
					}
				}
			},
			// complete : function() {
			// 	xform_ajax_count--;
			// },
			dataType: 'json'
		});
	}
}
//兼容校验器
if (window.XForm_ValidatorDoExecutorAll){
	XForm_ValidatorDoExecutorAll();
}

function set_new_address_setParams(domElement,scriptIds){
	var $flag = $(domElement).parents("xformflag[flagtype='xform_new_address']");
	var flagid = $flag.attr('flagid');
	if (!$flag[0] && !flagid){
		$flag = $(domElement).parents("xformflag[flagtype='formula_calculation']");
		flagid = $flag.attr('flagid');
	}
	return new_address_setParams(scriptIds,flagid);
}

//抓取公式解析时所需的field参数
function new_address_setParams(scriptIds,controlId){
	if(scriptIds!=null&&scriptIds!=''&&scriptIds!='null'){
		var autoFiledIds = scriptIds.split(';');
		//用来存储 公式绑定域的属性和值如：{fd_134343:aaaa,fd_dfdafafd:bbbb}
		var filedsJSON = {};
		for(var i=0;i<autoFiledIds.length;i++){
			if(controlId.indexOf(".")>-1&&autoFiledIds[i].indexOf(".")>-1){
				//地址本和参数控件都在明细表内
				var index = controlId.match(/\.(\d+)\./g);
					//controlId.substring(controlId.indexOf("."),controlId.lastIndexOf(".")+1);
				var filedsInfo = GetXFormFieldById(autoFiledIds[i].replace('.',index), true);
			}else{
				var filedsInfo = GetXFormFieldById(autoFiledIds[i], true);
			}
			
			//文本框根据同一个名字获取多个对象后，取同名称最匹配的对象。
			if(filedsInfo&&filedsInfo.length>1){
				for(var j=0;j<filedsInfo.length;j++){
					if(filedsInfo[j].type=='text' && filedsInfo[j].name==autoFiledIds[i]){
						var temp=filedsInfo[j];
						filedsInfo=[];
						filedsInfo.push(temp);
						break;
					}
				}
			}
			var valueInfo=[];
			for(var j=0;j<filedsInfo.length;j++){
				if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox' || filedsInfo[j].type == 'select' || filedsInfo[j].type == 'select-one') {
					if(filedsInfo[j].name == "_"+autoFiledIds[i]) {
						// 排除标签的特殊处理
						continue;
					}
				}
				if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox') {
					if (filedsInfo[j].checked) valueInfo.push(filedsInfo[j].value);
					continue;
				}
				valueInfo.push(filedsInfo[j].value);
			}
			
			if(!valueInfo || valueInfo.length==0){
				valueInfo = "null";
			}
			filedsJSON[autoFiledIds[i]] = valueInfo;
		}
	}
	
	//请求参数数组
	var paramArray = new Array();
	//url格式的参数
	for(filedId in filedsJSON){
		if(filedsJSON[filedId] != "null") {
			paramArray.push(encodeURIComponent(filedId)+"="+encodeURIComponent(filedsJSON[filedId]));
		}
	}
	paramArray.push("modelId="+encodeURIComponent(_xformMainModelId));
	paramArray.push("modelName="+encodeURIComponent(_xformMainModelClass));
	
	var _extendFilePath = $("input[name$='extendDataFormInfo.extendFilePath']")[0];
	if(_extendFilePath){
		var extendFilePath = _extendFilePath.value;
		paramArray.push("extendFilePath="+encodeURIComponent(extendFilePath));
	}
	paramArray.push("fdControlId="+encodeURIComponent(controlId));
	return paramArray.join('&');
}