package com.landray.kmss.sys.evaluation.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface ISysEvaluationShareService extends IExtendDataService {
    /**
     * 处理分享
     * @param form
     * @param requestContext
     */
    void saveEvalShare(IExtendForm form, RequestContext requestContext) throws Exception;
}
