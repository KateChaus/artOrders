package orders.model.entities;


import orders.model.enums.OrderStyle;
import orders.model.enums.OrderType;

public class OrderApplication {
    private int id;
    private OrderType type;
    private OrderStyle style;
    private String info;
    private int cost;
    private int slots;
    private User artist;

    public OrderApplication(int id, OrderType type, OrderStyle style, String info, int cost, int slots, User artist) {
        this.id = id;
        this.type = type;
        this.style = style;
        this.info = info;
        this.cost = cost;
        this.slots = slots;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStyle getStyle() {
        return style;
    }

    public void setStyle(OrderStyle style) {
        this.style = style;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }
}
