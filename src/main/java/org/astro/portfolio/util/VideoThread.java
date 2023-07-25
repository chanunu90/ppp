package org.astro.portfolio.util;

import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;
 
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
 
public class VideoThread extends Thread {
    private int threadNo;
    private int threadSize;
    private double plusSize;
    private File source;
    private String saveFileName;

    @Value("${org.zerock.upload.path}")
    private String path;
    
    public VideoThread(File source, int threadSize, int threadNo, double plusSize , String saveFileName) {
        this.source = source;
        this.threadSize = threadSize;
        this.threadNo = threadNo;
        this.plusSize = plusSize;
        this.saveFileName = saveFileName;
    }
 
    public void run() {
        FrameGrab grab;
        int sec = 2;
        try {
            grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));
                    // 2초라인의 섬네일 -- 우리가추가
                    double startSec = sec *  plusSize;
                    grab.seekToSecondPrecise(startSec);
                    Picture picture = grab.getNativeFrame();
                    BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
                    ImageIO.write(bufferedImage, "png", new File("C:\\nginx-1.24.0\\html", "s_" + saveFileName.split("\\.")[0] + "." + "png")); 
                      
        } catch (Exception e1) {
            e1.printStackTrace();
        }            
    }
}