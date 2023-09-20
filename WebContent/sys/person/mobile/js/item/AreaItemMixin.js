define(	["dojo/_base/declare", "dojo/_base/array", "dojo/dom-class","dojo/dom-style", "mui/iconUtils" , "mui/category/CategoryItemMixin" ,"dojo/dom-construct", "mui/util",
       	"dijit/registry","dojo/_base/array" ,"dojo/topic"],
		function(declare, array, domClass, domStyle,iconUtils, CategoryItemMixin,domConstruct, util,registry,array,topic) {
			var item = declare("sys.person.AreaItemMixin", [ CategoryItemMixin ], {

				buildRendering:function(){
					this.label = domConstruct.toDom(this.fdName).nodeValue;
					this.inherited(arguments);
				},
				
				showMore : function(){
					if(this.child != '')
						return true;
					return false;
				},
				
				showSelect : function(){
					if(this.isShowCheckBox == 'false')
						return false;
					else
						return true;
				},
				
				isSelected : function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds){
						var arrs = pWeiget.curIds.split(";");
						if (array.indexOf(arrs,this.fdId)>-1)
							return true;
					}
					return false;
				},
				
				_setSelected:function(srcObj,evt){
					this.inherited(arguments);
					
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId==this.fdId){
								topic.publish("/sys/person/area/submit",this,{
									'label':this.label,
									'fdId':this.fdId
								});
							}
						}	
					}
				},
				buildIcon:function(iconNode){
					domStyle.set(iconNode, {"display":"none"});
				}
				
			});
			return item;
		});