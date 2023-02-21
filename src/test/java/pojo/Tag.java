package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    public String id;
    public String name;

    public static Tag tagBuild(Map<String, String> params) {
        return Tag.builder()
                .id(params.get("tagId"))
                .name(params.get("tagName"))
                .build();
    }
}
