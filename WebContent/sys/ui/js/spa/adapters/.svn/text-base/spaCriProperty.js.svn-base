/**
 * 属性筛选单页面容器
 */
define(function(require, exports, module) {

	var SpaConst = require('../const');
	var $ = require('lui/jquery');
	var source = require('lui/data/source');
	var base = require("lui/base");
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var parser = require('lui/parser');
	
	var cbase = require('lui/criteria/base');
	

	var criteriaGroup = cbase.criteriaGroup;
	var CRITERION_UPDATE = cbase.CRITERION_UPDATE;

	var SpaCriProperty = base.Container
			.extend({

				_isCriterionProxy : true,

				supportMulti : function() {

					return true;
				},

				setMulti : function() {

				},

				propertyUrl : '/sys/ui/js/spa/adapters/spaCriProperty.jsp?modelName=!{modelName}&docCategory=!{docCategory}',

				initProps : function($super, cfg) {

					this.spa = env.fn.getConfig().isSpa;

					$super(cfg);

					if (!this.spa)
						return;

					this.criterions = [];

					this.modelName = cfg.modelName;

					// 筛选器分类key
					if (cfg.cri) {
						this.criKey = cfg.cri;
					}

					topic.subscribe(SpaConst.SPA_CHANGE_VALUES,
							this.spaPropertyValues, this);

					if (!this.source) {

						this.source = new source.AjaxText({
							url : this.propertyUrl,
							formatKey : 'text',
							parent : this
						});

						this.source.on('data', this.onDataLoad, this);

						this.children.push(this.source);
					}

				},

				buildRelation : function(instances) {
					
					var self = this;

					$
							.each(
									instances,
									function(index, instance) {

										if (instance.className != 'criterion')
											return;
										//把筛选选移动到筛选器正确位置
										setTimeout(
												function() {
													instance
															.setParentNode(self.parent.criterionsArea[0]);
												}, 1);
										// 构建跟主筛选器的关系
										instance.setParent(self.parent);
										self.parent.addChild(instance);
										// 注册筛选器值
										var select_values = instance.selectBox.criterionSelectElement.selectedValues;
										var p = select_values;

										while (p) {
											if (p.registerCriteriaValue) {
												p
														.registerCriteriaValue(select_values);
											break;
											}
											p = p.parent;
										}
										
										criteriaGroup(select_values).subscribe(CRITERION_UPDATE, 
												select_values._onUpdateValues, select_values);
									
										// 构建跟本对象的关系，用于分类切换销毁
										self.criterions.push(instance);

										if (self.parent.expand)
											instance.show(true);

									})
				},		

				// 属性页面请求完毕后进行解析渲染
				onDataLoad : function(evt) {

					if (!evt)
						return;

					if (!evt.text)
						return;

					this.element.html(evt.text);

					var self = this;
					
					parser.parse(this.element, function(instances) {
						self.buildRelation(instances);
					});

				},

				// 销毁当前对象所关联的子筛选项
				destroyCriterions : function() {

					var parent = this.parent;

					$.each(this.criterions, function(index, criterion) {

						parent.removeCriterion(criterion);
						criterion.selectBox.criterionSelectElement.clearAll();
						criterion.destroy();
						criterion = null;
					});

					this.criterions = [];
				},

				// 获取筛选器中分类值
				getCriValue : function(evt) {

					var criteriaKey;
					var channel = this.parent.channel;

					if (!channel) {
						criteriaKey = "cri.q";
					} else {
						criteriaKey = "cri." + channel + ".q";
					}

					var hashStr = evt.value[criteriaKey];

					if (!hashStr) {
						return;
					}

					var hashArray = hashStr.split(';');

					var criMap = {};

					for (var i = 0; i < hashArray.length; i++) {

						var p = hashArray[i].split(':');
						var val = criMap[p[0]];

						if (val == null) {
							val = [];
							criMap[p[0]] = val;
						}

						val.push(p[1]);
					}

					if (!this.criKey) {
						return;
					}

					return criMap[this.criKey];

				},

				// 监听分类信息变化进行对应属性筛选项的渲染
				spaPropertyValues : function(evt) {

					if (!evt)
						return;

					if (!evt.value)
						return;

					var nDocCategory;

					if (!this.criKey) {
						nDocCategory = evt.value.docCategory;
					} else {
						var cates = this.getCriValue(evt);
						nDocCategory = cates && cates.length > 0 ? cates[0]
								: '';
					}

					// 分类未发生改变
					if (nDocCategory == this.docCategory)
						return;

					this.docCategory = nDocCategory;

					this.destroyCriterions();

					this.source.resolveUrl(this);
					this.source.get();

				}

			});

	exports.SpaCriProperty = SpaCriProperty;
})
