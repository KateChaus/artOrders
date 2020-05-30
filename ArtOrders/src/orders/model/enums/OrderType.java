package orders.model.enums;

public enum OrderType {
    FREE("Свободный"),
    DESCRIPTION ("По описанию");

   private String type;

    OrderType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}