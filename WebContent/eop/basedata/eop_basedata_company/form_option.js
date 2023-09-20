var formOption = {
    formName: 'eopBasedataCompanyForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany'

    ,
    dialogs: {
        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_company_group_fdGroup: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup',
            sourceUrl: '/eop/basedata/eop_basedata_company_group/eopBasedataCompanyGroupData.do?method=fdGroup'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
