/**
 * 思维导图 —— 其他节点
 */
define(function (require, exports, module) {
    var $ = require("lui/jquery"),
        base = require("lui/base"),
        topic = require("lui/topic"),
        mindMapOtherNodeItem = require("sys/modeling/base/views/business/res/mindMapOtherNodeItem");
    var modelingLang = require("lang!sys-modeling-base");
    var MindMapOtherNode =  base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.$temp = cfg.$temp;
            this.parent = cfg.parent;
            this.otherNodeCollection = [];      //存放其他节点的数组
            this.otherNodeIdCollection=[];      //存放目标表单的id
            this.otherTreeNodeCollection=[];      //存放树节点
            this.bindEvent();
        },
        bindEvent: function () {
            var self = this;
            this.$temp.find(".model-data-create").on("click", function (e) {
                self.build();
            })
        },
        nodeIdRefresh:function (){
            var self = this;
            for(var i = 0;i<self.otherNodeCollection.length;i++){
                var cSet = self.otherNodeCollection[i].otherNodeSettingButton.otherNodeSetting;
                // console.log(cSet);
            }

        },
        build: function (config) {
            var self = this;
            var $table = self.$temp.find(".mind-map-other-node");
            var cfg = {
                container: $table,
                parent: self,
                widgets : self.parent.widgets,
                config:config
            };
            self.mindMapOtherNodeItem = new mindMapOtherNodeItem.MindMapOtherNodeItem(cfg);
            self.mindMapOtherNodeItem.startup();
            self.otherNodeCollection.push(self.mindMapOtherNodeItem);
            self.$temp.find(".model-panel-table-base").css("display","block");
            //交互上不需要拖动
            // self.sortable("otherNode");

        },
        delItem : function (wgt,targetModelId) {
            for(var i = 0;i < this.otherNodeCollection.length;i++){
                if(this.otherNodeCollection[i] === wgt){
                    this.otherNodeCollection.splice(i,1);
                    i--;
                }
            }
            for(var i = 0;i < this.otherNodeIdCollection.length;i++) {
                if (this.otherNodeIdCollection[i] === targetModelId) {
                    this.otherNodeIdCollection.splice(i,1);
                    i--;
                }
            }
            for(var i = 0;i < this.otherTreeNodeCollection.length;i++) {
                if (this.otherTreeNodeCollection[i].value === targetModelId) {
                    this.otherTreeNodeCollection.splice(i,1);
                    i--;
                }
            }
            this.refreshIndexWhenDelete(wgt);
            wgt.destroy();
            topic.publish("mindMap.nodeChange",{evt:this,msg:modelingLang['modelingTreeView.delete.node.config']})
        },
        refreshIndexWhenDelete : function(wgt){
            if(wgt.id){
                var wiget = LUI(wgt.cid);
                var deleteIndex = wiget.element.index();
                $("#otherNode").find(".otherItem").each(function () {
                    if($(this).index() > deleteIndex){
                        var oldIndex = $(this).index();
                        var newIndex = oldIndex - 1;
                        $(this).attr("index",newIndex);
                        if($(this).find(".model-edit-view-oper-head-title span").text().indexOf(modelingLang['modelingTreeView.node']) > -1){
                            var text = $(this).find(".model-edit-view-oper-head-title span").text();
                            $(this).find(".model-edit-view-oper-head-title span").text(modelingLang['modelingTreeView.node']+oldIndex);
                        }
                    }
                });
            }
        },
        sortable : function(type) {
            var list = $("#"+type)[0];
            var self = this;
            Sortable.create(list,{
                sort: true,
                scroll: true,
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle:".sortableIcon",
                draggable: ".sortItem",
                onStart: function(evt) {
                    console.log(evt);
                },
                onEnd: function (evt) {
                    self.refreshWhenSort(evt);
                    topic.publish("preview.refresh", {key: self.channel});
                }
            });
        },

        /**
         * 拖动重新排序组件数据跟刷新对应的index
         * @param evt
         */
        refreshWhenSort : function(evt) {
            var sortItem = evt.item;
            var context = $(sortItem).closest("[data-table-type]");
            var type = context.attr("data-table-type");
            var srcWgts = [];
            if (type === "otherNode") {
                srcWgts = this.otherNodeCollection;
            }
            var targetWgts = [];
            context.find(".sortItem").each(function (index, obj){
                var oldIndex = parseInt($(obj).attr("index"));
                targetWgts.push(srcWgts[oldIndex]);
            });
            if (type === "otherNode") {
                this.refreshOrderIndex();
                this.orderCollection = targetWgts;
            }
        },
        refreshOrderIndex : function() {
            $("#otherNode").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdOtherNode-" + index);
                });
            });
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        verifyLoop:function(){
            // return this.verifyOtherNodes(this.getKeyData());
            return true;
        },
        /**
         * 检测是否节点相互引用
         * @param fdOtherNodes
         */
        verifyOtherNodes:function (fdOtherNodes) {
            var self = this;
            if(0 == fdOtherNodes.length){
                return true;
            }
            var fdOtherNode;
            var fdPreNodes = [];
            var fdPreNode;
            var treeNodes = [];
            var treeNode;

            var fdTargetModelId = '';
            //生成关系树
            for(var i=0;i<fdOtherNodes.length;i++){
                fdOtherNode = fdOtherNodes[i];
                fdTargetModelId = fdOtherNode.fdTargetModelId;
                fdPreNodes = fdOtherNode.fdPreNode;
                for(var j=0;j<fdPreNodes.length;j++){
                    var curTreeNode = undefined;
                    var perTreeNode = undefined;
                    fdPreNode = fdPreNodes[j];
                    //当前（表单-字段）不允许和父级相同
                    if(fdTargetModelId == fdPreNode.fdPreModelId && fdPreNode.fdTargetModelField == fdPreNode.fdCurModelField ){
                        return false;
                    }
                    for(var k=0;k<treeNodes.length;k++){
                        treeNode = treeNodes[k];
                        if(fdTargetModelId == treeNode.modelId && fdPreNode.fdCurModelField == treeNode.field ){
                            //子
                            curTreeNode = treeNode;
                        }
                        if(fdPreNode.fdPreModelId == treeNode.modelId && fdPreNode.fdTargetModelField == treeNode.field ){
                            //父
                            perTreeNode = curTreeNode;
                        }
                    }
                    if(!perTreeNode){
                        perTreeNode = {};
                        perTreeNode.parent= {};
                        perTreeNode.modelId = fdPreNode.fdPreModelId;
                        perTreeNode.field = fdPreNode.fdTargetModelField ;
                        perTreeNode.child=[];
                        treeNodes.push(perTreeNode);
                    }
                    if(!curTreeNode){
                        curTreeNode = {};
                        curTreeNode.parent= {};
                        curTreeNode.modelId = fdTargetModelId;
                        curTreeNode.field = fdPreNode.fdCurModelField;
                        curTreeNode.child=[];
                        treeNodes.push(curTreeNode);
                    }else{
                        curTreeNode.parent = perTreeNode;
                    }
                    perTreeNode.child.push(curTreeNode);
                }
            }
            //判断关系树是否成环
            for(var i=0;i<treeNodes.length;i++){
                treeNode = treeNodes[i];
                var nodeSet = [];
                if(false == self.isRing(treeNode,nodeSet)){
                    return false;
                }
            }
            return true;
        },

        /**
         * 检测是否节点是否成环
         * @param treeNode
         * @param nodeSet
         */
        isRing:function (treeNode,nodeSet){
            var node;
            for(var i = 0 ;i<nodeSet.length;i++){
                node = nodeSet[i];
                if(treeNode.modelId == node.modelId  && treeNode.field == node.field){
                    return false;
                }
            }
            nodeSet.push({modelId:treeNode.modelId,field:treeNode.field});
            if(0 < treeNode.child.length){
                for(var i=0;i<treeNode.child.length;i++){
                    if(false == this.isRing(treeNode.child[i],nodeSet)){
                        return false;
                    }
                }
            }
            return true;
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            var otherNodeArr = [];
            for(var i = 0;i<this.otherNodeCollection.length;i++){
                otherNodeArr.push(this.otherNodeCollection[i].getKeyData());
            }
            return otherNodeArr;
        },
        //后台数据渲染方法
        initByStoreData: function (otherNodeCollection) {
            for(var i = 0;i<otherNodeCollection.length;i++){
                var config = otherNodeCollection[i];
                if(!config.nodeSettingId){
                    config.nodeSettingId=config.fdTargetModelId;
                }
                this.otherNodeIdCollection.push({"fdTargetModelId":config.fdTargetModelId,"nodeSettingId":config.nodeSettingId});
                this.otherTreeNodeCollection.push({"value":config.fdTargetModelId,"name":config.fdTargetModelName});
                this.build(config);
            }

        }
    });
    exports.MindMapOtherNode = MindMapOtherNode;
})