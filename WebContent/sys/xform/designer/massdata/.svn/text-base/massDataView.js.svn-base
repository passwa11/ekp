
define(function(require, exports, module) {
	require("sys/xform/designer/massdata/css/massdata.css");
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require("lui/dialog");
	var paging = require("sys/xform/designer/massdata/massDataPaging");
	var RequestBeanLoader = require("sys/xform/designer/massdata/requestBeanLoader");
	var ExcelDataLoader = require("sys/xform/designer/massdata/excelDataLoader");
	
	var MassDataView = base.DataView.extend({
		
		initProps : function($super, cfg){ 
			$super(cfg);
			this.dataLoader = this.getLoader(this.config._source);
			// 格式化列定义
			this.dataLoader.initColumns(this.config);
			
			// 当前所有记录
			this.setRecordDatas({});
				
		},
		
		// 数据加载器
		getLoader : function(source){
			var loader;
			if(source === "EXCEL"){
				loader = ExcelDataLoader;
			}else{
				loader = RequestBeanLoader;
			}
			return loader;
		},
		
		// 对sourceUrl里面的变量进行替换，load在draw阶段执行
		load : function($super){
			this.source.resolveUrl(this.config);
			$super();
		},
		
		// 请求完数据之后，不能立即渲染，需要先进行分页处理
		onDataLoad : function(data){
			// 本组件统一使用this.config.records
			this.setRecordDatas(data);
			this.reRender({pageno:"1",rowsize:"10",totalSize:this.getRecordDatas().length});
		},
		
		// 渲染完毕之后添加事件
		doRender : function($super,cfg){
			$super(cfg);
			var self = this;
			/****************** 添加按钮事件 start *************************/
			var $btn = self.element.find("[data-btn-type='getData']");
			$btn.html(this.dataLoader.getBtnLabel());
			$btn.click(function(){
				self.refresh();
			});
			// excel导出
			var $excelBtn = self.element.find("[data-btn-type='excelExport']");
			$excelBtn.click(function(){
				self.excelExport();
			});
			/****************** 添加按钮事件 end *************************/
			/**************** 无记录提示 start *****************/
			var table = self.element.find(".massdata-table")[0];
			// self.config.allColInvalid 所有列删除标识
			if(self.config.allColInvalid){
				var colspan = table.rows[0].cells.length;
				$(table).find("tbody").html("").append("<tr><td colspan=\""+ colspan +"\">暂无数据</td></tr>");
			}
			/**************** 无记录提示 end *****************/
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
			this.config.pageSize = (evt.rowsize && evt.rowsize > 0) ? evt.rowsize : "10";;
			this.config.totalSize = evt.totalSize;
			this.currentPageDatas = this.divideDatasByPage();
			this.render.get(this.currentPageDatas);
		},
		
		refresh : function(){
			// 后面在想下怎么处理
			var self = this;
			setTimeout(function(){
				self.fetchData();	
			},100);
			
		},
		
		showLoading:function(){
			if (!this.loadingDialog) {
				this.element.css('min-height', 200);
				this.loadingDialog = dialog.loading(null, this.element);
			}else{
				this.loadingDialog.show();
			}
		},
		
		hideLoading: function(){
			if (this.loadingDialog) {
				this.loadingDialog.hide();
				this.element.css('min-height', 'inherit');
				this.loadingDialog = null;
			}
		},
		
		fetchData : function(){
			var cfg = this.config;
			cfg.owner = this;
			var self = this;
			this.dataLoader.request(cfg,function(data){
				self.source.emit('data', data);
			});
		},
		
		setRecordDatas : function(data){
			data = data || {};
			this.config.records = data;
			// 把加载的数据存储到input表单元素里面，提交时拦截机制把该字段进行相关保存处理
			$("input[name='"+ this.config.name +"']").val(JSON.stringify(data));
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
		},
		
		// excel导出，导出原则：非所见即所导，只是后台跟前台一样的获取列数据逻辑，注意当table内容展示逻辑调整，后台excel导出也需要调整
		excelExport : function(){
			var config = {columns: this.config.columns , data: this.getRecordDatas(), label : this.config.subject};
			var url = Com_Parameter.ContextPath + "sys/xform/massdata/sysFormMassData.do?method=excelExport";
			var $form = $("<form>");               // 定义一个form表单
			$form.attr("style","display:none");  // form隐藏
			$form.attr("method","post");         // 请求类型（以post方式提交）
			$form.attr("action",url);    		// 模板文件下载请求地址
			$form.append(this.buildFormElementByParam({cfg:JSON.stringify(config)}));	// 把需要传输的内容放置到form元素里面
			$("body").append($form);             // 将表单放置在页面body中
			$form.submit();                      // 表单提交
			$form.remove();						// 删除form
		},
		
		buildFormElementByParam : function(formDatas){
			var div = document.createElement("div");
			//把传进来的json的全部属性转换
			for(var proName in formDatas){
				var opt = document.createElement("textarea");
				opt.style.display = 'none';
		        opt.name = proName;
		        opt.value = formDatas[proName];
		        div.appendChild(opt);
			}
			return div;
		}
	});
	
	exports.MassDataView = MassDataView;
})