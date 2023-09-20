package com.landray.kmss.common.forms;

import java.io.Serializable;
import java.util.List;

import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.upload.FormFile;

/**
 * 原struts上传文件的全局表单对象
 * @author Kilery.Chen
 *
 */
@SuppressWarnings("rawtypes")
public class FileUploadForm extends ActionForm implements Serializable{
    private static final long serialVersionUID = 4712938192412L;
    
    /**
     * <pre>
     * 单文件上传：
     * 对应页面表单元素 &lt;input type="file" name="file"/>
     * 注意name要等于这个属性名
     * </pre>
     */
	private FormFile file = null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
     * <pre>
     * 多文件上传：
     * 对应页面表单元素 
     * &lt;input type="file" name="formFiles[0]"/>
     * &lt;input type="file" name="formFiles[1]"/>
     * 注意name要等于这个属性名+数组下标的写法
     * </pre>
     */
	
    private List  formFiles = new AutoArrayList(FormFile.class);
    public List getFormFiles() {
        return formFiles;
    }
    public void setFormFiles(List formFiles) {
        this.formFiles = formFiles;
    }
}
