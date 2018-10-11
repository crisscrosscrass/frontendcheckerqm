package crisscrosscrass;

public enum InfoText {
    TextSearch("HP: text search + Suggestions","Enter keywords that will be used to test our search functionality and the suggestions.\n" +
            " If you enter more than one keyword, use pipe as separator\n"),
    GridPageURL("Grid Page URL with Windows","This field will be used to test a gridpage with Windows\n"),
    GridPageURLWithWindows("Grid Page URL with Windows","This field will be used to test a gridpage witthout Windows\n"),
    BrandShopKeyword("Brand | Shop Keyword","Enter keywords that will be used to test Brand and Shop search boxes in the rule.\n"),
    FilterText("Filer Buttons","This field will be used to test a gridpage with Windows\n"),
    GridPageWitthFillIns("Grid Page URL with FillIns","This field will be used to test a gridpage with Fill-ins\n"),
    LucenePageSearch("Keyword trigger Lucene Page","This keyword will be used to trigger a Lucene page and perform all the Lucene tests. \n");
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
