package vn.student.vluxfashion.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {

    private Integer subCategoryId;
    private String subCategoryName;
    private String gender;
    private Date createdAt;
    private Date updatedAt;
    private String categoryName; 

}
