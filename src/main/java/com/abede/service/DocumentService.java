package com.abede.service;

import com.abede.model.FileMultipartRequest;
import com.abede.model.Item;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@ApplicationScoped
public class DocumentService {

    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Transactional
    public void importItem(FileMultipartRequest request) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);

        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        sheet.removeRow(sheet.getRow(0));
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            Item item = new Item();

            item.setName(formatter.formatCellValue(row.getCell(0)));
            item.setType(formatter.formatCellValue(row.getCell(1)));
            item.setCount((int) row.getCell(2).getNumericCellValue());
            item.setPrice((row.getCell(3).getNumericCellValue()));
            item.setDescription(row.getCell(4).getStringCellValue());

            item.persist();
            logger.info("Post item -->" + item.getId());
        }
    }

}



