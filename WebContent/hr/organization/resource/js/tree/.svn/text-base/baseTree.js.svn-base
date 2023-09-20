define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require("lui/topic")
	var treeBottom = require("hr/organization/resource/js/tree/treeBottom.js");
	var BaseTree = base.Component.extend( {
		_count:-1,
		initialize : function($super, config) {
			$super(config);
			this.nodesData = config.nodesData;
			this.config = config;
			//开启异步加载
			this.config.asyncTree = this.config.asyncTree||true;
			//开启异步加载才会生效
			this.config.initLevel =this.config.initLevel|| 1;
			//已经异步加载过的子节点
			this.loadedChild={};
			//刷新后能还原已经展开的节点
			this.config.unfoldNodes={};
		},
		refresh:function(data){
			this.container.empty();
			this.firstNode=null;
			this.renderTree(data||this.getChildrenData(),{node:this.container,pos:"0"});
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
							self.refresh(JSONData);
						}catch(e){
							
						}
					}
				}
			})
		},
		remove:function(){
			this.container.empty();
		},
		startup : function() {
			
		},
		drawInitDom:function(){
			if (this.isDrawed)
				return;
			var self = this;
			this.element= $("<div class='lui_tree'>");
			this.container = $("<div class='lui_tree_container'>");
			this.element.append(this.container);
			this.element.append();
			if(this.config.bottom&&this.config.autoAddOrg){
				if(!this.treeBottomNode){
					this.treeBottomNode = new treeBottom.TreeBottom(this.config).draw();
					this.element.append(this.treeBottomNode);
				}
			}
		},
		draw : function($super) {
			this.drawInitDom();
			var id = this.config.id;
			if(id){
				$("#"+id)&&$("#"+id).append(this.element);
			}
			return this;
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
					var children = nodeData[i].hasChildren=="true"?true:false
					//可能会有异步问题
					var newNode = this.createNode(nodeData[i],curpos,parent,children,true);
					if(curpos=="0-0"){
						this.firstNode = newNode;
					}
					if(this.loadedChild[curpos]&&this.loadedChild[curpos]=='true'){
						canLoadChild=true;
					}
					
					this.selectdClick(newNode,nodeData[i]);
					if(this.isShowByLevel(curpos)||canLoadChild){
						if(children){
							var childrenData = this.getChildrenData(nodeData[i]['value']);
							this.renderTree(childrenData,{node:newNode,pos:curpos});
						}				
					}

				}
			}
		},

		isShowByLevel:function(pos){
			return (pos.split("-").length-1)<this.config.initLevel
		},		
		createNode:function(nodeData,pos,parent,hasChildren,install){
			
		},
		recordUnfoldNodes:function(pos,status){
			this.config.unfoldNodes[pos]=status
		},
		changeNodeFold:function(dom,status){
			//折叠
			if(status){
				dom.attr("data-folder",true);
				dom.removeClass("tree-node-btn-folder");
			}else{
				dom.attr("data-folder",false);
				dom.addClass("tree-node-btn-folder");
			}
		},
		//节点收缩展开
		clickTreeBtn:function(dom,pos,nodeData,parentNode){
			var self = this;
			dom.click("click",function(e){
				self.asyncLoadChild(nodeData,pos,parentNode);
				var isFolder = dom.attr("data-folder");
				if(isFolder=="true"){
					self.recordUnfoldNodes(pos,true);
					dom.attr("data-folder",false);
					dom.nextAll('.tree-node').show();
					dom.addClass("tree-node-btn-folder");
				}else{
					self.recordUnfoldNodes(pos,false);
					dom.attr("data-folder",true);
					dom.nextAll('.tree-node').hide();
					dom.removeClass("tree-node-btn-folder");
				}
			})
		},
		//获取子节点数据
		getChildrenData:function(parentId){
			var initialKmssData = new KMSSData();
			var url = "hrOrganizationTree&parent=!{id}&isCompile=!{isCompile}"+"&seqRan="+new Date().getTime();
			initialKmssData.AddBeanData(Com_ReplaceParameter(
					url, {
						id : parentId||"",
						isCompile:"true"
					}));
			return initialKmssData.GetHashMapArray();
		},
		/* 判断浏览器是否支持CSS3 */
		supportCss3 : function() {
			var style = 'animation-play-state';
			var prefix = [ 'webkit', 'Moz', 'ms', 'o' ], i, humpString = [], htmlStyle = document.documentElement.style, _toHumb = function(string) {
				return string.replace(/-(\w)/g, function($0, $1) {
					return $1.toUpperCase();
				});
			};
			for (i in prefix)
				humpString.push(_toHumb(prefix[i] + '-' + style));
			humpString.push(_toHumb(style));
			for (i in humpString)
				if (humpString[i] in htmlStyle)
					return true;
			return false;
		},
		asyncLoadChild:function(nodeData,pos,parentNode){
			if(!this.isShowByLevel(pos)&&!this.loadedChild[pos]){
				var childrenData = this.getChildrenData(nodeData['value']);
				for(var i = 0;i<childrenData.length;i++){
					var hasChildren = childrenData[i].hasChildren=="true"?true:false;
					var node = this.createNode(childrenData[i],pos+'-'+i,null,hasChildren,false);
					parentNode.append(node);
					node.show();
				}
				this.loadedChild[pos]=true;			
			}			
		},
		loadingCSS : function(path) {
			var head = document.getElementsByTagName('head')[0];
			var link = document.createElement('link');
			link.href = path;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			head.appendChild(link);
		}
	});

	exports.BaseTree = BaseTree;
});