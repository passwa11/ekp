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
	var validatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/validatorGenerator")

	var DetailQueryTarget = base.Container.extend({
		whereWgtCollection : [],
		initProps : function($super,cfg){
			$super(cfg);
			this.sourceData = cfg.sourceData;
			this.$table = cfg.container;
			this.xformId = cfg.xformId || "";
			this.backValueWgt = null;
			this.detailId = cfg.detailId || "";
			this.detailIds = cfg.detailIds || [];
			this.detailContainer = cfg.detailContainer || null;
		},

		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			formulaBuilder.initFieldList(this.xformId);

			//判断是否需要明细表
			var targetDetail = {};
			if (self.sourceData && self.sourceData.data) {
				targetDetail = self.filterDetailNameAndId(self.sourceData.data);
			}
			//一定要目标存在明细表 目标若不存在明细表则返回,本身可以没有明细表
			if (!targetDetail) {
				return;
			} else {
				this.targetDetail = targetDetail;
				this.$select = $("<select name='detailId'><option>请选择</option></select>");
				this.$select.appendTo(this.detailContainer);
				// $("<span class=\"txtstrong\">*</span>").appendTo(this.detailContainer)
				for (var key in targetDetail) {
					if(key === this.detailId || this.detailIds.indexOf(key) < 0){
						var $option = $("<option value='"+key+"'>" + targetDetail[key] + "</option>")
						$option.appendTo(this.$select);
						if(key === this.detailId){
							this.$select.val(key);
							this.$select.change();
						}
					}
				}
				this.$select.on("change", function (e) {
					var detailId = $(this).val();
					self.detailId = detailId;
					self.clearWgt();
				});
			}
			self.buildWhere();
		},

		// 画明细表查询字段
		buildWhere: function () {
			var self = this;
			var $whereDiv = this.$table.find(".view_field_detail_query_where_div");
			//查询条件切换事件
			this.$table.find(".detailQueryWhereTypediv input[type='radio']").on("change",function(){
				if(this.value === "3"){
					$whereDiv.hide();
				}else{
					$whereDiv.show();
				}
			});
			this.$table.find(".detailQueryWhereTypediv input[type='radio']").each(function (){
				if(this.value === "0"){
					$(this).trigger($.Event("click"));
				}
			})
			// 添加事件
			$whereDiv.find(".table_opera").on("click", function (e) {
				e.stopPropagation();
				if ($.isEmptyObject(self.getTargetData())) {
					dialog.alert("请选择明细表");
					return;
				}
				var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
				var $whereTable = self.$table.find(".view_field_detail_query_where_table");
				whereWgt.draw($whereTable,true);
			});
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
						this.detailId = storeTarget.value;
						this.$select.val(storeTarget.value);
						this.$select.change();
					}
				}
				if (storeData.hasOwnProperty("whereType")) {
					var whereType = storeData["whereType"];
					this.$table.find(".detailQueryWhereTypediv input[type='radio']").each(function (){
						if(this.value === whereType){
							$(this).prop("checked","checked");
							$(this).trigger($.Event("change"));
						}
					})
				}
				if (storeData.hasOwnProperty("detailQueryWhere")) {
					var storeWhere = storeData["detailQueryWhere"];
					if(storeWhere){
						for (var i = 0; i < storeWhere.length; i++) {
							var data = storeWhere[i];
							var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: this});
							whereWgt.draw(this.getWhereContainer(),true);
							whereWgt.initByStoreData(data);
						}
					}
				}
			}
		},

		getWhereContainer: function () {
			return this.$table.find(".view_field_detail_query_where_table");
		},

		filterDetailNameAndId: function (data) {
			var detail = undefined;
			for (var controlId in data) {
				if (controlId.indexOf(".") < 0) {
					continue;
				}
				if (!detail) {
					detail = {};
				}
				var item = data[controlId];
				var names = item.label.split(".");
				var ids = controlId.split(".");
				detail[ids[0]] = names[0];
			}
			return detail;
		},

		getTargetData : function(){
			var sourceData = this.sourceData;
			var targetData = {data: {}};
			var targetDetailId = this.detailId;
			if (!sourceData || !sourceData.data || !targetDetailId) {
				return null;
			}
			for (var controlId in sourceData.data) {
				if(typeof(String.prototype.endsWith) === "function" && controlId.endsWith("_config") || this.endsWith(controlId,"_config")){
					//#127245 过滤关联文档 ,关联文档控件有controlId，controlId_config,暂时屏蔽controlId_config
					continue;
				}
				var info = sourceData.data[controlId];
				if (info.name.indexOf(targetDetailId) >= 0)
					targetData.data[controlId] = info;
			}
			return targetData;
		},
		getKeyData : function(){
			var keyData = {};
			if(this.detailId){
				keyData.target={};
				keyData.target.text =  $("[name=detailId] option:selected").text();
				keyData.target.value =  $("[name=detailId] option:selected").val();
				keyData.detailQueryWhere = [];
				var whereTypeChecked = this.$table.find("[name=fdDetailQueryWhereType]:checked").val();
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
						keyData.detailQueryWhere.push(whereWgtKeyData);
					}
				}

				this.setEmptyStr4Undef(keyData);
			}
			return keyData;
		},

		validators:function() {
			var validator = true;
			// //目标表单为空校验
			// if(!this.targetWgt && !this.targetId){
			// 	this.targetWgt = new validatorGenerator.ValidatorGenerator({
			// 		title:"目标表单",
			// 		content:"不能为空",
			// 		container:this.$table.find("[name=modelTargetId]").closest("td")
			// 	})
			// }
			// this.buildValidator(!this.targetId,this.targetWgt);
			// if(this.targetWgt && this.targetWgt.isShow()){
			// 	validator = false;
			// }
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
		},
		endsWith : function(str,pattern){
			var reg = new RegExp(pattern + "$");
			return reg.test(str);
		},

	});

	exports.DetailQueryTarget = DetailQueryTarget;
});
