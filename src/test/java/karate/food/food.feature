Feature: Tests crud operations for Food type

    Background:
        * def createFoodPayload = read('classpath:payloads/create-food.json')
    ##    * call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

#    ---------------------------------------------
#    Create
#    ---------------------------------------------

    Scenario: Create a food item
        Given url baseUrl
        And path 'food'
        And request createFoodPayload
        When method post
        Then status 201
        And match response == schemas.food

        # confirm read endpoint works as well
        * call read('classpath:callable/read-food.feature') { id: '#(response.id)'}

        # clean up
        * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

#    ---------------------------------------------
#    Read
#    ---------------------------------------------

    Scenario: Read a food item
        Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

        Given url baseUrl
        And path 'food', createFoodResult.response.id
        When method get
        Then status 200
        And match response == schemas.food

        # clean up
        * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

#    ---------------------------------------------
#    Delete
#    ---------------------------------------------

    Scenario: Delete a food item
        Given def createFoodResult = call read('classpath:callable/create-food.feature') { request: '#(createFoodPayload)'}

        Given url baseUrl
        And path 'food', createFoodResult.response.id
        When method delete
        Then status 200

        # confirm we cannot read item anymore
        Then path 'food', createFoodResult.response.id
        When method get
        Then status 404
