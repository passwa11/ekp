/**
 * 搜索列表
 */
define([
  "dojo/_base/declare",
  "dojo/parser",
  "dojo/dom-construct",
  "mui/util",
  "dojo/dom-style",
  "dojo/_base/query",
  "dojo/topic",
  "dijit/registry"
], function(declare, parser, domConstruct, util, domStyle, query, topic,registry) {
  return declare("sys.lbpmperson.mobile.list.LbpmPersonSearchPromptList", [], {
	url:"/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=list4Mobile&q.key=!{key}",

	templateHtml:"",
	
	//获取筛选的内容
	getFilterValue:function(){
		var navBar = registry.byId("lbpmpersonNavBar");
		if(navBar && navBar.selectedItem){
			this.key = navBar.selectedItem.key;
			var url = navBar.selectedItem.url;
			this.fdModelName = util.getUrlParameter(url,"q.fdModelName");
		}
	},
	
    setTemplateStr: function() {
    	this.getFilterValue();
    	var url = this.url;
    	if(this.fdModelName){
    		url += "&q.fdModelName=!{fdModelName}";
    	}
    	url = util.urlResolver(url,this) + "&q.docSubject=!{keyword}";
	    this.templateHtml='<div data-dojo-type="mui/list/StoreScrollableView"> ' +
	        '<div class="muiSearchTitle">' +
	        "</div>" +
	        '<ul data-dojo-type="mui/list/JsonStoreList" ' +
	        'data-dojo-mixins="sys/lbpmperson/mobile/resource/js/list/LbpmPersonItemListMixin" ' +
	        "data-dojo-props=\"lazy:false,url:'"+url+"'\">" +
	        "</ul></div>";
    },
   
    templateStr: function() {
    	return this.templateHtml;
    },
    
    _onSubmit:function(obj){
    	var keyword = obj.keyword;
    	if(this.listNode){
    		var url = this.url;
    		if(this.fdModelName){
    			url += "&q.fdModelName=!{fdModelName}";
    		}
    		url = util.urlResolver(url,this) + "&q.docSubject=!{keyword}"
    		url = util.formatUrl(url);
    		this.jsonList.url = util.setUrlParameterMap(url, {
            	"q.docSubject": keyword,
            })
    	}
    	this.inherited(arguments);
    }
  })
})
