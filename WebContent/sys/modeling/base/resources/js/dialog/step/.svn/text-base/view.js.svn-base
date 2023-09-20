/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var strutil = require('lui/util/str');
	var env = require("lui/util/env");
	var source = require("lui/data/source");
	var render = require("lui/view/render");
	var modelingLang = require("lang!sys-modeling-base");
	
	/*
	 * 视图基类
	 * 1.子类需要实现formateSourceData方法，该方法用于转换后台返回的数据，转换成{id : info}的结构，赋给this.data
	 * 2.搜索的关键字为searchVal
	 * 3.render里面具体点击的元素必须含有属性data-trigger='true'
	 * 4.建议子类都实现isSourceDataEmpty，用于判断后台返回的数据是否为空
	 * 
	 * */
	var BaseView = base.DataView.extend({

		initProps: function($super, cfg) {
			$super(cfg);
			this.isDraw = false;
			this.searchDom = null;
			this.value = {};
			this.data = {};	// {id : info}
	    },
	    
	    startup:function($super){
			this.source = new source.AjaxJson({
				url : this.config.info.sourceUrl,
				parent : this
			});
			this.source.startup();
			this.source.resolveUrl({});
			
			this.render = new render.Template({
				parent : this,
				src : this.config.info.renderSrc
			});
			this.render.startup();
			
			$super();
		},
		
		show : function(){
			if(!this.isDraw){
				// 画内容
				this.drawContent(this.parent.contentDom);
				this.isDraw = true;
			}
			this.element.show();
			this.element.addClass("active");
			// 更新内容
			this.buildContent();
		},
		
		hide : function(){
			this.element.hide();
			this.element.removeClass("active");
		},
		
		drawSearch : function(container){
			var self = this;
			this.searchDom = $("<div class='content-search' />").appendTo(container);
			var $inputWrap = $("<div class='search-input-wrap'>").appendTo(this.searchDom);
			var searchPlaceHolderTxt = modelingLang['modeling.view.input.search']+ (this.config.info.text ? this.config.info.text + modelingLang['modeling.model.fdName'] : modelingLang['modeling.form.Content']);
			this.searchInputDom = $("<input type='text' placeholder='"+ searchPlaceHolderTxt +"' class='search-input'/>").appendTo($inputWrap);
			var $searchIcon = $("<i class='search-icon lui_icon_s_icon_search'/>").appendTo($inputWrap);
			// 监听enter事件
			this.searchInputDom.on("keyup", function(event){
				if (event && event.keyCode == '13') {
					self.doSearch();
				}
			});
			// 搜索
			$searchIcon.on("click", function(){
				self.doSearch();
			});
			
		},
		
		drawContent : function(container){
			this.element = $("<div class='content-main' />").appendTo(container);
			// 画搜索框
			this.drawSearch(this.element);
			this.contentDom = $("<div class='content-block' />").appendTo(this.element);
		},
		
		buildContent : function(){
			// 存在上一个视图
			if(this.preWgt){
				// 如果两次请求的url一样，则不发送请求
				var tmpLastUrl = this.source.url;	// 上一次请求的url
				this.source.resolveUrl(this.getReqParams());
				tmpLastUrl = Com_SetUrlParameter(tmpLastUrl, "t", "0");
				var curTmpUrl = Com_SetUrlParameter(this.source.url, "t", "0");
				if(tmpLastUrl == curTmpUrl){
					return;
				}
				this.source.get();
			}else{
				// 第一个视图
				if(!this.isLoad){
					this.source.get();
				}
			}
		},
		
		onDataLoad: function(data) {
			this.data = data;
			// 判断数据是否为空，如果数据为空，且配置了显示暂无数据公共提醒，则渲染无数据提醒HTML，反之由render进行数据内容渲染
			if(this.isSourceDataEmpty(data)){
				var noDataTipHtml = this._buildNoDataTipContent();
				this.doRender(noDataTipHtml);
			}else{
				this.render.get(data);
			}
		},
		
		isSourceDataEmpty : function(data){
			return $.isEmptyObject(data) || (data.datas && $.isEmptyObject(data.datas));
		},
		
		getReqParams : function(){
			var reqParams = {};
			var searchVal = this.searchInputDom.val();
			$.extend(reqParams, {
				searchVal : searchVal
			});
			if(this.preWgt){
				var preKeyData = this.preWgt.getKeyData();
				$.extend(reqParams, preKeyData);
			}
			return reqParams;
		},
		
		// 主内容区渲染
		doRender : function(html){
			this.loading.remove();
			if(html){
				this.contentDom.html("");
				this.contentDom.append(html);
			}
			this.isLoad = true;
			var self = this;
			/******** 添加事件 start **********/
			this.contentDom.find("[data-trigger='true']").on("click", function(){
				self.contentDom.find("[data-trigger='true']").removeClass("active");
				$(this).addClass("active");
				self._click(this);
			});
			/******** 添加事件 end **********/
		},
		
		_click : function(dom){
			// 设置当前值
			var val = $(dom).attr("data-item-value");
			this.setVal(val);
			// 切换到下一个视图
			this.parent.switchNext(true);
		},
		
		doSearch : function(){
			this.clearVal();
			// 需求：搜索时是否需要校验值一致，一致则不请求
			this.source.resolveUrl(this.getReqParams());
			this.source.get();
		},
		
		clearVal : function(){
			this.value = this.setVal("");
			this.data = {};
		},
		
		setVal : function(val){
			this.value = this.getInfoByVal(val);
			// 如果值不为空，则判断当前视图已经选择了值
			if(!$.isEmptyObject(this.value)){
				this.doFinish(true);
			}else{
				this.doFinish(false);
			}
		},
		
		doFinish : function(isFinish){
			if(this.parent.doFinish){
				this.parent.doFinish(isFinish, this.config.info.index);
			}
		},
		
		getInfoByVal : function(val){
			var rs = {};
			if(val){
				rs = this.data[val];
			}
			return rs;
		},
		
		validateData : function(){
			var rs = true;
			var data = this.getKeyData();
			if($.isEmptyObject(data)){
				rs = false;
			}
			return rs;
		},
		
		getKeyData : function(){
			return this.value;
		},
		
		formateSourceData: function (infos) {
			
	    }
	});
	
	var PluginBaseView = BaseView.extend({
		
		formateSourceData: function (infos) {
			this.data = {};
	        for(var i = 0; i < infos.length; i++){
	        	var info = infos[i];
	        	this.data[info.id] = info;
	        }
	        return this.data;
	    },
	    
	    // 路径
	    getPaths : function(){
	    	var paths = [];
	    	var preWgt = this.preWgt;
	    	while(preWgt){
	    		var preKeyData = preWgt.getKeyData() || {};
	    		paths.push(preKeyData.name || "");
	    		preWgt = preWgt.preWgt;
	    	}
	    	return paths;
	    }
	});
	
	// 应用视图
	var AppView = BaseView.extend({
		// 暂废弃 支持按分类搜索
		drawSearch2 : function(container){
			var self = this;
			this.searchDom = $("<div class='content-search' />").appendTo(container);
			var $selectWrap = $("<div class='search-select-wrap'>").appendTo(this.searchDom);
			var $inputWrap = $("<div class='search-input-wrap'>").appendTo(this.searchDom);
			
			// 选择类型
			var searchSelectHtml = "<select class='search-select'><option value='app'>应用</option><option value='cate'>分类</option></select>";
			this.searchTypeDom = $(searchSelectHtml).appendTo($selectWrap);
			
			this.searchInputDom = $("<input type='text' placeholder='请输入搜索内容' class='search-input'/>").appendTo($inputWrap);
			var $searchIcon = $("<i class='search-icon lui_icon_s_icon_search'/>").appendTo($inputWrap);
			// 监听enter事件
			this.searchInputDom.on("keyup", function(event){
				if (event && event.keyCode == '13') {
					self.doSearch();
				}
			});
			// 搜索
			$searchIcon.on("click", function(){
				self.doSearch();
			});
			
		},
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.curAppId = cfg.info.curAppId || "";
	    },
		
		doSearch : function(){
			this.clearVal();
			var params = this.getReqParams();
			if(params.searchType === "app"){
				this.source.url = this.source._url + "&c.like.fdAppName=!{searchVal}";
			}else if(params.searchType === "cate"){
				this.source.url = this.source._url + "&c.like.fdCategory.fdName=!{searchVal}";
			}
			
			var tempData = {};
			$.extend(tempData, this.source.vars, params);
			this.source.url = strutil.variableResolver(this.source.url, tempData);
			
			var ts = (new Date()).getTime();
			this.source.url += "&t=" + ts;
			
			this.source.get();
		},
		
		getReqParams : function(){
			var reqParams = {};
			var searchVal = this.searchInputDom.val();
			var searchType = "app";
			if(this.searchTypeDom){
				searchType = this.searchTypeDom.val() || "app";
			}
			$.extend(reqParams, {
				searchVal : searchVal,
				searchType : searchType
			});
			if(this.preWgt){
				var preKeyData = this.preWgt.getKeyData();
				$.extend(reqParams, preKeyData);
			}
			return reqParams;
		},
		
		isSourceDataEmpty : function(data){
			return !data || $.isEmptyObject(data) || (data.apps && data.apps.length === 0);
		},
		
		// 对应用数据，进行分类处理
		formateSourceData: function (appInfos) {
	        var rs = {};
	        rs.noCategorys = [];
	        rs.categorys = {};	//{"xxx(categoryId)" : {"name":"xxx","order":"xxx","apps":[{...},{...}]} }
	        for (var i = 0; i < appInfos.length; i++) {
	            var appInfo = appInfos[i];
	            if (appInfo.categoryId) {
	                if (!rs.categorys.hasOwnProperty(appInfo.categoryId)) {
	                    rs.categorys[appInfo.categoryId] = {};
	                    rs.categorys[appInfo.categoryId]["apps"] = [];
	                }
	                rs.categorys[appInfo.categoryId].name = appInfo.categoryName;
	                rs.categorys[appInfo.categoryId].order = appInfo.categoryOrder;
	                rs.categorys[appInfo.categoryId].apps.push(appInfo);
	            } else {
	                rs.noCategorys.push(appInfo);
	            }
	            this.data[appInfo.id] = appInfo;
	        }
	        return rs;
	    }
	});
	
	// 业务表单视图
	var ModelView = PluginBaseView.extend({
		
		isSourceDataEmpty : function(data){
			return !data || $.isEmptyObject(data) || (data.formInfos && data.formInfos.length === 0);
		}
	   
	});
	
	exports.BaseView = BaseView;
	exports.PluginBaseView = PluginBaseView;
	exports.AppView = AppView;
	exports.ModelView = ModelView;
})