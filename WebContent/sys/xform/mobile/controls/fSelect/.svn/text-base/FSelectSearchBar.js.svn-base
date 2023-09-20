define([ "dojo/_base/declare", "dijit/_Contained", "dijit/_Container","dijit/_WidgetBase", 
         "dojo/dom-class", "dojo/dom-construct","dojo/dom-style", "dojo/topic", "dojo/query", 
		"dojo/_base/lang", "mui/util", "mui/i18n/i18n!sys-xform-base:mui","mui/search/SearchBar","dojo/_base/array"],
	function(declare, Contained, Container, WidgetBase, domClass,
			domConstruct, domStyle, topic, query, lang, util, Msg, SearchBar, array) {

	return declare("sys.xform.mobile.controls.fSelect.FSelectSearchBar",[ WidgetBase, Container, Contained ],
			{
				jumpToSearchUrl : false,
				needPrompt : false,
				searchUrl : '',
				key : null,
				showLayer: false,
				emptySearch : true,
				children : [],
				data : [], //所有选项
				
				postCreate : function() {
					// 清空选项
					this.children = [];
					this.inherited(arguments);
					// 搜索触发
					this.subscribe("/mui/search/submit",lang.hitch(this,'searchItem'));
					// 取消搜索触发
					this.subscribe("/mui/search/cancel",lang.hitch(this,'searchItem'));
				},
				
				initSearchBars : function(argu,dataSource){
					this.data = argu;
					this.append(this.createSearchItem(''));
				},
				
				createSearchItem : function(outerSearch){
					var placeHolder = Msg["mui.fSelect.search.enter"];
					var item = new SearchBar({jumpToSearchUrl:this.jumpToSearchUrl, needPrompt:this.needPrompt,
								searchUrl:this.searchUrl, key:this.key, emptySearch:this.emptySearch, 
								placeHolder:placeHolder,showLayer:this.showLayer, outerSearch:outerSearch}); 
					item.startup();
					this.searchBar = item;
					this.connect(this.searchBar.clearNode, "onclick", "_onClear");
					this.children.push(item);
					return item;
				},
				
				_onClear : function(){
					this.defer(function() {
						topic.publish("/mui/search/cancel",this);
					}, 450);
				},
				
				append:function(item){
					domConstruct.place(item.containerNode,this.containerNode,"last");
				},
				
				searchItem : function(srcObj , ctx){
					if(srcObj.key && srcObj.key == this.key){
						// 获取搜索的单元
						var children = this.children;
						var outerSearchParams = [];
						for(var i = 0;i < children.length;i++){
							var child = children[i];
							child.outerSearch = {};
							child.outerSearch.value = child.searchNode.value;
							outerSearchParams.push(child.outerSearch);
						}
						this.paramsJSON = JSON.stringify(child.outerSearch);
						topic.publish("/sys/xform/fSelect/search",this,{argu:this.paramsJSON});
					}
				}
				
			});
	});
