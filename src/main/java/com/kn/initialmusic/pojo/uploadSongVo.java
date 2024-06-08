package com.kn.initialmusic.pojo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
public class uploadSongVo {

//    private MultipartFile[] file;
    private String[] songNames;
    private Album album;

}
