package common.utility;
//package sg.ngnsp.ssta.common.utility;
//
//import org.apache.poi.hssf.usermodel.HSSFPalette;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.oi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.CellUtil;
//import org.springframework.stereotype.Component;
//
//@Component
//public class HSSFUtility {
//  public void createTitleRow(HSSFSheet s, HSSFPalette palette, HSSFWorkbook wb,
//      int row) {
//    HSSFColor redish = palette.findSimilarColor((byte) 0xE6, (byte) 0x50,
//        (byte) 0x32);
//    palette.setColorAtIndex(redish.getIndex(), (byte) 0xE6, (byte) 0x50,
//        (byte) 0x32);
//
//    CellStyle headerStyle = wb.createCellStyle();
//    headerStyle.setWrapText(true);
//
//    HSSFRow r = s.createRow(row);
//
//    Cell c = r.createCell(0);
//    c.setCellValue("Internal Use Only");
//    r.createCell(1).setCellStyle(headerStyle);
//    r.createCell(2).setCellStyle(headerStyle);
//    c.setCellStyle(headerStyle);
//
//    row++;
//  }
//
//  public void createHeaderRow(HSSFSheet s, HSSFWorkbook wb, int row) {
//    CellStyle cs = wb.createCellStyle();
//    cs.setWrapText(true);
//
//    HSSFRow r = s.createRow(row);
//    r.setRowStyle(cs);
//
//    Cell c = r.createCell(0);
//    c.setCellValue("Author");
//    s.setColumnWidth(0, poiWidth(18.0));
//    c = r.createCell(1);
//    c.setCellValue("Book Name");
//    s.setColumnWidth(1, poiWidth(24.0));
//    c = r.createCell(2);
//    c.setCellValue("ISBN");
//    s.setColumnWidth(2, poiWidth(18.0));
//    c = r.createCell(3);
//    c.setCellValue("Price");
//    s.setColumnWidth(3, poiWidth(18.0));
//
//    row++;
//  }
//
//  private static int poiWidth(double width) {
//    return (int) Math.round(width * 256 + 200);
//  }
//
//}
