package pl.polishstation.polishstationbackend.externalapis.polishinfrastructureapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Bool;
import lombok.Data;

@Data
public class InfrastructureFuelStationDTO {
    public Long dkn;
    @JsonProperty("nazwa")
    public String name;
    public String nip;

    @JsonProperty("regon")
    public String region;

    @JsonProperty("adres")
    public String address;
    @JsonProperty("kod")
    public String code;

    @JsonProperty("miejscowosc")
    public String town;

    @JsonProperty("poczta")
    public String postOfficeName;

    @JsonProperty("wojewodztwo")
    public String regionName;

    @JsonProperty("nazwaStacji")
    public String fuelStationName;

    @JsonProperty("liczbaZbiornikow")
    public Long countOfCases;

    @JsonProperty("lacznaPojemnosc")
    public Double amount;

    @JsonProperty("infraUlica")
    public String street;

    @JsonProperty("infraNrLokalu")
    public String localNumber;

    @JsonProperty("infraKod")
    public String addressCode;

    @JsonProperty("infraPoczta")
    public String postOfficeAddress;

    @JsonProperty("wspolrzedne")
    public String localization;
    @JsonProperty("benzynySilnikowe")
    public Boolean isPetrol;

    @JsonProperty("olejeNapedowe")
    public Boolean isDiesel;

    @JsonProperty("gazPlynnyLPG")
    public Boolean isLPG;

    @JsonProperty("pojemnikiZLOO")
    public Boolean isZLOOcase;

    @JsonProperty("olejOpalowy")
    public Boolean isFuelOil;
}
