package io.github.vsinkievic.mockwallesterapi.domain;

import java.util.UUID;

public class CompanyTestSamples {

    public static Company getCompanySample1() {
        return new Company()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .name("name1")
            .registrationNumber("registrationNumber1")
            .regAddress1("regAddress11")
            .regAddress2("regAddress21")
            .regAddressCity("regAddressCity1")
            .regAddressPostalCode("regAddressPostalCode1")
            .hqAddress1("hqAddress11")
            .hqAddress2("hqAddress21")
            .hqAddressCity("hqAddressCity1")
            .hqAddressPostalCode("hqAddressPostalCode1")
            .mobile("mobile1")
            .email("email1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1")
            .deletedBy("deletedBy1")
            .industryType("industryType1")
            .businessRelationshipPurpose("businessRelationshipPurpose1")
            .employeesQuantity("employeesQuantity1")
            .cardSpendingAmount("cardSpendingAmount1")
            .vatNumber("vatNumber1");
    }

    public static Company getCompanySample2() {
        return new Company()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .name("name2")
            .registrationNumber("registrationNumber2")
            .regAddress1("regAddress12")
            .regAddress2("regAddress22")
            .regAddressCity("regAddressCity2")
            .regAddressPostalCode("regAddressPostalCode2")
            .hqAddress1("hqAddress12")
            .hqAddress2("hqAddress22")
            .hqAddressCity("hqAddressCity2")
            .hqAddressPostalCode("hqAddressPostalCode2")
            .mobile("mobile2")
            .email("email2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2")
            .deletedBy("deletedBy2")
            .industryType("industryType2")
            .businessRelationshipPurpose("businessRelationshipPurpose2")
            .employeesQuantity("employeesQuantity2")
            .cardSpendingAmount("cardSpendingAmount2")
            .vatNumber("vatNumber2");
    }

    public static Company getCompanyRandomSampleGenerator() {
        return new Company()
            .id(UUID.randomUUID())
            .name(UUID.randomUUID().toString())
            .registrationNumber(UUID.randomUUID().toString())
            .regAddress1(UUID.randomUUID().toString())
            .regAddress2(UUID.randomUUID().toString())
            .regAddressCity(UUID.randomUUID().toString())
            .regAddressPostalCode(UUID.randomUUID().toString())
            .hqAddress1(UUID.randomUUID().toString())
            .hqAddress2(UUID.randomUUID().toString())
            .hqAddressCity(UUID.randomUUID().toString())
            .hqAddressPostalCode(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString())
            .deletedBy(UUID.randomUUID().toString())
            .industryType(UUID.randomUUID().toString())
            .businessRelationshipPurpose(UUID.randomUUID().toString())
            .employeesQuantity(UUID.randomUUID().toString())
            .cardSpendingAmount(UUID.randomUUID().toString())
            .vatNumber(UUID.randomUUID().toString());
    }
}
