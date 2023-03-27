package com.fhao;

import com.fhao.domain.JsonResponse;
import com.fhao.domain.Video;
import com.fhao.service.ElasticSearchService;
import com.fhao.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private ElasticSearchService elasticSearchService;
    @Autowired
    FastDFSUtil fastDFSUtil;
    @GetMapping("/slices")
    public void slices(MultipartFile file) throws IOException {
        fastDFSUtil.converFileToSlices(file);

    }
    @GetMapping("/es-videos")
    public JsonResponse<Video> getEsVideos(@RequestParam String keyword){
        Video video = elasticSearchService.getVideos(keyword);
        return new JsonResponse<>(video);
    }

}
