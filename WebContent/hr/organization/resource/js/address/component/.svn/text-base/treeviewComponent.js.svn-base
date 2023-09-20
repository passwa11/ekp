/**
 * 地址本树组件(暂时沿用treeview.js)
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var lang = require('lang!sys-organization');
	var uilang = require('lang!sys-ui');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var constant = require('lui/address/addressConstant');
	
	var bind = function(fn, me){ 
		return function(){ 
			return fn.apply(me, arguments); 
		}; 
	}
	
	/**
	 * 抽象树组件(基类)
	 */
	var AbstractTreeViewComponent = base.Component.extend({
		
		orgTypeFilter : -1,
		
		scrollTop : 0,
		
		initProps : function($super, _config) {
			$super(_config);
			this.refName = _config.refName;
		}, 
		
		draw: function($super){
			var self = this;
			this.generateTree();
			this.element.scroll(function(){
				self.scrollTop = self.element.scrollTop();
			});
			this.element.addClass('lui-address-treeview-component');
			$super();
		},
		
		generateTree : function(){
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_NAV_SELECTED'],this.handleNavSelected,this);
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_SEARCH_CANCEL'],this.handleSearchCancel,this);
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_ORGTYPEFILTER_CHANGE'],this.handleOrgTypeFilterChange,this);
			topic.subscribe( constant.event['ADDRESS_TAB_SELECTED'],this.handleSelectedChanged,this);
		},
		
		handleNavSelected : function(args){
			var node = args.node,
				tree = window[this.refName];
			if(typeof(node)=="number"){
				node = Tree_GetNodeByID(tree.treeRoot, node);
			}
			if( node ){
				tree.ClickNode(node);
				if( !node.isExpanded)
					tree.ExpandNode(node);
				this.scrollTo(node);
			}
		},
		
		reload : function(){
			var tree = window[this.refName],
			node = tree.GetCurrentNode() || tree.treeRoot;
			tree.ClickNode(node);
		},
		
		handleSearchCancel : function(){
			this.reload();
		},
		
		handleOrgTypeFilterChange : function(args){
			this.orgTypeFilter = args.orgTypeFilter;
		},
		
		handleSelectedChanged : function(){
			//#22406,IE下切换页签(即div的display值发生变化会重置scrollTop为0，这里在手动设置一下)
			var container = this.element;
			container.scrollTop(this.scrollTop);
		},
		
		//构建根节点到指定节点的数组
		buildNodesData : function(childNode){
			var nodesdata = [];
			nodesdata.unshift({
				id : childNode.id,
				text : childNode.text,
				value : childNode.value
			});
			var tmpNode = childNode;
			while(tmpNode.parent){
				tmpNode = tmpNode.parent;
				var nodedata = {
					id : tmpNode.id,
					value : tmpNode.value,
					text : tmpNode.text	
				};
				nodesdata.unshift(nodedata);
			}
			return nodesdata;
		},
		
		scrollTo : function(node){
			var container = this.element,
				element = document.getElementById("TVN_"+node.id);
			container.scrollTop(element.offsetTop - 35 > 0 ? element.offsetTop - 35 : 0 );
			this.scrollTop = container.scrollTop();
		}
		
		
	});
	
	/**
	 * 组织架构树组件
	 */
	var OrgTreeViewComponent = AbstractTreeViewComponent.extend({
		
		draw : function($super){
			$super();
			this.generateQuickLocate();
		},
		
		generateTree : function($super){
			$super();
			var params = this.parent.params,
				tree = new TreeView(this.refName, lang['sysOrg.address.tree'] , this.element[0]),
				deptLimit = params.deptLimit;
			tree.ClickNode = bind(this.clickNode,this);
			tree.DblClickNode = bind(this.dbclickNode,this);
			var selectType = params.selectType;
			var para = "hrOrganizationDialogList&parent=!{value}&orgType="+selectType;
			if(deptLimit){
				para += "&deptLimit="+deptLimit
			}
			var n1 = tree.treeRoot;
			n1.parameter = para;
			var treeOrgType = selectType;
			if ((treeOrgType & ORG_TYPE_ALL) != ORG_TYPE_ORG){
				treeOrgType = treeOrgType & ~ORG_TYPE_ALL | ORG_TYPE_ORGORDEPT;
			}
			n1.AppendHrOrgData(treeOrgType, para, null, params.startWith, null, deptLimit);
			n1.isExpanded = true;
			window[this.refName] = tree;//被迫暴露个全局变量..
			tree.Show();
			this.clickUserDept();
		},
		
		clickUserDept:function(){
			var tree = window[this.refName],
				kmssData = new KMSSData(),
				params = this.parent.params,
				startWith = params.startWith;
			kmssData.AddBeanData("organizationUserDept&startWith="+startWith);
			var parentNodes = kmssData.GetHashMapArray(),
				visibleParentNode = null;
			for(var i = parentNodes.length - 1; i >= 0; i--){
				var _node = Tree_GetNodeByValue(tree.treeRoot,parentNodes[i].value);
				if(_node){
					visibleParentNode = _node;
					tree.ExpandNode(_node);
				}
			}
			if(parentNodes.length > 0 && visibleParentNode){
				var _node = Tree_GetNodeByValue(tree.treeRoot,visibleParentNode.value);
				tree.ClickNode(_node);
				this.scrollTo(_node);
			}else{
				tree.ClickNode(tree.treeRoot);
			}
		},
		
		clickNode : function(node){
			var params = this.parent.params,
				tree = window[this.refName],
				deptLimit = params.deptLimit;
			if(typeof(node)=="number")
				node = Tree_GetNodeByID(tree.treeRoot, node);
			if(node == null)
				return;
			if(node.parameter==null){
				tree.ExpandNode(node);
				return;
			}
			//获取当前节点下的组织架构信息
			var data, beanData;
			if(node.parameter.indexOf('organizationDialogRoleList')==-1){
				var selectType = params.rightSelectType;
				if(selectType == null || selectType == ""){
					selectType = params.selectType;
				}
				if(this.orgTypeFilter && this.orgTypeFilter != -1){
					selectType = selectType & this.orgTypeFilter;
				}
				beanData = Com_ReplaceParameter("hrOrganizationDialogList&parent=!{value}&orgType="+selectType, node)
				if(deptLimit){
					beanData += "&deptLimit=" + deptLimit
				}
				data = new KMSSData().AddBeanData(beanData);
			}else{
				beanData =  Com_ReplaceParameter(node.parameter, node)
				data = new KMSSData().AddBeanData(beanData);
			}
			//获取当前节点至根节点路径的信息
			var navsdata = this.buildNodesData(node);
			//事件发布
			topic.channel(this.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
				data : data,
				navsdata : navsdata,
				sourceComponent : this //发出数据改变事件的对象
			});
			//选中当前节点
			tree.SetCurrentNode(node);
		},
		
		dbclickNode : function(node){
			var params = this.parent.params,
				selectType = params.selectType,
				tree = window[this.refName];
			if((selectType & ORG_TYPE_DEPT) == ORG_TYPE_DEPT 
					|| (selectType & ORG_TYPE_ORG) == ORG_TYPE_ORG){
				if(typeof(node)=="number")
					node = Tree_GetNodeByID(tree.treeRoot, node);
				if(node == null)
					return;
				if(!(node.nodeType & selectType))
					return;
				if(node.nodeType == '1' || node.nodeType == '2'){
					var kmssData = new KMSSData();
					kmssData.AddHashMap({
						ids : node.value
					});
					kmssData.SendToBean('hrorganizationDialogSearch', function(rtnData){
						topic.publish( constant.event['ADDRESS_ELEMENT_SELECT'],rtnData.data);
					});
				}
			}
		},
		
		//#23244 左侧组织架构树新增快速定位
		generateQuickLocate : function(){
			var parentdom = this.element.parent(),
				quickLocate = $('<div class="lui-address-quciklocate-searchbar" />'),
			    input = $('<input class="lui-form-control" type="text" data-lui-mark="lui-quciklocate-search-input" />'),
			    searchbtn = $('<button type="button" data-lui-mark="lui-quciklocate-search-btn"></button>'),
			    searchDialog = null,
			    searchTimer = null,
			    self = this;
			input.attr('placeholder',uilang['address.input.dept.tip']);
			quickLocate.append(input).append(searchbtn);
			this.element.parent().append(quickLocate);
			$(document.body).click(function(evt){
				var target  = $(evt.target);
				if(!target.parent().hasClass('lui-address-quciklocate-li') && searchDialog){
					searchDialog.hide();
				}
			});
			input.click(function(evt){
				if(!input.val())
					return;
				evt.stopPropagation();
				if(searchDialog && searchDialog.hasValue){
					searchDialog.fadeIn('fast');
				}
			});
			input.keyup(function(evt){
				if(evt.keyCode==13 || evt.keyCode == 38 || evt.keyCode == 40){
					return;
				}
				if(!searchDialog){
					searchDialog = $('<div class="lui-address-quciklocate-dialog" />');
					parentdom.append(searchDialog);
				}
				if(searchTimer){
					clearTimeout(searchTimer);
				}
				searchTimer = setTimeout(function(){
					___search();
					searchTimer = null;
				},500);
			});
			$(quickLocate).keydown(function(e){
				e.stopPropagation();
				var selectedTarget = parentdom.find('.lui-address-quciklocate-li.selected');
				//处理Enter事件
				if(e.keyCode==13){
					___enter();
				}
				//处理上移事件
				if(e && e.keyCode == 38){
					var prevTarget = null;
					if(selectedTarget.length > 0){
						prevTarget = selectedTarget.prev();
					}
					if(prevTarget.length > 0 ){
						___select({ currentTarget : prevTarget[0] });
					}
				}
				//处理下移事件
				if(e && e.keyCode == 40){
					var nextTarget = null;
					if(selectedTarget.length == 0){
						nextTarget = parentdom.find('.lui-address-quciklocate-li:first');
					}else{
						nextTarget = selectedTarget.next();
					}
					if(nextTarget.length > 0 ){
						___select({ currentTarget : nextTarget[0] });
					}
				}
			});
			function ___search(){
				var params = self.parent.params,
					selectType = params.selectType,//筛选组织架构类型
					deptLimit = params.deptLimit,//部门限定参数
					searchBeanURL = params.searchBeanURL,//搜索bean
					kmssData = new KMSSData();
				if ((selectType & ORG_TYPE_ALL) != ORG_TYPE_ORG){
					selectType = selectType & ~ORG_TYPE_ALL | ORG_TYPE_ORGORDEPT;
				}
				selectType = selectType & ~ORG_TYPE_GROUP & ~ORG_TYPE_ROLE;
				if ((selectType & ORG_TYPE_POSTORPERSON) > 0){
					selectType = selectType | ORG_TYPE_ORGORDEPT;
				}else if ((selectType & ORG_TYPE_DEPT) == ORG_TYPE_DEPT){
					selectType = selectType | ORG_TYPE_ORG;
				}
				if(deptLimit){
					searchBeanURL+= "&deptLimit=" + deptLimit;
				}
				kmssData.AddHashMap({
					key : input.val(),
					orgType: selectType,
					accurate: false,
					returnHierarchyId : true
				});
				if(input.val()){
					kmssData.SendToBean(searchBeanURL,function(rtnData){
						searchDialog.html('');
						var ul = $('<ul/>').appendTo(searchDialog);
						if(rtnData.data.length == 0){
							var li = $('<li class="lui-address-quciklocate-li nodata"/>');
							li.text(uilang['address.noData']);
							ul.append(li);
							searchDialog.hasValue = false;
						}else{
							for(var i = 0;i < rtnData.data.length;i++){
								var li = $('<li class="lui-address-quciklocate-li"/>'),
									name = $('<div class="name"/>'),
									dept = $('<div class="dept" />'),
									info = rtnData.data[i].info;
								if(info.indexOf('-') > -1){
									info = info.substring(info.indexOf('-') + 1,info.length);
								}
								li.append(name.text(rtnData.data[i].name).attr('title',rtnData.data[i].name))
									.append(dept.text(info).attr('title',info));
								li.attr('data-hierarchyId',rtnData.data[i].hierarchyIdComplete);
								ul.append(li);
								li.mouseover(___select).click(___enter);
							}
							searchDialog.hasValue = true;
						}
						if( rtnData.data.length == 1){
							li.trigger('mouseover').trigger('click');
						}else{
							searchDialog.fadeIn('fast');
						}
					});
				}else{
					searchDialog.hide();
				}
			}
			//
			function ___select(evt){
				var target = evt.currentTarget,
					selectedTarget = parentdom.find('.lui-address-quciklocate-li.selected');
				selectedTarget.removeClass('selected');
				$(target).addClass('selected');
				if(target.offsetTop + $(target).height() >= searchDialog.height() + searchDialog.scrollTop() ){
					searchDialog.scrollTop(target.offsetTop - searchDialog.height() + $(target).height() + 10 );
				}else if(target.offsetTop <=  searchDialog.scrollTop()){
					searchDialog.scrollTop(target.offsetTop );
				}
			}
			
			function ___enter (){
				var tree = window[self.refName],
					selectedTarget = parentdom.find('.lui-address-quciklocate-li.selected');
				if(selectedTarget.length > 0){
					var hierarchyId = selectedTarget.attr('data-hierarchyId'),
						hierarchyIdArray = hierarchyId.split('x'),
						visibleParentNode = null;
					for(var i = 0 ;i < hierarchyIdArray.length;i++){
						var _node = Tree_GetNodeByValue(tree.treeRoot,hierarchyIdArray[i]);
						if(_node){
							visibleParentNode = _node;
							if(!_node.isExpanded)
								tree.ExpandNode(_node);
						}
					}
					if(visibleParentNode){
						var _node = Tree_GetNodeByValue(tree.treeRoot,visibleParentNode.value);
						tree.ClickNode(_node);
						self.scrollTo(_node);
					}
				}
				if(searchDialog){
					searchDialog.hide();
				}
			}
		}
	});
	
	/**
	 * 群组树组件
	 */
	var GroupTreeViewComponent = AbstractTreeViewComponent.extend({

		generateTree : function($super){
			$super();
			var params = this.parent.params,
				tree = new TreeView(this.refName, lang['sysOrgElement.group'] , this.element[0]),
				selectType = params.selectType;
			tree.ClickNode = bind(this.clickNode,this);
			tree.DblClickNode=bind(this.dbclickNode,this);
			var n1 = tree.treeRoot;
			
			//公共常用群组
			var n2 = n1.AppendChild(lang['sysOrgElement.group.common'],"organizationDialogGroupList&groupCate=!{value}&nodeType=cate&orgType="+selectType);
			n2.AppendBeanData("organizationDialogGroupTree&parent=!{value}","organizationDialogGroupList&groupCate=!{value}&nodeType=!{nodeType}&orgType="+selectType);
			n2.isExpanded = true;
			
			//个人常用群组
			var n3 = n1.AppendChild(lang['sysOrgElement.group.personal']);
			n3.AppendBeanData("organizationDialogMyAddress","organizationDialogMyAddress&id=!{value}&orgType="+selectType,null,false);
			n3.isExpanded = true;
			
			window[this.refName] = tree;//被迫暴露个全局变量..
			tree.ClickNode(n2);
			tree.Show();
		},
		
		clickNode: function(node){
			var params = this.parent.params,
				tree = window[this.refName];
			if(typeof(node)=="number")
				node = Tree_GetNodeByID(tree.treeRoot, node);
			if(node == null)
				return;
			if(node.parameter==null){
				tree.ExpandNode(node);
				return;
			}
			//获取当前节点下的组织架构信息
			var data = node.parameter;
			if(node.parameter.IsKMSSData!=true)
				data = node.parameter = new KMSSData().AddBeanData(Com_ReplaceParameter(node.parameter, node));
			//获取当前节点至根节点路径的信息
			var navsdata = this.buildNodesData(node);
			//发布事件
			topic.channel(this.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
				data : data,
				navsdata : navsdata,
				sourceComponent : this //发出数据改变事件的对象
			});
			//选中当前节点
			tree.SetCurrentNode(node);
		},
		
		dbclickNode : function(node){
			var params = this.parent.params,
				selectType = params.selectType,
				tree = window[this.refName];
			if((selectType & ORG_TYPE_DEPT) == ORG_TYPE_DEPT 
					|| (selectType & ORG_TYPE_ORG) == ORG_TYPE_ORG
					|| (selectType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP){
				if(typeof(node)=="number"){
					node = Tree_GetNodeByID(tree.treeRoot, node);
				}
				if(node == null)
					return;
				if(node.nodeType == 'group' && (selectType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP){
					var kmssData = new KMSSData();
					kmssData.AddHashMap({
						ids : node.value
					});
					kmssData.SendToBean('hrorganizationDialogSearch', function(rtnData){
						topic.publish( constant.event['ADDRESS_ELEMENT_SELECT'],rtnData.data);
					});
				}
				if(node.nodeType =='personalGroup'){
					var kmssData = new KMSSData();
					kmssData.AddHashMap({
						ids : node.value
					});
					kmssData.SendToBean('organizationDialogMyAddress', function(rtnData){
						topic.publish( constant.event['ADDRESS_ELEMENT_SELECT'],rtnData.data);
					});
				}
			}
		}
	
	});
	
	
	var SysRoleTreeViewComponent = AbstractTreeViewComponent.extend({
		
		generateTree : function($super){
			$super();
			var params = this.parent.params,
				tree = new TreeView(this.refName, lang['sysOrgElement.sysRole'], this.element[0]),
				selectType = params.selectType;
			tree.ClickNode = bind(this.clickNode,this);
			var n1 = tree.treeRoot;
			n1.nodeType = 'title';
			if ((selectType & ORG_TYPE_ROLE) == ORG_TYPE_ROLE){
				var listBean = "organizationDialogRoleList&fdConfId=!{value}&orgType="+selectType;
				if(params.mulSelect)
					listBean += "&ismuti=1";
				n2 = n1.AppendChild(lang['table.common.sysOrgRole'], listBean);
				n3 = n1.AppendChild(lang['sysOrg.address.roleLine'],null,null,null,null,'title');
				//n3.AppendBeanData("sysOrgRoleConfTree",listBean,null,false);
				n3.AppendBeanData("sysOrgRoleConfTree&parent=!{value}", listBean);
			}	
			window[this.refName] = tree;//被迫暴露个全局变量..
			tree.ClickNode(n2);
			tree.Show();
		},
			
		clickNode : function(node){
			var pamras = this.parent.params,
				tree = window[this.refName];
			if(typeof(node)=="number")
				node = Tree_GetNodeByID(tree.treeRoot, node);
			//#23503 系统角色页签，点击角色线分类，仅实现展开效果即可，去掉选中效果
			if(node.nodeType == 'cate'){
				tree.ExpandNode(node);
				return;
			}
			if(node == null){
				//发布事件
				topic.channel(this.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
					data : new KMSSData(),
					navsdata : [{
						text : tree.treeRoot.text,
						value : tree.treeRoot.value
					}],
					sourceComponent : this //发出数据改变事件的对象
				});
				return;
			}
			if(node.parameter==null){
				tree.ExpandNode(node);
				return;
			}
			//获取当前节点下的组织架构信息
			var	data = new KMSSData().AddBeanData(Com_ReplaceParameter(node.parameter, node));
			//获取当前节点至根节点路径的信息
			var navsdata = this.buildNodesData(node);
			//发布事件
			topic.channel(this.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
				data : data,
				navsdata : navsdata,
				sourceComponent : this //发出数据改变事件的对象
				
			});
			//选中当前节点
			tree.SetCurrentNode(node);
		},
		
		//构建根节点到指定节点的数组
		buildNodesData : function(childNode){
			var nodesdata = [];
			nodesdata.unshift({
				id : childNode.id,
				text : childNode.text,
				value : childNode.value
			});
			var tmpNode = childNode;
			while(tmpNode.parent){
				tmpNode = tmpNode.parent;
				var nodedata = {
					id : tmpNode.id,
					value : tmpNode.value,
					text : tmpNode.text	
				};
				if(tmpNode.nodeType == 'cate' || tmpNode.nodeType == 'title'){
					nodedata.disable = true;
				}
				nodesdata.unshift(nodedata);
			}
			return nodesdata;
		}
	
	});
	
	
	exports.OrgTreeViewComponent = OrgTreeViewComponent;
	exports.GroupTreeViewComponent = GroupTreeViewComponent;
	exports.SysRoleTreeViewComponent = SysRoleTreeViewComponent;
	
});