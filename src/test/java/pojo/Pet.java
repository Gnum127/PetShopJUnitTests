package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    public String id;
    public String name;
    public Category category;
    public ArrayList<String> photoUrls;
    public ArrayList<Tag> tags;
    public String status;

    public static Pet petBuild(Map<String, String> params) {
        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(params.get("photoUrls"));
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.tagBuild(params));
        return Pet.builder()
                .id(params.get("id"))
                .category(Category.categoryBuild(params))
                .name(params.get("name"))
                .photoUrls(photoUrls)
                .tags(tags)
                .status(params.get("status"))
                .build();
    }
}
