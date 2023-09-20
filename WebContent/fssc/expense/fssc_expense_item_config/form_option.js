var formOption = {
    formName: 'fsscExpenseItemConfigForm',
    modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        fssc_expense_category_getCategory: {
            modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseCategory',
            sourceUrl: '/fssc/expense/fssc_expense_category/fsscExpenseCategoryData.do?method=getCategory'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent&type=all'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
