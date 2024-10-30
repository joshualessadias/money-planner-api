package com.joshuadias.moneyplannerapi.domains.evolution.helpers;

public class ParticipantHelper {

    private ParticipantHelper() {
    }

    public static String getPhoneNumber(String participant) {
        if (participant == null || !participant.contains("@")) return null;
        return participant.substring(0, participant.indexOf('@'));
    }
}
