/**
 * 
 */
$(function(){
	if(!Xform_ObjectInfo.ExtraDealControlFun){
		Xform_ObjectInfo.ExtraDealControlFun = [];
	}
	Xform_ObjectInfo.ExtraDealControlFun.push(Placeholder_SetValue);
	
	function Placeholder_SetValue(xformflag,value){
		var xformType;
		if(xformflag){
			xformType = xformflag.attr("flagtype");
		}
		if(xformType){
			if(xformType == "placeholder"){
				var wgtId = xformflag.find(".modelingPlaceholder").attr("id");
				LUI(wgtId).updateTextView();
			}
		}
	};
	
	seajs.use(["sys/modeling/main/xform/controls/placeholder/placeholderDispatcher"],function(placeholderDispatcher){
		
		$("xformflag[flagtype='placeholder']:not([id*='!{index}'])").each(function(index,dom){
			PlaceholderDispatcher_Instance($(dom));
		});
		
		//明细表内控件 有明细表的 table-add 事件触发初始化
		$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
			var row = argus.row;
			//初始化 
			$(row).find("xformflag[flagtype='placeholder']:not([id*='!{index}'])").each(function(index,dom){
				PlaceholderDispatcher_Instance($(dom));
			});
		});
		
		$(document).on('table-delete','table[showStatisticRow]',function(e,row){
			// 删除的同时需要删除对象
			$(row).find("xformflag[flagtype='placeholder']:not([id*='!{index}'])").each(function(index,dom){
				PlaceholderDispatcher_Destroy($(dom));
			});
		});
		
		function PlaceholderDispatcher_Instance($xformflag){
			var $storeDataDom = $xformflag.find(".modelingPlaceholder");
			var wgt = new placeholderDispatcher.PlaceholderDispatcher({
				element : $storeDataDom
			});
			wgt.startup();
		}
		
		function PlaceholderDispatcher_Destroy($xformflag){
			var $storeDataDom = $xformflag.find(".modelingPlaceholder");
			LUI($storeDataDom.attr("id")).destroy();
		}
		
	})
	if(window['$form']){
		$form.regist({
			_support : function(target){
				return target.type=='placeholder';
			},
			readOnly : function(target, value){
				var operationDiv = target.root.find(".lui-placeholder-oper");
				if(value == null){
					if(operationDiv.length > 0){
						return !operationDiv.is(':visible');
					}
					return;
				}
				if(operationDiv.length > 0){
					if(value){
						operationDiv.hide();
						target.root.find(".lui-placeholder-textshow").addClass("placeholder_textShow_readonly");
					}else{
						operationDiv.css("display","table-cell");
						target.root.find(".lui-placeholder-textshow").removeClass("placeholder_textShow_readonly");
					}
				}else{
					var modelingPlaceholderDom = target.root.find(".modelingPlaceholder");
					if(value){
						modelingPlaceholderDom.find("input").attr("disabled","disabled");
						modelingPlaceholderDom.find("select").attr("disabled","disabled");
						modelingPlaceholderDom.find(".tree-wrap").attr("_readonly","readOnly");
					}else{
						modelingPlaceholderDom.find("input").removeAttr("disabled")
						modelingPlaceholderDom.find("select").removeAttr("disabled");
						modelingPlaceholderDom.find(".tree-wrap").removeAttr("_readonly");
					}
				}
			},
			//必填标签兄弟节点
			getRequiredFlagPreElement : function(target){
				return target.root.find(".modelingPlaceholder");
			}
		});
	}
})