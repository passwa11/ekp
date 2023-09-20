/**
 *  实例化汇总控件
 */
$(function(){
	Xform_ObjectInfo.ExtraDealControlFun.push(DetailSummary_SetValue);
	
	function DetailSummary_SetValue(xformflag,value){
		var xformType;
		if(xformflag){
			xformType = xformflag.attr("flagtype");
		}
		if(xformType){
			if(xformType == "detailSummary"){
				var wgtId = xformflag.find(".designerDetailSummaryConfig").attr("id");
				LUI(wgtId).updateTextView();
			}
		}
	};
	
	seajs.use(["sys/xform/designer/detailFun/detailSummaryConstructor"],function(DSConstructor){

		$("xformflag[flagtype='detailSummary']:not([id*='!{index}'])").each(function(index,dom){
			DetailSummaryConstructor_Instance($(dom));
		});

		//明细表内控件 有明细表的 table-add 事件触发初始化
		$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
			var row = argus.row;
			//初始化
			$(row).find("xformflag[flagtype='detailSummary']:not([id*='!{index}'])").each(function(index,dom){
				DetailSummaryConstructor_Instance($(dom));
			});
		});

		$(document).on('table-delete','table[showStatisticRow]',function(e,row){
			// 删除的同时需要删除对象
			$(row).find("xformflag[flagtype='detailSummary']:not([id*='!{index}'])").each(function(index,dom){
				DetailSummaryConstructor_Destroy($(dom));
			});
		});

		function DetailSummaryConstructor_Instance($xformflag){
			var $storeDataDom = $xformflag.find(".designerDetailSummaryConfig");
			var wgt = new DSConstructor.DetailSummaryConstructor({
				element : $storeDataDom
			});
			wgt.startup();
		}

		function DetailSummaryConstructor_Destroy($xformflag){
			var $storeDataDom = $xformflag.find(".designerDetailSummaryConfig");
			LUI($storeDataDom.attr("id")).destroy();
		}

	})
	
	if(window['$form']){
	    $form.regist({
			_support : function(target){
				return target.type=='detailSummary';
			},
			// 必填标签兄弟节点
			getRequiredFlagPreElement : function(target){
				return target.root.find(".lui-detail-summary-button");
			}
		});
	}
})