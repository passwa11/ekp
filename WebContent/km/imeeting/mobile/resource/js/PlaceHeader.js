define( [ "dojo/_base/declare", "mui/category/CategoryHeader"], function(declare,CategoryHeader) {
		var header = declare("km.imeeting.PlaceHeader", [ CategoryHeader], {
				//获取详细信息地址
				detailUrl : '/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=listCategoryDetail&categoryId=!{curId}'
			});
			return header;
});