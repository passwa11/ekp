seajs.use(['lui/dialog','lang!fssc-common','lang!','lui/util/env'],function(dialog,lang,comlang,env){
	window.FSSC_SourceModelSelected = function(data){
		if(!data){
			return;
		}
		var len = $("#TABLE_DocList_fdDetailLsit_Form>tbody>tr").length-2;
		for(var i=0;i<len;i++){
			DocList_DeleteRow($("#TABLE_DocList_fdDetailLsit_Form>tbody>tr").eq(1)[0]);
		}
		$('[name=fdSourceModelName]').val(data[0].fdModelName)
		$('[name=fdSourceModelSubject]').val(data[0].fdModelSubject)
		$('[name=fdSourceTableName]').val(data[0].fdModelTable)
		//带出目标表名及字段信息
		var fdModelName = data[0].fdModelName;
		data = new KMSSData();
		data.AddBeanData("fsscCommonTransferFieldService&type=getSourceField&fdSourceModelName="+fdModelName);
		data = data.GetHashMapArray();
		if(!data||data.length==0){
			return;
		}
		data = data[0];
		$('[name=fdTargetModelName]').val(data.fdTargetModelName)
		$('[name=fdTargetModelSubject]').val(data.fdTargetModelSubject)
		$('[name=fdTargetTableName]').val(data.fdTargetTableName)
		var sproperty = JSON.parse(data.source)||[];
		var tproperty = JSON.parse(data.target)||[];
		for(var i in sproperty){
			var es = $("[name$="+i+"]");
			if(es.length>0){
				continue;
			}
			DocList_AddRow('TABLE_DocList_fdDetailLsit_Form');
			var index = $("#TABLE_DocList_fdDetailLsit_Form>tbody>tr").length-3;
			sproperty[i] = JSON.parse(sproperty[i]);
			$("[name='fdDetailLsit_Form["+index+"].fdSourceField']").val(sproperty[i].column);
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFieldName']").val(i);
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFieldText']").val(sproperty[i].subject);
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFieldType']").val(sproperty[i].type);
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchTable']").val(sproperty[i]['fetch-table'])
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchFrom']").val(sproperty[i]['fetch-source'])
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchTo']").val(sproperty[i]['fetch-target'])
			if(tproperty[i]){
				tproperty[i] = JSON.parse(tproperty[i])
				$("[name='fdDetailLsit_Form["+index+"].fdTargetField']").val(tproperty[i].column);
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFieldName']").val(i);
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFieldText']").val(tproperty[i].subject);
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFieldType']").val(tproperty[i].type);
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchTable']").val(tproperty[i]['fetch-table'])
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchFrom']").val(tproperty[i]['fetch-source'])
				$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchTo']").val(tproperty[i]['fetch-target'])
			}
		}
	}
	
	window.FSSC_TargetModelSelected = function(data){
		$('[name=fdTargetModelName]').val(data[0].fdModelName)
		$('[name=fdTargetModelSubject]').val(data[0].fdModelSubject)
		$('[name=fdTargetTableName]').val(data[0].fdModelTable)
	}
	
	window.FSSC_SelectSourceField = function(){
		var fdSourceModelName = $("[name=fdSourceModelName]").val();
		if(!fdSourceModelName){
			dialog.alert(lang['message.pleaseSelectSourceModelFirst']);
			return;
		}
		var index = DocListFunc_GetParentByTagName("TR").rowIndex-1;
		dialogSelect(false,'fssc_common_transfer_field_selectField','fdDetailLsit_Form[*].fdSourceField','fdDetailLsit_Form[*].fdSourceFieldText',{modelName:fdSourceModelName},function(data){
			if(!data){
				return;
			}
			$("[name='fdDetailLsit_Form["+index+"].fdSourceField']").val(data[0].column)
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFieldName']").val(data[0].name)
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFieldText']").val(data[0].subject)
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchTable']").val(data[0]['fetch-table'])
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchFrom']").val(data[0]['fetch-source'])
			$("[name='fdDetailLsit_Form["+index+"].fdSourceFetchTo']").val(data[0]['fetch-target'])
		});
	}
	
	window.FSSC_SelectTargetField = function(){
		var fdTargetModelName = $("[name=fdTargetModelName]").val();
		if(!fdTargetModelName){
			dialog.alert(lang['message.pleaseSelectTargetModelFirst']);
			return;
		}
		var index = DocListFunc_GetParentByTagName("TR").rowIndex-1;
		dialogSelect(false,'fssc_common_transfer_field_selectField','fdDetailLsit_Form[*].fdTargetField','fdDetailLsit_Form[*].fdTargetFieldText',{modelName:fdTargetModelName},function(data){
			if(!data){
				return;
			}
			$("[name='fdDetailLsit_Form["+index+"].fdTargetField']").val(data[0].column)
			$("[name='fdDetailLsit_Form["+index+"].fdTargetFieldName']").val(data[0].name)
			$("[name='fdDetailLsit_Form["+index+"].fdTargetFieldText']").val(data[0].subject)
			$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchTable']").val(data[0]['fetch-table'])
			$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchFrom']").val(data[0]['fetch-source'])
			$("[name='fdDetailLsit_Form["+index+"].fdTargetFetchTo']").val(data[0]['fetch-target'])
		});
	}
	
});