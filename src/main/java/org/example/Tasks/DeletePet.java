package org.example.Tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeletePet implements Task {
    private final int petId;

    public DeletePet(int petId) {
        this.petId = petId;
    }

    public static Performable byId(int petId) {
        return instrumented(DeletePet.class, petId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from("/pet/" + petId)
                        .with(requestSpecification -> requestSpecification
                                .log().all())
        );
    }
}