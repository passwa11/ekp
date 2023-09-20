/**
 * 打印增强：明细表分页增加表头
 * 解决打印预览时表单明细表过长导致分页截断的问题
 * */
$(function(){
	var tdls = $("table[id^='TABLE_DL_']");
	for(var i=0;i<tdls.length;i++){
		var t = $(tdls[i]);
		var tRow = t.find("tr[type='titleRow']");
		if (tRow && tRow.length > 0) {
			var thead = '<thead style="display:table-header-group;">';
			for (var j = 0; j < tRow.length; j++) {
				thead +=  tRow[j].cloneNode(true).outerHTML;
			}
			thead += '</thead>';
			$(tRow).remove();
			t.prepend(thead);
		}
	}
});