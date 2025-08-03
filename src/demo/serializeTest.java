package demo;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class serializeTest {
	
	public static void main(String[]args) {
		try {
		    RestAssured.baseURI = "https://rahulshettyacademy.com";

		    AddPlace p = new AddPlace();
		    p.setAccuracy(50);
		    p.setAddress("29, side layout, cohen 10");
		    p.setLanguage("French-IN");
		    p.setPhone_number("(+91) 983 893 8937");
		    p.setWebsite("http://google.com");
		    p.setName("Frontline house");

		    List<String> myList = new ArrayList<>();
		    myList.add("shoe park");
		    myList.add("shop");
		    p.setTypes(myList);

		    Location l = new Location();
		    l.setLat(-38.383494);
		    l.setLng(33.427362);
		    p.setLocation(l);

		    // Serialize POJO to JSON
		    ObjectMapper mapper = new ObjectMapper();
		    String payload = mapper.writeValueAsString(p);
		    System.out.println("Serialized Payload:\n" + payload);

		    Response res = given().queryParam("key", "qaclick123")
		        .header("Content-Type", "application/json")
		        .body(payload)
		        .when().post("/maps/api/place/add/json")
		        .then().assertThat().statusCode(200)
		        .extract().response();

		    String responseString = res.asString();
		    System.out.println("Response:\n" + responseString);

		} catch (JsonProcessingException e) {
		    System.err.println("❌ Serialization failed:");
		    e.printStackTrace();
		} catch (Exception ex) {
		    System.err.println("❌ Request failed:");
		    ex.printStackTrace();
		}

	}
	
	

}
