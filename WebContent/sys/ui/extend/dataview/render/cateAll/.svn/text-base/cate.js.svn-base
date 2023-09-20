define(function(require, exports, module) {

	var base = require('lui/base');
	var env = require('lui/util/env');
	var topic = require('lui/topic');
	var source = require('lui/data/source');
	var spaConst = require('lui/spa/const');
	var routerUtils = require('lui/framework/router/router-utils');
	var $ = require('lui/jquery');

	var CATE_ALL_PATH_PRE = "cate.all.path.pre";
	var CATE_ALL_SUB_NEXT = "cate.all.sub.next";

	var CATE_ALL_ITEM_NEXT = "cate.all.item.next";
	var CATE_ALL_ITEM_PRE = "cate.all.item.pre";
	var CATE_ALL_ITEM_CURRENT = "cate.all.item.current";

	var PATH_URL = "/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=!{modelName}&categoryId=!{currentId}&authType=2";
	var INDEX_URL = "/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=index&modelName=!{modelName}&level=3&expand=true&authType=2&__currId=!{currentId}&parentId=!{currentId}";

	var Spa = require('lui/spa/Spa');

	function getGroup(obj) {

		while (obj) {

			if (obj instanceof Group) {
				return obj.value;
			}

			obj = obj.parent;
		}

	}

	function getPath(obj) {

		var path = obj;

		while (path) {

			if (path instanceof Cate) {

				if (!path.path || path.path.length < 2
						|| obj.value != path.path[1].value) {
					break;
				}

				return path.path;
			}

			path = path.parent;
		}

	}
	
	function getCriProps(obj) {

		while (obj) {

			if (obj instanceof Cate) {

				return obj.criProps;
			}

			obj = obj.parent;
		}

	}

	function getModelName(obj) {

		while (obj) {

			if (obj instanceof Cate) {

				return obj.modelName;
			}

			obj = obj.parent;
		}
	}

	function getCurrentId(obj) {

		while (obj) {

			if (obj instanceof Cate) {

				return obj.currentId;
			}

			obj = obj.parent;
		}
	}

	var SearchCate = base.Container.extend({
		
		initProps : function($super, _config) {
			$super(_config);
			this.datas = this.config.datas;
			//topic.subscribe(spaConst.SPA_CHANGE_VALUES, this.onCurrent, this);

			this.buildCate(this.datas);

		},

		onCurrent : function(evt) {
			if (!evt)
				return;

			//this.currentId = Spa.spa.getValue('docCategory');

		},

		buildCate : function(datas) {
			this.element.addClass('lui_dataview_cate_all');

			for (var i = 0; i < datas.length; i++) {

				var data = datas[i];
				data.parent = this;

				var group = new Group(data);

				this.addChild(group);

				if (i < 1)
					continue;

				// 为了显示好看
				if (data.children.length == 0
						&& datas[i - 1].children.length == 0) {

					var element = group.element;

					element.addClass('float');
					element.prev().addClass('float');

				}

			}

		},

		addChild : function($super, obj) {
			$super(obj);

			obj.element.appendTo(this.element);

		}

	});
	
	
	var Cate = base.Container.extend({
		
		initProps : function($super, _config) {
			$super(_config);
			
			this.datas = this.config.datas;
			this.modelName = this.config.modelName;
			this.path = this.config.path;
			this.currentId = this.config.currentId;
			this.criProps = this.config.criProps;
			// #131554 新增属性(构建当前选中的菜单)
			this.buildCurr = this.config.buildCurr || false;
			this.current = this.config.current;
			this.path = this.config.path;
			this.childs = this.config.childs;
			
			topic.subscribe(spaConst.SPA_CHANGE_VALUES, this.onCurrent, this);
			
			if(this.buildCurr) {
				this.buildCurrent();
			} else {
				this.buildCate(this.datas);
			}
		},
		// #131554 构建当前菜单的弹出层
		buildCurrent: function() {
			this.element.addClass('lui_dataview_cate_all');
			var data = this.current;
			data.parent = this;
			data.path = this.path;
			data.children = this.childs;
			data.hasChild = this.children;
			
			var group = new Group(data);
			this.addChild(group);
		},
		
		onCurrent : function(evt) {
			if (!evt)
				return;
			
			this.currentId = Spa.spa.getValue('docCategory');
			
		},
		
		buildCate : function(datas) {
			this.element.addClass('lui_dataview_cate_all');
			
			for (var i = 0; i < datas.length; i++) {
				
				var data = datas[i];
				data.parent = this;
				
				var group = new Group(data);
				
				this.addChild(group);
				
				if (i < 1)
					continue;
				
				// 为了显示好看
				if (data.children.length == 0
						&& datas[i - 1].children.length == 0) {
					
					var element = group.element;
					
					element.addClass('float');
					element.prev().addClass('float');
					
				}
				
			}
			
		},
		
		addChild : function($super, obj) {
			$super(obj);
			
			obj.element.appendTo(this.element);
			
		}
		
	});

	var Group = base.Container.extend({

		initProps : function($super, _config) {

			$super(_config);

			this.value = this.config.value;
			this.text = this.config.text;
			this.childs = this.config.children;
			// 传入自定义的路径
			this.path = this.config.path;

			this.element.addClass('lui_dataview_cate_all_group');

			var datas = getPath(this);
			this.buildPath(this.path || datas);
			this.buildChildren(datas);

		},

		// 构建路径
		buildPath : function(datas) {

			var path = [];

			if (!datas) {
				path = [ {}, {
					text : this.text,
					value : this.value
				} ];
			// 注释下面2行，是为了显示全路径
			//} else if (datas.length > 2) {
			//	path = datas.slice(0, datas.length - 1);
			} else {
				path = datas;
			}

			var path = new Path({

				datas : path,
				parent : this
			});
			this.addChild(path);

		},

		// 构建子分类
		buildChildren : function(datas) {

			var parentId;

			if (datas) {

				var leg = datas.length;

				if (datas.length > 2) {
					parentId = datas[leg - 2];
				} else {
					parentId = datas[1];
				}

			}

			var sub = new Sub({
				childs : this.childs,
				parent : this,
				parentId : parentId
			});

			this.addChild(sub);

		},

		addChild : function($super, obj) {

			$super(obj);

			obj.element.appendTo(this.element);

		}

	});

	var Item = base.Container
			.extend({

				initProps : function($super, _config) {

					$super(_config);
					this.value = this.config.value;
					this.childs = this.config.children;

					// 节点类型，pre或next，为空不显示图标
					this.type = this.config.type;
					this.hasChild = this.config.hasChild;
					this.text = env.fn.formatText(this.config.text);
					// 筛选信息
					this.criProps = getCriProps(this);

					this.buildItem();
				},

				buildItem : function() {

					this.element.addClass('lui_dataview_cate_all_item');

					var textNode = $('<div class="lui_dataview_cate_all_item_text">'
							+ this.text + '</div>');

					if (getCurrentId(this) == this.value) {
						this.element.addClass('selected');
					}

					this.element.append(textNode);

					topic.subscribe(CATE_ALL_ITEM_CURRENT, this.selected, this);

					topic.subscribe(spaConst.SPA_CHANGE_VALUES, this.selected,
							this);

					var self = this;

					textNode.on('click', function() {

						self.current();
					});

					if (!this.type)
						return;

					// 没有子节点，不需要图标
					if ('next' == this.type
							&& (!this.childs || this.childs.length == 0))
						return;

					if(this.hasChild && this.hasChild.length ==0)
						return;
					this.iconNode = $('<div class="lui_icon_s lui_icon_s_'
							+ this.type + '" />');

					this.element.append(this.iconNode);

					this.iconNode.on('click', function() {

						self[self.type]();
					});

				},

				current : function() {

					topic.publish(CATE_ALL_ITEM_CURRENT, {
						value : this.value
					});

					var router = routerUtils.getRouter();

					var data = {
						'j_path' : '/docCategory',
						'docCategory' : this.value
					};

					if (this.criProps) {
						$.extend(data, this.criProps);
					}

					router.push('/docCategory', data);

				},

				selected : function(evt) {

					if (!evt)
						return;

					if (evt.value.docCategory == this.value
							|| evt.value == this.value) {
						this.element.addClass('selected');
					} else {
						this.element.removeClass('selected');

					}

				},

				next : function() {

					this.fire({
						name : CATE_ALL_ITEM_NEXT,
						value : this.value,
						text : this.text
					});

				},

				pre : function() {

					this.fire({
						name : CATE_ALL_ITEM_PRE,
						value : this.value
					});

				},

				destroy : function($super) {

					$super();

					topic.unsubscribe(spaConst.SPA_CHANGE_VALUES,
							this.selected, this);
					topic.unsubscribe(CATE_ALL_ITEM_CURRENT, this.selected,
							this);

				}

			});

	var Path = base.Container
			.extend({

				url : PATH_URL,

				initProps : function($super, _config) {

					$super(_config);

					this.datas = this.config.datas;

					this.modelName = getModelName(this);

					this.buildPath();

				},

				addSplit : function(obj) {

					var splitNode = $('<div class="lui_dataview_cate_all_split">></div>');
					splitNode.appendTo(obj.element);

				},

				buildPath : function() {

					this.element.addClass('lui_dataview_cate_all_group_path');

					this.onRender(this.datas);

					if (!this.source) {

						this.source = new source.AjaxJson({
							url : this.url,
							parent : this
						});

						this.source.on('data', this.onRender, this);

					}

					this.on(CATE_ALL_ITEM_NEXT, this.onNext, this);
					this.on(CATE_ALL_ITEM_PRE, this.onPre, this);

					topic.channel(getGroup(this)).subscribe(CATE_ALL_SUB_NEXT,
							this.onNext, this);
				},

				onRender : function(datas) {

					// 销毁子分类
					for (var i = 0; i < this.children.length; i++) {
						this.children[i].destroy();
					}

					this.children = [];

					for (var i = 1; i < datas.length; i++) {

						var param = {
							parent : this
						}

						if (datas.length > 2 && i >1)
							param.type = 'pre';

						this.addChild(new Item($.extend(datas[i], param)));

					}

				},

				addChild : function($super, obj) {

					var children = this.children;
					var len = children.length;

					if (len > 0) {
						this.addSplit(obj);
					}

					$super(obj);

					obj.element.appendTo(this.element);

				},

				onPre : function(evt) {

					if (!evt)
						return;

					var value = evt.value;

					var child;

					for (var i = 0; i < this.children.length; i++) {

						if (this.children[i].value == value) {
							child = this.children[i - 1];
						}
					}

					if (!child)
						return;

					value = child.value;

					this.source.resolveUrl({
						modelName : this.modelName,
						currentId : value
					});

					this.source.get();

					topic.channel(getGroup(this)).publish(CATE_ALL_PATH_PRE, {
						value : value
					});

				},

				onNext : function(evt) {

					if (!evt)
						return;

					this.source.resolveUrl({
						modelName : this.modelName,
						currentId : evt.value
					});

					this.source.get();

				}

			});

	var Sub = base.Container.extend({

		modelName : '',

		url : INDEX_URL,

		initProps : function($super, _config) {

			$super(_config);

			this.childs = this.config.childs;
			this.parentId = this.config.parentId;

			this.element.addClass('lui_dataview_cate_all_group_sub');

			this.on(CATE_ALL_ITEM_NEXT, this.onReRender, this);
			this.on(CATE_ALL_ITEM_NEXT, this.onNext, this);

			topic.channel(getGroup(this)).subscribe(CATE_ALL_PATH_PRE,
					this.onReRender, this);

			if (this.parentId) {

				this.onReRender(this.parentId);

				return;
			}

			this.onRender(this.childs);

		},

		onNext : function(evt) {

			if (!evt)
				return;

			topic.channel(getGroup(this)).publish(CATE_ALL_SUB_NEXT, {
				value : evt.value,
				text : evt.text
			});

		},

		addChild : function($super, obj) {

			$super(obj);

			obj.element.appendTo(this.element);

		},

		// 渲染子分类
		onRender : function(datas) {
			datas = datas || [];

			// 销毁原有子分类
			for (var i = 0; i < this.children.length; i++) {
				this.children[i].destroy();
			}

			this.children.length = 0;

			for (var i = 0; i < datas.length; i++) {

				var child = datas[i];
				child.parent = this;

				var item = new Item($.extend(child, {
					parent : this,
					type : 'next'
				}));

				this.addChild(item);

			}

			// if (this.parentId)
			// topic.publish(CATE_ALL_ITEM_CURRENT, {
			// value : getCurrentId(this)
			// });
		},

		/**
		 * 重新渲染<br>
		 * 监听当前分类改变，重新渲染子分类区域
		 */
		onReRender : function(evt) {

			if (!evt)
				return;

			var value = evt.value;

			if (!this.source) {

				this.source = new source.AjaxJson({
					url : this.url,
					parent : this
				});

				this.source.on('data', this.onRender, this);

			}

			this.source.resolveUrl({
				modelName : getModelName(this),
				currentId : value
			});

			this.source.get();

		}

	});

	exports.Group = Group;
	exports.Path = Path;
	exports.Item = Item;
	exports.Sub = Sub;
	exports.Cate = Cate;
	exports.SearchCate = SearchCate;

});