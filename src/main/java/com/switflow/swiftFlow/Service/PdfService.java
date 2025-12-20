package com.switflow.swiftFlow.Service;

import com.switflow.swiftFlow.Entity.Orders;
import com.switflow.swiftFlow.Entity.Status;
import com.switflow.swiftFlow.Repo.OrderRepository;
import com.switflow.swiftFlow.Repo.StatusRepository;
import com.switflow.swiftFlow.Response.StatusResponse;
import com.switflow.swiftFlow.pdf.PdfRow;
import com.switflow.swiftFlow.pdf.PdfRowExtractor;
import com.switflow.swiftFlow.utility.Department;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PdfService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private StatusService statusService;

    public List<PdfRow> analyzePdfRows(long orderId) throws IOException {
        String pdfUrl = findDesignPdfUrl(orderId);
        if (pdfUrl == null) {
            return new ArrayList<>();
        }

        try (InputStream in = new URL(pdfUrl).openStream(); PDDocument document = PDDocument.load(in)) {
            PdfRowExtractor extractor = new PdfRowExtractor();
            extractor.setStartPage(1);
            extractor.setEndPage(document.getNumberOfPages());
            // We only need PdfRowExtractor's collected row metadata; discard text output
            extractor.writeText(document, new StringWriter());
            return extractor.getRows();
        }
    }

    private String findDesignPdfUrl(long orderId) {
        List<Status> statuses = statusRepository.findByOrdersOrderId(orderId);
        return statuses.stream()
                .filter(s -> s.getAttachmentUrl() != null && s.getAttachmentUrl().toLowerCase().endsWith(".pdf"))
                .sorted(Comparator.comparing(Status::getId).reversed())
                .map(Status::getAttachmentUrl)
                .findFirst()
                .orElse(null);
    }

    public StatusResponse generateFilteredPdf(long orderId, List<String> selectedRowIds) throws IOException {
        String pdfUrl = findDesignPdfUrl(orderId);
        if (pdfUrl == null) {
            throw new IllegalStateException("No source PDF found for order " + orderId);
        }

        List<PdfRow> allRows = analyzePdfRows(orderId);
        Map<String, PdfRow> rowMap = allRows.stream()
                .collect(Collectors.toMap(PdfRow::getRowId, r -> r));

        List<PdfRow> selectedRows = selectedRowIds.stream()
                .map(rowMap::get)
                .filter(r -> r != null)
                .sorted(Comparator.comparing(PdfRow::getPageNumber).thenComparing(PdfRow::getYPosition))
                .collect(Collectors.toList());

        if (selectedRows.isEmpty()) {
            throw new IllegalArgumentException("No valid rows selected");
        }

        byte[] filteredPdfBytes = createSimplePdfFromRows(selectedRows);
        String filteredUrl = cloudinaryService.uploadBytes(filteredPdfBytes);

        // Create a status entry moving order to PRODUCTION with the filtered PDF
        return statusService.createFilteredPdfStatus(orderId, filteredUrl, Department.PRODUCTION);
    }

    private byte[] createSimplePdfFromRows(List<PdfRow> rows) throws IOException {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                content.setFont(PDType1Font.HELVETICA, 10);
                float leading = 14.0f;
                float margin = 40;
                PDRectangle mediaBox = page.getMediaBox();
                float y = mediaBox.getUpperRightY() - margin;

                content.beginText();
                content.newLineAtOffset(margin, y);

                for (PdfRow row : rows) {
                    content.showText(row.getText());
                    content.newLineAtOffset(0, -leading);
                }

                content.endText();
            }

            doc.save(out);
            return out.toByteArray();
        }
    }
}
