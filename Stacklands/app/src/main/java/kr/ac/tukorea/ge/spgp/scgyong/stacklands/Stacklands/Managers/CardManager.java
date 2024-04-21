package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BlackCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.CardPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.CoinCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.SilverCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.YellowCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.eCardPack;

public class CardManager {
    public CardManager() {
    }

    public void init(Resources res){

    }
}

class CardPackManager extends CardManager {
    protected final ArrayList<CardPack> cardpacks = new ArrayList<>();
    public CardPackManager() {
        super();
    }

    public void init(Resources res) {
        InitHumbleBeginnings(res);
        InitANewWorld(res);
        InitCuriousCuisine(res);
        InitExplorers(res);
        InitLogicAndReason(res);
        InitOrderAndStructure(res);
        InitReapAndSow(res);
        InitTheArmory(res);
        InitSeekingWisdom(res);
    }

    public Card GetCardPack(eCardPack cardName) {
        return cardpacks.get(cardName.ordinal());
    }

    private CardPack GetterMachine(Resources res, int imageID, int nCard, int buyPrice){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imageID);
        CardPack card = new CardPack(nCard, buyPrice);
        card.setBitmap(bitmap);
        return card;
    }

    private void InitANewWorld(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_a_new_world, 5, 0);
        card.PushCard(SilverCardManager.getInstance().GetWood(res));
        card.PushCard(BlackCardManager.getInstance().GetRock(res));
        card.PushCard(BlackCardManager.getInstance().GetBerryBush(res));
        card.PushCard(YellowCardManager.getInstance().GetVilliger(res));
        card.PushCard(GoldCardManager.getInstance().GetCoin(res));
        cardpacks.add(card);

    }

    private void InitCuriousCuisine(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_curious_cuisine, 3, 10);
        cardpacks.add(card);
    }

    private void InitExplorers(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_explorers, 3, 20);
        cardpacks.add(card);
    }

    private void InitHumbleBeginnings(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_humble_beginnnings, 3, 3);
        cardpacks.add(card);
    }
    private void InitLogicAndReason(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_logic_and_reason, 4, 15);
        cardpacks.add(card);
    }
    private void InitOrderAndStructure(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_order_and_structure, 4, 25);
        cardpacks.add(card);
    }

    private void InitReapAndSow(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_reap_and_sow, 4, 10);
        cardpacks.add(card);
    }
    private void InitSeekingWisdom(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_seeking_wisdom, 4, 4);
        cardpacks.add(card);
    }
    private void InitTheArmory(Resources res){
        CardPack card = GetterMachine(res, R.mipmap.pack_the_armory, 3, 15);
        cardpacks.add(card);
    }
}
public class SilverCardManager extends CardManager {

    private static SilverCardManager instance;
    public static SilverCardManager getInstance() {
        if (instance == null) {
            instance = new SilverCardManager();
        }
        return instance;
    }
    public SilverCardManager() {
    }

    private SilverCard GetterMachine(Resources res, int imageID, int price){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imageID);
        SilverCard card = new SilverCard(price);
        card.setBitmap(bitmap);
        return card;
    }
    public SilverCard GetStone(Resources res){
        return GetterMachine(res, R.mipmap.silver_stone, 1);
    }

    public SilverCard GetFlint(Resources res){
        return GetterMachine(res, R.mipmap.silver_flint, 2);
    }

    public SilverCard GetStick(Resources res){
        return GetterMachine(res, R.mipmap.silver_stick, 2);
    }

    public SilverCard GetWood(Resources res){
        return GetterMachine(res, R.mipmap.silver_wood, 1);
    }
}

class BlackCardManager extends CardManager {

    private static BlackCardManager instance;

    public static BlackCardManager getInstance() {
        if (instance == null) {
            instance = new BlackCardManager();
        }
        return instance;
    }

    public BlackCardManager() {
    }

    private BlackCard GetterMachine(Resources res, int imageID, int price){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imageID);
        BlackCard card = new BlackCard(price);
        card.setBitmap(bitmap);
        return card;
    }

    public BlackCard GetRock(Resources res){
        return GetterMachine(res, R.mipmap.black_rock, 0);
    }

    public BlackCard GetTree(Resources res){
        return GetterMachine(res, R.mipmap.black_tree, 0);
    }

    public BlackCard GetBerryBush(Resources res){
        return GetterMachine(res, R.mipmap.black_berry_bush, 1);
    }

    public BlackCard GetAppleTree(Resources res){
        return GetterMachine(res, R.mipmap.black_apple_tree, 0);
    }
}

class YellowCardManager extends CardManager {

    private static YellowCardManager instance;

    public static YellowCardManager getInstance() {
        if (instance == null) {
            instance = new YellowCardManager();
        }
        return instance;
    }

    public YellowCardManager() {
    }

    private YellowCard GetterMachine(Resources res, int imageID, int life){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imageID);
        YellowCard card = new YellowCard(life);
        card.setBitmap(bitmap);
        return card;
    }

    public YellowCard GetVilliger(Resources res) {
        return GetterMachine(res, R.mipmap.yellow_villager, 15);
    }
}

class GoldCardManager extends CardManager {

    private static GoldCardManager instance;

    public static GoldCardManager getInstance() {
        if (instance == null) {
            instance = new GoldCardManager();
        }
        return instance;
    }

    public GoldCardManager() {
    }

    private CoinCard GetterMachine(Resources res, int imageID){
        Bitmap bitmap = BitmapFactory.decodeResource(res, imageID);
        CoinCard card = new CoinCard();
        card.setBitmap(bitmap);
        return card;
    }
    public CoinCard GetCoin(Resources res){
        return GetterMachine(res, R.mipmap.gold_coin);
    }
}