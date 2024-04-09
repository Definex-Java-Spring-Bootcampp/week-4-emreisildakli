package com.patika.kredinbizdeservice.client;

import com.patika.kredinbizdeservice.client.dto.request.GarantiApplicationRequest;
import com.patika.kredinbizdeservice.client.dto.response.ApplicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "garanti-service", url = "localhost:8081")
public interface GarantiServiceClient {
    @PostMapping("api/garanti/v1/applications")
    ApplicationResponse createApplication(@RequestBody GarantiApplicationRequest request);

    @GetMapping("api/garanti/v1/applications/{userId}")
    List<ApplicationResponse> getApplicationsByUserId(@PathVariable Long userId);
}
