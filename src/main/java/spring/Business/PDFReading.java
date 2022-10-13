package spring.Business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFReading {
	
		// Raghava CV
		// job resume ram-converted
		//Aishwarya_nawale_resume
		// ramesh resume final
		// Resume updated 13 th
	//Venkata Harikrishna_ 2022 (3)-1
	//U.Ramanjaneyulu_Resume

	public List<String> readPDF() {	
		List<String> content = new ArrayList<>();	
		try {		
			PdfReader reader = new PdfReader("C:\\Users\\admin\\Downloads\\Raghava CV.pdf");		
			int pages = reader.getNumberOfPages();
			for (int i = 1; i <= pages; i++) {
				String pageContent = PdfTextExtractor.getTextFromPage(reader, i);
				pageContent = pageContent.toLowerCase();
				String[] content_array = pageContent.split("\n");
				content.addAll(Arrays.stream(content_array).toList());
			}
		}catch (Exception e) {
			
		}
		return content;
	}
	
	public JSONObject fieldExtraction(Object[] content_array) {
		JSONObject jsonFormat_details = new JSONObject();
		try {		
			int filecontent=content_array.length;
			//System.out.println("file content is "+filecontent);
			Properties prop = new Properties();
			prop.load(new FileReader("C:\\STS Workspace\\InHouseProject-ReadingFile_PDF\\src\\main\\resources\\application.properties"));
			
			FileInputStream educationReader = new FileInputStream(new File(
					"C:\\STS Workspace\\InHouseProject-ReadingFile_PDF\\src\\main\\java\\jsonfiles\\education.json"));
			FileInputStream skillsReader = new FileInputStream(new File(
					"C:\\STS Workspace\\InHouseProject-ReadingFile_PDF\\src\\main\\java\\jsonfiles\\skills.json"));
				
			Pattern email_pattern = Pattern.compile(prop.getProperty("EMAIL_PATTERN"));
			Pattern mobile_pattern = Pattern.compile(prop.getProperty("MOBILE_PATTERN"));
			Pattern dob_pattern = Pattern.compile(prop.getProperty("DOB_PATTERN"));
			Pattern name_pattern = Pattern.compile(prop.getProperty("NAME_PATTERN"));
						
			ObjectMapper educationMapper = new ObjectMapper();
			ObjectMapper skillMapper = new ObjectMapper();
			
			LinkedHashMap<String, Object> skillData = skillMapper.readValue(skillsReader,
					new TypeReference<LinkedHashMap<String, Object>>() {
					});
			LinkedHashMap<String, Object> educationData = educationMapper.readValue(educationReader,
					new TypeReference<LinkedHashMap<String, Object>>() {
					});
			
			ArrayList<String> skills = (ArrayList<String>) skillData.get("skills");
			ArrayList<String> education = (ArrayList<String>) educationData.get("education");
			ArrayList<String> dontConsider_educationData = (ArrayList<String>) educationData.get("exclude");
			
			List<String> skill_list = new ArrayList<>();
			List<String> education_list=new ArrayList<>();
			Set<String> education_set = new LinkedHashSet<>();
			
			List<String> education_list1=new ArrayList<>();
			Set<String> education_set1 = new LinkedHashSet<>();
			
			for(Object textData: content_array) {
				String line = (String)textData;
				line = line.trim();
				Matcher email_matcher = email_pattern.matcher(line);
				Matcher mobile_matcher = mobile_pattern.matcher(line);
				Matcher dob_matcher = dob_pattern.matcher(line);
				
				if (email_matcher.find()) {
					String email = email_matcher.group(0);
					jsonFormat_details.put("EMAIL", email.toUpperCase());
					Matcher matcher = name_pattern.matcher(email);
					if (matcher.find()) {
						String name = matcher.group(0);
						//System.out.println("Name:"+name);
						if(!jsonFormat_details.containsKey("name"))
						{
							jsonFormat_details.put("NAME", name.toUpperCase());
						}
						
					}
					//System.out.println("Email:" + email );
				}
				if (mobile_matcher.find()) {
					String mobile = mobile_matcher.group(0);
					//System.out.println("Mobile:" + mobile);
					if(!jsonFormat_details.containsKey("mobile"))
					{
						jsonFormat_details.put("MOBILE", mobile.toUpperCase());
					}
				}
				if (dob_matcher.find()) {
					String dob = dob_matcher.group(1);
					//System.out.println("DOB:" + dob);
					if(!jsonFormat_details.containsKey("dob"))
					{
						jsonFormat_details.put("DOB:", dob.toUpperCase());
					}
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
				if(!jsonFormat_details.containsKey("skills"))
				{
					List<String> skill_list_upper = skill_list.stream().map(String::toUpperCase).collect(Collectors.toList());
					jsonFormat_details.put("SKILLS", skill_list_upper);
				}
				boolean skip = false;
				for(String education_dontconsider:dontConsider_educationData) {
					Pattern education_pattren_dontmatch = Pattern.compile(".*" + education_dontconsider + ".*");
					Matcher education_matcher_dontmatch = education_pattren_dontmatch.matcher(line);
					if(education_matcher_dontmatch.find()) {
						skip = true;
					}
				}
				if(skip) {
					continue;
				}
				for (String educations : education) 
				{
					Pattern education_patttren = Pattern.compile(".*"+educations+".*");
					Matcher education_matcher = education_patttren.matcher(line);
					if (education_matcher.find())
					{
						String education_details = education_matcher.group(0);
						education_set.add(education_details);
					}
					
					
					Pattern education_patttren1 = Pattern.compile(educations);
					Matcher education_matcher1 = education_patttren.matcher(line);
					if (education_matcher1.find())
					{
						education_set1.add(educations);
					}
					
				}	
			}
			//System.out.println(skill_list);
			//System.out.println(education_list);
			education_list.addAll(education_set);
			List<String> education_list_upper = education_list.stream().map(String::toUpperCase).collect(Collectors.toList());
			if(!jsonFormat_details.containsKey("EducationDetails"))
			{
				jsonFormat_details.put("EDUCATIONDETAILS", education_list_upper);
			}
			
			
			education_list1.addAll(education_set1);
			List<String> education_list_upper1 = education_list1.stream().map(String::toUpperCase).collect(Collectors.toList());
			if(!jsonFormat_details.containsKey("Education"))
			{
				jsonFormat_details.put("EDUCATION", education_list_upper1);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("smtg went wrong");
		}
		
		return jsonFormat_details;
	}
	
}



