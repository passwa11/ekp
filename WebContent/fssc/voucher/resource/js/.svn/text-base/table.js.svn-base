seajs.use(['lui/jquery','lui/topic'],function($,topic){
	window.saveVoucherDetail=function(){
		var chooseArr = new Array("fdType",
				"fdBaseAccountsId",
				"fdBaseAccountsName",
				"fdBaseAccountsCode",
				"fdBaseCostCenterId",
				"fdBaseCostCenterName",
				"fdBaseErpPersonId",
				"fdBaseErpPersonName",
				"fdBaseCashFlowId",
				"fdBaseCashFlowName",
				"fdBaseCustomerId",
				"fdBaseCustomerName",
				"fdBaseSupplierId",
				"fdBaseSupplierName",
				"fdBaseWbsId",
				"fdBaseWbsName",
				"fdBaseInnerOrderId",
				"fdBaseInnerOrderName",
				"fdBaseProjectId",
				"fdBaseProjectName",
				"fdBasePayBankId",
				"fdBasePayBankName",
				"fdDeptId",
				"fdDeptName",
				"fdMoney",
				"fdVoucherText",
				"fdContractCode");
		var methodNew =$("input[name=methodNew]").val();
		var rowIndex;
		var isOK=true;
		for (var j = 0; j < chooseArr.length; j++) {
			var valueOfR="";
			var validaVlid="";
			if("fdType"==chooseArr[j]){
				 valueOfR=$('select[name="' + chooseArr[j] + '_New"]').val();
				validaVlid = $('select[name="' + chooseArr[j] + '_New"]').attr("validate");
			}else{
				 valueOfR=$('input[name="' + chooseArr[j] + '_New"]').val();
				 validaVlid = $('input[name="' + chooseArr[j] + '_New"]').attr("validate");
			}
			if(('undefined'==valueOfR||""==valueOfR||null==valueOfR)&&'undefined'!=validaVlid&&""!=validaVlid&&null!=validaVlid){
				isOK=true;
                seajs.use(['lui/dialog'], function(dialog){
                    dialog.alert(messageInfo["fssc-voucher:table.js.property.null"]);
                });
				return false;
			}else{
				isOK=false;
			}
		}
		
		if(!isOK){
			if("update"==methodNew){
				rowIndex=$("input[name=rowNo]").val();
			}else{
				var newrow = DocList_AddRow("TABLE_DocList");
				$(newrow).find("td").bind('click',function(){
					customClickRow(newrow,"update")
				});
				rowIndex=$(newrow)[0].rowIndex-1;
			}
			setRequiredOf();
			for (var j = 0; j < chooseArr.length; j++) {
				if("fdType"==chooseArr[j]){
					var value=$('select[name="' + chooseArr[j] + '_New"]').val();
					$('select[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val(value);
					$('select[name="_fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val(value);
					$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val(value);
				}else{
					var value=$('input[name="' + chooseArr[j] + '_New"]').val();
					$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val(value);
				}
			}
			closeDetail();
		}
	}
	//点击行事件方法
	window.customClickRow = function(row,methodNew,rowValueOf){
        var chooseArr = new Array("fdType",
            "fdBaseAccountsId",
            "fdBaseAccountsName",
            "fdBaseAccountsCode",
            "fdBaseCostCenterId",
            "fdBaseCostCenterName",
            "fdBaseErpPersonId",
            "fdBaseErpPersonName",
            "fdBaseCashFlowId",
            "fdBaseCashFlowName",
            "fdBaseCustomerId",
            "fdBaseCustomerName",
            "fdBaseSupplierId",
            "fdBaseSupplierName",
            "fdBaseWbsId",
            "fdBaseWbsName",
            "fdBaseInnerOrderId",
            "fdBaseInnerOrderName",
            "fdBaseProjectId",
            "fdBaseProjectName",
            "fdBasePayBankId",
            "fdBasePayBankName",
            "fdMoney",
            "fdVoucherText",
            "fdContractCode");
		if(rowValueOf!=""&&rowValueOf!=null&&rowValueOf!="undefined"){
			var element = $('#table_of_pro_detail');
			rowOpt(rowValueOf,{
				element:element
			});
		}else{
			var element = $('#table_of_pro_detail');
			rowOpt(row,{
				element:element
			});
		}
		$("#TABLE_DocList").find("tr").each(function(){
			$(this).removeClass("current");
		});
		$(row).addClass("current");
		var rowIndex=$(row)[0].rowIndex-1;
		$("input[name=rowNo]").val(rowIndex);
		$("input[name=methodNew]").val(methodNew);
		for (var j = 0; j < chooseArr.length; j++) {
			if("fdType"==chooseArr[j]){
				var value=$('select[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val();
				if("undefined"==value||""==value||null==value){
					value=$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val();
				}
				$('select[name="' + chooseArr[j] + '_New"]').val(value);
			}else{
				var value=$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val();
				$('input[name="' + chooseArr[j] + '_New"]').val(value);
			}
		}
		var fdDeptId = $("[name='fdDetail_Form["+rowIndex+"].fdDeptId']").val();
		var fdDeptName = $("[name='fdDetail_Form["+rowIndex+"].fdDeptName']").val();
		emptyNewAddress("fdDeptName_New",null,null,false);
		$('[name="fdDeptId_New"]').val(fdDeptId);
		$('[name="fdDeptName_New"]').val(fdDeptName);
		var addressInput = $("[xform-name='mf_fdDeptName_New']")[0];
	    var addressValues = new Array();
	    addressValues.push({id:fdDeptId,name:fdDeptName});
		newAddressAdd(addressInput,addressValues);
		
        removeRequiredOf();//清空校验
        setRequiredOf();//初始化必要的校验
        FS_FdAccountCodeChange();//初始化可选属性校验
	};
	//点击行事件方法
	window.customClickRowforView = function(row,fdIdd){
        var chooseArr = new Array("fdType",
            "fdBaseAccountsName",
            "fdBaseCostCenterName",
            "fdBaseErpPersonName",
            "fdBaseCashFlowName",
            "fdBaseCustomerName",
            "fdBaseSupplierName",
            "fdBaseWbsName",
            "fdBaseInnerOrderName",
            "fdBaseProjectName",
            "fdBasePayBankName",
			"fdDeptName",
            "fdMoney",
            "fdVoucherText",
            "fdContractCode");
		var rowIndex=$(row)[0].rowIndex-1;
		var element = $('#table_of_pro_detail');
		rowOpt(row,{
			element:element
		});
		$("#TABLE_DocList").find("tr").each(function(){
			$(this).removeClass("current");
		});
		$(row).addClass("current");
		for (var j = 0; j < chooseArr.length; j++) {
			if("fdType"==chooseArr[j]){
				var value=$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val();
            if("1"==value||1==Number(value)){
                $('#' + chooseArr[j] + '_New').html(messageInfo["fssc-voucher:enums.fd_type.1"]);
            }else{
                $('#' + chooseArr[j] + '_New').html(messageInfo["fssc-voucher:enums.fd_type.2"]);
            }
        }else{
				var value=$('input[name="fdDetail_Form['+rowIndex+'].' + chooseArr[j] + '"]').val();
                $('#' + chooseArr[j] + '_New').html(value);
			}
		}
	};
	window.clearnValue=function(){
        var chooseArr = new Array("fdType",
            "fdBaseAccountsId",
            "fdBaseAccountsName",
            "fdBaseAccountsCode",
            "fdBaseCostCenterId",
            "fdBaseCostCenterName",
            "fdBaseErpPersonId",
            "fdBaseErpPersonName",
            "fdBaseCashFlowId",
            "fdBaseCashFlowName",
            "fdBaseCustomerId",
            "fdBaseCustomerName",
            "fdBaseSupplierId",
            "fdBaseSupplierName",
            "fdBaseWbsId",
            "fdBaseWbsName",
            "fdBaseInnerOrderId",
            "fdBaseInnerOrderName",
            "fdBaseProjectId",
            "fdBaseProjectName",
            "fdBasePayBankId",
            "fdBasePayBankName",
            "fdDeptId",
			"fdDeptName",
            "fdMoney",
            "fdVoucherText");
		$(".validation-advice").each(function(){
			$(this).remove();
		});
		$(".lui_validate").each(function(){
			$(this).remove();
		});
		$("#TABLE_DocList").find("tr").each(function(){
			$(this).removeClass("current");
		});
		$("input[name=rowNo]").val("");
		$("input[name=methodNew]").val("");
		for (var j = 0; j < chooseArr.length; j++) {
				if("fdAccountProperty"== chooseArr[j]){
					$('input[name="fdAccountProperty_New"]').val("");
					changeRequired();
				}else if("fdType"==chooseArr[j]){
					$('select[name="' + chooseArr[j] + '_New"]').val("");
				}else{
					$('input[name="' + chooseArr[j] + '_New"]').val("");
				}
		}
	}
	window.setRequiredOf=function(){
        $('input[name="fdType_New"]').attr("validate","required");
        $('input[name="fdVoucherText_New"]').attr("validate","required");
		$('input[name="fdMoney_New"]').attr("validate","required currency-dollar");
        $('input[name="fdBaseAccountsName_New"]').attr("validate","required");
	}
	window.removeRequiredOf=function(){
		 var arr = new Array("fdType",
		            "fdVoucherText",
		            "fdMoney",
		            "fdBaseAccountsName",
		            "fdBaseCostCenterName",
		            "fdBaseProjectName",
		            "fdBaseCustomerName",
		            "fdBaseSupplierName",
		            "fdBaseCashFlowName",
		            "fdBaseErpPersonName",
		            "fdBaseWbsName",
		            "fdBaseInnerOrderName",
		            "fdBasePayBankName",
					"fdDeptName",
					"fdContractCode");
		 for (var n = 0; n < arr.length; n++) {
			 $('input[name="'+arr[n]+'_New"]').attr("validate","");
			 if($('#'+arr[n]+'_reset')){
				 $('#'+arr[n]+'_reset').hide();
			 }
		 }
	}
	//添加行事件方法
	window.customAddRow = function(){
		//var newrow = DocList_AddRow(optTB);
		//var element = $('<br/><div>带进来的编辑内容....</div>');
		//rowOpt(newrow,{
		//	element:element
		//});
	};
	window.closeDetail=function(val){
		if("view"==val){
			$("#TABLE_DocList").find("tr").each(function(){
				$(this).removeClass("current");
			});
			topic.publish('rowPoplayer.display.hide');
		}else{
			clearnValue();
            removeRequiredOf();
			topic.publish('rowPoplayer.display.hide');
		}
	}
	
	//row:需要操作的tr
	//config:配置对象,element:弹出层内部元素、height:高度(默认350)、width:宽度(默认与表格同宽)
	window.rowOpt = function(row,config){
		var row = $(row),
			tables = row.parents('table'),
			rowPoplayer = null,
			config = config || {};
		if(tables.length > 0){
			var table = tables.eq(0),//离tr最近的table
				container = table.parent();
			if(container && ( container.css('position')=='static' || container.css('position')=='' ) ){
				container.css('position','relative');
			}
			if(!container){
				container = $(document.body);
			}
			if( !window.rowPoplayer ){
				window.rowPoplayer = $('<div class="tb_normal_poplayer"/>');
				container.append(window.rowPoplayer);
				window.rowPoplayer.css({
					'position': 'absolute',
					'border': '1px #d2d2d2 solid',
					'background-color': 'white'
				});
				
				topic.subscribe('rowPoplayer.display.hide',function(){
					window.clearInterval(window.rowPoplayer.resize);
					window.rowPoplayer.resize = null;
					if(rowPoplayer.activeRow){
						rowPoplayer.activeRow.css("height","");
						window.rowPoplayer.activeRow.css('vertical-align','');
					}
					rowPoplayer.hide();
				});
			}
			
			window.clearInterval(window.rowPoplayer.resize);
			if(window.rowPoplayer.activeRow){
				window.rowPoplayer.activeRow.css("height","");
				window.rowPoplayer.activeRow.css('vertical-align','');
			}
			window.rowPoplayer.resize = null;
			window.rowPoplayer.resize = window.setInterval(function(){
				if(window.rowPoplayer.activeRow){
					var __height = window.rowPoplayer.height(),
						__rowHeight = window.rowPoplayer.rowHeight;
					window.rowPoplayer.activeRow.height(__height + __rowHeight);
					window.rowPoplayer.activeRow.css('vertical-align','top');
				}
			});//监听高度变化改变tr高度
			
			var	rowPoplayer = window.rowPoplayer,
				height = config.height || 160,
				width = config.width || table.innerWidth(),
				rowHeight = row.height();
			if(!rowPoplayer.rowHeigth){
				rowPoplayer.rowHeight = rowHeight;
			}
			if(rowPoplayer.activeRow){
				rowPoplayer.activeRow.css("height","");
				window.rowPoplayer.activeRow.css('vertical-align','');
			}
			rowPoplayer.activeRow = row;
			
			//rowPoplayer.height(height);
			rowPoplayer.css('min-height',height);
			rowPoplayer.width(width);
			//固定fsscVoucherDetail_new_edit.jsp页面宽度跟父级table宽度一样
            $("#table_of_pro_detail_table").attr("width", width);
            //row.height(height);
			if(config.element){
				rowPoplayer.append(config.element);
			}
			var offsetTop = getOffsetTop(row,container);
			rowPoplayer.css("top",offsetTop + rowHeight );
			rowPoplayer.show();
		}
		
		function getOffsetTop(child,parent){
			var offsetTop = 0;
			while( child[0] != parent[0] ){
				offsetTop += child[0].offsetTop;
				child = child.parent();
			}
			return offsetTop;
		}
		
		return rowPoplayer[0];
		
	};
	
	
});
