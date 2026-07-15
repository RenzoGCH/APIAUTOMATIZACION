@PetStoreAll
Feature: APIS from PetStore

  Como usuario de PetStore
  Quiero gestionar mascotas
  Para poder verificar el flujo CRUD completo

  @CP01_Pet @Post_Pet @PetStore
  Scenario Outline: Crear una mascota exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor crea una mascota con los datos "<id>" "<name>" "<status>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | id    | name     | status    |
      | 12345 | Firulais | available |


  @CP02_Pet @Get_Pet @PetStore
  Scenario: Obtener una mascota por ID exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor consulta la mascota con id 12345
    Then el codigo de respuesta debe ser 200

  @CP03_Pet @Put_Pet @PetStore
  Scenario Outline: Actualizar datos de una mascota exitosamente
    Given el actor establece el endpoint de PetStore
    And el actor crea una mascota con los datos "12345" "Temporal" "available"
    When el actor actualiza la mascota con los datos "<id>" "<name>" "<status>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | id    | name  | status  |
      | 999   | Max   | sold    |


  @CP04_Pet @Delete_Pet @PetStore
  Scenario: Eliminar una mascota exitosamente
    Given el actor establece el endpoint de PetStore
    When el actor crea una mascota con los datos "999" "Temporal" "available"
    When el actor elimina la mascota creada
    Then el codigo de respuesta debe ser 200