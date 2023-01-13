package com.abede.service;

import com.abede.model.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ReportService {
    public Response exportJasper() throws JRException {
        File file = new File("src/main/resources/List_item.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Item.listAll());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter(), dataSource);
        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);
        return Response.ok().build();
    }
    public Map<String, Object> jasperParameter(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("createBy", "Abede");
        return parameter;
    }
}