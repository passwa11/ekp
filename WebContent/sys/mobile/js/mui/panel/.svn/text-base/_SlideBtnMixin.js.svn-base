define(
		[ "dojo/_base/declare",
				"dojo/topic" ],
		function(declare, topic) {

			return declare(
					"mui.panel._SlideBtnMixin",
					null,
					{	
						
						onClick : function(evt) {
							this._showLeftSlide(evt);
						},
					   
						//显示左边目录
						_showLeftSlide : function(evt) {
							topic.publish('/mui/panel/slidePanel/show', this);
						}
					});
		});