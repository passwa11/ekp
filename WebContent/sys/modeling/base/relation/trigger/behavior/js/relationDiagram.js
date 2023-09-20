/**
 * 关系图对象，用于查询预定义的配置信息
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var RelationDiagram = base.Component.extend({

        // 待完善
        diagram: {
            "where": {
                "String": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "="},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                    {"name": modelingLang['modelingAppListview.enum.contain'], "value": "like"}
                ],
                "fdId": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "="},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                    {"name": modelingLang['modelingAppListview.enum.contain'], "value": "like"}
                ],
                "com.landray.kmss.sys.organization.model.SysOrgPerson|com.landray.kmss.sys.organization.model.SysOrgElement": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "="},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"}
                ],
                "Date|DateTime|Time": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "eq"},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                    {"name": modelingLang['modelingAppListview.enum.less2'], "value": "lt"},
                    {"name": modelingLang['modelingAppListview.enum.lessEqual2'], "value": "le"},
                    {"name": modelingLang['modelingAppListview.enum.more2'], "value": "gt"},
                    {"name": modelingLang['modelingAppListview.enum.moreEqual2'], "value": "ge"}
                    //介于 bt
                ],
                "Number|Double|BigDecimal": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "eq"},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                    {"name": modelingLang['modelingAppListview.enum.less'], "value": "lt"},
                    {"name": modelingLang['modelingAppListview.enum.lessEqual'], "value": "le"},
                    {"name": modelingLang['modelingAppListview.enum.more'], "value": "gt"},
                    {"name": modelingLang['modelingAppListview.enum.moreEqual'], "value": "ge"}
                ],
                "Enum|enum": [
                    {"name": modelingLang['modelingAppListview.enum.equal'], "value": "="},
                    {"name": modelingLang['modelingAppListview.enum.notequal'], "value": "!{notequal}"},
                    {"name": modelingLang['modelingAppListview.enum.contain'], "value": "like"},
                    {"name": modelingLang['modelingAppListview.enum.notContain'], "value": "!{notContain}"}
                ],

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
            if (!path || !item){
                return null;
            }
            if(path.indexOf("[]")>-1){
                path=  path.replace("[]","");
            }
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
