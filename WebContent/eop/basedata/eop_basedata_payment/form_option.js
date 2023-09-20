var formOption = {
    formName: 'eopBasedataPaymentForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayment',
    templateName: '',
    subjectField: 'fdSubject',
    mode: ''

    ,
    dialogs: {
        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },
        eop_basedata_pay_way_getPayWay: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',
            sourceUrl: '/eop/basedata/eop_basedata_pay_way/eopBasedataPayWayData.do?method=fdPayWay'
        },
        eop_basedata_pay_bank_fdBank: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank',
            sourceUrl: '/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBankData.do?method=fdBank'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
