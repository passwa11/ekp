/**
 * 自定义视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var baseView = require("sys/modeling/base/relation/trigger/behavior/js/view/baseView");
	var modelingLang = require("lang!sys-modeling-base");

	var BeanTypeSystemView =  baseView.BaseView.extend({

		/**
		 * 初始化
		 * cfg应包含：
		 */
		initProps: function ($super, cfg) {
			$super(cfg);
			this.viewContainer = cfg.viewContainer;
			this.targetData = cfg.target.data;
		},

		build : function(){
			var self = this;
			var $element = $("<div width='100%'/>");
			var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
			//beanId
			var $tr = $("<tr />");
			$tr.append("<td class='td_normal_title' width='15%'><span>"+modelingLang['behavior.custom.Bean.ID']+"</span></td>");
			var $inputDiv = $('<div class="beanInputDiv" />');
			$inputDiv.on("click",function(){
				self.onclickBeanInput()
			});
			var $input = $('<input type="hidden" name="beanSign" /><input type="hidden" name="beanId" /><input class="inputsgl"  readonly type="text" name="beanIdName"  style="width:95%;" />');
			var $inputSelectItem = $('<div class="inputSelectItem" />');

			$inputDiv.append($input);
			$inputDiv.append($inputSelectItem);
			$tr.append($("<td/>").append($inputDiv));
			$table.append($tr);

			//beancfg
			var $tr_config = $("<tr />");
			$tr_config.append("<td class='td_normal_title' width='15%'><span>"+modelingLang['behavior.bean.parameter.configuration']+"</span></td>");
			var $cfgInput = $('<textarea name="beanCfg" validate="maxLength(2000)" readonly="true" style="width:95%;white-space: nowrap;overflow-y: hidden;"></textarea>');
			$cfgInput.on("click",function(){
				self.onclickBeanParamCfgTextarea()
			});
			$tr_config.append($("<td/>").append($cfgInput));
			$table.append($tr_config);

			$element.append($table);
			this.viewContainer.append($element);
			return $element;
		},


		onclickBeanInput: function(){
			var self = this;
			var url='/sys/modeling/base/relation/trigger/behavior/beanSelectDialog.jsp';
			dialog.iframe(url,modelingLang['behavior.custom.bean.selection'],function(data){
				if(!data){
					return;
				}
				//如果改变了值，重置配置
				if(self.element.find('[name="beanSign"]').val() != data.sign){
					self.selfData = undefined;
					self.element.find('[name="beanCfg"]').val('')
				}
				//将值写入输入框
				self.element.find('[name="beanSign"]').val(data.sign);
				self.element.find('[name="beanId"]').val(data.value);
				self.element.find('[name="beanIdName"]').val(data.name);
			},{
				width : 900,
				height : 500,
				params : {}
			});
		},

		onclickBeanParamCfgTextarea: function(){
			var self = this;
			if(!self.targetData){
				dialog.alert(modelingLang['behavior.configure.target.form.first']);
				return;
			}
			var sign =$('[name="beanSign"]').val();
			if(!sign){
				dialog.alert(modelingLang['behavior.configure.custom.beanId.first']);
				return;
			}
			var editContentText = self.element.find('[name="beanCfg"]').val();
			var url='/sys/modeling/base/relation/trigger/behavior/beanParamDialog.jsp';
			dialog.iframe(url,modelingLang['behavior.parameter.configuration'],function(data){
				if(!data&&''!=data){
					return;
				}
				//将值写入输入框
				self.element.find('[name="beanCfg"]').val(data);
			},{
				width : 900,
				height : 500,
				params : {
					targetData:self.targetData,
					sign:sign,
					editContentText:editContentText,
					selfData : self.selfData
				}
			});
		},

		getKeyData : function($super,cfg){
			var keyData = $super(cfg);
			var $beanSign = this.element.find("[name='beanSign']");
			var $beanIdName = this.element.find("[name='beanIdName']");
			var $beanId = this.element.find("[name='beanId']");
			var $beanCfg = this.element.find("[name='beanCfg']");
			keyData.beanSign = $beanSign.val();
			keyData.beanIdName = $beanIdName.val();
			keyData.beanId = $beanId.val();
			//将label转换为name
			var beanCfgVal = $beanCfg.val();
			for (var controlId in this.targetData) {
				//跳过明细表
				if (controlId.indexOf(".") > 0) {
					continue;
				}
				var option = this.targetData[controlId];
				var label = '\\$' + option.label +'\\$';
				var name = '$' + option.name +'$';
				var reg = new RegExp(label,"g");//g,表示全部替换。
				beanCfgVal = beanCfgVal.replace(reg,name);
			}
			keyData.beanCfg = beanCfgVal;
			return keyData;
		},

		initByStoreData : function($super,storeData){
			$super(storeData);
			var $beanSign = this.element.find("[name='beanSign']");
			$beanSign.val(storeData["beanSign"]);
			var $beanIdName = this.element.find("[name='beanIdName']");
			$beanIdName.val(storeData["beanIdName"]);
			var $beanId = this.element.find("[name='beanId']");
			$beanId.val(storeData["beanId"]);
			var $beanCfg = this.element.find("[name='beanCfg']");
			//将name转换为label
			var beanCfgVal = storeData["beanCfg"];
			for (var controlId in this.targetData) {
				//跳过明细表
				if (controlId.indexOf(".") > 0) {
					continue;
				}
				var option = this.targetData[controlId];
				var realLabel = option.fullLabel?option.fullLabel:option.label;
				var label = '$' + realLabel +'$';
				var name = '\\$' + option.name +'\\$';
				var reg = new RegExp(name,"g");//g,表示全部替换。
				beanCfgVal = beanCfgVal.replace(reg,label);
			}
			$beanCfg.val(beanCfgVal);
			this.selfData = storeData;
		}
	});


	exports.BeanTypeSystemView = BeanTypeSystemView;
});