package com.pdf.ocr.tessaract;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.TesseractException;


/**
 * The interface provides the
 * 
 * @author pavan
 *
 */
public interface ReadPdf {

	/**
	 * Method for reading the pdf.
	 * 
	 * @param pdfPath
	 * @return 
	 * @throws IOException 
	 */
	public File convertPdfToTiff(String pdfPath) throws IOException;

	/**
	 * Method for reading the tiff image to text file.
	 * 
	 * @param tiffPath
	 */

	public void extractContent(File tiffFile) throws TesseractException;

}
