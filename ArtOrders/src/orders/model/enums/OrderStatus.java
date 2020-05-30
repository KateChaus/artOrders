package orders.model.enums;

public enum OrderStatus {
        APPLIED("Не подтверждён"),
        PROCESS("В процессе"),
        FINISHED("Завершён");

        private String status;

        OrderStatus(String status) {
                this.status = status;
        }

        public String toString() {
                return status;
        }
}
