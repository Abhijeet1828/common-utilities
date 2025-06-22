package com.custom.common.utilities.convertors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonConversionUtilsTest {

	@Test
    void testConvertFromCommaSeparatedString_normalCase() {
        String input = "[1, 2, 3]";
        List<Long> result = CommonConversionUtils.convertFromCommaSeperatedStrings(input);
        assertEquals(List.of(1L, 2L, 3L), result);
    }

    @Test
    void testConvertFromCommaSeparatedString_singleValue() {
        String input = "[42]";
        List<Long> result = CommonConversionUtils.convertFromCommaSeperatedStrings(input);
        assertEquals(List.of(42L), result);
    }

    @Test
    void testConvertFromCommaSeparatedString_emptyString() {
        String input = "";
        List<Long> result = CommonConversionUtils.convertFromCommaSeperatedStrings(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertFromCommaSeparatedString_nullInput() {
        String input = null;
        List<Long> result = CommonConversionUtils.convertFromCommaSeperatedStrings(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void testConvertFromCommaSeparatedString_invalidFormat() {
        String input = "[1, two, 3]";
        assertThrows(NumberFormatException.class, () ->
            CommonConversionUtils.convertFromCommaSeperatedStrings(input)
        );
    }

    @Test
    void testConstructorIsPrivate() throws Exception {
        Constructor<CommonConversionUtils> constructor =
                CommonConversionUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("CommonConversionUtils class cannot be instantiated", exception.getCause().getMessage());
    }
	
}
