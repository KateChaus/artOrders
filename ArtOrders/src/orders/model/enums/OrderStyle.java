package orders.model.enums;

public enum OrderStyle {

        DIGITAL ("Цифровой"),
        TRADITIONAL ("Традиционный"),
        THREE_DIMENSIONAL ("3D"),
        ANIMATION ("Анимация"),
        OTHER ("Другое");

        private String style;

        OrderStyle(String style) {
                this.style = style;
        }

        public String toString() {
                return style;
        }

}
