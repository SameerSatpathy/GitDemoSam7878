package demo;

import io.restassured.RestAssured;


import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://sameerphotos7878.atlassian.net/";
		
		String createIssueResponse=given().log().all().auth().preemptive().basic("sameerphotos7878@gmail.com","ATATT3xFfGF08f1no0nDHT71HEH-HEcSzBdpVjchH5FgrxA-dE8rW_NXNK4c1Ya50vm_e0iZKI00T_1WGSkuSKbvzy-DZjZE0m9YX_8dnE4wv8UOOUs0mR3j0IhnTu5G0NCCgax1YYL-3RsN2IPbsn4vkgW3cdg3oHDV0zQ4Y1ZKaCHelCP6Ob8=FCA3842B")
				.header("Accept", "*/*").header("Content-Type","application/json")
		.body("{\n" +
				  "  \"fields\": {\n" +
				  "    \"project\": {\n" +
				  "      \"key\": \"SCRUM\"\n" +
				  "    },\n" +
				  "    \"summary\": \"Submit Button 2 not working in automation selenium\",\n" +
				  "    \"description\": {\n" +
				  "      \"type\": \"doc\",\n" +
				  "      \"version\": 1,\n" +
				  "      \"content\": [\n" +
				  "        {\n" +
				  "          \"type\": \"paragraph\",\n" +
				  "          \"content\": [\n" +
				  "            {\n" +
				  "              \"type\": \"text\",\n" +
				  "              \"text\": \"While filling all the details, submit is enabled but upon clicking no action is being performed\"\n" +
				  "            }\n" +
				  "          ]\n" +
				  "        }\n" +
				  "      ]\n" +
				  "    },\n" +
				  "    \"issuetype\": {\n" +
				  "      \"name\": \"Bug\"\n" +
				  "    }\n" +
				  "  }\n" +
				"}")
		.when().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js=new JsonPath(createIssueResponse);
		String issueId=js.getString("id");
		System.out.println(issueId);
		
		//Add Attachment
		given().pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.auth().preemptive().basic("sameerphotos7878@gmail.com","ATATT3xFfGF08f1no0nDHT71HEH-HEcSzBdpVjchH5FgrxA-dE8rW_NXNK4c1Ya50vm_e0iZKI00T_1WGSkuSKbvzy-DZjZE0m9YX_8dnE4wv8UOOUs0mR3j0IhnTu5G0NCCgax1YYL-3RsN2IPbsn4vkgW3cdg3oHDV0zQ4Y1ZKaCHelCP6Ob8=FCA3842B")
		.multiPart("file", new File("C:\\Users\\samee\\OneDrive\\Pictures\\Screenshots\\sql.png")).log().all()
		.post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	}

}
