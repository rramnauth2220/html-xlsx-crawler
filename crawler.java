import com.jaunt.*;
import java.io.*;
import com.jaunt.component.*;
import com.jaunt.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class crawler {
	private static Workbook workbook;
	private static int rowNum;
	
	//Excel columns
	private final static int TITLE_NUM_COLUMN = 0;
	private final static int TITLE_SUBJECT = 1;
    private final static int TITLE_PARTS = 2;
    private final static int TITLE_REVISION_DATE = 3;
    private final static int TITLE_CONTAINS = 4;
    private final static int TITLE_DATE_PUBLISHED = 5;  
    private final static int SUBTITLE_NAME_COLUMN = 6;
    private final static int CHAPTER_NUM_COLUMN = 7;
    private final static int PART_NUM_COLUMN = 8;
    private final static int PART_NAME_COLUMN = 9;
    private final static int SUBPART_COLUMN = 10;
    private final static int SECTION_NUM_COLUMN = 11;
    private final static int SECTION_NAME_COLUMN = 12;
    private final static int SUBREQ_PARA_COLUMN = 13;
    private final static int CITA_COLUMN = 14;
	
	public static void main (String[] args){
		crawl("https://govt.westlaw.com/nycrr/");
	}
	
	public static void crawl(String item){
		if (!hasChild(item)){
			citation(item);
		}
		else{
			try{
				UserAgent userAgent = new UserAgent();
				userAgent.visit(item);
				
				Elements links = userAgent.doc.findEach("<a name>");
				System.out.println("----------------------------------");
				System.out.println("Found " + links.size() + " tables:");
				System.out.println("----------------------------------");
				
				String link, title_num, subject;
				for (Element anchor : links){
					try{
						link = anchor.getAt("href");
						System.out.println(link);
						title_num = anchor.getText();
						System.out.println(title_num);
						
						crawl(link);
					}
					catch (NotFound e){
						System.out.println("");
					}
				}
		    }
		    catch(ResponseException e){      //in case of HTTP/Connection error, catch ResponseExeption
		    	System.err.println(e);         //printing exception shows HTTP error information or connection error
		    }
		}
	}
	
	public static boolean hasChild(String link){
		try {
			UserAgent child = new UserAgent();
			child.visit(link);
			Elements children = child.doc.findEach("<a name>");
			return children.size() > 0;
		}
		catch (ResponseException e){
			System.err.println(e);
		}
		return false;
	}
	
	public static void citation(String link){
		System.out.println("REQUIREMENT LEVEL REACHED");
	}
}
