package vn.student.vluxfashion.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private String brandId;
    private String brandName;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
