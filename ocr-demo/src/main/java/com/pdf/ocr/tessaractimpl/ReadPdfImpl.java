package com.pdf.ocr.tessaractimpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.pdf.ocr.tessaract.ReadPdf;
import com.pdf.util.OcrUtil;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.PdfUtilities;

/**
 * The class provides the behavior published by {@link ReadPdf} interface.
 * 
 * @author pavan
 *
 */
public class ReadPdfImpl implements ReadPdf {

	private ITesseract tesseract;
	
	public ReadPdfImpl() {
		tesseract= new Tesseract();
		
		//Set the tessdata path
		tesseract.setDatapath("D:\\prgrams\\ocr\\ApInvoiceArtifacts\\tessdata");
		tesseract.setLanguage("tel");
	}

	/*
	 * The method is for reading the pdf and converting into tiff image.
	 * (non-Javadoc)
	 * 
	 * @see com.pavan.ocr.tessaract.ReadPdf#readPdf(java.lang.String)
	 */
	public File convertPdfToTiff(String pdfPath) throws IOException {
		File pdf = new File(pdfPath);
		File tiff = null;
		tiff = PdfUtilities.convertPdf2Tiff(pdf);
		return tiff;
	}

	/*
	 * The method is for extracting the content from tiff image. (non-Javadoc)
	 * 
	 * @see com.pavan.ocr.tessaract.ReadPdf#extractContent(java.lang.String)
	 */
	public void extractContent(File tiffFile) throws TesseractException {
		Path path = Paths.get(OcrUtil.INTERMEDIATE_PATHS + "/" + tiffFile.getName() + ".txt");
		BufferedWriter writer = null;
		/*
		 * Do ocr operation.
		 */
		String result = tesseract.doOCR(tiffFile);
		try {
			writer = Files.newBufferedWriter(path);
			writer.write(result);
			writer.flush();
		} catch (Exception e) {
			
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

	}

}
