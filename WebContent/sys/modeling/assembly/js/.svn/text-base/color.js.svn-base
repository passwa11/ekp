/**
*
*/
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-main");
    var postProjectListData = [];
    var defaultColorListId = '1';
    var isPost = false;

    var ModelingColor = base.Container.extend({


        /**
         * 初始化
         * cfg应包含：
         */
        initProps: function ($super, cfg) {
            this.bindEvent(cfg);
        },
        /**
         * 绑定事件
         */
        bindEvent: function (cfg) {
            var projectListData = []; //项目数据集

            var colorListData = []; //配色方案数据集

            var curModifyIndex = ''; //当前操作项目的下标

            var curModifyItem = []; //当前操作项目

            var curScheme = []; //当前配色方案

            var curColorList = []; // 当前配色方案的色值数组

            // 渲染配色方案色卡
            var renderColorCard = function renderColorCard(data) {
                var card = '';
                data.map(function (item) {
                    card += "<li style='background: ".concat(item, "'></li>");
                });
                return card;
            };

            //渲染配色方案选择列表
            var renderColorSchemes = function renderColorSchemes(data) {
                colorListData = data;
                var schemeItem = '';
                data.map(function (item) {
                    schemeItem += "<li class=\"option-list-item\" key=\"".concat(item.id, "\">\n                                <ul class=\"color-list\">").concat(renderColorCard(item.colorList), "</ul>\n                                <p class=\"color-schemes-name\">").concat(item.name, "</p>\n                            </li>");
                });

                if (data.length == 0) {
                    schemeItem = "<li class=\"option-empty-item\">\u6682\u65E0\u53EF\u9009\u914D\u8272\u65B9\u6848</li>";
                }

                $('.option-list').append(schemeItem);
            };

            // 渲染项目列表
            var renderProjectLists = function renderProjectLists(data) {
                projectListData = data;
                var projectItem = '';
                data.map(function (item) {
                    projectItem += "<li key=\"".concat(item.id, "\"><i class=\"project-color\" style=\"background: ").concat(item.bgColor, "\"></i><p class=\"project-title\">").concat(item.name, "</p></li> ");
                });
                $('.project-lists').append(projectItem);
            };

            // 渲染切换配色方案后的列表颜色
            var renderProjectColor = function renderProjectColor(data) {
                $(".project-lists li").each(function (index, item) {
                    if(projectListData[index].status == "default"){
                        // projectListData[index].status = "default";
                        var remainder = index % 10;
                        $.each(data, function (i, color) {
                            if (remainder == i) {
                                var itemColor = $(item).find(".project-color");
                                itemColor.css('background', color);
                            }
                        });
                    }
                });
            };

            // 渲染自定义颜色
            var renderProjectItemColor = function renderProjectItemColor(flag, color, status) {
                var customColor = '';
                $(".custom-color").empty();

                if (flag) {
                    customColor = " <p class=\"editing-color-title\">"+modelingLang['modelingGantt.customize']+"</p>\n                            <div class=\"custom-color-box\">\n                                <i class=\"custom-item-color  ".concat(status, "\" style=\"background: ").concat(color, "\"></i>\n                            </div>");
                } else {
                    customColor = " <p class=\"editing-color-title\">"+modelingLang['modelingGantt.customize']+"</p>\n                            <i class=\"custom-color-add\"></i>";
                }

                $(".custom-color").append(customColor);
            };

            // 渲染预设颜色
            var renderPresetColor = function renderPresetColor(data) {
                var presetItem = '';
                $('.preset-color-list').empty();
                $.each(data, function (index, item) {
                    presetItem += "<li style=\"background: ".concat(item, "\"></li>");
                });
                $('.preset-color-list').append(presetItem);
            };

            // 获取当前配色方案的色卡
            var getCurColorList = function getCurColorList(id) {
                curScheme = colorListData.filter(function (item) {
                    return item.id == id;
                });
                $('#select-txt').val(curScheme[0].name);
                curColorList = curScheme[0].colorList;
                renderPresetColor(curColorList);
            };

            // 渲染更新的标记颜色
            var rendermodifyColor = function rendermodifyColor(color) {
                // 更新数据列表
                curModifyItem.status = "temporary";
                curModifyItem.bgColor = color;

                // 重新渲染项目颜色
                $(".project-lists li").eq(curModifyIndex).find("i").css('background', color);
                // 重新渲染自定义颜色
                renderProjectItemColor(true, color, "temporary");
            };

            var updatePostProjectListData = function updatePostProjectListData (color){
                isPost = true;
                let b = true;
                var key = $(".project-lists li").eq(curModifyIndex).attr('key');
                for (let i = 0; i <postProjectListData.length ; i++) {
                    if(key == postProjectListData[i].key){
                        postProjectListData[i].bgColor = color;
                        b = false;
                    }
                }
                if(b){
                    var project = {};
                    project.key = key;
                    project.bgColor = color;
                    postProjectListData.push(project);
                }
            };


            /** ****************初始化数据********************* */
            // 初始化数据
            var colorData = cfg.colorData;
            var projectData = cfg.projectData;
            var colorListIndex = cfg.colorListIndex;
            defaultColorListId = colorListIndex;
            renderColorSchemes(colorData); //配色方案数据

            renderProjectLists(projectData); //项目数据

            getCurColorList(colorListIndex); // 当前使用的配色方案id

            // 选择配色方案 starts
            $("#select-txt").click(function (event) {
                event.stopPropagation();

                if ($(".option-list").css("display") == "block") {
                    $(".option-list").css("display", "none");
                } else {
                    $(".option-list").css("display", "block");
                }
            });
            $(".option-list").on('click', '.option-list-item', function (event) {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                modifyList = [];
                renderProjectItemColor(false);
                defaultColorListId = $(this).attr("key");
                isPost = true;
                getCurColorList($(this).attr("key"));
                renderProjectColor(curColorList);
            });

            // 点击其他地方关闭下拉列表
            $(document).click(function () {
                if ($(".option-list").css("display") == "block") {
                    $(".option-list").css("display", "none");
                }
            });

            // 选择配色方案 ends
            // 选择操作项目 starts
            $(".project-lists").on('click', 'li', function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                curModifyIndex = $(this).index('.project-lists li');
                curModifyItem = projectListData[curModifyIndex];
                var itemStatus = projectListData[curModifyIndex].status;
                var itemColor = projectListData[curModifyIndex].bgColor;

                if (itemStatus == 'default') {
                    renderProjectItemColor(false);
                } else {
                    renderProjectItemColor(true, itemColor, itemStatus);
                    $(".custom-item-color").on('mouseenter', function () {
                        $(this).removeClass(itemStatus);
                    }).on('mouseleave', function () {
                        $(this).addClass(itemStatus);
                    });
                }
            });
            // 选择操作项目 ends

            // 默认选择第一项
            $(".project-lists li")[0].click();

            // // 添加自定义颜色 starts
            $(".custom-color").on('click', '.custom-color-add', function () {
                // .custom-color-add 点击事件只作弹出选择颜色框用
                if (window.SpectrumColorPicker) {
                    // .custom-color-color 点击事件只作弹出选择颜色框用
                    $(".custom-color-add").spectrum({
                        showInput: true,
                        palette: self.palette,
                        preferredFormat: "hex",
                        showPalette: true,
                        change: function (color) {
                            var _color = color.toHexString().toUpperCase();
                            rendermodifyColor(_color);
                            updatePostProjectListData(_color);
                        },
                        hide:function (color){
                            var _color = color.toHexString().toUpperCase();
                            var background = $(".project-lists li").eq(curModifyIndex).find("i").css('backgroundColor');
                            var rgb = background.split(',');
                            var r = parseInt(rgb[0].split('(')[1]);
                            var g = parseInt(rgb[1]);
                            var b = parseInt(rgb[2].split(')')[0]);
                            var hex = "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
                            if(color == hex){
                                return;
                            }
                            rendermodifyColor(_color);
                            updatePostProjectListData(_color);
                            // var oldColor = $(this).find("[name='" + valueName + "_sColorPic']").val();
                        }
                    });
                    var background = $(".project-lists li").eq(curModifyIndex).find("i").css('background');
                    $(".custom-color-add").spectrum("set", background);
                    $(".custom-color-add").spectrum('show');
                    // 将确定的标记颜色存起来
                }
            });

            // // 添加自定义颜色 ends
            // //编辑自定义颜色 starts
            $(".custom-color").on('click', '.custom-item-color', function () {
                // .custom-color-color 点击事件只作弹出选择颜色框用
                $(".custom-item-color").spectrum({
                    showInput: true,
                    palette:self.palette,
                    preferredFormat: "hex",
                    showPalette: true,
                    change: function (color) {
                        var _color = color.toHexString().toUpperCase();
                        rendermodifyColor(_color);
                        updatePostProjectListData(_color);
                    },
                    hide:function (color){
                        var _color = color.toHexString().toUpperCase();
                        rendermodifyColor(_color);
                        updatePostProjectListData(_color);
                        // var oldColor = $(this).find("[name='" + valueName + "_sColorPic']").val();
                    }
                });
                var background = $(".project-lists li").eq(curModifyIndex).find("i").css('background');
                $(".custom-item-color").spectrum("set", background);
                $(".custom-item-color").spectrum('show');
            });

            // 编辑自定义颜色 ends
            // 从预设颜色中自定义颜色
            $(".preset-color-list").on('click', 'li', function () {
                var selectColor = $(this).css("background-color");
                var rgb_color = selectColor.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
                function hex(x) {
                    return ("0" + parseInt(x).toString(16)).slice(-2);
                }
                rgb_color= "#" + hex(rgb_color[1]) + hex(rgb_color[2]) + hex(rgb_color[3]);
                rendermodifyColor(rgb_color);
                updatePostProjectListData(rgb_color);
            });

        },

        startup: function ($super, cfg) {
            $super(cfg);
        },

        getPostProjectListData: function () {
            return postProjectListData;
        },

        getDefaultColorListId: function () {
            return defaultColorListId;
        },

        getIsPost: function () {
            return isPost;
        },

    });

    exports.ModelingColor = ModelingColor;
})