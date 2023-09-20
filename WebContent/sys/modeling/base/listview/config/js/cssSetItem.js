/**
 * 显示项查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
	var modelingLang = require("lang!sys-modeling-base");
    var ITEM_HTML_TEMP= 
    "       <tr class=\"displaycssTr\">\n" +
    "           <td>\n" +
    "               <div class=\"model-edit-view-oper\">\n" +
    "                  <div class=\"model-edit-view-oper-head\">\n" +
    "                     <div class=\"model-edit-view-oper-head-title\">\n" +
    "                          <div onclick=\"changeToOpenOrClose(this)\">\n" +
    "                               <i class=\"open\"></i>\n" +
    "                          </div>\n" +
    "                          <span>样式<span class='title-index'>一</span></span>\n" +
    "                     </div>\n" +
    "                     <div class=\"model-edit-view-oper-head-item\" style=\"padding-top:0px;\">\n" +
    "                         <div class=\"del\"\n >\n" +
    "                              <i></i>\n" +
    "                          </div>\n" +
    "                          <div class=\"update\"\n >\n" +
    "                              <i></i>\n" +
    "                          </div>\n" +
    "                     </div>\n" +
    "                  </div>\n" +
    "                <div class=\"model-edit-view-oper-content listview-content\">\n" +
    "                    <div class='model-edit-view-oper-content-item first-item last-item'>\n" +
    "                         <div class=\"display-whereType\">\n" +
    "                             <div class='display-whereType-title'>\n" +
    "                                   <span>条件：</span>\n" +
    "                 					<span class=\"displayWhereType\">满足</span>\n" +
    "                 			  </div>\n" +
    "                 		  	  <div class='display-whereType-content'>\n" +
    "                 		  	  </div>\n" +
    "               	 	  </div>\n" +
    "                		  <div class=\"display-css\">\n" +
    "                 		  		<span>样式 ：</span>\n" +
    "                 	      <div class='display-css-content'>\n" +
    "                  	            <div style=\"display:inline-block;\" class=\"fieldItem\">\n" +
    "                                  <div class=\"fieldItem-box\">\n"+
    "                                    <div class=\"pretabWrap\"><div class=\"pretab\"></div></div>\n" +
    "                   				 <div class=\"satisfy\">内容</div>\n" +
    "                   				 <div class=\"lasttabWrap\"><div class=\"lasttab\"></div></div>\n" +
    "                                  </div>"+
    "               				</div>\n" +
    "                		 		<div style=\"display:inline-block;\" class=\"otherItem\">\n" +
    "                        			<span class=\"otherField\">其他</span>\n" +
    "               		 		</div>\n" +
    "             			  </div>\n" +
    "                	 	  </div>\n" +
    "              		 </div>\n" +
    "              	  </div>\n" +
    "              </div>\n" +
    "             </td>\n" +
    "           </tr>\n" ;
	var CALENDAR_ITEM_HTML_TEMP=
		"       <tr class=\"displaycssTr\">\n" +
		"           <td>\n" +
		"               <div class=\"model-edit-view-oper\">\n" +
		"                  <div class=\"model-edit-view-oper-head\">\n" +
		"                     <div class=\"model-edit-view-oper-head-title\">\n" +
		"                          <div onclick=\"changeToOpenOrClose(this)\">\n" +
		"                               <i class=\"open\"></i>\n" +
		"                          </div>\n" +
		"                          <span>样式<span class='title-index'>一</span></span>\n" +
		"                     </div>\n" +
		"                     <div class=\"model-edit-view-oper-head-item\" style=\"padding-top:0px;\">\n" +
		"                         <div class=\"del\"\n >\n" +
		"                              <i></i>\n" +
		"                          </div>\n" +
		"                          <div class=\"update\"\n >\n" +
		"                              <i></i>\n" +
		"                          </div>\n" +
		"                     </div>\n" +
		"                  </div>\n" +
		"                <div class=\"model-edit-view-oper-content listview-content\">\n" +
		"                    <div class='model-edit-view-oper-content-item first-item last-item'>\n" +
		"                         <div class=\"display-whereType\">\n" +
		"                             <div class='display-whereType-title'>\n" +
		"                                   <div class='display-whereType-title-where'>条件</div>\n" +
		"                 					<div class=\"displayWhereType\">满足</div>\n" +
		"                 			  </div>\n" +
		"                 		  	  <div class='display-whereType-content'>\n" +
		"                 		  	  </div>\n" +
		"               	 	  </div>\n" +
		"                		  <div class=\"display-css\">\n" +
		"                 		  		<div class='display-css-title'>样式</div>\n" +
		"                 	      <div class='display-css-content-calendar'>\n" +
		"                  	            <div style=\"display:inline-block;\" class=\"fieldItem\">\n" +
		"               				</div>\n" +
		"                   			<div class=\"satisfy\">内容</div>\n" +
		"             			  </div>\n" +
		"                	 	  </div>\n" +
		"              		 </div>\n" +
		"              	  </div>\n" +
		"              </div>\n" +
		"             </td>\n" +
		"           </tr>\n" ;
    
    var CssSetItem = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.parent.jishu += 1;
            this.showtext = this.parent.jishu;
            this.data = cfg.data;
            this.childrenTemp = cfg.childrenTemp;
            this.parent = cfg.parent;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.displayType = cfg.displayType;
			this.allField = cfg.allField;
			this.modelDict = listviewOption.baseInfo.modelDict;
			this.channel = cfg.channel || null;
            this.draw(this.data);
            
        },
	    draw:function (data) {
        	if(this.displayType === "calendar"){
				var $tr = $(CALENDAR_ITEM_HTML_TEMP);
			}else{
				var $tr = $(ITEM_HTML_TEMP);
			}
	    	var self = this;
	    	self.orgData=data;
	    	$tr.find(".del").on("click",function(){
	    		dialog.confirm("确认删除“"+data.fieldText+"”?",function(value){
	    			if(value == true){
	    				self.destroy($tr);
	    			}
	    		})
	    	})
	    	$tr.find(".update").on("click",function(){
	    		self.updatedisplayCssSet();
	    	})
	    	var displayvalue = data.fieldKey;
	    	$tr.find(".title-index").text(this.showtext);
	    	$tr.find(".displayWhereType").text(data.whereType.whereTypeText);
			//显示项样式->条件
	    	if(data.whereType.whereTypeValue != "2"){
	    		for(var i = 0;i < data.where.length; i++){
					var html = "";
					html += '<div class="displayWhereItem">';
					html += '<span class="displayWhereItem_title">'+ data.where[i].name.text + '</span>';
					html += this.getWhereItemHtml(data.where[i]);
					html += '<span class="displayWhereItem_type">“'+data.where[i].type.text+'”</span>';
					if(JSON.stringify(data.where[i].expression) != "{}" && data.where[i].expression.text != ""
						&& data.where[i].expression.text != "undefined"){
						html += '<span class="displayWhereItem_text">“'+decodeURI(data.where[i].expression.text)+'”</span>';
					}
					html += '</div>';
					$tr.find(".display-whereType-content").append(html);
				}
	    	}
			$tr.find(".fieldItem").css("background-color",data.field.backgroundColor);
			$tr.find(".satisfy").css("color",data.field.fontColor);
			if(this.displayType == 'calendar'){
				$tr.find(".satisfy").css("background-color",this.getRGBA(data.field.backgroundColor,'0.2'));
			}
			if(this.displayType != "board"){
				$tr.find(".otherItem").css("background-color",data.fieldItem.ItemBackgroundColor);
				$tr.find(".otherField").css("color",data.fieldItem.ItemColor);
				var tab = data.field.tab;
				if(tab.tabType == '0'){
					//自定义
					if(tab.showPosition == '0'){
						$tr.find(".pretab").text(tab.tabContent);
						$tr.find(".pretab").css("background-color",tab.tabBackgroundColor);
						$tr.find(".pretab").css("color",tab.tabFontColor);
						$tr.find(".preTab").css({"font-size":tab.tabFontSize+"px"});
						$tr.find(".lasttab").text("");
						$tr.find(".lasttab").removeAttr("style");
					}else{
						$tr.find(".pretab").text("");
						$tr.find(".pretab").removeAttr("style");
						$tr.find(".lasttab").text(tab.tabContent);
						$tr.find(".lasttab").css({"color":tab.tabFontColor});
						$tr.find(".lasttab").css({"background-color":tab.tabBackgroundColor});
						$tr.find(".lasttab").css({"font-size":tab.tabFontSize+"px"});
					}

				}else{
					//图标
					$tr.find(".lasttab").text("");
					$tr.find(".pretab").text("");
					$tr.find(".pretab").removeAttr("style");
					$tr.find(".lasttab").removeAttr("style");
					var html = '<i></i>';
					if(tab.showPosition == '0'){
						$tr.find(".pretab").append(html);
						$tr.find(".pretab i").attr("class","iconfont_nav "+tab.defaultIcon);
						$tr.find(".pretab i").css("font-size",tab.iconSize+"px");
					}else{
						$tr.find(".lasttab").append(html);
						$tr.find(".lasttab i").attr("class","iconfont_nav "+tab.defaultIcon);
						$tr.find(".lasttab i").css("font-size",tab.iconSize+"px");
					}
				}
			}

			this.element = $tr;
			this.parent.element.find(".displayCssSetTable").append($tr);
	    },
	    reDraw:function(data){
	    	var self = this;
	    	this.element.find(".displayWhereType").text(data.whereType.whereTypeText);
	    	this.element.find(".title-index").text(this.showtext);
	    	this.element.find(".display-whereType-content").html("");
	    	//显示项样式->条件
	    	if(data.whereType.whereTypeValue != "2"){
				for(var i = 0;i < data.where.length; i++){
					var html = "";
					html += '<div class="displayWhereItem">';
					html += '<span class="displayWhereItem_title">'+ data.where[i].name.text + '</span>';
					html += this.getWhereItemHtml(data.where[i]);
					html += '<span class="displayWhereItem_type">“'+data.where[i].type.text+'”</span>';
					if(JSON.stringify(data.where[i].expression) != "{}" && data.where[i].expression.text != ""
						&& data.where[i].expression.text != "undefined"){
						html += '<span class="displayWhereItem_text">“'+decodeURI(data.where[i].expression.text)+'”</span>';
					}
					html += '</div>';
					this.element.find(".display-whereType-content").append(html);
				}
	    	}
			//显示项样式->样式
			this.element.find(".fieldItem").css("background-color",data.field.backgroundColor);
			this.element.find(".satisfy").css("color",data.field.fontColor);
			if(this.displayType == 'calendar'){
				this.element.find(".satisfy").css("background-color",this.getRGBA(data.field.backgroundColor,'0.2'));
			}
			this.element.find(".otherItem").css("background-color",data.fieldItem.ItemBackgroundColor);
			this.element.find(".otherField").css("color",data.fieldItem.ItemColor);
			var tab = data.field.tab;
			if(tab.tabType == '0'){
	    		   //自定义
	    		   if(tab.showPosition == '0'){
	    			   this.element.find(".pretab").text(tab.tabContent);
	    			   this.element.find(".pretab").css("background-color",tab.tabBackgroundColor);
	    			   this.element.find(".pretab").css("color",tab.tabFontColor);
	    			   this.element.find(".pretab").css({"font-size":tab.tabFontSize+"px"});
	    			   this.element.find(".lasttab").text("");
	    			   this.element.find(".lasttab").removeAttr("style");
	        	   }else{
	        		   this.element.find(".pretab").text("");
	        		   this.element.find(".pretab").removeAttr("style");
	        		   this.element.find(".lasttab").text(tab.tabContent);
	        		   this.element.find(".lasttab").css({"color":tab.tabFontColor});
	        		   this.element.find(".lasttab").css({"background-color":tab.tabBackgroundColor});
	        		   this.element.find(".lasttab").css({"font-size":tab.tabFontSize+"px"});
	        	   }
        		   
        	   }else{
        		   //图标
        		   this.element.find(".lasttab").text("");
        		   this.element.find(".pretab").text("");
        		   this.element.find(".pretab").removeAttr("style");
        		   this.element.find(".lasttab").removeAttr("style");
        		   var html = '<i></i>';
        		   if(tab.showPosition == '0'){
        			   this.element.find(".pretab").append(html);
        			   this.element.find(".pretab i").attr("class","iconfont_nav "+tab.defaultIcon);
        			   this.element.find(".pretab i").css("font-size",tab.iconSize+"px");
        		   }else{
        			   this.element.find(".lasttab").append(html);
        			   this.element.find(".lasttab i").attr("class","iconfont_nav "+tab.defaultIcon);
        			   this.element.find(".lasttab i").css("font-size",tab.iconSize+"px");
        		   }
        	   }
	    },
	    destroy: function ($tr) {
	    	this.parent.deleteWgt(this);
	    	var index = $tr.find(".title-index").text() - 1; 
	    	$tr.remove();
	    	//更改样式计数文本：“样式1”
	    	var fieldCssSetArr = this.parent.parent.fieldCssSet;
	    	for(key in fieldCssSetArr){
	    		if(key == this.data.fieldKey && fieldCssSetArr[key] != null){
	    			var cssdataArr = fieldCssSetArr[key].fieldCssSetArray;
	    			for(var i = index;i < cssdataArr.length;i++){
	    				$(cssdataArr[i].element).find(".title-index").text(++index);
	    			}
	    		}
	    	}
        },
        updatedisplayCssSet:function(){
        	 var self = this;
        	 var data=self.orgData;
             var selected = this.data.selected;
             var text = this.data.fieldText;
             if(this.parent && this.parent.parent){
				 var showMode = this.parent.parent.showMode || "";
			 }
             //显示项
             var url = '/sys/modeling/base/listview/config/dialog_display.jsp?type=update';
             dialog.iframe(url, modelingLang['modelingAppListview.fdDisplayCssSet']+"(" + text + ")", function (backdata) {
                 //回调
                 if (!backdata) {
                     return;
                 }
                 backdata = $.parseJSON(backdata);
                 self.orgData = backdata;
                 self.reDraw(backdata);
             }, {
                 width: 780,
                 height: 500,
                 params: {
                     selected: selected,
                     text: text,
                     data:data,
					 displayType:this.displayType,
					 showMode:showMode,
					 allField:self.allField,
					 modelDict:self.modelDict
                 }
             })
        },
		getRGBA : function(colorData,a){
			var color = (colorData.charAt(0)=="#") ? colorData.substring(1,7):colorData;
			var R = parseInt(color.substring(0,2),16);
			var G = parseInt(color.substring(2,4),16);
			var B = parseInt(color.substring(4,6),16);
			return 'rgb('+ R + ','+ G +','+ B +',' + a + ')';
		},
		getWhereItemHtml:function (data){
        	var html = "";
			if(data.match == "="){
				html += '<span class="compare">“等于”</span>';
			}else if(data.match == "like"){
				html += '<span class="compare">“包含”</span>';
			}else if(data.match == "eq"){
				html += '<span class="compare">“等于”</span>';
			}else if(data.match == "lt"){
				if (data.name.type == "DateTime"
					|| data.name.type == "Date"
					|| data.name.type == "Time"  ){
					html += '<span class="compare">“早于”</span>';
				}else{
					html += '<span class="compare">“小于”</span>';
				}
			}else if(data.match == "le"){
				if (data.name.type == "DateTime"
					|| data.name.type == "Date"
					|| data.name.type == "Time"  ){
					html += '<span class="compare">“不晚于”</span>';
				}else{
					html += '<span class="compare">“小于等于”</span>';
				}
			}else if(data.match == "gt"){
				if (data.name.type == "DateTime"
					|| data.name.type == "Date"
					|| data.name.type == "Time"  ){
					html += '<span class="compare">“晚于”</span>';
				}else{
					html += '<span class="compare">“大于”</span>';
				}
			}else if(data.match == "ge"){
				if (data.name.type == "DateTime"
					|| data.name.type == "Date"
					|| data.name.type == "Time"  ){
					html += '<span class="compare">“不早于”</span>';
				}else{
					html += '<span class="compare">“大于等于”</span>';
				}
			}else if(data.match == "!{notequal}"){
				html += '<span class="compare">“不等于”</span>';
			}else if(data.match == "!{notContain}"){
				html += '<span class="compare">“不包含”</span>';
			}
			return html;
		},
        getKeyData:function(){
        	return this.orgData;
        }
    });

    exports.CssSetItem = CssSetItem;
});
