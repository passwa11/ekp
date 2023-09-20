define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-class","dojo/request","dojo/dom-attr",
        "dojo/query","dojo/touch","dijit/registry","dojo/dom-construct","mui/util","dojo/on"],
        function(declare,lang,WidgetBase,domClass,request,domAttr,query,touch,registry,domConstruct,util,on){
		
		return declare("km.review.ReviewCategoryView",[WidgetBase],{
			authUrl:'/sys/category/mobile/sysCategory.do?method=authData&authType=02&modelName=com.landray.kmss.km.review.model.KmReviewTemplate',
			cateUrl:'/sys/category/mobile/sysCategory.do?method=cateList&fdTempKey=&categoryId=!{categoryId}&getTemplate=1&modelName=com.landray.kmss.km.review.model.KmReviewTemplate&authType=02&extendPara=key:_cateSelect',
			createDoc:'/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=!{fdTemplateId}',
			//最近使用的模板
			lastUrl:'/sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsual&mainModelName=com.landray.kmss.km.review.model.KmReviewMain',
			authCateIds:'',
			
			buildRendering:function(){
				this.inherited(arguments);
			},
			startup: function(){
				this.inherited(arguments);	
				var self = this;
				this.initLastTemplate();
				this.authData().then(function(result){
					self.getCategories().then(function(res){
						self.renderCate(res);
					},function(err){
						console.error('获取分类出错：',err)
					})
				})
			},
			initLastTemplate:function(){
				var self =this;
				request(util.formatUrl(this.lastUrl), {
					handleAs : 'json',
					method : 'post',
					data : ''
				}).then(function(result){
					if(!result || result.list.length==0){
						return;
					}
					var ulNode = self.renderCardHtml('','最近使用',self.domNode);
					
					var datas = result.list;
					var emNode = query('.mui-review-menu-frame-item em',ulNode.parentNode.parentNode);
					emNode[0].innerHTML = "("+ datas.length +")";
					for( var i = 0; i<datas.length;i++){
						var record = datas[i];
						self.renderContentHtml(ulNode,'TEMPLATE',record.fdTemplateId,record.templateName);
					}
					
				},function(e){
					window.console.error("获取最近使用模板失败,error:" + e);
				});
			},
			
			renderCardHtml:function(id,text,domNode){
				var itemNode = domConstruct.create("div",{className:"mui-review-platform-template-item",innerHTML:''},domNode,id ? 'last':'first');
				//标题栏
				var headNode = domConstruct.create("div",{className:"mui-review-platform-template-item-head",innerHTML:''},itemNode);
				
				var headTxtNode = domConstruct.toDom("<div class='mui-review-head-l'><div class='mui-review-menu-frame-nav'><div class='mui-review-menu-frame-item' data-id='" + id +"'><span>"+text+"</span><em></em></div></div></div>");
				domConstruct.place(headTxtNode, headNode);
				var toggleNode = domConstruct.toDom("<div class='mui-review-head-r'><i class='mui-icon-toggle-up'></i></div>");
				domConstruct.place(toggleNode, headNode);
				this.connect(toggleNode, touch.press, 'onToggleClick');
				var currentPathNode = query('.mui-review-menu-frame-item',headTxtNode);
				this.connect(currentPathNode[0], touch.press, 'onNavItemClick');
				//内容
				var contentNode = domConstruct.create("div",{className:"mui-review-platform-template-item-content",innerHTML:''},itemNode);
				var ulNode = domConstruct.create("ul",{className:"clearfix"},contentNode);
				return ulNode;
			},
			
			//分类模板
			renderCate:function(datas){
				if(!datas || datas.length==0){
					return;
				}
				var self = this;
				for( var i = 0; i<datas.length;i++){
					var record = datas[i];
					var ulNode =self.renderCardHtml(record.value,record.text,self.domNode);
					
					(function(r,domNode){
						self.getCategories(r.value).then(function(result){
							if(!result || result.length==0){
								return;
							}
							self.renderContent(result,domNode,r.value);
						})
					})(record,ulNode);
					
				}
			},
			//渲染某个分类下的内容
			renderCategory:function(ulNode,fdTemplateId,label){
				var self = this;
				this.getCategories(fdTemplateId).then(function(res){
					//重新渲染分类路径
					if(label){
						var itemNode = ulNode.parentNode.parentNode;
						var arrowNode = domConstruct.toDom("<div class='mui-review-menu-frame-item mui-review-menu-frame-icon mui-review-menu-frame-icon-arrow'><i></i></div>");
						var currentPathNode = domConstruct.toDom("<div data-id='"+ fdTemplateId +"' class='mui-review-menu-frame-item'><p>"+ label +"</p><em></em></div>");
						query('.mui-review-menu-frame-nav',itemNode).append(arrowNode).append(currentPathNode);
						
						self.connect(currentPathNode, 'click', 'onNavItemClick');
					}
					//内容渲染
					domConstruct.empty(ulNode);
					self.renderContent(res,ulNode,fdTemplateId)
				},function(err){
					console.error('获取分类出错：',err)
				})
			},
			
			//渲染某个分类下的内容
			renderContent :function(datas,ulNode,fdTemplateId){
				var self = this;
				if(!datas || datas.length==0){
					return;
				}
				var count = this.getTemplateCount(datas);
				if(count > 0){
					var emNode = query('div[data-id="'+fdTemplateId+'"] em',ulNode.parentNode.parentNode);
					emNode[0].innerHTML = "("+ count +")";
				}
				
				for( var i = 0; i<datas.length;i++){
					var record = datas[i];
					this.renderContentHtml(ulNode,record.nodeType,record.value,record.text);
				}
			},
			
			renderContentHtml:function(ulNode,nodeType,id,text){
				var liNode = domConstruct.create("li",{className:''},ulNode);
				var icon = nodeType=="CATEGORY" ? 'icon-category@2x.png':'icon-template@2x.png'
				var linkNode = domConstruct.create("a",{href:'javascript:;','data-id':id,'data-nodetype':nodeType,'data-label':text},liNode);
				var imgNode = domConstruct.toDom("<img src='./resource/images/" + icon + "'><div class='mui-review-platform-template-title'><p>"+ text+"</p></div>");
				domConstruct.place(imgNode, linkNode);
				
				this.connect(linkNode, 'click', 'onItemClick');
			},
			
			onNavItemClick:function(evt){
				var navNode = evt.currentTarget;
				if(!navNode.nextSibling){
					return;
				}
				var nodes = [];
				var currentNode = navNode;
				
				while(currentNode.nextSibling){
					var next = currentNode.nextSibling;
					nodes.push(next);
					currentNode = next;
				}
				for( var i = 0; i<nodes.length;i++){
					domConstruct.destroy(nodes[i]);
				}
				var itemNode = navNode.parentNode.parentNode.parentNode.parentNode;
				var ulNode = query('.mui-review-platform-template-item-content ul',itemNode)
				var fdTemplateId = domAttr.get(navNode,'data-id');
				this.renderCategory(ulNode[0],fdTemplateId,'')
			},
			onItemClick:function(evt){
				var self = this;
				var aNode = evt.currentTarget;
				var fdTemplateId = domAttr.get(aNode,'data-id');
				var nodeType = domAttr.get(aNode,'data-nodetype');
				var label = domAttr.get(aNode,'data-label');
				
				if(nodeType=='TEMPLATE'){
					var url = util.urlResolver(this.createDoc,{fdTemplateId:fdTemplateId});
					location.href = util.formatUrl(url);
					return;
				}
				var ulNode = aNode.parentNode.parentNode;
				this.renderCategory(ulNode,fdTemplateId,label)
			},
			onToggleClick :function(evt){
				var contentNode = query('.mui-review-platform-template-item-content',evt.currentTarget.parentNode.parentNode);
				if(contentNode.length>0){
					domClass.toggle(contentNode[0],'unexpanded')
				}
				domClass.toggle(evt.currentTarget,'mui-icon-toggle-down')
			},
			//获取某个分类下的模板数
			getTemplateCount:function(datas){
				var count = 0;
				for( var i = 0; i<datas.length;i++){
					var record = datas[i];
					if(record.nodeType==='TEMPLATE'){
						count++;
						continue;
					}
				}
				return count;
			},
			
			//初始化授权信息
			authData:function(){
				var self =this;
				return request(util.formatUrl(this.authUrl), {
					handleAs : 'json',
					method : 'post',
					data : ''
				}).then(function(result){
					if (result.authIds) {
						self.authCateIds = result.authIds;
					}
				},function(e){
					window.console.error("获取分类授权数据失败,error:" + e);
				});
			},
			
			getCategories:function(categoryId){
				var url = util.urlResolver(this.cateUrl,{categoryId:categoryId})
				return request(util.formatUrl(url), {
					handleAs : 'json',
					method : 'post',
					data : {
						authCateIds:this.authCateIds
					}
				})
			}
			
		});
});