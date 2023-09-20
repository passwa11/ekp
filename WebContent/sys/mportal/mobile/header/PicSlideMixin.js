define([ "dojo/_base/declare", "dojo/_base/array", "mui/util","dojo/topic","sys/mportal/mobile/PicItem"], function(
		declare, array, util,topic,PicItem) {
	var mixin = declare("sys.mportal.PicSlideMixin", null, {
		
		itemRenderer : PicItem,
		
		onComplete : function(items) {
			items = this.f(items)
			this.inherited(arguments);
		},

		// 格式化
		f : function(items) {
			var _items = [];
			array.forEach(items, function(item, index) {
				var _item = {};
				_item.url = util.formatUrl(item.image);
				_item.href = item.href;
				_item.label = item.text;
				_items.push(_item);
			}, this);
			items = null;
			return _items;
		}
	});
	return mixin;
});