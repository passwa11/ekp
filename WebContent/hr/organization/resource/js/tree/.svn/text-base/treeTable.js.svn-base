var pageSize = 15;
var searchKey='';
var searchPageNo=1;
Com_IncludeFile("dialog.js");
define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");
	var topic = require("lui/topic")
	var TreeWidget = require("hr/organization/resource/js/tree/baseTree.js");
	var lastNodeMap = new Object();
	seajs.use(['lang!hr-organization'],function(lang){
		var TreeTable = TreeWidget.BaseTree.extend( {
			_count:-1,

			initialize : function($super, config) {
				$super(config);
				this.draw();
				this.createRootNode();
				var getInitData = this.getChildrenData('',1);
				this.renderTree(getInitData, {node: this.container, pos: "0"}, 2);
				if(getInitData.length==0){
					if(this.config.showEmpty){
						this.emptyIcon();
					}
				}
				this.startup();
			},
			emptyIcon:function(){
				$("<div class='hr-tree-table-empty'></div>").appendTo(this.element);
			},
			startup : function() {
				var self = this;
				/* 引入CSS文件 */
				if (self.supportCss3()) {
					self.loadingCSS(Com_Parameter.ContextPath + "hr/organization/resource/css/tree.css");
				}
				if(this.firstNode){
					//this.firstNode.children(".tree-node-text").click();
				}
				topic.subscribe("hr/org/table/search",function(val){
					if(val){
						searchPageNo = 1;
						self.research(val);
					}else{
						self.refresh();
					}

				});
				topic.subscribe("hr/org/table/refresh",function(){
					self.refresh();
				});
				topic.subscribe("hr/org/table/search/cancel",function(val){
					self.cancelSearch();
				});
			},
			//获取子节点数据
			getChildrenData:function(parentId,pageNo){
				var initialKmssData = new KMSSData();
				var url = "hrOrganizationTree&orgType=3075&parent=!{id}&isCompile=!{isCompile}"+"&seqRan="+new Date().getTime()+"&isAuth=true&pageNo=!{pageNo}&pageSize=!{pageSize}";
				initialKmssData.AddBeanData(Com_ReplaceParameter(
					url, {
						id : parentId||"",
						isCompile:"true",
						pageNo:pageNo,
						pageSize:pageSize
					}));
				return initialKmssData.GetHashMapArray();
			},
			cancelSearch:function(){
				if(this.cacheContainer){
					this.container.empty();
					this.container.append(this.cacheContainer.children());
					this.cacheContainer=null;
				}
				searchKey = '';
				searchPageNo=1;
			},
			chcheInitData:function(){
				if(!this.cacheContainer){
					this.cacheContainer = $("<div></div>");
					this.cacheContainer.hide();
					this.cacheContainer.append(this.container.children());
				}
			},
			refresh:function(data){
				this.loadedChild={};
				this.container.empty();
				this.createRootNode();
				return this.renderTree(data||this.getChildrenData('',1),{node:this.container,pos:"0"},2);
			},
			research:function(value){
				var url = Com_Parameter.ContextPath+"hr/organization/hr_organization_element/hrOrganizationElement.do?method=searchElement";
				var self = this;
				$.ajax(url,{
					data:{
						_fdKey:value,
						pageNo: searchPageNo,
						pageSize:pageSize
					},
					success:function(data){
						if(data){
							try{
								searchKey = value;
								var JSONData = JSON.parse(data);
								if (searchPageNo == 1) {
									self.chcheInitData();
									lastNodeMap['search'] = self.refresh(JSONData);
									if (JSONData.length == 0 && self.config.showEmpty) {
										self.emptyIcon();
									}
								} else {
									var lastNode = lastNodeMap['search'];
									for(var i = 0;i<JSONData.length;i++){
										let node = self.createNode(JSONData[i],   '0-' + (i), false, false);
										lastNode.after(node);
										lastNode = node;
										node.show();
									}
									if (JSONData.length >= pageSize){
										self.addLoadMore("0-1", {}, 1, lastNode);
									}
									lastNodeMap['search'] = lastNode;
								}
							}catch(e){

							}
						}
					}
				})
			},
			createRootNode:function(){
				var oTr = $("<tr class='hr_tree_table_head'></tr>");
				oTr.append($("<td style='padding-left:20px;text-align:left;width:20%;'>").append(lang['hrOrganizationElement.fdName']).append("</td>"));

				oTr.append($("<td>").append(lang['hrOrganizationElement.fdOrgType']).append("</td>"));
				oTr.append($("<td>").append(lang['hrOrganizationPost.job.num']).append("</td>"));
				oTr.append($("<td>").append(lang['hrOrganizationPost.compilation.num']).append("</td>"));
				oTr.append($("<td>").append(lang['hrOrganizationPost.compile.desc']).append("</td>"));
				if(this.config.hrToEkpEnable == 'true'){/*this.config.authCompile && */
					oTr.append($("<td>").append(lang['hr.organization.info.opertion']).append("</td>"));
				}
				this.container.append(oTr);
			},
			draw : function($super) {
				if (this.isDrawed)
					return;
				var self = this;
				this.element= $("<div class='hr_tree'>");
				this.container = $("<table class='hr_tree_table_container'>");
				this.element.append(this.container);
				var id = this.config.id;
				if(id){
					$("#"+id)&&$("#"+id).append(this.element);
				}
				return this;
			},
			isShowByLevel:function(pos){
				return (pos.split("-").length-1)<this.config.initLevel
			},
			renderTree:function(nodeData,parent,pageNo){
				var newNode;
				if(nodeData instanceof Array){
					var lastNode ;
					for(var i = 0;i<nodeData.length;i++){
						var curpos=parent.pos;
						var curposArr = curpos.split('-');
						var canLoadChild = false;
						curpos=curpos+"-"+i;
						if(i==nodeData.length-1){
							nodeData[i].lastNode=true;
						}
						var children = nodeData[i].hasChildren=="true"?true:false
						nodeData[i].parentId = parent.parentId;
						newNode = this.createNode(nodeData[i],curpos,children,true);
						lastNode = newNode;
						if(parent.pos=="0"&&!this.firstNode){
							this.firstNode = newNode;
						}
						if(this.config.unfoldNodes[curpos]){
							canLoadChild=true;
							this.loadedChild[curpos]=true;
						}
						if(this.isShowByLevel(curpos)||canLoadChild){
							if(children){
								var childrenData = this.getChildrenData(nodeData[i]['value'],1);
								this.renderTree(childrenData,{node:newNode,pos:curpos});
							}
						}
					}
					if (nodeData.length >= pageSize){
						//显示加载更多
						this.addLoadMore(parent.pos, {}, pageNo, lastNode);
					}
				}
				return newNode;
			},
			createNode:function(nodeData,pos,hasChildren,install){
				var position = pos.split('-');
				var node = $("<tr class='tree-node'>");
				var spaceNumber=hasChildren?position.length-1:position.length;
				var paddingValue = 0;
				for(var j = 0;j<spaceNumber;j++){
					paddingValue=paddingValue+20;
				}
				var firstTd = $("<td class='tree-node-table-cell-first'></td>")
				var treeBtn= $("<span class='tree-node-table-btn'><span></span></span>");
				firstTd.css("padding-left",paddingValue+"px");
				if(hasChildren){
					firstTd.append(treeBtn);
					treeBtn.attr("data-folder","true");
					this.clickTreeBtn(treeBtn,pos,nodeData,node);
				}
				var iconType=""
				if(this.config.icon){
					iconType = "<img class='icon-node-type' src=''/>"
				}
				name=$("#namex").text(nodeData['text']).html();
				firstTd.append($("<span class=''>"+iconType+name+"</span>"));
				if(iconType){
					var iconImg = firstTd.find("img");
					if(nodeData.nodeType==2){
						iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_dept.png"
					}
					if(nodeData.nodeType==1){
						iconImg.get(0).src=Com_Parameter.ContextPath+"hr/organization/resource/images/tree_org.png"
					}
				}
				//this.config.icon&&firstTd.append("<i class='tree-node-icon tree-node-icon-has'>");
				node.append(firstTd);
				node.append(this.renderItem(node,nodeData));
				node.attr("data-pos",pos);
				this._showNode(node,pos,treeBtn);
				if(install){
					this.container.append(node);
				}
				return node;
			},
			renderItem:function(node,nodeData){
				var onpostNum = nodeData['onAllPost'];
				var onCompilingNum = nodeData['onCompiling'];
				//组织类型
				node.append($("<td>"+(nodeData['nodeType']==1?lang['enums.org_type.1']:lang['enums.org_type.2'])+"</td>"));
				//在职人数
				node.append($("<td>"+onpostNum+"</td>"));
				//在编人数
				node.append($("<td>"+onCompilingNum+"</td>"));

				//是否开启编制
				var compileNum ="-"
				var compileOpen = nodeData['fdIsCompileOpen']&&nodeData['fdIsCompileOpen']=='true';
				if(compileOpen){
					if(nodeData['fdIsLimitNum']=="true"){
						nodeData['fdCompileNum']===undefined?compileNum='-':compileNum=nodeData['fdCompileNum']
					}else{
						compileNum=lang['hr.organization.info.setup.unlimited'];
					}
				}else{
					compileNum=lang['hrOrganization.set.compile']
				}
				/*node.append($("<td>"+compileNum+"</td>"));*/
				var personNumDetail = onCompilingNum-nodeData['fdCompileNum'];
				var listNum = "";
				if(compileOpen){
					if(nodeData['fdCompileNum']>0&& nodeData['fdIsLimitNum']=="true"){
						//限制编制
						if(personNumDetail>0){
							listNum="<i class='red'></i>&nbsp;"+lang['hr.organization.info.setup.overbooking']+lang['hr.organization.info.emp.p'];
						}else if(personNumDetail==0){
							listNum="<i class='green'></i>&nbsp;"+lang['hr.organization.info.setup.manbian'];
						}else{
							listNum="<i class='blue'></i>&nbsp;"+lang['hr.organization.info.setup.shortage']+Math.abs(personNumDetail)+lang['hr.organization.info.emp.p'];
						}
					}else{
						//不限制编制
						/*if(nodeData['fdCompileNum']==0){
							listNum="0"
						}else{
							listNum="-"
						}*/
						listNum="-"
					}
				}else{
					listNum="-";
				}

				listNum="<span>"+listNum+"</span>";
				node.append($("<td class='hr_compile'>"+listNum+"</td>"));
				var callback = "window.compile_edit('"+nodeData['value']+"','" + nodeData['text'] + "'," +nodeData['fdIsCompileOpen']+")"
				//node.append('<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=updateCompile">');
				//添加权限 this.config.authCompile &&
				if((nodeData['fdCompileAuth'] == 'true' || nodeData['fdCompileAuth'] == true) && this.config.hrToEkpEnable == 'true' && (nodeData['fdIsVirOrg'] == 'false' || nodeData['fdIsVirOrg'] == false)){
					node.append($('<td><a class="lui_text_primary" onclick="'+callback+'">'+lang['hrOrganization.set.compile']+'</a></td>'));
				}else{
					/*node.append($('<td>-</td>'));*/
				}
			},
			addLoadMore:function (pos,nodeData,pageNo,lastNode){
				var posArray = pos.split('-');
				let spaceLeft = (posArray.length + 1) * 20;
				let loadMore = $("<tr class='tree-node' >" +
					"<td class='tree-node-table-cell-first' style='padding-left: " + spaceLeft + "px;'><span style='color: green' class='loadMore'><a>加载更多</a></span></td>" +
					"<td></td><td></td><td></td><td></td><td></td>" +
					"</tr>");
				let loadMoreView = loadMore.find('.loadMore');
				let id = "id" + pos + '-' + (pageNo - 1) * pageSize;
				loadMore.attr("data-pos", pos + '-' + (pageNo - 1) * pageSize);
				loadMore.attr("id",id);
				lastNode.after(loadMore);
				let self = this;
				lastNodeMap[nodeData['value']] = id;
				loadMore.click("click", function () {
					loadMore.click("click", function () {
					});
					loadMoreView.html("加载中...");
					if (searchKey == '') {
						self.asyncLoadChild(nodeData, pos, $('#' + lastNodeMap[nodeData['value']]) || lastNode, pageNo, true, true);
					} else {
						searchPageNo++;
						self.research(searchKey);
					}
					loadMore.remove();
				});
				return loadMore;
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
				var _this = this;
				dom.click("click",function(e){
					var curFooler = pos.split("-").length;
					//获取所有子级节点
					var closeNode = _this.element.find('.tree-node[data-pos*='+pos+'-]')
					//获取子级节点
					var showNode = closeNode.filter(function(index,item){
						if(($(item).attr("data-pos").split("-").length-1)==curFooler){
							return item;
						}
					})

					_this.asyncLoadChild(nodeData,pos,parentNode,1);
					var isFolder = dom.attr("data-folder");
					if(isFolder=="true"){
						dom.attr("data-folder","false");
						showNode.show();
						_this.recordUnfoldNodes(pos,true);
						dom.addClass("tree-node-btn-folder");
					}else{
						dom.attr("data-folder","true");
						_this.recordUnfoldNodes(pos,false);
						closeNode.hide();
						dom.removeClass("tree-node-btn-folder");
						//清除子级展开
						closeNode.find(".tree-node-btn-folder").attr("data-folder","true");
						closeNode.find(".tree-node-btn-folder").removeClass("tree-node-btn-folder");
					}
				})
			},
			asyncLoadChild: function (nodeData, pos, lastNode, pageNo, isLoadMore) {
				let startI = (pageNo - 1) * pageSize;
				var node = undefined;
				if (isLoadMore || !this.isShowByLevel(pos) && !this.loadedChild[pos]) {
					var childrenData = this.getChildrenData(nodeData['value'],pageNo);
					for(var i = 0;i<childrenData.length;i++){
						var hasChildren = childrenData[i].hasChildren == "true" ? true : false
						childrenData[i].parentId = nodeData['value'];
						node = this.createNode(childrenData[i], pos + '-' + (i+startI), hasChildren, false);
						lastNode.after(node);
						lastNode = node;
						node.show();
					}
					if (childrenData.length >= pageSize){
						node = this.addLoadMore(pos, nodeData, pageNo + 1,lastNode);
					}

					this.loadedChild[pos]=true;
				}
				return node;
			},

		});

		exports.TreeTable = TreeTable;
	});
});
