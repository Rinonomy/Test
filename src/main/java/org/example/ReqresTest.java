package org.example;


import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.time.Clock;
import java.util.List;

import static io.restassured.RestAssured.given;


public class ReqresTest {
    private final static String URL = "https://reqres.in/";

    @Test
    public void SuccessfullyRegisteredTest() {
        Specifications.installsSpec(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessRegister successRegister = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessRegister.class);
        Assert.assertEquals(id,successRegister.getId());
        Assert.assertEquals(token,successRegister.getToken());
    }
    @Test
    public void UnSuccessfullyRegisteredTest() {
        Specifications.installsSpec(Specifications.requestSpec(URL), Specifications.responseSpecUnique(400));
        Register user = new Register("sydney@fife", "");
        UnSucReg unSucReg = given()
                .body(user)
                .post("api/register")
                .then().log().all()
                .extract().as(UnSucReg.class);
        Assert.assertEquals("Missing password",unSucReg.getError());
    }
    @Test
    public void checkEmailTest() {
        Specifications.installsSpec(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data",UserData.class);
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    }
    @Test
    public void deleteUserTest() {
        Specifications.installsSpec(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();
    }
    @Test
    public void timeTest() {
        Specifications.installsSpec(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        UserTime user = new UserTime("morpheus","zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        Assert.assertEquals(Clock.systemUTC().instant().toString(),response.getUpdatedAt());
        //Честно, я не знаю как сделать время так, чтоб символы совпали:(
        // Только до этого додумалась...
    }
}
