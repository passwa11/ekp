define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "sys/praise/mobile/import/js/_PraiseTabBarButtonMixin","mui/dialog/Tip",
    "mui/i18n/i18n!km-forum:kmForumPost.support",
    "mui/i18n/i18n!km-forum:kmForumPost.postCancleSupport"
	], function(declare, domConstruct, _PraiseTabBarButtonMixin,Tip,Msg1,Msg2) {
	var create = declare("km.forum.mobile.resource.js.ForumTopicParseMixin", [_PraiseTabBarButtonMixin], {
		lock : false,
		_reDrawLabel:function(){
			if(this.labelNode){
				if(this.count>0){
					this.labelNode.innerHTML = Msg1['kmForumPost.support']+"("+this.count+")";
				}else{
					this.labelNode.innerHTML =Msg1['kmForumPost.support'];
				}
			}
		},
		
		_setCountAttr:function(count){
			this._set('count',count);
			this._reDrawLabel();
		},
		togglePraised : function(isInit) {
			
			if(this.lock) return;
			this.lock = true;
	        this.defer(function(){
	            this.lock = false;
	          },450);
	        
			if(!isInit){
				if(this.isPraised){
					Tip.tip({
						icon : 'mui '
								+ this.praisedClass,
						text : Msg1['kmForumPost.support']+'+1'
					});
					this._set('count',this.count + 1);
				}else{
					Tip.tip({
						icon : 'mui '
								+ this.unPraisedClass,
						text : Msg2['kmForumPost.postCancleSupport']
					});
					this._set('count',this.count - 1);
				}
			}
			if(this.icon1){
				this.replaceClass(this.isPraised);
				this.removeScaleClass();
			}	
			this._reDrawLabel();
		}
	});
	return create;
});