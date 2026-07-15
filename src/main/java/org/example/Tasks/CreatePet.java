package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreatePet implements Task {

    private final int id;
    private final String name;
    private final String status;

    public CreatePet(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public static Performable withData(int id, String name, String status) {
        return instrumented(CreatePet.class, id, name, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(Post.to("/pet")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .body("""
                                {
                                  "id": %d,
                                  "name": "%s",
                                  "status": "%s"
                                }
                                """.formatted(id, name, status))
                        .log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
        if (SerenityRest.lastResponse().statusCode() == 200) {
            OnStage.theActorInTheSpotlight().remember("petId", SerenityRest.lastResponse().path("id").toString());
            String valorDelPetId = actor.recall("petId");
            System.out.println("PET ID: " + valorDelPetId);
        }
    }
}