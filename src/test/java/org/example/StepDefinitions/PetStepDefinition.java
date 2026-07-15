package org.example.StepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.example.Questions.ResponseCode;
import org.example.Tasks.CreatePet;
import org.example.Tasks.DeletePet;
import org.example.Tasks.GetPet;
import org.example.Tasks.UpdatePet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class PetStepDefinition {

    public static Logger LOGGER = LoggerFactory.getLogger(PetStepDefinition.class);

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de PetStore")
    public void elActorEstableceElEndpointDePetStore(Actor actor) {
        actor.whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @When("el actor crea una mascota con los datos {string} {string} {string}")
    public void elActorCreaUnaMascotaConLosDatos(String id, String name, String status) {
        int petId = Integer.parseInt(id);
        theActorInTheSpotlight().attemptsTo(CreatePet.withData(petId, name, status));
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(seeThat("El código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }

    @When("el actor consulta la mascota con id {int}")
    public void elActorConsultaLaMascotaConId(int id) {
        theActorInTheSpotlight().attemptsTo(GetPet.byId(id));
    }

    @When("el actor actualiza la mascota con los datos {string} {string} {string}")
    public void elActorActualizaLaMascotaConLosDatos(String id, String name, String status) {
        int petId = Integer.parseInt(id);
        theActorInTheSpotlight().attemptsTo(UpdatePet.withData(petId, name, status));
    }

    @When("el actor elimina la mascota creada")
    public void elActorEliminaLaMascotaCreada() {
        String petId = theActorInTheSpotlight().recall("petId");
        if (petId != null) {
            int id = Integer.parseInt(petId);
            theActorInTheSpotlight().attemptsTo(DeletePet.byId(id));
        }
    }


}