define(function(require, exports, module) {

	var base = require('lui/base');
	var env = require('lui/util/env');
	var topic = require('lui/topic');
	var source = require('lui/data/source');
	var spaConst = require('lui/spa/const');
	var routerUtils = require('lui/framework/router/router-utils');
	var $ = require('lui/jquery');

	var Spa = require('lui/spa/Spa');
	
	//var isKeepCriParameter=false;
	
	var criProps;
	
	var SearchCate = base.Container.extend({
		
		initProps : function($super, _config) {
			$super(_config);
			this.datas = this.config.datas;
			this.config.modelName;
			criProps=_config['cri.q'];
			//isKeepCriParameter=_config.isKeepCriParameter;
			topic.subscribe(spaConst.SPA_CHANGE_VALUES, this.onCurrent, this);
			this.buildCate(this.datas,this.config.modelName);

		},

		onCurrent : function(evt) {
			
			if (!evt)
				return;

			this.currentId = Spa.spa.getValue('docCategory');

		},

		buildCate : function(datas,modelName) {
			this.element.addClass('lui_dataview_search_wrap');
			var search_a = $('<div class="lui_search_area" />');
			
			var search_l = $('<p class="lui_search_tips">为您找到<em>'+datas.length+'</em>个搜索结果</p>');
			var result_i = $('<div class="lui_search_result_item" />');
			var ul = $('<ul></ul>');
			
			for (var i = 0; i < datas.length; i++) {
				
				var data = datas[i];
				
				var pathHtml="";
				
				$.ajax({
					url : env.fn.formatUrl("/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName="+modelName+"&categoryId="+datas[i]['value']+"&authType=2"),
					dataType : 'json',
					async:false,
					success : function(pathDatas) {
						for (var j = 0; j < pathDatas.length; j++) {
							if(pathHtml=="")
								pathHtml=pathDatas[j].text;
							else
								pathHtml += ">"+pathDatas[j].text;
						}
					}
				});
				
				if(pathHtml != ""){
					pathHtml = "<p class='search_source'><span title='"+pathHtml+"'>"+pathHtml+"</span></p>"
				}
				
				var li = $('<li><h5 class="search_result" title="'+data.text+'">'+data.text+'</h5>'+pathHtml+'</li>');
				ul.append(li);
				
				~~function(i) {
					return function() {
						li.click(function(evt) {
							var router = routerUtils.getRouter();
							if (router) {
								var cri ={
										'docCategory' : datas[i]['value']
								};
								if (criProps) {
									cri ={
											'docCategory' : datas[i]['value'],
											'cri.q':criProps
									}
								}
								router.push('/docCategory', cri)
							} else {
								var vla={
											'docCategory' : datas[i]['value']
										};
								if (criProps) {
									vla={
											'docCategory' : datas[i]['value'],
											'cri.q':criProps
										};
								}
								topic.publish(spaConst.SPA_CHANGE_ADD, {
									value : vla
								});
							}
							
							//this.addClass('selected');
						});
					}();
				}(i);
				
				
			}
			
			result_i.append(ul);
			search_a.append(search_l);
			search_a.append(result_i);
			
			//$('.lui_dataview_search_wrap').append(search_a);
			
			this.element.append(search_a);

		},

		addChild : function($super, obj) {
			$super(obj);

			obj.element.appendTo(this.element);

		}

	});
	

	exports.SearchCate = SearchCate;

});