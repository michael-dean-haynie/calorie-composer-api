Feature: Tests crud operations for Food type

  Background:
    * def createFoodPayload = read('classpath:payloads/create-food.json')

    # Util
    * def utils = call read('classpath:callable/util/common.feature')

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

  @focus
  Scenario Outline: Create a food item and validate '<fieldName>' set appropriately
    Given def payload = createFoodPayload
    And set payload.<fieldName> = <fieldValue>

    Given url baseUrl
    And path 'food'
    And request payload
    When method post
    Then status 201

    # assert field value in create response
    Then match response.<fieldName> == payload.<fieldName>

    # assert field value in read response
    Given path 'food', response.id
    When method get
    Then status 200
    And match response.<fieldName> == payload.<fieldName>

    # clean up
    * call read('classpath:callable/delete-food.feature') { id: '#(response.id)'}

    Examples:
      | fieldName   | fieldValue   |
      | fdcId       | utils.rs(5)  |
      | description | utils.rs(10) |


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
