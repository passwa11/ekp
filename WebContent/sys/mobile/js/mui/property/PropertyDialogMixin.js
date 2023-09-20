define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/html",
		"dojox/mobile/_css3", "dojo/dom-style", "dojo/_base/lang",'dojo/_base/array', "dijit/registry",
		"dojo/topic", "dojo/dom-class", "mui/history/listener", "mui/ChannelMixin" , "./PropertyFilterValuesMixin"], 
		function(declare, domConstruct, html, css3, domStyle, lang, array, registry, topic, domClass, listener, ChannelMixin ,PropertyFilterValuesMixin) {
	var claz = declare("mui.property.PropertyDialogMixin", [ ChannelMixin, PropertyFilterValuesMixin ], {

		modelName: null,

		fdCategoryId: null,

		filterURL: '',
		
		key: '__default__',

		buildRendering: function() {
			this.inherited(arguments);
			this.subscribe('/mui/property/filter/submit', 'submit');
			this.subscribe('/mui/property/filter/reset', 'reset');
			this.subscribe('/mui/property/filter/hide', 'hide');
			this.subscribe('/mui/catefilter/confirm', 'setFdCategoryId');
		},
		
		setFdCategoryId: function(obj, evt){
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			this.fdCategoryId = evt.value.value;
		},

		startup: function() {
			this.inherited(arguments);
		},
		
		reset: function() {
			this.values = this.resetValue(this.values);
			this._values = this.resetValue(this._values);
			
			topic.publish('/mui/form/datetime/change', null, true);
			topic.publish('/mui/property/filter/input/change', true);
			
			// 提交并关闭弹窗
			//this.submit();
			this.publishValues();
		},

		submit: function() {
			var ctx = this;
			this.defer(function() {
				topic.publish('/mui/property/filter', this ,this.ensureValues(this.values));
				ctx.hide();
			}, 1)
			domClass.toggle(this.domNode, 'selected', this.hasValue());
			this.inherited(arguments);
		},

		show: function(evt) {
			this.removeFilter();
			this.openFilter();
		},
		
		removeFilter: function() {
			if(this.parseResults){
				var arrs = this.parseResults;
				for(var i = 0; i < arrs.length; i++) {
					arrs[i].destroy();
				}
			}
			this.dialogDiv && domConstruct.destroy(this.dialogDiv);
			this.dialogDiv = null;
		},

		hide: function() {
			var ctx = this;

			if(!ctx.dialogDiv) {
				return;
			}
			domClass.remove(ctx.dialogDiv, 'muiShow');
			setTimeout(function() {
				ctx.removeFilter();
			}, 300);
			if(this.previousHistoryId){
				listener.go({
        			historyId : this.previousHistoryId,
        			callback: function(){} //空操作,纯粹为了清掉hash
        		})
        		this.previousHistoryId = null;
			}
		},
		
		showFilter: function() {
			this.dialogDiv && domClass.add(this.dialogDiv, 'muiShow');
		},

		openFilter: function() {
			var self = this;
			
			var url = 'mui/property/filter.jsp?modelName=' + this.modelName + '&fdCategoryId=' + this.fdCategoryId;
			if(this.filterURL)
				url = this.filterURL;
			
			var key = this.key;

			require([ 'dojo/text!' + url ], function(tmpl) {

				self.dialogDiv = domConstruct.create("div", {
					className: 'filterlayer_dialog'
				}, document.body, 'last');
				
				var filterlayer_mask = domConstruct.create("div", {
					className: 'filterlayer_mask'
				}, self.dialogDiv);
				
				self.connect(filterlayer_mask, 'onclick', 'hide');
				
				var filterlayer_content = domConstruct.create("div", {
					className: 'filterlayer_content'
				}, self.dialogDiv);

				var buttonDialog = self.buildDialogButton();

				self.dialogDiv.appendChild(buttonDialog)
				
				var staticFilters = self.getStaticFilters();
				tmpl = tmpl.replace('{!staticFilters}', staticFilters);
				
				// 暴力的判断该分类下是否有属性
				var hasProperty = tmpl.indexOf('mui/property/filter') > -1;

				// 如果既没有属性，又没有常用分类，显示无数据
				if(staticFilters == '' && !hasProperty)
					tmpl = self.buildNoDataTmpl();

				// 属性拼出来的html片段不包含key值，导致频道不一致
				if(hasProperty)
					tmpl = tmpl.replace(/!{key}/g, key);
				
				var dhs = new html._ContentSetter({
					node: filterlayer_content,
					parseContent: true,
					cleanContent: true
				});
				dhs.set(tmpl);
				dhs.parseDeferred.then(function(results) {
					
					self.parseResults = results;
					setTimeout(function() {
						self.showFilter();
					}, 50);
				});
				dhs.tearDown();
			});
		},
		
		buildNoDataTmpl: function(){
			var result = '<li class="muiListNoData" style="height: 550px; line-height: 550px;">';
			result += '<div class="muiListNoDataArea">';
			result += '<div class="muiListNoDataInnerArea">';
			result += '<div class="muiListNoDataContainer muiListNoDataIcon">';
			result += '<i class="mui mui-message"></i>';
			result += '</div></div>';
			result += '<div class="muiListNoDataTxt">暂无内容</div>';
			result += '</div></li>';
			
			return result;
		},
		
		/**
		 * 处理需要传递的values
		 * 	1、对于前缀不是q.的筛选项，传递prefix
		 */
		ensureValues: function(values){
			var newValues = lang.clone(values);
			// 从filter中查询对应筛选项的前缀，如果没有默认为q.
			var findPrefix = function(key){
				if(this.filters && this.filters.length > 0){
					for(var i = 0; i < this.filters.length; i++){
						var filer = this.filters[i];
						if(filer.name === key && typeof(filer.prefix) !== 'undefined'){
							return filer.prefix;
						}
						if(filer.name === key){
							break;
						}
					}
				}
				return 'q';
			};
			for(var key in newValues){
				var prefix = findPrefix(key);
				if(prefix !== 'q'){
					var isObject = lang.isObject(newValues[key]) && !lang.isArray(newValues[key]);
					newValues[key] = isObject ? 
							lang.mixin({ prefix:prefix }, newValues[key]) : { prefix: prefix, value: newValues[key] };
				}
			}
			return newValues;
		},
		
		resetValue: function(values){
			var newValues = lang.clone(values);
			for(var key in newValues){
				if(lang.isArray(newValues[key])){
					newValues[key] = [];
				}else if(lang.isObject(newValues[key])){
					newValues[key].value = [];
				}else{
					newValues[key] = '';
				}
			}
			return newValues;
		}
		
	});
	return claz;
});