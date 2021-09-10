package com.example.location_tracking;

import kotlin.text.UStringsKt;

public class LocationHelper {
    private double Longitude;
    private double Latitude;

    public LocationHelper(double longitude, double latitude){
        Longitude=longitude;
        Latitude=latitude;
//        CountryName=countryName;
//        AddressLine=addressLine;
//        Locality=locality;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

//    public String getAddressLine() {
//        return AddressLine;
//    }
//
//    public String getCountryName() {
//        return CountryName;
//    }
//
//    public String getLocality() {
//        return Locality;
//    }

//    public void setAddressLine(String addressLine) {
//        AddressLine = addressLine;
//    }
//
//    public void setCountryName(String countryName) {
//        CountryName = countryName;
//    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

//    public void setLocality(String locality) {
//        Locality = locality;
//    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
