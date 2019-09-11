package cn.huihai.fjdr.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EexelToJson {
	
	static JSONArray key = new JSONArray();
	public static void main(String[] args) {
        try {
        	String s = readXlsx(new FileInputStream("D:/workspace/慧海/福建平台/1年级思想品德活动项目数据数据统计.xls.xlsx"));
            System.out.println(s);
            /*JSONArray jsonArray = JSONArray.fromObject(s);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String s1 = jsonObject.get("Sheet1").toString();
            jsonArray = JSONArray.fromObject(s1);
            s1 = jsonArray.getJSONObject(0).toString();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Test i = gson.fromJson(s1, test.class);
            System.out.println(i);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private static void initKeyMap() {
		Field[] declaredFields = SiXiangPinDe.class.getDeclaredFields();
		for(int i=0;i<declaredFields.length;i++){
			Field field = declaredFields[i];
			if(!"this$0".equals(field.getName())){
				key.add(field.getName());
			}
		}
	}
    /**
     * 读取xlsx文件
     *
     * @param path
     * @throws IOException
     * @throws ParseException
     */
    public static String readXlsx(InputStream inputStream) throws IOException {
    	initKeyMap();
        JSONArray dataArray = new JSONArray();

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            String sheetName = xssfSheet.getSheetName();
            if (xssfSheet == null) {
                continue;
            }
            //当前sheet的json文件
            JSONObject sheetJson = new JSONObject();
            //当前sheet的array，作为sheetJson 的value值
            JSONArray sheetArr = new JSONArray();
            
            int xssfLastRowNum = xssfSheet.getLastRowNum();
            // 循环行Row,从第5行开始
            for (int rowNum = 4; rowNum <= xssfLastRowNum; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }
                // 循环列Cell，在这里组合json文件
                int firstCellNum = xssfRow.getFirstCellNum();
                int lastCellNum = xssfRow.getLastCellNum();
                //一行的json数据
                JSONObject rowJson = new JSONObject();
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                    XSSFCell cell = null;
                    try {
                        cell = xssfRow.getCell(cellNum);
                        if (cell == null) {
                            continue;
                        }
                        //若是列号超过了key的大小，则跳过
                        if (cellNum >= key.size()) 
                        	continue;
                        rowJson.put(key.getString(cellNum), toString(cell));
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (rowJson.keySet().size() > 0)
                    sheetArr.add(rowJson);
            }
            sheetJson.put(sheetName, shuffleData(sheetArr));
            dataArray.add(sheetJson);
        }
        return dataArray.toString();
    }

	public static Object toString(XSSFCell cell) {
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                cell.setCellType(CellType.STRING);
                return "";
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    return sdf.format(cell.getDateCellValue());
                }
                cell.setCellType(CellType.STRING);
                return cell.getStringCellValue();
            case STRING:
                String val = cell.getStringCellValue();
                if ("无".equalsIgnoreCase(val)) return "";
                //将其中的map格式和数组格式的字符串，转化为相应的数据类型
                if (val.indexOf("{") > -1) {
                    JSONObject jsonObject = JSONObject.fromObject(val);
                    Map<String, Integer> mapJson = JSONObject.fromObject(jsonObject);
                    return mapJson;
                }
                if (val.indexOf("[") > -1) {
                    val = val.substring(1, val.length() - 1);
                    String[] array = val.split(",");
                    return array;
                }
                return val;
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case ERROR:
                return "非法字符";
            default:
                return "未知字符";
        }
    }

    public static JSONArray shuffleData(JSONArray sheetArr) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < sheetArr.size(); i++) {
            JSONObject object = sheetArr.getJSONObject(i);
            int count = 0;
            int length = 0;
            for (Object key : object.keySet()) {
                Object o = object.get((String) key);
                length++;
                boolean b = StringUtils.isEmpty(o.toString());
                if (b) {
                    count++;
                }
            }
            if (count != length) {
                array.add(object);
            }
        }
        return array;
    }

    @Getter
    @Setter
    class SiXiangPinDe {
    	private String student_name;
    	private String stuNo;
        private String caucus_times;
        private String caucus_hours;
        private String volunt_times;
        private String volunt_hours;
        
        private String assoc_times;
        private String assoc_hours;
        private String welfare_times;
        private String welfare_hours;
        private String satate;
    }
}
