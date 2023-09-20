/**
 * 
 */
// 设置每个字段组件的选项
function SQLDataSource_CustomPropertyItem(source){
	var countItem = new SQLPropertyItem({
		fieldText : DbcenterLang.count,
		fieldPinYinText : DbcenterLang.indexAndCount,
		field : '!{count}',
		fieldType : '!{count}'
	});
	source.whereItems = source.propertyItems;
	for(var i = 0;i < source.propertyItems.length;i++){
		var item = source.propertyItems[i];
		//if(item.type == 'Integer' || item.type == 'Double' || item.type == 'BigDecimal' || item.type.indexOf("com.landray.kmss") > -1){
			source.selectItems.push(item);		
		//}
	}
	source.selectItems.push(countItem);
	
	source.seriesItems = source.propertyItems;
	source.filterItems = source.propertyItems;
	source.categoryItems = source.propertyItems;
}

function validateForm(){
	//检索rtf里面的变量是否都存在
	var rtfTxt = CKEDITOR.instances.fdCustomText.getData();
	var vars = rtfTxt.match(/\$[^\$]*\$/g);
	var selectVars = getSelectFieldVar();
	if(vars){
		for(var i = 0 ;i < vars.length;i++){
			if($.inArray(vars[i].replace(/\$/g,""), selectVars) == -1){
				alert(DbcenterLang.customError1 + " " + vars[i] + " " + DbcenterLang.customError2);
				return false;
			}
		}	
	}
	//返回值不能为空
	var sources = sourceCollection.sources;
	for(var i = 0;i < sources.length;i++){
		var source = sources[i];
		if(!source.structure.isValidByKey("select")){
			alert(source.titleTxt + DbcenterLang.rsCantNull);	
			return false;
		}
	}
	return true;
}