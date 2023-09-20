/**
 * 页面全局对象
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var topic = require("lui/topic");
	var modelingLang = require("lang!sys-modeling-base");
	var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
	var whereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/whereRecordGenerator");
	var backValueGenerator = require("sys/modeling/base/relation/trigger/behavior/js/backValueGenerator");
	var validatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/validatorGenerator")

	var PreQueryTarget = base.Container.extend({
		whereWgtCollection : [],
		targetData : {},	// 目标表单数据 {"modelId":"xxxx","data":{
		//	"fd_37d6a65f60d064" : {"label":"文本1","name":"fd_37d6a65f60d064","type":"String"}
		//	},"flowInfo":{}}

		initProps : function($super,cfg){
			$super(cfg);
			this.sourceData = cfg.sourceData;
			this.$table = cfg.container;
			this.xformId = cfg.xformId || "";
			this.backValueWgt = null;
			this.targetId = null;
		},

		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			formulaBuilder.initFieldList(this.xformId);
			self.buildWhere();
			//self.buildBackValue();
			topic.channel("modelingBehavior").subscribe("preQuerySoureData.load",function(rtn){
				if (rtn.modelId && rtn.modelId !== self.targetId) {
					self.clearWgt();
				}
				self.formatTargetData(rtn);
				self.buildBackValue(rtn.modelId);
				self.validators();
			});
		},

		// 画目标表单查询字段
		buildWhere: function () {
			var self = this;
			var $whereDiv = this.$table.find(".view_field_pre_query_where_div");
			//查询条件切换事件
			this.$table.find(".preQueryWhereTypediv input[type='radio']").on("change",function(){
				if(this.value === "3"){
					$whereDiv.hide();
				}else{
					$whereDiv.show();
				}
			});
			this.$table.find(".preQueryWhereTypediv input[type='radio']").each(function (){
				if(this.value === "0"){
					$(this).trigger($.Event("click"));
				}
			})
			// 添加事件
			$whereDiv.find(".table_opera").on("click", function (e) {
				e.stopPropagation();
				if ($.isEmptyObject(self.getTargetData())) {
					dialog.alert(modelingLang['behavior.select.target.form.first']);
					return;
				}
				var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
				var $whereTable = self.$table.find(".view_field_pre_query_where_table");
				whereWgt.draw($whereTable);
			});
		},

		buildBackValue:function (modelId){
			if ((!this.backValueWgt && modelId && modelId == this.targetId)) {
			    return;
			}
			if(this.backValueWgt){
				this.backValueWgt.destroy();
			}
			var container = this.$table.find(".view_pre_query_back_value");
			container.empty();
			this.backValueWgt= new backValueGenerator.BackValueGenerator({parent : this,$table:container,sourceData:this.targetData});
			this.backValueWgt.draw();
			this.targetId = modelId;
			this.$table.find(".backValueTr").show();
		},

		// type : where
		addWgt: function (wgt, type) {
			if (type === "where") {
				this.whereWgtCollection.push(wgt);
			}
		},

		// type : where
		deleteWgt: function (wgt, type) {
			var collect = [];
			if (type === "where") {
				collect = this.whereWgtCollection;
			}
			for (var i = 0; i < collect.length; i++) {
				if (collect[i] === wgt) {
					collect.splice(i, 1);
					break;
				}
			}
		},

		clearWgt: function () {
			for (var i = 0; i < this.whereWgtCollection.length; i++) {
				this.whereWgtCollection[i].destroy();
				i--;
			}
		},

		clear: function () {
			this.clearWgt();
			this.$table.remove();
		},

		initByStoreData : function(storeData){
			if(storeData){
				if (storeData.hasOwnProperty("target")) {
					var storeTarget = storeData["target"];
					if (storeTarget){
						this.targetId = storeTarget.value;
						$(".modelTargetNameBox").html(storeTarget.text);
						$("[name='modelTargetId']").val(storeTarget.value);
					}
				}
				if (storeData.hasOwnProperty("whereType")) {
					var whereType = storeData["whereType"];
					this.$table.find(".preQueryWhereTypediv input[type='radio']").each(function (){
						if(this.value === whereType){
							$(this).prop("checked","checked");
							$(this).trigger($.Event("change"));
						}
					})
				}
				if (storeData.hasOwnProperty("preQueryWhere")) {
					var storeWhere = storeData["preQueryWhere"];
					for (var i = 0; i < storeWhere.length; i++) {
						var data = storeWhere[i];
						var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: this});
						whereWgt.draw(this.getWhereContainer());
						whereWgt.initByStoreData(data);
					}
				}
				if (storeData.hasOwnProperty("backValue")) {
					var backValue = storeData["backValue"];
					this.buildBackValue(this.targetId);
					if(this.backValueWgt){
						this.backValueWgt.initByStoreData(backValue);
					}
				}
			}
		},

		getWhereContainer: function () {
			return this.$table.find(".view_field_pre_query_where_table");
		},

		formatTargetData : function(rtn){
			this.targetData = rtn.data;
			this.targetData.modelId = rtn.modelId;
		},

		getTargetData : function(){
			return this.targetData;
		},
		getKeyData : function(){
			var keyData = {};
			keyData.target={};
			keyData.target.text =  this.$table.find(".modelTargetNameBox").text();
			keyData.target.value =  this.$table.find("[name=modelTargetId]").val();
			keyData.preQueryWhere = [];
			var whereTypeChecked = this.$table.find("[name=fdPreQueryWhereType]:checked").val();
			keyData.whereType = whereTypeChecked;
			if (whereTypeChecked != "2" && whereTypeChecked != "3") {
				for (var i = 0; i < this.whereWgtCollection.length; i++) {
					var whereWgt = this.whereWgtCollection[i];
					var whereWgtKeyData = whereWgt.getKeyData();
					if (!whereWgtKeyData) {
						continue;
					}
					// 索引，用来进来记录排序，暂无用
					whereWgtKeyData.idx = i;
					keyData.preQueryWhere.push(whereWgtKeyData);
				}
			}
			keyData.backValue = {};
			if(this.backValueWgt){
				keyData.backValue = this.backValueWgt.getKeyData();
			}

			keyData.targetData = this.targetData;

			this.setEmptyStr4Undef(keyData);
			return keyData;
		},

		validators:function() {
			var validator = true;
			//目标表单为空校验
			if(!this.targetWgt && !this.targetId){
				this.targetWgt = new validatorGenerator.ValidatorGenerator({
					title:"目标表单",
					content:"不能为空",
					container:this.$table.find("[name=modelTargetId]").closest("td")
				})
			}
			this.buildValidator(!this.targetId,this.targetWgt);
			if(this.targetWgt && this.targetWgt.isShow()){
				validator = false;
			}
			return validator;
		},

		buildValidator: function (expr,weiget) {
			if (expr) {
				if(weiget && !weiget.isShow()){
					weiget.show();
				}
			} else {
				if (weiget) {
					weiget.hide();
				}
			}
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
		}

	});

	exports.PreQueryTarget = PreQueryTarget;
});
