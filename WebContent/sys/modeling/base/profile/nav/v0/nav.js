/**
 * 
 */
    var LKSTree;
    //菜单树当前选中节点
    var Modeling_Nav_Tree_CurrentNode;
    //节点id和节点映射,为了上移下移便于查找节点
    var Modeling_Nav_Id_Node_Map = {};
    Tree_IncludeCSSFile();

    //生成树
    function generateTree() {
        var xmlTxt, treeData, nodeArr, i, lv;
        xmlTxt = document.getElementsByName("fdNavContent")[0].value;
        if (xmlTxt == "")
            treeData = [];
        else
            treeData = domain.toJSON(xmlTxt);
        var divTree = document.getElementById("DIV_Tree");
        if (LKSTree) {
            divTree.innerHTML = "";
            delete LKSTree;
        }
        LKSTree = new TreeView("LKSTree", applicationName, divTree);

        Modeling_NavGenerateChildrenTree(LKSTree.treeRoot, treeData);
        Modeling_NavGeneratePortalInfo(treeData);
        LKSTree.ClickNode = Modeling_Nav_OnTreeNodeClick;
        LKSTree.DrawNodeInnerHTML = Modeling_Nav_DrawNodeInnerHTML;
        LKSTree.DrawNodeIndentHTML = Modeling_Nav_DrawNodeIndentHTML;
        LKSTree.MouseOverNode = Modeling_MouseOverNode;
    	LKSTree.MouseOutNode = Modeling_MouseOutNode;
        LKSTree.Show();
        Modeling_Nav_Preview(xmlTxt);
        currentNode = null;
        
        if(!LKSTree.treeRoot.firstChild){
        	$("#DIV_Tree").hide();
    		$("#Empty_Page").show();
    		$("#nav_preview").hide();
    		$("#nav_preview_empty").show();
        }
    }
    
    function Modeling_MouseOverNode(node){
    	if(Modeling_Nav_Tree_CurrentNode && Modeling_Nav_Tree_CurrentNode.id == node){
    		return;
    	}
    	$("a[lks_nodeid='"+node+"']").parents("div.model-menu-td:eq(0)").find("div.node_operation").css("display","inline-block");
    }

    function Modeling_MouseOutNode(node){
    	if(Modeling_Nav_Tree_CurrentNode && Modeling_Nav_Tree_CurrentNode.id == node){
    		return;
    	}
    	$("a[lks_nodeid='"+node+"']").parents("div.model-menu-td:eq(0)").find("div.node_operation").css("display","none");
    }
    
    function Modeling_NavGeneratePortalInfo(data){
    	var isExit = false;
    	for (var i = 0; i < data.length; i++) {
    		if(data[i].nodeType != "portal" || (data[i].nodeType == 'portal' && !data[i].value)){
    			continue;
    		}
    		$("[name='protalTmpURL']").val(data[i].value);
        	$("[name='protalTmpNodeName']").val(data[i].text);
        	$("#portalText").text(data[i].text);
        	$("#portalText").parents("div:eq(0)").addClass("select");
        	$("#portalIcon").show();
        	isExit = true;
        	break;
    	}
    	if(!isExit){
    		$("#portalText").text(lang.buttonAdd);
			$("#portalText").parents("div:eq(0)").removeClass("select");
			$("#portalIcon").hide();
    	}
    }

    
    function Modeling_NavGenerateChildrenTree(root, data) {
        for (var i = 0; i < data.length; i++) {
        	if(data[i].nodeType == 'portal'){//门户节点不显示
        		continue;
        	}
            /* if (data[i].nodeType == "listview"){
                if (__LISTVIEWINFO.listviewIds) {
                    var listviewIdArray = __LISTVIEWINFO.listviewIds.split(";");
                    if (listviewIdArray.indexOf(data[i].value) < 0) {
                        continue;
                    }
                }
            } */
            var node = new TreeNode();
            Modeling_Nav_Id_Node_Map[node.id] = node;
            node.text = data[i].text;
            node.value = data[i].value;
            node.isExpanded = true;
            node.nodeType = data[i].nodeType;
            if (data[i].href != null) {
                node.parameter = new Array();
                node.parameter[0] = data[i].href;
                if (data[i].target != null) {
                    node.parameter[1] = data[i].target;
                }
            }
            root.AddChild(node);
            if (data[i].children != null && data[i].children.length > 0) {
                Modeling_NavGenerateChildrenTree(node, data[i].children);
            }
        }
    }

    //根据节点id获取节点
    function Modeling_Nav_FindNodeById(node) {
        if (typeof (node) == "number") {
            return Modeling_Nav_Id_Node_Map[node];
        }
        return null;
    }

    //树的节点点击事件
    function Modeling_Nav_OnTreeNodeClick(node) {
        if (typeof (node) == "number") {
            node = Tree_GetNodeByID(this.treeRoot, node);
        }
        if(Modeling_Nav_Tree_CurrentNode == node){
        	this.SetCurrentNode(this.treeRoot);//默认就是根节点
        	Modeling_Nav_Tree_CurrentNode = null;
            $("div.node_operation").css("display","none");
        }else{
        	this.SetCurrentNode(node);
        	Modeling_Nav_Tree_CurrentNode = node;
        	$("div.node_operation").css("display","none");
            $("a[lks_nodeid='"+node.id+"']").parents("div.model-menu-td:eq(0)").find("div.node_operation").css("display","inline-block");
        }
    }


    function Modeling_OptionAdd() {
        var currentIndex = LUI("navTabPanel").currentIndex;
        var context = LUI("navTabPanel").contentsNode[currentIndex];
        var listviewIframe = context.find("iframe")[0];
        var listviewTree = listviewIframe.contentWindow.LKSTree;
        var listviewCurrentNodes = listviewTree.GetCheckedNode();
        var isAdd = true;
        var tip = ""
        /*if ((!listviewCurrentNodes || listviewCurrentNodes.length <= 0) && !Modeling_Nav_Tree_CurrentNode) {
            isAdd = false;
            tip = "请先选中两边的节点!"
        } else if ((!listviewCurrentNodes || listviewCurrentNodes.length <= 0)) {
            isAdd = false;
            tip = "请先选中左边的节点!"
        } else if (!Modeling_Nav_Tree_CurrentNode) {
            isAdd = false;
            tip = "请先选中右边的节点!"
        }*/
        	
    	if ((!listviewCurrentNodes || listviewCurrentNodes.length <= 0)) {
            isAdd = false;
            tip = lang.selLeftNode
        }
        if (!isAdd) {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(tip, function () {
                });
            });
            return;
        }
        //获取层级数
        if(Modeling_Nav_Tree_CurrentNode){
        	var num = Modeling_GetHierarchyNum(Modeling_Nav_Tree_CurrentNode) + 1;
            if(num > 3){
            	seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                    dialog.alert(lang.maxLimit3, function () {
                    });
                });
                return;
            }
        }
        if(Modeling_Nav_Tree_CurrentNode == LKSTree.treeRoot || !Modeling_Nav_Tree_CurrentNode){//带层级插入
        	var listviewCurrentNodeTemp = [];
        	var listviewNoAddNodes = [];
        	var currentNodeTemp = [];
        	//先添加二级节点，若存在三级节点（子节点），则一起添加
        	for(var i=0; i<listviewCurrentNodes.length; i++){
            	var listviewCurrentNode = listviewCurrentNodes[i];
            	//业务图表必须先在右边选择
            	if(listviewCurrentNode.nodeType == "chart" || listviewCurrentNode.nodeType == "table" || listviewCurrentNode.nodeType == "chartset"){
            		if (!Modeling_Nav_Tree_CurrentNode) {
	                    tip = lang.selSecondNode;
                    	seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                            dialog.alert(tip, function () {
                            });
                        });
                        return;
                	}
            	}
            	if(listviewCurrentNode.nodeType == "listview"){
            		if(!listviewCurrentNode.parent.isChecked){
            			//业务表单父节点没选
            			if (!Modeling_Nav_Tree_CurrentNode) {
    	                    tip = lang.selSecondNode;
                        	seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                                dialog.alert(tip, function () {
                                });
                            });
                            return;
                    	}
            		}
            	}
            	if(listviewCurrentNodeTemp.indexOf(listviewCurrentNode) != -1 || (listviewCurrentNode.firstChild && listviewCurrentNode.firstChild.firstChild) || listviewCurrentNode.nodeType == 'other'){
            		continue;//已经添加过或者一级的节点跳过
            	}
            	var parentNodeTemp = LKSTree.treeRoot;
            	if(listviewCurrentNode.firstChild && listviewCurrentNodes.indexOf(listviewCurrentNode.firstChild) != -1){
            		//添加父节点
            		var pnode = new TreeNode(listviewCurrentNode.text, null, null, listviewCurrentNode.value, listviewCurrentNode.text, listviewCurrentNode.nodeType);
        			Modeling_Nav_Id_Node_Map[pnode.id] = pnode;
        			parentNodeTemp.isExpanded = true;
        			parentNodeTemp.AddChild(pnode);
        			listviewCurrentNodeTemp.push(listviewCurrentNode);
        			currentNodeTemp.push(pnode);
        			
        			//添加第一个子节点
        			parentNodeTemp = pnode;
        			var cnode = new TreeNode(listviewCurrentNode.firstChild.text, null, null, listviewCurrentNode.firstChild.value, listviewCurrentNode.firstChild.text, listviewCurrentNode.firstChild.nodeType);
        			Modeling_Nav_Id_Node_Map[cnode.id] = cnode;
        			parentNodeTemp.isExpanded = true;
        			parentNodeTemp.AddChild(cnode);
        			listviewCurrentNodeTemp.push(listviewCurrentNode.firstChild);
        			currentNodeTemp.push(cnode);
        			
        			//添加第一个子节点的兄弟节点
        			var listViewChildNode = listviewCurrentNode.firstChild;
        			while(listViewChildNode.nextSibling){
        				var nnode = new TreeNode(listViewChildNode.nextSibling.text, null, null, listViewChildNode.nextSibling.value, listViewChildNode.nextSibling.text, listViewChildNode.nextSibling.nodeType);
            			Modeling_Nav_Id_Node_Map[nnode.id] = nnode;
            			parentNodeTemp.isExpanded = true;
            			parentNodeTemp.AddChild(nnode);
            			listviewCurrentNodeTemp.push(listViewChildNode.nextSibling);
            			currentNodeTemp.push(nnode);
        				listViewChildNode = listViewChildNode.nextSibling;
        			}
            	}else{
            		if(listviewCurrentNode.parent && !listviewCurrentNode.firstChild && listviewCurrentNodes.indexOf(listviewCurrentNode.parent) == -1){
            			listviewNoAddNodes.push(listviewCurrentNode);
            		}
            	}
            }
        	//添加三级节点
        	for(var i=0; i<listviewNoAddNodes.length; i++){
        		var listviewNoAddNode = listviewNoAddNodes[i];
        		var node = new TreeNode(listviewNoAddNode.text, null, null, listviewNoAddNode.value, listviewNoAddNode.text, listviewNoAddNode.nodeType);
    			Modeling_Nav_Id_Node_Map[node.id] = node;
    			LKSTree.treeRoot.isExpanded = true;
    			LKSTree.treeRoot.AddChild(node);
    			listviewCurrentNodeTemp.push(listviewNoAddNode);
    			currentNodeTemp.push(node);
        	}
        }else{
        	for(var i=0; i<listviewCurrentNodes.length; i++){
            	var listviewCurrentNode = listviewCurrentNodes[i];
            	if(listviewCurrentNode.firstChild){
            		continue;
            	}
            	var node = new TreeNode(listviewCurrentNode.text, null, null, listviewCurrentNode.value, listviewCurrentNode.text, listviewCurrentNode.nodeType);
                Modeling_Nav_Id_Node_Map[node.id] = node;
                Modeling_Nav_Tree_CurrentNode.isExpanded = true;
                Modeling_Nav_Tree_CurrentNode.AddChild(node);
            }
        }
        
        LKSTree.Show();
        var treeData = Modeling_NavGenerateData();
        Modeling_Nav_Preview(treeData);
        $("#DIV_Tree").show();
		$("#Empty_Page").hide();
		$("#nav_preview").show();
		$("#nav_preview_empty").hide();
    }

    function Modeling_OptionDelete() {
        if (!Modeling_Nav_Tree_CurrentNode) {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(lang.selDelNode, function () {
                });
            });
            return;
        }
        var node = Modeling_Nav_Tree_CurrentNode;
        if (node.parent == null) {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(lang.noDelRootNode, function () {
                });
            });
            return;
        }
        seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
            dialog.confirm(lang.suerDel, function (value) {
                if (value) {
                    node.Remove();
                    node = null;
                    LKSTree.Show();
                    var treeData = Modeling_NavGenerateData();
                    Modeling_Nav_Preview(treeData);
                    if(!LKSTree.treeRoot || !LKSTree.treeRoot.firstChild){
                    	Modeling_Nav_Tree_CurrentNode = null;
                		$("#nav_preview").hide();
                		$("#nav_preview_empty").show();
                		$("#DIV_Tree").hide();
                		$("#Empty_Page").show();
                    }
                }
            });
        });
    }

    function Modeling_AddPortalInfo(){
    	//拼接门户信息
    	var tmpURL = $("[name='protalTmpURL']").val();
    	var tmpNodeName = $("[name='protalTmpNodeName']").val();
    	var xmlTxt = document.getElementsByName("fdNavContent")[0].value;
    	var treeData = domain.toJSON(xmlTxt);
    	var isEdit = false;
    	for(var i=0; i<treeData.length; i++){
    		var data = treeData[i];
    		if(data.nodeType == 'portal'){
    			data.text = tmpNodeName;
    			data.value = tmpURL;
    			isEdit = true;
    			break;
    		}
    	}
    	if(!isEdit){
    		var no = {};
            no.text = tmpNodeName;
            no.value = tmpURL;
            no.nodeType = 'portal';
            if(!treeData){
            	treeData = [];
            }
            treeData.push(no);
    	}
        document.getElementsByName("fdNavContent")[0].value = domain.stringify(treeData);
    }
    
    function Modeling_Submit(method) {
    	Modeling_AddPortalInfo();
        Com_Submit(document.modelingAppNavForm, method);
    }
    
    function Modeling_GetHierarchyNum(node){
    	var num = 1;
    	while(node.parent != null){
    		num++;
    		node = node.parent;
    	}
    	return num;
    }
    
    //重置
    function Modeling_ResetNavContainer(){
    	//清空节点信息
    	for(var key in Modeling_Nav_Id_Node_Map){
    		var node = Modeling_Nav_Id_Node_Map[key];
    		try{
    			if(node){
        			node.Remove();
        		}
    		}catch(e){}
    	}
    	LKSTree.Show();
    	var treeData = Modeling_NavGenerateData();
        Modeling_Nav_Preview(treeData);
    	Modeling_Nav_Id_Node_Map={};
    	Modeling_Nav_Tree_CurrentNode = null;
    	//重置门户信息
    	$("#portalText").text(lang.buttonAdd);
    	$("#portalText").parents("div:eq(0)").removeClass("select");
    	$("#portalIcon").hide();
    	document.getElementsByName("protalTmpURL")[0].value = "";
		document.getElementsByName("protalTmpNodeName")[0].value = "";
		//重置预览
		$("#nav_preview").hide();
		$("#nav_preview_empty").show();
		//显示图片
		$("#DIV_Tree").hide();
		$("#Empty_Page").show();
    }

    //添加
    function Modeling_AddNavContainer() {
        /*if (Modeling_Nav_Tree_CurrentNode == null &&
            (LKSTree && LKSTree.treeRoot.firstChild != null)) {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert("请先选中节点!", function () {
                });
            });
            return false;
        }*/
        var parameter = {};
        var $nodeNameEle = $("#DIV_Tree").find("input[name$='tmpNodeName']:visible");
        if ($nodeNameEle.length > 0) {
            $nodeNameEle.select();
            return false;
        }
        parameter.isEdit = true;
        var node = new TreeNode("", parameter);
        node.nodeType = "none";
        node.value = "";
        var parentNode = Modeling_Nav_Tree_CurrentNode || LKSTree.treeRoot;
        //获取层级数
        var num = Modeling_GetHierarchyNum(parentNode) + 1;
        if(num > 3){
        	seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(lang.maxLimit3, function () {
                });
            });
            return false;
        }
        Modeling_Nav_Id_Node_Map[node.id] = node;
        parentNode.isExpanded = true;
        parentNode.AddChild(node);
        LKSTree.Show();
        var $nodeNameEle = $("#DIV_Tree").find("input[name$='tmpNodeName']:visible");
        $nodeNameEle.select();
        parameter.isEdit = false;
        
        //恢复样式
        if(Modeling_Nav_Tree_CurrentNode){
        	$("a[lks_nodeid='"+Modeling_Nav_Tree_CurrentNode.id+"']").parents("div.model-menu-td:eq(0)").find("div.node_operation").css("display","inline-block");
        }
        $("#DIV_Tree").show();
		$("#Empty_Page").hide();
		$("#nav_preview").show();
		$("#nav_preview_empty").hide();
        return true;
    }

    //编辑按钮
    function Modeling_NavEdit(self) {
        var parent = $(self).closest("td");
        parent.find("a[lks_nodeid]").hide();
        parent.find("input[name$='tmpNodeName']").show();
        parent.find("input[name$='tmpNodeName']").select();
    }

    //编辑框失焦
    function Modeling_NavNameBlur(self) {
        var value = self.value;
        //校验表单名称是否为空
        if (!value) {
            $(self).attr("onblur", "");
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(lang.nameRrequired, function () {
                    $(self).attr("onblur", "Modeling_NavNameBlur(this)");
                    $(self).select();
                });
            });
            return
        }
        var a = $(self).parent().find("a[lks_nodeid]");
        var nodeId = a.attr("lks_nodeid");
        node = Tree_GetNodeByID(LKSTree.treeRoot, nodeId);
        node.text = value;
        a.attr("title", value);
        if (value.length > 10) {
            value = value.substring(0, 10) + "..."
        }
        a.text(value);
        a.show();
        $(self).hide();
        var treeData = Modeling_NavGenerateData();
        Modeling_Nav_Preview(treeData);
    }

    //上移
    function Modeling_NavUp(node) {
        if (typeof (node) == "number")
            node = Modeling_Nav_FindNodeById(node);
        if (node == null)
            return;
        var pNode = node.parent;
        if (pNode == null)
            return;
        var preNode = node.prevSibling;
        if (preNode == null)
            return;
        pNode.AddChild(node, preNode);
        LKSTree.Show();
        var treeData = Modeling_NavGenerateData();
        Modeling_Nav_Preview(treeData);
    }

    //下移
    function Modeling_NavDown(node) {
        if (typeof (node) == "number")
            node = Modeling_Nav_FindNodeById(node);
        node = node.nextSibling;
        if (node == null)
            return;
        var pNode = node.parent;
        if (pNode == null)
            return;
        var preNode = node.prevSibling;
        if (preNode == null)
            return;
        pNode.AddChild(node, preNode);
        LKSTree.Show();
        var treeData = Modeling_NavGenerateData();
        Modeling_Nav_Preview(treeData);
    }

    //删除
    function Modeling_NavDelete(node) {
        if (typeof (node) == "number")
            node = Modeling_Nav_FindNodeById(node);
        if (node.parent == null) {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(lang.noDelRootNode, function () {
                });
            });
            return;
        }
        seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
            dialog.confirm(lang.suerDel, function (value) {
                if (value) {
                    node.Remove();
                    node = null;
                    LKSTree.Show();
                    var treeData = Modeling_NavGenerateData();
                    Modeling_Nav_Preview(treeData);
                    Modeling_Nav_Tree_CurrentNode = null;
                    if(!LKSTree.treeRoot || !LKSTree.treeRoot.firstChild){
                		$("#nav_preview").hide();
                		$("#nav_preview_empty").show();
                		$("#DIV_Tree").hide();
                		$("#Empty_Page").show();
                    }
                }
            });
        });
    }

    //根据节点生成json数据
    function Modeling_NavGenerateData() {
    	var treeData = domain.stringify(Modeling_NavGetChildrenXML(LKSTree.treeRoot));
    	document.getElementsByName("fdNavContent")[0].value = treeData;
        return treeData;
    }

    function Modeling_NavGetChildrenXML(node) {
        var children = [];
        for (var child = node.firstChild; child != null; child = child.nextSibling) {
        	//若一级并且只有一级节点
        	if(child.parent == LKSTree.treeRoot && child.firstChild == null){
        		continue;
        	}
            var no = {};
            no.text = child.text;
            no.value = child.value;
            no.nodeType = child.nodeType;
            if (child.parameter != null) {
                no.href = child.parameter[0];
            }
            if (child.firstChild != null) {
                no.children = Modeling_NavGetChildrenXML(child);
            }
            children.push(no);
        }
        return children;
    }
    
  	//预览
  	function Modeling_Nav_Preview(navContent){
  		if (!navContent) {
  			navContent = domain.stringify(Modeling_NavGetChildrenXML(LKSTree.treeRoot));
  		}
  		var $navPreviewItemWrap = $("#nav_preview_item_wrap");
  		$navPreviewItemWrap.empty();
  		var previewHtml = [];
  		Modeling_Nav_GenerateNavPreview(navContent,previewHtml);
  		$navPreviewItemWrap.append(previewHtml.join(""));
  		Modeling_Nav_BindEvent();
  		
    }
  	
  	function Modeling_Nav_BindEvent(){
  		$("#nav_preview_item_wrap").find("li.model-sidebar-item").click(function(){
  			if ($(this).hasClass("item-expansion")) { //收起
	  				$(this).removeClass("item-expansion");
	  				$(this).next("ul").removeClass("item-expansion-child");
	  			} else { //展开
	  				$(this).addClass("item-expansion");
	  				$(this).next("ul").addClass("item-expansion-child");
	  				$(this).addClass("nav_item_active");
	  			}
  		});
  	}
  	
  	function Modeling_Nav_GenerateNavPreview(navContent,previewHtml) {
		if (navContent == "") {
			navContent = [];
		} else {
		 	navContent = domain.toJSON(navContent);
		}
		for (var index = 0; index < navContent.length; index++) {
			var itemHtml = Modeling_Nav_Preview_CreateItem(navContent[index]);
		    previewHtml.push(itemHtml);
		    if (navContent[index].children) {
		    	previewHtml.push("<ul class='model-sidebar-wrap model-sidebar-wrap-child'>");
	    		Modeling_Nav_GenerateNavPreview(navContent[index].children,previewHtml);
		    	previewHtml.push("</ul>");
		    }
		}
	}
  	
  	function Modeling_Nav_Preview_CreateItem(node){
  		var itemHtml = [];
  		itemHtml.push("<li class='model-sidebar-item" + (node.children ? " model-item-expansion" : "")  + "' title='" + node.text + "'>");
  		itemHtml.push("<div class='model-sidebar-item-box'>");
  		itemHtml.push("<i class='model-sidebar-icon sidebar-icon-info'></i>");
  		var text = node.text;
  		if (text && text.length >= 10) {
  			text = text.substring(0,10) + "...";
  		}
  		itemHtml.push("<span class='model-sidebar-desc'>" + text + "</span>");
  		itemHtml.push("</div>");
  		itemHtml.push("</li>");
  		return itemHtml.join("");
  	}

    LUI.ready(function () {
        $("#modeling_listview").css({"margin-top": "5px", "margin-left": "15px"});
        $("#modeling_rptview").css({"margin-top": "5px", "margin-left": "15px"});
        $("#modeling_otherListview").css({"margin-top": "5px", "margin-left": "15px"});
        $("#modeling_otherRptview").css({"margin-top": "5px", "margin-left": "15px"});
        var host = location.host;
	    var fdUrl = host + Com_Parameter.ContextPath  + "sys/modeling/main/index.jsp?fdAppId=" + fdAppId + "&fdNavId=" + fdNavId ;
    	$("a[name='__fdUrl']").text(fdUrl);
    	var edit = __edit == "edit";
    	if (edit) {
			$(".modeling_app_right_navs_item_l").attr("title",lang.unfold);
			$(".modeling_app_right_navs_item_l").removeClass("modeling_app_right_expand_item");
			$(".modeling_app_right_navs_item_l").addClass("modeling_app_right_hide_item");
    	}
    	$(".modeling_app_right_navs_item_l").click(function(){
    		var expand = $(this).hasClass("modeling_app_right_expand_item");
    		if (expand) {
    			$(this).removeClass("modeling_app_right_expand_item");
    			$(this).addClass("modeling_app_right_hide_item");
    			$(this).attr("title",lang.unfold);
    			$(".model-mask-panel-table").hide();
    		} else {
    			$(this).removeClass("modeling_app_right_hide_item");
    			$(this).addClass("modeling_app_right_expand_item");
    			$(this).attr("title",lang.putAway);
    			$(".model-mask-panel-table").show();
    		}
    	});
    });
    
    function redirectIndex(src) {
    	var host = location.host;
	    var fdUrl = Com_Parameter.ContextPath  + "sys/modeling/main/index.jsp?fdAppId=" + fdAppId + "&fdNavId=" + fdNavId ;
    	window.open(fdUrl,"_blank");
    }


    seajs.use(['lui/dialog'], function (dialog) {
        //此方法已弃用，下次看到可以删掉
        // window.openListviewsDialog = function (dom) {
        //     var curListviewIds = $(dom).closest("td").find("[name*='fdListviewIds']").val();
        //     var url = "/sys/modeling/base/profile/nav/v0/container/dialog.jsp?curListviewIds=" + curListviewIds + "&fdAppId=${JsParam.fdAppId}";
        //     dialog.iframe(url, "选择列表视图", function (rs) {
        //         Modeling_SetListviews(rs, dom);
        //     }, {width: '720', height: '500', params: {"__LISTVIEWINFO": __LISTVIEWINFO}});
        // }
        
        window.editNav = function(){
        	var url = "/sys/modeling/base/modelingAppNav.do?method=edit&type=createNav&fdAppId=" + fdAppId + "&fdId=" + fdNavId;
    		dialog.iframe(url, lang.baseinfo, function (rt) {
    			if(rt && rt.type && rt.type == "success"){//成功时把数据添加到页面，总体进行保存
    				var data = rt.data;
    				$("[name='docSubject']").val(data.docSubject || "");
                	$("[name='fdOrder']").val(data.fdOrder || "");
                	$("[name='authReaderIds']").val(data.authReaderIds || "");
                	$("[name='authReaderNames']").val(data.authReaderNames || "");
    			}
    		}, {width: 550, height: 365, params:{"isUpdate":true}});
        }
        
        window.generateUrl = function(){
        	//var tmpURL = $("input[name='tmpURL']").val();
        	//var tmpNodeName = $("input[name='tmpNodeName']").val();
        	/*var url = "/sys/modeling/base/manage/nav/portal_dialog.jsp?tmpURL="+tmpURL+"&tmpNodeName="+tmpNodeName;
    		dialog.iframe(url, "添加门户链接", function (rt) {
    			if(rt && rt.type && rt.type=='1'){
    				$(obj).text(rt.tmpNodeName);
        			$("input[name='tmpURL']").val(rt.tmpURL);
        			$("input[name='tmpNodeName']").val(rt.tmpNodeName);
    			}
    		}, {width: 650, height: 250, params:{}});*/
        	dialog.iframe('/sys/portal/sys_portal_page/sysPortalPage_select.jsp?single=true',
 					lang.selectPage,function(val){
 					if(val){
 						if(!val.fdId){
 							$("#portalText").text(lang.buttonAdd);
 							$("#portalText").parents("div:eq(0)").removeClass("select");
 	 						$("#portalIcon").hide();
 	 						document.getElementsByName("protalTmpURL")[0].value = "";
 	 						document.getElementsByName("protalTmpNodeName")[0].value = "";
 						}else{
 							$("#portalText").text(val.fdName || "");
 	 						document.getElementsByName("protalTmpURL")[0].value = val.fdId ? "/sys/portal/page.jsp?mainPageId=" + val.fdId : "";
 	 						document.getElementsByName("protalTmpNodeName")[0].value = val.fdName || "";
 	 						$("#portalText").parents("div:eq(0)").addClass("select");
 	 						$("#portalIcon").show();
 						}
 					}
 			},{"width":650,"height":550});
        }
    });

    function Modeling_SetListviews(rs, dom) {
        if (rs) {
            $(dom).closest("td").find("[name*='fdListviewIds']").val(rs.listviewIds);
            $(dom).closest("td").find("[name*='fdListviewNames']").val(rs.listviewNames);
        }
    }
    
    generateTree();
