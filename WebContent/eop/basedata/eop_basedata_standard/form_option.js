var formOption = {
    formName: 'eopBasedataStandardForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataStandard'

    ,
    dialogs: {
        eop_basedata_level_fdLevel: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataLevel',
            sourceUrl: '/eop/basedata/eop_basedata_level/eopBasedataLevelData.do?method=fdLevel'
        },

        eop_basedata_area_fdArea: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataArea',
            sourceUrl: '/eop/basedata/eop_basedata_area/eopBasedataAreaData.do?method=fdArea&fdTypeArea=fdTypeArea'
        },

        eop_basedata_vehicle_fdVehicle: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVehicle',
            sourceUrl: '/eop/basedata/eop_basedata_vehicle/eopBasedataVehicleData.do?method=fdVehicle'
        },

        eop_basedata_berth_fdBerth: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBerth',
            sourceUrl: '/eop/basedata/eop_basedata_berth/eopBasedataBerthData.do?method=fdBerth'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
        },
        eop_basedata_special_item: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataSpecialItem',
            sourceUrl: '/eop/basedata/eop_basedata_special_item/eopBasedataSpecialItemData.do?method=fdSpecialItem'
        },
        eop_basedata_city_selectCity: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCity',
            sourceUrl: '/eop/basedata/eop_basedata_city/eopBasedataCity.do?method=listCity'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
