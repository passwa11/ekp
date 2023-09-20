package com.landray.kmss.fssc.fee.xform.impt.parsers;

import com.landray.kmss.fssc.fee.xform.impt.controls.FsscFeeCompanyImportControler;
import com.landray.kmss.sys.xform.impt.ISysFormImportControler;
import com.landray.kmss.sys.xform.impt.ISysFormImportParseContext;
import com.landray.kmss.sys.xform.impt.service.parsers.AbstractSysFormImportControlerParser;

public class FsscFeeCostCenterImportParser extends
		AbstractSysFormImportControlerParser {

	@Override
    protected ISysFormImportControler createNewControler(
			ISysFormImportParseContext context) {
		FsscFeeCompanyImportControler label = new FsscFeeCompanyImportControler();
		return label;
	}

}
