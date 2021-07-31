package mcc.springbootboilerplate.controller;

import mcc.springbootboilerplate.repository.SampleSqlRepository;
import mcc.springbootboilerplate.scheduler.ScheduledTasks;
import mcc.springbootboilerplate.utils.ExcelUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    SampleSqlRepository sampleSqlRepository;

    @RequestMapping(value = "/api/sample/excel", method = RequestMethod.GET)
    public ResponseEntity<Resource> toExcel() throws IOException {
        logger.info("toExcel - Start");
        List<Map<String, Object>> allDummy = sampleSqlRepository.selectAllDummy();
        if (allDummy!= null && !allDummy.isEmpty()) {
            Map<String, Object> objectMap = allDummy.get(0);
            SXSSFWorkbook workbook = ExcelUtils.toExcel("dummy",
                    objectMap.keySet().toArray(String[]::new), allDummy);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                workbook.write(out);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=excel.xlsx");
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
            } catch (IOException e) {
                throw e;
            } finally {
                workbook.dispose();
                logger.info("toExcel - End");
            }

        } else {
            throw new HttpClientErrorException(HttpStatus.NO_CONTENT);
        }
    }
}
