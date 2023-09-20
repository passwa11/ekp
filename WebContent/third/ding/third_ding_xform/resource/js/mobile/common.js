define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/query",
	"dojo/_base/array",
	"dojo/dom-class",
	"dojo/dom-style"
],function(declare, domConstruct, query, array, domClass, domStyle){
	
	var Common = declare("third.ding.third_ding_xform.resource.js.mobile.common", null , {
		
		getStatus : function(){
			return Xform_ObjectInfo.mainDocStatus;
		},
		
		getWidgetFrom$Form : function($formWidget){
			return $formWidget.target.widget;
		},
		
		// 查找所有class为【ding_mobile_divide_flag】标识的div，在它所在行的上方添加一个分隔行并删除该标识
		addDivdieTr : function(){
			var divideFlagDomArr = query(".ding_mobile_divide_flag");
			array.forEach(divideFlagDomArr, function(divideFlagDom){
				var trDom = query(divideFlagDom).closest("tr");
				var newTrDom = domConstruct.create("tr", {
					"classname" : "ding_divide"
				}, trDom[0], "before");
				newTrDom.innerHTML = "<td colspan='2'></td>";
				// 把上一行的td的border-bottom设置为0px
				var prevTrDom = query(newTrDom).prev();
				if(prevTrDom && prevTrDom.length > 0){
					var tdArr = prevTrDom.query("td");
					array.forEach(tdArr, function(tdDom){
						domStyle.set(tdDom,"border-bottom","0px");
					});
				}
			});
			divideFlagDomArr.remove();
		},
		
		// 右侧风格
		setRightStyle : function(){
			var muiFormLeftDomArr = query(".muiFormLeft");
			array.forEach(muiFormLeftDomArr, function(muiFormLeftDom){
				domClass.replace(muiFormLeftDom, "muiFormRight", "muiFormLeft");
			});
		}
	});
	
	Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
	
	return new Common();
})