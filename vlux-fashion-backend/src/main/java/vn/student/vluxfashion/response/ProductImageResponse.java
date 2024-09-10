package vn.student.vluxfashion.response;

public class ProductImageResponse {
    private Integer imageId;
    private String imageUrl;
    private Boolean isMain;

    
    public ProductImageResponse(Integer imageId, String imageUrl, Boolean isMain) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
    }
    public Integer getImageId() {
        return imageId;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Boolean getIsMain() {
        return isMain;
    }
    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

}
