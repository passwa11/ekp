/**
 * 移动列表视图的关系图对象，用于查询预定义的配置信息
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var RelationDiagram = base.Component.extend({

        // 待完善
        diagram: {
            "where": {
                "String": {
                    "operator": [
                        {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
                        {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                        {"name": modelingLang['modelingAppListview.enum.contain'], "value": "!{contain}"}
                    ]
                },
                "enum": {
                    "operator": [
                        {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
                        {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                        {"name": modelingLang['modelingAppListview.enum.contain'], "value": "!{contain}"},
                        {"name": modelingLang['modelingAppListview.enum.notContain'], "value": "!{notContain}"}
                    ]
                },
                "com.landray.kmss.sys.organization.model.SysOrgPerson|com.landray.kmss.sys.organization.model.SysOrgElement":
                    {
                        "operator": [
                            {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
                            {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"}
                        ]
                    },
                "Date|DateTime|Time": {
                    "operator": [
                        {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
                        {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                        {"name": modelingLang['modelingAppListview.enum.less2'], "value": "!{less2}"},
                        {"name": modelingLang['modelingAppListview.enum.more2'], "value": "!{more2}"},
                        {"name": modelingLang['modelingAppListview.enum.lessEqual2'], "value": "!{lessEqual2}"},
                        {"name": modelingLang['modelingAppListview.enum.moreEqual2'], "value": "!{moreEqual2}"}
                    ]
                },
                "Number|Double|BigDecimal": {
                    "operator": [
                        {"name": modelingLang['modelingAppListview.enum.equal'], "value": "!{equal}"},
                        {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                        {"name": modelingLang['modelingAppListview.enum.less'], "value": "!{less}"},
                        {"name": modelingLang['modelingAppListview.enum.lessEqual'], "value": "!{lessEqual}"},
                        {"name": modelingLang['modelingAppListview.enum.more'], "value": "!{more}"},
                        {"name": modelingLang['modelingAppListview.enum.moreEqual'], "value": "!{moreEqual}"}
                    ]
                }
            }
        },
        getDiagram: function (per, key) {
            if (per && this.diagram.hasOwnProperty(per)) {
                return this.getItem(this.diagram[per], key)
            }
            return null;
        },
        get: function (path) {
            var item = this.diagram;
            if (path) {
                var paths = path.split(".");
                for (i = 0; i < paths.length; i++) {
                    item = this.getItem(item, paths[i]);
                }
            }
            return item;
        },

        getItem: function (item, path) {
            for (var key in item) {
                var keys = key.split("|");
                var idx = $.inArray(path, keys)
                if (idx >= 0) {
                    return item[key];
                }
            }
            return null;
        }

    });

    module.exports = new RelationDiagram();
});
