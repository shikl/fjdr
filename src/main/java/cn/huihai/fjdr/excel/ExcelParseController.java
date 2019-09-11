package cn.huihai.fjdr.excel;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.huix.core.bean.Message;

@Controller
public class ExcelParseController {

	@RequestMapping("excel/parse")
	@ResponseBody
	public Message parse(HttpServletResponse response,MultipartFile file) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String readXlsx = "";
		try {
			readXlsx = EexelToJson.readXlsx(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Message(true, "200", "上传成功!",readXlsx);
	}
}
