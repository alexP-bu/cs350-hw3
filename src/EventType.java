public enum EventType {
    BIRTH("ARR"),
    NEXT("NEXT 1"),
    DEATH("DONE"),
    MONITOR("MONITOR");

    private String name;

    private EventType(String type){
        this.name = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
