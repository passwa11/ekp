define(
		[
				"dojo/_base/declare",
				"dojo/topic",
				"dojo/_base/lang",
				"dojo/dom-attr",
				"dojo/request",
				"mui/util",
				"dojo/query",
				"dijit/registry",
				"dojo/dom-style",
				"dojo/_base/array",
				"dojo/dom-construct",
				"mui/form/Select" ],
		function(
				declare,
				topic,
				lang,
				domAttr,
				request,
				util,
				query,
				registry,
				domStyle,
				array,
				domConstruct,
				Select) {

			var claz = declare(
					"sys.property.define.mlinkage",
					null,
					{

						url : '/sys/property/define/mlinkage.jsp',

						RADIO_CHANGE : 'mui/form/radio/change',
						SELECT_CALLBACK : 'mui/form/select/callback',

						bind : function(defineId, pDefineId) {

							if (!('add' == __propertyMethod__ || 'edit' == __propertyMethod__)) {
								return;
							}

							this.show(defineId, false);

							// 缓存父子属性信息
							if (!window.__property__) {
								window.__property__ = {};

								topic.subscribe(this.RADIO_CHANGE, lang.hitch(
										this, this.onRadioChange));

								topic.subscribe(this.SELECT_CALLBACK, lang
										.hitch(this, this.onRadioChange));

							}

							var props = window.__property__;
							if (props[pDefineId]) {
								props[pDefineId].push(defineId);
							} else {
								props[pDefineId] = [ defineId ];
							}
						},

						show : function(defineId, show) {

							var display = 'none';

							if (show) {
								display = 'block';
							}

							domStyle.set(
									query('[defineId="' + defineId + '"]')[0],
									'display', display);
						},

						onRadioChange : function(obj, evt) {

							if (!obj) {
								return;
							}

							// 非新建编辑状态不触发事件
							if ('edit' != obj.showStatus) {
								return;
							}

							// 获取父节点属性id
							var parent = obj;
							if (!(obj instanceof Select)) {
								parent = obj.getParent();
							}

							if (!parent) {
								return;
							}

							var pDefineId = domAttr.get(
									parent.domNode.parentNode, 'defineId');
							if (!pDefineId) {
								return;
							}

							// 获取需要联动的子属性
							var defineIds = window.__property__[pDefineId];
							if (!defineIds) {
								return;
							}

							// 父属性选中值
							var value = obj.value;

							// 获取父子属性的联动信息并进行联动
							this.requestProps(value, defineIds);

						},

						requestProps : function(pValue, defineIds) {

							var self = this;

							array.forEach(defineIds, function(defineId) {

								var fromData = {
									pValue : pValue,
									fdDefineId : defineId
								};
								var promise = request.post(util
										.formatUrl(this.url), {
									data : fromData,
									timeout : 30000,
									handleAs : 'json'
								});

								promise.response.then(function(resp) {

									var data = resp.data;
									if (!data) {
										return;
									}

									self.show(defineId, true);

									var obj = registry
											.byNode(query('[defineId="'
													+ defineId + '"]>div')[0]);

									obj.set('value', '');

									// 兼容pc端数据结构
									var type = data.pop().displayType;

									self['on' + util.capitalize(type)](obj,
											data);
								})
							}, this);

						},

						onSelect : function(obj, data) {

							obj.values = data;
						},

						onRadio : function(obj, data) {
							if(!obj._origValidate && obj._validate){
								obj._origValidate = obj._validate;
							}
							if(!obj.origValidate && obj.validate){
								obj.origValidate = obj.validate;
							}
							if(!obj._origRequired && obj.required){
								obj._origRequired = obj.required;
							}
							if(!data || data.length == 0){
								obj._validate = '';
								obj.validate = '';
								obj.required = false;
							}else{
								obj._validate = obj._origValidate;
								obj.validate = obj.origValidate;
								obj.required = obj._origRequired;
							}
							obj.getChildren().forEach(function(item) {

								item.destroy();
							});
							domConstruct.empty(obj.valueNode);

							obj.set('values', '');
							obj.generateList(data);

						},

						onCheckbox : function(obj, data) {

							this.onRadio(obj, data);
						}

					});

			return new claz();
		});