package com.example.stampmap.dto;
import lombok.Data;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Place {
    private int placeId;
    @NotBlank
    private String placeName;
    @NotBlank
    @Size(min=1, max= 3000)
    private String description;
    @NotNull
    @Max(180)
    @Min(-180)
    private double longitude;
    @NotNull
    @Max(90)
    @Min(-90)
    private double latitude;
    @NotBlank
    private String address;
    private int userId;
    private String placeCreatedAt;
    private String placeUpdatedAt;
    private MultipartFile[] images;
    private List<String> publicIdsAndFormats;
    private String topUrl; 
}
