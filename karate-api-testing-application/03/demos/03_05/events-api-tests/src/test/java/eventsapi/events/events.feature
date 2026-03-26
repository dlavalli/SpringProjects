Feature: Test Event


 Background:
  * url 'http://localhost:8080/api'
  * def authFeature = call read('authentication.feature')
  * def access_token = authFeature.access_token
  
  Scenario: Get list of events for current user
  
  Given path 'events'
  And header Authorization = 'Bearer ' + access_token
  When method get
  Then status 200
  
  Scenario: Filter Events
  Given path 'events'
  And header Authorization = 'Bearer ' + access_token
  And params { eventName: 'Fitness'}
  When method get
  Then status 200
  
  Scenario: Delete Lock
  Given path 'events', 1
  And header Authorization = 'Bearer ' + access_token
  When method delete
  Then status 403
  * assert response.errorMessage == 'User not allowed to delete this event'
