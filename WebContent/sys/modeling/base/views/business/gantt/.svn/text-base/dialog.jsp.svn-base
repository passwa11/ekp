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
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/showFilters.css" />
</head>	
<body>
	<script>
		Com_IncludeFile("selectPanel.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", "js", true);
		Com_IncludeFile("dialog.js");
		Com_IncludeFile("jquery.dragsort.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
		Com_IncludeFile('calendar.js');
		Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
	</script>
<div class="lui_custom_list_container" style="overflow:hidden;">
  <!-- 主要内容 Starts -->
  <div class="lui_custom_list_box">
    <div class="lui_custom_list_box_content">
      <div class="lui_custom_list_box_content_container">
        <div class="lui_custom_list_box_content_row">
          <div class="lui_custom_list_box_content_col first_content_col" style="width: 30%; padding-right: 12px; box-sizing: border-box;">
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
          <div class="lui_custom_list_box_content_col second_content_col" style="width: 70%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word">${lfn:message('sys-modeling-base:modeling.common.selected') }</span>
                <span class="lui_custom_list_box_content_link_right">
                  <a href="javascript:void(0)" onclick="cancleAll()">
                     	${lfn:message('sys-modeling-base:modeling.common.cancleAll') }
                  </a>
                </span>
              </div>
              <div id="selectedlist_title"></div>
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
		if(!_localValidation()){
			return;
		}
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
				cfg["type"] = item.type;
				cfg["businessType"] = item.businessType;
				var dictEnums = selectPanel.getEnumsById(item.field);
				if(item.businessType === "inputCheckbox" || item.businessType === "select" ||  item.businessType === "fSelect" || item.businessType === "inputRadio"){
					if(typeof(dictEnums.enumValues) != "undefined"){
						cfg["enumValues"] = dictEnums.enumValues;
					}
				}
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
				var defaultValue = $(selectedItemDom).find(".defaultValue").val();
				//地址本特殊处理
				if(item.type.startsWith("com.landray.kmss.sys.organization")){
					var defaultValueText = $(selectedItemDom).find(".defaultValueText").val();
					var defaultValues = defaultValue.split(";");
					var defaultValueTexts = defaultValueText.split(";");
					var defaultValueArr = [];
					if(defaultValues != "" || defaultValueTexts != ""){
						for(var j = 0;j < defaultValues.length;j++){
							var defaultValueObj = {
									text: defaultValueTexts[j],
									title: "",
									value: defaultValues[j]
							};
							defaultValueArr.push(defaultValueObj);
						}  
					}
					defaultValue = defaultValueArr;
				}
				//多选、单选、下拉框、文档状态
				else if(item.businessType === "inputCheckbox" || item.businessType === "select"  || item.businessType === "fSelect"){
					var defaultValueArr = [];
					if(mValue.hasOwnProperty(item.field)){
						var dataArr = mValue[item.field];
						if(JSON.stringify(dataArr) != "[]"){
							for(var j = 0;j < dataArr.length;j++){
								defaultValueArr.push(dataArr[j]);
							}
							defaultValue = defaultValueArr;
						}
					}
					cfg["businessType"] = item.businessType;
				}
				//金额，数字
				else if(item.type === "BigDecimal" || item.type === "Double"){
					var defaultValue1 = $(selectedItemDom).find(".defaultValue1").val();
					var defaultValue2 = $(selectedItemDom).find(".defaultValue2").val();
					var defaultValueArr = [{
						text: defaultValue1,
						value: defaultValue1,
					},
					{
						text: defaultValue2,
						value: defaultValue2,
					}];
					defaultValue = defaultValueArr;
				}else if(item.type.toLowerCase() === "time"){
					//时间
					var defaultValue1 = $(selectedItemDom).find(".defaultValue1").val();
					var defaultValue2 = $(selectedItemDom).find(".defaultValue2").val();
					var defaultValueArr = [defaultValue1,defaultValue2];
					defaultValue = defaultValueArr;
				}else if(item.field === "docStatus"){
					//文档状态
					var defaultValueArr = [];
					defaultValue = $(selectedItemDom).find(".s_option_list").find("[class='active']").attr("value")||"";
					if(defaultValue != null){
						var defaultValueText = $(selectedItemDom).find(".s_select_txt").text();
						var defaultValueObj = {
								text: defaultValueText,
								value: defaultValue
						};
						defaultValueArr.push(defaultValueObj);
						defaultValue = defaultValueArr;
					}
					cfg["businessType"] = item.businessType;
				}else if(item.type.toLowerCase() === "datetime" || item.type.toLowerCase() === "date" || item.businessType === "inputRadio"){
					defaultValue = $(selectedItemDom).find(".s_option_list").find("[class='active']").attr("value") || "";
				}
				cfg["defaultValue"] = defaultValue;
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
	
	function drawTableHead(){
		var html = "";
		html += '<div class="lui_custom_list_box_content_col_content_head">';
		html += '<div style="display:inline-block;width:20%;" class="filterItem">筛选项</div>';
		html += '<div style="display:inline-block;width:15%;" class="expandItem">默认展开</div>';
		html += '<div style="display:inline-block;width:15%;" class="muiItem">允许多选</div>';
		html += '<div style="display:inline-block;width:43%;" class="defaultValeItem">默认值</div>';
		html += '</div>';
		return html;
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
		html += '<div data-value="'+ item.field +'" class="lui_custom_list_box_content_col_content_line"  id="defaultValueItem_'+item.field+'" >';
        html += '<div style="display:inline-block;width:20%;" class="drag_item" title="'+(item.text || "")+'"><span>'+ (item.text || "") +'</span></div>';
        //显示项
        if(type === "display") {
        	html += '<div class="lui_custom_list_box_content_col_content_left">';
	        // html += '<span class="lui_custom_list_box_content_col_content_widthchange">';
	        // html += '<input type="text" placeholder="宽度1111" class="selectedWidth" value="'+ (item.width || '') +'" />';
	      	// html += '</span>';
	      	html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);"></span>';
        } else if(type === "condition") {
        	//搜索项
	        //默认展开
	        html += '<div style="display:inline-block;width:15%;" class="expandItem">';
	        html += '<label style="margin-right: 4px;"><input type="checkbox" class="isExpend" '+ (item.expend ? 'checked' : '') +' /></label>';
	        html += '</div>';
	        
	        var ismulti = false;
	        if(item.businessType === "inputCheckbox" || item.businessType === "select" || item.businessType === "fSelect" || item.businessType === "inputRadio"){
	        	ismulti = true;
	        }
	        if(item.type.startsWith("com.landray.kmss.sys.organization") || ismulti){
	        	 //允许多选(地址本、多选框等)
		        html += '<div style="display:inline-block;width:15%;" class="muiItem">';
		        html += '<label alt="只对部分类型有效"><input type="checkbox" class="isMuti" '+ (item.muti === 'false' ? '' : 'checked') +' /></label>';
		        html += '</div>';
	        }else{
	        	html += '<div style="display:inline-block;width:15%;" class="muiItem">';
		        html += '</div>';
	        }
	        
	        //默认值回显
	        var tmpHtml = DefaultValueHtml(item);
	        var $tmphtml = $(tmpHtml);
	        if(item.field == "docStatus"){
	        	$tmphtml.find(".s_option_list li").each(function(){
	        		if(item.defaultValue[0]){
	        			if(item.defaultValue[0].value == $(this).attr("value")){
							$(this).addClass("active");
							$tmphtml.find(".s_select_txt").text($(this).text());
						}
	        		}
				})
	        }else if(item.businessType === "inputCheckbox" || item.businessType === "select" || item.businessType === "fSelect"){
	        	if(item.defaultValue != ""){
	        		mValue[item.field] = item.defaultValue;
	        	}
	        	var tempdataArr = item.defaultValue;
				for(var j = 0;j < tempdataArr.length;j++){
					$tmphtml.find(".m_option_list li").each(function(){
						if($(this).text() == tempdataArr[j].text){
							$(this).addClass("active");
							var mValList = "<span class='m_option_list_item'>" + tempdataArr[j].text + "</span>";
				           $(this).closest(".defaultValue").find(".m_select_txt").append(mValList);
						}
					}) 
				} 
	        }else{
	        	$tmphtml.find(".s_option_list li").each(function(){
					if(item.defaultValue == $(this).attr("value")){
						$(this).addClass("active");
						$tmphtml.find(".s_select_txt").text($(this).text());
					}
				})
	        }
			html += $tmphtml.html();
	        html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);" style="float:right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        } else if(type === "normal"){
            html += '<div class="lui_custom_list_box_content_col_content_left">';
        	//搜索项
	        html += '<span class="lui_custom_list_box_content_col_content_chekcbox">';
	        //默认展开
	        //html += '<label style="margin-right: 4px"><input type="checkbox" class="isExpend" '+ (item.expend ? 'checked' : '') +' />默认展开</label>';
	        //允许多选
	        //html += '<label alt="只对部分类型有效"><input type="checkbox" class="isMuti" '+ (item.muti === 'false' ? '' : 'checked') +' />允许多选</label>';
	        html += '<span class="lui_custom_list_box_btn_quit" onclick="unselectOption(this);" style="float:right; top:1px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        }
        
        /* html += '<span class="lui_custom_list_box_btn_up" onclick="toUp(this)"></span>';
        html += '<span class="lui_custom_list_box_btn_down" onclick="toDown(this)"></span>'; */
        html += '</div>';
        html += '</div>';
		return html;
	}
	
	//默认值
	function DefaultValueHtml(item,ismuti){
		var name = item.field.replace(".","_");
		var valueType = item.type;
		var valueBsType = item.businessType;
		var html="";
		if(typeof(ismuti) == "undefined"){
			ismuti = true;
		}
		if(typeof(item.defaultValue) == "undefined"){
			item.defaultValue = "";
		}
		html += '<div class="DefaultValueHtml" style="display:initial;">';
		if(valueBsType === "inputCheckbox" || valueBsType === "select" || valueBsType === "fSelect"){
			var dictEnums = selectPanel.getEnumsById(item.field);
			//多选框或下拉列表
			html += '<div style="display:inline-block;width:41.3%;" class="defaultValueItem">'
			html += '<div class="m_select_box defaultValue" data-mark="'+item.field+'">';
            html += '    <div class="m_select_txt"></div>';
            html += '       <ul class="m_option_list">';
            for(var i = 0;i < dictEnums.enumValues.length; i++){
				html += '		<li value="'+dictEnums.enumValues[i].fieldEnumValue+'">'+dictEnums.enumValues[i].fieldEnumLabel+'</li>';
            }
            html += '		</ul>';
        	html += '	</div>';
			html += '</div>';
			
		}else if(valueBsType === "inputRadio"){
			//单选按钮
			var dictEnums = selectPanel.getEnumsById(item.field);
			html += '<div style="display:inline-block;width:41.3%;" class="defaultValueItem">'
			html += '<div class="s_select_box defaultValue" placeholder="请选择">';
            html += '    <div class="s_select_txt"></div>';
            html += '       <ul class="s_option_list">';
            for(var i = 0;i < dictEnums.enumValues.length; i++){
				html += '		<li value="'+dictEnums.enumValues[i].fieldEnumValue+'">'+dictEnums.enumValues[i].fieldEnumLabel+'</li>';
            }
            html += '		</ul>';
        	html += '	</div>';
			html += '</div>';
		}else if(valueType === "Date" || valueType ==="DateTime"){
			//日期
			html += '<div style="display:inline-block;width:41.3%;" class="defaultValueItem">'
			html += '<div class="s_select_box defaultValue">';
            html += '    <div class="s_select_txt"></div>';
            html += '       <ul class="s_option_list">';
            html += '			<li value="1">近一周</li>';
            html += '			<li value="2">近一个月</li>';
            html += '			<li value="3">近三个月</li>';
            html += '			<li value="4">近半年</li>';
            html += '			<li value="5">近一年</li>';
            html += '		</ul>';
        	html += '	</div>';
			html += '</div>';
			html += '</div>';
		}else if(valueType === "Time" ){
			//时间
			var value1 = "";
			var value2 = "";
			var valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
			if(item.defaultValue != null && item.defaultValue != ""){
				value1 = item.defaultValue[0];
				value2 = item.defaultValue[1];
			}
			//时间类型
			html += '<div style="display:inline-block;width:43%;" class="defaultValueItem">';
	        html += '<input type="text" modeling-validation="defaultValue1_'+valueName+'; 请输入有效的时间;time;name:defaultValue1_'+valueName+';id:defaultValueItem_'+item.field+'" class="defaultValue1" id="defaultValue1_'+valueName+'" name="defaultValue1_'+valueName+'" onclick="selectTimeDefaultValue(event,this)" style="width:43%;" value="'+value1+'" placeholder="请选择"/>';
	        html += '-';
	        html += '<input type="text" modeling-validation="defaultValue2_'+valueName+'; 请输入有效的时间;time;name:defaultValue2_'+valueName+';id:defaultValueItem_'+item.field+'" class="defaultValue2" id="defaultValue2_'+valueName+'"  name="defaultValue2_'+valueName+'" onclick="selectTimeDefaultValue(event,this)" style="width:43%;" value="'+value2+'" placeholder="请选择"/>';
	        html += '</div>';
		}else if(valueType === "BigDecimal" || valueType === "Double"){
			var value1 = "";
			var value2 = "";
			if(item.defaultValue != null && item.defaultValue != ""){
				value1 = item.defaultValue[0].value;
				value2 = item.defaultValue[1].value;
			}
			//文本输入类(数字、金额)
	        html += '<div style="display:inline-block;width:43%;" class="defaultValueItem">';
	        html += '<input type="number" class="defaultValue1" name="defaultValue1"  style="width:43%;" value="'+value1+'" placeholder="请输入"/>';
	        html += '-';
	        html += '<input type="number" class="defaultValue2" name="defaultValue2" style="width:43%;" value="'+value2+'" placeholder="请输入"/>';
	        html += '</div>';
		}else if(valueType.startsWith("com.landray.kmss.sys.organization")){
			//地址本
			var textStr = "";
			var valueStr = "";
			var orgSelectType = "ORG_TYPE_ALL";
			if(item.customElement != null && item.customElement.orgType != ""){
				//由于筛选器的限制，做过滤
				if(item.customElement.orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST|ORG_TYPE_PERSON"){
					orgSelectType = "ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST";
				}
				else if(item.customElement.orgType === "ORG_TYPE_POST|ORG_TYPE_PERSON"){
					orgSelectType = "ORG_TYPE_POST";
				}else {
					orgSelectType = item.customElement.orgType;
				}
				if(valueType === "com.landray.kmss.sys.organization.model.SysOrgElement"){
					//部门现仅单选
					ismuti = false;
				}
			}else if(valueType.endsWith("SysOrgPerson")){
				orgSelectType = "ORG_TYPE_PERSON";
			} 
			if(item.defaultValue != null){
				for(var i = 0;i < item.defaultValue.length;i++){
					textStr += item.defaultValue[i].text;
					valueStr += item.defaultValue[i].value;
					if(i != item.defaultValue.length - 1){
						textStr += ";";
						valueStr += ";";
					}
				}
			}
			html += '<div style="display:inline-block;width:41.3%;" class="defaultValueItem" onclick="Dialog_Address('+ismuti+',\''+name+'\',\''+name+'_name\''+',\';\','+orgSelectType+')">';
	        html += '<input type="text" class="defaultValueText"  name="'+name+'_name" value="'+textStr+'" placeholder="请选择">';
	        html += '<input type="hidden" class="defaultValue"  name="'+name+'" value="'+valueStr+'">';
	        html += '</div>'; 
		}else if(item.field === "docStatus"){
			//文档状态（有流程）
			html += '<div style="display:inline-block;width:43%;" class="defaultValueItem">'
			html += '<div class="s_select_box defaultValue">';
            html += '    <div class="s_select_txt"></div>';
            html += '       <ul class="s_option_list">';
            html += '			<li value="10">草稿</li>';
            html += '			<li value="20">待审</li>';
            html += '			<li value="11">驳回</li>';
            html += '			<li value="00">废弃</li>';
            html += '			<li value="30">结束</li>';
            html += '			<li value="31">已反馈</li>';
            html += '		</ul>';
        	html += '	</div>';
			html += '</div>';
			html += '</div>';
		}else{
			//文本输入类
	        html += '<div style="display:inline-block;width:41.3%;" class="defaultValueItem">';
	        html += '<input type="text" class="defaultValue" name="defaultValue" value="'+item.defaultValue+'" placeholder="请输入">';
	        html += '</div>';
		}
		html += "</div>";
       return html;
	}
	function selectTimeDefaultValue(event,e){
		 $(e).keyup(function(){    
	             $(e).val($(e).val().replace(/[^0-9:]/g,''));    
	     }).bind("paste",function(){  //CTR+V事件处理    
	             $(e).val($(e).val().replace(/[^0-9:]/g,''));     
	     }).css("ime-mode", "disabled"); //CSS设置输入法不可用    
        $(e).blur(function(){
        	_localValidation();
        })
		selectTime(event,$(e));
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
				if(col["defaultValue"]){
					item["defaultValue"] = col["defaultValue"]; 
				}
			}
		}
	}
	
	function multiSelect(e){
		var mark = $(e).closest(".defaultValue").attr("data-mark");
        if ($(e).hasClass("active")) {
            $(e).removeClass("active");
            var text = $(e).text();
            mValue[mark] = mValue[mark].filter(function(obj){
                return text !== obj.text;
            });
        }else {
            $(e).addClass("active");
            if(!mValue[mark]){
            	mValue[mark] = [];
            }
            var tempObj = {
            	text: $(e).text(),
            	value: $(e).attr("value")
            }
            mValue[mark].push(tempObj);
        }
        $(e).closest(".m_select_box").find(".m_select_txt").empty();
        var tempArr = mValue[mark];
        for (var i = 0; i < tempArr.length; i++) {
            var mValList = "<span class='m_option_list_item'>" + tempArr[i].text + "</span>";
            $(e).parent().siblings(".m_select_txt").append(mValList);
		}	
	}
	
	function init(){
		/***************** 初始化 列数据 start **********************/
		// 处理所有选项
		var all = $dialog.___params.allField;
		var modelDict = $dialog.___params.modelDict;
		console.log(all)
        console.log(modelDict)
		var dataModel = null;
		var enumData = {};
		if(modelDict){
			try{
				modelDict = modelDict.replace(/\n/g,"\\\\n");
                //转义mac \r\n换行符
                modelDict = modelDict.replace(/\r/g,"\\\\r");
				dataModel = $.parseJSON(modelDict.replace(/&quot;/g,"\""));
				for(var i = 0 ;i < dataModel.length;i++){
					if(dataModel[i].fieldType === "enum"){
						enumData[dataModel[i]["field"]] = dataModel[i];
					}
				}
			}catch(e){
				console.error("默认值：modelDict报错", e);
			}
		}
		if(!all){
			all = "[]";
		}
		if(typeof(all) === "string"){
			//转义换行符
			all = all.replace(/\n/g,"\\\\n");
            //转义mac \r\n换行符
            all = all.replace(/\r/g,"\\\\r");
			all = $.parseJSON(all.replace(/&quot;/g,"\""));
		}
		//#123026：移动端筛选项屏蔽数字控件，因为现在移动组件还不支持数字类型
        if(type === "normal"){
        	all = preSelected(all);
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
        //#120818:筛选项选择创建者不回显
        selected = selected.replace("docCreator","docCreator.fdName");
        if(selected){
            try{
            	selected = selected.replace(/\n/g,"\\\\n");
                //转义mac \r\n换行符
                selected = selected.replace(/\r/g,"\\\\r");
                colCfgs = $.parseJSON(selected.replace(/&quot;/g,"\""));
                //#123026：移动端筛选项屏蔽数字控件，因为现在移动组件还不支持数字类型
                if(type === "normal"){
                	colCfgs = preSelected(colCfgs);
                }
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
		selectPanel = new SelectPanel({items: items,currentValues:currentValues,dictEnums:enumData});
		// 画已选
		var selectedValues = selectPanel.selectedValues;
		var selectedHtml = "";
		$("#selectedlist_title").append(drawTableHead());
		if(type == "display" || type == "normal"){		//type=normal是移动端，现在移动端还不支持设置默认值
			$("#selectedlist_title").css("display", "none");
			$("#selectedlist").css({"padding-top":"0","margin-top":"0"});
		}
		$("#selectedlist").append(drawTableHead());
		$("#selectedlist .lui_custom_list_box_content_col_content_head").css("display", "none");
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
		/* 筛选项和显示项拖动排序 */
		setTimeout(function(){ 
			$("#selectedlist").dragsort({
		 	        dragSelector: ".lui_custom_list_box_content_col_content_line",
		 	        dragSelectorExclude: "input",
		 	        dragEnd: dragEnd,
		 	        dragBetween: true,
		 	        placeHolderTemplate: "<div></div>"
		 	}); 
			
	 		function dragEnd() {
	 	         var $this = $(this);
	 	         var data = $("#selectedlist").each(function () {
	 	             return $(this).children().html();
	 	         }).get();
	 	    }
		}, 1000); 
	}

	var interval = setInterval(beginInit, "50");
	function beginInit(){
		if(!window['$dialog'])
			return;
		clearInterval(interval);
		init();
	}
	
	 /*全局变量，存放复选下拉框的值
	 	格式：{field:[{text,value}]}
	 */
    var mValue = {};
	Com_AddEventListener(window,"load",function(){
		//“允许多选”值变化重画
 		$(document).on("click",".isMuti",function(){
 			if(type != "normal"){
 				$(this).closest(".lui_custom_list_box_content_col_content_line").find(".defaultValueItem").remove();
 	 			var dataValue = $(this).closest(".lui_custom_list_box_content_col_content_line").attr("data-value");
 	 			var isMuti = $(this).closest(".lui_custom_list_box_content_col_content_line").find(".isMuti").is(":checked");
 	 			$(this).closest(".lui_custom_list_box_content_col_content_line").append(DefaultValueHtml(selectPanel.getItemById(dataValue),isMuti));
 	 			//重画清空掉上一次已选值、
 	 			if(mValue[dataValue]){
 	 				mValue[dataValue] = [];
 	 			}
 			}
 		});
 		$(document).on("mouseenter",".lui_custom_list_box_content_col_content_line", function() {
            $(this).addClass("drag");
 		});
 		
 		$(document).on("mouseleave",".lui_custom_list_box_content_col_content_line", function() {
            $(this).removeClass("drag");
 		});
 		
 		//单选下拉框
 		$(document).on("click",".s_select_box", function (event) {
            event.stopPropagation();
            if($(this).find(".s_option_list").css("display") == "block") {
                $(this).find(".s_option_list").css("display", "none");
            } else {
                $(this).find(".s_option_list").css("display", "block")
            }
        });
 		 /*赋值给文本框*/
 		$(document).on("click",".s_option_list li", function () {
            var sValue = $(this).text();
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
            $(this).closest(".s_select_box").find(".s_select_txt").text(sValue);
            $(this).closest(".s_select_box").find(".s_select_value").val(sValue);
        });
 		 
 		// 多选框 starts
       $(document).on("click",".m_select_box",function (event) {
            event.stopPropagation();
            $(this).find(".m_option_list").css("display", "block");
        });
       /*赋值给文本框*/
        $(document).on("click",".m_option_list li",function () {
        	multiSelect(this);
        })
    	// 多选框 ends
 		// 点击其他地方关闭下拉列表
        $(document).click(function () {
          $(".s_option_list").each(function(){
        		 if ($(this).css("display") == "block") {
                     $(this).css("display", "none");
                 }
        	})
           $(".m_option_list").each(function(){
        	   if ($(this).css("display") == "block") {
                   $(this).css("display", "none");
               }
           })
        })
        
        $(document).on("mouseleave",".m_option_list",function(){
	          $(".m_option_list").each(function(){
	       	   if ($(this).css("display") == "block") {
	                  $(this).css("display", "none");
	              }
	          })
        })
        
        $(document).on("mouseleave",".s_option_list",function(){
	          $(".s_option_list").each(function(){
	       		 if ($(this).css("display") == "block") {
	                    $(this).css("display", "none");
	                }
	       	  })
        })
        
        //点击多选气泡删除
        $(document).on("click",".m_option_list_item",function(){
        	var mark = $(this).closest(".defaultValue").attr("data-mark");
        	var text = $(this).text();
        	var self = this;
        	var dataArr = mValue[mark];
        	$(this).closest(".defaultValue").find(".m_option_list li").each(function(){
        		if($(this).text() == text){
        			 $(this).removeClass("active");
        			 mValue[mark] = mValue[mark].filter(function(obj){
                        return text !== obj.text;
                    });
        			$(self).remove();
        		}
        	}) 
        })
 	});
	
	function preSelected(colCfgs){
		var newselected = [];
		for(var i = 0;i < colCfgs.length;i++){
             var col = colCfgs[i];
             if(col["type"] == "BigDecimal" || col["type"] == "Double"){
            	 continue;
             }else{
            	 newselected.push(col);
             }
        }
		return newselected;
	}
 	
</script>
</body>
</html>
