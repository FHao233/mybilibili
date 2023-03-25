package com.fhao.api;

import com.fhao.domain.JsonResponse;
import com.fhao.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * author: FHao
 * create time: 2023-03-23 14:34
 * description:
 */
@RestController
public class FileApi {
    @Autowired
    private FileService fileService;
    @PostMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile slice,
                                                   String fileMd5,
                                                   Integer sliceNo,
                                                   Integer totalSliceNo) throws Exception {
        String filePath = fileService.uploadFileBySlices(slice,fileMd5,sliceNo,totalSliceNo);
        return new JsonResponse<>(filePath);
    }
//    6f5f74af2dbce8872d89573b937f6b22
//    M00/00/00/wKiIgGQebeyEIMoRAAAAACQPk0w5.1.mp4
    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception {
        String fileMD5 = fileService.getFileMD5(file);
        return new JsonResponse<>(fileMD5);
    }
}
