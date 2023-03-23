package com.fhao;

import com.fhao.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * author: FHao
 * create time: 2023-03-23 13:44
 * description:
 */
@RestController
public class DemoApi {
    @Autowired
    FastDFSUtil fastDFSUtil;
    @GetMapping("/slices")
    public void slices(MultipartFile file) throws IOException {
        fastDFSUtil.converFileToSlices(file);

    }
}
