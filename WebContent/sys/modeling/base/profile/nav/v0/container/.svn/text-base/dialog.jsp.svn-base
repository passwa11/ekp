<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
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
            <div class="lui_custom_list_box_content_col">
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
            <div class="lui_custom_list_box_content_col">
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
            <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>
            <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
          </div>
        </div>
      </div>
    </div>
    <!-- 主要内容 Ends -->
  </div>
  
<script type="text/javascript">
	
	function initModelInfo(info){
		var listviewIds = info.listviewIds.split(";");
		var listviewNames = info.listviewNames.split(";");
		var rs = {};
		for(var i = 0;i < listviewIds.length;i++){
			rs[listviewIds[i]] = {"value" : listviewIds[i],"text" : listviewNames[i]};
		}
		return rs;
	}
	
	function cancle(){
		$dialog.hide();
	}
	
	function ok(){
		// selectPanel.getSelectedInfo() 由于支持排序，确定时实时取
		var selectedItems = $("#selectedlist").find("[data-value]");
		var rs = {};
		rs.listviewIds = "";
		rs.listviewNames = "";
		for(var i = 0;i < selectedItems.length;i++){
			var selectedItem = selectedItems[i];
			var item = selectPanel.getItemById($(selectedItem).attr("data-value"));
			rs.listviewIds += item.value + ";";
			rs.listviewNames += item.text + ";";
		}
		if(rs.listviewIds.length > 0){
			rs.listviewIds = rs.listviewIds.substring(0,rs.listviewIds.length - 1);
			rs.listviewNames = rs.listviewNames.substring(0,rs.listviewNames.length - 1);
		}
		$dialog.hide(rs);
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
		html += '<div class="lui_custom_list_checkbox" data-value="'+ item.value +'" onclick="selectOption(this);">';
        html += '<label>';
        html += item.text;
        html +='</label>';
    	html += '</div>';
		return html;
	}
	
	function buildSelectedOption(item){
		var html = "";
		html += '<div data-value="'+ item.value +'" class="lui_custom_list_box_content_col_content_line" >';
        html += '<span>'+ item.text +'</span>';
        html += '<div class="lui_custom_list_box_content_col_content_left">';
        html += '<span class="lui_custom_list_box_btn_up" onclick="toUp(this)"></span>';
        html += '<span class="lui_custom_list_box_btn_down" onclick="toDown(this)"></span>';
        html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);"></span>';
        html += '</div>';
        html += '</div>';
		return html;
	}
	
	function ____Interval(){
		if (!window['$dialog'])
			return;
		// 全局变量
		window.selectPanel = new SelectPanel({items: initModelInfo($dialog["___params"].__LISTVIEWINFO),currentValues:"${param.curListviewIds}"});
		// 画已选
		var selectedValues = selectPanel.selectedValues;
		var selectedHtml = "";
		for(var i = 0;i < selectedValues.length;i++){
			selectedHtml += buildSelectedOption(selectPanel.getItemById(selectedValues[i]));
		}
		$("#selectedlist").append(selectedHtml);
		// 画待选
		var unselectedValues = selectPanel.unselectedValues;
		var unselectedHtml = "";
		for(var i = 0;i < unselectedValues.length;i++){
			unselectedHtml += buildUnselectedOption(selectPanel.getItemById(unselectedValues[i]));
		}
		$("#unselectedlist").append(unselectedHtml);
		
		clearInterval(interval);
	}
	
	function init(){
		window.interval = setInterval(____Interval, "50");
	}

	Com_AddEventListener(window,'load',init);	
</script>
</body>
</html>
