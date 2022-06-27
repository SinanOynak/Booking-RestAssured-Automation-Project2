package endPoinTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import serviceHelper.RestfulBookerService;

public class CreateBooking {
    RestfulBookerService restfulBookerService = new RestfulBookerService();
    String token;
    String bookingid;


    @BeforeClass
    public void preConditions(){
      Response authToken = restfulBookerService.authCreateToken();
      token = authToken.jsonPath().getString("token");
    }

    @Test
    public void createBooking(){
        String crateBookingBody = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Response postCreateBooking = RestAssured.given()
                .body(crateBookingBody)
                .header("Content-Type", "application/json")
                .log().all(true)
                .post("https://restful-booker.herokuapp.com/booking")
                .andReturn();
        Assert.assertEquals(postCreateBooking.getStatusCode(),200);
        Assert.assertEquals(postCreateBooking.jsonPath().getString("booking.firstname"),"Jim");
        bookingid= postCreateBooking.jsonPath().getString("bookingid");

    }

    @AfterClass
    public void postConditons(){
     restfulBookerService.deleteBooking(token,bookingid );
    }
}
