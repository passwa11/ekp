define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require("lui/topic")
	var TreeWidget = require("hr/organization/resource/js/tree/baseTree.js");
	var dialogTree = TreeWidget.BaseTree.extend( {
		_count:-1,
		initialize : function($super, config) {
			$super(config);
			this.draw();
			this.config.boxWidth = this.config.boxWidth||this.element.width()
			this.renderTree(this.getChildrenData(),{node:this.container,pos:"0"});
			this.startup();
		},
		startup : function() {
			var self = this;
			/* 引入CSS文件 */
			if (self.supportCss3()) {
				self.loadingCSS(Com_Parameter.ContextPath + "hr/organization/resource/css/tree.css");
			}
			if(this.firstNode){
				this.firstNode.children(".tree-node-text").find(".tree-node-text-inside").click();
			}
			topic.subscribe("hr/org/search",function(val){
				if(val){
					self.research(val);
				}else{
					self.refresh();
				}
			});
		},
		draw : function($super) {
			$super();
			if(this.config.installRootDom){
				this.config.installRootDom.append(this.element);
			}else{
				var id = this.config.id
				if(id){
					$("#"+id)&&$("#"+id).append(this.element);
				}			
			}
		},
		renderTree:function(nodeData,parent){
			if(nodeData instanceof Array){
				for(var i = 0;i<nodeData.length;i++){
					var typeFilter = this.config.orgTypeFilter;
					if(typeFilter&&typeFilter==="1"){
						if(nodeData[i].nodeType==2){
							//如果当前选择为机构，上级组织跳过不能为部门，则过滤掉
							continue;
						}
					}
					var curpos=parent.pos;
					var curposArr = curpos.split('-');
					curpos=curpos+"-"+i;
					if(i==nodeData.length-1){
						nodeData[i].lastNode=true;
					}
					var parentId = nodeData[i]['value']
					var children = this.hasChildren(nodeData[i].value)?true:false
					//可能会有异步问题
					var newNode = this.createNode(nodeData[i],curpos,parent,children,true);
					if(parent.pos=="0"&&!this.firstNode){
						this.firstNode = newNode;
					}
					if(this.isShowByLevel(curpos)){
						if(children){
						var childrenData = this.getChildrenData(nodeData[i]['value']);
						this.renderTree(childrenData,{node:newNode,pos:curpos});
					}
					}

				}
			}
		},	
		createNode:function(nodeData,pos,parent,hasChildren,install){
			var position = pos.split('-')
			var treeNodeSpace=$("<a class='tree-node-space' href='javascript:void(0)'></a>");
			var node = $("<div class='tree-node'>");
			var spaceNumber=hasChildren?position.length-1:position.length;
			var nodeTextWith  = 0;
			//20 为空格宽度，目前写死，可以自定义
			this.spaceBtnWidth = 20;
			treeNodeSpace.width(this.spaceBtnWidth);
			for(var j = 0;j<spaceNumber;j++){
				var cloneSpaceNode = treeNodeSpace.clone(true);
				node.append(cloneSpaceNode);
				nodeTextWith+=this.spaceBtnWidth;
			}
			var treeBtn= $("<span class='tree-node-btn'><span></span></span>");
			if(hasChildren){
				node.append(treeBtn)
				treeBtn.attr("data-folder","true");
				this.clickTreeBtn(treeBtn,pos,nodeData,node);
				nodeTextWith+=this.spaceBtnWidth;
			}
			//this.config.icon&&node.append("<i class='tree-node-icon tree-node-icon-has'>");
			var iconType=""
			if(this.config.icon){
				iconType = "<img class='icon-node-type' src=''/>"
			}
			var nodeText = $("<span class='tree-node-text'><span class='tree-node-text-inside'><input type='radio' data-name='"+nodeData['text']+"' data-id='"+nodeData['value']+"'/>"+iconType+nodeData['text']+"</span></span>");
			if(iconType){
				var iconImg = nodeText.find("img");
				if(nodeData.nodeType==2){
					iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_dept.png"
				}
				if(nodeData.nodeType==1){
					iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_org.png"
				}
			}
			node.append(nodeText);
			nodeText.width(this.config.boxWidth - nodeTextWith-20)
			node.data("pos",pos);
			if(pos.split("-").length>2){
				node.hide();
			}
			if(install){
				parent['node'].append(node);
			}
			return node;
		},
		//节点收缩展开
		clickTreeBtn:function(dom,pos,nodeData,parentNode){
			var self = this;
			dom.click("click",function(e){
				self.asyncLoadChild(nodeData,pos,parentNode);
				var isFolder = dom.attr("data-folder");
				if(isFolder=="true"){
					dom.attr("data-folder","false");
					dom.nextAll('.tree-node').show();
					dom.addClass("tree-node-btn-folder");
				}else{
					dom.attr("data-folder","true");
					dom.nextAll('.tree-node').hide();
					dom.removeClass("tree-node-btn-folder");
				}
			})
		},
		asyncLoadChild:function(nodeData,pos,parentNode){
			var typeFilter = this.config.orgTypeFilter;
			if(!this.isShowByLevel(pos)&&!this.loadedChild[pos]){
				var childrenData = this.getChildrenData(nodeData['value']);
				for(var i = 0;i<childrenData.length;i++){
					if(typeFilter&&typeFilter==="1"){
						if(childrenData[i].nodeType==2){
							//如果当前选择为机构，上级组织跳过不能为部门，则过滤掉
							continue;
						}
					}
					
					var hasChildren = childrenData[i].hasChildren=="true"?true:false;
					var node = this.createNode(childrenData[i],pos+'-'+i,null,hasChildren,false);
					parentNode.append(node);
					node.show();
				}
				this.loadedChild[pos]=true;			
			}			
		},
		hasChildren:function(parentId){
			var childrenData = this.getChildrenData(parentId,null);
			var typeFilter = this.config.orgTypeFilter;
			var res = false;
			for(var i = 0;i<childrenData.length;i++){
				if(typeFilter&&typeFilter==="1"){
					if(childrenData[i].nodeType==2){
						//如果当前选择为机构，上级组织跳过不能为部门，则过滤掉
						continue;
					}else{
						res = true;
					}
				}else{
					res = true;
				}
			}
			return res;
		},
		//获取子节点数据
		getChildrenData:function(parentId,filterId){
			var initialKmssData = new KMSSData();
			var url = "hrOrganizationTree&parent=!{id}&isCompile=!{isCompile}&filterId=!{filterId}"+"&seqRan="+new Date().getTime();
			initialKmssData.AddBeanData(Com_ReplaceParameter(
					url, {
						id : parentId||"",
						isCompile:"true",
						filterId:this.config.filterId
					}));
			return initialKmssData.GetHashMapArray();
		},

	});

	exports.dialogTree = dialogTree;
});