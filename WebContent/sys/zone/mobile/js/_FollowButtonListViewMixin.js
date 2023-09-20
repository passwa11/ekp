define(	["dojo/_base/declare", "dojo/_base/lang", "dojo/request", "mui/util", "dojo/dom-class", 
       	 "dojo/dom-attr", "dojo/dom-construct" ,"dojo/topic","mui/i18n/i18n!sys-zone:sysZonePerson.4m"], 
       	 function(declare, lang, req, util, domClass, domAttr, domConstruct, topic, msg) {
         return declare("sys.zone.mobile.js._FollowButtonListViewMixin", null, {
        	
			buildRendering : function() {
				this.inherited(arguments);
				this.icon = domConstruct.create("span" , {
					className:"mui_zone_list_item_opt"
				}, this.domNode);
				this.iconinner = domConstruct.create('i' , {
					className : "fontmuis muis-more muiFontColorMuted"
				}, this.icon);		        
				domClass.add(this.domNode , this.baseClass);
				this.subscribe("/sys/zone/followStatusChange", "_buildFollowIcon");
			},
         
             _buildFollowIcon : function(buttonObj,data){
            	 if(buttonObj==this){
    				 if(data.relation == "2" || data.relation == "0") { /* relation:2 表示对方关注了我 、relation:0 表示双方都未关注对方  */
    					 // 关注
    					 this.currentIcon = "muis-follow";
    	                 this.currentText = msg['sysZonePerson.4m.follow'];
    				 }else if(data.relation=="3" || data.relation == "1") { /* relation:3 表示双方相互关注了对方 、relation:1 表示我关注了对方  */
    					 // 取消关注
    					 this.currentIcon = "muis-follow";
    	                 this.currentText = msg['sysZonePerson.4m.cancelText']; 
    				 }    		 
            	 }

             } 
				
				
		 });
});
			