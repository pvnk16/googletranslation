package com.pdf.html;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.fit.pdfdom.PDFDomTree;

import com.pdf.util.OcrUtil;

public class PdfToHtml {
	
	public static void main(String...args) throws InvalidPasswordException, IOException, ParserConfigurationException {
		// load the PDF file using PDFBox
		PDDocument pdf = PDDocument.load(new java.io.File(OcrUtil.INVOICE_PATHS+"CHAMPION_1.pdf"));
		// create the DOM parser
		// parse the file and get the DOM Document
		Path path = Paths.get(OcrUtil.OUT_PATH);
		if (Files.notExists(path)) {
			Files.createDirectory(path);
		}
		new PDFDomTree().writeText(pdf,new PrintWriter(OcrUtil.OUT_PATH+"pdf2.html", "utf-8"));
		
		
	}

}
