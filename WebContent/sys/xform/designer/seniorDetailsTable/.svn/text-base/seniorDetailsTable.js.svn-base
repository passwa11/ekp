/**********************************************************
 功能：高级明细表
 使用：

 作者：lwc
 创建时间：2021-06-15
 **********************************************************/
Designer_Config.controls.seniorDetailsTable = {
    type : "seniorDetailsTable",
    inherit    : 'detailsTable',
    relatedWay : 'up',
    isAdvancedDetailsTable : true,
    drawXML : _Designer_Control_DetailsTable_DrawXML,
    onDrawEnd : _Designer_Control_DetailsTable_OnDrawEnd,
    onAttrLoad : _Designer_Control_Senior_OnAttrLoad,
    info : {
        name: Designer_Lang.controlSeniorDetailsTable_info_name,
        td: Designer_Lang.controlSeniorDetailsTable_info_td
    }
};

function _Designer_Control_Senior_OnAttrLoad() {
    var $nameObj = $("[name='importType']");
    if($nameObj.length>0){
        var tr = $nameObj.closest("tr");
        var tipContent = Designer_Lang.seniorImportTypeTip;
        // 绘制导入模式的提示<bean:message bundle="sys-xform" key="sysFormTemplate.seniorImportTypeTip"/>
        var importTypeTip = '<div class="seniorTipArea">'+tipContent+'</div> <img class="seniorTip" src="'+Com_Parameter.ContextPath+'sys/xform/designer/style/img/promptControl.png"></img>';
        tr.find('td:first').append(importTypeTip);
        $(".seniorTip").mouseover(function(){
            $(".seniorTipArea").show();
            var left = $(this).offset().left-10;
            var top = $(this).offset().top+23;
            var h = document.documentElement.scrollTop || document.body.scrollTop;
            $(".seniorTipArea").css({
                "left" : left,
                "top" : top-h
            });
        });
        $(".seniorTip").mouseout(function(){
            $(".seniorTipArea").hide();
        });
        if($("input[name='excelImport']")[0].value === 'true'){
            tr.show();
        }else{
            tr.hide();
        }
    }
}

// 绘制结束调用方法
function _Designer_Control_DetailsTable_OnDrawEnd() {
    var values = this.options.values;
    var domElement = this.options.domElement;
    domElement.label = values.label;
    domElement.setAttribute('id', values.id);
    // 处理多表头
    this.initMultiHead(domElement,values);

    //序号强制不换行
    $(domElement).find("td[coltype='noTitle']").css("white-space","nowrap");

    this.options.values.width=this.options.values.width?this.options.values.width:'100%';

    domElement.width=this.options.values.width;
    domElement.style.width=this.options.values.width;
    $(domElement).attr("width",this.options.values.width);
    $(domElement).css("width",this.options.values.width);

    if (this.parent)
        domElement.setAttribute('tableName', _Get_Designer_Control_TableName(this.parent));

    if (values) {
        var rows = domElement.rows;
        if (values.showIndex == 'false') {
            domElement.setAttribute('showIndex', 'false');
            for (var i = 0; i < rows.length; i ++) {
                if(rows[i].getAttribute('type') == 'optRow')
                    continue;
                //隐藏序号列
                $(rows[i]).find('td[colType="noTitle"],td[colType="noTemplate"],td[colType="noFoot"]').hide();
            }
        } else {
            domElement.setAttribute('showIndex', 'true');
            for (var i = 0; i < rows.length; i ++) {
                if(rows[i].getAttribute('type') == 'optRow')
                    continue;
                //显示序号列
                if (rows[i].cells[1]) {
                    rows[i].cells[1].style.display = '';//旧模板在新需求下（增加复选框列），导致序列号后面那列不小心被隐藏了，这里增加兼容代码把不小心被隐藏的列显示回出来
                }
                $(rows[i]).find('td[colType="noTitle"],td[colType="noTemplate"],td[colType="noFoot"]').show();
            }
        }
        if (values.showRow == null) {
            values.showRow = 1;
        }


        var alignment = Designer_Config.controls.detailsTable.attrs.alignment.value;
        //#55955 明细表出滚动条的情况下，建议底部操作布局优化
        if (values.alignment){
            alignment = values.alignment;
            domElement.setAttribute("aligment",alignment);
            var row = "";
            for (var i = 0; i < rows.length; i ++) {
                if(rows[i].getAttribute('type') == 'optRow'){
                    row = rows[i];
                }
            }
            if(alignment === "right"){
                $(row).find("div[name='tr_normal_opt_content']").css("text-align","right");
            }
            if (alignment === "left"){
                $(row).find("div[name='tr_normal_opt_content']").css("text-align","left");
            }
            if (alignment === "left" || alignment === "right"){
                $(row).find("div[name='tr_normal_opt_l']").css("display","inline-block");
                $(row).find("div[name='tr_normal_opt_c']").css("display","inline-block");
                $(row).find("div[name='tr_normal_opt_r']").css("display","inline-block");
                $(row).find("div[name='tr_normal_opt_l']").css("position","");
                $(row).find("div[name='tr_normal_opt_c']").css("position","");
                $(row).find("div[name='tr_normal_opt_r']").css("position","");
                $(row).find("div[name='tr_normal_opt_l']").css("margin-right","30px");
                $(row).find("div[name='tr_normal_opt_c']").css("margin-right","30px");
            }
            if(alignment === "default"){//还原默认样式
                $(row).find("div[name='tr_normal_opt_content']").css("text-align","");
                $(row).find("div[name='tr_normal_opt_l']").css("position","absolute");
                $(row).find("div[name='tr_normal_opt_r']").css("position","absolute");
                $(row).find("div[name='tr_normal_opt_l']").css("margin-right","0");
                $(row).find("div[name='tr_normal_opt_c']").css("margin-right","0");
            }
        }


        if (values.showRow) {
            // 初始行数
            var rowSize = parseInt(values.showRow);
            if (isNaN(rowSize) || rowSize < 0) {
                rowSize = 0;
            }
            domElement.setAttribute('showRow', '' + rowSize);
            // 是否显示统计行
            domElement.setAttribute('showStatisticRow', values.showStatisticRow);
            if(values.showStatisticRow === 'false'){
                $('tr[type="statisticRow"]',$(domElement)).hide();
            }else{
                $('tr[type="statisticRow"]',$(domElement)).show();
            }
            // 是否显示复制行功能
            domElement.setAttribute('showCopyOpt', values.showCopyOpt);
            if(values.showCopyOpt === 'false'){
                $('img.copyIcon',$(domElement)).hide();
            }else{
                $('img.copyIcon',$(domElement)).show();
            }
        }
        domElement.setAttribute("dataEntryMode",values.dataEntryMode || "multipleRow");
        domElement.setAttribute('required', values.required);
        domElement.setAttribute('excelExport', values.excelExport);
        domElement.setAttribute('excelImport', values.excelImport);
        domElement.setAttribute('importType', values.importType);
        if(values.excelExport && values.excelExport == 'true'){
            $('span[name="excelExport"]',$(domElement)).show();
        }else{
            $('span[name="excelExport"]',$(domElement)).hide();
        }
        if(values.excelImport && values.excelImport == 'true'){
            $('span[name="excelImport"]',$(domElement)).show();
        }else{
            $('span[name="excelImport"]',$(domElement)).hide();
        }
        domElement.setAttribute('defaultFreezeTitle', values.defaultFreezeTitle);
        domElement.setAttribute('defaultFreezeCol', values.defaultFreezeCol);
        if (values) {
            domElement.setAttribute("layout2col", values['layout2col']);
        }
    }
}

/*Designer_Config.operations['seniorDetailsTable'] = {
    lab : "2",
    imgIndex : 15,
    title : Designer_Lang.controlSeniorDetailsTable_title,
    titleTip:Designer_Lang.controlSeniorDetailsTable_description,
    run : function (designer) {
        designer.toolBar.selectButton('seniorDetailsTable');
    },
    type : 'cmd',
    order: 10,
    line_splits_end:true,
    shortcut : 'R',
    select: true,
    cursorImg: 'style/cursor/detailsTable.cur',
    isAdvanced: false
};
Designer_Config.buttons.form.push("seniorDetailsTable");
Designer_Menus.form.menu['seniorDetailsTable'] = Designer_Config.operations['seniorDetailsTable'];*/