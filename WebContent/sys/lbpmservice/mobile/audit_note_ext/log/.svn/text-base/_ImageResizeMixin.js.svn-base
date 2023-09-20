/**
 * 只调用图片js预览接口
 */

define(["dojo/_base/declare","dojo/dom-style","dojo/dom-attr","dojo/on","mui/device/adapter"],
		function(declare,domStyle,domAttr,on,adapter){
	return declare("sys.lbmpservice.auditNoteExt._ImageResizeMixin",null,{
		resizeDom : function(item) {
			domAttr.remove(item, "style");
			var styleVar = {
				'height' : 'auto',
				'textIndent' : '0px',
				"max-width" : '100%'
			};
			domStyle.set(item, styleVar);
			var self = this;
			var src = item.src;
			if(src.indexOf('picthumb')>0){
				src = src.replace('&picthumb=big','');
			}
			on(item, "click", function(evt) {
				adapter.imagePreview({
					curSrc : src, 
					srcList : self.getSrcList(),
					previewImgBgColor : self.previewImgBgColor
				});
			});
		}
	});
});
