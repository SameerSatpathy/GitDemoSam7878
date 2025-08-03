package demo;

import io.restassured.RestAssured;


import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

public class ECommerceApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("samsatpathyrahul@gmail.com");
		loginRequest.setUserPassword("Sam@12345");
		RequestSpecification reqLogin=given().log().all().spec(req).body(loginRequest);
		LoginResponse loginresponse=reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		System.out.println(loginresponse.getToken());
		String token=loginresponse.getToken();
		System.out.println(loginresponse.getUserId());
		String userId=loginresponse.getUserId();
		
		//Add Product
		
		RequestSpecification addProductBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).build();
		RequestSpecification reqAddProduct=given().log().all().spec(addProductBaseReq).param("productName", "One-Plus").param("productAddedBy", userId)
		.param("productCategory", "electronics").param("productSubCategory", "mobiles")
		.param("productPrice", "30000").param("productDescription", "One-Plus-Nord5g")
		.param("productFor", "men").multiPart("productImage",new File("C:\\Users\\samee\\OneDrive\\Pictures\\productImage_1650649561326.jpg"));
		
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		JsonPath js=new JsonPath(addProductResponse);
		String productId=js.get("productId");
		
		//Create Order
		RequestSpecification createOrderBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail orderDetail=new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail>orderDetailList= new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);
		
		Orders orders=new Orders();
		orders.setOrders(orderDetailList);
		RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(orders);
		String responseAddOrder=createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		System.out.println(responseAddOrder);
		
		//Delete Product
		
		RequestSpecification deleteProdBaseReq =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		RequestSpecification deleteProdReq=given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		String deleteProductResponse=deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
		.extract().response().asString();
		
		JsonPath js1=new JsonPath(deleteProductResponse);
		
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
		
		
	}

}
