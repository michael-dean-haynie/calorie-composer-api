Feature: Tests crud operations for Food type

Background:
    * def createFoodPayload = read('classpath:payloads/create-food.json')
#    * call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

#    ---------------------------------------------
#    Create
#    ---------------------------------------------

Scenario: Create a Food
    Given url 'http://localhost:8080/'
    And path 'food'
    And request createFoodPayload
    When method post
    Then status 200
    And match response == schemas.food

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

#    TODO: add scenarios for error responses for each endpoint.