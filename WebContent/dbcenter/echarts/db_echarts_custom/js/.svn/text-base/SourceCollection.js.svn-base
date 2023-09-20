/**
 * 
 */
(function(){
	
	Com_IncludeFile('Source.js',Com_Parameter.ContextPath+'dbcenter/echarts/db_echarts_custom/js/','js',true);
	
	function SourceCollection(domNode){
		
		this.domNode = domNode;
		this.fieldBlocksDom;
		
		this.sources = [];
		
		this.addSource = SourceCollection_AddSource;
		this.delSource = SourceCollection_DelSource;
		this.getSourceByTable = SourceCollection_GetSourceByTable;
		this.readData = SourceCollection_ReadData;
		this.updateSourceDel = SourceCollection_UpdateSourceDel; // 更新数据源的删除按钮，在只有一个数据源的时候，不显示删除按钮
		
		this.getAllFieldBlocks = SourceCollection_GetAllFieldBlocks;
		this.addFieldBlock = SourceCollection_AddFieldBlock;
		this.updateFieldBlock = SourceCollection_UpdateFieldBlock;
		this.findSourceByStru = SourceCollection_FindSourceByStru;
		this.initFieldBlock = SourceCollection_InitFieldBlock;
		this.updateDisplayComponent = SourceCollection_UpdateDisplayComponent;
	}
	
	function SourceCollection_AddSource(config){
		var source = new Source(config);
		
		this.sources.push(source);
		
		var $tr = tableDraw.addRow(this.domNode);
		$tr.attr("data-coltype","dataCol");
		var $table = $tr.find(".SQLStructureTable");
		
		source.index = tableDraw.lastIndex - tableDraw.firstIndex;
		source.titleTxtDom = $table.find(".db_serial");
		source.titleDelDom = $table.find(".opera_del");
		source.updateTitle();
		
		source.fieldBlock = new FieldBlock(this.fieldBlocksDom);
		source.fieldBlock.source = source;
		source.initFieldBlock(); // 顺序不能换
		source.initTable($table);
		
		this.updateSourceDel();
		// 更新样式，目前是更新必填元素的显示隐藏
		this.updateDisplayComponent(source);
		return source;
	}
	
	function SourceCollection_DelSource($table){
		var sources = this.sources;
		for(var i = 0;i < sources.length;i++){
			var source = sources[i];
			if(source.tableDom[0] == $table[0]){
				source.delFieldBlock();
				sources.splice(i,1);
				
				var $tr = $table.closest("tr[data-coltype='dataCol']");
				var curIndex = $tr.index();
				$tr.remove();
				tableDraw.lastIndex--;
				// 更新索引
				for(var j = i;j < sources.length;j++){
					sources[j].index--;
					sources[j].updateTitle();
					sources[j].updateBlockTitle();
				}
				
				for(var i = curIndex;i < tableDraw.lastIndex;i++){
					var index = i - tableDraw.firstIndex;
					var $tempTr = this.domNode.find("tr[data-coltype='dataCol']:eq("+index+")");
					tableDraw.refreshFieldIndex($tempTr,index);
				}
				break;
			}
		}
		this.updateSourceDel();
	}
	
	function SourceCollection_UpdateSourceDel(){
		var sources = this.sources;
		// 只有一个数据源的时候，隐藏删除按钮，理论上至少有一个数据源
		if(sources.length == 1){
			sources[0].updateTitleDel(false);
		}else {
			sources[0].updateTitleDel(true);
		}
	}
	
	function SourceCollection_GetSourceByTable(table){
		var sources = this.sources;
		for(var i = 0;i < sources.length;i++){
			var source = sources[i];
			if(source.tableDom[0] == table){
				return source;
			}
		}
	}
	
	function SourceCollection_ReadData(tables){
		if(tables.length != this.sources.length){
			console.error("表格长度和记录的长度不一致！");
		}
		for(var i = 0;i < this.sources.length;i++){
			var source = this.sources[i];
			$.extend(tables[i],source.readData());
		}
	}
	
	function SourceCollection_GetAllFieldBlocks(){
		var items = [];
		for(var i = 0;i < this.sources.length;i++){
			var source = this.sources[i];
			items.push(source.fieldBlock);
		}
		return items;
	}
	
	function SourceCollection_UpdateFieldBlock(structure,fieldComponent,type){
		var source = this.findSourceByStru(structure);
		if(source){
			source.updateFieldBlock(fieldComponent,type);
		}
	}
	
	function SourceCollection_FindSourceByStru(structure){
		for(var i = 0;i < this.sources.length;i++){
			var source = this.sources[i];
			if(source.structure == structure){
				return source;
			}
		}
	}
	
	function SourceCollection_InitFieldBlock(structure){
		var source = this.findSourceByStru(structure);
		source.initFieldBlock();
		var selects = structure.getComponentByKey("select");
		for(var i = 0;i < selects.arr.length;i++){
			var select = selects.arr[i];
			this.addFieldBlock(structure,select);
		}
	}
	
	function SourceCollection_AddFieldBlock(structure,component){
		var source = this.findSourceByStru(structure);
		source.addFieldBlock(component);
	}
	
	function SourceCollection_UpdateDisplayComponent(source){
		source.updateDisplayComponent();
	}
	
	window.SourceCollection = SourceCollection;
})()