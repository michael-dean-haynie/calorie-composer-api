@focus
Feature: Tests crud operations for Unit type

  Background:
    Given url baseUrl

    * def createUnitPayload = read('classpath:payloads/create-unit.json')

  # ---------------------------------------------
  # Create
  # ---------------------------------------------

  Scenario: Create a unit item
    * set createUnitPayload.abbreviation = utils.rs(5)

    Given path 'unit'
    And request createUnitPayload
    When method post
    Then status 201
    And match response == schemas.unit

    # confirm read endpoint works as well
    When call read('classpath:callable/crud/unit/read-unit.feature') { id: '#(response.id)'}

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(response.id)'}

  Scenario: Creating a unit that matches an existing unit results in 400
    * set createUnitPayload.abbreviation = utils.rs(5)

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit'
    And request createUnitPayload
    When method post
    Then status 400

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(createUnitResult.response.id)'}

  # ---------------------------------------------
  # Read
  # ---------------------------------------------

  Scenario: Read a unit item
    * set createUnitPayload.abbreviation = utils.rs(5)

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit', createUnitResult.response.id
    When method get
    Then status 200
    And match response == schemas.unit

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(response.id)'}

  Scenario: Read all unit items for user
    * set createUnitPayload.abbreviation = utils.rs(5)

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit/all'
    When method get
    Then status 200
    And match response == '##[] schemas.unit'

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(createUnitResult.response.id)'}

  # ---------------------------------------------
  # Update
  # ---------------------------------------------

  Scenario Outline: Update a unit item and validate '<fieldName>' set appropriately
    * set createUnitPayload.abbreviation = utils.rs(5)

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    # stash initial value
    * copy initialUnit = createUnitResult.response

    # apply change
    * copy payload = createUnitResult.response
    * set payload.<fieldName> = <fieldValue>

    # update
    Given path 'unit'
    And request payload
    When method put
    Then status 200

    # assert updated value in create response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * copy almstInitial = initialUnit
    * set almstInitial.<fieldName> = '#ignore'
    * match response == almstInitial

    # assert update value in separate read response
    Given path 'unit', response.id
    When method get
    Then status 200

    # assert updated value in get response
    * match response.<fieldName> == payload.<fieldName>

    # assert other values didn't change
    * match response == almstInitial

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(response.id)'}

    Examples:
      | fieldName    | fieldValue  |
      | singular     | utils.rs(5) |
      | plural       | utils.rs(5) |
      | abbreviation | utils.rs(5) |

  # ---------------------------------------------
  # Delete
  # ---------------------------------------------

  Scenario: Delete a unit item
    * set createUnitPayload.abbreviation = utils.rs(5)

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit', createUnitResult.response.id
    When method delete
    Then status 200

    # confirm we cannot read item anymore
    Given path 'unit', createUnitResult.response.id
    When method get
    Then status 404