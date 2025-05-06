package io.github.vsinkievic.mockwallesterapi.domain.enumeration;

public enum CardBlockType {
    BlockedByCardUser,
    BlockedByCardholder,
    BlockedByCardholderViaPhone,
    BlockedByClient,
    BlockedByIssuer,
    Counterfeit,
    Fraudulent,
    Frozen,
    Lost,
    MaxInvalidTriesCVV2,
    MaxInvalidTriesPIN,
    NotDelivered,
    Stolen,
}
