// 解决ie不支持replaceAll问题
if(!String.prototype.hasOwnProperty("replaceAll")){
    /**
     * 替换所有匹配的字符
     * @param matchStr
     * @param replaceStr
     * @param isReplaceReg（是否转译正则符号）
     * @return {*}
     */
    String.prototype.replaceAll = function (matchStr,replaceStr, isReplaceReg){
        if(isReplaceReg){
            //转译匹配字符中的正则符号
            matchStr = matchStr.replace(/\[/gm,"\\[")
                .replace(/\]/gm,"\\]")
                .replace(/\{/gm,"\\{")
                .replace(/\}/gm,"\\}")
                .replace(/\(/gm,"\\(")
                .replace(/\)/gm,"\\)")
                .replace(/\^/gm,"\\^")
                .replace(/\$/gm,"\\$")
                .replace(/\|/gm,"\\|")
                .replace(/\?/gm,"\\?")
                .replace(/\*/gm,"\\*")
                .replace(/\+/gm,"\\+")
                .replace(/\./gm,"\\.")
        }
        return this.replace(new RegExp(matchStr,"gm"),replaceStr);
    }
}

// 得到某个范围的日期
function getRangeDate(val, isCmpl) {
    var params = null;
    // var date = new Date();
    var now = new Date(); //当前日期
    var nowDayOfWeek = now.getDay(); //今天本周的第几天
    var nowDay = now.getDate(); //当前日
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += nowYear < 2000 ? 1900 : 0; //
    var lastMonthDate = new Date(); //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
    var lastYear = lastMonthDate.getYear();
    var lastMonth = lastMonthDate.getMonth();
    //格式化日期：yyyy-MM-dd
    function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth() + 1;
        var myweekday = date.getDate();
        if (mymonth < 10) {
            mymonth = "0" + mymonth;
        }
        if (myweekday < 10) {
            myweekday = "0" + myweekday;
        }
        return myyear + "-" + mymonth + "-" + myweekday;
    }
    function getMonthDays(myMonth) {
        var monthStartDate = new Date(nowYear, myMonth, 1);
        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
        var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
        return days;
    }
    //获得本季度的开始月份
    function getQuarterStartMonth() {
        var quarterStartMonth = 0;
        if (nowMonth < 3) {
            quarterStartMonth = 0;
        }
        if (2 < nowMonth && nowMonth < 6) {
            quarterStartMonth = 3;
        }
        if (5 < nowMonth && nowMonth < 9) {
            quarterStartMonth = 6;
        }
        if (nowMonth > 8) {
            quarterStartMonth = 9;
        }
        return quarterStartMonth;
    }

    function getDay(day) {
        var today = new Date();
        var targetday_milliseconds =
            today.getTime() + 1000 * 60 * 60 * 24 * day;
        today.setTime(targetday_milliseconds); //注意，这行是关键代码
        var tYear = today.getFullYear();
        var tMonth = today.getMonth();
        var tDate = today.getDate();
        tMonth = doHandleMonth(tMonth + 1);
        tDate = doHandleMonth(tDate);
        return tYear + "-" + tMonth + "-" + tDate;
    }

    function doHandleMonth(month) {
        var m = month;
        if (month.toString().length == 1) {
            m = "0" + month;
        }
        return m;
    }



    var startSuffix = '';
    var endSuffix = '';
    if(isCmpl) {
        startSuffix = ' 00:00:00';
        endSuffix = ' 23:59:59';
    }
    switch (val) {
        case 'day':
            // 今日
            var startStr = now.format("yyyy-MM-dd" + startSuffix);
            var endStr = now.format("yyyy-MM-dd" + endSuffix);
            params = {
                startDate: startStr,
                endDate: endStr
            };
            break;
        case 'week':
            var start = getDay(-6);
            var end = getDay(0);
            params = {
                startDate: start + startSuffix,
                endDate: end + endSuffix
            };
            break;
        case 'month':
            // 本月
            var monthStartDate = new Date(nowYear, nowMonth, 1);
            var start = formatDate(monthStartDate);
            var monthEndDate = new Date(
                nowYear,
                nowMonth,
                getMonthDays(nowMonth)
            );
            var end = formatDate(monthEndDate);
            params = {
                startDate: start + startSuffix,
                endDate: end + endSuffix
            };
            break;
        case 'season':
            // 本季度
            var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
            var start = formatDate(quarterStartDate);
            var quarterEndMonth = getQuarterStartMonth() + 2;
            var quarterStartDate = new Date(
                nowYear,
                quarterEndMonth,
                getMonthDays(quarterEndMonth)
            );
            var end = formatDate(quarterStartDate);
            params = {
                startDate: start + startSuffix,
                endDate: end + endSuffix
            };
            break;
        case 'year':
            // 本年
            var start = nowYear+ "-01-01" + startSuffix;
            var end = nowYear + "-12-31" + endSuffix;
            params = {
                startDate: start,
                endDate: end
            };
            break;
    }

    return params;
}