
var formOption = {
    formName: 'fsscVoucherRuleConfigForm',
    modelName: 'com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig'

    ,
    dialogs: {
        fssc_voucher_model_config_fdModel: {
            modelName: 'com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig',
            sourceUrl: '/fssc/voucher/fssc_voucher_model_config/fsscVoucherModelConfigData.do?method=fdModel'
        },
        fssc_voucher_model_config_fdCategory: {
            modelName: 'com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig',
            sourceUrl: '/fssc/voucher/fssc_voucher_model_config/fsscVoucherModelConfigData.do?method=fdCategory'
        },
        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },

        eop_basedata_cash_flow_getCashFlow: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCashFlow',
            sourceUrl: '/eop/basedata/eop_basedata_cash_flow/eopBasedataCashFlowData.do?method=fdParent'
        },

        eop_basedata_wbs_fdWbs: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
            sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdWbs'
        },

        eop_basedata_inner_order_fdInnerOrder: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder',
            sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdInnerOrder'
        },

        eop_basedata_accounts_fdMinLevel: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
            sourceUrl: '/eop/basedata/eop_basedata_accounts/eopBasedataAccountsData.do?method=fdMinLevel'
        },
        
        eop_basedata_customer_getCustomer: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCustomer',
            sourceUrl: '/eop/basedata/eop_basedata_customer/eopBasedataCustomerData.do?method=getCustomer'
        },

        eop_basedata_supplier_getSupplier: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataSupplier',
            sourceUrl: '/eop/basedata/eop_basedata_supplier/eopBasedataSupplierData.do?method=getSupplier'
        },

        eop_basedata_voucher_type_getVoucherType: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVoucherType',
            sourceUrl: '/eop/basedata/eop_basedata_voucher_type/eopBasedataVoucherTypeData.do?method=getVoucherType'
        },

        eop_basedata_erp_person_fdERPPerson: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVoucherType',
            sourceUrl: '/eop/basedata/eop_basedata_erp_person/eopBasedataErpPersonData.do?method=fdERPPerson'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        },

        eop_basedata_pay_bank_fdBank: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank',
            sourceUrl: '/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBankData.do?method=fdBank'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
