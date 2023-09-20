
var formOption = {
    formName: 'eopBasedataProvinceForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProvince'

    ,
    dialogs: {
        eop_basedata_country_getCountry: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCountry',
            sourceUrl: '/eop/basedata/eop_basedata_country/eopBasedataCountryData.do?method=getCountry'
        },

        eop_basedata_company_getCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
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