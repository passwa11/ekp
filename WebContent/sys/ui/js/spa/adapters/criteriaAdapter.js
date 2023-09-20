/**
 * 筛选器支持单页面适配器
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	var cbase = require('lui/criteria/base');
	var Spa = require('lui/spa/Spa');
	var hash = require('lui/spa/router/hash');
	
	var criteriaGroup = cbase.criteriaGroup;
	var CRITERIA_SPA_CHANGED = cbase.CRITERIA_SPA_CHANGED;

	var criteriaAdapter = {

		spaLock : false,

		// 是否已经初始化
		spaInit : false,

		// 当前已选数据包
		spaEvt : {},

		initProps : function($super, cfg) {

			this.spa = env.fn.getConfig().isSpa;

			$super(cfg);

		},

		startup : function($super) {

			$super();

			/**
			 * 初始化筛选器<br>
			 * 监听事件后进行筛选器初始化和数据改变
			 */

			if (!this.spa)
				return;

			topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.spaCriteriaValues,
					this);

		},

		/**
		 * 禁止筛选器自己hash解析<br>
		 * 单页面模式全部由spa的router进行初始化
		 */
		_parseHashUrl : function($super) {

			if (this.spa) {

				this.spaInit = true;
				this.spaCriteriaValues(this.spaEvt);

				return;
			}

			$super();
		},

		/**
		 * 默认值监控
		 */
		_appendDefault : function($super, evt) {

			if (!this.spa) {

				$super(evt);
				return;
			}

			var key = encodeURIComponent(evt.key);
			var values = evt.values;

			var vals = [];

			for (var i = 0; i < values.length; i++) {
				if(Object.isArray(values[i].value)){
					for(var j = 0; j < values[i].value.length; j++){
						vals.push(key + ":" + values[i].value[j]);
					}
				}else{
					vals.push(key + ":" + values[i].value);
				}
				
			}
			
			/**
			 * #67599 修复 选择筛选项后刷新页面，之前选择的筛选项数据未被保留
			 * 暂定方案：
			 * 因为筛选器初始值设置涉及到多个逻辑异步，因此此处直接取浏览器筛选值当作默认值
			 * 若浏览器hash默认值为空则取spa缓存值
			 */
			var currentHash = hash.get();
			var hashStr = currentHash[this._hashCriterionKey()];
			if(!hashStr) {
				hashStr = decodeURIComponent(Spa.spa.getValue(this
						._hashCriterionKey()));
			} else {
				Spa.spa.setValue(this._hashCriterionKey(), hashStr);
			}

			var hashArray = hashStr ? hashStr.split(';') : [];

			for (var i = 0; i < hashArray.length; i++) {

				var p = hashArray[i].split(':');

				if (p[0] != key) {
					vals.push(hashArray[i]);
				}else{
					
//					for (var j = 0; j < vals.length; j++) {
//						var kk= vals[j].split(':');
//						if(kk[0] == key)
//							vals[j]=hashArray[i];
//					}
				}
					
			}

			var value = {};

			value[this._hashCriterionKey()] = vals.join(';');

			topic.publish(SpaConst.SPA_CHANGE_ADD, {
				value : value,
				target : this
			});

		},

		fireCriteriaChanged : function($super) {

			if (!this.spa) {

				$super();
				return;
			}

			if (this.spaLock)
				return;

			this.spaLock = true;

			var criterions = this._buildCriteriaSelectedValues();

			var vals = [];

			$.each(criterions, function() {

				var key = encodeURIComponent(this.key);

				for (var i = 0; i < this.value.length; i++) {
					vals.push(key + ":" + this.value[i]);
				}

			});

			var value = {};
			value[this._hashCriterionKey()] = vals.join(';');

			topic.publish(SpaConst.SPA_CHANGE_ADD, {
				value : value
			});

			criteriaGroup(this).publish(CRITERIA_SPA_CHANGED, {
				criteria : this.id,
				criterions : criterions
			});

			this.spaLock = false;

		},

		spaCriteriaValues : function(evt) {

			if (!evt)
				return;

			// 防止死循环
			if (!evt.target && !evt.$target)
				return;

			if (!evt.value)
				return;

			this.spaEvt = evt;

			if (!this.spaInit)
				return;

			if (this.spaLock)
				return;

			this.spaLock = true;

			var keys = [ this._hashMultiKey(), this._hashCriterionKey() ];
			var hashMap = {};

			for (var i = 0; i < keys.length; i++) {

				var key = encodeURIComponent(keys[i]);

				if (evt.value[key])
					hashMap[key] = decodeURIComponent(evt.value[key]);

			}

			// // 如果为空则清空筛选器
			var criQ = hashMap[this._hashCriterionKey()];
			if ($.isEmptyObject(criQ) ) {
				if(criQ === undefined || criQ.length === 0 ){
					this.clearValue();
					criteriaGroup(this).publish(CRITERIA_SPA_CHANGED, {
						criteria : this.id,
						criterions : []
					});
	
					this.spaLock = false;
	
					return;
				}
			}

			this._parseHashMap(hashMap, keys);

			this.spaLock = false;

		},

		_parseHashMap : function($super, hashMap, keys) {

			$super(hashMap, keys);

			var criterions = this._buildCriteriaSelectedValues();
			/*#131393、#130243（流程审核、流程反馈）的筛选项功能，取消选中后还是被选中的颜色问题修改-开始*/
			for (var i = 0; i < criterions.length; i++) {
				// 是否存在标记
				var flag = false;
				for ( var key in hashMap) {
					if (hashMap[key].indexOf(criterions[i].key) != -1) {
						// 在hashMap的value中存在criterions的key值
						flag = true;
						break;
					}
				}
				// 在hashMap的value中不存在criterions的key值情况下删除掉不存在的元素
				if (!flag) {
					criterions.splice(i, 1);
					i--;
				}
			}
			/*#131393、#130243（流程审核、流程反馈）的筛选项功能，取消选中后还是被选中的颜色问题修改-结束*/
			criteriaGroup(this).publish(CRITERIA_SPA_CHANGED, {
				criteria : this.id,
				criterions : criterions
			});

		}
	}

	module.exports = criteriaAdapter;
})
