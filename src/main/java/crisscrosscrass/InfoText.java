package crisscrosscrass;

public enum InfoText {
    TextSearch("Text Search and Suggestions","This will be a introduction"),
    FilterText("Filer Buttons","This will be ..."),
    BrandShopKeyword("Brand / Shop Keyword","This will be ..."),
    GridPageURL("Grid Page URL","This will be ..."),
    GridPageURLWithWindows("Grid Page URL with Windows","This will be ..."),
    GridPageWitthFillIns("Grid Page URL with FillIns","This will be ...");
    private String HeaderMessage;
    private String MainMessage;
    InfoText(String headerMessage, String mainMessage) {
        this.HeaderMessage = headerMessage;
        this.MainMessage = mainMessage;
    }
    public String getHeaderMessage() {
        return HeaderMessage;
    }
    public String getMainMessage() {
        return MainMessage;
    }
}
