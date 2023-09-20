/**
 * 
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    
    var DisplayWhereGenerator = require("sys/modeling/base/resources/js/displayWhereGenerator");

    var DisplayDialog = base.Container.extend({

        initProps: function ($super, cfg) {
        	$super(cfg);
        	this.viewContainer = cfg.viewContainer;
        	this.$tmpEle = cfg.$tmpEle;
        	this.text = cfg.text;
        	this.selected = cfg.selected;
        	this.field = cfg.selected.field;
        	this.type = cfg.selected.type;
        	this.data = cfg.data;
        	this.whereCollection = [];
        	this.xformIsInit = false;
        	this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
			this.allField = $.parseJSON(cfg.allField.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n"));
			this.modelDict = cfg.modelDict
        	this.displayType = cfg.displayType;
        	var self =this;
        	this.$table = this.config.viewContainer;
        	$(".table_opera").on("click", function () {
        		self.data = {};
        		self.build();
            });
        	self.drawShowCss();
        	$(".display_view_item").text(this.text);
        	
        	this.initByStoreData(self.data);
        	//将页面显示
        	$(".lui_custom_list_container").css("display","block");
        },
        build: function () {
            this.buildWhere(this.$table);
        },
        // 画查询字段
        buildWhere: function ($table) {
        	//#129390 ie10 不支持flex写法 直接移除style
			/*$(".where_view").removeAttr("style");
			$(".where_tr").css("display","contents");*/
        	this.$tmpEle.find(".where_view").css('display','contents');
        	this.$tmpEle.find(".display_item").text(this.text);
            var self = this;
            var whereWgt = new DisplayWhereGenerator.DisplayWhereGenerator({parent: self});
            whereWgt.draw($table);
            this.$tmpEle.find(".display_item_td").css('display','table-cell');
        },
        //画显示样式
       drawShowCss: function (){
    	   $(".fontColorSelect").attr("data-color-mark-id","fontColorSelectValue");
    	   $(".backgroundColorSelect").attr("data-color-mark-id","backgroundColorSelectValue");
    	   $(".fontColorTabSelect").attr("data-color-mark-id","fontColorTabSelectValue");
    	   $(".backgroundColorTabSelect").attr("data-color-mark-id","backgroundColorTabSelectValue");
    	   $(".fontColorItemSelect").attr("data-color-mark-id","fontColorItemSelectValue");
    	   $(".backgroundColorItemSelect").attr("data-color-mark-id","backgroundColorItemSelectValue");
    	   if (window.SpectrumColorPicker) {
    		   window.SpectrumColorPicker.init("fontColorSelectValue");
    		   window.SpectrumColorPicker.init("backgroundColorSelectValue");
    		   window.SpectrumColorPicker.init("fontColorTabSelectValue");
    		   window.SpectrumColorPicker.init("backgroundColorTabSelectValue");
    		   window.SpectrumColorPicker.init("fontColorItemSelectValue");
    		   window.SpectrumColorPicker.init("backgroundColorItemSelectValue");
    		   
    		   window.SpectrumColorPicker.setColor("fontColorSelectValue","#FF8000");
               window.SpectrumColorPicker.setColor("backgroundColorSelectValue","#ffffff");
               window.SpectrumColorPicker.setColor("fontColorTabSelectValue","#FF8000");
               window.SpectrumColorPicker.setColor("backgroundColorTabSelectValue","#ffffff");
               window.SpectrumColorPicker.setColor("backgroundColorItemSelectValue","#ffffff");
               
               seajs.use(['lui/jquery','lui/topic'],function($,topic) {
               		topic.publish("modeling.SpectrumColorPicker.init");
               })
           }
       },
       addWgt: function(wgt){
    	   this.whereCollection.push(wgt);
       },
       deleteWgt: function(wgt){
    	   var collect = [];
           collect = this.whereCollection;
           for (var i = 0; i < collect.length; i++) {
               if (collect[i] === wgt) {
                   collect.splice(i, 1);
                   break;
               }
           }
           if(this.whereCollection.length == '0'){
           		$(".where_tr").css("display","none");
           }
       },
       getKeyData: function () {
           var keyData = {};
           keyData.where = [];
           keyData.fieldKey = this.field;
           keyData.fieldText = this.text;
           keyData.selected = this.selected;
           for (var i = 0; i < this.whereCollection.length; i++) {
               var whereWgt = this.whereCollection[i];
               var whereWgtKeyData = whereWgt.getKeyData();
               if (!whereWgtKeyData) {
                   continue;
               }
               // 索引，用来进行记录排序，暂无用
               whereWgtKeyData.idx = i;
               keyData.where.push(whereWgtKeyData);
           }
           return keyData;
       },
      initByStoreData : function(storeData){
    	  if(JSON.stringify(storeData) != "{}"){
    		//初始化查询规则
    		  $("[name='fdDisplayWhereType']").each(function(){
    			  if(storeData.whereType.whereTypeValue == $(this).val()){
    				  $(this).attr("checked","checked");
    				  if($(this).val() == '2'){
    					  $(".view_field_where_div").css("display","none");
    				  }
    			  }
    		  })
    		  
    		  if (storeData.hasOwnProperty("where")) {
    			  var storeWhere = storeData["where"];
    			  var self = this;
    			  for (var i = 0; i < storeWhere.length; i++) {
                      var data = storeWhere[i];
                      var whereWgt = new DisplayWhereGenerator.DisplayWhereGenerator({parent: self,"storeData":data});
                      whereWgt.draw(self.$table);
                      whereWgt.initByStoreData(data);
                      $(".where_view").css('display','contents');
    			  }
    		  }

    		  //初始化样式
    		  var fontcolor = storeData.field.fontColor;
    		  var bgcolor = storeData.field.backgroundColor;
    		  var whereType = storeData.whereType;
    		  
    		  var tab = storeData.field.tab;
    		  var fontColorTab = tab.tabFontColor;
    		  var bgColorTab = tab.tabBackgroundColor;
    		  var tabContent = tab.tabContent;
    		  var tabfontSize = tab.tabFontSize;
    		  var tabIsShow = tab.isShow;
    		  
    		  var fontcolorItem = storeData.fieldItem.ItemColor;
    		  var bgcolorItem = storeData.fieldItem.ItemBackgroundColor;
    		  
    		  if(tabIsShow == '1'){
  				//添加标记展开和隐藏
      			$(".tab_temp_html").css("display","block");
      			$("[name='addTab']").val("1");
  			 }else{
  				$(".tab_temp_html").css("display","none");
  				$(".add_tab_switch").find("[type='checkbox']").attr("checked",false);
  				$("[name='addTab']").val("0");
  			 }
    		  
    		  $(".tabContent").val(tabContent);
    		  $("[name='showPosition']").each(function(){
    			  if($(this).val() == tab.showPosition){
    				  $(this).attr("checked","checked");
    				  $(this).trigger("click");
    			  }
    		  })
    		  //字体大小
    		  $("[name='whereFieldValue']").each(function(){
    			  if($(this).val() == tab.tabFontSize){
    				  $(this).attr("selected","selected");
    				  $(this).trigger("click");
    			  }
    		  })
    		  
    		  $("[name='whereiconFieldValue']").each(function(){
    			  if($(this).val() == tab.iconSize){
    				  $(this).attr("selected","selected");
    				  $(this).trigger("click");
    			  }
    		  })
    		  
    		  $("[name='tabStyle']").each(function(){
    			  if($(this).val() == tab.tabType){
    				  $(this).attr("checked","checked");
    				  $(this).trigger("click");
    			  }
    		  })
    		  
    		  $(".appMenu_main_icon i").attr("class","iconfont_nav "+tab.defaultIcon);
    		  $("[name='fdIcon']").val("iconfont_nav "+tab.defaultIcon);
    		  
    		  
    		  //更新色块选择初始颜色
    		  if (window.SpectrumColorPicker) {
                  window.SpectrumColorPicker.setColor("fontColorSelectValue",fontcolor);
                  window.SpectrumColorPicker.setColor("backgroundColorSelectValue",bgcolor);
                  window.SpectrumColorPicker.setColor("fontColorTabSelectValue",fontColorTab);
                  window.SpectrumColorPicker.setColor("backgroundColorTabSelectValue",bgColorTab);
                  window.SpectrumColorPicker.setColor("fontColorItemSelectValue",fontcolorItem);
                  window.SpectrumColorPicker.setColor("backgroundColorItemSelectValue",bgcolorItem);
              }
    		  
    		  //预览区
    		  seajs.use(['lui/jquery','lui/topic'],function($,topic) {
                	topic.publish("modeling.SpectrumColorPicker.change");
              })
		  }
      }
       
    });

    exports.DisplayDialog = DisplayDialog;
});
