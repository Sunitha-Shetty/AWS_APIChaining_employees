package AWS_APIChaining;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AWS_EndToEndTest {
	
	Response response;
	String BaseURI = "http://3.91.157.188:8088/employees";
	
	@Test
	public void test1()
	{		
		response = GetMethodAll();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("Amith","Shah","6000", "Amith123@zzz.com");
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath Jpath = response.jsonPath();
	    int EmpID = Jpath.get("id");
	    System.out.println("id "+ EmpID);
	    
	    response = PutMethod(EmpID, "Jack","Main", "1000", "asdf@gmail.com");
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Jpath = response.jsonPath();
	    Assert.assertEquals(Jpath.get("firstName"), "Jack");
	    Assert.assertEquals(Jpath.get("lastName"), "Main");
	    Assert.assertEquals(Jpath.get("email"), "asdf@gmail.com");
	    
	    response = DeleteMethod(EmpID);
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Jpath = response.jsonPath();
	    Assert.assertEquals(response.getBody().asString(), ""); 
	    
	    response = GetMethod(EmpID); 
		Assert.assertEquals(response.getStatusCode(), 400);
//	Assert.assertEquals(response.getBody().asString(), "");
		Jpath = response.jsonPath();
	      Assert.assertEquals(Jpath.get("message"), "Entity Not Found");
		
	      System.out.println(response.getStatusCode());
				
	}
	
	public Response GetMethodAll()
	{
		RestAssured.baseURI = BaseURI ;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		return response;
		
	}
	
	public Response PostMethod(String firstName, String lastName, String salary, String email)
	{
		RestAssured.baseURI = BaseURI ;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.post();
		
		return response;	
		
	}	
	
	public Response PutMethod(int EmpID, String firstName, String lastName, String salary, String email)
	{
		RestAssured.baseURI = BaseURI ;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(jobj.toString())
				.put("/"+EmpID);
		
		return response;
		
	}

	public Response DeleteMethod(int EmpID)
	{
		RestAssured.baseURI = BaseURI ;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/"+ EmpID);
		
		return response;
		
	}   
	
	public Response GetMethod(int Empid) {
		
		RestAssured.baseURI = BaseURI;
		
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/"+Empid);
		
		
	return response;
	}

}
