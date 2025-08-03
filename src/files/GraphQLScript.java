package files;
import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Query
		
		int characterId=16419;
		
		String response=given().log().all().header("content-Type", "application/json").body( "{\"query\":\"query($characterId: Int! ,$locationId:Int! , $episodeId:Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  location(locationId:$locationId)\\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    name\\n    air_date\\n    episode\\n  }\\n}\\n\\n\",\"variables\":{\"characterId\":"+characterId+",\"locationId\":23007,\"episodeId\":15754}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		
		System.out.println(response);
		JsonPath js= new JsonPath(response);
		String characterName= js.getString("data.character.name");
		Assert.assertEquals(characterName, "Sameer");
		
		//Mutations
		

		String newCharacter="Robin";
		String mutationResponse=given().log().all().header("content-Type", "application/json").body( "{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!) {\\n\\n  createLocation(location:{name:$locationName,type:\\\"Southzone\\\",dimension:\\\"7878\\\"})\\n  {\\n    id\\n  }\\n   createCharacter(character:{name:$characterName,type:\\\"Macho\\\",status:\\\"alive\\\",species:\\\"human\\\",gender:\\\"Male\\\",image:\\\"png\\\",originId:23063,locationId:23063})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"25-July-2025\\\",episode:\\\"Risk\\\"})\\n  {\\n    id\\n  }\\n  deleteLocations(locationIds:[23064])\\n  {\\n    locationsDeleted\\n  }\\n} \",\"variables\":{\"locationName\":\"Australia\",\"characterName\":\""+newCharacter+"\",\"episodeName\":\"The Family Man 2\"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		
		System.out.println(mutationResponse);
		JsonPath js1= new JsonPath(mutationResponse);
	
	}

}
