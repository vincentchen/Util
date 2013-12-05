import info.vincentchan.util.FileHelper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vincent.Chan
 * @since 2012.04.27 11:26
 */
public class FileHelperTest {
    @Test
    public void testFileHelper() throws IOException {
        System.out.println(new File("d://APPLE_62004.bmp").getAbsolutePath());
        FileHelper.createThumbnail("d://APPLE_62004.bmp", "d://dest.png");
    }

    @Test
    public void testURLDecode() throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode("%E5%9C%A8%E7%BA%BF%E6%9D%A1%E6%AC%BE%E5%88%B0%E5%BA%95%E7%AB%AF%E9%98%B3%E5%85%89%E6%99%AE%E7%85%A7%E6%A0%B7%E6%9D%BF%E5%87%B3", "UTF-8"));
    }

    /**
     * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss
     *
     * @param patternString 需要验证的字符串
     * @return 合法返回 true ; 不合法返回false
     */
    public static boolean isTimeLegal(String patternString) {
        Pattern a = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher b = a.matcher(patternString);
        if (b.matches()) {
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void testPattern() {
        assert isTimeLegal("2012-01-01 12:05:01");
        assert isTimeLegal("2012-01-01");
        assert isTimeLegal("2012-1-1");
        assert isTimeLegal("2012-01-01 20:5:1");
    }

    @Test
    public void testScaleImage() throws IOException {
        // 得到图片
        /*BufferedImage src = ImageIO.read(new File("d://Andromeda Galaxy1.JPG"));
        int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        // 根据原图的大小生成空白画布
        BufferedImage tempImg = new BufferedImage(old_w, old_h,
                BufferedImage.TYPE_INT_RGB);
        // 在新的画布上生成原图的缩略图
        Graphics2D g = tempImg.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, old_w, old_h);
        g.drawImage(src, 0, 0, old_w, old_h, Color.white, null);
        g.dispose();
        BufferedImage newImg = new BufferedImage(200, 200,
                BufferedImage.TYPE_INT_RGB);
        newImg.getGraphics().drawImage(
                tempImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH), 0,
                0, null);*/
        FileHelper.createThumbnail(new File("/Users/vincent/Downloads/18547b67001aa3828bdb8975950afdc1.jpg"), new File("/Users/vincent/Downloads/2.jpg"), 96);
        /*BufferedImage bufferedImage = ImageUtils.resizeImage(ImageIO.read(new File("d://4e2cbd4c-8547-398b-b54b-5b85db5231df.jpg")), ImageUtils.IMAGE_JPEG, 96);
        ImageUtils.saveImage(bufferedImage, "d:/test.jpg", ImageUtils.IMAGE_JPEG);*/
    }

    @Test
    public void testDate() {
        System.out.println(new java.sql.Date(new java.util.Date().getTime()));
    }
}
