/**
 *  汇总审批弹窗内容
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		source = require('lui/data/source'),
		render = require('lui/view/render'),
		topic = require('lui/topic');
	
	var View = base.DataView.extend({
		
		elementTemp : null,
		
		initProps: function($super, cfg) {
			$super(cfg);
			this.container = cfg.container || null;
			this.storeData = cfg.data || {};
			this.parent = cfg.parent;
			this.params = cfg.params;
			this.optionButtom = cfg.optionButtom;
		},
		
		startup : function($super, cfg) {
			if (!this.render) {
				this.setRender(new render.Template({
					src : "/sys/lbpmservice/support/lbpm_summary_approval/render.html#",
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
		},
		
		draw : function($super, cfg){
			if(this.isDrawed)
				return;
			this.isDrawed = true;
			
			this.element.appendTo(this.container).addClass("panel-tab-main-view");
			this.load();
			this.elementTemp = this.element;
		},
		
		doRender : function($super, cfg){
			this.element = this.elementTemp || this.element;
			$super(cfg);
			var self = this;
			/********************* 添加事件 start *****************/
			$(".processPass").on('click',function(){
				var processId = $(this).parents(".content-item").eq(0).attr("data-id");
				self.handleProcess(processId,'pass');
			})
			$(".processRefuse").on('click',function(){
				var processId = $(this).parents(".content-item").eq(0).attr("data-id");
				self.handleProcess(processId,'refuse');
			})
			/********************* 添加事件 end *****************/
		},
		
		handleProcess : function(processId,opType){
			var self = this;
			var params = {
				"processId" : processId,
				"opType" : opType
			};
			//Ajax请求后台计算决策节点的分支
			$.ajax({
				type : 'post',
				async : false, //指定是否异步处理
				data : params,
				dataType : "json",
				url : Com_Parameter.ContextPath
						+ "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=approveOne",
				success : function(data) {
					seajs.use("lui/dialog" , function(dialog) {
						var code = data.code;
						if(code == 1){//处理成功
							dialog.success('操作成功!',null,function(){
								//回调，重画
								self.parent.reDraw();//整个容器重画
							},null,null,{
								topWin:window,
								autoCloseTimeout:0.5
							});
						}else{//处理失败
							dialog.failure('操作失败，请检查流程是否异常！',null,null,null,null,{
								topWin:window,
								autoCloseTimeout:0.5
							});
						}
				  	});
				}
			});
		}
	});
	
	exports.View = View;
		
})