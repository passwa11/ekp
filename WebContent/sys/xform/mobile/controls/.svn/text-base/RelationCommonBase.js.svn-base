//动态下拉、单选、多选共同继承的基类
define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-prop",  "mui/form/_FormBase", 
         "sys/xform/mobile/controls/xformUtil", "dojo/request", "mui/util", "mui/dialog/Tip",
         "mui/device/device","mui/i18n/i18n!sys-xform-base:mui"], 
          function(declare,  domConstruct, domProp, _FormBase, xUtil, request, util, Tip, device, Msg) {
	
	var claz = declare("sys.xform.mobile.controls.RelationCommonBase", [_FormBase], {
		//入参
		inputParams:null,
		
		//出参
		outputParams:null,
		
		source:null,
		
		funKey:null,
		
		mul:false,
		
		textName:null,
		
		_working:false,
		
		_inDetailTable:null,
		
		needToUpdateAttInDetail : ['name','textName'],
		
		buildRendering : function() {
			this.inherited(arguments);
			this.inputParams = this._preDealExpression(this.inputParams);
			this.outputParams = this._preDealExpression(this.outputParams);
			this.source = this._preDealExpression(this.source);
			this.funKey = this._preDealExpression(this.funKey);
			this.textName = this._preDealExpression(this.textName);
			this.values = [{"value":this.value,"text":this.text}];
			if(/\.(\d+)\./g.test(this.name)){
				this._inDetailTable = true;
			}else{
				this._inDetailTable = false;
			}
		},
		
		// 匹配source里面的源目标元素和目标元素，执行函数fun
		execFunByDom : function(source,fun){
			var evtObjName = source.evtObjName;
			var bindDomIds = source.bindDoms;
			var rowIndex;
			// 获取索引
			if(this._inDetailTable == true){
				rowIndex = this.name.match(/\.(\d+)\./g);
				rowIndex = rowIndex ? rowIndex:[];
			}
			// 只有表单元素才处理
			if(evtObjName.indexOf('.value(') > -1){
				if(/\.(\d+)\./g.test(evtObjName)){
					evtObjName = evtObjName.match(/\((\w+)\.(\d+)\.(\w+)/g)[0].replace("(","");	
				}else{
					evtObjName = evtObjName.match(/\((\w+)/g)[0].replace("(","");
				}
			}else{
				return;
			}
			if(evtObjName != null && evtObjName != ''){
				if(bindDomIds){
					var domArray = bindDomIds.split(";");
					for(var i = 0;i < domArray.length;i++){
						var inputId = domArray[i];
						if(/-fd(\w+)/g.test(inputId)){
							inputId = inputId.match(/(\S+)-/g)[0].replace("-","");
						}
						if(this._inDetailTable == true){
							// 替换索引
							inputId = inputId.replace(".",rowIndex[0]);	
						}
						if(evtObjName == inputId){
							fun();
							break;
						}
					}
				}
			}
		},
		
		listenInputWgt : function(srcObj, arguContext, bindDom){
			if(srcObj){
				var evtObjName = srcObj.get("name");
				if(evtObjName == null || evtObjName == ''){
					return;
				}
				var self = this;
				this.execFunByDom({'evtObjName':evtObjName,'bindDoms':bindDom},function(){
					self.queryData();	
				});	
			}
			
		},
		
		queryData:function(isInit,canReload){
			if(this._working==true)
				return;
			this._working = true;
			
		 	var data = {'_source':this.source,'_key':this.funKey};
		 	data.ins = [];
		 	var inputsJSON = {};
		 	if(this.inputParams!=null && this.inputParams!="")
		 		inputsJSON = JSON.parse(this.inputParams.replace(/quot;/g, "\""));
		 	var outputsJSON = {};
		 	if(this.outputParams!=null && this.outputParams!="")
		 		outputsJSON = JSON.parse(this.outputParams.replace(/quot;/g, "\""));
		 	var hiddenValue = outputsJSON["hiddenValue"].uuId;
		 	var textValue = outputsJSON["textValue"].uuId;
		 	//	构建输出参数
		 	var outs=[];
		 	var outsAry=(hiddenValue+textValue).match(/\$[^\$]+\$/g);
		 	outsAry=outsAry?outsAry:[];
		 	var temoOuts={};
		 	for(var i=0;i<outsAry.length;i++){
		 		var outsUuId=outsAry[i].replace(/\$/g, "");
		 		//去掉重复的输出参数
		 		if(temoOuts[outsUuId]){
		 			continue;
		 		}
				temoOuts[outsUuId]=true;
				outs.push({"uuId":outsUuId});
			}
		 	data.outs=JSON.stringify(outs);
		
		 	var dataInput = xUtil.buildInputParams(this.get("name"),inputsJSON,isInit);
		 	if(!dataInput){
		 		this._working = false;
		 		return ;
		 	}
		
		 	//把json数字 字符串化
		 	data.ins = JSON.stringify(dataInput);// .replace(/"/g,"'");
		 	data.conditionsUUID=xUtil.toMD5(data.ins+""+data._key);
		 	// 校验传入参数是否相同，相同则无需重复加载
			if (this.inputParamValues == data.ins) {
				return;
			}
			this.values = [];
			this.inputParamValues = data.ins;
			var self = this; 
			request.post(util.formatUrl("/sys/xform/controls/relation.do?method=run"),{data:data, handleAs : 'json', sync:true}).then(function(json){
				if(!json){
					json = {};
					json.outs = [];
				}
				//增加排序防止出现 id和name错乱
				if(json.outs){
					//sort在不同浏览器排序规则不同，先兼容钉钉
					if(device && device.getClientType() == 11){
						for(var i=0; i<json.outs.length; i++){
							for(var j=0; j<json.outs.length-i-1; j++){
								if(json.outs[j].rowIndex > json.outs[j+1].rowIndex){
									var temp = json.outs[j];
									json.outs[j] = json.outs[j+1];
									json.outs[j+1] = temp;
								}
							}
						}
					}else{
						json.outs.sort(function(a,b){
							return a.rowIndex-b.rowIndex;
						});
					}
				}
				var values = relation_getFiledsById(json, hiddenValue);
				var texts =  relation_getFiledsById(json, textValue);
				self.addItems(values,texts,isInit);
				self._working = false;
			},function(){
				Tip.fail({text:Msg["mui.eventbase.errorMsg"]});
			});
			//如果传入参数可以重新加载，则将_working改为false
			if(canReload){
				this._working = false;
			}
		},
		
		_preDealExpression:function(tempStr){
			var scriptDom = domConstruct.create("div",{innerHTML:tempStr});
			return domProp.get(scriptDom,"textContent");
		}
	});
	
	function relation_getFiledById(row,result, script) {
		script = script.replace(/\$[^\$]+\$/g,function(id){
			for ( var attr in result) {
				//row取的是最大的长度的属性数据，其他不足该长度的属性直接设置为空
				if(result[attr].length<=row){
					continue ;
				}
				var op = result[attr][row];
				var uuid = op.uuId ? op.uuId : op.fieldId;
				if (id == "$"+uuid+"$") {
					return op.fieldValue;
				}
			}
			//找不到表达式对应的值直接设置为空
			return "";
		});
		return script;
	}
	function relation_getFiledsById(result, script){
		var res=[];
		var rows=0;
		var cols=0;
		var tables=[];
		if(!result || !result.outs){
			return res;
		}
		//把传出结果变成字段数组
		for ( var i = 0; i < result.outs.length; i++) {
			var op = result.outs[i];
			var uuid = op.uuId ? op.uuId : op.fieldId;
			if(!res[uuid]){
				cols++;
				res[uuid]=[];
			}
			res[uuid].push(op);
			//取表达式中 最大的数据行作为有效行
			if(script.indexOf("$"+uuid+"$") != -1 && res[uuid].length>rows){
				rows=res[uuid].length;
			}
		}
		var rtn=[];
		for(var i=0;i<rows;i++){
			rtn.push(relation_getFiledById(i,res,script));
		}
		return rtn;
	}

	
	return claz;
});
