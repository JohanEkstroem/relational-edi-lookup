package com.johanekstroem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

class HelpersTest {


        @ParameterizedTest(name = "given a {0} should give DocTypeScheme")
        @MethodSource("getIdentifier")
        void givenDocTypeShouldReturnShouldReturnCorrectDocTypeScheme(String identifier,
                        AvailableDocTypes expectedResult) {
                // Arrange
                Helpers helper = new Helpers();
                // Act
                var result = helper.getDocTypeFromPeppolScheme(identifier);

                // Assert
                assertEquals(expectedResult, result);
        }

        private static Stream<Arguments> getIdentifier() {
                return Stream.of(
                                Arguments.of("UNKNOWN", AvailableDocTypes.UNKNOWN),
                                Arguments.of("Jibberish", AvailableDocTypes.UNKNOWN),
                                Arguments.of("::Invoice", AvailableDocTypes.INVOIC),
                                Arguments.of("::CreditNote", AvailableDocTypes.CREDITNOTE),
                                Arguments.of("::Delfor", AvailableDocTypes.DELFOR),
                                Arguments.of("::OrderResponse", AvailableDocTypes.ORDERRESPONSE),
                                Arguments.of("::Catalogue", AvailableDocTypes.CATALOGUE));
        }

}