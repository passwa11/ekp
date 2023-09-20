/**
 * 
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "dijit/_WidgetBase","dojo/request", "mui/util", "dojo/topic", "sys/modeling/main/xform/controls/placeholder/mobile/RadioGroup",
		"dojo/dom-construct", "sys/modeling/main/xform/controls/placeholder/mobile/dialog/Dialog", "sys/modeling/main/xform/controls/placeholder/mobile/dialog/MultiDialog",
		"sys/modeling/main/xform/controls/placeholder/mobile/PlaceholderViewWgt","sys/modeling/main/xform/controls/placeholder/mobile/CheckBoxGroup",
		"sys/modeling/main/xform/controls/placeholder/mobile/tree/Tree","sys/modeling/main/xform/controls/placeholder/mobile/tree/MultiTree",
		"sys/modeling/main/xform/controls/placeholder/mobile/Select","mui/i18n/i18n!:error",
		"dojox/mobile/viewRegistry"], 
		function(declare, lang, WidgetBase, request, util, topic, RadioGroup, domConstruct, Dialog, MultiDialog, PlaceholderViewWgt,CheckBoxGroup, Tree,MultiTree, Select, Msg,viewRegistry) {
	
	// 后台配置信息缓存
	var __cfgData = null;
	var __isSend = false;
	var tmpWgtType = {
		"0" : Dialog,
		"1" : MultiDialog,
		"2" : Select,
		"3" : RadioGroup,
		"4" : CheckBoxGroup,
		"5" : Tree,
		"6" : MultiTree
	};
	
	// 主要是为了预防每个控件都发送请求
	function loadCfg(fdAppModelId){
		if(!__isSend){
			var url = "/sys/modeling/main/modelingAppXFormMain.do?method=getRelationDynamic&modelName="
						+ _xformMainModelClass + "&fdAppModelId=" + fdAppModelId;
			__isSend = true;
			request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(data){
				__cfgData = formatData(data);
				topic.publish("/modeling/placeholder/dataOnload",__cfgData);
			});		
		}else{
			if(__cfgData){
				topic.publish("/modeling/placeholder/dataOnload", __cfgData);				
			}
		}
	}
	
	// 合并输出参数的明细表key
	function formatData(data){
		for(var controlId in data){
			if(data[controlId].status === "00" && data[controlId]["outputs"]){
				// 1、把目标控件是明细表的，单独放置；2、把源控件的明细表ID合并
				var commonFields = data[controlId]["outputs"]["fields"] || [];
				var details = data[controlId]["outputs"]["details"] = {};
				for(var sourceControlId in commonFields){
					var outCfg = commonFields[sourceControlId];
					// 如果目标控件是明细表，则放置到details里面
					for(var i = outCfg.target.length-1;i >= 0;i--){
						var targetInfo = outCfg.target[i];
						if(targetInfo.controlId.indexOf(".") > -1){
							if(!details.hasOwnProperty(sourceControlId)){
								details[sourceControlId] = {target:[],type:outCfg.type};
							}
							details[sourceControlId].target.push(targetInfo);
							outCfg.target.splice(i,1);
						}
					}
				}
				// 把源控件的明细表ID合并
				data[controlId]["outputs"]["fields"] = _divideDetailsId(commonFields,"sourceCommon", "sourceDetails");
				data[controlId]["outputs"]["details"] = _divideDetailsId(details,"sourceCommon", "sourceDetails");
			}
		}
		return data;
	}
	
	function _divideDetailsId(fields, sourceCommonKey, sourceDetailsKey){
		var temp = {};
		temp[sourceCommonKey] = {};
		temp[sourceDetailsKey] = {};
		for(var key in fields){
			var field = fields[key];
			if(key.indexOf(".") > -1){
				// fd_380da16ff9ebfa.fd_380da171fa821e
				var keyArr = key.split(".");
				if(!temp[sourceDetailsKey].hasOwnProperty(keyArr[0])){
					temp[sourceDetailsKey][keyArr[0]] = {};
				}
				temp[sourceDetailsKey][keyArr[0]][keyArr[1]] = field;
			}else{
				if(!temp[sourceCommonKey].hasOwnProperty(key)){
					temp[sourceCommonKey][key] = {};
				}
				temp[sourceCommonKey][key] = field;
			}
		}
		return temp;
	}
	
	var claz = declare("sys.modeling.main.xform.controls.placeholder.mobile.PlaceholderDispatcher", [ WidgetBase ], {
		
		buildRendering : function(){
			this.inherited(arguments);
			this.dataOnLoadhandler = topic.subscribe("/modeling/placeholder/dataOnload",lang.hitch(this,this.onDataLoad));
		},
		
		startup : function() {
			this.inherited(arguments);
			this.fdAppModelId = document.getElementsByName("fdModelId")[0].value;
			loadCfg(this.fdAppModelId);
			//添加另外的校验
			this.addExtValidate();
		},
		
		addExtValidate: function(){
			var _self = this;
			this.validateView= this.getValidateView();
			if(this.validateView&& this.validateView._validation){
				this.validateView._validation.addValidator(
					"textMaxLength",
					Msg['errors.maxLength.simple'].replace("{0}","{name}").replace("{1}","{maxLength}"),
					function(v, e, o) {
						v = e.hiddenTextNode.value;
						var length = isNaN(o['length']) ? 0 : parseInt(o['length']);
						if (length == 0 || _self.validateView._validation.getValidator('isEmpty').test(v)) return true;
						o['maxLength'] = length; //Math.floor(length/30)*20;
						var newvalue = v.replace(/[^\x00-\xff]/g, "***");
						return newvalue.length <= length;
					}
				);
			}

		},
		
		getValidateView: function(domNode) {
	        var node = this.domNode || domNode;
	        var view = viewRegistry.getEnclosingView(node);
	        while (view != null && !view._validation) {
	          view = viewRegistry.getParentView(view);
	        }
	        return view;
	      },
		
		onDataLoad : function(data){
			this.fdAppModelId = document.getElementsByName("fdModelId")[0].value;
			if(data){
				for(var key in data){
					if(key.indexOf(this.controlId) > -1){
						var relationCfg = data[key];
						if(relationCfg.status === '00'){
							if(this.showStatus === "edit"){
								this.buildEdit(relationCfg, key);
							}else{
								this.buildView(relationCfg, key);
							}
							// 销毁当前组件
							this.dataOnLoadhandler.remove();
							this.destroy();
							break;
						}else{
							console.warn("业务关联控件("+ this.controlId +")没有在后台配置业务关联！");
						}
					}
				}
			}
		},
		
		buildEdit : function(relationCfg, key){
			var itemContainer = domConstruct.create("div",{},this.domNode,"after");
			var render = this.getRender(relationCfg["showType"]);
			//若校验中含有长度校验，要补充文本的长度校验
			if(this.validate && this.validate.indexOf("maxLength") > -1){
				var regex1 = /maxLength\((.+?)\)/g;
				var regex2 = /\((.+?)\)/g;
				var result = this.validate.match(regex1);
				if(result && result[0]){
					result = result[0].match(regex2);
				}
				if(result && result[0]){
					this.validate += " textMaxLength"+result[0]+" ";
				}
			}
			new render({
				"domNode" : itemContainer,
				"name" : this.name,
				"textName" : this.textName,
				"showStatus": this.showStatus,
				"controlId" : key,
				"appModelId" : this.fdAppModelId,
				"envInfo" : relationCfg,
				"subject" : this.subject,
				"text" : this.text,
				"value" : this.value,
				"required" : this.required,
				"validate" : this.validate,
				"align" : this.align
			}).startup();
		},
		
		buildView : function(relationCfg, key){
			var through = relationCfg.through || {};
			var itemContainer = domConstruct.create("div",{},this.domNode,"after");
			new PlaceholderViewWgt({
				"domNode" : itemContainer,
				"name" : this.name,
				"textName" : this.textName,
				"showStatus": this.showStatus,
				"controlId" : key,
				"appModelId" : this.fdAppModelId,
				"envInfo" : relationCfg,
				"subject" : this.subject,
				"text" : this.text,
				"value" : this.value,
				"required" : this.required,
				"align" : this.align
			}).startup();
		},
		
		getRender : function(code){
			return tmpWgtType[code];
		}
	});
	
	return claz;
});