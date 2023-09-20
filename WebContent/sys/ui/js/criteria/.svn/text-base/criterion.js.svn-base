define(function(require, exports, module) {

	var base = require("lui/base");
	var element = require("lui/element");
	var $ = require('lui/jquery');
	var select = require('lui/criteria/select_panel');
	var lang = require('lang!sys-ui');
	var cbase = require('./base');
	
	var criteriaGroup = cbase.criteriaGroup;
	var CRITERION_CHANGED = cbase.CRITERION_CHANGED;
	
	var Criterion = base.Container.extend({
		className: 'criterion',
		initProps: function($super, cfg) {
			$super(cfg);
			this.criterions = [];
			this.title = cfg.title;
			this.canMulti = cfg.canMulti == 'false' || cfg.canMulti == false ? false : true;
			this.multi = cfg.multi || false;
			this.key = cfg.key;
			this.expand = cfg.expand || false;
		},
		supportMulti: function() {
			return this.selectBox && this.selectBox.supportMulti();
		},
		setMulti: function(multi) {
			this.multi = multi;
			this.selectBox.setMulti(multi);
		},
		isEnable: function() {
			if (!this.selectBox) {
				return true;
			}
			if(!this.selectBox.isEnable)
				return true;
			return this.selectBox.isEnable();
		},
		show: function(show, animate) {
			if (!this.isEnable()) {
				this.element.hide();
				return;
			}
			if (show) {
				if (animate) {
					this.element.slideDown();
				} else {
					this.element.show();
				}
			} else {
				if (animate) {
					this.element.slideUp();
				} else {
					this.element.hide();
				}
			}
		},
		doLayout: function() {
			this.titleBox.setParentNode(this.element);
			this.selectBox.setParentNode(this.element);
			
			this.titleBox.draw();
			this.selectBox.draw();
			if (this.expand) {
				this.show(true, false);
			}
		},
		addChild: function($super, child, attr) {
			$super(child, attr);
			if (attr == null) {
				if (child instanceof CriterionTitleBox) {
					this.setTitleBox(child);
				}
				if (child instanceof CriterionSelectBox) {
					this.setSelectBox(child);
				}
			}
		},
		setSelectBox: function(box) {
			this.selectBox = box;
		},
		setTitleBox: function(box) {
			this.titleBox = box;
		},
		startup: function($super) {
			if (this.isStartup) {
				return;
			}
			if (!this.titleBox) {
				this.titleBox = new CriterionTitleBox({parent:this});
				this._startupChild(this.titleBox);
			}
			if (!this.selectBox) {
				this.selectBox = new CriterionSelectBox({parent:this});
				this._startupChild(this.selectBox);
			}
			$super();
			return this;
		},
		draw: function() {
			if(this.isDrawed){
				this.fire({"name":"isDrawed"});
				return;
			}
			this.doLayout();
			this.isDrawed = true;
		}
	});
	
	var TabCriterion = Criterion.extend({
		doLayout: function($super) {
			$super();
			this.element.addClass('tab-criterion');
		}
	});
	
	var CriterionTitleBox = base.Container.extend({
		initProps: function($super, cfg) {
			$super(cfg);
			this.title = cfg.title;
		},
		className: 'criterion-title',
		startup: function() {
			if (this.isStartup) {
				return;
			}
			if (this.children.length == 0 
					&& this.element.children().length == 0 
					&& $.trim(this.element.text()) == '') {
				var text = new element.Text({text: this.title || this.parent.title, parent: this, parentNode: this.element});
				this.addChild(text);
			}
			this.isStartup = true;
		},
		draw :function() {
			if(this.isDrawed)
				return this;

			for (var i = 0; i < this.children.length; i++) {
				if(this.children[i].draw) {
					this.children[i].draw();
				}
			}
			this.element.show();
			this.isDrawed = true;
			return this;
		}
	});
	
	var CriterionSelectBox = base.Container.extend({
		className: "criterion-value",
		initProps: function($super, cfg) {
			$super(cfg);
			this.criterionSelectElement = null;
			this.multi = cfg.multi || false;
			//this.initCriterionAreas();
		},
		initCriterionAreas: function() {
			this.optionsArea = $("<div class=\"criterion-options\"></div>").appendTo(this.element);
			this.collapseArea = $("<ul class=\"criterion-collapse\"></ul>").appendTo(this.element);
			this.buttonArea = $("<ul class=\"criterion-button\"></ul>").appendTo(this.element);
			this.max_height = this.collapseArea.css('max-height');
			this.collapseArea.css('max-height','none');
		},
		addChild: function($super, child, attr) {
			$super(child, attr);
			if (attr == null) {
				if (child instanceof select.CriterionSelectDatas) {
					this.setCriterionSelectElement(child);
				}
			}
		},
		setCriterionSelectElement: function(elem) {
			this.criterionSelectElement = elem;
			if("criterion-calendar"==(this.criterionSelectElement.config.element.className)){
				if(typeof(this.element[0])!="undefind"||this.element[0]!=null){
					this.element[0].className="criterion-value";
				}
				if(typeof(this.element.context)!="undefind"||this.element.context!=null){
					this.element.context.className="criterion-value";
				}
			}

		},
		supportMulti: function() {
			return this.criterionSelectElement.supportMulti();
		},
		setMulti: function(multi) {
			if(this.multi == multi){
				return;
			}
			this.multi = multi;
			if(this.multiOption){
				var multiOption = this.multiOption;
				if (multi) {
					multiOption.addClass('selected');
					multiOption.find(".multi-text").text(lang['ui.criteria.single']);
					multiOption.find(".multi-radio").text(lang['ui.criteria.multi.text']);
					multiOption.find(".multi-checkbox").text(lang['ui.criteria.single.text']);
					multiOption.attr('title', lang['ui.criteria.single']);
				}else{
					multiOption.removeClass('selected');
					multiOption.find(".multi-text").text(lang['ui.criteria.multi']);
					multiOption.find(".multi-radio").text(lang['ui.criteria.single.text']);
					multiOption.find(".multi-checkbox").text(lang['ui.criteria.multi.text']);
					multiOption.attr('title', lang['ui.criteria.multi']);
				}
			}
			this.criterionSelectElement.setMulti(multi);
			this.criterionSelectElement.clearAll();
		},
		isEnable: function() {
			return this.criterionSelectElement.isEnable();
		},
		startup: function() {
			if (this.isStartup) {
				return;
			}
			this._startupChild(this.criterionSelectElement);
			criteriaGroup(this).subscribe(CRITERION_CHANGED, this.onSelectedChanged, this);
			this.isStartup = true;
		},
		draw :function($super){
			if(this.isDrawed)
				return;
			// 构建更多按钮
			this.buildMore();
			this.initCriterionAreas();
			$super();
			if (this.criterionSelectElement) {
				this.criterionSelectElement.setParentNode(this.collapseArea);
				this.criterionSelectElement.draw();
			}
			if(this.supportMulti()){
				this.multiAreaDraw();
			}
			this.element.show();
		},
		onSelectedChanged : function(val){
			function findCriterion(component){
				var parent = component.parent;
				while(parent){
					if(parent.className == 'criterion'){
						return parent;
					}
					parent = parent.parent;
				}
				return null;
			}
			if(findCriterion(val.src) == this.parent){
				if(val.immediateFire){
					this.hideButtonArea();
				}else{
					this.showButtonArea();
				}
			}
		},
		multiAreaDraw : function(){
			var self = this;
			
			//单多选切换按钮绘制
			var optionArea = this.optionsArea,
				multiOption = this.multiOption = $("<a class=\"multi-option\"/>").attr('title',lang['ui.criteria.multi']);
			$("<span class=\"multi-radio\"></span>").text( lang['ui.criteria.single.text'] ).appendTo(multiOption);
			$("<span class=\"multi-checkbox\"></span>").text( lang['ui.criteria.multi.text'] ).appendTo(multiOption);
			$("<span class=\"multi-text\"></span>").text( lang['ui.criteria.multi'] ).appendTo(multiOption);
			
			//单多选按钮切换操作
			multiOption.on('click',$.proxy(function(){
				if(multiOption.hasClass('selected')){
					this.setMulti(false);
				}else{
					this.setMulti(true);
				}
			},this)).appendTo(optionArea);
			
			//确定、取消按钮绘制
			var buttonArea = this.buttonArea,
				okButton = $("<li class=\"ok-action\"/>")
							.attr('title',lang['ui.dialog.button.ok'])
							.append($('<a href=\"javascript:;\"/>').text(lang['ui.dialog.button.ok'])),
				cancelButton =  $("<li class=\"cancel-action\"/>")
							.attr('title',lang['ui.dialog.button.cancel'])
							.append($('<a href=\"javascript:;\"/>').text(lang['ui.dialog.button.cancel']));
			
			//确定按钮操作
			okButton.on('click',$.proxy(function(){
				this.criterionSelectElement.reloadAll();
				var criteria = (function(){
					var parent = self.parent;
					while(parent){
						if(parent.className === 'criteria'){
							return parent;
						}
						parent = parent.parent;
					}
					return null;
				})();
				if(criteria){
					criteria.fireCriteriaChanged();
				}
			},this)).appendTo(buttonArea);
			
			//取消按钮操作
			cancelButton.on('click',$.proxy(function(){
				this.criterionSelectElement.clearAll();
			},this)).appendTo(buttonArea);
			
			setTimeout($.proxy(function(){
				this.hideButtonArea();
			},this),1);
		},
		
		//显示`确定/取消`按钮区域
		showButtonArea : function(){
			var criterionNode = this.parent.element,
				buttonArea = this.buttonArea
			criterionNode && criterionNode.addClass('editing');
			buttonArea && buttonArea.show();
		},
		
		//隐藏`确定/取消`按钮区域
		hideButtonArea : function(){
			var criterionNode = this.parent.element,
				buttonArea = this.buttonArea
			criterionNode && criterionNode.removeClass('editing');
			buttonArea && buttonArea.hide();
		},
		 
		// 重置更多按钮
		resetMore : function() {
			this.toggleHadMoreClass();
			this.resetMoreArrowClass();
		},
		
		// 需要显示更多按钮class
		criterionHadMore : 'criterion-had-more',
		
		arrowClass : 'criterion-more-up',
		
		// 是否显示更多按钮
		toggleHadMoreClass : function() {
			var self = this;
			setTimeout(function() {
				self.collapseArea.css({ 'max-height' : 'none'});
				self.orign_height = self.collapseArea.height();
				if(!self.element)
					return;
				if (self.orign_height > parseInt(self.max_height)){
					self.element.addClass(self.criterionHadMore);
					self.collapseArea.css({ 'max-height' : self.max_height});
				}else{
					self.element.removeClass(self.criterionHadMore);
				}
			}, 201);
		},
		
		// 切换更多按钮样式
		resetMoreArrowClass : function() {
			this.moreArrow.removeClass(this.arrowClass);
			this.isMore = false;
		},
		
		// 构建更多按钮
		buildMore : function() {
			this.moreArea = $("<div class=\"criterion-more fix_82123\"></div>").appendTo(this.element);
			this.moreArrow = $("<div class=\"criterion-more-down\" data-prefix=\""+lang['layout.tabpanel.collapse.more']+"\" data-suffix=\""+lang['ui.tabPage.collapsed']+"\"></div>").appendTo(this.moreArea);
			var self = this;
			// 监听已加载展开全部条件时事件
			this.parent.on('isDrawed',function(){
				self.resetMore();
			});
			// 监听渲染完后事件，用于判定是否出现更多按钮&还原最大高度
			this.criterionSelectElement.on('load', function() {
				self.resetMore();
			});
			this.bindMore();
		},
		
		// 绑定更多按钮点击事件
		bindMore : function() {
			var self = this;
			this.moreArea.on('click', function(evt) {
						var maxheight = 'none';
						if (self.isMore) {
							maxheight = self.max_height;
							self.isMore = false;
							self.moreArrow.removeClass(self.arrowClass);
						} else {
							maxheight = 'none';
							self.isMore = true;
							self.moreArrow.addClass(self.arrowClass);
						}
						self.collapseArea.css({
									'max-height' : maxheight
								});
					});
		}
	});
	
	exports.Criterion = Criterion;
	exports.TabCriterion = TabCriterion;
	exports.CriterionTitleBox = CriterionTitleBox;
	exports.CriterionSelectBox = CriterionSelectBox;
});