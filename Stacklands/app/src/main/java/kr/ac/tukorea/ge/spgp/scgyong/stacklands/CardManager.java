package kr.ac.tukorea.ge.spgp.scgyong.stacklands;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

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

    private void InitANewWorld(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_a_new_world);
        CardPack.setBitmap(bitmap);
        CardPack.PushCard(SilverCardManager.getInstance().GetStone(res)); // 여기서 추가하면 될듯
        cardpacks.add(new CardPack(5, 0));

    }

    private void InitCuriousCuisine(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_curious_cuisine);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(3, 10));
    }

    private void InitExplorers(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_explorers);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(3, 20));
    }

    private void InitHumbleBeginnings(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_humble_beginnnings);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(3, 3));
    }
    private void InitLogicAndReason(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_logic_and_reason);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(4, 15));
    }
    private void InitOrderAndStructure(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_order_and_structure);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(4, 25));
    }

    private void InitReapAndSow(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_reap_and_sow);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(4, 10));
    }
    private void InitSeekingWisdom(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_seeking_wisdom);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(4, 4));
    }
    private void InitTheArmory(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.pack_the_armory);
        Card.setBitmap(bitmap);
        cardpacks.add(new CardPack(3, 15));
    }
}
class SilverCardManager extends CardManager {

    private static SilverCardManager instance;
    public static SilverCardManager getInstance() {
        if (instance == null) {
            instance = new SilverCardManager();
        }
        return instance;
    }
    public SilverCardManager() {
    }

    public Card GetStone(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.silver_stone);
        Card.setBitmap(bitmap);
        return new NotAlive(1);
    }

    public Card GetFlint(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.silver_flint);
        Card.setBitmap(bitmap);
        return new NotAlive(2);
    }

    public Card GetStick(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.silver_stick);
        Card.setBitmap(bitmap);
        return new NotAlive(2);
    }

    public Card GetWood(Resources res){
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.mipmap.silver_wood);
        Card.setBitmap(bitmap);
        return new NotAlive(1);
    }
}