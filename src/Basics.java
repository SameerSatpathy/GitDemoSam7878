import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;
public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//validate if Add Place API is working as expected 
		//given- all input details
		//when-submit the api
		//Then-validate the response 
	
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		//Add Place -> Update Place with New Address -> Get Place to validate if New address is present in response
		System.out.println(response);
		// to convert the above response into json 
		JsonPath js= new JsonPath(response); //for parsing Json
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		//update place
		String newAddress="Summer walk 2, Africa";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ " ").when().put("maps/api/place/update/json").then().assertThat().log().all().body("msg", equalTo("Address successfully updated"));
		
		//GetPlace
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId).get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress= js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress,newAddress);
		
		//CucumberJunit, Testng
		
	}

}
