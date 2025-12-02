package com.switflow.swiftFlow.Controller;

import com.switflow.swiftFlow.Request.StatusRequest;
import com.switflow.swiftFlow.Response.StatusResponse;
import com.switflow.swiftFlow.Service.CloudinaryService;
import com.switflow.swiftFlow.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

   

   
    /**
     * Create a status update for an order.
     * This endpoint supports both direct URL attachments and file uploads.
     * If a file is provided, it will be uploaded to Cloudinary.
     * If no file is provided, the attachmentUrl from the request will be used directly.
     * 
     * @param orderId The ID of the order
     * @param statusRequest The status update details
     * @param attachment The file to upload (optional)
     * @return The created status update
     */
    @PostMapping("/create/{orderId}")
    public ResponseEntity<StatusResponse> createStatus(
            @PathVariable long orderId, 
            @RequestPart("status") StatusRequest statusRequest,
            @RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {
        StatusResponse response = statusService.createStatus(statusRequest, orderId, attachment);
        return ResponseEntity.ok(response);
    }

    // @PostMapping("/create-with-attachment/{orderId}")
    // public ResponseEntity<StatusResponse> createStatusWithAttachment(
    //         @PathVariable long orderId,
    //         @RequestPart("status") StatusRequest statusRequest,
    //         @RequestPart(value = "attachment", required = false) MultipartFile attachment) throws IOException {
    //     StatusResponse response = statusService.createStatusWithAttachment(statusRequest, orderId, attachment);
    //     return ResponseEntity.ok(response);
    // }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<StatusResponse>> getStatusesByOrderId(@PathVariable long orderId) {
        List<StatusResponse> responses = statusService.getStatusesByOrderId(orderId);
        return ResponseEntity.ok(responses);
    }
}