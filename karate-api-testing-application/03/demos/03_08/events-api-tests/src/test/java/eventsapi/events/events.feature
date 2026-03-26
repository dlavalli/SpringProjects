Feature: Test Event


 Background:
  * url 'http://localhost:8080/api'
  * def authFeature = call read('authentication.feature')
  * def access_token = authFeature.access_token
  * def tokenDetails = authFeature.tokenDetails
  
  Scenario: Get list of events for current user
  
  Given path 'events'
  And header Authorization = 'Bearer ' + access_token
  When method get
  Then status 200
  * def userIds = karate.map(response.data, function(x){ return x.userId })
  
  * match karate.distinct(userIds) contains only tokenDetails.uid
  
  
  
  
  
  Scenario: Filter Events
  * def eventName = 'Fitness'
  Given path 'events'
  And header Authorization = 'Bearer ' + access_token
  And params { eventName: '#(eventName)'}
  When method get
  Then status 200
  
  * match each response.data[*].name == '#regex .*' + eventName + '.*'
  
  
  
  Scenario: Delete Lock
  Given path 'events', 1
  And header Authorization = 'Bearer ' + access_token
  When method delete
  Then status 403
  * assert response.errorMessage == 'User not allowed to delete this event'
