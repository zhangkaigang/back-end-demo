package com.zhangkaigang.qrcode;

import java.io.*;

/**
 * @Description:将word转换成pdf
 * @Author:zhang.kaigang
 * @Date:2022/4/27
 * @Version:1.0
 * 参考：
 * https://blog.csdn.net/cheng137666/article/details/111677549
 * https://blog.csdn.net/qq_41394352/article/details/123319046
 */
public class WordToPdf {

    public static void main(String[] args) throws Exception{
        AsposeUtil.getAsposeLicense();
        File basicFile = new File("D:\\test.docx");
        byte[] bytes = AsposeUtil.fileToByte(basicFile);
        // 如果doc文件转成的pdf打不开的话，可先将doc字节流转成docx字节流，再转成pdf
        // AsposeWordUtils.docToDocx(bytes);
        // word字节数组转pdf字节数组
        byte[] result = AsposeUtil.byteToPdf(bytes);
        InputStream inputStream = new ByteArrayInputStream(result);

        File tempFile = new File("D:\\testTemp2Pdf.pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

        QrCodeToPdf.setQrCodeForPDF(bos, inputStream);
    }

}
