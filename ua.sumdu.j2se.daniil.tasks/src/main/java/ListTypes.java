public enum ListTypes {
    ARRAY("array"), LINKED("linked");

    private final  String type;

    ListTypes(String title) {
        this.type = title;
    }

    public String getType() {
        return type;
    }
}
