/**
 * 
 */
define(['dojo/_base/declare','mui/view/DocScrollableView',"dojo/request", "mui/util", "dojo/topic"],
		function(declare, DocScrollableView, request, util, topic){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.pageView', [DocScrollableView], {
		
		url : "/sys/modeling/main/mobile/modelingAppMainMobile.do?method=getDefaultModeCfg&fdId=!{fdAppId}&fdMobileId=!{fdMobileId}",
		
		scrollDir : "v",
		
		fdAppId : "",
		fdMobileId:"",
		
		startup : function() {
			if (this._started)
				return;
			this.doLoad();
			// 物理按键点击返回时，需要重新加载
			this.connect(window,'pageshow','_pageshow');
			this.inherited(arguments);
		},
		
		_pageshow : function(evt){
			/* 注：经验证，只有第一次浏览器回退的时候persisted才会为true，所以必须使用页面刷新的方式来显示新的列表数据
			 * 不可以手动去发事件去更新列表，因为只有强制刷新整个页面后，才会使得每次浏览器回退时persisted都为true
			*/ 
			if(evt.persisted){	
				window.location.reload();
			}
		},
		
		doLoad : function(){
			console.log("doLoad");
			// 请求获取nav的信息
			var url = util.urlResolver(this.url, {fdAppId: this.fdAppId,fdMobileId: this.fdMobileId});
			var self = this;
			request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(json){
				// 处理请求返回的数据
				if(json && json.status === "00"){
					self.onComplete(json.data);		
				}
			});
		},
		
		onComplete : function(data){
			topic.publish('/sys/modeling/mobile/index/load', data);
		},
		
		getDim: function(){
			// summary:
			//		Returns various internal dimensional information needed for calculation.

			var d = {};
			// content width/height
			// 当前页面特殊，高度应使用scrollHeight
			d.c = {h:this.containerNode.scrollHeight, w:this.containerNode.offsetWidth};

			// view width/height
			d.v = {h:this.domNode.offsetHeight + this._appFooterHeight, w:this.domNode.offsetWidth};

			// display width/height
			d.d = {h:d.v.h - this.fixedHeaderHeight - this.fixedFooterHeight - this._appFooterHeight, w:d.v.w};

			// overflowed width/height
			d.o = {h:d.c.h - d.v.h + this.fixedHeaderHeight + this.fixedFooterHeight + this._appFooterHeight, w:d.c.w - d.v.w};
			return d;
		},
		
	});
});