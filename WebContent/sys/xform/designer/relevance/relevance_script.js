
Com_IncludeFile('relevanceObj.js',Com_Parameter.ContextPath+'sys/xform/designer/relevance/','js',true);

$(function(){
	// 支持系统底层框架，支持必填设置
	$form.regist({
		support : function(target){
			return target.type=='relevance';
		},
		required : function(target, value){
			var wgt = this.getRelevanceWgt(target);
			if(wgt){
				if(value == null){
					return wgt.required;
				}
				wgt.setRequire(value);
			}else{
				return false;
			}
		},
		getRelevanceWgt : function(target){
			var rsWgt = null;
			for(var i = 0;i < Xform_ObjectInfo.Xform_Controls.relevanceObj.length;i++){
				var wgt = Xform_ObjectInfo.Xform_Controls.relevanceObj[i];
				if(wgt.$xformflag[0] == target.root[0]){
					rsWgt = wgt;
					break;
				}
			}
			return rsWgt;
		}
	});
	
	// css的引用，不用Com_IncludeFile（不注册），它在重新编辑时候的明细表里面会有问题，在基准行删除css之后，无法再引入css，除非新增行
	Relevance_ImportCss(Com_Parameter.ContextPath+'sys/xform/designer/relevance/css/relevance_main.css');

    Relevance_Ready();

    /** 监听高级明细表 */
    $(document).on("detailsTable-init", function(e, tbObj){
        Relevance_Ready(tbObj);
    })
	
	//明细表内控件 有明细表的 table-add 事件触发初始化
	$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
		var row = argus.row;
		//初始化 
		$(row).find("xformflag[flagtype='relevance']:not([id*='!{index}'])").each(function(index,dom){
			Relevance_Instance(dom);
			$(dom).trigger($.Event("exceRelationRule"),row);
		});
	});
	
	$(document).on('table-delete','table[showStatisticRow]',function(e,row){
		// 删除的同时需要删除对象
		$(row).find("xformflag[flagtype='relevance']:not([id*='!{index}'])").each(function(index,dom){
			Relevance_DeleteObj(dom);
		});
	});

    function Relevance_Ready(tbObj) {
        var context = tbObj || document;
        $("xformflag[flagtype='relevance']:not([id*='!{index}'])", context).each(function(index,dom){
            if (!tbObj) {
                //页面初始化时, 跳过高级明细表
                if ($(dom).closest("[fd_type='seniorDetailsTable']").length > 0) {
                    return true;
                }
            }
            Relevance_Instance(dom);
        });
    }

	function Relevance_Instance(dom){
		if(Xform_ObjectInfo && Xform_ObjectInfo.isArchives){
			return;
		}
		var $input = $(dom).find("input[name*='"+ $(dom).attr("flagid") +"']");
		if($input.length > 0){
			var params = {};
			params.controlId = $(dom).attr("flagid");
			params.showStatus = Xform_ObjectInfo.mainDocStatus;
			params.domValNode = $input[0];
			params.$xformflag = $(dom);
			var $label = $(dom).find("div[name='"+ $(dom).attr("flagid") +"']");
			var $rightStatus = "";
			if($label.length > 0){
				params.attrNode = $label[0];
				if(typeof $label.attr("rightStatus") != "undefined"){
					// 支持权限区段的使用
					$rightStatus = $label.attr("rightStatus");
					if($rightStatus!=""){
						params.showStatus = $rightStatus;
					}
				}
			}
			var relObj = new RelevanceDocObj(params);
			relObj.display();
		}
	}
});

function Relevance_ImportCss(url){
	var link = document.createElement('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = url;
    var head = document.getElementsByTagName('head')[0];
    head.appendChild(link);
}


Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = relevance_main_update_buildControlData;

function relevance_main_confirm(relObj) {
    var validateEle;
    if(relObj.required && relObj.value.length==0){
        //判断值隐藏域是否有必填校验属性，因某些业务模块暂存时，会移除所有必填校验，故此处做兼容
        var canValidate = $(relObj.domValNode).attr("validate")&&$(relObj.domValNode).attr("validate").indexOf("required")>=0?true:false;
        if(canValidate){
            validateEle = relObj;
        }else{
            if(relObj.reminder){
                relObj.reminder.hide();
            }
        }
    }
    relObj.domValNode.value = relObj.value.length==0?"":JSON.stringify(relObj.value);
    if (validateEle) {
        validateEle.reminder.show();
        return false;
    }
    return true;

}

//提交时设置数据
function relevance_main_update_buildControlData(){
	var validateEles = [];
	for(var i = 0;i < Xform_ObjectInfo.Xform_Controls.relevanceObj.length;i++){
		var relObj = Xform_ObjectInfo.Xform_Controls.relevanceObj[i];
        var confirm = relevance_main_confirm(relObj);
        if (!confirm) {
            validateEles.push(relObj);
        }
	}
	if(validateEles.length>0){
		//定位到第一个提示地方
		$KMSSValidation_ElementFocus(validateEles[0].attrNode);
		return false;
	}
	return true;
}
