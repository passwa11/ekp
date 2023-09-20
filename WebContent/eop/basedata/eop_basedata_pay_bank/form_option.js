var formOption = {
    formName: 'eopBasedataPayBankForm',
    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataPayBank'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany&type=base_auth'
         },
        fssc_cmb_account_area:{
       	 modelName: 'com.landray.kmss.fssc.cmb.model.FsscCmbAccountArea',
            sourceUrl: '/fssc/cmb/fssc_cmb_account_area/fsscCmbAccountAreaData.do?method=fdAccountArea'
       },
	     eop_basedata_accounts_fdAccount: {
	         modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataAccounts',
	         sourceUrl: '/eop/basedata/eop_basedata_accounts/eopBasedataAccountsData.do?method=fdAccount&fdType=add'
	     },
        fssc_cmbint_account_area:{
            modelName: 'com.landray.kmss.fssc.cmbint.model.FsscCmbIntAccountArea',
            sourceUrl: '/fssc/cmbint/fssc_cmbint_account_area/fsscCmbIntAccountAreaData.do?method=fdAccountArea'
        },
        fssc_cbs_account_city:{
            modelName: 'com.landray.kmss.fssc.cbs.model.FsscCbsCity',
            sourceUrl: '/fssc/cbs/fssc_cbs_city/fsscCbsCityData.do?method=fdCity'
        },
        fssc_cbs_bank_type:{
            modelName: 'com.landray.kmss.fssc.cbs.model.FsscCbsBankType',
            sourceUrl: '/fssc/cbs/fssc_cbs_bank_type/fsscCbsBankTypeData.do?method=fdBankType'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    linkValidates: [],
    dataType: {}
};
