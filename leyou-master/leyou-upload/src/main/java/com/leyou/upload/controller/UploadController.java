package com.leyou.upload.controller;

import com.leyou.response.CodeMsg;
import com.leyou.response.Result;
import com.leyou.upload.service.serviceimpl.UploadServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



/**
 * @Author: 98050
 * Time: 2018-08-09 14:36
 * Feature:
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private UploadServiceImpl uploadServiceImpl;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url= this.uploadServiceImpl.upload(file);
        if(StringUtils.isBlank(url)){
            //url为空，证明上传失败
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(url);
    }

    @RequestMapping(value="/jx/memo",method = RequestMethod.POST)
    public Result<String> jsMemo(Long orderId, String memo) {
        if(orderId == null) {
            return Result.errorMsg("orderId");
        }
        if(StringUtils.isEmpty(memo)) {
            return Result.errorMsg("orderId");
        }
        try{
            uploadServiceImpl.jxMemo(orderId,memo);
            return Result.success("备注成功");
        } catch (IllegalArgumentException | IllegalStateException e) {
            logger.error("错误：{}",e);
            return Result.errorMsg(e.getMessage());
        }
    }
}
