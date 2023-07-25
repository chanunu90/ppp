package org.astro.portfolio.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TodoDTO {
  
  private Long tno;
  private String title;
  private String writer;
  private String contents;

  private boolean state;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDate regDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDate updateDate;

  //DB 처리 용도 - 대표이미지 하나만 가지고올거다.
  private String images;

  // 썸네일 작업
  private String picture;

  //등록,수정 업로드된 파일 데이터를 수집하는 용도
  @Builder.Default
  private List<MultipartFile> files = new ArrayList<>(); //업로드 파일

  public String getPicture() {
    if(images!=null){
      picture = images.split("\\.")[0] + "." + "png"; 
          return picture;
    }
    return "";
    

  }

}
