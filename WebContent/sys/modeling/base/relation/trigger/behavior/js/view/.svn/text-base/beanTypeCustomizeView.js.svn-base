/**
 * 自定义视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var baseView = require("sys/modeling/base/relation/trigger/behavior/js/view/baseView");
	var modelingLang = require("lang!sys-modeling-base");

	var BeanTypeCustomizeView = baseView.BaseView.extend({


		/**
		 * 初始化
		 * cfg应包含：
		 */
		initProps: function ($super, cfg) {
			$super(cfg);
			this.bindEvent();
			this.viewContainer = cfg.viewContainer;
		},

		build : function(){
			var self = this;
			var $element = $("<div width='100%'/>");
			var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
			//beanId
			var $tr = $("<tr />");
			$tr.append("<td class='td_normal_title' width='15%'><span>"+modelingLang['behavior.custom.Bean.ID']+"</span></td>");
			var $input = $('<input class="inputsgl" name="beanId" type="text" style="width:95%;" />');
			$tr.append($("<td/>").append($input));

			$table.append($tr);

			//bean简介
			var $tr_tips = $("<tr  >" +
				"<td class='td_normal_title'  width='15%'><span style='color: #6a6a6a'>"+modelingLang['behavior.bean.interface.description']+"</span></td>" +
				"<td ><div style='margin-left: 16px'>" +
				"	<p style='margin-bottom: 10px;color: #6a6a6a'>"+modelingLang['behavior.custom.Bean.implement.interface'] +
				"<br>"+modelingLang['behavior.execute.method']+"</p></div>" +
				"</td>" +
				"</tr>");
			$table.append($tr_tips);

			//beancfg
			var $tr_config = $("<tr />");
			$tr_config.append("<td class='td_normal_title' width='15%'><span>"+modelingLang['behavior.bean.parameter.configuration']+"</span></td>");
			var $cfgInput = $('<textarea name="beanCfg" validate="maxLength(2000)" style="width:95%;"></textarea>');
			$tr_config.append($("<td/>").append($cfgInput));
			$table.append($tr_config);

			$element.append($table);
			this.viewContainer.append($element);
			return $element;
		},

		bindEvent : function($super,cfg){

		},

		getKeyData : function($super,cfg){
			var keyData = $super(cfg);
			var $beanId = this.element.find("[name='beanId']");
			var $beanCfg = this.element.find("[name='beanCfg']");
			keyData.beanId = $beanId.val();
			keyData.beanCfg = $beanCfg.val();
			return keyData;
		},
	
		initByStoreData : function($super,storeData){
			$super(storeData);
			var $beanId = this.element.find("[name='beanId']");
			$beanId.val(storeData["beanId"]);
			var $beanCfg = this.element.find("[name='beanCfg']");
			$beanCfg.val(storeData["beanCfg"]);
		}
	});
	
	exports.BeanTypeCustomizeView = BeanTypeCustomizeView;
});