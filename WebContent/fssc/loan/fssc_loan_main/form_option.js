
var formOption = {
    formName: 'fsscLoanMainForm',
    modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanMain',
    templateName: 'com.landray.kmss.fssc.loan.model.FsscLoanCategory',
    subjectField: 'docSubject',
    mode: 'main_scategory'

    ,
    dialogs: {
        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_company_getCompanyByPerson: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=loanSearch'
        },

        eop_basedata_pay_way_fdPayWay: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',
            sourceUrl: '/eop/basedata/eop_basedata_pay_way/eopBasedataPayWayData.do?method=fdPayWay'
        },

        eop_basedata_account_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccount',
            sourceUrl: '/eop/basedata/eop_basedata_account/eopBasedataAccountData.do?method=fdAccount'
        },

        eop_basedata_wbs_fdWbs: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
            sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdWbs'
        },

        eop_basedata_inner_order_fdInnerOrder: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder',
            sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdInnerOrder'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        },

        fssc_loan_fee_main_getFeeMain: {
            modelName: 'com.landray.kmss.fssc.loan.model.FsscLoanMain',
            sourceUrl: '/fssc/loan/fssc_loan_main/fsscLoanFeeData.do?method=getFeeMain'
        },
        fssc_cmb_city_code: {
          	 modelName: 'com.landray.kmss.fssc.cmb.model.FsscCmbCityCode',
             sourceUrl: '/fssc/cmb/fssc_cmb_city_code/fsscCmbCityCodeData.do?method=fdCityCode'
        },
        fssc_cbs_city: {
            modelName: 'com.landray.kmss.fssc.cbs.model.FsscCbsCity',
            sourceUrl: '/fssc/cbs/fssc_cbs_city/fsscCbsCityData.do?method=fdCity'
        },
        eop_basedata_pay_bank_fdBank: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank',
            sourceUrl: '/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBankData.do?method=fdBank'
        },
        fssc_loan_selectAuthorize:{
      		modelName:'com.landray.kmss.sys.organization.model.SysOrgPerson',
      		sourceUrl:'/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=selectAuthorize'
        },
        fssc_cmbint_city_code: {
            modelName: 'com.landray.kmss.fssc.cmbint.model.FsscCmbIntCityCode',
            sourceUrl: '/fssc/cmbint/fssc_cmbint_city_code/fsscCmbIntCityCodeData.do?method=fdCityCode'
        }

    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};
