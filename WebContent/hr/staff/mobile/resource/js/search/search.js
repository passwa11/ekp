define(["dojo/_base/declare", 
        "dojo/dom-construct",
        "dijit/_WidgetBase",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query","dojo/store/Memory","dojo/dom-style","mui/i18n/i18n!hr-staff:mobile.hr.staff.search.placeholder"], 
		function(declare, domConstruct, WidgetBase,on,touch,domClass,query,Memory,domStyle,msg) {
		return declare("hr.staff.search", [WidgetBase], {
			placeholder:msg['mobile.hr.staff.search.placeholder'],
			value:'',
			preUrl:'',
			paramValue:'',
			startup:function(){
				this.inherited(arguments);
			},
			buildRendering:function(){
				this.inherited(arguments);
				var searchNode = domConstruct.create("div",{className:'file-overview-search'},this.domNode);
				var searchTextNode = domConstruct.create("div",{className:'file-overview-text'},searchNode);
				var searchBtnNode = domConstruct.create("i",{className:'file-overview-search-icon'},searchTextNode);
				var searchInputNode = domConstruct.create("input",{className:'file-overview-input',type:'text'},searchTextNode);
				searchInputNode.placeholder=this.placeholder;
				searchInputNode.value = this.value;
				var cancelNode = domConstruct.create("div",{className:'file-overview-search-cancel'},searchTextNode);
				var vline=domConstruct.create("div",{className:'file-overview-search-vline'},cancelNode);
				var vline=domConstruct.create("div",{className:'file-overview-search-hline'},cancelNode);
				if(!this.value){
					domStyle.set(cancelNode,'display','none');
				}
				on(searchInputNode,'input',function(e){
					var value = e.target.value;
					if(value){
						domStyle.set(cancelNode,'display','block');
					}else{
						domStyle.set(cancelNode,'display','none');
					}
				})
				on(cancelNode,"click",function(e){
					searchInputNode.value='';
					domStyle.set(cancelNode,'display','none');
				})
				var _this = this;
				on(searchBtnNode,'click',function(){
					_this.onEnterEvent(searchInputNode);
				})
				on(document,'keydown',function(e){
					if(event.keyCode == 13){
						_this.onEnterEvent(searchInputNode);
					}
				})
			},
			onEnterEvent:function(searchInputNode){
				var url = dojo.config.baseUrl+"hr/staff/mobile/search_list.jsp?key="+searchInputNode.value+"&preUrl="+this.preUrl;
				if(this.paramValue){
					url+="&moduleParam="+this.paramValue;
				}
				window.location.href=url;
			}
		})
	  })