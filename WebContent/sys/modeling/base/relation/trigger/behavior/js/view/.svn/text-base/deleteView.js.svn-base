/**
 * 更新视图
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var crudBaseView = require("sys/modeling/base/relation/trigger/behavior/js/view/crudBaseView");
	
	var DeleteView = crudBaseView.CrudBaseView.extend({
		
		build : function(){
			this.prefix = "delete";
			if($("[mdlng-bhvr-data='precfg']").find(".behavior_update_preview").length != 1){
        		this.buildPreWhereView();
        	}
			if($("[mdlng-bhvr-data='precfg']").find(".behavior_detail_query_preview").length != 1){
				this.buildDetailQueryView();
			}
			var $element = $("<div class='behavior_delete_view'/>");
			var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
			//明细表
			this.buildDetailRule($table, this.parent.detailRuleTmpStr, this.parent.detailTmpStr);

			this.buildWhere($table,this.parent.whereTmpStr);
			//查询条件类型
			$table.find(".WhereTypeinput4").hide();
			 //查询条件切换事件
            $table.find(".WhereTypediv input[type='radio']").on("change",function(){
            	 var $whereBlockDom = $table.find(".view_field_where_div");
            	 if(this.value === "2"){
            		 $whereBlockDom.hide();
            	 }else{
            		 $whereBlockDom.show();
            	 }
            });
			var whereType = $table.find(".WhereTypediv input[type='radio']:checked").val();
			if(whereType === "3"){
				$table.find(".WhereTypediv input[type='radio'][value='0']").prop("checked","checked");
			}
            $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
			$table.find(".view_fdId_where_table").remove();
			$element.append($table);
			
			this.config.viewContainer.append($element);
			return $element;
		}
	});
	
	exports.DeleteView = DeleteView;
});