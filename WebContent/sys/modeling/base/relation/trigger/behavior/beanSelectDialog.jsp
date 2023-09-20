<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
    <link href="${LUI_ContextPath}/sys/modeling/base/relation/trigger/behavior/css/beanSelectDialog.css" rel="stylesheet">
</head>
<body>
	<script>
		Com_IncludeFile("dialog.js");
		Com_IncludeFile('calendar.js');
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
                <span class="lui_custom_list_box_content_header_word">选择</span>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="selectContent">
              </div>
            </div>
          </div>
          <div class="lui_custom_list_box_content_col second_content_col" style="width: 70%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word">说明</span>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="descriptionContent">
	              
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
    var selectOptionList=[
        {sign:"insertEco",name:"写入生态组织",value:"saveEcoElementTriggerAction",description:"将组织/岗位信息通过触发自动写入指定生态组织架构中。"},
        {sign:"updateEco",name:"更新生态组织",value:"saveEcoElementTriggerAction",description:"将组织/岗位/人员信息通过触发自动更新至指定生态组织架构中。"}
    ];
    var returnValue = {};
    seajs.use(["lui/dialog", "lui/topic","lui/jquery"], function (dialog, topic,$) {
        //初始化数据
        function init() {
          var $selectContent = $('#selectContent');
          for (var i = 0; i < selectOptionList.length; i++) {
            var option = selectOptionList[i];
            var html = '<div class="lui_custom_list_checkbox" onclick="selectOption(this,\''+option.sign+'\');"><label>'+option.name+'</label></div>'
            $selectContent.append(html);
          }
        }
        init();
    });

    function selectOption(thisObj,sign){
      $('.lui_custom_list_checkbox').removeClass('active');
      $(thisObj).addClass('active');
      var $descriptionContent = $('#descriptionContent');
      $descriptionContent.empty();
      for (var i = 0; i < selectOptionList.length; i++) {
        var option = selectOptionList[i];
        if(option.sign == sign){
          var html = '<div><label>'+option.description+'</label></div>';
          $descriptionContent.append(html);
          returnValue.value = option.value;
          returnValue.sign = option.sign;
          returnValue.name = '【'+option.name+'】';
        }
      }
    }

    function cancle(){
      $dialog.hide();
    }

    function ok(){
      $dialog.hide(returnValue);
    }
 	
</script>
</body>
</html>
