define([
    "./iconUtils",
    'mui/dialog/Tip',
    'dojox/mobile/SwapView',
    'mui/i18n/i18n!sys-mobile',
    'dojo/NodeList-dom',
	'dojo/NodeList-html',
	'dojo/NodeList-manipulate',
	'dojo/NodeList-traverse'
	], function(iconUtils, Tip, SwapView,Msg) {
	if (!window.building) {
		window.building = function(){
			Tip.tip({
				icon : 'mui mui-warn',
				text : Msg['mui.btn.building']
			});
		};
	}
	// 修改 w/4 为 w/2
	SwapView.prototype.slideTo = function(/*Object*/to, /*Number*/duration, /*String*/easing, /*Object?*/fake_pos){
		// summary:
		//		Overrides dojox/mobile/scrollable.slideTo().
		if(!this._beingFlipped){
			var w = this.domNode.offsetWidth;
			var _w = this.w || 2;
			var pos = fake_pos || this.getPos();
			var newView, newX;
			if(pos.x < 0){ // moving to left
				newView = this.nextView(this.domNode);
				if(pos.x < -w/_w){ // slide to next
					if(newView){
						to.x = -w;
						newX = 0;
					}
				}else{ // go back
					if(newView){
						newX = w;
					}
				}
			}else{ // moving to right
				newView = this.previousView(this.domNode);
				if(pos.x > w/_w){ // slide to previous
					if(newView){
						to.x = w;
						newX = 0;
					}
				}else{ // go back
					if(newView){
						newX = -w;
					}
				}
			}

			if(newView){
				newView._beingFlipped = true;
				newView.slideTo({x:newX}, duration, easing);
				newView._beingFlipped = false;
				newView.domNode._isShowing = (newView && newX === 0);
			}
			this.domNode._isShowing = !(newView && newX === 0);
		}
		// this.inherited(arguments);
		this._runSlideAnimation(this.getPos(), to, duration, easing, this.containerNode, 2);
		this.slideScrollBarTo(to, duration, easing);
	};
	if (typeof String.prototype.startsWith != 'function') {
		 String.prototype.startsWith = function (prefix){
			  return this.slice(0, prefix.length) === prefix;
		 };
	}
	return {"iconUtils": iconUtils, "building": building};
});