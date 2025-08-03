package demo;

import io.restassured.RestAssured;


import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] courseTitles= {"Selenium Webdriver Java","Cypress","Protractor"};
		String response=given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		System.out.println(response);
		
		JsonPath jsonPath = new JsonPath(response);
		String accessToken=jsonPath.getString("access_token");
		
		
		/*String response2 =given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token=ZrdStolQdYJN1AYNo8dy5w==").asString();
		
		System.out.println(response2);*/
		
		GetCourse gc =given().queryParam("access_token", accessToken)
				.when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token="+ accessToken).as(GetCourse.class);
				
				System.out.println(gc.getLinkedIn());
				System.out.println(gc.getCourses());
				
				System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());
				//System.out.println(gc.getError());
				
				List<Api>apiCourses=gc.getCourses().getApi();
				for(int i=0;i<apiCourses.size();i++)
				{
					if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java"))
					{
						System.out.println(apiCourses.get(i).getPrice());
					}
				}
				//Get the course names of WebAutomation
				ArrayList<String> a= new ArrayList<String>();
				
				List<WebAutomation>wA=gc.getCourses().getWebAutomation();
				for(int j=0;j<wA.size();j++)
				{
					a.add(wA.get(j).getCourseTitle());
				}
				
				List<String> expectedList=Arrays.asList(courseTitles);
				Assert.assertTrue(a.equals(expectedList));
				
				
				
	}

}
