define(function(require, exports, module) {
	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	
	exports.initColumns = function(cfg){
		// 初始化列参数
		cfg.excelcolumns = cfg.excelcolumns || "{}";
		cfg.excelcolumns = $.parseJSON(cfg.excelcolumns.replace(/quot;/g,"\""));
		// 格式化列定义，返回以字段名为key的json：{docSubject:{fieldTitle:xxx}}，方便数据通过key定位
		cfg.columns = formatColumns(cfg.excelcolumns);
	}

	exports.request = function(cfg,callback){
		var records = {};
		// 打开弹窗
		var url = "/sys/xform/designer/massdata/excelUpload.jsp";
		var height = 250;
		var width = document.documentElement.clientWidth * 0.6;
		dialog.iframe(url,"Excel导入",function(rs){
			// rs为空，当做关闭窗口
			if(rs){
				rs = $.parseJSON(rs);
				callback(rs.excel);
			}
		},{width:width,height : height,close:false,params:cfg.columns});
		return records;
	}
	
	exports.getBtnLabel = function(){
		return "Excel导入";
	}
	
	// excelcolumns : {column_xxx:{title:xxx,type:xxx}}
	function formatColumns(excelcolumns){
		var columns = {};
		$.extend(columns,excelcolumns);
		return columns;
	}

})