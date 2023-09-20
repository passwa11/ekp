define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require("lui/topic")
	var treeBottom = require("hr/organization/resource/js/tree/treeBottom.js");
	var TreeWidget = require("hr/organization/resource/js/tree/baseTree.js");
	var xml = require('resource/js/xml.js');
	var OrgTree = TreeWidget.BaseTree.extend( {
		_count:-1,
		initialize : function($super, config) {
			$super(config);
			this.selectNodes =null
			this.draw();
			this.config.boxWidth = this.config.boxWidth||this.element.width()
			// this.renderTree(this.getChildrenData(),{node:this.container,pos:"0"});
			this.sync('',{node:this.container,pos:"0"});
			this.startup();
			//init height
			if(this.config.treeHeight){
				this.container.height(this.config.treeHeight);
				this.container.css("min-height",this.config.treeHeight)
			}
		},
		startup : function() {
			var self = this;
			/* 引入CSS文件 */
			if (self.supportCss3()) {
				self.loadingCSS(Com_Parameter.ContextPath + "hr/organization/resource/css/tree.css");
			}
			this.initSelectNode();
			topic.subscribe("hr/org/search",function(val){
				if(val){
					self.research(val);
				}else{
					self.refresh();
				}
			});
			topic.subscribe("hr/org/search/cancel",function(val){
				self.cancelSearch();
			});
		},
		refresh:function(data){
			this.container.empty();
			this.firstNode=null;
			if (data == undefined){
				this.getChildrenData('',{node:this.container,pos:"0"})
			}else {
				this.renderTree(data,{node:this.container,pos:"0"})
				this.preClick(0);
			}
		},
		preClick:function(i){
			let firstButton = $('.lui_tree_container .tree-node-text-inside')[i];
			if (firstButton !== undefined){
				firstButton.click()
			}
		},
		cancelSearch:function(){
			if(this.cacheContainer){
				this.container.empty();
				this.container.append(this.cacheContainer.children());
				this.cacheContainer=null;
				this.initSelectNode();
			}
		},
		research:function(value){
			var url = Com_Parameter.ContextPath+"hr/organization/hr_organization_element/hrOrganizationElement.do?method=searchElement";
			var self = this;
			$.ajax(url,{
				data:{
					_fdKey:value
				},
				success:function(data){
					if(data){
						try{
							var JSONData = JSON.parse(data);
							if(!self.cacheContainer){
								self.cacheContainer = $("<div></div>");
								self.cacheContainer.hide();
								self.cacheContainer.append(self.container.children());
							}
							self.refresh(JSONData);
						}catch(e){

						}
					}
				}
			})
		},
		initSelectNode:function(){
			if(this.firstNode){
				this.firstNode.children(".tree-node-text").find(".tree-node-text-inside").click();
			}
		},
		renderTree:function(nodeData,parent){
			if(nodeData instanceof Array){
				for(var i = 0;i<nodeData.length;i++){
					var curpos=parent.pos;
					var curposArr = curpos.split('-');
					var canLoadChild = false;
					curpos=curpos+"-"+i;
					if(i==nodeData.length-1){
						nodeData[i].lastNode=true;
					}
					var parentId = nodeData[i]['value']
					var children = nodeData[i].hasChildren == "true"
					var newNode = this.createNode(nodeData[i],curpos,parent,children,true);
					if((this.selectNodes&&this.selectNodes==curpos)||curpos=="0-0"){
						this.firstNode = newNode;
					}
					if(this.config.unfoldNodes[curpos]){
						canLoadChild=true;
						this.loadedChild[curpos]=true;
					}
					if(this.isShowByLevel(curpos)||canLoadChild){
						if(children){
							// var childrenData = this.getChildrenData(nodeData[i]['value']);
							// this.renderTree(childrenData,{node:newNode,pos:curpos});
							this.sync(nodeData[i]['value'],{node:newNode,pos:curpos});
						}
					}

				}
			}
		},
		sync:function(parentId,parent){
			this.getChildrenData(parentId,parent);
		}
		,
		createNode:function(nodeData,pos,parent,hasChildren,install){
			var position = pos.split('-')
			var treeNodeSpace=$("<span class='tree-node-space'><a href='javascript:void(0)'></a></span>");
			var node = $("<div class='tree-node'>");
			var spaceNumber=hasChildren?position.length-1:position.length;
			var nodeTextWith  = 0;
			//20 为空格宽度，目前写死，可以自定义
			this.spaceBtnWidth = 20;
			treeNodeSpace.find("a").width(this.spaceBtnWidth);
			treeNodeSpace.width(this.spaceBtnWidth);
			for(var j = 0;j<spaceNumber;j++){
				var cloneSpaceNode = treeNodeSpace.clone(true);
				node.append(cloneSpaceNode);
				nodeTextWith+=this.spaceBtnWidth;
			}

			var treeBtn= $("<span class='tree-node-btn'><span></span></span>");
			if(hasChildren){
				node.append(treeBtn)
				treeBtn.attr("data-folder",true);
				this.clickTreeBtn(treeBtn,pos,nodeData,node);
				nodeTextWith+=this.spaceBtnWidth;
			}
			var iconType=""
			if(this.config.icon){
				iconType = "<img class='icon-node-type' src=''/>"
				nodeTextWith+=13
			}
			//this.config.icon&&node.append("<i class='tree-node-icon tree-node-icon-has'>");
			name=$("#namex").text(nodeData['text']).html();
			var nodeText = $("<span class='tree-node-text'><span class='tree-node-text-inside'>"+iconType+name+"</span></span>");
			node.append(nodeText);
			nodeText.width(this.config.boxWidth - nodeTextWith-20)
			node.data("pos",pos);
			this._showNode(node,pos,treeBtn);

			if(iconType){
				var iconImg = nodeText.find("img");
				if(nodeData.nodeType==2){
					iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_dept.png"
				}
				if(nodeData.nodeType==1){
					iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_org.png"
				}
			}
			if(install){
				parent['node'].append(node);
			}
			nodeData['pos']= pos;
			this.selectdClick(node,nodeData);
			return node;
		},
		_showNode:function(node,pos,treeBtn){
			var postLevel = pos.split("-").length;
			var child = pos.split("-").splice(0,postLevel-1);
			if(this.config.unfoldNodes[pos]){
				node.show();
				this.changeNodeFold(treeBtn,false)
			}else{
				if(this.config.asyncTree&&postLevel>2){
					if(this.config.unfoldNodes[child.join("-")]){
						node.show();
					}else{
						node.hide();
					}
				}else{
					node.show();
				}
			}

		},
		//节点收缩展开
		clickTreeBtn:function(dom,pos,nodeData,parentNode){
			var self = this;
			dom.click("click",function(e){
				// self.asyncLoadChild(nodeData,pos,parentNode);
				if(!self.isShowByLevel(pos)&&!self.loadedChild[pos]){
					self.sync(nodeData['value'] ,{node:parentNode,pos:pos});
				}

				self.loadedChild[pos] = true;
				var isFolder = dom.attr("data-folder");
				if(isFolder=="true"){
					dom.attr("data-folder",false);
					dom.nextAll('.tree-node').show();
					dom.addClass("tree-node-btn-folder");

					self.recordUnfoldNodes(pos,true);
				}else{
					dom.attr("data-folder",true);
					dom.nextAll('.tree-node').hide();
					dom.removeClass("tree-node-btn-folder");
					self.recordUnfoldNodes(pos,false);
				}
			})
		},
		//获取子节点数据
		getChildrenData:function(parentId,parent){
			var initialKmssData = new KMSSData();
			var url = "hrOrganizationTree&orgType=3075&parent=!{id}&isCompile=!{isCompile}"+"&seqRan="+new Date().getTime()+"&isAuth=true";
			initialKmssData.AddBeanData(Com_ReplaceParameter(
					url, {
						id : parentId||"",
						isCompile:""
					}));
			var self = this;
			var treeNodeSpace='';
			var position = parent['pos'].split('-');
			for(var j = 0;j<position.length + 1;j++){
				treeNodeSpace+="<span class='tree-node-space' style='width: 20px;'><a href='javascript:void(0)' style='width: 20px;'></a></span>";
			}
			let appending = $('<div class="tree-node" style="display: block;">'+treeNodeSpace+'<span class="tree-node-text" style="width: 0px;"><span class="tree-node-text-inside">加载中...</span></span></div>');
			parent['node'].append(appending);
			initialKmssData.GetHashMapArray(function (result){
				appending.remove();
				self.renderTree(result.data,parent);
				if (parent.pos == '0'){
					self.preClick(0);
				}
				if (result.data !== undefined && result.data.length == 0 &&
					parent.node.children('.tree-node-btn') !== undefined) {
					parent.node.children('.tree-node-btn').css("visibility", "hidden")
				}
			});
			return [];
		},
		//选中节点
		selectdClick:function(dom,nodeData){
			var _this = this;
			dom = dom.children(".tree-node-text").find(".tree-node-text-inside");
			dom&&dom.on('click',function(e){
				$("#"+_this.id+" .tree-node-text-inside").removeClass("tree-node-text-active")
				$(e.target).addClass("tree-node-text-active");
				topic.publish("hr/click/node/text",nodeData);
				_this.selectNodes = nodeData['pos'];
				_this.curSelectedData = nodeData;
//				if(window.recoverTree == true){
//					var checked = $(this).find("input[name='List_selected']").prop("checked");
//					if(checked){
//						$(this).find("input[name='List_selected']").prop("checked",false)
//					}else{
//						$(this).find("input[name='List_selected']").prop("checked",true);
//					}
//				}
				e.stopPropagation();
			})
//			if(window.recoverTree == true){
//				$("[name='List_selected']").click(function(e){
//				       e.stopPropagation();
//				   });
//			}
		}
	});

	exports.OrgTree = OrgTree;
});