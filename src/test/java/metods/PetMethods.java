package metods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import pojo.Pet;

import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static pojo.Pet.petBuild;

public class PetMethods {

    static Pet requestPetBody;
    static Pet responsePetBody;
    static Response response;
    static String id;
    ObjectMapper mapper = new ObjectMapper();
    Pet[] pets;

    public int postPet(Map<String, String> params) throws JsonProcessingException {
        requestPetBody = petBuild(params);
        response = given()
                .body(requestPetBody)
                .post();
        responsePetBody = mapper.readValue(response.getBody().asPrettyString(), Pet.class);
        requestPetBody.setId(responsePetBody.getId());
        id = responsePetBody.getId();
        return response.getStatusCode();
    }

    public int getPetWithId() {
        response = given()
                .basePath("/" + id)
                .get();
        responsePetBody = response.getBody().as(Pet.class);
        return response.getStatusCode();
    }

    public int getPetWithParams(String status) throws JsonProcessingException {
        String link = "findByStatus";
        response = given()
                .param("status", status)
                .get(link);
        pets = mapper.readValue(response.getBody().asPrettyString(), Pet[].class);
        return response.getStatusCode();
    }

    public boolean bodyContainsResponse() {
        return Arrays.stream(pets).anyMatch(pet -> pet.equals(responsePetBody));
    }
}
