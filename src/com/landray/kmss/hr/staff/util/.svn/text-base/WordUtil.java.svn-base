package com.landray.kmss.hr.staff.util;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {

	private static final String REGX = "\\$\\{[^{}]+\\}";
	
	public static byte [] generateDoc(Map<String, Object> param, InputStream input) {
		HWPFDocument document = null;
		ByteArrayOutputStream output = null;
		try{
			document = new HWPFDocument(input);
			StringBuilder text =  document.getText();
			Pattern pattern = Pattern.compile(REGX);
			Matcher matcher = pattern.matcher(text.toString());
			Range range = document.getRange();
			while(matcher.find()){
				String key = matcher.group(0);
				String mapKey = key.replace("${", "");
				mapKey = mapKey.replace("}", "");
				boolean f = false;
				for (Map.Entry<String, Object> entry : param.entrySet()) {
					if(entry.getKey().equals(mapKey)){
						f = true;
						String value = entry.getValue() != null ? (String) entry.getValue() : "";
						range.replaceText(key, value);
						break;
					}
				}   
				if(!f){
					range.replaceText(key, ""); 
				}
			}
			output = new ByteArrayOutputStream();
            document.write(output);  
            byte [] bytes = output.toByteArray();
            return bytes;  
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(output != null){
        		try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
		}
		return null;
	}

	
	
	public static byte [] generateDocx(Map<String, Object> param, InputStream input) {
		XWPFDocument doc = null;
		ByteArrayOutputStream output = null;
        try { 
            doc = new XWPFDocument(input);
            if (param != null && param.size() > 0) {  
                  
                //处理段落  
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);  
                  
                //处理表格  
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {  
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);  
                        }  
                    }  
                }  
            }  
            output = new ByteArrayOutputStream();
            doc.write(output);
            byte [] bytes = output.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();  
        } finally{
        	if(output != null){
        		try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
		return null;
	}

	private static void processParagraphs(
            List<XWPFParagraph> paragraphList, Map<String, Object> param,
            XWPFDocument doc) throws Exception {
		if(paragraphList != null && paragraphList.size() > 0){ 
			for(XWPFParagraph paragraph : paragraphList){
				List<XWPFRun> runs = paragraph.getRuns();
				int length = runs.size();
				//使用正则表达式解析${}形式包含的所有 需要替换的字段
				Pattern pattern = Pattern.compile(REGX);
				if (length > 0) {
					/*
					String text = StringUtils.join(paragraph.getRuns().toArray());
					for (int i = (length - 1); i >= 0; i--) {
						paragraph.removeRun(i);
					}
					//获取结果集
					Matcher matcher = pattern.matcher(text);
					while(matcher.find()){
						String key = matcher.group(0);
						String mapKey = key.replace("${", "");
						mapKey = mapKey.replace("}", "");
						
						String docVal = (String)param.get(mapKey);
						if(docVal==null){
							docVal = "";
						}
						text = text.replace(key, docVal);
					}
					XWPFRun newRun = paragraph.insertNewRun(0);
					newRun.setText(text, 0);
					*/
					List<Integer> runNum = new ArrayList();
					StringBuilder runText = new StringBuilder();
					String toDoStr = "";
					boolean f = false;
					for (int i=0; i<runs.size(); i++) {  
						int findCount = 0;
						XWPFRun run = runs.get(i);
						String text= run.toString();
						if(!f){
							if(text.contains("$")){
								runNum.add(i);
								runText.append(text);
								f = true;
							}
						}else{
							runNum.add(i);
							runText.append(text);
						}
						Matcher matcher = pattern.matcher(toDoStr = runText.toString());
						while(matcher.find()){
							String key = matcher.group(0);
							String mapKey = key.replace("${", "");
							mapKey = mapKey.replace("}", "");
							Object v = param.get(mapKey);
							String docVal = "";
							if(v instanceof Double){
								docVal = ((Double)v).toString();
							}else if(v instanceof Integer){
								docVal = ((Integer)v).toString();
							}else{
								docVal = (String)v;
							}

							if(docVal==null){
								docVal = "";
							}
							toDoStr = toDoStr.replace(key, docVal);
							findCount++;
						}
						if(findCount>0){
//							runText = new StringBuilder(toDoStr);
							String replaceStr = "";
							for(int k = 0;k<runNum.size();k++){
								CTRPr ctr = runs.get(runNum.get(k)).getCTR().getRPr();
								if(k==0){
									replaceStr = toDoStr;
								}else{
									replaceStr = "";
								}
								XWPFRun newRun = paragraph.insertNewRun(runNum.get(k));
								List<CTSym> symList = newRun.getCTR().getSymList();
								if("true".equals(replaceStr)){
									CTSym s = getCTSym("Wingdings 2", "F0" + Integer.toHexString(82));
									symList.add(s);  
								}else if("false".equals(replaceStr)){
									CTSym s = getCTSym("Wingdings 2", "F0" + Integer.toHexString(83));
									symList.add(s);  
								}else{
									newRun.setText(replaceStr, 0);
								}
								newRun.getCTR().setRPr(ctr);
								paragraph.removeRun(runNum.get(k)+1);
							}
							f = false;
							runText.delete(0, runText.length()+1);
							runNum.clear();
						}
					} 	
				}
			}
		}
	}
	
	public static CTSym getCTSym(String wingType, String charStr) throws Exception {
        CTSym sym = CTSym.Factory
                .parse("<xml-fragment w:font=\""  
                        + wingType  
                        + "\" w:char=\""  
                        + charStr  
                        + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");  
        return sym;  
	}
	
	public static void main(String[] args) throws IOException {
		/*Map<String, Object> map = new HashMap<String, Object>();  
        map.put("docCreateTime", "2021年5月1号"); 
        map.put("fdSigner", "夏先生"); 
        map.put("fdContent", "测试测试测试测hi策划士大夫士大夫就离开洒家分厘卡撒酒疯"); 
        map.put("fdCopyto", "抄送所有部门");
        //File file = new File("E:\\LandrayProject\\Tianma\\合同模块\\测试合同.doc");  
		File file = new File("/督办通知2001号.doc");
        InputStream in = new FileInputStream(file);  
        //generateDoc(map, in);
        byte[] b = generateDoc(map, in);
        String filepath ="D:\\test2.doc" ;   
        File f  = new File(filepath);   
        if(file.exists()){   
           file.delete();   
        }   
        FileOutputStream fos = new FileOutputStream(f);   
        fos.write(b,0,b.length);   
        fos.flush();   
        fos.close();  */
	}
}
