package pet;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import metods.PetMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PetMethodsTest {

    Map<String, String> params = new HashMap<>();

    @BeforeEach
    public void buildParams() {
        params.put("categoryId", "88");
        params.put("categoryName", "rat");
        params.put("name", "Klara");
        params.put("photoUrls", "https://upload.wikimedia.org/wikipedia/commons/f/fd/Fancy_rat_blaze.jpg");
        params.put("tagId", "99");
        params.put("tagName", "little");
        params.put("status", "available");
        RestAssured.baseURI = "https://petstore.swagger.io/v2/pet";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "application/json")
                .build();
    }

    @ParameterizedTest(name = "Проверка поиска животных по статусу")
    @CsvSource({
            "available, 200",
            "sold, 200",
            "pending, 200",
            "whatever, 400"
    })
    public void findPetForStatus(String status, int statusCode) throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        params.replace("status", status);
        assertEquals(200, pet.postPet(params));
        assertEquals(statusCode, pet.getPetWithParams(status));
        assertTrue(pet.bodyContainsResponse());
    }
}
