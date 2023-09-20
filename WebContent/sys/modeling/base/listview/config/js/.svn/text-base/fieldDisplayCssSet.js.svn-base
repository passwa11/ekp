/**
 * 显示项查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var dialog = require('lui/dialog');
    var cssSetItem = require("sys/modeling/base/listview/config/js/cssSetItem");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/relation/trigger/behavior/js/relationDiagram");
    
    var ELE_HTML_TEMP="<tr class=\"displayCssSet\">\n" +
    "         <td class=\"displayCss\">\n" +
    "          <table  class=\"displayCssSetTable tb_simple model-edit-view-oper-content-table\" width=\"100%\">\n" +
    "           <tr class=\"diaplayItem\">\n" +
    "            <td class=\"displayItemName\">显示项一</td>\n" +
    "           </tr>\n" +
    "          </table>\n" +
    "         </td>\n" +
    "        </tr>";
    
    var FieldDisplayCssSet = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.jishu=0;
            var $c = $(cfg.contentTemp);
            this.childrenTemp = $c.find(".displaycssTr").html();
            $c.find(".displaycssTr").remove();
            this.titleTemp =$c.html();
            this.operationEle = cfg.operationEle;
            this.fieldKey = cfg.fieldKey;
            this.text = cfg.text;
            this.data = cfg.data;
            this.parent = cfg.parent;
            this.displayType = cfg.displayType;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.channel = cfg.channel || null;
            //全局变量:存放显示项字段的样式
            this.fieldCssSetArray=[];
            this.allField = cfg.allField;
            this.draw();
        },
        draw:function () {
            var $tr = $(ELE_HTML_TEMP);
            $tr.find(".displayItemName").text(this.text);
            this.element = $tr;
            this.operationEle.after(this.element);
        },
        addChild:function (data) {
        	var self = this;
            var cfg = {
            	data:data,
            	childrenTemp:self.childrenTemp,
            	parent:self,
                displayType:self.displayType,
                allField:self.allField,
            }
            var childCssSet = new cssSetItem.CssSetItem(cfg);
            this.fieldCssSetArray.push(childCssSet);
        },
        destroy: function ($super, cfg) {
            $super(cfg);
        },
        deleteWgt: function(wgt){
     	   var collect = [];
            collect = this.fieldCssSetArray;
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    this.jishu -= 1;
                    break;
                }
            }
            if(collect.length == 0){
             	this.parent.fieldCssSet[wgt.data.fieldKey].destroy();
             	this.parent.fieldCssSet[wgt.data.fieldKey] = null;
             }
        }
       
    });

    exports.FieldDisplayCssSet = FieldDisplayCssSet;
});
