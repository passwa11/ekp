package com.landray.kmss.fssc.config.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.config.forms.FsscConfigListForm;
import com.landray.kmss.fssc.config.model.FsscConfigList;
import com.landray.kmss.fssc.config.service.IFsscConfigListService;
import com.landray.kmss.fssc.config.util.FsscConfigUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
  * 物资清单 服务实现
  */
public class FsscConfigListServiceImp extends ExtendDataServiceImp implements IFsscConfigListService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscConfigList) {
            FsscConfigList fsscConfigList = (FsscConfigList) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscConfigList fsscConfigList = new FsscConfigList();
        fsscConfigList.setDocCreateTime(new Date());
        fsscConfigList.setDocCreator(UserUtil.getUser());
        FsscConfigUtil.initModelFromRequest(fsscConfigList, requestContext);
        return fsscConfigList;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscConfigList fsscConfigList = (FsscConfigList) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    @Override
	public List<String> addInfoByImport(FsscConfigListForm form, HttpServletRequest request) throws Exception {
		if(form == null || form.getFormFile() == null
				|| form.getFormFile().getInputStream() == null)
			throw new Exception("文件解析失败！");
		
		// 读取文件
		Workbook workbook = WorkbookFactory.create(form.getFormFile().getInputStream());
		List<String> list = new ArrayList<String>();
		// 读取excel文件内容
		Sheet sheet = workbook.getSheetAt(0);
		for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
			try{
				getExpert(sheet.getRow(j),request);
			}catch(Exception e){
				e.printStackTrace();
				list.add("第【" + j + "】行数据解析失败:"+ e.getMessage());
			}
		}
		
		return list;
		
	}
    
    /**
   	 * 获取导入的物资清单存进数据库中																				
   	 * @param row
   	 * @return
   	 * @throws Exception
   	 */
   	private void getExpert(Row row,HttpServletRequest request) throws Exception{
   		FsscConfigList main = new FsscConfigList();
       	for (Cell cell : row) {
       		int columnIndex = cell.getColumnIndex();
//       		if(columnIndex!=7)
//       			cell.setCellType(1);
       		String cellValue = ImportUtil.getCellValue(cell);
       		if(StringUtil.isNull(cellValue)){
       			throw new Exception("字段不能为空");
       		}
       		switch (columnIndex) {
   			case 0://物资名称
   				main.setFdName(cellValue);
   				break;
   			case 1://物资编码
   				main.setFdCode(cellValue);
   				break;
   			case 2:// 物资类别
   				main.setFdGoodsType(cellValue);
   				break;
   			case 3:// 物资属性
   				main.setFdGoodsProperty(cellValue);
   				break;
   			case 4:// 最小起订量
   				try {
   					Integer value=Integer.valueOf(cellValue);
   					main.setFdMinNum(value);
				} catch (Exception e) {
					throw new Exception("最小起订量必须是整数。");
				}
   				break;
   			case 5:// 规格
   				main.setFdSpec(cellValue);
   				break;
   			case 6:// 单位
   				main.setFdUnit(cellValue);
   				break;
   			case 7:// 单价
   				try {
   					Double value=Double.valueOf(cellValue);
   					main.setFdPrice(value);
				} catch (Exception e) {
					throw new Exception("单价必须是数字类型。");
				}
   				break;
   			}
   		}
       	main.setDocCreateTime(new Date());
       	main.setDocCreator(UserUtil.getUser());
       	main.setFdId(IDGenerator.generateID());
       	add(main);
   	}
 private ISysOrgPersonService sysOrgPersonService;
    
    public ISysOrgPersonService getSysOrgPersonService() {
    	if(sysOrgPersonService==null){
    		sysOrgPersonService=(ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
    	}
		return sysOrgPersonService;
	}
}
