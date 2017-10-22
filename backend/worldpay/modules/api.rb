require 'net/http'
require 'uri'
require 'json'
require 'pry'

class WorldPayApi

    DEVELOPER_PARAMS = {
        developerId: 12345678,
        version: '1.2'
    }
    
    def initialize(secureNetId, secureKey, url)
        @secureNetId = secureNetId
        @secureKey = secureKey
        @base_uri = url
    end

    def sync_transactions(params)
        # Can be way more optimized
        # But for the prupose of this hackathon, it's simplified
        url = "/api/transactions/Search"
        payload = params.merge(developerApplication: DEVELOPER_PARAMS)
        call_api(url, payload)
    end

    def prior_auth_capture(params)
        url = "/api/Payments/Capture"
        payload = params.merge(developerApplication: DEVELOPER_PARAMS)
        call_api(url, payload)
    end

    private

    def call_api(url, payload)
        uri = URI.parse(@base_uri + url)
        http = Net::HTTP.new(uri.host, uri.port)
        http.use_ssl = true
        req = Net::HTTP::Post.new(uri.request_uri)
        req.body = payload.to_json
        req["Content-Type"] = 'application/json'
        req.basic_auth @secureNetId, @secureKey
        res = http.request(req)
        JSON.parse(res.body)
    end
end

secureNetId = '8011036'
secureKey = 'JTcEC7m6FhGE'
url = 'https://gwapi.demo.securenet.com'

api = WorldPayApi.new(secureNetId, secureKey, url)
response = api.sync_transactions({startDate: '10/21/2017', endDate: '10/22/2017'})

response = api.prior_auth_capture({transactionId: '116028020', amount: 1})
binding.pry
