package com.landray.kmss.km.archives.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;

public class KmArchivesRenewValidator implements IAuthenticationValidator {

	private IKmArchivesBorrowService kmArchivesBorrowService;

	public void setKmArchivesBorrowService(IKmArchivesBorrowService kmArchivesBorrowService) {
		this.kmArchivesBorrowService = kmArchivesBorrowService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String fdBorrowId = validatorContext.getValidatorParaValue("recid");
		KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) kmArchivesBorrowService.findByPrimaryKey(fdBorrowId);
		Boolean result = false;
		List fdBorrowDetails = kmArchivesBorrow.getFdBorrowDetails();
		Date nowDate = new Date();
		if (fdBorrowDetails.size() > 0) {
			for (int i = 0; i < fdBorrowDetails.size(); i++) {
				KmArchivesDetails kad = (KmArchivesDetails) fdBorrowDetails.get(i);
				if (!KmArchivesConstant.BORROW_STATUS_EXPIRED.equals(kad.getFdStatus())) {
					Date fdRenewReturnDate = kad.getFdRenewReturnDate();
					Date fdReturnDate = kad.getFdReturnDate();
					if (fdRenewReturnDate != null) {
						if (fdRenewReturnDate.getTime() > nowDate.getTime()) {
							result = true;
						}
					} else if (fdReturnDate != null) {
						if (fdReturnDate.getTime() > nowDate.getTime()) {
							result = true;
						}
					}
				}
			}

		}
		return result;
	}
}
