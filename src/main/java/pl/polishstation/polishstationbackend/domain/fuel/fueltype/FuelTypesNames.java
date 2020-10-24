package pl.polishstation.polishstationbackend.domain.fuel.fueltype;

import java.util.List;

public class FuelTypesNames {
    public static String PETROL = "95";
    public static String PETROL2 = "98";
    public static String DIESEL = "ON";
    public static String LPG = "LPG";
    public static String ZLOOCASE = "ZLOOCASE";
    public static String FUELOIL = "FUELOIL";
    public static final List<String> FUEL_TYPES_LIST  = List.of(PETROL, PETROL2, DIESEL, LPG, ZLOOCASE, FUELOIL);
    public static final List<String> DEFAULT_FUEL_TYPES_LIST = List.of(PETROL, PETROL2, DIESEL, LPG);
}
