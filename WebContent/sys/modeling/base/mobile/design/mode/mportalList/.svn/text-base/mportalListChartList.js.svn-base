/**
 * 图表区
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var render = require("lui/view/render");
	var dialog = require("lui/dialog");
	var listAttrBase = require("sys/modeling/base/mobile/design/mode/common/listAttrBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {
		text : modelingLang["modeling.Chart.area"],
		iconClass : "label_icon_mportallist_chartlist",
		attr : {
			title : {
				"text" : "标题",
				"drawType" : "input",
				"validate" : "required",
				"value" : ""
			},
			listView : {
				"text" : "业务图表",
				"drawType" : "listView",	// listView对应listViewDraw（画配置页面）和listViewGetValue（获取值）方法
				"validate" : {
					listView : "required"
				},	// 校验器
				"value" : {}
			}
		}
	};
	
	var mportalListDataList = listAttrBase.ListAttrBase.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/mportalList/mportalListChartListRender.html#",
		
		widgetKey : "chartList",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			 return datas;
		},
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.isCount = false;	// 是否需要设置总计页签
			this.setInitData(cfg.data);
	    },
	    
		draw : function($super,cfg){
			this.element.html("");
			// 画标题
			this.drawTitle();
			
			// 画静态内容
			this.drawContent();
			
			$super(cfg);
		},
		
		drawTitle : function(){
			var titleHtml = "";
			titleHtml += "<div class='dataList-title'>";
			// 画标题
			var title = this.data.attr.title.value || modelingLang["modelingAppMobile.docSubject"];
			titleHtml += "<p class='dataList-title-left'>"+ title +"</p>";
			
			this.element.append(titleHtml);
		},
		
		drawContent : function(){
			var defalutCardNum = 1;	// 默认展示图表样板
			var tmpCard = "<div class='cards-item-chart'> <div class='cards-item-chart-image'></div> </div>";
			var contentHtml = "<div class='cards'>";
			for(var i = 0;i < defalutCardNum;i++){
				contentHtml += tmpCard;
			}
			contentHtml += "</div>";
			
			this.element.append(contentHtml);
		},
		
		/*********** 属性面板 start ************/
		setValuesAfterListViewDialog : function($super,uuId, rtn, $formItem){
			var views = this.transStrToJson(rtn.data.viewsjson);
			views = this.formatViews(views);
			if(views.length > 3){
				dialog.alert(modelingLang['modeling.morethan.tab']);
				return false;
			}
			var $lvCollection = $formItem.find("[name*='lvCollection']");
			$lvCollection.val(JSON.stringify(views || []));
			$super(uuId, rtn, $formItem);
		},
		
		listViewGetValue : function($super, key, info, area){
			var itemInfo = $super(key, info, area);
			itemInfo.lvCollection = JSON.parse(area.find("[name*='lvCollection']").val() || "[]");
			return itemInfo;
		},
		/*********** 属性面板 start ************/
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	module.exports = mportalListDataList;
})