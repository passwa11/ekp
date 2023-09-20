define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var dialog = require("lui/dialog");
    var base = require("lui/base");
    var env = require("lui/util/env");
    var strutil = require('lui/util/str');
    var topic = require("lui/topic");
    var constant = require('lui/address/addressConstant');
    var data_split = "|||||";

    // 矩阵页签对象
    var MatrixPanel = base.Container.extend({
        initProps: function ($super, _config) {
            this.config = _config || {};
            if (!this.config.version) {
                this.config.version = "V1";
            }
            this.version = this.config.version;
            if (!this.config.id) {
                this.config.id = "lui_matrix_panel_content_" + this.version;
            }

            // 初始化内置变量
            this.seqTab = $("#matrix_seq_table_" + this.version + " tbody");
            this.dataTab = $("#matrix_data_table_" + this.version + " tbody");
            this.optTab = $("#matrix_opt_table_" + this.version + " tbody");
            this.page = 1;
            this.rowsize = 10;
            this.seq = 1;
            this.fdDataCateId = window.fdDataCateId;

            $super(_config);
        },
        startup: function ($super) {
            $super($super);
        },
        setCateId: function (cateId) {
            this.fdDataCateId = cateId;
        },
        render: function ($super, obj) {
            var self = this;
            // 全选
            $("#matrix_seq_checkbox_" + self.version).click(function () {
                self.seqTab.find("[name=List_Selected]:checkbox").prop("checked", this.checked);
            });
            $("#matrix_seq_table_" + self.version).on("change", "[name=List_Selected]:checkbox", function () {
                var isAll = true;
                self.seqTab.find("[name=List_Selected]:checkbox").each(function (i, n) {
                    if (!n.checked) {
                        isAll = false;
                        return false;
                    }
                });
                $("#matrix_seq_checkbox_" + self.version).prop("checked", isAll);
            });
            // 监听分页事件
            topic.subscribe('paging.changed', function (evt) {
                if (evt) {
                    // 保存当前页面的数据
                    self.saveData();
                    // 切换到下一页
                    for (var i = 0; i < evt.page.length; i++) {
                        if (evt.page[i].key == "pageno") {
                            self.page = evt.page[i].value[0];
                        } else if (evt.page[i].key == "rowsize") {
                            self.rowsize = evt.page[i].value[0];
                        }
                    }
                    self.initData(self.page);
                }
            });
        },
        /* 增加一行 */
        addData: function () {
            var self = this;
            self.addLine(true);
            self.resetSeq();
        },
        /* 删除一行 */
        delData: function (elem) {
            var self = this;
            var idx = $(elem.parentNode).parent().parent().prevAll().length;
            var ids = []
            var id = self.seqTab.find("tr:eq(" + idx + ")").find("input[name=List_Selected]").val();
            if (id.length > 0 && id != 'on' && id.indexOf("new_") == -1) {
                ids.push(id);
            }
            if (ids.length > 0) {
                // 需要删除数据库
                dialog.confirm(Msg_Info.page_comfirmDelete, function (value) {
                    if (value == true) {
                        self.removeElem(idx);
                        self.resetSeq();
                        self.deleteMatrixData(ids);
                    }
                });
            } else {
                // 只删除表格
                self.removeElem(idx);
                self.resetSeq();
            }
        },
        /* 删除数据库数据 */
        deleteMatrixData: function (ids) {
            var self = this;
            $.ajax({
                url: Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteData',
                type: 'POST',
                dataType: 'json',
                data: $.param({'fdId': window.MatrixResult.fdId, 'List_Selected': ids}, true),
                success: function (res) {
                    if (!res.status) {
                        dialog.failure(res.msg);
                    }
                    self.initData(self.page);
                },
                error: function () {
                    if(evt) {
                        evt.cancel = true;
                    }
                    dialog.failure(Msg_Info.errors_unknown);
                }
            });
        },
        /* 删除表格元素 */
        removeElem: function (idx) {
            var self = this;
            self.seqTab.find("tr:eq(" + idx + ")").remove();
            self.dataTab.find("tr:eq(" + idx + ")").remove();
            self.optTab.find("tr:eq(" + idx + ")").remove();
        },
        /* 批量删除 */
        delAllData: function () {
            var self = this;
            var ids = [], idx = [];
            var count = 0;
            self.seqTab.find("input[name=List_Selected]:checked").each(function (i, n) {
                var id = $(this).val();
                if (id.length > 0 && id != 'on' && id.indexOf("new_") == -1) {
                    ids.push(id);
                }
                idx.push($(n.parentNode).parent().prevAll().length);
                count++;
            });
            if (count == 0)
                dialog.alert(Msg_Info.select_notice);
            if (ids.length > 0) {
                dialog.confirm(Msg_Info.page_comfirmDelete, function (value) {
                    if (value == true) {
                        // 删除表格行
                        for (var i = idx.length - 1; i >= 0; i--) {
                            self.removeElem(idx[i]);
                        }
                        self.resetSeq();
                        self.deleteMatrixData(ids);
                    }
                });
            } else {
                // 只删除表格
                for (var i = idx.length - 1; i >= 0; i--) {
                    self.removeElem(idx[i]);
                }
                self.resetSeq();
            }
            // 取消全选
            $("#matrix_seq_checkbox" + self.version).prop("checked", false);
        },
        /* 批量替换 */
        batchReplace: function () {
            var self = this;
            var ids = [];
            // 保存替换的表格行号
            self.seqTab.find("input[name=List_Selected]").each(function (i, n) {
                if ($(n).is(":checked")) {
                    var id = $(this).val();
                    if (id.length > 0 && id != 'on') {
                        ids.push(i);
                    }
                }
            });

            if (ids.length > 0) {
                // 获取字段，原数据，新数据
                dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrix_edit_data_replace.jsp?fdId=" + window.MatrixResult.fdId + "&curVersion=" + window.curVersion,
                    Msg_Info.replace_note, null, {
                        width: 550,
                        height: 350,
                        buttons: [{
                            name: Msg_Info.button_ok,
                            value: true,
                            focus: true,
                            fn: function (value, _dialog) {
                                var frame = _dialog.frame[0];
                                var contentWin = $($(frame).find("iframe")[0].contentWindow.document),
                                    field = contentWin.find('#sysOrgMatrix_field option:selected').data("obj"),
                                    _field = JSON.parse(decodeURIComponent(field));
                                var idx;
                                self.dataTab.find("th").each(function (i, n) {
                                    if ($(n).data("field") == _field.fieldName) {
                                        idx = i;
                                        return false;
                                    }
                                });
                                // 获取原数据和目标数
                                var oriId_person, oriId_post, targetId_person, targetName_person, targetId_post,
                                    targetName_post, oriId,oriName,targetId, targetName;
                                if (_field.type == "person_post") {
                                    // 人 + 岗
                                    oriId_person = contentWin.find("input[name='oriId_person']").val(),
                                        oriId_post = contentWin.find("input[name='oriId_post']").val(),
                                        targetId_person = contentWin.find("input[name='targetId_person']").val(),
                                        targetName_person = contentWin.find("input[name='targetName_person']").val(),
                                        targetId_post = contentWin.find("input[name='targetId_post']").val(),
                                        targetName_post = contentWin.find("input[name='targetName_post']").val();
                                } else {
                                    // 人 或 岗
                                    oriId = contentWin.find("input[name='oriId']").val(),
                                        oriName = contentWin.find("input[name='oriName']").val(),
                                        targetId = contentWin.find("input[name='targetId']").val(),
                                        targetName = contentWin.find("input[name='targetName']").val();
                                }
                                // 数据替换
                                for (var i = 0; i < ids.length; i++) {
                                    var tr = self.dataTab.find("tr:eq(" + (ids[i] + 1) + ")"),
                                        td = tr.find("td:eq(" + idx + ")");
                                    if (_field.type == "person_post") { //人+岗位
                                        var inputs = td.find("input"), data = {};
                                        if (inputs[0].value != "") {
                                            data = JSON.parse(inputs[0].value);
                                        }
                                        data.person = data.person || "";
                                        data.post = data.post || "";
                                        if (oriId_person == data.person) {
                                            data["person"] = targetId_person;
                                            $(inputs[1]).val(targetId_person);
                                            $(inputs[2]).val(targetName_person);
                                        }
                                        if (oriId_post == data.post) {
                                            data["post"] = targetId_post;
                                            $(inputs[3]).val(targetId_post);
                                            $(inputs[4]).val(targetName_post);
                                        }
                                        $(inputs[0]).val(JSON.stringify(data));
                                    } else {//人或岗位
                                        var fieldIds = td.find("input[type=hidden]").val(); //选中的字段
                                        if (fieldIds) {
                                            if (fieldIds.indexOf(oriId) != -1) { //如果替换id匹配上
                                                var newfieldIds = td.find("input[type=hidden]").val();
                                                var newfieldTexts = td.find("input[type=text]").val();
                                                newfieldIds = newfieldIds.replace(oriId, targetId);
                                                newfieldTexts = newfieldTexts.replace(oriName, targetName);
                                                if (window.console) {
                                                    console.log("替换后的Ids：" + newfieldIds);
                                                    console.log("替换后的Name：" + newfieldTexts);
                                                }
                                                td.find("input[type=hidden]").val(newfieldIds);
                                                td.find("input[type=text]").val(newfieldTexts);
                                            }
                                        }
                                    }
                                }
                                _dialog.hide();
                            }
                        }, {
                            name: Msg_Info.button_cancel,
                            styleClass: "lui_toolbar_btn_gray",
                            value: false,
                            fn: function (value, _dialog) {
                                _dialog.hide();
                            }
                        }]
                    });
            } else {
                dialog.alert(Msg_Info.select_notice);
            }
        },
        /* 生成表格 */
        initDataTab: function (addLine) {
            var self = this;
            if (!window.MatrixResult) {
                if (window.console) {
                    console.log("生成表格失败：MatrixResult=", window.MatrixResult);
                }
                return false;
            }
            var con = window.MatrixResult.fdRelationConditionals, res = window.MatrixResult.fdRelationResults;
            if (window.console) {
                console.log("准备生成表格：MatrixResult=", window.MatrixResult);
            }
            var width = window.width;
            // 默认生成10行
            self.seqTab.find("tr:gt(0)").remove();
            self.dataTab.empty();
            self.optTab.find("tr:gt(0)").remove();
            for (var idx = 0; idx < 10; idx++) {
                // 标题
                if (idx == 0) {
                    var dataTr = [];
                    dataTr.push("<tr style='height: 65px;'>");
                    for (var i = 0; i < con.length; i++) {
                        var isRange = con[i].fdType.indexOf("Range") > -1;
                        var __field;
                        if(isRange) {
                            __field = "<th class='lui_matrix_td_normal_title lui_maxtrix_condition_th' style='min-width:"+ width + "px' data-field='" + con[i].fdFieldName + "' data-isrange='" + isRange + "' data-field-id='" + con[i].fdId + "'>" +
                                "	<pre>" + con[i].fdName + "</pre>" +
                                "</th>";
                        } else {
                            __field = "<th class='lui_matrix_td_normal_title lui_maxtrix_condition_th'  style='min-width:"+ width + "px' data-field='" + con[i].fdFieldName + "' data-field-id='" + con[i].fdId + "'>" +
                                "	<pre>" + con[i].fdName + "</pre>" +
                                "	<i class='sysMatrix_tb_filterBtn' onclick='addSearch(this, event)'>" +
                                "		<div class='sysOrgMartrixSearchItem' onclick='stopBub(event)'>" +
                                "			<input type='text' name='keyword' placeholder='" + Msg_Info.search_keyword + "' onkeyup='keySearch(this, event)'>" +
                                "			<span class='sysOrgMartrixSearchItemBtn' onclick='searchBtn(this)'></span>" +
                                "			<ul class='sysOrgMartrixSearchItemList'></ul>" +
                                "		</div>" +
                                "	</i>" +
                                "</th>";
                        }
                        dataTr.push(__field);
                    }
                    for (var i = 0; i < res.length; i++) {
                        dataTr.push("<th class='lui_matrix_td_normal_title lui_maxtrix_result_th'  style='min-width:"+ width + "px' data-field='" + res[i].fdFieldName + "'><pre>" + res[i].fdName + "</pre></th>");
                        // 结果字段暂时不支持过滤
                        // dataTr.push("<th class='lui_matrix_td_normal_title lui_maxtrix_result_th' data-field='" + res[i].fdFieldName + "'><pre>" + res[i].fdName + "</pre><i class='sysMatrix_tb_filterBtn' onclick='addSearch(this,event)'><div class='sysOrgMartrixSearchItem' onclick='stopBub(event)'><input placeholder='请输入关键词搜索'><span class='sysOrgMartrixSearchItemBtn' onclick='searchBtn(this)'></span></div></i></th>");
                    }
                    dataTr.push("</tr>");
                    self.dataTab.append(dataTr.join(""));
                }
                if (addLine) {
                    self.addLine();
                }
            }
            self.resetSeq();
            // 设置高度
            self.resizeTr();
        },
        /* 重置行高 */
        resizeTr: function () {
            var self = this;
            var height = self.dataTab.find("tr:first").height();
            self.seqTab.find("tr:first").height(height);
            self.optTab.find("tr:first").height(height);
            setTimeout(function () {
                self.resizeTr();
            }, 300);
        },
        /* 编辑页填充数据 */
        initData: function (page) {
            var self = this;
            page = page || 1;
            // 如果没有结果数据，不处理
            if (!(window.MatrixResult && window.MatrixResult.fdId && window.curVersion)) {
                return false;
            }
            var _filter = window.filter ? JSON.stringify(window.filter) : '';
            var data = {
                'pageno': page, 'rowsize': self.rowsize, 'pagingtype': 'default',
                'fdId': window.MatrixResult.fdId,
                'fdVersion': window.curVersion,
                'filter': _filter
            };
            if (window.fdIsEnabledCate == true || window.fdIsEnabledCate == 'true') {
                if (typeof (self.fdDataCateId) == 'object') {
                    self.fdDataCateId = self.fdDataCateId[window.curVersion];
                }
                data['fdDataCateId'] = self.fdDataCateId || '';
            }
            $.ajax({
                url: Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=findMatrixPage&method_GET=edit',
                type: 'POST',
                dataType: 'json',
                data: data,
                success: function (res) {
                    // 设置数据加载成功标识
                    window.matrixDataArray[window.curVersion] = true;
                    if (res.datas && res.datas.length > 0) {
                        self.seqTab.find("tr:gt(0)").remove();
                        self.dataTab.find("tr:gt(0)").remove();
                        self.optTab.find("tr:gt(0)").remove();
                        var max = self.seqTab.find("tr").length - 1;
                        for (var i = 0; i < res.datas.length; i++) {
                            var datas = res.datas[i];
                            if (i >= max) {
                                self.addLine();
                            }
                            for (var j = 0; j < datas.length; j++) {
                                var data = datas[j];
                                if (data.value == data_split) {
                                    continue;
                                }
                                if (data.col == "fdId") {
                                    // 处理ID和序号
                                    self.seqTab.find("tr:eq(" + (i + 1) + ")").find("[type=checkbox]").val(data.value);
                                } else {
                                    var field = data.col;
                                    var isRange = false;
                                    if(field.substr(field.length - 2) == "_2") {
                                        isRange = true;
                                        field = field.substr(0, field.length-2);
                                    }
                                    var th = self.dataTab.find("tr:eq(0)").find("th[data-field='" + field + "']");
                                    if(th.length == 0) {
                                        continue;
                                    }
                                    var idx = th.prevAll().length;
                                    data.value = data.value.replace(/(^\s*)|(\s*$)/g, "").replace(/[\r\n\t]/g, "");
                                    var temp = data.value.split(data_split);
                                    // 处理内容
                                    var td = self.dataTab.find("tr:eq(" + (i + 1) + ")").find("td:eq(" + idx + ")");
                                    if(isRange) {
                                        // 处理区间数据
                                        var name2 = td.find("[type=text][name$=_2]");
                                        if(name2) {
                                            name2.val(data.value);
                                        }
                                    } else {
                                        if(temp.length < 3) {
                                            var id = td.find("[type=hidden]");
                                            var name = td.find("[type=text]:eq(0)");
                                            var tmplNode = td.find(".luiTmplField");
                                            if(id) {
                                                id.val(temp[0]);
                                            }
                                            if(name && temp.length > 1) {
                                                name.val(temp[1]);
                                                tmplNode.html(temp[1]);
                                            }
                                        } else {
                                            var _ids = temp[0].split(";"),
                                                _names = temp[1].split(";"),
                                                _orgType = trimSpace(temp[2].split(";")),
                                                _json = {};
                                            for(var k=0; k<_orgType.length; k++) {
                                                var __orgType = _orgType[k].replace(/\s+/g, "").replace(/[\r\n\t]/g, "");
                                                if(__orgType == '4') {
                                                    td.find("[type=text][name$=post]").val(_names[k]);
                                                    td.find("[type=hidden][name$=post]").val(_ids[k]);
                                                    _json["post"] = _ids[k];
                                                } else {
                                                    td.find("[type=text][name$=person]").val(_names[k]);
                                                    td.find("[type=hidden][name$=person]").val(_ids[k]);
                                                    _json["person"] = _ids[k];
                                                }
                                            }
                                            td.find("[type=hidden]").each(function(i, n) {
                                                var __name = n.name;
                                                if(!endWith(__name, "post") && !endWith(__name, "person")) {
                                                    $(n).val(JSON.stringify(_json));
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                        self.resetSeq();
                        // 分页
                        self.showPage(res.page.currentPage, res.page.pageSize, res.page.totalSize);
                    } else {
                        var cons = window.MatrixResult.fdRelationConditionals;
                        if (self.dataTab.find("[name^='" + cons[0].fdId + "']").length == 0) {
                            // 如果没有数据，且没有生成空白行，就默认创建10行
                            self.seq = 0;
                            for (var i = 0; i < 10; i++) {
                                self.addLine();
                            }
                        }
                        // 隐藏分页
                        LUI("matrix_data_table_" + window.curVersion + "_page").element.hide();
                    }
                    // 发布事件
                    self.emit('dataLoaded');
                },
                error: function () {
                    dialog.failure(Msg_Info.errors_unknown);
                }
            });
        },
        /* 编辑页填充数据 */
        initDataByBulkAdd: function (page, resultDatas) {
            var self = this;
            if (resultDatas.length > 0) {
                for (var i = 0; i < resultDatas.length; i++) {
                    self.addLine(true);
                }
            }
            page = page || 1;
            // 如果没有结果数据，不处理
            if (!(window.MatrixResult && window.MatrixResult.fdId && window.curVersion)) {

                return false;
            }
            // 设置数据加载成功标识
            if (resultDatas && resultDatas.length > 0) {
                for (var i = 0; i < resultDatas.length; i++) {
                    var datas = resultDatas[i];
                    for (var j = 0; j < datas.length; j++) {
                        var data = datas[j];
                        var field = data.col;
                        if(field.substr(field.length - 2) == "_2") {
                            field = field.substr(0, field.length-2);
                        }

                        var th = self.dataTab.find("tr:eq(0)").find("th[data-field='" + field + "']"), td;
                        if(th.length == 0) {
                            td = self.dataTab.find("tr:eq(" + (i + 1) + ")").find("td:eq(" + (j) + ")");
                        } else {
                            var idx = th.prevAll().length;
                            td = self.dataTab.find("tr:eq(" + (i + 1) + ")").find("td:eq(" + idx + ")");
                        }
                        if(td.length == 0) {
                            continue;
                        }
                        data.value = data.value.replace(/(^\s*)|(\s*$)/g, "").replace(/[\r\n\t]/g, "");
                        var temp = data.value.split(data_split);
                        // 处理内容
                        if(th.length > 0 && th.data('isrange') === true || th.data('isrange') === 'true') {
                            // 处理区间数据
                            var vals = temp[0].split(/[~|～]/);
                            td.find("[type=text]:eq(0)").val(vals[0]);
                            if(vals.length > 1) {
                                td.find("[type=text]:eq(1)").val(vals[1]);
                            }
                        } else {
                            if(temp.length < 3) {
                                var id = td.find("[type=hidden]");
                                var name = td.find("[type=text]");
                                var tmplNode = td.find(".luiTmplField");
                                if(id) {
                                    id.val(temp[0]);
                                }
                                if(name && temp.length > 1) {
                                    name.val(temp[1]);
                                    tmplNode.html(temp[1]);
                                }
                            } else {
                                var _ids = temp[0].split(";"),
                                    _names = temp[1].split(";"),
                                    _orgType = trimSpace(temp[2].split(";")),
                                    _json = {};
                                for(var k=0; k<_orgType.length; k++) {
                                    var __orgType = _orgType[k].replace(/\s+/g, "").replace(/[\r\n\t]/g, "");
                                    if(__orgType == '4') {
                                        td.find("[type=text][name$=post]").val(_names[k]);
                                        td.find("[type=hidden][name$=post]").val(_ids[k]);
                                        if(_ids[k] && _ids[k] != '')
                                            _json["post"] = _ids[k];
                                    } else {
                                        td.find("[type=text][name$=person]").val(_names[k]);
                                        td.find("[type=hidden][name$=person]").val(_ids[k]);
                                        if(_ids[k] && _ids[k] != '')
                                            _json["person"] = _ids[k];
                                    }
                                }
                                td.find("[type=hidden]").each(function(i, n) {
                                    var __name = n.name;
                                    if(!endWith(__name, "post") && !endWith(__name, "person")) {
                                        $(n).val(JSON.stringify(_json));
                                    }
                                });
                            }
                        }

                        /**
                        var data = datas[j];
                        if (!data) {
                            continue;
                        }
                        if (data.value == data_split) {
                            continue;
                        }
                        data.value = data.value.replace(/(^\s*)|(\s*$)/g, "").replace(/[\r\n\t]/g, "");
                        var temp = data.value.split(data_split);
                        // 处理内容
                        var td = self.dataTab.find("tr:eq(" + (i + 1) + ")").find("td:eq(" + (j) + ")");
                        if (temp.length < 3) {
                            var id = td.find("[type=hidden]");
                            var name = td.find("[type=text]");
                            var tmplNode = td.find(".luiTmplField");
                            if (id) {
                                id.val(temp[0]);
                            }
                            if (name && temp.length > 1) {
                                name.val(temp[1]);
                                tmplNode.html(temp[1]);
                            }
                        } else {
                            var _ids = temp[0].split(";"),
                                _names = temp[1].split(";"),
                                _orgType = trimSpace(temp[2].split(";")),
                                _json = {};
                            for (var k = 0; k < _orgType.length; k++) {
                                var __orgType = _orgType[k].replace(/\s+/g, "").replace(/[\r\n\t]/g, "");
                                if (__orgType == '4') {
                                    td.find("[type=text][name$=post]").val(_names[k]);
                                    td.find("[type=hidden][name$=post]").val(_ids[k]);
                                    if (_ids[k] && _ids[k] != '')
                                        _json["post"] = _ids[k];
                                } else {
                                    td.find("[type=text][name$=person]").val(_names[k]);
                                    td.find("[type=hidden][name$=person]").val(_ids[k]);
                                    if (_ids[k] && _ids[k] != '')
                                        _json["person"] = _ids[k];
                                }
                            }
                            td.find("[type=hidden]").each(function (i, n) {
                                var __name = n.name;
                                if (!endWith(__name, "post") && !endWith(__name, "person")) {
                                    $(n).val(JSON.stringify(_json));
                                }
                            });
                        }
                        */
                    }
                }
                self.removeRows();
                self.resetSeq();
                // 分页
                //self.showPage(res.page.currentPage, res.page.pageSize, res.page.totalSize);
            }
        },
        //删除空白行
        removeRows: function () {
            var self = this;
            //var deleteCount = 0;
            self.dataTab.find("tr:gt(0)").each(function (i, _tr) {
                //如果有数据则不删除
                var deleteFlag = true;
                $(_tr).find("td").find("input").each(function (j, _input) {
                    if ($(_input).val() && $(_input).val() != '') {
                        deleteFlag = false;
                    }
                });
                if (deleteFlag) {
                    $(_tr).remove();
                    //deleteCount++;
                }
            });
        },
        /* 显示分页 */
        showPage: function (page, pageSize, totalSize) {
            var self = this;
            window.dataTablePage = LUI("matrix_data_table_" + self.version + "_page");
            if (window.dataTablePage) {
                dataTablePage.config.totalSize = totalSize;
                dataTablePage.config.currentPage = page;
                dataTablePage.config.pageSize = pageSize;
                dataTablePage.totalSize = parseInt(totalSize);
                dataTablePage.currentPage = parseInt(page);
                dataTablePage.pageSize = parseInt(pageSize);
                dataTablePage.draw();
            } else {
                setTimeout(function () {
                    self.showPage(page, pageSize, totalSize);
                }, 100);
            }
        },
        /* 保存当前页面的数据(切换分页时需要保存当前页数据，避免数据丢失) */
        saveData: function () {
            var self = this;
            var datas = [];
            self.seqTab.find("tr:gt(0)").each(function (i, n) {
                var obj = {};
                var fdId = $(n).find("[type=checkbox]").val();
                if (fdId.length > 0 && fdId != 'on') {
                    obj['fdId'] = fdId;
                }
                var hasVal = false;
                self.dataTab.find("tr:eq(" + (i + 1) + ")").find("[data-type=fieldId]").each(function (j, m) {
                    var name = $(m).attr("name").replace(/\[[^\]]+\]/g, '');
                    var value = $(m).val();
                    if (value.length > 0) {
                        hasVal = true;
                        obj[name] = value;
                    }
                });
                if (hasVal) {
                    datas.push(obj);
                }
            });
            var __data = {};
            // 只保存当前版本数据
            __data[window.curVersion] = datas;
            $.ajax({
                url: Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveAllData',
                type: 'POST',
                dataType: 'json',
                data: {'fdMatrixId': window.MatrixResult.fdId, 'matrixData': JSON.stringify(__data)},
                success: function (res) {
                    if (!res.status) {
                        dialog.failure(res.msg);
                    }
                },
                error: function () {
                    evt.cancel = true;
                    dialog.failure(Msg_Info.errors_unknown);
                }
            });
        },
        /* 新增一行 */
        addLine: function (first) {
            var self = this;
            if (!window.MatrixResult) {
                return false;
            }
            var con = window.MatrixResult.fdRelationConditionals, res = window.MatrixResult.fdRelationResults,
                idx = self.seq++;
            var _height = "65px";

            // 内容行
            var dataTr = [];
            // 内容
            dataTr.push("<tr style=\"height: " + _height + ";\">");
            for (var i = 0; i < con.length; i++) {
                var orgType = con[i].fdType == 'org' ? 'ORG_TYPE_ORG' : con[i].fdType == 'dept' ? 'ORG_TYPE_DEPT' : con[i].fdType == 'post' ? 'ORG_TYPE_POST' : con[i].fdType == 'person' ? 'ORG_TYPE_PERSON' : con[i].fdType == 'group' ? 'ORG_TYPE_GROUP' : '';
                dataTr.push("<td class=\"lui_maxtrix_condition_td\" style=\"padding: 5px;\">");
                if (con[i].fdMainDataType == 'sys') {
                    dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
                    dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalId || '') + "\" />");
                    dataTr.push("	<input type=\"text\" name=\"" + con[i].fdFieldName + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" style=\"width:90%; min-width:100px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("	<a onclick=\"Dialog_MainData('" + con[i].fdId + "[" + idx + "]', '" + con[i].fdFieldName + "[" + idx + "]', '" + con[i].fdName + "');\" class=\"selectitem\"></a>");
                    dataTr.push("</div>");
                } else if (con[i].fdMainDataType == 'cust') {
                    dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
                    dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalId || '') + "\" />");
                    dataTr.push("	<input type=\"text\" name=\"" + con[i].fdFieldName + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" style=\"width:90%; min-width:100px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("	<a onclick=\"Dialog_CustData(false, '" + con[i].fdId + "[" + idx + "]', '" + con[i].fdFieldName + "[" + idx + "]', null, 'sysOrgMatrixMainDataService&id=" + con[i].fdType + "', '" + con[i].fdName + "');\" class=\"selectitem\"></a>");
                    dataTr.push("</div>");
                } else if (con[i].fdType == 'constant') {
                    dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" onblur=\"checkconstant(this);\" style=\"width:90%; min-width:100px;\" class=\"inputsgl\" validate=\"maxLength(200)\" />");
                } else if(con[i].fdType == 'numRange') {
                    // 数值区间
                    dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" onblur=\"checknumRange(this);\" style=\"width:45%; min-width:100px;\" class=\"inputsgl\" validate=\"number\"/>");
                    dataTr.push("<span> ~ </span>");
                    dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]_2\" value=\"" + (con[i].fdConditionalValue2 || '') + "\" onblur=\"checknumRange(this);\" style=\"width:45%; min-width:100px;\" class=\"inputsgl\" validate=\"number numRange\"/>");
                } else if(con[i].fdType == 'dateRange') {
                    // 日期区间
                    dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" onblur=\"checknumRange(this);\" style=\"width:45%; min-width:100px;\" class=\"inputsgl\" validate=\"number\"/>");
                    dataTr.push("<span> ~ </span>");
                    dataTr.push("<input type=\"text\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]_2\" value=\"" + (con[i].fdConditionalValue2 || '') + "\" onblur=\"checknumRange(this);\" style=\"width:45%; min-width:100px;\" class=\"inputsgl\" validate=\"number dateRange\"/>");
                } else {
                    var icon = con[i].fdType;
                    if(icon == 'org') {
                        // 防止图标重复
                        icon += '1';
                    }
                    dataTr.push("<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
                    dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + con[i].fdId + "[" + idx + "]\" value=\"" + (con[i].fdConditionalId || '') + "\" />");
                    dataTr.push("	<div class=\"luiTmplField\" data-name=\"" + con[i].fdFieldName + "[" + idx + "]\">" + (con[i].fdConditionalValue || '') + "</div>");
                    dataTr.push("	<input type=\"hidden\" name=\"" + con[i].fdFieldName + "[" + idx + "]\" value=\"" + (con[i].fdConditionalValue || '') + "\" style=\"width:90%; min-width:100px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("	<a onclick=\"Dialog_Address_Cust(false, '" + con[i].fdId + "[" + idx + "]', '" + con[i].fdFieldName + "[" + idx + "]', null, " + orgType + ");\" class=\"" + icon + "element\"></a>");
                    dataTr.push("</div>");
                }
                dataTr.push("</td>");
            }
            for (var i = 0; i < res.length; i++) {
                dataTr.push("<td class=\"lui_maxtrix_result_td\" style=\"padding: 5px;\">");
                if (res[i].fdType == 'person_post') {
                    dataTr.push("	<input type=\"hidden\" data-type=\"fieldId\" name=\"" + res[i].fdId + "[" + idx + "]\" value=\"" + res[i].fdResultValueIds + "\" />");
                    dataTr.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
                    dataTr.push("		<input type=\"hidden\" data-type=\"fieldId\" name=\"" + res[i].fdId + "[" + idx + "]_person\" />");
                    dataTr.push("		<input type=\"text\" name=\"" + res[i].fdFieldName + "[" + idx + "]_person\" style=\"width:80%;min-width:60px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("		<a onclick=\"Dialog_Address_Cust(false, '" + res[i].fdId + "[" + idx + "]_person', '" + res[i].fdFieldName + "[" + idx + "]_person', null, ORG_TYPE_PERSON, resultCheck2);\" class=\"personelement\"></a>");
                    dataTr.push("	</div>");
                    dataTr.push("	<br>");
                    dataTr.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\" name=\"" + res[i].fdId + "[" + idx + "]_post\">");
                    dataTr.push("		<input type=\"hidden\" data-type=\"fieldId\" name=\"" + res[i].fdId + "[" + idx + "]_post\" />");
                    dataTr.push("		<input type=\"text\" name=\"" + res[i].fdFieldName + "[" + idx + "]_post\" style=\"width:80%;min-width:60px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("		<a onclick=\"Dialog_Address_Cust(false, '" + res[i].fdId + "[" + idx + "]_post', '" + res[i].fdFieldName + "[" + idx + "]_post', null, ORG_TYPE_POST, resultCheck2);\" class=\"postelement\"></a>");
                    dataTr.push("	</div>");
                } else {
                    var orgType = res[i].fdType == 'post' ? 'ORG_TYPE_POST' : 'ORG_TYPE_PERSON';
                    dataTr.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
                    dataTr.push("		<input type=\"hidden\" data-type=\"fieldId\" name=\"" + res[i].fdId + "[" + idx + "]\" value=\"" + res[i].fdResultValueIds + "\" />");
                    dataTr.push("		<input type=\"text\" name=\"" + res[i].fdFieldName + "[" + idx + "]\" value=\"" + res[i].fdResultValueNames + "\" style=\"width:80%;min-width:60px;\" readonly=\"true\" class=\"inputsgl\" />");
                    dataTr.push("		<a onclick=\"Dialog_Address_Cust(true, '" + res[i].fdId + "[" + idx + "]', '" + res[i].fdFieldName + "[" + idx + "]', null, " + orgType + ",resultCheck);\" class=\"" + res[i].fdType + "element\"></a>");
                    dataTr.push("	</div>");
                }
                dataTr.push("</td>");
            }
            dataTr.push("</tr>");
            if (first) {
                self.dataTab.find("tr:eq(0)").after(dataTr.join(""));
            } else {
                self.dataTab.append(dataTr.join(""));
            }

            // 序号行
            var seqTr = [];
            seqTr.push("<tr style=\"height: " + _height + ";\">");
            seqTr.push("<td><input type=\"checkbox\" name=\"List_Selected\" value=\"new_" + new Date().getTime() + "\"></td>");
            seqTr.push("<td name=\"matrix_data_seq\">" + (idx + 1) + "</td>");
            seqTr.push("</tr>");
            if (first) {
                self.seqTab.find("tr:eq(0)").after(seqTr.join(""));
            } else {
                self.seqTab.append(seqTr.join(""));
            }

            // 操作行
            var optTr = [];
            optTr.push("<tr style=\"height: " + _height + ";\">");
            optTr.push("<td><span class=\"lui_text_primary\"><a href=\"javascript:;\" onclick=\"delData(this);\">" + Msg_Info.button_delete + "</a></span></td>");
            optTr.push("</tr>");
            self.optTab.append(optTr.join(""));
        },
        /* 重新排列序号 */
        resetSeq: function () {
            var self = this;
            self.seqTab.find("td[name='matrix_data_seq']").each(function (i, n) {
                $(n).text(i + 1);
            });
        }

    });

    exports.MatrixPanel = MatrixPanel;
});
