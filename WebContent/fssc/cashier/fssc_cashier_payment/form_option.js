
var formOption = {
    formName: 'fsscCashierPaymentForm',
    modelName: 'com.landray.kmss.fssc.cashier.model.FsscCashierPayment'


    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_pay_way_fdPayWay: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',
            sourceUrl: '/eop/basedata/eop_basedata_pay_way/eopBasedataPayWayData.do?method=fdPayWay'
        },

        eop_basedata_pay_bank_fdBank: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank',
            sourceUrl: '/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBankData.do?method=fdBank'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },
        fssc_cmb_city_code: {
        	 modelName: 'com.landray.kmss.fssc.cmb.model.FsscCmbCityCode',
             sourceUrl: '/fssc/cmb/fssc_cmb_city_code/fsscCmbCityCodeData.do?method=fdCityCode'
         },
        fssc_cmbint_city_code: {
            modelName: 'com.landray.kmss.fssc.cmbint.model.FsscCmbIntCityCode',
            sourceUrl: '/fssc/cmbint/fssc_cmbint_city_code/fsscCmbIntCityCodeData.do?method=fdCityCode'
        },
        fssc_cbs_city: {
            modelName: 'com.landray.kmss.fssc.cbs.model.FsscCbsCity',
            sourceUrl: '/fssc/cbs/fssc_cbs_city/fsscCbsCityData.do?method=fdCity'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
