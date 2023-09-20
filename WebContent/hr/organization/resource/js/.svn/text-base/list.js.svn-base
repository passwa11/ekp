Com_IncludeFile("lang.jsp", Com_Parameter.ContextPath + "hr/organization/resource/js/",null,true);
var now = new Date(); //当前日期 
var nowDayOfWeek = now.getDay(); //今天本周的第几天 
var nowDay = now.getDate(); //当前日 
var nowMonth = now.getMonth(); //当前月 
var nowYear = now.getYear(); //当前年 
nowYear += (nowYear < 2000) ? 1900 : 0; //

var lastMonthDate = new Date(); //上月日期
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
var lastYear = lastMonthDate.getYear();
var lastMonth = lastMonthDate.getMonth();
//格式化日期：yyyy-MM-dd 
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth() + 1;
    var myweekday = date.getDate();

    if(mymonth < 10) {
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10) {
        myweekday = "0" + myweekday;
    }
    return(myyear + "-" + mymonth + "-" + myweekday);
}

//今天
function getCurrentDay() {
    var date = new Date();
    return formatDate(date);
}

//获得某月的天数 
function getMonthDays(myMonth) {
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    return days;
}

//获得本季度的开始月份 
function getQuarterStartMonth() {
    var quarterStartMonth = 0;
    if(nowMonth < 3) {
        quarterStartMonth = 0;
    }
    if(2 < nowMonth && nowMonth < 6) {
        quarterStartMonth = 3;
    }
    if(5 < nowMonth && nowMonth < 9) {
        quarterStartMonth = 6;
    }
    if(nowMonth > 8) {
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}

//获得本周的开始日期 
function getWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
    return formatDate(weekStartDate);
}

//获得本周的结束日期 
function getWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
    return formatDate(weekEndDate);
}

//获得本月的开始日期 
function getMonthStartDate() {
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}

//获得本月的结束日期 
function getMonthEndDate() {
    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
    return formatDate(monthEndDate);
}

//获得上月开始时间
function getLastMonthStartDate() {
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
}

//获得上月结束时间
function getLastMonthEndDate() {
    var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
    return formatDate(lastMonthEndDate);
}

//获得本季度的开始日期 
function getQuarterStartDate() {

    var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
    return formatDate(quarterStartDate);
}

//或的本季度的结束日期 
function getQuarterEndDate() {
    var quarterEndMonth = getQuarterStartMonth() + 2;
    var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
    return formatDate(quarterStartDate);
}

function getCurrentQuarterStartTime() {
    var
    c = Calendar.getInstance();
    var currentMonth =
    c.get(Calendar.MONTH) + 1;
    var longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    var now = null;
    if(currentMonth >= 1 && currentMonth <= 3)
        c.set(Calendar.MONTH, 0);
    else if(currentMonth >= 4 && currentMonth <= 6)
        c.set(Calendar.MONTH, 3);
    else if(currentMonth >= 7 && currentMonth <= 9)
        c.set(Calendar.MONTH, 6);
    else if(currentMonth >= 10 && currentMonth <= 12)
        c.set(Calendar.MONTH, 9);
    c.set(Calendar.DATE, 1);
    now = longSdf
        .parse(simpleDateFormat.format(
            c.getTime()) + " 00:00:00");
    return now;
}

//今年第一天
function getYearStartDate() {
    var date = new Date();
    var year = date.getFullYear();
    var day = year + "-01-01";
    return day;
}

//今年最后一天
function getYearEndDate() {
    var date = new Date();
    var year = date.getFullYear();
    var day = year + "-12-31";
    return day;
}

//去年年第一天
function getlastYearStartDate() {
    var date = new Date();
    var year = date.getFullYear() - 1;
    var day = year + "-01-01";
    return day;
}

//去年年最后一天
function getlastYearEndDate() {
    var date = new Date();
    var year = date.getFullYear() - 1;
    var day = year + "-12-31";
    return day;
}

function appendQueryParameter(url) {
    var option = window.listOption;
    if(location.search == "") {
        url = url + "&q.j_path=" + option.jPath;
        return url;
    }
    var paraList = location.search.substring(1).split("&");
    for(var
        i = 0;
        i < paraList.length;
        i ++) {
        var para = paraList[
            i];
        var index = para.indexOf("q.");
        if(index != 0) {
            continue;
        }
        if(url.indexOf('?') == -1) {
            url += '?' + para;
        } else {
            url += '&' + para;
        }
    }
    url = url + "&q.j_path=" + option.jPath;
    return url;
}

seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common','lang!hr-organization'], function($, dialog, topic, dialogCommon,lang) {
    var option = window.listOption;

    //选择类型后选择模板-新UI
    function afterTemplateSelectUi(rtn) {
        if(rtn) {
            var idString = rtn.value;
            if(idString != "") {
                var idArray = idString.split(";");
                var docTemplateId = idArray[0];
                var url = option.contextPath + option.basePath + "?method=add&i.docTemplate=" + docTemplateId;
                Com_OpenWindow(url);
            }
        }
    }

    function addTemplateTreeMain() {
        var currTempId = getValueByHash("docTemplate");
        var fdTemplateId = currTempId;
        if(fdTemplateId == null || fdTemplateId == "") {
            dialog.tree(option.templateService + '&parentId=!{value}', option.templateAlert, afterTemplateSelectUi);
        }
    }

    function addDoc() {
        Com_OpenWindow(option.contextPath + option.basePath + "?method=add");
    }

    function addTemplateConfig() {
        dialog.categoryForNewFile(option.modelName, option.basePath + '?method=add&i.docCategory=!{id}',
            false, null, null, getValueByHash("docCategory"), null, null, false);
    }

    function addTemplateMain() {
        dialog.categoryForNewFile(option.templateName, option.basePath + '?method=add&i.docTemplate=!{id}',
            false, null, null, getValueByHash("docTemplate"));
    }

    function addSCategoryMain() {
        dialog.simpleCategoryForNewFile(option.templateName, option.basePath + '?method=add&i.docTemplate=!{id}',
            false, null, null, getValueByHash("docTemplate"));
    }

    function addOtherTemplateMain() {
        var currTempId = getValueByHash("docTemplate");
        if(currTempId != null && currTempId != '') {
            window.open(option.basePath + '?method=add&i.docTemplate=' + currTempId, "_blank");
        } else {
            var context = option.createDialogCtx;
            var sourceUrl = context.sourceUrl;
            var params = {};
            if(context.params) {
                for(var
                    i = 0;
                    i < context.params.length;
                    i ++) {
                    var argu = context.params[
                        i];
                    for(var field in argu) {
                        var tmpFieldObj = document.getElementsByName(field);
                        if(tmpFieldObj.length > 0) {
                            params['c.' + argu[field] + '.' + field] = tmpFieldObj[0].value;
                        }
                    }
                }
            }
            dialogCommon.dialogSelectForNewFile(context.modelName, sourceUrl, params,
                option.basePath + '?method=add&i.docTemplate=!{id}', null, null, '_blank');
        }
    }

    function deleteAll() {
        var selected = [];
        $("input[name='List_Selected']:checked").each(function() {
            selected.push($(this).val());
        });
        if(selected.length == 0) {
            dialog.alert(option.lang.noSelect);
            return;
        }
        dialog.confirm(option.lang.comfirmDelete, function(ok) {
            if(ok == true) {
                var del_load = dialog.loading();
                var param = {
                    "List_Selected": selected
                };
                var hash = getValueByHash("docCategory");
                if(hash) {
                    param.docCategory = hash;
                }
                hash = getValueByHash("docTemplate");
                if(hash) {
                    param.docTemplate = hash;
                }
                $.ajax({
                    url: option.contextPath + option.basePath + '?method=deleteall',
                    data: $.param(param, true),
                    dataType: 'json',
                    type: 'POST',
                    success: function(data) {
                        if(del_load != null) {
                            del_load.hide();
                            topic.publish("list.refresh");
                        }
                        dialog.result(data);
                    },
                    error: function(req) {
                        if(req.responseJSON) {
                            var data = req.responseJSON;
                            dialog.failure(data.title);
                        } else {
                            dialog.failure($lang.fail);
                        }
                        del_load.hide();
                    }
                });
            }
        });
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    window.addBatchPrint = function() {
        var isSelected = false;
        var selectedIds = "";
        var obj = document.getElementsByName("List_Selected");
        for(var
            i = 0;
            i < obj.length;
            i ++) {
            if(obj[
                i].checked) {
                selectedIds = selectedIds + obj[
                    i].value;
                if(
                    i != obj.length - 1) {
                    selectedIds = selectedIds + ",";
                }
                isSelected = true;
            }
        }
        if(isSelected == false) {
            dialog.alert(option.lang.noSelect);
            return false;
        }
        submitForm(selectedIds);
        //Com_OpenWindow(option.contextPath + option.basePath + '?method=addBatchPrint&fdIds='+selectedIds);
    }

    function submitForm(selectedIds) {
        //创建url
        var url = option.contextPath + option.basePath + "?method=addBatchPrint";
        //创建form
        var form = $("<form target=\"_blank\"></form>");
        //设置属性
        form.attr("action", url);
        form.attr("method", "post");
        //创建input，即参数
        var input = $("<input type='text' name='fdIds'/>");
        input.attr("value", selectedIds);
        //注入参数到表单
        form.append(input);
        var mainIframe = document.getElementById("mainIframe");
        if(mainIframe != null) {
            form.appendTo(window.parent.document.getElementsByTagName("body"));
        } else {
            form.appendTo("body");
        }
        form.hide();
        //提交表单
        form.submit();
    }

    /*自定义操作按钮的click处理*/
    function doCustomOpt(optCode) {
        var selected = [];
        $("input[name='List_Selected']:checked").each(function() {
            selected.push($(this).val());
        });
        if(selected.length == 0) {
            dialog.alert(option.lang.noSelect);
            return;
        }
        var del_load = dialog.loading();
        var param = {
            "List_Selected": selected
        };
        if(option.customOpts && option.customOpts[optCode]) {
            var argsObject = option.customOpts[optCode];
            for(var arg in argsObject) {
                param[arg] = argsObject[arg];
            }
            if(argsObject.popup == 'true') {
                var popurl = option.contextPath + argsObject.popupUrl;
                param['List_Selected_Count'] = selected.length;
                openWindowViaDynamicForm(popurl, param);
                del_load.hide();
                return;
            }
        }
        $.ajax({
            url: option.contextPath + option.basePath + '?method=' + optCode,
            data: $.param(param, true),
            dataType: 'json',
            type: 'POST',
            success: function(data) {
                if(del_load != null) {
                    del_load.hide();
                    topic.publish("list.refresh");
                }
                dialog.result(data);
            },
            error: function(req) {
                if(req.responseJSON) {
                    var data = req.responseJSON;
                    dialog.failure(data.title);
                } else {
                    dialog.failure($lang.fail);
                }
                del_load.hide();
            }
        });
    }

    function getValueByHash(key) {
        var value = Com_GetUrlParameter(location.href, 'q.' + key);
        if(value) {
            return value;
        }
        var hash = window.location.hash;
        if(hash.indexOf(key) < 0) {
            return "";
        }
        var url = hash.split("cri.q=")[1];
        var reg = new RegExp("(^|;)" + key + ":([^;]*)(;|$)");
        var
        r = url.match(reg);
        if(
            r != null) {
            return unescape(
                r [2]);
        }
        return "";
    }
    
    
    //导入
    window.listExport = function(url){
		window.export_load = dialog.loading();
		var json = new Array();
		var values;
		var ths = LUI.$(".lui_listview_columntable_table").find("th");
		var thsValues=null;
		var index=0;
		LUI.$("input[name='List_Selected']:checked").each(function(j){
		 var tds = LUI.$(this).parent().parent().find("td");
		 var data = new Array();
		 for (var i = 1; i < ths.length; i++) {
			 if(ths[i].innerText == '操作'){
					continue
			}
			 var input=LUI.$(ths[i]).find("input[type='checkbox'][name='List_Tongle']");
				if(LUI.$(input).length==0){
					if(tds[i].innerText=='' && LUI.$(tds[i]).children("img").length>0){
						if(LUI.$(tds[i]).children("img").eq(0).attr("title")){
						data.push([ths[i].innerText,LUI.$(tds[i]).children("img").eq(0).attr("title")]);
						}else{
						data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
						}
					}else{
						data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
					}
				} 
		 }
		 json.push(["json"+j,data]);
		 index=j;
		});
		if(json.length!=0){
			for (var i = 1; i < ths.length; i++) {
				if(ths[i].innerText == '操作'){
					continue
				}
				if(thsValues==null){
					thsValues=ths[i].innerText;
				}else{
					thsValues=thsValues+","+ths[i].innerText;
				}
		}
		 openWindowWithPost(url,"post","json",encodeURI(LUI.stringify(json)),"ths",encodeURI(thsValues));
		  if(window.export_load!=null){
				window.export_load.hide(); 
			}
		}else{
			 if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(lang['hr.organization.info.tip.7']);
			return;
		}
    }
    
    var encodeHTML = function(str){ 
		return str.replace(/&/g,"&amp;")
			.replace(/</g,"&lt;")
			.replace(/>/g,"&gt;")
			.replace(/\"/g,"\"")
			.replace(/\'/g,"&#39;")
			.replace(/¹/g, "&sup1;")
			.replace(/²/g, "&sup2;")
			.replace(/³/g, "&sup3;");
	};
    
    
    
    

    topic.subscribe('criteria.changed', function(evt) {
        //      if(LUI('del')){ LUI('toolbar').removeButton(LUI('del'));}
        for(var
            i = 0;
            i < evt['criterions'].length;
            i ++) {
            //alert(evt['criterions'][i].key+"="+evt['criterions'][i].value[0]);
        }
    });
    topic.subscribe('successReloadPage', function() {
        topic.publish('list.refresh');
    });

    switch(option.mode) {
        case 'config_template':
            window.addDoc = addTemplateConfig;
            break;
        case 'main_template':
            window.addDoc = addTemplateMain;
            break;
        case 'main_scategory':
            window.addDoc = addSCategoryMain;
            break;
        case 'main_other':
            window.addDoc = addOtherTemplateMain;
            break;
        case 'main_treeModel':
            window.addDoc = addTemplateTreeMain;
            break;
        default:
            window.addDoc = addDoc;
    }
    window.deleteAll = deleteAll;
    window.doCustomOpt = doCustomOpt;


});
LUI.ready(function() {
    var option = window.listOption;
    if(option.canDelete === '' && LUI('btnDelete')) {
        LUI('btnDelete').setVisible(false);
    }
});