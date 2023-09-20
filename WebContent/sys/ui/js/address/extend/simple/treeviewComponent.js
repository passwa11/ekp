/**
 * 地址本树组件(暂时沿用treeview.js)
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var constant = require('lui/address/addressConstant');
	
	var OrgTreeViewComponent = require('lui/address/component/treeviewComponent').OrgTreeViewComponent;
	
	var CustomerOrgTreeViewComponent = OrgTreeViewComponent.extend({
		
		nodeBeanURL : null,
		
		clickNode : function(node){
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
			var panel = this.parent,
				address = panel.ancestor;
			
			this.nodeBeanURL = address.config.nodeBeanURL;
			
			var ___url = this.nodeBeanURL;
			if(!___url){
				if(node.parameter.indexOf('organizationDialogRoleList')==-1){
					var selectType = params.rightSelectType;
					if(selectType == null || selectType == ""){
						selectType = params.selectType;
					}
					if(this.orgTypeFilter && this.orgTypeFilter != -1){
						selectType = selectType & this.orgTypeFilter;
					}
					___url = Com_ReplaceParameter("organizationDialogList&parent=!{value}&orgType="+selectType, node);
				}else{
					___url = Com_ReplaceParameter(node.parameter, node)
				}
			}else{
				___url = Com_ReplaceParameter(___url +  "&parent=!{value}&orgType="+selectType, node);
			}
			var data = new KMSSData().AddBeanData(___url);
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
		}
		
	});
	
	exports.CustomerOrgTreeViewComponent = CustomerOrgTreeViewComponent;
	
});