/**
 * 
 */

define(function(require, exports, module) { 
	var base = require('lui/base');
	var strutil = require('lui/util/str');
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var modelingLang = require("lang!sys-modeling-base");
	var ShuttleBox = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
			// 使用一个随机数当做主表的ID
			this.randomMainId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16) + "_main";
			this.formattedDatas = {
					"details" : {}
			};	// 已经格式化过的元数据 {"fd_xxxx_main":{"label":"","children":[]},"details":{"detailsId":{"label":"","children":[]}}}
	    },
	    
	    draw : function($super, cfg){
	    	// 业务需求，需要把数据按主表和明细表的方式分开
			this.formatDatas(this.source.source.datas);
	    	$super(cfg);
	    },
	    
	    doRender : function($super, cfg){
	    	$super(cfg);
	    	var self = this;
	    	this.tablePathDom = this.element.find(".header-path");
	    	this.toChooseDom = this.element.find(".to-choose-box");
	    	this.selectedDom = this.element.find(".selected-box");
	    	/************** 添加事件 start ******************/
	    	// 表格切换事件
	    	this.element.find("[name='tableSelect']").on("change", function(){
	    		var tableValue = this.value;
	    		var tableInfo = self.findTableInfoByVal(tableValue);
	    		self.toChooseDom.find("[data-to-choose-value]").remove();
	    		self.selectedDom.find("[data-selected-value]").remove();
	    		self.doRenderBoxByTableInfo(tableInfo);
	    	})
	    	
	    	// 左侧待选选中事件,由于是动态数据,使用委托的方式
	    	this.toChooseDom.on("click", "[data-to-choose-value]", function(e){
	    		// 添加校验
	    		if(self.validateOnClick($(this))){
	    			var info = self.findFieldInfoByField($(this).attr("data-to-choose-value"));
	    			info.selected = true;
	    			self.buildSelectedItem(info);
	    			$(this).remove();
	    			topic.channel("modeling-shuttle-box").publish("item.change",{data : self.getStoredDatas2Out(), wgt:self});
	    		}
	    	});
	    	
	    	// 按钮事件
	    	this.element.find(".box-btn-item.add").on("click",function(){
	    		// 全选
	    		self.toChooseDom.find("[data-to-choose-value]").each(function(){
	    			$(this).trigger($.Event("click"));
	    		});
	    		
	    		topic.channel("modeling-shuttle-box").publish("item.change",{data : self.getStoredDatas2Out(), wgt:self});
	    	});
	    	this.element.find(".box-btn-item.reset").on("click",function(){
	    		var tableVal = self.element.find("[name='tableSelect']").val();
	    		self.resetTable(tableVal);
	    		topic.channel("modeling-shuttle-box").publish("item.change",{data : self.getStoredDatas2Out(), wgt:self});
	    	});
	    	
	    	// 右侧目标列表删除事件
	    	this.selectedDom.on("click", ".box-item-close", function(){
	    		var $dom = $(this).closest(".box-item");
	    		var info = self.findFieldInfoByField($dom.attr("data-selected-value"));
    			info.selected = false;
    			self.buildToChooseItem(info);
    			$dom.remove();
    			topic.channel("modeling-shuttle-box").publish("item.change",{data : self.getStoredDatas2Out(), wgt:self});
	    	});
	    	/************** 添加事件 end ******************/
	    	this.element.find("[name='tableSelect']").trigger($.Event("change"));
	    },
	    
	    resetTable : function(tableVal){
    		this.clearSelectedTable(tableVal);	// 清空已选值
    		this.element.find("[name='tableSelect']").trigger($.Event("change"));
	    },
	    
	    validateOnClick : function(dom){
	    	var pass = true;
	    	var fieldId = dom.attr("data-to-choose-value");
	    	// 明细表不能含有多于1个的选项
	    	if(fieldId.indexOf(".") > -1){
	    		var selectedNum = this.selectedDom.find("[data-selected-value]").length;
	    		if(selectedNum > 0){
	    			dialog.alert(modelingLang['modeling.dataValidate.OnlyChooseOne']);
	    			pass = false;
	    		}
	    	}
	    	return pass;
	    },
	    
	    /************** 渲染相关函数 start *******************/
	    clearSelectedTable : function(tableVal){
	    	this.selectedDom.find("[data-selected-value]").remove();
    		var tableInfo = this.findTableInfoByVal(tableVal);
	    	for(var key in tableInfo.children){
	    		tableInfo.children[key].selected = false;
	    	}
	    },
	    
	    doRenderBoxByTableInfo : function(tableInfo){
	    	// 更新表名
	    	this.tablePathDom.html(tableInfo.label);
	    	
	    	// 更新待选项
	    	var children = tableInfo.children;
	    	for(var key in children){
	    		var item = children[key];
	    		if(!item.selected){
	    			this.buildToChooseItem(item);	    			
	    		}
	    	}
	    	
	    	// 更新目标列表
	    	for(var key in children){
	    		var item = children[key];
	    		if(item.selected){
	    			this.buildSelectedItem(item);	    			
	    		}
	    	}
	    },
	    
	    // 创建待选的选项
	    buildToChooseItem : function(item){
	    	var html = "";
	    	html += "<div class='box-item";
	    	if(item.selected){
	    		html += " active";
	    	}
	    	html += "' data-to-choose-value='"+ item.name +"'>";
	    	html += "<div class='box-item-checkbox'></div>";
	    	html += "<div class='box-item-txt'><span>"+ item.label +"</span></div>";
	    	html += "</div>";
	    	this.toChooseDom.find(".box-content").append(html);
	    },
	    
	    // 创建已选的选项
	    buildSelectedItem : function(item){
	    	var html = "";
	    	html += "<div class='box-item' data-selected-value='"+ item.name +"'>";
	    	html += "<div class='box-item-txt'><span>"+ item.label +"</span></div>";
	    	html += "<div class='box-item-close'>"+modelingLang['modelingTransport.button.delete']+"</div>";
	    	html += "</div>";
	    	this.selectedDom.find(".box-content").append(html);
	    },
	    
	    /************** 渲染相关函数 end *******************/
	    
	    // 根据表格的值，查找表格信息
	    findTableInfoByVal : function(val){
	    	if(val === this.randomMainId){
	    		return this.formattedDatas[this.randomMainId];
	    	}
	    	var details = this.formattedDatas["details"];
	    	return details[val];
	    },
	    
	    // 根据fieldId查找对应的选项信息
	    findFieldInfoByField : function(fieldId){
	    	var rs = null;
	    	// 如果是明细表，则只会存在details
	    	if(fieldId.indexOf(".") > 1){
	    		// 明细表id.控件ID
	    		var arr = fieldId.split(".");
	    		rs = this.formattedDatas["details"][arr[0]]["children"][arr[1]];
	    	}else{
	    		rs = this.formattedDatas[this.randomMainId]["children"][fieldId];
	    	}
	    	return rs;
	    },
	    
	    /**************** 初始化数据 start **********************/
	    // 格式化数据，把主表数据和明细表数据区分开
	    formatDatas : function(sourceDatas){
	    	var allDatas = sourceDatas.allDatas.data || [];
	    	for(var i = 0;i < allDatas.length;i++){
	    		var info = allDatas[i];
	    		if(info.name){
	    			if(this.isControlValid(info)){
	    				// 仅做一层明细表的判断，不支持明细表再套明细表
		    			if(info.name.indexOf(".") > -1){
		    				var names = info.name.split(".");
		    				var labels = info.label.split(".");
		    				// 明细表不需要前面的明细表文本
		    				info.label = labels[1];
		    				info.fullLabel = labels.join(".");
		    				this.putValToDatas(names[0], labels[0], info, this.formattedDatas["details"]);
		    			}else{
		    				this.putValToDatas(this.randomMainId, modelingLang['modeling.dataValidate.MainTable'], info, this.formattedDatas);
		    			}
	    			}
	    		}
	    	}
	    	if(!$.isEmptyObject(sourceDatas.storedDatas)){
	    		this.mergeStoredDatas(this.formattedDatas, sourceDatas.storedDatas);
	    	}
	    	topic.channel("modeling-shuttle-box").publish("item.change",{data : this.getStoredDatas2Out(), wgt:this});
	    },
	    
	    isControlValid : function(info){
	    	var isValid = true;
	    	// 不需要的控件
	    	var unPassControlType = ["detailsTable", "massData"];
			if(info.controlType && $.inArray(info.controlType, unPassControlType) > -1){
				isValid = false;
			}
			
			// 附件不需要
			if(info.type && info.type === "Attachment"){
				isValid = false;
			}
			return isValid;
	    },
	    
	    putValToDatas : function(name, label, info, datas){
	    	var tableInfo = null;
	    	if(datas.hasOwnProperty(name)){
	    		tableInfo = datas[name];
	    	}else{
	    		tableInfo = {
	    			"label" : label,
	    			"children" : {}
	    		};
	    		datas[name] = tableInfo;
	    	}
	    	var names = info.name.split(".");
	    	tableInfo.children[names[names.length-1]] = info;
	    	tableInfo.children[names[names.length-1]].selected = false;
	    },
	    
	    // 合并数据
	    // source:{"mainModel":[],"xformStandard":[],"xformDetails":[]}
	    mergeStoredDatas : function(target, source){
	    	var addSelected = function(data, arr){
	    		for(var i = 0;i < arr.length;i++){
	    			var field = arr[i].field;
	    			if(field.indexOf(".") > -1){
	    				var fields = field.split(".");
	    				if(data.hasOwnProperty(fields[0])){
	    					var children = data[fields[0]].children || {};
	    					if(children.hasOwnProperty(fields[1])){
	    	    				children[fields[1]].selected = true;
	    	    			}else{
								continue;
	    	    				// console.warn("明细表("+fields[0] +")里面的字段"+fields[1]+"已被删除");
	    	    			}
	    				}
	    			}else{
	    				var children = data.children || {};
		    			if(children.hasOwnProperty(field)){
		    				children[field].selected = true;
		    			}else{
							continue;
		    				// console.warn("主表里面的字段"+fields[1]+"已被删除");
		    			}
	    			}
	    		}
	    	}
	    	addSelected(target[this.randomMainId], source["mainModel"]);
	    	addSelected(target[this.randomMainId], source["xformStandard"]);
	    	addSelected(target["details"], source["xformDetails"]);
	    },
	    /**************** 初始化数据 end **********************/
	    
	    // 是否没有选择数据
	    isEmpty : function(){
	    	var isEmp = false;
	    	var keyData = this.getKeyData();
	    	if(keyData.mainModel.length === 0 && keyData.xformStandard.length === 0 && keyData.xformDetails.length === 0){
	    		isEmp = true;
	    	}
	    	return isEmp;
	    },
	    
	    // 对外提供的数据
	    getStoredDatas2Out : function(){
	    	var setValue = function(target, source){
	    		for(var key in source){
	    			var info = source[key];
	    			if(info.selected){
	    				target.push({
		    				name : info.fullLabel || info.label,
		    				value : info.name
		    			});
	    			}
	    		}
	    	}
	    	var rs = [];
	    	var data = this.getSourceData();
	    	setValue.call(this, rs, data[this.randomMainId].children);
	    	var detailsInfo = data["details"];
	    	for(var key in detailsInfo){
	    		setValue.call(this, rs, data["details"][key].children);	    		
	    	}
	    	return rs;
	    },
	    
	    getSourceData : function(){
	    	return this.formattedDatas;
	    },
	    
	    getKeyData : function(){
	    	var keyData = {
	    		"mainModel" : [],
	    		"xformStandard" : [],
	    		"xformDetails" : []
	    	}
	    	var formattedDatas = this.formattedDatas;
	    	for(var key in formattedDatas[this.randomMainId].children){
	    		var controlInfo = formattedDatas[this.randomMainId].children[key];
	    		if(controlInfo.selected){
	    			if(controlInfo.sourceType && controlInfo.sourceType === "mainModel"){
		    			keyData["mainModel"].push({
		    				field : controlInfo.name
		    			});
		    		}else{
		    			keyData["xformStandard"].push({
		    				field : controlInfo.name
		    			});
		    		}	    			
	    		}
	    	}
	    	for(var key in formattedDatas["details"]){
	    		for(var controlId in formattedDatas["details"][key].children){
	    			var controlInfo = formattedDatas["details"][key].children[controlId];
	    			if(controlInfo.selected){
	    				keyData["xformDetails"].push({
		    				field : controlInfo.name
		    			});	    				
	    			}
	    		}
	    	}
	    	return keyData;
	    },
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	var TextShowWgt = base.Component.extend({
		
		startup : function($super, cfg){
			$super(cfg);
			topic.channel("modeling-shuttle-box").subscribe("item.change",this.updateTxt,this);
		},
		
	    draw : function($super, cfg){
	    	$super(cfg);
	    	this.txtDom = $("<div class='txt-wrap'/>").appendTo(this.element);
	    },
		
		// {data:[{name:xx,value:xxx}], wgt:this}
		updateTxt : function(argu){
			if(argu.data.length > 0){
				var html = [];
				for(var i = 0;i < argu.data.length;i++){
					html.push("<span>"+ argu.data[i].name +"</span>");
				}
				this.txtDom.html(html.join("+"));
			}else{
				this.drawNoRecord();
			}
		},
		
		drawNoRecord : function(){
			this.txtDom.html("<span class='txt-no-record'>"+modelingLang['modeling.dataValidate.PleaseSelectBelow']+"</span>");
		},
	});
	
	exports.ShuttleBox = ShuttleBox;
	exports.TextShowWgt = TextShowWgt;
})