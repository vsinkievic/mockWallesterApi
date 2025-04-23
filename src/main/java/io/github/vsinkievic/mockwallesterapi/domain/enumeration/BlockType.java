package io.github.vsinkievic.mockwallesterapi.domain.enumeration;

/**
 * The BlockType enumeration.
 */
public enum BlockType {
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
