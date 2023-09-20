/**
 * 
 */
(function(){
	
	Com_IncludeFile("FieldBlock.js", Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_custom/js/", 'js', true);
	
	function Source(config){
		this.config = config ? config : {};
		
		this.tableDom;
		this.structure;
		this.fieldBlock;
		
		this.index = 0;
		this.titleTxt = "";
		this.titleVal = "";
		this.titleTxtDom; // 标题文本
		this.titleTemp = DbcenterLang.dataSource + "!{serial}";
		this.titleValTemp = "dataSource!{serial}";
		this.titleDelDom; 
		
		this.initTable = Source_InitTable;
		this.readData = Source_ReadData;
		this.updateTitle = Source_UpdateTitle;
		this.updateTitleDel = Source_UpdateTitleDel;
		
		this.addFieldBlock = Source_AddFieldBlock;
		this.updateFieldBlock = Source_UpdateFieldBlock;
		this.initFieldBlock = Source_InitFieldBlock;
		this.updateBlockTitle = Source_UpdateBlockTitle;
		this.delFieldBlock = Source_DelFieldBlock;
		this.updateDisplayComponent = Source_UpdateDisplayComponent;
	}
	
	function Source_InitTable($table){
		this.tableDom = $table;
		var components = $.extend(true,{},_db_components);
		for(var key in components){
			if(!components[key].dom && components[key].tableClass){
				components[key].dom = $table.find(components[key].tableClass);
			}
		}
		this.structure = new SQLStructure(components);
		
		if(!$.isEmptyObject(this.config)){
			var fieldDatas = SQLDataSource_findFieldDict(this.config.baseModelData);
			this.config.data = fieldDatas.data;
			this.config.isXform = this.config.baseModelData.isXform;
			
			this.structure.init(this.config);
		}
	}
	
	function Source_ReadData(){
		var keyData = this.structure.getKeyData();
		keyData.titleTxt = this.titleTxt;
		keyData.titleVal = this.titleVal;
		return keyData;
	}
	
	function Source_UpdateTitle(){
		this.titleTxt = this.titleTemp.replace(/!\{serial\}/g,this.index);
		this.titleVal = this.titleValTemp.replace(/!\{serial\}/g,this.index);
		this.titleTxtDom.html(this.titleTxt);
	}
	
	function Source_UpdateTitleDel(show){
		if(show){
			this.titleDelDom.show();
		}else{
			this.titleDelDom.hide();
		}
	}
	
	function Source_InitFieldBlock(){
		this.fieldBlock.init();
		this.updateBlockTitle();
	}
	
	function Source_UpdateFieldBlock(component,type){
		this.fieldBlock.update(component,type);
	}
	
	function Source_AddFieldBlock(component){
		this.fieldBlock.add(component);
	}
	
	function Source_UpdateBlockTitle(){
		this.fieldBlock.updateTitleTxt(this.titleTxt);
	}
	
	function Source_DelFieldBlock(){
		this.fieldBlock.domNode.remove();
		this.fieldBlock = null;
	}
	
	function Source_UpdateDisplayComponent(){
		this.structure.updateDisplayComponent();
	}
	
	window.Source = Source;
})()