package com.luizaprestes.frame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 @author luiza
 @version-implemented 0.0.1
 @since 12.19.2020
 */
@Getter
@AllArgsConstructor
public enum Region {

    AMSTERDAM("amsterdam", "Amsterdam"),
    FRANKFURT("frankfurt", "Frankfurt"),
    LONDON("london", "London"),
    SINGAPORE("singapore", "Singapore"),
    SYDNEY("sydney", "Sydney"),
    US_EAST("us-east", "US East"),
    US_WEST("us-west", "US West"),
    UNKNOWN("", "Unknown Region");

    private final String id;
    private final String name;

    public static Region getById(String regionId) {
        for (Region region : values()) {
            if (region.getId().equals(regionId)) {
                return region;
            }
        }
        return UNKNOWN;
    }

    public static Region getByName(String regionName) {
        for (Region region : values()) {
            if (region.getName().equals(regionName)) {
                return region;
            }
        }
        return UNKNOWN;
    }

}
