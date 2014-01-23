package info.vincentchan.mvc;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Author: Vincent Chan
 * Create At: 13-12-31 上午11:23
 */
public class AcctInfoPdfView extends AbstractIText5PdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        java.util.List<AcctInfo> acctInfos = (java.util.List<AcctInfo>) model.get("acctInfos");
        Paragraph header = new Paragraph(new Chunk("PDF 输出测试",
                getChineseFont(24)));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Paragraph(" "));
        //设置Table表格,创建一个7列的表格
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(98);
        table.setHeaderRows(0);
        int width[] = {10, 15, 10, 17, 18, 5, 15, 5, 5};//设置每列宽度比例
        table.setWidths(width);
//        table.set(Element.ALIGN_MIDDLE);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        PdfPCell cell = new PdfPCell(new Paragraph("指令编号", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("存款用户名", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("开户银行", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("账号", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("储蓄点", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("币种", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("金额", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("其他", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("状态", getChineseFont(10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.ORANGE);
        table.addCell(cell);

        if (acctInfos != null) {
            for (AcctInfo acctInfo : acctInfos) {
                cell = new PdfPCell(new Paragraph(acctInfo.getZlId(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getXm(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("招商银行", getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getKhzh(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getKhwd(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getBz(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getYe(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("", getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(acctInfo.getZhzt(), getChineseFont(10)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
        }
        document.add(table);
        String encodedFileName = new String(("反馈结果" + model.get("applyId")).getBytes(), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
    }

    private static final Font getChineseFont(float size) {
        Font FontChinese = null;
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            FontChinese = new Font(bfChinese, size, Font.NORMAL);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return FontChinese;
    }
}
