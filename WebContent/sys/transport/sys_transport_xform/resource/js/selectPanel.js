/**
 * 
 */
(function(){
	function SelectPanel(evt){
		this.items;		// 所有选项 {value:{value:xxx,text:xxx}}
		this.dictEnums;
		this.selectedValues = [];		// 被选的ID
		this.unselectedValues = [];	// 未被选择的ID
		
		this.init = SelectPanel_Init;
		this.putIdsToSelected = SelectPanel_PutIdsToSelected;
		this.getItemById = SelectPanel_GetItemById;
		this.getEnumsById = SelectPanel_GetEnumsById;
		this.changeItem = SelectPanel_ChangeItem;	// 改变选项
		
		this.init(evt);
	}
	
	// argu:{items(全部选项):xxx,currentValues(当前选项):xxx}
	function SelectPanel_Init(argu){
		this.items = argu.items || {};
		this.dictEnums = argu.dictEnums || {};
		if(argu.currentValues){
			this.putIdsToSelected(argu.currentValues);
		}else{
			for(var key in this.items){
				this.unselectedValues.push(key);
			}
		}
	}
	
	function SelectPanel_PutIdsToSelected(ids){
		var idArr = ids.split(";");
		this.selectedValues = this.selectedValues.concat(idArr);
		// 添加未选择项
		for(var key in this.items){
			if($.inArray(key,idArr) == -1){
				this.unselectedValues.push(key);
			}
		}
		// 校验已选择项，可能存在某些已选的选项已经被删除的情况
		for(var i = 0;i < this.selectedValues.length;i++){
			if(!this.items.hasOwnProperty(this.selectedValues[i])){
				this.selectedValues.splice(i, 1);
			}
		}
	}
	
	function SelectPanel_GetItemById(id){
		return this.items[id];
	}
	
	function SelectPanel_GetEnumsById(id){
		return this.dictEnums[id];
	}
	
	// type: selected|unselected(选择|取消)
	function SelectPanel_ChangeItem(id,type){
		if(type == 'selected'){
			var index = $.inArray(id,this.selectedValues);
			this.selectedValues.splice(index,1);
			this.unselectedValues.push(id);
		}else if(type == 'unselected'){
			var index = $.inArray(id,this.unselectedValues);
			this.unselectedValues.splice(index,1);
			this.selectedValues.push(id);
		}
	}
	
	window.SelectPanel = SelectPanel;
})()