package io.github.vsinkievic.mockwallesterapi.domain;

import java.util.UUID;

public class CardAccountTestSamples {

    public static CardAccount getCardAccountSample1() {
        return new CardAccount().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).externalId("externalId1");
    }

    public static CardAccount getCardAccountSample2() {
        return new CardAccount().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).externalId("externalId2");
    }

    public static CardAccount getCardAccountRandomSampleGenerator() {
        return new CardAccount().id(UUID.randomUUID()).externalId(UUID.randomUUID().toString());
    }
}
