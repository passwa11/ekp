/**
 * 移动列表视图
 */
define([
    "dojo/_base/declare",
    'dojo/topic',
    'dojox/mobile/viewRegistry',
    'dojo/dom',"dojo/query","dojo/dom-class",'dojo/dom-style','dojo/dom-construct',"dojo/dom-attr",
], function(declare, topic, viewRegistry,dom,query,domClass,domStyle,domConstruct,domAttr) {

    return declare("sys.modeling.main.resources.js.mobile.listView.statisticsList", null, {

        statisticsInfoList:[],

        startup: function () {
            var self = this;
            this.inherited(arguments);

            topic.subscribe('/mui/navitem/_selected',  function (item, data) {
              if (query("#statistics-content").length >0)  {
                   domConstruct.empty("statistics-content");
                    var index = data.index;
                    for (var i = 0; i < self.statisticsInfoList.length; i++) {
                        if(self.statisticsInfoList[i].index == index){
                            self.buildStatisticsDom(self.statisticsInfoList[i].datas);
                        }
                    }
              }
            });

            topic.subscribe('statistics.info.update', function (index,datas) {
                var b = true;
                for (var i = 0; i < self.statisticsInfoList.length; i++) {
                    if(self.statisticsInfoList[i].index == index){
                        self.statisticsInfoList[i].datas = datas;
                        b = false;
                    }
                }
                if(b){
                    var statisticsInfo = {};
                    statisticsInfo.index = index;
                    statisticsInfo.datas = datas;
                    self.statisticsInfoList.push(statisticsInfo);
                }
               self.buildStatisticsDom(datas);
            });
        },

        buildStatisticsDom: function (datas) {
            var listData = datas;
            var statisticsContent = dom.byId("statistics-content");
            if (listData && listData.length > 0) {
                if (listData[0].staticsInfos ==""){
                    listData[0].staticsInfos = "{}";
                }
                var staticsInfos;
                staticsInfos = JSON.parse(listData[0].staticsInfos);
                if (staticsInfos && staticsInfos.length > 0) {
                    // domStyle.set(statisticsBody,"display", "block")

                    var nodeList = query(".statistics-content-item")
                    if(nodeList && nodeList.length > 0){
                       return;
                    }else{
                        domConstruct.empty("statistics-content");
                        for (var i = 0; i < staticsInfos.length; i++) {
                            var titleValue = staticsInfos[i].value;
                            var showValue = staticsInfos[i].value;
                            var unit = staticsInfos[i].unit;
                            //统计模式是百分比模式下，把单位改成%
                            if (staticsInfos[i].showType && staticsInfos[i].showType === "2"){
                                showValue += "%";
                                unit = "";
                            }
                            if (9999 < Number(showValue) || -999 > Number(showValue)) {
                                showValue = showValue.substr(0, 4) + "+";
                            }
                            var statisticsContentItem = domConstruct.create("div", {
                                className:"statistics-content-item" }, statisticsContent);
                            var statisticsContentValue = domConstruct.create("div", {
                                className:"statistics-content-item-value",
                                title: titleValue,
                                innerHTML:  showValue  }, statisticsContentItem);
                            var statisticsContentUnit = domConstruct.create("span", {
                                className:"statistics-content-item-unit",
                                title: unit,
                                innerHTML:  unit  }, statisticsContentValue);
                            var statisticsContentName = domConstruct.create("div", {
                                className:"statistics-content-item-name",
                                title: staticsInfos[i].name,
                                innerHTML:  staticsInfos[i].name }, statisticsContentItem);
                        }
                    }
                } else {
                    domConstruct.empty("statistics-content");
                }
            }
        },
    });
});