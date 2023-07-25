package org.astro.portfolio.util;

import java.io.File;
 
public class Mp4Test { 

    public void getThumbnail(File source , String saveFileName) throws Exception {
        // plusSize 가져올 크기값
        double plusSize = 1;
        // thread사용 갯수
        int threadSize = 1;
     
        VideoThread[] videoThread = new VideoThread[threadSize];
     
        // 쓰레드 수만큼 동시 반복 처리
        for(int i = 0; i < videoThread.length; i++) {
            videoThread[i] = new VideoThread(source, threadSize, i, plusSize , saveFileName);
            videoThread[i].start();
        }

        boolean runFlag = true;
        while(runFlag) {
            runFlag = false;
            for(int i = 0; i < threadSize; i++) {
                if(videoThread[i].isAlive())
                    runFlag = true;
            }            
        }
    }
}