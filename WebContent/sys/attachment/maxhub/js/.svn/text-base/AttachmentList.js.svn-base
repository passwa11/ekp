define(["dojo/_base/declare","dijit/_WidgetBase", "dijit/_Contained", "dijit/_Container","dojo/dom-construct","dojo/_base/array",
	"dojo/request","dojo/dom-form",
	"dojo/topic",
	"sys/attachment/mobile/js/AttachmentList",
	"sys/attachment/maxhub/js/AttachmentEditListItem",
	"sys/attachment/maxhub/js/AttachmentViewListItem","mui/util"],
		function(declare, WidgetBase, Contained, Container,
				domConstruct, array, request, domForm, topic, _AttachmentList,AttachmentEditListItem,AttachmentViewListItem, util){
	
	return declare("sys.attachment.maxhub.js.AttachmentList",
			[_AttachmentList],{
		
		postCreate : function(){
			this.inherited(arguments);
			this.subscribe(this.eventPrefix + 'submit' , "submit");
			this.subscribe(this.eventPrefix + 'refresh' , "refresh");
		},
		
		startup : function(){
			this.inherited(arguments);
			this.pathAtts = [];
			var childen = this.getChildren();
			for(var i=0; i< childen.length; i++){
				if(childen[i].href){
					this.pathAtts.push(childen[i].href);
				}
			}
			
			// 发布事件告知附件个数
			var ctx = this;
			ctx.defer(function() {
				ctx.publishCount();
			});
		},
		
		fillAttInfo : function(){
			if (this.filekeys.length > 0) {
				var attachmentObj = window.AttachmentList[this.fdKey];
				if(attachmentObj != null && attachmentObj.fileStatus != -1){
					array.forEach(this.filekeys, function(file) {
						if(array.indexOf(this.pathAtts,file.path) < 0){
							this.pathAtts.push(file.path);
						}
					},this);
				}
			}
		},
		
		publishCount: function(count) {
			var t = count || this.getCount() || 0;
			topic.publish(this.eventPrefix + 'count', t);
		},
		
		getCount : function(){
			var count = 0;
			var children = this.getChildren();
			if(children && children.length > 0){
				for(var i = 0; i < children.length;i++){
					if(children[i].isInstanceOf(AttachmentEditListItem) 
							|| children[i].isInstanceOf(AttachmentViewListItem)){
						count += 1;
					}
				}
			}
			return count;
		},
		
		submit : function(evt){
			evt = evt || {};
			//console.log('submit')
			if(this.checkAttRules()){
				if(!evt.url){
					evt.success && evt.success();
					return;
				}
				request.post(evt.url, {
	                data: domForm.toObject(this.domNode),  
	                headers: {'Accept': 'application/json'},
	                handleAs: 'json'
	            }).then(function(result) {
	            	if (result['status'] === false) {
	            		evt.fail && evt.fail(result);
	            		return;
	            	}
	            	evt.success && evt.success(result);
	            }, function(result) {
	            	evt.fail && evt.fail(result);
	            });
			}
		},
		
		refresh : function(){
			var self = this;
			console.log('editMode:' + this.editMode);
			if(this.editMode !== 'view'){
				return;
			}
			var jsonUrl = '/sys/attachment/maxhub/import/view_json.jsp';
			jsonUrl = util.setUrlParameter(jsonUrl,'fdModelName',this.fdModelName);
			jsonUrl = util.setUrlParameter(jsonUrl,'fdModelId',this.fdModelId);
			jsonUrl = util.setUrlParameter(jsonUrl,'fdKey',this.fdKey);
			jsonUrl = util.formatUrl(jsonUrl);
			console.log('refresh url:' + jsonUrl);
			request.post(jsonUrl,{
				headers: {'Accept': 'application/json'},
                handleAs: 'json'
			}).then(function(result){
				console.log('refresh result:' + JSON.stringify(result));
				var children = self.getChildren();
				for(var i = 0; i < children.length; i++){
					if(children[i].isInstanceOf(AttachmentViewListItem)){
						children[i].destroyRecursive();
					}
				}
				
				if(result && result.length > 0){
					
					self.publishCount(result.length);
					
					for(var i = 0; i < result.length; i++){
						var viewListItem = new AttachmentViewListItem(result[i]);
						viewListItem.startup();
						self.addChild(viewListItem);
					}
				}
			});
		},
		
		checkAttRules : function(){
			if(!this.validateAttUploading()) {
				Tip.tip({
					text : '附件上传中..'
				});
				return false;
			}
			if (this.filekeys.length > 0) {
				var attachmentObj = window.AttachmentList[this.fdKey];
				if(attachmentObj!=null && attachmentObj.fileStatus!=-1){
					array.forEach(this.filekeys, function(file) {
						if(file._hasSubmit){
							return;
						}
						var fdid = attachmentObj.registFile({'filekey':file.filekey,
							'name':file.name});
						file._hasSubmit = true;
						if(fdid){
							this.addAtts.push(fdid);
						}
					},this);
				}
			}
			this.fillAttInfo();
			return true;
		}
		
	});
	
});