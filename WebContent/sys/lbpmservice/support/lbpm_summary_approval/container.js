/**
 * 汇总审批弹窗容器，header是模板，row是流程数据
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		view = require('sys/lbpmservice/support/lbpm_summary_approval/view');
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	var env = require('lui/util/env');
	
	var Container = base.Container.extend({
		
		data : {},
		
		curView:{},//当前的数据内容
		
		params : {},
		optionButtom : {},

		initProps: function($super, cfg) {
			$super(cfg);
			this.headerContainer = $("." + this.config.headerClass);
			this.viewContainer = $("." + this.config.mainClass);
			if(cfg.storeData){
				this.data = cfg.storeData || {};
			}
			if(cfg.params){
				this.params = cfg.params || {};
			}
			if(cfg.optionButtom){
				this.optionButtom = cfg.optionButtom || {};
			}
		},
		
		startup : function($super, cfg) {
			$super(cfg);
		},
		
		draw : function($super, cfg){
			//展示loading的效果
			this.element.show();
			this.loadingNode = $("<img src='"+env.fn.formatUrl('/sys/ui/js/ajax.gif')+"' />");
			this.element.append(this.loadingNode);
			var self = this;
			//画头部（模板）
			this.drawHeader(this.headerContainer);

			if(this.loadingNode){
				this.loadingNode.hide();
			}

			//画数据内容（流程）
			this.drawView();
			//调整tab-main的高度，避免因多页签太多将下面挤下去，滚动条有一部分不可见
			var _height = this.element.find(".panel-tab-header").height();
			this.element.find(".panel-tab-main").css("max-height",450-(_height-37));
		},
		
		//重画整个内容，在某些流程操作后回调
		reDraw : function(){
			var self = this;
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=flushData&modelName="+self.params.modelName;
			$.ajax({
			  url: url,
			  type:'GET',
			  async:false,//同步请求
			  success: function(json){
				  if(json.emptyData == true){
					  //空数据，刷新页面
					  window.location.reload();
				  }else{
					  self.data = json;
					  self.headerContainer.empty();
					  self.viewContainer.empty();
					  self.draw();
					  //回到上一个模板的位置
					  var itemDom = self.headerContainer.find("[data-id='"+self.params.templateId+"']")[0];
					  if(itemDom){
						  itemDom.click();
					  }
				  }
			  },
			  dataType: 'json'
			});
		},
		
		/****************** 头部 start **********************/
		drawHeader : function(container){
			var headerHtml = this.getHeaderHtml();
			var $header = $(headerHtml).appendTo(container);
			var self = this;
			/** ********** 添加事件 start *********** */
			$header.find(".summary-tab-item").on('click',function(){
				var tagId = $(this).attr("data-id");
				self.params.templateId = tagId;
				self.flushView($(this));
			})
			/** ********** 添加事件 end *********** */
			
		},
		
		getHeaderHtml : function(){
			var self = this;
			var headers = self.data.headers || [];
			var html = "<div id='summary-tab' class='summary-tab'>";
			// 页签区
			html = '<div class="summary-tab-items">';
			for(var i=0; i<headers.length; i++){
				var className = "";
				if(i==0){
					className = "active";
					if(!self.params.templateId){
						self.params.templateId = headers[i].fdId;
					}
				}
				html += '<div class="summary-tab-item '+className+'" data-id="'+headers[i].fdId+'" title="'+headers[i].fdName+'">'+headers[i].fdName+'</div>';
			}
			html += '</div>';
			html += '<div class="closeWin" onclick="Com_CloseWindow()">';
			html += '</div>';
			
			html += "</div>";
			
			return html;
		},		
		/****************** 头部 end **********************/
		/****************** 内容区 start ******************/
		drawView : function(){
			// 设计基础，标题页面和视图组件通过data-wgt-id进行绑定
			var self = this;
			var viewWgt = new view["View"]({data:self.data.rows,params:self.params,parent:this,container:this.viewContainer,optionButtom:this.optionButtom});
			viewWgt.startup();
			self.curView = viewWgt;
			viewWgt.draw();

			return viewWgt;
		},
		
		delView : function(viewWgt){
			// 删除视图
			viewWgt.destroy();
		},

		flushView : function(curDom){
			var self = this;
			curDom.siblings().removeClass("active");
			curDom.addClass("active");
			var url = Com_Parameter.ContextPath + "sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=flushData&modelName="+self.params.modelName+"&templateId="+self.params.templateId;
			$.ajax({
			  url: url,
			  type:'GET',
			  async:false,//同步请求
			  success: function(json){
				  if(json.emptyData == true){
					  //空数据，刷新页面
					  window.location.reload();
				  }else{
					  self.data = json;
					  //删除原有的内容，重建
					  self.delView(self.curView);
					  self.drawView();
				  }
			  },
			  dataType: 'json'
			});
		}
		/****************** 内容区 end ********************/
	});
	
	exports.Container = Container;
})