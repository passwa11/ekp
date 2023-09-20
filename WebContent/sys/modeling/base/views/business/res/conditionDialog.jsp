<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
</head>
<body>
	<script>
		Com_IncludeFile("selectPanel.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", "js", true);
	</script>
<div class="lui_custom_list_container">
  <!-- 主要内容 Starts -->
  <div class="lui_custom_list_box">
    <div class="lui_custom_list_box_content">
      <div class="lui_custom_list_box_content_container">
        <div class="lui_custom_list_box_content_row">
          <div class="lui_custom_list_box_content_col" style="width: 40%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word"><bean:message bundle='sys-modeling-base' key='modeling.common.toBeSelected'/></span>
                <!-- 全选 -->
                <label class="lui_custom_list_checkbox right">
                 <a href="javascript:void(0)" onclick="selectAll()">
					${lfn:message('sys-modeling-base:modeling.common.selectAll') }
                 </a>
                </label>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="unselectedlist">
              </div>
            </div>
          </div>
          <div class="lui_custom_list_box_content_col" style="width: 60%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word">${lfn:message('sys-modeling-base:modeling.common.selected') }</span>
                <span class="lui_custom_list_box_content_link_right">
                  <a href="javascript:void(0)" onclick="cancleAll()">
                     	${lfn:message('sys-modeling-base:modeling.common.cancleAll') }
                  </a>
                </span>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="selectedlist">
              </div>
            </div>
          </div>
        </div>
        <div class="lui_custom_list_box_content_col_btn">
          <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
          <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>
        </div>
      </div>
    </div>
  </div>
  <!-- 主要内容 Ends -->
</div>
<script type="text/javascript">

	//display:显示项 condition:搜索项
	var type = '${param.type}';

	function initModelInfo(info){
		var modelIds = info.modelIds.split(";");
		var modelNames = info.modelNames.split(";");
		var rs = {};
		for(var i = 0;i < modelIds.length;i++){
			rs[modelIds[i]] = {"field" : modelIds[i],"text" : modelNames[i]};
		}
		return rs;
	}

	function cancle(){
		$dialog.hide();
	}

	function ok(){
		var colCfg = {};
		colCfg["selected"] = [];
		colCfg["text"] = [];
		var selectedItems = $("#selectedlist").find("[data-value]");
		for(var i = 0;i < selectedItems.length;i++){
			var cfg = {};
			var selectedItemDom = selectedItems[i];
			var item = selectPanel.getItemById($(selectedItemDom).attr("data-value"));
			cfg["field"] = item.field;

			//显示项
			if(type === "display"){
				var width = $(selectedItemDom).find(".selectedWidth").val();
				if(width){
					cfg["width"] = width;
				}
			}else
			//搜索项
			if(type === "condition"){
				//默认展开
				var isExpend = $(selectedItemDom).find(".isExpend").is(":checked");
				if(isExpend)
					cfg["expend"] = "true";
				//允许多选
				var isMuti = $(selectedItemDom).find(".isMuti").is(":checked");
				if(!isMuti)
					cfg["muti"] = "false";
			} else if(type === "normal"){
				cfg.type = item.type;
				cfg.customElement = item.customElement || {};
			}
			colCfg["selected"].push(cfg);
			colCfg["text"].push(item.text);
		}
		$dialog.hide(JSON.stringify(colCfg));
	}

	// 选择全部
	function selectAll(elem){
		$("#unselectedlist").find("[data-value]").trigger($.Event("click"));
	}

	// 取消全部
	function cancleAll(){
		$("#selectedlist").find(".lui_custom_list_box_btn_quit").trigger($.Event("click"));
	}

	// 上移
	function toUp(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		$div.prev().before($div);
	}

	// 下移
	function toDown(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		$div.next().after($div);
	}

	// 删除选项
	function unselectOption(dom){
		var $div = $(dom).closest(".lui_custom_list_box_content_col_content_line");
		var value = $div.attr("data-value");
		$("#unselectedlist").append(buildUnselectedOption(selectPanel.getItemById(value)));
		$div.remove();
		selectPanel.changeItem(value,"selected");
	}

	// 选择选项
	function selectOption(dom){
		var value = $(dom).attr("data-value");
		$("#selectedlist").append(buildSelectedOption(selectPanel.getItemById(value)));
		$(dom).remove();
		selectPanel.changeItem(value,"unselected");
	}

	function buildUnselectedOption(item){
		var html = "";
		html += '<div class="lui_custom_list_checkbox" data-value="'+ item.field +'" onclick="selectOption(this);">';
        html += '<label>';
        html += item.text;
        html +='</label>';
    	html += '</div>';
		return html;
	}

	function buildSelectedOption(item){
		var html = "";
		html += '<div data-value="'+ item.field +'" class="lui_custom_list_box_content_col_content_line" >';
        html += '<span>'+ (item.text || "") +'</span>';
        html += '<div class="lui_custom_list_box_content_col_content_left">';
        //显示项
        if(type === "display") {
	        html += '<span class="lui_custom_list_box_content_col_content_widthchange">';
	        html += '<input type="text" placeholder="宽度" class="selectedWidth" value="'+ (item.width || '') +'" />';
	      	html += '</span>';
        } else if(type === "condition") {
        	//搜索项
	        html += '<span class="lui_custom_list_box_content_col_content_chekcbox">';
	        //默认展开
	        html += '<label style="margin-right: 4px"><input type="checkbox" class="isExpend" '+ (item.expend ? 'checked' : '') +' />默认展开</label>';
	        //允许多选
	        html += '<label alt="只对部分类型有效"><input type="checkbox" class="isMuti" '+ (item.muti === 'false' ? '' : 'checked') +' />允许多选</label>';
	      	html += '</span>';
        } else if(type === "normal"){

        }

        html += '<span class="lui_custom_list_box_btn_up" onclick="toUp(this)"></span>';
        html += '<span class="lui_custom_list_box_btn_down" onclick="toDown(this)"></span>';
        html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);"></span>';
        html += '</div>';
        html += '</div>';
		return html;
	}

	function makeUpAttr(item,cfgs){
		for(var i = 0;i < cfgs.length;i++){
			var col = cfgs[i];
			if(col["field"] == item['field']){
				if(col["width"]){
					item["width"] = col["width"];
				}
				if(col["expend"]){
					item["expend"] = col["expend"];
				}
				if(col["muti"]){
					item["muti"] = col["muti"];
				}
			}
		}
	}

	function init(){
		/***************** 初始化 列数据 start **********************/
		// 处理所有选项
		var all = $dialog.___params.allField;
		if(!all){
			all = "[]";
		}

		if(typeof(all) === "string"){
			all = $.parseJSON(all.replace(/&quot;/g,"\""));
		}
		var allOptions = all;
		var items = {};
		for(var i = 0;i < allOptions.length;i++){
			var option = allOptions[i];
			//only属性用于限定显示字段
			if(!option["only"] || option["only"].indexOf(type) !== -1)
			    items[option["field"]] = option;
		}

        var colCfgs = null;
        var selected = "";
        // 处理现有值
        var currentValues = "";
        selected = $dialog.___params.selected;
        if(selected){
            try{
                colCfgs = $.parseJSON(selected.replace(/&quot;/g,"\""));
                for(var i = 0;i < colCfgs.length;i++){
                    var col = colCfgs[i];
                    if(items[col["field"]])
                        currentValues += col["field"] + ";";
                }
                if(currentValues.length > 0){
                    currentValues = currentValues.substring(0,currentValues.length - 1);
                }
            }catch(e){
                console.error("历史数据造成的报错，可忽略：selected=" + selected, e);
            }
        }

		/***************** 初始化 列数据 end **********************/
		// 全局变量
		selectPanel = new SelectPanel({items: items,currentValues:currentValues});
		// 画已选
		var selectedValues = selectPanel.selectedValues;
		var selectedHtml = "";
		for(var i = 0;i < selectedValues.length;i++){
			var selectedItem = $.extend({},selectPanel.getItemById(selectedValues[i]));
			// 初始化时，需要补全属性
			if(colCfgs){
				makeUpAttr(selectedItem, colCfgs);
			}
			selectedHtml += buildSelectedOption(selectedItem);
		}
		$("#selectedlist").append(selectedHtml);
		// 画待选
		var unselectedValues = selectPanel.unselectedValues;
		var unselectedHtml = "";
		for(var i = 0;i < unselectedValues.length;i++){
			unselectedHtml += buildUnselectedOption(selectPanel.getItemById(unselectedValues[i]));
		}
		$("#unselectedlist").append(unselectedHtml);
	}

	var interval = setInterval(beginInit, "50");
	function beginInit(){
		if(!window['$dialog'])
			return;
		clearInterval(interval);
		init();
	}

</script>
</body>
</html>
