/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var topic = require('lui/topic');
    /**
     * Nav 导航组件 ，
     */
    var Nav = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.formParam = cfg.formParam;
            this.element = cfg.container;
            this.navContent = cfg.navContent
            this.initElement(this.element);
            // t
            // this.initByStoreData(cfg.navContent)
            console.log("applicationName",applicationName);
        },
        initElement: function ($e) {
            //先后顺序不可变，左侧需要监听右侧的值
            this.right = new NavRight({
                container: $e.find(".businessNav-main-opera"),
                parent: this,
                formParam: this.formParam
            })
            this.initByStoreData(this.navContent)
            this.left = new NavLeft({
                container: $e.find(".businessNav-main-source"),
                parent: this,
                formParam: this.formParam
            });

        },
        getKeyData: function () {
            return this.right.getKeyData();
        },
        validate: function () {
            return this.right.validate();
        },
        initByStoreData: function (storeData) {
            if (storeData) {
                this.right.initByStoreData(JSON.parse(storeData));
            }
        },

    });
    /**
     *
     */
    var NavLeft = base.Container.extend({
        //URI
        url_AllApp: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=allAppJsonArray",
        url_AllModelByAppId: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=listModelJsonByAppId&fdAppId=",
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.element = cfg.container;
            this.formParam = cfg.formParam;
            var $e = this.element;
            var containerStr = $e.find(".main-source-container").prop("outerHTML");
            $e.find(".main-source-container").hide();
            var tabForm = new NavLeftTabPanel({
                title: lang.table,
                type: "tab_form",
                $tab: $e.find(".tab_form"),
                $source: $e,
                $container: $(containerStr),
                parent: this,
                formParam: this.formParam
            })
            var tabChart = new NavLeftTabPanel({
                title: lang.chart,
                type: "tab_chart",
                $tab: $e.find(".tab_chart"),
                $source: $e,
                $container: $(containerStr),
                parent: this,
                formParam: this.formParam
            })
            var tabSpace = new NavLeftTabPanel({
                title: lang.pcTitle,
                type: "tab_space",
                $tab: $e.find(".tab_space"),
                $source: $e,
                $container: $(containerStr),
                parent: this,
                formParam: this.formParam
            })
            $e.find(".tab_form").on("click", function () {
                tabForm.active();
                tabChart.negative();
                tabSpace.negative();
            }).trigger($.Event("click"));
            $e.find(".tab_chart").on("click", function () {
                tabChart.active();
                tabForm.negative();
                tabSpace.negative();
            })
            $e.find(".tab_space").on("click", function () {
                tabSpace.active();
                tabForm.negative();
                tabChart.negative();
            })
        },
        //获取所有应用信息
        _getAllApp: function () {
            var self = this;
            if (!self._allApps) {
                $.ajax({
                    url: self.url_AllApp,
                    method: 'GET',
                    async: false
                }).success(function (result) {
                    self._allApps = result;
                })
            }
            return self._allApps
        },
        _allModel: {},
        _getAllModel: function (appId) {
            var self = this;
            if (!self._allModel[appId]) {
                $.ajax({
                    url: self.url_AllModelByAppId + appId,
                    method: 'GET',
                    async: false
                }).success(function (result) {

                    self._allModel[appId] = result;
                })
            }
            return self._allModel[appId]
        },

    })

    /**
     * 左侧的标签（用于表单和图表的切换）
     */
    var NavLeftTabPanel = base.Container.extend({
        isOnLoad: false,
        loadTime: 0,
        //标签中一级列表
        supperList: {},
        //标签中二级列表-表单
        viewExtendList: {},
        //标签中二级列表-图表
        chartExtendList: {},
        selectAppId: "",
        isBuilded: false,
        initProps: function ($super, cfg) {
            $super(cfg);
            this.parent = cfg.parent;
            this.title = cfg.title;
            this.type = cfg.type;
            this.selectAppId = cfg.formParam.fdAppId;
            //dom
            this.$source = cfg.$source;
            this.$tab = cfg.$tab;
            this.$container = cfg.$container;
            this.$extendContainer = cfg.$container.find(".source-content-extend");
            this.$supperList = cfg.$container.find(".source-content-supper");
        },
        active: function () {
            this.build()
            this.$container.show();
            this.$tab.addClass("active");
        },
        negative: function () {
            this.$container.hide();
            this.$tab.removeClass("active");
        },
        build: function () {
            if (this.isBuilded) {
                return;
            }
            var self = this;
            //渲染 应用选择
            self.buildApplicationSelect();
            self.$container.addClass(self.type + "_panel");
            //渲染
            self.$source.append(self.$container);
            this.isBuilded = true;
        },
        /**
         * 应用下拉框绘制
         */
        buildApplicationSelect: function () {
            var self = this;
            self.allApps = self.parent._getAllApp();
            var $select = self.$container.find("ul.name-select-list");
            var $selectLi;
            for (var i = 0; i < self.allApps.length; i++) {
                var appObj = {
                    "title": self.allApps[i].name,
                    "value": self.allApps[i].value,
                    "type": "application",
                    "isOnLoad": false,
                    "loadTime": 0,
                }
                var $li = $("<li />");
                $li.html(appObj.title)
                $li.attr("value", appObj.value);
                $li.attr("title", appObj.title);
                $li.data(appObj);
                if (self.selectAppId === self.allApps[i].value) {
                    $selectLi = $li;
                }
                $select.append($li)
            }
            $select.find("li").on("click", function () {
                $select.find("li").removeClass("active");
                $(this).addClass("active");
                self.$container.find(".name-select-list-selected").html($(this).text());
                self.$container.find(".name-select-list-selected").attr("title", $(this).text());
                self.selectAppId = $(this).data().value;
                self.selectAppName = $(this).data().title;
                if (self.type == "tab_form") {
                    self.buildModelList($(this));
                } else if(self.type == "tab_chart") {
                    self.buildChartList($(this));
                }else{
                    self.buildSpaceList($(this));
                }


            })
            $selectLi.trigger($.Event("click"));
            self.appSelectLi = $select;
            self.$container.find(".name-select-search .name-select-search-val")
                .on("change", function (ele) {
                    self.triggerApplicationList($(this).val());
                })
        },
        triggerApplicationList: function (str) {
            this.appSelectLi.find("li").each(function (item, idx) {
                var title = $(this).html();
                if (!str || title.indexOf(str) != -1) {
                    $(this).show()
                } else {
                    $(this).hide()
                }
            })
        },
        /**
         * 表单列表绘制
         * @param $li
         */
        buildModelList: function ($li) {
            var self = this;
            //检查缓存中是否存在
            if (!self.supperList[self.selectAppId]) {
                var modelList = self.parent._getAllModel(self.selectAppId);
                //绘制列表
                var $modelUl = $("<ul className=\"supper-list\">");
                $modelUl.attr("appId", self.selectAppId);
                $modelUl.hide();
                for (var i = 0; i < modelList.length; i++) {
                    mObj = modelList[i]
                    var $modelLi = $("<li/>");
                    $modelLi.html(mObj.name);
                    $modelLi.attr("value", mObj.id);
                    $modelLi.data(mObj);
                    $modelUl.append($modelLi);

                }
                //事件绑定
                $modelUl.find("li").on("click", function () {
                    $modelUl.find("li").removeClass("active");
                    $(this).addClass("active");
                    self.buildViewExtend($(this));
                })
                //数据记录
                self.$supperList.append($modelUl)
                self.supperList[self.selectAppId] = $modelUl
            }
            //切换绘制
            for (var appId in self.supperList) {
                self.supperList[appId].hide();
                if (appId === self.selectAppId) {
                    self.supperList[appId].show();
                    self.supperList[appId].find("li:first").trigger($.Event("click"));
                }
            }
        },
        /**
         * 视图页面绘制
         * @param $li
         */
        buildViewExtend: function ($li) {
            var self = this;
            var liData = $li.data();
            var selfKey = self.selectAppId + "." + liData.id;
            //检查缓存中是否存在,key以appId+‘.’+modelId形式存在
            if (!self.viewExtendList[selfKey]) {
                var navViewExt = new NavViewExtend({
                    modelId: liData.id,
                    appId: self.selectAppId,
                    parent: self,
                    path: [self.title, self.selectAppName, liData.name],
                    container: self.$extendContainer
                });
                self.viewExtendList[selfKey] = navViewExt;
            }
            for (var key in self.viewExtendList) {
                if (selfKey === key) {
                    self.viewExtendList[key].show();
                } else {
                    self.viewExtendList[key].hide();
                }
            }

        },
        /**
         * 图表列表绘制
         * @param $li
         */
        chartCategory: {
            custom: lang.customData,
            chartset: lang.dbEchartsChartSet,
            table: lang.dbEchartsTable,
            chart: lang.dbEchartsChart
        },
        buildSpaceList : function ($li) {
            var self = this;
            self.buildSpaceExtend();
        },
        buildSpaceExtend : function(){
            var self = this;
            var navSpaceExt = new NavSpaceExtend({
                appId: self.selectAppId,
                type: "custom",
                parent: self,
                path: [self.title, self.selectAppName, ""],
                container: self.$extendContainer
            })
            self.$supperList.css("display","none");
        },
        buildChartList: function ($li) {
            var self = this;
            //检查缓存中是否存在,仅绘制一次
            if (!self.$chartCategory) {
                self.$chartCategory = $("<ul className=\"supper-list\">");
                for (var key in self.chartCategory) {
                    var $chartLi = $("<li/>");
                    $chartLi.html(self.chartCategory[key]);
                    $chartLi.attr("value", key);
                    $chartLi.data({
                        id: key,
                        name: self.chartCategory[key]
                    })
                    self.$chartCategory.append($chartLi);
                }
                //事件绑定
                self.$chartCategory.find("li").on("click", function () {
                    self.$chartCategory.find("li").removeClass("active");
                    $(this).addClass("active");
                    self.buildChartExtend($(this));
                })
                self.$supperList.append(self.$chartCategory)
            }
            self.$chartCategory.find("li:first").trigger($.Event("click"))
        },
        /**
         * 图表二级页面绘制
         * @param $li
         */
        buildChartExtend: function ($li) {
            var self = this;
            var liData = $li.data();
            var selfKey = self.selectAppId + "." + liData.id;
            //检查缓存中是否存在,key以appId+‘.’+mkey形式存在
            if (!self.chartExtendList[selfKey]) {
                var navChartExt = new NavChartExtend({
                    appId: self.selectAppId,
                    type: $li.attr("value"),
                    parent: self,
                    path: [self.title, self.selectAppName, liData.name],
                    container: self.$extendContainer
                });
                self.chartExtendList[selfKey] = navChartExt;
            }
            for (var key in self.chartExtendList) {
                if (selfKey === key) {
                    self.chartExtendList[key].show();
                } else {
                    self.chartExtendList[key].hide();
                }
            }

        },
    });
    /**
     * 视图列表
     */
        //雷丰传新增,#166746
    var countAlert=0;
    var NavViewExtend = base.Container.extend({
        url_group: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=groupModelViewJsonByModelId&fdModelId=",
        trans: {
            "listView": lang.modelingAppListview,
            "collection": lang.modelingCollectionView,
            "gantt": lang.modelingGantt,
            "resPanel": lang.modelingResourcePanel,
            "mindMap":lang.modelingMindMap,
            "calendar":lang.modelingCalendar,
            "treeView":lang.modelingTreeView
        },

        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.container = cfg.container;
            this.modelId = cfg.modelId;
            this.parent = cfg.parent;
            this.path = cfg.path;
            this.sourceNode = {};
            this.getData(this.modelId)
            this.build();
        },
        getData: function (modelId) {
            var self = this;
            $.ajax({
                url: self.url_group + modelId,
                method: 'GET',
                async: false
            }).success(function (result) {
                self.groupData = result;
            })
        },
        build: function () {
            var self = this;
            this.element = $("<div />")
            var temp_group = "<div class=\"extend-group\">";
            var temp_groupTitle = "<div class=\"extend-group-title\">" +
                "<p>{groupTitle}</p><span class=\"addAll\" groupKey='{groupkey}'>"+lang.addAll+"</span></div>";
            var temp_groupUl = "<ul class=\"extend-group-list\"/>";

            for (var groupKey in this.groupData) {
                var arr = this.groupData[groupKey];
                if (arr && arr.length > 0) {
                    var $g = $(temp_group);
                    self.sourceNode[groupKey] = [];
                    $(temp_groupTitle.replace("{groupTitle}", this.trans[groupKey])
                        .replace("{groupkey}", groupKey)).appendTo($g);
                    var $u = $(temp_groupUl);
                    for (var i = 0; i < arr.length; i++) {
                        var liNode = new SourceNode({
                            data: arr[i],
                            groupKey: groupKey,
                            parent: self,
                            path: self.path,
                            modelId: self.modelId,
                        });
                        liNode.element.appendTo($u);
                        self.sourceNode[groupKey].push(liNode);
                    }
                    $g.append($u);
                    //添加所有的
                    $g.find(".addAll").on("click", function () {
                        var key = $(this).attr("groupkey");
                        if (key && self.sourceNode[key]) {
                            //这个位置会循环执行，导致在没有导航栏目的情况下，下面多次alert
                            countAlert=0;
                            for (var i = 0; i < self.sourceNode[key].length; i++) {
                                self.sourceNode[key][i].pushToRight();
                            }
                        }

                    })
                    $g.find("p").on("click", function () {
                        $(this).parent().parent().toggleClass("extend-group-list-hide")

                    })
                    this.element.append($g)
                }
            }
            this.container.append(this.element);

        },
        show: function () {
            this.element.show();
        },
        hide: function () {
            this.element.hide();
        }
    })

    /**
     * 图表列表
     */
    var NavChartExtend = base.Container.extend({
        url_data: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppNav.do?method=modelChartJsonByType",
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.container = cfg.container;
            this.appId = cfg.appId;
            this.type = cfg.type;
            this.parent = cfg.parent;
            //原始路径
            this.path = cfg.path;
            this.url_data = this.url_data + "&fdAppId=" + this.appId + "&type=" + this.type;
            this.getData();
            this.build();
        },
        getData: function () {
            var self = this;
            $.ajax({
                url: self.url_data,
                method: 'GET',
                async: false
            }).success(function (result) {
                self.groupData = result;
            })
        },
        build: function () {
            var self = this;
            this.element = $("<div />")
            var $g = $("<div class=\"extend-group\">");
            var $u = $("<ul class=\"extend-group-list\"/>");
            for (var i = 0; i < this.groupData.length; i++) {
                var liNode = new SourceNode({
                    data: this.groupData[i],
                    modelId: self.modelId,
                    parent: self,
                    path: self.path
                });
                liNode.element.appendTo($u);
            }
            $g.append($u);
            this.element.append($g)
            this.container.append(this.element);
        },
        show: function () {
            this.element.show();
        },
        hide: function () {
            this.element.hide();
        }
    })

    /**
     * pc首页列表
     */
    var NavSpaceExtend = base.Container.extend({
        url_data: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppSpace.do?method=findSpaceJsonByAppId",
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.container = cfg.container;
            this.appId = cfg.appId;
            this.type = cfg.type;
            this.parent = cfg.parent;
            //原始路径
            this.path = cfg.path;
            this.url_data = this.url_data + "&fdAppId=" + this.appId + "&type=" + this.type;
            this.getData()
            this.build();
        },
        getData: function () {
            var self = this;
            $.ajax({
                url: self.url_data,
                method: 'GET',
                async: false
            }).success(function (result) {
                self.groupData = result;
            })
        },
        build: function () {
            var self = this;
            this.element = $("<div />")
            var $g = $("<div class=\"extend-group\">");
            var $u = $("<ul class=\"extend-group-list\"/>");
            if(this.groupData.length == 0){
                //显示缺省图
                var html = "<div class='space-not-div'><div class = 'space-not-content'><div class='space-images'></div><div class='space-tips'>"+lang.noPcTitle+"</div></div><div?";
                $u.append(html);
            }
            for (var i = 0; i < this.groupData.length; i++) {
                var liNode = new SourceNode({
                    data: this.groupData[i],
                    modelId: self.modelId,
                    parent: self,
                    path: self.path
                });
                liNode.element.appendTo($u);
            }
            $g.append($u);
            this.element.append($g)
            this.container.append(this.element);
        },
        show: function () {
            this.element.show();
        },
        hide: function () {
            this.element.hide();
        }
    })

    var SourceNode = base.Container.extend({
        temp_groupLi: "<li value='{id}' ><i class=\"nav_route\"></i><p title='{title}'>{name}</p>" +
            "<span class=\"add\">"+lang.buttonAdd+"</span>" +
            " <div class=\"nav_cont\">\n" +
            "                    <p>"+lang.addRightNav+"</p>\n" +
            "                    <ul>\n" +
            "                    </ul>\n" +
            "                </div>" +
            "</li>",
        initProps: function ($super, cfg) {
            $super(cfg);
            this.cfg = cfg;
            this.data = cfg.data;
            this.path = cfg.path;
            this.parent = cfg.parent;
            this.referenceNavNode = [];
            this.build()
        },
        build: function () {
            var self = this;
            self.temp_groupLi = self.temp_groupLi
                .replace("{title}", this.data.text)
                .replace("{name}", this.data.text)
                .replace("{id}", this.data.value)
            this.element = $(self.temp_groupLi);
            this.element.find(".add").on("click", function () {
                //这个位置应该设置初始值为0
                countAlert=0;
                self.pushToRight();
            })
            this.pathUl = this.element.find(".nav_cont ul");
            this.referenceCount();
            topic.subscribe("NavNode.destroy", function (dom) {
                self.referenceCount();
            });

        },
        pushToRight: function () {
            var topicEvent = {
                target: this
            }
            topic.publish("NavSource.add", topicEvent);

            this.referenceCount();
        },
        referenceCount: function () {
            var main = this.parent.parent.parent.parent;
            var right = main.right;
            var groupList = right.groupList;
            this.referenceNavNode = [];
            var type = this.data.type;
            for (var i = 0; i < groupList.length; i++) {
                var group = groupList[i]
                var groupText = group.text;

                var nodeList = group.nodeList;
                for (var j = 0; j < nodeList.length; j++) {
                    var node = nodeList[j];
                    if (node.type === type && node.value == this.data.value) {
                        this.referenceNavNode.push({"groupText": groupText, "node": node});
                    }
                }
            }
            this.refreshReference();
        },
        //刷新引用状态
        refreshReference: function () {
            this.pathUl.empty();
            if (this.referenceNavNode.length > 0) {
                this.element.addClass("added");
                for (var i = 0; i < this.referenceNavNode.length; i++) {
                    var navNode = this.referenceNavNode[i];
                    this.pathUl.append("<li>" + navNode.groupText + " > " + navNode.node.text + "</li>")
                }
            } else {
                this.element.removeClass("added");
            }
        }
    })
    /**
     *
     */
    var NavRight = base.Container.extend({
        titleSelectStatus: {
            //  selected 表示已选择、
            //  unable   表示未选择且不可编辑、
            //  selectedUnable  表示已选择且不可编辑 冗余
            "selected": "selected",
            "unable": "unable",
            "selectedUnable": "selectedUnable",
        },
        groupList: [],
        selectedGroup: "",
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.element = cfg.container;
            this.buildTop();
            this.buildMain();
            this.buildSort();
        },
        buildTop: function () {
            var $title = this.element.find(".main-opera-title");
            this.title = new NavRightTitle({
                container: $title,
                parent: self
            })
        },
        buildMain: function () {
            var self = this;
            self.$main = self.element.find(".main-opera-content");
            self.$content = self.$main.find(".opera-content-main");
            self.$white = self.$main.find(".whitePage");

            //空白时判断
            if (self.groupList.length === 0) {
                self.$content.hide();
                self.$white.show();
            } else {
                self.$content.show();
                self.$white.hide();
            }
            self.$white.find(".newNode").on("click", function () {
                self.addNodeGroup();
                if (self.groupList.length > 0) {
                    self.$content.show();
                    self.$white.hide();
                }
            })
            self.$main.find(".addFirLevel").on("click", function () {
                self.addNodeGroup()
            })
            //监听左侧事件
            topic.subscribe("NavSource.add", function (dom) {
                if(countAlert > 0){
                    //弹出框了，那么不执行下面的逻辑
                    return;
                }
                if (!self.selectedGroup) {
                    //次数加一次
                    countAlert +=1;
                    dialog.alert(lang.selectFirstNav)
                } else {
                    self.selectedGroup.newNode(dom);
                }
            });

        },
        addNodeGroup: function () {
            var self = this;
            var ng = new NavNodeGroup({parent: self});
            self.$content.find(".addFirLevel").before(ng.element);
            //焦点设置
            setTimeout(function () {
                $("[nav-gourp-id=" + ng.id + "]").find(".titleVal").focus();
            }, 500);
            self.groupList.push(ng);
            //一级节点添加事件传递
            this.title.pushNode(ng);
            this.selectGroup(ng);
        },
        selectGroup: function (n) {
            var self = this;
            this.groupList.forEach(function (item, idx) {
                item.negative();
                if (n.id === item.id) {
                    item.active();
                    self.selectedGroup = item;
                }
            })
        },
        removeGroup: function (n) {
            for (var i = 0; i < this.groupList.length; i++) {
                if (this.groupList[i] === n) {
                    this.groupList.splice(i, 1);
                    if (this.selectedGroup === n) {
                        if (this.groupList.length == 0) {
                            this.selectedGroup = undefined;
                        } else {
                            this.selectedGroup = this.groupList[i];
                        }
                    }
                    //一级节点删除事件传递
                    this.title.removeNode(n);
                    break;
                }
            }
        },
        changeGroup: function (n) {
            this.title.nodeChange(n);
        },

        getKeyData: function () {
            var keyData = [];
            for (var i = 0; i < this.groupList.length; i++) {
                var ng = this.groupList[i];
                keyData.push(ng.getKeyData());
            }
            return keyData;
        },
        validate: function () {
            for (var i = 0; i < this.groupList.length; i++) {
                var ng = this.groupList[i];
                if (!ng.validate()) {
                    return false;
                }
            }
            return true;

        },
        initByStoreData: function (storeData) {
            for (var i = 0; i < storeData.length; i++) {
                var sd = storeData[i];
                var ng = new NavNodeGroup({parent: this});
                this.$content.find(".addFirLevel").before(ng.element);
                this.groupList.push(ng);
                //一级节点添加事件传递
                ng.text = sd.text
                this.title.pushNode(ng);
                this.selectGroup(ng);
                ng.initByStoreData(sd)
            }
            if (this.groupList.length > 0) {
                this.$content.show();
                this.$white.hide();
            }
        },
        //初始化一级排序
        buildSort: function () {
            var self = this;
            this.sortable = Sortable.create($("#navRightContent")[0], {
                sort: true,
                scroll: true,
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle: ".navNodeGroupDrag",
                draggable: ".nav-item",
                onStart: function (evt) {
                },
                onEnd: function (evt) {
                    self.refreshGroup(evt);
                }
            });
        },
        //刷新列表
        refreshGroup: function (evt) {
            if (!evt || evt.newIndex == evt.oldIndex) {
                return;
            }
            var self = this;
            var cacheGl = [];
            this.$content.find(".nav-item").each(function (idx, item) {
                var id = $(this).attr("nav-gourp-id");
                for (var i = 0; i < self.groupList.length; i++) {
                    if (self.groupList[i].id == id) {
                        cacheGl.push(self.groupList[i]);
                        break;
                    }
                }
            })
            this.groupList = cacheGl;
            this.title.sortNode(this.groupList);
        },
        //二级分类组件，移动，通道，用于选择其他组件刷新
        refreshGroupChannel: function (group, moveNode) {
            var self = this;
            for (var i = 0; i < self.groupList.length; i++) {
                if (self.groupList[i] == group) {
                    continue;
                }
                self.groupList[i].refreshGroupChannelDeal(moveNode);
            }
        }


    })

    var NavRightTitle = base.Container.extend({
        selectList: {},
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.element = cfg.container;
            this.parent = cfg.parent;
            this.$selectList = this.element.find(".showSetting-select-list")
            this.$selectAll = this.element.find(".showSetting-select-all")
            this.bindEvent();
        },
        bindEvent: function () {
            var self = this;
            this.$selectAll.on("click", function () {
                $(this).toggleClass("active");
                var isAll = $(this).hasClass("active");
                self.$selectList.find("li").each(function (idx, item) {
                    $(this).removeClass("selected");
                    if (isAll && !$(this).hasClass("selected")
                        && !$(this).hasClass("unable")) {
                        $(this).addClass("selected");
                    }
                })
            })
            //全部收起
            this.element.find(".opera-title-name").on("click", function () {
                topic.publish("NavGroup.expend.all", {"expend": $(this).hasClass("expend")});
                $(this).toggleClass("expend");
            })
            //搜索
            this.element.find(".showSetting-select-search-val").on("change", function (ele) {
                for (var id in self.selectList) {
                    var $i = self.selectList[id]
                    if (!$(this).val() || $i.html().indexOf($(this).val()) != -1) {
                        $i.show()
                    } else {
                        $i.hide()
                    }
                }

            })

        },
        selectAll: function () {
            var self = this;
            this.$selectAll.addClass("active");
            this.$selectList.find("li").each(function (idx, item) {
                if (!$(this).hasClass("selected") && !$(this).hasClass("unable")) {
                    self.$selectAll.removeClass("active");
                }
            })
        },
        pushNode: function (ng) {
            var self = this;
            var $li = $("<li class='unable'/>");
            if (ng.text) {
                $li.html("<i></i> " + ng.text);
            } else {
                $li.html("<i></i> " + lang.enter);
            }
            //单选点击
            $li.on("click", function () {
                if ($(this).hasClass("unable")) {
                    $(this).removeClass("selected");
                } else {
                    $(this).toggleClass("selected");
                }
                self.selectAll()
            })

            this.selectList[ng.id] = $li;
            this.$selectList.append($li);

        },
        nodeChange: function (ng) {
            var self = this;
            var $li = this.selectList[ng.id];
            //text
            $li.html("<i></i>" + ng.text);
            //child
            if (ng.nodeList.length > 0) {
                if ($li.hasClass("unable")) {
                    $li.addClass("selected");
                    $li.removeClass("unable");
                }
            } else {
                $li.removeClass("selected");
                $li.addClass("unable");
            }
            self.selectAll()

        },
        removeNode: function (ng) {
            var self = this;
            var $li = this.selectList[ng.id];
            $li.remove();
            self.selectAll()
        },
        isNodeSelected: function (ng) {
            var $li = this.selectList[ng.id];
            return $li && $li.hasClass("selected")
        },
        setNodeSelected: function (ng) {
            var $li = this.selectList[ng.id];
            if (ng.expand) {
                $li.addClass("selected");
            } else {
                $li.removeClass("selected");
            }
        },
        sortNode: function (ngList) {
            this.$selectList.find("li").detach()
            for (var i = 0; i <ngList.length ; i++) {
                var $li = this.selectList[ngList[i].id];;
                this.$selectList.append($li);
            }
        }
    })
    var NavNodeGroup = base.Container.extend({
        temp: "  " +
            "<div class=\"nav-item\" nav-gourp-id='{id}'>\n" +
            "    <div class=\"firLevel \">\n" +
            "        <i class=\"stretch-btn\"></i>\n" +
            "        <div class=\"firLevel-title\">\n" +
            "            <input type=\"text\" value=\"\" placeholder='"+lang.maxCharacters+"' class=\"titleVal\">\n" +
            "        </div>\n" +
            "        <div class=\"valiate-error-tips\">\n" +
            "         "+lang.maxCharacters+"" +
            "        </div>\n" +
            "        <div class=\"firLevel-edit\">\n" +
            "            <div class=\"edit-link\"></div>\n" +
            "            <div class=\"edit-del\"></div>\n" +
            "            <div class=\"edit-drag navNodeGroupDrag\"></div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <ul class=\"secLevel-list\"></ul>\n" +
            "</div>",

        text: "",
        id: "",
        initProps: function ($super, cfg) {
            $super(cfg);
            var self = this;
            this.parent = cfg.parent;
            this.nodeList = [];
            //初始化1
            this.id = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.element = $(this.temp.replace("{id}", this.id));
            this.$firLevel = this.element.find(".firLevel");
            this.$secLevel = this.element.find(".secLevel-list")
            this.bindEvent();
            this.buildSort();
        },
        /**
         * 事件绑定
         */
        bindEvent: function () {
            var self = this;
            //标题失焦阻止
            this.$firLevel.find(".titleVal").on("blur", function (event) {
                var item = $(this);
                if (!item.val()) {
                    setTimeout(function () {
                        self.validate();
                    }, 500);
                    event.stopPropagation();
                }
            })
            //标题值改变事件
            this.$firLevel.find(".titleVal").on("change", function () {
                //向上传递
                self.text = $(this).val();
                self.validate();
                self.parent.changeGroup(self);
            })
            //群组切换
            this.element.on("click", function () {
                self.parent.selectGroup(self);
            })
            //添加自定义链接
            this.element.find(".firLevel-edit .edit-link").on("click", function () {
                self.newLinkNode();
            })
            //添加删除事件
            this.element.find(".firLevel-edit .edit-del").on("click", function () {
                self.destroy();
            })
            //收起事件
            this.element.find(".stretch-btn").on("click", function () {
                $(this).toggleClass('stretch');
                if ($(this).hasClass("stretch")) {
                    self.$secLevel.hide();
                } else {
                    self.$secLevel.show();
                }
            })
            //全部收起事件
            topic.subscribe("NavGroup.expend.all", function (cfg) {
                if (cfg.expend) {
                    self.$secLevel.hide();
                    self.element.find(".stretch-btn").addClass('stretch');
                } else {
                    self.$secLevel.show();
                    self.element.find(".stretch-btn").removeClass('stretch');
                }
            });

        },
        // hasChild 表示该一级导航存在二级导航
        // ,stretch  表示该一级导航下的二级导航处于收起展开状态
        // ,active   表示该导航正处于选中状态
        hasChild: function () {
            this.$firLevel.addClass("hasChild");
        },
        noChild: function () {
            this.$firLevel.removeClass("hasChild");
        },
        active: function () {
            this.$firLevel.addClass("active");
        },
        negative: function () {
            this.$firLevel.removeClass("active");
        },
        newNode: function (dom) {
            var self = this;
            var nn = new NavNode({
                type: "dom",
                dom: dom,
                parent: self
            });

            this.addNode(nn)
        },
        newLinkNode: function () {
            var self = this;
            var nn = new NavNode({
                type: "link",
                parent: self
            });
            nn.editLink();
            this.addNode(nn)
        },
        addNode: function (n) {
            this.nodeList.push(n);
            this.$secLevel.append(n.element)
            if (this.nodeList.length > 0) {
                this.hasChild();
            }
            //向上传递
            this.parent.changeGroup(this);
        },
        removeNode: function (n) {
            for (var i = 0; i < this.nodeList.length; i++) {
                if (this.nodeList[i] === n) {
                    this.nodeList.splice(i, 1);
                    break;
                }
            }
            if (this.nodeList.length === 0) {
                this.noChild();
            }
            //向上传递
            this.parent.changeGroup(this);
        },
        validate: function () {
            var t = this.text || "";
            var len = 0;
            for (var i = 0; i < t.length; i++) {
                var length = t.charCodeAt(i);
                if (length >= 0 && length <= 128) {
                    len += 1;
                } else {
                    len += 3;
                }
            }
            if (len == 0) {
                this.$firLevel.addClass("valiate-error");
                this.$firLevel.find(".valiate-error-tips").html(lang.fillNavTtile);
                this.$firLevel.find(".titleVal").focus();
                return false;
            } else if (len > 24) {
                //8个中文字，24个英文字符
                this.$firLevel.addClass("valiate-error");
                this.$firLevel.find(".valiate-error-tips").html(lang.maxCharacters);
                this.$firLevel.find(".titleVal").focus();
                return false;
            }
            this.$firLevel.removeClass("valiate-error");

            //子组件校验
            for (var i = 0; i < this.nodeList.length; i++) {
                var node = this.nodeList[i];
                if (!node.validate()) {
                    return false;
                }
            }
            return true;
        },
        destroy: function ($super, cfg) {
            this.parent.removeGroup(this);
            for (var i = 0; i < this.nodeList.length; i++) {
                var node = this.nodeList[i];
                node.destroy();
            }
            $super(cfg);
        },
        getKeyData: function () {
            return {
                "text": this.text,
                "value": this.id,
                "expand": this.parent.title.isNodeSelected(this),
                "nodeType": "dynamic",
                "children": this.getChildKeyData(),
                "systemDefault": this.systemDefault
            }
        },
        getChildKeyData: function () {
            var keyData = [];
            for (var i = 0; i < this.nodeList.length; i++) {
                var node = this.nodeList[i];
                keyData.push(node.getKeyData());
            }
            return keyData;
        },
        initByStoreData: function (storeData) {
            console.debug("initByStoreData", storeData);
            this.$firLevel.find(".titleVal").val(storeData.text)
            this.expand = storeData.expand
            var children = storeData.children;
            if(children){
                for (var i = 0; i < children.length; i++) {
                    var sd = children[i];
                    var ng = new NavNode({parent: this});
                    ng.initByStoreData(sd)
                    this.addNode(ng)
                }
            }
            if(storeData.systemDefault){
                this.systemDefault = storeData.systemDefault;
            }
            this.parent.title.setNodeSelected(this)
        },
        buildSort: function () {
            var self = this;
            this.sortable = Sortable.create(this.$secLevel[0], {
                sort: true,
                scroll: true,
                group: "navNodeGroup",
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle: ".navNodeDrag",
                draggable: ".navNodeItem",
                onStart: function (evt) {
                },
                onEnd: function (evt) {
                    self.refreshGroup(evt);
                }
            });
        },
        refreshGroup: function (evt) {
            //首先判断是否在组内进行
            var li = this.$secLevel.find(".navNodeItem");
            var self = this;
            var cacheGl = [];

            if (li.length == this.nodeList.length) {
                //组内拖拽
                if (!evt || evt.newIndex == evt.oldIndex) {
                    return;
                }
                //只需要更新自己
                li.each(function (idx, item) {
                    var id = $(this).attr("id").replaceAll("NavNode_", "");
                    for (var i = 0; i < self.nodeList.length; i++) {
                        if (self.nodeList[i].id == id) {
                            cacheGl.push(self.nodeList[i]);
                            break;
                        }
                    }
                })
                self.parent.changeGroup(self);
                this.nodeList = cacheGl;
            } else {
                //组间更新
                //先更新自己，找到被拖拽出去的node moveNode;
                li.each(function (idx, item) {
                    var id = $(this).attr("id").replaceAll("NavNode_", "");
                    for (var i = 0; i < self.nodeList.length; i++) {
                        if (self.nodeList[i].id == id) {
                            cacheGl.push(self.nodeList[i]);
                            break;
                        }
                    }
                })
                var moveNodeId = $(evt.item).attr("id").replaceAll("NavNode_", "");
                var moveNode;
                for (var i = 0; i < self.nodeList.length; i++) {
                    if (self.nodeList[i].id == moveNodeId) {
                        //被移动的元素
                        moveNode = self.nodeList[i];
                        break;
                    }
                }
                if (moveNode) {
                    //先更新自己
                    this.nodeList = cacheGl;
                    self.parent.changeGroup(self);
                    //向上传递，通知其他组进行更新
                    this.parent.refreshGroupChannel(self, moveNode)
                }
            }
        },
        //组间移动各组自身的检查方法
        refreshGroupChannelDeal: function (moveNode) {
            var li = this.$secLevel.find(".navNodeItem");
            if (li.length == this.nodeList.length) {
                return;
            }
            var self = this;
            var cacheGl = [];
            li.each(function (idx, item) {
                var id = $(this).attr("id").replaceAll("NavNode_", "");
                if (moveNode.id == id) {
                    self.nodeList.splice(idx, 0, moveNode);
                    //传递下改变
                    self.parent.changeGroup(self);
                    return;
                }
            })

        }
    })
    var NavNode = base.Container.extend({
        //原节点名
        name: "",
        //显示值
        text: "",
        //实际值
        value: "",
        //类型
        type: "",
        //图标
        icon: "iconfont_nav lui_iconfont_nav_sys_news",
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            this.id = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.parent = cfg.parent;
            if (cfg.type) {
                this.type = cfg.type;
                if (this.type === "dom") {
                    this.buildDom(cfg.dom);
                }
                this.draw();
            }
        },
        buildDom: function (dom) {
            var self = this;
            var domData = dom.target.data;
            this.path = dom.target.path;
            //原列表值
            this.name = domData.text;
            this.text = domData.text;
            this.value = domData.value;
            this.type = domData.type;
        },
        draw: function () {
            var self = this;
            //dom 绘制
            this.element = $("<li class='navNodeItem'/>");
            this.$item = $("<div class=\"secLevel-item \"/>");
            this.$title = $("  <div class=\"secLevel-title\">\n" +
                "                <span class=\"" + this.icon + "\"></span>\n" +
                "                <input placeholder='"+lang.maxCharacters+"' type=\"text\" value=\"" + this.text + "\" class=\"titleVal\">\n" +
                "            </div>" +
                "        <div class=\"valiate-error-tips\">\n" +
                "         "+lang.maxCharacters+"" +
                "        </div>\n");
            //标题改变校验
            this.$title.find(".titleVal").on("change", function () {
                self.text = $(this).val();
                self.validate();
            })
            this.$item.append(this.$title);
            this.$edit;
            if (this.type == "link") {
                this.$edit = $(" <div class=\"secLevel-edit\">\n" +
                    "                 <div class=\"edit-msg-link\"></div>" +
                    "                <div class=\"edit-del\"></div>\n" +
                    "                <div class=\"edit-drag navNodeDrag\"></div>\n" +
                    "            </div>")
                this.$edit.find(".edit-msg-link").on("click", function () {
                    self.editLink();
                })
            } else {
                //路径
                var pathStr = "";
                if (this.path) {
                    for (var i = 0; i < this.path.length; i++) {
                        p = this.path[i];
                        pathStr += p;
                        pathStr += " > ";
                    }
                    pathStr += this.name;
                }
                this.$edit = $(" <div class=\"secLevel-edit\">\n" +
                    "                <div class=\"edit-msg\">\n" +
                    "                    <div class=\"content_route\">\n" +
                    "                        <p>"+lang.contentPath+"</p>\n" +
                    "                        <span>" + pathStr + "</span>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <div class=\"edit-del\"></div>\n" +
                    "                <div class=\"edit-drag navNodeDrag\"></div>\n" +
                    "            </div>")
            }
            //事件绑定
            this.$edit.find(".edit-del").on("click", function () {
                self.destroy();
            })
            //图标事件
            this.$title.find("span").on("click", function () {
                dialog.iframe("/sys/modeling/base/resources/iconfont.jsp?type=module",
                    "选择图标", function (rt) {
                        if (rt) {
                            self.$title.find("span").attr("class", rt)
                            self.icon = rt;
                        }
                    }, {
                        width: 600,
                        height: 540
                    });
            })

            this.$item.append(this.$edit);
            this.element.append(this.$item);
            this.element.attr("id", "NavNode_" + this.id);
            this.validate();
        },
        editLink: function () {
            var self = this;
            dialog.iframe("/sys/modeling/base/profile/nav/v1/editLinkNodeDialog.jsp", lang.exlink, function (rt) {
                if (rt && rt.type && rt.type == "success") {
                    //成功时把数据添加到页面，总体进行保存
                    self.text = rt.data.text;
                    self.value = rt.data.value;
                    self.setValue();
                } else {
                    self.destroy();
                }
            }, {
                width: 550,
                height: 320,
                params: {
                    text: self.text,
                    value: self.value
                }
            });
        },
        setValue: function () {
            this.$title.find(".titleVal").val(this.text);
            this.validate();
        },
        destroy: function ($super, cfg) {
            this.parent.removeNode(this);
            topic.publish("NavNode.destroy", {target: this});
            $super(cfg);
        },
        show: function () {
            this.element.show();
        },
        hide: function () {
            this.element.hide();
        },
        getKeyData: function () {
            return {
                "text": this.$title.find(".titleVal").val(),
                "value": this.value,
                "name": this.name,
                "nodeType": this.type,
                "icon": this.icon,
                "path": this.path
            }
        },
        validate: function () {
            var t = this.text || "";
            var len = 0;
            for (var i = 0; i < t.length; i++) {
                var length = t.charCodeAt(i);
                if (length >= 0 && length <= 128) {
                    len += 1;
                } else {
                    len += 3;
                }
            }
            //8个中文字，
            if (len == 0) {
                this.$item.addClass("valiate-error");
                this.$item.find(".valiate-error-tips").html(lang.fillTtile);
                this.$item.find(".titleVal").focus();
                return false;
            } else if (len > 24) {
                this.$item.addClass("valiate-error");
                this.$item.find(".valiate-error-tips").html(lang.maxCharacters);
                this.$item.find(".titleVal").focus();
                return false;
            }
            this.$item.removeClass("valiate-error");
            return true;
        },
        initByStoreData: function (storeData) {

            var self = this;
            this.path = storeData.path;
            //原列表值
            this.name = storeData.name;
            this.text = storeData.text;
            this.value = storeData.value;
            this.type = storeData.nodeType;
            this.icon = storeData.icon;
            this.draw()
        },

    });
    exports.Nav = Nav;
});
