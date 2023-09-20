/**
 *
 */
define(function (require, exports, module) {
  //系统组件
  var $ = require("lui/jquery");
  var base = require("lui/base");
  var modelingLang = require("lang!sys-modeling-main");
  var Milepost = base.Container.extend({

    /**
     * 初始化
     * cfg应包含：
     */
    initProps: function ($super, cfg) {
      this.bindEvent(cfg);
      if(cfg.fdConfig){
        this.initByStoreData(cfg.fdConfig)
      }
    },
    /**
     * 绑定事件
     */
    bindEvent: function (cfg) {
      // 初始化数据
      var projectTimeHtml = ' <i></i>\n' +
      '                '+modelingLang["modelingGantt.project.time"]+'：<span>'+cfg.startTime+'-'+cfg.endTime+'</span></div>';
      $('.project-time').empty();
      $('.project-time').html(projectTimeHtml);

      // 点击输入框--弹出或收起下拉列表
     /* $("#select-txt").click(function (event) {
        event.stopPropagation();
        if ($(".option-list").height() == "0") {
          $(".option-list").height("62px");
          $(".option-list").css('border-width', '1px');
        } else {
          $(".option-list").height("0");
          $(".option-list").css('border-width', '0');
        }
      });*/
      // 点击选项--将所选项赋值到输入框
/*      $(".option-list").on('click', '.option-list-item', function (event) {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $("#select-txt").val($(this).html());
        milepostOption.positionType =$(this).attr("key");
        // 渲染input对应样式
        if($(this).attr("key") == "time") {

          $("#positionVal").attr("placeholder", "请选择");
          $("#positionVal").attr("readOnly", true);
          $(".positionTab").html("");
          $(".positionTab").css("background-image","url(../img/milepost/calendar@2x.png)");
          $(".positionTab").css("cursor", "pointer");
          $("#positionVal").on('click', function (event) {
            project_selectDateTime(event);
          })
          $(".positionTab").on('click', function (event) {
            project_selectDateTime(event);
          })
        } else {
          $("#positionVal").attr("placeholder", "位于项目的");
          $("#positionVal").attr("readOnly", false);
          $(".positionTab").css("background-image","url()");
          $(".positionTab").html("%");
          $(".positionTab").css("cursor", "default")
        }
      });*/
      //目前位置类型只做日期
      $("#select-txt").val(modelingLang['modelingGantt.project.date']);
      $(".select-arrow").css("display","none");
      $(".positionTab").html("");
      $(".positionTab").css("background-image","url(../img/milepost/calendar@2x.png)");
      $(".positionTab").css("cursor", "pointer");
      $("#positionVal").on('click', function (event) {
        project_selectDateTime(event);
      })
      $(".positionTab").on('click', function (event) {
        project_selectDateTime(event);
      })
      milepostOption.positionType = 'time';


      // 点击其他地方关闭下拉列表
      $(document).click(function () {
        if ($(".option-list").height() != "0") {
          $(".option-list").height("0");
          $(".option-list").css('border-width', '0');
        }
      });

    },

    initByStoreData: function (sd) {
      var sd = $.parseJSON(sd)
      //名称
      if(sd.titleVal){
        $("[id='titleVal']").val(sd.titleVal);
      }

      //位置
      if(sd.positionVal){
        $("[id='positionVal']").val(sd.positionVal);
      }
      //图标
      if(sd.showIconValue){
        $(".showIcon i").removeClass();
        var showIconValueSplit = sd.showIconValue.split(":");
        var showIconValue = showIconValueSplit[0];
        var colorValue = '';
        if('flag' != showIconValue){
          showIconValue = 'iconfont_nav ' + showIconValueSplit[0];
          if(showIconValueSplit.length > 1){
            colorValue = showIconValueSplit[1];
          }
          //隐藏更改颜色按钮
          $(".changeColor").css('display', '');
        }
        $(".showIcon i").addClass(showIconValue);
        $(".showIcon i").css("color",colorValue);
      }

      //描述
      if(sd.textVal){
        $("[id='textVal']").val(sd.textVal);
      }
    },

    startup: function ($super, cfg) {
      $super(cfg);
    },

    getKeyData: function () {
      var fdConfig = {};
      //名称
      var titleVal = $("[id='titleVal']").val();
      fdConfig.titleVal = titleVal;
      //位置
      fdConfig.positionType = milepostOption.positionType;
      var positionVal = $("[id='positionVal']").val();
      fdConfig.positionVal = positionVal;
      //图标
      fdConfig.showIconValue =  $(".showIcon i").attr('class');
      if(0 == fdConfig.showIconValue.indexOf('iconfont_nav ')){
        fdConfig.showIconValue = fdConfig.showIconValue.substring(13);
      }
      var colorValue= $(".showIcon i").css('color');
      var rgb = colorValue.split(',');
      var r = parseInt(rgb[0].split('(')[1]);
      var g = parseInt(rgb[1]);
      var b = parseInt(rgb[2].split(')')[0]);
      var hex = "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
      fdConfig.showIconValue = fdConfig.showIconValue + ":" + hex;
      //描述
      var textVal = $("[id='textVal']").val();
      fdConfig.textVal = textVal;
      return fdConfig;
    },

  });

  window.MilepostValidate = {
    validate: function (cfg) {
      //名称
      if (!cfg.titleVal) {
        return modelingLang['modelingGantt.name.required'];
      }
      if (16 < cfg.titleVal.length) {
        return modelingLang['modelingGantt.length.name.cannot.exceed.16'];
      }
      //位置
      if (!cfg.positionType || '' == cfg.positionType) {
        return modelingLang['modelingGantt.select.location.type'];
      }
      if (!cfg.positionVal) {
        return modelingLang['modelingGantt.location.cannot.empty'];
      }
      var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
      if(!DATE_FORMAT.test(cfg.positionVal)){
        return modelingLang['modelingGantt.incorrect.location.date.format']
      }
      if ('per' == cfg.positionType) {
        if(cfg.positionVal < 0){
          return modelingLang['modelingGantt.position.percentage.cannot.less.than.0'];
        }
        if(cfg.positionVal > 100){
          return modelingLang['modelingGantt.position.percentage.cannot.more.than.100'];
        }
      }
      if (!cfg.showIconValue) {
        return modelingLang['modelingGantt.icon.cannot.empty'];
      }
      //描述
      if (cfg.textVal && 150 < cfg.titleVal.length) {
        return modelingLang['modelingGantt.description.cannot.exceed.150'];
      }
    }

  };

  exports.Milepost = Milepost;
})
