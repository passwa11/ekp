define(	["dojo/_base/declare", "dojo/_base/array", "mui/iconUtils" , "mui/category/CategoryItemMixin" ,
       	"dojo/topic", "dojo/dom-style", "dojo/dom", "dojo/dom-class", "dojo/dom-construct"],
		function(declare, array, iconUtils, CategoryItemMixin,topic,domStyle,dom,domClass,domConstruct) {
			var item = declare("sys.xform.mobile.controls.relevance.RelevanceItemMixin", [CategoryItemMixin ], {
				isMul : true,
				//nodeType :CATEGORY(分类) TEMPLATE(模板) DOCUMENTNODE(文档) RETURNNODE(返回分类) CURRDOM(查看当前分类文档) 
				
				buildRendering:function(){
					this.inherited(arguments);
					if(this.nodeType=="RETURNNODE"){
						domStyle.set(this.titleNode,{color:'#37aee9'});
						domStyle.set(this.titleNode,{fontSize:'1.6rem'});
					}
				},
				
				//是否显示往下一级
				showMore : function(){
					var pWeiget = this.getParent();
					if(this.nodeType=="TEMPLATE"||this.nodeType=="DOCUMENTNODE"||this.nodeType=="RETURNNODE"){
						return false;
					}
					return true;
				},
				
				//是否显示选择框
				showSelect:function(){
					if(this.nodeType=="DOCUMENTNODE"){
						return true;
					}
					return false;
				},
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds){
						var arrs = pWeiget.curIds.split(";");
						if (array.indexOf(arrs,this.fdId)>-1)
							return true;
					}
					return false;
				},
				
				buildIcon:function(iconNode){
					if(this.icon){
						iconUtils.setIcon(this.icon, null,
								this._headerIcon, null, iconNode);
					}else{
						if(this.nodeType=="CURRDOM" || this.nodeType=="DOCUMENTNODE"){
							iconUtils.setIcon("mui mui-file-text", null,
									this._headerIcon, null, iconNode);
						}else{
							domStyle.set(iconNode, {"display":"none"});
						}
					}
				},
				
				showItemDetail:function(){
					if(this.nodeType=="RETURNNODE"){
						topic.publish("/mui/category/changed",this,{
							'fdId':this.fdId,
							'label':this.label,
							'fdTemplateId':this.fdTemplateId,
							'modelPath':this.modelPath,
							'isBase':this.isBase,
							'fdKey':this.fdKey,
							'pAdmin':this.pAdmin,
							'isSimpleCategory':this.isSimpleCategory,
							'fdTemplateModelName':this.fdTemplateModelName,
							'modelName' : this.fdMainModelName
						});
					}else{
						topic.publish("/sys/xform/relevance/category/changed",this,{
							'fdId':this.fdId,
							'fdTemplateId':this.fdTemplateId,
							'categoryId':this.categoryId,
							'label':this.label,
							'modelPath':this.modelPath,
							'notSearch':this.notSearch,
							'fdKey':this.fdKey,
							'isChild':this.isChild,
							'isBase':this.isBase,
							'isSimpleCategory':this.isSimpleCategory,
							'fdTemplateModelName':this.fdTemplateModelName,
							'modelName' : this.fdMainModelName
						});
					}
				},
				
				_openCate:function(evt){
					this.set("entered", true);
					this.defer(function(){
						this.set("entered", false);
						if(this.nodeType=="CURRDOM"){
							if(this.fdId.indexOf("-")>=0){
								this.fdTemplateId = '';
							}
							this.showItemDetail();
						}else{
							topic.publish("/mui/category/changed",this,{
								'fdId':this.fdId,
								'label':this.label,
								'fdTemplateId':this.fdTemplateId,
								'modelPath':this.modelPath,
								'isBase':this.isBase,
								'fdKey':this.fdKey,
								'pAdmin':this.pAdmin,
								'isSimpleCategory':this.isSimpleCategory,
								'fdTemplateModelName':this.fdTemplateModelName,
								'modelName' : this.fdMainModelName
							});
						}
					}, 200);
					return;
				},
				
				_setSelected:function(srcObj,evt){
					if(srcObj.key==this.key){
						if(evt && evt.fdId){
							if(evt.fdId==this.fdId){
								domClass.add(this.selectNode,"muiCateSeled");
						    	this.checkedIcon= domConstruct.create('i', {
										'className' : 'mui mui-checked muiCateSelected'
								}, this.selectNode);
								this.set("entered", true);
								this.defer(function(){
									this.set("entered", false);
								},200);
								if(this.isMul){
									topic.publish("/mui/category/selected",this,{
										'fdId':this.fdId,
										'label':this.label,
										'isCreator':this.isCreator,
										'fdModelName':this.fdModelName
									});
									topic.publish("/mui/category/cate_selected",this,{
										'label':this.label,
										'fdId':this.fdId,
										'icon':this.icon,
										'type':this.type
									});
								}else{
									var eCxt = {
											curIds:this.fdId,
											subjects:this.label,
											fdModelNames:this.fdModelName,
											isCreators:this.isCreator,
											key: this.key
										}
									topic.publish("/sys/xform/relevance/main/submit" , this, eCxt);
								}
							}
						}
					}
				}
			});
			return item;
		});