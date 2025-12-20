package com.switflow.swiftFlow.pdf;

public class PdfRow {
    private String rowId;
    private int pageNumber;
    private float yPosition;
    private String text;

    public PdfRow(String rowId, int pageNumber, float yPosition, String text) {
        this.rowId = rowId;
        this.pageNumber = pageNumber;
        this.yPosition = yPosition;
        this.text = text;
    }

    public String getRowId() {
        return rowId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public float getYPosition() {
        return yPosition;
    }

    public String getText() {
        return text;
    }
}
