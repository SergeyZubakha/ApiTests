package reqres.in.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetListUsersResponseModel {

    int page, per_page, total, total_pages;
    GetUsersResponseDataModel support;
    List<DataClass> data;

}




