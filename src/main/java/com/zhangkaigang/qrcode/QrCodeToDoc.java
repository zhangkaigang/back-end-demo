package com.zhangkaigang.qrcode;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.HeaderFooterType;
import com.aspose.words.SaveFormat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * @Description:将二维码写入Word2003中
 * @Author:zhang.kaigang
 * @Date:2022/4/27
 * @Version:1.0
 * mvn install:install-file -Dfile=E:\Repository\com\aspose\aspose-words\15.8.0\aspose.words-15.8.0.jar -DgroupId=com.aspose -DartifactId=aspose-words -Dversion=15.8.0 -Dpackaging=jar
 * 按两下ctrl，输入以上命令直接安装jar，或者直接参照本项目pom.xml直接引入需要配合includeSystemScope打包
 *
 * 参考：
 * https://blog.csdn.net/qq_32864189/article/details/112968257
 */
public class QrCodeToDoc {

    public static void main(String[] args) throws Exception{
        AsposeUtil.getAsposeLicense();
        InputStream is = new FileInputStream(new File("D:\\test.doc"));
        com.aspose.words.Document doc=new com.aspose.words.Document(is);
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.getPageSetup().setOddAndEvenPagesHeaderFooter(false);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);
        //靠右
        builder.getParagraphFormat().setAlignment(com.aspose.words.ParagraphAlignment.RIGHT);
        StringBuilder sb = new StringBuilder();
        sb.append("当前时间：").append(new Date()).append("\n");
        BufferedImage qrCodeImg = QrCodeUtils.createQrCodeImg(sb.toString(), 175, 175);
        builder.insertImage(qrCodeImg, 80, 80);
        FileOutputStream outStream = new FileOutputStream("D:\\testTemp.doc");
        doc.save(outStream, SaveFormat.DOC);
    }
}
