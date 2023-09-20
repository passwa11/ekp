var formOption = {
    formName: 'fsscFeeMainForm',
    modelName: 'com.landray.kmss.fssc.fee.model.FsscFeeMain',
    templateName: 'com.landray.kmss.fssc.fee.model.FsscFeeTemplate',
    subjectField: 'docSubject',
    mode: 'main_template'
    , dialogs: {
    	fssc_fee_to_expense: {
             modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseCategory',
             sourceUrl: '/fssc/fee/fssc_fee_template/fsscFeeTemplateData.do?method=getExpenseCategory'
         },
         eop_basedata_company_fdCompany: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
             sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
         },

         eop_basedata_cost_center_selectCostCenter: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
             sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
         },
         eop_basedata_cost_center_selectCostCenterGroup: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
             sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=fdParent'
         },

         eop_basedata_expense_item_selectExpenseItem: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
             sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
         },


         eop_basedata_project_project: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
             sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
         },

         eop_basedata_currency_fdCurrency: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
             sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
         },
         eop_basedata_expense_wbs_selectWbs: {
             modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
             sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdParent'
         },
         
         eop_basedata_expense_order_selectInnerOrder: {
             modelName: 'com.landray.kmss.eop.basedata.model.FsscInnerOrder',
             sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdParent'
         }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
