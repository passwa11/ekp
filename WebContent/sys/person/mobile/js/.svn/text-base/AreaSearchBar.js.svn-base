define( [ 'dojo/_base/declare', 'dijit/_WidgetBase', 'dojo/dom', 'dojo/dom-construct', 'dojo/dom-class',
          'dojo/dom-attr', 'dojo/dom-style', 'dojo/on', 'dojo/topic', 'mui/util'], 
          function(declare, _WidgetBase, dom, domCtr, domClass, domAttr, domStyle, on, topic, util) {
	
	return declare('sys.person.AreaSearchBar', [ _WidgetBase ], {

		key: null,
		
		//搜索请求地址
		searchUrl : '/sys/person/sys_person_switchArea/sysPersonSwitchArea.do?method=getData&fdName=!{keyword}',
		
		buildRendering: function() {
			
			this.domNode = domCtr.create('form', {
				className: 'muiSearchBar',
				style: {
					padding: '1rem 0',
					boxSizing: 'border-box'
				}
			});
			
			this.containerNode = domCtr.create('div', {
				className: 'muiSearchBarContainer'
			}, this.domNode);
			
			var divNode = domCtr.create('div', {
				className: 'muiSearchDiv'
			}, this.containerNode);
			
			domCtr.create('div', {
				className: 'muiSearchIcon mui mui-search'
			}, divNode);

			this.clearNode = domCtr.create('div', {
				className: 'muiSearchClear mui mui-fail',
				style: {
					display: 'none'
				}
			}, divNode);
			
			this.inputNode = domCtr.create('input', {
				type: 'search',
				className: 'muiSearchInput',
				placeholder:'搜索场所',
				onfocus: 'this.placeholder=""',
				onblur: 'this.placeholder="搜索场所"'
			}, divNode);
			
		},
		
		postCreate: function() {
			this.inherited(arguments);
			this.bindEvents();
		},
		
		bindEvents: function() {
			this.connect(this.domNode, "onsubmit",
				"_onSearch");
			
			this.connect(this.inputNode, "oninput",
				"_onChange");

			this.connect(this.clearNode, "onclick",
			"_clearSearch");

		},
		
		_eventStop : function(evt) {
			if (evt) {
				if (evt.stopPropagation)
					evt.stopPropagation();
				if (evt.cancelBubble)
					evt.cancelBubble = true;
				if (evt.preventDefault)
					evt.preventDefault();
				if (evt.returnValue)
					evt.returnValue = false;
			}
		},
		
		_clearSearch: function(e) {
			domAttr.set(this.inputNode, 'value', '');
			domStyle.set(this.clearNode, 'display', 'none');
			topic.publish('/mui/search/cancel', this);
		},
		
		_onSearch: function(e) {
			this._eventStop(e);

			var value = this.inputNode.value;
			if(value) {
				
				topic.publish('/mui/search/submit', this, {
					keyword: value,
					url: util.formatUrl(util.urlResolver(
							this.searchUrl, {
								keyword: value
							}))
				});
				
			}
			
		},
		
		_onChange: function(e) {
			this._eventStop(e);

			if((e.target.value || '').length > 0) {
				domStyle.set(this.clearNode, 'display', 'inline-block');
			} else {
				var event = document.createEvent("HTMLEvents");
		        event.initEvent("click", false, true);
				this.clearNode.dispatchEvent(event);
			}
			
		}
			
	});
});