package crisscrosscrass;

public enum countries{
    DE("https://www.ladenzeile.de/",
            "https://www.ladenzeile.de/impressum.html",
            "https://www.ladenzeile.de/nutzungserklaerung.html",
            "https://www.ladenzeile.de/marken.html",
            "https://www.ladenzeile.de/partner/shops",
            "https://www.ladenzeile.de/partner/info",
            "https://www.ladenzeile.de/affiliate-programm/",
            "https://www.ladenzeile.de/merchandise.html"),
    FR("https://www.shopalike.fr/",
            "https://www.shopalike.fr/mentions-legales.html",
            "https://www.shopalike.fr/condition-d-utilisation.html",
            "https://www.shopalike.fr/marques.html",
            "https://www.shopalike.fr/partner/shops",
            "https://www.shopalike.fr/partner/info",
            "https://www.shopalike.fr/programme-daffiliation/",
            "https://www.shopalike.fr/merchandise.html"),
    IT("https://www.shopalike.it/",
            "https://www.shopalike.it/informazioni-legali.html",
            "https://www.shopalike.it/termini-e-condizioni-di-utilizzo.html",
            "https://www.shopalike.it/marche.html",
            "https://www.shopalike.it/partner/shops",
            "https://www.shopalike.it/partner/info",
            "https://www.shopalike.it/programma-di-affiliazione/",
            "https://www.shopalike.it/merchandise.html"),
    NL("https://www.shopalike.nl/",
            "https://www.shopalike.nl/impressum.html",
            "https://www.shopalike.nl/gebruiksvoorwaarden.html",
            "https://www.shopalike.nl/merken.html",
            "https://www.shopalike.nl/partner/shops",
            "https://www.shopalike.nl/partner/info",
            "https://www.shopalike.nl/affiliateprogramma/",
            "https://www.shopalike.nl/merchandise.html");
    private final String locationMainPage;
    private final String locationImprintPage;
    private final String locationPrivacyPage;
    private final String locationBrandOverviewPage;
    private final String locationPartnershopsPageURL;
    private final String locationBecomePartnerPageURL;
    private final String locationAffiliateProgramPageURL;
    private final String locationMerchandiseOverviewPageURL;
    countries(String mainLocation,
              String imprintLocation,
              String locationPrivacyPage,
              String locationBrandOverviewPage,
              String locationPartnershopsPageURL,
              String locationBecomePartnerPageURL,
              String locationAffiliateProgramPageURL,
              String locationMerchandiseOverviewPageURL) {
        this.locationMainPage = mainLocation;
        this.locationImprintPage = imprintLocation;
        this.locationPrivacyPage = locationPrivacyPage;
        this.locationBrandOverviewPage = locationBrandOverviewPage;
        this.locationPartnershopsPageURL = locationPartnershopsPageURL;
        this.locationBecomePartnerPageURL = locationBecomePartnerPageURL;
        this.locationAffiliateProgramPageURL = locationAffiliateProgramPageURL;
        this.locationMerchandiseOverviewPageURL = locationMerchandiseOverviewPageURL;
    }
    public String getLocationMainPage(){return locationMainPage;}
    public String getlocationImprintPage(){return locationImprintPage;}
    public String getPrivacyPage(){return locationPrivacyPage;}
    public String getLocationBrandOverviewPage(){return locationBrandOverviewPage;}
    public String getLocationPartnershopsPageURL(){return locationPartnershopsPageURL;}
    public String getLocationBecomePartnerPageURL(){return locationBecomePartnerPageURL;}
    public String getLocationAffiliateProgramPageURL(){return locationAffiliateProgramPageURL;}
    public String getLocationMerchandiseOverviewPageURL() {return locationMerchandiseOverviewPageURL;}
}
