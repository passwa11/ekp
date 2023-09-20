Com_AddEventListener(window,'load',function(){
    seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lang!sys-ui'], function($, dialog, dialogCommon,strutil,lang){

        window.dialogSelectBaseData=function(mul, dialogLinkId, idField, nameField,modelName,sourceUrl,targetWin,tobj){
        	var _idFields = idField.split(".");
        	if (_idFields.length > 1){
        		// 列表中可以更换顺序导致填充错误
        		var hideIdInputs = $(tobj).find("input");
        		if (hideIdInputs.length > 0){
        			var hideIdInput = hideIdInputs[0];
        			var _id_name = $(hideIdInput).attr('name');
        			if (_id_name != idField){
        				// 顺序变化了
        				idField = _id_name;
        				var _id_names = _id_name.split(".");
        				var _oldIndex = "." + _idFields[1] + ".";
        				var _newIndex = "." + _id_names[1] + ".";
        				nameField = nameField.replace(_oldIndex, _newIndex);
        			}
        		}
        	}
            targetWin = targetWin||window;
            if(idField.indexOf('[0]')>-1 && window.DocListFunc_GetParentByTagName){
                //明细表
                dialogSelectBaseDateForDetail(mul, dialogLinkId, idField, nameField,modelName,sourceUrl);
            }else{
                var params='';
                var inputs=getDialogInputs(idField);
                if(inputs){
                    for(var i=0;i<inputs.length;i++){
                        var argu = inputs[i];
                        var modelVal=$form(argu["value"]).val();
                        if(modelVal==null||modelVal==''){
                            if(argu["required"]=="true"){
                                var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
                                alert(errorInfo);
                                return;
                            }
                            params+='&'+argu["key"]+'='+formInitData[argu["value"]];
                        }else{
                            params+='&'+argu["key"]+'='+modelVal;
                        }
                    }
                }
                dialogSelect(modelName,
                    mul,sourceUrl, null, idField, nameField,null,function(data){
                        var outputs=getDialogOutputs(dialogLinkId);
                        if(outputs){
                            if(data.length==1){
                                for(var i=0;i<outputs.length;i++){
                                    var output=outputs[i];
                                    $form(output["value"]).val(data[0][output["key"]]);
                                }
                            }
                        }
                    });

            }
        };

        function rtfield(idField, nameField) {
            var idObj, nameObj;
            if (idField != null) {
                if (typeof (idField) == "string")
                    idObj = document.getElementsByName(idField)[0];
                else
                    idObj = idField;
            }
            if (nameField != null) {
                if (typeof (nameField) == "string")
                    nameObj = document.getElementsByName(nameField)[0];
                else
                    nameObj = nameField;
            }
            return {
                idObj : idObj,
                nameObj : nameObj
            }
        };
        function dialogSelect (modelName, mulSelect, source, params, idField, nameField, winTitle, action){
            var buttons = [ {
                name : lang['ui.dialog.button.ok'],
                value : true,
                focus : true,
                fn : function(value, dialog) {
                    var iframe = dialog.element.find('iframe').get(0);
                    if(!iframe.contentWindow._getSelectedData){
                        return;
                    }
                    var rtnInfo = iframe.contentWindow._getSelectedData();
                    if(rtnInfo==null) {
                        return;
                    }
                    var datas = rtnInfo.data;
                    var rtnDatas=[];
                    var ids = [];
                    var names=[];
                    for(var i=0;i<datas.length;i++){
                        var rowData = domain.toJSON(datas[i]);
                        rtnDatas.push(rowData);
                        ids.push($.trim(rowData[rtnInfo.idField]));
                        names.push($.trim(rowData[rtnInfo.nameField]));
                    }
                    var fields = rtfield(idField, nameField);
                    if(fields.idObj){
                        fields.idObj.value = ids.join(";");
                        $(fields.idObj).trigger('change');
                    }
                    if(fields.nameObj){
                        fields.nameObj.value = names.join(";");
                        $(fields.nameObj).trigger('change');
                        fields.nameObj.focus();
                    }
                    if(action){
                        action(rtnDatas);
                    }
                    dialog.hide(value);
                }
            } ];
            buttons.push({
            	name : lang['ui.dialog.button.cancelSelect'],
                value : false,
                styleClass : 'lui_toolbar_btn_gray',
            	fn : function(value, dialog) {
            		var iframe = dialog.element.find('iframe').get(0);
            		if(!iframe.contentWindow._getSelectedData){
            			return;
            		}
            		iframe.contentWindow._cancelSelect();
            	}
            });
            buttons.push({
                name : lang['ui.dialog.button.cancel'],
                value : false,
                styleClass : 'lui_toolbar_btn_gray',
                fn : function(value, dialog) {
                    dialog.hide(value);
                }
            });
            var tempUrl = '/sys/dynpage/resource/base-data-dialog.jsp?modelName=' + modelName + '&_key=dialog_' + idField;
            if(mulSelect==true){
                tempUrl += '&mulSelect=true';
            }else{
                tempUrl += '&mulSelect=false';
            }
            var dialogObj = dialog.build({
                config:{
                    width: 800,
                    height: 500,
                    lock: true,
                    cache: false,
                    title : winTitle?winTitle:lang['ui.vars.select'],
                    content : {
                        id : idField + '_dialog_div',
                        scroll : false,
                        type : "iframe",
                        url : tempUrl,
                        params:null,
                        buttons:buttons
                    }
                }
            });
            domain.register('dialog_' + idField,function(){
                buttons[0].fn(null,dialogObj);
            });
            window['__dialog_' + idField + '_dataSource'] = function(){
                if(idField==null){
                    return strutil.variableResolver(source ,params);
                }else{
                    return {url:strutil.variableResolver(source ,params),init:document.getElementsByName(idField)[0].value};
                }
                //return strutil.variableResolver(source ,params);
            }
            dialogObj.show();
        };

        function dialogSelectBaseDateForDetail(mul, dialogLinkId, idField, nameField,modelName,sourceUrl){
            var tr=DocListFunc_GetParentByTagName('TR');
            var tb= DocListFunc_GetParentByTagName("TABLE");
            var tbInfo = DocList_TableInfo[tb.id];
            var refIdField=idField.replace("*",tr.rowIndex-tbInfo.firstIndex);
            var refNameField=nameField.replace("*",tr.rowIndex-tbInfo.firstIndex);

            dialogSelect(modelName,
                mul,sourceUrl, null, refIdField, refNameField,null,function(data){
                    var outputs=getDialogOutputs(dialogLinkId);
                    if(outputs){
                        if(data.length==1){
                            for(var i=0;i<outputs.length;i++){
                                var output=outputs[i];
                                $form(output["value"],refIdField).val(data[0][output["key"]]);
                            }
                        }
                    }
                });

        }

        function getDialogInputs(idField){
            if(typeof(formOption) == "undefined"){
                return null;
            }
            var dialogLinks=formOption.dialogLinks;
            if(dialogLinks==null||dialogLinks.length==0){
                return null;
            }
            for(var i=0;i<dialogLinks.length;i++){
                var dialogLink=dialogLinks[i];
                if(idField==dialogLink.idField){
                    return dialogLink.inputs;
                }
            }
            return null;
        };

        function getDialogOutputs(idField){
            if(typeof(formOption) == "undefined"){
                return null;
            }
            var dialogLinks=formOption.dialogLinks;
            if(dialogLinks==null||dialogLinks.length==0){
                return null;
            }
            for(var i=0;i<dialogLinks.length;i++){
                var dialogLink=dialogLinks[i];
                if(idField==dialogLink.idField){
                    return dialogLink.outputs;
                }
            }
            return null;
        };


        //对话框联动清空值
        bindDialogLink();
        function bindDialogLink(){
            if(typeof(formOption) == "undefined"||null==formOption){
                return null;
            }
            var dialogLinks=formOption.dialogLinks;
            if(dialogLinks==null||dialogLinks.length==0){
                return null;
            }
            for(var i=0;i<dialogLinks.length;i++){
                var sourceFields=[];
                var targetFields=[];
                var dialogLink=dialogLinks[i];
                for(var j=0;j<dialogLink.inputs.length;j++){
                    var input=dialogLink.inputs[j];
                    sourceFields.push(input['value']);
                }
                for(var k=0;k<dialogLink.outputs.length;k++){
                    var output=dialogLink.outputs[k];
                    targetFields.push(output['value']);
                }
                targetFields.push(dialogLink.idField);
                targetFields.push(dialogLink.nameField);
                $form.bind({
                    field:sourceFields,
                    targetFields : targetFields,
                    onValueChange:function(event){
                        var targetFields = event.listener.targetFields;
                        for(var i=0;i<targetFields.length;i++){
                            $form(targetFields[i], event.field).val('');
                        }
                    }
                });
            }
        }

        function getDetailTable(field){
            var index = field.indexOf('[');
            if(index==-1){
                return null;
            }
            return field.substring(0, index);
        }
        var detailTableAttrLinks = {};
        bindAttrLinks();
        function bindAttrLinks(){
            if(typeof(formOption) == "undefined"||formOption.attrLinks==null || formOption.attrLinks.length==0){
                return;
            }
            for(var i=0;i<formOption.attrLinks.length;i++){
                var attrLink = formOption.attrLinks[i];
                var conditions = attrLink.conditions;
                var inputs = [];
                var detailTables = [];
                for(var j=0; j<conditions.length; j++){
                    var condition = conditions[j];
                    if(condition.inputs){
                        for(var k=0; k<condition.inputs.length; k++){
                            if($.inArray(condition.inputs[k], inputs)==-1){
                                inputs.push(condition.inputs[k]);
                                var detailTable = getDetailTable(condition.inputs[k]);
                                if(detailTable && $.inArray(detailTable, detailTables)==-1){
                                    detailTables.push(detailTable);
                                }
                            }
                        }
                    }
                }
                attrLink.inputs = inputs;
                // 明细表事件
                for(var j=0; j<detailTables.length; j++){
                    var detailTableAttrLink = detailTableAttrLinks['TABLE_DocList_'+detailTables[j]];
                    if(detailTableAttrLink==null){
                        detailTableAttrLink = detailTableAttrLinks['TABLE_DocList_'+detailTables[j]] = [];
                    }
                    detailTableAttrLink.push(attrLink);
                }
                // 表单事件
                $form.bind({
                    field : inputs,
                    attrLink : attrLink,
                    onValueChange:function(event){
                        var eventDetail = getDetailTable(event.field);
                        if(eventDetail){
                            var detailTable = getDetailTable(event.listener.attrLink.outputs[0]);
                            if(eventDetail==detailTable){
                                eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
                            }else{
                                eventDetail = null;
                            }
                        }
                        fireAttrLink(event.listener.attrLink, eventDetail);
                    }
                });
                // 初次加载
                fireAttrLink(attrLink);
            }
        }
        function fireAttrLink(attrLink, fieldIndex){
            var detailTable = getDetailTable(attrLink.outputs[0]);
            var data = {};
            if(detailTable){
                // 明细表
                if(fieldIndex==null){
                    // 不带下标，分开计算
                    var size = $form(attrLink.outputs[0]).size();
                    for(var i=0; i<size; i++){
                        execCondition(i);
                    }
                }else{
                    // 带下标
                    execCondition(fieldIndex);
                }
            }else{
                // 非明细表
                execCondition();
            }
            function execCondition(fieldIndex){
                for(var i=0; i<attrLink.conditions.length; i++){
                    var condition = attrLink.conditions[i];
                    var result = execLinkValue(data, condition.inputs, condition.value, fieldIndex);
                    if(result==true){
                        for(var j=0; j<attrLink.outputs.length; j++){
                            if(fieldIndex!=null){
                                $form(attrLink.outputs[j].replace('*', fieldIndex)).editLevel(condition.editLevel);
                            }else{
                                $form(attrLink.outputs[j]).editLevel(condition.editLevel);
                            }
                        }
                        return;
                    }
                }
            }
        }

        function execLinkValue(data, inputs, func, fieldIndex){
            if(inputs){
                for(var i=0; i<inputs.length; i++){
                    var input = inputs[i];
                    if(data[input]==null || fieldIndex!=null && input.indexOf('*')>-1){
                        // 加载数据
                        var value = null;
                        if(input.indexOf('*')>-1){
                            if(fieldIndex!=null){
                                value = $form(input.replace('*', fieldIndex)).val();
                            }else{
                                value = $form(input).val();
                            }
                        }else{
                            value = $form(input).val();
                            if(value==null && formInitData!=null){
                                value = formInitData[input];
                            }
                        }
                        value = $form.format(value, formOption.dataType[input]);
                        if($.isArray(value)){
                            for(var j=0; j<value.length; j++){
                                if(value[j]==null){
                                    return null;
                                }
                            }
                        }else{
                            if(value==null){
                                return null;
                            }
                        }
                        data[input] = value;
                    }
                }
            }
            return func(data);
        }

    });
});

