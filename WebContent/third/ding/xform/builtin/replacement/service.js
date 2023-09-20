Com_IncludeFile("common.js", Com_Parameter.ContextPath + "third/ding/xform/resource/js/","js",true);
/**
 * 补卡流程函数接口
 */
//debug打印开关
let debug_flag = false;
//班次信息
let schedules = {};

function Ding_Init() {
    if(debug_flag) {
        console.log("Ding_Init()");
    }
    if(Xform_ObjectInfo.mainDocStatus === "edit" || Xform_ObjectInfo.mainDocStatus === "add") {
        //本月申请补卡次数及剩余次数
        loadMonthReplacementInfo();
        //补卡时间变更事件
        $('input[name="extendDataFormInfo.value(data_time)"]').on('change', function () {
            Ding_ReplacementTimeChange(this);
        });
        //点击更换补卡班次
        $('.change_replacement_schedule').on('click', function () {
            Ding_ReplacementSchedule(this);
        });
        //初始化补卡班次相关内容
        let data_time = $('input[name="extendDataFormInfo.value(data_time)"]').val();
        if(data_time) {
            loadScheduleByDay();
            let punchId = $('input[name="extendDataFormInfo.value(punchId)"]').val();
            if(punchId && schedules) {
                showSchedule(schedules[punchId]);
            }
        }
    } else {
        //查看页面显示补卡班次
        let data_time = $('xformflag[property="extendDataFormInfo.value(data_time)"]').text();
        if(data_time) {
            loadScheduleByDay(data_time);
            let $punchId = $('input[name="extendDataFormInfo.value(punchId)"]');
            if($punchId.val() && schedules) {
                let schedule = schedules[$punchId.val()];
                let tr = $('<tr/>');
                let tdTitle = $('<td/>').append("<label/>").text("补卡班次");
                tdTitle.addClass("td_normal_title").addClass("form_tr_title");
                let tdContent = $('<td/>').text(schedule.text);
                tr.append(tdTitle).append(tdContent);
                $punchId.closest('table').prepend(tr);
            }
        }
    }
}

Com_AddEventListener(window, "load", Ding_Init);

/**
 * 本月申请补卡次数及剩余次数
 */
function loadMonthReplacementInfo() {
    let url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=getMonthReplacementInfoByUser";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        error: loadFailure,
        success: loadMonthReplacementInfoSuccess
    });

    function loadMonthReplacementInfoSuccess(data){
        if (debug_flag) {
            console.log(data);
        }
        $('.form_tr_context_desc').show();
        $('.month_replacement_info').text("本月已申请" + data.used + "次补卡，剩余" + data.left + "次");
    }
}

/**
 * ajax请求失败
 */
function loadFailure(data) {
    seajs.use(['lui/dialog'], function (dialog) {
        dialog.failure('网络异常，请稍后再试');
    });
}

/**
 * 更换补卡班次按钮点击
 */
function Ding_ReplacementSchedule(dom) {
    if(debug_flag) {
        console.log("Ding_ReplacementSchedule()");
    }
    seajs.use(['lui/dialog'], function (dialog) {
        let url = "/third/ding/xform/builtin/replacement/dialog_replacement_schedule.jsp";
        //读取班次ID
        let currentSchedule = $('input[name="extendDataFormInfo.value(punchId)"]').val();
        let length = Object.keys(schedules).length;
        if (length > 0) {
            dialog.iframe(url, null, ReplacementScheduleResult, {
                width: 360, height: 120 + length * 40,
                params: {data: {"schedules": schedules, "currentSchedule": currentSchedule}}
            });
        }
    });

    function ReplacementScheduleResult(result){
        if (debug_flag) {
            console.log(result);
        }
        if (result) {
            let schedule = schedules[result.value];
            $('.replacement_schedule').text(schedule.text);
            //保存班次ID
            $('input[name="extendDataFormInfo.value(punchId)"]').val(schedule.punchId);
        }
    }
}


/**
 * 补卡时间变更事件
 */
let ReplacementTime;
function Ding_ReplacementTimeChange(dom) {
    if(ReplacementTime && ReplacementTime === $(dom).val()) {
        return;
    } else {
        ReplacementTime = $(dom).val();
        if(debug_flag) {
            console.log("Ding_ReplacementTimeChange()");
        }
    }
    //获取日期对应补卡班次
    loadScheduleByDay();
    //显示当前补卡班次及更换补卡班次按钮
    showSchedule(getScheduleByDate());
}

/**
 * 显示当前补卡班次及更换补卡班次按钮
 */
function showSchedule(schedule) {
    if(!schedule) {
        return;
    }
    $('.replacement_schedule').text(schedule.text);
    //保存班次ID、排班时间、班次日期
    $('input[name="extendDataFormInfo.value(punchId)"]').val(schedule.punchId);
    $('input[name="extendDataFormInfo.value(punch_check_time)"]').val(schedule.check_date_time);
    $('input[name="extendDataFormInfo.value(work_date)"]').val(schedule.work_date);
    //更换补卡班次
    $('.replacement_split').text("|");
    let $changeReplacementSchedule = $('.change_replacement_schedule');
    $changeReplacementSchedule.text("更换补卡班次");
    $changeReplacementSchedule.show();
}

/**
 * 根据补卡时间计算应该取哪个班次
 */
function getScheduleByDate(){
    if(!schedules) {
        return;
    }
    //整理后续进行时间对比的格式
    let minSchedule;
    let maxSchedule;
    for(let i in schedules) {
        //休息日
        if(!schedules[i].punchId) {
            return schedules[i];
        }

        let schedule = schedules[i];
        let real_plan_time = new Date(schedule.check_date_time).getTime();
        if(!minSchedule || minSchedule.check_date_time > real_plan_time) {
            minSchedule = {};
            minSchedule.check_date_time = real_plan_time;
            minSchedule.index = i;
        }
        if(!maxSchedule || maxSchedule.check_date_time < real_plan_time) {
            maxSchedule = {};
            maxSchedule.check_date_time = real_plan_time;
            maxSchedule.index = i;
        }
    }
    if(debug_flag) {
        console.log("minSchedule=");
        console.log(minSchedule);
        console.log("maxSchedule=");
        console.log(maxSchedule);
    }
    //根据补卡时间计算
    let dateStr = $('input[name="extendDataFormInfo.value(data_time)"]').val();
    let date = new Date(dateStr).getTime();
    if(date < minSchedule.check_date_time && date < maxSchedule.check_date_time) {
        //选择的时间小于所有的排班时间，返回最小的排班时间
        return schedules[minSchedule.index];
    } else if(date > minSchedule.check_date_time && date > maxSchedule.check_date_time) {
        //选择的时间大于所有的排班时间，返回最大的排班时间
        return schedules[maxSchedule.index];
    } else {
        //返回最大的排班时间
        return schedules[maxSchedule.index];
    }
}

/**
 * 加载班次信息
 */
function loadScheduleByDay(data_time) {
    if(!data_time) {
        data_time = $('input[name="extendDataFormInfo.value(data_time)"]').val();
    }
    var date = new Date(data_time);
    let url = Com_Parameter.ContextPath + "third/ding/thirdDingAttendance.do?method=scheduleByDay&date_time=" + date.getTime();
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        error: loadFailure,
        success: loadScheduleByDaySuccess
    });

    function loadScheduleByDaySuccess(data) {
        if(debug_flag) {
            console.log("loadScheduleByDaySuccess data=");
            console.log(data);
        }
        if (data.errcode === 0) {
            let result = data.result;
            if (result && result.length > 0) {
                schedules = {};
                for (let i = 0; i < result.length; i++) {
                    let work = result[i];
                    let schedule = {};
                    if(work.is_rest === "N") {
                        if(work.features && work.features.indexOf("{") >=0 && work.features.indexOf("}") > 0){
                            let features = JSON.parse(work.features);
                            schedule.punchId = features.punchId;
                        }
                        schedule.check_date_time = work.check_date_time;
                        schedule.work_date = work.work_date;
                        schedule.text = getText(work);
                        schedules[schedule.punchId] = schedule;
                    } else {
                        schedule.work_date = work.work_date;
                        schedule.text = getRestText(work);
                        schedules[i] = schedule;
                    }
                }
            }
        }
    }

    function getRestText(work) {
        let data = new Date(work.work_date);
        let text = "周" + getDayOfWeek(data.getDay());
        let month = data.getMonth() + 1;
        let day = data.getDate();
        text += "（";
        text += getFormatNum(month);
        text += ".";
        text += getFormatNum(day);
        text += "）";
        text += "补卡";
        return text;
    }

    function getText(work) {
        if (!work) {
            return "";
        }
        let data = new Date(work.real_plan_time);
        let text = "周" + getDayOfWeek(data.getDay());
        if (work.check_type === "OnDuty") {
            text += "上班";
        } else if (work.check_type === "OffDuty") {
            text += "下班";
        }
        let month = data.getMonth() + 1;
        let day = data.getDate();
        let hours = data.getHours();
        let minutes = data.getMinutes();
        text += "（";
        text += getFormatNum(month);
        text += ".";
        text += getFormatNum(day);
        text += " ";
        text += getFormatNum(hours);
        text += ":";
        text += getFormatNum(minutes);
        text += "）";

        if (work.check_status === "Init") {
            text += "未打卡";
        } else if (work.check_status === "Checked") {
            text += "已打卡";
        } else if (work.check_status === "Timeout") {
            text += "缺卡";
        }

        if (debug_flag) {
            console.log("real_plan_time=" + work.real_plan_time + " text=" + text);
        }
        return text;
    }
}

function getDayOfWeek(num) {
    switch (num) {
        case 0:
            return "日";
        case 1:
            return "一";
        case 2:
            return "二";
        case 3:
            return "三";
        case 4:
            return "四";
        case 5:
            return "五";
        case 6:
            return "六";
    }
}

function getFormatNum(number) {
    return number < 10 ? "0" + number : number;
}
