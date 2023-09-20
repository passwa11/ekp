define([ 
	"dojo/_base/declare",
	"dojo/topic",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dijit/registry",	
	"dojox/mobile/TransitionEvent",
	"mui/search/SearchBar",
	"mui/util",
	"mui/i18n/i18n!sys-mobile:mui"
	],function(declare, topic, domStyle, domAttr, registry, TransitionEvent, SearchBar, util, Msg) {

	function debounce(func, wait, options){
		var timer = null;
	    return function(){
	        var context = options.context || this;
	        var args = arguments;
	        if(timer){
	            clearTimeout(timer)
	        }
	        timer = setTimeout(function(){
	            func.apply(context, args)
	        }, wait)
	    }
	}
	
	return declare("mui.address.AddressSearchBar",[ SearchBar ],{
		
		//搜索结果直接挑转至searchURL界面
		jumpToSearchUrl:false,
		
		//是否需要输入提醒
		needPrompt:false,
		
		showLayer:false,
		
		//搜索关键字
		keyword : "",
		
		//例外类别id
		exceptValue:'',
		
		//提示文字
		placeHolder : Msg['mui.mobile.address.search'],
		
		orgType: null,
		
		// 前视图
		previousView: null,
		
		buildRendering: function() {
			this.inherited(arguments);
			
			// 生态组织
			this.subscribe('/mui/address/searchBar/cancel',"_onCancel");
		},
		
		postMixInProperties: function(){
			this.inherited(arguments);
			this.placeHolder = this.getPlaceHolder();
		},
		
		postCreate: function(){
			this.inherited(arguments);
			this.connect(this.searchNode, "onclick", "_onOpenSearchView");
			this.connect(this.searchNode, "oninput", debounce(this._onSearch, 500, {
				context : this
			}));
			this.subscribe('/mui/address/swapView',"_onChangeView");
			this.subscribe('/mui/address/search/submit',"_onHandleSearchNode");
		},
		
		_onOpenSearchView: function(){
			
			// 生态组织
			topic.publish("/sys/org/eco/btn/hidden");
			
			var searchView = this.getSearchView();
			var showingView = searchView.getShowingView();
			var showingViewID = showingView.id;
			// 默认视图才需要切换搜索至搜索视图，其余视图停留在原地。这里的处理有点拙劣，要改...
			if(showingViewID.startsWith('default')){
				new TransitionEvent(document.body ,{
					moveTo: searchView.id
				}).dispatch();
			}
			if(searchView !== showingView){
				this.previousView = showingView;
				topic.publish("/mui/address/search/init", this);
			}
		},
		
		_onChangeView: function(srcObj, evt){
			if(srcObj.key === this.key){
				if(evt && evt.viewKey != 'default'){
					domAttr.set(this.searchNode, 'placeHolder', Msg['mui.mobile.address.search.simple']);
				}else{
					domAttr.set(this.searchNode, 'placeHolder', this.getPlaceHolder());
				}
			}
		},
		
		_onCancel: function(evt){
			
			// 生态组织
			topic.publish("/sys/org/eco/btn/show");

			this._onClear(evt)
			domStyle.set(this.buttonArea, {
	    	  display: "none"
			});
			if(this.previousView){
				new TransitionEvent(document.body , {
					moveTo: this.previousView.id
				}).dispatch();
				topic.publish("/mui/address/search/cancel", this);
			}
		},
		
		_onSearch: function(evt){
			this._eventStop(evt);
		    this.set("keyword", this.searchNode.value);
		    if (this.keyword) {
		    	topic.publish("/mui/address/search/submit", this, {
		    		// 关键字
		    		keyword: decodeURIComponent(this.keyword.replace(/%/g,'%25'))
		    	});
		    }
		    return false
		},
		
		_onHandleSearchNode: function(evt){
			var value = this.searchNode.value;
			if(evt.keyword !== value){
				this.searchNode.value = evt.keyword;
			}
		},
		
		getSearchView: function(){
			var searchViewID = 'searchView_' + this.key;
			var searchView = registry.byId(searchViewID);
			return searchView;
		},
		
		getPlaceHolder: function(){
			// 根据orgType来决定placeHolder提示语
			var placeHolder = Msg['mui.form.please.input'];
			if((this.orgType & window.ORG_TYPE_PERSON) ==  window.ORG_TYPE_PERSON){
				// 姓名
				placeHolder +=  Msg['mui.mobile.address.search.type.person'] + '/';
			}
			if((this.orgType & window.ORG_TYPE_ORG) == window.ORG_TYPE_ORG){
				// 机构名称
				placeHolder += Msg['mui.mobile.address.search.type.org'] + '/';
			}
			if((this.orgType & window.ORG_TYPE_DEPT) == window.ORG_TYPE_DEPT){
				// 部门名称
				placeHolder += Msg['mui.mobile.address.search.type.dept'] + '/';
			}
//			if((this.orgType & window.ORG_TYPE_GROUP) == window.ORG_TYPE_GROUP){
//				// 群组名称
//				placeHolder += Msg['mui.mobile.address.search.type.group'] + '/';
//			}
			if((this.orgType & window.ORG_TYPE_POST) == window.ORG_TYPE_POST){
				// 岗位名称
				placeHolder += Msg['mui.mobile.address.search.type.post'] + '/';
			}
			return placeHolder.replace(/\/$/,'');
		}
		
			
	});
});
