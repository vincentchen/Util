package info.vincentchan.util;

import com.mortennobel.imagescaling.ResampleOp;
import info.vincentchan.ibaby.entities.DocLocate;
import info.vincentchan.ibaby.util.DocBaseDir;
import info.vincentchan.ibaby.util.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Vincent.Chan
 * @since 2012.04.16 11:12
 */
public class FileHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);
    public static final int TAG_SIZE = 200;
    private static java.util.List<String> imageTypes = Arrays.asList(new String[]{".png", ".gif", ".jpg", ".jpeg", ".bmp"});
    private static java.util.List<String> audioTypes = Arrays.asList(new String[]{".mp3", ".mp2", ".mp1", ".wav", ".ra", ".wma", ".m4a", ".mid", ".xmf", ".ogg", ".amr"});
    private static java.util.List<String> videoTypes = Arrays.asList(new String[]{".avi", ".3gp", ".mp4", ".rm", ".rmvb", ".mkv", ".wmv", ".ts", ".mpg", ".mpeg", ".mov"});

    /**
     * 保存上传文件到附件目录
     *
     * @param file      要保存的文件
     * @param dir       保存目录，实际路径为系统配置 附件路径+dir
     * @param thumbnail 是否生成缩略图，仅对图片有效
     * @return DocLocate 文档的一些信息，可供后续插数据库使用
     */
    public static DocLocate saveFileToDir(MultipartFile file, String docType, String dir, boolean thumbnail) throws IOException {
        if (file == null || !StringUtils.hasText(dir) || !StringUtils.hasText(file.getOriginalFilename())) {
            return null;
        }

        logger.debug("文件保存路径{}", dir);
        DocLocate doc = new DocLocate();
        doc.setDocTitle(file.getOriginalFilename());
        String extension = getFileExtention(file.getOriginalFilename());
        doc.setDocName(DigestHelper.md5(System.currentTimeMillis() + doc.getDocName()) + extension);
        doc.setDocSize(file.getSize());
        doc.setDocType(docType);
        doc.setDocDir(DocBaseDir.defaultDir(docType));
        //doc.setMimeType(extractDocType(mimeType));
        File attachDir = new File(dir);
        if (!attachDir.exists()) {
            attachDir.mkdir();
        }
        File dest = new File(dir + File.separatorChar + doc.getDocName());

        file.transferTo(dest);

        doc.setMimeType(extractDocType(file));

        if (thumbnail && doc.getMimeType().equals(MimeType.IMAGE)) {
            createThumbnail(dest.getAbsolutePath(), dir + File.separatorChar + "s_" + doc.getDocName());
        }
        return doc;
    }

    public static void createThumbnail(File source, File dest) throws IOException {
        createThumbnail(source, dest, TAG_SIZE);
    }

    public static void createThumbnail(String source, String dest) throws IOException {
        createThumbnail(new File(source), new File(dest), TAG_SIZE);
    }

    public static void createThumbnail(String source, String dest, int size) throws IOException {
        createThumbnail(new File(source), new File(dest), size);
    }

    public static void createThumbnail(File source, File writeTo, int size) throws IOException {
        if (size <= 0) {
            size = TAG_SIZE;
        }

        BufferedImage image = ImageIO.read(source);
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        if (imageWidth < size || imageHeight < size) {
            int realSize = Math.min(imageHeight, imageWidth);
            imageHeight = imageWidth = realSize;
        } else {
            imageWidth = imageHeight = size;
        }

        String format = "jpg";
        if (source.getName().toLowerCase().endsWith(".png")) {
            format = "png";
        } else if (source.getName().toLowerCase().endsWith(".gif")) {
            format = "gif";
        }

        ResampleOp resampleOp = new ResampleOp(imageWidth, imageHeight);// 转换
        ImageIO.write(resampleOp.filter(image, null), format, writeTo);
        /* BufferedImage bufferedImage = ImageUtils.resizeImage(ImageIO.read(source), format, size);
           ImageUtils.saveImage(bufferedImage, writeTo.getAbsolutePath(), format);
        */   /*Image src = ImageIO.read(source);
        int srcWidth = src.getWidth(null), srcHeight = src.getHeight(null);
        logger.debug("原图大小{}x{}", srcWidth, srcHeight);
        int destWidth = 0, destHeight = 0;
        float temp;
        if (srcWidth < size || srcHeight < size) {
            FileInputStream fis= new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(writeTo);
            IOUtils.copy(fis, fos);
            fis.close();
            fos.close();
            return;
        }
        if (srcWidth > srcHeight) {
            temp = srcWidth / (float) size;
        } else {
            temp = srcHeight / (float) size;
        }

        destWidth = Math.round(srcWidth / temp);
        destHeight = Math.round(srcHeight / temp);
        logger.debug("缩略图大小{}x{}", destWidth, destHeight);
        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src, 0, 0, destWidth, destHeight, null);
        FileOutputStream newimage = new FileOutputStream(writeTo);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
        encoder.encode(tag);
        newimage.flush();
        newimage.close();*/
    }

    public static String extractDocType(MultipartFile file) {
        if (file == null || !StringUtils.hasText(file.getContentType())) {
            return MimeType.UNKNOWN;
        }
        String mimeType = file.getContentType();
        String extension = getFileExtention(file.getOriginalFilename());
        if (mimeType.toLowerCase().startsWith("image") || imageTypes.contains(extension.toLowerCase())) {
            return MimeType.IMAGE;
        } else if (mimeType.toLowerCase().startsWith("audio") || audioTypes.contains(extension.toLowerCase())) {
            return MimeType.AUDIO;
        } else if (mimeType.toLowerCase().startsWith("video") || videoTypes.contains(extension.toLowerCase())) {
            return MimeType.VIDEO;
        } else {
            return MimeType.ELSE;
        }
    }

    public static String getFileExtention(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return "";
        }
        int extIndex = fileName.lastIndexOf('.');
        String extension = "";
        if (extIndex > -1) {
            extension = fileName.substring(extIndex);
        }
        return extension;
    }
}