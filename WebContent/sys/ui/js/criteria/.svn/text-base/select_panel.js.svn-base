
/**
 * 筛选器基础模块
 */
define(function(require, exports, module) {
	var base = require("lui/base");
	var render = require("lui/view/render");
	var $ = require('lui/jquery');
	
	var selected_values = require('lui/criteria/selected_values');
	var cbase = require('lui/criteria/base');
	var ifDirection = require('lui/spa/directions/ifDirection');
	var SearchBox = require('lui/search_box').SearchBox;
	
	var criterionContainer = cbase.criterionContainer;
	var criterionIsMulti = cbase.criterionIsMulti;
	var NormalSelectedValues = selected_values.NormalSelectedValues;
	var HierarchySelectedValues = selected_values.HierarchySelectedValues;
	var RangeSelectedValues = selected_values.RangeSelectedValues;
	var criteriaParent = cbase.criteriaParent;
	
	var CriterionSelectDatas = base.DataView.extend(ifDirection).extend({
		_className: 'criterion-normal',
		className: "criterion-normal",
		initProps: function($super, cfg) {
			$super(cfg);
			this.selectedValues = null;
			this.multi = cfg.multi || false;
			if (cfg.requried === true) {
				this.multi = false;
			}
			//this.canMulti = cfg.canMulti == 'false' || cfg.canMulti == false ? false : true;
			this.defaultValue = cfg.defaultValue || null;
			this.exclusive = cfg.exclusive || false;
			this.isEdit = false; //是否处于编辑状态(多选模式下有效)
		},
		setSpaShow: function(show){
			this.setEnable(show);
		},
		setEnable: function(enable) {
			this.selectedValues.setEnable( enable );
		},
		isEnable: function() {
			return this.selectedValues.isEnable();
		},
		setRequired: function(req) {
			this.selectedValues.setRequired( req );
		},
		isRequired: function() {
			return this.selectedValues.isRequired();
		},
		addChild : function($super, obj) {
			$super(obj);
			if(this.defaultValue)
				serLayHashInit(this);
			if (obj instanceof SearchBox) {
				this.searchbox = obj;
			}
		},
		supportMulti: function() {
			return this.canMulti;
		},
		buildSelectedValues: function(multi) {
			if (this.selectedValues == null) {
				var arg = this.selectedValueArgs();
				arg['multi'] = multi;
				var sv = new NormalSelectedValues(arg);
				this.setSelectedValues(sv);
			}
		},
		setMulti: function(multi) {
			if (!this.supportMulti()) {
				return;
			}
			this.multi = multi;
			if (!this.multi) {
				if (!this.element.hasClass('radio'))
					this.element.addClass('radio');
			} else {
				this.element.removeClass('radio');
			}
		},
		setSelectedValues: function(sv) {
			if (this.selectedValues) {
				this.selectedValues.destroy();
			}
			this.selectedValues = sv;
			this.selectedValues.startup();
		},
		reloadAll : function(){
			this.isEdit = false;
			this.selectedValues.reloadAll();
		},
		clearAll: function() {
			this.isEdit = false;
			this.selectedValues.removeAll();
		},
		_updateSelectedValuesModel: function(data) {
			//console.info('update model: ', data);
			this.selectedValues.updateModel(data);
		},
		onDataLoad: function($super, data) {
			var parents = this.parent.parent;
			var self = this;
			setTimeout(function(){
				if(parents.expand){
					criteriaParent(self).emit('selectDatasload');
				}
			},1);
			this._updateSelectedValuesModel(data);
			$super(data);
		},
		doRender: function($super, html) {
			var self = this;
			if(this.searchbox){//存在搜索框
				//渲染搜索框
				if(!this.searchbox.isDrawed){
					this.searchbox.draw();
				}
				//覆盖前移出渲染区域
				this.element.after(this.searchbox.element);
				//渲染
				$super(html);
				//覆盖后移回渲染区域
				var allActionContainer = this.element.find('.criterion-all');
				allActionContainer.after($('<li class="criterion-search"/>').append(this.searchbox.element));
			}else{//不存在搜索框
				$super(html);
			}
			//不限选项事件
			this.allActionArea = this.element.find('.criterion-all > a');
			this.allActionArea.bind('click', function(evt) {
				self.clearAll();
				
				// 样式修改
				var parent = $(evt.target);
				while (parent.length > 0) {
					if (parent[0].tagName == 'A') {
						if (!parent.hasClass('selected'))
							parent.addClass('selected');
						break;
					}
					parent = parent.parent();
				}
			});
			if (!this.multi) {
				this.element.addClass('radio');
			}
			if (this.selectedValues.selected()) {
				this.synchSelected();
			}

			/**
			 * 先判断监听“渲染完成”事件是否已经存在<br>
			 * 不存在则监听“绑定“渲染完成”完毕”事件后发出“渲染完成”事件
			 */ 
			
			setTimeout(function(){
				
				var signal = self._signals['rendered'];
				
				if (!signal) {
					self.one('__rendered', function() {
						self.emit('rendered');
					});
				} else {
					self.emit('rendered');
				}
			},1);
			
					
			
		},
		
		destroy: function($super) {
			
			if (this.selectedValues) {
				this.selectedValues.destroy();
			}
			
			$super();
		},
		
		startup: function($super) {
			if (this.isStartup) {
				return;
			}
			if (!this.render) {
				this.setRender(new render.Template({src:require.resolve('./template/criterion-cell.jsp#'), parent: this}));
				this.render.startup();
			}
			if (!this.source) {
				this.setSource(new source.Static({datas: [], parent: this}));
				if(this.source.startup)
					this.source.startup();
			}
			if (!this.selectedValues) {
				this.buildSelectedValues(criterionIsMulti(this));
			} else {
				this.selectedValues.startup();
			}
			var cfg = this.config;
			this.selectedValues.setEnable(cfg.enable == 'false' ? false : true);//  是否有效
			this.selectedValues.setRequired(cfg.required == 'true' ? true : false);// 是否必须选中
			
			this.selectedValues.on('changed', this.onChanged, this);
			this.selectedValues.on('enableChanged', this.onEnableChanged, this);
			this.selectedValues.on('changed', this.emitSelectedChanged, this);
			// 初始化是否支持多选
			this.canMulti = this.setCanMulti();
			$super();
			var parents = this.parent.parent;
			if (this.defaultValue != null || parents.expand) {
				this.parent.parent.immediately = true;
				criteriaParent(this)._appendCriterionCount();
			}
		},
		emitSelectedChanged: function(evt) {
			this.emit('selectedChanged', evt);
		},
		draw: function($super) {
			if(this.isDrawed)
				return;
			$super();
			
			var self = this;
			this.element.unbind('click.criteria.Select');
			this.element.bind('click.criteria.Select', function(evt) {
				self.onSelected(evt);
			});
			this.emit('draw');
		},
		
		setCanMulti : function() {
			if (this.selectedValues.isRequired()) {
				return false;
			}
			var parent = this, canMulti = true;
			while (parent) {
				if (typeof(parent.canMulti) != 'undefined') {
					canMulti = parent.canMulti;
					break;
				}
				parent = parent.parent;
			}
			return canMulti;
		},
		
		_withElement: function(target, attr, fn) {
			var parent = target.html ? target : $(target);
			while (parent.length > 0) {
				if (parent.attr(attr)) {
					fn(parent);
					break;
				}
				parent = parent.parent();
			}
		},
		onSelected: function(evt) {
			var target = evt.target;
			if (target == this.element[0]) {
				return;
			}
			var self = this;
			self._withElement(target, 'data-lui-val', function(valElem) {
				if(self.supportMulti() && self.multi){//多选状态下点选项，置为编辑状态
					self.isEdit = true;
				}
				if (self.multi && !valElem.hasClass('selected')) {
					self.selectedValues.add(valElem.attr('data-lui-val'));
				}
				else if (valElem.hasClass('selected')) {
					self.selectedValues.remove(valElem.attr('data-lui-val'));
				}
				else {
					self.selectedValues.set(valElem.attr('data-lui-val'));
				}
			});
		},
		synchSelected: function() {
			var selectedValues = this.selectedValues;
			var _self = this;
			this.element.find('[data-lui-val]').each(function() {
				var sel = $(this);
				var val = sel.attr('data-lui-val');
				var expect = selectedValues.findFromSelected(val);
				if (expect != null) {
					if (!sel.hasClass('selected'))
						sel.addClass('selected');
					if (!expect.text) { // 顺带更新text
						expect.text = sel.attr('title');
					}
				} else {
					sel.removeClass('selected');
				}
			});
			
			this.switchAllActionAreaSelected();
			
		},
		onEnableChanged: function() {
			var criterion = criterionContainer(this);
			if(criterion.isDrawed){
				if(criterion.parent.expand || this instanceof TabCriterionSelectDatas )
					criterion.show(true);
			} 
		},
		onChanged: function(evt) {
			this.synchSelected();
		},
		selectedValueArgs: function() {
			var arg = selectValueConfig(this);
			arg['parent'] = this;
			arg['selectedValues'] = this.selectedValues;
			arg['conditionName'] = this.config.conditionName;
			return arg;
		},
		load : function($super) {
			if(this.defaultValue){
				if("selectDate"==this.type){
					//criterion_calendar里doRender单独设置
					//this.selectedValues.setAll(this.allDates[this.defaultValue]);
				}else
					this.selectedValues.defaultValueDatas(this.defaultValue);
				var self = this;
				setTimeout(function(){
					criteriaParent(self).emit('selectDatasload');	
				},1);
				
			}
			$super();
		},
		
		addAllActionAreaSelected : function() {
			var self = this;
			if (self.allActionArea && !self.allActionArea.hasClass('selected'))
				self.allActionArea
					.addClass('selected');
		},

		removeAllActionAreaSelected : function() {
			var self = this;
			if (self.allActionArea && self.allActionArea
					.hasClass('selected'))
				self.allActionArea
						.removeClass('selected');
		},
		
		// ”全部“按钮样式修复
		switchAllActionAreaSelected : function() {
			if (this.selectedValues.selected())
				this.removeAllActionAreaSelected();
			else
				this.addAllActionAreaSelected();
		}
	});
	
	var TabCriterionSelectDatas =  CriterionSelectDatas.extend({
		initProps: function($super, cfg) {
			$super(cfg);
			this.multi = false;
			this.criteria_selected = false;
		},
		setCanMulti : function() {
			
			return false;
			
		}
	});
	
	
	var CriterionHierarchyDatas = CriterionSelectDatas.extend({
		_className: "criterion-hierarchy",
		initProps: function($super, cfg) {
			$super(cfg);
			this.multi = false;
			this.lookValue = cfg.lookValue || null;
		},
//		supportMulti: function() {
//			return false;
//		},
		_updateSelectedValuesModel: function(data) {
			// 由selectedValues执行model更新
		},
		load : function() {
			// 第一次加载的时候
			this.element.append(this.loading);
			// 考虑非选中默认值
			if (this.lookValue) {
				this.selectedValues.lookValueDatas({value:this.lookValue});
			} else {
				this.selectedValues.lookValueDatas({});
			}
			if(this.defaultValue)
				this.selectedValues.defaultValueDatas(this.defaultValue);
		},
		onChanged: function(evt) {
			this.switchAllActionAreaSelected();
			if (this.multi) {
				this.synchSelected();
			}
		},
		backToParent: function() {
			// 返回父层的加载
			var pVal = this.selectedValues.model.parent;
			this.selectedValues.lookValueDatas(pVal);
		},
		doRender : function($super, html){
			$super(html);
			this.fillParentInfo();
			this.fillSelectedStatus();
		},
		onSelected: function(evt) {
			var target = evt.target;
			if (target == this.element[0]) {
				return;
			}
			var self = this;
			self._withElement(target, 'data-lui-val', function(valElem) {
				if (self.multi) {
					if(self.supportMulti() && self.multi){//多选状态下点选项，置为编辑状态
						self.isEdit = true;
					}
					if ($(target).hasClass('next')) {
						var cVal = self.selectedValues.findFromModel(valElem.attr('data-lui-val'));
						self.selectedValues.lookValueDatas(cVal);
						return;
					}
					if (valElem.hasClass('selected')) {
						self.selectedValues.remove(valElem.attr('data-lui-val'));
					} else {
						self.selectedValues.add(valElem.attr('data-lui-val'));
					}
				} else {
					self.selectedValues.set(valElem.attr('data-lui-val'), self);
				}
			});
		},
		fillSelectedStatus: function() {
			this.synchSelected();
		},
		fillParentInfo: function() {
			var current = this.selectedValues.model.current;
			var hierarchyElement = this.element.find('.criterion-hierarchy-parent');
			var parentInfoArea = this.element.find('.criterion-hierarchy-parent > a');
			parentInfoArea.off();
			if (current == null) {
				hierarchyElement.hide();
				return;
			}
			var self = this;
			parentInfoArea.bind('click', function(evt) {
				self.backToParent();
			});
			parentInfoArea.attr('title', current.text);
			parentInfoArea.children('.text').text(current.text);
			
			hierarchyElement.show();
		},
		startup: function($super) {
			if (this.isStartup) {
				return;
			}
			if (!this.render) {
				this.setRender(new render.Template({src:require.resolve('./template/criterion-hierarchy-cell.jsp#'), parent:this}));
				this.render.startup();
			}
			if (!this.selectedValues) {
				this.selectedValues = new HierarchySelectedValues(this.selectedValueArgs());
			}
			//criteriaGroup(this).subscribe(CRITERION_CHANGED, this.onSelectedChanged, this);
			$super();
			if (this.source) {
				this.selectedValues.setSource(this.source);
			}
		},
		draw: function($super) {
			if(this.isDrawed)
				return;
			$super();
			var parent = this.element.parent();
			while (parent.length > 0) {
				if (parent.hasClass('criterion-value')) {
					if (!parent.hasClass(this._className))
						parent.addClass(this._className);
					break;
				}
				parent = parent.parent();
			}
		},
		
		onDataLoad : function($super, data) {
			var __data = data.value, __firer = data.firer;
//			var __hasData = __data.length == 0
//					|| (__data.datas && __data.datas.length == 0), __isSelf = __firer
//					&& __firer.cid == this.cid;
//			if (__hasData && __isSelf) {
//				// 刷新无子对象项样式
//				this.refreshSelected();
//				// 父对象取上上级
//				this.selectedValues.upperParent();
//				return;
//			}
			$super(__data);
		},
		
		refreshSelected : function() {
			this.element.find('[data-lui-val]').removeClass('selected');
			var __vals = this.selectedValues.getFireSelectedValues();
			if (__vals.length > 0)
				this.element.find('[data-lui-val="'
						+ __vals[__vals.length - 1].value + '"]')
						.addClass('selected');
		}
	});
	
	var CriterionInputDatas = CriterionSelectDatas.extend({
		_className: 'criterion-input',
		supportMulti: function() {
			return false;
		},
		startup : function($super) {
			if (this.isStartup) {
				return;
			}
			if (!this.selectedValues) {
				var arg = this.selectedValueArgs();
				var sv = new RangeSelectedValues(arg);
				this.setSelectedValues(sv);
			}
			$super();
		},
		_updateSelectedValuesModel: function() {
			// empty
		}
	});
	
	var selectValueConfig = function(component) {
		var criterion = criterionContainer(component);
		if (criterion)
			return {key: criterion.key, title: criterion.title};
		return {};
	};
	
	
	function serLayHashInit(criterionSelectDatas){
		var p = criterionSelectDatas;
		while(p) {
			if (p.serLayHashInit) {
				p.serLayHashInit(true);
				return;
			}
			p = p.parent;
		}
	}
	
	exports.TabCriterionSelectDatas = TabCriterionSelectDatas;
	exports.CriterionSelectDatas = CriterionSelectDatas;
	exports.CriterionHierarchyDatas = CriterionHierarchyDatas;
	exports.CriterionInputDatas = CriterionInputDatas;
});