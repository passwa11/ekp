/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require("lui/topic");
	var stepView = require("sys/modeling/base/resources/js/dialog/step/view");
	var modelingLang = require("lang!sys-modeling-base");
	var StepDialog = base.Component.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.views = {};	// {索引:视图组件}
			this.curView = null;
			this.lastIndex = 1;
	    },
		
		/*
		 * viewInfos : [{
		 * "type" : "app",
			"text" : "应用",
			"sourceUrl" : "",
			"render" : "",
			"search" : "self"
		 * }]
		 * 
		 * */
		initViewInfos : function(viewInfos, values){
			viewInfos = viewInfos || [];
			values = values || {};
			// 构建视图组件
			var preWgt = null;
			for(var i = 0;i < viewInfos.length;i++){
				var info = viewInfos[i];
				preWgt = this.createView(preWgt, info, values[info.type] || "");
			}
			
			// 生成元素结构
			this.buildFrame();
		},
		
		createView : function(preWgt, info, value){
			var viewWgtStr = info.viewWgt || "PluginBaseView";
			var viewWgt = new stepView[viewWgtStr]({
				info : info,
				value : value,
				parent : this
			});
			viewWgt.startup();
			viewWgt.preWgt = preWgt;
			this.views[info.index] = viewWgt;
			this.addChild(viewWgt);
			return viewWgt;
		},
		
		// 生成主体结构
		buildFrame : function(){
			var self = this;
			
			// 页签信息
			this.tabDom = $("<div class='content-tab-wrap'/>").appendTo(this.element);
			for(var index in this.views){
				var wgt = this.views[index];
				var tabHtml = "<div class='content-tab' data-tab-index='"+ index +"'>";
				tabHtml += "<div class='content-tab-icon'><i></i>"+ index +"</div>";
				tabHtml += "<div class='content-tab-txt'>";
				tabHtml += modelingLang['modeling.form.Choice'] + (wgt.config.info.text || modelingLang['modeling.form.Content']);
				tabHtml += "</div>";
				tabHtml += "</div>";
				var $tab = $(tabHtml).appendTo(this.tabDom);
				$tab.on("click", function(){
					self._clickTab(this);
				})
			}
			
			// 主体内容
			this.contentDom = $("<div class='content-main-wrap'/>").appendTo(this.element);
		},
		
		_clickTab : function(dom){
			var targetIndex = $(dom).attr("data-tab-index");
			var targetViewWgt = this.views[targetIndex];
			var targetKeyData = targetViewWgt.getKeyData();
			// 目标视图已选了值的，支持切换
			if(targetKeyData && !$.isEmptyObject(targetKeyData)){
				this.switchByIndex(targetIndex);
			}else{
				// 如果目标视图的上一个视图还未选择，则无法切换到下一个视图
				if(this.views[targetIndex-1]){
					var preViewKeyData = this.views[targetIndex-1].getKeyData();
					if(preViewKeyData && !$.isEmptyObject(preViewKeyData)){
						this.switchByIndex(targetIndex);
					}else{
						// 需选择值
					}
				}
			}
		},
		
		// 切换到对应视图
		switchByIndex : function(index, isClearTargetView){
			var wgt = this.views[index];
			if(this.curView){
				if(wgt === this.curView){
					return;	
				}else{
					this.curView.hide();					
				}
			}

			// 添加激活状态
			this.tabDom.find("[data-tab-index]").removeClass("active");
			this.tabDom.find("[data-tab-index='"+ index +"']").addClass("active");
			
			wgt.show();
			this.curView = wgt;
			if(isClearTargetView){
				var i = index
				while(this.views[i]){
					this.views[i].clearVal();
					i++;
				}
			}
		},
		
		// 切换到下一个视图 isClear:是否清空目标视图的值
		switchNext : function(isClearTargetView){
			var $tab = this.getNextTab();
			if($tab && $tab.length > 0){
				this.switchByIndex($tab.attr("data-tab-index"), isClearTargetView);
			}
		},
		
		// 获取下一个tab
		getNextTab : function(){
			var curIndex = this.curView.config.info.index;
			var nextIndex = parseInt(curIndex) + 1;
			return this.tabDom.find("[data-tab-index='"+ nextIndex +"']");
		},
		
		// 处理完成之后的状态
		doFinish : function(isFinish, tabIndex){
			// 添加或移除页签的状态
			if(isFinish){
				this.tabDom.find("[data-tab-index='"+ tabIndex +"']").addClass("finished");
			}else{
				this.tabDom.find("[data-tab-index='"+ tabIndex +"']").removeClass("finished");
			}
		},
		
		validateData : function(){
			var rs = true;
			for(var key in this.views){
				var view = this.views[key];
				if(!view.validateData()){
					rs = false;
				}
				if(!rs){
					break;
				}
			}
			return rs;
		},
		
		getKeyData : function(){
			var rs = {};
			for(var key in this.views){
				var view = this.views[key];
				rs[key] = view.getKeyData();
			}
			return rs;
		}
	})
	
	exports.StepDialog = StepDialog;
})