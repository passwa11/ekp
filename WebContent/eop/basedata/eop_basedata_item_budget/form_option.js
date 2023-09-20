var formOption = {
    formName: 'eopBasedataItemBudgetForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataItemBudget'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        },

        eop_basedata_budget_scheme_fdCategory: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme',
            sourceUrl: '/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetSchemeData.do?method=fdCategory'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
