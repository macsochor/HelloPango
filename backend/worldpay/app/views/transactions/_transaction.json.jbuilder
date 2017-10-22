json.extract! transaction, :id, :customer_id, :amount, :order_id, :created_at, :updated_at
json.url transaction_url(transaction, format: :json)
