define(	["dojo/_base/declare", 
       	 "dojox/mobile/_ItemBase", 
       	 "dojo/dom-construct",
       	 "dojo/dom-class", 
       	 "dojo/dom-style", 
       	 "mui/util",
       	"dojo/on"], function(
				declare, ItemBase, domConstruct, domClass, domStyle, util,on) {
			return declare("mui.list.item.ComplexLItemListItem", [ItemBase], {
				tag : 'li',
				// 图片url
				icon : '',
				fdId:'',
				entryName:"",
				fdPlanEntryTime:"",
				fdStatus:"",
				index:1,
				fdLastModifiedTime:"",
				buildRendering : function() {
						this.inherited(arguments);
						this.domNode = this.containerNode = this.srcNodeRef|| domConstruct.create(this.tag, {
											className : 'entryItem'
										});
						this.contentNode = domConstruct.create(
										'div', {
											className : 'muiItem'
										});
				
					
						this.buildInternalRender();
					if(this.contentNode)
						domConstruct.place(this.contentNode,this.domNode);
				},

				buildInternalRender : function() {
					this.articleNode = domConstruct.create('div', {className:'entryItemNode'},
							this.contentNode);
					if(this.fdId){
						var imgUrl = util.formatUrl("/sys/person/image.jsp?personId="+this.fdId+"&size=null");
						var fdSexNode=""
						if(this.fdSex){
							fdSexNode ="<span class='entryItemIconSpan"+(this.fdSex==='1'||this.fdSex==='M'?' applicantIconMale':' applicantIconFemale')+"'></span>"
						}
						this.iconNode = domConstruct.create('div',{className:'entryItemIcon',innerHTML:fdSexNode}, this.articleNode);
						domStyle.set(this.iconNode, "background-image" , "url(" + imgUrl + ")");
						domStyle.set(this.iconNode, "background-size" , "cover");
						var _this = this;
						var ts = dojoConfig.baseUrl
						on(this.articleNode,"click",function(){
							window.location.href=dojoConfig.baseUrl.substr(0,dojoConfig.baseUrl.length-1)+'/hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId='+_this.fdId
						})
					}
					
					this.infoNode = domConstruct.create('div', {
								className : 'entryItemInfo'
							}, this.articleNode);
					
					if (this.entryName) {
						var status = ""
						if(this.fdQRStatus=='已扫码'){
							status="<span class='entryItemstatus'></span>"
						}
						
						this.hrefNode = domConstruct.create('div', {
									className : 'entryItemName',
									innerHTML : "<span>"+this.entryName+"</span>"+status
								}, this.infoNode);
					}
					this.entryTimeNode = domConstruct.create('div',{
						className:'entryItemTime'
					},this.infoNode)
					this.enterPlanTimeNode = domConstruct.create('div',{
						className:'entryItemEnterTime',innerHTML:"<span class='mbListItemBtn'>入职</span>"+this.fdPlanEntryTime
					},this.entryTimeNode)
					this.modifiedTimeNode = domConstruct.create('div',{
						className:'entryItemModifiedTime',innerHTML:"<span class='mbListItemBtn'>录入</span>"+this.fdLastModifiedTime.split(" ").shift()
					},this.entryTimeNode)
				
				},

				startup : function() {

					if (this._started) {
						return;
					}
					this.inherited(arguments);
				},
				_onClick:function(){
					alert("tsgr")
				}
				
				,
				_setLabelAttr : function(text) {
					if (text)
						this._set("label", text);
				}
			});
		});