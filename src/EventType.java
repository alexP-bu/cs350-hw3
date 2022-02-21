public enum EventType {
    BIRTH("ARR"), DEATH("DONE"), MONITOR("MONITOR");

    private String name;

    private EventType(String type){
        this.name = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
