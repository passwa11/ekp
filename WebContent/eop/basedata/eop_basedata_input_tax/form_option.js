
var formOption = {
    formName: 'eopBasedataInputTaxForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInputTax'

    ,
    dialogs: {
        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        },

        eop_basedata_accounts_com_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
            sourceUrl: '/eop/basedata/eop_basedata_accounts/eopBasedataAccountsData.do?method=fdAccount'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {}
};
