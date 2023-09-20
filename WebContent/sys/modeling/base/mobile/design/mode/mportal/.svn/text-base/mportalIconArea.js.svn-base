/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var render = require("lui/view/render");
	var dialog = require("lui/dialog");
	var multiListAttrBase = require("sys/modeling/base/mobile/design/mode/common/multiListAttrBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {
		text : modelingLang["modeling.Icon.area"],
		iconClass : "label_icon_icon_area",
		attr : {
			listViews : {
				"text" : "模块列表",
				"drawType" : "multiListView",	// listView对应listViewDraw（画配置页面）和listViewGetValue（获取值）方法
				"validate" : {
					title : "required",
					listView : "required",
				},	// 校验器
				"value" : []
			}
		}
	};
	
	var mportalIconArea = multiListAttrBase.MultiListAttrBase.extend({
		
		widgetKey : "iconArea",
		
		// 由于增加图标设置，需重新定义html
		renderHtml : "/sys/modeling/base/mobile/design/mode/mportal/mportalIconAreaRender.html",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 // 如果没有设置值，则默认生成三个未定义
			 if(datas.attr.listViews.value.length === 0){
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.1"]
				 });
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.2"]
				 });
				/* datas.attr.listViews.value.push({
					 title : "样例3"
				 });
				 datas.attr.listViews.value.push({
					 title : "样例4"
				 });*/
			 }
			 return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = false;	// 是否需要设置总计页签
			this.setInitData(cfg.data);
	    },
	    
	    startup : function($super,cfg){
	    	$super(cfg);
	    	// 初始化swiper
			this.initSwiper();
	    },
	    
		initSwiper : function(){
			this.swiper = new Swiper(".mportal-swiper", {
		      calculateHeight: true,
		      pagination: '.mportal-pagination'
		    });
		},
	    
		draw : function($super,cfg){
			var items = this.data.attr.listViews.value || [];
			this.swiper.removeAllSlides();	// 移除所有子元素
			var sliceArr = this.sliceArrByNum(items, 4);	// 分割数组，每4个元素为一组
			// 当有超过一屏时，出现分页
			if(sliceArr.length > 1){
				this.element.find(".slide-pagination").addClass("active");
			}else{
				this.element.find(".slide-pagination").removeClass("active");
			}
			for(var i = 0;i < sliceArr.length;i++){
				this.createSlide(sliceArr[i]);
			}
			$super(cfg);
		},
		
		sliceArrByNum : function(data , num){
			var result = [];
			for (var i = 0; i < data.length; i += num) {
				result.push(data.slice(i, i + num));
			}
			return result;
		},
		
		// 创建滑块
		createSlide : function(items){
			var slideHtml = "";
			//var width = parseFloat(100/items.length) + "%";
			var width = "25%";
			for(var i = 0;i < items.length;i++){
				slideHtml += this.getItemHtml(items[i], width);
			}
			if(slideHtml){
				this.swiper.appendSlide(slideHtml,"swiper-slide");
			}
		},
		
		getItemHtml : function(item, width){
			var itemHtml = "";
			itemHtml += "<div class='mportal-slide' style='width:"+ width +"'>" +
					"<div class='mportal-slide-wrap'>" +
					"<div class='mportal-slide-icon'><i class='mui "+ (item.icon || 'mui-docRecord') +" '></i></div>" +
					"<p>"+ (item.title || modelingLang["modeling.Undefined"]) +"</p>" +
					"</div>" +
					"</div>";
			
			return itemHtml;
		},
		
		erase: function() {
			this.swiper.removeAllSlides();	// 移除所有子元素
			this.isDrawed = false;
		},
		
		/***********属性面板 start *************/
		createFormItem : function($super,formData, attr){
			var $formItem = $super(formData, attr);
			$formItem.find(".content-icon-choose").on("click", function(){
				var iconDom = this;
				var url = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=2";
				dialog.iframe(url, modelingLang["modeling.app.selectIcon"], function(rtn){
					if(rtn){
						$formItem.find("[name*='icon']").val(rtn.className).trigger($.Event("change"));
						$(iconDom).find("i").attr("class","mui " + rtn.className);
					}
				} ,{
					width: 800,
					height: 500,
					close: true
				});
			});
			return $formItem;
		},
		
		getMultiListViewBlockKeyData : function($super, dom){
			var keyData = $super(dom);
			keyData.icon = $(dom).find("[name*='icon']").val() || "";
			return keyData;
		},

		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		},
		/***********属性面板 end *************/
		
	});
	
	module.exports = mportalIconArea;
})