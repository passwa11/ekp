/**
 * 
 */
(function(){
	function TableDraw(){
		this.templateHtml;
		
		this.firstIndex = 0;
		this.lastIndex = 0;
		
		this.init = TableDraw_Init;
		this.addRow = TableDraw_AddRow;
		this.refreshFieldIndex = TableDraw_RefreshFieldIndex;
	}
	
	function TableDraw_Init($table){
		var $tempTr = $table.find("[KMSS_Db_IsReferRow='1']");
		if($tempTr.length > 0){
			this.templateHtml = $tempTr.html();
			$tempTr.remove();
			this.firstIndex = $table.find("tr:not([data-coltype='optCol'])").length;
			this.lastIndex = $table.find("tr:not([data-coltype='optCol'])").length;
		}
	}
	
	function TableDraw_AddRow($table){
		var newRow = $table[0].insertRow(this.lastIndex);
		// 更新索引
		 var templateHtml = TableDraw_RefreshIndex(this.templateHtml,this.lastIndex - this.firstIndex);
		
		$(newRow).append(templateHtml);
		this.lastIndex++;
		return $(newRow);
	}
	
	function TableDraw_RefreshFieldIndex($tr,index){
		$tr.find("[data-dbecharts-config='fdCode']").each(function(){
			this.name = this.name.replace(/\[\d+\]/g, "["+index+"]");
		});
	}
	
	function TableDraw_RefreshIndex(temp,index){
		return temp.replace(/!\{index\}/g,index);
	}
	
	window.tableDraw = new TableDraw();
})()