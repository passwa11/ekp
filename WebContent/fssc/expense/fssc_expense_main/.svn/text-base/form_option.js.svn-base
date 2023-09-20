var formOption = {
    formName: 'fsscExpenseMainForm',
    modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseMain',
    templateName: 'com.landray.kmss.fssc.expense.model.FsscExpenseCategory',
    subjectField: 'docSubject',
    mode: 'main_scategory'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },
        
        eop_basedata_expense_item_selectExpenseItem: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        },


        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_vehicle_fdVehicle: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataVehicle',
            sourceUrl: '/eop/basedata/eop_basedata_vehicle/eopBasedataVehicleData.do?method=fdVehicle'
        },
        
        eop_basedata_expense_wbs_selectWbs: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
            sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdParent'
        },
        
        eop_basedata_expense_order_selectInnerOrder: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder',
            sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdParent'
        },

        eop_basedata_berth_fdBerth: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBerth',
            sourceUrl: '/eop/basedata/eop_basedata_berth/eopBasedataBerthData.do?method=fdBerth'
        },

        eop_basedata_pay_way_getPayWay: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayWay',
            sourceUrl: '/eop/basedata/eop_basedata_pay_way/eopBasedataPayWayData.do?method=fdPayWay'
        },
        
        eop_basedata_pay_bank_fdBank: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank',
            sourceUrl: '/eop/basedata/eop_basedata_pay_bank/eopBasedataPayBankData.do?method=fdBank'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        },

        eop_basedata_account_fdAccount: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccount',
            sourceUrl: '/eop/basedata/eop_basedata_account/eopBasedataAccountData.do?method=fdAccount'
        },
        fssc_ledger_fdInvoice: {
        	modelName: 'com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice',
        	sourceUrl: '/fssc/ledger/fssc_ledger_invoice/fsscLedgerInvoiceData.do?method=fdInvoice'
        },

        eop_basedata_tax_rate_getTaxRate: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataTaxRate',
            sourceUrl: '/eop/basedata/eop_basedata_tax_rate/eopBasedataTaxRateData.do?method=getTaxRate'
        },
        
        fssc_expense_main_selectFee: {
            modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseMain',
            sourceUrl: '/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=selectFee'
        },
        fssc_expense_main_selectProapp: {
            modelName: 'com.landray.kmss.fssc.proapp.model.FsscProappMain',
            sourceUrl: '/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=selectProapp'
        },
        fssc_cmb_city_code: {
       	 modelName: 'com.landray.kmss.fssc.cmb.model.FsscCmbCityCode',
            sourceUrl: '/fssc/cmb/fssc_cmb_city_code/fsscCmbCityCodeData.do?method=fdCityCode'
       },
        fssc_cbs_city: {
            modelName: 'com.landray.kmss.fssc.cbs.model.FsscCbsCity',
            sourceUrl: '/fssc/cbs/fssc_cbs_city/fsscCbsCityData.do?method=fdCity'
        },
       fssc_expense_detail_selectNote:{
   		modelName:'com.landray.kmss.fssc.expense.model.FsscExpenseDetail',
   		sourceUrl:'/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=selectNote'
       },
       fssc_expense_selectAuthorize:{
      		modelName:'com.landray.kmss.sys.organization.model.SysOrgPerson',
      		sourceUrl:'/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=selectAuthorize'
       },
       eop_basedata_input_tax_getInputTax:{
     		modelName:'com.landray.kmss.eop.basedata.model.EopBasedataInputTax',
     		sourceUrl:'/eop/basedata/eop_basedata_input_tax/eopBasedataInputTax.do?method=getInputTax'
      },
      fssc_expense_didi_detail_getDidiDetail:{
   		modelName:'com.landray.kmss.fssc.expense.model.FsscExpenseDidiDetail',
   		sourceUrl:'/fssc/expense/fssc_expense_didi_detail/fsscExpenseDidiDetailData.do?method=getDidiDetail'
      },
      eop_basedata_city_selectCity: {
          modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCity',
          sourceUrl: '/eop/basedata/eop_basedata_city/eopBasedataCity.do?method=listCity'
      },
      fssc_expense_detail_selectTranData:{
          modelName:'com.landray.kmss.fssc.expense.model.FsscExpenseMain',
          sourceUrl:'/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=selectTranData'
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
