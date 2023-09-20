/**
 * 弹窗选择控件
 */

define(function(require, exports, module) {
    var modelingLang = require("lang!sys-modeling-base");
	var $ = require('lui/jquery'),
		topic = require('lui/topic'),
        dialog = require('lui/dialog'),
		base = require('lui/base');

	var OperateDialogUnion = base.Component.extend({

		initProps: function($super, cfg) {
			$super(cfg);
			this.container = cfg.container || null;
			this.value = cfg.value || "";
            this.text  = cfg.text || "";
            this.channel = cfg.channel;
		},
		
		draw : function($super, cfg){
			$super(cfg);
			this.element = $("<div class='operation' />").appendTo(this.container);
			this.content = $("<div class='inputselectsgl' />").appendTo(this.element);
			this.buildIdAndNameElement();
			var self = this;
			this.content.click(function(){
                self.operationDialog();
            });
		},

        //业务操作弹框
        operationDialog: function(){
		    console.log(" this.channel", this.channel);
            var index = this.parent.element.attr("index");
            var isMobile = this.channel ==="mobile"?true : false;
            //业务操作弹框回调
            var self = this;
            var action = function(rtnData){
                var listOperationIdLasts = $("[name='operationIds']").val() || "";
                var listOperationNameLasts = $("[name='operationNames']").val() || "";
                if(isMobile){
                     listOperationIdLasts = $("[name='mobileOperationIds']").val() || "";
                     listOperationNameLasts = $("[name='mobileOperationNames']").val() || "";
                }
                var oprIdArr = listOperationIdLasts.split(";");
                var oprNameArr = listOperationNameLasts.split(";");
                var selectedOprLastIndex = oprIdArr.length - 1;
                var fdId = rtnData[0].fdId;
                var fdName = rtnData[0].fdName;
                if(fdId && listOperationIdLasts.indexOf(fdId) == -1 ){
                    if(index > selectedOprLastIndex){
                        if(listOperationIdLasts.lastIndexOf(";") != listOperationIdLasts.length-1){
                            listOperationIdLasts += ";" + fdId + ";";
                            listOperationNameLasts += ";" + fdName + ";";
                        }else{
                            listOperationIdLasts += fdId + ";";
                            listOperationNameLasts += fdName + ";";
                        }
                    }else{
                        oprIdArr[index] = fdId;
                        oprNameArr[index] = fdName;
                        listOperationIdLasts = oprIdArr.join(";");
                        listOperationNameLasts = oprNameArr.join(";");
                    }
                }
                if(isMobile){
                    $("[name='mobileOperationIds']").val(listOperationIdLasts);
                    $("[name='mobileOperationNames']").val(listOperationNameLasts);
                }else {
                    $("[name='operationIds']").val(listOperationIdLasts);
                    $("[name='operationNames']").val(listOperationNameLasts);
                }

                //刷新预览
                topic.channel(self.channel).publish("field.change",{dom:self.lastSelect, type:self.ownerType, wgt:self, parent: self.parent, text: fdName});
                topic.publish("preview.refresh", {key: self.channel});
            }
            var exceptValue = $("[name='operationIds']").val() || "";
            if (isMobile){
                exceptValue =$("[name='mobileOperationIds']").val() || "";
            }
            var currentValue = this.idObj.val();
            if(currentValue){
                exceptValue = exceptValue.replace(currentValue+";","");
                exceptValue = exceptValue.replace(currentValue,"");
            }
            this.dialogSelect(false,'sys_modeling_operation_selectListviewOperation','listOperationIds','listOperationNames', action, exceptValue,isMobile);
        },

        /**
         * 业务操作对话框
         *
         * exceptValue 需要排除的值，格式为字符串id;id
         */
        dialogSelect: function(mul, key, idField, nameField, action, exceptValue,isMobile){
            var rowIndex;
            var self = this;
            if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
                var tr=DocListFunc_GetParentByTagName('TR');
                var tb= DocListFunc_GetParentByTagName("TABLE");
                var tbInfo = DocList_TableInfo[tb.id];
                rowIndex=tr.rowIndex-tbInfo.firstIndex;
            }
            var dialogCfg = listviewOption.dialogs[key];
            if(dialogCfg){
                var params='';
                params+='&isMobile=' + isMobile;
                params=encodeURI(params);
                var tempUrl = 'sys/modeling/base/resources/jsp/dialog_select_template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_collection_' + idField + '&exceptValue='+exceptValue;
                if(mul==true){
                    tempUrl += '&mulSelect=true';
                }else{
                    tempUrl += '&mulSelect=false';
                }
                var dialog = new KMSSDialog(mul,true);
                dialog.URL = Com_Parameter.ContextPath + tempUrl;
                var source = dialogCfg.sourceUrl;
                var propKey = '__dialog_collection_' + idField + '_dataSource';
                dialog[propKey] = function(){
                    if(idField.indexOf('*')>-1){
                        var initField=idField.replace('*',rowIndex);
                        return {url:source+params,init:document.getElementsByName(initField)[0].value};
                    }else{
                        return {url:source+params,init:self.idObj.val()};
                    }
                };
                window[propKey] = dialog[propKey];
                propKey =  'dialog_collection_' + idField;
                dialog[propKey] = function(rtnInfo){
                    if(rtnInfo==null) return;
                    var datas = rtnInfo.data;
                    var rtnDatas=[],ids=[],names=[],fdOperationScenarios = [], fdDefTypes=[];
                    for(var i=0;i<datas.length;i++){
                        var rowData = domain.toJSON(datas[i]);
                        rtnDatas.push(rowData);
                        ids.push($.trim(rowData[rtnInfo.idField]));
                        names.push($.trim(rowData[rtnInfo.nameField]));
                        fdOperationScenarios.push($.trim(rowData["fdOperationScenario"]));
                        fdDefTypes.push($.trim(rowData["fdDefType"]));
                    }
                    if(idField.indexOf('*')>-1){
                        //明细表
                        $form(idField,idField.replace("*",rowIndex)).val(ids.join(";"));
                        $form(nameField,idField.replace("*",rowIndex)).val(names.join(";"));
                    }else{
                        //主表
                        self.idObj.val(ids.join(";"));
                        self.nameObj.val(names.join(";"));
                        self.tipsShow(fdDefTypes.join(";"),fdOperationScenarios.join(";"));
                    }
                    if(action){
                        action(rtnDatas);
                    }
                };
                domain.register(propKey,dialog[propKey]);
                dialog.Show(800,550);
            }
        },
        //显示开启机制提示信息
        tipsShow : function (fdDefType,fdOperationScenario){
            //fdDefType:8-打印、 9-导入、 10-批量打印、  11-归档  支付场景: fdDefType = null fdOperationScenario-1
            if("8"===fdDefType|| "9" ===fdDefType|| "10"===fdDefType ||"11"===fdDefType ||
                ((fdDefType == null || fdDefType == undefined || fdDefType == "") && "1" === fdOperationScenario)){
                //如果存在旧的tips，则先删除掉
                if(this.tips){
                    this.tips.remove();
                }
                var html = modelingLang['modeling.model.mechanism.tips'];
                this.tips = $("<div class='operateTips' />").appendTo(this.element);
                this.tips.html(html);
            } else {
                //如果存在旧的tips，则删除掉
                if(this.tips){
                    this.tips.remove();
                }
            }
        },

        buildIdAndNameElement : function(){
            this.idObj = $("<input type='hidden' name='operationId' value='' />").appendTo(this.content);
            var $nameWrapper = $("<div type='text' class='input' />").appendTo(this.content);
            this.nameObj = $("<input type='text' readOnly name='operationName' />").appendTo($nameWrapper);
            if (this.required) {
                this.nameObj.attr("validate", "required");
            }
            $("<div class='selectitem' />").appendTo(this.content);
        },

		getFieldValue : function(){
			var fieldValue = [];
			this.element.find("[name='operationId']").each(function(index, dom){
				fieldValue.push($(dom).val());
			});

			return fieldValue.join("|");
		},

		getFieldText : function(){
			var fieldText = [];
            this.element.find("[name='operationName']").each(function(index, dom){
                fieldText.push($(dom).val());
            });
			return fieldText.join("|");
		}

	})
	module.exports = OperateDialogUnion;
})