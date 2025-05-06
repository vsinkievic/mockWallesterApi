package io.github.vsinkievic.mockwallesterapi.wallestermodel;

import lombok.Data;

@Data
public class WallesterCardResponse {

    private WallesterCard card;

    public WallesterCardResponse(WallesterCard card) {
        this.card = card;
    }
}
