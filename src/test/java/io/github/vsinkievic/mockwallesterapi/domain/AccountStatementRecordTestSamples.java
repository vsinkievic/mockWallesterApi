package io.github.vsinkievic.mockwallesterapi.domain;

import java.util.UUID;

public class AccountStatementRecordTestSamples {

    public static AccountStatementRecord getAccountStatementRecordSample1() {
        return new AccountStatementRecord()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .accountId(UUID.fromString("11d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .cardId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .merchantId("merchantId1")
            .terminalId("terminalId1")
            .merchantName("merchantName1")
            .merchantCity("merchantCity1")
            .description("description1")
            .originalAuthorizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4bb"))
            .markedForDisputeBy("markedForDisputeBy1")
            .responseCode("responseCode1")
            .subType("subType1")
            .enrichedMerchantName("enrichedMerchantName1")
            .enrichedMerchantUrl("enrichedMerchantUrl1")
            .enrichedMerchantDomain("enrichedMerchantDomain1")
            .enrichedMerchantTelephone("enrichedMerchantTelephone1")
            .enrichedMerchantIconUrl("enrichedMerchantIconUrl1");
    }

    public static AccountStatementRecord getAccountStatementRecordSample2() {
        return new AccountStatementRecord()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .accountId(UUID.fromString("11d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .cardId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .merchantId("merchantId2")
            .terminalId("terminalId2")
            .merchantName("merchantName2")
            .merchantCity("merchantCity2")
            .description("description2")
            .originalAuthorizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4cc"))
            .markedForDisputeBy("markedForDisputeBy2")
            .responseCode("responseCode2")
            .subType("subType2")
            .enrichedMerchantName("enrichedMerchantName2")
            .enrichedMerchantUrl("enrichedMerchantUrl2")
            .enrichedMerchantDomain("enrichedMerchantDomain2")
            .enrichedMerchantTelephone("enrichedMerchantTelephone2")
            .enrichedMerchantIconUrl("enrichedMerchantIconUrl2");
    }

    public static AccountStatementRecord getAccountStatementRecordRandomSampleGenerator() {
        return new AccountStatementRecord()
            .id(UUID.randomUUID())
            .accountId(UUID.randomUUID())
            .cardId(UUID.randomUUID())
            .merchantId(UUID.randomUUID().toString())
            .terminalId(UUID.randomUUID().toString())
            .merchantName(UUID.randomUUID().toString())
            .merchantCity(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .originalAuthorizationId(UUID.randomUUID())
            .markedForDisputeBy(UUID.randomUUID().toString())
            .responseCode(UUID.randomUUID().toString())
            .subType(UUID.randomUUID().toString())
            .enrichedMerchantName(UUID.randomUUID().toString())
            .enrichedMerchantUrl(UUID.randomUUID().toString())
            .enrichedMerchantDomain(UUID.randomUUID().toString())
            .enrichedMerchantTelephone(UUID.randomUUID().toString())
            .enrichedMerchantIconUrl(UUID.randomUUID().toString());
    }
}
