package vn.student.vluxfashion.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorResponse {
    private Integer productColorId;
    private String colorName;
    private String imageUrl;
}
