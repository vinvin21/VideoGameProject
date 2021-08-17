package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

//make this import static
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

//import  two dependacies manually
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
public class TC_VideoGameAPI {
	@Test(priority=1)
	public void test_getAllVideoGames() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then().statusCode(200);
		
	}
	@Test(priority=2)
	public void test_addNewVideoGame() {
		Map map = new HashMap();
		map.put("id","100");
		map.put("name","Spider-Man");
		map.put("releaseDate","2019-09-20T08:55:58.510Z");
		map.put("reviewScore","5");
		map.put("category","Adventure");
		map.put("rating","Universal");
		
		Response res = given().contentType("application/json")
		.body(map)
        .when().post("http://localhost:8080/app/videogames")	
	    .then().statusCode(200).log().body()
	    .extract().response();
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
		
	}
	@Test(priority=3)
	public void  test_getVideoGame() {
		
		given().when().get("http://localhost:8080/app/videogames/100")
	.then().statusCode(200).log().body()
	.body("videoGame.id",equalTo("100"))
	.body("videoGame.name", equalTo("Spider-Man"));
	
	}
	
	@Test(priority=4)
	public void test_UpdateVideoGame() {
		Map map = new HashMap();
		map.put("id","100");
		map.put("name","Pacman");
		map.put("releaseDate","2019-08-20T08:55:58.510Z");
		map.put("reviewScore","4");
		map.put("category","Adventure");
		map.put("rating","Universal");
		
		given().contentType("application/json")
		.body(map).when()
		.put("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.body("videoGame.id",equalTo("100"))
		.body("videoGame.name", equalTo("Pacman"));
			
	}
	@Test(priority=5)
	public void test_DeleteVideoGame() throws InterruptedException {
		Response res = given().when()
		.delete("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.extract().response();
		Thread.sleep(3000);
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
	}

}
