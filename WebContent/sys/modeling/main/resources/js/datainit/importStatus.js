Com_IncludeFile("data.js");
$(function () {
    checkImportStatus();
});

function buildRawImportBtn(divClass, text, dialogType, percent) {
    return '<div class="model-status ' + divClass + '" onclick="appImport(\'' + dialogType + '\');"><i></i><p>' + text + '</p></div>';
}

function checkImportStatus() {

    function loadImportStatus() {
        var data = new KMSSData();
        data.UseCache = false;
        data.AddBeanData("modelingDatainitXMLDataBean&method=simple");
        return data.GetHashMapArray()[0];
    }

    function setImportBtn(status, percent) {
        if (status === 0) {
            //初始导入状态
            setDefaultBtnHtml();
        } else if (status === -1) {
            //成功
            setBtnHtml(buildRawImportBtn('suc', '导入成功', 'status'));
        } else if (status === -2 || status === -3 || status === 3) {
            //失败、中止、中止中
            setBtnHtml(buildRawImportBtn('fail', '导入失败', 'status'));
        } else {
            //导入中
            setBtnHtml(buildRawImportBtn('ing', '正在导入', 'status'));
            setTimeout("checkImportStatus()", 2000);
        }
    }

    function setDefaultBtnHtml(){
        var $headImport = $('#head_import_btn');
        $('div:first', $headImport).show(); //显示默认按钮
        $('.model-mask-panel-sta', $headImport).hide(); //隐藏状态按钮
    }

    function setBtnHtml(btnHtml) {
        var $headImport = $('#head_import_btn');
        if (!$headImport)
            return;
        //隐藏默认按钮
        $('div:first', $headImport).hide();
        //状态按钮
        var staDiv = $('.model-mask-panel-sta', $headImport);
        staDiv[0].innerHTML = btnHtml;
        staDiv.show();
    }

    var rtnInfo = loadImportStatus();
    var status = parseInt(rtnInfo.status);
    var percent = rtnInfo.percent;
    setImportBtn(status, percent);
}