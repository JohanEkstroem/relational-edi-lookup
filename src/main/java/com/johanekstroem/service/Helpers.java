package com.johanekstroem.service;

public class Helpers {
    public static AvailableDocTypes getDocTypeFromPeppolScheme(String value) {
        if (value == null)
            return AvailableDocTypes.UNKNOWN;
        if (value.contains("::Invoice"))
            return AvailableDocTypes.INVOIC;
        if (value.contains("::CreditNote"))
            return AvailableDocTypes.CREDITNOTE;
        if (value.contains("::OrderResponse"))
            return AvailableDocTypes.ORDERRESPONSE;
        if (value.contains("::Catalogue"))
            return AvailableDocTypes.CATALOGUE;
        if (value.contains("::Delfor"))
            return AvailableDocTypes.DELFOR;

        return AvailableDocTypes.UNKNOWN;

    }

    public static String getDescriptionFormTechnicalDoctype(AvailableDocTypes value) {
        switch (value) {
            case UNKNOWN:
                return "UNKNOWN";

            case INVOIC:
                return "Invoice message";

            case CREDITNOTE:
                return "Business credit report message";

            case ORDERRESPONSE:
                return "Purchase order message";

            case CATALOGUE:
                return "Price/sales catalogue message";

            default:
                break;
        }

        return AvailableDocTypes.UNKNOWN.toString();

    }
}
