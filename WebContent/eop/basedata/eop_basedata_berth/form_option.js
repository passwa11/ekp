var formOption = {
    formName: 'eopBasedataBerthForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBerth'

    ,
    dialogs: {
        eop_basedata_vehicle_fdVehicle: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVehicle',
            sourceUrl: '/eop/basedata/eop_basedata_vehicle/eopBasedataVehicleData.do?method=fdVehicle'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
