package com.zhangkaigang.qrcode;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Description:将二维码写入Word2007中
 * @Author:zhang.kaigang
 * @Date:2022/4/27
 * @Version:1.0
 * 参考：https://blog.csdn.net/qq_37043780/article/details/83385656
 */
public class QrCodeToDocx {

    public static void main(String[] args) throws Exception{
        InputStream is = new FileInputStream(new File("D:\\test.docx"));
        XWPFDocument doc = new XWPFDocument(is);
        // 添加页眉，在页眉上添加二维码图片
        CTSectPr sectPr = doc.getDocument().getBody().getSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy;
        if(sectPr!=null) {
            headerFooterPolicy = doc.getHeaderFooterPolicy();
        } else {
            sectPr = doc.getDocument().getBody().addNewSectPr();
            headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        }
        XWPFHeader header = headerFooterPolicy.createHeader(STHdrFtr.DEFAULT);
        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.RIGHT);
        int twipsPerInch = 1440;
        tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        XWPFRun run = paragraph.createRun();
        run.setColor("fc5531");
        run.addTab();
        StringBuilder sb = new StringBuilder();
        sb.append("当前时间：").append(new Date()).append("\n");
        BufferedImage qrCodeImg = QrCodeUtils.createQrCodeImg(sb.toString(), 175, 175);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImg, "png", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

        XWPFPicture picture = run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, null, Units.toEMU(80), Units.toEMU(80));
        String blipID = "";
        for (XWPFPictureData picturedata : header.getAllPackagePictures()) {
            blipID = header.getRelationId(picturedata);
        }
        picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
        doc.write(new FileOutputStream("D:\\testTemp.docx"));
    }
}
