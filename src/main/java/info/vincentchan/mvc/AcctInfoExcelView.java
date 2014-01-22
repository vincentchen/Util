package info.vincentchan.mvc;

import com.cmbchina.xm.domain.AcctInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author: Vincent Chan
 * Create At: 13-12-11 下午4:21
 */
public class AcctInfoExcelView extends AbstractExcelView {
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        List<AcctInfo> acctInfos = (List) model.get("acctInfos");
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow header = sheet.createRow(0);
        header.createCell((short) 0).setCellValue("指令编号");
        header.createCell((short) 1).setCellValue("存款用户名");
        header.createCell((short) 2).setCellValue("开户银行");
        header.createCell((short) 3).setCellValue("账号");
        header.createCell((short) 4).setCellValue("储蓄点");
        header.createCell((short) 5).setCellValue("币种");
        header.createCell((short) 6).setCellValue("金额");
        header.createCell((short) 7).setCellValue("其他");
        header.createCell((short) 8).setCellValue("账户状态");

        if (acctInfos != null) {
            int rowNum = 1;
            for (AcctInfo acctInfo : acctInfos) {
                HSSFRow row = sheet.createRow(rowNum++);
                row.createCell((short) 0).setCellValue(acctInfo.getZlId());
                row.createCell((short) 1).setCellValue(acctInfo.getXm());
                row.createCell((short) 2).setCellValue("招商银行");
                row.createCell((short) 3).setCellValue(acctInfo.getKhzh());
                row.createCell((short) 4).setCellValue(acctInfo.getKhwd());
                row.createCell((short) 5).setCellValue(acctInfo.getBz());
                row.createCell((short) 6).setCellValue(acctInfo.getYe());
                row.createCell((short) 7).setCellValue("");
                row.createCell((short) 8).setCellValue(acctInfo.getZhzt());
            }
        }
        String encodedFileName = new String(("反馈结果" + model.get("applyId")).getBytes(), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
    }
}
