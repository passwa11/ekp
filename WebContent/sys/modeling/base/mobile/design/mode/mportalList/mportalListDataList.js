/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var render = require("lui/view/render");
	var dialog = require("lui/dialog");
	var listAttrBase = require("sys/modeling/base/mobile/design/mode/common/listAttrBase");
	var listAttrItemBase = require("sys/modeling/base/mobile/design/mode/common/ListAttrItemBase");
	var modelingLang = require("lang!sys-modeling-base");
	var tmpData = {

		text : modelingLang["modeling.Display.area"],
		iconClass : "label_icon_mportallist_datalist",
		attr : {
			title : {
				"text" : "标题",
				"drawType" : "input",
				"validate" : "required",
				"value" : ""
			},
			listView : {
				text : "列表视图",
				drawType : "multiListView",
				selfTitle : "listViewTitleDraw",
				validate : {
					listView : "required",
					tab : "required",
				},	// 校验器
				value : []
			}
		}
	};
	
	var mportalListDataList = listAttrItemBase.ListAttrItemBase.extend({
		
		renderHtml : "/sys/modeling/base/mobile/design/mode/mportalList/mportalListDataListRender.html#",
		
		widgetKey : "dataList",
		
		formatData : function(data){
			var datas = this.extend(tmpData, data);
			// 如果没有设置值，则默认生成1个未定义
			if(datas.attr.listView.value.length === 0){
				datas.attr.listView.value.push({
					tab : "",
				});

			}
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
			// 画左侧标题
			var title = this.data.attr.title.value || modelingLang["modelingAppNav.docSubject"];
			titleHtml += "<p class='dataList-title-left'>"+ title +"</p>";
			
			// 画右侧页签
			titleHtml += "<div class='dataList-right'>";
			var values = this.data.attr.listView.value || {};
			var tabInfos = [];
			if (values instanceof Array ){
				this.drawNewTabs(tabInfos,values);
			}else {
				if($.isEmptyObject(values) || $.isEmptyObject(values.lvCollection)){
					tabInfos = [{
						text: modelingLang["modeling.tab.1"]
					},{
						text: modelingLang["modeling.tab.2"]
					},{
						text: modelingLang["modeling.tab.3"]
					}]
				}else{
					tabInfos = values.lvCollection || [];
				}
			}
			for(var i = 0;i < tabInfos.length;i++){
				titleHtml += "<p ";
				if(i === 0){
					titleHtml += "class='active'";
				}
				titleHtml += ">"+ tabInfos[i].text +"</p>";
			}
			titleHtml += "</div>";
			
			titleHtml += "</div>";
			
			this.element.append(titleHtml);
		},

		drawNewTabs :function (tabInfos,values){
          if (values.length>0){
			  for(var i = 0;i < values.length;i++){
				  var value = values[i];
				  tabInfos.push({text: value.tab});
			  }
		  }else {
			  tabInfos = [{
				  text: modelingLang["modeling.tab.1"]
			  },{
				  text: modelingLang["modeling.tab.2"]
			  },{
				  text: modelingLang["modeling.tab.3"]
			  }]
		  }
		},
		
		drawContent : function(){
			var defalutCardNum = 2;	// 默认展示样板
			var tmpCard = "<div class='cards-item'>" +
							"<div class='cards-item-wrap'>" +
							"<div class='cards-item-top'></div>" +
							"<div class='cards-item-bottom'></div>" +
							"</div></div>";
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