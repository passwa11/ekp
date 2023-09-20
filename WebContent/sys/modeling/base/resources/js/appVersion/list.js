
seajs.use(["lui/dialog", "lui/topic"], function(dialog, topic){
    function switchVersion(){
        var values = [];
        $("input[name='List_Selected']:checked").each(function() {
            values.push($(this).val());
        });
        if(values.length==0){
            dialog.alert(listOption.lang.noSelect);
            return;
        }
        var url  = listOption.contextPath + listOption.basePath + '?method=switchVersion';
        dialog.confirm(listOption.lang.comfirmSwitch, function(value) {
            if(value == true) {
                window.del_load = dialog.loading();
                $.ajax({
                    url : url,
                    type : 'POST',
                    data : $.param({"List_Selected" : values}, true),
                    dataType : 'json',
                    error : function(data) {
                        commonErrorFunction(data)
                    },
                    success: function(data) {
                        if(window.del_load != null){
                            window.del_load.hide();
                            topic.publish("list.refresh");
                            isSwitchVersion = true;
                        }
                        dialog.result(data);
                    }
                });
            }
        });
    }

    function addNewVersion() {
        var values = [];
        $("input[name='List_Selected']:checked").each(function() {
            values.push($(this).val());
        });
        if(values.length==0){
            dialog.alert(listOption.lang.noSelect);
            return;
        }

        //提示：如果升级，其它将会连带升级的应用
        var url  = listOption.contextPath + listOption.basePath + '?method=checkAddNewVersionApps';
        window._load = dialog.loading();
        $.ajax({
            url : url,
            type : 'POST',
            data : $.param({"List_Selected" : values}, true),
            dataType : 'json',
            error : function (data) {
                commonErrorFunction(data)
            },
            success: function (data) {
                if(window._load != null) {
                    window._load.hide();
                }
                if (data.status) {
                    //确认是否连带升级
                    var apps = data.result.apps;
                    if (apps && apps.length > 0) {
                        var appName = data.result.appName;
                        var relatedAppNames = "";
                        var isAllFormal = true;
                        var paramName = "";
                        for(i in apps){
                            if(apps[i].fdLicenseType == '0'){
                                isAllFormal = false;
                                paramName = apps[i].fdAppName ;
                                paramName = paramName.substring(0,paramName.lastIndexOf("(")+1)+ listOption.lang.licenseShort + ")";
                                relatedAppNames += "【" + paramName + "】、";
                            }else{
                                relatedAppNames += "【" + apps[i].fdAppName +"】、";
                            }
                        }
                        if(relatedAppNames.endsWith("、")){
                            relatedAppNames = relatedAppNames.substring(0, relatedAppNames.length-1);
                        }

                        var checkAddNewVersionAppsText = '';
                        if(isAllFormal){
                            checkAddNewVersionAppsText = appName + listOption.lang.copyNewAppTip1 + relatedAppNames + listOption.lang.copyNewAppTip2;
                            dialog.confirm({html: checkAddNewVersionAppsText, title: listOption.lang.tips, width: 436, callback: function (value) {
                                    if (value) {
                                        startAddNewVersion(values, true);
                                    }
                                }});
                        }else{
                            checkAddNewVersionAppsText = appName + listOption.lang.copyNewAppTip1 + relatedAppNames + listOption.lang.copyNewAppTip3;
                            dialog.alert({html: checkAddNewVersionAppsText, title: listOption.lang.tips, width: 436 });
                        }


                    } else {
                        startAddNewVersion(values);
                    }
                } else {
                    dialog.result(data);
                }
            }
        });
    }

    function startAddNewVersion(values, confirmed) {
        if(confirmed){
            appNewVersionReq(values);
        } else {
            dialog.confirm({html: listOption.lang.comfirmAdd, title: listOption.lang.tips, width: 436, callback: function (value) {
                if (value) {
                    appNewVersionReq(values);
                }
            }});
        }
    }

    function appNewVersionReq(values) {

        function showProcessIframe(values) {
            var fdAppId = $('li[data-ver-id="'+values+'"]').attr("data-app-id") || listOption.fdId;
            var url = '/sys/modeling/base/appVersion/dialog_process.jsp?fdAppId=' + fdAppId;
            return dialog.iframe(url, null, function (data) {
            }, {
                width: 540,
                height: 108
            });
        }

        var processIframe = showProcessIframe(values);
        var url = listOption.contextPath + listOption.basePath + '?method=addNewVersion';
        $.ajax({
            url: url,
            type: 'POST',
            data: $.param({"List_Selected": values}, true),
            dataType: 'json',
            error: function (data) {
                processIframe.hide();
                commonErrorFunction(data)
            },
            success: function (data) {
                processIframe.hide();
                topic.publish("list.refresh");
                dialog.result(data);
            }
        });
    }

    function commonErrorFunction(data) {
        if(window._load != null) {
            window._load.hide();
        }
        if(!data){
            data = {"status" : false, "title" : listOption.lang.failed};
        }
        dialog.result(data);
    }

    /**
     * 版本管理网格页面的点击事件绑定
     */
    function bindOnGridClick(){
        $('#selectedVersion')[0].innerHTML = "";
        var $lis = $(".model-list-content").find("li");
        $lis.each(function (i, o) {
            //行点击事件
            $(o).on("click", function () {
                liOnClick($(this), $lis);
            });
            //编辑按钮点击事件
            $(o).find(".model-list-content-create").on("click", function () {
                var fdId = $(o).attr("data-app-id");
                Com_OpenWindow(listOption.contextPath + "/sys/modeling/base/modelingApplication.do?method=appIndex&fdId="+fdId, "_blank");
            });
        });
        //默认选中当前版本
        $lis.each(function (i, o) {
            if ($(o).hasClass('cur')) {
                liOnClick($(o), $lis);
                return false;
            }
        });
    }

    /**
     * 版本管理网格页面的点击事件
     */
    function liOnClick(li, lis){
        var appId = li.attr("data-ver-id");
        lis.each(function (i, o) {
            var radio = $(this).find("input[data-lui-mark]:first");
            if(radio.val() === appId){
                radio[0].checked=true;
                $(this).addClass("active");
                var title = $(this).find(".model-list-content-desc-title p:first")[0].innerHTML;
                $('#selectedVersion')[0].innerHTML = listOption.lang.selected+":" + title;
                //选中当前版本时，隐藏切换版本按钮
                if($(this).hasClass('cur')){
                    $("#switchVersionBtn").hide();
                } else {
                    $("#switchVersionBtn").show();
                }
            } else {
                radio[0].checked=false;
                $(this).removeClass("active");
            }
        });
    }

    /**
     * 版本管理详细列表页面行点击事件
     */
    function selectRow(divObj, fdId){
        $(divObj).find("tr[kmss_fdid='"+fdId+"']").find("input[name='List_Selected']")[0].checked = true;
    }

    window.addNewVersion = addNewVersion;
    window.switchVersion = switchVersion;
    window.bindOnGridClick = bindOnGridClick;
    window.liOnClick = liOnClick;
    window.selectRow = selectRow;
});