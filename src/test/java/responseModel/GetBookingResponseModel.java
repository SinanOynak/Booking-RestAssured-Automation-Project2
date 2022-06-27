package responseModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class GetBookingResponseModel {

    @JsonProperty("bookingid")
    public int bookingid;
}
