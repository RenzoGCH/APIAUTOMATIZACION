package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetPet implements Task {
    private final int petId;

    public GetPet(int petId) {
        this.petId = petId;
    }

    public static Performable byId(int petId) {
        return instrumented(GetPet.class, petId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/pet/" + petId)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .log().all())
        );
    }
}