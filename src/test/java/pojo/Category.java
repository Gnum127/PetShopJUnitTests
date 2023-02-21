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
public class Category {

    public String id;
    public String name;

    public static Category categoryBuild(Map<String, String> params) {
        return Category.builder()
                .id(params.get("categoryId"))
                .name(params.get("categoryName"))
                .build();
    }
}