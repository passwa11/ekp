var formOption = {
    formName: 'eopBasedataCostCenterForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        },

        eop_basedata_cost_type_fdType: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostType',
            sourceUrl: '/eop/basedata/eop_basedata_cost_type/eopBasedataCostTypeData.do?method=fdType'
        },

        eop_basedata_cost_center_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=fdParent'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
