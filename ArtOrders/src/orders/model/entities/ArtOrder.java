package orders.model.entities;


import orders.model.enums.OrderStatus;

public class ArtOrder {
    private int id;
    private OrderStatus orderStatus;
    private String image;
    private User customer;
    private OrderApplication orderApplication;

    public ArtOrder(int id, OrderStatus orderStatus, String image, User customer, OrderApplication orderApplication) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.image = image;
        this.customer = customer;
        this.orderApplication = orderApplication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public OrderApplication getOrderApplication() {
        return orderApplication;
    }

    public void setOrderApplication(OrderApplication orderApplication) {
        this.orderApplication = orderApplication;
    }

    @Override
    public String toString() {
        return "ArtOrder{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", image='" + image + '\'' +
                ", customer=" + customer +
                ", orderApplication=" + orderApplication +
                '}';
    }
}
