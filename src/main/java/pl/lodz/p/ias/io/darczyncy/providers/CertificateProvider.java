package pl.lodz.p.ias.io.darczyncy.providers;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import org.jetbrains.annotations.NotNull;
import pl.lodz.p.ias.io.darczyncy.model.FinancialDonation;
import pl.lodz.p.ias.io.darczyncy.model.ItemDonation;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CertificateProvider {

    public byte[] generateItemCertificate(Account account, ItemDonation donation) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {

            // Tworzenie PDF
            stream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(stream);
            //PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Ustawienie strony na horyzontalną A4
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());

            Document document = new Document(pdfDoc);

            // Dodanie tła (np. obrazek w tle)
            String backgroundPath = "src/main/resources/static/pdf/images/certificate_back.png"; // Ścieżka do obrazka tła
            ImageData imageData = ImageDataFactory.create(backgroundPath);
            Image background = new Image(imageData);
            background.setFixedPosition(0, 0);
            background.setWidth(pdfDoc.getDefaultPageSize().getWidth());
            background.setHeight(pdfDoc.getDefaultPageSize().getHeight());
            document.add(background);

            // Dodanie treści dokumentu
            String title = "PODZIĘKOWANIA";
            String message = getString(account, donation);

            // Ustawienia kolorów i stylów tekstu
            Color titleColor = new DeviceRgb(0, 51, 102);
            Color textColor = new DeviceRgb(0, 0, 0);

            //Czcionka
            PdfFont titleFont = PdfFontFactory.createFont("src/main/resources/static/pdf/fonts/Roboto-Bold.ttf", PdfEncodings.IDENTITY_H);
            PdfFont messageFont = PdfFontFactory.createFont("src/main/resources/static/pdf/fonts/Roboto-Regular.ttf", PdfEncodings.IDENTITY_H);

            // Tytuł
            Paragraph titleParagraph = new Paragraph(title)
                    .setFontSize(24)
                    .simulateBold()
                    .setFontColor(titleColor)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setPaddingTop(200);
            document.add(titleParagraph);

            // Treść wiadomości
            Paragraph messageParagraph = new Paragraph(message)
                    .setFontSize(16)
                    .setFontColor(textColor)
                    .setFont(messageFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(messageParagraph);

            // Zamknięcie dokumentu
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }

    private static @NotNull String getString(Account account, ItemDonation donation) {
        String goal = donation.getNeed().getDescription();
        String date = donation.getDonationDate().toString();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String itemDescription = donation.getResourceName();
        String message = ("Dziękujemy za przekazanie przedmiotu \"%s\" na cel \"%s\".\nDarowizna została przekazana w dniu %s \n " +
                "przez %s %s.\n")
                .formatted(itemDescription, goal, date, firstName, lastName);
        return message;
    }

    public byte[] generateFinancialCertificate(Account account, FinancialDonation donation) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {

            // Tworzenie PDF
            PdfWriter writer = new PdfWriter(stream);
            //PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Ustawienie strony na horyzontalną A4
            pdfDoc.setDefaultPageSize(PageSize.A4.rotate());

            Document document = new Document(pdfDoc);

            // Dodanie tła (np. obrazek w tle)
            String backgroundPath = "src/main/resources/static/pdf/images/certificate_back.png"; // Ścieżka do obrazka tła
            ImageData imageData = ImageDataFactory.create(backgroundPath);
            Image background = new Image(imageData);
            background.setFixedPosition(0, 0);
            background.setWidth(pdfDoc.getDefaultPageSize().getWidth());
            background.setHeight(pdfDoc.getDefaultPageSize().getHeight());
            document.add(background);

            // Dodanie treści dokumentu
            String title = "PODZIĘKOWANIA";
            String goal = donation.getNeed().getDescription();
            String date = donation.getDonationDate().toString();
            String firstName = account.getFirstName();
            String lastName = account.getLastName();
            double ammount = donation.getAmount();
            String message = ("Dziękujemy za wpłacenie darowizny o wartości %s na cel \"%s\".\nDarowizna została wpłacona w dniu %s \n " +
                    "przez %s %s.\n")
                    .formatted(Double.toString(ammount), goal, date, firstName, lastName);

            // Ustawienia kolorów i stylów tekstu
            Color titleColor = new DeviceRgb(0, 51, 102);
            Color textColor = new DeviceRgb(0, 0, 0);

            //Czcionka
            PdfFont titleFont = PdfFontFactory.createFont("src/main/resources/static/pdf/fonts/Roboto-Bold.ttf", PdfEncodings.IDENTITY_H);
            PdfFont messageFont = PdfFontFactory.createFont("src/main/resources/static/pdf/fonts/Roboto-Regular.ttf", PdfEncodings.IDENTITY_H);

            // Tytuł
            Paragraph titleParagraph = new Paragraph(title)
                    .setFontSize(24)
                    .simulateBold()
                    .setFontColor(titleColor)
                    .setFont(titleFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setPaddingTop(200);
            document.add(titleParagraph);

            // Treść wiadomości
            Paragraph messageParagraph = new Paragraph(message)
                    .setFontSize(16)
                    .setFontColor(textColor)
                    .setFont(messageFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(messageParagraph);

            // Zamknięcie dokumentu
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toByteArray();
    }



}
