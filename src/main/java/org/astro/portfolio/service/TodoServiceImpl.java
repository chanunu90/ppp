package org.astro.portfolio.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.astro.portfolio.dto.PageRequestDTO;
import org.astro.portfolio.dto.PageResponseDTO;
import org.astro.portfolio.dto.TodoDTO;
import org.astro.portfolio.dto.TodoImageDTO;
import org.astro.portfolio.mappers.TodoMappers;
import org.astro.portfolio.util.Mp4Test;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.builders.BufferedImageBuilder;


@Log4j2
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final ServletContext servletContext;

    private final TodoMappers todoMappers;

    @Value("${org.zerock.upload.path}")
    private String path;

    //예외처리
    public static class UploadException extends RuntimeException{
    
        public UploadException(String msg){
            super(msg);
        }
    }
        


    @Override
    public PageResponseDTO<TodoDTO> todoList(PageRequestDTO pageRequestDTO) {

        // pageRequestDTO.setSize(100);

        List<TodoDTO> list = todoMappers.todoSelectAll(pageRequestDTO);
        Long total = todoMappers.getListCount(pageRequestDTO);

        log.info("===========================================================");
        log.info(list);

        return PageResponseDTO.<TodoDTO>withAll()
       .list(list)
       .total(total)
       .build();
    }

    @Override
    public TodoDTO todoOne(Long tno) {
        return todoMappers.todoOne(tno);
    }

    @Override
    public Long setTodo(TodoDTO todoDTO , boolean makeThumbnail) {

        List<MultipartFile> files = todoDTO.getFiles();
        
        //파일을 저장하고 이름만 추출
        if(files == null || files.size() == 0) {
            throw new UploadException("No File");
        }

        List<String> uploadFileNames = new ArrayList<>();
        
       //loop
        for (MultipartFile mFile : files) {

            String originalFileName = mFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String mimeType = servletContext.getMimeType(originalFileName);
            log.info("mimeType: " + mimeType);

            // save할 파일이름
            String saveFileName = uuid+"_"+originalFileName;
            File saveFile = new File(path, saveFileName);
            
            // 예외처리
            try ( InputStream in = mFile.getInputStream();
                  OutputStream out = new FileOutputStream(saveFile);
            ) {

                // 파일 Copy
                FileCopyUtils.copy(in, out);

                // 이미지 파일일 경우
                if(makeThumbnail && mimeType.contains("image")) {
                    // 섬네일 생성
                    File thumOutFile = new File(path, "s_" + saveFileName);
                    Thumbnailator.createThumbnail(saveFile, thumOutFile, 200, 200);
                }
                else if(makeThumbnail && mimeType.contains("video")) {
                    
                    log.info("동영상 썸네일 만드는 자리");

                    Mp4Test mp4Test = new Mp4Test();
                    File source = new File(path, saveFile.getName().split("\\.")[0] + "." + "mp4");       
                    mp4Test.getThumbnail(source , saveFileName);
                    
                }     

                uploadFileNames.add(saveFileName);
                
            } catch (Exception e) {
                throw new UploadException("Upload Fail: " + e.getMessage());
            }
            
        }
        //이름을 디비에 저장
        log.info("여기 잘들어왔어 222222");
        TodoImageDTO todoImageDTO = new TodoImageDTO();
        Long tno = todoMappers.setTodo(todoDTO); 
        todoImageDTO.setImage_tno(todoDTO.getTno());
        log.info("=======================================");
        
        int ord = 0;
        for(String uploadFileName : uploadFileNames ){
            todoImageDTO.setImage(uploadFileName);
            todoImageDTO.setOrd(ord++);
            todoMappers.setTodoImage(todoImageDTO);
        }
        



        
        
        


        return tno;
    }

    
    
}
