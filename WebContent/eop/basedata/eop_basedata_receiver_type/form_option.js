
var formOption = {
    formName: 'eopBasedataReceiverTypeForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataReceiverType',
    templateName: '',
    subjectField: 'fdName',
    mode: ''


    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        },
        eop_basedata_accounts_com_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
            sourceUrl: '/eop/basedata/eop_basedata_accounts/eopBasedataAccountsData.do?method=fdAccount&fdType=add'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {}
};