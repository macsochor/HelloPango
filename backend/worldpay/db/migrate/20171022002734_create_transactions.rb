class CreateTransactions < ActiveRecord::Migration[5.1]
  def change
    create_table :transactions do |t|
      t.integer :customer_id
      t.float :amount
      t.string :order_id

      t.timestamps
    end
  end
end
