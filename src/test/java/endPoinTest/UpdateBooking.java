package endPoinTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import serviceHelper.RestfulBookerService;

public class UpdateBooking {

    RestfulBookerService restfulBookerService = new RestfulBookerService();
    String bookingid;
    String token;

    @BeforeClass
    public void preConditions(){
        Response authToken = restfulBookerService.authCreateToken();
        token = authToken.jsonPath().getString("token");
        Response createBooking = restfulBookerService.createBooking();
        bookingid = createBooking.jsonPath().getString("bookingid");
    }

    @Test
    public void updateBookingHappyPath(){
        Response putUpdateBooking = restfulBookerService.putUpdateBooking(token,bookingid);
        Assert.assertEquals(putUpdateBooking.getStatusCode(),200);
        Assert.assertEquals(putUpdateBooking.jsonPath().getString("firstname"),"Sinan");
    }

    @AfterClass
    public void postConditons(){
        restfulBookerService.deleteBooking(token,bookingid);
    }
}
