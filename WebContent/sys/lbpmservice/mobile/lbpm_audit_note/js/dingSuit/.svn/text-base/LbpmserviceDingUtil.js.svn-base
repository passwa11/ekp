define([
  "dojo/_base/declare",
  "dojo/_base/window",
  "dojo/on",
  "dojo/query",
  "mui/base64",
  "dojo/Deferred",
  "dojo/promise/all"
], function(declare, win, on, query, base64, Deferred, all) {
  var claz = declare("mui.lbpm.service.audit.util", null, {
  	/********************************** 钉钉专用 ***********************************/
	generateDefaultDingImg: function(fdName) {
		var dingImg = "<span class='mui-address-imgcontainer-sm mui-ding-address-imgcontainer-sm'>"
		+ "<span class='mui-ding-address-name'>"
		+ this.getDingImgName(fdName)
		+ "</span></span>";
		return dingImg;
	},
	
	getDingImgName: function(fdName){
		let showName = fdName || ''; 
		let arr =[];
		let _isEnglish = showName.match(/^([a-zA-Z]|\s|,|\.)+$/) !== null ? true : false; 
		if (_isEnglish) {
			//英 文 名 字 将 转 为 空 格 & 将 连 续 空 格 转 换 为 单 个 空 格
			showName = showName.replace(/,|\./g, ' ').replace(/\s+/g,' ');
			arr = showName.split(' ');
			if (arr.length === 1) {
				return showName.slice(0, 2);
			}
			return arr[0].slice(0, 1) + arr[1].slice(0, 1);
		}
		//中 文 名 字 - 取 后 两 位
		return showName.replace(/,|\.|\s+/g, '').slice(-2);
	}
	/********************************** 钉钉专用 ***********************************/	 	 
  });
  return new claz();
});
