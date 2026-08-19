package core.driver;

public enum BrowserType {

    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType fromString(String value) {
        if(value == null || value.isBlank()) return BrowserType.CHROME;

        try{
            return BrowserType.valueOf(value.trim().toUpperCase());
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException(
                    String.format("Unsupported browser: '%s'.  Supported values: chrome, firefox, edge", value));
        }
    }
}
