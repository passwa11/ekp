/**
 * 移动首页设计页面的视图逻辑控制组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		topic = require('lui/topic');
	var modelingLang = require("lang!sys-modeling-base");
	
	var IndexTemplate = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.curTmpValue = cfg.type || "default";
			this.data = cfg.data || {
				data : {
					template : []
				}
			};
		},
		
		draw: function() {
			
		},
		
		doRender : function($super, cfg) {
			$super(cfg);
			var self = this;
			
			self.rightWrapDom = this.element.find(".model-mask-phone-right-item");
			/*************** 添加事件 start *****************/
			this.element.find("[data-tmp-value]").on("click",function(){
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
				var tmpValue = $(this).attr("data-tmp-value");
				self.updateRightInfo(tmpValue);
				self.curTmpValue = tmpValue;
			});
			// 双击直接选中
			this.element.find("[data-tmp-value]").on("dblclick",function(){
				$(this).trigger($.Event("click"));
				$(".model-mask-phone-right-btn").trigger($.Event("click"));
			});
			this.element.find('.model-mask-bg').on('click',function(){
				self.element.removeClass('active');
				self.element.fadeOut("fast");
			});
			this.element.on("click", ".model-mask-phone-right-btn" ,function(){
				self.element.removeClass('active');
				self.element.hide();
				topic.channel("modeling").publish("mobile.index.tmp.change",{value:self.curTmpValue});
			});
			this.element.on("click",".index-close-icon",function(){
				self.element.hide();
			});
			/*************** 添加事件 end *****************/
			this.element.find("[data-tmp-value='"+ this.curTmpValue +"']").trigger($.Event("click"));
		},
		
		updateRightInfo : function(value){
			this.rightWrapDom.html("");
			// 根据type获取到当前模板的信息
			var info = this.getTmpInfoByValue(value);
			// 渲染
			if(info){
				var html = "";
				html += "<div class='model-mask-phone-right-img model-mask-phone-img-"+ value +"' style='display:none'>";
				html += "</div>";
				html += "<div class='model-mask-phone-right-desc'>";
				html += "<p class='model-mask-phone-title'>"+ info.name +"</p>";
				html += "<p class='model-mask-phone-desc'>"+ info.desc + "</p>";
				html += "<div class='model-mask-phone-right-btn'>"+modelingLang['modeling.use.immediately']+"</div>";
				html += "</div>";
				$(html).appendTo(this.rightWrapDom).fadeIn();
			}else{
				console.log("【移动首页设计】找不到当前模板的信息("+ value +")");
			}
		},
		
		getTmpInfoByValue : function(value){
			var infos = this.data.data.template || [];
			for(var i = 0;i < infos.length;i++){
				if(infos[i].value === value){
					return infos[i];
				}
			}
		},
		
		select : function(){
			if(!this.isDrawed){
				this.load();	
			}
			this.element.fadeIn("fast");
			this.element.addClass('active');
			this.isDrawed = true;
		}
		
	});
	
	exports.IndexTemplate = IndexTemplate;
		
})