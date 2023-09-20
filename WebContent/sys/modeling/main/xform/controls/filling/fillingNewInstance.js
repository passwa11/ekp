function execFillingEvent(bindObj, params,eventObj,myid, callback, _obj) {
    var paramsJSON = JSON.parse(params.replace(/quot;/g, "\""));
    var xformFlag = bindObj.closest("xformflag");
    var button = eventObj.closest("xformflag").find(".lui-placeholder-button");
    //$(button).trigger("click","true");
    _currentTriggerNotVal =  $(bindObj).val() == ""? true : false;
    //正在填充时,不需要再次触发
    if ($(xformFlag).attr("onfilling") != "true") {
         $(button).trigger($.Event("click"),{isTrigger: true});
    }
}

// 获取控件的id，含extendDataFormInfo.value,动态单选、多选、选择框、填充控件有用到
function relation_getParentXformFlagIdHasPre(control){
    var xformFlag = $(control).closest("xformflag");
    var flagid;
    if(xformFlag){
        flagid =  xformFlag.attr("flagid");
        var name = $(control).attr('name');
        if(!name){
            name = $(control).attr('myid');
        }
        flagid = name.replace(/\([^\)]*\)/g,'(' + flagid + ')');
    }
    return flagid;
}

/**
 * 处理正在加载loading绑定的id
 * @param myid 数据填充控件的id,如果在明细表内,已经被截取掉
 * @param obj  数据填充控件
 * @param bindDom 触发控件
 * @returns
 */
function Xform_RelationEvent_processImgBindId(myid, obj, bindDom) {
    var relationEventControlId = myid;
    if (!obj) {
        return relationEventControlId;
    }
    var tempMyid = relation_getParentXformFlagIdHasPre(obj);
    var tempBindDomName = $(bindDom).attr('name');
    if (tempMyid.indexOf(".") > -1 && tempBindDomName.indexOf(".") > -1) {
        //name为明细表的情况
        var rowIndex = tempBindDomName.match(/\.(\d+)\./g);
        rowIndex = rowIndex ? rowIndex : [];
        //明细表ID
        var detailFromId = "";
        var detailsData = {};
        detailFromId = tempBindDomName.match(/\((\w+)\./g);
        if (detailFromId) {
            detailFromId = detailFromId[0].replace("(", "").replace(".", "");
        } else {
            detailFromId = tempBindDomName.split(".")[0];
        }
        if (tempMyid.indexOf(detailFromId) > -1) {
            tempMyid = tempMyid.replace(/\.(\S+)\./, rowIndex[0]);
            relationEventControlId = tempMyid;
        }
    }
    return relationEventControlId;
}
var _modelingFillingMap = {};
var _modelingFillingNum = 0;
var _modelingFillingCfgInfo = {};
var _openDialogFlag = false;
//#170142 记录是否是回填字段触发值改变事件，false则需要触发业务填充，true则不触发业务填充
var isAfterSelect = false;
// var _currentTriggerNotVal = false;
$(function () {
    if(!Xform_ObjectInfo.ExtraDealControlFun){
        Xform_ObjectInfo.ExtraDealControlFun = [];
    }
    Xform_ObjectInfo.ExtraDealControlFun.push(Placeholder_SetValue);

    function Placeholder_SetValue(xformflag,value){
        var xformType;
        if(xformflag){
            xformType = xformflag.attr("flagtype");
        }
        if(xformType){
            if(xformType == "filling"){
                var wgtId = xformflag.find(".modelingPlaceholder").attr("id");
                LUI(wgtId).updateTextView();
            }
        }
    };

    $("div[mytype='filling']").each(function (i, obj) {
        var bindDom = $(obj).attr('binddom');
        var bindEvent = "change";   //现在默认只有值改变事件
        if (bindDom.indexOf(".")) {
            bindDom = bindDom.substr(bindDom.lastIndexOf(".") + 1);
        }
        var myid = relation_getParentXformFlagIdHasPre(obj);
        if (myid.indexOf(".")) {
            myid = myid.substr(myid.lastIndexOf(".") + 1);
        }

        seajs.use(["sys/modeling/main/xform/controls/placeholder/placeholderDispatcher","lui/topic"],function(placeholderDispatcher,topic){
            //控件在主表
            $(obj).closest("xformflag[flagtype='filling']:not([id*='!{index}'])").each(function(index,dom) {
                var $storeDataDom = $(obj).closest("xformflag").find(".modelingPlaceholder");
                var fillingWgt = new placeholderDispatcher.PlaceholderDispatcher({
                    element: $storeDataDom,
                    fillingType: "filling",
                    bindDom: bindDom
                });
                fillingWgt.startup();
            });

            //明细表内控件 有明细表的 table-add 事件触发初始化
            $(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
                var row = argus.row;
                //初始化
                $(row).find("xformflag[flagtype='filling']:not([id*='!{index}'])").each(function(index,dom){
                    var $storeDataDom = $(dom).find(".modelingPlaceholder");
                    var flagid = $(dom).attr("flagid");
                    bindDom = $(dom).find("[mytype='filling']").attr('binddom');
                    var fillingWgt = new placeholderDispatcher.PlaceholderDispatcher({
                        element: $storeDataDom,
                        fillingType: "filling",
                        bindDom: bindDom,
                        controlId:flagid
                    });
                    fillingWgt.startup();
                });
            });

            $(document).on('table-delete','table[showStatisticRow]',function(e,row){
                // 删除的同时需要删除对象
                $(row).find("xformflag[flagtype='filling']:not([id*='!{index}'])").each(function(index,dom){
                    var $storeDataDom = $(dom).closest("xformflag").find(".modelingPlaceholder");
                    var domId = $storeDataDom.attr("id");
                    if(domId && LUI(domId)){
                        var controlIdWithIndex = LUI(domId).controlIdWithIndex;
                        LUI(domId).destroy();
                        var wgtId = parseInt(domId.substring(7));
                        var count = 0;//#165980 循环最近的三个组件，判断是否是相同的controlId，如果是则销毁
                        while(count < 3){
                            wgtId--;
                            if(LUI('lui-id-'+wgtId)
                                && LUI('lui-id-'+wgtId).controlIdWithIndex === controlIdWithIndex
                                && LUI('lui-id-'+wgtId).placeholderWgt){
                                //#165980 需要销毁同一行，同一个id的填充控件，以防重复监听事件，执行多次回填事件
                                LUI('lui-id-'+wgtId).destroy();
                            }
                            count++;
                        }
                    }
                });
            });
            if (/-fd(\w+)/g.test(bindDom)) {
                // 控件id不会含有-的，只要有-证明是手动添加上去的
                if ("click" == bindEvent) {
                    bindDom = bindDom.match(/(\S+)-/g)[0].replace("-", "");
                } else {
                    var param = bindDom.match(/-fd(\w+)/g)[0].replace("-fd", "").toLowerCase();
                    bindDom = bindDom.match(/(\S+)-/g)[0].replace("-", ".") + param;
                }
            }
            //获取绑定的事件控件对象
            var bindStr = document.getElementById(bindDom) ? "#" + bindDom : "[name*=\'" + bindDom + "\']";
            var callback = function (e) {
                var topicId = myid;
                var detail = false;
                if(this._xForm_cache){
                    detail = this._xForm_cache.detailTable;
                }
                if(detail){
                    var nameDom = this.name;
                    var index = XForm_GetIndexInDetailByDom($("[name='"+nameDom+"']"));
                    topicId = topicId + "."+index;
                }
                topic.channel(topicId).publish("modeling.filling.change"+topicId,{"obj":this,"myid":myid,"evenObj":$(obj)});
                //不知道为啥有时候浏览器点击左上角的刷新之后.trigger传不过去参数，可能是浏览器的限制，故换成事件的形式
                // execFillingEvent($(this), $(obj).attr("params"),$(obj), myid,null, obj);
            };
            $(document).on(bindEvent, bindStr, callback);

        })
    });

});
