require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class OnboardMerchantTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @onboardMerchant ='''{
      "communityCode": "SANDBOX",
      "merchantDetails": [{
        "visaMerchantId": "34893938",
        "visaStoreId": "13655392"
      }]
    }'''
  end
  
  def test_onboard_merchant
    # base_uri = 'visadirect/'
    # resource_path = 'fundstransfer/v1/pushfundstransactions'
    base_uri = 'vop/'
    resource_path = 'v1/merchants/onboard'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Onboard Merchan test", "post", @onboardMerchant)
    assert_equal("200", response_code, "Onboard Merchant test failed")
  end
end
