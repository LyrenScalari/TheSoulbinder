package theTodo;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theTodo.Icons.*;
import theTodo.Patches.SoulsPatches.SoulsField;
import theTodo.cards.AbstractEasyCard;
import theTodo.cards.SoulbinderCards.Tokens.CorpseSpider;
import theTodo.cards.SoulbinderCards.Tokens.FleshGolem;
import theTodo.cards.SoulbinderCards.Tokens.IcePhantom;
import theTodo.cards.SoulbinderCards.Tokens.Skeleton;
import theTodo.cards.cardvars.SecondDamage;
import theTodo.cards.cardvars.SecondMagicNumber;
import theTodo.relics.AbstractEasyRelic;
import theTodo.util.AugmentHelper;
import theTodo.util.SoulsCounter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

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
    private static int reloadmax;
    public static final String ENABLE_CHIMERA_CROSSOVER = "Enable Chimera Crossover";
    public static boolean enableChimeraCrossover = true;
    public static SpireConfig SoulbinderConfig;
    public static UIStrings uiStrings;
    @SpireEnum
    public static AbstractCard.CardTags Undead;
    @SpireEnum
    public static AbstractCard.CardTags SelfExile;
    public SoulbinderMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(TheSoulbinder.Enums.SOULBINDER_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
        Properties SoulbinderDefaults = new Properties();;
        SoulbinderDefaults.getProperty("Enable Chimera Crossover","TRUE");
        try {
            SoulbinderConfig = new SpireConfig("The Soulbinder", "DragonkinMod", SoulbinderDefaults);
        } catch (IOException e) {
            logger.error("SoulbinderMod SpireConfig initialization failed:");
            e.printStackTrace();
        }
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
    public static String makeOrbPath(String resourcePath) {
        return modID + "Resources/orbs/" + resourcePath;
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
    }
    public void receivePostInitialize() {
        soulsCounter = new SoulsCounter( ImageMaster.loadImage("soulbindermodResources/images/ui/Soul.png"));
        ModPanel settingsPanel = new ModPanel();
        float currentYposition = 740f;
        float spacingY = 55f;
        uiStrings = CardCrawlGame.languagePack.getUIString(modID + ":UIText");
        ModLabeledToggleButton enableChimeraCrossoverButton = new ModLabeledToggleButton(uiStrings.TEXT[1], 350.0f, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont,
                SoulbinderConfig.getBool(ENABLE_CHIMERA_CROSSOVER), settingsPanel, (label) -> {}, (button) -> {
            SoulbinderConfig.setBool(ENABLE_CHIMERA_CROSSOVER, button.enabled);
            enableChimeraCrossover = button.enabled;
            try {SoulbinderConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        settingsPanel.addUIElement(enableChimeraCrossoverButton);
        if (Loader.isModLoaded("CardAugments")) {
            AugmentHelper.register();
        }
        BaseMod.addSaveField(makeID("Souls"), new CustomSavable<Integer>() {
            @Override
            public Integer onSave() {
                return SoulsField.Souls.get(AbstractDungeon.player);
            }

            @Override
            public void onLoad(Integer souls) { SoulsField.Souls.set(AbstractDungeon.player, souls);
            reloadSouls = SoulsField.Souls.get(AbstractDungeon.player);
            }
        });
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
        CustomIconHelper.addCustomIcon(BlockIcon.get());
        CustomIconHelper.addCustomIcon(WeakIcon.get());
        CustomIconHelper.addCustomIcon(VulnerableIcon.get());
        CustomIconHelper.addCustomIcon(PoisonIcon.get());
        CustomIconHelper.addCustomIcon(VenomIcon.get());
        CustomIconHelper.addCustomIcon(FrostIcon.get());
        CustomIconHelper.addCustomIcon(RimeIcon.get());
        CustomIconHelper.addCustomIcon(FrostFeverIcon.get());
        CustomIconHelper.addCustomIcon(SyphonIcon.get());
        CustomIconHelper.addCustomIcon(LambdaIcon.get());
        CustomIconHelper.addCustomIcon(BloodcastIcon.get());
        CustomIconHelper.addCustomIcon(SoulBarrierIcon.get());
        CustomIconHelper.addCustomIcon(TempHPIcon.get());
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

        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/eng/UIstrings.json");

        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/eng/Orbstrings.json");
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
        reloadmax = SoulsField.MaxSouls.get(AbstractDungeon.player);
        SoulsField.Souls.set(AbstractDungeon.player, reloadSouls);
        SoulsField.MaxSouls.set(AbstractDungeon.player,reloadmax);
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        reloadSouls = SoulsField.Souls.get(AbstractDungeon.player);
        reloadmax = SoulsField.MaxSouls.get(AbstractDungeon.player);
    }
    public static AbstractCard getRandomUndead(){
        ArrayList<AbstractCard> Undead = new ArrayList<>();
        Undead.add(new Skeleton());
        Undead.add(new CorpseSpider());
        Undead.add(new FleshGolem());
        Undead.add(new IcePhantom());
        return Undead.get(AbstractDungeon.miscRng.random(Undead.size()-1));
    }
}
