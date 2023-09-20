/**
 * 
 * 属性面板
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	
	function lowerFirst(str){
		return str.charAt(0).toLowerCase() + str.substring(1);
	}
	
	var Panel = base.Component.extend({
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.wgt = null;	// 记录当前绑定的wgt
			// 监听移动组件激活时间
			topic.channel("modeling").subscribe("mobile.design.widget.active", this.refresh, this);
	    },
	    
		startup : function($super,cfg){
			$super(cfg);
			// 通过委托的方式添加校验和视图同步
			this.attachValidate();
			this.attachView();
		},
	    
	    /** argus : {data:{attr[{
	     * 		text : "",
	     * 		name : "",
	     * 		type : "",
	     * 		value : ""
	     * 	}]},widget:xxxx} 
	     * */
	    draw : function($super,data){
	    	$super(data);
	    	data = data || {};
	    	var self = this;
	    	
	    	if(data && !$.isEmptyObject(data)){
	    		if(data.panelDraw){
	    			// 自己画属性面板
	    			this.element.append(this.wgt[data.panelDraw](data, this));
	    		}else{
	    			// 画模块头部信息
	    			this.drawModuleHeader(data);
	    			
	    			// 画内容区
	    			this.drawPanelContent(data);
	    		}
	    		if(this.wgt.warning){
	    			this.element.find("[data-validate]").each(function(index, dom){
	    				self.exceValidate(dom);
	    			});
	    		}
	    	}
	    	
	    },
	    
	    // 头部
	    drawModuleHeader : function(data){
	    	if(this.wgt && this.wgt.panelTitleDraw){
				this.wgt.panelTitleDraw(data, this.element);
			}else{
				var $title = $("<div class='field_label'></div>").appendTo(this.element);
				$title.append('<div class="field_label_desc"><i class="'+ (data.iconClass || "") +'"></i><p>'+ (data.text || "模块") +'</p></div>');				
			}
	    },
	    
	    // 内容区
	    drawPanelContent : function(data){
	    	this.fieldBody = $("<div class='field_value'></div>").appendTo(this.element);
	    	var $contentWrap = this.drawPanelContentWrap(this.fieldBody);
	    	
			if(data.attr){
	    		var attr = data.attr;
	    		for(var key in attr){
	    			var attrInfo = attr[key];
	    			this.buildAttrElement(key, attrInfo, $contentWrap);	    				
	    		}		    		
	    	}
			// 如果$contentWrap没有元素，则默认删除
			if($contentWrap.children().length === 0){
				$contentWrap.closest(".field_item_value").remove();
			}
	    },
	    
	    // 画表单元素外层框
	    drawPanelContentWrap : function(container){
	    	var $itemValue = $("<div class='field_item_value'/>").appendTo(container);
	    	var $itemContent = $("<div class='field_item_content' />").appendTo($itemValue);
	    	return $("<div class='content_wrap' />").appendTo($itemContent);
	    },
	    
	    // 属性
	    buildAttrElement : function(key, info, container){
	    	if(info && typeof(info) === "object"){
	    		var drawType = info.drawType || "input";
	    		var funName = lowerFirst(drawType) + "Draw";
	    		if(this.wgt[funName]){
	    			this.wgt[funName](key, info, container, this.fieldBody, this);
	    		}else{
	    			console.log("【属性面板】找不到方法：" + funName);
	    		}
	    	}
	    },
	    
		refresh : function(argus){
			/*if(argus && argus.widget && argus.widget === this.wgt){
				return;
			}*/
			this.erase();
			argus = argus || {};
			this.wgt = argus.widget;
			this.draw(argus.data);
		},
		
		// 暂挂，有空完善
		eraseByFadeOut : function(){
			if (this.children && this.children.length) {
				for (var i = 0; i < this.children.length; i ++) {
					if (this.children[i].erase) {
						this.children[i].erase();
					}
				}
			}
			this.isDrawed = false;
			this.element.fadeOut("normal",function(){
				$(this).empty();
			});
		},
		
		/***************** 视图同步 start ***************************/
		// 待完善
		attachView : function(){
			var self = this;
			var events = ["input", "change"];
			for(var i = 0;i < events.length;i++){
				this.attachViewEvent(events[i]);
			}
		},
		
		attachViewEvent : function(event){
			var self = this;
			this.element.delegate("[data-update-event='"+ event +"']", event ,function(e){
				e.stopPropagation();
				var dom = e.target;
	    		var updateView = $(dom).attr("data-updateView") === "true" ? true : false;
	    		self.wgt.updateData(updateView);
			});
		},
		
		/***************** 视图同步 end ***************************
		/***************** 校验 start *******************/
		attachValidate : function(){
			var self = this;
			this.element.delegate("[data-validate]","change",function(event){
				self.exceValidate(this);
				// 校验当前组件是否还处于警告状态
				self.wgt.validateWarning();
			});
		},
		
		exceValidate : function(dom){
			var wgt = this.wgt;
			var validateFun = $(dom).attr("data-validate") || "";
			if(validateFun){
				var funs = validateFun.split(" ");
				for(var i = 0;i < funs.length;i++){
					if(wgt[funs[i] + "Validate"]){
						wgt[funs[i] + "Validate"](dom);
					}
				}
			}
		}
		/***************** 校验 start *******************/
	});
	
	module.exports = Panel;
})