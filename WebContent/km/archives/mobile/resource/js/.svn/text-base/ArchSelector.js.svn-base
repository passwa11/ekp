define([ "dojo/_base/declare", "dojox/mobile/_ItemBase", "dojo/_base/lang", 
         "dojo/_base/array", "dojo/_base/config", "dojo/window", "dojo/dom", "dojo/_base/array",
         "dojo/dom-style", "dojo/dom-attr", "dojo/dom-class", "dojo/dom-construct", "dojo/query", "dojo/topic", 
         "dojo/on", "dojo/request", "dojo/touch", "./ArchSelectorListItem","./util"], 
	
	function(declare, ItemBase, lang, array, config, win, dom, array, 
			domStyle, domAttr, domClass, domCtr,  query, topic, on, request, touch, 
			ArchSelectorListItem, util) {
	
	return declare("km.archives.mobile.js.ArchSelector", [ ItemBase ], {

		key: '',
		
		itemRenderer: ArchSelectorListItem,
		
		TYPE_ARCH: 0,
		TYPE_CAT: 1,
		
		isMul: false,
		
		catList: [],
		archList: [],
		
		fdTemplatId:"",
		
		selectedArchs: {},
		
		catDataUrl: 'sys/category/mobile/sysSimpleCategory.do?method=cateList&categoryId={categoryId}&getTemplate=0&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&authType=00',
		archDataUrl: 'km/archives/km_archives_borrow/kmArchBorrowOption.do?method=checkArchList&categoryId={categoryId}',
		
		postCreate: function(){
			var ctx = this;
			ctx.inherited(arguments);
			ctx.initialize();
		},
		
		initialize: function(){
			var ctx = this;
			
			ctx.catList = [];
			
			ctx.selectedArchs = {};
			topic.publish('km/archives/selectedarch/get', function(selectedArchs) {
				array.forEach(selectedArchs, function(arch) {
					ctx.selectedArchs[arch.fdId] = true;
				});
			});
			
			ctx.title = dom.byId('archSelectorTitle');
			ctx.preBtn = dom.byId('archSelectorPreBtn');
			ctx.cancelBtn = dom.byId('archSelectorCalBtn');
			
			ctx.bindEvents();
			
			ctx.loadData();
			
		},
		
		bindEvents: function(){
			
			var ctx = this;
			
			on(ctx.cancelBtn, touch.press, function(e){
				topic.publish('/mui/category/cancel', {
					key: '_archSelect'
				});
			});
			
			on(ctx.preBtn, touch.press, function(e){
				ctx.catList.pop();
				
				if(ctx.catList.length < 1){
					domStyle.set(ctx.preBtn, 'display', 'none');	
					ctx.title.innerHTML = '请选择';
					ctx.loadData();
				}else {
					var t = ctx.catList[ctx.catList.length - 1];
					ctx.title.innerHTML = t.text;
					ctx.loadData(t.id);
				}
				
				
			});
			
			on(ctx.domNode, on.selector('.muiCateItem', 'click'), function(e){
				
				e.stopPropagation();
				e.cancelBubble = true;
				e.preventDefault();
				e.returnValue = false;
				
				var node = query(e.target).closest(".muiCateItem")[0];
				
				if(node) {
					
					var type = domAttr.get(node, 'data-type');
					var id = domAttr.get(node, 'data-id');
					var text = domAttr.get(node, 'data-text');
					
					if(type == ctx.TYPE_CAT) {
						
						ctx.catList.push({
							id: id,
							text: text
						});
						ctx.title.innerHTML = text;
						domStyle.set(ctx.preBtn, 'display', 'block');
						ctx.loadData(id);
						
					} else if(type == ctx.TYPE_ARCH) {
						
						array.forEach(ctx.archList, function(d){
							
							if(d.fdId == id){
								
								
								if(ctx.isMul) {
									
									if(domClass.contains(node, 'muiCateSeled')) {
										domClass.remove(node,'muiCateSeled');
										ctx.selectedArchs[d.arch.fdId] = false;
										topic.publish('/mui/category/unselected', ctx, lang.mixin(d.arch, {
											label: d.arch.title
										}));
									} else {
										domClass.add(node,'muiCateSeled');
										ctx.selectedArchs[d.arch.fdId] = true;
										topic.publish('/mui/category/selected', ctx, lang.mixin(d.arch, {
											label: d.arch.title
										}));		
									}
									
									
								} else {
									
									if(ctx.selectedArchs[d.arch.fdId]) {
										return;
									}
									
									domClass.add(node,'muiCateSeled');
									topic.publish('km/archives/archselector/result', d.arch);
									topic.publish('/mui/category/cancel', {
										key: '_archSelect'
									});
								}
								
							}
							
						});
						
					}
					
				}
				
			});
			
			topic.subscribe('/mui/category/cancelSelected', function(_ctx, item) {
				
				if(ctx.key != _ctx.key) {
					return;
				}
				
				var node = query('.muiCateItem[data-id="' + item.fdId + '"]');
				ctx.selectedArchs[item.fdId] = false;
				if(node[0]) {
					domClass.remove(node[0], 'muiCateSeled');
				}
				
			});
			
			topic.subscribe('/mui/category/submit', function(_ctx, items) {
				
				if(ctx.key != _ctx.key) {
					return;
				}
				topic.publish('km/archives/archselector/result', items);
			});
			
		},
		
		loadData: function(catId) {
			
			var ctx = this;
			
			domCtr.empty(ctx.domNode);
			
			request.post(config.baseUrl + lang.replace(ctx.catDataUrl, {
				categoryId: catId || ''
			}), {
				handleAs: 'json'
			}).then(function(res){
				
				var t = [];
				
				array.forEach(res, function(d){
					
					t.push({
						type: ctx.TYPE_CAT,
						fdId: d.value,
						text: util.htmlEncodeByRegExp(d.text)
					});
				});
				
				ctx.renderData(t);
				topic.publish('/mui/list/loaded', ctx, t);
			});
			
			if(catId){
				request.post(config.baseUrl + lang.replace(ctx.archDataUrl, {
					categoryId: catId || ''
				}), {
					handleAs: 'json',
					data: {
						rowsize: 999999,
						fdTemplatId:this.fdTemplatId
					}
				}).then(function(res){
					
					var t = [];
					
					array.forEach(res.datas, function(d){
						var _t = {};
						array.forEach(d, function(_d){
							_t[_d.col] = _d.value;
						});
						t.push({
							type: ctx.TYPE_ARCH,
							fdId: _t.fdId,
							text: util.htmlDecode(_t.title),
							arch: _t
						});
					});
					ctx.archList = t;
					ctx.renderData(t);
				});
			}
			
		},
		
		renderData: function(data) {
			
			var ctx = this;
			
			array.forEach(data, function(item, idx){
				
				var archItem = new ctx.itemRenderer(lang.mixin(item, {
					isMul: ctx.isMul
				}));
				
				if(ctx.selectedArchs[item.fdId]) {
					domClass.add(archItem.domNode, 'muiCateSeled');
				}
				
				archItem.placeAt(ctx.domNode);
				
			});
			
		}
		
	});
		
})