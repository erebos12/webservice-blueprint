Feature: Upload portfolio by HTTP/REST

  Scenario: Upload a valid portfolio in JSON format to BHC-WS
      Given the following json:
      """
      {
        "system_id": "PBC",
       	"company_id_type": "1",
	    "companies": [{
		"id": "43257",
		"business_partner_id": "54309888",
		"country": "DE",
		"data_profile": "Large"
	    }, {
		"id": "44561",
		"business_partner_id": "54435",
		"country": "US",
		"data_profile": "Medium"
	    }, {
		"id": "43756823",
		"business_partner_id": "12321432",
		"country": "SE",
		"data_profile": "Small"
	    }]
      }
      """
      When the client sends json by POST to url path /portfolios
      Then the client receives a response with status code 200
      And the response message is "Portfolio proceeded successfully. Uploaded 3 records to your portfolio"


