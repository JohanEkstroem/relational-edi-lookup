package com.johanekstroem.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class HelpersTest {

    @Test
    void givenInvoiceStringShouldReturnDocTypeScheme() {
//        Arrange
        Helpers helper = new Helpers();

//        Act
        var result = helper.getDocTypeFromPeppolScheme("::Invoice");

//        Assert
        assertThat(result).isEqualTo(AvailableDocTypes.INVOIC);
    }

    @Test
    void givenJibberishStringShouldReturnUNKNOWNDocTypeScheme(){
//        Arrange
        Helpers helper = new Helpers();

//        Act
        var result = helper.getDocTypeFromPeppolScheme("Jibberish");

//        Assert
        assertThat(result).isEqualTo(AvailableDocTypes.UNKNOWN);
    }

    @Test
    void givenCreditNoteStringShouldReturnCREDITNOTEDocTypeScheme(){
//        Arrange
        Helpers helper = new Helpers();

//        Act
        var result = helper.getDocTypeFromPeppolScheme("::CreditNote");

//        Assert
        assertThat(result).isEqualTo(AvailableDocTypes.CREDITNOTE);
    }

}