package wdsr.exercise2.procon;

import java.math.BigDecimal;

public class Order {
	private final long id;
	private final String productName;
	private final int amount;
	private final BigDecimal price;
	
	public Order(long id, String productName, int amount, BigDecimal price) {
		this.id = id;
		this.productName = productName;
		this.amount = amount;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public int getAmount() {
		return amount;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productName=" + productName + ", amount=" + amount + ", price=" + price + "]";
	}
}
