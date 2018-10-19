package com.chengs.dict.service;


import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import com.chengs.dict.dao.DatabaseDao;
import com.chengs.dict.model.DictColumn;
import com.chengs.dict.model.DictTable;
import com.chengs.dict.util.DBUtil;

public class CreaterService {

	private static String[] FILEDS = new String[]{"字段名","字段类型","主键/外键","默认值","描述"};
	private static int[] COLUMN_WIDTHS = new int[] {1504,1504,1504,1504,1504};
	
	public void creater(String url,String user,String password,String dbName)throws Exception {
		Connection conn=DBUtil.getConnection(url, user, password);
		DatabaseDao databaseDao=new DatabaseDao(conn);
		List<String> tableList=databaseDao.getAllTable(dbName);
		List<DictTable> list=databaseDao.getAllColumn(tableList, dbName);
		
		XWPFDocument xdoc = new XWPFDocument();
		CreaterService xsg_data = new CreaterService();
		for (DictTable dictTable : list) {
			xsg_data.createTable(xdoc,dictTable);
		}
		String path="d:"+File.separator + dbName + ".docx";
		xsg_data.saveDocument(xdoc, path);
		System.out.println("文件生成成功,路径为"+path);
	}
	
	private void createTable(XWPFDocument xdoc,DictTable dictTable) {
		XWPFParagraph p = xdoc.createParagraph();
		setParagraphSpacingInfo(p, true, "0", "80", null, null, true, "500", STLineSpacingRule.EXACT);
		setParagraphAlignInfo(p, ParagraphAlignment.CENTER,TextAlignment.CENTER);
		XWPFRun pRun = getOrAddParagraphFirstRun(p, false, false);
		List<DictColumn> list=dictTable.getColumns();
		int count = list.size();
		// 创建表格21行3列
		XWPFTable table = xdoc.createTable((count==1?2:count+2), FILEDS.length);
		setTableBorders(table, STBorder.SINGLE, "4", "auto", "0");
		setTableWidthAndHAlign(table, "9024", STJc.CENTER);
		setTableCellMargin(table, 0, 108, 0, 108);
		setTableGridCol(table, COLUMN_WIDTHS);
		
		XWPFTableRow row = table.getRow(0);
		XWPFTableCell cell = row.getCell(0);
		setCellShdStyle(cell, true, "FFFFFF", null);
		p = getCellFirstParagraph(cell);
		pRun = getOrAddParagraphFirstRun(p, false, false);
		
		int index = 0;
		//创建行
		row = table.getRow(index);
		
		cell = row.getCell(0);
		setCellWidthAndVAlign(cell, String.valueOf(COLUMN_WIDTHS[0]), STTblWidth.DXA, STVerticalJc.TOP);
		p = getCellFirstParagraph(cell);
		pRun = getOrAddParagraphFirstRun(p, false, false);
		setParagraphRunFontInfo(p, pRun, dictTable.getTableName(), "宋体", "Times New Roman", "21", false, false, false, false, null, null, 0, 6, 0);
		
		index++;
		row = table.getRow(index);
		
		//创建列
		for(int i=0;i<FILEDS.length;i++){
			cell = row.getCell(i);
			setCellWidthAndVAlign(cell, String.valueOf(COLUMN_WIDTHS[i]), STTblWidth.DXA, STVerticalJc.TOP);
			p = getCellFirstParagraph(cell);
			pRun = getOrAddParagraphFirstRun(p, false, false);
			setParagraphRunFontInfo(p, pRun, FILEDS[i], "宋体", "Times New Roman", "21", false, false, false, false, null, null, 0, 6, 0);
		}
		index = 2;
		for (int i = 0; i < list.size(); i++) {
			DictColumn dc=list.get(i);
			row = table.getRow(index);
			//创建列
			//"字段名","字段类型","主键/外键","默认值","描述"
			String [] values=new String[] {dc.getColumnName(),dc.getColumnType(),
					dc.getColumnKey(),dc.getColumnDefault(),dc.getColumnComment()};
			for(int j=0;j<FILEDS.length;j++){
				cell = row.getCell(j);
				setCellWidthAndVAlign(cell, String.valueOf(COLUMN_WIDTHS[j]), STTblWidth.DXA, STVerticalJc.TOP);
				p = getCellFirstParagraph(cell);
				pRun = getOrAddParagraphFirstRun(p, false, false);
				setParagraphRunFontInfo(p, pRun, values[j], "宋体", "Times New Roman", "21", false, false, false, false, null, null, 0, 6, 0);
			}
			index++;
		}
	}
	
	
	/**
	 * @Description: 设置段落文本样式(高亮与底纹显示效果不同)设置字符间距信息(CTSignedTwipsMeasure)
	 * @param verticalAlign
	 *            : SUPERSCRIPT上标 SUBSCRIPT下标
	 * @param position
	 *            :字符间距位置：>0提升 <0降低=磅值*2 如3磅=6
	 * @param spacingValue
	 *            :字符间距间距 >0加宽 <0紧缩 =磅值*20 如2磅=40
	 * @param indent
	 *            :字符间距缩进 <100 缩
	 */
 
	private void setParagraphRunFontInfo(XWPFParagraph p, XWPFRun pRun,
			String content, String cnFontFamily, String enFontFamily,
			String fontSize, boolean isBlod, boolean isItalic,
			boolean isStrike, boolean isShd, String shdColor,
			STShd.Enum shdStyle, int position, int spacingValue, int indent) {
		CTRPr pRpr = getRunCTRPr(p, pRun);
		if (StringUtils.isNotBlank(content)) {
			// pRun.setText(content);
			if (content.contains("\n")) {// System.properties("line.separator")
				String[] lines = content.split("\n");
				pRun.setText(lines[0], 0); // set first line into XWPFRun
				for (int i = 1; i < lines.length; i++) {
					// add break and insert new text
					pRun.addBreak();
					pRun.setText(lines[i]);
				}
			} else {
				pRun.setText(content, 0);
			}
		}
		// 设置字体
		CTFonts fonts = pRpr.isSetRFonts() ? pRpr.getRFonts() : pRpr
				.addNewRFonts();
		if (StringUtils.isNotBlank(enFontFamily)) {
			fonts.setAscii(enFontFamily);
			fonts.setHAnsi(enFontFamily);
		}
		if (StringUtils.isNotBlank(cnFontFamily)) {
			fonts.setEastAsia(cnFontFamily);
		}
		// 设置字体大小
		CTHpsMeasure sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();
		sz.setVal(new BigInteger(fontSize));
 
		CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr
				.addNewSzCs();
		szCs.setVal(new BigInteger(fontSize));
 
		// 设置字体样式
		// 加粗
		if (isBlod) {
			pRun.setBold(isBlod);
		}
		// 倾斜
		if (isItalic) {
			pRun.setItalic(isItalic);
		}
		// 删除线
		if (isStrike) {
			pRun.setStrike(isStrike);
		}
		if (isShd) {
			// 设置底纹
			CTShd shd = pRpr.isSetShd() ? pRpr.getShd() : pRpr.addNewShd();
			if (shdStyle != null) {
				shd.setVal(shdStyle);
			}
			if (shdColor != null) {
				shd.setColor(shdColor);
				shd.setFill(shdColor);
			}
		}
 
		// 设置文本位置
		if (position != 0) {
			pRun.setTextPosition(position);
		}
	}
	
	/**
	 * @Description: 设置Table的边框
	 */
	private void setTableBorders(XWPFTable table, STBorder.Enum borderType,
			String size, String color, String space) {
		CTTblPr tblPr = getTableCTTblPr(table);
		CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders()
				: tblPr.addNewTblBorders();
		CTBorder hBorder = borders.isSetInsideH() ? borders.getInsideH()
				: borders.addNewInsideH();
		hBorder.setVal(borderType);
		hBorder.setSz(new BigInteger(size));
		hBorder.setColor(color);
		hBorder.setSpace(new BigInteger(space));
 
		CTBorder vBorder = borders.isSetInsideV() ? borders.getInsideV()
				: borders.addNewInsideV();
		vBorder.setVal(borderType);
		vBorder.setSz(new BigInteger(size));
		vBorder.setColor(color);
		vBorder.setSpace(new BigInteger(space));
 
		CTBorder lBorder = borders.isSetLeft() ? borders.getLeft() : borders
				.addNewLeft();
		lBorder.setVal(borderType);
		lBorder.setSz(new BigInteger(size));
		lBorder.setColor(color);
		lBorder.setSpace(new BigInteger(space));
 
		CTBorder rBorder = borders.isSetRight() ? borders.getRight() : borders
				.addNewRight();
		rBorder.setVal(borderType);
		rBorder.setSz(new BigInteger(size));
		rBorder.setColor(color);
		rBorder.setSpace(new BigInteger(space));
 
		CTBorder tBorder = borders.isSetTop() ? borders.getTop() : borders
				.addNewTop();
		tBorder.setVal(borderType);
		tBorder.setSz(new BigInteger(size));
		tBorder.setColor(color);
		tBorder.setSpace(new BigInteger(space));
 
		CTBorder bBorder = borders.isSetBottom() ? borders.getBottom()
				: borders.addNewBottom();
		bBorder.setVal(borderType);
		bBorder.setSz(new BigInteger(size));
		bBorder.setColor(color);
		bBorder.setSpace(new BigInteger(space));
	}
	
	/**
	 * @Description: 得到Table的CTTblPr,不存在则新建
	 */
	private CTTblPr getTableCTTblPr(XWPFTable table) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
				.getTblPr();
		return tblPr;
	}
	
	/**
	 * @Description: 设置表格总宽度与水平对齐方式
	 */
	private void setTableWidthAndHAlign(XWPFTable table, String width, STJc.Enum enumValue) {
		CTTblPr tblPr = getTableCTTblPr(table);
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
				.addNewTblW();
		if (enumValue != null) {
			CTJc cTJc = tblPr.addNewJc();
			cTJc.setVal(enumValue);
		}
		tblWidth.setW(new BigInteger(width));
		tblWidth.setType(STTblWidth.DXA);
	}
	
	/**
	 * @Description: 设置单元格Margin
	 */
	private void setTableCellMargin(XWPFTable table, int top, int left,
			int bottom, int right) {
		table.setCellMargins(top, left, bottom, right);
	}
	
	/**
	 * @Description: 设置表格列宽
	 */
	private void setTableGridCol(XWPFTable table, int[] colWidths) {
		CTTbl ttbl = table.getCTTbl();
		CTTblGrid tblGrid = ttbl.getTblGrid() != null ? ttbl.getTblGrid()
				: ttbl.addNewTblGrid();
		for (int j = 0, len = colWidths.length; j < len; j++) {
			CTTblGridCol gridCol = tblGrid.addNewGridCol();
			gridCol.setW(new BigInteger(String.valueOf(colWidths[j])));
		}
	}
	
	/**
	 * @Description: 设置底纹
	 */
	private void setCellShdStyle(XWPFTableCell cell, boolean isShd,
			String shdColor, STShd.Enum shdStyle) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		if (isShd) {
			// 设置底纹
			CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
			if (shdStyle != null) {
				shd.setVal(shdStyle);
			}
			if (shdColor != null) {
				shd.setColor(shdColor);
				shd.setFill(shdColor);
			}
		}
	}
	
	/**
	 * @Description: 得到CTTrPr,不存在则新建
	 */
	private CTTrPr getRowCTTrPr(XWPFTableRow row) {
		CTRow ctRow = row.getCtRow();
		CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
		return trPr;
	}
	
	/**
	 * 
	 * @Description: 得到Cell的CTTcPr,不存在则新建
	 */
	private CTTcPr getCellCTTcPr(XWPFTableCell cell) {
		CTTc cttc = cell.getCTTc();
		CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
		return tcPr;
	}
	
	/**
	 * @Description: 设置段落间距信息,一行=100 一磅=20
	 */
	private void setParagraphSpacingInfo(XWPFParagraph p, boolean isSpace,
			String before, String after, String beforeLines, String afterLines,
			boolean isLine, String line, STLineSpacingRule.Enum lineValue) {
		CTPPr pPPr = getParagraphCTPPr(p);
		CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing()
				: pPPr.addNewSpacing();
		if (isSpace) {
			// 段前磅数
			if (before != null) {
				pSpacing.setBefore(new BigInteger(before));
			}
			// 段后磅数
			if (after != null) {
				pSpacing.setAfter(new BigInteger(after));
			}
			// 段前行数
			if (beforeLines != null) {
				pSpacing.setBeforeLines(new BigInteger(beforeLines));
			}
			// 段后行数
			if (afterLines != null) {
				pSpacing.setAfterLines(new BigInteger(afterLines));
			}
		}
		// 间距
		if (isLine) {
			if (line != null) {
				pSpacing.setLine(new BigInteger(line));
			}
			if (lineValue != null) {
				pSpacing.setLineRule(lineValue);
			}
		}
	}
	
	/**
	 * @Description: 得到段落CTPPr
	 */
	private CTPPr getParagraphCTPPr(XWPFParagraph p) {
		CTPPr pPPr = null;
		if (p.getCTP() != null) {
			if (p.getCTP().getPPr() != null) {
				pPPr = p.getCTP().getPPr();
			} else {
				pPPr = p.getCTP().addNewPPr();
			}
		}
		return pPPr;
	}
	
	/**
	 * @Description: 设置段落对齐
	 */
	private void setParagraphAlignInfo(XWPFParagraph p,
			ParagraphAlignment pAlign, TextAlignment valign) {
		if (pAlign != null) {
			p.setAlignment(pAlign);
		}
		if (valign != null) {
			p.setVerticalAlignment(valign);
		}
	}
	
	private XWPFRun getOrAddParagraphFirstRun(XWPFParagraph p, boolean isInsert,
			boolean isNewLine) {
		XWPFRun pRun = null;
		if (isInsert) {
			pRun = p.createRun();
		} else {
			if (p.getRuns() != null && p.getRuns().size() > 0) {
				pRun = p.getRuns().get(0);
			} else {
				pRun = p.createRun();
			}
		}
		if (isNewLine) {
			pRun.addBreak();
		}
		return pRun;
	}
	
	/**
	 * @Description: 得到单元格第一个Paragraph
	 */
	private XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
		XWPFParagraph p;
		if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
			p = cell.getParagraphs().get(0);
		} else {
			p = cell.addParagraph();
		}
		return p;
	}
	
	/**
	 * @Description: 设置列宽和垂直对齐方式
	 */
	private void setCellWidthAndVAlign(XWPFTableCell cell, String width,
			STTblWidth.Enum typeEnum, STVerticalJc.Enum vAlign) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		CTTblWidth tcw = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
		if (width != null) {
			tcw.setW(new BigInteger(width));
		}
		if (typeEnum != null) {
			tcw.setType(typeEnum);
		}
		if (vAlign != null) {
			CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
					.addNewVAlign();
			vJc.setVal(vAlign);
		}
	}
	
	/**
	 * @Description: 设置行高
	 */
//	private void setRowHeight(XWPFTableRow row, String hight) {
//		CTTrPr trPr = getRowCTTrPr(row);
//		CTHeight trHeight;
//		if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {
//			trHeight = trPr.getTrHeightList().get(0);
//		} else {
//			trHeight = trPr.addNewTrHeight();
//		}
//		trHeight.setVal(new BigInteger(hight));
//	}
	
	/**
	 * @Description: 跨列合并
	 */
	private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
			int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
			if (cellIndex == fromCell) {
				// The first merged cell is set with RESTART merge value
				getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one,are set with CONTINUE
				getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
			}
		}
	}
	
	/**
	 * @Description: 得到XWPFRun的CTRPr
	 */
	private CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
		CTRPr pRpr = null;
		if (pRun.getCTR() != null) {
			pRpr = pRun.getCTR().getRPr();
			if (pRpr == null) {
				pRpr = pRun.getCTR().addNewRPr();
			}
		} else {
			pRpr = p.getCTP().addNewR().addNewRPr();
		}
		return pRpr;
	}
	
	/**
	 * @Description: 保存文档
	 */
	private void saveDocument(XWPFDocument document, String savePath)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(savePath);
		document.write(fos);
		fos.close();
	}

}
