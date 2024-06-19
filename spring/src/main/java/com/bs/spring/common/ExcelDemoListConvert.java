package com.bs.spring.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.bs.spring.demo.model.dto.Demo;

// 뷰(엑셀로 응답받게) <DemoController>
@Component("excel")
public class ExcelDemoListConvert extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<Demo> demoList = (List<Demo>)model.get("demoList");
		
		final Sheet sheet = workbook.createSheet("sheet1"); //import org.apache.poi.ss.usermodel.Sheet;

		addHeader(sheet, List.of("이름", "나이", "이메일", "성별", "사용 언어"));
		
		demoList.forEach(demo->{
			addContent(sheet, demo);
		});
	}
	
	//헤더 가져오기
	private void addHeader(Sheet sheet, List<String> header) {
		Row headerRow = sheet.createRow(0);
		for(int i = 0; i < header.size(); i++) {
			headerRow.createCell(i).setCellValue(header.get(i));; // 셀 만들기 -> 셀 하나에 List<String> 하나씩
		}
	}

	private void addContent(Sheet sheet, Demo demo) {
		Row content = sheet.createRow(sheet.getLastRowNum() + 1); //sheet.getLastRowNum() : 마지막으로 만든 row숫자 가져오기
		content.createCell(0).setCellValue(demo.getDevName());
		content.createCell(1).setCellValue(demo.getDevAge());
		content.createCell(2).setCellValue(demo.getDevEmail());
		content.createCell(3).setCellValue(demo.getDevGender());
		content.createCell(4).setCellValue(String.join(",", demo.getDevLang())); //Lang - 배열 
	}
	
}
