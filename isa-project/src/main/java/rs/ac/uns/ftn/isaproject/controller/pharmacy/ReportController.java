package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import rs.ac.uns.ftn.isaproject.service.pharmacy.ReportService;

@RestController
@RequestMapping(value = "api/report")
public class ReportController {

	private ReportService reportService;
	
	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	@GetMapping("/{id}/appointment")
	public ResponseEntity<Void> reportAppointment(HttpServletResponse response, @PathVariable int id) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportService.reportAppointment(id));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/appointment_report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		exporter.exportReport();
		return null;
		
	}
	
	@GetMapping("/{id}/drug")
	public ResponseEntity<Void> reportDrug(HttpServletResponse response, @PathVariable int id) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportService.reportDrug(id));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/drug_report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		exporter.exportReport();
		return null;
		
	}
}
