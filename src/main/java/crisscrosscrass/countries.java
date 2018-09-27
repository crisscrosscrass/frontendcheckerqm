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
            "https://www.shopalike.nl/merchandise.html"),
    AT("https://www.ladenzeile.at/",
            "https://www.ladenzeile.at/impressum.html",
            "https://www.ladenzeile.at/nutzungserklaerung.html",
            "https://www.ladenzeile.at/marken.html",
            "https://www.ladenzeile.at/partner/shops",
            "https://www.ladenzeile.at/partner/info",
            "https://www.ladenzeile.at/affiliate-programm/",
            "https://www.ladenzeile.at/merchandise.html"),
    ES("https://www.shopalike.es/",
            "https://www.shopalike.es/aviso-legal.html",
            "https://www.shopalike.es/condiciones-de-uso.html",
            "https://www.shopalike.es/marcas.html",
            "https://www.shopalike.es/partner/shops",
            "https://www.shopalike.es/partner/info",
            "https://www.shopalike.es/programa-de-afiliacion/",
            "https://www.shopalike.es/merchandise.html"),
    BR("https://www.umsolugar.com.br/",
            "https://www.umsolugar.com.br/imprint.html",
            "https://www.umsolugar.com.br/termos-de-uso.html",
            "https://www.umsolugar.com.br/brands.html",
            "https://www.umsolugar.com.br/partner/shops",
            "https://www.umsolugar.com.br/partner/info",
            "https://www.umsolugar.com.br/programa-de-afiliacion/",
            "https://www.umsolugar.com.br/merchandise.html"),
    HU("https://www.shopalike.hu/",
            "https://www.shopalike.hu/ceginformaciok.html",
            "https://www.shopalike.hu/felhasznalasi-feltetelek.html",
            "https://www.shopalike.hu/markak.html",
            "https://www.shopalike.hu/partner/shops",
            "https://www.shopalike.hu/partner/info",
            "-",
            "https://www.shopalike.hu/merchandise.html"),
    PL("https://www.shopalike.pl/",
            "https://www.shopalike.pl/impressum.html",
            "https://www.shopalike.pl/regulamin-dla-uzytkownikow.html",
            "https://www.shopalike.pl/marki.html",
            "https://www.shopalike.pl/partner/shops",
            "https://www.shopalike.pl/partner/info",
            "https://www.shopalike.pl/affiliate-programm/",
            "https://www.shopalike.pl/merchandise.html"),
    SK("https://www.shopalike.sk/",
            "https://www.shopalike.sk/informacie-o-firme.html",
            "https://www.shopalike.sk/vseobecne-podmienky.html",
            "https://www.shopalike.sk/brands.html",
            "https://www.shopalike.sk/partner/shops",
            "https://www.shopalike.sk/partner/info",
            "https://www.shopalike.sk/affiliate-programm/",
            "https://www.shopalike.sk/merchandise.html"),
    CZ("https://www.shopalike.cz/",
            "https://www.shopalike.cz/o-spolecnosti.html",
            "https://www.shopalike.cz/obchodni-podminky.html",
            "https://www.shopalike.cz/znacky.html",
            "https://www.shopalike.cz/partner/shops",
            "https://www.shopalike.cz/partner/info",
            "https://www.shopalike.cz/affiliate-programm/",
            "https://www.shopalike.cz/merchandise.html"),
    FI("https://www.shopalike.fi/",
            "https://www.shopalike.fi/yrityksen-tiedot.html",
            "https://www.shopalike.fi/kayttoehdot.html",
            "https://www.shopalike.fi/tuotemerkit.html",
            "https://www.shopalike.fi/partner/shops",
            "https://www.shopalike.fi/partner/info",
            "https://www.shopalike.fi/affiliate-programm/",
            "https://www.shopalike.fi/merchandise.html");
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
