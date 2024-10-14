package com.joshuadias.moneyplannerapi.domains.core.helpers;

import com.joshuadias.moneyplannerapi.domains.core.models.SpendingGoal;

public class SpendingGoalHelper {

    public static void setCustomParameters(SpendingGoal entity) {
        if (entity.getCategorySpendingGoalList() != null && !entity.getCategorySpendingGoalList().isEmpty()) {
            entity.getCategorySpendingGoalList().forEach(categorySpendingGoal -> {
                categorySpendingGoal.setSpendingGoal(entity);
                if (categorySpendingGoal.getIsPercentual() == null)
                    categorySpendingGoal.setIsPercentual(false);
            });
        }
    }
}
