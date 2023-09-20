require(['dojo/ready', "dojo/parser", 'dojo/dom', 'dojo/dom-construct', 'mui/util', 'dijit/registry', 'dojo/topic', 
         'dojo/query', 'dojo/dom-style',"dojo/dom-attr","dojo/dom-class" ,'dojo/NodeList-manipulate', 'dojo/NodeList-traverse','mui/form'], 
		function(ready, parser, dom, domConstruct, util, registry, topic, query,domStyle,domAttr,domClass) {
	ready(function() {
		require([ "mui/form/ajax-form!" + formOption.formName ]);
	});
	var mainView = null;						//主视图
	var lastClick = 0;
	window.expandDetail=function(detailId, curView){
		if(!clickChecked()) return;
		var detailViewName = detailId + "_view";
		var tmpl = dom.byId(detailViewName);
		if(mainView==null && curView!=null){
			mainView = registry.byId(curView);
		}
		mainView.set('validateNext',false);
		if(mainView.domNode.parentNode == tmpl.parentNode){
			mainView.performTransition(detailViewName, 1, "slide");
			tmpl.style.display = 'block';
		}else{
			domConstruct.place(tmpl, mainView.domNode.parentNode, 'last');
			parser.parse(tmpl).then(function(){
				mainView.performTransition(detailViewName, 1, "slide");
				tmpl.style.display = 'block';
				try {
					if(DocList_TableInfo[detailId]==null){
						DocListFunc_Init();
					}
					lastClick = 0;
					addDetailRow(detailId);
				} catch (e) {
				}
			});
		}
	};
	
	window.listMoreInner=function(url){
		location.href = util.formatUrl(url);
	};
	
	window.collapseDetail=function(detailId){
		if(!clickChecked()) return;
		var view = registry.byId(detailId + '_view');
		view.performTransition(mainView.id, -1, "slide");
		mainView.set('validateNext',true);
		if(mainView.validate)
			mainView.validate();
	};
	
	window.submitFormValidate=function(){
		var validorObj=registry.byId('scrollView');
		if(validorObj.validate()){
			Com_Submit(document.forms[0], 'save');
		}
	};
	
	window.addDetailRow = function(detailId){
		if(!clickChecked()) return;
		var newRow = DocList_AddRow(detailId);
		parser.parse(newRow).then(function(){
			var tabInfo = DocList_TableInfo[detailId];
			if(tabInfo['_getcols']== null){
				tabInfo.fieldNames=[];
				tabInfo.fieldFormatNames=[];
				DocListFunc_AddReferFields(tabInfo, newRow, "INPUT");
				DocListFunc_AddReferFields(tabInfo, newRow, "TEXTAREA");
				DocListFunc_AddReferFields(tabInfo, newRow, "SELECT");
				tabInfo['_getcols'] = 1;
			}
			fixDetailNo(detailId);
			var view = registry.byId(detailId + '_view');
			if(view.resize)
				view.resize();
			topic.publish('/mui/form/valueChanged',null,{row:newRow});
		});
	};
	
	window.deleteDetailRow = function(detailId, evtDomObj){
		if(!clickChecked()) return;
		var trDom = query(evtDomObj).parents('.detail_wrap_td').parent();
		var rowObj = null;
		if(trDom.length>0){
			rowObj = trDom[0];
		}
		if(rowObj == null){
			return
		}
		query('*[widgetid]',rowObj).forEach(function(widgetDom,idx){
			var widget = registry.byNode(widgetDom);
			if(widget && widget.destroy){
				widget.destroy();
			}
		});
		var optTB = DocListFunc_GetParentByTagName("TABLE", rowObj); 
		var rowIndex = Com_ArrayGetIndex(optTB.rows, rowObj);
		var tbInfo = DocList_TableInfo[optTB.id];
		DocList_DeleteRow_ClearLast(rowObj);
		for(var i= rowIndex; i<tbInfo.lastIndex; i++){
			var row = tbInfo.DOMElement.rows[i];
			query('*[widgetid]',row).forEach(function(widgetDom){
				var widget = registry.byNode(widgetDom);
				if(widget.name){
					widget.name = repalceIndexInfo(widget.name, i - tbInfo.firstIndex);
				}else if(widget.idField){
					widget.idField = repalceIndexInfo(widget.idField, i - tbInfo.firstIndex);
					widget.nameField = repalceIndexInfo(widget.nameField, i - tbInfo.firstIndex);
				}
			});
		}
		fixDetailNo(detailId);
		var view = registry.byId(detailId + '_view');
		if(view && view.resize)
			view.resize();
		topic.publish('/mui/form/valueChanged');
	};
	
	function repalceIndexInfo(fieldName,index){
		fieldName = fieldName.replace(/\[\d+\]/g, "[!{index}]");
		fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
		fieldName = fieldName.replace(/!\{index\}/g, index);
		return fieldName;
	}
	function clickChecked(){
		var now = new Date().getTime()
		if(now-lastClick>800){
			lastClick = now
			return true;
		}
		return false;
	}
	function fixDetailNo(detailId) {
		query('#' + detailId+' .muiDetailTableNo').forEach(function(domObj,i) {
			query("span",domObj).text(lang['the'] + (i + 1) + lang['row'])
		});
	}
	
	window.getSource = function(key){
		var context = formOption.dialogs[key];
		if(context){
			var callback = function(){
				if(new RegExp(/\[\d\]/).test(this.idField)){
					return getSourceForDetail(this.idField,context);
				}else{
					var params='';
	    			var inputs=getDialogInputs(this.idField);
	    			if(inputs){
	    				for(var i=0;i<inputs.length;i++){
	    					var argu = inputs[i];
	    					var modelVal=$form(argu["value"]).val();
	    					if(modelVal==null||modelVal==''){
	    						if(argu["required"]=="true"){
	    							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
	    							alert(errorInfo);
	    							return null;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
	    				}
	    			}
	    			params=encodeURI(params);
					return util.setUrlParameterMap(context.sourceUrl+params,{});
				}
			}
			return callback;
		}else{
			return null;
		}
	};
	
	window.afterDialogSel=function(event){
		if(new RegExp(/\[\d\]/).test(this.idField)){
			//明细表
			var outputs=getDialogOutputs(this.idField.replace(/\[\d\]/,'[*]'));
			if(outputs){
				if(event.data.length==1){
					for(var i=0;i<outputs.length;i++){
						var output=outputs[i];
						$form(output["value"],this.idField).val(event.data[0][output["key"]]);
					}
				}
			}
		}else{
			var outputs=getDialogOutputs(this.idField);
			if(outputs){
				if(event.data.length==1){
					for(var i=0;i<outputs.length;i++){
						var output=outputs[i];
						$form(output["value"]).val(event.data[0][output["key"]]);
					}
				}
			}
		}
	};
	
	function getDialogOutputs(idField){
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
	
	function getSourceForDetail(idField,context){
		var params='';
		var inputs=getDialogInputs(idField.replace(/\[\d\]/,'[*]'));
		if(inputs){
			for(var i=0;i<inputs.length;i++){
				var argu = inputs[i];
				if(argu["value"].indexOf('*')>-1){
					//入参来自明细表
					var modelVal=$form(argu["value"],idField).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
							alert(errorInfo);
							return null;
						}
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}else{
					//入参来自主表
					var modelVal=$form(argu["value"]).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
							alert(errorInfo);
							return null;
						}
						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}
				
			}
		}
		params=encodeURI(params);
		return util.setUrlParameterMap(context.sourceUrl+params,{});
	}
	
	
	function getDialogInputs(idField){
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
	
	//对话框联动清空值
	bindDialogLink();
	function bindDialogLink(){
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
	
		function startsWith(value, prefix) {
			return value.slice(0, prefix.length) === prefix;
		}
	
	// 函数注册
	formOption.fn = {
		sum : function(data, detailTable, func){
			var size = formOption.fn.count(data, detailTable);
			var result = 0;
			for(var i=0; i<size; i++){
				result += func(data, i);
			}
			return result;
		},
		avg : function(data, detailTable, func){
			var size = formOption.fn.count(data, detailTable);
			if(size==0){
				return 0;
			}
			return formOption.fn.sum(data, detailTable, func)*1.0/size;
		},
		count : function(data, detailTable){
			for(var o in data){
				var _detailTable = getDetailTable(o);
				if(_detailTable==detailTable && $.isArray(data[o])){
					return data[o].length;
				}
			}
			return 0;
		},
		dateDiffMin:function(beginTime,endTime){
			return Math.round((endTime.getTime()-beginTime.getTime())/1000/60);
		},
		dateDiffDay:function(beginTime,endTime){
			return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60/24);
		},
		dateDiffYear:function(beginTime,endTime){
			return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60/24/365);
		},
		dateDiffHour:function(beginTime,endTime){
			return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60);
		},
		dateMerge:function(date,time){
			return new Date(date.getTime()+time.getTime());
		},
		dateAddDay:function(date,add){
			return new Date(date.getTime()+add*24*60*60*1000);
		},
		dateAddMin:function(date,add){
			return new Date(date.getTime()+add*60*1000);
		},
		emailCheck:function(email){
			if (email == "") {
					return true;
				}
			return /^([a-z0-9A-Z]+(-|\.|_)*)+[a-z0-9A-Z]@([a-z0-9A-Z-]+\.)+[a-zA-Z]{2,}$/.test(email);
		},
		phoneCheck:function(phone){
			if (phone == "") {
					return true;
				}
				// 国内手机号可以有+86，但是后面必须是11位数字
				// 国际手机号必须要以+区号开头，后面可以是6~11位数据
				if(startsWith(phone, "+")) {
					if(startsWith(phone, "+86")) {
						return /^(\+86)(\d{11})$/.test(phone);
					} else {
						return /^(\+\d{1,5})(\d{6,11})$/.test(phone);
					}
				} else {
					// 没有带+号开头，默认是国内手机号
					return /^[1][0-9]{10}$/.test(phone);
				}
		},
		abs:function(x){
			return Math.abs(x);
		},
		max:function(data){
			return Math.max.apply(null,data);
		},
		min:function(data){
			return Math.min.apply(null,data);
		},
		ceil:function(x){
			return Math.ceil(x);
		},
		floor:function(x){
			return Math.floor(x);
		},
		round:function(x,n){
			return Math.round(x * Math.pow(10, n)) / Math.pow(10, n) ;
		},
		systime:function(){
			return new Date();
		},
		year:function(x){
			return x.getFullYear();
		},
		month:function(x){
			return x.getMonth()+1;
		},
		date:function(x){
			return x.getDate();
		},
		hour:function(x){
			return x.getHours();
		},
		minute:function(x){
			return x.getMinutes();
		},
		second:function(x){
			return x.getSeconds();
		},
		lower:function(x){
			return x.toLowerCase();
		},
		upper:function(x){
			return x.toUpperCase();
		},
		trim:function(x){
			return x.replace(/^\s+|\s+$/gm,'');
		},
		len:function(x){
			return x.length;
		},
		startWith:function(x,ex){
			var reg=new RegExp("^"+ex);     
			return reg.test(x); 
		},
		endWith:function(x,ex){
			var reg=new RegExp(ex+"$");     
			return reg.test(x); 
		},
		contains:function(x,ex){
			return x.indexOf(ex)!=-1;
		},
		replace:function(x,old,newStr){
			return x.replace(old,newStr);
		},
		split:function(x,ex){
			return x.split(ex);
		},
		isEmpty:function(x){
			if(typeof x == "undefined" || x == null || x == ""){
		        return true;
		    }else{
		        return false;
		    }
		},
			uniqueCheckDetail:function(url,property,tableId,data){
				var	table="TABLE_DocList_"+tableId;
				var trs=window.parent.document.getElementById(table).getElementsByClassName("docListTr");
				var num=0;
				var fdIds=[];
				for (var i = 0; i < trs.length; i++) {
					var inputs=$("input[name='"+tableId+"["+i+"]."+property+"']")[0];
					if(inputs.value==data){
						num=num+1;
						fdIds.push($("input[name='"+tableId+"["+i+"]."+"fdId']")[0].value);
					}
				}
				if(num>1){
					return false;
				}
				
				if (data == "") {
						return true;
					}
					var flag=false;
					 $.ajax({     
					     type:"post",   
					     url:url,     
					     data:{property:property,data:data.replace(/(^\s*)|(\s*$)/g, "")},
					     async:false,
					     success:function(data){
					    	 if(data =='null'){
					    		 flag = true;
					    	 }else{
					    		  for(var i = 0; i < fdIds.length; i++){
					    		        if(data == fdIds[i]){
					    		        	flag = true;
					    		        }
					    		    }
					    	 }
						}    
				 });
					 return flag;
			},
		uniqueCheck:function(url,property,data){
			if (data == "") {
					return true;
				}
			var fdId = Com_GetUrlParameter(location.href, "fdId");	
			var flag=true;
			 $.ajax({     
			     type:"post",   
			     url:url,     
			     data:{property:property,data:data.replace(/(^\s*)|(\s*$)/g, ""),fdId:fdId},
			     async:false,
			     success:function(data){
			    	 if(data =='false'){
			    		 flag = false;
			    	 }
				}    
		 });
			 return flag;
		}
	};
	
	function getDetailTable(field){
		var index = field.indexOf('[');
		if(index==-1){
			return null;
		}
		return field.substring(0, index);
	}
	// key:detailTableId, value:[attrLink]
	var detailTableAttrLinks = {};
	bindAttrLinks();
	function bindAttrLinks(){
		if(formOption.attrLinks==null || formOption.attrLinks.length==0){
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
	
	
	var detailTableValueLinks = {};
	bindValueLinks();
	function bindValueLinks(){
		if(formOption.valueLinks==null || formOption.valueLinks.length==0){
			return;
		}
		for(var i=0;i<formOption.valueLinks.length;i++){
			var valueLink = formOption.valueLinks[i];
			var inputs = valueLink.inputs;
			var detailTables = [];
			if(inputs){
				for(var j=0; j<inputs.length; j++){
					var input = inputs[j];
					var detailTable = getDetailTable(input);
					if(detailTable && $.inArray(detailTable, detailTables)==-1){
						detailTables.push(detailTable);
					}
				}
			}
			// 明细表事件
			for(var j=0; j<detailTables.length; j++){
				var detailTableValueLink = detailTableValueLinks['TABLE_DocList_'+detailTables[j]];
				if(detailTableValueLink==null){
					detailTableValueLink = detailTableValueLinks['TABLE_DocList_'+detailTables[j]] = [];
				}
				detailTableValueLink.push(valueLink);
			}
			// 表单事件
			$form.bind({
				field : inputs,
				valueLink : valueLink,
				onValueChange:function(event){
					var eventDetail = getDetailTable(event.field);
					if(eventDetail){
						var detailTable = getDetailTable(event.listener.valueLink.output);
						if(eventDetail==detailTable){
							eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
						}else{
							eventDetail = null;
						}
					}
					fireValueLink(event.listener.valueLink, eventDetail);
				}
			});
		}
	}
	function fireValueLink(valueLink, fieldIndex){
		var detailTable = getDetailTable(valueLink.output);
		var data = {};
		if(detailTable){
			// 明细表
			if(fieldIndex==null){
				// 不带下标，分开计算
				var size = $form(valueLink.output).size();
				for(var i=0; i<size; i++){
					execValue(i);
				}
			}else{
				// 带下标
				execValue(fieldIndex);
			}
		}else{
			// 非明细表
			execValue();
		}
		function execValue(fieldIndex){
			var result = execLinkValue(data, valueLink.inputs, valueLink.value, fieldIndex);
			if(result!=null){
				result=$form.str(result,formOption.dataType[valueLink.output]);
				if(fieldIndex!=null){
					$form(valueLink.output.replace('*', fieldIndex)).val(result);
				}else{
					$form(valueLink.output).val(result);
				}
			}
		}
	}
	
	var detailTableOptionLinks={};
	ready(function(){
		bindOptionLinks();
	});
	function bindOptionLinks(){
		if(formOption.optionLinks==null||formOption.optionLinks.length==0){
			return;
		}
		for(var i=0;i<formOption.optionLinks.length;i++){
			var optionLink = formOption.optionLinks[i];
			var conditions = optionLink.conditions;
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
			optionLink.inputs = inputs;
			// 明细表事件
			for(var j=0; j<detailTables.length; j++){
				var detailTableOptionLink = detailTableOptionLinks['TABLE_DocList_'+detailTables[j]];
				if(detailTableOptionLink==null){
					detailTableOptionLink = detailTableOptionLinks['TABLE_DocList_'+detailTables[j]] = [];
				}
				detailTableOptionLink.push(optionLink);
			}
			// 表单事件
			$form.bind({
				field : inputs,
				optionLink : optionLink,
				onValueChange:function(event){
					var eventDetail = getDetailTable(event.field);
					if(eventDetail){
						var detailTable = getDetailTable(event.listener.optionLink.output);
						if(eventDetail==detailTable){
							eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
						}else{
							eventDetail = null;
						}
					}
					fireOptionLink(event.listener.optionLink, eventDetail);
				}
			});
			// 初次加载
			fireOptionLink(optionLink);
		}
	}
	
	function fireOptionLink(optionLink, fieldIndex){
		var detailTable = getDetailTable(optionLink.output);
		var data = {};
		if(detailTable){
			// 明细表
			if(fieldIndex==null){
				// 不带下标，分开计算
				var size = $form(optionLink.output).size();
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
			for(var i=0; i<optionLink.conditions.length; i++){
				var condition = optionLink.conditions[i];
				var result = execLinkValue(data, condition.inputs, condition.value, fieldIndex);
				if(result==true){
					var optionVal=condition.optionValue;
					if(fieldIndex!=null){
						if('radio'==optionLink.displayType){
							$('input[name="'+optionLink.output.replace('*', fieldIndex).replace('.','_')+'_group"]').parent().hide();
							var oldVal=$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"][type="hidden"]').val();
							$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"][type="hidden"]').val(null);
							$('input[name="'+optionLink.output.replace('*', fieldIndex).replace('.','_')+'_group"]').prev().removeClass('active');
							for(var j=0;j<optionVal.length;j++){
								if(optionVal[j].value==oldVal){
									$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"][type="hidden"]').val(oldVal);
									$('input[name="'+optionLink.output.replace('*', fieldIndex).replace('.','_')+'_group"][value="'+optionVal[j].value+'"]').prev().addClass('active');
								}
								$('input[name="'+optionLink.output.replace('*', fieldIndex).replace('.','_')+'_group"][value="'+optionVal[j].value+'"]').parent().show();
							}
						}else if('select'==optionLink.displayType){
							var selectObj=registry.byId(optionLink.output.replace('*', fieldIndex));
							var oldVal=selectObj.get('value');
							var flag=false;
							selectObj.set('value','');
							var newStoreArr=[];
							newStoreArr.push({'text':'==请选择==','value':''});
							for(var j=0;j<optionVal.length;j++){
								newStoreArr.push({'text':optionVal[j].text,'value':optionVal[j].value});
								if(optionVal[j].value==oldVal){
									flag=true;
								}
							}
							selectObj.set('values',newStoreArr);
							if(flag){
								selectObj.set('value',oldVal);
							}
						}
						
					}else{
						if('radio'==optionLink.displayType){
							$('input[name="'+optionLink.output+'_group"]').parent().hide();
							var oldVal=$('input[name="'+optionLink.output+'"][type="hidden"]').val();
							$('input[name="'+optionLink.output+'"][type="hidden"]').val(null);
							$('input[name="'+optionLink.output+'_group"]').prev().removeClass('active');
							for(var j=0;j<optionVal.length;j++){
								if(optionVal[j].value==oldVal){
									$('input[name="'+optionLink.output+'"][type="hidden"]').val(oldVal);
									$('input[name="'+optionLink.output+'_group"][value="'+optionVal[j].value+'"]').prev().addClass('active');
								}
								$('input[name="'+optionLink.output+'_group"][value="'+optionVal[j].value+'"]').parent().show();
							}
						}else if('select'==optionLink.displayType){
							var selectObj=registry.byId(optionLink.output);
							var oldVal=selectObj.get('value');
							var flag=false;
							selectObj.set('value','');
							var newStoreArr=[];
							newStoreArr.push({'text':'==请选择==','value':''});
							for(var j=0;j<optionVal.length;j++){
								newStoreArr.push({'text':optionVal[j].text,'value':optionVal[j].value});
								if(optionVal[j].value==oldVal){
									flag=true;
								}
							}
							selectObj.set('values',newStoreArr);
							if(flag){
								selectObj.set('value',oldVal);
							}
						}
					}
					return;
				}
			}
		}
	}
	
	bindDetailTable();
	function bindDetailTable(){
		if(window.DocList_Info==null){
			return;
		}
		for(var i=0; i<DocList_Info.length; i++){
			var tb = document.getElementById(DocList_Info[i]);
			if(tb==null){
				continue;
			}
			$(tb).bind({
				'table-add':function(e, row){
					var tbId = e.target.id;
					var fieldIndex = row.rowIndex - DocList_TableInfo[tbId].firstIndex;
					if(detailTableAttrLinks[tbId]){
						for(var i=0; i<detailTableAttrLinks[tbId].length; i++){
							fireAttrLink(detailTableAttrLinks[tbId][i], fieldIndex);
						}
					}
					if(detailTableValueLinks[tbId]){
						for(var i=0; i<detailTableValueLinks[tbId].length; i++){
							fireValueLink(detailTableValueLinks[tbId][i], fieldIndex);
						}
					}
					if(detailTableOptionLinks[tbId]){
						for(var i=0; i<detailTableOptionLinks[tbId].length; i++){
							fireOptionLink(detailTableOptionLinks[tbId][i], fieldIndex);
						}
					}
				},
				'table-delete':function(e){
					var tbId = e.target.id;
					setTimeout(function(){
						if(detailTableAttrLinks[tbId]){
							for(var i=0; i<detailTableAttrLinks[tbId].length; i++){
								var attrLink = detailTableAttrLinks[tbId][i];
								if(getDetailTable(attrLink.outputs[0])==null){
									fireAttrLink(attrLink);
								}
							}
						}
						if(detailTableValueLinks[tbId]){
							for(var i=0; i<detailTableValueLinks[tbId].length; i++){
								var valueLink = detailTableValueLinks[tbId][i];
								if(getDetailTable(valueLink.output)==null){
									fireValueLink(valueLink);
								}
							}
						}
						if(detailTableOptionLinks[tbId]){
							for(var i=0; i<detailTableOptionLinks[tbId].length; i++){
								var optionLink = detailTableOptionLinks[tbId][i];
								if(getDetailTable(optionLink.output)==null){
									fireOptionLink(valueLink);
								}
							}
						}
					}, 100);
				}
			});
		}
	}
	ready(function(){
		addLinkValidates();
	});
	function addLinkValidates(){
		var linkValidates=formOption.linkValidates;
		if(linkValidates!=null&&linkValidates.length>0){
			var _validation = registry.byId("scrollView")._validation;
			for(var i=0;i<linkValidates.length;i++){
				var iden=linkValidates[i].iden;
				var message=linkValidates[i].message;
				_validation.addValidator(iden,message,function(v,e,o){
					var linkValidate=getLinkValidateByIden(o.iden+'(iden)');
					if(linkValidate==null){
						return true;
					}
					var func=linkValidate.value;
					var inputs=linkValidate.inputs;
					var outputs=linkValidate.outputs;
					var data={};
					var detailTable = getDetailTable(outputs[0]);
					var eventDetail = getDetailTable(e.valueField==null?e.name:e.valueField);
					if(eventDetail){
						if(eventDetail==detailTable){
							eventDetail = e.valueField==null?e.name:e.valueField.substring(eventDetail.length+1,e.valueField==null?e.name:e.valueField.indexOf(']'));
						}else{
							eventDetail = null;
						}
					}
					if(detailTable){
						// 明细表
						if(eventDetail==null){
							// 不带下标，分开计算
							var size = $form(outputs[0]).size();
							for(var i=0; i<size; i++){
								var result=execLinkValue(data,inputs,func,i);
								return result==null?true:result;
							}
						}else{
							// 带下标
							var result=execLinkValue(data,inputs,func,eventDetail);
							return result==null?true:result;
						}
					}else{
						// 非明细表
						var result=execLinkValue(data,inputs,func);
						return result==null?true:result;
					}
				});
			}
		}
	}
	
	function getLinkValidateByIden(iden){
		var linkValidates=formOption.linkValidates;
		if(linkValidates!=null||linkValidates.length>0){
			for(var i=0;i<linkValidates.length;i++){
				if(iden==linkValidates[i].iden){
					return linkValidates[i];
				}
			}
		}
		return null;
	}
	
	window.expandRow = function(domNode){
		var domTable = $(domNode).closest('table')[0];
		var display = domAttr.get(domTable,'data-display'),
			newdisplay = (display == 'none' ? '' : 'none');
		domAttr.set(domTable,'data-display',newdisplay);
		var items = query('tr[data-celltr="true"],tr[statistic-celltr="true"]',domTable);
		for(var i = 0; i < items.length; i++){
			if(newdisplay == 'none'){
				domStyle.set(items[i],'display','none');
			}else{
				domStyle.set(items[i],'display','');
			}
		}
		var opt = query('.muiDetailDisplayOpt',domTable)[0];
		if(newdisplay == 'none'){
			domClass.add(opt,'muiDetailUp');
			domClass.remove(opt,'muiDetailDown');
		}else{
			domClass.add(opt,'muiDetailDown');
			domClass.remove(opt,'muiDetailUp');
		}
	};
	
	window.addRowExpand = function(detailId) {
		event = event || window.event;
		if (event.stopPropagation)
			event.stopPropagation();
		else
			event.cancelBubble = true;
		detail_addRowExpand(detailId);
	};
	window.detail_addRowExpand = function(detailId,callbackFun) {
		var newRow = DocList_AddRow(detailId);
		newRow.dojoClick = true;
		parser.parse(newRow).then(function(){
			var tabInfo = DocList_TableInfo[detailId];
			if(tabInfo['_getcols']== null){
				tabInfo.fieldNames=[];
				tabInfo.fieldFormatNames=[];
				DocListFunc_AddReferFields(tabInfo, newRow, "INPUT");
				DocListFunc_AddReferFields(tabInfo, newRow, "TEXTAREA");
				DocListFunc_AddReferFields(tabInfo, newRow, "SELECT");
				tabInfo['_getcols'] = 1;
			}
			fixNoExpand(detailId);
			topic.publish('/mui/form/valueChanged',null,{row:newRow});
			topic.publish("/mui/list/resize",newRow);
			if(callbackFun)callbackFun(newRow);
		});
	}
	window.delRowExpand = function(detailId,domNode) {
		var td = $(domNode).closest('.detail_wrap_td')[0];
		detail_delRowExpand(detailId,td.parentNode);
	};
	window.detail_delRowExpand = function(detailId,trDom){
		$(trDom).find("*[widgetid]").each(function(idx,widgetDom){
			var widget = registry.byNode(widgetDom);
			if(widget && widget.destroy){
				widget.destroy();
			}
		});
		var optTB = DocListFunc_GetParentByTagName("TABLE", trDom); 
		var rowIndex = Com_ArrayGetIndex(optTB.rows, trDom);
		var tbInfo = DocList_TableInfo[optTB.id];
		DocList_DeleteRow_ClearLast(trDom);
		for(var i= rowIndex; i<tbInfo.lastIndex; i++){
			var row = tbInfo.DOMElement.rows[i];
			query('*[widgetid]',row).forEach(function(widgetDom){
				var widget = registry.byNode(widgetDom);
				if(widget.needToUpdateAttInDetail){
					var updateAttrs = widget.needToUpdateAttInDetail;
					for(var j = 0;j < updateAttrs.length;j++){
						widget[updateAttrs[j]] = repalceIndexInfoExpand(widget[updateAttrs[j]], i - tbInfo.firstIndex);
					}
				}else if(widget.name){
					widget.name = repalceIndexInfoExpand(widget.name, i - tbInfo.firstIndex);
				}else if(widget.idField){
					widget.idField = repalceIndexInfoExpand(widget.idField, i - tbInfo.firstIndex);
					widget.nameField = repalceIndexInfoExpand(widget.nameField, i - tbInfo.firstIndex);
				}
			});
		}
		fixNoExpand(detailId);
		topic.publish('/mui/form/valueChanged');
		topic.publish("/mui/list/resize",trDom);
	};
	
	function repalceIndexInfoExpand(fieldName,index){
		fieldName = fieldName.replace(/\[\d+\]/g, "[!{index}]");
		fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
		fieldName = fieldName.replace(/!\{index\}/g, index);
		return fieldName;
	}
	
	function fixNoExpand(detailId) {
		$('#'+detailId).find('.muiDetailTableNo').each(function(i) {
			$(this).children('span').text(lang['the'] + (i + 1) + lang['row'] );
		});
	}
	
	
	window.form_submit = function() {
		var method = Com_GetUrlParameter(location.href, 'method');
		var statusObj = query("input[name='docStatus']");
		statusObj.val('20');
		if (method == 'add') {
			Com_Submit(document.forms[0], 'save');
		} else {
			Com_Submit(document.forms[0], 'update');
		}
	};
});