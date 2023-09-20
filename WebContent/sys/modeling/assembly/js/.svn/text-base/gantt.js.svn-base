/**
 *
 */
define(function (require, exports, module) {
  //系统组件
  var $ = require("lui/jquery");
  var base = require("lui/base");
  var env = require('lui/util/env');
  var modelingLang = require("lang!sys-modeling-main");
  var ModelingGantt= base.DataView.extend({

    /**
     * 初始化
     * cfg应包含：
     */
    initProps: function ($super, cfg) {
      this.bindEvent(cfg);
      this.load();
    },
    doRender: function ($super, cfg) {
    },
    /**
     * 绑定事件
     */
    bindEvent: function (cfg) {
      //清空
      $('.model-gantt').empty();
      // $('.model-gantt').append(model_gantt_html);
      $(document).ready(function () {
        // 渲染根元素
        const renderBody = () => {
          var bodyTemplate = `
            <div class="model-gantt-wrap">
              <div class="model-gantt-drawer">
                <div class="model-gantt-drawer-wrap">
                </div>
                <div class="model-gantt-drawer-add">`+modelingLang['modelingGantt.add.milestone']+`</div>
                <div class="model-gantt-drawer-opt active"></div>
              </div>
              <div class="model-gantt-table">
                <div class="model-gantt-table-wrap">
                  <div class="model-gantt-table-content">
                  </div>
                </div>
              </div>
            </div>
            `;
          $('#gantt').append(bodyTemplate);
        }; // 颜色格式转换函数


        var colorChange = (sHex, alpha = 1) => {
          // 十六进制颜色值的正则表达式
          var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
          /* 16进制颜色转为RGB格式 */

          var sColor = sHex.toLowerCase();

          if (sColor && reg.test(sColor)) {
            if (sColor.length === 4) {
              var sColorNew = '#';

              for (var i = 1; i < 4; i += 1) {
                sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
              }

              sColor = sColorNew;
            } //  处理六位的颜色值


            var sColorChange = [];

            for (var i = 1; i < 7; i += 2) {
              // 使用0x将16进制转换为10进制
              sColorChange.push(parseInt('0x' + sColor.slice(i, i + 2)));
            } // return sColorChange.join(',')
            // 或


            return 'rgba(' + sColorChange.join(',') + ',' + alpha + ')';
          } else {
            return sColor;
          }
        }; // 渲染表格头部（日、月）子标题列


        const renderRow = data => {
          var rows = '';
          data.map((item, index) => {
            // 如果子项数据为date
            if (item.constructor === Object) {
              rows += `<div class="${item.restFlag ? 'rest-day' : ''}"><p>${item.dateNum}</p></div>`;
            } else {
              rows += `<div><p>${item}</p></div>`;
            }
          });
          return rows;
        }; // 渲染表格头部（季度、年）子标题列


        const renderColspan = data => {
          var rows = '';
          data.map(item => {
            // 如果子项数据为date
            rows += `<div class="model-gantt-table-content-header-subtitle-colspan"><p>${item}</p></div>`;
          });
          return rows;
        }; // 渲染表格背景列


        const renderEmptyRow = (lineItem,data,title,index) => {
          var rows = '';
          data.map(item=> {
            var hasMilepost = false;
            var titleVal = '';
            var positionVal = '';
            var showIconValue = '';
            var textVal = '';
            var fdId = '';
            var milepostContentLi = '';
            if(!lineItem.milepostArr){
              lineItem.milepostArr = [];
            }
            for(var i = 0;i< lineItem.milepostArr.length;i++){
              switch (gantt_data.table.timeData.defaultValue) {
                  //#165905 甘特图添加里程碑后，没有显示 ,这里[day]留着兼容旧数据
                case "day":
                  if(lineItem.milepostArr[i].title == title && lineItem.milepostArr[i].dateNum == item.dateNum){
                    titleVal =  lineItem.milepostArr[i].titleVal;
                    positionVal = lineItem.milepostArr[i].positionVal;
                    showIconValue = lineItem.milepostArr[i].showIconValue;
                    textVal = lineItem.milepostArr[i].textVal;
                    fdId = lineItem.milepostArr[i].fdId;
                    var configParam = {
                      fdId:fdId,
                      titleVal:titleVal,
                      positionVal:positionVal,
                      showIconValue:showIconValue,
                      textVal:textVal
                    }
                    hasMilepost = true;
                  }
                  break;
                case "date":
                  if(lineItem.milepostArr[i].title == title && lineItem.milepostArr[i].dateNum == item.dateNum){
                    titleVal =  lineItem.milepostArr[i].titleVal;
                    positionVal = lineItem.milepostArr[i].positionVal;
                    showIconValue = lineItem.milepostArr[i].showIconValue;
                    textVal = lineItem.milepostArr[i].textVal;
                    fdId = lineItem.milepostArr[i].fdId;
                    var configParam = {
                      fdId:fdId,
                      titleVal:titleVal,
                      positionVal:positionVal,
                      showIconValue:showIconValue,
                      textVal:textVal
                    }
                    hasMilepost = true;
                  }
                  break;
                case "month":
                  var month = lineItem.milepostArr[i].title.substring(5,7);
                  var year = lineItem.milepostArr[i].title.substring(0,4);
                  if(month  <= 6){
                    year = year +"上";
                  }else{
                    year = year +"下";
                  }
                  if(year == title && parseInt(month) == item){
                    titleVal =  lineItem.milepostArr[i].titleVal;
                    positionVal = lineItem.milepostArr[i].positionVal;
                    showIconValue = lineItem.milepostArr[i].showIconValue;
                    textVal = lineItem.milepostArr[i].textVal;
                    fdId = lineItem.milepostArr[i].fdId;
                    var configParam = {
                      fdId:fdId,
                      titleVal:titleVal,
                      positionVal: positionVal,
                      showIconValue:showIconValue,
                      textVal:textVal
                    }
                    milepostContentLi = milepostContentLi + "<li key="+ configParam.fdId +" title=" + titleVal + " onclick=drawerAddOnclick('"+lineItem.fdId+"','"+JSON.stringify(configParam)+"')>" +
                        "<div style='border:0;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 82px'>" +
                        titleVal +
                        "</div>" +
                        "<i class='delete'></i>" +
                        "</li>";
                    hasMilepost = true;
                  }
                  break;
              }

            }
            //过长处理
            var showTitleVal = titleVal;
            if(titleVal.length>4){
              showTitleVal = titleVal.substring(0,3) + '...';
            }
            if(hasMilepost){
              var milepostContent = '';
              //#165905 甘特图添加里程碑后，没有显示 ,这里[day]留着兼容旧数据
              if('day' == gantt_data.table.timeData.defaultValue || 'date' == gantt_data.table.timeData.defaultValue){
                //日视图
                //<!-- 显示单个里程碑内容选用此div -->
                var milepostNormalWidth = '68px';
                var contentLength = cfg.timeData.dataArr[0].content.length;
                if(contentLength>0){
                  var subTitleLength = cfg.timeData.dataArr[0].content[contentLength-1].subTitle.length;
                  if(cfg.timeData.dataArr[0].content[contentLength-1].title == title) {
                    if(cfg.timeData.dataArr[0].content[contentLength-1].subTitle[subTitleLength-1].dateNum - item.dateNum <= 2){
                      milepostNormalWidth = '35px';
                    }
                  }
                }
                var colorStyle = '';
                var showIconValueSplit = showIconValue.split(":");
                showIconValue = showIconValueSplit[0];
                if('flag' != showIconValueSplit[0]){
                  showIconValue = 'iconfont_nav ' + showIconValueSplit[0];
                  if(showIconValueSplit.length > 1){
                    colorStyle = 'color:'+ showIconValueSplit[1]+";";
                  }
                }

                milepostContent = "    <div class='single-milepost-content' key='" + fdId + "'  onclick=drawerAddOnclick('"+lineItem.fdId+"','"+JSON.stringify(configParam)+"') >" +
                    "      <i class='" + showIconValue + "' style='"+colorStyle+"'></i>" +
                    "      <span title='" + titleVal + "'>" + showTitleVal + "</span>" +
                    "      <i class='delete'></i>" +
                    "    </div>";
                rows += "<div><div class='milepost-box' index='"+index+"'>" +
                    "    <div class='milepost-normal' style='width: "+milepostNormalWidth+";border: 0px;'>" +
                    "<i class='"+showIconValue+"' style='"+colorStyle+"'></i><span title='"+titleVal+"'></span></div>" +
                    milepostContent +
                    "  </div></div>"
              }else{
                //<!-- 显示多个里程碑内容选用此div -->
                milepostContent = "    <div class='milepost-content-list'>" +
                    "      <ul>" +
                    milepostContentLi +
                    "      </ul>" +
                    "    </div>";
                var colorStyle = '';
                var showIconValueSplit = showIconValue.split(":");
                showIconValue = showIconValueSplit[0];
                if('flag' != showIconValueSplit[0]){
                  showIconValue = 'iconfont_nav ' + showIconValueSplit[0];
                  if(showIconValueSplit.length > 1){
                    colorStyle = 'color:'+ showIconValueSplit[1]+";";
                  }
                }
                rows += "<div><div class='milepost-box' index='"+index+"' style='border: 0px;'>" +
                    "    <div class='milepost-normal' style='width: 35px;border: 0px;'>" +
                    "<i class='"+showIconValue+"' style='"+colorStyle+"'></i></div>" +
                    milepostContent +
                    "  </div></div>";
              }

            }else{
              rows += `<div></div>`;
            }
          });
          return rows;
        }; // 渲染表格行


        const renderLine = (line, row , title) => {
          var lines = ''; // 遍历数据行
          line.map((item,index) => {
            lines += `
          <div class="model-gantt-table-content-body-line">
            ${renderEmptyRow(item,row,title,index)}          
          </div>
        `;
          });
          return lines;
        }; // 渲染表格行(跨列)


        const renderColspanLine = (line, row, title, index) => {
          var lines = ''; // 遍历数据行
          line.map(lineItem => {
            lines += `
          <div class="model-gantt-table-content-body-line">
            ${row.map(item => {
              var hasMilepost = false;
              var titleVal = '';
              var positionVal = '';
              var showIconValue = '';
              var textVal = '';
              var fdId = '';
              var milepostContentLi = '';
              var rows = '';
              if (!lineItem.milepostArr) {
                lineItem.milepostArr = [];
              }
              for(var i = 0;i< lineItem.milepostArr.length;i++) {
                switch (gantt_data.table.timeData.defaultValue) {
                  case "quarter":
                    var month = lineItem.milepostArr[i].title.substring(5, 7);
                    var year = lineItem.milepostArr[i].title.substring(0, 4);
                    year = year + '年';
                    var quarter = '一季度';
                    month = parseInt(month);
                    if (0 < month && month <= 3) {
                      quarter = '一季度';
                    } else if (3 < month && month <= 6) {
                      quarter = '二季度';
                    } else if (6 < month && month <= 9) {
                      quarter = '三季度';
                    } else if (9 < month && month <= 12) {
                      quarter = '四季度';
                    }
                    if (year == title && quarter == item) {
                      titleVal = lineItem.milepostArr[i].titleVal;
                      positionVal = lineItem.milepostArr[i].positionVal;
                      showIconValue = lineItem.milepostArr[i].showIconValue;
                      textVal = lineItem.milepostArr[i].textVal;
                      fdId = lineItem.milepostArr[i].fdId;
                      var configParam = {
                        fdId:fdId,
                        titleVal:titleVal,
                        positionVal: positionVal,
                        showIconValue:showIconValue,
                        textVal:textVal
                      }
                      milepostContentLi = milepostContentLi + "<li key="+ configParam.fdId +" title=" + titleVal + " onclick=drawerAddOnclick('"+lineItem.fdId+"','"+JSON.stringify(configParam)+"')>" +
                          "<div style='border:0;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 82px'>" +
                          titleVal +
                          "</div>" +
                          "<i class='delete'></i>" +
                          "</li>";
                      hasMilepost = true;

                    }
                    break;
                  case "year":
                    var month = lineItem.milepostArr[i].title.substring(5, 7);
                    var year = lineItem.milepostArr[i].title.substring(0, 4);
                    year = year + '年';
                    var upOrDown = '上';
                    month = parseInt(month);
                    if (0 < month && month <= 6) {
                      upOrDown = '上';
                    } else if (6 < month && month <= 12) {
                      upOrDown = '下';
                    }
                    if (year == title && upOrDown == item) {
                      titleVal = lineItem.milepostArr[i].titleVal;
                      positionVal = lineItem.milepostArr[i].positionVal;
                      showIconValue = lineItem.milepostArr[i].showIconValue;
                      textVal = lineItem.milepostArr[i].textVal;
                      fdId = lineItem.milepostArr[i].fdId;

                      var configParam = {
                        fdId:fdId,
                        titleVal:titleVal,
                        positionVal: positionVal,
                        showIconValue:showIconValue,
                        textVal:textVal
                      }
                      milepostContentLi = milepostContentLi + "<li key="+ configParam.fdId +" title=" + titleVal + " onclick=drawerAddOnclick('"+lineItem.fdId+"','"+JSON.stringify(configParam)+"')>" +
                          "<div style='border:0;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 82px'>" +
                          titleVal +
                          "</div>" +
                          "<i class='delete'></i>" +
                          "</li>";
                      hasMilepost = true;
                    }
                    break;
                }
              }
              if(hasMilepost){
                var colorStyle = '';
                var showIconValueSplit = showIconValue.split(":");
                showIconValue = showIconValueSplit[0];
                if('flag' != showIconValueSplit[0]){
                  showIconValue = 'iconfont_nav ' + showIconValueSplit[0];
                  if(showIconValueSplit.length > 1){
                    colorStyle = 'color:'+ showIconValueSplit[1]+";";
                  }
                }

                //<!-- 显示多个里程碑内容选用此div -->
                milepostContent = "    <div class='milepost-content-list'>" +
                    "      <ul>" +
                    milepostContentLi +
                    "      </ul>" +
                    "    </div>";
                rows += "<div class='model-gantt-table-content-body-line-colspan'><div class='milepost-box' index='"+index+"' style='border: 0px;'>" +
                    "    <div class='milepost-normal' style='width: 35px;border: 0px;'>" +
                    "<i class='" + showIconValue + "' style='"+colorStyle+"left: 26px;'></i></div>" +
                    milepostContent +
                    "  </div></div>"
              }else{
                rows += `<div class="model-gantt-table-content-body-line-colspan"></div>`;
              }

              return rows;
            }).join('')}      
          </div>
        `;
          });
          return lines;
        }; // 渲染表格


        const renderTable = (titleData, lineData) => {
          // 渲染季度、年等跨列表格
          if (titleData.value === 'quarter' || titleData.value === 'year') {
            titleData.content.map((item, index) => {
              var rowWidth = 0; // 如果是季度，年的首个表格，则宽度再格子数*70的基础上再+1

              if (index === 0) {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 70;
                });
                rowWidth += 1; // 其他表格样式宽度均为季度，年数*35
              } else {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 70;
                });
              }

              const tableItem = `
            <div class="model-gantt-table-content-item" style="width:${rowWidth}px">
              <div class="model-gantt-table-content-header">
                <div class="model-gantt-table-content-header-title">${item.title}</div>
                <div class="model-gantt-table-content-header-subtitle">
                 ${renderColspan(item.subTitle)}
                </div>
              </div>
              <div class="model-gantt-table-content-body">
                ${renderColspanLine(lineData, item.subTitle, item.title, index)}
              </div>
            </div>`;
              $('.model-gantt-table-content').append(tableItem);
            });
          } else if (titleData.value === 'date') {
            // 将日期单独做渲染因为日期表格太长，当表格在浏览器中的实际视窗宽度（浏览器的视窗宽度-左侧抽屉层的宽度）<单个日期表格的DOM宽度时，JQ的outerWidth()方法获取的宽度是浏览器剩余视窗的宽度，导致渲染出来的空表格显示异常，所以此处需要添加style的width属性，保证获取数值正常
            // 渲染日期
            titleData.content.map((item, index) => {
              var rowWidth = 0; // 如果是日期的首个表格，则宽度再格子数*35的基础上再+1

              if (index === 0) {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 35;
                });
                rowWidth += 1; // 其他表格样式宽度均为天数*35
              } else {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 35;
                });
              }

              const tableItem = `
          <div class="model-gantt-table-content-item date" style="width:${rowWidth}px">
            <div class="model-gantt-table-content-header">
              <div class="model-gantt-table-content-header-title">${item.title}</div>
              <div class="model-gantt-table-content-header-subtitle">
               ${renderRow(item.subTitle)}
              </div>
            </div>
            <div class="model-gantt-table-content-body">
              ${renderLine(lineData, item.subTitle ,item.title)}
            </div>
          </div>`;
              $('.model-gantt-table-content').append(tableItem);
            });
          } else {
            // 渲染月份
            titleData.content.map((item, index) => {
              var rowWidth = 0; // 如果是月份的首个表格，则宽度再格子数*35的基础上再+1

              if (index === 0) {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 35;
                });
                rowWidth += 1; // 其他表格样式宽度均为月份数*35
              } else {
                item.subTitle.map((item, index) => {
                  var rowNum = index + 1;
                  rowWidth = rowNum * 35;
                });
              }

              const tableItem = `
            <div class="model-gantt-table-content-item" style="width:${rowWidth}px">
              <div class="model-gantt-table-content-header">
                <div class="model-gantt-table-content-header-title">${item.title}</div>
                <div class="model-gantt-table-content-header-subtitle">
                 ${renderRow(item.subTitle)}
                </div>
              </div>
              <div class="model-gantt-table-content-body">
                ${renderLine(lineData, item.subTitle, item.title)}
              </div>
            </div>`;
              $('.model-gantt-table-content').append(tableItem);
            });
          }
        }; // 渲染任务行，progressData：任务进度的集合，calibration：当前的时间轴单位日/月/季度/年


        const renderProgress = (progressData, calibration) => {
          var progress = ''; // 按日渲染

          const renderDateProgress = (progressVal, progressDate) => {
            progressVal.map((item, index) => {
              // 渲染任务进度条的长度
              // 分割时间字符串转换为数组'2020/11/05' => ['2020','11','05']
              var startTime = item.lineContent.start.split(' ')[0].split('/');
              var endTime = item.lineContent.end.split(' ')[0].split('/'); // new Date()第3个参数默认为1，就是每个月的1号，把它设置为0时， new Date()会返回本月的最后一天，然后通过getDate()方法得到天数

              var curStartDate = new Date(startTime[0], startTime[1] - 0, 0).getDate(); // 开始任务的日期至当月结束日期的时间段

              var remainStartDate = curStartDate + 1 - startTime[2]; // 中间时间段（跨月/年）

              var plusDate = 0; // 总时间跨度(占据的格子数)

              var timeNum = 0; // console.log(startTime, endTime)
              // 时间跨度没跨年

              if (startTime[0] === endTime[0]) {
                // 没跨月
                if (startTime[1] === endTime[1]) {
                  timeNum = parseInt(endTime[2]) + 1 - startTime[2]; // 跨月
                } else {
                  // 时间跨度 = 本月开始时间至月末的时间段 + 中间月份的天数 + 结束时间所在月份需要的天数，
                  // 开始与结束的月份，中间相差的几个月的天数总和
                  // 若中间月份的天数plusDate = 0，则表示跨的月份为相邻的2个月
                  for (var i = startTime[1] - 0 + 1; i < endTime[1]; i++) {
                    plusDate += new Date(startTime[0], i, 0).getDate();
                  }

                  timeNum = remainStartDate + plusDate + (endTime[2] - 0);
                } // 时间跨年

              } else {
                // 天数总和 = remainStartDate + 任务开始时间的后一个月至开始时间年份最后一天的时间段 + 年份所差的天数总和 + 结束时间年份的1月1日至结束时间的时间段
                // 开始时间的下一个月至当年年尾的天数总和
                for (var i = startTime[1] - 0 + 1; i <= 12; i++) {
                  plusDate += new Date(startTime[0], i, 0).getDate();
                } // 中间相差的年数的天数总和


                for (var i = endTime[0] - 1; i > startTime[0]; i--) {
                  // 判断平年闰年
                  if (i % 4 === 0) {
                    plusDate += 366;
                  } else {
                    plusDate += 365;
                  }
                } // 结束时间所在年份的1月1日至结束时间，中间月份的天数总和


                for (var i = 1; i < endTime[1]; i++) {
                  plusDate += new Date(endTime[0], i, 0).getDate();
                }

                timeNum = remainStartDate + plusDate + (endTime[2] - 0);
              } // console.log('时间跨度', timeNum)
              // 起始时间与表格位置绑定
              // 起始年/月/日


              const leftStartYear = progressDate.content[0].title.split('年')[0];
              const leftStartMonth = progressDate.content[0].title.split('年')[1].split('月')[0];
              const leftStartDate = progressDate.content[0].subTitle[0].dateNum; // 起始日到起始日所在月份月底的时间段

              const leftRemainDate = new Date(leftStartYear, leftStartMonth, 0).getDate() - leftStartDate + 1; // console.log('起始日到起始日所在月份月底的时间段',leftRemainDate)
              // 任务条左偏移的格子数

              var progressLeft = 0; // 任务条左偏移的时间段天数

              var leftPlusDate = 0; // 当前任务起始时间与最早任务的起始时间在同一年

              if (startTime[0] === leftStartYear) {
                // 当前任务起始时间与最早任务的起始时间在同一月
                if (startTime[1] === leftStartMonth) {
                  // 左偏移从结尾时间点开始，所以不需要+1
                  progressLeft = startTime[2] - leftStartDate; // 当前任务起始时间与最早任务的起始时间不在同一月
                } else {
                  // 左偏移 = 最早任务开始的日期 + 最早任务开始日期的下一个月至当前任务开始日期的上一个月中间相差的月份天数总和 + 当前任务开始的日期-1
                  // 若中间的差值leftPlusDate = 0，则说明2个任务开始时间为相邻的2个月
                  for (var i = leftStartMonth - 0 + 1; i < startTime[1]; i++) {
                    leftPlusDate += new Date(startTime[0], i, 0).getDate();
                  }

                  progressLeft = leftRemainDate + leftPlusDate + (startTime[2] - 1);
                } // 当前任务起始时间与最早任务的起始时间不在同一年

              } else {
                // 左偏移 = 最早任务开始的日期 + 最早任务开始日期的下一个月至当前任务开始日期的上一个月中间相差的月份天数总和 + 当前任务开始的日期-1
                // 中间相差年数所包含的天数
                for (var i = startTime[0] - 1; i > leftStartYear - 0; i--) {
                  // 判断平年闰年
                  if (i % 4 === 0) {
                    leftPlusDate += 366;
                  } else {
                    leftPlusDate += 365;
                  }
                } // 最早任务开始月份的后一月至那年年末月份数的天数总和，若最早开始任务的月份为11月，则plusDate不会计算


                for (var i = leftStartMonth - 0 + 1; i <= 12; i++) {
                  leftPlusDate += new Date(startTime[0], i, 0).getDate();
                } // 下一年的1月1日至任务开始时间月份的前一个月的天数总和，如果任务恰好在1月开始，则plusDate不会计算，仍然为0


                for (var i = 1; i < startTime[1]; i++) {
                  leftPlusDate += new Date(endTime[0], i, 0).getDate();
                }

                progressLeft = leftRemainDate + leftPlusDate + (startTime[2] - 0 - 1);
              } // console.log('渲染时的宽度',timeNum * 35)
              // console.log('最初左偏移时间段', leftRemainDate)
              // console.log('中间时间段', leftPlusDate)
              // console.log('左偏移的格子数开始时间是10月2日', progressLeft)


              progress += `
      <div class="model-gantt-table-mask-progress-item date" index="${index}" style="width:0px;margin-left:${progressLeft * 35}px">
        <div class="model-gantt-table-mask-progress-item-soon" style="background:${colorChange(item.background, .5)};">
          <p class="model-gantt-table-mask-progress-item-desc">${item.lineContent.desc}</p>
          <div class="model-gantt-table-mask-progress-item-done" style="width:0px;background:${colorChange(item.background)}"></div>
          <div class="model-gantt-table-mask-progress-item-shadow" style="background:${colorChange(item.background, .3)};"></div>
        </div>
      </div>
      `; // 进度条动效

              setTimeout(function () {
                var progressDom = $('.model-gantt-table-mask-progress-item');
                $(progressDom[index]).css('width', `${timeNum * 35}px`);
                $(progressDom[index]).find('.model-gantt-table-mask-progress-item-done').css('width', `${timeNum * 35 * item.lineContent.progress}px`);
              }, 100);
            });
            progress = `
    <div class="model-gantt-table-mask">
      <div class="model-gantt-table-mask-progress">
        ${progress}
      </div>
    </div>`;
          }; // 按月份渲染


          const renderMonthProgress = (progressVal, progressMonth) => {
            // console.log(progressMonth)
            progressVal.map((item, index) => {
              // 渲染任务进度条的长度
              // 分割时间字符串转换为数组'2020/11/05' => ['2020','11','05']
              var startTime = item.lineContent.start.split(' ')[0].split('/');
              var endTime = item.lineContent.end.split(' ')[0].split('/'); // console.log(startTime, endTime)
              // 开始任务的月份至当年12月所包含的月份数

              var remainStartMonth = 12 - startTime[1] + 1; // 结束任务所在年份的1月至结束任务的月所包含的月份数

              var remainEndMonth = endTime[1] - 0; // 中间时间段（跨月/年）

              var plusMonth = 0; // 总时间跨度(占据的格子数)

              var timeNum = 0;

              if (startTime[0] === endTime[0]) {
                timeNum = endTime[1] - startTime[1] + 1; // 时间跨度跨年
              } else {
                // 中间相差的年数包含的月份总和
                for (var i = endTime[0] - 1; i > startTime[0]; i--) {
                  plusMonth += 12;
                }

                timeNum = remainStartMonth + plusMonth + remainEndMonth;
              } // console.log('按月份的时间跨度',timeNum)
              // 起始时间与表格位置绑定
              // 起始年/月


              const leftStartYear = progressMonth.content[0].title.slice(0, 4);
              const leftStartMonth = parseInt(progressMonth.content[0].subTitle[0]); // 任务条左偏移的格子数

              var progressLeft = 0; // 任务条左偏移的时间段月份数

              var leftPlusMonth = 0; // 起始月到起始月所在年末的月份总和

              const leftRemainMonth = 12 - leftStartMonth; // 当前任务起始时间与最早任务的起始时间在同一年

              if (startTime[0] === leftStartYear) {
                progressLeft = startTime[1] - leftStartMonth; // 当前任务起始时间与最早任务的起始时间不在同一年
              } else {
                // 中间相差的年数包含的月份总和
                for (var i = startTime[0] - 1; i > leftStartYear; i--) {
                  leftPlusMonth += 12;
                }

                progressLeft = leftRemainMonth + leftPlusMonth + (startTime[1] - 0);
              }

              progress += `
      <div class="model-gantt-table-mask-progress-item" index="${index}" style="width:0;margin-left:${progressLeft * 35}px">
        <div class="model-gantt-table-mask-progress-item-soon" style="background:${colorChange(item.background, .5)};">
          <p class="model-gantt-table-mask-progress-item-desc">${item.lineContent.desc}</p>
          <div class="model-gantt-table-mask-progress-item-done" style="width:0px;background:${colorChange(item.background)}"></div>
          <div class="model-gantt-table-mask-progress-item-shadow" style="background:${colorChange(item.background, .3)};"></div>
        </div>
      </div>
      `; // 进度条动效

              setTimeout(function () {
                var progressDom = $('.model-gantt-table-mask-progress-item');
                $(progressDom[index]).css('width', `${timeNum * 35}px`);
                $(progressDom[index]).find('.model-gantt-table-mask-progress-item-done').css('width', `${timeNum * 35 * item.lineContent.progress}px`);
              }, 100);
            });
            progress = `
    <div class="model-gantt-table-mask">
      <div class="model-gantt-table-mask-progress">
        ${progress}
      </div>
    </div>`;
          }; // 按季度渲染


          const renderQuarterProgress = (progressVal, progressQuarter) => {
            progressVal.map((item, index) => {
              // 渲染任务进度条的长度
              // 分割时间字符串转换为数组'2020/11/05' => ['2020','11','05']
              var startTime = item.lineContent.start.split(' ')[0].split('/');
              var endTime = item.lineContent.end.split(' ')[0].split('/'); // 开始任务的月份至当年12月所包含季度数

              var remainStartQuarter = parseInt((12 - startTime[1]) / 3 + 1); // 结束任务所在年份的1月至结束任务的月所包含的季度数

              var remainEndQuarter = parseInt((endTime[1] - 1) / 3 + 1); // 中间时间段（跨季度/年）

              var plusQuarter = 0; // 总时间跨度(占据的格子数)

              var timeNum = 0; // 时间跨度没跨年
              if (startTime[0] === endTime[0]) {
                // 时间跨度 = 任务经过的时间段/3（按季度划分） + 1，然后进一法取整
                timeNum = parseInt(Math.ceil(endTime[1]/3) - Math.ceil(startTime[1]/3)  + 1 ); // 时间跨度跨年
              } else {
                // 中间相差的年数包含的季度数总和
                for (var i = endTime[0] - 1; i > startTime[0]; i--) {
                  plusQuarter += 4;
                }

                timeNum = remainStartQuarter + plusQuarter + remainEndQuarter;
              } // console.log('总时间跨度',timeNum)
              // 起始时间与表格位置绑定
              // 起始年/季度


              const leftStartYear = progressQuarter.content[0].title.slice(0, 4);
              const leftStartWord = progressQuarter.content[0].subTitle[0]; // 将汉字季度转义为数字

              var leftStartQuarter = 0;

              switch (leftStartWord[0]) {
                case '一':
                  leftStartQuarter = 1;
                  break;

                case '二':
                  leftStartQuarter = 2;
                  break;

                case '三':
                  leftStartQuarter = 3;
                  break;

                case '四':
                  leftStartQuarter = 4;
                  break;
              } // 任务条左偏移的格子数


              var progressLeft = 0; // 任务条左偏移的季度数

              var leftPlusQuarter = 0; // 最早起始季到所在年末的季度总和

              const leftRemainQuarter = 5 - leftStartQuarter; // 当前任务的起始季度

              const curStartQuarter = parseInt((startTime[1] - 1) / 3 + 1); // 当前任务起始时间与最早任务的起始时间在同一年

              if (startTime[0] === leftStartYear) {
                progressLeft = curStartQuarter - leftStartQuarter; // 当前任务起始时间与最早任务的起始时间不在同一年
              } else {
                // 中间相差的年数包含的季度数总和
                for (var i = startTime[0] - 1; i > leftStartYear; i--) {
                  leftPlusQuarter += 4;
                } // 左偏移 = 最早起始任务至那年年尾包含的季度数总和 + 中间所跨年份包含的季度数总和 + 当前任务的开始季度数-1


                progressLeft = leftRemainQuarter + leftPlusQuarter + curStartQuarter - 1;
              }
              progress += `
      <div class="model-gantt-table-mask-progress-item" index="${index}" style="width:0px;margin-left:${progressLeft * 70}px">
        <div class="model-gantt-table-mask-progress-item-soon" style="background:${colorChange(item.background, .5)};">
          <p class="model-gantt-table-mask-progress-item-desc">${item.lineContent.desc}</p>
          <div class="model-gantt-table-mask-progress-item-done" style="width:0px;background:${colorChange(item.background)}"></div>
          <div class="model-gantt-table-mask-progress-item-shadow" style="background:${colorChange(item.background, .3)};"></div>
        </div>
      </div>
      `; // 进度条动效

              setTimeout(function () {
                var progressDom = $('.model-gantt-table-mask-progress-item');
                $(progressDom[index]).css('width', `${timeNum * 70}px`);
                $(progressDom[index]).find('.model-gantt-table-mask-progress-item-done').css('width', `${timeNum * 70 * item.lineContent.progress}px`);
              }, 100);
            });
            progress = `
    <div class="model-gantt-table-mask">
      <div class="model-gantt-table-mask-progress">
        ${progress}
      </div>
    </div>`;
          }; // 按年份渲染


          const renderYearProgress = (progressVal, progressYear) => {
            progressVal.map((item, index) => {
              // 渲染任务进度条的长度
              // 分割时间字符串转换为数组'2020/11/05' => ['2020','11','05']
              var startTime = item.lineContent.start.split(' ')[0].split('/');
              var endTime = item.lineContent.end.split(' ')[0].split('/'); // console.log(startTime, endTime)
              // 开始任务的月份至当年12月所包含半年数

              var remainStartYear = parseInt((12 - startTime[1]) / 6 + 1); // 结束任务所在年份的1月至结束任务的月所包含的半年

              var remainEndYear = parseInt((endTime[1] - 1) / 6 + 1); // 中间时间段（跨季度/年）

              var plusYear = 0; // 总时间跨度(占据的格子数)

              var timeNum = 0; // 时间跨度没跨年

              if (startTime[0] === endTime[0]) {
                // 时间跨度 = 任务经过的时间段/6（按上/下半年划分） + 1，然后进一法取整
                timeNum = parseInt(Math.ceil(endTime[1]/6) - Math.ceil(startTime[1]/6) + 1); // 时间跨度跨年
              } else {
                // 中间相差的年数包含的年数总和
                for (var i = endTime[0] - 1; i > startTime[0]; i--) {
                  plusYear += 2;
                }

                timeNum = remainStartYear + plusYear + remainEndYear;
              } // 起始时间与表格位置绑定
              // 起始年/季度


              const leftStartYear = progressYear.content[0].title.slice(0, 4);
              const leftStartWord = progressYear.content[0].subTitle[0]; // 将汉字上下半年转义为数字

              var leftNumYear = 0;

              switch (leftStartWord[0]) {
                case '上':
                  leftNumYear = 1;
                  break;

                case '下':
                  leftNumYear = 2;
                  break;
              } // 任务条左偏移的格子数


              var progressLeft = 0; // 任务条左偏移的季度数

              var leftPlusYear = 0; // 最早时间起始的半年到所在年末的半年数总和

              const leftRemainYear = 3 - leftNumYear; // 当前任务的起始半年数

              const curStartYear = parseInt((startTime[1] - 1) / 6 + 1); // 当前任务起始时间与最早任务的起始时间在同一年

              if (startTime[0] === leftStartYear) {
                progressLeft = curStartYear - leftNumYear; // 当前任务起始时间与最早任务的起始时间不在同一年
              } else {
                // 中间相差的年数包含的半年数总和
                for (var i = startTime[0] - 1; i > leftStartYear; i--) {
                  leftPlusYear += 2;
                } // 左偏移 = 最早起始任务至那年年尾包含的半年数总和 + 中间所跨年份包含的半年数总和 + 当前任务的开始半年数-1


                progressLeft = leftRemainYear + leftPlusYear + curStartYear - 1;
              }

              progress += `
      <div class="model-gantt-table-mask-progress-item" index="${index}" style="width:0px;margin-left:${progressLeft * 70}px">
        <div class="model-gantt-table-mask-progress-item-soon" style="background:${colorChange(item.background, .5)};">
          <p class="model-gantt-table-mask-progress-item-desc">${item.lineContent.desc}</p>
          <div class="model-gantt-table-mask-progress-item-done" style="width:0px;background:${colorChange(item.background)}"></div>
          <div class="model-gantt-table-mask-progress-item-shadow" style="background:${colorChange(item.background, .3)};"></div>
        </div>
      </div>
      `; // 进度条动效

              setTimeout(function () {
                var progressDom = $('.model-gantt-table-mask-progress-item');
                $(progressDom[index]).css('width', `${timeNum * 70}px`);
                $(progressDom[index]).find('.model-gantt-table-mask-progress-item-done').css('width', `${timeNum * 70 * item.lineContent.progress}px`);
              }, 100);
            });
            progress = `
    <div class="model-gantt-table-mask">
      <div class="model-gantt-table-mask-progress">
        ${progress}
      </div>
    </div>`;
          }; // 确认时间轴为日期/月份/季度/年，以显示对应任务条进度的长度


          switch (calibration.value) {
            case 'date':
              renderDateProgress(progressData, calibration);
              break;

            case 'month':
              renderMonthProgress(progressData, calibration);
              break;

            case 'quarter':
              renderQuarterProgress(progressData, calibration);
              break;

            case 'year':
              renderYearProgress(progressData, calibration);
              break;
          }

          $('.model-gantt-table-wrap').prepend(progress);
        }; // 渲染抽屉层


        const renderDrawer = drawerData => {
          var dTable = '';
          var dTableHeader = `
    <td>
      <div class="model-gantt-checkbox select-all">
        <div class="model-gantt-checkbox-wrap">
          <input type="checkbox">
          <span class="model-gantt-checkbox-inner"></span>
        </div>
      </div>
    </td>
    <td class="model-gantt-drawer-table-setting">
      <div></div>
    </td>`;
          var dTableBody = ''; // 渲染抽屉头部

          drawerData.missionTitle.map(item => {
            dTableHeader += `
        <td>
          <div>${item.title}</div>
        </td>
      `;
          }); // 包装抽屉表格头部

          dTableHeader = `
      <thead>
        <tr>${dTableHeader}</tr>
      </thead>
    `; // 渲染抽屉内容

          drawerData.missionContent.map((item, index) => {
            // console.log(item.displayItem)
            dTableBody += `
        <tr fdId=${item.fdId}>
          <td>
            <div class="model-gantt-checkbox">
              <div class="model-gantt-checkbox-wrap">
                <input type="checkbox" id=${index}">
                <span class="model-gantt-checkbox-inner"></span>
              </div>
            </div>
          </td>
          <td>
            <div class="model-gantt-checkbox-num">
              <i style="background:${item.background}"></i>
              <span class="model-gantt-checkbox-desc">${index + 1}</span>
            </div>
          </td>
          ${item.displayItem.map(aItem => {
              return `<td>${aItem.itemDesc}</td>`;
            }).join('')}
        </tr>
      `;
          }); // 包装表格内容

          dTableBody = `
      <tbody>
        ${dTableBody}
      </tbody>
    `; // 包装表格

          dTable = `
      <table>
        ${dTableHeader}
        ${dTableBody}
      </table>
    `;
          $('.model-gantt-drawer-wrap').prepend(dTable);
        }; // 渲染筛选面板


        const renderFilterPanel = filterVal => {
          var filterPanel = '';
          filterVal.map((item, index) => {
            filterPanel += `
      <li class="model-gantt-drawer-setting-item">
        <div class="model-gantt-checkbox">
          <div class="model-gantt-checkbox-wrap checked">
            <input type="checkbox" id=${index} checked="true">
            <span class="model-gantt-checkbox-inner"></span>
          </div>
          <span class="model-gantt-checkbox-desc">${item.title}</span>
        </div>
      </li>
      `;
          });
          filterPanel = `
    <ul class="model-gantt-drawer-setting">
      ${filterPanel}
    </ul>
    `;
          $('.model-gantt-drawer').append(filterPanel);
        }; // 渲染定位条


        const renderPositionElement = timeVal => {
          // 日期与月份渲染的定位条
          if (timeVal.value === 'date' || timeVal.value === 'month') {
            const positionElement = `
      <div class="model-gantt-table-current">
        <div class="model-gantt-table-currentX"></div>
        <div class="model-gantt-table-currentY"></div>
      </div>
    `;
            $('.model-gantt-table-wrap').prepend(positionElement); // 季度与年渲染的定位条
          } else {
            const positionElement = `
      <div class="model-gantt-table-current">
        <div class="model-gantt-table-currentX"></div>
        <div class="model-gantt-table-currentYMax"></div>
      </div>
    `;
            $('.model-gantt-table-wrap').prepend(positionElement);
          }
        }; // 进度条弹窗

        const renderComeBackTodayPositionElement = () => {
          const comeBackTodayPositionElement = `
          <div class="model-gantt-table-today">
            <div class="model-gantt-table-todayY"></div>
          </div>
          `;
          $('.model-gantt-table-wrap').prepend(comeBackTodayPositionElement);
        }; // 进度条弹窗



        const renderMsgMask = () => {
          const msgMask = `
    <div class="model-gantt-table-msg">
      <div class="model-gantt-table-msg-title"><p></p><i></i></div>
      <ul class="model-gantt-table-msg-content">
        <li class="model-gantt-table-msg-content-item">
          <div class="model-gantt-table-msg-content-start"><p>`+modelingLang['modelingGantt.start.time']+`：</p><span></span></div>
          <div class="model-gantt-table-msg-content-end"><p>`+modelingLang['modelingGantt.end.time']+`：</p><span></span></div>
          <div class="model-gantt-table-msg-content-percent"><p>`+modelingLang['modelingGantt.schedule']+`：</p><span></span></div>
          <div class="model-gantt-table-msg-content-link"><p>`+modelingLang['modeling.jump']+`</p></div>
        </li>
      </ul>
    </div>
    `;
          $('.model-gantt-table-wrap').append(msgMask);
          $('.model-gantt-table-msg').on('click', 'i', function () {
            $('.model-gantt-table-msg').removeClass('active');
          });
        }; // 设置表格容器总宽度(Todo: 子元素宽度超过视窗宽度时，outterWidth获取宽度会不正常，将JQ对象改为DOM对象获取的宽度也不正常，暂时在css中给父元素添加最小宽度解决问题)


        const calculateWidth = () => {
          var tableItems = $('.model-gantt-table-content-item');
          var tableItemWidth = 0; // console.log(tableItems)

          tableItems.each(function () {
            tableItemWidth += $(this).outerWidth(); // console.log('获取表格DOM宽',$(this).outerWidth())
            // console.log('获取表格DOM的css宽',$(this).css('width'))
          });
          $('.model-gantt-table-wrap').css({
            width: tableItemWidth
          });
        }; // 操作抽屉层


        const operateDrawer = () => {
          $('.model-gantt-drawer-opt').on('click', function () {
            var drawerWidth = $('.model-gantt-drawer').outerWidth();
            $(this).toggleClass('active'); // 抽屉层被收起，右侧表格被释放

            if ($(this).attr('class').indexOf('active') === -1) {
              $('.model-gantt-drawer-setting').removeClass('active');
              $('.model-gantt-drawer').css({
                marginLeft: -drawerWidth
              }); // 抽屉层被拉出，右侧表格被压缩
            } else {
              $('.model-gantt-drawer').css({
                marginLeft: 0
              });
            }
          });
        }; // 鼠标移动定位交互


        const mousePosition = () => {
          $('.model-gantt-table-mask-progress').on('mousemove', function (e) {
            // console.log(e.target)
            // console.log('e的offsetX================',e.offsetX)
            // 在表格里滑动时再显示定位条
            $('.model-gantt-table-current').addClass('active'); // 鼠标获取X轴上的格子数

            var xNum = parseInt(e.offsetX / 35); // 跨列的格子数

            var xNumMax = parseInt(e.offsetX / 70); // 鼠标获取Y轴上的格子数

            var yNum = parseInt(e.offsetY / 35) + 1; // 当前定位的X，Y轴变蓝色

            var positionX = xNum * 35; // 跨列的X定位

            var positionXMax = xNumMax * 70;
            var positionY = yNum * 35 + 36; // 定位到父元素取margin-left

            var parentLeft = parseInt($(e.target).parents(".model-gantt-table-mask-progress-item").css('margin-left')); // 直接取到本元素的margin-left

            var itemLeft = parseInt($(e.target).css('margin-left')); // 日期标识

            var dateFlag = e.target.getAttribute('class');
            $('.model-gantt-table-currentX').css({
              top: positionY
            });
            $('.model-gantt-table-currentY').css({
              left: positionX
            }); // 跨列单元格定位

            $('.model-gantt-table-currentYMax').css({
              left: positionXMax
            }); // 当移动到任务条时，鼠标事件e的触发源被改变

            if (e.target.getAttribute('class') === 'model-gantt-table-mask-progress-item-desc' || e.target.getAttribute('class') === 'model-gantt-table-mask-progress-item-shadow') {
              var progressItemY = $(e.target).parents(".model-gantt-table-mask-progress-item").attr('index') - 0 + 1;
              $('.model-gantt-table-currentX').css({
                top: progressItemY * 35 + 36
              });
              $('.model-gantt-table-currentY').css({
                left: parentLeft + (positionX - 0 * 35)
              }); // 跨列单元格定位

              $('.model-gantt-table-currentYMax').css({
                left: parentLeft + positionXMax
              }); // console.log('soon',parentLeft,positionXMax)
            } else if (e.target.getAttribute('class') === 'model-gantt-table-mask-progress-item' || dateFlag) {
              var progressItemY = $(e.target).attr('index') - 0 + 1;
              $('.model-gantt-table-currentX').css({
                top: progressItemY * 35 + 36
              });
              $('.model-gantt-table-currentY').css({
                left: itemLeft + (positionX - 0 * 35)
              }); // 跨列单元格定位

              $('.model-gantt-table-currentYMax').css({
                left: itemLeft + positionXMax
              }); // console.log('item',itemLeft,positionXMax)
            }
          });
        }; // 点击任务进度，更改弹出提示窗的信息


        const alertMsg = () => {
          // 任务数据的最大值
          var maxIndex = missionData.missionContent.length; // 当前被点击任务的索引

          var msgIndex = 0;
          $('.model-gantt-table-mask-progress-item').on('click', function (e) {
            // console.log(e.clientY)
            // console.log('===', e.offsetX)
            msgIndex = $(this).attr('index'); // 提示窗标题

            var msgTitle = missionData.missionContent[msgIndex].lineContent.desc; // 开始时间

            var startTime = missionData.missionContent[msgIndex].lineContent.start; // 结束时间

            var endTime = missionData.missionContent[msgIndex].lineContent.end; // 进度，toFixed解决浮点计算精度丢失导致的计算异常问题

            var progressPercent = (missionData.missionContent[msgIndex].lineContent.progress * 100).toFixed(2); // 进度条左偏移数值

            var itemLeft = parseInt($(this).css('margin-left'));
            $('.model-gantt-drawer-setting').removeClass('active'); // 修改提示窗信息

            $('.model-gantt-table-msg-title p').html(msgTitle);
            $('.model-gantt-table-msg-content-start span').html(`${startTime}`);
            $('.model-gantt-table-msg-content-end span').html(`${endTime}`);
            $('.model-gantt-table-msg-content-percent span').html(`${progressPercent}%`); // 显示提示窗，修改显示位置

            $('.model-gantt-table-msg').addClass('active'); // 保证数据少的时候，弹出面板能正常显示，将定位改为fixed定位后，需要判断抽屉层是否展开，决定左偏移的值

            if (maxIndex - msgIndex > 4) {
              $('.model-gantt-table-msg').css({
                left: e.clientX,
                top: e.clientY
              }); // 如果弹出窗口在最后3条数据上，弹出窗口的位置靠上偏移
            } else {
              $('.model-gantt-table-msg').css({
                left: e.clientX,
                top: e.clientY - 3 * 35
              });
            }

            // 点击数据最右边时，弹出窗口位于点击位置左侧
            if(document.body.clientWidth - e.clientX - 0 < 278) {
              $('.model-gantt-table-msg').css({
                left: e.clientX - 278
              })
            }
          }); // 点击跳转跳转页面

          $('.model-gantt-table-msg-content-link p').on('click', function () {
            // 进度条对应的ID值
            var itemLink = missionData.missionContent[msgIndex].fdId;
            // console.log(itemLink);
            //视图穿透
            if(cfg.fdViewFlag && "" != cfg.fdViewId){
              //指定视图
              Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppView.do?method=modelView&fdId="+itemLink+"&viewId="+cfg.fdViewId, "_blank");
            }else{
              //默认视图
              Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppView.do?method=modelView&fdId="+itemLink+"&modelName="+cfg.fdModelName, "_blank");
            }
          });
        };

        const publicBehavior = () => {
          // 多选公共逻辑
          $('.model-gantt-checkbox-desc').on('click', function () {
            $(this).siblings().toggleClass('checked');
          });
          $('.model-gantt-checkbox-wrap input').on('click', function () {
            $(this).parent().toggleClass('checked');
          }); // 点击抽屉层筛选条件，改变抽屉层内容

          $('.model-gantt-drawer-table-setting').on('click', function () {
            $('.model-gantt-drawer-setting').toggleClass('active');
          });
          $('.model-gantt-table-mask-progress').on('click', function () {
            $('.model-gantt-drawer-setting').removeClass('active');
          });
        }; // 点击label隐藏/显示表格列


        const clickFilter = () => {
          $('.model-gantt-drawer-setting .model-gantt-checkbox-desc').on('click', function () {
            // 当前被点击的DOM索引
            var curIndex = $(this).parents('.model-gantt-drawer-setting-item').index(); // 选中标识

            var checkedFlag = $(this).siblings().attr('class').indexOf('checked'); // 与被点击DON索引相同的抽屉头部列

            var tableHeader = $(this).parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap thead tr td').eq(curIndex + 2); // 与被点击DON索引相同的抽屉内容行

            var tableLine = $(this).parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap tbody tr'); // console.log($(tableLine[0]).children().eq(curIndex + 2))
            // 如果为选中状态
            updateDrawerSettingData(curIndex,checkedFlag);
            if (checkedFlag !== -1) {
              tableHeader.css('display', 'table-cell'); // 显示表格列

              for (var i = 0; i < tableLine.length; i++) {
                // 筛选项索引相同的抽屉列
                var tableRow = $(tableLine[i]).children().eq(curIndex + 2);
                tableRow.css('display', 'table-cell');
              } // 非选中状态

            } else {
              tableHeader.css('display', 'none');

              for (var i = 0; i < tableLine.length; i++) {
                // 筛选项索引相同的抽屉列
                var tableRow = $(tableLine[i]).children().eq(curIndex + 2);
                tableRow.css('display', 'none');
              }
            }
          });
          $('.model-gantt-drawer-setting  .model-gantt-checkbox-wrap input').on('click', function () {
            // 当前被点击的DOM索引
            var curIndex = $(this).parents('.model-gantt-drawer-setting-item').index(); // 选中标识

            var checkedFlag = $(this).parent().attr('class').indexOf('checked'); // 与被点击DON索引相同的抽屉头部列

            var tableHeader = $(this).parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap thead tr td').eq(curIndex + 2); // 与被点击DON索引相同的抽屉内容行

            var tableLine = $(this).parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap tbody tr'); // console.log($(tableLine[0]).children().eq(curIndex + 2))
            // 如果为选中状态
            updateDrawerSettingData(curIndex,checkedFlag);
            if (checkedFlag !== -1) {
              tableHeader.css('display', 'table-cell'); // 显示表格列

              for (var i = 0; i < tableLine.length; i++) {
                // 筛选项索引相同的抽屉列
                var tableRow = $(tableLine[i]).children().eq(curIndex + 2);
                tableRow.css('display', 'table-cell');
              } // 非选中状态

            } else {
              tableHeader.css('display', 'none');

              for (var i = 0; i < tableLine.length; i++) {
                // 筛选项索引相同的抽屉列
                var tableRow = $(tableLine[i]).children().eq(curIndex + 2);
                tableRow.css('display', 'none');
              }
            }
          });
        }; // 点击抽屉行，返回id值


        const rowSkip = () => {
          $('.model-gantt-drawer-wrap tbody tr').on('click', function () {
            // console.log($(this).attr('fdid'));
            $('.model-gantt-drawer-setting').removeClass('active');
            //视图穿透
            if(cfg.fdViewFlag && "" != cfg.fdViewId){
              //指定视图
              Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppView.do?method=modelView&fdId="+$(this).attr('fdid')+"&viewId="+cfg.fdViewId, "_blank");
            }else{
              //默认视图
              Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/main/modelingAppView.do?method=modelView&fdId="+$(this).attr('fdid')+"&modelName="+cfg.fdModelName, "_blank");
            }
          });
          $('.model-gantt-drawer-wrap tbody tr').on('mouseover', function (e) {
            var top = $(this).offset().top + 6;
            var left = $(this).offset().left +  $('.model-gantt-drawer').width() - 80;
            var fdId = $(this).attr('fdid');
            $('.model-gantt-drawer-add').attr("fdId",fdId);
            // 显示里程碑添加按钮
            $('.model-gantt-drawer-add').css(
                {
                  "top":top,
                  "left":left,
                  "position":"fixed",
                  "display":"block"
                }
            );
          });
          $('.model-gantt-drawer-wrap tbody tr').on('mouseout', function (e) {
            // 隐藏里程碑添加按钮
            $('.model-gantt-drawer-add').css(
                {
                  "display":"none",
                }
            );
          });
          $('.model-gantt-drawer-add').on('mouseover', function (e) {
            $('.model-gantt-drawer-add').css(
                {
                  "display":"block"
                }
            );
          });
          $('.model-gantt-drawer-add').on('mouseout', function (e) {
            $('.model-gantt-drawer-add').css(
                {
                  "display":"none"
                }
            );
          });
          $('.model-gantt-drawer-add').on('click', function (e) {
            var fdId = $(this).attr('fdId');
            drawerAddOnclick(fdId);
          });
        }; // 多选项目逻辑


        const deleteRow = () => {
          var deleteArr = [];
          var selectAll = $('.select-all').children(); // 全选逻辑

          $('.select-all').on('click', function () {
            // 全选时ID数组先置为空
            deleteArr = []; // 多选项

            var selectBox = $(this).parents('thead').siblings().find('.model-gantt-checkbox-wrap'); // 多选项ID

            var checkedArr = $(this).parents('thead').siblings().children(); // 选中标示

            var checkedFlag = $(this).children().attr('class').indexOf('checked'); // 选中状态

            if (checkedFlag !== -1) {
              selectBox.addClass('checked');

              for (var i = 0; i < checkedArr.length; i++) {
                deleteArr.push($(checkedArr[i]).attr('fdid'));
              } // 取消选中状态

            } else {
              selectBox.removeClass('checked');
              deleteArr = [];
            } // console.log('全选',deleteArr)
            gantt_data.deleteArr = deleteArr;
            $('.List_Selected_div').remove();
            var selectHtml = '<div class="List_Selected_div" style="display: none"></div>';
            $('.model-gantt').append(selectHtml);
            for (var i = 0; i < deleteArr.length; i++) {
              $('.List_Selected_div').append( '<input type="checkbox" name="List_Selected" value="'+deleteArr[i]+'" >');
              ;
            }
            $("input[name='List_Selected']").attr("checked","true");
          }); // 单选逻辑

          $('.model-gantt-drawer-wrap tbody tr').on('click', '.model-gantt-checkbox-wrap input', function (e) {
            e.stopPropagation(); // 全部单选项的选中状态数组

            var selectAllArr = []; // 全部单选项为选中状态的标识

            var selectAllFlag = false; // 被选中行的ID值

            var checkedId = $(this).parents('tr').attr('fdid'); // 选中标识

            var checkedFlag = $(this).parents('.model-gantt-checkbox').children('.model-gantt-checkbox-wrap').attr('class').indexOf('checked'); // 被点击的单选项的兄弟元素

            var checkSiblings = $(this).parents('tr').siblings().find('.model-gantt-checkbox-wrap'); // 选中状态把对应id推入deleteArr中，当全部按钮被选中时，全选按钮高亮

            if (checkedFlag !== -1) {
              // 遍历其他选项，更改selectAllArr状态
              for (var i = 0; i < checkSiblings.length; i++) {
                var checkedItemFlag = $(checkSiblings[i]).attr('class').indexOf('checked');

                if (checkedItemFlag !== -1) {
                  selectAllArr.push(true);
                } else {
                  selectAllArr.push(false);
                }
              } // 遍历数组当数组有false时selectAllFlag置为true，没有时selectAllFlag置为false


              selectAllFlag = selectAllArr.some(function (data) {
                return data === false;
              });

              if (!selectAllFlag) {
                selectAll.addClass('checked');
              }

              deleteArr.push(checkedId); // 非选中状态取消全选按钮的高亮
            } else {
              selectAll.removeClass('checked');
              deleteArr = deleteArr.filter(item => {
                return item !== checkedId;
              });
            } // console.log('单选',deleteArr)
            gantt_data.deleteArr = deleteArr;
            $('.List_Selected_div').remove();
            var selectHtml = '<div class="List_Selected_div" style="display: none"></div>';
            $('.model-gantt').append(selectHtml);
            for (var i = 0; i < deleteArr.length; i++) {
              $('.List_Selected_div').append( '<input type="checkbox" name="List_Selected" value="'+deleteArr[i]+'" >');
              ;
            }
            $("input[name='List_Selected']").attr("checked","true");
          });
        }; // 渲染DOM函数

        const milepostEvent = () => {
          $(".milepost-box").on("mouseenter", function (e) {
            // $(this)添加showMilepost类名则是查看单个里程碑的名称，添加添加showMilepost类名则是查看多个里程碑的列表
            $(".milepost-box").css('z-index','10');
            $('.single-milepost-content').css({
              left: 0
            })
            //#165905 甘特图添加里程碑后，没有显示 ,这里[day]留着兼容旧数据
            if('day' == gantt_data.table.timeData.defaultValue || 'date' == gantt_data.table.timeData.defaultValue) {
              $(this).addClass("showMilepost");
              $(this).find('.milepost-normal').css('display','none');
              // 点击数据最右边时，弹出窗口位于点击位置左侧
              if(document.body.clientWidth - e.clientX - 0 < 75) {
                $('.single-milepost-content').css({
                  left: -75
                })
              }
              $(this).css('z-index','99');
            }else{
              $(this).addClass("showMileposts")
              // var maxIndex = missionData.missionContent.length;
              // var msgIndex = $(this).attr('index');
              $('.milepost-content-list').css({
                left: e.clientX,
                top: e.clientY
              });

              // 点击数据最右边时，弹出窗口位于点击位置左侧
              if(document.body.clientWidth - e.clientX - 0 < 130) {
                $('.milepost-content-list').css({
                  left: e.clientX - 114
                })
              }

              $(this).css('z-index','99');
            }
          })

          $(".milepost-box").on("mouseleave", function () {
            $(this).removeClass("showMilepost");
            $(this).removeClass("showMileposts");
            $(this).find('.milepost-normal').css('display','block');
          })

          $(".milepost-content-list").on("mouseenter", function () {
            $(this).parent().addClass("showMileposts");
          }).on("mouseleave", function () {
            // $(this).parent().removeClass("showMileposts");
          })
          $(".delete").on("click", function (e) {
            e.stopPropagation();
            var fdId = $(this).parent().attr("key");
            deleteMilepost(fdId);
          })

        }

        /** **************自定义方法************** **/
        var updateDrawerSettingData = function updateDrawerSettingData(curIndex,checkedFlag){
          //只存筛选掉的列
          //先移除
          for (var i = 0; i <gantt_data.drawerSettingData.length; i++) {
            if(gantt_data.drawerSettingData[i].curIndex == curIndex){
              gantt_data.drawerSettingData.splice(i,1);
              break;
            }
          }
          if(checkedFlag == -1){
            gantt_data.drawerSettingData.push({curIndex:curIndex,checkedFlag:checkedFlag})
          }
        }

        // 点击其他地方关闭下拉列表
        $(document).click(function () {
          if ($('.model-gantt-tab-change').hasClass('active')) {
            $('.model-gantt-tab-change').toggleClass('active')
          }
        });

        var initDrawerSettingData = function initDrawerSettingData(){
          var dom = $('.model-gantt-drawer-setting  .model-gantt-checkbox-wrap input');
          for (var j = 0; j <gantt_data.drawerSettingData.length; j++) {
            var curIndex = gantt_data.drawerSettingData[j].curIndex; // 选中标识

            var tableHeader = dom.parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap thead tr td').eq(curIndex + 2); // 与被点击DON索引相同的抽屉内容行

            var tableLine = dom.parents('.model-gantt-drawer').find('.model-gantt-drawer-wrap tbody tr'); // console.log($(tableLine[0]).children().eq(curIndex + 2))

            dom.parent('.model-gantt-drawer-setting-item .model-gantt-checkbox .model-gantt-checkbox-wrap').eq(curIndex).toggleClass('checked')
            tableHeader.css('display', 'none');

            for (var _i13 = 0; _i13 < tableLine.length; _i13++) {
              // 筛选项索引相同的抽屉列
              var _tableRow2 = $(tableLine[_i13]).children().eq(curIndex + 2);

              _tableRow2.css('display', 'none');
            }
          }

        }


        /** *******************初始化******************* */
        var timeData = cfg.timeData;
        var missionData = cfg.missionData;
        const renderInit = () => {
          var dataArr;
          var defaultValue;
          var dataArrLength;
          if(timeData){
            defaultValue= timeData.defaultValue;
            if(timeData.dataArr){
              dataArr = timeData.dataArr[0];
              dataArrLength =  timeData.dataArr.length;
            }else{
              $('#listview').css('display','block');
              return;
            }
          }

          for (var i = 0; i < dataArrLength; i++) {
            if(defaultValue == timeData.dataArr[i].value){
              dataArr = timeData.dataArr[i];
              break;
            }
          }
          // 渲染型函数
          renderBody();
          renderTable(dataArr, missionData.missionContent);
          renderProgress(missionData.missionContent, dataArr);
          renderMsgMask();
          renderFilterPanel(missionData.missionTitle);
          renderDrawer(missionData);
          renderPositionElement(dataArr); // 功能型函数
          renderComeBackTodayPositionElement();
          initDrawerSettingData(); //初始化筛选项

          calculateWidth();
          operateDrawer();
          mousePosition();
          alertMsg();
          publicBehavior();
          clickFilter();
          rowSkip();
          deleteRow();
          milepostEvent();
        };

        renderInit(); // 点击视图切换

        $('.model-gantt-tab-change').unbind("click").click(function (e) {
          e.stopPropagation();
          $(this).toggleClass('active');
        }); // 重新渲染甘特图

        const renderChange = (argTime, argMission) => {
          renderBody();
          renderTable(argTime, argMission.missionContent);
          renderProgress(argMission.missionContent, argTime);
          renderMsgMask();
          renderFilterPanel(argMission.missionTitle);
          renderDrawer(argMission);
          renderPositionElement(argTime); // 功能型函数
          renderComeBackTodayPositionElement();

          calculateWidth();
          operateDrawer();
          mousePosition();
          alertMsg();
          publicBehavior();
          clickFilter();
          rowSkip();
          deleteRow();
          milepostEvent();
        }; // 删除之前页面上的数据

        const resetInit = () => {
          $('#gantt').empty();
        };

        $('.model-gantt-tab-change-select li').unbind("click").click(function (e) {
          e.stopPropagation();
          var selectIndex = $(this).index();
          $(this).siblings().removeClass('active');
          $(this).toggleClass('active');

          //发起ajax请求，保存设置到自定义时间维度
          var timeDimension = "date";
          switch (selectIndex) {
            case 0:
              timeDimension = "date";
              break;
            case 1:
              timeDimension = "month";
              break;
            case 2:
              timeDimension = "quarter";
              break;
            case 3:
              timeDimension = "year";
              break;
          }

          var url =  Com_Parameter.ContextPath + 'sys/modeling/main/business.do?method=saveGanttTimeDimension&modelId='+cfg.modelId+'&businessId='+cfg.businessId+'&type=gantt';
          $.ajax({
            type:"post",
            url:url,
            data:{
              timeDimension: timeDimension
            },
            async:false ,    //用同步方式
            success:function(data){
              //保存成功
              gantt_data.table.timeData.defaultValue = timeDimension;
              resetInit();
              renderChange(timeData.dataArr[selectIndex], missionData);
            }
          });
        });
      });
    },

    startup: function ($super, cfg) {
      $super(cfg);
    },

  });

  exports.ModelingGantt = ModelingGantt;
})