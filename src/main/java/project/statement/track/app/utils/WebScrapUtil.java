package project.statement.track.app.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import lib.base.backend.exception.data.BusinessException;

public class WebScrapUtil {
	
	public HtmlPage readHtml(String uri) throws BusinessException {
		
		try(WebClient client = new WebClient()) {
			
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
			
			URL url = new URL("http://www.example.com");
			Page pageOrigin = client.getPage(uri);
			
			StringWebResponse response = new StringWebResponse( pageOrigin.getWebResponse().getContentAsString(StandardCharsets.UTF_8), url);
			return HTMLParser.parseHtml(response, client.getCurrentWindow());
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			throw new BusinessException("Error reading file html");
		}
	}
	
	public HtmlPage readHtmlString(String textHtml) throws BusinessException {
		
		try(WebClient client = new WebClient()) {
			
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
			
			URL url = new URL("http://www.example.com");
			
			StringWebResponse response = new StringWebResponse(textHtml, url);
			return HTMLParser.parseHtml(response, client.getCurrentWindow());
			
		} catch (FailingHttpStatusCodeException | IOException e) {
			throw new BusinessException("Error reading file html");
		}
	}
	
	public HtmlPage readHtmlFile(String path) throws BusinessException {
		
		return readHtml("file:\\\\" + path);
	}
	
	public HtmlPage readHtmlText(String text) throws BusinessException {
		
		return readHtmlString(text);
	}
	
}
