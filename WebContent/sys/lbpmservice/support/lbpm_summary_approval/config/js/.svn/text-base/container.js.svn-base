/**
 * 汇总审批页面
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		view = require('sys/lbpmservice/support/lbpm_summary_approval/config/js/view');
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	
	var Container = base.Container.extend({
		
		data : {},
		
		curView:{},//当前的数据内容
		
		params : {},
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.headerContainer = $("." + this.config.headerClass);
			this.viewContainer = $("." + this.config.mainClass);
			if(cfg.storeData){
				this.datas = cfg.storeData || {};
			}
			if(cfg.params){
				this.params = cfg.params || {};
			}
		},
		
		startup : function($super, cfg) {
			$super(cfg);
			var _self = this;
			//详情页流程审批后回调
			topic.subscribe('successReloadPage', function() {
				var dataPaging = LUI("dataPaging");
				var params = {};
				params['pageno'] = dataPaging.currentPage;
				params["rowsize"] = dataPaging.pageSize;
				_self.flushView(params);
			});
		},
		
		draw : function($super, cfg){
			var self = this;
			//画出模板
			this.drawHeader();
			//画出流程
			this.drawView();
			this.element.show();
			
			topic.publish("list.changed");
		},
		
		/****************** 头部 start **********************/
		drawHeader : function(){
			var headerHtml = this.getHeaderHtml();
			var $header = $(headerHtml).appendTo(this.headerContainer);
			var self = this;
			/** ********** 添加事件 start *********** */
			/*$header.find(".template-item").on('click',function(){
				var tagId = $(this).attr("data-id");
				self.curTemplateId = tagId;
				self.flushView($(this));
			})*/
			/** ********** 添加事件 end *********** */
			
		},
		
		getHeaderHtml : function(){
			var self = this;
			var node = self.datas.node || {};
			//页签
			var html = '<div class="header-items">';
			html += '<div class="header-item active" data-id="'+node.nodeId+'" title="'+node.nodeName+'">'+node.nodeName+'</div>';
			html += '</div>';
			
			return html;
		},		
		/****************** 头部 end **********************/
		/****************** 内容区 start ******************/
		drawView : function(){
			// 设计基础，标题页面和视图组件通过data-wgt-id进行绑定
			var self = this;
			var viewWgt = new view["View"]({datas:self.datas.processes,node:self.datas.node,isBatchApprove:self.datas.isBatchApprove,isBatchReject:self.datas.isBatchReject,params:self.params,parent:this,container:this.viewContainer});
			viewWgt.startup();
			self.curView = viewWgt;
			viewWgt.draw();

			return viewWgt;
		},
		
		delView : function(viewWgt){
			// 删除视图
			viewWgt.destroy();
		},

		flushView : function(params){
			var self = this;
			var href = location.href;	
			var fdConfigId = Com_GetUrlParameter(href,"fdConfigId");
			var fdNodeFactId = Com_GetUrlParameter(href,"fdNodeFactId");
			var fdTime = Com_GetUrlParameter(href,"fdTime");
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=flushDatas&fdConfigId="+fdConfigId+"&fdNodeFactId="+fdNodeFactId+"&fdTime="+fdTime;
			params = params || {};
			for(var key in params){
				var param = "&" + key + "=" + params[key];
				url += param;
			}
			$.ajax({
			  url: url,
			  type:'GET',
			  async:false,//同步请求
			  success: function(json){
				  if(json.processEmpty == true){
					  if(self.curView.dataLoading){
						  self.curView.dataLoading.hide();
					  }
					  //空数据，刷新页面
					  $("#emptyContainer").css("display","block");
					  $("#noEmptyContainer").css("display","none");
					  setTimeout(function(){
						  //直接关闭页面
						  Com_CloseWindow();
					  }, 500);
				  }else{
					  self.datas = json;
					  //刷新数据
					  self.curView.reRender(self.datas.processes);
					  //刷新分页器
					  var evt = {'page':{}};
					  evt['page'].currentPage = self.datas.currentPage;
					  evt['page'].pageSize = self.datas.pageSize;
					  evt['page'].totalSize = self.datas.totalSize;
					  topic.publish("list.changed",evt);
					  if(self.curView.dataLoading){
						  self.curView.dataLoading.hide();
					  }
				  }
			  },
			  dataType: 'json'
			});
		}
		/****************** 内容区 end ********************/
	});
	
	exports.Container = Container;
})