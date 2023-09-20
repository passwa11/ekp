define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct","mui/util","mui/tabbar/TabBarButton",
         "sys/xform/mobile/controls/massdata/MassData","dojo/_base/event","sys/xform/mobile/controls/xformUtil","dojo/dom-style","dojo/request","dijit/registry","mui/base64","dojo/query" ,"dojo/dom"], 
		function(declare, WidgetBase,domConstruct, util, TabBarButton, massdata, event, xUtil,domStyle,request,registry,base64,query,dom) {
	var claz = declare("sys.xform.mobile.controls.massdata.template._MassDataToolBarMixin", [TabBarButton], {
		defaultUrl : "/sys/xform/controls/relation.do?method=run",

		_onClick : function() {
			// 发请求
			var params = {};
			var massData = registry.byId(this.controlId);
			params._source = massData._source;
			params._key = massData._key;
			// 处理传出
			var dataOutput = this.buildOutputParams(massData.outputParams);
			// 处理传入
			var dataInput = this.buildInputParams(massData.inputParams);
			
			params.outs = JSON.stringify(dataOutput);
			params.ins = JSON.stringify(dataInput);
			var url = util.formatUrl(util.urlResolver(this.defaultUrl,this));
			this.buildLoading();
			//加密条件信息
			//params.conditionsUUID = hex_md5(params.ins + "" + params._key);
			self = this;
			request.post(url,{handleAs : 'json',data : params}).then(function(rs){
				//格式化数据
				var records = self.formatRequestDatas(rs);
				if(massData.onDataLoad){
					massData.onDataLoad(records);
					domStyle.set(self.tmpLoading,"display","none");
				}
			});
		},
		buildLoading:function(){
			if(this.tmpLoading == null){
				this.tmpLoading = domConstruct.create("div",{className:'muiCateLoading',style:{
					display:"inline-block"
				},innerHTML:'<i class="mui mui-loading mui-spin"></i>'},this.domNode);
			}else{
				domStyle.set(self.tmpLoading,"display","inline-block");
			}
		},
		formatRequestDatas:function(data){
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
					formatRecord[base64.decode(fieldInfo["fieldId"])] = {value:fieldInfo["fieldValue"]};
				}
			}
			return rs;
		},
		buildOutputParams:function(outputParams){
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
		},
		
		buildInputParams:function(inputsJSON){
			var data=[];
			var inputObject = inputsJSON;
			/*if(typeof inputObject == 'string'){
				JSON.parse(inputObject);
			}*/
			//构建输入参数.replace(/quot;/g,"\"")
			for ( var uuid in inputObject) {
				var formId = inputObject[uuid].fieldIdForm;
				var formName = inputObject[uuid].fieldNameForm;
				formId = formId.replace(/\$/g, "");
				//获取字段的值 
				var wgts = xUtil.getXformWidgetsBlur(null, formId);
				var val = [];
				for (var j = 0; j < wgts.length; j++) {
		              if (
		                wgts[j].declaredClass == "mui.form.Address" ||
		                wgts[j].declaredClass == "sys.xform.mobile.controls.NewAddress"
		              ) {
		            	  val.push(wgts[j].curIds);
		            	  val.push(wgts[j].curNames);
		              } else {
		            	  val.push(wgts[j].value);
		              }
		            }
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
	});
	return claz;
});