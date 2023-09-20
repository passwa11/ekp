define([ "dojo/_base/declare", "mui/util" ], function(declare, util) {

	return declare("sys.mportal.TopMixin", null, {

		postCreate : function() {
			this.connect(this.domNode, "onclick", '_onClick');
			this.connectToggle();
		},
		
		toTop : function(evt) {

			var time = setInterval(function(){
				if(document.body.scrollTop > 200){
					document.body.scrollTop = document.body.scrollTop -200;
				}else{
					document.body.scrollTop = 0;
				}
				if(document.body.scrollTop == 0){
					clearInterval(time);
				}
			},50);
			
			//document.body.scrollTop = 0;
		},

		onScroll : function() {

			var top = document.body.scrollTop;

			var beShow = false;

			if (top > this.size.h / 2)
				beShow = true;

			if (beShow && !this._show)
				this.show();

			if (!beShow && this._show)
				this.hide();

		},

		connectToggle : function() {

			this.size = util.getScreenSize();

			this.connect(window, 'scroll', 'onScroll');

		}
	});
});