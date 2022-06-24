package theTodo;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theTodo.Icons.*;
import theTodo.Patches.SoulsField;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.cardvars.SecondDamage;
import theTodo.cards.cardvars.SecondMagicNumber;
import theTodo.relics.AbstractEasyRelic;
import theTodo.util.SoulsCounter;

import javax.swing.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class SoulbinderMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnStartBattleSubscriber,
PostBattleSubscriber{

    public static final String modID = "soulbindermod"; //TODO: Change this.

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    // This makes debugging so much easier
    public static Logger logger = LogManager.getLogger(SoulbinderMod.class.getName());

    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This should be changed eventually
    private static boolean renderSoulsCounter = false;
    public static SoulsCounter soulsCounter;
    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/attack.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/skill.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/power.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/attack.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/skill.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/power.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";
    private static int reloadSouls;

    public SoulbinderMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(TheSoulbinder.Enums.SOULBINDER_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        SoulbinderMod thismod = new SoulbinderMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheSoulbinder(TheSoulbinder.characterStrings.NAMES[1], TheSoulbinder.Enums.THE_SOULBINDER),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheSoulbinder.Enums.THE_SOULBINDER);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
        CustomIconHelper.addCustomIcon(BlockIcon.get());
        CustomIconHelper.addCustomIcon(WeakIcon.get());
        CustomIconHelper.addCustomIcon(VulnerableIcon.get());
        CustomIconHelper.addCustomIcon(PoisonIcon.get());
        CustomIconHelper.addCustomIcon(SyphonIcon.get());
    }
    public void receivePostInitialize() {
        soulsCounter = new SoulsCounter( ImageMaster.loadImage("soulbindermodResources/images/ui/Soul.png"));
    }
    public static boolean getSoulsRender(){
        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (renderSoulsCounter) {
                if (soulsCounter == null) {
                    soulsCounter = new SoulsCounter(ImageMaster.loadImage("soulbindermodResources/images/ui/Soul.png"));
                }
                return true;
            } else if (SoulsField.Souls.get(AbstractDungeon.player) > 0 || AbstractDungeon.player.chosenClass == TheSoulbinder.Enums.THE_SOULBINDER) {
                renderSoulsCounter = true;
                if (soulsCounter == null) {
                    soulsCounter = new SoulsCounter(ImageMaster.loadImage("soulbindermodResources/images/ui/Soul.png"));
                }
                return true;
            }
        }
        return false;
    }

    public static void renderSoulsCounter(SpriteBatch sb, float current_x){
        soulsCounter.render(sb, current_x);
    }

    public static void updateSoulsCounter()
    {
        soulsCounter.update();
    }
    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/eng/Charstrings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/eng/Powerstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        reloadSouls = SoulsField.Souls.get(AbstractDungeon.player);
        SoulsField.Souls.set(AbstractDungeon.player,reloadSouls);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        reloadSouls = SoulsField.Souls.get(AbstractDungeon.player);
    }
}
