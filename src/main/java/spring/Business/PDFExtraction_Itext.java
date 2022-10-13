package spring.Business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.json.ParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFExtraction_Itext {
	public static void maeahc(String[] args) throws ParseException, FileNotFoundException, IOException {
		List<String> skill_list = new ArrayList<>();
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("/InHouseProject-ReadingFile_PDF/src/main/resources/application.properties"));
			PdfReader reader = new PdfReader("C:\\Users\\admin\\Downloads\\Resume updated 13 th.pdf");
			Pattern email_pattern = Pattern.compile(prop.getProperty("EMAIL_PATTERN"));
			Pattern mobile_pattern = Pattern.compile(prop.getProperty("MOBILE_PATTERN"));
			Pattern dob_pattern = Pattern.compile(prop.getProperty("DOB_PATTERN"));
			Pattern name_pattern = Pattern.compile(prop.getProperty("NAME_PATTERN"));
			// Get the number of pages in pdf.
			int pages = reader.getNumberOfPages();
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			// <-------------- Skills Reading ------------------->
			FileInputStream skillsReader = new FileInputStream(new File(
					"C:\\STS Workspace\\InHouseProject-ReadingFile_PDF\\src\\main\\java\\jsonfiles\\skills.json"));
			ObjectMapper skillMapper = new ObjectMapper();

			LinkedHashMap<String, Object> skillData = skillMapper.readValue(skillsReader,
					new TypeReference<LinkedHashMap<String, Object>>() {
					});

			ArrayList<String> skills = (ArrayList<String>) skillData.get("skills");

			// <-------------------------=========------------------------->

			// <--------------- Education Reading ------------------------>
			FileInputStream educationReader = new FileInputStream(new File(
					"C:\\STS Workspace\\InHouseProject-ReadingFile_PDF\\src\\main\\java\\jsonfiles\\education.json"));
			ObjectMapper educationMapper = new ObjectMapper();

			LinkedHashMap<String, Object> educationData = educationMapper.readValue(educationReader,
					new TypeReference<LinkedHashMap<String, Object>>() {
					});
			// System.out.println(educationData);
			// Set<String> keys = data.keySet();
			ArrayList<String> education = (ArrayList<String>) educationData.get("education");
			ArrayList<String> dontConsider = (ArrayList<String>) educationData.get("dontconsider");
			// System.out.println(education);

			// <-------------------------================------------------->

			// Iterate the pdf through pages.
			for (int i = 1; i <= pages; i++) {
				// Extract the page content using PdfTextExtractor.
				String pageContent = PdfTextExtractor.getTextFromPage(reader, i);
				pageContent = pageContent.toLowerCase();
				String[] str = pageContent.split("\n");
				
				for (String line : str) {
					line = line.trim();
					// System.out.println(line);
					Matcher email_matcher = email_pattern.matcher(line);
					Matcher mobile_matcher = mobile_pattern.matcher(line);
					Matcher dob_matcher = dob_pattern.matcher(line);
					if (email_matcher.find()) {
						String email = email_matcher.group(0);

						Matcher matcher = name_pattern.matcher(email);
						if (matcher.find()) {
							String name = matcher.group(0);
							// System.out.println("Name:"+name);
						}

						// System.out.println("Email:" + email );
					}

					if (mobile_matcher.find()) {
						String mobile = mobile_matcher.group(0);
						// System.out.println("Mobile:" + mobile);
					}

					if (dob_matcher.find()) {
						String dob = dob_matcher.group(1);
						// System.out.println("DOB:" + dob);
					}
					String skill = "";
					String[] words = line.split(" ");
					for (String word : words) {
						if (skills.contains(word)) {
							// System.out.println(words);
							skill = word;
							skills.remove(word);
							skill_list.add(skill);
						}
					}
					boolean skip = false;
					for(String education_dontconsider:dontConsider) {
						Pattern education_pattren_dontmatch = Pattern.compile(".*" + education_dontconsider + ".*");
						Matcher education_matcher_dontmatch = education_pattren_dontmatch.matcher(line);
						if(education_matcher_dontmatch.find()) {
							skip = true;
						}
					}
					if(skip) {
						continue;
					}
					
					for (String educations : education) {
						Pattern education_patttren = Pattern.compile(".*" + educations + ".*");
						Matcher education_matcher = education_patttren.matcher(line);
						if (education_matcher.find()) {
							String education_details = education_matcher.group(0);
							education.remove(education_details);
							System.out.println(education_details);
//							String education_matched = education_details.toString();
//							System.out.println("Education Details :" + education_matched);	
						}   
					}
				}						
			}

			String skill_matched = skill_list.toString();
			// System.out.println("Skills:" + skill_matched);	
			reader.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Ram final resume-converted
	// Raghava CV
	// job resume ram-converted
	// Aishwarya nawale resume
	// ramesh resume final
	// Resume updated 13 th
}
