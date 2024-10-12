package vn.student.vluxfashion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {
    private String fullName; // Full name of the guest
    private String email; // Email of the guest
    private String phone; // Phone number of the guest
    private String address; // Primary address of the guest
    private String address2; // Secondary address (optional)
    private String city; // City of the guest
}
