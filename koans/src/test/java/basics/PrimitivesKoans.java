package basics;

import io.fries.koans.Koan;
import org.junit.jupiter.api.Test;

import static io.fries.koans.KoanAssert.__;
import static io.fries.koans.KoanAssert.assertThat;
import static io.fries.koans.basics.Primitives.typeOf;
import static org.assertj.core.api.Assertions.fail;

@SuppressWarnings("all")
class PrimitivesKoans {

    @Koan
    void whole_numbers_are_of_type_int() {
        int variableName = 1;

       assertThat(typeOf(1)).isEqualTo(int.class);
    }

    @Koan
    void primitives_of_type_int_have_an_object_type_integer() {
        Object number = 1;

       assertThat(typeOf(number)).isEqualTo(Integer.class);
    }

    @Koan
    void integers_have_a_fairly_large_range() {
        assertThat(Integer.MIN_VALUE).isEqualTo(-2147483648);
        assertThat(Integer.MAX_VALUE).isEqualTo(2147483647);
    }

    @Koan
    void integer_size_in_bits() {
        assertThat(Integer.SIZE).isEqualTo(32);
    }

    @Koan
    void whole_numbers_can_also_be_of_type_long() {
        assertThat(typeOf(1L)).isEqualTo(long.class);
    }

    @Koan
    void primitives_of_type_long_have_an_object_type_long() {
        Object number = 1L;
        assertThat(typeOf(number)).isEqualTo(Long.class);
    }

    @Koan
    void longs_have_a_larger_range_than_ints() {
        assertThat(Long.MIN_VALUE).isEqualTo(-9223372036854775808l);
        assertThat(Long.MAX_VALUE).isEqualTo(9223372036854775807l);
    }

    @Koan
    void long_size_in_bits() {
        assertThat(Long.SIZE).isEqualTo(64);
    }

    @Koan
    void whole_numbers_can_also_be_of_type_short() {
        assertThat(typeOf((short) 1)).isEqualTo(short.class);
    }

    @Koan
    void primitives_of_type_short_have_an_object_type_short() {
        Object number = (short) 1;
        assertThat(typeOf(number)).isEqualTo(Short.class);
    }

    @Koan
    void shorts_have_a_smaller_range_than_ints() {

        assertThat(Short.MIN_VALUE).isEqualTo(-32768);
        assertThat(Short.MAX_VALUE).isEqualTo(32767);
    }

    @Koan
    void short_size_in_bits() {
        assertThat(Short.SIZE).isEqualTo(16);
    }

    @Koan
    void whole_numbers_can_also_be_of_type_byte() {
        assertThat(typeOf((byte) 1)).isEqualTo(byte.class);
    }

    @Koan
    void primitives_of_type_byte_have_an_object_type_byte() {
        Object number = (byte) 1;
        assertThat(typeOf(number)).isEqualTo(Byte.class);
    }

    @Koan
    void bytes_have_a_smaller_range_than_shorts() {
        assertThat(Byte.MIN_VALUE).isEqualTo(-128);
        assertThat(Byte.MAX_VALUE).isEqualTo(127);
    }

    @Koan
    void byte_size_in_bits() {
        assertThat(Byte.SIZE).isEqualTo(__);
    }

    @Koan
    void whole_numbers_can_also_be_of_type_char() {
        assertThat(typeOf((char) 1)).isEqualTo(__);
    }

    @Koan
    void single_characters_are_of_type_char() {
        assertThat(typeOf('a')).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_char_have_an_object_type_character() {
        Object number = (char) 1;
        assertThat(typeOf(number)).isEqualTo(__);
    }

    @Koan
    void chars_can_only_be_positive() {
        assertThat((int) Character.MIN_VALUE).isEqualTo(__);
        assertThat((int) Character.MAX_VALUE).isEqualTo(__);
    }

    @Koan
    void char_size_in_bits() {
        assertThat(Character.SIZE).isEqualTo(__);
    }

    @Koan
    void decimal_numbers_are_of_type_double() {
        assertThat(typeOf(1.0)).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_double_can_be_declared_without_the_decimal_point() {
        assertThat(typeOf(1d)).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_double_can_be_declared_with_exponents() {
        assertThat(typeOf(1e3)).isEqualTo(__);
        assertThat(1.0e3).isEqualTo(__);
        assertThat(1E3).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_double_have_an_object_type_double() {
        Object number = 1.0;
        assertThat(typeOf(number)).isEqualTo(__);
    }

    @Koan
    void doubles_have_a_large_range() {
        assertThat(Double.MIN_VALUE).isEqualTo(__);
        assertThat(Double.MAX_VALUE).isEqualTo(__);
    }

    @Koan
    void double_size_in_bits() {
        assertThat(Double.SIZE).isEqualTo(__);
    }

    @Koan
    void decimal_numbers_can_also_be_of_type_float() {
        assertThat(typeOf(1f)).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_float_can_be_declared_with_exponents() {
        assertThat(typeOf(1e3f)).isEqualTo(__);
        assertThat(1.0e3f).isEqualTo(__);
        assertThat(1E3f).isEqualTo(__);
    }

    @Koan
    void primitives_of_type_float_have_an_object_type_float() {
        Object number = 1f;
        assertThat(typeOf(number)).isEqualTo(__);
    }

    @Koan
    void floats_have_a_smaller_range_than_doubles() {
        assertThat(Float.MIN_VALUE).isEqualTo(__);
        assertThat(Float.MAX_VALUE).isEqualTo(__);
    }

    @Koan
    void float_size() {
        assertThat(Float.SIZE).isEqualTo(__);
    }

    @Koan
    void autoboxing_of_an_int_into_an_integer() {
        Integer i = 3;
        assertThat(i).isEqualTo(__);
    }

    @Koan
    void unboxing_of_an_integer_to_an_int() {
        int i = Integer.valueOf(5);
        assertThat(i).isEqualTo(__);
    }

    @Test
    void autoboxing_and_unboxing_are_applied_to_every_primitive_type() {
        char unboxedChar = Character.valueOf('a');
        Character autoboxedChar = 'a';
        assertThat(unboxedChar).isEqualTo(autoboxedChar);

        byte unboxedByte = Byte.valueOf((byte) 1);
        Byte autoboxedByte = (byte) 1;
        assertThat(unboxedByte).isEqualTo(autoboxedByte);

        short unboxedShort = Short.valueOf((short) 1);
        Short autoboxedShort = (short) 1;
        assertThat(unboxedShort).isEqualTo(autoboxedShort);

        long unboxedLong = Long.valueOf(1L);
        Long autoboxedLong = 1L;
        assertThat(unboxedLong).isEqualTo(autoboxedLong);

        float unboxedFloat = Float.valueOf(1.F);
        Float autoboxedFloat = 1.F;
        assertThat(unboxedFloat).isEqualTo(autoboxedFloat);

        double unboxedDouble = Double.valueOf(1.);
        Double autoboxedDouble = 1.;
        assertThat(unboxedDouble).isEqualTo(autoboxedDouble);

        fail("Remove this filthy line when you've mastered the concepts of autoboxing and unboxing.");
    }
}
