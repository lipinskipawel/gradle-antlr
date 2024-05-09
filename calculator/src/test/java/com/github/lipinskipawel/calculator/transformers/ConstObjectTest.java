package com.github.lipinskipawel.calculator.transformers;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.github.lipinskipawel.calculator.transformers.ConstObject.constObject;

@DisplayName("ConstObject spec")
class ConstObjectTest implements WithAssertions {

    @Nested
    @DisplayName("object with empty reference (1 level deep)")
    class OneLevelDeep {

        @Test
        @DisplayName("returns value when contains name")
        void returns_value_when_contains_name() {
            final var constObject = constObject("foo", 4);

            final var result = constObject.findValue("foo");

            assertThat(result)
                    .isPresent()
                    .contains(4);
        }

        @Test
        @DisplayName("returns empty when does not contains name")
        void returns_empty_when_does_not_contains_name() {
            final var constObject = constObject("foo", 4);

            final var result = constObject.findValue("bar");

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("returns empty when search is too deep")
        void returns_empty_when_search_is_too_deep_2() {
            final var constObject = constObject("foo", 4);

            final var result = constObject.findValue("foo.bar");

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("object with reference (2 level deep)")
    class TwoLevelsDeep {

        @Test
        @DisplayName("returns value when contains name")
        void returns_value_when_contains_name() {
            final var constObject = constObject("foo", 7, constObject("bar", 8));

            final var result = constObject.findValue("foo.bar");

            assertThat(result)
                    .isPresent()
                    .contains(8);
        }

        @Test
        @DisplayName("returns empty when non of the names exists")
        void returns_empty_when_does_not_contains_name() {
            final var constObject = constObject("foo", 4, constObject("bar", 1));

            final var result = constObject.findValue("foo.baz");

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("returns empty when search is too deep")
        void returns_empty_when_search_is_too_deep_3() {
            final var constObject = constObject("foo.bar", 4, constObject("bar", 7));

            final var result = constObject.findValue("foo.bar.baz");

            assertThat(result).isEmpty();
        }
    }
}