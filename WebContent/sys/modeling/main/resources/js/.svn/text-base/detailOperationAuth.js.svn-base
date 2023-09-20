/**
 * 明细表操作权限控制，
 * 新建与删除可分别控制，通过修改xform:editShow并不能实现该功能，所以使用该js进行页面上的后置处理
 *
 * [新建、编辑、起草节点]
 *      配置                      操作列                                操作行
 *      允许添加、不允许删除        隐藏删除按钮，添加或复制的行有删除按钮    隐藏删除按钮
 *      不允许添加、允许删除        隐藏复制按钮                           隐藏添加按钮
 *      不允许添加、不允许删除      隐藏列                                 隐藏删除、添加按钮
 *      允许添加、允许删除(默认)：不隐藏按钮
 *
 * [审批节点（无编辑权限）]
 *      配置                    选择框列        操作列                                         操作行
 *      允许添加、允许删除        显示该列        显示该列。显示删除、复制按钮                     显示该列。显示删除、添加按钮
 *      允许添加、不允许删除      显示该列        显示该列。显示复制按钮，添加或复制的行有删除按钮    显示该列。显示添加按钮
 *      不允许添加、允许删除      显示该列        显示该列。显示删除按钮                           显示该列。显示删除按钮
 *      不允许添加、不允许删除(默认)：选择框、操作列，操作行不显示
 *
 * [审批节点（有编辑权限）]
 *      无视配置，允许添加、允许删除
 *
 * ps:当有添加权限但没有删除权限时，依然可以删除自己增加的行
 */
const mode_dg = "mode_dg";
const mode_n = "mode_n";
const delKey = "operation_auth_delDetail";
const addKey = "operation_auth_addDetail";
const IS_DEBUG = false;

var detailOperationConfig = [];

/**
 * 明细表操作权限控制入口
 *
 * @param configStr     表单权限配置
 * @param isFlowEnable  是否有流程
 * @param method        请求方法(add/edit/view)
 * @param nodeId        若有流程，当前处理人对应的节点ID
 * @param isMobile      是否为移动端
 */
function initDetailOperationAuth(configStr, isFlowEnable, method, nodeId,isMobile) {
    if (isFlowEnable) {
        if (!nodeId && method === "add") {
            //起草节点设置默认nodeId
            nodeId = "N2";
        } else if (nodeId && nodeId !== "N2" && method === "edit") {
            //审批节点（有编辑权限直接跳过）
            return;
        }
    }
    if (IS_DEBUG) {
        console.log("--------------------");
        console.log(method);
        console.log(nodeId);
        console.log(isFlowEnable);
        console.log(configStr);
    }

    //读取明细表配置
    if(!configStr || configStr === "") {
        return;
    }
    loadDetailOperationConfig(configStr, isFlowEnable, method, nodeId);
    //处理明细表
    for (let i in detailOperationConfig) {
        if (detailOperationConfig.hasOwnProperty(i)) {
            let config = detailOperationConfig[i];
            handleDetailOperationAuth($(config.dom), config.isAdd, config.isDel, method,isMobile);
        }
    }

    //明细表添加行事件
    $(document).on("table-add-new", function (event, source) {
        if (source && source.table) {
            //当有添加权限但没有删除权限时，依然可以删除自己增加的行
            for (let i in detailOperationConfig) {
                if (detailOperationConfig.hasOwnProperty(i)) {
                    let config = detailOperationConfig[i];
                    if (config.dom === source.table) {
                        if (config.isAdd && !config.isDel) {
                            $(source.row).find('.opt_del_style').show();
                        }else if(!config.isDel){
                            //#161226 由于表单的新需求对移动端明细表性能优化 改变了加载顺序，
                            // 前面在handleDefaultExistOper方法中对操作按钮的隐藏时还没获取到对应的删除结构，隐藏失败
                            //这里重新隐藏一下
                            $(source.row).find(".muiDetailTableDel").hide();
                            $(source.row).find(".opt_del_style").hide();
                            $(source.row).find(".opt_batchDel_style").parent("span").hide();
                        }
                    }
                }
            }
        }
    });
}

/**
 * 读取明细表配置
 */
function loadDetailOperationConfig(configStr, isFlowEnable, method, nodeId) {
    let config = JSON.parse(configStr);
    let mode = isFlowEnable ? mode_n : mode_dg;
    let node = isFlowEnable ? nodeId : method;
    if (!config[mode] || !config[mode][node]) {
        return;
    }
    let methodOrNodeConfig = config[mode][node];

    $("table[id^='TABLE_DL_']").each(function (i, o) {
        let tablename = $(o).attr("id").replace("TABLE_DL_", "");
        //读取明细表配置
        let isAdd;  //是否允许新增
        let addDetailConfig = methodOrNodeConfig[tablename + "." + addKey];
        if (!addDetailConfig) {
            //无配置时的默认值
            isAdd = method === "add" || method === "edit";
        } else {
            isAdd = addDetailConfig["value"] === "addDetail";
        }
        let isDel;  //是否允许删除
        let delDetailConfig = methodOrNodeConfig[tablename + "." + delKey];
        if (!delDetailConfig) {
            //无配置时的默认值
            isDel = method === "add" || method === "edit";
        } else {
            isDel = delDetailConfig["value"] === "delDetail";
        }
        detailOperationConfig.push({"id": tablename, "isAdd": isAdd, "isDel": isDel, "dom": o});
    });
}

/**
 * 处理明细表 入口
 */
function handleDetailOperationAuth(table, isAdd, isDel, method,isMobile) {
    if (IS_DEBUG) {
        console.log("isAdd=" + isAdd + " isDel=" + isDel + " method=" + method);
    }
    if (method === "add" || method === "edit") {
        //处理新增、编辑页面默认存在添加删除按钮的情况
        handleDefaultExistOper(table, isAdd, isDel,isMobile);

    } else if (method === "view") {
        //处理审批页面默认没有操作行、操作列、选择框的情况
        handleDefaultNotExistOper(table, isAdd, isDel,isMobile);
    }
}

/**
 * 处理新增、编辑页面默认存在添加删除按钮的情况
 */
function handleDefaultExistOper(table, isAdd, isDel,isMobile) {
    if (!isAdd && !isDel) {
        //添加及删除都隐藏时，右侧操作列无需悬浮
        table.children("tbody").children("tr:not(last-child)").children("td:last-child").removeClass("freeze_right_col").css("right", "");
    }
    if (!isAdd) {
        if (isMobile){
            //隐藏新增按钮
            var $detailTableContent =  table.parent(".detailTableContent");
            if($detailTableContent.length > 0){
                //明细表移动展端，移动端
                $($detailTableContent[0]).next(".muiDetailTableAdd").hide()
            }else{
                //明细表移动展端，桌面端
                var $detailTableContents =  table.parents(".detailTableContent")
                if($detailTableContents.length > 0){
                    $detailTableContents.siblings(".muiDetailTableAdd").each(function(i) {
                        if(i==0){
                            //获取到的第一个为当前明细表的添加按钮
                            $(this).hide();
                        }
                    });
                }
            }
        } else {
            table.find(".opt_add_style").parent("span").hide();
            table.find(".opt_copy_style").hide();
        }

    }
    if (!isDel) {
        if (isMobile){
            table.find(".muiDetailTableDel").hide();
        } else {
            table.find(".opt_del_style").hide();
            table.find(".opt_batchDel_style").parent("span").hide();
        }
    }
}

/**
 * 处理审批页面默认没有操作行、操作列、选择框的情况
 */
function handleDefaultNotExistOper(table, isAdd, isDel,isMobile) {
    if (isAdd || isDel) {
        //选择框
        let checkBoxTitle = $('<td coltype="selectCol" style="width: 15px;" class="td_normal_title"></td>');
        checkBoxTitle.insertBefore(table.find("tr:first td:first"));

        let checkBox = $('<td valign="" align="center"><input type="checkbox" name="DocList_Selected" onclick="DocList_SelectRow(this);"></td>');
        checkBox.insertBefore(table.find("tr:not(:first-child):not([type=statisticRow]):not([type=optRow])").find("td:first"));

        let checkBoxBottom = $('<td coltype="noFoot" style="width: 25px;">&nbsp;</td>');
        checkBoxBottom.insertBefore(table.find("tr[type=statisticRow] td:first"));

        //右侧操作
        let rightOperTitle = $('<td class="td_normal_title" coltype="blankTitleCol" style="width: 48px; right: 0px;"></td>');
        rightOperTitle.appendTo(table.find("tr:first"));

        let rightOper = $('<td coltype="copyCol" align="center" style="width: 48px; right: 0px;"><nobr>' +
            '<span style="cursor:pointer;' + (!isAdd ? 'display:none;' : '') + '" class="optStyle opt_copy_style" title="复制行" onmousedown="DocList_CopyRow();"></span>&nbsp;&nbsp;'+
            '<span style="cursor:pointer;' + (!isDel ? 'display:none;' : '') + '" class="optStyle opt_del_style" title="删除行" onmousedown="DocList_DeleteRow_ClearLast();XFom_RestValidationElements();"></span>&nbsp;&nbsp;'+
            '</nobr></td>');
        rightOper.appendTo(table.find("tr:not(:first-child):not([type=statisticRow]):not([type=optRow])"));

        let bottomStaticRow = $('<td coltype="emptyCell" style="width: 48px; right: 0px;">&nbsp;</td>');
        bottomStaticRow.appendTo(table.find("tr[type=statisticRow]"));

        // 下方操作
        var oldTdLength = table.find("tr[type=optRow]").find("td").length;
        if(oldTdLength != 0){
            //#137469 流程表单设计带有明细表，设置操作权限允许添加和删除，审批节点出现明细表排版乱
            let bottomOperBar = $('<div class="tr_normal_opt_l"><label class="opt_ck_style" style="position:relative;top:3px;"><input type="checkbox" name="DocList_SelectAll" onclick="DocList_SelectAllRow(this);"><span style="margin-left: 6px;">全选<span></span></span></label>' +
                (isDel ? '<span style="margin-left:15px;" onclick="DocList_BatchDeleteRow();XFom_RestValidationElements();"><span class="optStyle opt_batchDel_style" style="margin-left:0px; " title="删除行"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">删除行</span></span>' : '')+
                '</div><div class="tr_normal_opt_c">' +
                (isAdd ? '<span onclick="DocList_AddRow();XFom_RestValidationElements();"><span class="optStyle opt_add_style" title="添加行"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">添加行</span></span>' : '')+
                '<span style="margin-left:15px;" onclick="DocList_MoveRowBySelect(-1);"><span class="optStyle opt_up_style" title="上移"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">上移</span></span><span style="margin-left:15px;" onclick="DocList_MoveRowBySelect(1);"><span class="optStyle opt_down_style" title="下移"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">下移</span></span></div>');
            bottomOperBar.appendTo(table.find("tr[type=optRow]").find("td").find(".tr_normal_opt_content"));
        }else {
            let bottomOperBar = $('<td align="center" coltype="optCol" colspan="7" style="" class="tr_normal_opt"><div class="tr_normal_opt_content" style="min-width: 580px; width: 780px;">' +
                '<div class="tr_normal_opt_l"><label class="opt_ck_style" style="position:relative;top:3px;"><input type="checkbox" name="DocList_SelectAll" onclick="DocList_SelectAllRow(this);"><span style="margin-left: 6px;">全选<span></span></span></label>' +
                (isDel ? '<span style="margin-left:15px;" onclick="DocList_BatchDeleteRow();XFom_RestValidationElements();"><span class="optStyle opt_batchDel_style" style="margin-left:0px; " title="删除行"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">删除行</span></span>' : '') +
                '</div><div class="tr_normal_opt_c">' +
                (isAdd ? '<span onclick="DocList_AddRow();XFom_RestValidationElements();"><span class="optStyle opt_add_style" title="添加行"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">添加行</span></span>' : '') +
                '<span style="margin-left:15px;" onclick="DocList_MoveRowBySelect(-1);"><span class="optStyle opt_up_style" title="上移"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">上移</span></span><span style="margin-left:15px;" onclick="DocList_MoveRowBySelect(1);"><span class="optStyle opt_down_style" title="下移"></span><span style="position:relative;top:3px;cursor: pointer;margin-left: 6px;">下移</span></span></div>' +
                '<div class="tr_normal_opt_r">' +
                '</div></div></td>');
            bottomOperBar.appendTo(table.find("tr[type=optRow]"));
        }

        // 拖拽能力
        table.attr('tbdraggable', 'true');
    }
}
