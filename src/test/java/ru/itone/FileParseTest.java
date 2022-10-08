package ru.itone;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import ru.itone.Model.Order;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class FileParseTest {
    ClassLoader cl = FileParseTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/example.zip"));
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("example.zip"))){
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                switch (entry.getName()) {
                    case "example_csv.csv" :
                        try (InputStream inputStream = zf.getInputStream(entry);
                             CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                            List<String[]> content = reader.readAll();
                            String[] row = content.get(0);
                            String searchWords = row[7];
                            assertThat(searchWords).isEqualTo("Variable_category");
                        }
                        break;
                    case "example_xlsx.xlsx":
                        try (InputStream inputStream = zf.getInputStream(entry)) {
                            XLS xls = new XLS(inputStream);
                            assertThat(
                                    xls.excel.getSheetAt(0)
                                            .getRow(5)
                                            .getCell(4)
                                            .getStringCellValue()
                            ).isEqualTo("United States");
                        }
                        break;
                    case "example_pdf.pdf":
                        try (InputStream inputStream = zf.getInputStream(entry)) {
                            PDF pdf = new PDF(inputStream);
                            assertThat(pdf.author).isEqualTo("Alex");
                        }
                        break;
                }
        }
    }
}
    @Test
    void jsonTest() throws Exception {
        File file = new File("src/test/resources/order.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(file, Order.class);

        assertThat(order.FirstName).isEqualTo("Timur");
        assertThat(order.LastName).isEqualTo("Maksyutov");
        assertThat(order.Email).isEqualTo("tmaksyutov@example.com");
        assertThat(order.contents.get(0).productID).isEqualTo(99);
    }
}
