
define(function(require, exports, module) {
	require("sys/lbpmservice/support/lbpm_template/batchEvent/css/eventSettingData.css");
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var paging = require("sys/lbpmservice/support/lbpm_template/batchEvent/eventSettingDataPaging");  // E:\ekp\ekpGit\ekpV16\WebContent\sys\lbpmservice\support\lbpm_template\batchEvent\eventBeanLoader.js
	var EventBeanLoader = require("sys/lbpmservice/support/lbpm_template/batchEvent/eventBeanLoader");
	var topic = require("lui/topic");

	var EventSettingDataView = base.DataView.extend({
		
		initProps : function($super, cfg){ 
			$super(cfg);
			// 格式化列定义
			EventBeanLoader.initColumns(this.config);
			// 当前所有记录
			this.setRecordDatas({});
			var self = this;
			topic.subscribe('eventsRefresh',function (events){
				self.refresh(events);
			});
		},

		// load在draw阶段执行
		load : function($super){
			this.onDataLoad({});
		},
		
		// 不能立即渲染，需要先进行分页处理
		onDataLoad : function(data){
			// 本组件统一使用this.config.records
			this.setRecordDatas(data);
			this.reRender({pageno:"1",rowsize:"10",totalSize:this.getRecordDatas().length});
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,cfg){
			$super(cfg);
			var self = this;
			/**************** 分页插件 start *****************/
			// 由于分页块放置外render外面不合适，只能放置在模板HTML里面，故渲染完毕需要重新new分页组件
			if(!self.config.allColInvalid){
				self.paging = new paging.MassDataPaging({
					parent : self,
					element : self.element.find(".massdata-foot-paging"),
					currentPage : self.config.currentPage,
					pageSize: self.config.pageSize,
					totalSize: self.config.totalSize,
					viewSize: "2"
				});
				self.paging.startup();
				self.paging.draw();
			}
			/**************** 分页插件 end *****************/
		},
		
		// 重新渲染 evt:{pageno:xxx,rowsiez:xxx,totalSize:xxx}
		reRender : function(evt){
			// 所有列删除标识
			this.config.allColInvalid = true;
			this.config.currentPage = (evt.pageno && evt.pageno > 0) ? evt.pageno : "1";
			this.config.pageSize = (evt.rowsize && evt.rowsize > 0) ? evt.rowsize : "10";
			this.config.totalSize = evt.totalSize;
			this.currentPageDatas = this.divideDatasByPage();
			this.render.get(this.currentPageDatas);
		},
		
		refresh : function(data){
			// 后面在想下怎么处理
			var self = this;
			setTimeout(function(){
				self.fetchData(data);
			},100);
			
		},

		fetchData : function(data){
			var cfg = this.config;
			cfg.data = data;
			var self = this;
			EventBeanLoader.request(cfg,function(d){
				self.onDataLoad(d);
			});
		},
		
		setRecordDatas : function(data){
			data = data || {};
			this.config.records = data;
		},
		
		getRecordDatas : function(){
			if(this.config.records.hasOwnProperty("records")){
				return this.config.records["records"];
			}else{
				return [];
			}
		},
		
		// 分页，分割数据
		divideDatasByPage : function(){
			var rs = [];
			var datas = this.getRecordDatas();
			if(datas.length == 0){
				return rs;
			}
			var startIndex = parseInt((this.config.currentPage - 1)*this.config.pageSize);
			var endIndex = parseInt(this.config.currentPage*this.config.pageSize);
			if(datas.length > startIndex && datas.length < endIndex){
				rs = datas.slice(startIndex);
			}else if(datas.length <= startIndex){
				// 如果起始索引比总数还多，直接返回最后一页的数据
				var groups = this.groupData(this.config.pageSize,datas);
				this.config.currentPage = groups.length;
				rs = groups[this.config.currentPage-1];
			}else if(datas.length >= endIndex){
				rs = datas.slice(startIndex,endIndex);
			}
			return rs;
		},
		
		groupData : function(proportion,datas){
		    var num = 0;
		    var _data =[];
		    for(var i = 0;i < datas.length;i++){
		        if(i % proportion == 0 && i != 0){
		            _data.push(datas.slice(num,i));
		            num = i;
		        }
		        if((i+1) == datas.length){
		            _data.push(datas.slice(num,(i+1)));
		        }
		    }
		    return _data;
		}
	});
	exports.EventSettingDataView = EventSettingDataView;
})