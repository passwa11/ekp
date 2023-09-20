define( [ "dojo/_base/declare","mui/category/CategoryList","dojo/_base/lang",
          "dojo/_base/json","mui/util","dojo/topic"], function(declare,
		CategoryList, lang,json,util,topic) {
	return declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizeList", [ CategoryList ], {
		
		isMul : true,
		
		selType : 1,
		
		rowsize : 15,
		
		pageno: 1,
		
		//数据请求URL
		dataUrl : '/sys/lbpmext/authorize/lbpm_authorize_scope/lbpmAuthorizeScope.do?method=getAllList',
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe('/mui/search/submit', '_cateChange');
			this.subscribe("/mui/search/cancel","_cateReback");
		},
		
		//往下查看数据
		_cateChange:function(srcObj,evt){
			if(srcObj.key==this.key){
				this.isTemp = false;
				if(evt && evt.url){
					if(!this._addressUrl){
						this._addressUrl = this.url;
					}
					this.showMore=true;
					if(this.url.indexOf("keyword")<0){
						this.url = util.urlResolver(this.url+"&keyword="+evt.keyword,this);
					}
					this.url = util.setUrlParameter(this.url,"keyword",evt.keyword);
				}else{
					if(this._addressUrl){
						this._addressUrl = '';
					}
					this.showMore=true;
					if(evt.param != undefined){
						this.url = util.urlResolver(this.dataUrl+evt.param,this);
					}else{
						this.url = util.urlResolver(this.dataUrl+'&top=true',this);
					}
					
					this.url = util.formatUrl(this.url);
				}
				
				this.buildLoading();
				this.reload();
				var self = this;
				this.defer(function(){
					topic.publish('/sys/lbpmext/authorize/toTop',self);
				}, 200);
			}
		}
	});
});