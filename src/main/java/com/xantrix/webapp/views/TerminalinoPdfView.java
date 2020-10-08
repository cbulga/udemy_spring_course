package com.xantrix.webapp.views;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.xantrix.webapp.domain.Trasmissioni;
import com.xantrix.webapp.utils.TerminalinoResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TerminalinoPdfView extends MyAbstractPdfView {

	private static final SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	private String fileName;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String titolo = "Elenco Rilevazioni Terminalino";

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		// Impostazione del nome del file
		List<Trasmissioni> trasmissioni = (List<Trasmissioni>) model.get("Trasmissioni");
		TerminalinoResult result = (TerminalinoResult)model.get("result");

		PdfPTable table = createTable(4);

		// impostazione del colore e tipo di font
		Font font = FontFactory.getFont(FontFactory.TIMES);
		font.setColor(Color.WHITE);

		// impostazioni dell'intestazione
		// result table
		PdfPTable headerTable = createTable(4);
		
		// header
		document.add(new Paragraph("Elenco Rilevazioni Terminalino - Documento creato il " + LocalDate.now()));

		PdfPCell headerCell = createCell();
		addCell(headerCell, headerTable, "Codice Punto Vendita", font);
		addCell(headerCell, headerTable, "Numero Terminalino", font);
		addCell(headerCell, headerTable, "Totale righe elaborate", font);
		addCell(headerCell, headerTable, "Totale righe in errore", font);
		headerTable.addCell(result.getCodicePuntoVendita());
		headerTable.addCell(result.getNumeroTerminalino());
		headerTable.addCell("" + result.getTotalRowsCount());
		headerTable.addCell("" + result.getTotalRowsInErrorCount());
		document.add(headerTable);
		
		
		// Intestazione del documento
		document.add(new Paragraph("Righe importate correttamente"));

		PdfPCell cell = createCell();
		cell.setPhrase(new Phrase("Data", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Barcode", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Descrizione", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Quantita'", font));
		table.addCell(cell);

		for (Trasmissioni trasmissione : trasmissioni) {
			table.addCell(trasmissione.getData() != null ? sdf_ddMMyyyy.format(trasmissione.getData()) : "");
			table.addCell(trasmissione.getBarCode());
			table.addCell(trasmissione.getDescrizione());
			table.addCell(trasmissione.getQta().toString());
		}

		document.addTitle(titolo);
		document.setPageCount(0);
		document.add(table);

		if (result.hasErrors()) {
			// result table
			PdfPTable resultTable = createTable(1);
			
			// header
			resultTable.addCell(createCell(1, "Errori occorsi", font));

			result.getErrors().stream().forEach(e -> {
				resultTable.addCell(e);
			});
			
			document.add(resultTable);
		}
	}

	private void addCell(PdfPCell cell, PdfPTable table, String phrase, Font font) {
		cell.setPhrase(new Phrase(phrase, font));
		table.addCell(cell);
	}
	
	private PdfPTable createTable(int columnsCount) {
		PdfPTable table = new PdfPTable(columnsCount);
		table.setWidthPercentage(100.0f);
		table.setSpacingBefore(10);
		return table;
	}

	private PdfPCell createCell() {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		return cell;
	}

	private PdfPCell createCell(int colSpan, String phrase, Font font) {
		PdfPCell cell = createCell();
		cell.setColspan(colSpan);
		cell.setPhrase(new Phrase(phrase, font));
		return cell;
	}
}
