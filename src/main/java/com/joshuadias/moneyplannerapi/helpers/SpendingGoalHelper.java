package com.joshuadias.moneyplannerapi.helpers;

import com.joshuadias.moneyplannerapi.models.SpendingGoal;

public class SpendingGoalHelper {

    public static void setParametersForCreate(SpendingGoal entity) {
        if (entity.getCategorySpendingGoalList() != null && !entity.getCategorySpendingGoalList().isEmpty()) {
            entity.getCategorySpendingGoalList().forEach(categorySpendingGoal -> {
                categorySpendingGoal.setSpendingGoal(entity);
                categorySpendingGoal.setId(null);
                if (categorySpendingGoal.getIsPercentual() == null)
                    categorySpendingGoal.setIsPercentual(false);
            });
        }
    }
}
