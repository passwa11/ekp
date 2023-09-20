
var formOption = {
    formName: 'fsscLoanCategoryForm',
    modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanCategory'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_accounts_com_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccountsCom',
            sourceUrl: '/eop/basedata/eop_basedata_accounts_com/eopBasedataAccountsComData.do?method=fdAccount'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
