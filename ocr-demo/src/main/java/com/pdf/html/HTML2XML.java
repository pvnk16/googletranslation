package com.pdf.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jdom2.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.pdf.html.content.PdfContent;
import com.pdf.util.OcrUtil;

public class HTML2XML {
	public static void main(String args[]) throws JDOMException, IOException {
		Document document = Jsoup.parse(new File(OcrUtil.OUT_PATH + "pdf.html"), null);
		try {
			Element bodyElement = document.body();
			int numberOfPages = bodyElement.children().size();
			System.out.println("invoice contains pages" + numberOfPages);
			List<PdfContent> contentList = new ArrayList<>();
			List<List<PdfContent>> pdfContentsList = new ArrayList<>();

			bodyElement.children().stream().forEach(child -> {
				/*
				 * Now iterate through the childs.
				 */
				child.children().stream().forEach(children -> {
					PdfContent content = new PdfContent();
					/*
					 * Get the child attribute and split them to get style attributes.
					 */
					String[] styles = children.attr("style").split(";");
					content.setValue(children.text());
					/*
					 * Find keys and fetch the values.
					 */
					for (String style : styles) {
						if (style.contains("top")) {
							content.setTop(HTML2XML.getValue(style.split(":")[1]));
							content.setRoundedTop((int)Math.round(content.getTop()));
						} else if (style.contains("left")) {
							content.setLeft(HTML2XML.getValue(style.split(":")[1]));
						} else if (style.contains("width")) {
							content.setWidth(HTML2XML.getValue(style.split(":")[1]));
						}
					}
					String topPoint = children.attr("style").split(";")[0].split(":")[1];
					if (topPoint.indexOf("pt") != -1) {
						topPoint = topPoint.substring(0, topPoint.indexOf("pt"));
					}
					contentList.add(content);
				});
				/*
				 * Now copy the page1 list.
				 */
				List<PdfContent> pageList = new ArrayList<>(contentList);
				pdfContentsList.add(pageList);
				contentList.clear();
			});
			/*
			 * Now we have all the content and top left widhts in list. group them to get
			 * each row.
			 */
			pdfContentsList.stream().forEach(pageList -> {
				System.out.println("Page list");
				Map<Integer, List<PdfContent>> rowGroup = pageList.stream()
													.collect(Collectors.groupingBy(PdfContent::getRoundedTop));

				/*
				 * Order them in ascending order.
				 */
				Map<Integer, List<PdfContent>> sortedMap = null;
				sortedMap = rowGroup.entrySet().stream()
											.sorted(Map.Entry.comparingByKey())
											.collect(Collectors
													.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
				/*
				 * Now put values in ascending order.
				 */
				sortedMap.entrySet().stream().forEach(e->{
					e.setValue(e.getValue().stream().sorted(Comparator.comparingDouble(PdfContent :: getLeft))
								.collect(Collectors.toList()));
				});
				/*
				 * Get the list and print it by key
				 */
				sortedMap.entrySet()
						.stream()
						.forEach(entry -> {

					String line = entry.getValue().stream().map(e -> e.getValue()).collect(Collectors.joining(" "));
					System.out.println(entry.getKey()+"   : "+ line);
				});

			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * Method for removing noise from values.
	 * 
	 * @param name
	 * @return returns the values.
	 */
	public static Double getValue(String name) {
		if (name != null) {
			if (name.indexOf("pt") != -1) {
				return Double.parseDouble(name.substring(0, name.indexOf("pt")));
			}
		}
		return null;
	}

}
