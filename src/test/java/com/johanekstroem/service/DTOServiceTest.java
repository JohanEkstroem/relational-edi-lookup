package com.johanekstroem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

public class DTOServiceTest {


    @Test
    void givenUnknownIdentifierShouldReturnNull() {
        // Arrange
        DTOService service = new DTOService();

        // Act
        String result = service.getIdentifier("Jibberish");

        // Assert
        assertThat(result).isNull();

    }

    @ParameterizedTest(name = "given a {0} should give Identifier value or Null")
    @MethodSource("getIdentifier")
    void givenStringShouldReturnIdentifierValueOrNull(String identifier, String expectedResult) {
        // Arrange
        DTOService service = new DTOService();

        // Act
        var result = service.getIdentifier(identifier);

        // Assert
        assertEquals(expectedResult, result);
    }
    private static Stream<Arguments> getIdentifier(){
        return Stream.of(
            Arguments.of("UNKNOWN", "UNKNOWN"),
            Arguments.of("NAME", "NAME"),
            Arguments.of("ORGNO", "ORGNO"),
            Arguments.of("GLN", "GLN"),
            Arguments.of("PEPPOLID", "PEPPOLID")
        );
    }

}
