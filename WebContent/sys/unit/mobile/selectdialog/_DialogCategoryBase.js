define( [ "dojo/_base/declare", "mui/form/_CategoryBase"],
		function(declare,_CategoryBase) {
			var cateOpt = declare("mui.selectdialog._DialogCategoryBase", _CategoryBase, {
				//列表数据获取URL
				listDataUrl: null,
				//获取详细数据(头部详细信息)
				detailUrl:null,
				//当多选的时候，获取底部选中的的信息（头部信息只能分类，底部有可能是分类或是文档或是模板）
				selectionUrl : null,
				//搜索获取URL
				searchDataUrl :null,
				//字段参数
				fieldParam:null,
				// 群组数据
				groupDataUrl:null,
				//交换单位数据
				decDataUrl:null,
				//发送单位ID
				fdUnitId:null
				});
			return cateOpt;
		});