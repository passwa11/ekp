<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<style>

</style>
<!-- 内容区 starts -->
<div class="fullscreen">
    <div class="model-body ">
        <div class="model-body-warp">
            <div class="model-body-header">
                <p class="model-body-header-title">${lfn:message('sys-modeling-base:relation.associated.display.area')}</p>
            </div>
            <div class="model-body-desc">
                <div class="model-body-desc-wrap">
                    <p class="model-body-header-title">业务关联</p>
                    <p class="model-body-header-desc">
                        ${lfn:message('sys-modeling-base:relation.can.associated')} <span id="relationAllNumber">0</span>${lfn:message('sys-modeling-base:relation.pcs')}
                        ${lfn:message('sys-modeling-base:relation.remaining.linkable')} <span id="relationEmptyNumber">0</span>${lfn:message('sys-modeling-base:relation.pcs')}</p>
                </div>
               <div class="model-body-desc-wrap">
                   <p class="model-body-header-title">业务填充</p>
                   <p class="model-body-header-desc">
                       ${lfn:message('sys-modeling-base:relation.can.associated')} <span id="fillingAllNumber">0</span>${lfn:message('sys-modeling-base:relation.pcs')}
                       ${lfn:message('sys-modeling-base:relation.remaining.linkable')} <span id="fillingEmptyNumber">0</span>${lfn:message('sys-modeling-base:relation.pcs')}</p>
                   <div class="model-body-header-desc-tip">
                       <span></span>
                   </div>
               </div>
            </div>
            <div class="model-body-content">
                <!-- 这里放入你的组件 starts -->
                <div class="model-body-content-wrap" ondragover="allowDrop(event)" ondrop="drop(event)">
                    <div id="graphContainer" style="">
                    </div>
                </div>
                <!-- 这里放入你的组件 ends -->
            </div>
        </div>
    </div>
    <!-- 内容区 ends -->

    <!-- 右边栏 starts 位置-->
    <div class="model-rightbar associated_container relation">
        <div class="model-rightbar-btn">
            <div class="model-rightbar-btn-center">
                <i></i>
                <%-- 收起按钮--%>
                <p class="model-rightbar-btn-tips">${lfn:message('sys-modeling-base:relation.associated.template.library')}</p>
            </div>
        </div>
        <div class="model-rightbar-header header-modeling">
            <div class="model-rightbar-header-title">
                <div class="model-rightbar-header-text">
                    <p>${lfn:message('sys-modeling-base:relation.associated.template.library')}</p>
                </div>
                <div class="lui_profile_listview_searchWrap" style="float:left;margin:0 0 0 25px;">
                    <input type="text" class="lui_profile_search_input" id="modelingProfileSearch"
                           placeholder="${lfn:message('sys-modeling-base:modeling.profile.search')}"
                           onkeyup='searchModel(event,this);'>
                    <i class="lui_profile_listview_icon lui_icon_s_icon_search"
                       onclick="clickSearchModel('modelingProfileSearch');"></i>
                </div>
            </div>
        </div>

        <div class="model-rightbar-header header-relation">
            <div class="model-rightbar-header-title">
                <div class="model-rightbar-header-title-left" onclick="rebuild()" style="cursor: pointer">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:modeling.form.RelationshipSet')}</p>
                </div>
                <div class="model-rightbar-header-title-right" style="display: none">
                    <div class="model-rightbar-header-title-sum">
                        <p>图书分类</p><i></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="model-rightbar-scroll ">
            <div class="model-rightbar-sub ">
                <div class="associated_quickly_intro">
                    <div class="associated_quickly_intro_title">
                        <strong>${lfn:message("sys-modeling-base:relation.fast.know")}</strong>
                        <span class="associated_quickly_intro_slide">
                        <span>${lfn:message("sys-modeling-base:modelingApplication.putAway")}</span>
                        <i></i>
                    </span>
                    </div>
                    <div class="associated_quickly_intro_content">
                        <div class="associated_quickly_intro_item left">
                            <div class="associated_quickly_intro_item_title" onclick="openVideo()">
                                <i></i>
                                <span>${lfn:message("sys-modeling-base:relation.business.association")}</span>
                            </div>
                            <p>
                                ${lfn:message("sys-modeling-base:relation.association.tips")}
                            </p>
                        </div>
                        <div class="associated_quickly_intro_item right">
                            <div class="associated_quickly_intro_item_title" onclick="openVideo()">
                                <i></i>
                                <span>${lfn:message("sys-modeling-base:relation.business.filling")}</span>
                            </div>
                            <p>
                                ${lfn:message("sys-modeling-base:relation.filling.tips")}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="model-rightbar-bottom">
            <div class="model-rightbar-bottom-btn" style="margin-left: 25%" onclick="createRelation('0')">
                <div>
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:relation.new.business.linkage')}</p>
                </div>
            </div>
            <div class="model-rightbar-bottom-btn" onclick="createRelation('1')" style="display: none">
                <div>
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:relation.new.business.penetration')}</p>
                </div>
            </div>
            <div class="model-rightbar-bottom-btn" onclick="createRelation('2')" style="display: none">
                <div>
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:relation.new.business.filling')}</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    //引入mx 绘图
    window.mxBasePath = Com_Parameter.ContextPath + 'sys/ui/js/mxgraph/lib';
</script>
<script src="${LUI_ContextPath}/sys/ui/js/mxgraph/lib/mxclient.src.js"></script>
<script>


    seajs.use(["lui/jquery", "sys/ui/js/dialog",
            "sys/modeling/base/relation/graph/graph",
            "sys/modeling/base/relation/graph/rightModelList"],
        function ($, dialog, MxGraph, rml) {

            //业务关系初始化
            var relation = ${relation};
            //初始化相关参数
            var _index = this;
            _index.cfg = {
                applicaton: relation.applicaton,
                nodes: relation.nodes,
                edges: relation.edges,
                relationFields: relation.relationFields,
                fillingFields: relation.fillingFields
            };
            if (relation.error) {
                dialog.alert(relation.error)
            }
            //------------------提示关联数字
            //#1 提示区
            _index.relationNumber = {
                all: 0,
                selected: 0,
                enable: 0,
                allFill:0,
                fillSelected:0,
                fillEnable:0,
                fieldInfos:{},
                init: function () {
                    for (var k in _index.cfg.relationFields) {
                        this.all++;
                    }
                    for (var k in _index.cfg.fillingFields) {
                        var field = _index.cfg.fillingFields[k];
                        if(field.customProperties && field.customProperties.bindCount){
                            this.allFill+=parseInt(field.customProperties.bindCount);
                            this.fieldInfos[field.name]={};
                            this.fieldInfos[field.name].bindCount = parseInt(field.customProperties.bindCount);
                            this.fieldInfos[field.name].label = field.label;
                        }
                    }
                    for (var i in _index.cfg.nodes) {
                        if (_index.cfg.nodes[i].level === 1 && _index.cfg.nodes[i].widgetInfos) {
                            var widgetInfo = _index.cfg.nodes[i].widgetInfos;
                            for (var key in widgetInfo) {
                                var info = widgetInfo[key];
                                if(info.type == "2") {
                                    this.fillSelected += parseInt(info.count);
                                    if(this.fieldInfos.hasOwnProperty(key)){
                                        this.fieldInfos[key].count = parseInt(info.count);
                                    }
                                }else{
                                    this.selected += parseInt(info.count);
                                }
                            }
                        }
                    }
                    this.update( this.selected);
                    this.updateFill( this.fillSelected);
                },
                update: function (s) {
                    this.enable = this.all - s;
                    if (this.enable <0 ){
                        this.enable = 0;
                    }
                    $("#relationAllNumber").text(this.all);
                    $("#relationEmptyNumber").text(this.enable)
                },
                updateFill: function (s,widgetInfos) {
                    if(widgetInfos){
                        for (var key in widgetInfos) {
                            var info = widgetInfos[key];
                            if(info.type == "2") {
                                if(info.count && this.fieldInfos[key]){
                                    this.fieldInfos[key].count = parseInt(info.count);
                                }
                            }
                        }
                        var count = 0;
                        for (var key in this.fieldInfos) {
                            var info = this.fieldInfos[key];
                            if(info.count){
                                count += parseInt(info.count);
                            }
                        }
                        s = count >0 ?count:s;
                    }
                    this.fillEnable = this.allFill - s;
                    if (this.fillEnable <0 ){
                        this.fillEnable = 0;
                    }
                    $("#fillingAllNumber").text(this.allFill);
                    $("#fillingEmptyNumber").text(this.fillEnable)
                },
                getEnableCount:function (){
                    return this.enable;
                },
                getFillEnableCount:function (){
                    return this.fillEnable;
                },
                showTip:function (){
                    $(".model-body-header-desc-tip").empty();
                    for (var key in this.fieldInfos) {
                        var info = this.fieldInfos[key];
                        var $div =$("<div class='model-body-desc-wrap'></div>");
                        var $title= $("<p class='model-body-header-title'></p>");
                        $title.text(info.label);
                        $div.append($title);
                        var $desc = $("<p class='model-body-header-desc'></p>");
                        var html = "${lfn:message('sys-modeling-base:relation.can.associated')}"
                            +" <span>"+ (info.bindCount || 0) +"</span>"
                            + "${lfn:message('sys-modeling-base:relation.pcs')}"
                            + "${lfn:message('sys-modeling-base:relation.remaining.linkable')}"
                            + "<span>"+ (info.bindCount - (info.count || 0)) +"</span>"
                            + "${lfn:message('sys-modeling-base:relation.pcs')}";
                        $desc.html(html);
                        $div.append($desc);
                        $(".model-body-header-desc-tip").append($div);
                    }
                    $(".model-body-header-desc-tip").addClass("active");
                },
                hideTip:function (){
                    $(".model-body-header-desc-tip").removeClass("active");
                }
            };
            _index.relationNumber.init();

            //#2 拖拽区
            function scanDisableModel() {
                var disModel = mxGraph.data.nodes;
                if (disModel) {
                    $(".disDrag").attr("draggable", true)
                    $(".disDrag").removeClass("disDrag");
                    for (var d in disModel) {
                        var did = disModel[d].id;
                        if (did) {
                            if (did === relation.applicaton.modelId) {
                                continue
                            }
                            did = did === "self" ? relation.applicaton.modelId : did;
                            $("#" + did).addClass("disDrag");
                            $("#" + did).attr("draggable", false)
                        }
                    }
                }

            }

            window.rightbar = new rml.RightModelList({
                "container": $(".model-rightbar"),
                "modelList": relation.modelList,
                "applicaton": relation.applicaton,
                "options": {
                    onRelationAdd: function (passiveId, number) {
                        mxGraph.updateNodeNumber(passiveId, number, "plus");
                        scanDisableModel();
                        var selectedNumber=mxGraph.getRelationNumber();
                        _index.relationNumber.update(selectedNumber)
                    },
                    onRelationDel: function (passiveId, number) {
                        mxGraph.updateNodeNumber(passiveId, number, "minus");
                        scanDisableModel();
                        var selectedNumber=mxGraph.getRelationNumber();
                        _index.relationNumber.update(selectedNumber-number);
                    },
                    mxGraph_updateNodeNumber: function (passiveId, number,widgetInfos) {
                        mxGraph.updateNodeNumber(passiveId, number, null);
                        scanDisableModel();
                        var selectedNumber=0;
                        var fillSelectedNumber=0;
                        var hasFill = false;
                        var hasRelation = false;
                        if(widgetInfos){
                            for (var key in widgetInfos) {
                                var info = widgetInfos[key];
                                if(info.type == "2") {
                                    fillSelectedNumber += parseInt(info.count);
                                    hasFill = true;
                                }else{
                                    selectedNumber += parseInt(info.count);
                                    hasRelation = true;
                                }
                            }
                        }
                        if(hasFill){
                            _index.relationNumber.updateFill(fillSelectedNumber,widgetInfos)
                        }
                        if(hasRelation){
                            _index.relationNumber.update(selectedNumber);
                        }
                    },
                    onFillingDel: function (passiveId, number,widgetId) {
                        mxGraph.updateNodeNumber(passiveId, number, "minus");
                        scanDisableModel();
                        var selectedNumber=mxGraph.getAllFillingNumber(widgetId);
                        var widgetInfo = {};
                        if(widgetId){
                            widgetInfo[widgetId].count = selectedNumber - number;
                            widgetInfo[widgetId].type = "2";
                        }
                        _index.relationNumber.updateFill(selectedNumber - number,widgetInfo)
                    },
                    getFillEnableCount:function (){
                        return _index.relationNumber.getFillEnableCount();
                    },
                    getEnableCount:function (){
                        return _index.relationNumber.getEnableCount();
                    }
                }
            });
            rightbar.startup();
            $(".model-body").on("click", function () {
                rightbar.rebuild();
            });
            //绘图
            mxGraph = new MxGraph(
                document.getElementById('graphContainer'), {
                    nodes: _index.cfg.nodes,
                    edges: _index.cfg.edges
                }, {
                    onClickNode: function (node, noRebuild) {
                        $('.model-rightbar').addClass('active');
                        $('.model-body').addClass('shrink');
                        $('.model-rightbar-btn-tips').text('${lfn:message('sys-modeling-base:modelingApplication.putAway')}');
                        var sub = node.sub;
                        if (sub === "NORMAL_SUB" || sub === "SELF_SUB") {
                            //非主表单的删除
                            var id = node.id;
                            id = id.replace("_sub", "");
                            mxGraph.clearNodes(id);
                        } else {
                            //其他的点击,重绘右侧
                            if (!noRebuild) {
                                rightbar.rebuild(node.relation);
                            }

                        }

                    },
                    onClickEdge: function (edge) {
                        // console.log("onClickEdge", edge);

                    },
                    onClearNodes: function (id) {
                        //删除节点-在节点上操作
                        if (id) {
                            dialog.confirm('${lfn:message('sys-modeling-base:relation.delete.current.relationship')}', function (value) {
                                var pid = id === "self" ? _index.cfg.applicaton.modelId : id;
                                if (value == true) {
                                    $.ajax({
                                        url: "${LUI_ContextPath}/sys/modeling/base/sysModelingRelation.do?method=deleteRelation&fdPassiveId=" + pid + "&fdModelId=" + _index.cfg.applicaton.modelId,
                                        method: 'GET',
                                        async: false
                                    }).success(function (resultStr) {
                                        var result = JSON.parse(resultStr);
                                        if (result.success) {
                                            dialog.success('${lfn:message('sys-modeling-base:modeling.baseinfo.DeleteSuccess')}')
                                            mxGraph.delNode(id);
                                            var selectedNumber=mxGraph.getRelationNumber();
                                            _index.relationNumber.update(selectedNumber);
                                            var fillSelectedNumber=mxGraph.getAllFillingNumber();
                                            _index.relationNumber.updateFill(fillSelectedNumber)
                                            scanDisableModel()
                                        } else {
                                            if(result.status == false){
                                                dialog.failure(result.msg);
                                                //弹出框修改
                                                var url='/sys/modeling/base/listview/config/dialog_relation.jsp';
                                                dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.form.DeleteAssociatedModule')}", function(data){
                                                },{
                                                    width : 600,
                                                    height : 400,
                                                    params : { datas : result.datas }
                                                });
                                            }else{
                                                dialog.failure(result.msg);
                                            }
                                        }
                                    });
                                }
                            });

                        }
                        return false;
                    }
                }
            );
            scanDisableModel()
            //拖拽-新增
            window.allowDrop = function (event) {
                event.preventDefault();
            };
            window.drop = function (event) {
                event.preventDefault();
                var text = event.dataTransfer.getData("Text");
                if (_index.relationNumber.enable > 0 || _index.relationNumber.fillEnable > 0) {
                    addRelation(text);
                } else {
                    dialog.alert("${lfn:message('sys-modeling-base:relation.available.relational.0')}")
                }

            };
            //新增model
            window.addRelation = function (text) {
                var model = JSON.parse(text);
                var newNode = {
                    "id": model.id,
                    "label": model.name,
                    "title": model.title,
                    "number": 0,
                    "level": 1
                };
                mxGraph.appendNode(newNode);
                scanDisableModel()
                // _index.relationNumber.plus(1);
            };
            window.createRelation = function (type) {
                rightbar.createRelation(type)
            };
            window.rebuild = function (id) {
                rightbar.rebuild(id)
            }
            //搜索
            window.searchModel = function (event, dom) {
                var val = encodeURIComponent($(dom).val());
                if (val === "") {
                    rightbar.rebuildModel(val)
                } else if (event && event.keyCode == '13') {
                    rightbar.rebuildModel(val)
                }

            }
            //普通事件-------------------------
            //搜索
            window.clickSearchModel = function (id) {
                var val = encodeURIComponent($("#" + id).val());
                rightbar.rebuildModel(val)
            };
            //左侧
            window.onload = function () {   //加载页面的时候就执行
                $(".fullscreen").height($(".model-rightbar").height() + 50);
                $(".associated_quickly_intro .associated_quickly_intro_slide").on("click",function(e){
                    e.stopPropagation();
                    $(".associated_quickly_intro").toggleClass("spread");
                    if($(".associated_quickly_intro").hasClass("spread")){
                        $(".associated_quickly_intro_slide span").text("${lfn:message('sys-modeling-base:modelingApplication.unfold')}");
                    } else{
                        $(".associated_quickly_intro_slide span").text("${lfn:message('sys-modeling-base:modelingApplication.putAway')}");
                    }
                })
                $("#fillingEmptyNumber").on("mouseover",function(e){
                    e.stopPropagation();
                    _index.relationNumber.showTip();
                }).on("mouseout",function(e){
                    e.stopPropagation();
                    _index.relationNumber.hideTip();
                })
            };
            //当文档窗口发生改变时 触发
            $(window).resize(function () {
                $(".fullscreen").height($(".model-rightbar").height() + 50);
            });
            window.openVideo = function () {
                dialog.iframe("/sys/modeling/base/resources/relationVideoPlay.jsp", "${lfn:message('sys-modeling-base:relation.3.minute.know')}",
                    function (value) {
                    }, {width: 814, height: 430});
            };
        })

    ;
</script>
<!-- 右边栏 ends -->