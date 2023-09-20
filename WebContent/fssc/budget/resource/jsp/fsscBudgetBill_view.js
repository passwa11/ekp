seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/util/env','lang!fssc-budget','lang!'], function($, dialog, dialogCommon,strutil,env,lang,comlang){
    window.viewBillBudget=function(fdModelId){
        dialog.iframe('/fssc/budget/fssc_budget_execute/fsscBudgetExecute.do?method=viewBillBudget&fdModelId='+fdModelId, " ", null, {width:900, height:450, topWin : window, close: true});
    }
});