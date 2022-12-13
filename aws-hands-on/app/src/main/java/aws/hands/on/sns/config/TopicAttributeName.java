package aws.hands.on.sns.config;

public enum TopicAttributeName {
    DISPLAY_NAME("DisplayName"),
    DELIVERY_POLICY("DeliveryPolicy");

    private final String attributeName;

    TopicAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
