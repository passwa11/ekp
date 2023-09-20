package com.landray.kmss.sys.evaluation.rest.convertor;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.rest.convertor.PropertyConvertorContext;
import com.landray.kmss.common.rest.convertor.PropertyConvertorSupport;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationMainService;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationNotesService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * @ClassName: SysEvaluationCountConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-15 18:29
 * @Version: 1.0
 */
public class SysEvaluationCountConvertor extends PropertyConvertorSupport {
    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        ISysEvaluationMainService sysEvaluationMainService = (ISysEvaluationMainService) SpringBeanUtil.getBean("sysEvaluationMainService");
        ISysEvaluationNotesService sysEvaluationNotesService = (ISysEvaluationNotesService) SpringBeanUtil.getBean("sysEvaluationNotesService");
        int count = sysEvaluationMainService.getRecordCountByModel((ISysEvaluationModel) value);
        //段落点评数量
        int notesCount = sysEvaluationNotesService.getNotesCountByModel((IBaseModel) value);
        return count + notesCount;
    }
}
