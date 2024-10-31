package com.kakao.saramaracommunity.bucket.controller;

import com.kakao.saramaracommunity.bucket.dto.business.response.BucketUploadResponse;
import com.kakao.saramaracommunity.bucket.controller.port.BucketService;
import com.kakao.saramaracommunity.common.response.ApiResponse;
import com.kakao.saramaracommunity.util.ValidFileList;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * 클라이언트의 요청을 받아 AWS S3 버킷에 이미지 업로드를 수행할 컨트롤러 클래스입니다.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bucket")
public class BucketController {

    private final BucketService bucketService;

    @Operation(summary = "이미지 파일 목록 업로드")
    @PostMapping
    public ResponseEntity<ApiResponse<BucketUploadResponse>> uploadImages(
            @RequestPart(required = false)
            @ValidFileList
            List<MultipartFile> request
    ) {
        BucketUploadResponse response = bucketService.uploadImages(request);
        return ResponseEntity.ok().body(
                ApiResponse.successResponse(
                        OK,
                        "성공적으로 AWS S3 버킷에 이미지 업로드를 완료하였습니다.",
                        response
                )
        );
    }

}
