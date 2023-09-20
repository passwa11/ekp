<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>里程碑</title>
  <link rel="stylesheet" href="../css/milepost/index.css">
    <link rel="stylesheet" href="../css/common.css">
</head>
<body>
  <div class="model-gantt-milepost">
    <div class="milepost-dialog">
<%--      <div class="milepost-dialog-head">--%>
<%--        <p>编辑里程碑</p>--%>
<%--        <i class="close-dialog"></i>--%>
<%--      </div>--%>
      <div class="milepost-dialog-container">
        <div class="milepost-dialog-container-box">
          <div class="milepost-dialog-container-item">
            <p class="container-item-title">${ lfn:message('sys-modeling-main:modelingGantt.name') }</p>
            <div class="container-item-content name">
              <input type="text" placeholder="${ lfn:message('sys-modeling-main:modelingGantt.enter.name') }" id="titleVal">
            </div>
          </div>
          <div class="milepost-dialog-container-item">
            <p class="container-item-title">${ lfn:message('sys-modeling-main:modelingGantt.location') }</p>
            <div class="container-item-content position">
              <div class="type-select">
                <div class="select-box">
                  <input id="select-txt" placeholder="${ lfn:message('sys-modeling-main:modeling.please.select') }" readonly="readonly">
                  <i class="select-arrow"></i>
                  <ul class="option-list">
                      <li class="option-list-item" key="time">${ lfn:message('sys-modeling-main:modelingGantt.project.date') }</li>
                      <li class="option-list-item" key="per">${ lfn:message('sys-modeling-main:modelingGantt.percentage') }</li>
                  </ul>
              </div>
              </div>
              <div class="position-vlaue">
                <input type="text" id="positionVal" name="positionVal" validate="__dateTime">
                <span class="positionTab"></span>
              </div>
              <div class="project-time">
                <i></i>
                ${ lfn:message('sys-modeling-main:modelingGantt.project.time') }：<span>2020/08/08-2020/09/09</span></div>
            </div>
          </div>
          <div class="milepost-dialog-container-item">
            <p class="container-item-title" style="margin-top: 11px">${ lfn:message('sys-modeling-main:modelingGantt.icon') }</p>
            <div class="container-item-content icon">
              <div class="showIcon"><i class="flag"></i></div>
              <div class="changeIcon"><span>${ lfn:message('sys-modeling-main:modelingGantt.change.icon') }</span></div>
              <div class="changeColor" style="display: none"><span>${ lfn:message('sys-modeling-main:modelingGantt.change.color') }</span></div>
            </div>
          </div>
          <div class="milepost-dialog-container-item">
            <p class="container-item-title">${ lfn:message('sys-modeling-main:modelingGantt.describe') }</p>
            <div class="container-item-content desc">
              <textarea placeholder="${ lfn:message('sys-modeling-main:modelingGantt.enter.text') }" id="textVal"></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="milepost-dialog-footer">
        <div class="cancel">${ lfn:message('sys-modeling-main:modeling.cancel') }</div>
        <div class="sure">${ lfn:message('sys-modeling-main:modeling.ok') }</div>
      </div>
    </div>
  </div>
  <script>Com_IncludeFile('dialog.js|data.js|jquery.js');</script>
  <script>Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js', 'js/jquery-plugin/manifest/');</script>

  <script>
    window.colorChooserHintInfo = {
      cancelText: '取消${ lfn:message('sys-modeling-main:modeling.cancel') }',
      chooseText: '确定${ lfn:message('sys-modeling-main:modeling.ok') }'
    };

    var milepostOption = {
      positionType :''
    };
    Com_IncludeFile('calendar.js');
    Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
    Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
    Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
    seajs.use(["sys/modeling/assembly/js/milepost",'lui/dialog']
            , function ( milepost,dialog) {
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

              function init(param) {
                var cfg = {
                  fdModelRecordId: param.fdModelRecordId,
                  startTime: param.startTime,
                  endTime: param.endTime,
                  modelId: param.modelId,
                  businessId: param.businessId,
                  fdConfig:param.fdConfig
                };
                window.milepost = new milepost.Milepost(cfg);
                window.milepost.startup();
              }

              // 确定提交事件
              $(".sure").on("click", function() {
                var fdConfig = window.milepost.getKeyData();
                if(_param.fdConfig){

                  var p = $.parseJSON(_param.fdConfig);
                  var fdId =p.fdId;
                }
                var validateResult = window.MilepostValidate.validate(fdConfig)
                if (!validateResult) {
                  //发生ajax请求，保存里程碑
                  var url = '${LUI_ContextPath}/sys/modeling/main/business.do?method=saveMilepost&modelId='+_param.modelId
                          +'&businessId='+_param.businessId+'&type=gantt&fdModelRecordId='+_param.fdModelRecordId ;
                  if(fdId){
                    url = url +'&fdId='+fdId;
                  }
                  $.ajax({
                    type:"post",
                    url:url,
                    data:fdConfig,
                    async:false ,    //用同步方式
                    dataType: "json",
                    success:function(data){
                      //保存成功,隐藏dialog(回调刷新)
                      if(data){
                        if('false' == data.isExit){
                          $dialog.hide('submit');
                        }else{
                          dialog.alert("${ lfn:message('sys-modeling-main:modelingGantt.milestone.date.exists') }")
                        }
                      }

                    }
                  });
                }else {
                  dialog.alert(validateResult)
                }
              })

              // 取消事件
              $(".cancel").on("click", function() {
                $dialog.hide();
              })
              // 更改图标事件
              $(".changeIcon").on("click", function() {
                var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
                dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
                  width: 750,
                  height: 500
                })
              })

              // 更改颜色事件
              $(".changeColor").on("click", function() {
                $(".changeColor").spectrum({
                  showInput: true,
                  palette: self.palette,
                  preferredFormat: "hex",
                  showPalette: true,
                  change: function (color) {
                  },
                  hide:function (color){
                    var _color = color.toHexString().toUpperCase();
                    $(".showIcon i").css('color',_color);
                    $(".changeColor").spectrum('destroy');
                  }
                });
                $(".changeColor").spectrum("set", $(".showIcon i").css('color'));
                $(".changeColor").spectrum('show');

              })


              window.changeIcon = function (className) {
                if (className) {
                  $(".showIcon").css('background', '#FFFFFF');
                  $(".showIcon i").removeClass().addClass('iconfont_nav '+className);
                  //开启更改颜色按钮
                  $(".changeColor").css('display', '');
                }
              }

              window.project_selectDateTime = function (event){
                var input =  $("[name='positionVal']");
                selectDate(event,input);
              }
            });
  </script>
</body>
</html>