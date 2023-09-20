define(function(require, exports, module) {
	var $ = require('lui/jquery');
	
	exports.initColumns = function(cfg){
		// 初始化传入参数
		cfg.inputParams = cfg.inputParams || "{}";
		cfg.inputParams = $.parseJSON(cfg.inputParams.replace(/quot;/g,"\""));
		// 初始化传出参数
		cfg.outputParams = cfg.outputParams || "{}";
		cfg.outputParams = $.parseJSON(cfg.outputParams.replace(/quot;/g,"\""));
		// 格式化列定义，返回以字段名为key的json：{docSubject:{title:xxx}}，方便数据通过key定位
		cfg.columns = formatColumns(cfg.outputParams);
	}

	exports.request = function(cfg,cb){
		var params = {};
		params._source = cfg._source;
		params._key = cfg._key;
		// 处理传出
		var dataOutput = buildOutputParams(cfg.outputParams);
		// 处理传入
		var dataInput = buildInputParams(cfg.inputParams);
		
		params.outs = JSON.stringify(dataOutput);
		params.ins = JSON.stringify(dataInput);
		cfg.owner.showLoading();
		//加密条件信息
		params.conditionsUUID = hex_md5(params.ins + "" + params._key);
		$.ajax( {
			url : Com_Parameter.ContextPath + "sys/xform/controls/relation.do?method=run",
			type : 'post',
			async : true,//是否异步 ,先改成异步方式
			data : params,
			dataType : 'json',
			success : function(rs) {
				var records = formatRequestRecords(rs);
				cb(records);
				cfg.owner.hideLoading();
			}
		});
	}
	
	exports.getBtnLabel = function(){
		return "获取数据";
	}
	
	// params : {}
	function formatColumns(params){
		var columns = {};
		for(var key in params){
			var param = params[key];
			columns[Base64.decode(param["fieldId"])] = {title:param["fieldTitle"]};
		}
		return columns;
	}
	
	function buildOutputParams(outputParams){
		var outs=[];
		var tempOuts={};
		for(var key in outputParams){
			var fieldId = outputParams[key].fieldId;
			if(fieldId){
				fieldId = fieldId.replace(/\$/g, "");
				// 删除重复值
				if(tempOuts[fieldId]){
					continue;
				}
				tempOuts[fieldId] = true;
				outs.push({"uuId":fieldId});	
			}
		}
		return outs;
	}
	
	function buildInputParams(inputsJSON){
		var data=[];
		//构建输入参数
		for ( var uuid in inputsJSON) {
			var formId = inputsJSON[uuid].fieldIdForm;
			var formName = inputsJSON[uuid].fieldNameForm;
			formId = formId.replace(/\$/g, "");
			//获取字段的值 GetXFormFieldValueById_ext 来源于xform.js里面的方法
			var val = GetXFormFieldValueById_ext(formId,true);
			if(/-fd(\w+)/g.test(formId)){
				formId = formId.match(/(\S+)-/g)[0].replace("-","");
			}
			for(var i = 0;i < val.length;i++){
				data.push( {
					"uuId" : uuid,
					"fieldIdForm" : formId,
					"fieldValueForm" : val[i]
				});
			}
		}
		return data;
	}
	
	//生成固定结构--{"records":[{docSubject:{value:xx},fdId:{value:xx},fdName:{value:xx},docCreateTime:{value:xx}}]}
	function formatRequestRecords(data){
		var rs = {};
		var records = rs["records"] = [];
		if(data.outs.length > 0){
			for(var i = 0;i < data.outs.length;i++){
				// {fieldId:xxx(base64加密),fieldValue:xxx,rowIndex:xxx}
				var fieldInfo = data.outs[i];
				if(records.length <= fieldInfo["rowIndex"]){
					records[fieldInfo["rowIndex"]] = {};
				}
				var formatRecord = records[fieldInfo["rowIndex"]];
				formatRecord[Base64.decode(fieldInfo["fieldId"])] = {value:fieldInfo["fieldValue"]};
			}
		}
		return rs;
	}

})