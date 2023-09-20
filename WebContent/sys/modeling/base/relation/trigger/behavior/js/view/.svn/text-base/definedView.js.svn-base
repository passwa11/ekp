/**
 * 自定义视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var baseView = require("sys/modeling/base/relation/trigger/behavior/js/view/baseView");
	var crudBaseView = require("sys/modeling/base/relation/trigger/behavior/js/view/crudBaseView");
	var msgSendGenerator = require("sys/modeling/base/relation/trigger/behavior/js/msgSendGenerator");
	var beanTypeCustomizeView = require("sys/modeling/base/relation/trigger/behavior/js/view/beanTypeCustomizeView");
	var beanTypeSystemView = require("sys/modeling/base/relation/trigger/behavior/js/view/beanTypeSystemView");
	var modelingLang = require("lang!sys-modeling-base");

	var DefinedView =  crudBaseView.CrudBaseView.extend({

		beanTypeViews : {
			"0":null,	// 自定义
			"1":null	// 已经封装好的接口（system）
		},

		currentBeanTypeView : null,	// 当前视图组件


		build : function(){
			this.prefix = "defined";
			if($("[mdlng-bhvr-data='precfg']").find(".behavior_update_preview").length != 1){
        		this.buildPreWhereView();
        	}
			var self = this;
			var $element = $("<div class='behavior_defined_view'/>");

			var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");

			var $trBeanType = $("<tr />");
			$trBeanType.append("<td class='td_normal_title' width='15%'><span>"+modelingLang['behavior.custom.Bean.interface']+"</span></td>");
			var $beanTypeDiv = $(" <div id=\"_xform_beanType\" _xform_type=\"checkbox\"> \n" +
				"   <label class=\"lui-lbpm-radio\"><input type=\"radio\" name=\"beanType\" checked=\"\" value=\"0\" subject=\""+modelingLang['behavior.custom.Bean.interface']+"\" /><span class=\"radio-label\">"+modelingLang['modeling.customize']+"</span></label>\n" +
				"   <label class=\"lui-lbpm-radio\"><input type=\"radio\" name=\"beanType\" value=\"1\" subject=\""+modelingLang['behavior.custom.Bean.interface']+"\" /><span class=\"radio-label\">"+modelingLang['behavior.select.encapsulated.interface']+"</span></label>\n" +
				"  </div>");
			$trBeanType.append($("<td>").append($beanTypeDiv)).append($("</td>"));
			$table.append($trBeanType);
			//自定义部分
			var $tr = $("<tr />");
			var $td = $("<td colspan='2' />");
			$tr.append($td);
			this.beanTypeViews["0"] = new beanTypeCustomizeView.BeanTypeCustomizeView({viewContainer:$td});
			this.beanTypeViews["1"] = new beanTypeSystemView.BeanTypeSystemView({viewContainer:$td,target:this.getTargetData()});
			this.beanTypeViews["0"].show();
			this.currentBeanTypeView = this.beanTypeViews["0"];
			$table.append($tr);
			$element.append($table);
			//消息
			this.buildMsg($table);
			//查询
			this.buildWhere($table, this.parent.whereTmpStr);
			 //查询条件切换事件
            $table.find(".WhereTypediv input[type='radio']").on("change",function(){
            	 var $whereBlockDom = $table.find(".view_field_where_div");
            	 if(this.value === "2" || this.value === "3"){
            		 $whereBlockDom.hide();
            	 }else{
            		 $whereBlockDom.show();
            	 }
            });
            $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
			$table.find(".view_fdId_where_table").remove();
			//目标
			var authProperty = this.parent.targetData?this.parent.targetData.authProperty:null;
			this.buildTarget($table, this.parent.targetTmpStr,authProperty);
			this.config.viewContainer.append($element);

			self.bindEvent($element);
			return $element;
		},

		bindEvent: function ($element) {
			var self = this;
			$($element).find("input[name='beanType']").on('change',function () {
				self.currentBeanTypeView.hide();
				self.currentBeanTypeView = self.beanTypeViews[this.value];
				self.currentBeanTypeView.show();
			});
		},

		buildMsg: function ($table) {
			var self = this;
			var targetData = this.getTargetData();

			var msgData = {
				"modelId":targetData.modelId,
				"modelMainId":this.parent.config.modeMainId,
				"sendOrRemove":"remove",
				"dataList":[],
			};
			//发送消息
			this.sendMsg = new msgSendGenerator.MsgSendGenerator(msgData);
			var $sendMsgTr = $("<tr/>")
			$sendMsgTr.append("<td class='td_normal_title'>"+modelingLang['behavior.message.processing']+"</td>");
			var $msgDiv = $("<div class=\"model-mask-panel-table-base\" />")
			$msgDiv.append(this.sendMsg.element)
			var $sendMsgTd = $("<td/>");
			$sendMsgTd.append( $msgDiv);
			$sendMsgTr.append($sendMsgTd);
			$table.append($sendMsgTr);

		},
		getKeyData : function($super,cfg){
			var keyData = $super(cfg);
			keyData.beanType = this.element.find('input[type="radio"][name="beanType"]:checked').val();

			var beanTypeViewKeyData = this.currentBeanTypeView.getKeyData();
			keyData.beanSign = beanTypeViewKeyData.beanSign;
			keyData.beanIdName = beanTypeViewKeyData.beanIdName;
			keyData.beanId = beanTypeViewKeyData.beanId;
			keyData.beanCfg = beanTypeViewKeyData.beanCfg;
			//设置消息
			var msgs  = this.sendMsg.getKeyData();
			keyData.msgArray = msgs;
			return keyData;
		},
	
		initByStoreData : function($super,storeData){
			$super(storeData);
			this.currentBeanTypeView.hide();
			if(storeData["beanType"]){
				$("input[type='radio'][name='beanType'][value='"+storeData["beanType"]+"']").attr("checked", "checked");
				this.beanTypeViews[storeData["beanType"]].show();
				this.currentBeanTypeView = this.beanTypeViews[storeData["beanType"]];
			}else{
				$("input[type='radio'][name='beanType'][value='0']").attr("checked", "checked");
				this.beanTypeViews["0"].show();
				this.currentBeanTypeView = this.beanTypeViews["0"];
			}
			this.currentBeanTypeView.initByStoreData(storeData);

			//设置消息
			var msg = storeData.msgArray;
			this.sendMsg.initByStoreData(msg);
		}
	});
	
	exports.DefinedView = DefinedView;
});