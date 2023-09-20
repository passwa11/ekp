define( [ "dojo/_base/declare", "mui/category/CategoryPath","mui/i18n/i18n!sys-mobile","dojo/dom-construct","dojo/topic",
          "dojo/dom-style" , "dojo/request", "dojo/_base/array", "mui/util","dojox/mobile/_ScrollableMixin","mui/i18n/i18n!sys-lbpmext-authorize"],
	function(declare,CategoryPath,Msg,domConstruct,topic,domStyle,request,array,util,ScrollableMixin,Msg) {
		var path = declare("sys.lbpmext.authorize.mobile.js.authorizescope.LbpmAuthorizePath", [ CategoryPath,ScrollableMixin], {
				
				scrollDir : "h",
			
				modelName: null ,
				
				//获取详细信息地址
				detailUrl : '/sys/lbpmext/authorize/lbpm_authorize_scope/lbpmAuthorizeScope.do?method=pathList',
				
				buildRendering : function() {
					this.inherited(arguments);
				},
				
				postCreate : function() {
					this.inherited(arguments);
					//调整宽度
					domStyle.set(this.domNode,{width:'max-content'});
					domStyle.set(this.domNode,{minWidth:'100%'});
				},
				
				_createPathItem:function(item,label) {
					var itemTag = domConstruct.create('div', {
						value:item.fdId
					},this.containerNode);
					var textNode = domConstruct.create('div', {
						className : ''
					},itemTag);
					if (label) {													
						var labelNode = domConstruct.create('span', {
							className :''
						}, textNode);
						labelNode.innerHTML = label;
					}
					
					this.connect(itemTag, "onclick", function(){
						event = event || window.event;
						event.cancelBubble = true;
						if (event.stopPropagation) {event.stopPropagation();}
						if(item.fdId != this.curId){
							this._chgHeaderInfo(this,{
								'fdId':item.fdId,
								'param':item.param
							});
							topic.publish("/mui/category/changed",this,{
								'fdId':item.fdId,
								'label':item.label,
								'param':item.param
							});
						}
					});
				},
				 _createTitleNode: function() {
					 var itemTag = domConstruct.create('div', {
						},this.containerNode);
					 var textNode = domConstruct.create('div', {
						 className : ''
					 },itemTag);
					 var labelNode = domConstruct.create('span', {
						 className :''
					 }, textNode);
					 labelNode.innerHTML = Msg["mui.authorize.path.all"];
					
					 this.connect(itemTag, "onclick", function(){
						 this._chgHeaderInfo(this,{});
						 topic.publish("/mui/category/changed",this,{});
					 });
                 },
				_chgHeaderInfo : function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt){
							if(evt.fdId){
								domStyle.set(this.domNode,{display:'block'});
								if(this.curId != evt.fdId || this.containerNode.innerHTML == Msg["mui.authorize.path.all"]){
									this.curId = evt.fdId;
									this.param = evt.param;
									var _url = util.urlResolver(this.detailUrl+evt.param,this);
									_url = util.formatUrl(_url);
									var promise = request.post(_url, {
										handleAs : 'json'
									});
									var _self = this;
									promise.then(function(items) {
										if(items.length>0){
											_self.containerNode.innerHTML='';
											_self._createTitleNode();
											if(items.length < 3){
												array.forEach(items, function(item,index) {
													_self._createPathItem(item,item.label);
												}, this);
											}else{
												var item_length = items.length;
												array.forEach(items, function(item,index) {
													if(index == (item_length-3)){
														_self._createPathItem(item,'...');
													}else if(index > (item_length-3)){ 
														_self._createPathItem(item,item.label);
													}else{
														return;
													}
												}, this);
											}
										}else{
											//错误处理
										}
									}, function(data) {
										//错误处理
									});
								}
							}else{
								this.containerNode.innerHTML = "";
								this._createTitleNode();
							}
						}
					}
				}
			});
			return path;
});