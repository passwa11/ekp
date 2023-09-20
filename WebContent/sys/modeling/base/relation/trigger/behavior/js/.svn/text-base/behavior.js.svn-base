/**
 * 页面全局对象
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
	var updateView = require("sys/modeling/base/relation/trigger/behavior/js/view/updateView");
	var notifyView = require("sys/modeling/base/relation/trigger/behavior/js/view/notifyView");
	var createView = require("sys/modeling/base/relation/trigger/behavior/js/view/createView");
	var deleteView = require("sys/modeling/base/relation/trigger/behavior/js/view/deleteView");
	var definedView = require("sys/modeling/base/relation/trigger/behavior/js/view/definedView");
	var modelingLang = require("lang!sys-modeling-base");

	var Behavior = base.Container.extend({

		views : {
			"0":null,	// 消息
			"1":null,	// 新建
			"2":null,	// 删除
			"3":null,	// 更新
			"5":null	// 自定义
		},

		currentView : null,	// 当前视图组件
		
		currentData : {},	//本表单数据
		preWhereWgtCollection : [],
		detailQueryCollection : [],
		sqlWhereWgtCollection : [],

		targetData : {},	// 目标表单数据 {"modelId":"xxxx","data":{
		//	"fd_37d6a65f60d064" : {"label":"文本1","name":"fd_37d6a65f60d064","type":"String"}
		//	},"flowInfo":{}}
		preModelData : {}, //前置表单

		// cfg:{$behaviorTypeEle:"动作类型","viewContainer":"视图容器","$tmpEle":"模板"}
		initProps : function($super,cfg){
			$super(cfg);
			this.preWhereTmpStr = cfg.$tmpEle.find(".pre_where_tmp_html tbody").html();
			this.whereTmpStr = cfg.$tmpEle.find(".where_tmp_html tbody").html();
			this.whereMainTmpStr = cfg.$tmpEle.find(".where_main_tmp_html tbody").html();
			this.sqlWhereTmpStr = cfg.$tmpEle.find(".sql_where_tmp_html tbody").html();
			this.targetTmpStr = cfg.$tmpEle.find(".target_tmp_html tbody").html();
			this.flowTypeTmpStr = cfg.$tmpEle.find(".flow_radio_tmp_html").html();
			this.noflowTypeTmpStr = cfg.$tmpEle.find(".no_flow_radio_tmp_html").html();
			this.notifyTmpStr = cfg.$tmpEle.find(".notify_tmp_html tbody").html();
			this.detailRuleTmpStr = cfg.$tmpEle.find(".detail_rul_tmp_html tbody").html();
			this.detailTmpStr = cfg.$tmpEle.find(".detail_tmp_html tbody").html();
			this.preModelTmpStr = cfg.$tmpEle.find(".pre_model_tmp_html tbody").html();
			this.preModelWhereTmpStr = cfg.$tmpEle.find(".pre_model_where_tmp_html tbody").html();
			this.preQueryTmpStr = cfg.$tmpEle.find(".pre_query_tmp_html tbody").html();
			this.detailQueryTmpStr = cfg.$tmpEle.find(".detail_query_tmp_html tbody").html();
			formulaBuilder.initFieldList(cfg.xformId);
			this.sourceData = cfg.sourceData;
			this.currentData = cfg.currentData;
			this.appId = cfg.appId || "";
			if(cfg.currentData && cfg.currentData.flowInfo){
				this.modelMainName = cfg.currentData.flowInfo.modelName || "";
			}
			this.xformId = cfg.xformId || "";
			this.views["0"] = new notifyView.NotifyView({viewContainer:cfg.viewContainer,preViewContainer:cfg.preViewContainer,parent:this});
			this.views["1"] = new createView.CreateView({viewContainer:cfg.viewContainer,preViewContainer:cfg.preViewContainer,parent:this});
			this.views["2"] = new deleteView.DeleteView({viewContainer:cfg.viewContainer,preViewContainer:cfg.preViewContainer,parent:this});
			this.views["3"] = new updateView.UpdateView({viewContainer:cfg.viewContainer,preViewContainer:cfg.preViewContainer,parent:this});
			this.views["5"] = new definedView.DefinedView({viewContainer:cfg.viewContainer,preViewContainer:cfg.preViewContainer,parent:this});

			cfg.$tmpEle.remove();
		},

		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			this.config.$behaviorTypeEle.on("change",function(){
				// 变更视图
				if(self.currentView){
					self.currentView.hide();
				}
				self.currentView = self.views[this.value];
				self.currentView.show();
				if(this.value=="5"){
					$(".behavior_detail_query_preview").hide();
				}else{
					$(".behavior_detail_query_preview").show();
				}
				if(this.value=="3"){
					$("#fdUpdateType_tr").show();
				}else{
					$("#fdUpdateType_tr").hide();
				}
				if(this.value=="0"){
					$(".detail_target").hide();
				}
			});
			this.config.$behaviorTypeEle.each(function (idx,dom) {
				var checked  =$(dom).attr("checked");
				if(checked){
					var thisValue = $(dom).val();
					// 变更视图
					if(self.currentView){
						self.currentView.hide();
					}
					self.currentView = self.views[this.value];
					self.currentView.show();
					if(this.value=="5"){
						$(".behavior_detail_query_preview").hide();
					}else{
						$(".behavior_detail_query_preview").show();
					}
					if(this.value=="3"){
						$("#fdUpdateType_tr").show();
					}else{
						$("#fdUpdateType_tr").hide();
					}
					if(this.value=="0"){
						$(".detail_target").hide();
					}
				}
			})
			//this.config.$behaviorTypeEle.trigger($.Event("change"));

			topic.channel("modelingBehavior").subscribe("soureData.load",function(rtn){
				self.formatTargetData(rtn);
				if(self.currentView["doRenderWhenDataLoad"]){
					self.currentView["doRenderWhenDataLoad"](self.targetData);
				}
			});
			topic.channel("modelingBehavior").subscribe("preModelData.load",function(rtn){
				self.formatPreModelData(rtn);
			});
			topic.channel("modelingBehavior").subscribe("preModelData.remove",function(rtn){
				self.removePreModelData(rtn);
			});
			
		},

		initByStoreData : function(storeData){
			this.currentView.initByStoreData(storeData);
		},

		formatTargetData : function(rtn){
			this.targetData = rtn.data;
			this.targetData.modelId = rtn.modelId;
		},

		formatPreModelData : function(rtn){
			this.preModelData.data = rtn.data;
			this.preModelData.modelId = rtn.modelId;
			this.preModelData.modelName = rtn.modelName;
			var varName = rtn.modelName + "("+modelingLang["sysModelingBehavior.modelPre"]+")";
			formulaBuilder.initOtherFieldList(rtn.data,varName,"preModel",this.currentView.prefix);
		},

		removePreModelData : function(rtn){
			var varName = rtn.modelName + "("+modelingLang["sysModelingBehavior.modelPre"]+")";
			formulaBuilder.removeOtherFieldList(varName,"preModel",this.currentView.prefix);
		},

		getKeyData : function(){
			var keyData = this.currentView.getKeyData();
			this.setEmptyStr4Undef(keyData);
			return keyData;
		},

		// 把对象里面的null转换为空字符串
		setEmptyStr4Undef : function(data){
			for(var key in data){
				var item = data[key];
				if(typeof item === "object"){
					this.setEmptyStr4Undef(item);
					continue;
				}
				if(typeof(item) == "undefined"){
					data[key] = "";
				}
			}
		},
		validators : function() {
			var validator = true;
			var result = this.currentView.validators();
			if (!result) {
				validator = false;
			}
			return validator;
		}

	});

	exports.Behavior = Behavior;
});
