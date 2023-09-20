define(	["dojo/_base/declare", "dojo/_base/lang", "dojo/request", "mui/util", "dojo/dom-class", 
       	 "dojo/dom-attr", "dojo/dom-construct" ,"dojo/topic","mui/i18n/i18n!sys-zone:sysZonePerson.4m"], 
       	 function(declare, lang, req, util, domClass, domAttr, domConstruct, topic, msg) {
         return declare("sys.zone.mobile.js._FollowButtonListViewMixin", null, {
        	 

				baseClass : "mui_zone_follow_btn_base",
				
				/*******关注需要的字段****/ 
				userId : "",
				
				attentModelName : "",
				
				fansModelName : "",
				
				isFollowPerson : "",
				/*******关注需要的字段****/
				
				//取消关注class
				followedClass: "mui_zone_followed_btn",
				
				//互相关注class
				followEachClass: "mui_zone_followeach_btn",
				
				//关注class
				followClass: "mui_zone_follow_btn",
				
				locked : false,
				
				followLabel : "<span class='mui_zone_tips'>"+ msg['sysZonePerson.4m.follow'] +"</span>",
				
				followedLabel : '<span class="mui_zone_tips">' + msg['sysZonePerson.4m.followed']+'<em>|</em>'+ msg['sysZonePerson.4m.cancel'] +'</span>',
				
				followeachLabel : '<span class="mui_zone_tips">'+ msg['sysZonePerson.4m.follow.each'] +'<em>|</em>'+ msg['sysZonePerson.4m.cancel'] +'</span>',
				
				followIcon : "mui-plus",
				
				followedIcon : "mui-right",
				
				followeachIcon : "mui-addTwo",
				
				
				buildRendering : function() {
					this.inherited(arguments);
					this.icon = domConstruct.create("span" , {
						className:"mui_zone_follow_base"
					}, this.domNode);
					this.iconinner = domConstruct.create('i' , {
						className : "mui mui_zone_follow_icon"
					}, this.icon);		        
					domClass.add(this.domNode , this.baseClass);
					this.subscribe("/sys/zone/followStatusChange", "_buildFollowIcon");
				},
         
		        _buildFollowIcon : function(buttonObj,data){
					var icon ="";			
					if(data.relation == "2" || data.relation == "0") { /* relation:2 表示对方关注了我 、relation:0 表示双方都未关注对方  */
						this.label = this.followLabel;				
						domClass.remove(this.domNode , this.followedClass);	
						if(this.iconinner) {
							domClass.remove(this.iconinner, this.followedIcon + " " + this.followeachIcon);
							domClass.add(this.iconinner ,  this.followIcon);
						}
						domClass.remove(this.domNode , this.followedClass);
						domClass.add(this.domNode , this.followClass );
						//当前的icon
						this.currentIcon = this.followIcon;
						this.cuurentText = msg['sysZonePerson.4m.follow'];
						
					}else if(data.relation=="3" || data.relation == "1") { /* relation:3 表示双方相互关注了对方 、relation:1 表示我关注了对方  */
						
						var fIcon = "";
						if(data.relation=="3")  {
							this.label = this.followeachLabel;
							fIcon = this.followeachIcon;
						} else {
							this.label = this.followedLabel;
							fIcon = this.followedIcon;
						}
						if(this.iconinner) {
							domClass.remove(this.iconinner, this.followIcon);
							domClass.add(this.iconinner , fIcon);
						}
						domClass.remove(this.domNode , this.followClass);
						domClass.add(this.domNode , this.followedClass);
						//当前的icon
						this.currentIcon = fIcon;
						this.cuurentText =  msg['sysZonePerson.4m.cancelText'];
						
					}
					
					var labelNode = this.containerNode || this.focusNode;
					
					labelNode.innerHTML = this.icon.outerHTML + this.label;
		        } 
				
				
		 });
});
			