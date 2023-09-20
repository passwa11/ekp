
var formOption = {
    formName: 'fsscExpenseBalanceForm',
    modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseBalance',
    templateName: '',
    subjectField: 'docSubject',
    mode: ''

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },
        
        eop_basedata_voucher_type_selectVoucherType: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVoucherType',
            sourceUrl: '/eop/basedata/eop_basedata_voucher_type/eopBasedataVoucherTypeData.do?method=getVoucherType'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent&type=all'
        },

        eop_basedata_accounts_com_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
            sourceUrl: '/eop/basedata/eop_basedata_accounts/eopBasedataAccountsData.do?method=fdAccount'
        },

        eop_basedata_cash_flow_getCashFlow: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCashFlow',
            sourceUrl: '/eop/basedata/eop_basedata_cash_flow/eopBasedataCashFlowData.do?method=fdParent'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
