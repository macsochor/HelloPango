class CreateDeals < ActiveRecord::Migration[5.1]
  def change
    create_table :deals do |t|
      t.string :merchant_name
      t.float :amount
      t.string :coupon_code

      t.timestamps
    end
  end
end
