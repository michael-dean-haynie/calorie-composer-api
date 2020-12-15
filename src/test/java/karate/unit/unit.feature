
Feature: Tests crud operations for Unit type

  Background:
    Given url baseUrl

    * def createUnitPayload = read('classpath:payloads/create-unit.json')
    # set random abbreviation so concurrent tests don't collide
    * set createUnitPayload.abbreviation = utils.rs(5)

    * def standaloneDraftUnitPayload = read('classpath:payloads/create-unit-draft-standalone.json')
    # set random abbreviation so concurrent tests don't collide
    * set standaloneDraftUnitPayload.abbreviation = utils.rs(5)

  # ---------------------------------------------
  # Create
  # ---------------------------------------------

  Scenario: Create a unit item

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

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit', createUnitResult.response.id
    When method get
    Then status 200
    And match response == schemas.unit

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(response.id)'}

  Scenario: Read all unit items for user (includes drafts but only ones that are standalone)

    # Create unit with draft unit included
    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    # Create a standalone draft
    When def standaloneDraftUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(standaloneDraftUnitPayload)'}

    # Get all units that aren't a draft of another unit
    Given path 'unit/all'
    When method get
    Then status 200
    And match response == '##[] schemas.unit'

    * match response[*].id contains createUnitResult.response.id
    * match response[*].id contains standaloneDraftUnitResult.response.id
    * match response[*].id !contains createUnitResult.response.draft.id

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(createUnitResult.response.id)'}
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(standaloneDraftUnitResult.response.id)'}

  # ---------------------------------------------
  # Update
  # ---------------------------------------------

  Scenario Outline: Update a unit item and validate '<fieldName>' set appropriately
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
      | fieldName          | fieldValue  |
      | singular           | utils.rs(5) |
      | plural             | utils.rs(5) |
      | abbreviation       | utils.rs(5) |
      | isDraft            | false       |
      | draft.singular     | utils.rs(5) |
      | draft.plural       | utils.rs(5) |
      | draft.abbreviation | utils.rs(5) |


  Scenario: Update a unit with a new draft

    # Create a unit without a draft
    * copy createUnitWithoutDraftPayload = createUnitPayload
    * remove createUnitWithoutDraftPayload.draft
    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitWithoutDraftPayload)'}

    # update unit to have a draft now
    * copy updatedUnitWithDraft = createUnitResult.response
    * set updatedUnitWithDraft.draft = createUnitPayload.draft
    When def updateUnitResult = call read('classpath:callable/crud/unit/update-unit.feature') { request: '#(updatedUnitWithDraft)'}
    And match updateUnitResult.response.draft == schemas.unit

    # assert update via get response
    When def getUnitResult = call read('classpath:callable/crud/unit/read-unit.feature') { id: '#(updateUnitResult.response.id)'}
    And match getUnitResult.response.draft == schemas.unit

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(createUnitResult.response.id)'}


  Scenario: Update a unit by removing a draft
    # Create a unit with a draft
    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    # Update unit by removing draft
    * copy updateUnitPayload = createUnitResult.response
    * remove updateUnitPayload.draft
    When def updateUnitResult = call read('classpath:callable/crud/unit/update-unit.feature') { request: '#(updateUnitPayload)'}
    And match updateUnitResult.response !contains { draft: '#notnull' }

    # assert update via get response
    When def getUnitResult = call read('classpath:callable/crud/unit/read-unit.feature') { id: '#(updateUnitResult.response.id)'}
    And match getUnitResult.response !contains { draft: '#notnull' }

    # clean up
    When call read('classpath:callable/crud/unit/delete-unit.feature') { id: '#(createUnitResult.response.id)'}


  # ---------------------------------------------
  # Delete
  # ---------------------------------------------

  Scenario: Delete a unit item

    When def createUnitResult = call read('classpath:callable/crud/unit/create-unit.feature') { request: '#(createUnitPayload)'}

    Given path 'unit', createUnitResult.response.id
    When method delete
    Then status 200

    # confirm we cannot read item anymore
    Given path 'unit', createUnitResult.response.id
    When method get
    Then status 404