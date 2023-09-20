/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		langView = require('./langView');
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	var modelingLang = require("lang!sys-modeling-base");
	var LangViewContainer = base.Container.extend({

		initProps: function($super, cfg) {
			$super(cfg);
			this.curView = null;
			this.view = {};
			this.convertData();
			this.isSetIFrameHeight = false;
		},
		
		draw : function($super, cfg){
			$super(cfg);
			var langEnv = this.config.vars.langEnv;
			for(var key in langEnv.lang){
				if(key === langEnv.curLang){
					continue;
				}
				$(".lang-head").css("display","block");
				this.view[key] = this.createView(key, langEnv.curLang, langEnv.officialLang);
			}
			//#120416：多语言配置只配置了一个值，并且和官方语言相同，直接continue了
			if($.isEmptyObject(this.view) == true){
				var html = "";
				html += '<div style="margin-top:20%;"><center>';
				html += '<div>';
				html += '	<i class="td_normal_blank"></i>';
				html += '    <div style="margin:10px;">'+modelingLang['lang.config.multilingual.function']+'</div>';
				html += '</div>';
				html += '</center></div>';
				$(html).appendTo(this.element);
			}
			//更新父窗口的高度
			$("body",parent.document).find('#cfg_iframe').animate({
            	height : "570px"
            },"fast");
		},
		
		createView : function(key, curLang, officialLang){
			var viewElement = $("<div class='lang-view-"+ key +"' style='display:none;'/>").appendTo(this.element);
			var langViewWgt = new langView.LangView({
				element : viewElement,
				vars : {
					key : key,
					curLang : curLang,
					officialLang : officialLang,
					storedDatas : this.config.vars.storedDatas
				},
				parent : this
			});
			langViewWgt.startup();
			langViewWgt.draw();
			return langViewWgt;
		},
		
		// 对数据源数据进行转换，主要是为了处理数组的情况
		convertData : function(){
			var data = this.config.vars.storedDatas;
			if(data && data.length){
				for(var i = 0;i < data.length;i++){
					var controlInfo = data[i];
					// 把c_option的子对象对象从字符串转换为数组
					for(var key in controlInfo.c_option){
						var optionInfo = controlInfo.c_option[key];
						for(var langKey in optionInfo){
							var langVals = optionInfo[langKey].split(/\r\n|\n/);
							optionInfo[langKey] = langVals;
						}
					}
				}
			}
			return data;
		},
		
		// 还原数据
		restoreData : function(data){
			if(data && data.length){
				for(var i = 0;i < data.length;i++){
					var controlInfo = data[i];
					// 把c_option的子对象对象从数组转换为字符串
					for(var key in controlInfo.c_option){
						var optionInfo = controlInfo.c_option[key];
						for(var langKey in optionInfo){
							optionInfo[langKey] = optionInfo[langKey].join("\n");
						}
					}
				}
			}
			return data;
		},
		
		switchViewByKey : function(key){
			if(this.curView){
				if(this.curView === this.view[key]){
					return;
				}
				this.curView.hide();
			}
			this.curView = this.view[key];
			var self = this;
			this.view[key].show(function(){
				if(self.isSetIFrameHeight){
					return;
				}
				self.updateFrameHeight();
				self.isSetIFrameHeight = true;
			});
		},
		
		getKeyData : function(){
			var datas = this.config.vars.storedDatas;
			for(var viewKey in this.view){
				this.view[viewKey].collectVal2KeyData(datas);
			}
			// 还原数据
			this.restoreData(datas);
			return datas;
		},
		
		// 外层窗口设计：需要子窗口自己更新父窗口的高度
		updateFrameHeight : function(){
			var bodyHeight = ($(".lang").height()) + 20;
            $("body",parent.document).find('#cfg_iframe').animate({
            	height : bodyHeight
            },"fast");
		}
	});
	
	exports.LangViewContainer = LangViewContainer;
})