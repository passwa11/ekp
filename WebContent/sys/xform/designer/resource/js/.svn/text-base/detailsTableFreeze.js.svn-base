//当前冻结的最后一列标记
var tableFreezeArray = {};
var tableDivInfoArray = {};
var isDebug = false;

// 附件上传成功后回调
var onUploadAfterSuccessEventTableId="";
function onUploadAfterSuccessEvent() {
    if (onUploadAfterSuccessEventTableId){
        reSetTrLeft(onUploadAfterSuccessEventTableId);
    }
}

// 附件删除成功后回调
var onUploadFileDeleteEventTableId="";
function onUploadFileDeleteEvent() {
    if (onUploadFileDeleteEventTableId){
        reSetTrLeft(onUploadFileDeleteEventTableId);
    }
}

/**
 * 入口
 * @param tableId
 * @param defaultFreezeTitle 默认是否冻结标题
 * @param defaultFreezeCol 默认冻结前几行
 * @param multihead 多表头
 * @param showIndex 显示序号
 * @param method 查看页面view/编辑页面edit/新建页面add
 */
function tableFreezeStarter(tableId, defaultFreezeTitle, defaultFreezeCol, multihead, showIndex, method) {
    method = Com_GetUrlParameter(location.href,"method")
    // 打印页面不执行
    if (method=="print"){
        return;
    }
	//IE不支持冻结，屏蔽按钮及样式
    var userAgent = navigator.userAgent;
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1;
	var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
	if(isIE || isIE11) {
		return;
	}
	
    let table = $('table[id="' + tableId + '"]');
    //不在div容器中
    let div = table.parent('div');
    if(div.length === 0 && table.parent().attr("flagtype")== "xform_right"){
	   div = table.parent().parent('div');
    }
    if (div.length === 0)
        return;
    //高级明细表再向上找div,剔除自己本身的div
    if (XForm_IsAdvancedDetailsTable(table)) {
        div = div.parent("div");
    }
    if (div.length === 0)
        return;
    let divWidth = div[0].style.width;
    let divHeight = div[0].style.height;
    let isFreezeCol = divWidth && divWidth.indexOf("px") > 0; // 外层div没有固定宽度时，无需冻结列
    let isFreezeRow = divHeight && divHeight.indexOf("px") > 0; // 外层div没有固定高度时，无需冻结行
    // 无需任何冻结
    if (multihead) {
    	return;
    } else {
    	if (table.find("tr[type='titleRow']").length > 1){
    		return;
    	}
    }
    if (!isFreezeRow && !isFreezeCol)
        return;
    if(isDebug)
        console.log("isFreezeRow=" + isFreezeRow + " isFreezeCol=" + isFreezeCol);
    tableDivInfoArray[tableId] = {'isFreezeRow': isFreezeRow, 'isFreezeCol': isFreezeCol, 'multihead': multihead};
    tableFreezeArray[tableId] = -1;
    //判断冻结前明细表是否可见, 若不可见设置为可见
    var isVisible = $(div).is(":visible") && $(div).closest("tr").is(":visible");
    var divDis = $(div).css("display");
    var closestTr = $(div).closest("tr");
    var trDis = closestTr.css("display");
    var isResetDiv = false;
    var isResetTr = false;
    if (!isVisible) {
        if (divDis == "none") {
            $(div).attr("_display", divDis);
            $(div).css("display", "block");
            isResetDiv = true;
        }
        if (trDis == "none") {
            $(closestTr).attr("_display", trDis);
            $(closestTr).css("display", "block");
            isResetTr = true;
        }
    }
    //创建冻结开关
    createFreezeSwitch(table, tableId, showIndex, method);
    //初始化冻结行、列
    InitFreezeColRow(table, tableId, defaultFreezeTitle, defaultFreezeCol, showIndex, method);
    //监听附件点击事件
    fileUpClick();

    //若上面设置不可见设置为可见, 恢复默认状态
    if (!isVisible) {
        if (isResetDiv) {
            $(div).css("display", divDis);
        }
        if (isResetTr) {
            $(closestTr).css("display", trDis);
        }
    }
    //冻结新增TD 监听添加、复制行事件
    table.on("table-add-new", function (event, source) {
        if (source && source.row) {
            setTimeout(function () {
                trFreeze(source.row, -1, tableFreezeArray[tableId], source.row.rowIndex, method);
                onclick_Freeze(source.row, -1, tableFreezeArray[tableId], source.row.rowIndex, method,source.table);
                fileUpClick();
            },200)
        }
    });
    // 当默认行数为0时，第一次添加行之后，由于td的宽度发生改变导致left:xxxPx 有误差，所以这里模仿一下点击冻结列方法（即针对目前行的前后列）
    function onclick_Freeze(tr, current, target, trIndex, method,table){
	    if(tr !=null && tr.rowIndex==1){
	         var titleElement =tr.previousElementSibling;
             if(titleElement!= null && titleElement.tagName == "TR"){
	            var prev_tr = tr.previousElementSibling;
                var next_tr = tr.nextElementSibling;
                trFreeze(prev_tr, current, target, trIndex, method);
                trFreeze(next_tr, current, target, trIndex, method);
             }
        }
    }

    /**
     * 初始化冻结行、列
     * @param table
     * @param tableId
     * @param defaultFreezeTitle
     * @param defaultFreezeColIndex
     * @param showIndex
     * @param method
     */
    function InitFreezeColRow(table, tableId, defaultFreezeTitle, defaultFreezeColIndex, showIndex, method) {
        if(tableDivInfoArray[tableId].multihead) {
            defaultFreezeTitle = false;
            defaultFreezeColIndex = -1;
        } else {
            //不显示序号
            if(showIndex===false)
                defaultFreezeColIndex--;
            //没有多选框
            if(method==="view")
                defaultFreezeColIndex--;
        }
        //冻结标题行
        freezeTitle(table, tableId, defaultFreezeTitle, method);
        //冻结默认列&&开关状态显示&&左上角悬浮
        tableColFreeze(table, tableId, defaultFreezeColIndex, method);
        //下方操作行
        freezeOperations(table, method);
    }

    /**
     * 当附件上传时触发该方法，重新计算冻结列td的left。
     */
    function fileUpClick(){
        $("xformflag[flagtype='xform_relation_attachment']").each(function (i,o) {
            //等待附件的dom结构加载完成
            setTimeout(function () {
                // 附件点击的事件，3种：1删除附件，2上传附件，3查看页面点击展开关闭
                if($(o).closest("table").attr("fd_type")=="detailsTable"){
                    $(o).bind("click",function () {
                        let id = $(o).closest("table").attr("id");
                        onUploadAfterSuccessEventTableId = id;
                        onUploadFileDeleteEventTableId = id;
                        if (id){
                            //查看页面点击展开
                            reSetTrLeft(id);
                        }
                    })
                    // 附件所在行删除事件，删除时会找不到table,传参获取id
                    let id = $(o).closest("table").attr("id");
                    $(o).closest("tr").find("td:last").bind("mousedown",{"id":id},function (param) {
                        onUploadAfterSuccessEventTableId = param.data.id;
                        onUploadFileDeleteEventTableId = param.data.id;
                        if (id){
                            //查看页面点击展开
                            reSetTrLeft(id);
                        }
                    })
                }
            },500)
        })
    }

    /**
     * 创建冻结开关
     * @param table
     * @param tableId
     * @param showIndex
     * @param method
     */
    function createFreezeSwitch(table, tableId, showIndex, method) {
        if(tableDivInfoArray[tableId].multihead)
            return;
        //冻结列开关
        if(tableDivInfoArray[tableId].isFreezeCol) {
            //选择框、序号列与操作列不添加开关
            let notLast = ":not(:last)";
            let ltIndex = 2;
            if (showIndex === false) {
                ltIndex--;
            }
            if (method === "view") {
                ltIndex--;
                notLast = "";
            }
            let trSelector = 'tr:first td:not(:lt(' + ltIndex + '))' + notLast;
            table.find(trSelector).each(function (tdIndex, tdObj) {
                let freezeLastTdIndex = tdIndex + ltIndex;
                //定义冻结开关
                let freeze = $("<div class='freeze_switch_div' style='display:none;' title='冻结列'><span class='freeze_switch_span freeze_switch_position4col'></span></div>");
                freeze.click(function () {
                    tableColFreeze(table, tableId, freezeLastTdIndex, method);
                });
                $(tdObj).append(freeze);
                //按钮显示及预览冻结表格底色
                $(tdObj).hover(function () {
                    freeze.show();
                    // 添加>排除附件td
                    //table.children("tbody").children("tr:not(:last)").find("td:lt(" + (freezeLastTdIndex + 1) + ")").addClass("freeze_preview");
                    table.children("tbody").children("tr:not(:last)").find(">td:lt(" + (freezeLastTdIndex + 1) + ")").addClass("freeze_preview");
                }, function () {
                    freeze.hide();
                    table.children("tbody").children("tr:not(:last)").find(">td:lt(" + (freezeLastTdIndex + 1) + ")").removeClass("freeze_preview");
                });
            });
        }

        //冻结行开关
        if(tableDivInfoArray[tableId].isFreezeRow && method !== "view") {
            table.find('tr:first td:first').each(function (tdIndex, tdObj) {
                //定义冻结开关
                let freeze = $("<div class='freeze_switch_div' style='display:none;' title='冻结行'><span class='freeze_switch_span freeze_switch_position4row'></span></div>");
                freeze.click(function () {
                    freezeTitle(table, tableId, true, method);
                });
                $(tdObj).append(freeze);
                //按钮显示及预览冻结表格底色
                $(tdObj).hover(function () {
                    freeze.show();
                    freeze.closest('tr').find('>td').addClass("freeze_preview");
                }, function () {
                    freeze.hide();
                    freeze.closest('tr').find('>td').removeClass("freeze_preview");
                });
            });
        }
    }

    /**
     * 所有冻结tr重新设置left
     *
     */
    window.reSetTrLeft = function reSetTrLeft(tableId){
        // 附件删除或者上传成功后执行，要给个等待时间，删除后的回调事件不会立刻改变td的宽度
        setTimeout(function () {
            $("#"+tableId).children("tbody").children("tr").each(function (i,o){
                let leftPx = 0;	//TD离左侧的距离
                $(o).find("td.freeze_left_col").each(function (l, p) {
                    // 附件上传后重新计算left
                    setLeftTdFreeze(p, leftPx);
                    leftPx += p.offsetWidth;
                });
            })
        },200)
    }

    /**
     * 冻结第一行
     * @param table
     * @param tableId
     * @param defaultFreezeTitle 传false用于进入页面时，只冻结操作列右上角的情况
     * @param method
     */
    function freezeTitle(table, tableId, defaultFreezeTitle, method) {
        if (!tableDivInfoArray[tableId].isFreezeRow)
            return;
        if (defaultFreezeTitle === undefined)
            defaultFreezeTitle = true;
        let tr = table.children("tbody").children("tr:first-child");
        //当前是否已冻结行
        let isFreezeRowNow = tr.find('td:first').find('span.freeze_switch_status_freeze').length > 0;
        if (isFreezeRowNow) {
            //解除冻结
            tr.find("td.freeze_first_row").each(function (i, o) {
                clearFirstTrFreeze(o);
            });
            tr.find('td:first').find('span.freeze_switch_span').removeClass("freeze_switch_status_freeze");
        } else {
            //冻结
            tr.children("td").each(function (i, o) {
                if (defaultFreezeTitle) {
                    setFirstTrFreeze(o);
                    setTitleBackground(o);
                }
            });
            //非查看页面时，操作列右上角需要悬浮在最顶层
            if(method !== "view") {
                tr.children("td:last").each(function (i, o) {
                    setOverlapFreeze(o);
                    $(o).css("right", 0);
                });
            }
            //冻结开关状态
            if (defaultFreezeTitle) {
                tr.find('td:first').find('span.freeze_switch_span').addClass("freeze_switch_status_freeze");
            }
        }
    }

    /**
     * 冻结最后一行
     * @param table
     */
    function freezeOperations(table, method) {
        if ((!tableDivInfoArray[tableId].isFreezeCol && !tableDivInfoArray[tableId].isFreezeRow) || method==='view')
            return;
        //冻结最后一行
        table.children("tbody").children("tr:last-child").children("td").each(function (i, o) {
            setLastTrFreeze(o, table.parent('div').width());
        });
    }

    function tableColFreeze(table, tableId, freezeLastTdIndex, method) {
        if (!tableDivInfoArray[tableId].isFreezeCol) {
            return;
        }
        let current = tableFreezeArray[tableId];
        let target = freezeLastTdIndex;
        if (isDebug) {
            console.log("current=" + current + " target=" + target);
        }
        table.children("tbody").children("tr:not(:last-child)").each(function (trIndex, tr) {
            //点击解冻or初始化时无需默认冻结列
            if (current === target && target !== -1) {
                //全部取消冻结
                tableColUnfreeze(table);
                return;
            }

            //#167574,处理该服务单时发现缺陷：有附件的列冻结，初始化时附件的宽度会变小td跟着变小，导致下一个td前空白
            setTimeout(function (){
                trFreeze(tr, current, target, trIndex, method);
            },500)

            //冻结开关按钮状态
            if (trIndex === 0) {
                $(tr).find('td:not(:first):not(:last)').find('span.freeze_switch_status_freeze').removeClass("freeze_switch_status_freeze");
                $(tr).find('td:eq(' + target + ')').find('span.freeze_switch_span').addClass("freeze_switch_status_freeze");
            }
        });
        if (current === target) {
            tableFreezeArray[tableId] = -1;
        } else {
            tableFreezeArray[tableId] = target;
        }
    }

    /**
     * 全部取消冻结
     * @param table
     */
    function tableColUnfreeze(table) {
        //取消原冻结列
        table.find(".freeze_left_col").each(function (i, td) {
            clearTdFreeze(td);
        });
        //冻结开关按钮状态
        $(table).children("tbody").children("tr:first").children("td:not(:first):not(:last)").find('span.freeze_switch_status_freeze').removeClass("freeze_switch_status_freeze");
    }

    /**
     * 以某一列为边界进行tr的冻结
     */
    function trFreeze(tr, current, target, trIndex, method) {
	    if(tr == null)return;
        // 右侧操作始终冻结
        // 非查看页面不冻结操作列标题
        let multihead = tableDivInfoArray[tableId].multihead;
        if(method!=="view") {
            //多表头时第二行tr标题的最后一列不是操作列的标题，需要跳过
            if((multihead && trIndex !== 1) || !multihead) {
                setRightTdFreeze($(tr).find('td:last'));
            }
        }
        // 未冻结列时跳过后续逻辑
        if (target === undefined || target === -1) {
            return;
        }
        // 解冻操作or初始化时无需默认冻结列
        if (current === target)
            return;
        if (current < target) {
            //将要冻结的列在已冻结列的右侧
            let leftPx = 0;	//TD离左侧的距离
            $(tr).find(">td:lt(" + (target + 1) + ")").each(function (i, o) {
                if (i >= current) {
                    setLeftTdFreeze(o, leftPx);
                    if (trIndex === 0) {
                        setOverlapFreeze(o);
                        setTitleBackground(o);
                    }
                }
                leftPx += o.offsetWidth;
                //#171054当连续两次left为0时，页面点击后重新赋值。
                if (i>0 && leftPx == 0){
                    $(tr).closest("table[fd_type='mutiTab']").one('click',function () {
                        trFreeze(tr, current, target, trIndex, method);
                    })
                }
            });
        } else {
            //将要冻结的列在已冻结列的左侧
            $(tr).find(">td:gt(" + target + "):lt(" + (current + 1) + ")").each(function (i, o) {
                if (trIndex === 0) {
                    //取消左上角重叠列
                    clearOverlapFreeze(o);
                }
                clearTdFreeze(o);
            });
        }
    }

    function setReZIndex(o,leftPx){
        //#171340
        if ($(o).attr("style") && $(o).attr("style").indexOf("z-index")<0){
            let parent = $(o).closest("table").parent();
            for (let j = 0; j < $(o).closest("table").parents().length; j++) {
                if (parent[0].clientWidth){
                    break;
                }
                parent = parent.parent();
            }
            if (leftPx+$(o).width() > parent[0].clientWidth) {
                if ($(o).closest("tr").index() > 0) {
                    $(o).css("z-index", 1);
                } else {
                    $(o).closest("tr").children("td:last").css("z-index", 4);
                    $(o).css("z-index", 2);
                }
            }
        }
    }

    function setLeftTdFreeze(o, leftPx) {
        $(o).addClass("freeze_left_col");
        $(o).css("left", leftPx);
        setReZIndex(o,leftPx);
    }

    function setRightTdFreeze(o) {
        $(o).addClass("freeze_right_col");
        $(o).css("right", 0);
    }

    function clearTdFreeze(o) {
        $(o).removeClass("freeze_left_col");
        $(o).css("left", "");
    }

    function setFirstTrFreeze(o) {
        $(o).addClass("freeze_first_row");
    }

    function clearFirstTrFreeze(o) {
        $(o).removeClass("freeze_first_row");
    }

    function setLastTrFreeze(o, divWidth) {
        $(o).addClass("freeze_last_row");
        $(o).addClass("tr_normal_opt");
        //使里面的div悬浮
        $(o).find('div:first').addClass("freeze_div");
        $(o).find('div:first').width(divWidth - 20);
    }

    /**
     * 表头背景色
     */
    function setTitleBackground(o) {
        $(o).addClass("td_normal_title");
    }

    /**
     * 左上角/右上角 重叠列需要悬浮在最顶层
     */
    function setOverlapFreeze(o) {
        $(o).addClass("freeze_overlap");
    }

    function clearOverlapFreeze(o) {
        $(o).removeClass("freeze_overlap");
        $(o).css("left", "");
    }
}