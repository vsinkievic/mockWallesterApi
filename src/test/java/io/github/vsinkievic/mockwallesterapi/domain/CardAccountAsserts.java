package io.github.vsinkievic.mockwallesterapi.domain;

import static io.github.vsinkievic.mockwallesterapi.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class CardAccountAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCardAccountAllPropertiesEquals(CardAccount expected, CardAccount actual) {
        assertCardAccountAutoGeneratedPropertiesEquals(expected, actual);
        assertCardAccountAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCardAccountAllUpdatablePropertiesEquals(CardAccount expected, CardAccount actual) {
        assertCardAccountUpdatableFieldsEquals(expected, actual);
        assertCardAccountUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCardAccountAutoGeneratedPropertiesEquals(CardAccount expected, CardAccount actual) {
        assertThat(actual)
            .as("Verify CardAccount auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCardAccountUpdatableFieldsEquals(CardAccount expected, CardAccount actual) {
        assertThat(actual)
            .as("Verify CardAccount relevant properties")
            .satisfies(a -> assertThat(a.getExternalId()).as("check externalId").isEqualTo(expected.getExternalId()))
            .satisfies(a -> assertThat(a.getCurrencyCode()).as("check currencyCode").isEqualTo(expected.getCurrencyCode()))
            .satisfies(a ->
                assertThat(a.getBalance()).as("check balance").usingComparator(bigDecimalCompareTo).isEqualTo(expected.getBalance())
            )
            .satisfies(a ->
                assertThat(a.getAvailableAmount())
                    .as("check availableAmount")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(expected.getAvailableAmount())
            )
            .satisfies(a ->
                assertThat(a.getBlockedAmount())
                    .as("check blockedAmount")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(expected.getBlockedAmount())
            )
            .satisfies(a -> assertThat(a.getStatus()).as("check status").isEqualTo(expected.getStatus()))
            .satisfies(a -> assertThat(a.getCreatedAt()).as("check createdAt").isEqualTo(expected.getCreatedAt()));
        //            .satisfies(a -> assertThat(a.getUpdatedAt()).as("check updatedAt").isEqualTo(expected.getUpdatedAt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCardAccountUpdatableRelationshipsEquals(CardAccount expected, CardAccount actual) {
        // empty method
    }
}
