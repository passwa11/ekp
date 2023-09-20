define([ "dojo/_base/declare", "dojo/_base/lang","dojo/query", "km/forum/mobile/resource/js/ReplyEditorPopupMixin" ], function(
		declare, lang,query, ReplyEditorPopupMixin) {

	return new declare("km.forum.ReplyEditorUtil", ReplyEditorPopupMixin, {

		// 弹出框
		popup: function(url, options, callback) {
			console.log(url);
			var config = {_url : url,afterHideMask : callback};
			config = lang.mixin(config,options ? options : {});
			var editor = new ReplyEditorPopupMixin(config);
			editor.onEditorClick.call(editor);
			return editor;
		},

		// 自适应
		resize: function() {

		}
	})();
});