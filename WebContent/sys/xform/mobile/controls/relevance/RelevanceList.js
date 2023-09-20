define( [ "dojo/_base/declare","mui/category/CategoryList","dojo/_base/lang",
          "dojo/_base/json","mui/util","dojo/topic"], function(declare,
		CategoryList, lang,json,util,topic) {
	return declare("sys.xform.mobile.controls.relevance.RelevanceList", [ CategoryList ], {
		
		modelName:null,
		
		isTemp : false,
		
		isMul : true,
		
		selType : 1,
		
		rowsize : 15,
		
		pageno: 1,

		isSearch :false, //是否为搜索时，下拉更多数据
		
		//数据请求URL
		dataUrl : '/sys/xform/controls/relevance.do?method=getAllList&parentId=!{parentId}&modelPath=!{modelPath}&fdControlId=!{fdControlId}&extendXmlPath=!{extendXmlPath}&fdKey=!{fdKey}&pAdmin=!{pAdmin}',
		
		tempUrl : '/sys/xform/controls/relevance.do?method=getDocByCategory&modelName=!{modelName}&fdControlId=!{fdControlId}&extendXmlPath=!{extendXmlPath}&fdKey=!{fdKey}&fdTemplateId=!{fdTemplateId}&categoryId=!{categoryId}&isChild=!{isChild}&modelPath=!{modelPath}&notSearch=!{notSearch}&pageno=!{pageno}&rowsize=!{rowsize}&orderby=fdId&ordertype=down&inputParams=!{inputParams}',
		
		postCreate : function() {
			this.inherited(arguments);
			this.dataUrl = util.formatUrl(this.dataUrl);
			this.tempUrl = util.formatUrl(this.tempUrl);
			this.subscribe("/sys/xform/relevance/category/changed","_RcateChange");
			this.subscribe("/sys/xform/relevance/search","_searchByUrl");
			this.subscribe("/mui/category/submit","_RcateChangeSubmit");
		},
		
		_searchByUrl : function(srcObj,evt){
			if(srcObj.key==this.key){
				this.isSearch = true;
				if(this.isTemp){
					evt.url = evt.docUrl;
					this._RcateChange(srcObj,evt);
				}else{
					evt.url = evt.cateUrl;
					this._cateChange(srcObj,evt);
				}
			}
		},
		
		_RcateChange : function(srcObj,evt){
			if(srcObj.key==this.key){
				this.isTemp = true;
				this.pageno = 1;
				if(evt && evt.url){
					if(!this._addressUrl){
						this._addressUrl = this.url;
					}
					this.showMore=false;
					this.searchUrl = evt.url;
					this.url = evt.url+"&fdControlId=!{fdControlId}&extendXmlPath=!{extendXmlPath}&pageno=!{pageno}&rowsize=!{rowsize}&modelName=!{modelName}&fdKey=!{fdKey}&fdTemplateId=!{fdTemplateId}&categoryId=!{categoryId}&isChild=!{isChild}&notSearch=!{notSearch}&orderby=fdId&ordertype=down&inputParams=!{inputParams}";
					this.url = util.urlResolver(this.url,this);
				}else{
					this.fdTemplateId = evt.fdTemplateId;
					this.categoryId = evt.categoryId;
					this.modelPath = evt.modelPath;
					this.fdKey = evt.fdKey;
					this.notSearch = evt.notSearch;
					this.isChild = evt.isChild;
					this.parentId = evt.fdId;
					if(evt.isBase){
						this.searchUrl = this.dataUrl;
					}else{
						this.searchUrl = this.tempUrl;
					}
					this.url = util.urlResolver(this.searchUrl,this);
				}
				this.buildLoading();
				this.reload();
				var self = this;
				this.defer(function(){
					topic.publish('/sys/xform/relevance/toTop',self);
				}, 200);
			}
		},
		
		loadMore: function(handle) {
			if(this.isTemp){
				if(this.isSearch){ //如果为搜索后下拉的请求则请求该url
					this.url = this.searchUrl+"&fdControlId=!{fdControlId}&extendXmlPath=!{extendXmlPath}&pageno=!{pageno}&rowsize=!{rowsize}&modelName=!{modelName}&fdKey=!{fdKey}&fdTemplateId=!{fdTemplateId}&categoryId=!{categoryId}&isChild=!{isChild}&notSearch=!{notSearch}&orderby=fdId&ordertype=down&inputParams=!{inputParams}";
					this.url = util.urlResolver(this.url,this);
				}else{
					this.url = util.urlResolver(this.tempUrl,this);
				}
			}else{
				if(this.isSearch){//如果为搜索后下拉的请求则请求该url
					this.url = util.urlResolver(this.searchUrl,this);
				}else{
					this.url = util.urlResolver(this.dataUrl,this);
				}
			}
			return this.doLoad(handle, true);
		},
		
		formatDatas : function(datas) {
			return datas;
		},
		
		//往下查看数据
		_cateChange:function(srcObj,evt){
			if(srcObj.key==this.key){
				this.isTemp = false;
				if(evt && evt.url){
					if(!this._addressUrl){
						this._addressUrl = this.url;
					}
					this.showMore=false;
					this.url = evt.url+"&modelName=!{modelName}&fdKey=!{fdKey}&fdControlId=!{fdControlId}&extendXmlPath=!{extendXmlPath}";
					this.url = util.urlResolver(this.url,this);
				}else{
					this.showMore=true;
					this.parentId = evt.fdId;
					this.modelPath = evt.modelPath;
					this.fdKey = evt.fdKey;
					this.modelName = evt.modelName;
					this.pAdmin = evt.pAdmin;
					if(typeof evt.isBase == 'undefined' || evt.isBase){
						this.url = util.urlResolver(this.dataUrl,this);
					}else{
						this.isTemp = true;
						this.url = util.urlResolver(this.tempUrl,this);
					}
				}
				this.buildLoading();
				this.reload();
				var self = this;
				this.defer(function(){
					topic.publish('/sys/xform/relevance/toTop',self);
				}, 200);
			}
		},
		
		//搜索后返回
		_cateReback:function(srcObj,evt){
			if(srcObj.key==this.key){
				this.isSearch = false;
				this.showMore=true;
				if(this.isTemp){
					this.pageno = 1;
					this.url = util.urlResolver(this.tempUrl,this);
				}else{
					this.url = util.urlResolver(this.dataUrl,this);
				}
				this.buildLoading();
				this.reload();
				var self = this;
				this.defer(function(){
					topic.publish('/sys/xform/relevance/toTop',self);
				}, 200);
			}
		},
		//分类搜索点击确定后加载文档数据
		_RcateChangeSubmit:function (srcObj,evt){
			this.fdTemplateId = evt.curIds;
			this.url = util.urlResolver(this.tempUrl,this);
			this.buildLoading();
			this.reload();
			var self = this;
			//未选择分类时分类图标默认为黑色，选择分类时，为蓝色
			document.querySelectorAll("div.muiFormRelevanceFilterItem").forEach(
				function (el){
					if(window._curIds != "-") {
						el.setAttribute("style", "color: #4285f4;");
					}else{
						el.setAttribute("style", "color: ");
					}
				});
			this.defer(function(){
				topic.publish('/sys/xform/relevance/toTop',self);
			}, 200);
		}
	});
});