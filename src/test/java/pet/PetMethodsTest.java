package pet;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import metods.PetMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @ParameterizedTest(name = "Поиск животных по статусу")
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

    @Test
    @DisplayName("Создание животного")
    public void createPet() throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        assertEquals(200, pet.postPet(params));
        assertTrue(pet.requestEqualResponse());
        assertEquals(200, pet.getPetWithId());
        assertTrue(pet.requestEqualResponse());
    }

    @Test
    @DisplayName("Создание животного с невалидным id")
    public void createPetWithWrongId() throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        params.put("id", "q");
        assertEquals(405, pet.postPet(params));
    }

    @Test
    @DisplayName("Удаление животного")
    public void deletePet() throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        assertEquals(200, pet.postPet(params));
        assertEquals(200, pet.deletePet());
        assertEquals(404, pet.getPetWithId());
    }

    @Test
    @DisplayName("Удаление животного, которого нет")
    public void deleteWrongPet() throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        assertEquals(200, pet.postPet(params));
        pet.changeId("1");
        assertEquals(404, pet.deletePet());
        assertEquals(404, pet.getPetWithId());
    }

    @Test
    @DisplayName("Удаление животного с неверный форматом параметра id")
    public void deleteWrongIdPet() throws JsonProcessingException {
        PetMethods pet = new PetMethods();
        assertEquals(200, pet.postPet(params));
        pet.changeId("q");
        assertEquals(400, pet.deletePet());
    }
}
