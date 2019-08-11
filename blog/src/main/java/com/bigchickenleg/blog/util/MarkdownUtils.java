package com.bigchickenleg.blog.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtils {

	/*
	 * Markdown to html
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtml(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);		
	}
	
	/*
	 * extension, header id, table generation
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtmlExtensions(String markdown) {
		//h header generated id
		Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
		//convert table html
		List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder()
						.extensions(tableExtension)
						.build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtensions)
								.extensions(tableExtension)
								.attributeProviderFactory(new AttributeProviderFactory() {
									public AttributeProvider create(AttributeProviderContext context) {
										return new CustomAttributeProvider();
									}
								}).build();				
		return renderer.render(document);	
	}
	
	/*
	 * change the attribute of tag
	 */
	static class CustomAttributeProvider implements AttributeProvider{

		@Override
		public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
			//change the <a> tag target attribute to _blank
			if(node instanceof Link) {
				attributes.put("target", "_blank");
			}
			if(node instanceof TableBlock) {
				attributes.put("class"," ui celled table");
			}
		}

	}
	
	
	
	
	
	
	
	
	

	
}
