define([ "dojo/_base/declare", "mui/form/Category","dojo/_base/lang","dojo/query","dojo/topic","dojox/mobile/viewRegistry", "dojox/mobile/TransitionEvent"],
	function(declare, Category, lang, query, topic,viewRegistry,TransitionEvent) {
		var PlaceComponent = declare("km.imeeting.PlaceComponent",[ Category ], {
			
			isMul : false,

			templURL : "km/imeeting/mobile/resource/tmpl/place.jsp" ,
			
			validateInputName: 'fdPlaceUserTime',
			
			required: true,
			
			/**
			 * 选中的会议室信息数组
			 */
			selPlaceInfoArray:[],
			
			postCreate : function(){
				this.inherited(arguments);
				//选择分类确认事件订阅
				topic.subscribe("/mui/category/submit",lang.hitch(this,"setIMeetingPlaceUserTime"));
				//删除选中会议室事件订阅
				topic.subscribe("/mui/Category/valueChange",lang.hitch(this,"setIMeetingPlaceUserTime"));
				topic.subscribe("/km/imeeting/place/submit",lang.hitch(this,"setPlaceUserTime"));
				topic.subscribe("/mui/category/tmploaded",lang.hitch(this,"handleTmpLoaded"));
			},
			
			startup : function(){
				this.inherited(arguments);
				this.backview = viewRegistry.getEnclosingView(this.domNode);
			},
			
			/**
			 * 选择完会议地点后设置地点最长可使用时长字段，并校验
			 */
			setPlaceUserTime : function(srcObj , evt){
				
				var self = this;
				
				if(evt){
					if(srcObj.key == self.key){
						var isExist = false;
						for(var i=0;i<self.selPlaceInfoArray.length;i++){
							var place = self.selPlaceInfoArray[i];
							if(place.fdId==evt.fdId){
								isExist = true;
								break;
							}
						}
						if(!isExist){
							console.log(evt);
							self.selPlaceInfoArray.push(evt);
						}
						if(!self.isMul){//单选时直接设置最长可使用时长字段，并校验
							query('[name="' + self.validateInputName + '"]')[0].value = evt.fdUserTime;
							self.validateImeetingPlace();
						}
					}
				}
			},
			
			/**
			 * 确认之后会议地点后设置地点最长可使用时长字段，并校验
			 */
			setIMeetingPlaceUserTime:function(srcObj , evt){
				var self=this;
				if(evt){
					if(srcObj.key == self.key){
						var fdUserTime = "";
						var selPlaceIds = evt.curIds.split(";");
						for(var i=0;i<selPlaceIds.length;i++){
							var id = selPlaceIds[i];
							fdUserTime += (i!=0?";":"")+ self.getPlaceUserTimeById(id);
						}
						query('[name="' + self.validateInputName + '"]')[0].value = fdUserTime;
						this.validateImeetingPlace();
					}
				}
			},
			
			/**
			 * 通过会议室fdId获取会议室使用时长
			 */
			getPlaceUserTimeById:function(id){
				var self=this;
				var userTime="";
				for(var i=0;i<self.selPlaceInfoArray.length;i++){
					var placeInfo = self.selPlaceInfoArray[i];
					if(placeInfo.fdId==id){
						userTime = placeInfo.fdUserTime;
						break;
					}
				}
				return userTime;
			},
			
			/**
			 * 会议室校验
			 */
			validateImeetingPlace: function(){
				var self=this;
				if(self.validate!='' && self.edit ){
					if(self.validation)self.validation.validateElement(self);
				}
			},
			
			
			
			handleTmpLoaded : function(evt){
				//this.connect(query('.muiCateHeaderReturn',evt.dom)[0],'click',lang.hitch(this,"backOpt"));
			},
			
			backOpt : function(){
				this.closeDialog(this);
			}
			
		});
		return PlaceComponent;
});