package io.github.vsinkievic.mockwallesterapi.domain;

import java.util.UUID;

public class AccountStatementRecordTestSamples {

    public static AccountStatementRecord getAccountStatementRecordSample1() {
        return new AccountStatementRecord()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .cardId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .merchantId("merchantId1")
            .terminalId("terminalId1")
            .merchantName("merchantName1")
            .merchantCity("merchantCity1")
            .description("description1")
            .originalAuthorizationId("originalAuthorizationId1")
            .markedForDisputeBy("markedForDisputeBy1")
            .responseCode("responseCode1")
            .accountExternalId("accountExternalId1")
            .maskedCardNumber("maskedCardNumber1")
            .cardName("cardName1")
            .embossingName("embossingName1")
            .embossingFirstName("embossingFirstName1")
            .embossingLastName("embossingLastName1")
            .embossingCompanyName("embossingCompanyName1")
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
            .cardId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .merchantId("merchantId2")
            .terminalId("terminalId2")
            .merchantName("merchantName2")
            .merchantCity("merchantCity2")
            .description("description2")
            .originalAuthorizationId("originalAuthorizationId2")
            .markedForDisputeBy("markedForDisputeBy2")
            .responseCode("responseCode2")
            .accountExternalId("accountExternalId2")
            .maskedCardNumber("maskedCardNumber2")
            .cardName("cardName2")
            .embossingName("embossingName2")
            .embossingFirstName("embossingFirstName2")
            .embossingLastName("embossingLastName2")
            .embossingCompanyName("embossingCompanyName2")
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
            .cardId(UUID.randomUUID())
            .merchantId(UUID.randomUUID().toString())
            .terminalId(UUID.randomUUID().toString())
            .merchantName(UUID.randomUUID().toString())
            .merchantCity(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .originalAuthorizationId(UUID.randomUUID().toString())
            .markedForDisputeBy(UUID.randomUUID().toString())
            .responseCode(UUID.randomUUID().toString())
            .accountExternalId(UUID.randomUUID().toString())
            .maskedCardNumber(UUID.randomUUID().toString())
            .cardName(UUID.randomUUID().toString())
            .embossingName(UUID.randomUUID().toString())
            .embossingFirstName(UUID.randomUUID().toString())
            .embossingLastName(UUID.randomUUID().toString())
            .embossingCompanyName(UUID.randomUUID().toString())
            .subType(UUID.randomUUID().toString())
            .enrichedMerchantName(UUID.randomUUID().toString())
            .enrichedMerchantUrl(UUID.randomUUID().toString())
            .enrichedMerchantDomain(UUID.randomUUID().toString())
            .enrichedMerchantTelephone(UUID.randomUUID().toString())
            .enrichedMerchantIconUrl(UUID.randomUUID().toString());
    }
}
