/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var env = require("lui/util/env");
	var topic = require("lui/topic");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var modelingLang = require("lang!sys-modeling-base");
	var DataList = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			
			this.recordData = [];
			this.setSource(new source.AjaxXml({
				parent : this,
				url : cfg.sourceUrl
			}));
			
			// 属性面板的模板HTML，使用模板文件方便调整
			this.setRender(new render.Template({
				parent : this,
				src : cfg.renderSrc
			}));
		},
		
		startup : function($super,cfg){
			$super(cfg);
			this.source.startup();
			this.render.startup();
			
			// 左侧导航变更就刷新
			topic.channel("modeling").subscribe("dialog.cate.change", this.refresh, this);
		},
		
		draw: function($super,params) {
			this.source.resolveUrl(params);
			$super(params);
		},
		
		doRender : function($super,cfg){
			$super(cfg);
			// 添加事件
			this.element.find("[data-record-value]").on("click",function(){
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
			});
		},
		
		refresh : function(params){
			this.erase();
			this.draw(params);
		},
		
		setRecordData : function(data){
			this.recordData = data || [];	//[]
		},
		
		getRecordDataByValue : function(value){
			for(var i = 0;i < this.recordData.length;i++){
				if(this.recordData[i].value === value){
					return this.recordData[i];
				}
			}
		},
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	exports.DataList = DataList;
})