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
		text : modelingLang["modeling.Statistics.area"],
		iconClass : "label_icon_statistics",
		attr : {
			listViews : {
				"text" : "模块列表",
				"drawType" : "multiListView",	// listView对应listViewDraw（画配置页面）和listViewGetValue（获取值）方法
				"validate" : {
					title : "required",
					newlistView : "required"
				},	// 校验器
				"value" : []
			}
		}
	};

	var mportalListStatistics = multiListAttrBase.MultiListAttrBase.extend({

		widgetKey : "statistics",

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
				 datas.attr.listViews.value.push({
					 title : modelingLang["modeling.Example.3"]
				 });
			 }
			 return datas;
		},

		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = true;	// 是否需要设置总计页签
			this.setInitData(cfg.data);
	    },

	    startup : function($super,cfg){
	    	$super(cfg);
	    	// 初始化swiper
			this.initSwiper();
	    },


		initSwiper : function() {
			this.swiper = new Swiper(".mportalList-swiper", {
				calculateHeight : true,
				pagination: '.mportalList-pagination'
			});
		},

		draw : function($super,cfg){
			var self = this;
			// 画方块
			// 一开始页面还没展示，这里添加延时，是为了让swiper计算宽度用
			setTimeout(function(){
				self.drawContent();
			},10);
			$super(cfg);
		},

		drawContent : function(){
			var items = this.data.attr.listViews.value;
			this.swiper.removeAllSlides();	// 移除所有子元素

			var sliceArr = this.sliceArrByNum(items, 3);	// 分割数组，每3个元素为一组
			// 当有超过一屏时，出现分页
			if(sliceArr.length > 1){
				this.element.find(".slide-pagination").addClass("active");
			}else{
				this.element.find(".slide-pagination").removeClass("active");
			}
			for(var i = 0;i < sliceArr.length;i++){
				this.createSlide(sliceArr[i]);
			}
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
			var width = "33%";
			for(var i = 0;i < items.length;i++){
				slideHtml += this.getItemHtml(items[i], width);
			}
			if(slideHtml){
				this.swiper.appendSlide(slideHtml,"swiper-slide");
			}
		},

		getItemHtml : function(item, width){
			//var num = Math.round(Math.random()*1000);
			var num = "5";
			var itemHtml = "";
			itemHtml += "<div class='mportalList-slide' style='width:"+ width +"'>" +
					"<p>"+ num +"</p>" +
					"<span>"+ (item.title || modelingLang["modeling.Undefined"]) +"</span>" +
					"</div>";
			return itemHtml;
		},

		erase: function() {
			this.swiper.removeAllSlides();	// 移除所有子元素
			this.isDrawed = false;
		}

	});

	module.exports = mportalListStatistics;
})