package com.luky.online_shop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomerRequest {
    private String name;

    @Pattern(regexp = "^08\\d{9,11}$", message = "Nomor telepon harus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka.")
    private String mobilePhoneNo;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\\\d{4}-\\\\d{2}-\\\\d{2}$", message = "Format tanggal harus 'yyyy-MM=dd'")
    private String birthDate;
}
