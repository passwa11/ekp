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
                <span class="lui_custom_list_box_content_header_word">表单字段</span>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="selectContent">
              </div>
            </div>
          </div>
          <div class="lui_custom_list_box_content_col second_content_col" style="width: 40%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word">公式</span>
              </div>
              <div class="lui_custom_list_box_content_col_content" id="editContent">
                <textarea name="beanCfg" validate="maxLength(2000)" style="word-break: break-all;width:100%;height: 100%;border: 0"></textarea>
              </div>
            </div>
          </div>
          <div class="lui_custom_list_box_content_col third_content_col" style="width: 30%">
            <div class="item">
              <div class="lui_custom_list_box_content_col_header">
                <span class="lui_custom_list_box_content_header_word">示例</span>
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
    var optionList=[
        { sign:"insertEco",
          name:"写入生态组织",
          formula:"{\"method\":\"create\",\"name\":\"$组织名称$\",\"parentId\":\"$父级组织ID$|值\",\"parentName\":\"$父级组织名称$|值\",\"type\":\"dept|post\"}",
          demo:"{\"method\":\"create\",\"name\":\"$组织名称$\",\"parentId\":\"$父级组织ID$|值\",\"parentName\":\"$父级组织名称$|值\",\"type\":\"dept|post\"}",
          description:"将组织/岗位 等信息通过触发自动写入指定生态组织架构中。例如：",
          remark:"备注:parentId和parentName至少选填一个，优先匹配parentId"
        },
        { sign:"updateEco",
          name:"更新生态组织",
          formula:"{\"method\":\"update\",\"primaryKey\":\"$组织名称$\",\"parentId\":\"$父级组织ID$|值\",\"parentName\":\"$父级组织名称$|值\",\"type\":\"dept|post|person\",\"posts\":[{\"id\":\"$岗位1ID$|值\",\"name\":\"$岗位1名称$|值\"},{\"id\":\"$岗位2ID$|值\",\"name\":\"$岗位2名称$|值\"}]}",
          demo: "修改组织的父级组织<br>"+
                "{\"method\":\"update\", \"primaryKey\":\"$子组织ID$|值\", \"parentId\":\"$父级组织ID$|值\", \"parentName\":\"$父级组织名称$|值\", \"type\":\"dept\"}<br>"+
                "<br>修改岗位的父级组织<br>"+
                "{\"method\":\"update\",\"primaryKey\":\"$岗位ID$|值\",\"parentId\":\"$父级组织ID$|值\",\"parentName\":\"$父级组织名称$|值\",\"type\":\"post\"}<br>"+
                "<br>修改人员的父级组织和岗位<br>"+
                "{\"method\":\"update\", \"primaryKey\":\"$人员ID$\", \"parentId\":\"$父级组织ID$|值\", \"parentName\":\"$父级组织名称$|值\", \"type\":\"person\", \"posts\":[{\"id\":\"$岗位1ID$|值\",\"name\":\"$岗位1名称$|值\"},{\"id\":\"$岗位2ID$|值\",\"name\":\"$岗位2名称$|值\"}]}",
          description:"将组织/岗位/人员 等信息通过触发自动更新至指定生态组织架构中。例如：",
          remark:"<br>备注:primaryKey必填，parentId和parentName至少选填一个，优先匹配parentId，posts选填"
        }
    ];
    seajs.use(["lui/dialog", "lui/topic","lui/jquery"], function (dialog, topic,$) {
        //监听数据传入
        var _param;
        var intervalEndCount = 10;
        var interval = setInterval(__interval, "50");

        function __interval() {
          if (intervalEndCount == 0) {
            console.error("数据解析超时。。。");
            clearInterval(interval);
          }
          intervalEndCount--;
          if (!window['$dialog']) {
            return;
          }
          _param = $dialog.___params;
          init(_param);
          clearInterval(interval);
        }

        //初始化数据
        function init(param) {
          var sign = param.sign;
          var targetData = param.targetData;
          var editContentText = param.editContentText;
          var selfData = param.selfData;
          //表单字段
          var $selectContent = $('#selectContent');
          for (var controlId in targetData) {
            //过滤明细表
            if (controlId.indexOf(".") > 0) {
              continue;
            }
            var option = targetData[controlId];
            var title = option.fullLabel?option.fullLabel:option.label;
            var html = '<div class="lui_custom_list_checkbox" onclick="selectOption(this,\''+title+'\');" title='+title+'><label>'+option.label+'</label></div>'
            $selectContent.append(html);
          }

          var $descriptionContent = $('#descriptionContent');
          for (var i = 0; i < optionList.length; i++) {
            var option = optionList[i];
            if(option.sign == sign){
              //公式
              var $beanCfg = $('[name="beanCfg"]');
              if(!selfData && !editContentText){
                $beanCfg.val(option.formula);
              }else{
                $beanCfg.val(editContentText);
              }
              //示例
              var html = '<div><label>'+option.description+'</label><label>'+option.demo+'</label><label style="color:red">'+option.remark+'</label></div>';
              $descriptionContent.append(html);
              break;
            }
          }
        }
    });

    function selectOption(thisObj,value){
      $('.lui_custom_list_checkbox').removeClass('active');
      $(thisObj).addClass('active');
      var $beanCfg = $('[name="beanCfg"]');
      var str =  "$" + value + "$";
      $beanCfg.each(function() {
        var tb = $(this);
        tb.focus();
        if (document.selection) {
          var r = document.selection.createRange();
          document.selection.empty();
          r.text = str;
          r.collapse();
          r.select();
        } else {
          var newstart = tb.get(0).selectionStart + str.length;
          tb.val(tb.val().substr(0, tb.get(0).selectionStart) +
                  str + tb.val().substring(tb.get(0).selectionEnd));
          tb.get(0).selectionStart = newstart;
          tb.get(0).selectionEnd = newstart;
        }
      })
    }

    function cancle(){
      $dialog.hide();
    }

    function ok(){
      $dialog.hide($('[name="beanCfg"]').val());
    }
 	
</script>
</body>
</html>
