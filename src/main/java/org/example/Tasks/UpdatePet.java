package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdatePet implements Task {
    private final int id;
    private final String name;
    private final String status;

    public UpdatePet(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public static Performable withData(int id, String name, String status) {
        return instrumented(UpdatePet.class, id, name, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/pet")
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
                                .log().all())
        );
    }
}