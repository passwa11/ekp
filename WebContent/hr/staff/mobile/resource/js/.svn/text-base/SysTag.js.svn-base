define(["dojo/_base/declare",
        "dojo/dom-construct", 
		"dijit/_WidgetBase",
		"mui/util",
		"mui/device/adapter",
		"dojo/query",
		"mui/dialog/Dialog",
		"dojo/on",
		"dojo/touch"
		],
		function(declare, domConstruct,widgetBase, util, adapter, query,Dialog,on,touch) {
			return declare('hr.staff.mobile.js.SysTag',[ widgetBase ],{
				tag : "",
				modelId : "",
				modelName : "",
				tags : [],
				baseClass : "muiTagContainer",
				url : "/sys/ftsearch/mobile/index.jsp?keyword=!{keyword}&modelName=!{modelName}&searchFields=tag",
				buildRendering : function() {
					this.inherited(arguments);
					if(this.tag) {
						var tag = this.tag;
						this.tags = tag.split(";");
						if(this.tags && this.tags.length > 0) {
							for(var i = 0; i < this.tags.length; i ++) {
								var tdom = domConstruct.create("div" , {
									"data-index" : i,
									innerHTML : util.formatText(this.tags[i])
								},this.domNode);
							}
						}
					}
					var editButton = domConstruct.create('div',{className:'ppu_label_edit_button',id:'editButton'},this.domNode);
					domConstruct.create('i',{className:'ppu_label_edit_icon'},editButton);
					domConstruct.create('span',{innerHTML:'编辑'},editButton);
					this.connect(editButton, "onclick", 'editButtonClick');
				},
				
				startup : function() {
					this.inherited(arguments);
					this.connect(this.domNode, "onclick", 'tagClick');
				},
				
				editButtonClick : function(evt) {
					var editModal = query('#editModal')[0];
					var _this = this;
					/*var dialogObj = Dialog.element({
						canClose : false,
						element :_this.renderDialogDom(),
						id:'hr-staff-tag-dialog',
						position:'top',
						'scrollable' :true,
						'parseable' :true,
						showClass : 'muiFormSelect',			
					});*/
					window.location.href=dojo.config.baseUrl+"sys/tag/sys_tag_main/sysTagMain.do?method=editTag&fdModelId="+this.modelId+"&fdModelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&fdQueryCondition=bsk;adk;bbk;ts";
				},
				renderDialogDom:function(){
					var container=domConstruct.create("div",{className:'hr-staff-tag-list'});
					this.tags.map((item)=>{
						var itemNode = domConstruct.create("div",{className:'tag-item',innerHTML:item},container);
						domConstruct.create("div",{className:'tag-item-delete-btn'},itemNode);
					})
					var addTagBtn = domConstruct.create("div",{className:'tag-btn',innerHTML:''},container);
					
					on(addTagBtn,touch.press,function(){
						var dialogObj = Dialog.element({
							canClose : false,
							element :"<div></div>",
							id:'hr-staff-selecttag-dialog',
							position:'bottom',
							'scrollable' :true,
							'parseable' :true,
							showClass : 'muiFormSelect',			
						});
					})
					return container;
				},
				tagClick :  function(evt) {
					var target = evt.target , index =  evt.target.getAttribute("data-index");
					if(index !== null && typeof(index) !== "undefined" && index >= 0) {
						var url = util.urlResolver(this.url, {
							keyword : encodeURIComponent(this.tags[index]),
							modelName : this.modelName || ""
						});
						adapter.open(util.formatUrl(url), "_blank");
					}
				}
			})
		});