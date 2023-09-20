/**
 * 
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "dijit/_WidgetBase","dojo/request", "mui/util", "dojo/topic",
		"dojo/dom-construct", "sys/modeling/main/xform/controls/filling/mobile/dialog/DialogMixin","mui/i18n/i18n!:error",
		"dojox/mobile/viewRegistry","sys/xform/mobile/controls/xformUtil","sys/modeling/main/xform/controls/filling/mobile/FillingEvent","dojo/dom-class"],
		function(declare, lang, WidgetBase, request, util, topic, domConstruct, DialogMixin, Msg,viewRegistry,xUtil,FillingEvent,domClass) {
	
	// 后台配置信息缓存
	var __cfgFillingData = null;
	var __isFillinfSend = false;
	
	// 主要是为了预防每个控件都发送请求
	function loadFillingCfg(fdAppModelId){
		if(!__isFillinfSend){
			var url = "/sys/modeling/main/modelingAppXFormMain.do?method=getRelationFillingDynamic&modelName="
						+ _xformMainModelClass + "&fdAppModelId=" + fdAppModelId;
			__isFillinfSend = true;
			request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(data){
				__cfgFillingData = formatFillingData(data);
				topic.publish("/modeling/filling/dataOnload",__cfgFillingData);
			});		
		}else{
			if(__cfgFillingData){
				topic.publish("/modeling/filling/dataOnload", __cfgFillingData);
			}
		}
	}

	// 合并输出参数的明细表key
	function formatFillingData(datas){
		for (var relationId in datas) {
			var data = datas[relationId];
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
		}

		return datas;
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
	
	var claz = declare("sys.modeling.main.xform.controls.filling.mobile.FillingDispatcher", [ WidgetBase,FillingEvent ], {

		//绑定事件对象
		bindDom:null,

		fillingCfg:[],
		//绑定事件
		bindEvent:null,
		buildRendering : function(){
			this.inherited(arguments);
			this.dataOnLoadhandler = topic.subscribe("/modeling/filling/dataOnload",lang.hitch(this,this.onDataLoad));
			if(this.bindDom && this.bindDom.indexOf(".")>-1){
				// this._bindDom = this.bindDom;
				// this.bindDom = this._bindDom.substr(this._bindDom.lastIndexOf(".")+1);
				this._inDetailTable = true;
			}
		},
		
		startup : function() {
			this.inherited(arguments);
			domClass.add(this.domNode, "muiFillingWrap");
			this.fdAppModelId = document.getElementsByName("fdModelId")[0].value;
			this.valueName = "_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
			loadFillingCfg(this.fdAppModelId);
		},
		
		onDataLoad : function(datas){
			this.fdAppModelId = document.getElementsByName("fdModelId")[0].value;
			if(datas){
				for(var relationId in datas){
					var data = datas[relationId];
					for(var key in data){
						if(key.indexOf(this.controlId) > -1){
							var relationCfg = data[key];
							if(relationCfg.status === '00'){
								if(this.fillingCfg.indexOf(this.name)<0){
									if(this.status === "edit"){
										this.buildEdit(datas, this.name);
									}else{
										this.buildView(relationCfg, this.name);
									}
									// 销毁当前组件
									this.dataOnLoadhandler.remove();
									this.fillingCfg.push(this.name);
								}
							}else{
								console.info("业务关联控件("+ this.controlId +")没有在后台配置业务关联！");
							}
						}
					}
				}
				this.envInfo = datas;
			}
		},
		
		buildEdit : function(relationCfg, key){
			var itemContainer = domConstruct.create("span",{},this.domNode);
			var fillingItem = domConstruct.create("span",{"innerHTML":"填充","name":key,"className":""},itemContainer);
			var self = this;
			if("change" == this.bindEvent){//监听表单事件变更
				this.defer(function() {
					self.subscribe("/mui/form/valueChanged","_execChange");
				}, 500);
			}
			this.connect(fillingItem, "click", function (evt) {
				self._execClick(this,key, evt);
			});
			this.key = key + this.valueName;
		},
		
		buildView : function(relationCfg, key){
		},
		_execChange:function(srcObj, arguContext){
			if(srcObj){
				var evtObjName =  xUtil.parseXformName(srcObj);
				if(evtObjName!=null && evtObjName!=''){
					var bindDom = this.bindDom;
					if(bindDom){
						var domArray = bindDom.split(";");
						for(var i = 0;i < domArray.length;i++){
							var id = domArray[i];
							if(id != null && id != ""){
								// 如果srcObj是地址本，evtObjName为不带id和name的控件id
								// 处理地址本带id和name的情况
								if(/-fd(\w+)/g.test(id)){
									id = id.match(/(\S+)-/g)[0].replace("-","");
								}
								id = id.replace(/_text$/g,"");
								if(evtObjName.indexOf(id) > -1){
									this.execEvent("",srcObj.value);
									break;
								}
							}
						}
					}
				}
			}
		},
	});
	
	return claz;
});