/**
 *  汇总审批数据内容
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		source = require('lui/data/source'),
		render = require('lui/view/render'),
		dialog = require('lui/dialog'),
		topic = require('lui/topic');
	
	var View = base.DataView.extend({
		
		elementTemp : null,
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.container = cfg.container || null;
			this.storeData = cfg.datas || {};
			this.parent = cfg.parent;
			this.params = cfg.params;
			this.node = cfg.node;
		},
		
		startup : function($super, cfg) {
			if (!this.render) {
				this.setRender(new render.Template({
					src : "/sys/lbpmservice/support/lbpm_summary_approval/config/render.html#",
					parent : this
				}));
				this.render.startup();
			}
			if (!this.source) {
				this.setSource(new source.Static({
							datas : this.storeData,
							parent : this
						}));
				this.source.startup();
			}
			$super(cfg);
			topic.channel(this).subscribe("paging.changed",this.pageChange,this);
		},
		
		pageChange:function(evt){
			this.dataLoading = dialog.loading();
			var params = {};
			if(evt && evt.page){
				for(var i=0; i<evt.page.length; i++){
					var page = evt.page[i];
					if(page.key == 'pageno'){
						params['pageno'] = page.value[0];
					}else if(page.key == 'rowsize'){
						params["rowsize"] = page.value[0];
					}
				}
			}
			//刷新页面
			this.parent.flushView(params);
		},
		
		reRender : function(datas){
			if(datas){
				this.source.config.datas = datas;
			}
			this.source.get();
		},
		
		draw : function($super, cfg){
			if(this.isDrawed)
				return;
			this.isDrawed = true;
			
			this.element.appendTo(this.container).addClass("process-view");
			this.load();
			this.elementTemp = this.element;
		},
		
		doRender : function($super, cfg){
			this.element = this.elementTemp;
			$super(cfg);
			var self = this;
			/********************* 添加事件 start *****************/
			$(".processPass").on('click',function(){
				var processId = $(this).parents(".process-item").eq(0).attr("data-id");
				var processName = $(this).parents(".process-item").eq(0).attr("data-name");
				self.showOperDialog(processId,'pass',false,processName);
				//self.handleProcess(processId,'pass');
			})
			$(".processRefuse").on('click',function(){
				var processId = $(this).parents(".process-item").eq(0).attr("data-id");
				var processName = $(this).parents(".process-item").eq(0).attr("data-name");
				self.showOperDialog(processId,'refuse',false,processName);
				//self.handleProcess(processId,'refuse');
			})
			$("#batchPassBtn").on('click',function(){
				var processIds = [];
				var list = $("[name='List_Selected']:checked");
				for(var i=0; i<list.length; i++){
					var data = self.getProcessDataById(list[i].value);
					if(data && data.btn.isFastApprove){//存在通过按钮时
						processIds.push(list[i].value);
					}
				}
				if(processIds.length <= 0){
					dialog.alert('您没有选择需要通过的数据');
					return;
				}
				self.showOperDialog(processIds.join(';'),'pass',true);
			})
			$("#batchRejectBtn").on('click',function(){
				var processIds = [];
				var list = $("[name='List_Selected']:checked");
				for(var i=0; i<list.length; i++){
					var data = self.getProcessDataById(list[i].value);
					if(data && data.btn.isFastReject){//存在驳回按钮时
						processIds.push(list[i].value);
					}
				}
				if(processIds.length <= 0){
					dialog.alert('您没有选择需要驳回的数据');
					return;
				}
				self.showOperDialog(processIds.join(';'),'refuse',true);
			})
			$(".summary-li-value").mouseover(function(){
				var text = $(this).children(".summary-li-value-tip")[0].innerText;
				if(text){
					var contentWidth = self.getContentWidth(this.innerText);
					var maxWdith = $(this).css("max-width").replace("px","");
					if(contentWidth > parseInt(maxWdith)/7){
						var config ={
							minWidth:250,
							maxWidth:500,
							maxRow:8,
							fontSize:7,
							targetDom:$(this)[0],
							tipDom:$(this).children(".summary-li-value-tip")[0]
						}
						self.getTipWidth(text,config);
						$(this).children(".summary-li-value-tip").show();
					}
				}
			})
			$(".summary-li-value").mouseout(function(){
				$(this).children(".summary-li-value-tip").hide();
			})
			$(".td-value").mouseover(function(){
				var text = $(this).children(".td-value-tip")[0].innerText;
				if(text){
					var contentWidth = self.getContentWidth(this.innerText);
					var domWidth = $(this).width();
					if(contentWidth*7 > domWidth*2){//超过两行就要提示
						var config ={
							minWidth:250,
							maxWidth:500,
							maxRow:8,
							fontSize:7,
							targetDom:$(this)[0],
							tipDom:$(this).children(".td-value-tip")[0]
						}
						self.getTipWidth(text,config);
						$(this).children(".td-value-tip").show();
					}
				}
			})
			$(".td-value").mouseout(function(){
				$(this).children(".td-value-tip").hide();
			})
			/********************* 添加事件 end *****************/
		},
		
		getContentWidth : function(s){
			var len = 0;
			for (var i=0; i<s.length; i++) { 
				var c = s.charCodeAt(i); 
				 if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
				       len++;
				     }  else {
				         len+=2;
				     } 
			}
			return len;
		},
		
		getTipWidth : function(text,config){
			var minWidth = config.minWidth;
			var maxWidth = config.maxWidth;
			var maxRow = config.maxRow;
			var fontSize = config.fontSize;
			var targetDom = config.targetDom;
			var tipDom = config.tipDom;
			
			var length = this.getContentWidth(text);
			var minCharCount = minWidth/fontSize;
			var maxCharCount = maxWidth/fontSize;
			if(length/minCharCount > maxRow){//超出maxRow行
				var rowCount = length/minCharCount - maxRow;
				var charCount = rowCount*minCharCount/maxRow + minCharCount;
				if(charCount > maxCharCount){//超出了最大宽度限制，宽度按最大宽度来，需要考虑高度问题，避免被挡
					$(tipDom).css("width",maxCharCount*fontSize+"px");
					//获取元素高度和相对窗口的位置
					var domHeight = $(tipDom).outerHeight();
					var top = $(targetDom).offset().top;
					//若高度高于top，要换到下边显示
					if(domHeight > top){
						$(tipDom).addClass("down");
					}else{
						$(tipDom).removeClass("down");
					}
				}else{//没有超出最大宽度限制，那么就按规定的最大行数显示
					$(tipDom).css("width",charCount*fontSize+"px");
				}
			}else{//未超出maxRow行
				if(length/minCharCount < 1){//只有一行
					$(tipDom).css("width",length*fontSize+"px");
				}else{
					$(tipDom).css("width",minCharCount*fontSize+"px");
				}
			}
		},
		
		getProcessDataById:function(processId){
			for(var i=0; i<this.storeData.length; i++){
				if(this.storeData[i].fdId == processId){
					return this.storeData[i];
				}
			}
			return null;
		},
		
		showOperDialog:function(processId,type,isMulti,processName){
			var self = this;
			var url ="/sys/lbpmservice/support/lbpm_summary_approval/config/approval_dialog.jsp";
			window.isMulti = isMulti;
			window.operationType = "handler_"+type;
			window.processName = processName;
			if(isMulti){
				window.processId = processId.split(";")[0];
			}else{
				window.processId = processId;
			}
			var href = location.href;	
			window.nodeFactId = Com_GetUrlParameter(href,"fdNodeFactId");
			window.fdConfigId = Com_GetUrlParameter(href,"fdConfigId");
			var title = isMulti ? "批量" : "";
			if(type == 'pass'){
				title += '通过';
			}else if(type == 'refuse'){
				title += '驳回';
			}
			var _callback = function(data){
				if(data && data.type == 'ok'){
					self.dataLoading = dialog.loading();
					//审批成功，更新数据和分页
					var dataPaging = LUI("dataPaging");
					var params = {};
					params['pageno'] = dataPaging.currentPage;
					params["rowsize"] = dataPaging.pageSize;
					self.parent.flushView(params);
				}
			}
			var _config = {
				params:{
					"processId":processId,
					'type':type
				},
				width:600,
				height:400
			};
			dialog.iframe(url, title, _callback, _config);
		}
	});
	
	exports.View = View;
		
})