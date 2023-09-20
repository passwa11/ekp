define(function(require, exports, module){
	var topic = require('lui/topic');
	var $ = require("lui/jquery");
	var env = require("lui/util/env");
	var fn = function(evt) {
		if(!evt) return;
		var actionId = evt.fdPersonId;
		if("unfollowed" == evt.isFollowed) {
			$("[data-role-fansnum='"+  actionId + "']").each(function(index) {
				var $this = $(this), num = $this.html().trim();
				if(num>= 0) {
					$this.html( ++ num);
				}
			});
			$("[data-role-follownum='" +  Com_Parameter.CurrentUserId + "']").each(function(index) {
				var $this = $(this), num = $this.html().trim();
				if(num>= 0) {
					$this.html( ++ num);
				}
			});
			
		} else if("followed" == evt.isFollowed) {
			$("[data-role-fansnum='"+  actionId + "']").each(function(index) {
				var $this = $(this), num = $this.html().trim();
				if(num>= 1) {
					$this.html( -- num); 
				}
			});
			$("[data-role-follownum='" +  Com_Parameter.CurrentUserId + "']").each(function(index) {
				var $this = $(this), num = $this.html().trim();
				if(num>= 0) {
					$this.html(-- num);
				}
			});
		}
	}
	
	
	topic.channel("fans").subscribe("FANS.FOLLOW", fn);
	
	if(window.domain) {
		window.domain.register("fansChange", fn);
	}
	
});