(function(window, undefined){
	
	/**
	 * 明细表
	 */
	 var detailsTableControl=sysPrintDesignerTableControl.extend({
		 render:function(config){
		 	if(config && config.renderLazy) return;
		 	if(!config)
				config={};
		 	if(!config.cols)
				config.cols=3;//列数，不包括序号列
		 	if(!config.id){
		 		var id="fd_"+sysPrintUtil.generateID();
		 		config.id=id;
		 	}
		 	if(!config.name){
		 		config.name = config.id;
		 	}
		 	var fdType = config.fdType || "detailsTable";
		 	var tableHTML='<table fd_type="' + fdType + '" printcontrol="true" align="center" class="tb_normal" style="width: 98%;" id="'+config.id+'" name="' + config.name + '"><tbody>';
		 	//标题列
		 	tableHTML+='<tr class="tr_normal_title" type="titleRow">'+
		 	'<td row="0" column="0" align="center" coltype="noTitle" style="width: 25px;"><label attach="'+config.id+'">' + DesignerPrint_Lang.controlDetailsTable_seqNo + '</label></td>';
		 	for(var i=0;i<config.cols;i++){
		 		tableHTML+='<td row="0" column="'+parseInt(i+1)+'" align="center" width="30%"></td>';
		 	}
		 	tableHTML+=	'</tr>';
		 	//内容列
		 	tableHTML+='<tr type="templateRow">'+
		 		'<td row="1" column="0" align="center" coltype="noTemplate" style="width: 10px;"><label attach="'+config.id+'">1</label></td>';
		 	for(var i=0;i<config.cols;i++){
		 		tableHTML+='<td row="1" column="'+parseInt(i+1)+'" align="center" width="30%"></td>';
		 	}
		 	tableHTML+=	'</tr>';
		 	//统计列
		 	tableHTML+='<tr type="statisticRow">'+
		 		'<td row="2" column="0" align="center" coltype="noFoot" style="width: 10px;"><label attach="'+config.id+'">&nbsp;</label></td>';
		 	for(var i=0;i<config.cols;i++){
		 		tableHTML+='<td row="2" column="'+parseInt(i+1)+'" align="center" width="30%"></td>';
		 	}
		 	tableHTML+=	'</tr>';
		 	tableHTML+='</tbody></table>';
			this.$domElement=$(tableHTML);
			
			//记录列集合
			var self = this;
			setTimeout(function(){
				self.onInitialize();
			},1)
	 	}
	 	//onInitialize:function(){}
	 });
	
	 window.sysPrintDetailsTableControl=detailsTableControl;
	
})(window);