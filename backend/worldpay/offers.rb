require 'firebase'
require 'pry'
base_uri = 'https://rewards-6baf9.firebaseio.com/'

firebase = Firebase::Client.new(base_uri, "EJgCvD2B1Y1rJn8LATosZ66WiiOt4o4OUlKkAvpM")

response = firebase.push("todos", { :name => 'Pick the milk', :priority => 1 })
binding.pry
response.success? # => true
response.code # => 200
response.body # => { 'name' => "-INOQPH-aV_psbk3ZXEX" }
response.raw_body # => '{"name":"-INOQPH-aV_psbk3ZXEX"}'