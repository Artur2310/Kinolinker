package ru.kinolinker.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserIMDBUtil {

	private static Logger logger = LoggerFactory.getLogger(ParserIMDBUtil.class);

//Parse IMDB rating 
	public static Float parse(String url) {
		try {

			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String html = response.toString();
			
			if(!html.contains("ratingValue")) return null;
			
			html = html.substring(html.indexOf("ratingValue"), html.indexOf("ratingValue") + 40);

			Pattern p = Pattern.compile("[0-9].[0-9]");
			Matcher m = p.matcher(html);

			if (m.find()) {
				String imdbStr = html.substring(m.start(), m.end());

				return Float.parseFloat(imdbStr);

			}
			return null;
		} catch (MalformedURLException e) {
			logger.info(e.getMessage());
			return null;
		} catch (IOException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
}
