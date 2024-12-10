package pl.lodz.p.ias.io.raportowanie.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class GeneratedReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_surname")
    private String userSurname;
    @Column(name = "generation_time")
    private Timestamp generationTime;
    @Column(name = "data_table")
    private String table;

    public GeneratedReport(Long userId, String userName, String userSurname, String table) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.generationTime = new Timestamp(System.currentTimeMillis());
        this.table = table;
    }

    public byte[] toPdf() {
        String content = makeContent();

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            float margin = 50;
            float lineHeight = 15;

            float pageWidth = PDRectangle.A4.getWidth() - 2 * margin;
            float pageHeight = PDRectangle.A4.getHeight();
            float yPosition = pageHeight - margin;

            PDType1Font font = PDType1Font.COURIER;
            int fontSize = 11;

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);

            String[] rawLines = content.split("\n");

            for (String rawLine : rawLines) {
                List<String> wrappedLines = wrapText(rawLine, font, fontSize, pageWidth);

                for (String line : wrappedLines) {
                    if (yPosition - lineHeight < margin) {
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);

                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(font, fontSize);
                        contentStream.beginText();
                        yPosition = pageHeight - margin;
                        contentStream.newLineAtOffset(margin, yPosition);
                    }

                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -lineHeight);
                    yPosition -= lineHeight;
                }
            }
            contentStream.endText();
            contentStream.close();

            document.save(baos);

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toHtml() {
        return "<p>" + makeContent() + "</p>"; // TODO: implement
    }

    private String makeContent() {
        return userName + " " + userSurname + "\n" + table+ "\n";

    }

    private List<String> wrapText(String text, PDType1Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.isEmpty() ? word : line + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;
            if (textWidth > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                line.append(line.isEmpty() ? word : " " + word);
            }
        }

        if (!line.isEmpty()) {
            lines.add(line.toString());
        }

        return lines;
    }

}
