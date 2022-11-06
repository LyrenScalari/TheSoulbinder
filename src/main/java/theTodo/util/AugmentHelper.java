package theTodo.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import theTodo.SoulbinderMod;
import theTodo.TheSoulbinder;

public class AugmentHelper {
    public static void register() {
        new AutoAdd(SoulbinderMod.modID)
                .packageFilter("theTodo.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment);});
        CardAugmentsMod.registerOrbCharacter(TheSoulbinder.Enums.THE_SOULBINDER);
    }
}