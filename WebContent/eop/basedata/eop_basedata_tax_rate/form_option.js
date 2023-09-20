var formOption = {
    formName: 'eopBasedataTaxRateForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataTaxRate'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        },

        eop_basedata_accounts_com_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
            sourceUrl: '/eop/basedata/eop_basedata_accounts_com/eopBasedataAccountsData.do?method=fdAccount&fdType=add'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
