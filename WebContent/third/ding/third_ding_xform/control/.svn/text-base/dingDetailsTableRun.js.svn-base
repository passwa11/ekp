/**
 * 钉钉明细表
 * 
 */
define(function(require, exports, module) {
	require("third/ding/third_ding_xform/control/css/dingDetailsTable.css");
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var topic = require("lui/topic");
	var strUtil = require("lui/util/str");
	
	var DingDetailsTableRun = base.Component.extend({
		
		rowContainerClass : "ding_detailstable_content",
		
		rowClass : "ding_detailstable_row",
		
		delBtnClass : "ding_detailstable_del",
		
		addBtnClass : "ding_detailstable_add",
		
		rowContainer : null,
		
		_init: function($super,cfg) {
			cfg.keyName = XForm_GeyKeyName(cfg.name);
			cfg.id = cfg.keyName;
			cfg.tableId = "TABLE_DL_" + cfg.keyName;
			$super(cfg);
		},
		
		initialize : function($super,cfg){
			$super(cfg);
			var self = this;
			// 设置行容器
			this.rowContainer = this.element.find("." + this.rowContainerClass);
			this.config.vars.subject = this.config.vars.subject || "明细";
			this.rowContainer.find("[kmss_isreferrow='1']").hide();
		},
		
		startup : function($super,cfg){
			$super(cfg);
			var self = this;
			switch(this.config.showStatus){
				case "edit":
					this.buildEdit();
					break;
				case "view":
					this.buildView();
					break;
			}
			console.log(this);
		},
		
		buildEdit : function(){
			var self = this;
			// 如果明细表还未初始化，则监听初始化事件
			if(DocList_TableInfo && DocList_TableInfo.length > 0){
				this.doInitInEdit();
			}else{
				// 更新标题不能等待明细表初始化完之后做，不然会出现文字闪烁的情况
				var $rows = this.element.find("." + this.rowClass);
				if($rows.length > 0){
					this.updateAllRowDescTxt();
				}
				$(document).on("detaillist-init","table[id*='"+ this.config.keyName +"']",function(){
					self.doInitInEdit();
				})						
			}
			/************* 添加事件 start *****************/
			// 以委托的方式设置删除功能
			this.rowContainer.on("click", "." + this.delBtnClass, function(){
				self.delRow(this);
			});
			// 添加新增行功能
			var $addBtn = this.element.find("." + this.addBtnClass);
			$addBtn.on("click",function(){
				self.createRow();
			});
			$addBtn.html("添加" + this.config.vars.subject);
			/************* 添加事件 end *****************/
		},
		
		doInitInEdit : function(){
			// 编辑状态，如果一行都没有，则默认添加一行
			var $rows = this.element.find("." + this.rowClass);
			if($rows.length === 0){
				this.createRow();
			}
		},
		
		// 通过样式来隐藏删除按钮和新增按钮
		buildView : function(){
			this.rowContainer.parent().addClass("view");
			this.updateAllRowDescTxt();
		},
		
		draw : function($super,cfg){
			// 发布【show】事件，用于附件的渲染；附件会在父组件发布【show】事件时渲染
			this.emit("show");
			$super(cfg);
		},

		// 更新所有行的行标题
		updateAllRowDescTxt : function(){
			var $needToUpdateIndexRows = this.rowContainer.find("[kmss_iscontentrow='1']");
			for(var i = 0;i < $needToUpdateIndexRows.length;i++){
				var needToUpdateIndexRow = $needToUpdateIndexRows[i];
				this.updateRowDescTxt($(needToUpdateIndexRow), i + 1);
			}
		},
		
		// 新增行
		createRow : function(){
			var newRow = DocList_AddRow(this.config.tableId);
			this.updateRowDescTxt($(newRow), $(newRow).index() + 1);
			this.delBtnDisplay();
		},
		
		// 更新行标题描述
		updateRowDescTxt : function($newRow, index){
			var $txt = $newRow.find(".ding_detailstable_desc_txt");
			$txt.html(this.config.vars.subject + "("+ index +")");
		},
		
		// 理论上直接调用明细表的删除行方法即可，但是日期控件有兼容问题，需要特殊处理索引的更新
		delRow : function(dom){
			var $removedRow = $(dom).closest("tr");
			var $needToUpdateIndexRows = $removedRow.nextAll();
			DocList_DeleteRow($removedRow[0]);
			for(var i = 0;i < $needToUpdateIndexRows.length;i++){
				var needToUpdateIndexRow = $needToUpdateIndexRows[i];
				var index = $(needToUpdateIndexRow).index();
				this.refreshDateTimeIndex($(needToUpdateIndexRow), index);
				this.updateRowDescTxt($(needToUpdateIndexRow), index + 1);
			}
			this.delBtnDisplay();
		},
		
		// 第一行明细表行不需要删除按钮
		delBtnDisplay : function(){
			var $rows = this.rowContainer.find(".ding_detailstable_row");
			if($rows.length === 1){
				$rows.find(".ding_detailstable_del").hide();
			}else{
				$($rows[0]).find(".ding_detailstable_del").show();
			}
		},
		
		//================================= 以下为仿系统明细表的更新索引的代码
		// 更新日期控件的索引
		refreshDateTimeIndex : function($row, index){
			var $dateTime = $row.find("xformflag[_xform_type='datetime']");
			for(var i = 0;i < $dateTime.length;i++){
				this.refreshAttrIndex($($dateTime[i]).find(".inputselectsgl"), "onclick", index);
			}
		},
		
		refreshAttrIndex : function(element, attrName, index){
			var _attNameValue = $(element).attr(attrName);
			var fieldName = _attNameValue.replace(/\[\d+\]/g, "[!{index}]");
			fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
			fieldName = this.replaceIndex(fieldName, index);
			$(element).attr(attrName,fieldName);
		},
		
		// 替换索引
		replaceIndex : function(str, index){
			return str.replace(/!\{index\}/g, index);
		}
		
	});
	
	exports.DingDetailsTableRun = DingDetailsTableRun;
})