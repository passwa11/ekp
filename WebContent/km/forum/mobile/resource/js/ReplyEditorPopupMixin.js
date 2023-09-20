define([ "dojo/_base/declare", "mui/form/editor/EditorPopupMixin","dojo/topic",
		"dojo/string","mui/util","dojo/_base/lang","dojo/request","dojo/Deferred",
		"dojo/query","dojo/when","mui/i18n/i18n!km-forum:KmForumPost.notify.title.anonReply"],
		function(declare,EditorPopupMixin,topic,string,util,lang,request,Deferred,query,when,Msg) {
	
	return declare("km.forum.ReplyEditorPopupMixin", EditorPopupMixin, {
		
		
		fdIsAnonymous : "0",
		
		buildRendering : function() {
			var params = {};
			params["anonReplyText"]= Msg["KmForumPost.notify.title.anonReply"];
			if(this.layout != null && this.layout != ""){
				this.layout = util.urlResolver(this.layout,params);
			}
			
			if(this.canAnonymous){
				this.subscribe('mui/form/checkbox/valueChange', function(widget, args) {
					if (args.name == 'fdIsAnonymous' && document.getElementsByName(args.name).length > 0 ) {
			            var field = document.getElementsByName(args.name)[0];
			            if(field.checked){
			            	this.fdIsAnonymous = "1";
			            }else{
			            	this.fdIsAnonymous = "0";
			            }
			        }
			    });
			}
			this.inherited(arguments);
		},
		onEditorClick : function() {
			this.inherited(arguments);
			this.textClaz.editorDeferred.then(lang.hitch(this, function() {
				if(!this.canAnonymous && query('.muiCheckItem',this.textClaz.domNode).length > 0){
					query('.muiCheckItem',this.textClaz.domNode)[0].style.display = "none";
					this.fdIsAnonymous = "0";
				}
			}));
		},
		
		// 提交按钮
		onSubmit : function(evt) {
			for (var i = 0; i < this.validates.length; i++) {
				if (this.validates[i].call(this, this) == false)
					return;
			}
			var data = this.buildForm();
			this.disconnect(this.submitHandle);
			data["fdIsAnonymous"] = this.fdIsAnonymous;
			console.log(this._url);
			var promise = request.post(util.formatUrl(string.substitute(
					this._url, this)), { 
				data : data
			});
			var self = this;
			promise.response.then(function(data) {
				self.deferred = new Deferred();
				self.hideMask();
				when(self.deferred.promise, lang.hitch(self,
						self.afterHideMask, data));
			});
		}
	});
});	
	