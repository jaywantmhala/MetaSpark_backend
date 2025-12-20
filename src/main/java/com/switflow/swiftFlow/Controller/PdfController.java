package com.switflow.swiftFlow.Controller;

import com.switflow.swiftFlow.Service.PdfService;
import com.switflow.swiftFlow.pdf.PdfRow;
import com.switflow.swiftFlow.Response.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/order/{orderId}/rows")
    @PreAuthorize("hasAnyRole('ADMIN','DESIGN')")
    public ResponseEntity<List<PdfRow>> getPdfRows(@PathVariable long orderId) throws IOException {
        List<PdfRow> rows = pdfService.analyzePdfRows(orderId);
        return ResponseEntity.ok(rows);
    }

    public static class RowSelectionRequest {
        private List<String> selectedRowIds;

        public List<String> getSelectedRowIds() {
            return selectedRowIds;
        }

        public void setSelectedRowIds(List<String> selectedRowIds) {
            this.selectedRowIds = selectedRowIds;
        }
    }

    @PostMapping("/order/{orderId}/filter")
    @PreAuthorize("hasAnyRole('ADMIN','DESIGN')")
    public ResponseEntity<StatusResponse> createFilteredPdf(
            @PathVariable long orderId,
            @RequestBody RowSelectionRequest request
    ) throws IOException {
        StatusResponse response = pdfService.generateFilteredPdf(orderId, request.getSelectedRowIds());
        return ResponseEntity.ok(response);
    }
}
